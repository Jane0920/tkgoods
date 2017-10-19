package com.yys.controller;

import com.yys.dao.GoodTextRepository;
import com.yys.po.GoodText;
import com.yys.service.GoodTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xyr on 2017/10/16.
 */
@Controller
public class IndexController {

    @Autowired
    private GoodTextService goodTextService;
    @Autowired
    private GoodTextRepository goodTextRepository;

    @RequestMapping({"/", "/index"})
    public String index() {
        return "redirect:/goodShow";
    }

    /**
     * 搜索商品
     * @param searchContent 搜索内容
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping("/goodShow")
    public String goodShow(String searchContent, @PageableDefault(page = 0, size = 12) Pageable pageable, Model model) {
        if (pageable.getPageSize() != 4)
            pageable = new PageRequest(pageable.getPageNumber() == 0? pageable.getPageNumber():(pageable.getPageNumber() - 1),
                    12, pageable.getSort());
        else
            pageable = new PageRequest(pageable.getPageNumber() == 0? pageable.getPageNumber():(pageable.getPageNumber() - 1),
                    pageable.getPageSize(), pageable.getSort());

        Page<GoodText> goodTextPage = goodTextService.getGood(searchContent, pageable);
        model.addAttribute("list", goodTextPage);

        return "index";
    }

}
