package com.yys.vo;

import lombok.Data;

/**
 * Created by 鲁源源 on 2017/10/20.
 */
@Data
public class PictureResult {
    private int error;
    private String relativePath;

    private PictureResult(int error, String relativePath) {
        this.error = error;
        this.relativePath = relativePath;
    }
    //成功时调用的方法
    public static PictureResult ok(String relativePath) {
        return new PictureResult(0, relativePath);
    }
    //失败时调用的方法
    public static PictureResult error() {
        return new PictureResult(1, null);
    }

}
