package com.yamind.cloud.modules.sys.manager.impl;


import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.dao.SysDeviceMapper;
import com.yamind.cloud.modules.sys.entity.SysDeviceEntity;
import com.yamind.cloud.modules.sys.manager.SysDeviceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component("sysDeviceManager")
public class SysDeviceManagerImpl implements SysDeviceManager {


    @Autowired
    private SysDeviceMapper sysDeviceMapper;



    @Override
    public List<SysDeviceEntity> listForDevice(Page<SysDeviceEntity> page, Query search){
        return sysDeviceMapper.listForPage(page, search);
    }

    @Override
    public int saveDevice(SysDeviceEntity dev){
        return sysDeviceMapper.save(dev);
    }
}
