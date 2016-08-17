<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>会员总览</title>
<meta name="description" content="">
<meta name="keywords" content="">
<%@ include file="/common/resource.jsp" %> 
</head>
<body>

	<div class="ky-content content-iframe">
		<div class="shop-btn btn-add">
			<div class="btn-group-title" role="group" aria-label="...">
				<a>会员总览</a>
			</div>
		</div>
	</div>

	<div class="container">
		<div style="margin-top: 20px"></div>
		<div class="page-header">
			<h1>平台会员总览：</h1>
		</div>
		<div class="row">
			<div class="col-sm-6">
				会员总数： ${tenantMap.memberTotal} 人
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				会员储值总余额： ${tenantMap.valueTotal} 元
			</div>
			<div class="col-sm-6">
				会员积分总余额： ${tenantMap.pointTotal} 元
			</div>
		</div>
		<div class="page-header">
			<h1>商家会员情况：</h1>
		</div>

		<select id="tenant" class="easyui-combobox" name="dept"
			style="width: 200px;">
			<option value="12345">新辣道</option>
			<option value="67890">鸟巢</option>
		</select>

		<div class="row">
			<div class="col-sm-6">
				会员总数：${tenantMap.memberTenant} 人 
			</div>
			<div class="col-sm-6">
				剩余会员卡数量： ${tenantMap.cardTenant} 张
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				会员储值总余额： ${tenantMap.valueTenant} 元
			</div>
			<div class="col-sm-6">
				会员积分总余额：${tenantMap.pointTenant} 元 
			</div>
		</div>
	</div>

</body>
</html>