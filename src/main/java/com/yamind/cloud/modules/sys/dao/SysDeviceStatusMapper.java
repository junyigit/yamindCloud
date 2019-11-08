package com.yamind.cloud.modules.sys.dao;

import com.yamind.cloud.modules.sys.entity.SysDeviceStatusEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface SysDeviceStatusMapper extends BaseMapper<SysDeviceStatusEntity> {


    SysDeviceStatusEntity getDeviceStatusBySerialId(String serialId);

    List<SysDeviceStatusEntity> listForOnlineDevice();
}
