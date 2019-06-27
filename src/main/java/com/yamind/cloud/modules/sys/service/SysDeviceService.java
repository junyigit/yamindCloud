package com.yamind.cloud.modules.sys.service;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;

import java.util.List;
import java.util.Map;

/**
 * 远程调参 ---设备信息
 */
public interface SysDeviceService {

    //远程调参 --获取设备信息
    Page<SysParamaterSetEntity> listForDevice(Map<String, Object> params);


    //接受新版本协议数据
    int saveRecvHistoryData(String date);

    //查询设备每天设置信息
    List<SysParamaterSetEntity> listForParamaterInfo(Map<String, Object> params);
}
