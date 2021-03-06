 $(function () {


     /**
      * 请求获取json数据
      */
     $.ajax({
         url: "/json/language.json",//json文件位置
         type: "GET",//请求方式为get
         dataType: "json", //返回数据格式为json
         async:false,
         success: function(data) {//请求成功完成后要执行的方法
             if (getCookie('lang') == "zh-cn" || getCookie('lang') == "zh_cn") {
                 languageData = data.zh;
                 console.log("选择了中文==="+languageData);
             } else if (getCookie('lang') == "en-us" || getCookie('lang') == "en_us") {
                 languageData = data.en;
                 console.log("选择了英文==="+languageData);

             }
         }
     })

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


     //定义全局变量
     var ipAddr;

     var serialId = getUrlParam(window.location.href,'serialId');


     var cureMode ;
     $.ajax({
         type: "POST",
         url: "/sys/paraSet/getParaDetails",
         dataType: "json",
         data: {
             "serialId": serialId
         },//数据，这里使用的是Json格式进行传输
         success: function (result) {//返回数据根据结果进行相应的处理
             if (result.code <0){
                 dialogMsg(result.msg);
                 window.history.back(-1);
             }else{



             var table = $("#paraList");
             var paraString = result.para;
             var paraArr = paraString.split(",");

             switch (paraArr[4]) {
                 case "CPAP":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +
                         '<h4><b>'+languageData.devicePara+'</b></h4>' +

                         '<tr> ' +
                         '<td>' + languageData.mode + '</td>' +
                         '<td><select id="modeSel" onchange="changeMode()">\n' +
                         '<option value="APAP">APAP</option>' +
                         '<option value ="CPAP" selected="selected">CPAP</option>' +
                         '  <option value="S">S</option>\n' +
                         '  <option value="S-Auto">S-Auto</option>\n' +
                         '  <option value="T">T</option>\n' +
                         '  <option value="S/T">S/T</option>\n' +
                         '</select></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + languageData.hqsf + '</td>' +
                         '<td><input id="breatheRel" value=' + paraArr[17]+ '></td>' +
                         '<td>' +  languageData.zlyl + '</td>' +
                         '<td><input id="cureStress" value=' + paraArr[5] + '></td>' +
                         '</tr>' +
                         '<tr> ' +
                         '<td>' + languageData.ksyl + '</td>' +
                         '<td><input id="starStress" value=' + paraArr[6] + '></td>' +
                         '<td>' + languageData.ycsj + '</td>' +
                         '<td><input id="delayTime" value=' + paraArr[7] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "面罩类型" + '</td>' +
                         '<td><input id="starStress" value=' + paraArr[6] + '></td>' +
                         '<td>' + "智能启动" + '</td>' +
                         '<td><input id="delayTime" value=' + paraArr[7] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "漏气提示" + '</td>' +
                         '<td><input id="starStress" value=' + paraArr[6] + '></td>'+
                         '</tr>'+
                         '</table>'
                     );
                     break;
                 case "APAP":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +
                         '<h4><b>'+languageData.devicePara+'</b></h4>' +


                         '<tr> ' +
                         '<td>' + languageData.mode + '</td>' +
                         '<td><select id="modeSel" onchange="changeMode()">\n' +
                         '<option value="APAP" selected="selected">APAP</option>' +
                         '<option value ="CPAP">CPAP</option>' +
                         '  <option value="S">S</option>\n' +
                         '  <option value="S-Auto">S-Auto</option>\n' +
                         '  <option value="T">T</option>\n' +
                         '  <option value="S/T">S/T</option>\n' +
                         '</select></td>' +
                         '</tr>' +




                         '<tr> ' +
                         '<td>' + languageData.ksyl + '</td>' +
                         '<td><input id="starStress" value=' + paraArr[6] + '></td>' +
                         '<td>' + languageData.ycsj + '</td>' +
                         '<td><input id="delayTime" value=' + paraArr[7] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.maxKpa + '</td>' +
                         '<td><input id="maxStress" value=' + paraArr[10] + '></td>' +
                         '<td>' + languageData.minKpa + '</td>' +
                         '<td><input id="minStress" value=' + paraArr[1] + '></td>' +
                         '</tr>' +
                         '<tr> ' +
                         '<td>' + languageData.hqsf + '</td>' +
                         '<td><input id="breatheRel" value=' + paraArr[17] + '></td>' +
                         '</tr>' +

                         '</table>'
                     );
                     break;

                 case "S":

                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +

                         '<tr> ' +
                         '<td>' + languageData.mode + '</td>' +
                         '<td><select id="modeSel" onchange="changeMode()">\n' +
                         '<option value="APAP" >APAP</option>' +
                         '<option value ="CPAP">CPAP</option>' +
                         '  <option value="S" selected="selected">S</option>\n' +
                         '  <option value="S-Auto">S-Auto</option>\n' +
                         '  <option value="T">T</option>\n' +
                         '  <option value="S/T">S/T</option>\n' +
                         '</select></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + languageData.xqyl + '</td>' +
                         '<td><input id="" value=' + paraArr[14] + '></td>' +
                         '<td>' + languageData.hqyl + '</td>' +
                         '<td><input value=' + paraArr[15] + '></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + languageData.ksyl + '</td>' +
                         '<td><input value=' + paraArr[6] + '></td>' +
                         '<td>' + languageData.ycsj + '</td>' +
                         '<td><input value=' + paraArr[7] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.xqlmd + '</td>' +
                         '<td><input value=' + paraArr[18] + '></td>' +
                         '<td>' + languageData.hqlmd + '</td>' +
                         '<td><input value=' + paraArr[19] + '></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + languageData.ylsspd + '</td>' +
                         '<td><input value=' + paraArr[20] + '></td>' +
                         '<td>' + languageData.ylxjpd + '</td>' +
                         '<td><input value=' + paraArr[21]  + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "AVAPS" + '</td>' +
                         '<td><input value=' + paraArr[22] + '></td>' +
                         '<td>' + languageData.mbcql + '</td>' +
                         '<td><input value=' + paraArr[14] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.zdxqyl + '</td>' +
                         '<td><input value=' + paraArr[10] + '></td>' +
                         '<td>' + languageData.zxxqyl + '</td>' +
                         '<td><input value=' + paraArr[11] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.hqsf + '</td>' +
                         '<td><input value=' + paraArr[17] + '></td>' +
                         '</tr>' +
                         '</table>'
                     );
                     break;
                 case "S-Auto":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +


                         '<tr> ' +
                         '<td>' + languageData.mode + '</td>' +
                         '<td><select id="modeSel" onchange="changeMode()">\n' +
                         '<option value="APAP" >APAP</option>' +
                         '<option value ="CPAP">CPAP</option>' +
                         '  <option value="S">S</option>\n' +
                         '  <option value="S-Auto" selected="selected">S-Auto</option>\n' +
                         '  <option value="T">T</option>\n' +
                         '  <option value="S/T">S/T</option>\n' +
                         '</select></td>' +
                         '</tr>' +




                         '<tr> ' +
                         '<td>' + languageData.xqyl + '</td>' +
                         '<td><input value=' + paraArr[12] + '></td>' +
                         '<td>' + languageData.hqyl + '</td>' +
                         '<td><input value=' + paraArr[13] + '></td>' +
                         '</tr>' +

                         '<tr> '+
                         '<td>' + languageData.zdxqyl + '</td>' +
                         '<td><input value=' + paraArr[10] + '></td>' +
                         '<td>' + languageData.hqsf + '</td>' +
                         '<td><input value=' + paraArr[11] + '></td>' +
                         '</tr>'+


                         '<tr> '+
                         '<td>' + languageData.ksyl + '</td>' +
                         '<td><input value=' + paraArr[6] + '></td>' +
                         '<td>' + languageData.ycsj + '</td>' +
                         '<td><input value=' + paraArr[7] + '></td>' +
                         '</tr>'+

                         '<tr> ' +
                         '<td>' + languageData.xqlmd + '</td>' +
                         '<td><input value=' + paraArr[18] + '></td>' +
                         '<td>' + languageData.hqlmd + '</td>' +
                         '<td><input value=' + paraArr[19] + '></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + languageData.ylsspd + '</td>' +
                         '<td><input value=' + paraArr[20] + '></td>' +
                         '<td>' + languageData.ylxjpd + '</td>' +
                         '<td><input value=' + paraArr[21] + '></td>' +
                         '</tr>' +
                         '</table>'
                     );
                     break;
                 case "T":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +

                         '<tr> ' +
                         '<td>' + languageData.mode + '</td>' +
                         '<td><select id="modeSel" onchange="changeMode()">\n' +
                         '<option value="APAP" >APAP</option>' +
                         '<option value ="CPAP">CPAP</option>' +
                         '  <option value="S">S</option>\n' +
                         '  <option value="S-Auto">S-Auto</option>\n' +
                         '  <option value="T" selected="selected" >T</option>\n' +
                         '  <option value="S/T">S/T</option>\n' +
                         '</select></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + languageData.xqyl + '</td>' +
                         '<td><input value=' + paraArr[12] + '></td>' +
                         '<td>' + languageData.hqyl + '</td>' +
                         '<td><input value=' + paraArr[13] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.ksyl + '</td>' +
                         '<td><input value=' + paraArr[6] + '></td>' +
                         '<td>' + languageData.ycsj + '</td>' +
                         '<td><input value=' + paraArr[7] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.hxpl + '</td>' +
                         '<td><input value=' + paraArr[15] + '></td>' +
                         '<td>' + languageData.xqsj + '</td>' +
                         '<td><input value=' + paraArr[16] + '></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + languageData.xqlmd + '</td>' +
                         '<td><input value=' + paraArr[18] + '></td>' +
                         '<td>' + languageData.hqlmd + '</td>' +
                         '<td><input value=' + paraArr[19] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.zdxqyl + '</td>' +
                         '<td><input value=' + paraArr[10] + '></td>' +
                         '<td>' + languageData.zxxqyl + '</td>' +
                         '<td><input value=' + paraArr[11] + '></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + languageData.ylsspd + '</td>' +
                         '<td><input value=' + paraArr[20] + '></td>' +
                         '<td>' + languageData.ylxjpd + '</td>' +
                         '<td><input value=' + paraArr[21] + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "AVAPS" + '</td>' +
                         '<td><input value=' + paraArr[22] + '></td>' +
                         '<td>' + languageData.mbcql + '</td>' +
                         '<td><input value=' + paraArr[14] + '></td>' +
                         '</tr>' +


                         '<tr> ' +
                         '<td>' + languageData.hqsf + '</td>' +
                         '<td>' + paraArr[17] + '</td>' +
                         '</tr>' +
                         '</table>'
                     );
                     break;

                 case "S/T":
                     table.append(
                         '<table class="table" id="setParaTable" cellspacing="30" >' +

                         '<tr> ' +
                         '<td>' + languageData.mode + '</td>' +
                         '<td><select id="modeSel" onchange="changeMode()">\n' +
                         '<option value="APAP" >APAP</option>' +
                         '<option value ="CPAP">CPAP</option>' +
                         '  <option value="S">S</option>\n' +
                         '  <option value="S-Auto">S-Auto</option>\n' +
                         '  <option value="T"  >T</option>\n' +
                         '  <option value="S/T" selected="selected">S/T</option>\n' +
                         '</select></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.xqyl + '</td>' +
                         '<td><input value=' + d.cureData.xqyl + '></td>' +
                         '<td>' + languageData.hqyl + '</td>' +
                         '<td><input value=' + d.cureData.hqyl + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.ksyl + '</td>' +
                         '<td><input value=' + d.cureData.ksyl + '></td>' +
                         '<td>' + languageData.ycsj + '</td>' +
                         '<td><input value=' + d.cureData.ycsj + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.hxpl + '</td>' +
                         '<td><input value=' + d.cureData.hxpl + '></td>' +
                         '<td>' + languageData.xqsj + '</td>' +
                         '<td><input value=' + d.cureData.xqsj + '></td>' +
                         '</tr>' +



                         '<tr> ' +
                         '<td>' + languageData.xqlmd + '</td>' +
                         '<td><input value=' + d.cureData.xqlmd + '></td>' +
                         '<td>' + languageData.hqlmd + '</td>' +
                         '<td><input value=' + d.cureData.hqlmd + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.zdxqyl + '</td>' +
                         '<td><input value=' + d.cureData.zdxqyl + '></td>' +
                         '<td>' + languageData.zxxqyl + '</td>' +
                         '<td><input value=' + d.cureData.zxxqyl + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.ylsspd + '</td>' +
                         '<td>' + d.cureData.ylsspd + '</td>' +
                         '<td>' + languageData.ylxjpd + '</td>' +
                         '<td>' + d.cureData.ylxjpd + '</td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + "AVAPS" + '</td>' +
                         '<td>' + d.cureData.avaps + '</td>' +
                         '<td>' + languageData.mbcql + '</td>' +
                         '<td>' + d.cureData.mbcql + '</td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.zdxqyl + '</td>' +
                         '<td><input value=' + d.cureData.zdxqyl + '></td>' +
                         '<td>' + languageData.zxxqyl + '</td>' +
                         '<td><input value=' + d.cureData.zxxqyl + '></td>' +
                         '</tr>' +

                         '<tr> ' +
                         '<td>' + languageData.hqsf + '</td>' +
                         '<td>' + d.cureData.hqsf + '</td>' +
                         '</tr>' +
                         '</table>');
                     break;
             }
             }
         },
         error: function (result) {
             console.log(result.msg);
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
                 '<h4><b>'+languageData.devicePara+'</b></h4>' +

                 '<tr> ' +
                 '<td>' + languageData.mode + '</td>' +
                 '<td><select id="modeSel" onchange="changeMode()">\n' +
                 '<option value="APAP">APAP</option>' +
                 '<option value ="CPAP" selected="selected">CPAP</option>' +
                 '  <option value="S">S</option>\n' +
                 '  <option value="S-Auto">S-Auto</option>\n' +
                 '  <option value="T">T</option>\n' +
                 '  <option value="S/T">S/T</option>\n' +
                 '</select></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + languageData.hqsf + '</td>' +
                 '<td><input id="breatheRel" value=' + '></td>' +
                 '<td>' +  languageData.zlyl + '</td>' +
                 '<td><input id="cureStress" value='  + '></td>' +
                 '</tr>' +
                 '<tr> ' +
                 '<td>' + languageData.ksyl + '</td>' +
                 '<td><input id="starStress" value='  + '></td>' +
                 '<td>' + languageData.ycsj + '</td>' +
                 '<td><input id="delayTime" value='  + '></td>' +
                 '</tr>' +
                 '</table>'
             );
             break;
         case "APAP":
             table.append(
                 '<table class="table" id="setParaTable" cellspacing="30" >' +
                 '<h4><b>'+languageData.devicePara+'</b></h4>' +



                 '<tr> ' +
                 '<td>' + languageData.mode + '</td>' +
                 '<td><select id="modeSel" onchange="changeMode()">\n' +
                 '<option value="APAP" selected="selected">APAP</option>' +
                 '<option value ="CPAP">CPAP</option>' +
                 '  <option value="S">S</option>\n' +
                 '  <option value="S-Auto">S-Auto</option>\n' +
                 '  <option value="T">T</option>\n' +
                 '  <option value="S/T">S/T</option>\n' +
                 '</select></td>' +
                 '</tr>' +




                 '<tr> ' +
                 '<td>' + languageData.ksyl + '</td>' +
                 '<td><input id="starStress"></td>' +
                 '<td>' + languageData.ycsj + '</td>' +
                 '<td><input id="delayTime"></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.maxKpa + '</td>' +
                 '<td><input id="maxStress" value=' + paraArr[10] + '></td>' +
                 '<td>' + languageData.minKpa + '</td>' +
                 '<td><input id="minStress"></td>' +
                 '</tr>' +
                 '<tr> ' +
                 '<td>' + languageData.hqsf + '</td>' +
                 '<td><input id="breatheRel"></td>' +
                 '</tr>' +

                 '</table>'
             );
             break;

         case "S":

             table.append(
                 '<table class="table" id="setParaTable" cellspacing="30" >' +

                 '<tr> ' +
                 '<td>' + languageData.mode + '</td>' +
                 '<td><select id="modeSel" onchange="changeMode()">\n' +
                 '<option value="APAP" >APAP</option>' +
                 '<option value ="CPAP">CPAP</option>' +
                 '  <option value="S" selected="selected">S</option>\n' +
                 '  <option value="S-Auto">S-Auto</option>\n' +
                 '  <option value="T">T</option>\n' +
                 '  <option value="S/T">S/T</option>\n' +
                 '</select></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + languageData.xqyl + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.hqyl + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + languageData.ksyl + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.ycsj + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.xqlmd + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.hqlmd + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + languageData.ylsspd + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.ylxjpd + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + "AVAPS" + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.mbcql + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.zdxqyl + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.zxxqyl + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.hqsf + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +
                 '</table>'
             );
             break;
         case "S-Auto":
             table.append(
                 '<table class="table" id="setParaTable" cellspacing="30" >' +


                 '<tr> ' +
                 '<td>' + languageData.mode + '</td>' +
                 '<td><select id="modeSel" onchange="changeMode()">\n' +
                 '<option value="APAP" >APAP</option>' +
                 '<option value ="CPAP">CPAP</option>' +
                 '  <option value="S">S</option>\n' +
                 '  <option value="S-Auto" selected="selected">S-Auto</option>\n' +
                 '  <option value="T">T</option>\n' +
                 '  <option value="S/T">S/T</option>\n' +
                 '</select></td>' +
                 '</tr>' +




                 '<tr> ' +
                 '<td>' + languageData.xqyl + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.hqyl + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +

                 '<tr> '+
                 '<td>' + languageData.zdxqyl + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.hqsf + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>'+


                 '<tr> '+
                 '<td>' + languageData.ksyl + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.ycsj + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>'+

                 '<tr> ' +
                 '<td>' + languageData.xqlmd + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.hqlmd + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + languageData.ylsspd + '</td>' +
                 '<td><input id=""></td>' +
                 '<td>' + languageData.ylxjpd + '</td>' +
                 '<td><input id=""></td>' +
                 '</tr>' +
                 '</table>'
             );
             break;
         case "T":
             table.append(
                 '<table class="table" id="setParaTable" cellspacing="30" >' +

                 '<tr> ' +
                 '<td>' + languageData.mode + '</td>' +
                 '<td><select id="modeSel" onchange="changeMode()">\n' +
                 '<option value="APAP" >APAP</option>' +
                 '<option value ="CPAP">CPAP</option>' +
                 '  <option value="S">S</option>\n' +
                 '  <option value="S-Auto">S-Auto</option>\n' +
                 '  <option value="T" selected="selected" >T</option>\n' +
                 '  <option value="S/T">S/T</option>\n' +
                 '</select></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + languageData.xqyl + '</td>' +
                 '<td><input value=' + paraArr[12] + '></td>' +
                 '<td>' + languageData.hqyl + '</td>' +
                 '<td><input value=' + paraArr[13] + '></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.ksyl + '</td>' +
                 '<td><input value=' + paraArr[6] + '></td>' +
                 '<td>' + languageData.ycsj + '</td>' +
                 '<td><input value=' + paraArr[7] + '></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.hxpl + '</td>' +
                 '<td><input value=' + paraArr[15] + '></td>' +
                 '<td>' + languageData.xqsj + '</td>' +
                 '<td><input value=' + paraArr[16] + '></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + languageData.xqlmd + '</td>' +
                 '<td><input value=' + paraArr[18] + '></td>' +
                 '<td>' + languageData.hqlmd + '</td>' +
                 '<td><input value=' + paraArr[19] + '></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.zdxqyl + '</td>' +
                 '<td><input value=' + paraArr[10] + '></td>' +
                 '<td>' + languageData.zxxqyl + '</td>' +
                 '<td><input value=' + paraArr[11] + '></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + languageData.ylsspd + '</td>' +
                 '<td><input value=' + paraArr[20] + '></td>' +
                 '<td>' + languageData.ylxjpd + '</td>' +
                 '<td><input value=' + paraArr[21] + '></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + "AVAPS" + '</td>' +
                 '<td><input value=' + paraArr[22] + '></td>' +
                 '<td>' + languageData.mbcql + '</td>' +
                 '<td><input value=' + paraArr[14] + '></td>' +
                 '</tr>' +


                 '<tr> ' +
                 '<td>' + languageData.hqsf + '</td>' +
                 '<td>' + paraArr[17] + '</td>' +
                 '</tr>' +
                 '</table>'
             );
             break;

         case "S/T":
             table.append(
                 '<table class="table" id="setParaTable" cellspacing="30" >' +

                 '<tr> ' +
                 '<td>' + languageData.mode + '</td>' +
                 '<td><select id="modeSel" onchange="changeMode()">\n' +
                 '<option value="APAP" >APAP</option>' +
                 '<option value ="CPAP">CPAP</option>' +
                 '  <option value="S">S</option>\n' +
                 '  <option value="S-Auto">S-Auto</option>\n' +
                 '  <option value="T"  >T</option>\n' +
                 '  <option value="S/T" selected="selected">S/T</option>\n' +
                 '</select></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.xqyl + '</td>' +
                 '<td><input value=' + d.cureData.xqyl + '></td>' +
                 '<td>' + languageData.hqyl + '</td>' +
                 '<td><input value=' + d.cureData.hqyl + '></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.ksyl + '</td>' +
                 '<td><input value=' + d.cureData.ksyl + '></td>' +
                 '<td>' + languageData.ycsj + '</td>' +
                 '<td><input value=' + d.cureData.ycsj + '></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.hxpl + '</td>' +
                 '<td><input value=' + d.cureData.hxpl + '></td>' +
                 '<td>' + languageData.xqsj + '</td>' +
                 '<td><input value=' + d.cureData.xqsj + '></td>' +
                 '</tr>' +



                 '<tr> ' +
                 '<td>' + languageData.xqlmd + '</td>' +
                 '<td><input value=' + d.cureData.xqlmd + '></td>' +
                 '<td>' + languageData.hqlmd + '</td>' +
                 '<td><input value=' + d.cureData.hqlmd + '></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.zdxqyl + '</td>' +
                 '<td><input value=' + d.cureData.zdxqyl + '></td>' +
                 '<td>' + languageData.zxxqyl + '</td>' +
                 '<td><input value=' + d.cureData.zxxqyl + '></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.ylsspd + '</td>' +
                 '<td>' + d.cureData.ylsspd + '</td>' +
                 '<td>' + languageData.ylxjpd + '</td>' +
                 '<td>' + d.cureData.ylxjpd + '</td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + "AVAPS" + '</td>' +
                 '<td>' + d.cureData.avaps + '</td>' +
                 '<td>' + languageData.mbcql + '</td>' +
                 '<td>' + d.cureData.mbcql + '</td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.zdxqyl + '</td>' +
                 '<td><input value=' + d.cureData.zdxqyl + '></td>' +
                 '<td>' + languageData.zxxqyl + '</td>' +
                 '<td><input value=' + d.cureData.zxxqyl + '></td>' +
                 '</tr>' +

                 '<tr> ' +
                 '<td>' + languageData.hqsf + '</td>' +
                 '<td>' + d.cureData.hqsf + '</td>' +
                 '</tr>' +
                 '</table>');
             break;
         default:
             alert("出现错误");
             break;
     }

 }
