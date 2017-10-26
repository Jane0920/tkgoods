package com.yys.service.impl;

import com.yys.service.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by xyr on 2017/10/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceImplTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void loadUserByUsername() throws Exception {
        String password = passwordEncoder.encode("adminYYS123");
        password = passwordEncoder.encode("12345678");
        loginService.loadUserByUsername("admin");
    }

}