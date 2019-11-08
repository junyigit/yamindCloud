$(function () {


    $.getJSON("/json/language.json",function (data) {
        console.log(data);
    })


    var myChart1 = echarts.init(document.getElementById('charts1'));

    data = [["1",116],["2",129],["3",135],["4",86],["5",73],["6",85],["7",73],["8",68],["9",92],["10",130],["11",245]];
    var dateList = data.map(function (item) {
        return item[0];
    });
    var valueList = data.map(function (item) {
        return item[1];
    });

    option = {

        // Make gradient line here
        visualMap: [{
            show: false,
            type: 'continuous',
            seriesIndex: 0,
            min: 0,
            max: 400
        }, {
            show: false,
            type: 'continuous',
            seriesIndex: 1,
            dimension: 0,
            min: 0,
            max: dateList.length - 1
        }],


        title: [{
            left: 'center',
            text: 'Gradient along the y axis'
        }, {
            top: '55%',
            left: 'center',
            text: 'Gradient along the x axis'
        }],
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [{
            data: dateList
        }, {
            data: dateList,
            gridIndex: 1
        }],
        yAxis: [{
            splitLine: {show: false}
        }, {
            splitLine: {show: false},
            gridIndex: 1
        }],
        grid: [{
            bottom: '60%'
        }, {
            top: '60%'
        }],
        series: [{
            type: 'line',
            showSymbol: false,
            data: valueList
        }, {
            type: 'line',
            showSymbol: false,
            data: valueList,
            xAxisIndex: 1,
            yAxisIndex: 1
        }]
    };



    setInterval(function () {
        data = [["1",Math.ceil(Math.random()*100)],["2",Math.ceil(Math.random()*100)],["3",Math.ceil(Math.random()*100)]
            ,["4",Math.ceil(Math.random()*100)],["5",Math.ceil(Math.random()*100)],["6",Math.ceil(Math.random()*100)],
            ["7",Math.ceil(Math.random()*100)],["8",Math.ceil(Math.random()*100)],["9",Math.ceil(Math.random()*100)],
            ["10",Math.ceil(Math.random()*100)],["11",Math.ceil(Math.random()*100)]];
        var dateList = data.map(function (item) {
            return item[0];
        });
        var valueList1 = data.map(function (item) {
            return item[1];
        });

        myChart1.setOption({
            series: [{
                type: 'line',
                showSymbol: false,
                data: valueList1
            }, {
                type: 'line',
                showSymbol: false,
                data: valueList1,
                xAxisIndex: 1,
                yAxisIndex: 1
            }]
            });


    }, 1000);

    myChart1.setOption(option);
})



