package com.yamind.cloud.modules.app.api;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.entity.DeviceDataEntity;
import com.yamind.cloud.modules.app.entity.DeviceOtaEntity;
import com.yamind.cloud.modules.app.service.DeviceManageService;
import com.yamind.cloud.modules.app.service.DeviceOtaService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;
import com.yamind.cloud.modules.sys.service.SysParamaterSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户设备管理
 */
@RestController
@RequestMapping("/app/deviceManage")
public class DeviceManageController extends AbstractController {




    @Autowired
    private DeviceManageService deviceManageService;

    @Autowired
    private SysParamaterSetService sysParamaterSetService;

    @Autowired
    private DeviceOtaService deviceOtaService;


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
        Map<String, Object> map = new HashMap<>();
        SysParamaterSetEntity sysParamaterSetEntity =new SysParamaterSetEntity();

        int result = deviceManageService.bindUserDeviceInfo(deviceDataEntity);

        if (deviceDataEntity.getDeviceType()==3){
            map.put("serialId",deviceDataEntity.getDeviceSerial());
            sysParamaterSetEntity= sysParamaterSetService.getParamaterBySerial(map);
        }
        if (result>0) {
            return R.customOk(sysParamaterSetEntity);
        }
        return R.error("bind device faild");
    }


    /**
     * 更新设备
     * 软件版本号(判断是否为最新软件版本)
     * @param type
     * @param version
     * @return
     */
    @RequestMapping(value = "/compareVersion", method = RequestMethod.POST)
    public R compareVersion(String type,String version) {
        R r = new R();
        DeviceOtaEntity d  = deviceOtaService.compareVersion(type);
        if (!("").equals(d) && d!= null && version !=""){
            if (d.getVersion().equals(version)){
                return R.customOk(200,"当前已升至最新版本！");
            }else {
                r.put("code",201);
                r.put("msg","当前不是最新版本，请升级至最新版本固件！");
                r.put("version",d.getVersion());
                switch (type){
                    case "1":
                        r.put("url","http://cloud.yamind.cn:9999/appResource/zips/ota/dfufile-I101-20200115.zip");
                        break;
                    case "2":
                        r.put("url","http://cloud.yamind.cn:9999/appResource/zips/ota/dfufile-I201-20200115.zip");
                        break;
                }

                return r;
            }
        }

        return R.error(500,"compareVersion faild");
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
