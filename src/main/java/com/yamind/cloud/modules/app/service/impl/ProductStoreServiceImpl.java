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


    /**
     * 获取商城商品信息
     * @return
     */
    public List<ProductStoreEntity> listForProductStoreList(){
        List<ProductStoreEntity> productStoreEntities = productStoreMapper.listForProductStoreList();
        return productStoreEntities;
    }


    /**
     * 添加商品信息
     * @param productStoreEntity
     * @return
     */
    public int saveProductInfo(ProductStoreEntity productStoreEntity){
        return productStoreMapper.save(productStoreEntity);
    }


    /**
     * 更新商品信息
     * @param productStoreEntity
     * @return
     */
    public int updateProductInfo(ProductStoreEntity productStoreEntity){
        return productStoreMapper.update(productStoreEntity);
    }
}
