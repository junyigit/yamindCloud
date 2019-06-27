package com.yamind.cloud.modules.sys.manager.impl;


import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.dao.SysDeviceMapper;
import com.yamind.cloud.modules.sys.dao.SysParamaterSetMapper;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;
import com.yamind.cloud.modules.sys.manager.SysDeviceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component("sysDeviceManager")
public class SysDeviceManagerImpl implements SysDeviceManager {


    @Autowired
    private SysDeviceMapper sysDeviceMapper;

    @Autowired
    private SysParamaterSetMapper sysParamaterSetMapper;



    @Override
    public List<SysParamaterSetEntity> listForParaSet(Page<SysParamaterSetEntity> page, Query search){
        return sysParamaterSetMapper.listForPage(page, search);
    }

    @Override
    public int saveDevice(SysParamaterSetEntity dev){
        return sysParamaterSetMapper.save(dev);
    }
}
