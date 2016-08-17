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
        #dealDetail td {text-align: center;}
        .col-xs-12 span {padding-right: 40px;}
        .ouy{margin:0;padding:0;width:100%;height:auto;display:none;}
        .tab{margin:0;padding:0;list-style:none;width:100%;overflow:hidden;border-bottom: 1px #ccc solid; margin-bottom:10px;}
        .tab li{float:left;width:100px;height:40px;color:#000; text-align:center;line-height:40px;cursor:pointer; }
        .tab li a{ color:#000;height:41px;display:block;font-size:16px}
        .tab .cur a {color:rgb(122, 196, 84);font-weight:bold;border-bottom: 2px rgb(122, 196, 84) solid; }
        .select{background-color: rgb(122, 196, 84);text-align:center;height:30px;line-height:30px}
        .select a{color:#fff;}
        .no_select{height:30px;line-height:30px}
        .no_select a{color:#000}
        .col-xs-1{padding-left:0px}
    </style>
    
</head>
<body>
    <div class="ky-content content-iframe">
       <ul class="tab">
           <li class="cur" id="cur"><a href="../consumption/index?falg=1">会员卡充值</a></li>
           <li id="cur1"><a href="../consumption/index?falg=2">会员卡消费</a></li>
	 	</ul>
	<a href="Javascript:void(0)" onclick="toExportExl()" style="position: absolute; top: 30px; right: 20px;"><img src="<%=request.getContextPath()%>/images/download.png" alt=""></a>	 
       <!--  <form id="query" onsubmit="return toSearch()"> -->
        	<input type="hidden" id="current" name="current" value="${current}">
	        <input type="hidden" id="total" name="total" value="${total}">
	        <input type="hidden" id="totalpage" name="totalpage" value="${totalpage}">
	        <input type="hidden" id="falg" name="falg" value="${falg}">
	        <input type="hidden" id="timefalg" name="timefalg" value="${timefalg}">
            <div class="shop-search">
                <div class="form-group" style="float: left;width: 100%;">
                    <label class="control-label col-xs-1">交易时间：</label>
                    <div class="col-xs-4">
                        <input type="text" id="starttime" name="starttime" value="${starttime}" class="form-control" 
                        	   readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})" style="display: inline;width: 43%;">
                        <span style="width: 6%">到</span>
                        <input type="text" id="endtime" name="endtime" value="${endtime}" class="form-control" 
                               readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'})" style="display: inline;width: 43%;">
                    </div>
                    <div class="data">
                    <label id="data1" class="control-label col-xs-1 no_select" style="width: 55px;text-align: center;">
                    	<a href="../consumption/index?falg=${falg}&timefalg=1">今日</a>
                    </label>
                    <label id="data2" class="control-label col-xs-1 no_select" style="width: 55px;text-align: center;">
                    	<a href="../consumption/index?falg=${falg}&timefalg=2">昨日</a>
                    </label>
                    <label id="data3" class="control-label col-xs-1 no_select" style="width: 55px;text-align: center;">
                    	<a href="../consumption/index?falg=${falg}&timefalg=3">本月</a>
                    </label>
                    <label id="data4" class="control-label col-xs-1 no_select" style="width: 55px;text-align: center;">
                    	<a href="../consumption/index?falg=${falg}&timefalg=4">上月</a>
                    </label>
                    </div>
                    <div class="col-xs-2 shop-search-btn ">
                        <button class="btn btn-default" onclick="toSearch();"><i class="icon-search"></i>搜索</button>
                    </div>

                </div>
            </div>
            <div class="shop-btn btn-add">
                <div class="btn-group-title" role="group" aria-label="..." style="background:#f6fab8;margin-top:40px;">
                    <div class="col-xs-12" style=" border-bottom: 0px #ccc solid;padding-bottom:10px">
                        <span>交易时间 :
                        	<c:choose>
                        		<c:when test="${empty starttime}">
                        			<c:if test="${not empty detail}">
                        				<c:forEach var="item" items="${detail}" varStatus="i" begin="${fn:length(detail)-1}">
                        				<fmt:formatDate value="${item.deal_time}" pattern="yyyy-MM-dd HH:mm:ss"/>  
                        			</c:forEach>
                        			</c:if>
                        		</c:when>
                        		<c:when test="${not empty starttime}">${starttime}</c:when>
                        	</c:choose>
                        	 --
                        	<c:choose>
                        		<c:when test="${empty endtime}">
                        			<c:if test="${not empty detail}">
                        				<c:forEach var="item" items="${detail}" varStatus="i" begin="0" end="0">
                        					<fmt:formatDate value="${item.deal_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        				</c:forEach>
                        			</c:if>
                        		</c:when>
                        		<c:when test="${not empty endtime}">${endtime}</c:when>
                        	</c:choose>
                        </span><br/>
                        <c:choose>
                        	<c:when test="${falg == 1}">
                        		<span>会员卡充值总额:
                        			<font color="red">${cardCZ + cashCZ + cancelTK + presentCZ}</font>元 &nbsp;&nbsp;
                        			(银行卡充值:<font color="red">${cardCZ}</font>元 &nbsp;&nbsp;&nbsp;&nbsp;
                        			   现金充值:<font color="red">${cashCZ}</font>元&nbsp;&nbsp;&nbsp;&nbsp;
                        			   储值赠送:<font color="red">${presentCZ}</font>元&nbsp;&nbsp;&nbsp;&nbsp;
                        			   会员注销退款:<font color="red">${cancelTK}</font>元)
                        		</span>
                        	</c:when>
                        	<c:when test="${falg == 2}">
                        		<span>会员卡消费总额:<font color="red">${cardXF}</font>元 </span>
                        	</c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        <!-- </form> -->


        <table class="table table-list shop-store-table">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>交易时间</th>
                    <th>卡号</th>
                    <th>手机号</th>
                    <th>姓名</th>
                    <th>交易号</th>
                    <th>交易类型</th>
                    <th>交易值</th>
                    <th>票据单号</th>
                </tr>
            </thead>
            <tbody style="font-size: 12px;">
			<c:forEach var="item" items="${detail}" varStatus="i">
				<tr class="odd" onmouseover="showOpr(this)" onmouseout="hideOpr(this)">
                    <td class="tdclass">${(current-1) * pagesize+(i.index+1)}</td>
                    <td class="tdclass">
						<fmt:formatDate value="${item.deal_time}" pattern="yyyy-MM-dd HH:mm:ss" />                    
                    </td>
                    <td class="tdclass">${item.card_no}</td>
                    <td class="tdclass">${item.memberMobile}</td>
                    <td class="tdclass" width='20%'>${item.memberName}</td>
                    <td class="tdclass">
                    	<c:choose>
                    		<c:when test="${not empty item.deal_no }">${item.deal_no}</c:when>
                    		<c:when test="${empty item.deal_no }">--</c:when>
                    	</c:choose>
                    </td>
                    <td class="tdclass">
                    	<c:choose>
                    		<c:when test="${item.deal_type == '0'}">现金消费</c:when>
                    		<c:when test="${item.deal_type == '1'}">现金消费积分</c:when>
                    		<c:when test="${item.deal_type == '2'}">储值消费</c:when>
                    		<c:when test="${item.deal_type == '3'}">储值消费积分</c:when>
                    		<c:when test="${item.deal_type == '4'}">积分消费</c:when>
                    		<c:when test="${item.deal_type == '5'}">现金充值</c:when>
                    		<c:when test="${item.deal_type == '6'}">现金消费反结算</c:when>
                    		<c:when test="${item.deal_type == '7'}">现金消费积分反结算</c:when>
                    		<c:when test="${item.deal_type == '8'}">储值消费反结算</c:when>
                    		<c:when test="${item.deal_type == '9'}">储值消费积分反结算</c:when>
                    		<c:when test="${item.deal_type == '10'}">积分消费反结算</c:when>
                    		<c:when test="${item.deal_type == '11'}">银行卡充值</c:when>
                    		<c:when test="${item.deal_type == '12'}">会员注销退款</c:when>
							<c:when test="${item.deal_type == '17'}">储值赠送</c:when>                    		
                    		<c:otherwise>--</c:otherwise>
                    	</c:choose>
                    </td>
                    <td class="tdclass"><font color="red">${item.amount }</font></td>
                    <td class="tdclass" width="10%">
                    	<c:choose>
                    		<c:when test="${fn:length(item.slip_no) > 7}">${item.slip_no }</c:when>
                    		<c:when test="${fn:length(item.slip_no) < 7}">--</c:when>
                    	</c:choose>
                    	
                    </td>
                </tr>
			</c:forEach>
                
            </tbody>
        </table>
        <div class="pagingWrap">
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
	        	var starttime = $("#starttime").val();
	        	var endtime = $("#endtime").val();
	        	var falg = $("#falg").val();
	        	var timefalg = $("#timefalg").val();
                window.location.href = global_Path + '/consumption/index?current='+page+'&starttime='+starttime+'&endtime='+endtime
                		+'&falg='+falg+'&timefalg='+timefalg;
            }
        });
    } else {
        $(".pagingWrap").empty();
    }
	
	if($("#falg").val()==1){
		$("#cur").addClass("cur");
		$("#cur1").removeClass("cur");
	}else{
		$("#cur1").addClass("cur");
		$("#cur").removeClass("cur");
	}
	
	
	if($("#timefalg").val()==1){
		$("#timefalg").removeClass("select");
		$("#data1").removeClass("no_select").addClass("select");
	}else if($("#timefalg").val()==2){
		$("#timefalg").removeClass("select");
		$("#data2").removeClass("no_select").addClass("select");
	}else if($("#timefalg").val()==3){
		$("#timefalg").removeClass("select");
		$("#data3").removeClass("no_select").addClass("select");
	}else if($("#timefalg").val()==4){
		$("#timefalg").removeClass("select");
		$("#data4").removeClass("no_select").addClass("select");
	}else{
		$("#data1").removeClass("select");
		$("#data2").removeClass("select");
		$("#data3").removeClass("select");
		$("#data4").removeClass("select");
	}
	
	
	function toSearch(){
		var current = $("#current").val();
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		var falg = $("#falg").val();
		window.location.href = "../consumption/index?starttime="+starttime+"&endtime="+endtime+"&falg="+falg+"&timefalg=0";
	}
	function toExportExl(){
		var current = $("#current").val();
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		var falg = $("#falg").val();
		window.location.href = "../consumption/exportMemberDeal?starttime="+starttime+"&endtime="+endtime+"&falg="+falg+"&timefalg=0";
	}
</script>
</html>