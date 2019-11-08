package com.yamind.cloud.modules.app.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.SleepDataEntity;
import com.yamind.cloud.modules.app.service.SleepDataService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/app/sleep")
public class SleepDataController extends AbstractController {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SleepDataService sleepDataService;


    /**
     * 保存用户的睡眠数据
     * @param sleepDataEntity
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
