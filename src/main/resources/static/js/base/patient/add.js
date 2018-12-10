/**
 * 新增-定时任务js
 */
var vm = new Vue({
    el:'#dyLTE',
    data: {
        pat: {

        }
    },
    methods : {
        acceptClick: function() {
            if (!$('#form').Validform()) {
                return false;
            }
            $.SaveForm({
                url: '../../sys/patient/save?_' + $.now(),
                param: vm.pat,
                success: function(data) {
                    $.currentIframe().vm.load();
                }
            });
        }
    }
})
