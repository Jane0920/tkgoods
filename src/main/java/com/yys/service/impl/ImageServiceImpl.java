package com.yys.service.impl;

import com.yys.config.ImageConfig;
import com.yys.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by Administrator on 2017/10/22.
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageConfig imageConfig;

    @Override
    public void deleteImage(String srcFileName) throws Exception {
        File file = new File(imageConfig.getRoot(), srcFileName);
        if (file.exists())
            file.delete();
    }
}
