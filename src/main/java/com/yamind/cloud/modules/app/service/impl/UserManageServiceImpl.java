package com.yamind.cloud.modules.app.service.impl;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.dao.UserMapper;
import com.yamind.cloud.modules.app.entity.UserEntity;
import org.springframework.stereotype.Service;
import com.yamind.cloud.modules.app.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;

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
     * 获取当前手机号用户是否存在
     * @param mobile
     * @return
     */
    public UserEntity getUserMobile(String mobile){
        UserEntity userEntity = userMapper.getUserMobile(mobile);
        return userEntity;
    }
}
