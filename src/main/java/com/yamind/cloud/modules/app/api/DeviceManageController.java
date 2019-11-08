package com.yamind.cloud.modules.app.api;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.entity.DeviceDataEntity;
import com.yamind.cloud.modules.app.service.DeviceManageService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户设备管理
 */
@RestController
@RequestMapping("/app/deviceManage")
public class DeviceManageController extends AbstractController {




    @Autowired
    private DeviceManageService deviceManageService;


    /**
     * 获取设备列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public R listForDevice(@RequestParam String userId) {
        List<DeviceDataEntity> list = deviceManageService.listForDevice(userId);
        if (list!=null && list.size() >0){
            return R.customOk(list);
        }
        return R.error("当前用户没有绑定设备");
    }


    /**
     * app扫码绑定设备
     * @param deviceDataEntity
     * @return
     */
    @RequestMapping(value = "/bindDevice", method = RequestMethod.POST)
    public R bindDeviceToUser(DeviceDataEntity deviceDataEntity) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        deviceDataEntity.setBindTime(df.format(new Date()));
        int result = deviceManageService.bindUserDeviceInfo(deviceDataEntity);
        if (result>0) {
            return R.customOk("bind device success");
        }
        return R.error("bind device faild");
    }


    /**
     * 更新设备软件版本号(判断是否为最新软件版本)
     * @param deviceDataEntity
     * @return
     */
    @RequestMapping(value = "/updateSoftVersion", method = RequestMethod.POST)
    public R updateSoftVersion(DeviceDataEntity deviceDataEntity) {
        int result = deviceManageService.updateSoftVersion(deviceDataEntity);
        if (result>0){
            return R.customOk("bind device success");
        }
        return R.error("updateSoftVersion faild");
    }

    /**
     * 解绑删除序列号
     * @param userId 用户id
     * @param serial 序列号
     * @return
     */
    @RequestMapping(value = "/delBindDevice" ,method = RequestMethod.POST)
    public R delBindDevice(@RequestParam String userId, @RequestParam String serial){
        int result =deviceManageService.deleteUserDeviceInfo(userId,serial);
        if (result>0){
            return R.customOk("updateSoftVersion success");
        }
        return R.error("updateSoftVersion faild");
    }

}
