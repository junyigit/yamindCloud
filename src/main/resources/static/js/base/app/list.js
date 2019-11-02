$(function () {
    console.log("3333333");



    lauguageData = returnLanguage();

    initialPage();
    getGrid();

    function initialPage() {
        $(window).resize(function() {
            $('#dataGrid').bootstrapTable('resetView', {
                height : $(window).height() - 56
            });
        });
    }

    function getGrid() {
        $('#dataGrid').bootstrapTableEx({
            url : '../../app/user/list?_' + $.now(),
            height : $(window).height() - 56,
            queryParams : function(params) {
                params.username = vm.keyword;
                return params;
            },
            columns : [ {
                checkbox : true
            }, {
                field : "userId",
                title : lauguageData.userId,
                width : "50px"
            }, {
                field : "mobile",
                title : lauguageData.mobile,
                width : "200px"
            }, {
                field : "photo",
                title : lauguageData.photo,
                width : "200px"
            }, {
                field : "nikeName",
                title : lauguageData.nikeName,
                width : "300px"
            }, {
                field : "sex",
                title : lauguageData.sex,
                width : "130px"
            },{
                field : "height",
                title : lauguageData.height,
                width : "130px"
            },{
                field : "birthday",
                title : lauguageData.birthday,
                width : "130px"
            }, {
                field : "status",
                title : lauguageData.status,
                width : "60px",
                formatter : function(value, row, index) {
                    if (value == '0') {
                        return '<span class="label label-danger">'+"111"+'</span>';
                    } else if (value == '1') {
                        return '<span class="label label-success">'+"111"+'</span>';
                    }
                }
            }, {
                field : "gmtCreate",
                title : lauguageData.gmtCreate,
                width : "200px"
            }, {
                field : "remark",
                title : lauguageData.remark
            } ]
        })
    }


});


function   initialPage() {
    $(window).resize(function() {
        $('#dataGrid').bootstrapTable('resetView', {
            height : $(window).height() - 56
        });
    });
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
                title : lauguageData.add,
                url : 'base/user/add.html?_' + $.now(),
                width : '620px',
                height : '524px',
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
                    url : 'base/user/edit.html?_' + $.now(),
                    width : '620px',
                    height : '524px',
                    scroll : true,
                    success : function(iframeId) {
                        top.frames[iframeId].vm.user.userId = ck[0].userId;
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
                    ids[idx] = item.userId;
                    console.log(item.userId);
                });
                $.RemoveForm({
                    url : '../../sys/user/remove?_' + $.now(),
                    param : ids,
                    success : function(data) {
                        vm.load();
                    }
                });
            }
        },
        disable : function() {
            var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
            if (checkedArray(ck)) {
                $.each(ck, function(idx, item) {
                    ids[idx] = item.userId;
                });
                $.ConfirmForm({
                    msg : '您是否要禁用所选账户吗？',
                    url : '../../sys/user/disable?_' + $.now(),
                    param : ids,
                    success : function(data) {
                        vm.load();
                    }
                });
            }
        },
        enable : function() {
            var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
            if (checkedArray(ck)) {
                $.each(ck, function(idx, item) {
                    ids[idx] = item.userId;
                });
                $.ConfirmForm({
                    msg : '您是否要启用所选账户吗？',
                    url : '../../sys/user/enable?_' + $.now(),
                    param : ids,
                    success : function(data) {
                        vm.load();
                    }
                });
            }
        },
        reset : function() {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedRow(ck)) {
                dialogOpen({
                    title : '重置密码',
                    url : 'base/user/reset.html?_' + $.now(),
                    width : '400px',
                    height : '220px',
                    success : function(iframeId) {
                        top.frames[iframeId].vm.user.userId = ck[0].userId;
                    },
                    yes : function(iframeId) {
                        top.frames[iframeId].vm.acceptClick();
                    },
                });
            }
        }
    }
})