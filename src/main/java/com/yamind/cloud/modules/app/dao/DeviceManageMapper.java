package com.yamind.cloud.modules.app.dao;

import com.yamind.cloud.modules.app.entity.DeviceDataEntity;
import com.yamind.cloud.modules.sys.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeviceManageMapper extends BaseMapper<DeviceDataEntity> {

    int bindUserDeviceInfo(DeviceDataEntity deviceDataEntity);

    int deleteUserDeviceInfo(@Param("userId") String userId, @Param("serial") String serial);

    List<DeviceDataEntity> listForDevice(@Param("userId") String userId);
}
