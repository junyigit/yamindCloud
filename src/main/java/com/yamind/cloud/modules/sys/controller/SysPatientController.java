package com.yamind.cloud.modules.sys.controller;


import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yamind.cloud.common.annotation.SysLog;
import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;
import com.yamind.cloud.modules.sys.entity.SysPatientEntity;
import com.yamind.cloud.modules.sys.service.SysCureDataService;
import com.yamind.cloud.modules.sys.service.SysPatientService;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;


import javax.xml.crypto.Data;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/sys/patient")
public class SysPatientController extends AbstractController{


    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SysPatientService sysPatientService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysCureDataService sysCureDataService;


    /**
     * 用户列表
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
     * @param serialId
     * @param time
     * @return
     */

    @RequestMapping(value = "/findMapWithSerial",method = RequestMethod.POST)
    public R findMapWithSerial(@RequestParam("serialId") String serialId, @RequestParam("time") String time) {

        R r= new R();
        Map<String,Object> map = new HashMap<>();
        //日期格式化
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date eDte = null;
        Date sDte = null;
        //模式
        String mode ="";
        try {
            eDte = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //获取参数
        map.put("serialId",serialId);
        map.put("startTime",time);

        Calendar   calendar   =   new   GregorianCalendar();
        calendar.setTime(eDte);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        sDte=calendar.getTime();   //这个时间就是日期往后推一天的结果

        map.put("endTime",sDte);

        //根据序列号查询当前有几天的数据
        List<SysCureDataEntity> listForDay = sysCureDataService.getGroupCureData(map);

            for (SysCureDataEntity sysCureDataEntity :listForDay){
                    if (sysCureDataEntity.getCureTime().equals(time)){
                        mode = sysCureDataEntity.getMode();
                    }
            }

        //根据序列号获取当天的数据
        //List <SysCureDataEntity> list = sysCureDataService.findMapWithSerial(map);
        //传递模式
        r.put("mode",mode);
            List<SysCureDataEntity> list =sysCureDataService.findMapWithSerial(map);
        return r.put("map",list);
    }



    @SysLog("新增患者")
    @RequestMapping("/save")
    public R save(@RequestBody SysPatientEntity pat) {
        return sysPatientService.saveUser(pat);
    }


    /**
     * 根据患者ID查询详情
     * @param id
     * @return
     */
    @RequestMapping("/info")
    public R getRoleById(@RequestBody Long id) {
        return sysPatientService.getPatientById(id);
    }


    /**
     * 修改患者信息
     * @param pat
     * @return
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    public R updateRole(@RequestBody SysPatientEntity pat) {
        return sysPatientService.updatePatient(pat);
    }



    /**
     * 判断是否有实时数据传输
     */
    @RequestMapping(value = "/isRealData",method = RequestMethod.POST)
    public R isRealData(@RequestParam String serialId){
        String tempData=redisTemplate.opsForList().range(serialId,0,0).toString();
        tempData=tempData = tempData.substring(1,tempData.length()-1);
        if (StringUtils.isBlank(tempData)){
            return R.error(-1,"当前设备没有检测数据");
        }else {
            return R.ok("有实时数据");
        }

    }




    /**
     * 获取历史数据-设置信息
     * @param params
     * @return
     */

    @RequestMapping(value = "/getHistorySetData",method = RequestMethod.POST)
    public Page<SysCureDataEntity> getHistorySetData(@RequestBody Map<String, Object> params) {
        Page<SysCureDataEntity> list = sysCureDataService.listForCureSetData(params);
        return list;
    }



    /**
     * 获取历史数据-统计信息
     * @param serialId
     * @param startDate
     * @param endDate
     * @return
     */

    @RequestMapping(value = "/getHistoryStatInfo",method = RequestMethod.POST)
    public R getHistoryStatInfo(@RequestParam("serialId") String serialId,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
        R r = new R();
        Map<String,Object> map = new HashMap<>();
        List<String> colData = new ArrayList<>();
        //获取参数
        map.put("serialId",serialId);
        map.put("startDate",startDate);
        map.put("endDate",endDate);



        long a=System.currentTimeMillis();
        //查询当前日期所有的数据List
        System.out.println("执行耗时 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
        int size = sysCureDataService.getStatCount(map);
        System.out.println("执行耗时 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");






        //查询最大值最小值
         r.put("maxAvg",sysCureDataService.getStatDataMaxAndAvg(map)); //参数:最大值最小值的





        int useDay =sysCureDataService.getUseDayCount(map);
      //  long diff = listForHistoryData.size();
        long diff = size;
        long days = diff / ( 60 * 60 * 24);
        long hours = (diff - days * ( 60 * 60 * 24)) / (60 * 60);

        //使用信息

        r.put("dayCount",size); //使用天数
        r.put("useDay",useDay); ////获取所选时间内的 使用天数
        r.put("useTime",hours); //使用时间  单位小时
//        r.put("avgUseTime",hours/useDay); //平均使用时间


        //计算中位值 奇数 N+1/2   偶数 （长度/2的值 + 长度/2+1位)/2
        int median=0;
        if (size%2 ==0){
            median =size/2;
        }else{
            median =(size/2)+1;
        }

        String sql;
        //按字段排序查询[统计信息]数据
        map.put("colName","cure_stress");

       sql= "SELECT "+map.get("colName")+" FROM sys_curedata WHERE cure_time BETWEEN '"+ map.get("startDate")+"' AND '"
                + map.get("endDate") +"' AND bootSerial='"+map.get("serialId") +"' ORDER BY "+ map.get("colName")+" ASC";

        SqlRowSet rowSet=jdbcTemplate.queryForRowSet(sql);

        colData=sysCureDataService.listForColData(map);  //压力的中位值
        r.put("cureStress",Double.parseDouble(colData.get(median)));

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


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sDte = null; //开始日期
        Date eDte = null; //结束日期


        List<String> sData = new ArrayList<String>();

        //存放单个95值
        Double sigleNice1=0.00;
        Double sigleNice2=0.00;
        Double sigleNice3=0.00;
        Double sigleNice4=0.00;
        Double sigleNice5=0.00;
        Double sigleNice6=0.00;


        try {
            sDte = format.parse(startDate);  // 开始时间转换date类型
            eDte = format.parse(endDate); //结束时间转换date类型

            while (sDte.getTime() <eDte.getTime()){
                map.put("niceStart",sDte);
                map.put("niceEnd",addDays(sDte,1));

                //获取压力平均95值
                map.put("niceCol","cure_stress");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size()!=0){
                    Double test = Math.ceil(sData.size() * (0.95))-1;
                    sigleNice1+= Double.parseDouble(sData.get((int)Math.ceil(sData.size() * (0.95))-1));
                }

                //获取吸气压力平均95值
                map.put("niceCol","inhale_stress");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size()!=0) {
                    sigleNice2 += Double.parseDouble(sData.get((int)Math.ceil(sData.size() * (0.95)) - 1));
                }

                //获取呼气压力平均95值
                map.put("niceCol","exhale_stress");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size()!=0) {
                    sigleNice3 += Double.parseDouble(sData.get((int) Math.ceil(sData.size() * (0.95)) - 1));
                }

                //获取潮气量平均95值
                map.put("niceCol","tidal_volume");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size()!=0) {
                    sigleNice4 +=Double.parseDouble( sData.get((int) Math.ceil(sData.size() * (0.95)) - 1));
                }

                //获取分钟通气量平均95值
                map.put("niceCol","minu_throughput");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size()!=0) {
                    sigleNice5 += Double.parseDouble(sData.get((int) Math.ceil(sData.size() * (0.95)) - 1));
                }
                //获取呼吸频率平均95值
                map.put("niceCol","respiratory_rate");
                sData = sysCureDataService.listForDateStatInfo(map);
                if (sData.size()!=0) {
                    sigleNice6+= Double.parseDouble(sData.get((int)Math.ceil(sData.size() * (0.95))-1));
                }
                sDte=addDays(sDte,1);//时间再加一天


            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        //存储压力95的list cure_stress
        r.put("stressNice",sigleNice1/useDay);


        //存储吸气压力95的list inhale_stress
        r.put("inhaleStressNice",sigleNice2/useDay);

        //存储呼气压力95的list exhale_stress
        r.put("exhaleStressNice",sigleNice3/useDay);


        //存储潮气量95的list  tidal_volume
        r.put("tidalVolumeNice",sigleNice4/useDay);

        //存储分钟通气量95的list  minu_throughput
        r.put("minuThroughputNice",sigleNice5/useDay);

        //存储呼吸频率95的list  respiratory_rate
        r.put("respiratoryRateNice",sigleNice6/useDay);

        return r;
    }




    /**
     * 患者实时数据信息
     * @param serialId
     * @return
     */

    @RequestMapping(value = "/getRealData",method = RequestMethod.POST)
    public R getRealData(@RequestParam String serialId) {
        List<String> test = new ArrayList<>();
        R r= new R();


        String tempData=redisTemplate.opsForList().range(serialId,0,0).toString();
        tempData = tempData.substring(1,tempData.length()-1);


        if (!StringUtils.isBlank(tempData)){
            r.put("data",tempData);
        }else{
            return R.error(111,"当前设备没有检测到实时数据");
        }
        return r;
    }


    /**
     * 在当前时间上加Day天
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

}