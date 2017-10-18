package com.yys.service;

import com.yys.po.GoodText;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by xyr on 2017/10/16.
 */
public interface GoodTextService {

    /**
     * 前台商品展示
     */
    Page<GoodText> getGood(String searchContent, Pageable pageable);

}
