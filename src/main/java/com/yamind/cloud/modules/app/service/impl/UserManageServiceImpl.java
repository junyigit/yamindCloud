package com.yamind.cloud.modules.app.service.impl;

import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.dao.UserMapper;
import com.yamind.cloud.modules.app.entity.UserEntity;
import com.yamind.cloud.common.entity.Page;
import org.springframework.stereotype.Service;
import com.yamind.cloud.modules.app.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

@Service
public class UserManageServiceImpl implements UserManageService {


    @Autowired
    UserMapper userMapper;

    /**
     * 保存新增app用户
     * @param userEntity
     * @return
     */

    public int saveUser(UserEntity userEntity){
        if(getUserMobile(userEntity.getMobile()) != null) {
            return 2;
        }
        int result = userMapper.save(userEntity);
        return result;
    }


    /**
     * 更新用户信息资料
     * @param userEntity
     * @return
     */

    public int updateUserInfo(UserEntity userEntity){
        int result = userMapper.update(userEntity);
        return result;
    }


    /**
     * 获取当前手机号用户是否存在
     * @param mobile
     * @return
     */
    public UserEntity getUserMobile(String mobile){
        UserEntity userEntity = userMapper.getUserMobile(mobile);
        return userEntity;
    }


    /**
     * 获取APP用户列表
     * @return
     */
    public Page<UserEntity> listForUserInfo(Map<String, Object> params){
        Query form = new Query(params);
        Page<UserEntity> page = new Page<>(form);
        userMapper.listForUserInfo(page, form);
        return page;
    }
}
