 var SearchData={"telepno": "",
	        "cardno": "",
	        "name": "",
	        "cardname": "",
	        "cardstat": "",
	        "pointflag":"",
	        "point": "",
	        "birstarttime": "",
	        "birendtime": "",
	        "balance": "",
	        "balanceflag": "",
	        "totalbalance": "",
	        "totalbalanceflag": ""}; //查询条件

 function exportLoad(){//导出
	 //console.log(SearchData)
	         //SearchData
	 $("#textone").val(JSON.stringify(SearchData));
	 $("#formid").submit();
	}
function memberBaseMSg(){//会员基本信息
	var witdh = $(".User-list").width();
	$("#User-list").jqGrid({
		url: basePath + "/memberbase/memberinfo/memberbaseinfo",
        datatype: "json",
        mtype: "get",
        width: witdh,
        //shrinkToFit: true,
        forceFit: true,  //当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略。
        altRows: true,
        height: 'auto',
        colNames: ['卡号', '姓名', '手机号码', '性别', '生日','住址','状态','操作'],
        colModel: [
            { name: 'cardno',  width: "120",  align: "center"},
            { name: 'membername', index: 'membername', width: "80",  align: "center"},
            { name: 'mobile', index: 'mobile', width: 120, align: "center"},
            { name: 'gender', index: 'gender', width: 60, align: "center"},
            { name: 'birthday', index: 'birthday', width: 120,  align: "center"},
            { name: 'address', index: 'address', align: "center"},
            { name: 'cardstatus', index: 'cardstatus', align: "center", 
            	formatter: function (value, rowData, obj) {//交易类型
                    var type = value; var str='';
                    switch (type) {
                        case "0": type = "注销"; str="<font color='red'>" + type + "</font>"; break;
                        case "1": type = "正常"; str ="<font>" + type + "</font>"; break;
                        case "2": type = "挂失"; str ="<font color='#333'>" + type + "</font>"; break;
                        default: type = "未知"; break;
                    }
                    return str;
                }	
            },
            { name: 'operate', index: 'cardstatus', align: "center", 
            	formatter: function (value, rowData, obj) {//交易类型
                    var type = obj.cardstatus; var str='';
                    switch (type) {
                        case "0":break;
                        case "1": str ="<a href='javascript:void(0)' data-toggle='modal' style='color:green'  onclick=forwardUpdateMemberInfo('" + obj.cardno_temp + "','"+obj.branch_id+"')><span style='padding-right:5px'></span>修改</a><a href='javascript:void(0)' data-toggle='modal' style='color:green'  onclick=confirmCardLose('"+obj.cardno+"','" + obj.cardno_temp + "','"+obj.branch_id+"','"+obj.membername+"','"+obj.mobile+"')><span style='padding-right:5px'></span>挂失</a>"; break;
                        case "2": str ="<a href='javascript:void(0)' data-toggle='modal' style='color:green'  onclick=confirmUnCardLose('"+obj.cardno+"','" + obj.cardno_temp + "','"+obj.branch_id+"','"+obj.membername+"','"+obj.mobile+"')><span style='padding-right:5px'></span>解除挂失</a>"; break;
                        default:break;
                    }
                    return str;
                }	
            }

        ],
        rownumbers: true,
        gridview: true,
        grouping: true,
        pager: "#gridPagerBaseInfo",
        rowNum: 10,//gridParameters.grid_rowNum,
        rowList: [10,30,50], //gridParameters.grid_rowList,
        prmNames: {
            search: "search"
        },
        jsonReader: {
            root: "memberInfoList", // (2)    json中代表实际模型数据的入口
            page: "page", // 当前页
            total: "total", //总页数
            records: "records", // 总记录数 (3)   json中代表数据行总数的数据 ，既总记录数
            repeatitems: false // (4)如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定
        },
        loadComplete: function () {//没有查询到符合条件的记录
            var re_records = $("#User-list").getGridParam('records');
            if (re_records == 0 || re_records == null) {
                var str = '<div id="history-nodata" class=\"norecords\" style="height:60px;padding-top:20px;">'
                str += ' <span class=" glyphicon glyphicon-info-sign" style="vertical-align: middle"></span>'
                str += ' <span style="font: 14px/1.6 Tahoma,microsoft yahei,"微软雅黑","宋体";padding-left:10px;">没有查询到符合条件的记录</span></div>'
                $("#gridPagerBaseInfo").hide();
                if ($(".norecords").html() == null) {
                    $("#User-list").parent().append(str);
                }
                else {
                    $(".norecords").show();
                }
            }
            else {
                $(".norecords").hide();
                $("#gridPagerBaseInfo").show()
            }
        },
        hidegrid: true,
        viewrecords: true,
        multiselect: false	, //是否支持多选
        sortable:false,//是否排序
        //pager:#gridPager,
    });
        
}
function memberConsumptionMSg() { //会员消费信息
    var witdh = $(".User-list").width();
    jQuery("#User-consumption").jqGrid({
        url: basePath + "/memberbase/membermanage/memberdealinfo",
        datatype: "json",
        mtype: "get",
        //                    height: gridParameters.grid_tableHeight,
        //autowidth: true,
        width: witdh,
        //shrinkToFit: true,
        forceFit: true,  //当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略。
        altRows: true,  //隔行变色
        //                    viewrecords: true,

        //                    datatype: "local",
        //                    autowidth: true,
        height: 'auto',
        colNames: ['卡号', '姓名', '手机号码', '会员卡名称', '当前积分', '累计积分', '累计充值金额', '累计赠送金额', '余额', '累计消费次数', '开卡日期', '开卡员工', '状态', '操作'],
        colModel: [
            { name: 'cardno', index: 'cardno', width: 130, sorttype: "int", align: "center" },//卡号
            { name: 'membername', index: 'membername', width: "120", sorttype: "date", align: "center" },//姓名
            { name: 'mobile', index: 'mobile', width: "120", align: "center", },//电话
            { name: 'cardtype', index: 'cardtype', align: "center",},//会员卡名称
            { name: 'point', index: 'point', align: "center" },//当前积分
            { name: 'totalpoint', index: 'totalpoint', align: "center", },//累计积分
            { name: 'totalbalance', index: 'totalbalance', align: "center", },//累计充值金额
            { name: 'zstotalbalance', index: 'zstotalbalance', align: "center", },//累计赠送金额
            {
                name: 'balance', index: 'balance ', align: "center",//余额
                /*formatter: function (value, rowData, obj) {
                    return "<font color='red'>" + value + "</font>";
                }*/
            },
            { name: 'count', index: 'count ', width: "120", align: "center", },//累计消费次数
            { name: 'createtime', index: 'createtime ', align: "center", },//开卡日期
            { name: 'createuser', index: 'createuser ', width: "120", align: "center", },//开卡员工
            { name: 'cardstatus', index: 'cardstatus ', width: "120", align: "center", 
            	formatter: function (value, rowData, obj) {//交易类型
                    var type = value; var str=''
                    switch (type) {
                        case "0": type = "注销"; str="<font color='red'>" + type + "</font>"; break;
                        case "1": type = "正常"; str ="<font>" + type + "</font>"; break;
                        case "2": type = "挂失"; str ="<font color='#333'>" + type + "</font>"; break;
                        default: type = "未知"; break;
                    }
                    return str;
                }	
            },//状态
            {
                name: 'amount', index: 'amount', width: "120", align: "center", sorttype: "float",//操作
                formatter: function (value, rowData, obj) {
                	var str="";
                	/*var objInfo=jQuery.toJSON(obj);//Json对象转字符串
                	if(obj.cardstatus=="1")//会员卡状态正常
                		{
                		str="<a href='javascript:void(0)' data-toggle='modal' style='color:red'   onclick=Transfer('" +objInfo+ "') >转账</a>  ";
                		str+="<a href='javascript:void(0)' data-toggle='modal' style='color:green'  onclick=expenseList('" + obj.membercard + "') >查看</a> ";
                		}
                	else{
                		str="<a href='javascript:void(0)' data-toggle='modal' style='color:green'  onclick=expenseList('" + obj.membercard + "') >查看</a> "
                	}*/
                	str="<a href='javascript:void(0)' data-toggle='modal' style='color:green'  onclick=expenseList('" + obj.membercard + "') >查看详情</a> "
                    return str
                }
            }
        ],
        //                    altRows: false,//设置为交替行表格,默认为false
        //                    viewrecords: true,//是否在浏览导航栏显示记录总数
        //        rowNum: 15,//每页显示记录数
        //        rowList: [15, 20, 25],//用于改变显示行数的下拉列表框的元素数组。

        rownumbers: true,
        gridview: true,
        grouping: true,
        pager: "#gridPager",
        rowNum: 10,//gridParameters.grid_rowNum,
        rowList: [10,30,50], //gridParameters.grid_rowList,
        prmNames: {
            search: "search"
        },
        jsonReader: {
            root: "dealInfoList", // (2)    json中代表实际模型数据的入口
            page: "page", // 当前页
            total: "total", //总页数
            records: "records", // 总记录数 (3)   json中代表数据行总数的数据 ，既总记录数
            repeatitems: false // (4)如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定
        },
        loadComplete: function () {//没有查询到符合条件的记录
            var re_records = $("#User-consumption").getGridParam('records');
            if (re_records == 0 || re_records == null) {
                var str = '<div id="history-nodata" class=\"norecords_list\" style="height:60px;padding-top:20px;">'
                str += ' <span class=" glyphicon glyphicon-info-sign" style="vertical-align: middle"></span>'
                str += ' <span style="font: 14px/1.6 Tahoma,microsoft yahei,"微软雅黑","宋体";padding-left:10px;">没有查询到符合条件的记录</span></div>'
                $("#gridPager").hide();
                if ($(".norecords_list").html() == null) {
                    $("#User-consumption").parent().append(str);
                }
                else {
                    $(".norecords_list").show();
                }
            }
            else {
                $(".norecords_list").hide();
                $("#gridPager").show()
            }
        },
        hidegrid: true,
        viewrecords: true,
        multiselect: false	 //是否支持多选
        //pager:#gridPager,
    });
}
function memberIntoErrorList(data) { //会员信息导入错误列表
	//console.log(data);
    var witdh = $(".User-list").width();
    if(data.errorNum>0){
      var lastSel;
      $('html').bind('click', function(e) {
    	  //用于点击其他地方保存正在编辑状态下的行
          if(lastSel != "" ) { //if a row is selected for edit 
              if($(e.target).closest('#User-consumptionIntoErroList').length == 0) { 
              $('#User-consumptionIntoErroList').jqGrid('saveRow',lastSel);      
              jQuery('#User-consumptionIntoErroList').resetSelection(); 
              lastsel=""; 
              } 
          } 
      });
        jQuery("#User-consumptionIntoErroList").jqGrid({
        	/*url: basePath + "/memberbase/membermanage/memberdealinfo",
            datatype: "json",
            mtype: "get",*/
            //                    height: gridParameters.grid_tableHeight,
            //autowidth: true,
            width: witdh,
            //shrinkToFit: true,
            forceFit: true,  //当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略。
            altRows: true,  //隔行变色
            //                    viewrecords: true,

            //                    datatype: "local",
            //                    autowidth: true,
            height: 'auto',
            colNames: ['卡号', '姓名','手机号码','性别', '生日','地址','会员卡名称','累计充值金额', '累计赠送金额' ,'余额', '累计积分','当前积分','挂账/欠款','开卡员工编号','开卡时间','会员卡状态','所属门店'],
            colModel: [
                       { name: 'memberCardNo', index: 'memberCardNo',type:"1", width: 130, sorttype: "int", align: "center" },//卡号
                       { name: 'memberName', index: 'memberName', width: "120", sorttype: "date", align: "center",
                    	   editable:true,editoptions:{size:"20",maxlength:"30"} },//姓名
                       { name: 'phone', index: 'phone', width: "120", align: "center", editable:true,editoptions:{size:"20",maxlength:"30"} },//电话
                       { name: 'sex', index: 'sex', align: "center", editable:true,editoptions:{size:"20",maxlength:"30"} },//性别
                       { name: 'birth', index: 'birth', align: "center" ,editable:true,editoptions:{size:"20",maxlength:"30"} },//生日
                       { name: 'address', index: 'address', align: "center", editable:true,editoptions:{size:"20",maxlength:"30"} },//地址
                       { name: 'memberCardName', index: 'memberCardName', align: "center",editable:true,editoptions:{size:"20",maxlength:"30"}  },//会员卡名称
                       { name: 'chargeAmount', index: 'chargeAmount', align: "center", editable:true,editoptions:{size:"20",maxlength:"30"} },//累计充值金额
                       {
                           name: 'presentAmount', index: 'presentAmount ', align: "center",editable:true,editoptions:{size:"20",maxlength:"30"} //累计赠送金额
                       },
                       { name: 'balanceAmount', index: 'balanceAmount ', width: "120", align: "center",editable:true,editoptions:{size:"20",maxlength:"30"}  },//余额
                       { name: 'totalPoint', index: 'totalPoint ', align: "center",editable:true,editoptions:{size:"20",maxlength:"30"}  },//累计积分
                       { name: 'currentPoint', index: 'currentPoint ', width: "120", align: "center",editable:true,editoptions:{size:"20",maxlength:"30"}  },//当期积分
                       { name: 'debt', index: 'debt ', width: "120", align: "center",editable:true,editoptions:{size:"20",maxlength:"30"}  
                       	
                       },//挂张
                       {
                           name: 'creatorEmployeeNo', index: 'creatorEmployeeNo', width: "120", align: "center", sorttype: "float",//开卡员工编号
                           editable:true,editoptions:{size:"20",maxlength:"30"} 
                       },
                       {
                           name: 'createDate', index: 'createDate', width: "120", align: "center", sorttype: "float",//开卡时间
                           editable:true,editoptions:{size:"20",maxlength:"30"} 
                          
                       },
                       {
                           name: 'cardStatus', index: 'cardStatus', width: "120", align: "center", sorttype: "float",//会员卡状态
                           editable:true,editoptions:{size:"20",maxlength:"30"} 
                          
                       },
                       {
                           name: 'storeNo', index: 'storeNo', width: "120", align: "center", sorttype: "float",//门店ID
                           
                       }
                   ],
           
            
            
            prmNames: {
                search: "search"
            },
            onSelectRow: function(id){
            	/*$("#User-consumptionIntoErroList tr").click(function () {  
                	var errorMsg = $(this).position();  
                    $("#float_box").css("position", "absolute");  
                    //$("#float_box").css("left", errorMsg.left + 20); //距离左边距  
                    $("#float_box").css("top", errorMsg.top + 160); //距离上边距  
                    $("#float_box").show();  
            	})
            	
                $("#User-consumptionIntoErroList tr").mouseleave(function () {  
                    $("#float_box").hide();  
                }); */
            	
            	
                if(id && id!==lastSel){
                	$('#User-consumptionIntoErroList').jqGrid('saveRow',lastSel);      
                    lastSel=id;
                    }
                 $('#User-consumptionIntoErroList').jqGrid('editRow',id,true); 
                
            },
            
            hidegrid: true,
            viewrecords: true,
            multiselect: false	 //是否支持多选
        });
       
        
            for (var i = 0; i <= data.errorResult.length; i++)
            $("#User-consumptionIntoErroList").jqGrid('addRowData', i + 1, data.errorResult[i]);
            
    }
}

function dealListDialogMSg(cardno) { //查看消费记录
    var url = "/memberbase/membermanage/findDealDetailByCardno?cardno=" + cardno + "&time=" + new Date().getTime() + ""
    jQuery("#dealDetail-List").jqGrid({
        url: basePath + url,
        datatype: "json",
        mtype: "get",

        //                    height: gridParameters.grid_tableHeight,
        autowidth: 1000,
        shrinkToFit: true,
        forceFit: true,  //当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略。
        altRows: true,  //隔行变色
        //                    viewrecords: true,

        //                    datatype: "local",
        //                    autowidth: true,
        height: 'auto',
        colNames: ['交易流水号','交易类型', '交易金额', '票据号', '交易时间','交易地点'],
        colModel: [
            { name: 'deal_no', index: 'deal_no', width: "160", align: "center" },
            {
                name: 'deal_type', index: 'deal_type', width: "125", align: "center",
                formatter: function (value, rowData, obj) {//交易类型
                    var type = value
                    switch (type) {
                        case "0": type = "现金消费"; break;
                        case "1": type = "现金消费积分"; break;
                        case "2": type = "储值消费"; break;
                        case "3": type = "储值消费积分"; break;
                        case "4": type = "积分消费"; break;
                        case "5": type = "现金充值"; break;
                        case "6": type = "现金消费反结算"; break;
                        case "7": type = "现金消费积分反结算"; break;
                        case "8": type = "储值消费反结算"; break;
                        case "9": type = "储值消费积分反结算"; break;
                        case "10": type = "积分消费反结算"; break;
                        case "11": type = "银行卡充值"; break;
                        case "13": type = "微信扫码支付"; break;
                        case "14": type = "微信扫码支付反结算"; break;
                        case "15": type = "微信扫码支付积分"; break;
                        case "16": type = "微信扫码支付积分反结算"; break;
                        case "17": type = "储值赠送"; break;
                        default: type = "未知"; break;
                    }
                    return type;
                }
            },
            {
                name: 'amount', index: 'amount', align: "center",
                /*formatter: function (value, rowData, obj) {
                    return "<font color='red'>" + value + "</font>";
                }*/
            },
            { name: 'slip_no', index: 'slip_no', align: "center", width: "209" },
            {
                name: 'deal_time', index: 'deal_time', align: "center",
                formatter: function (value, rowData, obj) {
                    var str = (obj.deal_time).substr(0, 10);
                    var date = new Date(parseInt(str) * 1000);//字符串转换为整数，并转化为标准时间
                    var Y = date.getFullYear() + '-';
                    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
                    var h = (date.getHours() < 10 ? '0' + (date.getHours()) : date.getHours()) + ':';
                    var m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes()) + ':';
                    var s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
                    return Y + M + D + h + m + s;
                }
            },
            { name: 'deal_addr', index: 'deal_addr', align: "center" }
        ],
        //                    altRows: false,//设置为交替行表格,默认为false
        //                    viewrecords: true,//是否在浏览导航栏显示记录总数
        //        rowNum: 15,//每页显示记录数
        //        rowList: [15, 20, 25],//用于改变显示行数的下拉列表框的元素数组。

        rownumbers: true,
        gridview: true,
        grouping: true,
        pager: "#dealDetail-gridPager",
        rowNum: 10,//gridParameters.grid_rowNum,
        rowList: [10], //gridParameters.grid_rowList,
        prmNames: {
            search: "search"
        },
        jsonReader: {
            root: "detailDatas", // (2)    json中代表实际模型数据的入口
            page: "page", // 当前页
            total: "total", //总页数
            records: "records", // 总记录数 (3)   json中代表数据行总数的数据 ，既总记录数
            repeatitems: false // (4)如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定
        },
        loadComplete: function () {//没有查询到符合条件的记录
            var re_records = $("#dealDetail-List").getGridParam('records');
            if (re_records == 0 || re_records == null) {
                var str = '<div id="history-nodata" class=\"norecords_one\" style="height:60px;padding-top:20px;">'
                str += ' <span class=" glyphicon glyphicon-info-sign" style="vertical-align: middle"></span>'
                str += ' <span style="font: 14px/1.6 Tahoma,microsoft yahei,"微软雅黑","宋体";padding-left:10px;">没有查询到符合条件的记录</span></div>'
                $("#dealDetail-gridPager").hide();
                if ($(".norecords_one").html() == null) {
                    $("#dealDetail-List").parent().append(str);
                }
                else {
                    $(".norecords_one").show();
                }
            }
            else {
                $(".norecords_one").hide();
                $("#dealDetail-gridPager").show()
            }
        },
        hidegrid: true,
        viewrecords: true,
        multiselect: false	 //是否支持多选
        //pager:#gridPager,
    });
}
function expenseList(cardno) {//消费记录
    $("#context").html("")
    var str = '<table id="dealDetail-List" ></table>'
    str += '<div id="dealDetail-gridPager"></div>'
    $("#context").append(str);
    $('#dealListDialog').modal('show');
    dealListDialogMSg(cardno)
}
function Reset() {//重置
	$("#point_erro").remove();
	$("#username_erro").remove();
	$("#balance_erro").remove();
	$("#totalbalance_erro").remove();
	$("#birthdayEnd_erro").remove();
    var name = $.trim($("#username").val(""));//会员名字
    var cardname = $.trim($("#cardname").val(""));//会员卡名称
    var cardstat = $.trim($("#cardstat").val(""));//会员卡状态
    var pointflag = $.trim($("#pointflag").val("0"));//会员卡积分选择范围
    var point = $.trim($("#point").val(""));//会员卡积分具体值
    var balanceflag = $.trim($("#balanceflag").val("0"));//会员卡余额选择范围
    var balance = $.trim($("#balance").val(""));//会员卡余额具体数值
    var totalbalanceflag = $.trim($("#totalbalanceflag").val("0"));//会员卡累计充值选择范围
    var totalbalance = $.trim($("#totalbalance").val(""));//会员卡累计充值具体数值
    var birstarttime = $.trim($("#birthdayStart").val(""));//会员生日开始
    var birendtime = $.trim($("#birthdayEnd").val(""));//会员生日结束
    $("#totalbalance").attr("disabled", "disabled");
    $("#balance").attr("disabled", "disabled");
    $("#point").attr("disabled", "disabled");
}
var menmberHTMLName
function Searchtext() {//绑定会员卡名称已经会员卡状态。
    $.ajax({
        url: basePath + "/memberbase/membermanage/findCardInfo",
        type: "post",
        //async: false,
        dataType: "json",
        success: function (data) {
        	menmberHTMLName=data
        	//console.log(data)
            for (var i = 0; i < data.name.length; i++) {//会员卡名称
                var str = '<option value="' + data.name[i].value + '">' + data.name[i].name + '</option>';
                $("#cardname").append(str);
            }
            $("#cardname").val("");
            for (var i = 0; i < data.status.length; i++) {//会员卡状态
                var str = '<option value="' + data.status[i].value + '">' + data.status[i].name + '</option>';
                $("#cardstat").append(str);
            }
            $("#cardstat").val("");
            for (var i = 0; i < data.stores.length; i++) {//所谓门店
                var str = '<option value="' + data.stores[i].value+ '">' + data.stores[i].name+ '</option>';
                $("#store").append(str);
            }
        }
    });
}
function iconsearch() {//搜索
	Reset()//清空
    var telepno = '', cardno = '';
    var name = '';//会员名字
    var cardname = '';//会员卡名称
    var cardstat = "";//会员卡状态
    var pointflag = '';//会员卡积分选择范围
    var point = '';//会员卡积分具体值
    var balanceflag = '';//会员卡余额选择范围
    var balance = '';//会员卡余额具体数值
    var totalbalanceflag = '';//会员卡累计充值选择范围
    var totalbalance = '';//会员卡累计充值具体数值
    var birstarttime = '';//会员生日开始
    var birendtime = '';//会员生日结束
    var absc = /[\u4E00-\u9FA5]/g;//判断输入中文
    var phoneNumber = /^1[3|4|5|7|8]\d{9}$/  //电话号码
    var phone = $.trim($("#phone").val());
    $("#phone_erro").text("")
    if (absc.test(phone)) {
        $("#phone").focus();
        $("#phone_erro").text('会员卡号/手机号码,不能输入汉字');
        return;
    }
    else {
        if (phone.length == 11) {
            if (phoneNumber.test(phone)) {
                /*$("#phone").focus();
                $("#phone_erro").text('请输入正确的手机号码');*/
                telepno = phone
            }
            else {
            	cardno = phone
            }
        }
        else {
            $("#phone_erro").text('');
            cardno = phone
        }
    }
    //显示查询结果
    var jsonDataSearch = {
        "telepno": telepno,
        "cardno": cardno,
        "name": name,
        "cardname": cardname,
        "cardstat": cardstat,
        "pointflag": pointflag,
        "point": point,
        "birstarttime": birstarttime,
        "birendtime": birendtime,
        "balance": balance,
        "balanceflag": balanceflag,
        "totalbalance": totalbalance,
        "totalbalanceflag": totalbalanceflag,
    };
    SearchData=jsonDataSearch //给查询条件赋值
    var postData = $("#User-consumption").jqGrid("getGridParam", "postData");
    var postDatabBase = $("#User-list").jqGrid("getGridParam", "postData");
    //将查询参数融入postData选项对象
    $.extend(postData, jsonDataSearch);
    $.extend(postDatabBase, jsonDataSearch);
    $("#User-consumption").jqGrid("setGridParam", {
        postData: jsonDataSearch, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
    }).trigger("reloadGrid", [{ page: 1 }]);
    $("#User-list").jqGrid("setGridParam", {
    	postDatabBase: jsonDataSearch, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
    }).trigger("reloadGrid", [{ page: 1 }]);
}
$("#pointflag").change(function () {//会员卡积分
    var val = $(this).val();
    $("#point_erro").remove();
    if (val != 0) {
        $("#point").removeAttr("disabled");
    }
    else {
        $("#point").attr("disabled", "disabled");
        $("#point").val("");
    }
})
$("#balanceflag").change(function () {//会员卡余额
    $("#balance_erro").remove();
    var val = $(this).val();
    if (val != 0) {
        $("#balance").removeAttr("disabled");
    }
    else {
        $("#balance").attr("disabled", "disabled");
        $("#balance").val("");
    }
})
$("#totalbalanceflag").change(function () {//会员卡累计充值
    $("#totalbalance_erro").remove();
    var val = $(this).val();
    if (val != 0) {
        $("#totalbalance").removeAttr("disabled");
    }
    else {
        $("#totalbalance").attr("disabled", "disabled");
        $("#totalbalance").val("");
    }
})
function heightsearch() {//高级搜索
	$("#phone").val("");
	$("#point_erro").remove();
	$("#username_erro").remove();
	$("#balance_erro").remove();
	$("#totalbalance_erro").remove();
	$("#birthdayEnd_erro").remove();
    var telepno = '', cardno = '';
    var absc = /[\u4E00-\u9FA5]/g;//判断输入中文
    var Number = /^(0|[1-9][0-9]*)$/g  //只能输入数字
    var nuberNO=/^[0-9]+(\.[0-9]{2})?$/
    var name = $.trim($("#username").val());//会员名字
    var cardname = $.trim($("#cardname").val());//会员卡名称
    var cardstat = $.trim($("#cardstat").val());//会员卡状态
    var pointflag = $.trim($("#pointflag").val());//会员卡积分选择范围
    var point = $.trim($("#point").val());//会员卡积分具体值
    var balanceflag = $.trim($("#balanceflag").val());//会员卡余额选择范围
    var balance = $.trim($("#balance").val());//会员卡余额具体数值
    var totalbalanceflag = $.trim($("#totalbalanceflag").val());//会员卡累计充值选择范围
    var totalbalance = $.trim($("#totalbalance").val());//会员卡累计充值具体数值
    var birstarttime = $.trim($("#birthdayStart").val());//会员生日开始
    var birendtime = $.trim($("#birthdayEnd").val());//会员生日结束
    var htmlstr=/<\/?[^>]*>/g; //html标签
    if (name != "") {
        $("#username_erro").remove();
        if (htmlstr.test(name)) {
            $("#username").focus();
            if ($("#username_erro").size() < 1) {
            	$("#username").parent().after("<div id='username_erro' style='color:red;padding-left:95px;padding-top:5px;'>请不要输入非法的字符</div>");
            }
            return;
        }
    };
    if (pointflag != 0) {
    	nuberNO.lastIndex=0;
        $("#point_erro").remove();
        if (point == "") {
            $("#point").focus();
            if ($("#point_erro").size() < 1) {
                $("#point").parent().after("<div id='point_erro' style='color:red;padding-left:95px;padding-top:5px;'>积分不能为空</div>");
            }
            return;
        }
        if (!nuberNO.test(point)) {
        	
            $("#point").focus();
            if ($("#point_erro").size() < 1) {
                $("#point").parent().after("<div id='point_erro' style='color:red;padding-left:95px;padding-top:5px;'>只能输入大于0的数字和两位小数</div>");
            }
            return;
        }
        

    }
    if (balanceflag != 0) {
    	nuberNO.lastIndex=0;//正则开始为0 
        $("#balance_erro").remove();
        if (balance == "") {
            $("#balance").focus();
            if ($("#balance_erro").size() < 1) {
                $("#balance").parent().after("<div id='balance_erro' style='color:red;padding-left:95px;padding-top:5px;'>余额不能为空</div>");
            }
            return;
        }
        if (!nuberNO.test(balance)) {
            $("#balance").focus();
            if ($("#balance_erro").size() < 1) {
                $("#balance").parent().after("<div id='balance_erro' style='color:red;padding-left:95px;padding-top:5px;'>只能输入大于0的数字和两位小数</div>");
            }
            return;
        }
    }
    if (totalbalanceflag != 0) {
    	nuberNO.lastIndex=0;
        $("#totalbalance_erro").remove();
        if (totalbalance == "") {
            $("#totalbalance").focus();
            if ($("#totalbalance_erro").size() < 1) {
                $("#totalbalance").parent().after("<div id='totalbalance_erro' style='color:red;padding-left:95px;padding-top:5px;'>累计充值不能为空</div>");
            }
            return;
        }
        if (!nuberNO.test(totalbalance)) {
            $("#totalbalance").focus();
            if ($("#totalbalance_erro").size() < 1) {
                $("#totalbalance").parent().after("<span id='totalbalance_erro' style='color:red;padding-left:95px;padding-top:5px;'>只能输入大于0的数字和两位小数</span>");
            }
            return;
        }
    }
    if (birstarttime != "" && birendtime == "") {
        $("#birthdayEnd_erro").remove();
        $("#birthdayEnd").focus();
        if ($("#birthdayEnd_erro").size() < 1) {
            $("#birthdayEnd").after("<span id='birthdayEnd_erro' style='color:red;padding-left:10px;padding-top:5px;'>会员生日结束时间不能为空</span>");
        }
        return;
    }
    if (birendtime != "" && birstarttime == "") {
        $("#birthdayEnd_erro").remove();
        //$("#birthdayStart").focus();
        if ($("#birthdayEnd_erro").size() < 1) {
            $("#birthdayEnd").after("<span id='birthdayEnd_erro' style='color:red;padding-left:10px;padding-top:5px;'>会员生日开始时间不能为空</span>");
        }
        return;
    }
    $('#ScreenSearch').modal('hide');
    //显示查询结果
    var jsonData = {
        "telepno": telepno,
        "cardno": cardno,
        "name": name,
        "cardname": cardname,
        "cardstat": cardstat,
        "pointflag": pointflag,
        "point": point,
        "birstarttime": birstarttime,
        "birendtime": birendtime,
        "balance": balance,
        "balanceflag": balanceflag,
        "totalbalance": totalbalance,
        "totalbalanceflag": totalbalanceflag,
    };
    SearchData=jsonData //给查询条件赋值
    //将查询参数融入postData选项对象
    var postData = $("#User-consumption").jqGrid("getGridParam", "postData");//会员消费信息
    var postDatabBase = $("#User-list").jqGrid("getGridParam", "postData");//会员基本信息
    //将查询参数融入postData选项对象
    $.extend(postData, jsonData);//会员消费信息
    $.extend(postDatabBase, jsonData);//会员基本信息
    $("#User-consumption").jqGrid("setGridParam", {//会员消费信息
        postData: jsonData, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
    }).trigger("reloadGrid", [{ page: 1 }]);
    $("#User-list").jqGrid("setGridParam", {//会员基本信息
    	postDatabBase: jsonData, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
    }).trigger("reloadGrid", [{ page: 1 }]);
}
$(".VIPtab li").click(function () {//选项卡切换
	
	$(".User-list").hide().eq($(this).index()).show();//显示隐藏DIV切换
    $(".VIPtab li").removeClass("cur");
    $(this).addClass("cur");
    if ($(this).index() == 2) {//会员导入密码验证
    	$(".User-list").eq($(this).index()).hide()
        $("#VipOneTwo").hide();
        $("#newAddMember").hide();
        $("#export_Password").val("");
        $("#exportPassword_Info").modal('show');
        
    }
    else {
        $("#VipOneTwo").show()
         $("#newAddMember").hide()
    }
    if($(this).index() == 3){
    	$("#VipOneTwo").hide()
    	$("#newAddMember").show()
    }
    
});
//取消输入会员导入密码
function exportPasswordcancel(){
	$("#exportPassword_Info").modal('hide');//隐藏弹出框
	$(".VIPtab li").eq(0).trigger("click");//回到会员基础信息
}
//输入会员导入密码正确
function exportPasswordOk(){
	$("#export_Password_erro").remove();
	var exportPassword="candaohy"//
	var Pad=$.trim($("#export_Password").val())
	if(Pad==exportPassword){
		$("#exportPassword_Info").modal('hide');//隐藏弹出框
		$(".User-list").eq(2).show();
	}
	else{
		if ($("#export_Password_erro").size() < 1) {
 			$("#export_Password").parent().append("<div id='export_Password_erro' style='color:red;padding-left:0px;padding-top:10px;font-size:12px;'>密码不正确，请输入正确的密码</div>");
 	       }
	}
	
}
function avc() {//上传  
	var sss=$('.uploadify-queue-item').size();
	if(sss>0){
		$("#modal_Loading").modal('show')
		$("#uploadfy").uploadify("upload","*");
	}
	else{
	    $('#modal_Error .p1 span').text("请选择，您要上传的文件?")
		$('#modal_Error').modal('show');
	}
	}
function cheackInfo(data){//校验导入成功或者失败
	//console.log(data)
if(data.errorNum >0){
	 $("#daoru_Erro_list").html("")
	 var str = '<table id="User-consumptionIntoErroList" ></table>'
	 str += '<div id="gridPager-ErrorList"></div>'
	 $("#daoru_Erro_list").append(str);
	 $("#successNum").text(data.successNum);
	 $("#errorNum").text(data.errorNum);
	 memberIntoErrorList(data);
	 $("#J_doc").slideUp(1500);
	 $("#J_doc_erro").slideDown(1500);
	 $("#modal_Loading").modal('hide');
 }
if(data.errorNum==null){
	 $("#modal_Loading").modal('hide');
	 $('#modal_Error .p1 span').text("会员导入模板不正确，请选择正确的导入模板");
	 $('#modal_Error').modal('show');
}
 if(data.errorNum ==0){
	 $("#modal_Loading").modal('hide');
	 $('#modal_success').modal('show');
	 $("#success_alert_MSg span").text("成功导入"+data.successNum+"条");
	 var str = '<table id="User-consumptionIntoErroList" ></table>';
		 str += '<div id="gridPager-ErrorList"></div>';
	 $("#daoru_Erro_list").append(str);
	 $("#J_doc_erro").slideUp(1500);
	 $("#J_doc").slideDown(1500);
 } 
}
function editInfo(){// 提交修改数据
setTimeout( function(){
	var data = $("#User-consumptionIntoErroList").jqGrid("getRowData");//获取修改后的数据
	data= JSON.stringify(data);
	$("#modal_Loading").modal('show');
	 $("#daoru_Erro_list").html("");
    $.ajax({
        url: basePath + "/excel/changeInfo",
        type: "post",
        //async: false,
        dataType: "json",
        data:{"editdata":data,"tenantId":$("#tenantId").val(),"cardtype": $("#daoru_cardtype").val()},
        success: function (data) {
        	//data=JSON.parse(data)//转化为json对象
        	cheackInfo(data)
        }
    });
},800)
}

function forwardUpdateMemberInfo(cardno,branchid){//获取会员修改信息
	$("#info_mobile_erro").remove();
	$.ajax({
		url:basePath+"/memberbase/memberinfo/findMemberByCardNo",
		type:"post",
		dataType:"json",
		data:{
			"cardno":cardno,
			"branchid":branchid
		},
		success:function(data){
			console.log(data)
			if(data.Retcode==0){
				$("#info_cardno").val(data.cardno);
				$("#info_id").val(data.id);
				$("#info_tenantid").val(data.tenant_id);
				$("#info_cardnotemp").val(data.cardno_temp);
				$("#info_mobile").val(data.mobile);
				$("#info_name").val(data.name);
				$("#info_gender option[value='"+data.gender+"']").attr("selected","selected");
				$("#info_address").val(data.member_address);
				$("#info_birthday").val(data.birthday);
				$("#info_status option[value='"+data.status+"']").attr("selected","selected");
				var fun="updateMemberInfoHandler();return false;";
				$("#member_info_update_ok").attr("onclick",fun);
				$("#member_info_update").modal("show");
			}else{
				alert(data.RetInfo);
			}
		},
		error:function(){
			var InfoMSgHeader="获取会员信息失败";
	    	errorInfoMSg(InfoMSgHeader)
		}
	});
}

function updateMemberInfoHandler(){//修改会员信息二次确认弹窗
	var str="确定修改该会员信息？";
	var ht="修改会员信息";
	var name=$.trim($("#info_name").val())//姓名
	var mobile=$.trim($("#info_mobile").val())//电话
	var infocardno=$.trim($("#info_cardno").val())//卡号
	var html=" <tr><td class='classOne'>卡号：</td><td class='classTwo'>"+infocardno+"</td></tr>";
    html +=" <tr><td class='classOne'>会员姓名：</td><td class='classTwo'>"+name+"</td></tr>";
    html +=" <tr><td class='classOne'>手机号码：</td><td class='classTwo'>"+mobile+"</td></tr>";
   $("#Edit_member_algin_list").html(html);
	$("#modal_confirm_againedit .p1").text(str);
	$(".againeditheadr_info").text(ht)
	$("#modal_confirm_againedit").modal("show");
	/*if(window.confirm(str)){
		
	}*/
}
function againedit(){//确认修改会员信息保存
	$("#modal_confirm_againedit").modal("hide");//二次确认弹出框取消
	var cardno=$.trim($("#info_cardno").val());
	var id=$.trim($("#info_id").val());
	var tenant=$.trim($("#info_tenantid").val());
	var cardno_temp=$.trim($("#info_cardnotemp").val());
	var mobile=$.trim($("#info_mobile").val());
	var name=$.trim($("#info_name").val());
	var gender=$.trim($("#info_gender").val());
	var address=$.trim($("#info_address").val());
	var birthday=$.trim($("#info_birthday").val());
	var status=$.trim($("#info_status").val());
	$.ajax({
		url:basePath+"/memberbase/memberinfo/updateMemberInfo",
		type:"post",
		dataType:"json",
		data:{
			'id':id,
			'name':name,
			'mobile':mobile,
			'gender':gender,
			'birthday':birthday,
			'tenant_id':tenant,
            'member_address':address
		},
		success:function(data){
			$("#member_info_update").modal("hide");
			if(data.Retcode==0){
				var postDatabBase = $("#User-list").jqGrid("getGridParam", "postData");//会员基本信息
	    	    //将查询参数融入postData选项对象
	    		var jsonData=SearchData;
	    	    $.extend(postDatabBase, jsonData);//会员基本信息	    	   
	    	    $("#User-list").jqGrid("setGridParam", {//会员基本信息
	    	    	postDatabBase: jsonData, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
	    	    }).trigger("reloadGrid");
	    	    var InfoMSgHeader="修改会员信息";
	    	    var InfoMSgCOn='<div style="color:rgb(122, 196, 84)"><i class="glyphicon glyphicon-ok-circle"></i><span style="padding-left:5px">修改会员信息成功!</span></div>';
	    	    successInfoMSg(InfoMSgHeader,InfoMSgCOn);
			}else{
				alert(data.RetInfo);
			}
		},
		error:function(){
			var InfoMSgHeader="修改会员信息失败！";
		    	errorInfoMSg(InfoMSgHeader)
		}
	});
}
function confirmCardLose(cardno,cardno_temp,branchid,membername,mobile){
	var str="确定挂失该会员卡号?";
	$("#cardlose_confirm_info").text(str);
	var fun="cardLoseHandler('"+cardno_temp+"','"+branchid+"');return false;";
	$("#cardlose_ok").attr("onclick",fun);
	var html=" <tr><td class='classOne'>卡号：</td><td class='classTwo'>"+cardno+"</td></tr>";
    html +=" <tr><td class='classOne'>会员姓名：</td><td class='classTwo'>"+membername+"</td></tr>";
    html +=" <tr><td class='classOne'>手机号码：</td><td class='classTwo'>"+mobile+"</td></tr>";
   $("#guashi_member_algin_list").html(html);
	$("#modal_confirm_cardlose").modal("show");
}

function cardLoseHandler(cardno,branchid){
	$('#modal_confirm_cardlose').modal('hide');
	var json="{\"cardno\":\""+cardno+"\",\"branch_id\":\""+branchid+"\",\"FMemo\":\"挂失\"}";
	$.ajax({
		url:basePath+"/deal/CardLose",
	    type:"post",
	    dataType:"json",
	    contentType:"application/json",
	    data:json,
	    success:function(data){
	    	if(data.Retcode==0){
	    		var postDatabBase = $("#User-list").jqGrid("getGridParam", "postData");//会员基本信息
	    	    //将查询参数融入postData选项对象
	    		var jsonData=SearchData;
	    	    $.extend(postDatabBase, jsonData);//会员基本信息	    	   
	    	    $("#User-list").jqGrid("setGridParam", {//会员基本信息
	    	    	postDatabBase: jsonData, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
	    	    }).trigger("reloadGrid");
	    	    var InfoMSgHeader="挂失会员卡";
	    	    var InfoMSgCOn='<div style="color:rgb(122, 196, 84)"><i class="glyphicon glyphicon-ok-circle"></i><span style="padding-left:5px">挂失成功!</span></div>';
	    	    successInfoMSg(InfoMSgHeader,InfoMSgCOn)
	    	   // alert("挂失成功!");
	    	}else{
	    		alert(data.RetInfo);
	    	}
	    },
	    error:function(){
	    	var InfoMSgHeader="挂失失败！"
	    	errorInfoMSg(InfoMSgHeader)
	    }
	});
}

function confirmUnCardLose(cardno,cardno_temp,branchid,membername,mobile){
	var str="确定解除该会员卡号挂失状态?";
	$("#uncardlose_confirm_info").html(str);
	var fun="uncardLoseHandler('"+cardno_temp+"','"+branchid+"');return false;";
	$("#uncardlose_ok").attr("onclick",fun);
	var html=" <tr><td class='classOne'>卡号：</td><td class='classTwo'>"+cardno+"</td></tr>";
    html +=" <tr><td class='classOne'>会员姓名：</td><td class='classTwo'>"+membername+"</td></tr>";
    html +=" <tr><td class='classOne'>手机号码：</td><td class='classTwo'>"+mobile+"</td></tr>";
   $("#JcGs_member_algin_list").html(html);
	$("#modal_confirm_uncardlose").modal("show");
}

function uncardLoseHandler(cardno,branchid){
	$('#modal_confirm_uncardlose').modal('hide');
	var json="{\"cardno\":\""+cardno+"\",\"branch_id\":\""+branchid+"\",\"FMemo\":\"解除挂失\"}";
	$.ajax({
		url:basePath+"/deal/UnCardLose",
	    type:"post",
	    dataType:"json",
	    contentType:"application/json",
	    data:json,
	    success:function(data){
	    	if(data.Retcode==0){
	    		var postDatabBase = $("#User-list").jqGrid("getGridParam", "postData");//会员基本信息
	    	    //将查询参数融入postData选项对象
	    		var jsonData=SearchData;
	    	    $.extend(postDatabBase, jsonData);//会员基本信息	    	   
	    	    $("#User-list").jqGrid("setGridParam", {//会员基本信息
	    	    	postDatabBase: jsonData, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
	    	    }).trigger("reloadGrid");
	    	    var InfoMSgHeader="解除挂失";
	    	    var InfoMSgCOn='<div style="color:rgb(122, 196, 84)"><i class="glyphicon glyphicon-ok-circle"></i><span style="padding-left:5px">解除挂失成功!</span></div>'
	    	    successInfoMSg(InfoMSgHeader,InfoMSgCOn)
	    	}else{
	    		alert(data.RetInfo);
	    	}
	    },
	    error:function(){
	    	alert("解除挂失失败!");
	    }
	});
}


var ADDmenberInfo , ADDmenberInfotoo
function addMemberInfo(){// 提交新增会员数据
	    $("#member_algin_list").html("");
		var mobile = $.trim($("#mobile").val());//电话号码
		var branch_id = $.trim($("#store").val());//分店id
		var storeName =$.trim($("#store").find("option:selected").text());//门店名字
		var cardno = $.trim($("#cardno").val());//会员卡号
		var password = $.trim($("#password").val());//密码
		var gender = $.trim($("#gender").val());//性别 
		var genderName = $.trim($("#gender").find("option:selected").text());//性别 文本
		var name = $.trim($("#membername").val());//姓名
		var birthday = $.trim($("#birthday").val());//生日
		var member_address = $.trim($("#address").val());//会员地址
		var createuser=$.trim($("#createuser").val());//开卡员工
		var channel = '3';//注册来源
		var cardname1 = $.trim($("#cardname1").val());
		var params = {"mobile": mobile,
				      "branch_id": branch_id,
				      "cardno": cardno,
				      "password": password,
				      "name": name,
				      "gender": gender,
				      "birthday": birthday,
				      "member_address": member_address,
				      "createuser":createuser,
				      "channel": channel};
		ADDmenberInfotoo=params
		var json = JSON.stringify(params);
		ADDmenberInfo=json
		var html=" <tr><td class='classOne'>手机号码：</td><td class='classTwo'>"+mobile+"</td></tr>";
		    html +=" <tr><td class='classOne'>会员姓名：</td><td class='classTwo'>"+name+"</td></tr>";
		    html +=" <tr><td class='classOne'>性别：</td><td class='classTwo'>"+genderName+"</td></tr>";
		    html +=" <tr><td class='classOne'>生日：</td><td class='classTwo'>"+birthday+"</td></tr>";
		    html +=" <tr><td class='classOne'>所属门店：</td><td class='classTwo'>"+storeName+"</td></tr>";
		$("#member_algin_list").html(html);
		$('#modalmember_algin').modal('show');
	}
function addalgin(){//提交新增会员信息再次确认
	$('#modalmember_algin').modal('hide');
	$.ajax({
        url: basePath + "/memberManager/save",
        type: "post",
        contentType: "application/json",
        //async: false,
        dataType: "json",
        data:ADDmenberInfo,
        success: function (data) {
        	$("#dishes-type-save_success").attr("numberphone",ADDmenberInfotoo.mobile)
        	$('#modalmember_success').modal('show');
        	newReset();
        	$("#newAddMember #memberAdd").attr("disabled","disabled");
        	
        }
    });
}
function newReset(){// 重置新增会员数据
	$("#mobileNumber_erro").remove();
	$("#mobile").val("");//电话号码
	$("#membername").val("");//名字
    $("#birthday").val("");//生日
	$("#address").val("");//分店地址
	$("#createuser").val("");//开卡员工
	$("#gender").prop('selectedIndex', 0)//性别
	$('#store').prop('selectedIndex', 0);//门店
}
function bindMemberCard(obj){
	$("#Bindphone").val('');
	$('#modalmember_success').modal('hide');
	$("#noInfo_erro").remove();
	$(".addclone").remove();
	$("#addmemberCard_erro").remove();
	$("#menber_table").html("");
	var html='<div class="dligo-margin addclone" style="margin-top:10px;">';
    html+=' <div class="dligo-margin-left  linheight-30" style="width:auto;text-align:left;line-height:30px">卡<span class="classOne_b_text"></span>号：</div>';
    html+=' <span style="padding-left:10px"><input class="addmemberCard"  style="width:150px;height:30px;" maxlength="30" type="text" class="form-control "></span>';
    html+=' <span style="padding-left:16px">会员等级：</span>';
    html+=' <select class=" ztree-right-16 member_rank" style="width:80px; float:inherit;margin-left:0px;">'
    	for (var i = 1; i < menmberHTMLName.name.length; i++) {//会员卡等级
	    	html+= '<option value="' + menmberHTMLName.name[i].value + '">' + menmberHTMLName.name[i].name + '</option>';
                
            }
    html+=' 	</select>'
    html+=' <span style="padding-left:16px">门店：</span>';
    html+=' <select class=" ztree-right-16 member_Stores" style="width:80px; float:inherit;margin-left:0px;">'
    	for (var i = 0; i < menmberHTMLName.stores.length; i++) {//门店
    	html+= '<option value="' + menmberHTMLName.stores[i].value + '">' + menmberHTMLName.stores[i].name + '</option>';
            
        }
       
    html+='	</select>'
    /*html+=' <span  style="background:rgb(122, 196, 84);color:#fff;padding:5px 10px;border-radius:10px;cursor:pointer;margin-left:30px;"onclick="addRow()"> 增加</span>';*/
    html+=' </div>';
    var str='<tr>';
        str +=' <td class="classOne_b">会员姓名：</td>'
    	str +='<td class="classOne_b">性<span class="classOne_b_text"></span>别：</td>'
        str +='</tr>'
        str +=' <tr>'
        str +='<td class="classOne_b" colspan="2">地<span class="classOne_b_text"></span>址：</td>'
        str +=' </tr>'
        str +='<tr>'
        str +='<td class="classOne_b">生<span class="classOne_b_text"></span>日：</td>'
        str +=' <td class="classOne_b">卡片数量：</td>'
        str +=' </tr>'
    $("#BlindCardInfo .modal-body").append(html);
     $("#menber_table").append(str);
     if($(obj).attr("numberphone")){
 		$("#Bindphone").val($(obj).attr("numberphone"));
 		bindMemberSearch();
 	}
	$('#BlindCardInfo').modal('show');
	
}
function bindmemberSave(){//保存绑定会员实体卡
	var membercardInfo=[];
	 $(".addclone").each(function(){
		 var arr  =
		    {
		        "cardName" : $.trim($(this).find(".addmemberCard").val()),
		        "menberRank" : $.trim($(this).find(".member_rank").val()),
		        "memberStores":$.trim($(this).find(".member_Stores").val()),
		    }
		 membercardInfo.push(arr);
	  });
	 var mobile = $.trim($("#Bindphone").val());//电话号码
	 var dataInfo = {"telepno":mobile,"cardInfo":membercardInfo};
	 $.ajax({
	        url: basePath + "/memberManager/webBindingCard",
	        type: "post",
	        //async: false,
	        contentType: "application/json", 
	        dataType: "json",
	        data:JSON.stringify(dataInfo),
	        success: function (data) {
	        	var html="";
	        	if(data.success.length>0){
	        	    html+='<div style="color:rgb(122, 196, 84)"><i class="glyphicon glyphicon-ok-circle" style="padding-right:16px"></i>绑定卡号';
	        	   for(var i = 0; i < data.success.length; i++){
	        	        html+='<span style="padding-left:5px;color:rgb(122, 196, 84)">'+data.success[i]+'</span>';
	        	    }
	        	    html+='成功</div>';
	        	}
	        	if(data.fail.length>0){
	        	    html+='<div style="color:red"><i class="glyphicon glyphicon-remove-circle"style="padding-right:16px" ></i>绑定卡号';
		        	for(var i = 0; i < data.fail.length; i++){
		        	    html+='<span style="padding-left:5px">'+data.fail[i]+'</span>'
		        	}
		        	 html+='失败</div>'
	        	}
	        	var InfoMSgCOn=html;
	        	var InfoMSgHeader="绑定会员实体卡"
	        	successInfoMSg(InfoMSgHeader,InfoMSgCOn)
	        	$('#BlindCardInfo').modal('hide');
	        	var postDatabBase = $("#User-list").jqGrid("getGridParam", "postData");//会员基本信息
	    	    //将查询参数融入postData选项对象
	    		var jsonData=SearchData;
	    	    $.extend(postDatabBase, jsonData);//会员基本信息	    	   
	    	    $("#User-list").jqGrid("setGridParam", {//会员基本信息
	    	    	postDatabBase: jsonData, search: true, mtype: "get"    // 将jqGrid的search选项设为true      
	    	    }).trigger("reloadGrid");
	        	
	        },
	      error:function(data){
	    	  $('#modal_Error .p1 span').text("绑定会员卡失败，请稍后再试！");
	    	  $('#modal_Error').modal('show');
	      }
	    
	    });
}
function successInfoMSg(InfoMSgHeader,InfoMSgCOn){//成功弹窗
	$("#success_MSG").text(InfoMSgHeader);
	$("#success_MSG_infolist").html(InfoMSgCOn);
	$('#success_MSGInfo').modal('show');
}
function errorInfoMSg(InfoMSgHeader){//错误弹窗
	$('#modal_Error .p1 span').text(InfoMSgHeader)
	$('#modal_Error').modal('show');
}
function addRow(){
	var addcloneSize=$(".addclone").size()
	if(addcloneSize<5){
	var html='<div class="dligo-margin addclone" style="margin-top:10px;">';
	    html+=' <div class="dligo-margin-left  linheight-30" style="width:auto;text-align:left;line-height:30px">卡<span class="classOne_b_text"></span>号：</div>';
	    html+=' <span style="padding-left:10px"><input class="addmemberCard"  style="width:150px;height:30px;" maxlength="30" type="text" class="form-control "></span>';
	    html+=' <span style="padding-left:16px">会员等级：</span>';
	    html+=' <select class=" ztree-right-16 member_rank" style="width:80px; float:inherit;margin-left:0px;">'
	    	for (var i = 1; i < menmberHTMLName.name.length; i++) {//会员卡等级
		    	html+= '<option value="' + menmberHTMLName.name[i].value + '">' + menmberHTMLName.name[i].name + '</option>';
	                
	            }
	    html+=' 	</select>'
	    html+=' <span style="padding-left:16px">门店：</span>';
	    html+=' <select class=" ztree-right-16 member_Stores" style="width:80px; float:inherit;margin-left:0px;">'
	    	for (var i = 0; i < menmberHTMLName.stores.length; i++) {//门店
	    	html+= '<option value="' + menmberHTMLName.stores[i].value + '">' + menmberHTMLName.stores[i].name + '</option>';
                
            }
           
	    html+='	</select>'
	    html+=' <span class="delRow_info" style="background:#fff;border:1px #ccc solid;color:#c8c7c7;padding:5px 10px;border-radius:10px;cursor:pointer;margin-left:30px; ">删除</span>';
	    html+=' </div>';
	    $("#BlindCardInfo .modal-body").append(html);
	    $(".addmemberCard:last").focus();
	}
	else{
		var InfoMSgHeader="每次最多只能，绑定5张实体卡！"
		errorInfoMSg(InfoMSgHeader)
	}
}
$("body").on("click",".delRow_info",function(){
	$(this).parent().remove();
})
$("body").on("blur",".addmemberCard",function(){//会员卡失去焦点校验
    	//$("#addmemberCard_erro").remove();
    	var phoneNumber = /^1[3|4|5|7|8]\d{9}$/  //电话号码
   		var mobile = $.trim($("#Bindphone").val());//电话号码
    	var _this=$(this);
        var mobileNumber=$.trim($(this).val());
    	var branchId=$.trim($(this).parent().parent().find(".member_Stores").val());
    	  if(mobileNumber!=""){
              $.ajax({
      	        url: basePath + "/memberbase/membermanage/findMemberCard",
      	        type: "post",
      	        //async: false,
      	        dataType: "json",
      	        data:{"cardno":mobileNumber,"branchId":branchId},
      	        success: function (data) {
      	        	if(data.cardnum ==0){
      	        		    if(!phoneNumber.test(mobile) || mobile=="" || $("#noInfo_erro").size() > 0 ){
      	        		    	 $("#addmember_tijiao").attr("disabled","disabled");
      	        		    }
      	        		    else{
      	        		    	 $(".addmemberCard").not(_this).each(function () { //判断输入的会员卡是否有重复
      	        	            	  //$(".addmemberCard").css("background", "#fff");
      	        	                  if ($(this).val() == mobileNumber && $(this).val() != "") {
      	        	                	$(".addmemberCard").attr("disabled","disabled");//输入框不能选择
      	        	                	$(".member_rank").attr("disabled","disabled");//会员卡等级不能选择
      	        	                	$(".member_Stores").attr("disabled","disabled");//门店不能选择
      	        	                	$(".addmemberCard").css("background", "#eee");
      	        	                      if (_this.parent().parent().find(".addmemberCard_erro").size() < 1) {
      	        	                    	  _this.parent().parent().append("<div class='addmemberCard_erro' style='color:red;padding-left:75px;'>会员卡号不能相同</div>");
      	        	    	        	       }
      	        	                      else {
      	        	                    	_this.parent().parent().find(".addmemberCard_erro").text("会员卡号不能相同");
    	        	    	        	       }
      	        	                    $("#addmember_tijiao").attr("disabled","disabled");
  	        	                    	_this.css("background", "pink");
  	        	                    	_this.removeAttr("disabled");
  	        	                    	_this.parent().parent().find(".member_Stores").removeAttr("disabled");
  	        	                    	_this.parent().parent().find(".member_rank").removeAttr("disabled");
      	        	                    return false;
      	        	                  }
      	        	                  else{
      	        	                	 _this.css("background", "#fff");
      	        	                	_this.parent().parent().find(".addmemberCard_erro").remove();
      		        	                $("#addmember_tijiao").removeAttr("disabled"); 
      		        	                $(".addmemberCard").removeAttr("disabled"); 
      		        	                $(".member_Stores").removeAttr("disabled");
      		        	                $(".member_rank").removeAttr("disabled");
      		        	                $(".addmemberCard").css("background", "#fff");
      	        	                  }
      	        	              })
      	        	              if($(".addmemberCard").size()<2){
      	        	            	 _this.css("background", "#fff");
   		        	                $("#addmember_tijiao").removeAttr("disabled"); 
   		        	                _this.parent().parent().find(".addmemberCard_erro").remove();
      	        	              }
      	        	           
      	        		    }
      	        		    if( $(".addmemberCard_erro").size()>0 ){
      	        		    	$("#addmember_tijiao").attr("disabled","disabled");
      	        		    }
      	        	}
      	        	else{
      	        		_this.css("background", "pink");
      	        		if (_this.parent().parent().find(".addmemberCard_erro").size() < 1) {
      	        			_this.parent().parent().append("<div class='addmemberCard_erro' style='color:red;padding-left:75px;'>该会员卡号已存在</div>");
      	        	       }
      	        		else{
      	        			_this.parent().parent().find(".addmemberCard_erro").text("该会员卡号已存在");
      	        		}
      	        		 $("#addmember_tijiao").attr("disabled","disabled");
      	        	}
      	        }
      	    });
             
    	  }
    	  else{
    		  $("#addmember_tijiao").attr("disabled","disabled");
    		  if (_this.parent().parent().find(".addmemberCard_erro").size() < 1) {
      			$(this).parent().parent().append("<div class='addmemberCard_erro' style='color:red;padding-left:75px;'>请输入正确的会员卡号</div>");
      			
      	       }
    	  }
    	  
    	  
    	});

$("body").on("change",".member_Stores",function(){//绑定会员卡改变门店
	$("#addmemberCard_erro").remove();
	  var branchId=$.trim($(this).val());
	  var _this=$(this);
	  var phoneNumber = /^1[3|4|5|7|8]\d{9}$/  //电话号码
	  var mobileBind = $.trim($("#Bindphone").val());//电话号码
	  var mobile=$.trim($(this).parent().find(".addmemberCard").val());
	  var _thisinput=$(this).parent().find(".addmemberCard");
	  if(mobile!=""){
		  $.ajax({
	        url: basePath + "/memberbase/membermanage/findMemberCard",
	        type: "post",
	        //async: false,
	        dataType: "json",
	        data:{"cardno":mobile,"branchId":branchId},
	        success: function (data) {
	        	if(data.cardnum ==0){
	        		if(!phoneNumber.test(mobileBind) || mobileBind=="" || $("#noInfo_erro").size() > 0 ){
	        		    	 $("#addmember_tijiao").attr("disabled","disabled");
	        		    }
	        		else{
	        			_thisinput.css("background", "#fff");
        	            $("#addmember_tijiao").removeAttr("disabled"); 
        	            _this.parent().find(".addmemberCard_erro").remove();
	        		}
  	        	}
  	        	else{
  	        		if (_this.parent().find(".addmemberCard_erro").size() < 1) {
  	        			_this.parent().append("<div class='addmemberCard_erro' style='color:red;padding-left:75px;'>该会员卡号已存在</div>");
  	        	       }
  	        		_thisinput.css("background", "pink");
  	        		 $("#addmember_tijiao").attr("disabled","disabled");
  	        	}
	        }
	    }); 
	  }
	  else{
		  $("#addmember_tijiao").attr("disabled","disabled");
		  if ($(".addmemberCard_erro").size() < 1) {
			$(this).parent().parent().append("<div class='addmemberCard_erro' style='color:red;padding-left:75px;'>请输入正确的会员卡号</div>");
			
	       }
	  }
	 
	});
function bindMemberSearch(){//绑定会员卡信息查询电话号码
	$("#noInfo_erro").remove();
	 var phoneNumber = /^1[3|4|5|7|8]\d{9}$/  //电话号码
	var mobile = $.trim($("#Bindphone").val());//电话号码
    if(!phoneNumber.test(mobile) || mobile==""){
    	if ($("#noInfo_erro").size() < 1) {
  			$("#Bindphone").parent().parent().append("<div id='noInfo_erro' style='color:red;padding-left:75px;font-size:12px'>请输入正确的手机号码</div>");
  	       }
    	return
    }
	$.ajax({
        url: basePath + "/memberbase/membermanage/newmemberinfo",
        type: "post",
        //async: false,
        dataType: "json",
        data:{mobile:mobile},
        success: function (data) {
        	if(data.resultData){
        		$("#menber_table").html("");
        		$("#addmember_tijiao").attr("disabled","disabled");
      		  if ($("#noInfo_erro").size() < 1) {
      			$("#Bindphone").parent().parent().append("<div id='noInfo_erro' style='color:red;padding-left:75px;font-size:12px'>没有找到对应用户信息</div>");
      			
      	       }
      		var str='<tr>';
            str +=' <td class="classOne_b">会员姓名：</td>'
            str +='<td class="classOne_b" >性<span class="classOne_b_text"></span>别：</td>'
            str +='</tr>'
            str +=' <tr>'
            str +='<td class="classOne_b" colspan="2">地<span class="classOne_b_text"></span>址：</td>'
            str +=' </tr>'
            str +='<tr>'
            str +='<td class="classOne_b">生<span class="classOne_b_text"></span>日：</td>'
            str +=' <td class="classOne_b">卡片数量：</td>'
            str +=' </tr>'
         $("#menber_table").append(str);
        	}
        	else{
        		$("#menber_table").html("");
            	var str='<tr>';
                str +=' <td class="classOne_b" >会员姓名：<span style="padding-left:10px"></span>'+data.membername+'</td>'
                	if(data.gender==1){
                		str +='<td class="classOne_b" >性<span class="classOne_b_text"></span>别：<span style="padding-left:10px">女</span></td>'
                	}
                	else{
                		str +='<td class="classOne_b" >性<span class="classOne_b_text"></span>别：<span style="padding-left:10px">男</span></td>'
                	}
                str +='</tr>'
                str +=' <tr>'
                str +='<td class="classOne_b" colspan="2">地<span class="classOne_b_text"></span>址：<span style="padding-left:10px"></span>'+data.memberaddr+'</td>'
                str +=' </tr>'
                str +='<tr>'
                str +='<td class="classOne_b">生<span class="classOne_b_text"></span>日：<span style="padding-left:10px"></span>'+data.memberbir+'</td>'
                str +=' <td class="classOne_b">卡片数量：<span style="padding-left:10px"></span>'+data.cardnum+'</td>'
                str +=' </tr>'
             $("#menber_table").append(str);
        	}
        	
        }
    });
}
$("#info_mobile").blur(function(){//修改会员信息电话号码失去焦点
	$("#info_mobile_erro").remove();
	var phoneNumber = /^1[3|4|5|7|8]\d{9}$/  //电话号码
	var mobile = $.trim($("#info_mobile").val());//电话号码
	var id=$("#info_id").val();//ID
   if(!phoneNumber.test(mobile) || mobile==""){
   	if ($("#info_mobile_erro").size() < 1) {
 			$("#info_mobile").parent().parent().append("<div id='info_mobile_erro' style='color:red;padding-left:95px;padding-top:10px;'>请输入正确的手机号码</div>");
 	       }
   	$("#member_info_update_ok").attr("disabled","disabled");
   	return
   }
   $.ajax({
       url: basePath + "/memberbase/membermanage/newcardmemberinfo",
       type: "post",
       //async: false,
       dataType: "json",
       data:{"mobile":mobile,"id":id},
       success: function (data) {
       	if(data.membernum!=0){
       		if ($("#info_mobile_erro").size() < 1) {
     			$("#info_mobile").parent().parent().append("<div id='info_mobile_erro' style='color:red;padding-left:95px;padding-top:10px;'>该手机号码已存在</div>");
     	       }
       		$("#member_info_update_ok").attr("disabled","disabled");
       	}
       	else{
       		$("#info_mobile_erro").remove();
       		$("#member_info_update_ok").removeAttr("disabled");
       	}
       }
   });
   
	});
function Transfer(obj){//转账
	var obj=JSON.parse(obj);//Json字符串转对象
	console.log(obj)
	//alert(obj.membername)
}
