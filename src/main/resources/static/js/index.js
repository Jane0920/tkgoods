/**
 * Created by xyr on 2017/10/17.
 */
$(function () {

    //注册验证
    $("#registerForm").validate({
        rules: {
            username: "required",
            password: {
                required: true,
                minlength: 8
            },
            passwordSure: {
                required: true,
                minlength: 8,
                equalTo: "#passwordR"
            }
        },
        messages: {
            username: "请输入用户名",
            password: {
                required: "请输入密码",
                minlength: "密码长度至少八位"
            },
            passwordSure: {
                required: "请输入确认密码",
                minlength: "确认密码长度至少8位",
                equalTo: "两次输入不符"
            }
        }
    });
});


