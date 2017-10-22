package com.yys.service;

/**
 * 用于实现图片相关功能
 * Created by Administrator on 2017/10/22.
 */
public interface ImageService {

    /**
     * 删除图片
     * @param srcFileName
     * @throws Exception
     */
    void deleteImage(String srcFileName) throws Exception;

}
