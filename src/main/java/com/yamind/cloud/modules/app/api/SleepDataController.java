package com.yamind.cloud.modules.app.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.SleepDataEntity;
import com.yamind.cloud.modules.app.service.SleepDataService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;
import com.yamind.cloud.modules.sys.service.SysCureDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yamind.cloud.modules.sys.controller.SysPatientController.addDays;


@RestController
@RequestMapping("/app/sleep")
public class SleepDataController extends AbstractController {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SleepDataService sleepDataService;

    @Autowired
    SysCureDataService sysCureDataService;


    /**
     * 保存用户的睡眠数据
     * @return
     */
    @RequestMapping("/saveSleepData")
    public R saveUserSleepData(SleepDataEntity sleepDataEntity){
        R r = new R();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String uploadTime = formatter.format(currentTime);

        String sleepTime = timeStamp2Date(sleepDataEntity.getSleepDataTime(),"yyyy-MM-dd");
        //格式化数据时间
        sleepDataEntity.setSleepDataTime(sleepTime);
        //格式化上传时间
        sleepDataEntity.setUploadTime(uploadTime);
        //保存redis的路径
        sleepDataEntity.setPath(sleepDataEntity.getSerial()+sleepTime);

        int count = sleepDataService.saveUserSleepData(sleepDataEntity);

        if (count>0){
            //将数据存至REDIS
            redisTemplate.opsForValue().set(sleepDataEntity.getPath(),sleepDataEntity.getData());
           /* r.put("code",200);
            r.put("msg","upload sleep data success");*/
           return R.customOk("upload sleep data success");
        }
        return R.error("upload faild");
    }


    /**
     * 获取用户的睡眠数据
     * @param serial
     * @return
     */
    @RequestMapping("/listForUserSleepData")
    @ResponseBody
    public R listForUserSleepData(@RequestParam String userId,@RequestParam String serial){
        R r = new R();

        JSONArray dataArry = new JSONArray();



        List<SleepDataEntity> list = sleepDataService.listForUserSleepData(userId,serial);
        if (list !=null && list.size()>0){

            for (SleepDataEntity sleep:list){
                JSONObject dataJson = new JSONObject();
                String redisName = sleep.getSerial()+sleep.getSleepDataTime();
                String stringValueAppend =(String)redisTemplate.opsForValue().get(redisName);

                dataJson.put("date",sleep.getSleepDataTime());
                dataJson.put("sleepData",stringValueAppend);
                dataArry.add(dataJson);
            }
            r.put("userId",userId);
            r.put("serial",serial);
            r.put("data",dataArry);
            r.put("code",200);
        }else{
            r.put("code",500);
            r.put("msg","当前序列号没有睡眠数据");
        }

        return r;
    }


    /**
     * 获取呼吸机统计数据
     * @param serialId
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/getBreathingData", method = RequestMethod.POST)
    public R getHistoryStatInfo(@RequestParam("serialId") String serialId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        R r = new R();

        Map<String, Object> map = new HashMap<>();
        List<String> colData = new ArrayList<>();
        //获取前台传递的参数
        map.put("serialId", serialId);

        //日期从12点开始计算
        startDate += " 12:00:00";
        endDate += " 12:00:00";
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<SysCureDataEntity> colsData = new ArrayList<>();
        // 查询当前日期所有的数据List
        map.put("colName", "DATE_FORMAT(cure_time, \"%Y-%m-%d\" ) as cure_time,inhale_stress,cure_stress,exhale_stress,tidal_volume,minu_throughput,respiratory_rate,ai,hi");
        colsData = sysCureDataService.listForColData2(map);
        int size = colsData.size();
        if (size <= 0) {
            return R.error("当前时间段没有监测到治疗数据!");
        }
        //查询最大值最小值
        r.put("maxAvg", sysCureDataService.getStatDataMaxAndAvg(map)); //参数:最大值最小值的


        //获取查询的数据 AI 数据大于0的值
        long aicount = colsData.stream().filter(e  -> e.getAi() > 0).count();
        //获取查询的数据 HI 数据大于0的值
        long hicount = colsData.stream().filter(e  -> e.getHi() > 0).count();


        //中位值-  统计信息
        // 奇数 N+1/2   偶数 （长度/2的值 + 长度/2+1位)/2
        int median = 0;
        if (size % 2 == 0) {
            median = size / 2;
        } else {
            median = (size / 2) + 1;
        }
        r.put("cureStress", colsData.get(median).getCureStress());
        r.put("inhaleStress", colsData.get(median).getInhaleStress());
        r.put("exhaleStress", colsData.get(median).getExhaleStress());
        r.put("tidalVolume", colsData.get(median).getTidalVolume());
        r.put("minuThroughput", colsData.get(median).getMinusTroughput());
        r.put("respiratoryRate", colsData.get(median).getRespiratoryRate());





        // 根据 cure_time 对数据进行分组
        Map<String, List<SysCureDataEntity>> cureTimeMap = colsData.stream().collect(Collectors.groupingBy(SysCureDataEntity::getCureTime));
        // 获取使用天数
        int useDay = cureTimeMap.size();
        //单位转换为小时
        double useHours = new BigDecimal((float) size / 3600).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();



        r.put("avgAi",aicount/ (useHours>1.00 ? useHours:1));
        r.put("avgHi",hicount/ (useHours>1.00 ? useHours:1));

        r.put("avgAHI",(aicount+hicount )/ (useHours>1.00 ? useHours:1));


        //使用信息
        r.put("dayCount", size); //使用天数
        r.put("useDay", useDay); ////获取所选时间内的 使用天数
        r.put("useTime", useHours + " h"); //使用时间  单位小时

        r.put("avgUseTime", useHours / useDay + " h"); //平均使用时间

        // 获取 values
        Collection<List<SysCureDataEntity>> cureTimeList = cureTimeMap.values();
       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sDte = null; //开始日期
        Date eDte = null; //结束日期

        List<String> sData = new ArrayList<String>();*/


        //存放单个95值
        Double sigleNice1 = 0.00;
        Double sigleNice2 = 0.00;
        Double sigleNice3 = 0.00;
        Double sigleNice4 = 0.00;
        Double sigleNice5 = 0.00;
        Double sigleNice6 = 0.00;


        // 每一组的每一个字段进行排序并获取 95 值
        for (List<SysCureDataEntity> e : cureTimeList) {



            System.out.println(e.get(0).getCureTime());
            //取出数据总长度，计算95%位置
            int len = (int) Math.ceil(e.size() * (0.95)) - 1;
            sigleNice1 += e.stream().map(SysCureDataEntity::getCureStress).sorted()
                    .collect(Collectors.toList()).get(len);
            sigleNice2 += e.stream().map(SysCureDataEntity::getInhaleStress).sorted()
                    .collect(Collectors.toList()).get(len);
            sigleNice3 += e.stream().map(SysCureDataEntity::getExhaleStress).sorted()
                    .collect(Collectors.toList()).get(len);
            sigleNice4 += e.stream().map(SysCureDataEntity::getTidalVolume).sorted()
                    .collect(Collectors.toList()).get(len);
            sigleNice5 += e.stream().map(SysCureDataEntity::getMinusTroughput).sorted()
                    .collect(Collectors.toList()).get(len);
            sigleNice6 += e.stream().map(SysCureDataEntity::getRespiratoryRate).sorted()
                    .collect(Collectors.toList()).get(len);



        }



        System.out.println("ai的出现次数:"+aicount +"hi出现的次数"+hicount);
        //存储压力95的list cure_stress
        r.put("stressNice", sigleNice1 / useDay);

        //存储吸气压力95的list inhale_stress
        r.put("inhaleStressNice", sigleNice2 / useDay);

        //存储呼气压力95的list exhale_stress
        r.put("exhaleStressNice", sigleNice3 / useDay);

        //存储潮气量95的list  tidal_volume
        r.put("tidalVolumeNice", sigleNice4 / useDay);

        //存储分钟通气量95的list  minu_throughput
        r.put("minuThroughputNice", sigleNice5 / useDay);

        //存储呼吸频率95的list  respiratory_rate
        r.put("respiratoryRateNice", sigleNice6 / useDay);

        return r;
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
