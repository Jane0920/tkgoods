package com.yys.vo;

import lombok.Getter;

import java.util.List;

/**
 * Created by xyr on 2017/10/19.
 */
@Getter
public class PageModel<T> {

    private long total;

    private List<T> rows;

    public PageModel(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
