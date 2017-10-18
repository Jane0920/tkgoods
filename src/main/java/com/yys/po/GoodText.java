package com.yys.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by xyr on 2017/10/18.
 */
@Entity
@Data
@Table(name = "GOODTEXT")
public class GoodText {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 商品文本
     */
    @Lob
    private String text;

    /**
     * 图片
     */
    private String image;

    /**
     * 商品状态 0-未审核，1-审核通过（前台显示），2-审核失败
     */
    private int status;

    /**
     * 创建者id
     */
    private String userId;

    /**
     * 创建者用户名
     */
    private String username;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 开始展示时间
     */
    private Date startTime;

    /**
     * 展示截止时间
     */
    private Date endTime;

    /**
     * 是否被创建者删除
     */
    private boolean hasDelete;

}
