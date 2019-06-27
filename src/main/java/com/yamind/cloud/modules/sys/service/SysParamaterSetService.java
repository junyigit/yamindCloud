package com.yamind.cloud.modules.sys.service;

import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.dao.SysParamaterSetMapper;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.yamind.cloud.common.entity.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysParamaterSetService {



    //保存治疗数据
    R savePara(SysParamaterSetEntity para);


    //查询设备每天设置信息
    Page<SysParamaterSetEntity> listForParamaterInfo(Map<String, Object> params);

    //查询设置信息 --序列号
    SysParamaterSetEntity getParamaterBySerial(Map<String, Object> params);
}
