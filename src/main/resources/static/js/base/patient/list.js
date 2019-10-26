/**
 * 用户管理js
 */

$(function() {
    initialPage();
});

function initialPage() {
    $(window).resize(function() {
        $('#dataGrid').bootstrapTable('resetView', {
            height : $(window).height() - 56
        });
    });
}




window.operateEvents = {

    //TODO 待修改 修改实时数据
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
    }



}


