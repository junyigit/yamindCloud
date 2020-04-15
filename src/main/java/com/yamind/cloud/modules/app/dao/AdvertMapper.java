package com.yamind.cloud.modules.app.dao;


import com.yamind.cloud.modules.app.entity.AdvertEntity;
import com.yamind.cloud.modules.sys.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdvertMapper extends BaseMapper<AdvertEntity> {

    List<AdvertEntity> listForAdvertList(Map<String, Object> params);
}
