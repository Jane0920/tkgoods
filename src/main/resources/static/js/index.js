/**
 * Created by xyr on 2017/10/17.
 */
var BASE_URL = "/tkGoods";
//判断是否支持一键复制 0 不支持 1 支持
var ClipboardSupport = 0;
if (typeof Clipboard !== "undefined") {
    ClipboardSupport = 1;
} else {
    ClipboardSupport = 0;
}

$(function () {
    //alert($("#copyInput").html());
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
    $("#allBtn").click(function () {
        $("#searchContent").val("");
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

$(".copyBtn").click(function () {
    if (!ClipboardSupport) {
        layer.msg('浏览器版本太低，请升级或更换浏览器后重新复制！');
        //toastr.info('浏览器版本太低，请升级或更换浏览器后重新复制！');
        return false;
    }

    var copyDiv = $(this).prev();
    var content = $('#copyContent');

    if (content.length === 0) {
        content = $('<div id="copyContent" class="displayNone"></div>');
        $('body').append(content);
    }
    if (copyDiv.length > 0) {
        content.html(copyDiv.find('.copyInput').html());
        copyText('.copyBtn', content[0]);
    } else {
        layer.msg('太快了，请重新复制！');
        //toastr.info('太快了，请重新复制！');
    }
});

// 设置一键复制, 复制文本
var hasAlert = 0;
function copyText(target, copy) {
    var clipboard = new Clipboard(target, {
        target: function () {
            return copy;
        }
    });

    clipboard.on('success', function (e) {
        layer.msg("已复制");
        e.clearSelection();
    });

    clipboard.on('error', function (e) {
        layer.msg('复制失败，请升级或更换浏览器后重新复制！');
        e.clearSelection();
    });
}

