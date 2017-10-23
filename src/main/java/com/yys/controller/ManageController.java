package com.yys.controller;

import com.yys.config.ImageConfig;
import com.yys.enums.GoodStatusEnum;
import com.yys.enums.ResultEnum;
import com.yys.po.GoodText;
import com.yys.po.Login;
import com.yys.po.User;
import com.yys.service.GoodTextService;
import com.yys.vo.PageModel;
import com.yys.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * Created by xyr on 2017/10/19.
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private GoodTextService goodTextService;
    @Autowired
    private ImageConfig imageConfig;

    /**
     * 转入商品列表管理页面
     * @return
     */
    @RequestMapping("/goodListHtml")
    public String goodListHtml() {
        return "manage/goodList";
    }

    @RequestMapping("/addGoodTextHtml")
    public String addGoodTextHtml() {
        return "manage/addGoodText";
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
        return new PageModel<GoodText>(goodTextPage.getTotalElements(), goodTextPage.getContent());
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
     * @param reason 拒绝理由
     * @return
     */
    @RequestMapping("/checkGood/{id}")
    @ResponseBody
    public ResultVo checkGood(@AuthenticationPrincipal Login login, @PathVariable String id,
                              int status, Date startTime, Date endTime, String reason) {

        //判断登录者身份
        if (login instanceof User)
            return ResultVo.error(ResultEnum.NO_PERMISSION.getCode(), ResultEnum.NO_PERMISSION.getMessage());
        if (status == GoodStatusEnum.CHECKSUCCESS.getCode() && (startTime == null || endTime == null)) {
            return ResultVo.error(ResultEnum.DATE_NOT_SET.getCode(), ResultEnum.DATE_NOT_SET.getMessage());
        }

        try {
            return goodTextService.updateGoodStatus(id, status, startTime == null? null: date2LocalDate(startTime),
                    endTime == null? null: date2LocalDate(endTime), reason);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(ResultEnum.UPDATE_FAILURE.getCode(), ResultEnum.UPDATE_FAILURE.getMessage());
        }

    }

    /**
     * 删除商品
     * @param login 登录者
     * @param id 商品id
     * @return
     */
    @RequestMapping("/deleteGood/{id}")
    @ResponseBody
    public ResultVo deleteGood(@AuthenticationPrincipal Login login, @PathVariable String id) {
        try {
            if (login instanceof User)
                return goodTextService.updateGoodDelete(id);
            else
                return goodTextService.deleteGood(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(ResultEnum.DELETE_FAILURE.getCode(), ResultEnum.DELETE_FAILURE.getMessage());
        }
    }

    /**
     * 添加商品
     * @param login 登录者
     * @param richText 文本
     * @return
     */
    @RequestMapping("/addGoods")
    @ResponseBody
    public ResultVo addGood(@AuthenticationPrincipal Login login, String richText) {
        if (StringUtils.isBlank(richText))
            return ResultVo.error(ResultEnum.CONTENT_IS_EMPTY.getCode(), ResultEnum.CONTENT_IS_EMPTY.getMessage());

        try {
            return goodTextService.addGood(richText, login);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(ResultEnum.FAILURE.getCode(), ResultEnum.FAILURE.getMessage());
        }
    }

    /**
     * 返回商品到编辑页面
     * @param id 商品id
     * @param map
     * @return
     */
    @RequestMapping("/editGoodHtml/{id}")
    public String editGoodHtml(@PathVariable String id, Map<String, Object> map) {
        GoodText goodText = goodTextService.findOne(id);
        if (goodText == null) {
            map.put("msg", "未找到该商品");
            return "manage/goodList";
        }

        map.put("good", goodText);
        map.put("imageConfig", imageConfig);
        return "manage/editGoodText";
    }

    /**
     * 编辑商品
     * @param id 商品id
     * @param richText 文本
     * @param startTime 开始时间
     * @param endTime 过期时间
     * @return
     */
    @RequestMapping("/editGoods")
    @ResponseBody
    public ResultVo editGood(String id, String richText,
                             Date startTime, Date endTime) {
        try {
            return goodTextService.updateGoodText(id, richText, startTime == null ? null : date2LocalDate(startTime),
                    endTime == null ? null : date2LocalDate(endTime));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error(ResultEnum.FAILURE.getCode(), ResultEnum.FAILURE.getMessage());
        }
    }

    private LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

}
