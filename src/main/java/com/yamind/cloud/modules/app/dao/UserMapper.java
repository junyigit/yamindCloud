package com.yamind.cloud.modules.app.dao;


import com.yamind.cloud.modules.app.entity.UserEntity;
import com.yamind.cloud.modules.sys.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity>{

    UserEntity getUserMobile(String mobile);


    List<UserEntity> listForUserInfo(Page<UserEntity> page, Query search);
}
