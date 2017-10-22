package com.yys.service;

import com.yys.po.GoodText;
import com.yys.po.Login;
import com.yys.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * Created by xyr on 2017/10/16.
 */
public interface GoodTextService {

    /**
     * 根据商品id查找商品
     * @param id
     * @return
     */
    GoodText findOne(String id);

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
     */
    ResultVo updateGoodText(String id, String text, String image,
                            LocalDate startTime, LocalDate endTime) throws Exception;

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

    /**
     * 添加商品
     * @param text 文本
     * @param image 图片路径
     * @param login
     * @return
     */
    ResultVo addGood(String text, String image, Login login) throws Exception;

}
