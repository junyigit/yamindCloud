package com.yamind.cloud.modules.app.service;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.AdvertEntity;

import java.util.List;

public interface AdvertService {



    int addAdvert(AdvertEntity advertEntity);

    //获取广告列表
    List<AdvertEntity> listForAdvertList();
}
