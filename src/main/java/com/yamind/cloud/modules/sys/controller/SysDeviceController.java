package com.yamind.cloud.modules.sys.controller;


import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.netty.ChannelMap;
import com.yamind.cloud.modules.netty.NettyServer;
import com.yamind.cloud.modules.netty.SendMsgHandler;
import com.yamind.cloud.modules.sys.entity.SysDeviceEntity;
import com.yamind.cloud.modules.sys.entity.SysPatientEntity;
import com.yamind.cloud.modules.sys.service.SysDeviceService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/sys/device")
public class SysDeviceController {


    @Autowired
    SysDeviceService sysDeviceService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private MessageSource messageSource;
    /**
     * 参数设置-用户列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public Page<SysDeviceEntity> list(@RequestBody Map<String, Object> params) {

        Page<SysDeviceEntity> list = sysDeviceService.listForDevice(params);
        return list;
    }


    /**
     * 获取参数详情
     * @param params
     * @return
     */

    @RequestMapping(value ="/getParaDetails",method = RequestMethod.POST)
    public R getParaDetails(@RequestParam Map<String, String> params) {
        R r = new R();
        String tempData = redisTemplate.opsForList().range(params.get("serialId"), 0, 0).toString();
        tempData = tempData.substring(1, tempData.length() - 1);
        JSONObject jsonObject = JSONObject.fromObject(tempData);
        r.put("para",tempData);
        return r;
    }


    /**
     * 远程调参调用方法
     * @param ctx
     * @param params
     * @return
     */
    @RequestMapping(value ="/testNetty",method = RequestMethod.POST)
    public R testNetty(ChannelHandlerContext ctx,@RequestParam Map<String, String> params) {
        //获取IP地址用于指定IP地址的信息发送
        Channel channel=ChannelMap.getChannelByName(params.get("ipAddr"));

        JSONObject jsonObject = new JSONObject();
        JSONObject pataArr = new JSONObject();
        JSONArray paraSet = new JSONArray();
        switch (params.get("mode")){
            case "CPAP":
                jsonObject.put("serialId",params.get("serial"));
                jsonObject.put("mode",params.get("mode"));
                pataArr.put("ksyl",params.get("starStress"));
                pataArr.put("zlyl",params.get("cureStress"));
                pataArr.put("hqsf",params.get("breatheRel"));
                pataArr.put("ycsj",params.get("delayTime"));
                jsonObject.put("paraData",pataArr);
                break;
            case "APAP":
                jsonObject.put("serialId",params.get("serial"));
                jsonObject.put("mode",params.get("mode"));
                pataArr.put("ksyl",params.get("starStress"));
                pataArr.put("zdyl",params.get("maxStress"));
                pataArr.put("zxyl",params.get("minStress"));
                pataArr.put("hqsf",params.get("breatheRel"));
                pataArr.put("ycsj",params.get("delayTime"));
                jsonObject.put("paraData",pataArr);
                break;
        }
        //写入到缓冲通道
        channel.writeAndFlush(jsonObject.toString());
        return R.ok("ok");
    }


}