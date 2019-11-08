package com.yamind.cloud.modules.app.service;

import com.yamind.cloud.modules.app.entity.ProductStoreEntity;

import java.util.List;

public interface ProductStoreService {


    //保存商品信息
    int saveProductInfo(ProductStoreEntity productStoreEntity);

    //更新商品信息
    int updateProductInfo(ProductStoreEntity productStoreEntity);

    List<ProductStoreEntity> listForProductStoreList();
}
