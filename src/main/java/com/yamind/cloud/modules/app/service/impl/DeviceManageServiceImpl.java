package com.yamind.cloud.modules.app.service.impl;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.dao.DeviceManageMapper;
import com.yamind.cloud.modules.app.entity.DeviceDataEntity;
import com.yamind.cloud.modules.app.service.DeviceManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service("deviceManageService")
public class DeviceManageServiceImpl implements DeviceManageService {

    @Autowired
    private DeviceManageMapper deviceManageMapper;


    public int bindUserDeviceInfo(DeviceDataEntity deviceDataEntity){

        return deviceManageMapper.bindUserDeviceInfo(deviceDataEntity);
    }


    public int deleteUserDeviceInfo(String userId,String serial) {

        int result = deviceManageMapper.deleteUserDeviceInfo(userId, serial);
        return result;

    }

    public List<DeviceDataEntity> listForDevice(@RequestParam String userId){
        return deviceManageMapper.listForDevice(userId);
    }

    public int compareVersion(DeviceDataEntity deviceDataEntity){
        return deviceManageMapper.update(deviceDataEntity);
    }
}
