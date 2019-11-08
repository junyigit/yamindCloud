package com.yamind.cloud.modules.app.service;


import com.yamind.cloud.modules.app.entity.UserEntity;
import com.yamind.cloud.common.entity.Page;

import java.util.Map;

public interface UserManageService{


    //保存APP用户信息
    int saveUser(UserEntity userEntity);

    //更新用户资料信息
    int updateUserInfo(UserEntity userEntity);

    //获取是否存在手机号码
    UserEntity getUserMobile(String mobile);

    //获取用户列表
    Page<UserEntity>  listForUserInfo(Map<String, Object> params);
}