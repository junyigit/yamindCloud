/**
 * 新增-角色管理js
 */
var vm = new Vue({
	el:'#dyLTE',
	data: {
		role: {
			orgId: 0,
			orgName: null
		}
	},
	methods : {
		orgTree: function() {
			dialogOpen({
				id: 'layerOrgTree',
				title: '选择机构',
		        url: 'base/role/org.html?_' + $.now(),
		        scroll : true,
		        width: "350px",
		        height: "500px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../sys/role/save?_' + $.now(),
		    	param: vm.role,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
