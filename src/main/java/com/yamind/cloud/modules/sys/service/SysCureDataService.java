package com.yamind.cloud.modules.sys.service;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.entity.SysCureDataBoeEntity;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;

import java.util.List;
import java.util.Map;

public interface SysCureDataService {



    //获取历史数据 - 曲线图用
    List<SysCureDataEntity> findMapWithSerial(Map<String, Object> params);

    //获取历史数据-设置信息的天数 曲线图用
    List<SysCureDataEntity> getGroupCureData(Map<String, Object> params);

    //

    //获取历史数据 - 统计信息
    List<SysCureDataEntity> listForHistoryStatData(Map<String, Object> params);

    int getStatCount(Map<String, Object> params);

    //获取历史数据 统计信息 --最大值最小值
    Map<String,String> getStatDataMaxAndAvg(Map<String, Object> params);

    //统计所选日期使用天数
    int getUseDayCount(Map<String, Object> params);

    //按字段排序查询统计信息数据
    List<String> listForColData(Map<String,Object> param);

    //按字段排序查询统计信息数据2
    List<SysCureDataEntity> listForColData2(Map<String,Object> param);

    //根据日期获取95值
    List<String> listForDateStatInfo(Map<String,Object> param);

    //保存治疗数据
    R saveData(SysCureDataEntity user);

    //同步数据到京东方
    List<SysCureDataBoeEntity> listForBoeDataFlag(Map<String,Object> map);


    //删除过期的数据
    int delectData();


    //更新sys_device_status表中已同步的数据
    R updateForFlag(List<Integer> aiList);

}
