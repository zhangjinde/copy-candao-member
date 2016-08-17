//所有方法的gridTable可以不用指定，默认为'gridTable'
gt = 'gridTable';

function getGridTable(gridTable){
	if(gridTable == null){
		gridTable = gt;
	}
	return gridTable;
}

//单选取值
function getId(gridTable){
    gridTable = getGridTable(gridTable);
	var pId = $("#" + gridTable).jqGrid("getGridParam", "selrow");
	return pId;
}

//复选取值
function getIds(gridTable){
  	gridTable = getGridTable(gridTable);
	var pId = $("#" + gridTable).jqGrid("getGridParam", "selarrrow");
	return pId;
}


//单选删除
function delId(gridTable){
  	gridTable = getGridTable(gridTable);
	var data = $("#" + gridTable).jqGrid("getGridParam", "selrow");
	var delData = data;
    $("#" + gridTable).jqGrid("delRowData", delData);
}


//多选删除
function delIds(gridTable){
  	gridTable = getGridTable(gridTable);
	var data = $("#" + gridTable).jqGrid("getGridParam", "selarrrow");
	if(data.length != 0){
		delData = new Array(); 
		for (var i = 0; i < data.length; i++) {
			delData[i] = data[i];
		}
		for(var i = 0; i < delData.length; i++){
			$("#" + gridTable).jqGrid().delRowData(delData[i]);
		}	
	}
}

//多选删除  param为后台接收的属性值
function delGridMethod(url, param, gridTable){
	gridTable = getGridTable(gridTable);
	var data = getIds(gridTable);		
	if(data.length != 0){
		if (confirm('数据删除后无法恢复！请确定是否真要删除选定的数据？')) {
			$.post(url, param + '=' + data,
			function(returnData){
				if(returnData == 'OK'){
					delIds(gridTable);		
					alert("删除成功");
				}
				else if(returnData == 'NO'){
					alert("删除失败");
				}
				data.length = 0;
			});
		}
	}
	else{
		alert("请选择要删除的数据");
	}
}
//单选删除
function delGridForRadio(url, param, gridTable){
	gridTable = getGridTable(gridTable);
	var data = getId(gridTable);	
	if(data != null && data.length != 0){
		$.confirm("删除数据","数据删除后无法恢复！</br>请确定是否真要删除选定的数据？",function(){	
			$.ajax({
				type: "POST",
				dataType: "text",
				url: url,
		        data: param + '=' + data,
		        success: function(data) { 
					if(data == 'OK'){
						delId(gridTable);		
						$.alert('删除数据','删除成功！');
					}
					else if(data == 'NO'){
						$.alert('删除数据','删除失败！');
					}
					data.length = 0;
		        }
    		});   	
		},function(){});
	}
	else{
		$.alert('删除数据','请选择要删除的数据！');
	}
}


//重新载入Grid，包含表单所有请求参数, formName为表单Id 
function reloadGrid(formName, gridTable){	
	gridTable = getGridTable(gridTable);
	var formData = $('#' + formName).serializeArray();
    //获得当前postData选项的值  
    var postData = $("#" + gridTable).jqGrid("getGridParam", "postData");  
      
    //将查询参数融入postData选项对象  
    //$.extend(postData, sdata);  
    for(i = 0; i < formData.length; i ++){
		postData[formData[i].name] = formData[i].value;
	}
  
    $("#" + gridTable).jqGrid("setGridParam", {  
        search: true,
        mtype : "post"    
    }).trigger("reloadGrid", [{page:1}]);   
}
//重载Grid,指定url
function reloadGrid(formName, gridTable, url){	
	gridTable = getGridTable(gridTable);
	var formData = $('#' + formName).serializeArray();
    //获得当前postData选项的值  
    var postData = $("#" + gridTable).jqGrid("getGridParam", "postData");  
      
    //将查询参数融入postData选项对象  
    //$.extend(postData, sdata);  
    for(i = 0; i < formData.length; i ++){
		postData[formData[i].name] = formData[i].value;
	}
    $("#" + gridTable).jqGrid("setGridParam", {  
        search: true,
        mtype : "post",
        url : url    
    }).trigger("reloadGrid", [{page:1}]);   
}
/**
 * 动态计算高度
 * 默认参数: {"tableId":"gridTable","thHeight":40,"pagerHeight":25,"rowHeight":25}
 * @param options
 */
function dynamicHeight(options) {
	options = options || {};
	var gridTable = getGridTable(options.tableId);
	var thHeight = (typeof options.thHeight != 'undefined') ? options.thHeight : 40;
	var pagerHeight = (typeof options.pagerHeight != 'undefined') ? options.pagerHeight : 25;
	//console.debug(pagerHeight);
	// tableHeight
	var tableHeight = $(window).height() - $("#"+gridTable).offset().top;
	tableHeight = tableHeight - (thHeight + pagerHeight);
	// rowNum  options.rowHeight :28
	var rowHeight = (typeof options.rowHeight != 'undefined') ? options.rowHeight : 25;
	var rowNum = parseInt(tableHeight / rowHeight);
	// rowList
	var multi = parseInt(rowNum / 5);
	var list2 = (multi + 1) * 5;
	var list3 = (multi + 2) * 5;
	var list4 = (multi + 3) * 5;
	return {"tableHeight": tableHeight, "rowNum": rowNum, "rowList": [rowNum, list2, list3, list4]};
}
//自适应宽度
function dynamicWidth() {
//	var $e=$("#gridTable").closest(".gridPanel");
//	alert($e.width());
//	
//	$("#gridTable").setGridWidth($e.width());
	
//	$("div[class='ui-jqgrid ui-widget ui-widget-content-grid ui-corner-all']").removeClass("width");   
//    $("div[class='ui-jqgrid ui-widget ui-widget-content-grid ui-corner-all']").css("width","100%");   
//    $("div[class='ui-jqgrid-view']").removeClass("width");   
//    $("div[class='ui-jqgrid-view']").css("width","100%");   
//    $("div[class='ui-state-default ui-jqgrid-hdiv']").removeClass("width");   
//    $("div[class='ui-state-default ui-jqgrid-hdiv']").css("width","100%");   
//    $("table[class='ui-jqgrid-htable']").removeClass("width");   
//    $("table[class='ui-jqgrid-htable']").css("width","100%");   
//    $("div[class='ui-state-default ui-jqgrid-pager ui-corner-bottom']").removeClass("width");   
//    $("div[class='ui-state-default ui-jqgrid-pager ui-corner-bottom']").css("width","100%");  
//    $("div.ui-jqgrid-bdiv").css("width","100%");
//    $("table.ui-jqgrid-btable").css("width","98.2%");
    // 滚动条处理 98.2%
//    $("div.ui-jqgrid-hbox").css("padding-right", "20");
}
