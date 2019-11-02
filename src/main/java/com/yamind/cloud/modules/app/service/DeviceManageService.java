package com.yamind.cloud.modules.app.service;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.DeviceDataEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DeviceManageService {


    //绑定设备
    R bindUserDeviceInfo(DeviceDataEntity appDeviceDataEntity);

    //解除绑定
    R deleteUserDeviceInfo(String userId,String serial);

    //获取用户设备列表
    List<DeviceDataEntity> listForDevice(@RequestParam String userId);
}
