package com.yamind.cloud.modules.sys.oauth2;

import com.yamind.cloud.common.utils.ShiroUtils;
import com.yamind.cloud.modules.app.dao.UserMapper;
import com.yamind.cloud.modules.app.entity.UserEntity;
import com.yamind.cloud.modules.sys.entity.SysUserEntity;
import com.yamind.cloud.modules.sys.entity.SysUserTokenEntity;
import com.yamind.cloud.modules.sys.manager.SysUserManager;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * 认证
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年9月3日 上午3:24:29
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
	
	@Autowired
	private SysUserManager sysUserManager;
	@Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	Long userId = ShiroUtils.getUserId();
		//用户角色
		Set<String> rolesSet = sysUserManager.listUserRoles(userId);
		//用户权限
		Set<String> permsSet = sysUserManager.listUserPerms(userId);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(rolesSet);
		info.setStringPermissions(permsSet);
		return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        SysUserTokenEntity tokenEntity = sysUserManager.getByToken(accessToken);
        UserEntity appUser = new UserEntity();
        SysUserEntity sysUser = new SysUserEntity();
        SimpleAuthenticationInfo info =null;
        //token失效
        if(tokenEntity == null || tokenEntity.getGmtExpire().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        int length = tokenEntity.getUserId().length();// 获取密码的长度

        if (length>10){

            appUser =userMapper.getUserMobile(tokenEntity.getUserId());

            info = new SimpleAuthenticationInfo(appUser, accessToken, getName());
        }else{
            //查询用户信息
            sysUser = sysUserManager.getById(Long.parseLong(tokenEntity.getUserId()));
            //账号锁定
            if(sysUser.getStatus() == 0){
                throw new LockedAccountException("账号已被锁定,请联系管理员");
            }
            info = new SimpleAuthenticationInfo(sysUser, accessToken, getName());
        }

        return info;
    }
}



