package com.yamind.cloud.modules.sys.dao;

import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysDeviceMapper extends BaseMapper<SysParamaterSetEntity>{


    List<SysParamaterSetEntity> findAll();
}
