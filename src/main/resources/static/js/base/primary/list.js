$(function () {



    var vm = new Vue({
        el : '#dyLTE',
        data : {
            keyword : null
        },
        methods : {
            load : function() {
                $('#dataGrid').bootstrapTable('refresh');
            }
        }
    })

    $('#dataGrid').bootstrapTableEx({
        url : '../../sys/primarySieve/list?_' + $.now(),
        height : $(window).height() - 56,
        queryParams : function(params) {
            return params;
        },
        columns : [ {
            checkbox : true
        }, {
            field : "id",
            title : "ID",
            width : "50px"
        },{
            field : "status",
            title : "状态",
            width : "50px",
            formatter: function (value, row, index) {
                return changeStatus(value);
            }
        },
            {
                field : "userNickName",
                title : "用户名",
                width : "100px"

            },
            {
                field : "mobile",
                title : "手机号",
                width : "100px"

            },{
                field : "lastTime",
                title : "上次登陆时间",
                width : "100px",
                //获取日期列的值进行转换
                formatter: function (value, row, index) {
                    return changeDateFormat(value)
                }
            },
            {
                field : "operation",
                title : "操作",
                width : "150px",
                events: "operateEvents",
                formatter: function(value, row, index) {
                    return '<button id="findDetailList"  type="button" style="margin-left: 5px" class="btn btn-primary  btn-xs">查看</button>';

                }
            }]
    })


    window.operateEvents = {

        "click #findDetailList": function(e, value, row, index) {
            url = "detailList.html?userId=" + row.userId;//此处拼接内容
            window.location.href = url;
        }
    }


    /**
     * 时间戳转换为日期
     * @param value
     * @returns {string}
     */
    function changeDateFormat(value){
        console.log(value);
        var date = new Date(value * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = date.getMinutes() + ':';
        var s = date.getSeconds();
        return Y+M+D+h+m+s;
    }


    /**
     * 切换状态
     * @param value
     */
    function changeStatus(value) {

        if (value ==1){
            return "初筛仪";
        }
        
    }

})