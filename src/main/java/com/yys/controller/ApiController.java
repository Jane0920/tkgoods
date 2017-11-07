package com.yys.controller;

import com.yys.enums.ResultEnum;
import com.yys.service.GoodTextService;
import com.yys.util.RichTextUtil;
import com.yys.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * Created by xyr on 2017/11/7.
 */
@Slf4j
@RequestMapping("/api")
@Controller
public class ApiController {

    @Autowired
    private GoodTextService goodTextService;

    @RequestMapping("/addGood")
    public ResultVo addGood(String goodImg, String goodName, BigDecimal afterPrice,
                            String couponUrl, String goodUrl, String description) {

        String richText = RichTextUtil.getRichText(goodImg, goodName, afterPrice, couponUrl, goodUrl, description);

        try {
            ResultVo resultVo = goodTextService.addGood(richText, null);
            return resultVo;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【群拉去商品】报错：", e);
            return ResultVo.error(ResultEnum.FAILURE.getCode(), ResultEnum.FAILURE.getMessage());
        }
    }

}
