package com.yys.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yys.util.serialize.LocalDate2StrSerialize;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @JsonSerialize()
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
    @Column(columnDefinition = "datetime")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(columnDefinition = "datetime")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime updateTime;

    /**
     * 开始展示时间
     */
    @Column(columnDefinition = "date")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @JsonSerialize(using = LocalDate2StrSerialize.class)
    private LocalDate startTime;

    /**
     * 展示截止时间
     * @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)用于LocalDate和数据库的date属性之间的转换
     */
    @Column(columnDefinition = "date")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @JsonSerialize(using = LocalDate2StrSerialize.class)
    private LocalDate endTime;

    /**
     * 是否被创建者删除
     */
    private boolean hasDelete;

    /*public static List<Selection<GoodText, ?>> getSelection() {
        return Arrays.asList(
                new SimpleSelection<GoodText, String>("id", "id"),
                new SimpleSelection<GoodText, String>("image", "image"),
                new SimpleSelection<GoodText, String>("text", "text"),
                new Selection<GoodText, Object>() {

                    @Override
                    public Object apply(GoodText goodText) {
                        LocalDate now = LocalDate.now();
                        if (now.isBefore(goodText.startTime) || now.isAfter(goodText.endTime))
                            return GoodStatusEnum.TIMEOVER.getMessage();
                        if (goodText.status == GoodStatusEnum.UNCHECK.getCode())
                            return GoodStatusEnum.UNCHECK.getMessage();
                        else if (goodText.status == GoodStatusEnum.CHECKSUCCESS.getCode())
                            return GoodStatusEnum.CHECKSUCCESS.getMessage();
                        else
                            return GoodStatusEnum.CHECKFAILURE.getMessage();
                    }

                    @Override
                    public String getName() {
                        return "status";
                    }
                },
                new SimpleSelection<GoodText, String>("username", "username"),
                new Selection<GoodText, Object>() {

                    @Override
                    public Object apply(GoodText goodText) {
                        return goodText.startTime == null? "": LocalDateUtil.toStr(goodText.startTime);
                    }

                    @Override
                    public String getName() {
                        return "startTime";
                    }
                },
                new Selection<GoodText, Object>() {

                    @Override
                    public Object apply(GoodText goodText) {
                        return goodText.endTime == null? "": LocalDateUtil.toStr(goodText.endTime);
                    }

                    @Override
                    public String getName() {
                        return "endTime";
                    }
                }
        );
    }*/

}
