package com.yamind.cloud.modules.sys.manager.impl;

import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.dao.SysParamaterSetMapper;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;
import com.yamind.cloud.modules.sys.manager.SysParaManager;
import org.springframework.beans.factory.annotation.Autowired;
import com.yamind.cloud.common.entity.Page;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("sysParaManager")
public class SysParaManagerImpl implements SysParaManager {

    @Autowired
    SysParamaterSetMapper mapper;


    public List<SysParamaterSetEntity> listUserSetInfo(Page<SysParamaterSetEntity> page, Query search){
        return mapper.listForPage(page,search);
    }
}
