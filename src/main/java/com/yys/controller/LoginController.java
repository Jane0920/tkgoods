package com.yys.controller;

import com.yys.enums.ResultEnum;
import com.yys.form.RegisterForm;
import com.yys.po.Login;
import com.yys.service.LoginService;
import com.yys.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * 登录相关
 * Created by xyr on 2017/10/16.
 */
@Controller
public class LoginController {
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    private LoginService loginService;

    /**
     * 如果请求登录页面，则返回商品列表页，并提示请先登录
     */
    @RequestMapping("/login_page")
    public String loginPage(Map<String, String> map) {
        map.put("success", "false");
        map.put("message", "您还未登录");
        return "index";
    }

    @RequestMapping("/login/success")
    @ResponseBody
    public ResultVo loginSuccess(HttpServletRequest request, HttpServletResponse response) {
        /*SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = null;
        if (savedRequest != null)
            targetUrl = savedRequest.getRedirectUrl();*/
        return ResultVo.success();
    }

    @RequestMapping("/login/failure")
    @ResponseBody
    public ResultVo loginFailure() {
        return ResultVo.error(ResultEnum.LOGIN_FAILURE.getCode(), ResultEnum.LOGIN_FAILURE.getMessage());
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ResponseBody
    public ResultVo register(@Valid RegisterForm registerForm, Map<String, Object> map) {
        if (registerForm == null) {
            return ResultVo.error(ResultEnum.REGISTER_FAILURE.getCode(), ResultEnum.REGISTER_FAILURE.getMessage());
        }

        //判断用户名是否已存在
        Login login = loginService.findByUsername(registerForm.getUsername());
        if (login != null) {
            return ResultVo.error(ResultEnum.USERNAME_EXIT.getCode(), ResultEnum.USERNAME_EXIT.getMessage());
        }
        if (!registerForm.getPassword().equals(registerForm.getPasswordSure())) {
            return ResultVo.error(ResultEnum.PASSWORD_NOT_EQUAL.getCode(), ResultEnum.PASSWORD_NOT_EQUAL.getMessage());
        }

        //添加用户
        Login newLogin = loginService.addUser(registerForm.getUsername(), registerForm.getPassword(), true);
        return ResultVo.success(newLogin);
    }

}
