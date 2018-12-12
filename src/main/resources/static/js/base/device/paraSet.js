 $(function () {

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


     //定义全局变量
     var ipAddr;

     var serialId = getUrlParam(window.location.href,'serialId');
     var cureMode ;
     $.ajax({
         type: "POST",
         url: "/sys/device/getParaDetails",
         dataType: "json",
         data: {
             "serialId": serialId
         },//数据，这里使用的是Json格式进行传输
         success: function (result) {//返回数据根据结果进行相应的处理
             console.log(result);
             var table = $("#paraList");
             var d = eval('(' + result.para + ')');
             ipAddr = d.ipAddr;
             $("#paraList").empty(); //清空table（除了第一行以外）

             switch (d.mode) {
                 case "CPAP":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +
                         '<h4><b>设备参数</b></h4>' +

                         '<tr> ' +
                         '<td>' + "模式" + '</td>' +
                         '<td><select id="modeSel" selected = "selected" onchange="changeMode()">\n' +
                         '<option value="CPAP" selected="selected">CPAP</option>' +
                         '  <option value ="APAP">APAP</option>\n' +
                         // '  <option>S</option>\n' +
                         // '  <option>S-Auto</option>\n' +
                         // '  <option>T</option>\n' +
                         // '  <option>S/T</option>\n' +
                         '</select></td>' +
                         '</tr>' +
                         '<tr> ' +
                         '<td>' + "呼气释放" + '</td>' +
                         '<td><input id="breatheRel" value=' + d.cureData.hqsf + '></td>' +
                         '<td>' + "治疗压力" + '</td>' +
                         '<td><input id="cureStress" value=' + d.cureData.zlyl + '></td>' +
                         '</tr>' +
                         '<tr> ' +
                         '<td>' + "开始压力" + '</td>' +
                         '<td><input id="starStress" value=' + d.cureData.ksyl + '></td>' +
                         '<td>' + "延迟时间" + '</td>' +
                         '<td><input id="delayTime" value=' + d.cureData.ycsj + '></td>' +
                         '</tr>' +
                         '</table>'
                     );
                     break;
                 case "APAP":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +
                         '<h4><b>设备参数</b></h4>' +

                         '<tr> ' +
                         '<td>' + "模式" + '</td>' +
                         '<td><select id="modeSel" onchange="changeMode()">\n' +
                         '<option value="APAP" selected="selected">APAP</option>' +
                         '<option value ="CPAP">CPAP</option>' +
                         // '  <option>S</option>\n' +
                         // '  <option>S-Auto</option>\n' +
                         // '  <option>T</option>\n' +
                         // '  <option>S/T</option>\n' +
                         '</select></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "开始压力" + '</td>' +
                         '<td><input id="starStress" value=' + d.cureData.ksyl + '></td>' +
                         '<td>' + "延迟时间" + '</td>' +
                         '<td><input id="delayTime" value=' + d.cureData.ycsj + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "最大压力" + '</td>' +
                         '<td><input id="maxStress" value=' + d.cureData.zdyl + '></td>' +
                         '<td>' + "最小压力" + '</td>' +
                         '<td><input id="minStress" value=' + d.cureData.zxyl + '></td>' +
                         '</tr>' +
                         '<tr> ' +
                         '<td>' + "呼气释放" + '</td>' +
                         '<td><input id="breatheRel" value=' + d.cureData.hqsf + '></td>' +
                         '</tr>' +

                         '</table>'
                     );
                     break;

                 case "S":

                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +
                         '<tr> ' +
                         '<td>' + "吸气压力" + '</td>' +
                         '<td><input value=' + d.cureData.xqyl + '></td>' +
                         '<td>' + "呼气压力" + '</td>' +
                         '<td><input value=' + d.cureData.hqyl + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "开始压力" + '</td>' +
                         '<td><input value=' + d.cureData.ksyl + '></td>' +
                         '<td>' + "延迟时间" + '</td>' +
                         '<td><input value=' + d.cureData.ycsj + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "吸气灵敏度" + '</td>' +
                         '<td><input value=' + d.cureData.xqlmd + '></td>' +
                         '<td>' + "呼气灵敏度" + '</td>' +
                         '<td><input value=' + d.cureData.hqlmd + '></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + "压力上升坡度" + '</td>' +
                         '<td><input value=' + d.cureData.ylsspd + '></td>' +
                         '<td>' + "压力下降坡度" + '</td>' +
                         '<td><input value=' + d.cureData.ylxjpd + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "AVAPS" + '</td>' +
                         '<td><input value=' + d.cureData.avaps + '></td>' +
                         '<td>' + "目标潮气量" + '</td>' +
                         '<td><input value=' + d.cureData.mbcql + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "最大吸气压力" + '</td>' +
                         '<td><input value=' + d.cureData.zdxqyl + '></td>' +
                         '<td>' + "最小吸气压力" + '</td>' +
                         '<td><input value=' + d.cureData.zxxqyl + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "呼气释放" + '</td>' +
                         '<td><input value=' + d.cureData.hqsf + '></td>' +
                         '</tr>' +
                         '</table>'
                     );
                     break;
                 case "S-Auto":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +

                         '<tr> ' +
                         '<td>' + "吸气压力" + '</td>' +
                         '<td><input value=' + d.cureData.xqyl + '></td>' +
                         '<td>' + "呼气压力" + '</td>' +
                         '<td><input value=' + d.cureData.hqyl + '></td>' +
                         '</tr>' +

                         '<tr> '+
                         '<td>' + "最大吸气压力" + '</td>' +
                         '<td><input value=' + d.cureData.zdxqyl + '></td>' +
                         '<td>' + "呼气释放" + '</td>' +
                         '<td><input value=' + d.cureData.hqsf + '></td>' +
                         '</tr>'+


                         '<tr> '+
                         '<td>' + "开始压力" + '</td>' +
                         '<td><input value=' + d.cureData.ksyl + '></td>' +
                         '<td>' + "延迟时间" + '</td>' +
                         '<td><input value=' + d.cureData.ycsj + '></td>' +
                         '</tr>'+

                         '<tr> '+
                         '<td>' + "吸气灵敏度" + '</td>' +
                         '<td><input value=' + d.cureData.xqlmd + '></td>' +
                         '<td>' + "呼气灵敏度" + '</td>' +
                         '<td><input value=' + d.cureData.hqlmd + '></td>' +
                         '</tr>'+
                         '<tr> '+
                         '<td>' + "压力上升坡度" + '</td>' +
                         '<td><input value=' + d.cureData.ylsspd + '></td>' +
                         '<td>' + "压力下降坡度" + '</td>' +
                         '<td><input value=' + d.cureData.ylxjpd + '></td>' +
                         '</tr>' +
                         '</table>'
                     );
                     break;
                 case "T":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +
                         '<tr> ' +
                         '<td>' + "吸气压力" + '</td>' +
                         '<td><input value=' + d.cureData.xqyl + '></td>' +
                         '<td>' + "呼气压力" + '</td>' +
                         '<td><input value=' + d.cureData.hqyl + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "开始压力" + '</td>' +
                         '<td><input value=' + d.cureData.ksyl + '></td>' +
                         '<td>' + "延迟时间" + '</td>' +
                         '<td><input value=' + d.cureData.ycsj + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "呼吸频率" + '</td>' +
                         '<td><input value=' + d.cureData.hxpl + '></td>' +
                         '<td>' + "吸气时间" + '</td>' +
                         '<td><input value=' + d.cureData.xqsj + '></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + "吸气灵敏度" + '</td>' +
                         '<td><input value=' + d.cureData.xqlmd + '></td>' +
                         '<td>' + "呼气灵敏度" + '</td>' +
                         '<td><input value=' + d.cureData.hqlmd + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "最大吸气压力" + '</td>' +
                         '<td><input value=' + d.cureData.zdxqyl + '></td>' +
                         '<td>' + "最小吸气压力" + '</td>' +
                         '<td><input value=' + d.cureData.zxxqyl + '></td>' +
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

                         '<tr> ' +
                         '<td>' + "呼气释放" + '</td>' +
                         '<td>' + d.cureData.hqsf + '</td>' +
                         '</tr>' +
                         '</table>'
                     );
                     break;

                 case "S/T":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +
                         '<tr> ' +
                         '<td>' + "吸气压力" + '</td>' +
                         '<td>' + d.cureData.xqyl + '</td>' +
                         '<td>' + "呼气压力" + '</td>' +
                         '<td>' + d.cureData.hqyl + '</td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "开始压力" + '</td>' +
                         '<td>' + d.cureData.ksyl + '</td>' +
                         '<td>' + "延迟时间" + '</td>' +
                         '<td>' + d.cureData.ycsj + '</td>' +
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

                         '<tr> ' +
                         '<td>' + "呼气释放" + '</td>' +
                         '<td>' + d.cureData.hqsf + '</td>' +
                         '</tr>' +
                         '</table>');
                     break;
             }
         },
         error: function () {
             console.log("error");
         }
     })



     $('#setPara').click(function () {



         var modeSel=$("#modeSel").find("option:selected").text();

         // 呼气释放breatheRel
         // 治疗压力cureStress
         // 开始压力starStree
         // 延迟时间delayTime
         // 最大压力maxStree
         // 最小压力minStree
         var dateArr;
         switch (modeSel.trim()){
             case "CPAP":
                 dateArr = {
                     serial:serialId,
                     mode:modeSel,
                     cureStress:$("#cureStress").val(),
                     starStress:$("#starStress").val(),
                     breatheRel:$("#breatheRel").val(),
                     delayTime:$("#delayTime").val(),
                     ipAddr:ipAddr
                 };
                 break;
             case "APAP":
                 // alert(serialId);
                 // alert(modeSel);
                 // alert($("#maxStress").val());
                 // alert($("#minStress").val());
                 // alert($("#starStress").val());
                 // alert($("#delayTime").val());
                 // alert($("#breatheRel").val());
                 dateArr = {
                     serial:serialId,
                     mode:modeSel,
                     maxStress:$("#maxStress").val(),
                     minStress:$("#minStress").val(),
                     starStress:$("#starStress").val(),
                     delayTime:$("#delayTime").val(),
                     breatheRel:$("#breatheRel").val(),
                     ipAddr:ipAddr
                 };
                 break;
         }
         $.ajax({
             type: "POST",
             url: "/sys/device/testNetty",
             dataType: "json",
             data: dateArr,
             success: function (result) {
                 console.log("发送数据请求成功");
                 console.log(result.msg);
             }
         })
     })


 })





 /**
  * 获取参数值
  * @param url
  * @param name
  * @returns {*}
  */

 function getUrlParam(url,name) {
     var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
     var matcher = pattern.exec(url);
     var items = null;
     if (null != matcher) {
         try {
             items = decodeURIComponent(decodeURIComponent(matcher[1]));
         } catch (e) {
             try {
                 items = decodeURIComponent(matcher[1]);
             } catch (e) {
                 items = matcher[1];
             }
         }
     }
     return items;
 }


 /**
  * 切换事件
  */
 function changeMode() {

     console.log("触发切换时间");
     var modeSel=$("#modeSel").find("option:selected").text();
     console.log(modeSel);


     var table = $("#paraList");
     $("#paraList").empty(); //清空table（除了第一行以外）

     switch (modeSel){
         case "CPAP":
             table.append(
                 '<table class="table" id="setParaTable" cellspacing="30" >' +
                 '<h4><b>设备参数</b></h4>' +
                 '<tr> ' +
                 '<td>' + "模式" + '</td>' +
                 '<td><select id="modeSel" onchange="changeMode()">\n' +
                 '<option value="0" selected="selected">CPAP</option>'+
                 '  <option>APAP</option>\n' +
                 // '  <option>S</option>\n' +
                 // '  <option>S-Auto</option>\n' +
                 // '  <option>T</option>\n' +
                 // '  <option>S/T</option>\n' +
                 '</select></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + "呼气释放" + '</td>' +
                 '<td><input id="breatheRel" ></td>' +
                 '<td>' + "治疗压力" + '</td>' +
                 '<td><input id="cureStress" ></td>' +
                 '</tr>' +
                 '<tr> ' +
                 '<td>' + "开始压力" + '</td>' +
                 '<td><input id="starStress" ></td>' +
                 '<td>' + "延迟时间" + '</td>' +
                 '<td><input id="delayTime" ></td>' +
                 '</tr>' +
                 '</table>'
             );
             break;
         case "APAP":
             table.append(
                 '<table class="table" id="setParaTable" cellspacing="30" >' +
                 '<h4><b>设备参数</b></h4>' +

                 '<tr> ' +
                 '<td>' + "模式" + '</td>' +
                 '<td><select id="modeSel" onchange="changeMode()">\n' +
                 '<option value="0" selected="selected">APAP</option>'+
                 '  <option>CPAP</option>\n' +
                 // '  <option>S</option>\n' +
                 // '  <option>S-Auto</option>\n' +
                 // '  <option>T</option>\n' +
                 // '  <option>S/T</option>\n' +
                 '</select></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + "开始压力" + '</td>' +
                 '<td><input id="starStress" ></td>' +
                 '<td>' + "延迟时间" + '</td>' +
                 '<td><input id="delayTime" ></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + "最大压力" + '</td>' +
                 '<td><input id="maxStress" ></td>' +
                 '<td>' + "最小压力" + '</td>' +
                 '<td><input id="minStress" ></td>' +
                 '</tr>' +
                 '<tr> ' +
                 '<td>' + "呼气释放" + '</td>' +
                 '<td><input id="breatheRel" ></td>' +
                 '</tr>' +

                 '</table>'
             );
             break;
         default:
             alert("出现错误");
             break;
     }
 }
