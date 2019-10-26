package com.yamind.cloud.modules.app.service.impl;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.dao.DeviceManageMapper;
import com.yamind.cloud.modules.app.entity.DeviceDataEntity;
import com.yamind.cloud.modules.app.service.DeviceManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deviceManageService")
public class DeviceManageServiceImpl implements DeviceManageService {

    @Autowired
    private DeviceManageMapper deviceManageMapper;


    public R bindUserDeviceInfo(DeviceDataEntity deviceDataEntity){

        int result  =deviceManageMapper.bindUserDeviceInfo(deviceDataEntity);

        return CommonUtils.msgCustom("bind seril:"+deviceDataEntity.getDeviceSerial() +" device success!",result);
    }


    public R deleteUserDeviceInfo(String userId,String serial) {

        int result = deviceManageMapper.deleteUserDeviceInfo(userId, serial);
        return CommonUtils.msgCustom("delect seril:"+serial + " device success!",result);

    }

}
