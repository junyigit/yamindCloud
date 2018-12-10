

var vm = new Vue({
    el:'#dyLTE',
    data: {
        pat: {
        }
    },
    methods : {
        setForm: function() {
            $.SetForm({
                url: '../../sys/patient/info?_' + $.now(),
                param: vm.pat.id,
                success: function(data) {
                    vm.pat = data;
                }
            });
        },
        acceptClick: function() {
            if (!$('#form').Validform()) {
                return false;
            }
            $.ConfirmForm({
                url: '../../sys/patient/update?_' + $.now(),
                param: vm.pat,
                success: function(data) {
                    $.currentIframe().vm.load();
                }
            });
        }
    }
})