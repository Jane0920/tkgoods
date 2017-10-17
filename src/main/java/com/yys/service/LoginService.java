package com.yys.service;

import com.yys.po.Login;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by xyr on 2017/10/16.
 */
public interface LoginService extends UserDetailsService {

    /**
     * 根据用户名查找用户
     */
    Login findByUsername(String username);

    /**
     * 添加用户
     */
    Login addUser(String username, String password, boolean isEnabled);

}
