package com.yamind.cloud.modules.app.service;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.UserEntity;

public interface UserManageService{


    //保存APP用户信息
    int saveUser(UserEntity userEntity);

    //获取是否存在手机号码
    UserEntity getUserMobile(String mobile);

}