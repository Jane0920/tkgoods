package com.yys.service;

import com.yys.po.GoodText;
import com.yys.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * Created by xyr on 2017/10/16.
 */
public interface GoodTextService {

    /**
     * 前台商品展示
     * @param searchContent 搜索的内容
     * @param pageable
     * @return
     */
    Page<GoodText> getGood(String searchContent, Pageable pageable);

    /**
     * 后台管理展示
     * @param text 搜索的文本内容
     * @param status 搜索的状态
     * @param username 搜索的用户名
     * @param isAdmin 是否为管理员
     * @param pageable
     * @return
     */
    Page<GoodText> getGood(String text, int status, String username, boolean isAdmin, Pageable pageable);

    /**
     * 获取商品详情
     * @param id 商品id
     * @return
     */
    GoodText goodDetail(String id);

    ResultVo updateGoodStatus(String id, int status, LocalDate startTime, LocalDate endTime) throws Exception;

    /**
     * 更新商品
     * @param goodText
     */
    void updateGoodText(GoodText goodText);

    /**
     * 更新删除状态
     * @param id
     * @return
     */
    ResultVo updateGoodDelete(String id) throws Exception;

    /**
     * 删除商品
     * @param id
     * @return
     */
    ResultVo deleteGood(String id) throws Exception;

}
