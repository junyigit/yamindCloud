package com.yamind.cloud.modules;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class TestApplication {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    public static void main(String[] args) {

        //测试程序运行时间  测试程序在start和end中间
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("耗时：" + duration.getSeconds() + "s");

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalTime localTime = LocalTime.now();

        localTime.getSecond();
        localTime.getNano();
        System.out.println(localTime);

    }
}

class DeviceData {

    private String deviceNo; // 设备号
    private String data; //  数据

    public DeviceData() {
    }

    public DeviceData(String deviceNo, String data) {
        this.deviceNo = deviceNo;
        this.data = data;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "deviceNo='" + deviceNo + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
