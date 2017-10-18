package com.yys.dao;

import com.yys.po.GoodText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by xyr on 2017/10/18.
 */
public interface GoodTextRepository extends JpaRepository<GoodText, String>, JpaSpecificationExecutor<GoodText> {
}
