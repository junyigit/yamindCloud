package com.yamind.cloud.modules.sys.service;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.modules.sys.entity.SysPrimaryDetailTimeEntity;
import com.yamind.cloud.modules.sys.entity.SysPrimarySieveEntity;

import java.util.List;
import java.util.Map;

public interface SysPrimarySieveService {

    Page<SysPrimarySieveEntity> listForPrimary(Map<String, Object> params);


    Page<SysPrimaryDetailTimeEntity> listForDetailTime(Map<String, Object> params);


}
