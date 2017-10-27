package com.yys.service;

import com.yys.po.Image;

/**
 * 用于实现图片相关功能
 * Created by Administrator on 2017/10/22.
 */
public interface ImageService {

    /**
     * 删除图片
     * @param goodId
     * @throws Exception
     */
    void deleteImage(String goodId) throws Exception;

    /**
     * 保存图片
     */
    void saveImage(String path) throws Exception;

    void saveImage(Image image) throws Exception;

    /**
     * 查找图片根据路径
     */
    Image findImageByPath(String path);

    /**
     * 根据商品id查找对应的图片
     * @param goodId
     * @return
     */
    Image findImageByGoodId(String goodId);

}
