package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.sys.dao.SysDeviceStatusMapper;
import com.yamind.cloud.modules.sys.entity.SysDeviceStatusEntity;
import com.yamind.cloud.modules.sys.service.SysDeviceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sysDeviceStatusService")
public class SysDeviceStatusServiceImpl implements SysDeviceStatusService {


    @Autowired
    SysDeviceStatusMapper sysDeviceStatusMapper;

    public R saveData(SysDeviceStatusEntity des){
        int count = sysDeviceStatusMapper.save(des);
        return CommonUtils.msg(count);
    }

    public SysDeviceStatusEntity getDeviceStatusBySerialId(String serialId){

        return sysDeviceStatusMapper.getDeviceStatusBySerialId(serialId);
    }
}
