$(function () {

    //初始化echart插件
    var myChart = echarts.init(document.getElementById('main'));
    var myChart1 = echarts.init(document.getElementById('main1'));

    //加载JSON内容
    $.getJSON("/json/language.json",function (data) {

        if (getCookie('lang') == "zh-cn" ||getCookie('lang') == "zh_cn"){
            lauguageData =data.zh;
        }else if (getCookie('lang') == "en-us" || getCookie('lang') == "en_us") {
            lauguageData =data.en;
        }

        console.log(lauguageData);
    })

    //在此处剪切 动态代码在html文件中

    var data = [];
    var data1 = [];
    var colArr = [];
    var colNum =11;

    /**
     *按钮事件绑定(设定秒数)
     * 10秒
     */
    $('#tenSecond').on('click',function(event){
        colNum=11;
        colArr.length=0;
        for (var i =1;i<colNum;i++){
            colArr.push(i);
        }
        myChart.setOption({
            xAxis: {
                data:colArr,
            }
        });
        //设置曲线图参数
        myChart1.setOption({
            xAxis: {
                data:colArr,
            }
        });
    });
    /**
     *按钮事件绑定(设定秒数)
     * 20秒
     */
    $('#towsecond').on('click',function(event){
        colNum=21;
        colArr.length=0;
        for (var i =1;i<colNum;i++){
            colArr.push(i);
        }

        myChart.setOption({
            xAxis: {
                data:colArr,
            }
        });
        //设置曲线图参数
        myChart1.setOption({
            xAxis: {
                data:colArr,
            }
        });

    });

    /**
     *按钮事件绑定(设定秒数)
     * 30秒
     */
    $('#threeSecond').on('click',function(event){
        colNum=31;
        colArr.length=0;
        for (var i =1;i<colNum;i++){
            colArr.push(i);
        }

        myChart.setOption({
            xAxis: {
                data:colArr,
            }
        });
        //设置曲线图参数
        myChart1.setOption({
            xAxis: {
                data:colArr,
            }
        });
    });

    for (var i =1;i<colNum;i++){
        colArr.push(i);
    }

 //   var option1 = lauguageData.realyl;
    var option1 = '1111';
    option = {
        title: {
            text:option1
        },tooltip: {
            trigger: 'axis',
            showDelay:100,
            formatter: function (params) {
                params = params[0];
                var date = params.name;
                return '<div><p>当前时间第：'+params.name+'秒</p></div>'+'<p>当前压力值 : '+ params.value+'</p>';
            },
            axisPointer: {
                animation: false
            }
        },
        xAxis: {
            type: 'category',
            data:colArr,
            position:"bottom",     //x 轴的位置。"top"，"bottom"，默认 grid 中的第一个 x 轴在 grid 的下方（'bottom'），第二个 x 轴视第一个 x 轴的位置放在另一侧
            offset:0,
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            max: 35,
            min: 0,
            scale: true,
            splitNumber:8,
            splitLine: {
                show: true
            },

        },
        series: [{
            name: '模拟数据',
            type: 'line',
            smooth:0.3,
            showSymbol: false,
            hoverAnimation: false,
            data: data
        }]
    };

    option1 = {
        title: {
            text: '实时流量'
        },tooltip: {
            trigger: 'axis',
            showDelay:100,
            formatter: function (params) {
                params = params[0];
                var date = params.name;
                return '<div><p>当前时间第：'+params.name+'秒</p></div>'+'<p>当前流量值 : '+ params.value+'</p>';
            },
            axisPointer: {
                animation: true
            }
        },

        xAxis: {
            type: 'category',
            data:colArr,
            position:"bottom",     //x 轴的位置。"top"，"bottom"，默认 grid 中的第一个 x 轴在 grid 的下方（'bottom'），第二个 x 轴视第一个 x 轴的位置放在另一侧
            //offset:0,
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            max: 200,
            min: -200,
            scale: true,
            splitNumber:8,
            splitLine: {
                show: true
            }

        },
        series: [{
            name: '模拟数据',
            type: 'line',
            smooth:0.3,
            showSymbol: false,
            hoverAnimation: false,
            data: data1
        }]
    };

    setInterval(function () {

        var serialId = getUrlParam(window.location.href,'serialId');
        console.info(serialId);
        $.ajax({
            type: "POST",
            url: "/sys/patient/getRealData",
            dataType: "json",
            contentType:"application/x-www-form-urlencoded;charset=UTF-8",
            data : {
                "serialId":serialId
            },//数据，这里使用的是Json格式进行传输
            success : function(result) {//返回数据根据结果进行相应的处理
                var table =$(".realdata");
                if ( result!=null ) {
                    var temp= result.data;
                    var d=eval('('+temp+')');
                    //alert(d.cureData.ll);
                    $(".realdata").empty(); //清空table（除了第一行以外）


                    var date1=new Date();
                    var second = date1.getSeconds();//秒
                    console.log("秒为"+second);

                    //填充数据
                    //data.shift();
                    if (data.length >colNum){
                        data.length=0;
                    }

                    if (data1.length >colNum){
                        data1.length=0;
                    }
                    data.push(d.cureData.yl);
                    data1.push(d.cureData.ll);

                    console.log(data);
                    myChart.setOption({
                        series: [{
                            data: data
                        }]
                    });
                    //设置曲线图参数
                    myChart1.setOption({
                        series: [{
                            data: data1
                        }]
                    });
                    switch (d.mode){
                        case "CPAP":
                            table.append(
                                '<table class="table" id="realtable1" cellspacing="50">'+
                                '<h4><b>实时数据</b></h4>'+
                                '<tr> ' +
                                '<td>' + "实时压力" + '</td>' +
                                '<td>' + d.cureData.yl + '</td>' +
                                '<td>' + "实时流量" + '</td>' +
                                '<td>' + d.cureData.ll + '</td>' +
                                '</tr>' +
                                '</table>'+
                                '<table class="table" id="realtable2" cellspacing="30" >'+
                                '<h4><b>设备参数</b></h4>'+

                                returnSoft(d)+

                                '<tr> ' +
                                '<td>' + "呼气释放" + '</td>' +
                                '<td>' + d.cureData.hqsf + '</td>' +
                                '<td>' + "治疗压力" + '</td>' +
                                '<td>' + d.cureData.zlyl+ '</td>' +
                                '</tr>'+
                                '</table>'
                            )
                            break;
                        case "APAP":

                            table.append(
                                '<table class="table" id="realtable1" cellspacing="50">'+
                                '<h4><b>实时数据</b></h4>'+
                                '<tr> ' +
                                '<td>' + "实时压力" + '</td>' +
                                '<td>' + d.cureData.yl + '</td>' +
                                '<td>' + "实时流量" + '</td>' +
                                '<td>' + d.cureData.ll + '</td>' +
                                '</tr>' +
                                '</table>'+
                                '<table class="table" id="realtable2" cellspacing="30" >'+
                                '<h4><b>设备参数</b></h4>'+

                                returnSoft(d)+

                                '<tr> ' +
                                '<td>' + "呼气释放" + '</td>' +
                                '<td>' + d.cureData.hqsf + '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + "最大压力" + '</td>' +
                                '<td>' + d.cureData.zdyl+ '</td>' +
                                '<td>' + "最小压力" + '</td>' +
                                '<td>' + d.cureData.zxyl+ '</td>' +
                                '</tr>'+
                                '</table>'

                            )
                            break;

                        case "S":

                            table.append(
                                '<table class="table" id="realtable1" cellspacing="50">'+
                                '<h4><b>实时数据</b></h4>'+

                                '<tr> ' +
                                '<td>' + "实时压力" + '</td>' +
                                '<td>' + d.cureData.yl + '</td>' +
                                '<td>' + "实时流量" + '</td>' +
                                '<td>' + d.cureData.ll + '</td>' +
                                '</tr>' +

                                '<tr> ' +
                                '<td>' + "吸呼比" + '</td>' +
                                '<td>' + d.cureData.xhb + '</td>' +
                                '<td>' + "呼吸频率" + '</td>' +
                                '<td>' + d.cureData.hxpl + '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + "潮气量" + '</td>' +
                                '<td>' + d.cureData.cql + '</td>' +
                                '<td>' + "分钟通气量" + '</td>' +
                                '<td>' + d.cureData.fztql + '</td>' +
                                '</tr>'+


                                '</table>'+
                                '<table class="table" id="realtable2" cellspacing="30" >'+
                                '<h4><b>设备参数</b></h4>'+

                                returnSoft(d)+

                                '<tr> ' +
                                '<td>' + "吸气压力" + '</td>' +
                                '<td>' + d.cureData.xqyl+ '</td>' +
                                '<td>' + "呼气压力" + '</td>' +
                                '<td>' + d.cureData.hqyl+ '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + "吸气灵敏度" + '</td>' +
                                '<td>' + d.cureData.xqlmd+ '</td>' +
                                '<td>' + "呼气灵敏度" + '</td>' +
                                '<td>' + d.cureData.hqlmd+ '</td>' +
                                '</tr>'+


                                '<tr> ' +
                                '<td>' + "压力上升坡度" + '</td>' +
                                '<td>' + d.cureData.ylsspd+ '</td>' +
                                '<td>' + "压力下降坡度" + '</td>' +
                                '<td>' + d.cureData.ylxjpd+ '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + "AVAPS" + '</td>' +
                                '<td>' + d.cureData.avaps+ '</td>' +
                                '<td>' + "目标潮气量" + '</td>' +
                                '<td>' + d.cureData.mbcql+ '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + "最大吸气压力" + '</td>' +
                                '<td>' + d.cureData.zdxqyl+ '</td>' +
                                '<td>' + "最小吸气压力" + '</td>' +
                                '<td>' + d.cureData.zxxqyl+ '</td>' +
                                '</tr>'+
                                '</table>'
                            )
                            break;
                        case "S-Auto":
                            table.append(
                                '<table class="table" id="realtable1" cellspacing="50">'+
                                '<h4><b>实时数据</b></h4>'+

                                '<tr> ' +
                                '<td>' + "实时压力" + '</td>' +
                                '<td>' + d.cureData.yl + '</td>' +
                                '<td>' + "实时流量" + '</td>' +
                                '<td>' + d.cureData.ll + '</td>' +
                                '</tr>' +

                                '<tr> ' +
                                '<td>' + "吸呼比" + '</td>' +
                                '<td>' + d.cureData.xhb + '</td>' +
                                '<td>' + "呼吸频率" + '</td>' +
                                '<td>' + d.cureData.hxpl + '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + "潮气量" + '</td>' +
                                '<td>' + d.cureData.cql + '</td>' +
                                '<td>' + "分钟通气量" + '</td>' +
                                '<td>' + d.cureData.fztql + '</td>' +
                                '</tr>'+

                                '</table>'+
                                '<table class="table" id="realtable2" cellspacing="30" >'+
                                '<h4><b>设备参数</b></h4>'+

                                returnSoft(d)+

                                '<tr> ' +
                                '<td>' + "最大吸气压力" + '</td>' +
                                '<td>' + d.cureData.zdxqyl+ '</td>' +
                                '</tr>'+
                                '<tr> ' +
                                '<td>' + "吸气压力" + '</td>' +
                                '<td>' + d.cureData.xqyl+ '</td>' +
                                '<td>' + "呼气压力" + '</td>' +
                                '<td>' + d.cureData.hqyl+ '</td>' +
                                '</tr>'+
                                '<tr> ' +
                                '<td>' + "吸气灵敏度" + '</td>' +
                                '<td>' + d.cureData.xqlmd+ '</td>' +
                                '<td>' + "呼气灵敏度" + '</td>' +
                                '<td>' + d.cureData.hqlmd+ '</td>' +
                                '</tr>'+
                                '<tr> ' +
                                '<td>' + "压力上升坡度" + '</td>' +
                                '<td>' + d.cureData.ylsspd+ '</td>' +
                                '<td>' + "压力下降坡度" + '</td>' +
                                '<td>' + d.cureData.ylxjpd+ '</td>' +
                                '</tr>'+
                                '</table>'
                            )
                            break;
                        case "T":
                            table.append(returnStData(d))
                            break;
                        case "S/T":
                            table.append(returnStData(d))
                            break;
                    }
                } else {
                    console.log("fails");
                    dialogMsg("没有获取到实时数据,ERROR");
                }
            }
        });
    }, 1000);

// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart1.setOption(option1);

    window.onresize = function(){
        myChart.resize();
        myChart1.resize();
    };

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

function tableUpdate() {
    $(".realdata").each()
}

function returnSoft(d) {

    return softInfo =   '<tr> ' +
        '<td>' + lauguageData.serial + '</td>' +
        '<td>' + d.serialId + '</td>' +
        '<td>' + lauguageData.type + '</td>' +
        '<td>' + d.bootModel + '</td>' +
        '</tr>'+

        '<tr> ' +
        '<td>' + lauguageData.softVersion + '</td>' +
        '<td>' + d.softVersion+ '</td>' +
        '<td>' + lauguageData.time + '</td>' +
        '<td>' + d.serviceTime + '</td>' +
        '</tr>'+
        '<tr> ' +
        '<td>' + lauguageData.mode + '</td>' +
        '<td>' + d.mode + '</td>' +
        '</tr> ';

}


function returnStData(d) {


    return stmodel =
        '<table class="table" id="realtable1" cellspacing="50">' +
        '<h4><b>实时数据</b></h4>' +

        '<tr> ' +
        '<td>' + "实时压力" + '</td>' +
        '<td>' + d.cureData.yl + '</td>' +
        '<td>' + "实时流量" + '</td>' +
        '<td>' + d.cureData.ll + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + "吸呼比" + '</td>' +
        '<td>' + d.cureData.xhb + '</td>' +
        '<td>' + "呼吸频率" + '</td>' +
        '<td>' + d.cureData.hxpl + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + "潮气量" + '</td>' +
        '<td>' + d.cureData.cql + '</td>' +
        '<td>' + "分钟通气量" + '</td>' +
        '<td>' + d.cureData.fztql + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + "漏气量" + '</td>' +
        '<td>' + d.cureData.lql + '</td>' +
        '</tr>' +


        '</table>' +
        '<table class="table" id="realtable2" cellspacing="30" >' +
        '<h4><b>设备参数</b></h4>' +

        returnSoft(d) +
        '<tr> ' +
        '<td>' + "吸气压力" + '</td>' +
        '<td>' + d.cureData.xqyl + '</td>' +
        '<td>' + "呼气压力" + '</td>' +
        '<td>' + d.cureData.hqyl + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + "呼吸频率" + '</td>' +
        '<td>' + d.cureData.hxpl + '</td>' +
        '<td>' + "吸气时间" + '</td>' +
        '<td>' + d.cureData.xqsj + '</td>' +
        '</tr>' +


        '<tr> ' +
        '<td>' + "吸气灵敏度" + '</td>' +
        '<td>' + d.cureData.xqlmd + '</td>' +
        '<td>' + "呼气灵敏度" + '</td>' +
        '<td>' + d.cureData.hqlmd + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + "最大吸气压力" + '</td>' +
        '<td>' + d.cureData.zdxqyl + '</td>' +
        '<td>' + "最小吸气压力" + '</td>' +
        '<td>' + d.cureData.zxxqyl + '</td>' +
        '</tr>' +


        '<tr> ' +
        '<td>' + "压力上升坡度" + '</td>' +
        '<td>' + d.cureData.ylsspd + '</td>' +
        '<td>' + "压力下降坡度" + '</td>' +
        '<td>' + d.cureData.ylxjpd + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + "AVAPS" + '</td>' +
        '<td>' + d.cureData.avaps + '</td>' +
        '<td>' + "目标潮气量" + '</td>' +
        '<td>' + d.cureData.mbcql + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + "最大吸气压力" + '</td>' +
        '<td>' + d.cureData.zdxqyl + '</td>' +
        '<td>' + "最小吸气压力" + '</td>' +
        '<td>' + d.cureData.zxxqyl + '</td>' +
        '</tr>' +
        '</table>';

}
/**
 * 获取cookie值
 * @param cookie_name
 * @returns {string}
 */
function getCookie(cookie_name) {
    var allcookies = document.cookie;
    //索引长度，开始索引的位置
    var cookie_pos = allcookies.indexOf(cookie_name);

    // 如果找到了索引，就代表cookie存在,否则不存在
    if (cookie_pos != -1) {
        // 把cookie_pos放在值的开始，只要给值加1即可
        //计算取cookie值得开始索引，加的1为“=”
        cookie_pos = cookie_pos + cookie_name.length + 1;
        //计算取cookie值得结束索引
        var cookie_end = allcookies.indexOf(";", cookie_pos);

        if (cookie_end == -1) {
            cookie_end = allcookies.length;

        }
        //得到想要的cookie的值
        var value = unescape(allcookies.substring(cookie_pos, cookie_end));
    }
    return value;
}