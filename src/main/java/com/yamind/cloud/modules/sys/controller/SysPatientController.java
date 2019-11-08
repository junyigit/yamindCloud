package com.yamind.cloud.modules.sys.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yamind.cloud.common.annotation.SysLog;import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.HttpClientUtils;
import com.yamind.cloud.modules.sys.entity.*;
import com.yamind.cloud.modules.sys.service.SysCureDataService;
import com.yamind.cloud.modules.sys.service.SysDeviceStatusService;
import com.yamind.cloud.modules.sys.service.SysParamaterSetService;
import com.yamind.cloud.modules.sys.service.SysPatientService;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.yamind.cloud.common.entity.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/sys/patient")
public class SysPatientController extends AbstractController {


    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SysPatientService sysPatientService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysCureDataService sysCureDataService;
    @Autowired
    private SysParamaterSetService sysParamaterSetService;
    @Autowired
    private SysDeviceStatusService sysDeviceStatusService;

    /**
     * 用户列表
     *
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public Page<SysPatientEntity> list(@RequestBody Map<String, Object> params) {
        Page<SysPatientEntity> list = sysPatientService.listForPatient(params);
        return list;
    }


    /**
     * 获取历史数据-曲线图
     *
     * @param serialId
     * @param time
     * @return
     */

    @RequestMapping(value = "/findMapWithSerial", method = RequestMethod.POST)
    public R findMapWithSerial(@RequestParam("serialId") String serialId, @RequestParam("time") String time) {
    //测试程序运行时间  测试程序在start和end中间
        LocalDateTime start = LocalDateTime.now();
        R r = new R();
        Map<String, Object> map = new HashMap<>();
        //日期格式化
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date eDte = null;
        Date sDte = null;
        //模式
        String mode = "";
        try {
            eDte = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //获取参数
        map.put("serialId", serialId);
        map.put("seleceTime", time);

        //自增加日期
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(eDte);
        calendar.add(calendar.DATE, 1);
        sDte = calendar.getTime();
        //结束日期，用于查询数据时用
        map.put("endTime", format.format(sDte));

        //获取当天模式
        SysParamaterSetEntity sysParamaterSetEntity = sysParamaterSetService.getParamaterBySerial(map);
        if (sysParamaterSetEntity == null) {
            return R.error("该日期没有查询到数据");
        }
        //传递模式
        r.put("mode", sysParamaterSetEntity.getMode());
        // 统计数据
        List<SysCureDataEntity> list = sysCureDataService.findMapWithSerial(map);
        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start, end);
        logger.info("查询序列号为"+serialId+"曲线图耗时：" + duration.getSeconds() + "s");

        return r.put("map", list);
    }


    @SysLog("新增患者")
    @RequestMapping("/save")
    public R save(@RequestBody SysPatientEntity pat) {
        return sysPatientService.saveUser(pat);
    }


    /**
     * 根据患者ID查询详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/info")
    public R getRoleById(@RequestBody Long id) {
        return sysPatientService.getPatientById(id);
    }


    /**
     * 修改患者信息
     *
     * @param pat
     * @return
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    public R updateRole(@RequestBody SysPatientEntity pat) {
        return sysPatientService.updatePatient(pat);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @SysLog("删除用户")
    @RequestMapping("/remove")
    public R batchRemove(@RequestBody Long[] id) {
        return sysPatientService.batchRemove(id);
    }

    /**
     * 判断是否有实时数据传输
     */
    @RequestMapping(value = "/isRealData", method = RequestMethod.POST)
    public R isRealData(@RequestParam String serialId) {
        String tempData = redisTemplate.opsForList().range(serialId, 0, 0).toString();
        tempData  = tempData.substring(1, tempData.length() - 1);
        if (StringUtils.isBlank(tempData)) {
            return R.error(-1, "当前设备没有检测数据");
        } else {
            return R.ok("有实时数据");
        }
    }


    /**
     * 获取历史数据-设置信息
     *
     * @param params
     * @return
     */

    @RequestMapping(value = "/getHistorySetData", method = RequestMethod.POST)
    public Page<SysParamaterSetEntity> getHistorySetData(@RequestBody Map<String, Object> params) {
        Page<SysParamaterSetEntity> setEntityPage = sysParamaterSetService.listForParamaterInfo(params);
        return setEntityPage;
    }


    /**
     * 获取历史数据-统计信息
     *
     * @param serialId
     * @param startDate
     * @param endDate
     * @return
     */

    @RequestMapping(value = "/getHistoryStatInfo", method = RequestMethod.POST)
    public R getHistoryStatInfo(@RequestParam("serialId") String serialId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        R r = new R();
        Map<String, Object> map = new HashMap<>();
        List<String> colData = new ArrayList<>();
        //获取前台传递的参数
        map.put("serialId", serialId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);


        //查询当前日期所有的数据List
        int size = sysCureDataService.getStatCount(map);

        if (size <= 0) {
            return R.error("当前时间段没有监测到治疗数据!");
        }

        //查询最大值最小值
        r.put("maxAvg", sysCureDataService.getStatDataMaxAndAvg(map)); //参数:最大值最小值的


        int useDay = sysCureDataService.getUseDayCount(map);

        //单位转换为小时
        double useHours = new BigDecimal((float) size / 3600).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        //使用信息
        r.put("dayCount", size); //使用天数
        r.put("useDay", useDay); ////获取所选时间内的 使用天数
        r.put("useTime", useHours + " h"); //使用时间  单位小时
        r.put("avgUseTime", useHours / useDay + " h"); //平均使用时间


        //计算中位值 奇数 N+1/2   偶数 （长度/2的值 + 长度/2+1位)/2
        int median = 0;
        if (size % 2 == 0) {
            median = size / 2;
        } else {
            median = (size / 2) + 1;
        }

        long startTime = System.currentTimeMillis();    //获取开始时间


        List<SysCureDataEntity> colsData = new ArrayList<>();
        map.put("colName", "inhale_stress,cure_stress,exhale_stress,tidal_volume,minu_throughput,respiratory_rate");
        colsData = sysCureDataService.listForColData2(map);


        r.put("cureStress", colsData.get(median).getCureStress());
        r.put("inhaleStress", colsData.get(median).getInhaleStress());
        r.put("exhaleStress", colsData.get(median).getExhaleStress());
        r.put("tidalVolume", colsData.get(median).getTidalVolume());
        r.put("minuThroughput", colsData.get(median).getMinusTroughput());
        r.put("respiratoryRate", colsData.get(median).getRespiratoryRate());


        //按字段排序查询[统计信息]数据
        /*
        map.put("colName","cure_stress");
        colData=sysCureDataService.listForColData(map);  //压力的中位值
        r.put("cureStress",Double.parseDouble(colData.get(median)));
        System.out.println(colsData.get(median).getCureStress1());

        map.put("colName","inhale_stress");
        colData=sysCureDataService.listForColData(map);  //吸气压力中位值
        r.put("inhaleStress",Double.parseDouble(colData.get(median)));

        map.put("colName","exhale_stress");
        colData=sysCureDataService.listForColData(map);  //呼气压力中位值
        r.put("exhaleStress",Double.parseDouble(colData.get(median)));

        map.put("colName","tidal_volume");
        colData=sysCureDataService.listForColData(map);  //潮气量中位值
        r.put("tidalVolume",Double.parseDouble(colData.get(median)));

        map.put("colName","minu_throughput");
        colData=sysCureDataService.listForColData(map);  //分钟通气量中位值
        r.put("minuThroughput",Double.parseDouble(colData.get(median)));

        map.put("colName","respiratory_rate");
        colData=sysCureDataService.listForColData(map);  //呼吸频率中位值
        r.put("respiratoryRate",Double.parseDouble(colData.get(median)));
        */


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sDte = null; //开始日期
        Date eDte = null; //结束日期


        List<String> sData = new ArrayList<String>();

        //存放单个95值
        Double sigleNice1 = 0.00;
        Double sigleNice2 = 0.00;
        Double sigleNice3 = 0.00;
        Double sigleNice4 = 0.00;
        Double sigleNice5 = 0.00;
        Double sigleNice6 = 0.00;


        try {
            sDte = format.parse(startDate);  // 开始时间转换date类型
            eDte = format.parse(endDate); //结束时间转换date类型

            while (sDte.getTime() < eDte.getTime()) {
                map.put("niceStart", sDte);
                map.put("niceEnd", addDays(sDte, 1));


                //获取压力平均95值
                map.put("niceCol", "cure_stress");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size() != 0) {
                    Double test = Math.ceil(sData.size() * (0.95)) - 1;
                    sigleNice1 += Double.parseDouble(sData.get((int) Math.ceil(sData.size() * (0.95)) - 1));
                }

                //获取吸气压力平均95值
                map.put("niceCol", "inhale_stress");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size() != 0) {
                    sigleNice2 += Double.parseDouble(sData.get((int) Math.ceil(sData.size() * (0.95)) - 1));
                }

                //获取呼气压力平均95值
                map.put("niceCol", "exhale_stress");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size() != 0) {
                    sigleNice3 += Double.parseDouble(sData.get((int) Math.ceil(sData.size() * (0.95)) - 1));
                }

                //获取潮气量平均95值
                map.put("niceCol", "tidal_volume");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size() != 0) {
                    sigleNice4 += Double.parseDouble(sData.get((int) Math.ceil(sData.size() * (0.95)) - 1));
                }

                //获取分钟通气量平均95值
                map.put("niceCol", "minu_throughput");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size() != 0) {
                    sigleNice5 += Double.parseDouble(sData.get((int) Math.ceil(sData.size() * (0.95)) - 1));
                }

                //获取呼吸频率平均95值
                map.put("niceCol", "respiratory_rate");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size() != 0) {
                    sigleNice6 += Double.parseDouble(sData.get((int) Math.ceil(sData.size() * (0.95)) - 1));
                }

                sDte = addDays(sDte, 1);//时间再加一天

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间


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


    /**
     * 患者实时数据信息
     *
     * @param serialId
     * @return
     */

    @RequestMapping(value = "/getRealData", method = RequestMethod.POST)
    public R getRealData(@RequestParam String serialId) {
        List<String> test = new ArrayList<>();
        R r = new R();

        //获取Redis数据部分
        String dataMsg = redisTemplate.opsForList().range(serialId, 0, 0).toString();
        dataMsg = dataMsg.substring(1, dataMsg.length() - 1);

        //获取Redis设置信息部分
        String paraMsg = (String) redisTemplate.opsForValue().get("P" + serialId);
        //  paraMsg = paraMsg.substring(1,paraMsg.length()-1);

        //判断数据信息是否为空
        if (!StringUtils.isBlank(dataMsg)) {
            r.put("dataMsg", dataMsg);
        }

        //判断设置信息是否为空
        if (!StringUtils.isBlank(paraMsg)) {
            r.put("paraMsg", paraMsg);
        }

        return r;
    }


    /**
     * 在当前时间上加Day天
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }


    /**
     * 定时任务-删除大于7天的历史数据
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void delTimeOutHistory() {
        logger.info("删除过期的历史数据");
        sysCureDataService.delectData();
    }


    /**
     * 定时任务-每十秒钟执行一次 推送数据到京东方
     */
    @Scheduled(fixedRate = 10000)
    public void sendDataToBoe() {

        // TODO: 2019/10/18  京东方对接部分

        Map<String, Object> map = new HashMap<>();

        //查询当前在线的设备数据表，根据在线的设备轮流查询实时数据，推送
        List<SysDeviceStatusEntity> sysDeviceStatusEntities = sysDeviceStatusService.listForOnlineDevice();


        //判断设备状态列表是否为空
        if(CollectionUtils.isEmpty(sysDeviceStatusEntities)) {
            //logger.info("当前没有在线设备");
            return ;
        }

        for (SysDeviceStatusEntity sysDeviceStatusEntity : sysDeviceStatusEntities) {

            map.put("serialId",sysDeviceStatusEntity.getSerialId());
            map.put("starTime",sysDeviceStatusEntity.getStartTime());
            //根据序列号查询当前序列号标志位为0 未上传的数据
            List<SysCureDataBoeEntity> postData = sysCureDataService.listForBoeDataFlag(map);

            //判断未上传数据是否为0
            if (postData !=null && postData.size() >0){

                JSONObject boeCpod = new JSONObject();
                //测量数据JSON数组
                JSONObject measureJson = new JSONObject();

                JSONArray detailArr = new JSONArray();
                List<Integer> idList = new ArrayList<Integer>();

                //序列号
                boeCpod.put("deviceSN", sysDeviceStatusEntity.getSerialId());
                //MeasureArr数组内容
                measureJson.put("startTime", "");
                measureJson.put("endTime", "");
                measureJson.put("pint", "");
                measureJson.put("pset", "");
                //设置信息 - 最大压力和最小压力
                measureJson.put("pmax", "");
                measureJson.put("pmin", "");
                //没有该字段 赋值为0z
                measureJson.put("heat", "");
                measureJson.put("erp", "");
                //设置AI、HI、AHI平均值
                measureJson.put("aiAvg", "");
                measureJson.put("hiAvg", "");
                measureJson.put("ahiAvg", "");
                //压力平均值
                measureJson.put("pAvg", "");
                //漏气量平均值
                measureJson.put("leakAvg", "");

                for (SysCureDataBoeEntity sys : postData) {
                    ////////////////////////////////////////////
                    //实时数据详细信息
                    JSONObject detailJson = new JSONObject();
                    idList.add(sys.getId());
                    detailJson.put("startTime", StringUtils.substringBefore(sys.getCureTime(),"."));
                    detailJson.put("pressure", sys.getCureStress());
                    detailJson.put("flow", "");
                    detailJson.put("ai", sys.getAiCount());
                    detailJson.put("hi", sys.getHiCount());
                    detailJson.put("fi", "");
                    detailJson.put("leak", sys.getLeakage());
                    detailArr.add(detailJson);
                    ////////////////////////////////////////////
                }

                measureJson.put("detail", detailArr);
                boeCpod.put("measureData", measureJson);
                boeCpod.put("spo2Statistic", "");
                boeCpod.put("type", "realtime");
                boeCpod.put("createAt", Calendar.getInstance().getTimeInMillis());

                String result = HttpClientUtils.httpPost("http://copdtest.appsbu.com:8000/api/device/daya-resperitor-receiver", boeCpod.toString());
                //logger.info("[POST内容为:"+boeCpod.toString()+"]");
                JSONObject json_test = JSONObject.parseObject(result);
                String resultPost = json_test.getString("status");
                System.out.println("[POST-序列号为："+sysDeviceStatusEntity.getSerialId()+"的状态:"+json_test+"]");
                logger.info("[POST-序列号为："+sysDeviceStatusEntity.getSerialId()+"的状态:"+json_test+"]");
                if("ok".equals(resultPost)) {
                    logger.info("[UPDATA-序列号为："+sysDeviceStatusEntity.getSerialId()+"内容为:" + idList + "]");
                    if (idList.size() > 0) {
                        sysCureDataService.updateForFlag(idList);
                    }
                }
            }
        }
    }
}