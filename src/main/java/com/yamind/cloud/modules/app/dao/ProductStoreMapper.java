package com.yamind.cloud.modules.app.dao;

import com.yamind.cloud.modules.app.entity.ProductStoreEntity;
import com.yamind.cloud.modules.sys.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ProductStoreMapper extends BaseMapper<ProductStoreEntity> {

    List<ProductStoreEntity> listForProductStoreList();
}
