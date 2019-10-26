package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.common.utils.HttpClientUtils;
import com.yamind.cloud.modules.sys.dao.SysParamaterSetMapper;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;
import com.yamind.cloud.modules.sys.entity.SysDeviceStatusEntity;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;
import com.yamind.cloud.modules.sys.manager.SysDeviceManager;
import com.yamind.cloud.modules.sys.service.SysCureDataService;
import com.yamind.cloud.modules.sys.service.SysDeviceService;
import com.yamind.cloud.modules.sys.service.SysDeviceStatusService;
import com.yamind.cloud.modules.sys.service.SysParamaterSetService;
import io.netty.handler.codec.json.JsonObjectDecoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("sysDeviceService")
public class SysDeviceServiceImpl implements SysDeviceService {


    @Autowired
    SysDeviceManager sysDeviceManager;
    @Autowired
    SysCureDataService sysCureDataService;
    @Autowired
    SysDeviceStatusService sysDeviceStatusService;
    @Autowired
    SysParamaterSetService sysParamaterSetService;
    @Autowired
    SysParamaterSetMapper sysParamaterSetMapper;

    @Autowired
    RedisTemplate redisTemplate;



    @Override
    public Page<SysParamaterSetEntity> listForDevice(Map<String, Object> params) {
        Query form = new Query(params);
        Page<SysParamaterSetEntity> page = new Page<>(form);
        sysDeviceManager.listForParaSet(page, form);
        return page;
    }



    /**
     * 接收客户端 主动同步[历史数据]
     * @param data
     * @returnX
     */
    public int saveRecvHistoryData(String data) {

        String[] recvArr = data.split(",");
        switch (recvArr[0]){
            case "P":
                if (recvArr.length == 24) {

                    SysParamaterSetEntity sysParamaterSetEntity = new SysParamaterSetEntity();

                    //序列号
                    sysParamaterSetEntity.setSerialId(recvArr[1]);

                    //转换时间
                    String currentTime = timeStamp2Date(recvArr[2], "yyyy-MM-dd HH:mm:ss");

                    //设置时间
                    sysParamaterSetEntity.setUseDate(currentTime);

                    //机器型号
                    sysParamaterSetEntity.setBootType(recvArr[3]);

                    //模式
                    sysParamaterSetEntity.setMode(recvArr[4]);
                    //治疗压力
                    sysParamaterSetEntity.setCureStress(Float.parseFloat(recvArr[5]));

                    //开始压力
                    sysParamaterSetEntity.setStartStress(Float.parseFloat(recvArr[6]));

                    //延迟时间
                    sysParamaterSetEntity.setDelayTime(Integer.parseInt(recvArr[7]));

                    //最大压力
                    sysParamaterSetEntity.setMaxStress(Float.parseFloat(recvArr[8]));

                    //最小压力
                    sysParamaterSetEntity.setMinStress(Float.parseFloat(recvArr[9]));

                    //最大吸气压力
                    sysParamaterSetEntity.setMaxInhaleStress(Float.parseFloat(recvArr[10]));

                    //最小吸气压力
                    sysParamaterSetEntity.setMinInhaleStress(Float.parseFloat(recvArr[11]));

                    //吸气压力
                    sysParamaterSetEntity.setInhaleStress(Float.parseFloat(recvArr[12]));

                    //呼气压力
                    sysParamaterSetEntity.setExhaleStress(Float.parseFloat(recvArr[13]));

                    //目标潮气量
                    sysParamaterSetEntity.setTidalVolume(Integer.parseInt(recvArr[14]));

                    //呼吸频率
                    sysParamaterSetEntity.setRespiratoryRate(Float.parseFloat(recvArr[15]));

                    //吸气时间
                    sysParamaterSetEntity.setInhaleTime(Float.parseFloat(recvArr[16]));

                    //呼吸释放
                    sysParamaterSetEntity.setExhaleRelease(Integer.parseInt(recvArr[17]));

                    //吸气灵敏度
                    sysParamaterSetEntity.setInhaleSensitivity(Integer.parseInt(recvArr[18]));

                    //呼气灵敏度
                    sysParamaterSetEntity.setExhaleSensitivity(Integer.parseInt(recvArr[19]));

                    //压力上升坡度
                    sysParamaterSetEntity.setStressUp(Integer.parseInt(recvArr[20]));

                    //压力下降坡度
                    sysParamaterSetEntity.setStressDown(Integer.parseInt(recvArr[21]));

                    //avaps
                    sysParamaterSetEntity.setAvaps(Integer.parseInt(recvArr[22]));

                    //软件版本号
                    sysParamaterSetEntity.setSoftVersion(recvArr[23]);


                    sysParamaterSetService.savePara(sysParamaterSetEntity);
                    //存入redis
                    redisTemplate.opsForValue().set("P"+recvArr[1],data);

                } else {
                    System.err.println(data);
                }
                break;
            case "D":
                if (recvArr.length == 22) {
                    //创建实体类接收存储数据
                    SysCureDataEntity sysCureDataEntity = new SysCureDataEntity();
                    String currentTime = timeStamp2Date(recvArr[2], "yyyy-MM-dd HH:mm:ss");
                    sysCureDataEntity.setCureTime(currentTime);//当前时间
                    sysCureDataEntity.setBootSerial(recvArr[1]); //设置机器序列号
                    sysCureDataEntity.setRealFlow1(Float.parseFloat(recvArr[3]));//实时流量1
                    sysCureDataEntity.setRealFlow2(Float.parseFloat(recvArr[4]));//实时流量2
                    sysCureDataEntity.setRealFlow3(Float.parseFloat(recvArr[5]));//实时流量3
                    sysCureDataEntity.setRealFlow4(Float.parseFloat(recvArr[6]));//实时流量4
                    sysCureDataEntity.setRealFlow5(Float.parseFloat(recvArr[7]));//实时流量50

                    sysCureDataEntity.setLeakage(Float.parseFloat(recvArr[8])); //漏气量

                    sysCureDataEntity.setCureStress1(Float.parseFloat(recvArr[9]));//治疗压力
                    sysCureDataEntity.setCureStress2(Float.parseFloat(recvArr[10]));//治疗压力
                    sysCureDataEntity.setCureStress3(Float.parseFloat(recvArr[11]));//治疗压力
                    sysCureDataEntity.setCureStress4(Float.parseFloat(recvArr[12]));//治疗压力
                    sysCureDataEntity.setCureStress5(Float.parseFloat(recvArr[13]));//治疗压力

                    sysCureDataEntity.setTidalVolume(Integer.parseInt(recvArr[14]));//潮气量
                    sysCureDataEntity.setMinusTroughput( Float.parseFloat(recvArr[15]));//分钟通气量
                    sysCureDataEntity.setRespiratoryRate(Float.parseFloat(recvArr[16])); //呼吸频率
                    sysCureDataEntity.setAiCount(Float.parseFloat(recvArr[17])); //AI  低通气指数
                    sysCureDataEntity.setHiCount(Float.parseFloat(recvArr[18])); //HI 呼吸暂停出现 1 出现 0没有

                    sysCureDataEntity.setXhb(Float.parseFloat(recvArr[19])); //吸呼比
                    sysCureDataEntity.setInhaleStress(Float.parseFloat(recvArr[20])); //吸气压力
                    sysCureDataEntity.setExhaleStress(Float.parseFloat(recvArr[21])); //呼气压力

                    sysCureDataService.saveData(sysCureDataEntity);

                    //存入redis
                    redisTemplate.opsForList().leftPush(recvArr[1],data);

                } else {
                    System.err.println(data);
                }
                break;
            case "S":

                SysDeviceStatusEntity sysDeviceStatusEntity = new SysDeviceStatusEntity();

                System.out.println("序列号为:"+recvArr[1]+"开始运行了");

                //设置序列号
                sysDeviceStatusEntity.setSerialId(recvArr[1]);
                //转换时间
                String startTime = timeStamp2Date(recvArr[2], "yyyy-MM-dd HH:mm:ss");
                //设置开始时间
                sysDeviceStatusEntity.setStartTime(startTime);
                //设置状态
                sysDeviceStatusEntity.setStatus(1);

                sysDeviceStatusService.saveData(sysDeviceStatusEntity);




                ///////////////

                //推送JSON包
                JSONObject boeCpodS = new JSONObject();

                //序列号
                boeCpodS.put("deviceSN",recvArr[1]);
                boeCpodS.put("measureData","");
                boeCpodS.put("spo2Statistic","");
                boeCpodS.put("type","begin");
                boeCpodS.put("createAt",Calendar.getInstance().getTimeInMillis());
                System.out.println(boeCpodS);
                String startHttp = HttpClientUtils.httpPost("http://copdtest.appsbu.com:8000/api/device/daya-resperitor-receiver",boeCpodS.toString());
                com.alibaba.fastjson.JSONObject startJson = com.alibaba.fastjson.JSONObject.parseObject(startHttp);
                String startResult = startJson.getString("status");
                if("ok".equals(startResult)){
                    System.out.println("成功推送开始命令:"+boeCpodS.toString());
                }

                break;

            case "T":
                System.out.println("序列号为:"+recvArr[1]+"结束了");
                SysDeviceStatusEntity sysDeviceStatusEntity2 = new SysDeviceStatusEntity();

                //设置序列号
                sysDeviceStatusEntity2.setSerialId(recvArr[1]);
                //转换时间
                String entTime = timeStamp2Date(recvArr[2], "yyyy-MM-dd HH:mm:ss");
                //设置开始时间
                sysDeviceStatusEntity2.setStopTime(entTime);

                //设置状态
                sysDeviceStatusEntity2.setStatus(0);
                sysDeviceStatusService.saveData(sysDeviceStatusEntity2);

                ////////////////////////////////////////////

                //推送JSON包
                JSONObject boeCpod = new JSONObject();

                //测量数据Json
                JSONObject measureJson = new JSONObject();

                Map<String,Object> map = new HashMap<>();
                SysParamaterSetEntity sysParamaterSetEntity = new SysParamaterSetEntity();
                SysDeviceStatusEntity sysDeviceStatusEntity1 = new SysDeviceStatusEntity();

                sysDeviceStatusEntity1 = sysDeviceStatusService.getDeviceStatusBySerialId(recvArr[1]);
                map.put("serialId",recvArr[1]);
                map.put("startDate",sysDeviceStatusEntity1.getStartTime());
                map.put("endDate",sysDeviceStatusEntity1.getStopTime());
                map.put("seleceTime",timeStamp2Date(recvArr[2], "yyyy-MM-dd"));

                //获取平均压力和AHI
                Map<String,String> avgMap = new HashMap<>();
                avgMap = sysCureDataService.getStatDataMaxAndAvg(map);
                //获取序列号设置信息
                sysParamaterSetEntity =  sysParamaterSetService.getParamaterBySerial(map);


                //序列号
                boeCpod.put("deviceSN",recvArr[1]);

                //MeasureArr数组内容
                measureJson.put("startTime",StringUtils.substringBefore(sysDeviceStatusEntity1.getStartTime(),"."));
                measureJson.put("endTime",StringUtils.substringBefore(sysDeviceStatusEntity1.getStopTime(),"."));
                measureJson.put("pint",sysParamaterSetEntity.getStartStress());
                measureJson.put("pset",sysParamaterSetEntity.getCureStress());

                //设置信息 - 最大压力和最小压力
                measureJson.put("pmax",sysParamaterSetEntity.getMaxStress());
                measureJson.put("pmin",sysParamaterSetEntity.getMinStress());


                //没有该字段 赋值为0
                measureJson.put("heat","");
                measureJson.put("erp","");

                //设置AI、HI、AHI平均值
                measureJson.put("aiAvg",avgMap.get("aipjz"));
                measureJson.put("hiAvg",avgMap.get("hipjz"));
                measureJson.put("ahiAvg",avgMap.get("pjahi"));

                //压力平均值
                measureJson.put("pAvg",avgMap.get("ylpjz"));
                //漏气量平均值
                measureJson.put("leakAvg",avgMap.get("lqlpjz"));
                //详细数据为空
                measureJson.put("detail","");
                ////////////////////////////////////////////


                boeCpod.put("measureData",measureJson);
                boeCpod.put("spo2Statistic","");
                boeCpod.put("type","end");
                boeCpod.put("createAt",Calendar.getInstance().getTimeInMillis());

                String endHttp = HttpClientUtils.httpPost("http://copdtest.appsbu.com:8000/api/device/daya-resperitor-receiver",boeCpod.toString());
                com.alibaba.fastjson.JSONObject endJson = com.alibaba.fastjson.JSONObject.parseObject(endHttp);
                String endResult = endJson.getString("status");
                if("ok".equals(endResult)){
                    System.out.println("成功推送结束命令:"+boeCpod.toString());
                }
                break;
        }
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


    /**
     * 获取用户设备的设置信息
     * @param params
     * @return
     */
    public List<SysParamaterSetEntity> listForParamaterInfo(Map<String, Object> params){
        Query query = new Query(params);
        sysParamaterSetMapper.list(query);
        return null;
    }
}
