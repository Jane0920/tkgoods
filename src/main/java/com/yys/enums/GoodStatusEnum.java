package com.yys.enums;

import lombok.Getter;

/**
 * Created by xyr on 2017/10/19.
 */
@Getter
public enum GoodStatusEnum {
    UNCHECK(0, "未审核"),
    CHECKSUCCESS(1, "审核通过"),
    CHECKFAILURE(2, "审核失败"),
    TIMEOVER(3, "产品过期")
    ;

    private int code;

    private String message;

    GoodStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
