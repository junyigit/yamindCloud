package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;
import com.yamind.cloud.modules.sys.entity.SysDeviceEntity;
import com.yamind.cloud.modules.sys.entity.SysPatientEntity;
import com.yamind.cloud.modules.sys.manager.SysDeviceManager;
import com.yamind.cloud.modules.sys.service.SysCureDataService;
import com.yamind.cloud.modules.sys.service.SysDeviceService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service("sysDeviceService")
public class SysDeviceServiceImpl implements SysDeviceService {




    @Autowired
    SysDeviceManager sysDeviceManager;
    @Autowired
    SysCureDataService sysCureDataService;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Page<SysDeviceEntity> listForDevice(Map<String, Object> params){
        Query form = new Query(params);
        Page<SysDeviceEntity> page = new Page<>(form);
        sysDeviceManager.listForDevice(page, form);
        return page;
    }



    public int savaRecvDate(String date) {


        //tempData = tempData.substring(1,tempData.length()-1);
        if (!StringUtils.isBlank(date)) {
            try {
                JSONObject jsonObject = JSONObject.fromObject(date);
                //获取数组里面的数据
                JSONObject cureArr = (JSONObject) jsonObject.get("cureData");
                Map<String, String> cureMap = new HashMap<>();
                SysCureDataEntity sysCureDataEntity = new SysCureDataEntity();
                sysCureDataEntity.setCureTime(jsonObject.getString("currentTime"));//当前时间
                sysCureDataEntity.setBootSerial(jsonObject.getString("serialId")); //设置机器序列号
                sysCureDataEntity.setMode(jsonObject.getString("mode")); //设置模式
                sysCureDataEntity.setRealFlow(Float.parseFloat(cureArr.optString("ll", "0")));//实时流量
                sysCureDataEntity.setCureStress(Float.parseFloat(cureArr.optString("zlyl", "0")));//治疗压力
                sysCureDataEntity.setMinStress(Float.parseFloat(cureArr.optString("zxyl", "0")));//最大压力
                sysCureDataEntity.setMaxStress(Float.parseFloat(cureArr.optString("zdyl", "0"))); //最小压力
                sysCureDataEntity.setMinusTroughput(Float.parseFloat(cureArr.optString("fztql", "0"))); //分钟通气量
                sysCureDataEntity.setMaxInhaleStress(Float.parseFloat(cureArr.optString("zdxqyl", "0"))); //最大吸气压力
                sysCureDataEntity.setInhaleStress(Float.parseFloat(cureArr.optString("xqyl", "0"))); //吸气压力
                sysCureDataEntity.setExhaleStress(Float.parseFloat(cureArr.optString("hqyl", "0"))); //呼气压力
                sysCureDataEntity.setRespiratoryRate(Float.parseFloat(cureArr.optString("hxpl", "0"))); //呼吸频率respiratory_rate
                sysCureDataEntity.setTidalVolume(Float.parseFloat(cureArr.optString("cql", "0"))); //潮气量
                sysCureDataEntity.setInhaleTime(Float.parseFloat(cureArr.optString("xqsj", "0.0"))); //吸气时间
                sysCureDataEntity.setExhaleRelease(Integer.parseInt(cureArr.optString("hxsf", "0"))); //'呼气释放'
                sysCureDataEntity.setInhaleSensitivity(Integer.parseInt(cureArr.optString("xqlmd", "0"))); //'吸气灵敏度'
                sysCureDataEntity.setExhaleSensitivity(Integer.parseInt(cureArr.optString("hqlmd", "0"))); //呼气灵敏度

                sysCureDataEntity.setAiCount(Integer.parseInt(cureArr.optString("ai", "0"))); //AI  低通气指数
                sysCureDataEntity.setHiCount(Integer.parseInt(cureArr.optString("hi", "0"))); //HI 呼吸暂停出现 1 出现 0没有

                sysCureDataEntity.setContinueTime(Integer.parseInt(cureArr.optString("sq", "0"))); //持续时间

                sysCureDataEntity.setStressUp(Integer.parseInt(cureArr.optString("ylsspd", "0"))); //压力上升坡度
                sysCureDataEntity.setStressDown(Integer.parseInt(cureArr.optString("ylxjpd", "0"))); //压力下降坡度
                sysCureDataEntity.setAvaps(Integer.parseInt(cureArr.optString("avaps", "0"))); //平均容量保证压力支持
                sysCureDataEntity.setSoftVersion(jsonObject.getString("softVersion")); //软件版本


                sysCureDataService.sava(sysCureDataEntity);
            } catch (Exception e) {
                System.out.println("===========接受数据的时候出现错误============"+e.getMessage());
            }
        }
        return 1;
    }
}
