/**
 * Created by xyr on 2017/10/17.
 */
$(function () {
    var BASE_URL = "/tkGoods"
    //注册
    $("#registerBtn").click(function () {
        $.ajax(BASE_URL + "/register", {
            method: 'post',
            data: $("#registerForm").serialize(),
            success: function (result) {
                if (result.code == 0) {
                    toastr.success("注册成功");
                } else {
                    toastr.error(result.msg);
                }
            }
        });
    });
    //登录
    $("#loginBtn").click(function () {
        $.ajax(BASE_URL + "/login", {
            method: 'post',
            data: $("#loginForm").serialize(),
            success: function (result) {
                if (result.code == 0) {
                    toastr.success("登录成功");
                    window.location.reload();
                } else {
                    toastr.error(result.msg);
                }
            }
        });
    });

    //注册验证
    $("#registerForm").validate({
        rules: {
            username: "required",
            password: {
                required: true,
                minlength: 8,
                maxlength: 12
            },
            passwordSure: {
                required: true,
                minlength: 8,
                maxlength: 12,
                equalTo: "#passwordR"
            }
        },
        messages: {
            username: "请输入用户名",
            password: {
                required: "请输入密码",
                minlength: "密码长度至少8位",
                maxlength: "密码长度最多12位"
            },
            passwordSure: {
                required: "请输入确认密码",
                minlength: "确认密码长度至少8位",
                maxlength: "密码长度最多12位",
                equalTo: "两次输入不符"
            }
        }
    });

    $("#loginForm").validate({
        rules: {
            username: "required",
            password: {
                required: "请输入密码",
                minlength: "密码长度至少8位",
                maxlength: "密码长度最多12位"
            }
        },
        messages: {
            username: "请输入用户名",
            password: {
                required: "请输入密码",
                minlength: "密码长度至少8位",
                maxlength: "密码长度最多12位"
            }
        }
    });
});


