package com.yamind.cloud.modules.app.service;

import com.yamind.cloud.modules.app.entity.DeviceOtaEntity;

public interface DeviceOtaService {

    //获取用户设备列表
    DeviceOtaEntity compareVersion(String type);
}
