package com.yys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 登录相关
 * Created by xyr on 2017/10/16.
 */
@Controller
public class LoginController {

    /**
     * 如果请求登录页面，则返回商品列表页，并提示请先登录
     */
    @RequestMapping("/login_page")
    public String loginPage(Map<String, String> map) {
        map.put("success", "false");
        map.put("message", "您还未登录");
        return "index";
    }

    /**
     * 用户注册
     */
    /*@RequestMapping("/register")
    public*/

}
