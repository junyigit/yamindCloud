package com.yamind.cloud.modules.app.dao;


import com.yamind.cloud.modules.app.entity.SleepDataEntity;
import com.yamind.cloud.modules.sys.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SleepDataMapper extends BaseMapper<SleepDataEntity> {


    List<SleepDataEntity> listForUserSleepData(@Param("userId")String userId, @Param("serial") String serial);
}
