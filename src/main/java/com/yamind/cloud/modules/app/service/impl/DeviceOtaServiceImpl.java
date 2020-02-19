package com.yamind.cloud.modules.app.service.impl;

import com.yamind.cloud.modules.app.dao.DeviceOtaMapper;
import com.yamind.cloud.modules.app.entity.DeviceOtaEntity;
import com.yamind.cloud.modules.app.service.DeviceOtaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceOtaServiceImpl implements DeviceOtaService {


    @Autowired
    DeviceOtaMapper deviceOtaMapper;
    public DeviceOtaEntity compareVersion(String type){
        return deviceOtaMapper.compareVersion(type);
    }
}
