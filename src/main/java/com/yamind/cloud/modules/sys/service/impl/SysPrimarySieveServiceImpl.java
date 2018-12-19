package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.dao.SysPrimaryDetailTimeMapper;
import com.yamind.cloud.modules.sys.dao.SysPrimarySieveMapper;
import com.yamind.cloud.modules.sys.entity.SysPrimaryDetailTimeEntity;
import com.yamind.cloud.modules.sys.entity.SysPrimarySieveEntity;
import com.yamind.cloud.modules.sys.service.SysPrimarySieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysPrimarySieveService")
public class SysPrimarySieveServiceImpl implements SysPrimarySieveService {


    @Autowired
    private SysPrimarySieveMapper sysPrimarySieveMapper;
    @Autowired
    private SysPrimaryDetailTimeMapper sysPrimaryDetailTimeMapper;

    public Page<SysPrimarySieveEntity> listForPrimary(Map<String, Object> params){
        Query form = new Query(params);
        Page<SysPrimarySieveEntity> page = new Page<>(form);
        sysPrimarySieveMapper.listForPage(page,form);
        return page;
    }


    public Page<SysPrimaryDetailTimeEntity> listForDetailTime(Map<String, Object> params){
        Query form = new Query(params);
        Page<SysPrimaryDetailTimeEntity> page = new Page<>(form);
        sysPrimaryDetailTimeMapper.listForPage(page,form);
        return page;
    }
}
