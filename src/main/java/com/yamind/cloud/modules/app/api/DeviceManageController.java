package com.yamind.cloud.modules.app.api;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.DeviceDataEntity;
import com.yamind.cloud.modules.app.service.DeviceManageService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/app/deviceManage")
public class DeviceManageController extends AbstractController {




    @Autowired
    private DeviceManageService deviceManageService;


    @RequestMapping(value = "/bindDevice", method = RequestMethod.POST)
    public R bindDeviceToUser(@RequestParam String userId, @RequestParam String serial, @RequestParam String mac) {
        R r = new R();
        DeviceDataEntity deviceDataEntity = new DeviceDataEntity();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        deviceDataEntity.setUserId(Long.parseLong(userId));
        deviceDataEntity.setDeviceType(1);
        deviceDataEntity.setBindTime(df.format(new Date()));
        deviceDataEntity.setDeviceSerial(serial);
        deviceDataEntity.setMac(mac);
        r = deviceManageService.bindUserDeviceInfo(deviceDataEntity);
        return r;
    }


    /**
     * 解绑删除序列号
     * @param userId 用户id
     * @param serial 序列号
     * @return
     */
    @RequestMapping(value = "/delBindDevice" ,method = RequestMethod.POST)
    public R delBindDevice(@RequestParam String userId, @RequestParam String serial){
        return deviceManageService.deleteUserDeviceInfo(userId,serial);
    }


}