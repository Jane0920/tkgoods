package com.yys.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 注册提交的表单信息
 * Created by xyr on 2017/10/17.
 */
@Data
public class RegisterForm {

    /**
     * 注册的用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @Length(min = 8, max = 12, message = "密码要在8到12位之间")
    private String password;

    /**
     * 确认密码
     */
    @NotEmpty(message = "确认密码不能为空")
    @Length(min = 8, max = 12, message = "确认密码要在8到12位之间")
    private String passwordSure;

}
