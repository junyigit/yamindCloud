/**
 * 用户管理js
 */

$(function() {
    initialPage();
   // getGrid();
});

function initialPage() {
    $(window).resize(function() {
        $('#dataGrid').bootstrapTable('resetView', {
            height : $(window).height() - 56
        });
    });
}

function getGrid() {
    $('#dataGrid').bootstrapTableEx({
        url : '../../sys/patient/list?_' + $.now(),
        height : $(window).height() - 56,
        queryParams : function(params) {
            params.username = vm.keyword;
            return params;
        },
        columns : [ {
            checkbox : true
        }, {
            field : "id",
            title : "编号",
            width : "50px"
        },{
            field : "name",
            title : "姓名",
            width : "50px"
        }, {
            field : "age",
            title : "年龄",
            width : "50px"
        },{
            field : "email",
            title : "民族",
            width : "50px"
        }, {
            field : "height",
            title : "身高",
            width : "40px"
        }, {
            field : "weight",
            title : "体重",
            width : "40px",
        }, {
            field : "bmi",
            title : "BMI",
            width : "35px"
        }, {
            field : "pbw",
            title : "PBW",
            width : "35px"
        },
        {
            field : "serialNum",
            title : "设备序列号",
            width : "40px"
        },
        {
            field : "medicalRecord",
            title : "病历号",
            width : "40px"
        },
        {
            field : "diagnose",
            title : "诊断",
            width : "60px"
        },
        {
            field : "doctor",
            title : "负责医生",
            width : "60px"
        },
        {
            field : "yblx",
            title : "医保类型",
            width : "30px"
        },
        {
            field : "homeAddres",
            title : "家庭住址",
            width : "100px"

        },
        {
            field : "operation",
            title : "操作",
            width : "150px",
            events: "operateEvents",
            formatter: function(value, row, index) {
                    return ' <button id="selectBtn" type="button" class="btn btn-success  btn-xs">查看实时数据</button>'+
                         '<button id="historyBtn"  type="button" style="margin-left: 5px" class="btn btn-primary  btn-xs">历史数据</button>';

            }
        }]
    })
}



window.operateEvents = {

    "click #selectBtn": function (e, value, row, index) {


        $.ajax({
            type: "POST",
            url: "/sys/patient/isRealData",
            dataType: "json",
            data: {
                "serialId": row.serialNum
            },//数据，这里使用的是Json格式进行传输
            success: function (result) {//返回数据根据结果进行相应的处理
                if (result.code < 0) {
                    dialogMsg(result.msg);
                } else {
                    url = "realData.html?serialId=" + row.serialNum;//此处拼接内容
                    window.location.href = url;
                }
            },
            error: function () {

            }
        })
    },


    "click #historyBtn": function (e, value, row, index) {


        url = "historyData.html?serialId="+row.serialNum;//此处拼接内容
        window.location.href = url;


        // $.ajax({
        //     type: "POST",
        //     url: "/sys/patient/isRealData",
        //     dataType: "json",
        //     data: {
        //         "serialId": row.serialNum
        //     },//数据，这里使用的是Json格式进行传输
        //     success: function (result) {//返回数据根据结果进行相应的处理
        //         if (result.code < 0) {
        //             dialogMsg(result.msg);
        //         } else {
        //             console.log(row.name);
        //             url = "historyData.html?serialId="+row.serialNum;//此处拼接内容
        //             window.location.href = url;
        //         }
        //     },
        //     error: function () {
        //
        //     }
        // })

    }



}


