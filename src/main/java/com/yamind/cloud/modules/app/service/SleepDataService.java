package com.yamind.cloud.modules.app.service;

import com.yamind.cloud.modules.app.entity.SleepDataEntity;

import java.util.List;

public interface SleepDataService {

    //保存用户睡眠数据
    int saveUserSleepData(SleepDataEntity sleepDataEntity);

    //获取用户睡眠数据
    List<SleepDataEntity> listForUserSleepData(String userId,String serial);
}
