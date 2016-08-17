<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>餐道会员管理平台</title>
	<meta name="description" content="">
	<meta name="keywords" content="">
	<link href="<%=request.getContextPath()%>/tools/bootstrap/css/bootstrap.css" rel="stylesheet"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/tools/font-awesome/css/font-awesome.css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/tools/bootstrap/js/bootstrap.min.js"></script>
	<script>
		var web_conf = {
			rootPath : '<%=request.getContextPath()%>'
		}
	</script>
</head>
<body style="overflow-y:hidden" onload="">
	<div class="ky-navbar ky-navbar-default">
		<div class="ky-navbar-header">
			<img src="<%=request.getContextPath() %>/images/logo.png" alt="">
			<p>餐道会员管理平台</p>
		</div>
		<div style="background-color: #fff ;width:100%;height:2px"></div>
		<div class="ky-navbar-menu">
			<ul class="ky-nav ky-nav-pills" id="ul_left_menu">
				<li>
					<a href="#" class="ky-menu-primary J-btn-to-member">会员信息</a>
				</li>
				<li>
					<a href="#" class="ky-menu-primary J-btn-to-consumption">交易信息</a>
				</li>
				<li>
	                <a href="#" class="ky-menu-primary J-btn-to-preferential">优惠管理</a>
	            </li>
	            <li>
	                <a href="#" class="ky-menu-primary J-btn-to-transInfo">报表统计</a>
	            </li>
			</ul>
		</div>
	</div>
	<div class="ky-container-iframe">
		<div class="ky-title">
			<p id="title_p">会员管理</p>
			<div class="ky-title-right">
				<img src="<%=request.getContextPath()%>/images/user.png"   alt="">
 				<span class="ky-user"><%=session.getAttribute("currenttenantid") %></span> 
				<img src="<%=request.getContextPath()%>/images/logout.png" class="J-btn-exit"/>
			</div>
		</div>
		<iframe onload="" id="detail" src="" width="100%" frameBorder="0" scrolling="auto" height="100%" style="overflow-x: hidden;"> 
		</iframe>
		<!-- style="overflow-x:hidden; overflow-y:auto;" -->
		<div class="modal fade dialog-sm in " id="modal_confirmExit"  data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">	
					<div class="modal-body">
					<div class="dialog-sm-header">				  
				         <span class=" " style="color:black;">确认退出</span>
				        <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" data-dismiss="modal">
				    </div>
						<form action="" method="post" class="form-horizontal " name="">
							<div class="dialog-sm-info">
							<p class="p1" style="color:black;"><img src="<%=request.getContextPath()%>/images/del-tip.png"></i>确认退出“餐道会员管理平台”吗?</p>
							</div>
							<div class="btn-operate  ">
								<button class="btn btn-cancel  " type="button" data-dismiss="modal"    >取消</button>
								<div  class="btn-division"></div>
								<button class="btn btn-save  " id="dishes-type-save" type="button" data-dismiss="modal"  onclick="window.location.href='<%=request.getContextPath()%>/index/loginOut';">确认</button>
							</div>
						</form>
					</div>
				</div>
				
			</div>
		</div>
		
	</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/member/main.js"></script>
</body>
</html>