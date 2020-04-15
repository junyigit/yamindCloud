package com.yamind.cloud.modules.app.service;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.AdvertEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface AdvertService {



    //添加广告
    int addAdvert(AdvertEntity advertEntity);

    //获取广告列表
    List<AdvertEntity> listForAdvertList(Map<String, Object> params);

}
