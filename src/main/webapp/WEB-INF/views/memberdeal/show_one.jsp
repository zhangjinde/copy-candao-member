<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>门店管理</title>
	<meta name="description" content="">
	<meta name="keywords" content="">
	<link href="<%=request.getContextPath()%>/tools/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/member.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/tenant.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/tools/font-awesome/css/font-awesome.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json2.js"></script>
	<script src="<%=request.getContextPath()%>/scripts/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/commons.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/member.js"></script>
	<script src="<%=request.getContextPath()%>/scripts/jquery.twbsPagination.js"></script>
	<script src="<%=request.getContextPath()%>/tools/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/tools/jquery-validation/jquery.validate.js"></script>
	<script src="<%=request.getContextPath()%>/tools/jquery-validation/messages_zh.js"></script>
	<script src="<%=request.getContextPath()%>/tools/calendar_diy/WdatePicker.js"></script>

</head>
<body> 
		<div class="ky-content content-iframe">
			<div class="shop-btn btn-add">
				<div class="btn-group-title" role="group" aria-label="...">
				  <div class="col-xs-12" style=" border-bottom: 1px #ccc solid;padding-bottom:10px">
				         <span>会员总人数  :<font color="red">${memberCount}</font>人 </span>
						 <span>会员总余额 :<font color="red">${allBalance}</font>元</span>
						 <span>会员储值总额 :<font color="red">${countmap.cashCount + countmap.bankCount + countmap.refundCount + countmap.presentCount}</font>元&nbsp;&nbsp;
						 (现金 :<font color="red">${countmap.cashCount}</font>元&nbsp;&nbsp;&nbsp;&nbsp;
						 银行卡 :<font color="red">${countmap.bankCount}</font>元&nbsp;&nbsp;&nbsp;&nbsp;
						 储值赠送:<font color="red">${countmap.presentCount}</font>元&nbsp;&nbsp;&nbsp;&nbsp;
						 会员注销退款 :<font color="red">${countmap.refundCount}</font>)元 </span>	
						<%-- 所有会员积分:${allPoint} --%>
					</div>	
				</div>
			</div>
			
			<input type="hidden" name="current" id="current" value="${current}">
			<input type="hidden" name="pagesize" id="pagesize" value="10">
			<input type="hidden"  id="total" value="${total}">
			<ul class="VIPtab">
               <li class="cur" id="cur1"><a >会员基础信息</a></li>
               <li id="cur2"><a >会员消费信息</a></li>
               <li id="cur3"><a >会员导入</a></li>
	 	    </ul>
			<form id="query" action="<%=request.getContextPath()%>/memberdeal/searchPage" method="post" >
			<div class="shop-search" id="VipOneTwo">
				<div class="form-group">
                        <label class="control-label">会员卡号/电话：</label>
                        <input style="width:150px" type="text" class="form-control ng-pristine ng-valid" size="12">
                 </div>
                 <div class="form-group shop-search-btn">
                        <button class="btn btn-default btn-outline " type="submit"><i class="icon-search"></i>搜索</button>
                 </div>
                 <div class="form-group shop-search-btn">
                        <button class="btn btn-default btn-outline" data-toggle='modal' data-target='#ScreenSearch'>高级查询</button>
                 </div>
                 <div class="form-group shop-search-btn">
                        <button class="btn btn-default btn-outline">导出</button>
                 </div>
			</div>
			</form>

            <div class="User-list" style="display:block">
                 <table class="table table-list shop-store-table" id="User-list"> 
				<thead>
					<tr>
						<th>卡号</th>
						<th>姓名</th>
						<th>电话</th>
						<th>性别</th>
						<th>生日</th>
						<th>住址</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody style="font-size: 12px;">
					<tr   class="odd"  onmouseover="showOpr(this)"  onmouseout="hideOpr(this)">
						<td class="tdclass">100015000000</td>
						<td class="tdclass">于梦瑶</td>
						<td class="tdclass">13552731086</td>
						<td class="tdclass">女</td>
						<td class="tdclass" >1982-03-13</td>
						<td class="tdclass" width='15%'>--</td>
						<td class="tdclass" width='15%'>--</td>
					</tr>
					<tr   class="odd"  onmouseover="showOpr(this)"  onmouseout="hideOpr(this)">
						<td class="tdclass">100015000000</td>
						<td class="tdclass">于梦瑶</td>
						<td class="tdclass">13552731086</td>
						<td class="tdclass">女</td>
						<td class="tdclass" >1982-03-13</td>
						<td class="tdclass" width='15%'>--</td>
						<td class="tdclass" width='15%'>--</td>
					</tr>
					<tr   class="odd"  onmouseover="showOpr(this)"  onmouseout="hideOpr(this)">
						<td class="tdclass">100015000000</td>
						<td class="tdclass">于梦瑶</td>
						<td class="tdclass">13552731086</td>
						<td class="tdclass">女</td>
						<td class="tdclass" >1982-03-13</td>
						<td class="tdclass" width='15%'>--</td>
						<td class="tdclass" width='15%'>--</td>
					</tr>
					<tr   class="odd"  onmouseover="showOpr(this)"  onmouseout="hideOpr(this)">
						<td class="tdclass">100015000000</td>
						<td class="tdclass">于梦瑶</td>
						<td class="tdclass">13552731086</td>
						<td class="tdclass">女</td>
						<td class="tdclass" >1982-03-13</td>
						<td class="tdclass" width='15%'>--</td>
						<td class="tdclass" width='15%'>--</td>
					</tr>
					
				</tbody>
			</table>
			<div class="pagingWrap">
			</div>
			</div>
			<div class="User-list">
                   <table class="table table-list shop-store-table" id="User-consumption"> 
				<thead>
					<tr>
						<th>卡号</th>
						<th>姓名</th>
						<th>电话</th>
						<th>当前积分</th>
						<th>累计积分</th>
						<th>累计充值金额</th>
						<th>累计赠送金额</th>
						<th>余额</th>
						<th>累计消费次数</th>
						<th>开卡日期</th>
						<th>开卡员工</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody style="font-size: 12px;">
					<tr   class="odd"  onmouseover="showOpr(this)"  onmouseout="hideOpr(this)">
						<td class="tdclass">100015000000</td>
						<td class="tdclass">于梦瑶</td>
						<td class="tdclass">13552731086</td>
						<td class="tdclass">100</td>
						<td class="tdclass" >10000</td>
						<td class="tdclass" >100000</td>
						<td class="tdclass" >7000</td>
						<td class="tdclass"><font color="red">909909</font></td>
						<td class="tdclass">123</td>
						<td class="tdclass">1982-03-13</td>
						<td class="tdclass">于梦瑶</td>
						<td class="tdclass" >正常</td>
						<td class="tdclass" width="100">
						   <div class="operate">
								<a href="javascript:void(0)" style="text-decoration: none;" onclick="dealList('${item.cardno}')">查看消费记录</a>
							</div>
						</td>
					</tr>
					<tr   class="odd"  onmouseover="showOpr(this)"  onmouseout="hideOpr(this)">
						<td class="tdclass">100015000000</td>
						<td class="tdclass">于梦瑶</td>
						<td class="tdclass">13552731086</td>
						<td class="tdclass">100</td>
						<td class="tdclass" >10000</td>
						<td class="tdclass" >100000</td>
						<td class="tdclass" >7000</td>
						<td class="tdclass"><font color="red">909909</font></td>
						<td class="tdclass">123</td>
						<td class="tdclass">1982-03-13</td>
						<td class="tdclass">于梦瑶</td>
						<td class="tdclass" >正常</td>
						<td class="tdclass" width="100">
						   <div class="operate">
								<a href="javascript:void(0)" style="text-decoration: none;" onclick="dealList('${item.cardno}')">查看消费记录</a>
							</div>
						</td>
					</tr>
					
				</tbody>
			</table>
			<div class="pagingWrap">
			</div>
			</div>
			<div class="User-list">
                3
			</div>
			
		</div>

	 
		<!--点击按钮弹出添加界面 -->
	<div class="modal fade shop-dialog in " id="dealListDialog" data-backdrop="static">
		<div class="modal-dialog" style="width: 1000px;">
			<div class="modal-content">
				<div class="modal-header addDelicon">
					<div class="modal-header tenantInfo">消费记录</div>
					<img src="<%=request.getContextPath()%>/images/close.png" class="img-close" data-dismiss="modal">
				</div>
				
				<table class="table table-list shop-store-table">
				<thead>
					<tr>
						<th>交易号</th>
						<th>交易地点</th>
						<th>交易类型</th>
						<th>交易值</th>
						<th>票据单号</th>
						<th>交易时间</th>
						<!-- <th>交易者</th> -->
					</tr>
				</thead>
				<tbody id="dealDetail" style="font-size: 12px;">
				</tbody>
			</table>
			<div class="detailPagingWrap"></div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
$(".VIPtab li").click(function () {
    $(".VIPtab li").removeClass("cur");
    $(this).addClass("cur");
    if($(this).index()==2){
    	$("#VipOneTwo").hide()
    }
    else{
    	$("#VipOneTwo").show()
    }
    $(".User-list").hide().eq($(this).index()).show();//显示隐藏DIV切换
})
if( $("#total").val() > 10 ){
	$(".pagingWrap").html('<ul class="paging clearfix">');
	$(".paging").twbsPagination({
		totalPages: '${totalpage}',
		visiblePages: 7,
		startPage : parseInt('${current}'),
		first: '...',
		prev : '<',
		next : '>',
        last: '...',
        onPageClick: function (event, page){
        	var mobile = $("#mobile").val();
        	var name = $("#name").val();
        	var birthday = $("#birthday").val();
        	var valuemin = $("#valuemin").val();
        	var valuemax = $("#valuemax").val();
        	var pointmin = $("#pointmin").val();
        	var pointmax = $("#pointmax").val();
        	window.location.href=global_Path+ '/memberdeal/searchPage?current='+page+"&pagesize="+$("#pagesize").val()+
        	'&mobile='+mobile+'&name='+name+'&birthday='+birthday+'&valuemin='+valuemin+
        	'&valuemax='+valuemax+'&pointmin='+pointmin+'&pointmax='+pointmax;  
        }
	});
}else {
	$(".pagingWrap").empty();
}
//查看消费记录
function dealList(cardno){
	$.ajax({
		type : "post",
		async : false,
		data : { 'cardno':cardno },
		url:global_Path + '/memberdeal/findDealDetailByCardno.json',
		dataType : "json",
		success : function(result) {
			var cardno = result.cardno;
			if(result.detailDatas == ""){
				alert("该会员没有消费记录");
			}
			else{
				var html = "";
				$.each(result.detailDatas, function(i,val){ 
					var time=val.deal_time;
					var dataTime = new Date(parseInt(time.substr(0, 10)) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
					var type = val.deal_type;
					var addr = val.deal_addr;
					var slipNo = val.slip_no;
					if(addr == ""){
						addr = "--";
					}
					if(slipNo.substr(0, 1) != "H"){
						slipNo = "--";
					}
					switch (type) {
						case "0" : type = "现金消费"; break;
						case "1" : type = "现金消费积分"; break;
						case "2" : type = "储值消费"; break;
						case "3" : type = "储值消费积分"; break;
						case "4" : type = "积分消费"; break;
						case "5" : type = "现金充值"; break;
						case "6" : type = "现金消费反结算"; break;
						case "7" : type = "现金消费积分反结算"; break;
						case "8" : type = "储值消费反结算"; break;
						case "9" : type = "储值消费积分反结算"; break;
						case "10" : type = "积分消费反结算"; break;
						case "11" : type = "银行卡充值"; break;
						
						case "13" : type = "微信扫码支付"; break;
						case "14" : type = "微信扫码支付反结算"; break;
						case "15" : type = "微信扫码支付积分"; break;
						case "16" : type = "微信扫码支付积分反结算"; break;
						
						case "17" : type = "储值赠送"; break;
						
						default: type = "未知"; break;
					}
					html += '<tr>'
						+ '<td>' +val.deal_no+ '</td>'
						+ '<td>' +addr+ '</td>'
						+ '<td>' +type+ '</td>'
						+ '<td><font color="red">' +val.amount+ '</font></td>'
						+ '<td>' +slipNo+ '</td>'
						+ '<td>' +dataTime+ '</td>'
						/* + '<td>' +val.deal_user+ '</td>' */
						+'</tr>';
				});
				var tbody = $('table.shop-store-table').find('#dealDetail');
				tbody.html(html);
				
				if(result.detailTotal > 10){
					$(".detailPagingWrap").html('<ul class="detailPaging clearfix">');
					$(".detailPaging").twbsPagination({
						totalPages: result.detailTotalpage ,
						visiblePages: 7,
						startPage : parseInt(result.detailCurrent),
						first: '...',
						prev : '<',
						next : '>',
				        last: '...',
				        onPageClick: function (event, detailPage){
				        	$.ajax({
				        		type : "post",
				        		async : false,
				        		data : { 
				        			'cardno':cardno,
				        			'current': detailPage,
				        			'pagesize' : 10
				        		},
				        		url:global_Path + '/memberdeal/findDealDetailByCardno.json',
				        		dataType : "json",
				        		success : function(result) {
				        			var html = "";
				    				$.each(result.detailDatas, function(i,val){ 
				    					var time=val.deal_time;
				    					var dataTime = new Date(parseInt(time.substr(0, 10)) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
				    					var type = val.deal_type;
				    					var addr = val.deal_addr;
				    					var slipNo = val.slip_no;
				    					if(addr == ""){
				    						addr = "--";
				    					}
				    					if(slipNo.substr(0, 1) != "H"){
				    						slipNo = "--";
				    					}
				    					switch (type) {
					    					case "0" : type = "现金消费"; break;
											case "1" : type = "现金消费积分"; break;
											case "2" : type = "储值消费"; break;
											case "3" : type = "储值消费积分"; break;
											case "4" : type = "积分消费"; break;
											case "5" : type = "现金充值"; break;
											case "6" : type = "现金消费反结算"; break;
											case "7" : type = "现金消费积分反结算"; break;
											case "8" : type = "储值消费反结算"; break;
											case "9" : type = "储值消费积分反结算"; break;
											case "10" : type = "积分消费反结算"; break;
											case "11" : type = "银行卡充值"; break;
											
											case "13" : type = "微信扫码支付"; break;
											case "14" : type = "微信扫码支付反结算"; break;
											case "15" : type = "微信扫码支付积分"; break;
											case "16" : type = "微信扫码支付积分反结算"; break;
											
											case "17" : type = "储值赠送"; break;
											
				    						default: type = "未知"; break;
				    					}
				    					html += '<tr>'
				    						+ '<td>' +val.deal_no+ '</td>'
				    						+ '<td>' +addr+ '</td>'
				    						+ '<td>' +type+ '</td>'
				    						+ '<td><font color="red">' +val.amount+ '</font></td>'
				    						+ '<td>' +slipNo+ '</td>'
				    						+ '<td>' +dataTime+ '</td>'
				    						/* + '<td>' +val.deal_user+ '</td>' */
				    						+'</tr>';
				    				});
				    				var tbody = $('table.shop-store-table').find('#dealDetail');
				    				tbody.html(html);
				        		}
				        	});
				        }
					});
				}else {
					$(".detailPagingWrap").empty();
				}
				$("#dealListDialog").modal("show");
			}
	}});
}

</script>
</html>