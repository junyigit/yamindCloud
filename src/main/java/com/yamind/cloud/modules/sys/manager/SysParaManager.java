package com.yamind.cloud.modules.sys.manager;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;


import java.util.List;

public interface SysParaManager {


    List<SysParamaterSetEntity> listUserSetInfo(Page<SysParamaterSetEntity> page, Query search);
}
