package com.yamind.cloud.modules.app.service.impl;

import com.yamind.cloud.common.annotation.SysLog;
import com.yamind.cloud.modules.app.dao.ProductStoreMapper;
import com.yamind.cloud.modules.app.entity.ProductStoreEntity;
import com.yamind.cloud.modules.app.service.ProductStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductStoreServiceImpl implements ProductStoreService {


    @Autowired
    ProductStoreMapper productStoreMapper;

    public List<ProductStoreEntity> listForProductStoreList(){
        List<ProductStoreEntity> productStoreEntities = productStoreMapper.listForProductStoreList();
        return productStoreEntities;
    }
}
