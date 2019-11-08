package com.yamind.cloud.modules.app.api;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.AdvertEntity;
import com.yamind.cloud.modules.app.service.AdvertService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/app/advert")
public class AdvertController extends AbstractController {

    @Value("${advert-upload-path}")
    private String uploadPath;

    @Autowired
    AdvertService advertService;


    /**
     * 获取首页广告
     * @return
     */
    @RequestMapping("homeAdvert")
    @ResponseBody
    public R listForHomeAdvert(){
        List<AdvertEntity> list = advertService.listForAdvertList();
        if (list !=null && list.size()> 0){
            return R.customOk(list);
        }
        return R.error("没有广告");
    }
}
