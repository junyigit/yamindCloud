package com.yamind.cloud.modules.sys.manager;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.entity.SysDeviceEntity;


import java.util.List;

public interface SysDeviceManager {


    List<SysDeviceEntity> listForDevice(Page<SysDeviceEntity> page, Query search);
}
