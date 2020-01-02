package com.yamind.cloud.modules.app.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yamind.cloud.common.annotation.SysLog;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.HttpClientUtils;
import com.yamind.cloud.modules.app.entity.UserEntity;
import com.yamind.cloud.modules.app.service.UserManageService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import com.yamind.cloud.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app/login")
public class LoginController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Value("${appkey}")
    String appkey;

    @Value("${appSecret}")
    String appSecret;

    @Value("${mobVerifyReqUrl}")
    String mobVerifyReqUrl;

    private final String zone = "86";


    @Autowired
    UserManageService userManageService;
    @Autowired
    SysUserService sysUserService;

    /**
     * 登陆获取短信验证码并存入数据库
     * @param phone
     * @param code
     * @return
     */
    @SysLog("获取手机短信验证码")
    @RequestMapping(value = "/verifySmsCode", method = RequestMethod.POST)
    public R getSmsVerify(@RequestParam String phone,@RequestParam String code )  {
        R r = new R();
        logger.info("[手机号为："+phone +"获取验证码: "+code +"]");
        JSONObject mobJson = newMobObject();
        mobJson.put("phone",phone);
        mobJson.put("code",code);

        String resultJson = HttpClientUtils.httpPost(mobVerifyReqUrl,mobJson);
        JSONObject verifyResult = JSONObject.parseObject(resultJson);
        // 临时测试屏蔽
       // JSONObject verifyResult = JSONObject.parseObject("{status:200}");
        if ("200".equals(verifyResult.getString("status"))){
            // 保存用户 和 Token 信息
            UserEntity userEntity = new UserEntity();
            userEntity.setMobile(phone);
            //保存用户信息至APP用户表
            int result = userManageService.saveUser(userEntity);
            if (result==2){
                userEntity = userManageService.getUserMobile(phone);
            }
            //判断是否存在Token，如果存在则返回，如果不存在则创建
            r = sysUserService.saveUserToken(userEntity.getMobile());
            //判断用户信息是否保存成功
            if (result>0 && StringUtils.isNotEmpty(r.get("token").toString())){
                //返回创建成功的token信息
                r.put("code",200);
                r.put("userInfo",userEntity);
            }
        } else {
            return R.error("验证码错误");
        }

        return r;
    }

    /**
     * 初始化常量值
     * @return
     */
    public JSONObject newMobObject() {
        JSONObject mobJson = new JSONObject();
        mobJson.put("appkey",appkey);
        mobJson.put("zone",zone);
        return mobJson;
    }


}
