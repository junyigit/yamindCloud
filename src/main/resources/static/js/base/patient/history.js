$(function () {




    lauguageData = returnLanguage();


    initialPage();
    getGrid();


    var myChart1 = echarts.init(document.getElementById('charts1'));
    var myChart2 = echarts.init(document.getElementById('charts2'));
//    var myChart3 = echarts.init(document.getElementById('charts3'));


    laydate.render({
        elem: '#dateMap' //指定元素
    });


    //获取日期范围
    var dateRange=0;
    //开始时间
    var startDate=0;
    //结束时间
    var endTime=0;

    //获取地址传参
    var serialId = getUrlParam(window.location.href,'serialId');

    // 统计信息---点击查询
    $("#dateHistory").click(function(){
        $("#cisLoading").show();
        if (endTime !=0 && startDate !=0){
            $.ajax({
                type: "POST",
                url: "/sys/patient/getHistoryStatInfo",
                dataType: "json",
                data: {
                    "serialId": serialId,
                    "startDate":startDate,
                    "endDate":endTime
                },beforeSend:function (xhr) {
                    $("#cisLoading").show();
                },success : function(result) {//返回数据根据结果进行相应的处理
                    if (result.code ==500){
                        dialogMsg("当前选择日期没有数据！");
                        return;
                    }
                    $("#cisLoading").hide();
                    $("#tjxx").empty();
                    var table =$("#tjxx");
                    table.append(
                        returnMode2(result)
                    )
                }, complete:function (XMLHttpRequest, status) {
                    $("#cisLoading").hide();
                },error:function(err){
                }
            })
        }else{
            dialogMsg(lauguageData.xztjsj);
        }

    })


    // 曲线图---点击查询
    $("#findMap").click(function(){
        $("#cisLoading").show();

        $("#charts1").hide();
        $("#charts2").hide();
        //获取日期控件选择日期
        var date = $("#dateMap").val();
        $.ajax({
            type: "POST",
            url: "/sys/patient/findMapWithSerial",
            dataType: "json",
            data: {
                "serialId": serialId,
                "time":date
            }, success: function (result) {//返回数据根据结果进行相应的处理

                $("#charts1").show();
                $("#charts2").show();

                if (result.code ==500){
                    dialogMsg("当前日期没有治疗数据~");
                    $("#cisLoading").hide();
                    $("#charts1").hide();
                    $("#charts2").hide();
                    return;
                }
                $("#cisLoading").hide();
                //控制器返回的选择当天数据
                var mapData = result.map;
                //保存时间的数组
                var curetime = [];
                var curetimeOther = [];
                var cureArr = [];//治疗数组
                var flowArr = [];//流量数组
                var ahiArr  = [];//低通气数组
                var ahiTime = [];//低通气数组
                // var maxArr = [];//最大压力
                // var minArr = []; //最小压力
                var inhale = [];//吸气压力
                var outhale =[]; //呼气压力


                for (var i = 0, length = mapData.length; i < length; i++) {
                    curetime.push(mapData[i].cureTime);
                    // curetime.push(mapData[i].cureTime);
                    // curetime.push(mapData[i].cureTime);
                    // curetime.push(mapData[i].cureTime);
                    // curetime.push(mapData[i].cureTime);
                    // curetimeOther.push(mapData[i].cureTime)
                    // curetimeOther.push(mapData[i].cureTime)
                    // curetimeOther.push(mapData[i].cureTime)
                    // curetimeOther.push(mapData[i].cureTime)
                    // curetimeOther.push(mapData[i].cureTime)

                    cureArr.push(mapData[i].cureStress);
                    cureArr.push(mapData[i].cureStress2);
                    cureArr.push(mapData[i].cureStress3);
                    cureArr.push(mapData[i].cureStress4);
                    cureArr.push(mapData[i].cureStress5);
                    // minArr.push(mapData[i].minStress);
                    // maxArr.push(mapData[i].maxStress);
                    inhale.push(mapData[i].inhaleStress);
                    outhale.push(mapData[i].exhaleStress);
                    flowArr.push(mapData[i].realFlow1);
                    flowArr.push(mapData[i].realFlow2);
                    flowArr.push(mapData[i].realFlow3);
                    flowArr.push(mapData[i].realFlow4);
                    flowArr.push(mapData[i].realFlow5);
                }

                console.log("end:"+ new Date());

                switch (result.mode) {
                    case "CPAP":
                        option = {
                            title: {
                                left: 'center',
                                text: lauguageData.treatmentPres+'(hPa)',
                            },tooltip: {

                                show: true,
                                trigger: 'axis',
                                axisPointer: {
                                    type: 'line'
                                },
                                lineStyle: {
                                    color: '#000',
                                },
                                formatter: function (params) {
                                    params = params[0];
                                    var date = params.name;
                                    return '<div><p>当前时间第：'+params.name+'秒</p></div>'+'<p>当前压力值 : '+ params.value+'</p>';
                                }
                            },
                            xAxis: {
                                type: 'category',
                                boundaryGap: false,
                                data: curetime
                            },
                            yAxis: {
                                type: 'value',
                                max:30,
                                min:0,
                                boundaryGap: [0, '100%']
                            },
                            dataZoom: [{
                                type: 'inside',
                            },{

                            }],
                            series: [
                                {
                                    name: '治疗压力',
                                    type: 'line',
                                    smooth: 0.3,
                                    symbol:'none',
                                    color:["#CE0000"],
                                    sampling:"average",
                                    data: cureArr
                                }
                            ]
                        };

                        break;

                    case "APAP":
                        option = {
                            title: {
                                left: 'center',
                                text: '治疗压力(hPa)',
                            },tooltip: {
                                show: true,
                                trigger: 'axis',
                                axisPointer: {
                                    type: 'line'
                                },
                                lineStyle: {
                                    color: '#000',
                                },
                                formatter: function (params) {
                                    params = params[0];
                                    var date = params.name;
                                    return '<div><p>当前时间第：'+params.name+'秒</p></div>'+'<p>当前压力值 : '+ params.value+'</p>';
                                }
                            },
                            xAxis: {
                                type: 'category',
                                boundaryGap: false,
                                data: curetime
                            },
                            yAxis: {
                                type: 'value',
                                max:30,
                                min:0,
                                boundaryGap: [0, '100%']
                            },
                            dataZoom: [{
                                type: 'inside',
                            },{

                            }],

                            series: [
                                {
                                    name: lauguageData.maxKpa,
                                    type: 'line',
                                    smooth: 0.3,
                                    symbol:'none',
                                    color:["#CE0000"],
                                    sampling:"average",
                                    data: cureArr
                                }
                                ,
                                {
                                    name: lauguageData.minKpa,
                                    type: 'line',
                                    smooth: 0.3,
                                    symbol:'none',
                                    color:["#00BB00"],
                                    sampling:"average",
                                    data: cureArr
                                }
                            ]
                        };

                        break;
                    default:
                        option = {
                            title: {
                                left: 'center',
                                text: lauguageData.treatmentPres+'(hPa)',
                            },tooltip: {

                                show: true,
                                trigger: 'axis',
                                axisPointer: {
                                    type: 'line'
                                },
                                lineStyle: {
                                    color: '#000',
                                },
                                showDelay:100,
                                formatter: function (params) {
                                    params = params[0];
                                    var date = params.name;
                                    return '<div><p>当前时间第：'+params.name+'秒</p></div>'+'<p>当前压力值 : '+ params.value+'</p>';
                                }
                            },

                            xAxis: {
                                type: 'category',
                                boundaryGap: false,
                                data: curetime
                            },
                            yAxis: {
                                type: 'value',
                                max:30,
                                min:0,
                                boundaryGap: [0, '100%']
                            },
                            dataZoom: [{
                                type: 'inside',
                            },{
                            }],
                            series: [
                                {
                                    name: lauguageData.xqyl,
                                    type: 'line',
                                    smooth: 0.3,
                                    symbol:'none',
                                    color:["#CE0000"],
                                    sampling:"average",
                                    data: inhale
                                },
                                {
                                    name: lauguageData.hqyl,
                                    type: 'line',
                                    smooth: 0.3,
                                    symbol:'none',
                                    color:["#00BB00"],
                                    sampling:"average",
                                    data: outhale
                                }
                            ]
                        };

                        break;
                }


                option2 = {
                    title: {
                        left: 'center',
                        text: lauguageData.qll+'(L/min)',
                    },tooltip: {
                        trigger: 'axis',
                        showDelay:100,
                        formatter: function (params) {
                            params = params[0];
                            var date = params.name;
                            return '<div><p>当前时间第：'+params.name+'秒</p></div>'+'<p>当前气流量 : '+ params.value+'</p>';
                        }},
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: curetime
                    },
                    yAxis: {
                        type: 'value',
                        max:200,
                        min:-200,
                        boundaryGap: [0, '100%']
                    },
                    dataZoom: [{
                        type: 'inside',
                    },{

                    }],

                    series: [
                        {
                            name: '气流量',
                            type: 'line',
                            smooth: 0.3,
                            symbol:'none',
                            color:["#CE0000"],
                            sampling:"average",
                            data: flowArr
                        }
                    ]
                    /*,
                    sampling:"average",
                    showAllSymbol: false,
                    animation: false*/
                };
                myChart2.setOption(option2);
                myChart1.setOption(option);



                // 分别设置每个实例的 group id
                myChart1.group = 'group1';
                myChart2.group = 'group1';
                echarts.connect('group1');

                /**
                 * 呼吸事件图形
                 * @type {Array}
                 */


            }, error: function (r) {
                console.log("曲线图错误");
            }
        })

    })




//初始化时间控件
    function initialPage() {
        //日期选择
        laydate.render({
            elem: '#dateRange',
            range: true,
            theme: '#3C8DBC',
            eventElem: '#dateRange',
            trigger: 'click',
            done: function(value, date, endDate){
                dateRange = value;
                startDate = formatDate(date.year + '-' + date.month + '-' + date.date, 'yyyy-MM-dd');
                endTime = formatDate(endDate.year + '-' + endDate.month + '-' + endDate.date, 'yyyy-MM-dd');
            }
        });
    }



    function getGrid() {
        serialId = getUrlParam(window.location.href,'serialId');
        $('#setInfo').bootstrapTableEx({
            url: '../../sys/patient/getHistorySetData?_' + $.now(),
            height: $(window).height() - 76,
            striped: true,
            queryParams: function (params) {
                params.serialId = serialId;
                return params;
            },
            columns: [{
                checkbox: true
            }, {
                field: "useDate",
                title: lauguageData.data,
                width: "100px"
            }, {
                field: "mode",
                title: lauguageData.mode,
                width: "50px"
            }, {
                field: "cureStress",
                title: lauguageData.treatmentPres,
                width: "20px"
            }, {
                field: "minStress",
                title: lauguageData.minKpa,
                width: "50px"
            }, {
                field: "maxStress",
                title: lauguageData.maxKpa,
                width: "40px"
            }, {
                field: "maxInhaleStress",
                title: lauguageData.zdxqyl,
                width: "25px",
            }, {
                field: "inhaleStress",
                title: lauguageData.xqyl,
                width: "35px"
            }, {
                field: "exhaleStress",
                title: lauguageData.hqyl,
                width: "35px"
            },
                {
                    field: "respiratoryRate",
                    title: lauguageData.hxpl,
                    width: "40px"
                },
                {

                    field: "inhaleTime",
                    title: lauguageData.xqsj,
                    width: "40px"
                },
                {
                    field: "exhaleRelease",
                    title: lauguageData.hqsf,
                    width: "60px"
                },
                {
                    field: "inhaleSensitivity",
                    title: lauguageData.xqlmd,
                    width: "60px"
                },
                {
                    field: "exhaleSensitivity",
                    title: lauguageData.hqlmd,
                    width: "30px"
                },
                {
                    field: "stressUp",
                    title: lauguageData.ylsspd,
                    width: "80px"

                },
                {
                    field: "maxInhaleTime",
                    title: lauguageData.maxInhaleTime,
                    width: "80px"

                },
                {
                    field: "minInhaleTime",
                    title: lauguageData.minInhaleTime,
                    width: "80px"

                },
                {
                    field: "aiStart",
                    title: lauguageData.aiStart,
                    width: "80px"

                },
                {
                    field: "avaps",
                    title: "AVAPS",
                    width: "80px"

                },
                {
                    field: "softVersion",
                    title: lauguageData.softVersion,
                    width: "80px"
                }]
        })
    }

    /**
     * 返回模式1的HTML数据
     * @param p
     * @returns {string}
     */
    function returnMode1(p) {
        return mode1 =
            '<table class="table" id="syxx" >' +
            '<h3><tr><b>'+lauguageData.syxx+'</b></tr></h3>' +
            '<tr> ' +
            '<td>' + lauguageData.syts+ '</td>' +
            '<td>' + p.useDay + '</td>' +
            '<td>' + lauguageData.day4hours + '</td>' +
            '<td>' + p.useDay + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.time + '</td>' +
            '<td>' + p.useTime + '</td>' +
            '<td>' + lauguageData.pjsysj + '</td>' +
            '<td>' + p.avgUseTime + '</td>' +
            '</tr>' +
            '</table>' +

            '<table class="table" id="zlyl" cellspacing="50">' +
            '<h3><tr><b>'+lauguageData.treatmentPres+'</b></tr></h3>' +
            '<tr> ' +
            '<td>' + lauguageData.pjz + '</td>' +
            '<td>' + p.maxAvg.ylpjz + '</td>' +
            '<td>' + lauguageData.p95z + '</td>' +
            '<td>' + p.stressNice + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.zwz + '</td>' +
            '<td>' + p.cureStress.cureStress + '</td>' +
            '<td>' + lauguageData.zdz + '</td>' +
            '<td>' + p.maxAvg.ylzdz + '</td>' +
            '</tr>' +
            '</table>' +

            '<table class="table" id="hxzt" cellspacing="50">' +
            '<h3><tr><b>'+lauguageData.hxztdtqzs+'</b></tr></h3>' +
            '<tr> ' +
            '<td>' + lauguageData.pjahi+ '</td>' +
            '<td>' + p.avgAHI + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.pjai + '</td>' +
            '<td>' + p.avgAi+ '</td>' +
            '<td>' + lauguageData.pjhi + '</td>' +
            '<td>' + p.avgHi + '</td>' +
            '</tr>' +
            '</table>';
    }


    /**
     * 返回模式2的HTML数据
     * @param p
     * @returns {string}
     */
    function returnMode2(p){
        return mode2=

            '<table class="table" id="syxx" >' +
            '<h3><tr><b>'+lauguageData.syxx+'</b></tr></h3>' +
            '<tr> ' +
            '<td>' + lauguageData.syts+ '</td>' +
            '<td>' + p.useDay + '</td>' +
            '<td>' + lauguageData.day4hours + '</td>' +
            '<td>' + p.useDay + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.time + '</td>' +
            '<td>' + p.useTime + '</td>' +
            '<td>' + lauguageData.pjsysj + '</td>' +
            '<td>' + p.avgUseTime + '</td>' +
            '</tr>' +
            '</table>' +

            '<table class="table" id="lql" cellspacing="50">'+
            '<h3><tr><b>'+lauguageData.treatmentPres+'</b></tr></h3>'+
            '<tr> ' +
            '<td>' + lauguageData.xqpjyl + '</td>' +
            '<td>' + p.maxAvg.xqylpjz.toFixed(2) + '</td>' +
            '<td>' + lauguageData.hqpjyl + '</td>' +
            '<td>' + p.maxAvg.hqylpjz + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.xqpj95 + '</td>' +
            '<td>' + p.inhaleStressNice+ '</td>' +
            '<td>' + lauguageData.hqpj95 + '</td>' +
            '<td>' + p.exhaleStressNice + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.xqylzwz + '</td>' +
            '<td>' + p.inhaleStress+ '</td>' +
            '<td>' + lauguageData.hqylzwz + '</td>' +
            '<td>' + p.exhaleStress + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.xqylzdz + '</td>' +
            '<td>' + p.maxAvg.xqylzdz+ '</td>' +
            '<td>' + lauguageData.hqylzdz + '</td>' +
            '<td>' + p.maxAvg.hqylzdz + '</td>' +
            '</tr>' +
            '</table>'+


            '<table class="table" id="hxzt" cellspacing="50">' +
            '<h3><tr><b>'+lauguageData.hxztdtqzs+'</b></tr></h3>' +
            '<tr> ' +
            '<td>' + lauguageData.pjahi+ '</td>' +
            '<td>' + p.avgAHI + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.pjai + '</td>' +
            '<td>' + p.avgAi+ '</td>' +
            '<td>' + lauguageData.pjhi + '</td>' +
            '<td>' + p.avgHi + '</td>' +
            '</tr>' +
            '</table>'+


            '<table class="table" id="cql" cellspacing="50">'+
            '<h3><tr><b>'+lauguageData.cqlfztql+'</b></tr></h3>'+
            '<tr> ' +
            '<td>' + lauguageData.cqlpjz + '</td>' +
            '<td>' + p.maxAvg.cqlpjz.toFixed(2)+ '</td>' +
            '<td>' + lauguageData.fztqpjz + '</td>' +
            '<td>' + p.maxAvg.fztqlpjz.toFixed(2) + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.cqlzwz + '</td>' +
            '<td>' + p.tidalVolume+ '</td>' +
            '<td>' + lauguageData.fztqzwz+ '</td>' +
            '<td>' + p.minuThroughput + '</td>'  +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.cql95z + '</td>' +
            '<td>' + p.tidalVolumeNice.toFixed(2)+ '</td>' +
            '<td>' + lauguageData.fztq95z + '</td>' +
            '<td>' + p.minuThroughputNice.toFixed(2) + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + lauguageData.cqlzdz + '</td>' +
            '<td>' + p.maxAvg.cqlzdz+ '</td>' +
            '<td>' + lauguageData.fztqzdz + '</td>' +
            '<td>' + p.maxAvg.fztqzdz + '</td>' +
            '</tr>' +
            '</table>'+


            '<table class="table" id="hxpl" cellspacing="50">'+
            '<h3><tr><b>'+lauguageData.hxpl+'</b></tr></h3>'+
            '<tr> ' +
            '<td>' + lauguageData.hxplpjz + '</td>' +
            '<td>' + p.maxAvg.hxplpjz.toFixed(2)+ '</td>' +
            '<td>' + lauguageData.hxplzwz + '</td>' +
            '<td>' +  p.respiratoryRate+ '</td>' +
            '</tr>' +

            '<tr> ' +
            '<td>'  +lauguageData.hxpl95z+ '</td>' +
            '<td>' + p.respiratoryRateNice+ '</td>' +
            '<td>' +lauguageData.hxplzdz+ '</td>' +
            '<td>' + p.maxAvg.hxplzdz + '</td>' +
            '</tr>' +
            '</table>';
    }


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



});


