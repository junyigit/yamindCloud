$(function () {
    initialPage();
    getGrid();


    var myChart1 = echarts.init(document.getElementById('charts1'));
    var myChart2 = echarts.init(document.getElementById('charts2'));
    var myChart3 = echarts.init(document.getElementById('charts3'));
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

        if (endTime !=0 && startDate !=0){
            $.ajax({
                type: "POST",
                url: "/sys/patient/getHistoryStatInfo",
                dataType: "json",
                data: {
                    "serialId": serialId,
                    "startDate":startDate,
                    "endDate":endTime
                }, success : function(result) {//返回数据根据结果进行相应的处理
                    $("#tjxx").empty();
                    var table =$("#tjxx");
                    table.append(
                        returnMode2(result)
                    )
                },error:function(err){
                }
            })
        }else{
            dialogMsg("请选择统计时间!");
        }

    })


    // 曲线图---点击查询
    $("#findMap").click(function(){

        $("#operGroup").hide();
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

                //控制器返回的选择当天数据
                var mapData = result.map;
                //保存时间的数组
                var time = [];
                var cureArr = [];//治疗数组
                var flowArr = [];//流量数组
                var ahiArr  = [];//低通气数组
                var ahiTime = [];//低通气数组
                var maxArr = [];//最大压力
                var minArr = []; //最小压力
                var inhale = [];//吸气压力
                var outhale =[]; //呼气压力

                for (var i = 0;mapData[i]!=null; i++) {
                    time.push(mapData[i].cureTime);
                    cureArr.push(mapData[i].cureStress);
                    minArr.push(mapData[i].minStress);
                    maxArr.push(mapData[i].maxStress);
                    inhale.push(mapData[i].inhaleStress);
                    outhale.push(mapData[i].exhaleStress);
                    flowArr.push(mapData[i].realFlow);
                }
                console.timeEnd();
                    switch (result.mode) {
                        case "CPAP":
                                option = {
                                    title: {
                                        left: 'center',
                                        text: '治疗压力(hPa)',
                                    },tooltip: {
                                        trigger: 'axis',
                                        showDelay:100,
                                        formatter: function (params) {
                                            params = params[0];
                                            var date = params.name;
                                            return '<div><p>当前时间第：'+params.name+'秒</p></div>'+'<p>当前压力值 : '+ params.value+'</p>';
                                        }},
                                    xAxis: {
                                        type: 'category',
                                        boundaryGap: false,
                                        data: time
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
                                            data: cureArr
                                        }
                                    ]
                                };
                                myChart1.setOption(option);
                            break;

                        case "APAP":
                            option = {
                                title: {
                                    left: 'center',
                                    text: '治疗压力(hPa)',
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
                                    boundaryGap: false,
                                    data: time
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
                                        name: '最大压力',
                                        type: 'line',
                                        smooth: 0.3,
                                        symbol:'none',
                                        color:["#CE0000"],
                                        data: maxArr
                                    },
                                    {
                                        name: '最小压力',
                                        type: 'line',
                                        smooth: 0.3,
                                        symbol:'none',
                                        color:["#00BB00"],
                                        data: minArr
                                    }
                                ]
                            };
                            myChart1.setOption(option);
                            break;
                        default:
                            option = {
                                title: {
                                    left: 'center',
                                    text: '治疗压力(hPa)',
                                },tooltip: {
                                    trigger: 'axis',
                                    showDelay:100,
                                    formatter: function (params) {
                                        params = params[0];
                                        var date = params.name;
                                        return '<div><p>当前时间第：'+params.name+'秒</p></div>'+'<p>当前压力值 : '+ params.value+'</p>';
                                    }},
                                xAxis: {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: time
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
                                        name: '吸气压力',
                                        type: 'line',
                                        smooth: 0.3,
                                        symbol:'none',
                                        color:["#CE0000"],
                                        data: inhale
                                    },
                                    {
                                        name: '呼气压力',
                                        type: 'line',
                                        smooth: 0.3,
                                        symbol:'none',
                                        color:["#00BB00"],
                                        data: outhale
                                    }
                                ]
                            };
                            myChart1.setOption(option);
                            break;
                    }


                    option2 = {
                        title: {
                            left: 'center',
                            text: '气流量(L/min)',
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
                            data: time
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
                                data: flowArr
                            }
                        ]
                    };
                    myChart2.setOption(option2);


                var data = [];
                var dataCount = 10;
                var startTime = +new Date();
                var categories = ['低通气', '呼吸暂停',];
                var types = [
                    {name: 'JS Heap', color: '#009393'},
                    {name: 'Documents', color: '#eac100'}
                ];

// Generate mock data
                echarts.util.each(categories, function (category, index) {
                    var baseTime = startTime;
                    for (var i = 0; i < dataCount; i++) {
                        var typeItem = types[Math.round(Math.random() * (types.length - 1))];
                        var duration = Math.round(Math.random() * 10000);
                        data.push({
                            name: typeItem.name,
                            value: [
                                index,
                                baseTime,
                                baseTime += duration,
                                duration
                            ],
                            itemStyle: {
                                normal: {
                                    color: typeItem.color
                                }
                            }
                        });
                        baseTime += Math.round(Math.random() * 2000);
                    }
                });

                function renderItem(params, api) {
                    var categoryIndex = api.value(0);
                    var start = api.coord([api.value(1), categoryIndex]);
                    var end = api.coord([api.value(2), categoryIndex]);
                    var height = api.size([0, 1])[1] * 0.6;

                    var rectShape = echarts.graphic.clipRectByRect({
                        x: start[0],
                        y: start[1] - height / 2,
                        width: end[0] - start[0],
                        height: height
                    }, {
                        x: params.coordSys.x,
                        y: params.coordSys.y,
                        width: params.coordSys.width,
                        height: params.coordSys.height
                    });

                    return rectShape && {
                        type: 'rect',
                        shape: rectShape,
                        style: api.style()
                    };
                }


                option3 = {
                    tooltip: {
                        formatter: function (params) {
                            return params.marker + params.name + ': ' + params.value[3] + ' ms';
                        }
                    },
                    title: {
                        text: '事件',
                        left: 'center'
                    },
                    dataZoom: [{
                        type: 'slider',
                        filterMode: 'weakFilter',
                        showDataShadow: false,
                        top: 400,
                        height: 10,
                        borderColor: 'transparent',
                        backgroundColor: '#e2e2e2',
                        handleIcon: 'M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7v-1.2h6.6z M13.3,22H6.7v-1.2h6.6z M13.3,19.6H6.7v-1.2h6.6z', // jshint ignore:line
                        handleSize: 20,
                        handleStyle: {
                            shadowBlur: 6,
                            shadowOffsetX: 1,
                            shadowOffsetY: 2,
                            shadowColor: '#aaa'
                        },
                        labelFormatter: ''
                    }, {
                        type: 'inside',
                        filterMode: 'weakFilter'
                    }],
                    grid: {
                        height:300
                    },
                    xAxis: {
                        min: startTime,
                        scale: true,
                        axisLabel: {
                            formatter: function (val) {
                                return Math.max(0, val - startTime) + ' ms';
                            }
                        }
                    },
                    yAxis: {
                        data: categories
                    },
                    series: [{
                        type: 'custom',
                        renderItem: renderItem,
                        itemStyle: {
                            normal: {
                                opacity: 0.8
                            }
                        },
                        encode: {
                            x: [1, 2],
                            y: 0
                        },
                        data: data
                    }]
                };
                    myChart3.setOption(option3);

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
            field: "cureTime",
            title: "日期",
            width: "80px"
        }, {
            field: "mode",
            title: "模式",
            width: "50px"
        }, {
            field: "cureStress",
            title: "治疗压力",
            width: "20px"
        }, {
            field: "minStress",
            title: "最小压力",
            width: "50px"
        }, {
            field: "maxStress",
            title: "最大压力",
            width: "40px"
        }, {
            field: "maxInhaleStress",
            title: "最大吸气压力",
            width: "35px",
        }, {
            field: "inhaleStress",
            title: "吸气压力",
            width: "35px"
        }, {
            field: "exhaleStress",
            title: "呼气压力",
            width: "35px"
        },
        {
            field: "respiratoryRate",
            title: "呼吸频率",
            width: "40px"
        },
        {

            field: "inhaleTime",
            title: "吸气时间",
            width: "40px"
        },
        {
            field: "exhaleRelease",
            title: "呼气释放",
            width: "60px"
        },
        {
            field: "inhaleSensitivity",
            title: "吸气灵敏度",
            width: "60px"
        },
        {
            field: "exhaleSensitivity",
            title: "呼气灵敏度",
            width: "30px"
        },
        {
            field: "stressUp",
            title: "压力上升坡度",
            width: "80px"

        },
        {
            field: "stressDown",
            title: "压力下降坡度",
            width: "80px"

        },
        {
            field: "avaps",
            title: "AVAPS",
            width: "80px"

        },
        {
            field: "softVersion",
            title: "软件版本",
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
            '<h3><tr style="background-color: coral"><b>使用信息</b></tr></h3>' +
            '<tr> ' +
            '<td>' + "使用天数" + '</td>' +
            '<td>' + p.useDay + '</td>' +
            '<td>' + "天＞4小时" + '</td>' +
            '<td>' + p.useDay + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td style="background-color: coral">' + "使用时间" + '</td>' +
            '<td>' + p.useTime + '</td>' +
            '<td>' + "平均使用时间" + '</td>' +
            '<td>' + p.avgUseTime + '</td>' +
            '</tr>' +
            '</table>' +

            '<table class="table" id="zlyl" cellspacing="50">' +
            '<h3><tr><b>治疗压力</b></tr></h3>' +
            '<tr> ' +
            '<td  style="background:red;">' + "平均值" + '</td>' +
            '<td>' + p.maxAvg.ylpjz + '</td>' +
            '<td>' + "P95值" + '</td>' +
            '<td>' + p.stressNice + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "中位值" + '</td>' +
            '<td>' + p.cureStress.cureStress + '</td>' +
            '<td>' + "最大值" + '</td>' +
            '<td>' + p.maxAvg.ylzdz + '</td>' +
            '</tr>' +
            '</table>' +

            '<table class="table" id="hxzt" cellspacing="50">' +
            '<h3><tr><b>呼吸暂停低通气指数</b></tr></h3>' +
            '<tr> ' +
            '<td>' + "平均AHI" + '</td>' +
            '<td>' + p.maxAvg.pjahi + '</td>' +
            '<td>' + "最大AHI" + '</td>' +
            '<td>' + p.maxAvg.zdahi+ '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "平均AI" + '</td>' +
            '<td>' + p.maxAvg.aipjz+ '</td>' +
            '<td>' + "平均HI" + '</td>' +
            '<td>' + p.maxAvg.hipjz + '</td>' +
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
            '<h3><tr style="background-color: coral"><b>使用信息</b></tr></h3>' +
            '<tr> ' +
            '<td>' + "使用天数" + '</td>' +
            '<td>' + p.useDay + '</td>' +
            '<td>' + "天＞4小时" + '</td>' +
            '<td>' + p.useDay + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "使用时间" + '</td>' +
            '<td>' + p.useTime + '</td>' +
            '<td>' + "平均使用时间" + '</td>' +
            '<td>' + p.avgUseTime + '</td>' +
            '</tr>' +
            '</table>' +

            '<table class="table" id="lql" cellspacing="50">'+
            '<h3><tr><b>治疗压力</b></tr></h3>'+
            '<tr> ' +
            '<td>' + "吸气平均压力" + '</td>' +
            '<td>' + p.maxAvg.xqylpjz+ '</td>' +
            '<td>' + "呼气平均压力" + '</td>' +
            '<td>' + p.maxAvg.hqylpjz + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "吸气平均P95" + '</td>' +
            '<td>' + p.inhaleStressNice+ '</td>' +
            '<td>' + "呼气平均P95" + '</td>' +
            '<td>' + p.exhaleStressNice + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "吸气压力中位值" + '</td>' +
            '<td>' + p.inhaleStress+ '</td>' +
            '<td>' + "呼气压力中位值" + '</td>' +
            '<td>' + p.exhaleStress + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "吸气压力最大值" + '</td>' +
            '<td>' + p.maxAvg.xqylzdz+ '</td>' +
            '<td>' + "呼气压力最大值" + '</td>' +
            '<td>' + p.maxAvg.hqylzdz + '</td>' +
            '</tr>' +
            '</table>'+


            '<table class="table" id="hxzt" cellspacing="50">' +
            '<h3><tr><b>呼吸暂停低通气指数</b></tr></h3>' +
            '<tr> ' +
            '<td>' + "平均AHI" + '</td>' +
            '<td>' + p.maxAvg.pjahi + '</td>' +
            '<td>' + "最大AHI" + '</td>' +
            '<td>' + p.maxAvg.zdahi+ '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "平均AI" + '</td>' +
            '<td>' + p.maxAvg.aipjz+ '</td>' +
            '<td>' + "平均HI" + '</td>' +
            '<td>' + p.maxAvg.hipjz + '</td>' +
            '</tr>' +
            '</table>'+


            '<table class="table" id="cql" cellspacing="50">'+
            '<h3><tr><b>潮气量＆分钟通气量</b></tr></h3>'+
            '<tr> ' +
            '<td>' + "潮气量平均值" + '</td>' +
            '<td>' + p.maxAvg.cqlpjz+ '</td>' +
            '<td>' + "分钟通气量平均值" + '</td>' +
            '<td>' + p.maxAvg.fztqlpjz + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "潮气量中位值" + '</td>' +
            '<td>' + p.tidalVolume+ '</td>' +
            '<td>' + "分钟通气量中位值" + '</td>' +
            '<td>' + p.minuThroughput + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "潮气量95%值" + '</td>' +
            '<td>' + p.tidalVolumeNice+ '</td>' +
            '<td>' + "分钟通气量95%值" + '</td>' +
            '<td>' + p.minuThroughputNice + '</td>' +
            '</tr>' +
            '<tr> ' +
            '<td>' + "潮气量最大值" + '</td>' +
            '<td>' + p.maxAvg.cqlzdz+ '</td>' +
            '<td>' + "分钟通气量最大值" + '</td>' +
            '<td>' + p.maxAvg.fztqzdz + '</td>' +
            '</tr>' +
            '</table>'+


            '<table class="table" id="hxpl" cellspacing="50">'+
            '<h3><tr><b>呼吸频率</b></tr></h3>'+
            '<tr> ' +
            '<td>' + "呼吸频率平均值" + '</td>' +
            '<td>' + p.maxAvg.hxplpjz+ '</td>' +
            '<td>' + "呼吸频率中位值" + '</td>' +
            '<td>' +  p.respiratoryRate+ '</td>' +
            '</tr>' +

            '<tr> ' +
            '<td>' + "呼吸频率95%值" + '</td>' +
            '<td>' + p.respiratoryRateNice+ '</td>' +
            '<td>' + "呼吸频率最大值" + '</td>' +
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


