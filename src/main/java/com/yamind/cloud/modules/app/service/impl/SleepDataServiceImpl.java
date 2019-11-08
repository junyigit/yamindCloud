package com.yamind.cloud.modules.app.service.impl;

import com.yamind.cloud.modules.app.dao.SleepDataMapper;
import com.yamind.cloud.modules.app.entity.SleepDataEntity;
import com.yamind.cloud.modules.app.service.SleepDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SleepDataServiceImpl implements SleepDataService {

    @Autowired
    SleepDataMapper sleepDataMapper;


    /**
     * 保存用户睡眠数据
     * @param sleepDataEntity
     * @return
     */
    public int saveUserSleepData(SleepDataEntity sleepDataEntity){
        return sleepDataMapper.save(sleepDataEntity);
    }


    /**
     * 获取用户所有睡眠数据
     * @param userId
     * @param serial
     * @return
     */
    public List<SleepDataEntity> listForUserSleepData(String userId,String serial){
        return sleepDataMapper.listForUserSleepData(userId,serial);
    }
}
