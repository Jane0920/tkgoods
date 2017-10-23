/**
 * Created by Administrator on 2017/10/15.
 */
var BASE_URL = "/tkGoods/manage";
$(function () {
    var goodTable = $("#goodTable");

    initTable(0);

    $("#goodSearch").click(function () {
        initTable(0);
    })
});

$("#yesBtn").click(function () {
    alert("审核通过，" + $("#goodIdCheck").val());
    var id = $("#goodIdCheck").val();
    $.ajax(BASE_URL + "/checkGood/" + id, {
       method: 'post',
       data:{
           status: 1,
           startTime: $("#startTime").val(),
           endTime: $("#endTime").val()
       },
       success: function (result) {
           if (result.code == 0) {
               toastr.success("成功");
               $("#checkModal").modal('hide');
               $("#goodIdCheck").val("");
               $('#goodTable').bootstrapTable("refresh");
           } else {
               toastr.error(result.msg);
           }
       }
    });

});

$("#noBtn").click(function () {
    //alert("审核失败，" + $("#goodId").val());
    var reason = $("#reason").val();
    if (reason == null || reason.trim() == '') {
        toastr.error("请填写拒绝理由");
        return;
    }
    var id = $("#goodIdCheck").val();
    $.ajax(BASE_URL + "/checkGood/" + id, {
        method: 'post',
        data:{
            status: 2,
            reason: reason
        },
        success: function (result) {
            if (result.code == 0) {
                toastr.success("成功");
                $("#checkModal").modal('hide');
                $("#goodIdCheck").val("");
                $('#goodTable').bootstrapTable("refresh");
            } else {
                toastr.error(result.msg);
            }
        }
    });
});

function initTable(userId) {
    var text = $("#goodTextSearch").val();
    var username = $("#usernameSearch").val();
    var status = $("#statusSearch").val();
    //先销毁表格
    $('#goodTable').bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $("#goodTable").bootstrapTable({
        method: "get",  //使用get请求到服务器获取数据
        url: $("#goodTable").attr("data-tableUrl"), //获取数据的Servlet地址
        columns: [
            {
                title:'id',
                field:'id',
                sortable:true,
                visible:false
            },
            /*{
                title:'图片',
                field:'image',
                align: 'center',
                formatter: function(value,row,index){
                    if (value.indexOf("images/") == 0)
                        value = path + value;
                    return '<img style="height: 60px;width: 60px;" src="'+ value +'">';
                }
            },*/
            {
                title:'内容',
                field:'richText',
                class:'overFlow',
                align: 'center',
                formatter: function (value,row,index) {
                    var arr = [];
                    arr.push('<button class="btn btn-primary showGood">预览</button> ');
                    return arr;
                },
                events: {
                     'click .showGood': function (e, value, row, index) { //审核
                         $("#content").html(value);
                         $("#showModal").modal('show');
                     }
                }
            },
            {
                title:'状态',
                field:'status',
                align:'center',
                formatter: function (value,row,index) {
                    if (value == 0)
                        return "未审核";
                    else if (value == 1)
                        return "审核通过";
                    else
                        return "审核失败";
                }
            },
            {
                title:'失败理由',
                field:'reason',
                align:'center',
            },
            {
                title:'来源',
                field:'username',
                align:'center'
            },
            {
                title:'展示时间',
                field:'startTime',
                align:'center'
            },
            {
                title:'过期时间',
                field:'endTime',
                align:'center'
            },
            {
                title:'操作',
                field:'id',
                align:'center',
                //列数据格式化
                formatter:operateFormatter,
                events:operateEvents
            }
        ],
        striped: true,  //表格显示条纹
        pagination: true, //启动分页
        pageSize: 6,  //每页显示的记录数
        pageNumber: 1, //当前第几页
        pageList: [5, 10, 15, 20, 25],  //记录数可选列表
        search: false,  //是否启用查询
        showColumns: false,  //显示下拉框勾选要显示的列
        showRefresh: false,  //显示刷新按钮
        sidePagination: "server", //表示服务端请求
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParamsType: "undefined",
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
                page: params.pageNumber-1,
                size: params.pageSize,
                //userId: userId,
                text: text,
                username: username,
                status: status
            };
            return param;
        }
    });
}

//三个参数，value代表该列的值
function operateFormatter(value,row,index){
    var arr = [];
    arr.push('<button class="btn btn-primary editGood">编辑</button> ');
    var now = new Date();
    var startTime = new Date(row.startTime);
    var endTime = new Date(row.endTime);
    if (isAdmin && row.status != 1)
        arr.push('<button class="btn btn-primary checkGood">审核</button> ');
    if (row.status != 1 || (row.status == 1 && now > endTime))
        arr.push('<button class="btn btn-primary deleteGood">删除</button>');
    return arr.join('');
}

window.operateEvents = {
    'click .editGood': function (e, value, row, index) { //编辑
        var id = row.id;
        window.location.href = BASE_URL + "/editGoodHtml/" + id;
    },'click .checkGood': function (e, value, row, index) { //审核
        var id = row.id;
        //alert(id);
        $("#goodIdCheck").val("");
        //当显示时加载数据
        $("#goodIdCheck").val(id);
        $.ajax(BASE_URL + "/goodDetail/" + id, {
            method: 'get',
            success: function (result) {
                /*$("#goodImgCheck").attr("src", result.data.image);
                $("#goodTextCheck").val(result.data.text);*/
                $("#richText").html(result.data.richText);
                $("#checkModal").modal('show');
            }
        });
    },'click .deleteGood': function (e, value, row, index) { //删除
        var id = row.id;
        $.ajax(BASE_URL + "/deleteGood/" + id, {
            method: 'get',
            success: function (result) {
                if (result.code == 0) {
                    toastr.success("删除成功");
                    $('#goodTable').bootstrapTable("refresh");
                } else
                    toastr.error("删除失败");
            }
        });
    }
}
