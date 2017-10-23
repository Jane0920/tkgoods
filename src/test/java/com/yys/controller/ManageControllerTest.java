package com.yys.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by xyr on 2017/10/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageControllerTest {



    @Test
    public void goodList() throws Exception {

    }

    @Test
    public void regex() throws Exception {
        String text = "<div id=\"copyInput\" class=\"copyInput\"><img src=\"../../images/38e85e8081f14f3e8d3cba5b84797cf3.jpg\" " +
                "alt=\"\" width=\"240\" height=\"240\" /><br />都市爱巢双人简约磨毛四件套<br /> " +
                "券后【59元】包邮秒杀<br /> 优惠券：https://shop.m.taobao.com/shop/coupon.htm?seller_id=1691552074&amp;activityId=df7ae0ce3e9a4ca5a5630da7d15c2e9b<br /> 商品地址：https://detail.tmall.com/item.htm?id=551024160506<br /> 超柔天然阿拉伯棉，0刺激，无静电，静享纯净睡眠时代，加厚磨毛，贴身即暖，不起球不掉色~多款色可选！</div>";

        Pattern pattern = Pattern.compile("<img([\s\S]*)src");
    }

}