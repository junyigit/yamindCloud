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


    @RequestMapping("add")
    @ResponseBody
    public R addAdvert(AdvertEntity advertEntity, @RequestParam(value = "uploadFile", required = false) MultipartFile[] fileList, HttpServletRequest request, HttpServletResponse response) throws IOException {

        R result = new R();
        File targetFile = null;

        //获取请求地址
        String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"//";

        for (MultipartFile file : fileList) {

            String fileName = file.getOriginalFilename();//获取文件名加后缀
            System.out.println("文件名为:"+fileName);

            if (fileName != null && fileName != "") {


                String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
                fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileType;//新的文件名


                //获取文件夹路径
                File file1 = new File(uploadPath);
                //如果文件夹不存在则创建
                if (!file1.exists() && !file1.isDirectory()) {
                    file1.mkdir();
                }
                //将图片存入文件夹
                targetFile = new File(file1, fileName);
                try {
                    //将上传的文件写到服务器上指定的文件。
                    file.transferTo(targetFile);
                    advertEntity.setAdvertImgPath(returnUrl+"appResource/imgs" + "/"+"advert" +"/"+ fileName);

                } catch (Exception e) {
                    e.printStackTrace();
                    result.put("code", 500);
                    result.put("msg", "系统异常，图片上传失败");
                }
            }


            int count  = advertService.addAdvert(advertEntity);
            if (count > 0) {
                result.put("code",200);
                result.put("msg","advert add success!");
            }else{
                result.put("code",500);
                result.put("msg","advert add faild!");
            }

        }
        return result;
    }




    /**
     * 获取首页广告
     * @return
     */
    @RequestMapping("homeAdvert")
    @ResponseBody
    public R listForHomeAdvert(){
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
