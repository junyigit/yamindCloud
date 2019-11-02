package com.yamind.cloud.modules.app.api;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.AdvertEntity;
import com.yamind.cloud.modules.app.service.AdvertService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/app/advert")
public class AdvertController extends AbstractController {


    @Autowired
    AdvertService advertService;


    /**
     * 获取首页广告
     * @return
     */
    @RequestMapping("homeAdvert")
    @ResponseBody
    private R listForHomeAdvert(){
        R r = new R();
        List<AdvertEntity> list =advertService.listForAdvertList();
        if (list !=null && list.size()> 0){
            r.put("code",200);
            r.put("data",list);
        }else{
            r.put("code",500);
            r.put("msg","没有广告..");
        }
        return r;
    }
}
