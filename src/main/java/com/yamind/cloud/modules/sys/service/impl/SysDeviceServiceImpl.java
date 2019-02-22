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

import java.text.ParseException;
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



    public int saveRecvData(String data) {

        if (!StringUtils.isBlank(data)) {
            try {
                JSONObject jsonObject = JSONObject.fromObject(data);
                //获取数组里面的数据
                JSONObject cureArr = (JSONObject) jsonObject.get("cureData");
                Map<String, String> cureMap = new HashMap<>();
                SysCureDataEntity sysCureDataEntity = new SysCureDataEntity();
                sysCureDataEntity.setCureTime(jsonObject.getString("currentTime"));//当前时间
                sysCureDataEntity.setBootSerial(jsonObject.getString("serialId")); //设置机器序列号
                sysCureDataEntity.setMode(jsonObject.getString("mode")); //设置模式
                sysCureDataEntity.setRealFlow(Float.parseFloat(cureArr.optString("ll", "0")));//实时流量
                sysCureDataEntity.setCureStress(Float.parseFloat(cureArr.optString("zlyl", "0")));//治疗压力
                sysCureDataEntity.setRespiratoryRate(Float.parseFloat(cureArr.optString("hxpl", "0"))); //呼吸频率respiratory_rate
                sysCureDataEntity.setTidalVolume(Float.parseFloat(cureArr.optString("cql", "0"))); //潮气量
                sysCureDataEntity.setMinusTroughput(Float.parseFloat(cureArr.optString("fztql", "0"))); //分钟通气量
                sysCureDataEntity.setAiCount(Integer.parseInt(cureArr.optString("ai", "0"))); //AI  低通气指数
                sysCureDataEntity.setHiCount(Integer.parseInt(cureArr.optString("hi", "0"))); //HI 呼吸暂停出现 1 出现 0没有
                sysCureDataEntity.setSoftVersion(jsonObject.getString("softVersion")); //软件版本

                
                sysCureDataService.save(sysCureDataEntity);
            } catch (Exception e) {
                System.out.println("===========接受数据的时候出现错误============"+e.getMessage());
            }
        }
        return 1;
    }


    public int saveRecvHistoryData(String date){

        String recvArr[] = date.split(",");

        String currentTime = timeStamp2Date(recvArr[1],"yyyy-MM-dd HH:mm:ss");


        //创建实体类接收存储数据
        SysCureDataEntity sysCureDataEntity = new SysCureDataEntity();
        sysCureDataEntity.setCureTime(currentTime);//当前时间
        sysCureDataEntity.setBootSerial(recvArr[1]); //设置机器序列号
        sysCureDataEntity.setRealFlow(Float.parseFloat(recvArr[2]));//实时流量
        sysCureDataEntity.setRespiratoryRate(Float.parseFloat(recvArr[3])); //呼吸释放

        sysCureDataEntity.setAiCount(Integer.parseInt(recvArr[4])); //AI  低通气指数
        sysCureDataEntity.setHiCount(Integer.parseInt(recvArr[5])); //HI 呼吸暂停出现 1 出现 0没有


        return 0;
    }


    /***
     * 时间戳转换
     * @param seconds
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }
}
