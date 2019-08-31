package com.yamind.cloud.modules.sys.service;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.entity.SysDeviceStatusEntity;

public interface SysDeviceStatusService {


    //保存治疗数据
    R saveData(SysDeviceStatusEntity des);
    //获取设备的状态
    SysDeviceStatusEntity getDeviceStatusBySerialId(String serialId);


}
