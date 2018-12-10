package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.sys.dao.SysCureDataMapper;
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

    public Page<SysCureDataEntity> listForCureSetData(Map<String, Object> params){
        Query form = new Query(params);
        Page<SysCureDataEntity> page = new Page<>(form);
        sysCureDataManager.listForCureSetData(page, form);
        return page;
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


    public List<String> listForDateStatInfo(Map<String,Object> param){
        return sysCureDataMapper.listForDateStatInfo(param);
    }

    public R sava(SysCureDataEntity data){
        int count = sysCureDataManager.sava(data);
        return CommonUtils.msg(count);
    }


    public R delectData(){
        sysCureDataMapper.delectOldTimeData();
        return R.ok("success");
    }
}
