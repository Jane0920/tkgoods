package com.yys.service.impl;

import com.yys.dao.LoginRepository;
import com.yys.po.Login;
import com.yys.po.User;
import com.yys.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by xyr on 2017/10/16.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepository.findByUsername(username);
        if (login == null) {
            throw new UsernameNotFoundException("cannot find username: " + username);
        }
        return login;
    }

    @Override
    public Login findByUsername(String username) {
        return loginRepository.findByUsername(username);
    }

    @Override
    public Login addUser(String username, String password, boolean isEnabled) {
        User user = new User();
        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        user.setEnabled(isEnabled);
        Login login = loginRepository.saveAndFlush(user);

        return login;
    }
}
