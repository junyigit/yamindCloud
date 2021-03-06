package com.yamind.cloud.modules.sys.manager.impl;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.dao.SysCureDataMapper;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;
import com.yamind.cloud.modules.sys.manager.SysCureDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("SysCureDataManager")
public class SysCureDataManagerImpl implements SysCureDataManager {

    @Autowired
    SysCureDataMapper sysCureDataMapper;


    @Override
    public int save(SysCureDataEntity pat){
        int count = sysCureDataMapper.save(pat);
        return count;
    }

}
