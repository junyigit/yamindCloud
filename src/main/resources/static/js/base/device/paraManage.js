/**
 * 用户管理js
 */

$(function() {
    initialPage();
 //   getGrid();

});

function initialPage() {
    $(window).resize(function() {
        $('#dataGrid').bootstrapTable('resetView', {
            height : $(window).height() - 56
        });
    });
}



window.operateEvents = {


    //设置参数
    "click #setPara": function (e, value, row, index) {
        $.ajax({
            type: "POST",
            url: "/sys/paraSet/getParaDetails",
            dataType: "json",
            data: {
                "serialId": row.serialId
            },//数据，这里使用的是Json格式进行传输
            success: function (result) {//返回数据根据结果进行相应的处理
                if (result.code < 0) {
                    console.log("errorrrr");
                    dialogMsg(result.msg);
                } else {
                    console.log("success");
                    url = "paraSet.html?serialId=" + row.serialId;//此处拼接内容
                    window.location.href = url;
                }
            },
            error: function () {

            }
        })
    },


}

var vm = new Vue({
    el : '#dyLTE',
    data : {
        keyword : null
    },
    methods : {
        load : function() {
            $('#dataGrid').bootstrapTable('refresh');
        },
        save : function() {
            dialogOpen({
                title : '新增用户',
                url : 'base/patient/add.html?_' + $.now(),
                width : '580px',
                height : '720px',
                scroll : true,
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        },
        edit : function() {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedRow(ck)) {
                dialogOpen({
                    title : '编辑用户',
                    url : 'base/patient/edit.html?_' + $.now(),
                    width : '580px',
                    height : '720px',
                    scroll : true,
                    success : function(iframeId) {
                        top.frames[iframeId].vm.pat.id = ck[0].id;
                        top.frames[iframeId].vm.setForm();
                    },
                    yes : function(iframeId) {
                        top.frames[iframeId].vm.acceptClick();
                    },
                });
            }
        },
        remove : function() {
            var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
            if (checkedArray(ck)) {
                $.each(ck, function(idx, item) {
                    ids[idx] = item.deviceNo;
                });
                $.RemoveForm({
                    url : '../../sys/patient/remove?_' + $.now(),
                    param : ids,
                    success : function(data) {
                        vm.load();
                    }
                });
            }
        }

    }
})