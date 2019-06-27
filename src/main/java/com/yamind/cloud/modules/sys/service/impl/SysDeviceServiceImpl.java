package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.dao.SysParamaterSetMapper;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;
import com.yamind.cloud.modules.sys.manager.SysDeviceManager;
import com.yamind.cloud.modules.sys.service.SysCureDataService;
import com.yamind.cloud.modules.sys.service.SysDeviceService;
import com.yamind.cloud.modules.sys.service.SysParamaterSetService;
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

        if (recvArr[0].equals("P")) {

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
                return 0;
            } else {
                System.err.println(data);
            }
        }

            if (recvArr[0].equals("D")) {


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
                    sysCureDataEntity.setRealFlow5(Float.parseFloat(recvArr[7]));//实时流量5

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

                    //数据信息存入Redis
                    redisTemplate.opsForList().leftPush(recvArr[1],data);

                    return 0;
                } else {
                    System.err.println(data);
                }
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
