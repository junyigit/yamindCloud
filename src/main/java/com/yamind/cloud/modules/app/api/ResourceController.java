package com.yamind.cloud.modules.app.api;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/appResource")
public class ResourceController {
    // 1. MultipartFile 获取文件流

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${upload-path}")
    private String uploadPath;


    @PostMapping("uploadImg")
    @ResponseBody
    public R uploadFile(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {

        R result = new R();
        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile = null;
        String url = "";//返回存储路径
        int code = 1;
        System.out.println(file);
        String fileName = file.getOriginalFilename();//获取文件名加后缀
        if (fileName != null && fileName != "") {
            //String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";

            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 = new File(uploadPath + fileAdd);
            //如果文件夹不存在则创建
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);

            } catch (Exception e) {
                e.printStackTrace();
                // result.setMessage("系统异常，图片上传失败");
            }
        }
        return result;
    }

    /**
     * 意见反馈图片返回
     * @param fileDate
     * @param fileName
     * @param request
     * @return
     */
    @GetMapping(value = "imgs/{fileDate}/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity returnIdeaImg(@PathVariable String fileDate, @PathVariable String fileName, HttpServletRequest request) {
        try {
            // 获取请求文件后缀(.xxx)
            String requestURI = request.getRequestURI();
            String suffix = requestURI.substring(requestURI.indexOf("."), requestURI.length());
            // File.separator   linux \   windows // 或者 \
            String filePath = uploadPath + fileDate + File.separator + fileName;
            return ResponseEntity.ok(resourceLoader.getResource("file:" + filePath));
            //  return R.ok("file:" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * 返回ZIP文件给APP
     * @param fileDate
     * @param fileName
     * @return
     * @throws IOException
     */
    @GetMapping(value = "zips/{fileDate}/{fileName}")
    public ResponseEntity<byte[]> showFile(@PathVariable String fileDate, @PathVariable String fileName) throws IOException {
        // 设置 response 响应头
        String filePath = uploadPath + fileDate + File.separator + fileName;
        FileInputStream fis = new FileInputStream(new File(filePath));
        int size = fis.available();
        byte[] body = new byte[size];
        // 读取字节
        fis.read(body);
        // 写入传输流中
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" +fileName);
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return responseEntity;
    }


    /**
     * 上传zip
     * @param file
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("uploadZip")
    @ResponseBody
    //这里面就是用来测试zip上传的
    public R uploadZipFile(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
       //获取接收文件的对象
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        //获取上传的文件（存放在内存中） zhege m
        MultipartFile uploadPdf = multipartRequest.getFile("uploadFile");

        String fileName = file.getOriginalFilename();//获取文件名加后缀
        //进行文件的保存
        if (uploadPdf != null) {
            //获取文件后缀名
            String suffix ="F:/"+fileName;
            //需要保存的文件对象
            File targetFile = new File( suffix );
            //判断该文件对象在磁盘上是否存在
            if (!targetFile.exists()  && !targetFile.isDirectory()) {
                //磁盘上不存在，则进行文件的创建
                targetFile.mkdirs();
            }
            //文件从内存中保存到磁盘上(该方法是springmvc封装的方法)
            uploadPdf.transferTo(targetFile);
        }
        return R.ok("1111");
    }


}
