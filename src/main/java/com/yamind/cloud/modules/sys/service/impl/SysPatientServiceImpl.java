package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;

import com.yamind.cloud.modules.sys.dao.SysPadientMapper;
import com.yamind.cloud.modules.sys.entity.SysOrgEntity;
import com.yamind.cloud.modules.sys.entity.SysPatientEntity;
import com.yamind.cloud.modules.sys.manager.SysPatientManager;
import com.yamind.cloud.modules.sys.service.SysPatientService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysPadientService")
public class SysPatientServiceImpl implements SysPatientService {



    @Autowired
    private SysPatientManager sysPatientManager;

    @Autowired
    private SysPadientMapper sysPadientMapper;


    @Override
    public Page<SysPatientEntity> listForPatient(Map<String, Object> params){
        Query form = new Query(params);
        Page<SysPatientEntity> page = new Page<>(form);
        sysPatientManager.listForPadient(page, form);
        return page;
    }


    @Override
    public R saveUser(SysPatientEntity pat){
        int count = sysPatientManager.savePatient(pat);
        return CommonUtils.msg(count);
    }



    @Override
    public R getPatientById(Long id){
        SysPatientEntity pat= sysPatientManager.getPatientById(id);
        return CommonUtils.msg(pat);
    }


    @Override
    public R updatePatient(SysPatientEntity pat){
        int count = sysPatientManager.updatePatient(pat);
        return CommonUtils.msg(count);
    }
}
