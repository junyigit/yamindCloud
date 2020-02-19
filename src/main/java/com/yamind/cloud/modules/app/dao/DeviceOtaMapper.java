package com.yamind.cloud.modules.app.dao;


import com.yamind.cloud.modules.app.entity.DeviceOtaEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceOtaMapper {

    DeviceOtaEntity compareVersion(String type);
}
