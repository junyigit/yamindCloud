package com.yamind.cloud.modules.sys.controller;

import com.yamind.cloud.common.annotation.SysLog;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.HttpClientUtils;
import com.yamind.cloud.common.utils.MD5Utils;
import com.yamind.cloud.common.utils.ShiroUtils;
import com.yamind.cloud.modules.sys.entity.SysUserEntity;
import com.yamind.cloud.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 用户controller
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月8日 下午2:48:50
 */

@RestController
@RequestMapping("/sys")
public class SysLoginController extends AbstractController {
	
	@Autowired
	private SysUserService sysUserService;


	/**
	 * 登录
	 */
	@SysLog("登录")
	@ApiOperation(value = "查询用户信息", notes = "查询用户信息...")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public R login(@RequestParam String username,@RequestParam String password)throws IOException {
		SysUserEntity user = sysUserService.getByUserName(username);
		password = MD5Utils.encrypt(username, password);
		
		if(user == null || !user.getPassword().equals(password)) {
			return R.error("用户名或密码错误");
		}
		
		if(user.getStatus() == 0) {
			return R.error("账号已被锁定,请联系管理员");
		}
		return sysUserService.saveUserToken(String.valueOf(user.getUserId()));
	}


	/**
	 * 退出
	 */
	@SysLog("退出系统")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public R logout() {
		R r = sysUserService.updateUserToken(getUserId());
		ShiroUtils.logout();
		return r;
	}
	
}
