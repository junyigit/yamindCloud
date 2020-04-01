package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.sys.dao.SysCureDataMapper;
import com.yamind.cloud.modules.sys.entity.SysCureDataBoeEntity;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;
import com.yamind.cloud.modules.sys.manager.SysCureDataManager;
import com.yamind.cloud.modules.sys.service.SysCureDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysCureDataService")
public class SysCureDataServiceImpl implements SysCureDataService {





    @Autowired
    SysCureDataManager sysCureDataManager;
    @Autowired
    SysCureDataMapper sysCureDataMapper;


    public List<SysCureDataEntity> findMapWithSerial(Map<String, Object> params){
        return sysCureDataMapper.findMapWithSerial(params);
    }

    public List<SysCureDataEntity> getGroupCureData(Map<String, Object> params){
        return sysCureDataMapper.getGroupCureData(params);
    }



    public List<SysCureDataEntity> listForHistoryStatData(Map<String, Object> params){
        return sysCureDataMapper.listForHistoryStatData(params);
    }

    public int getStatCount(Map<String, Object> params){
        return sysCureDataMapper.getStatCount(params);
    }

    public Map<String,String> getStatDataMaxAndAvg(Map<String, Object> params){
        return sysCureDataMapper.getStatDataMaxAndAvg(params);
    }

    public int getUseDayCount(Map<String, Object> params){
        return sysCureDataMapper.getUseDayCount(params);
    }


    public List<String> listForColData(Map<String,Object> param){
        return sysCureDataMapper.listForColData(param);
    }


    public List<SysCureDataEntity> listForColData2(Map<String,Object> param){
        return sysCureDataMapper.listForColData2(param);
    }


    public List<String> listForDateStatInfo(Map<String,Object> param){
        return sysCureDataMapper.listForDateStatInfo(param);
    }

    public R saveData(SysCureDataEntity user){
        int count = sysCureDataManager.save(user);
        return CommonUtils.msg(count);
    }

    //同步数据到京东方
    public List<SysCureDataBoeEntity> listForBoeDataFlag(Map<String,Object> map){
        List<SysCureDataBoeEntity> boeList= sysCureDataMapper.listForBoeDataFlag(map);
        return boeList;
    }



    //删除过期用户治疗数据
    public int delectData(){
        int count =sysCureDataMapper.delectOldTimeData();
        return count;
    }


    public R updateForFlag(List<Integer> idList){
        int count = sysCureDataMapper.updateForFlag(idList);;
        return CommonUtils.msg(count);
    }

}
