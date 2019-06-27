package com.yamind.cloud.modules.sys.manager.impl;
import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.dao.SysDeviceMapper;
import com.yamind.cloud.modules.sys.dao.SysPadientMapper;
import com.yamind.cloud.modules.sys.entity.SysPatientEntity;
import com.yamind.cloud.modules.sys.manager.SysPatientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;


@Component("sysPadientManager")
public class SysPatientManagerImpl implements SysPatientManager {

    @Autowired
    private SysPadientMapper sysPadientMapper;
    @Autowired
    private SysDeviceMapper sysDeviceMapper;


    @Override
    public List<SysPatientEntity> listForPadient(Page<SysPatientEntity> page, Query search){
        return sysPadientMapper.listForPage(page, search);
    }

    @Override
    public int savePatient(SysPatientEntity pat) {
       int count = sysPadientMapper.save(pat);;
        return count;
    }

    @Override
    public int updatePatient(SysPatientEntity pat){
        return sysPadientMapper.update(pat);
    }


    @Override
    public SysPatientEntity getPatientById(Long id){
        SysPatientEntity pat = sysPadientMapper.getObjectById(id);

        return pat;
    }

    @Override
    public int batchRemove(Long[] id){
        int count = sysPadientMapper.batchRemove(id);
        return count;
    }
}
