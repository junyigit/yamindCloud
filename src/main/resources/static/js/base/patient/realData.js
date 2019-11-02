$(function () {

    //初始化echart插件
    var myChart = echarts.init(document.getElementById('main'));
    var myChart1 = echarts.init(document.getElementById('main1'));

    //定义全局变量
    lastTime =0;

    //多语言
    lauguageData = returnLanguage();


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
        colNum=10*5+1;
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
        colNum=20*5+1;
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
        colNum=30*5+1;
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


    option = {
        title: {
            text:lauguageData.realyl
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
            text: lauguageData.realll
        }
        ,tooltip: {
            trigger: 'axis',
            showDelay:100,
            formatter: function (params) {
                params = params[0];
                var date = params.name;
                return '<div><p>当前时间第：'+params.name+'秒</p></div>'+'<p>当前流量值值 : '+ params.value[1]+'</p>';
            },
            axisPointer: {
                animation: false
            }
        },
        xAxis: {
            type: 'category',
            data:colArr,
            position:"bottom",     //x 轴的位置。"top"，"bottom"，默认 grid 中的第一个 x 轴在 grid 的下方（'bottom'），第二个 x 轴视第一个 x 轴的位置放在另一侧
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



    /**
     * 定时器 更新Table及
     */
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

                console.log("接受数据为:"+result.dataMsg);
                //获取HTML网页TABLE的句柄
                var table =$(".realdata");

                if ( result!=null ) {



                    //数据部分
                    var dataMsg= result.dataMsg;
                    var dataArr = dataMsg.split(",");
                    //设置信息部分
                    var paraMsg= result.paraMsg;
                    if (paraMsg !=null) {
                        var paraArr = paraMsg.split(",");
                    }


                    //清空数据
                    $(".realdata").empty(); //清空table（除了第一行以外）

                    var date1=new Date();
                    var second = date1.getSeconds();//秒
                    //console.log("秒为"+second);

                    //填充数据
                    if (data.length >colNum){
                        data.length=0;
                    }

                    if (data1.length >colNum){
                        data1.length=0;
                    }



                    //时间转换  用于判断设备是否在线
                    var localTime=Date.parse(new Date())/1000;


                    console.log("lastTime :"+lastTime);

                    console.log("dataArr[2] :"+dataArr[2]);


                    if (lastTime !=dataArr[2] && lastTime !=0){

                        console.log("正在发送");

                        //压力数组
                        data.push(dataArr[9],dataArr[10],dataArr[11],dataArr[12],dataArr[13]);
                        //流量数组
                        data1.push(dataArr[3],dataArr[4],dataArr[5],dataArr[6],dataArr[7]);


                        console.log("压力值:"+data);

                        console.log("流量为:"+dataArr[3],dataArr[4],dataArr[5],dataArr[6],dataArr[7]);


                        myChart1.setOption({
                            series: [{
                                data: data1
                            }]
                        });
                        //设置曲线图的数据源
                        myChart.setOption({
                            series: [{
                                data: data
                            }]
                        });
                    }else{
                        console.log("设备断线");
                    }




                    //把当前时间戳保存 用于下次对比
                    lastTime=dataArr[2];
                    switch (paraArr[4]){
                        case "CPAP":
                            console.log("模式为:"+paraArr[19]);
                            table.append(
                                '<table class="table" id="realtable1" cellspacing="50">'+
                                '<h4><b>'+lauguageData.realData+'</b></h4>' +
                                '<tr> ' +
                                '<td>' + lauguageData.realyl + '</td>' +
                                '<td>' + dataArr[9] + '</td>' +
                                '<td>' + lauguageData.realll + '</td>' +
                                '<td>' + dataArr[3] + '</td>' +
                                '</tr>' +
                                '</table>'+
                                '<table class="table" id="realtable2" cellspacing="30" >'+
                                '<h4><b>'+ lauguageData.devicePara+ '</b></h4>'+

                                returnSoft(paraArr)+

                                '<tr> ' +
                                '<td>' + lauguageData.inRelese + '</td>' +
                                '<td>' + paraArr[13] + '</td>' +
                                '<td>' + lauguageData.treatmentPres + '</td>' +
                                '<td>' + paraArr[5]+ '</td>' +
                                '</tr>'+
                                '</table>'
                            )
                            break;
                        case "APAP":
                            console.log("模式为:"+paraArr[4]);
                            table.append(
                                '<table class="table" id="realtable1" cellspacing="50">'+
                                '<h4><b>'+lauguageData.realData+'</b></h4>' +
                                '<tr> ' +
                                '<td>' + lauguageData.realyl + '</td>' +
                                '<td>' + dataArr[9] + '</td>' +
                                '<td>' + lauguageData.realll + '</td>' +
                                '<td>' + dataArr[3] + '</td>' +
                                '</tr>' +
                                '</table>'+
                                '<table class="table" id="realtable2" cellspacing="30" >'+
                                '<h4><b>'+ lauguageData.devicePara+ '</b></h4>'+

                                returnSoft(paraArr)+

                                '<tr> ' +
                                '<td>' + lauguageData.hqsf + '</td>' +
                                '<td>' + paraArr[17] + '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + lauguageData.maxKpa + '</td>' +
                                '<td>' + paraArr[8]+ '</td>' +
                                '<td>' + lauguageData.minKpa+ '</td>' +
                                '<td>' + paraArr[9]+ '</td>' +
                                '</tr>'+
                                '</table>'

                            )
                            break;

                        case "S":
                            console.log("模式为:"+paraArr[4]);
                            table.append(
                                '<table class="table" id="realtable1" cellspacing="50">'+
                                '<h4><b>'+lauguageData.realData+'</b></h4>' +
                                '<tr> ' +
                                '<td>' + lauguageData.realyl + '</td>' +
                                '<td>' + dataArr[9] + '</td>' +
                                '<td>' + lauguageData.realll + '</td>' +
                                '<td>' + dataArr[3] + '</td>' +
                                '</tr>' +

                                '<tr> ' +
                                '<td>' + lauguageData.xhb + '</td>' +
                                '<td>' + +'1:'+dataArr[19] + '</td>' +
                                '<td>' + lauguageData.hxpl + '</td>' +
                                '<td>' + dataArr[16] + '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + lauguageData.cql + '</td>' +
                                '<td>' + dataArr[14] + '</td>' +
                                '<td>' + lauguageData.fztql + '</td>' +
                                '<td>' + dataArr[15] + '</td>' +
                                '</tr>'+

                                '</table>'+
                                '<table class="table" id="realtable2" cellspacing="30" >'+
                                '<h4><b>'+ lauguageData.devicePara+ '</b></h4>'+

                                returnSoft(paraArr)+

                                '<tr> ' +
                                '<td>' + lauguageData.xqyl + '</td>' +
                                '<td>' + paraArr[12] + '</td>' +
                                '<td>' + lauguageData.hqyl + '</td>' +
                                '<td>' + paraArr[13]  + '</td>' +
                                '</tr>' +

                                '<tr> ' +
                                '<td>' + lauguageData.xqlmd + '</td>' +
                                '<td>' + paraArr[18]  + '</td>' +
                                '<td>' + lauguageData.hqlmd + '</td>' +
                                '<td>' + paraArr[19]  + '</td>' +
                                '</tr>' +


                                '<tr> ' +
                                '<td>' + lauguageData.ylsspd + '</td>' +
                                '<td>' + paraArr[20]  + '</td>' +
                                '<td>' + lauguageData.ylxjpd + '</td>' +
                                '<td>' + paraArr[21]  + '</td>' +
                                '</tr>' +

                                '<tr> ' +
                                '<td>' + "AVAPS" + '</td>' +
                                '<td>' + paraArr[22] + '</td>' +
                                '<td>' + lauguageData.mbcql + '</td>' +
                                '<td>' + paraArr[14] + '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + lauguageData.zdxqyl+ '</td>' +
                                '<td>' + paraArr[10]  + '</td>' +
                                '<td>' + lauguageData.zxxqyl + '</td>' +
                                '<td>' + paraArr[11]  + '</td>' +
                                '</tr>' +
                                '</table>'
                            )
                            break;
                        case "S-Auto":
                            table.append(
                                '<table class="table" id="realtable1" cellspacing="50">'+
                                '<h4><b>'+lauguageData.realData+'</b></h4>' +

                                '<tr> ' +
                                '<td>' + lauguageData.realyl + '</td>' +
                                '<td>' + dataArr[9] + '</td>' +
                                '<td>' + lauguageData.realll + '</td>' +
                                '<td>' + dataArr[3] + '</td>' +
                                '</tr>' +

                                '<tr> ' +
                                '<td>' + lauguageData.xhb + '</td>' +
                                '<td>' + +'1:'+dataArr[19] + '</td>' +
                                '<td>' + lauguageData.hxpl + '</td>' +
                                '<td>' + dataArr[16] + '</td>' +
                                '</tr>'+

                                '<tr> ' +
                                '<td>' + lauguageData.cql + '</td>' +
                                '<td>' + dataArr[14] + '</td>' +
                                '<td>' + lauguageData.fztql + '</td>' +
                                '<td>' + dataArr[15] + '</td>' +
                                '</tr>'+

                                '</table>'+
                                '<table class="table" id="realtable2" cellspacing="30" >'+
                                '<h4><b>'+ lauguageData.devicePara+ '</b></h4>'+

                                returnSoft(paraArr)+

                                '<tr> ' +
                                '<td>' + lauguageData.zdxqyl + '</td>' +
                                '<td>' + paraArr[10]+ '</td>' +
                                '</tr>'+
                                '<tr> ' +
                                '<td>' + lauguageData.xqyl + '</td>' +
                                '<td>' + paraArr[12] + '</td>' +
                                '<td>' + lauguageData.hqyl + '</td>' +
                                '<td>' + paraArr[13]  + '</td>' +
                                '</tr>' +

                                '<tr> ' +
                                '<td>' + lauguageData.xqlmd + '</td>' +
                                '<td>' + paraArr[18]  + '</td>' +
                                '<td>' + lauguageData.hqlmd + '</td>' +
                                '<td>' + paraArr[19]  + '</td>' +
                                '</tr>' +

                                '<tr> ' +
                                '<td>' + lauguageData.ylsspd + '</td>' +
                                '<td>' + paraArr[20]  + '</td>' +
                                '<td>' + lauguageData.ylxjpd + '</td>' +
                                '<td>' + paraArr[21]  + '</td>' +
                                '</tr>' +
                                '</table>'
                            )
                            break;
                        case "T":
                            table.append(returnStData(paraArr,dataArr))
                            break;
                        case "S/T":
                            table.append(returnStData(paraArr,dataArr))
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


    myChart.group = 'group1';
    myChart1.group = 'group1';
    echarts.connect('group1');

    window.onresize = function(){
        myChart.resize();
        myChart1.resize();
    };



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

function returnSoft(paraArr) {

    return softInfo =   '<tr> ' +
        '<td>' + lauguageData.serial + '</td>' +
        '<td>' + paraArr[1]+ '</td>' +
        '<td>' + lauguageData.type + '</td>' +
        '<td>' + paraArr[3] + '</td>' +
        '</tr>'+

        '<tr> ' +
        '<td>' + lauguageData.softVersion + '</td>' +
        '<td>' + paraArr[23]+ '</td>' +
        // '<td>' + lauguageData.time + '</td>' +
        // '<td>' + paraArr[2] + '</td>' +
        '</tr>'+
        '<tr> ' +
        '<td>' + lauguageData.mode + '</td>' +
        '<td>' + paraArr[4] + '</td>' +
        '</tr> ';

}


function returnStData(paraArr,dataArr) {

    return stmodel =
        '<table class="table" id="realtable1" cellspacing="50">' +
        '<h4><b>'+lauguageData.realData+'</b></h4>' +

        '<tr> ' +
        '<td>' + lauguageData.realyl + '</td>' +
        '<td>' + dataArr[9] + '</td>' +
        '<td>' + lauguageData.realll + '</td>' +
        '<td>' + dataArr[3] + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + lauguageData.xhb + '</td>' +
        '<td>' +'1:'+ dataArr[19] + '</td>' +
        '<td>' + lauguageData.hxpl + '</td>' +
        '<td>' + dataArr[16] + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + lauguageData.cql + '</td>' +
        '<td>' + dataArr[14] + '</td>' +
        '<td>' + lauguageData.fztql + '</td>' +
        '<td>' + dataArr[15] + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + lauguageData.lql + '</td>' +
        '<td>' + dataArr[8] + '</td>' +
        '</tr>' +


        '</table>' +
        '<table class="table" id="realtable2" cellspacing="30" >' +
        '<h4><b>'+ lauguageData.devicePara+ '</b></h4>'+

        returnSoft(paraArr) +
        '<tr> ' +
        '<td>' + lauguageData.xqyl + '</td>' +
        '<td>' + paraArr[12] + '</td>' +
        '<td>' + lauguageData.hqyl + '</td>' +
        '<td>' + paraArr[13]  + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + lauguageData.hxpl + '</td>' +
        '<td>' + paraArr[15] + '</td>' +
        '<td>' + lauguageData.xqsj + '</td>' +
        '<td>' + paraArr[16] + '</td>' +
        '</tr>' +


        '<tr> ' +
        '<td>' + lauguageData.xqlmd + '</td>' +
        '<td>' + paraArr[18] + '</td>' +
        '<td>' + lauguageData.hqlmd + '</td>' +
        '<td>' + paraArr[19] + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + lauguageData.zdxqyl+ '</td>' +
        '<td>' + paraArr[10]  + '</td>' +
        '<td>' + lauguageData.zxxqyl + '</td>' +
        '<td>' + paraArr[11]  + '</td>' +
        '</tr>' +


        '<tr> ' +
        '<td>' + lauguageData.ylsspd + '</td>' +
        '<td>' + paraArr[20]  + '</td>' +
        '<td>' + lauguageData.ylxjpd + '</td>' +
        '<td>' + paraArr[21]  + '</td>' +
        '</tr>' +

        '<tr> ' +
        '<td>' + "AVAPS" + '</td>' +
        '<td>' + paraArr[22] + '</td>' +
        '<td>' + lauguageData.mbcql + '</td>' +
        '<td>' + paraArr[14] + '</td>' +
        '</tr>' +

        // '<tr> ' +
        // '<td>' + "最大吸气压力" + '</td>' +
        // '<td>' + d.cureData.zdxqyl + '</td>' +
        // '<td>' + "最小吸气压力" + '</td>' +
        // '<td>' + d.cureData.zxxqyl + '</td>' +
        // '</tr>' +
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
})