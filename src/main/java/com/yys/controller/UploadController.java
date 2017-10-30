package com.yys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yys.config.ImageConfig;
import com.yys.po.Image;
import com.yys.service.ImageService;
import com.yys.vo.PictureResult;
import lombok.extern.slf4j.Slf4j;
import me.jiangcai.lib.resource.service.ResourceService;
import me.jiangcai.lib.seext.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Created by 鲁源源 on 2017/10/20.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    //public static final String ROOT = "F:/images/";

    public static final String SCHEME_SUFFEX = "://";

    public static final String SEPARATE_SERVER_PORT = ":";

    @Autowired
    private ImageConfig imageConfig;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ImageService imageService;

    /**
     * 为tiny mce 专门设计的图片上传者
     * <a href="https://www.tinymce.com/docs/get-started/upload-images/">More</a>
     * TODO 缺少fasterXML的支持，以及resourceService；你们可以自行增加
     *
     * @param file
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/tinyImage",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> tinyUpload(MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            try (InputStream inputStream = file.getInputStream()) {
                //String path = "watch/" + UUID.randomUUID().toString().replaceAll("-", "") + "." + FileUtils.fileExtensionName(file.getOriginalFilename());
                String path = UUID.randomUUID().toString().replaceAll("-", "") + "." + FileUtils.fileExtensionName(file.getOriginalFilename());
                resourceService.uploadResource(path, inputStream);
                HashMap<String, Object> body = new HashMap<>();
                body.put("location", resourceService.getResource(path).httpUrl().toString());
                //保存上传的图片信息
                imageService.saveImage(path);

                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(objectMapper.writeValueAsString(body));
            }
        } catch (Exception ex) {
            HashMap<String, Object> body = new HashMap<>();
            body.put("error", ex.getLocalizedMessage());
            log.error("【上传图片】异常：", ex);
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(objectMapper.writeValueAsString(body));
        }
    }

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
            File fileDir = new File(imageConfig.rootPath());
            if(!fileDir.exists()){
                fileDir.setWritable(true);
                fileDir.mkdirs();
            }
            File targetFile = new File(imageConfig.rootPath(), newFileName);
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
