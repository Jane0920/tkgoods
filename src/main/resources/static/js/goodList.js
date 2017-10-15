/**
 * Created by Administrator on 2017/10/15.
 */
$(function () {
    var goodTable = $("#goodTable");

    initTable(0);

    $("#goodSearch").click(function () {
        initTable(0);
    })
});

$("#yesBtn").click(function () {
    //alert("审核通过，" + $("#goodId").val());
    $("#checkModal").modal('hide');
});

$("#noBtn").click(function () {
    //alert("审核失败，" + $("#goodId").val());
    $("#checkModal").modal('hide');
});

function initTable(userId) {
    var text = $("#goodTextSearch").val();
    alert(text);
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
            {
                title:'图片',
                field:'image',
                align: 'center',
                formatter: function(value,row,index){
                    return '<img style="height: 60px;width: 60px;" src="'+ value +'">';
                }
            },
            {
                title:'文本',
                field:'text',
                class:'overFlow',
                align: 'center'
            },
            {
                title:'状态',
                field:'status',
                align:'center'
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
        pageSize: 4,  //每页显示的记录数
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
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                userId: userId,
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
    arr.push('<button class="btn btn-primary checkGood" data-toggle="modal" data-target="#checkModal">审核</button> ');
    arr.push('<button class="btn btn-primary deleteGood">删除</button>');
    return arr.join('');
}

window.operateEvents = {
    'click .editGood': function (e, value, row, index) { //编辑

    },'click .checkGood': function (e, value, row, index) { //审核
        var id = row.id;
        $("#checkModal").on("show.bs.modal", function () {
            //当显示时加载数据
            $("#goodId").val(id);
        })
    },'click .deleteGood': function (e, value, row, index) { //删除

    }
}