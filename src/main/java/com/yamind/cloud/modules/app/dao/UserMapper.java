package com.yamind.cloud.modules.app.dao;

import com.yamind.cloud.modules.app.entity.UserEntity;
import com.yamind.cloud.modules.sys.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity>{

    UserEntity getUserMobile(String mobile);

}
