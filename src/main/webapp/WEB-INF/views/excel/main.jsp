<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>上传</title>
</head>
<body>
<%-- 	<form method="post" action="${pageContext.request.contextPath}/excel/mainimport"
				class="form-signin" id="j_loginform" enctype="multipart/form-data">
				<br></br>
				租户id:<input  type="text"  name="tenantid"/>
				<br></br>
				门店id:<input  type="text"  name="branchid"/>
				<br></br>
				<input type="file"  name="file"/>
				<br></br>
				<input  type="submit" value="提交"/>
	</form> --%>		
	<form method="post" action="${pageContext.request.contextPath}/excel/resolve"
				class="form-signin" id="j_loginform" enctype="multipart/form-data">
				<br></br>
				起始行号:<input  type="text"  name="beginRow" id="beginRow"/>
				<br></br>
				<br></br>
				<input type="file"  name="file"/>
				<br></br>
				<input  type="submit" value="提交"/>
	</form>			

</body>	