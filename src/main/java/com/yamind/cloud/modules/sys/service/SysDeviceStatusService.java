package com.yamind.cloud.modules.sys.service;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.entity.SysDeviceStatusEntity;

import java.util.List;

public interface SysDeviceStatusService {


    //保存治疗数据
    R saveData(SysDeviceStatusEntity des);

    //根据设备序列号查询当前设备在线状态
    SysDeviceStatusEntity getDeviceStatusBySerialId(String serialId);

    //获取所有设备当前状态
    List<SysDeviceStatusEntity> listForOnlineDevice();


}
