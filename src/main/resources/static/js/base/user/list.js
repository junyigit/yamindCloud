/**
 * 用户管理js
 */

$(function() {
	initialPage();
	getGrid();

    $.ajax({
        url: "/json/language.json",//json文件位置
        type: "GET",//请求方式为get
        dataType: "json", //返回数据格式为json
        async:false,
        success: function(data) {//请求成功完成后要执行的方法
            if (getCookie('lang') == "zh-cn" || getCookie('lang') == "zh_cn") {
                lauguageData = data.zh;
                console.log("选择了中文==="+lauguageData);
            } else if (getCookie('lang') == "en-us" || getCookie('lang') == "en_us") {
                lauguageData = data.en;
                console.log("选择了英文==="+lauguageData);

            }
        }
    })
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
				title : lauguageData.userId,
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