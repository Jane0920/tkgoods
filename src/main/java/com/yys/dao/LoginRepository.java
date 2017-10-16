package com.yys.dao;

import com.yys.po.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by xyr on 2017/10/16.
 */
public interface LoginRepository extends JpaRepository<Login, String>, JpaSpecificationExecutor<Login> {

    Login findByUsername(String username);

}
