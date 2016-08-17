<html>
<head>
	<%-- <script type="text/javascript">
		var global_ctxPath = '<%=request.getContextPath()%>'; 
		window.location.href = global_ctxPath + "/index";
	</script> --%>
</head>
<body>
<h2>Hello World!</h2>
<input type="button" value="导出数据" onclick="download()"/>
</body>
<script type="text/javascript">
function download(){
    var url="http://localhost:8080/member/memberbase/meminfoexcel/exportExcel";
    window.open(url);
}
	</script>
</html>
