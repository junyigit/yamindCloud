package com.yamind.cloud.modules.sys.manager;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.entity.SysCureDataEntity;


import java.util.List;

public interface SysCureDataManager {




    List<SysCureDataEntity> listForCureSetData(Page<SysCureDataEntity> page, Query search);




    int save(SysCureDataEntity pat);
}
