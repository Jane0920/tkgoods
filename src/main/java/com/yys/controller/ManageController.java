package com.yys.controller;

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

}
