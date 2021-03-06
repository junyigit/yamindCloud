package com.yamind.cloud.modules.sys.manager;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;


import java.util.List;

public interface SysDeviceManager {


    List<SysParamaterSetEntity> listForParaSet(Page<SysParamaterSetEntity> page, Query search);


    int saveDevice(SysParamaterSetEntity dev);
}
