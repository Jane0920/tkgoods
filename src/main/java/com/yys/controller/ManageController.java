package com.yys.controller;

import com.yys.enums.ResultEnum;
import com.yys.po.GoodText;
import com.yys.po.Login;
import com.yys.po.User;
import com.yys.service.GoodTextService;
import com.yys.vo.PageAndSelection;
import com.yys.vo.PageModel;
import com.yys.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

/**
 * Created by xyr on 2017/10/19.
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private GoodTextService goodTextService;

    /**
     * 转入商品列表管理页面
     * @return
     */
    @RequestMapping("/goodListHtml")
    public String goodListHtml() {
        return "/manage/goodList";
    }

    /**
     * 查询商品列表
     * @param login 登录者
     * @param text 搜索文本
     * @param status 搜索状态
     * @param username 创建者
     * @param pageable
     * @return
     */
    @RequestMapping("/goodList")
    @ResponseBody
    public PageModel<GoodText> goodList(@AuthenticationPrincipal Login login, String text, int status, String username,
                                     @PageableDefault(page = 0, size = 6) Pageable pageable) {
        boolean isAdmin = true;
        if (login instanceof User) {
            username = login.getUsername();
            isAdmin = false;
        }

        Page<GoodText> goodTextPage = goodTextService.getGood(text, status, username, isAdmin, pageable);
        return new PageModel<GoodText>(goodTextPage.getSize(), goodTextPage.getContent());
    }

    /**
     * 商品详细信息
     */
    @RequestMapping("/goodDetail/{id}")
    @ResponseBody
    public ResultVo<GoodText> goodDetail(@PathVariable String id) {

        GoodText goodText = goodTextService.goodDetail(id);

        return ResultVo.success(goodText);
    }

    /**
     * 审核商品
     * @param login 登录者
     * @param id 审核的商品id
     * @param status 审核后的状态
     * @param startTime 展示时间
     * @param endTime 过期时间
     * @return
     */
    @RequestMapping("/checkGood/{id}")
    @ResponseBody
    public ResultVo checkGood(@AuthenticationPrincipal Login login, @PathVariable String id,
                              int status, LocalDate startTime, LocalDate endTime) {

        //判断登录者身份
        if (login instanceof User)
            return ResultVo.error(ResultEnum.NO_PERMISSION.getCode(), ResultEnum.NO_PERMISSION.getMessage());

        try {
            return goodTextService.updateGoodStatus(id, status, startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(ResultEnum.UPDATE_FAILURE.getCode(), ResultEnum.UPDATE_FAILURE.getMessage());
        }

    }

}
