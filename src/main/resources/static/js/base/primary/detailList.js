$(function () {

    var userId = getUrlParam(window.location.href,'userId');

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
        url : '../../sys/primarySieve/detailList?_' + $.now(),
        height : $(window).height() - 56,
        queryParams : function(params) {
            params.userId = userId;
            return params;
        },
        columns : [ {
            checkbox : true
        }, {
            field : "id",
            title : "ID",
            width : "50px"
        },{
            field : "time",
            title : "日期",
            width : "50px"
        },
            {
                field : "operation",
                title : "操作",
                width : "150px",
                events: "operateEvents",
                formatter: function(value, row, index) {
                    return '<button id="historyBtn"  type="button" style="margin-left: 5px" class="btn btn-primary  btn-xs">查看详细数据</button>';

                }
            }]
    })


    /**
     * 获取参数值
     * @param url
     * @param name
     * @returns {*}
     */
    function getUrlParam(url,name) {
        var pattern = new RegExp("[?&]"+name+"\=([^&]+)", "g");
        var matcher = pattern.exec(url);
        var items = null;
        if(null != matcher){
            try{
                items = decodeURIComponent(decodeURIComponent(matcher[1]));
            }catch(e){
                try{
                    items = decodeURIComponent(matcher[1]);
                }catch(e){
                    items = matcher[1];
                }
            }
        }
        return items;
    }
})