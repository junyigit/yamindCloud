package com.yamind.cloud.modules.sys.service.impl;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.sys.dao.SysParamaterSetMapper;
import com.yamind.cloud.modules.sys.entity.SysParamaterSetEntity;
import com.yamind.cloud.modules.sys.manager.SysParaManager;
import com.yamind.cloud.modules.sys.service.SysParamaterSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sysParamaterSetService")
public class SysParamaterSetSeviceImpl implements SysParamaterSetService {


    @Autowired
    SysParamaterSetMapper sysParamaterSetMapper;
    @Autowired
    SysParaManager sysParaManager;

    public Page<SysParamaterSetEntity> listForParamaterInfo(Map<String, Object> params)
    {
        Query query = new Query(params);
        Page<SysParamaterSetEntity> page = new Page<SysParamaterSetEntity>(query);
        sysParaManager.listUserSetInfo(page,query);
        return page;
    }


    //保存治疗数据
    public R savePara(SysParamaterSetEntity para){
        int count = sysParamaterSetMapper.save(para);
        return CommonUtils.msg(count);
    }

    //查询当前序列号的参数信息
    public SysParamaterSetEntity getParamaterBySerial(Map<String, Object> params){
        return sysParamaterSetMapper.getParamaterBySerial(params);
    }



    //删除大于7天过期的设置信息
    public int delOldDate(){
        int count =sysParamaterSetMapper.delOldDate();
        return count;
    }


}