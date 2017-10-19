package com.yys.vo;

import com.yys.dto.Selection;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by xyr on 2017/10/19.
 */
@Getter
public class PageAndSelection<T> {

    private final Page<T> page;

    private final List<Selection<T, ?>> selectionList;

    public PageAndSelection(Page<T> page, List<Selection<T, ?>> selectionList) {
        this.page = page;
        this.selectionList = selectionList;
    }

}
