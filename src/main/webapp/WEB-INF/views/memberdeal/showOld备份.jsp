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
	<style>
	#dealDetail td{
		text-align:center;
	}
	.col-xs-12 span{ 
		padding-right:40px;
	}
	</style>
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
			
			<form id="query" action="<%=request.getContextPath()%>/memberdeal/searchPage" method="post" >
			<div class="shop-search">
				<div class="form-group" style="float: left;width: 100%;">
					<label class="control-label col-xs-1">手机号：</label>
					<div class="col-xs-2">
						<input type="text" id="mobile"  class="form-control"  name="mobile" value="${mobile}"/>
					</div>
					<label class="control-label col-xs-1">姓名：</label>
					<div class="col-xs-2">
						 <input type="text" id="name" name="name"   value="${name}" class="form-control"  >
					</div>		
					<label class="control-label col-xs-1">生日：</label>
					<div class="col-xs-2">
						 <input type="text" id="birthday" name="birthday" value="${birthday}"
							class="form-control" readonly onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					</div>
				</div>
				<div class="form-group" style="float: left;width: 100%;">
					<label class="control-label col-xs-1">余额：</label>
					<div class="col-xs-2">
						  <input type="text" id="valuemin" name="valuemin"  value="${valuemin}" class="form-control" style="display: inline;width: 45%;" ><i style="width: 6%">--</i>
						  <input type="text" id="valuemax" name="valuemax"  value="${valuemax}" class="form-control" style="display: inline;width: 45%;" >
					</div>		
					<label class="control-label col-xs-1">积分：</label>
					<div class="col-xs-2">
						 <input type="text" id="pointmin" name="pointmin"   value="${pointmin}" class="form-control" style="display: inline;width: 45%;" ><i style="width: 6%">--</i>
						 <input type="text" id="pointmax" name="pointmax"   value="${pointmax}" class="form-control"  style="display: inline;width: 45%;">
					</div>		
					
						
					
					<div class="col-xs-2 shop-search-btn ">
						<button class="btn btn-default" type="submit"><i class="icon-search"></i>搜索</button>						
					</div>	
					
				</div>
			</div>
			</form>


			<table class="table table-list shop-store-table">
				<thead>
					<tr>
						<th>序号</th>
						<th>卡号</th>
						<th>手机号</th>
						<th>等级</th>
						<th>姓名</th>
						<th>生日</th>
						<th>余额</th>
						<th>门店ID</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody style="font-size: 12px;">
				<c:forEach var="item" items="${datas}" varStatus="i">
					<tr  <c:if test="${i.index%2==0}"> class="odd"</c:if>   onmouseover="showOpr(this)"  onmouseout="hideOpr(this)">
						<td class="tdclass">${(current-1)*pagesize+(i.index+1)}</td>
						<td class="tdclass">${item.cardno }</td>
						<td class="tdclass">${item.mobile }</td>
						<td class="tdclass">
						<c:forEach var="it" items="${list}" varStatus="i">
							<c:if test="${item.level==it.level}">
								${it.discount}
							</c:if>
							<%-- <span onclick="levelselect('${item.code_id}','${item.code_desc}')"><label>${item.code_desc}</label></span> --%>
						</c:forEach>
						</td>
						<td class="tdclass" width='20%'>${item.name }</td>
						<td class="tdclass">${item.birthday }</td>
						<td class="tdclass"><font color="red">${item.value }</font></td>
						<td class="tdclass">${item.branch_id }</td>
						<td class="tdclass" width="10%">
							<div class="operate">
								<a href="javascript:void(0)" style="text-decoration: none;" onclick="dealList('${item.cardno}')">查看消费记录</a>
							</div>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="pagingWrap">
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