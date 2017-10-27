package com.yys.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 商品图片
 * Created by Administrator on 2017/10/27.
 */
@Entity
@Data
public class Image {

    @Id
    private String id;

    /**
     * 图片存放的相对地址
     */
    private String path;

    /**
     * 相对应的商品id，如果为null则说明该图片不被使用可删除
     */
    private String goodId;

}
