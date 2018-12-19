package com.yamind.cloud.modules.sys.controller;

import com.yamind.cloud.common.annotation.SysLog;
import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.entity.SysPrimaryDetailTimeEntity;
import com.yamind.cloud.modules.sys.entity.SysPrimarySieveEntity;
import com.yamind.cloud.modules.sys.service.SysPrimarySieveService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("sys/primarySieve")
public class SysPrimarySieveController extends AbstractController{


    @Autowired
    SysPrimarySieveService sysPrimarySieveService;
    /**
     * 用户列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public Page<SysPrimarySieveEntity> list(@RequestBody Map<String, Object> params) {
        Page<SysPrimarySieveEntity> list = sysPrimarySieveService.listForPrimary(params);
        return list;
    }

    /**
     * 用户治疗时间列表
     * @param params
     * @return
     */
    @RequestMapping("/detailList")
    public Page<SysPrimaryDetailTimeEntity> detailList(@RequestBody Map<String, Object> params) {
       Page<SysPrimaryDetailTimeEntity> list = sysPrimarySieveService.listForDetailTime(params);
        return list;
    }
}
