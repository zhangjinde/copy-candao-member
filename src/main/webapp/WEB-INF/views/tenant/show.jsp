<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<%=request.getContextPath()%>/css/easyui/metro/easyui.css" rel="stylesheet"></link>
<link href="<%=request.getContextPath()%>/css/easyui/icon.css" rel="stylesheet"></link>
<link href="<%=request.getContextPath()%>/css/common/common.css" rel="stylesheet"></link>
<script src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/scripts/jquery.json-2.3.js"></script>
<script src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>

<script type="text/javascript">
	$(function() {
		$('#tables').datagrid({
			url : global_Path + '/tenant/page.json',
			method : 'post',
			fit : false,
			title : '所有租户',
			fitColumns : true,
			singleSelect : true,
			pagination : true,
			rownumbers : true,
			pageSize : 10,
			pageList : [ 10, 20, 30 ],
			remoteSort : false,
				idField : 'tenantId',
			loadMsg : '数据装载中......',
			columns : [ [ {
				field : 'tenantId',
				title : '租户代码',
				width : 50,
				align : 'left'
			}, {
				field : 'tenantName',
				title : '名称',
				width : 50,
				align : 'center'
			},{
				field : 'branchId',
				title : '分店代码',
				width : 50,
				align : 'center'
			},{
				field : 'tenantTel',
				title : '联系电话',
				width : 50,
				align : 'center'
			},{
				field : 'address',
				title : '地址',
				width : 50,
				align : 'center'
			},{
				field : 'bizLicence',
				title : '营业执照',
				width : 50,
				align : 'center'
			},{
				field : 'channelTypeDesc',
				title : '渠道类型',
				width : 50,
				align : 'center'
			},{
				field : 'tenantStatus',
				title : '状态',
				width : 50,
				align : 'center',
				formatter : function(value, row, index) {
					if(value==1){
						return "启用";
					}else{
						return "禁用";
					}
				}
			},{
				field : 'id',
				title : '操作',
				width : 40,
				align : 'center',
				formatter : function(value, row, index) {
					var modify ="<a href=\"###\" onclick=\"modify_tenant('" + value + "')\">查看/修改</a>";
					var del = "&nbsp;&nbsp;<a href=\"###\" onclick=\"del_tenant('" + value + "')\">删除</a>";
				    return modify+del;
				}
			}
			] ],
			toolbar : '#toolbar_t'
		});
		//分页参数配置
		$('#tables').datagrid('getPager').pagination({
			displayMsg : '当前显示从{from}到{to}共{total}记录',
			beforePageText : '第',
			afterPageText : '页 共 {pages} 页',
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});

		$('#addTenantDialog').dialog({
			title : '增加用户 (*号部分为必填项)',
			width : 500,
			height : 300,
			closed : true,
			cache : false,
			inline : true,
			modal : true,
			buttons : [ {
				text : '保存',
				handler : function() {
					$('#tenantid').validatebox({required: true,missingMessage:"租户ID不能为空！"});
					$('#tenantname').validatebox({required: true,missingMessage:"门店名不能为空！"});
					$('#branchid').validatebox({required: true,missingMessage:"分店ID不能为空！"});
					$("#tenantid").validatebox('enableValidation');
					$("#tenantname").validatebox('enableValidation');
					$("#branchid").validatebox('enableValidation');
					if($('#tenantid').validatebox('isValid') && $('#tenantname').validatebox('isValid')&& $('#branchid').validatebox('isValid')){
						save_tenant();
					}
				}
			}, {
				text : '关闭',
				handler : function() {
					$('#addTenantDialog').dialog('close');
				}
			} ],
			onClose : function() {
				$("#tenantid").val("");
				$("#tenantname").val("");
				$("#branchid").val("");
				$("#tenanttel").val("");
				$("#address").val("");
				$("#bizlicence").val("");
				$("#rd1").prop("checked",true);
				$("#rd2").prop("checked",false);
				$("#channeltype").combobox("clear");
				$("#tenantid").validatebox('disableValidation');
				$("#tenantname").validatebox('disableValidation');
			}
		});

		$('#channeltype').combobox({
			url:global_Path + '/tenant/getChannelType.json',
			valueField:'code_id',
			textField:'code_desc',
			panelHeight : 'auto',
			loadFilter : function(row){
			return row;
			}
		});
	});

	function add_tenant() {
		$('#addTenantDialog').dialog('open');
	}

	function save_tenant(){
		var check=check_validate();
		if(check){
			var json=getTenantJson();
			$.ajax({
				type : "post",
				async : false,
				url : global_Path+"/tenant/save",
				dataType : "json",
				data : json,
				contentType:'application/json;charset=UTF-8',
				success : function(result) {
					$.messager.alert("提示",result.msg);
					$('#addTenantDialog').dialog('refresh');
					$('#addTenantDialog').dialog('close');
					$('#tables').datagrid('reload');
				}
			});
		}
	}

	function check_validate(){
		var tenantid=$("#tenantid").val();
		if($.trim(tenantid)==""){
			$.messager.alert("提示","请填写租户ID");
			return false;
		}
		var tenantname=$("#tenantname").val();
		if($.trim(tenantname)==""){
			$.messager.alert("提示","请填写门店名");
			return false;
		}
		var branchid=$("#branchid").val();
		if($.trim(branchid)==""){
			$.messager.alert("提示","请填写分店ID");
			return false;
		}
		var channeltype=$("#channeltype").combobox('getValue');
		if($.trim(channeltype)==""){
			$.messager.alert("提示","请选择渠道类型");
			return false;
		}
		return true;
	}

	function getTenantJson(){
		var id=$("#id").val();
		var tenantId=$("#tenantid").val();
		var tenantName=$("#tenantname").val();
		var branchId=$("#branchid").val();
		var tenantTel=$("#tenanttel").val();
		var address=$("#address").val();
		var bizLicence=$("#bizlicence").val();
		var channelType=$("#channeltype").combobox('getValue');
		var tenantStatus=parseInt($("input[name='tenantstatus']:checked").val());
		var tenantObject={};
		tenantObject.id=id;
		tenantObject.tenantId=tenantId;
		tenantObject.branchId=branchId;
		tenantObject.tenantName=tenantName;
		tenantObject.tenantTel=tenantTel;
		tenantObject.address=address;
		tenantObject.bizLicence=bizLicence;
		tenantObject.channelType=channelType;
		tenantObject.tenantStatus=tenantStatus;
		return $.toJSON(tenantObject);
	}


	function modify_tenant(id) {
		$.ajax({
			type : "post",
			async : false,
			url : global_Path + "/tenant/findById/" + id + ".json",
			dataType : "json",
			success : function(result) {
				if(result == null){
					$.messager.alert("提示","没找到租户信息");
					return;
				}
				$("#id").val(result.id);
				$("#tenantid").val(result.tenantId);
				$("#tenantname").val(result.tenantName);
				$("#branchid").val(result.branchId);
				$("#tenanttel").val(result.tenantTel);
				$("#address").val(result.address);
				$("#bizlicence").val(result.bizLicence);
				$('#channeltype').combobox('select', result.channelType);
				if (result.tenantStatus == 1) {
					$("#rd1").attr("checked",true);
				} else {
					$("#rd1").removeAttr("checked");
					$("#rd2").prop("checked", true);
				}
				$('#addTenantDialog').dialog('open');
			}
		});
	}

	function del_tenant(id) {
		$.messager.confirm("租户管理","确认删除此数据吗？",function(r){
			if (r){
				$.ajax({
					type : "post",
					async : false,
					url : global_Path+"/tenant/delete/"+id+".json",
					dataType : "json",
					success : function(result) {
						$('#tables').datagrid('reload');
						$.messager.alert('租户管理',result,'info',null);
					}
				});
			}
		});
	}

	function logout(){
		if(confirm("确认退出？"))  {
			window.location.href='<%=request.getContextPath()%>/index/tenantLoginOut';
		}else{
			return;
		}
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">

	<div data-options="region:'center',border:false" style="padding: 10px 0px 0px 0px;">
		<a onclick="logout();" style='text-align:right;display:block;padding-right:20px;'>退出</a>
		<table id="tables"></table>
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width: 100%">
				<tr>
					<td align="left" width="60"><a href="###"
						class="easyui-linkbutton" data-options="iconCls:'icon-add'"
						onclick="add_tenant()">创建租户</a></td>
					<td align="right" width="160">关键字搜索：</td>
					<td width="170"><input id="tenantnameSearch"
						class="easyui-searchbox" style="width: 220px;"
						data-options="searcher:tenantnameSearch" prompt="请输入门店名称" /></td>
				</tr>
			</table>
		</div>

		<div id="addTenantDialog">
		    <input type="hidden" id="id" name="id" />
			<table cellspacing="0" cellpadding="0" border="0" style="width: 100%">
				<tr align="left">
					<td>租户ID:</td>
					<td><input type="text" id="tenantid" name="tenantid" /><span
						style="color: red">*</span></td>
				</tr>
				<tr align="left">
					<td>门店名称:</td>
					<td><input type="text" id="tenantname" name="tenantname" /><span
						style="color: red">*</span></td>
				</tr>
				<tr align="left">
					<td>分店ID:</td>
					<td><input type="text" id="branchid" name="branchid" /><span
						style="color: red">*</span></td>
				</tr>
				<tr align="left">
					<td>联系电话:</td>
					<td><input type="text" id="tenanttel" name="tenanttel" /></td>
				</tr>
				<tr align="left">
					<td>地址:</td>
					<td><input type="text" id="address" name="address" /></td>
				</tr>
				<tr align="left">
					<td>营业执照:</td>
					<td><input type="text" id="bizlicence" name="bizlicence" /></td>
				</tr>
				<tr align="left">
					<td>渠道类型:</td>
					<td><input id="channeltype" name="channeltype"
						style="height: 30px" /></td>
				</tr>
				<tr align="left">
					<td>状态:</td>
					<td><input type="radio" id="rd1" name="tenantstatus" value="1"
						checked />启用 <input type="radio" id="rd2" name="tenantstatus"
						value="0" />禁用</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
