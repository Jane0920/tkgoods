package com.yys.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Created by xyr on 2017/11/5.
 */
public class RichTextUtil {

    public static String getRichText(String goodImg, String goodName, BigDecimal afterPrice,
                                     String couponUrl, String goodUrl, String description) {
        String template = "<div id=\"copyInput\" class=\"copyInput\">" +
                "<img src=\"[图片地址]\" width=\"240\" height=\"240\" /><br />" +
                "[商品名称]<br /> " +
                "券后【[券后价格]】包邮秒杀<br /> " +
                "优惠券：[优惠券地址]<br />" +
                " 商品地址：[商品链接]<br /> " +
                "[商品描述]</div>";

        if (StringUtils.isNotBlank(goodImg)) {
            template = template.replaceAll("\\[图片地址\\]", goodImg);
        }

        if (StringUtils.isNotBlank(goodName))
            template = template.replaceAll("\\[商品名称\\]", goodName);

        if (afterPrice != null)
            template = template.replaceAll("\\[券后价格\\]", afterPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        if (StringUtils.isNotBlank(couponUrl))
            template = template.replaceAll("\\[优惠券地址\\]", goodUrl);

        if (StringUtils.isNotBlank(description))
            template = template.replaceAll("\\[商品描述\\]", description);

        return template;

    }

    public static void main(String[] args) {
        String img = "http://101.132.102.102:8080/tkGoods/images/b79ce4defa9144328ce7ced94958a125.jpg";
        String goodName = "秋冬羊毛围巾";
        BigDecimal afterPrice = new BigDecimal(56.23);
        String couponUrl = "https://shop.m.taobao.com/shop/coupon.htm?seller_id=1811401893&activityId=107e290029454b7199455d43f10a048d";
        String goodUrl = "https://detail.tmall.com/item.htm?id=36102906969";
        String description = "妙语莲花高品质围巾，重量320g左右，权威质检认证，加厚保暖款，良好口碑，备战冬季，闺蜜人手一条~";
        String text = getRichText(img, goodName, afterPrice, couponUrl, goodUrl, description);
        System.out.println(text);
    }

}
