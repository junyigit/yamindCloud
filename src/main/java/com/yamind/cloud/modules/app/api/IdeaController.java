package com.yamind.cloud.modules.app.api;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.dao.IdeaManageMapper;
import com.yamind.cloud.modules.app.entity.IdeaDataEntity;
import com.yamind.cloud.modules.app.entity.IdeaImageEntity;
import com.yamind.cloud.modules.app.service.IdeaImageService;
import com.yamind.cloud.modules.app.service.IdeaManageService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/app/idea")
public class IdeaController extends AbstractController {


    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    IdeaManageService ideaManageService;
    @Autowired
    IdeaImageService ideaImageService;

    @Value("${idea-upload-path}")
    private String uploadPath;


    /**
     * 获取用户当前所有意见问题 列表
     * @param userId
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    public R ideaList(@RequestParam Long userId,HttpServletRequest request){
        R r = new R();
        JSONObject json = new JSONObject();
        JSONArray ideaArry = new JSONArray();

        String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        //获取当前用户所有提交的问题
        List<IdeaDataEntity> ideaList = ideaManageService.listFroUserIdea(userId);
        if(null == ideaList || ideaList.size() ==0 ){
            /*r.put("code",200);
            r.put("msg","当前用户没有提交过问题反馈");*/
            return R.customOk("当前用户没有提交过问题反馈");
        }
        //将每一个工单里面包含的图片获取
        for (IdeaDataEntity ideaDataEntity :ideaList){
            JSONObject ideaContent = new JSONObject();
            JSONArray imgArr = new JSONArray();

            ideaContent.put("ideaId",ideaDataEntity.getId());
            ideaContent.put("userId",ideaDataEntity.getUserId());
            ideaContent.put("userName",ideaDataEntity.getUserName());
            ideaContent.put("phone",ideaDataEntity.getPhone());
            ideaContent.put("type",ideaDataEntity.getType());
            ideaContent.put("time",ideaDataEntity.getCreateTime());
            ideaContent.put("content",ideaDataEntity.getContent());

            //获取当前工单的ID  ideaDataEntity.getId()
            List<IdeaImageEntity> imgList = ideaImageService.listFroIdeaImg(ideaDataEntity.getId());
            if (null != imgList || imgList.size() >0 ) {
                for (IdeaImageEntity ideaImageEntity : imgList) {
                    JSONObject img = new JSONObject();
                    img.put("imgId", ideaImageEntity.getId());
                    img.put("ideaId", ideaImageEntity.getQuestionId());
                    img.put("imgPath", returnUrl+"/appResource/imgs/idea"+"/"+ideaImageEntity.getImgPath() + "/" + ideaImageEntity.getImgName());
                    imgArr.add(img);
                }
                //将图片添加至JSON对象中
                ideaContent.put("img", imgArr);
            }
            ideaArry.add(ideaContent);
            r.put("code",200);
            r.put("data",ideaArry);
        }
        return r;
    }



    /**
     * 提交用户反馈信息接口
     * @param fileList
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("submitIdea")
    @ResponseBody
    public R uploadFile(IdeaDataEntity ideaDataEntity, @RequestParam(value = "uploadFile", required = false) MultipartFile[] fileList, HttpServletRequest request, HttpServletResponse response) throws IOException {

        R result = new R();
        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile = null;
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(ideaDataEntity !=null) {
            //转换时间
            String uploadTime = formatter.format(currentTime);
            ideaDataEntity.setCreateTime(uploadTime);
            result = ideaManageService.saveIdea(ideaDataEntity);
        }

        for (MultipartFile file : fileList) {
            //获取文件名加后缀
            String fileName = file.getOriginalFilename();

            if (fileName != null && fileName != "") {

                IdeaImageEntity ideaImageEntity=new IdeaImageEntity();

                String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
                fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileType;//新的文件名

                //创建一个当前日期的文件夹
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String fileAdd = sdf.format(new Date());



                //获取文件夹路径
                File file1 = new File(uploadPath +File.separator +fileAdd );
                //如果文件夹不存在则创建
                if (!file1.exists() && !file1.isDirectory()) {
                    file1.mkdir();
                }
                //将图片存入文件夹
                targetFile = new File(file1, fileName);
                try {
                    //将上传的文件写到服务器上指定的文件。
                    file.transferTo(targetFile);
                    ideaImageEntity.setQuestionId(ideaDataEntity.getId().intValue());
                    ideaImageEntity.setImgName(fileName);
                    ideaImageEntity.setImgType(fileType);
                    ideaImageEntity.setImgPath(fileAdd);
                    ideaImageService.saveIdeaImage(ideaImageEntity);

                    result.put("code", 200);
                    result.put("msg", "img idea success");

                } catch (Exception e) {
                    e.printStackTrace();
                    result.put("code", 500);
                    result.put("msg", "系统异常，图片上传失败");
                }
            }
        }
        return result;
    }

    /***
     * 时间戳转换
     * @param seconds
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }

}
