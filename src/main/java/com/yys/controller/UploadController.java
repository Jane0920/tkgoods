package com.yys.controller;

import com.yys.vo.PictureResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by 鲁源源 on 2017/10/20.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    public static final String ROOT = "F:/images/";

    public static final String SCHEME_SUFFEX = "://";

    public static final String SEPARATE_SERVER_PORT = ":";
    /**
     * 文件上传
     * web:
     * upload-path: （jar包所在目录）/resources/static/
     *
     * @param uploadFile
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadPic")
    @ResponseBody
    public PictureResult upload(MultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response){

        //spring security设置该响应头为deny，导致上传图片失败
        response.setHeader("X-Frame-Options","SAMEORIGIN");

        if (!uploadFile.isEmpty()) {
            // 获得新文件名
            String fileName = new SimpleDateFormat("yyyyMMddhhmmssSSS")
                    .format(new Date());
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                fileName = fileName + random.nextInt(10);
            }
            String originalFilename = uploadFile.getOriginalFilename();
            String newFileName = fileName
                    + originalFilename.substring(originalFilename.lastIndexOf("."));
            File fileDir = new File(ROOT);
            if(!fileDir.exists()){
                fileDir.setWritable(true);
                fileDir.mkdirs();
            }
            File targetFile = new File(ROOT, newFileName);
            try {
                uploadFile.transferTo(targetFile);

                return PictureResult.ok("images/"+newFileName);
            } catch (Exception e) {
                log.error("上传商品图片失败");
            }
        }

        return PictureResult.error();

    }
}
