package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.sys.dao.SysDeviceStatusMapper;
import com.yamind.cloud.modules.sys.entity.SysDeviceStatusEntity;
import com.yamind.cloud.modules.sys.service.SysDeviceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("sysDeviceStatusService")
public class SysDeviceStatusServiceImpl implements SysDeviceStatusService {


    @Autowired
    SysDeviceStatusMapper sysDeviceStatusMapper;

    public R saveData(SysDeviceStatusEntity des){
        int result = sysDeviceStatusMapper.save(des);
        return CommonUtils.msg(result);
    }

    //根据设备序列号查询当前设备在线状态
    public SysDeviceStatusEntity getDeviceStatusBySerialId(String serialId){
        return sysDeviceStatusMapper.getDeviceStatusBySerialId(serialId);
    }


    //获取所有设备当前状态
    public List<SysDeviceStatusEntity> listForOnlineDevice(){
        return sysDeviceStatusMapper.listForOnlineDevice();
    }
}
