var SearchData = {
    "startTime": "",
    "endTime": "",
    "orderId": "",
    "memberName": "",
    "memberCardNo": "",
    "phone": "",
    "branchId": "",
    "cardType": "",
}; //查询条件

function exportchart() {//导出门店报表
 /*   var statementSearchData = {
        "startTime": $.trim($("#choeStartData").val()),//开始时间
        "endTime": $.trim($("#choeEndData").val()),//结束时间
        "branchId": $.trim($("#Choose-StoreID").val()),//门店ID,
    };*/
    
    $("#textonecheart1").val($.trim($("#choeStartData").val()));
    $("#textonecheart2").val($.trim($("#choeEndData").val()));
    $("#textonecheart3").val($.trim($("#Choose-StoreID").val()));
    $("#formchartid").submit();
}
function exportLoad() {//导出
    //console.log(SearchData)
    //SearchData
    $("#textone").val(JSON.stringify(SearchData));
    $("#formid").submit();
}
function dataTime() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var lastmonth = date.getMonth();
    var strDate = date.getDate();
    var Hours = date.getHours();
    var Minutes = date.getMinutes();
    var Seconds = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (Hours >= 0 && Hours <= 9) {
        Hours = "0" + Hours;
    }
    if (Minutes >= 0 && Minutes <= 9) {
        Minutes = "0" + Minutes;
    }
    if (Seconds >= 0 && Seconds <= 9) {
        Seconds = "0" + Seconds;
    }
    var nowdata = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + Hours + seperator2 + Minutes
            + seperator2 + Seconds;
    var nowdys = date.getFullYear() + seperator1 + month + seperator1 + strDate//不带时分秒

    var now = new Date(nowdys.replace(/\-/g, "/"));
    var perMonth = new Date(now.setMonth(now.getMonth() - 1));//当前时间减去一月
    var perMonth1 = new Date(perMonth.setDate(perMonth.getDate()));//上月时间减去一天

    var lastdateTimemonth = perMonth1.getMonth() + 1;
    var lastdateTimestrDate = perMonth1.getDate();
    if (lastdateTimemonth <= 9) lastdateTimemonth = "0" + lastdateTimemonth;
    if (lastdateTimestrDate <= 9) lastdateTimestrDate = "0" + lastdateTimestrDate;

    if (lastdateTimemonth == 12) {
        var lastdata = date.getFullYear() - 1 + seperator1 + lastdateTimemonth + seperator1 + lastdateTimestrDate + " " + "00:00:01"
    }
    else {
        var lastdata = date.getFullYear() + seperator1 + lastdateTimemonth + seperator1 + lastdateTimestrDate + " " + "00:00:01"
    }
    $("#StartData").val(lastdata);
    $("#EndData").val(nowdata);
    $("#choeStartData").val(lastdata);
    $("#choeEndData").val(nowdata);
    SearchData = {
        "startTime": $("#StartData").val(),
        "endTime": $("#EndData").val(),
        "orderId": "",
        "memberName": "",
        "memberCardNo": "",
        "phone": "",
        "branchId": "",
        "cardType": "",
    };
}
function statementInfo() {//综合报表
    var witdh = $(".User-list").width();
    $("#statement-list").jqGrid({
        url: basePath + "/report/compositeReport",
        postData: { 'startTime': $("#choeStartData").val(), 'endTime': $("#choeEndData").val(), 'branchId': $("#Choose-StoreID").val() },
        datatype: "json",
        mtype: "get",
        width: witdh,
        //shrinkToFit: true,
        forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略。
        altRows: true,
        height: 'auto',
        colNames: ['卡号', '客户名称', '手机号码', '会员卡名称', '累计获得积分', '累计消费积分', '累计储值金额', '累计赠送金额', '累计消费金额', '累计消费次数'],
        colModel: [
            {
                name: 'cardno', index: 'cardno', align: "center",width: "340",
                formatter: function (value, rowData, obj) {
                    if (obj.cardType == 0) {
                        return "<font>-</font>"
                    }
                    else {
                        return "<font>" + value + "</font>"
                    }
                    //return "<font color='red'>" + value + "</font>";
                }

            },//卡号
            { name: 'name', index: 'name', align: "center", },//客户名称
            { name: 'mobile', index: 'mobile', align: "center", width: "230" },//手机号码
            { name: 'discount', index: 'discount', align: "center" },//会员卡名称
            { name: 'allPoint', index: 'allPoint', align: "center", },//累计获得积分
            { name: 'allConsumePoint', index: 'allConsumePoint', align: "center", },//累计消费积分
            { name: 'allValue', index: 'allValue', align: "center" },//累计储值金额
            { name: 'allPresent', index: 'allPresent', align: "center" },//累计赠送金额
            { name: 'allConsumeValue', index: 'allConsumeValue', align: "center" },//累计消费金额
            { name: 'countconsume', index: 'countconsume', align: "center" },//累计消费次数

        ],
        rownumbers: true,
        gridview: true,
        grouping: true,
        pager: "#statementInfo",
        rowNum: 10,//gridParameters.grid_rowNum,
        rowList: [10, 30, 50], //gridParameters.grid_rowList,
        prmNames: {
            search: "search"
        },
        jsonReader: {
            root: "data", // (2)    json中代表实际模型数据的入口
            page: "page", // 当前页
            total: "total", //总页数
            records: "records", // 总记录数 (3)   json中代表数据行总数的数据 ，既总记录数
            repeatitems: false // (4)如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定
        },
        loadComplete: function () {//没有查询到符合条件的记录
            var re_records = $("#statement-list").getGridParam('records');
            if (re_records == 0 || re_records == null) {
                var str = '<div id="history-nodata" class=\"norecords1\" style="height:60px;padding-top:20px;">'
                str += ' <span class=" glyphicon glyphicon-info-sign" style="vertical-align: middle"></span>'
                str += ' <span style="font: 14px/1.6 Tahoma,microsoft yahei,"微软雅黑","宋体";padding-left:10px;">没有查询到符合条件的记录</span></div>'
                $("#statementInfo").hide();
                if ($(".norecords1").html() == null) {
                    $("#statement-list").parent().append(str);
                }
                else {
                    $(".norecords1").show();
                }
            }
            else {
                $(".norecords1").hide();
                $("#statementInfo").show()
            }
        },
        hidegrid: true,
        viewrecords: true,
        multiselect: false	 //是否支持多选
    });

}
function consumptionInfo() {//会员基本信息
    var witdh = $(".User-list").width();
    $("#info-list").jqGrid({
        url: basePath + "/consumption/transInfoSubmit",
        postData: { 'startTime': $("#StartData").val(), 'endTime': $("#EndData").val() },
        datatype: "json",
        mtype: "get",
        width: witdh,
        //shrinkToFit: true,
        forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略。
        altRows: true,
        height: 'auto',
        colNames: ['票据号', '卡号', '客户名称', '手机号码', '会员卡名称', '实收金额', '会员卡支付', '充值金额消费', '赠送金额消费', '现金消费', '积分消费', '交易时间', '交易门店id'],
        colModel: [
            //{ name: 'dealNo', index: 'dealNo',width:"200",  align: "center"},//交易流水号
            { name: 'slipNo', index: 'slipNo', width: "340", align: "center" },//票据单号
            {
                name: 'cardNo', index: 'cardNo', align: "center",
                formatter: function (value, rowData, obj) {
                    if (obj.cardType == 0) {
                        return "<font>-</font>"
                    }
                    else {
                        return "<font>" + value + "</font>"
                    }
                    //return "<font color='red'>" + value + "</font>";
                }

            },//卡号
            //{ name: 'dealName', index: 'dealName', align: "center" },//交易类型
            { name: 'memberName', index: 'memberName', align: "center", },//客户名称
            { name: 'mobile', index: 'mobile', align: "center", width: "230" },//电话
            { name: 'cardName', index: 'cardName', align: "center" },//会员卡名称
            {
                name: 'amount', index: 'amount', align: "center",
                /*formatter: function (value, rowData, obj) {
                    return "<font color='red'>" + value + "</font>";
                }*/
            },//实收金额
            { name: 'consumeValue', index: 'consumeValue', align: "center", },//会员卡支付
            { name: 'totalBalance', index: 'totalBalance', align: "center" },//充值金额消费
            { name: 'zstotalBalance', index: 'zstotalBalance', align: "center" },//赠送金额消费
            { name: 'cash', index: 'cash', align: "center" },//现金消费
            { name: 'consumePoint', index: 'consumePoint', align: "center" },//积分消费
            { name: 'dealTime', index: 'dealTime', width: "270", align: "center" },//交易时间
            { name: 'branchId', index: 'branchId', align: "center" },//门店编号
            //{ name: 'dealUser', index: 'dealUser',  align: "center" },//操作员
        ],
        rownumbers: true,
        gridview: true,
        grouping: true,
        pager: "#gridPagerInfo",
        rowNum: 10,//gridParameters.grid_rowNum,
        rowList: [10, 30, 50], //gridParameters.grid_rowList,
        prmNames: {
            search: "search"
        },
        jsonReader: {
            root: "resultList", // (2)    json中代表实际模型数据的入口
            page: "page", // 当前页
            total: "total", //总页数
            records: "records", // 总记录数 (3)   json中代表数据行总数的数据 ，既总记录数
            repeatitems: false // (4)如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定
        },
        loadComplete: function () {//没有查询到符合条件的记录
            var re_records = $("#info-list").getGridParam('records');
            if (re_records == 0 || re_records == null) {
                var str = '<div id="history-nodata" class=\"norecords\" style="height:60px;padding-top:20px;">'
                str += ' <span class=" glyphicon glyphicon-info-sign" style="vertical-align: middle"></span>'
                str += ' <span style="font: 14px/1.6 Tahoma,microsoft yahei,"微软雅黑","宋体";padding-left:10px;">没有查询到符合条件的记录</span></div>'
                $("#gridPagerInfo").hide();
                if ($(".norecords").html() == null) {
                    $("#info-list").parent().append(str);
                }
                else {
                    $(".norecords").show();
                }
            }
            else {
                $(".norecords").hide();
                $("#gridPagerInfo").show()
            }
        },
        hidegrid: true,
        viewrecords: true,
        multiselect: false	 //是否支持多选
    });

}


function Reset() {//重置
    var memberName = $.trim($("#username").val(""));//会员名称
    var memberCardNo = $.trim($("#usercardName").val(""));//会员卡号
    var phone = $.trim($("#phone").val(""));//电话
    var branchId = $.trim($("#storeNubner").val(""));//门店ID
    var cardType = $.trim($("#cardname").val(""));//会员卡名称
    $("#username_erro").remove();
    $("#point_erro").remove();
}

function Searchtext() {//绑定会员卡名称已经会员卡状态。
    $.ajax({
        url: basePath + "/consumption/findSearchInfo",
        type: "post",
        async: false,
        dataType: "json",
        success: function (data) {
            //console.log(data)
            for (var i = 0; i < data.cardTypeResult.name.length; i++) {
                var str = '<option value="' + data.cardTypeResult.name[i].value + '">' + data.cardTypeResult.name[i].name + '</option>';
                $("#cardname").append(str);
            }
            $("#cardname").val("");
            var str='';
            if(data.cardTypeResult.stores.length>1){
                str += '<option value="">全部</option>'
            }
            for (var i = 0; i < data.cardTypeResult.stores.length; i++) {
                str += '<option value="' + data.cardTypeResult.stores[i].value + '">' + data.cardTypeResult.stores[i].name + '</option>';
            }
            $("#storeNubner").append(str);
            $("#Choose-StoreID").append(str);
            $("#storeNubner").prop('selectedIndex', 0)//选择第一个门店
            $("#Choose-StoreID").prop('selectedIndex', 0)//选择第一个门店
        }
    });
}
function selectTime(obj) {
    var date = new Date();
    var Year = date.getFullYear();
    var Month = date.getMonth() + 1;
    var Day = date.getDate();
    var Hours = date.getHours();
    var Minutes = date.getMinutes();
    var Seconds = date.getSeconds();
    if (Month <= 9) Month = "0" + Month;
    if (Day <= 9) Day = "0" + Day;
    if (Hours <= 9) Hours = "0" + Hours;
    if (Minutes <= 9) Minutes = "0" + Minutes;
    if (Seconds <= 9) Seconds = "0" + Seconds;
    var nowDysEnd = Year + '-' + Month + '-' + Day + ' ' + Hours + ':' + Minutes + ':' + Seconds //今天结束
    var nowDysStartData = Year + '-' + Month + '-' + Day + ' ' + '00:00:01' //今天开始
    var yesterday = new Date(date.setTime(date.getTime() - 24 * 60 * 60 * 1000));
    var yesterdayStart = yesterday.getFullYear() + "-";
    if (yesterday.getMonth() + 1 <= 9) {
        yesterdayStart = yesterdayStart + "0" + (yesterday.getMonth() + 1) + '-'
    }
    else {
        yesterdayStart = yesterdayStart + (yesterday.getMonth() + 1) + '-'
    }
    if (yesterday.getDate() <= 9) {
        yesterdayStart = yesterdayStart + "0" + yesterday.getDate()
    }
    else {
        yesterdayStart = yesterdayStart + yesterday.getDate()
    }
    yesterdayStartone = yesterdayStart + " " + "00:00:01";//昨天开始
    var yesterdayEnd = yesterdayStart + " " + "23:59:59";//昨天结束


    var thismonthStart = Year + '-' + Month + '-' + '01' + ' ' + '00:00:01' //本月开始
    var thismonthEnd = nowDysEnd //本月结束
    var lastyear = Year;
    var lastmonth = Month - 1;
    if (lastmonth >= 1 && lastmonth <= 9) {
        lastmonth = "0" + lastmonth;
    }
    if (lastmonth == 0) {
        lastmonth = 12;
        lastyear = lastyear - 1
    }
    var lastD = new Date(lastyear, lastmonth, 0);   //Wed Mar 31 00:00:00 UTC+0800 2010  
    var lastDCount = lastD.getDate();            //结束日期上一月的天数
    var lastStartt = lastyear + "-" + lastmonth + "-" + "01" + " " + "00:00:01"
    var lastEnd = lastyear + "-" + lastmonth + "-" + lastDCount + " " + "23:59:59"
    var timetype = $(obj).attr("timetype");
    var nametype = $(obj).attr("nametype");


    if (nametype) {
        if (timetype == "day") {
            $("#choeStartData").val(nowDysStartData);
            $("#choeEndData").val(nowDysEnd);
        }
        if (timetype == "yesterday") {
            $("#choeStartData").val(yesterdayStartone);
            $("#choeEndData").val(yesterdayEnd);
        }
        if (timetype == "month") {
            $("#choeStartData").val(thismonthStart);
            $("#choeEndData").val(thismonthEnd);
        }
        if (timetype == "lastmonth") {
            $("#choeStartData").val(lastStartt);
            $("#choeEndData").val(lastEnd);
        }
        Choosesearch();
    }
    else {
        if (timetype == "day") {
            $("#StartData").val(nowDysStartData);
            $("#EndData").val(nowDysEnd);
        }
        if (timetype == "yesterday") {
            $("#StartData").val(yesterdayStartone);
            $("#EndData").val(yesterdayEnd);
        }
        if (timetype == "month") {
            $("#StartData").val(thismonthStart);
            $("#EndData").val(thismonthEnd);
        }
        if (timetype == "lastmonth") {
            $("#StartData").val(lastStartt);
            $("#EndData").val(lastEnd);
        }
        iconsearch();
    }

}
function iconsearch() {//搜索
	$(".xiaofeiinfo .no-radius ").removeClass("colorgreen");
    Reset()//清空
    $("#username_erro").remove();
    $("#point_erro").remove();
    //var startTime = '';//开始时间
    //var endTime = '';//结束时间
    var orderId = "";//订单号
    var memberName = '';//会员名称
    var memberCardNo = '';//会员卡号
    var phone = '';//电话
    var branchId = '';//门店ID
    var cardType = '';//会员卡名称
    var absc = /[\u4E00-\u9FA5]/g;//判断输入中文
    var htmlstr = /<\/?[^>]*>/g; //html标签
    var phoneNumber = /^1[3|4|5|7|8]\d{9}$/  //电话号码
    orderId = $.trim($("#OrderNumber").val());
    startTime = $.trim($("#StartData").val());
    endTime = $.trim($("#EndData").val());
    if (startTime != "" && endTime == "") {
        $("#username_erro").remove();
        $("#birthdayEnd").focus();
        if ($("#username_erro").size() < 1) {
            $("#EndData").after("<span id='username_erro' style='color:red;padding-left:10px;padding-top:5px;font-size:12px'>结束时间不能为空</span>");
        }
        return;
    }
    if (endTime != "" && startTime == "") {
        $("#username_erro").remove();
        //$("#birthdayStart").focus();
        if ($("#username_erro").size() < 1) {
            $("#EndData").after("<span id='username_erro' style='color:red;padding-left:10px;padding-top:5px;font-size:12px'>开始时间不能为空</span>");
        }
        return;
    }
    //显示查询结果
    var jsonDataSearch = {
        "startTime": startTime,
        "endTime": endTime,
        "orderId": orderId,
        "memberName": memberName,
        "memberCardNo": memberCardNo,
        "phone": phone,
        "branchId": branchId,
        "cardType": cardType,
    };
    SearchData = jsonDataSearch
    var postData = $("#info-list").jqGrid("getGridParam", "postData");
    //将查询参数融入postData选项对象
    $.extend(postData, jsonDataSearch);
    $("#info-list").jqGrid("setGridParam", {
        postData: jsonDataSearch, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
    }).trigger("reloadGrid", [{ page: 1 }]);

}

function heightsearch() {//高级搜索
	$(".xiaofeiinfo .no-radius ").removeClass("colorgreen");
    $("#OrderNumber").val("");
    $("#StartData").val("");
    $("#EndData").val("");
    $("#username_erro").remove();
    $("#point_erro").remove();
    var startTime = '';//开始时间
    var endTime = '';//结束时间
    var orderId = "";//订单号
    var memberName = $.trim($("#username").val());//会员名称
    var memberCardNo = $.trim($("#usercardName").val());//会员卡号
    var phone = $.trim($("#phone").val());//电话
    var branchId = $.trim($("#storeNubner").val());//门店ID
    var cardType = $.trim($("#cardname").val());//会员卡名称
    var absc = /[\u4E00-\u9FA5]/g;//判断输入中文
    var htmlstr = /<\/?[^>]*>/g; //html标签
    var phoneNumber = /^1[3|4|5|7|8]\d{9}$/  //电话号码
    if (memberName != "") {
        $("#username_erro").remove();
        if (htmlstr.test(memberName)) {
            $("#username").focus();
            if ($("#username_erro").size() < 1) {
                $("#username").parent().after("<div id='username_erro' style='color:red;padding-left:95px;padding-top:5px;'>请不要输入非法的字符</div>");
            }
            return;
        }
    };
    if (phone != "") {
        phoneNumber.lastIndex = 0;
        $("#point_erro").remove();
        if (!phoneNumber.test(phone)) {
            $("#phone").focus();
            if ($("#point_erro").size() < 1) {
                $("#phone").parent().after("<div id='point_erro' style='color:red;padding-left:95px;padding-top:5px;'>请输入正确的手机号码</div>");
            }
            return;
        }

    }

    $('#ScreenSearch').modal('hide');
    //显示查询结果
    var jsonDataSearch = {
        "startTime": startTime,
        "endTime": endTime,
        "orderId": orderId,
        "memberName": memberName,
        "memberCardNo": memberCardNo,
        "phone": phone,
        "branchId": branchId,
        "cardType": cardType,
    };
    SearchData = jsonDataSearch;
    var postData = $("#info-list").jqGrid("getGridParam", "postData");
    //将查询参数融入postData选项对象
    $.extend(postData, jsonDataSearch);
    $("#info-list").jqGrid("setGridParam", {
        postData: jsonDataSearch, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
    }).trigger("reloadGrid", [{ page: 1 }]);
}
$(".VIPtab li").click(function () {//选项卡切换
    $(".User-list").hide().eq($(this).index()).show();//显示隐藏DIV切换
    $(".VIPtab li").removeClass("cur");
    $(this).addClass("cur");

});
function Choosesearch() {//查询门店报表
	$("#choeEndData_erro").remove();
	$(".zhongheachrt .no-radius ").removeClass("colorgreen");
    var branchId = $.trim($("#Choose-StoreID").val());//门店ID
    var startTime = $.trim($("#choeStartData").val());//开始时间
    var endTime = $.trim($("#choeEndData").val());//结束时间
    if (startTime != "" && endTime == "") {
            $("#choeEndData").after("<span id='choeEndData_erro' style='color:red;padding-left:10px;padding-top:5px;font-size:12px'>结束时间不能为空</span>");
        return;
    }
    if (endTime != "" && startTime == "") {
            $("#choeEndData").after("<span id='choeEndData_erro' style='color:red;padding-left:10px;padding-top:5px;font-size:12px'>开始时间不能为空</span>");
        return;
    }
    collectText();//汇总信息方法
    //显示查询结果
    var jsonDataSearch = {
        "startTime": startTime,
        "endTime": endTime,
        "branchId": branchId,
    };
    var postData = $("#statement-list").jqGrid("getGridParam", "postData");
    //将查询参数融入postData选项对象
    $.extend(postData, jsonDataSearch);
    $("#statement-list").jqGrid("setGridParam", {
        postData: jsonDataSearch, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
    }).trigger("reloadGrid", [{ page: 1 }]);
}
$(".no-radius").click(function(){
	$(this).parent().find(".no-radius ").removeClass("colorgreen");
	$(this).addClass("colorgreen");
})
function collectText(){//汇总信息
	$.ajax({
        url: basePath + "/report/reportCollect",
        type: "post",
        data:{'startTime': $("#choeStartData").val(), 'endTime': $("#choeEndData").val(), 'branchId': $("#Choose-StoreID").val()},
        //async: false,
        dataType: "json",
        success: function (data) {
        	//console.log(data)
        	var memberSum=parseFloat(data.newMemberIncome)+parseFloat(data.oldMemaberIncome)
        $("#cardnumber").text(data.newcardcount);//会员卡总数
        $("#TotalMoney").text(memberSum.toFixed(2));//会员总收入
        $("#newuserMoney").text(parseFloat(data.newMemberIncome).toFixed(2));//新会员收入
        $("#olduserMoney").text(parseFloat(data.oldMemaberIncome).toFixed(2));//老会员收入
        $("#givelMoney").text((parseFloat(data.newMemberPresent)+parseFloat(data.oldMemberPresent)).toFixed(2));//赠送总金额
        $("#givenewlMoney").text(parseFloat(data.newMemberPresent).toFixed(2));//赠送新会员金额
        $("#giveoldlMoney").text(parseFloat(data.oldMemberPresent).toFixed(2));//赠送老会员金额
        $("#paycash").text(parseFloat(data.cashPay).toFixed(2));//现金支付
        $("#Unionpay").text(parseFloat(data.bankPay).toFixed(2));//银联支付
        $("#weixinpay").text(parseFloat(data.weChatPay).toFixed(2) );//微信支付
        $("#zhifubpay").text(parseFloat(data.alipay).toFixed(2));//支付宝
        $("#TotalMoneyconsume").text(parseFloat(data.valueConsumption).toFixed(2) );//消费总金额
        //$("#consume-cz").text((data.valueConsumption).toFixed(2));//充值金额消费
        //$("#consume-zs").text((data.presentConsumption).toFixed(2));//赠送金额消费
        $("#consumetime").text(parseFloat(data.consumptionCount));//消费次数
        }
    });
}