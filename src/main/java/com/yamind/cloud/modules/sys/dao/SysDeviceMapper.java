package com.yamind.cloud.modules.sys.dao;

import com.yamind.cloud.modules.sys.entity.SysDeviceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysDeviceMapper extends BaseMapper<SysDeviceEntity>{


    List<SysDeviceEntity> findAll();
}
