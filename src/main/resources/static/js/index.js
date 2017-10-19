/**
 * Created by xyr on 2017/10/17.
 */
var BASE_URL = "/tkGoods";
$(function () {

    selected();

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
                rangelength:[8,12]
            },
            passwordSure: {
                required: true,
                rangelength:[8,12],
                equalTo: "#passwordR"
            }
        },
        messages: {
            username: "请输入用户名",
            password: {
                required: "请输入密码",
                rangelength: "密码长度要介于8到12之间"
            },
            passwordSure: {
                required: "请输入确认密码",
                rangelength: "密码长度要介于8到12之间",
                equalTo: "两次输入不符"
            }
        }
    });
    //登录验证
    $("#loginForm").validate({
        rules: {
            username: "required",
            password: {
                required: "请输入密码",
                rangelength:[8,12]
            }
        },
        messages: {
            username: "请输入用户名",
            password: {
                required: "请输入密码",
                rangelength: "密码长度要介于8到12之间"
            }
        }
    });

    //搜索
    $("#searchBtn").click(function () {
        refresh(1);
    });
    //页码改变
    $(".pagination").find("a").click(function () {
        if (!$(this).hasClass("disabled"))
            refresh($(this).attr("data-pageNum"));
    });
    $("select[name='page']").change(function () {
        refresh($(this).val());
    });

});

function refresh(pageNumber) {
    var searchContent = $("#searchContent").val();
    if (pageNumber == undefined || pageNumber == null)
        pageNumber = 0;

    window.location.href = BASE_URL + "/goodShow?" + "searchContent=" + searchContent + "&page=" + pageNumber +
            "&size=12";
}

//选中的下拉框
function selected() {
    var current = $("select[name='page']").attr('data-select');
    $("select[name='page']  option[value='"+ current +"']").attr("selected", true);
}

