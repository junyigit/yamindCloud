package com.yamind.cloud.modules.app.service.impl;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.dao.AdvertMapper;
import com.yamind.cloud.modules.app.entity.AdvertEntity;
import com.yamind.cloud.modules.app.service.AdvertService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    AdvertMapper advertMapper;


    /**
     * 添加广告
     * @param advertEntity
     * @return
     */
    public int addAdvert(AdvertEntity advertEntity){
        return advertMapper.save(advertEntity);
    }

    public List<AdvertEntity> listForAdvertList(){
        return advertMapper.listForAdvertList();
    }
}
