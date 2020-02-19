package com.yamind.cloud.modules.app.api;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.entity.SleepDataEntity;
import com.yamind.cloud.modules.app.entity.UserEntity;
import com.yamind.cloud.modules.app.service.UserManageService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import jdk.nashorn.internal.codegen.CompileUnit;
import org.springframework.beans.factory.annotation.Autowired;
import com.yamind.cloud.common.entity.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/app/user")
public class UserCenterController extends AbstractController {


    @Autowired
    UserManageService userManageService;


    @Value("${upload-path}")
    private String uploadPath;


    /**
     * 添加或者更新用户信息
     *
     * @param userEntity
     * @return
     */
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public R addAndUpdateUserInfo(UserEntity userEntity, @RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request) throws IOException {

        R result = new R();

        //获取请求地址
        String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "//";
        File targetFile = null;

        if (null != userEntity.getUserId()) {

            if (null != file) {
                String fileName = file.getOriginalFilename();//获取文件名加后缀
                System.out.println("文件名为:" + fileName);

                if (fileName != null && fileName != "") {

                    String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
                    fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileType;//新的文件名

                    //获取文件夹路径
                    File file1 = new File(uploadPath + File.separator + "user");
                    //如果文件夹不存在则创建
                    if (!file1.exists() && !file1.isDirectory()) {
                        file1.mkdir();
                    }
                    //将图片存入文件夹
                    targetFile = new File(file1, fileName);
                    try {
                        //将上传的文件写到服务器上指定的文件。
                        file.transferTo(targetFile);
                        userEntity.setPhoto(returnUrl + "appResource/img" + "/" + "user" + "/" + fileName);
                        logger.info(userEntity.getPhoto());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        int count = userManageService.updateUserInfo(userEntity);
        if (count > 0) {
            result.put("code", 200);
            result.put("data", userEntity);
            result.put("msg", "update success!");
            return result;
        }
        return R.error("update faild!");

    }


    /**
     * 后台展示- 获取APP列表
     *
     * @return
     */
    @RequestMapping("/list")
    public Page userList(@RequestBody Map<String, Object> params) {
        Page<UserEntity> list = userManageService.listForUserInfo(params);
        return list;
    }
}
