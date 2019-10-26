package com.yamind.cloud.modules.sys.dao;


import com.yamind.cloud.modules.sys.entity.SysCureDataBoeEntity;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCureDataMapper extends BaseMapper<SysCureDataEntity>{


    List<SysCureDataEntity> listForHistoryStatData(Map<String,Object> param);


    //获取当前设备有几天的数据
    List<SysCureDataEntity> getGroupCureData(Map<String,Object> param);

    //根据列计算中位值
    List<String> listForColData(Map<String,Object> param);

    //根据列计算中位值
    List<SysCureDataEntity> listForColData2(Map<String,Object> param);

    // 计算95值 获取方法
    int getStatCount(Map<String,Object> param);

    // 计算95值 获取方法
    List<String> listForDateStatInfo(Map<String,Object> param);


    List<SysCureDataEntity> findMapWithSerial(Map<String,Object> param);


    int getUseDayCount(Map<String,Object> param);

    Map<String,String> getStatDataMaxAndAvg(Map<String,Object> param);


    // 根据时间获取 曲线图里面的数据
    List<SysCureDataEntity> findMapListWithDate(Map<String,Object> param);

    //同步数据到京东方服务器
    List<SysCureDataBoeEntity> listForBoeDataFlag(String serialId);

    int updateForFlag(List<Integer> idList);

    void delectOldTimeData();
}
