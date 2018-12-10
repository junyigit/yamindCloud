package com.yamind.cloud.modules.sys.service;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.modules.sys.entity.SysDeviceEntity;

import java.util.Map;

public interface SysDeviceService {

    Page<SysDeviceEntity> listForDevice(Map<String, Object> params);





    int savaRecvDate(String date);
}
