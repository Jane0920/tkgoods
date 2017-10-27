package com.yys.service.impl;

import com.yys.config.ImageConfig;
import com.yys.dao.ImageRepository;
import com.yys.po.Image;
import com.yys.service.ImageService;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Administrator on 2017/10/22.
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageConfig imageConfig;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ResourceService resourceService;

    @Override
    public void deleteImage(String goodId) throws Exception {
        Image image = imageRepository.findByGoodId(goodId);
        if (image == null)
            return;
        imageRepository.delete(image);
        resourceService.deleteResource(image.getPath());
    }

    @Override
    public void saveImage(String path) throws Exception {
        Image image = new Image();
        image.setId(UUID.randomUUID().toString());
        image.setPath(path);
        imageRepository.saveAndFlush(image);
    }

    @Override
    public void saveImage(Image image) throws Exception {
        imageRepository.saveAndFlush(image);
    }

    @Override
    public Image findImageByPath(String path) {
        Image image = imageRepository.findByPath(path);
        if (image == null) {
            image = new Image();
            image.setId(UUID.randomUUID().toString());
            image.setPath(path);
        }
            return image;
    }

    @Override
    public Image findImageByGoodId(String goodId) {
        return imageRepository.findByGoodId(goodId);
    }
}
