package com.yamind.cloud.modules.sys.dao;

import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.Map;

@Mapper
public interface SysParamaterSetMapper extends BaseMapper<SysParamaterSetEntity>  {

    SysParamaterSetEntity getParamaterBySerial(Map<String, Object> params);


    //删除过期的设置信息 大于7天的数据
    int delOldDate();
}
