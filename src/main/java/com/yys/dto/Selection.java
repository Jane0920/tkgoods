package com.yys.dto;

import java.util.function.Function;

/**
 * Created by xyr on 2017/10/19.
 */
public interface Selection<T, R> extends Function<T, R> {

    String getName();
}
