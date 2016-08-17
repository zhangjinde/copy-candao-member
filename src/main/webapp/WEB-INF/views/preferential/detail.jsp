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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/member.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/tools/font-awesome/css/font-awesome.css"/>
    <link href="<%=request.getContextPath()%>/tools/calendar_diy/skin/WdatePicker.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/tools/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/tools/calendar_diy/WdatePicker.js"></script>
    <script>
        var web_conf = {
            rootPath : '/member'
        }
    </script>
    <style>
        .btn-save {
            background-color:rgb(122, 196, 84) ;
            border:none;
        }
    </style>
</head>
<body>

<div class="ky-content content-iframe  f-fs14">
    <div class="ky-content-title">
        <span>优惠类型</span>
    </div>
    <hr>
    <form id="preferentialForm" method="get" action="">

        <input type="hidden" id="pId" name="pId" value="${detail.id}">
        <div class="ui-form">
            <div class="item">
                <label class=label"><span class="required-span">*</span>选择优惠</label>
                <div class="info">
                    <select class="select J-type-select" name="type" id="type" min="1">
                        <option value="0" <c:if test="${detail.id == null}"> selected </c:if>>请选择优惠类型</option>
                        <option value="1" <c:if test="${detail.id != null}"> selected </c:if>>充值赠送</option>
                    </select>
                </div>
            </div>
        </div>

	


        <div id="preferential-type-box" style="<c:if test="${detail.id == null}"> display:none </c:if>" >
        
            <div class="ky-content-title">
                <span>优惠信息</span>
            </div>
            <hr>
            <div class="ui-form">
                <div class="item">
                    <label class=label"><span class="required-span">*</span>优惠名称</label>
                    <div class="info">
                        <input type="text" class="ipt" placeholder="" maxlength="100" name="name" id="name" value="${detail.name }">
                    </div>
                </div>
                <div class="item">
                    <label class=label"><span class="required-span">*</span>优惠规则</label>
                    <div class="info">
                        <p class="inner-type">
                           	 充值
                        </p>
                        <input type="text" class="ipt" placeholder="" name="dealValue" maxlength="7" id="dealValue" value="${detail.dealValue }"> <span class="unit">元</span>
                        (为空表示不限制充值金额)

                        <p class="inner-type">
                        	  赠送
                        </p>
                        <input type="text" class="ipt" placeholder="" name="presentValue" maxlength="7"  id="presentValue" value="${detail.presentValue}">  <span class="unit">元</span>

                    </div>
                </div>
                <div class="item">
                    <label class=label"><span class="required-span">*</span>适用门店</label>
                    <div class="info" id="store-select">
                        <button type="button" class="btn btn-default store-select-add" id="addstore" data-container="body"  data-placement="bottom" ><i class="icon-plus"></i><span>选择门店</span> </button>
                        <input id="selectBranchs" name="selectBranchs" type="text" style="opacity: 0;position: absolute;left:-1000px;" value="">
                    </div>
                </div>
                <div class="item">
                    <label class=label"><span class="required-span">*</span>启用状态</label>
                    <div class="info">
                    <span>
                        <input type="radio" id="status" name="status" class="mr5" value="0" <c:if test="${detail.status == 0}"> checked </c:if> >启用
                    </span>
                    <span class="ml20">
                        <input type="radio" id="status" name="status" class="mr5" value="1" <c:if test="${detail.status == 1}"> checked </c:if> >禁用
                    </span>
                    </div>
                </div>
                <div class="item">
                    <label class=label"><span class="required-span">*</span>微信会员充值优惠</label>
                    <div class="info">
                        <select class="select" name="weixinOpen" id="weixinOpen" >
                            <option value="0" <c:if test="${detail.weixinStatus == 0}"> selected </c:if>>不启用</option>
                            <option value="1" <c:if test="${detail.weixinStatus == 1}"> selected </c:if>>启用</option>
                            <%--<option value="0">不启用</option>--%>
                            <%--<option value="1">启用</option>--%>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="btn-operate f-fl">
            <button class="btn btn-lg btn-cancel in-btn135"  onclick="window.location.href='../preferential/index';return false;">取消</button>
            <div class="btn-division"></div>
            <button class="btn btn-lg btn-save in-btn135 J-btn-submit" type="submit">保存</button>
        </div>
        <div class="f-cb"></div>

    </form>

</div>
<div class="modal fade storeSelect-dialog in" id="store-select-dialog" aria-hidden="false" >
    <div class="modal-dialog modal-lg" style="width: 900px;">
        <div class="modal-content">
            <div class="modal-header addDelicon">
                <img data-dismiss="modal" class="img-close" src="../images/close.png">
            </div>
            <div class="modal-body">
                <div class="row store-select-title">
                    <div class="col-xs-9">选择门店<font id="store-count"></font></div>
                    <div class="col-xs-3 pull-right">
                        <label class="radio-inline">
                            <input type="radio" value="1"  name="checkAll">全选
                        </label>
                        <label class="radio-inline">
                            <input type="radio" value="0" name="checkall">全不选
                        </label>
                    </div>
                </div>
                <hr>
                <table class="table store-select-content"><tbody><tr> <td>暂无数据</td></tr><tr></tr></tbody></table>
                <div class="btn-operate">
                    <button class="btn btn-cancel in-btn135" id="store-select-cancel" data-dismiss="modal">取消</button>
                    <div class="btn-division"></div>
                    <button class="btn btn-save in-btn135 preferential-btn-bgcolor" id="store-select-confirm">确认</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 发送提示 -->
<div class="modal fade sendMsgState in " id="successPrompt"  style="z-index:99990" >
	<div class="modal-dialog" style="width:310px;">
		<div class="modal-content" >	
			<div class="modal-body pop-div-content">
				<p><i class="icon-success"></i></p>
				<p class="tipP"> 
					<label id="promptMsg"></label>
				</p>
			</div>
		</div>
	</div>
</div>

<script src="../scripts/global.js"></script>
<script src="../scripts/member/preferential.js"></script>
<script src="../tools/jquery-validation/jquery.validate.js"></script>
<script>
    $(function(){

        var pId = $("#pId").val();

        Preferential.getAllBranch();
        Preferential.getSelectedBranchById(pId);

        $.extend($.validator.messages, {
            required: "这是必填字段",
            remote: "请修正此字段",
            email: "请输入有效的电子邮件地址",
            url: "请输入有效的网址",
            date: "请输入有效的日期",
            dateISO: "请输入有效的日期 (YYYY-MM-DD)",
            number: "请输入有效的数字",
            digits: "只能输入数字",
            creditcard: "请输入有效的信用卡号码",
            equalTo: "你的输入不相同",
            extension: "请输入有效的后缀",
            maxlength: $.validator.format("最多可以输入 {0} 个字符"),
            minlength: $.validator.format("最少要输入 {0} 个字符"),
            rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串"),
            range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
            max: $.validator.format("请输入不大于 {0} 的数值"),
            min: $.validator.format("请输入不小于 {0} 的数值")
        });

        $("#preferentialForm").validate({
            submitHandler: function(form){
            	var status = $("input:radio[name='status']:checked").val();
                console.info('success');
                var selectBranchs = [];

                $.each($('#selectBranchs').val().split(','),function(i,v){
                    var ret = v.split('|');
                    selectBranchs.push({"preferentialId": pId,"branchId": ret[0],"branchName": ret[1]});
                })

                $.ajax({
                	type : "POST",
                    async : false,
                    url:interfaceUrl.updatePreferential,traditional:true,
            		dataType : "json",
            		contentType : "application/json; charset=utf-8",
                    data : JSON.stringify({
                        "id": $("#pId").val(),
                        "name": $.trim($('#name').val()),
                        "status": status,
                        "weixinStatus":$("#weixinOpen").val(),
                        "type": $('#type').val(),
                        "dealValue": $.trim($('#dealValue').val()),
                        "presentValue": $.trim($('#presentValue').val()),
                        "typeName": $.trim($('#typeName').val()),
                        "rule": 1,
                        "preferentialInfos":selectBranchs
                    }),
                    success : function(result) {
                        console.dir(result);
                        if(result.code === '0') {
                            console.info('succ');
                            window.location.href="./index";
                        } else {
                            sendMsg(false,result.msg);
                        }
                    }});
            },
            rules : {
                type : {
                    required : true
                },
                selectBranchs : {
                    required : true
                },
                name : {
                    required : true,
                    maxlength : 100
                },
                dealValue:{
                    number:true,
                    maxlength : 7,
                    min : 0,
                    digits : true
                },
                presentValue:{
                    required : true,
                    number:true,
                    maxlength : 7,
                    min : 1,
                    digits : true
                },
                status : {
                    required : true,
                    number:true
                }
            },
            messages : {
                type : {
                    min : '请选择优惠类型'
                },
                selectBranchs : {
                    required : '适用门店不能为空'
                },
                name : {
                    required : '优惠名称不能为空',
                    max:'优惠名称最多100个字符'
                },
                dealValue:{
                    number:'充值金额必须为纯数字',
                    digits: '充值金额必须为纯数字',
                    max : "只能输入7位纯数字"
                },
                presentValue:{
                    required : '不能为空',
                    number:'赠送金额必须为纯数字',
                    digits: '赠送金额必须为纯数字',
                    max : "只能输入7位纯数字"
                },
                status : {
                    required :'请选择状态'
                }
            }
        });

    })
</script>
</body>
</html>