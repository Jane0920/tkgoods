package com.yys.enums;

import lombok.Getter;

/**
 * Created by xyr on 2017/10/17.
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    REGISTER_FAILURE(2, "注册失败"),
    USERNAME_EXIT(3, "用户名已存在"),
    PASSWORD_NOT_EQUAL(4, "密码不相等"),
    LOGIN_FAILURE(5, "用户名或密码错误"),
    NO_PERMISSION(6, "无权限操作"),
    GOODS_NOT_EXIT(7, "商品不存在"),
    UPDATE_FAILURE(8, "更新失败"),
    DELETE_FAILURE(9, "删除失败"),
    DATE_NOT_SET(10, "时间不能为空"),
    CONTENT_IS_EMPTY(11, "内容不能为空")
    ;

    private int code;

    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
