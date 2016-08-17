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
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/commons.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/member.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/tools/bootstrap/js/bootstrap.min.js"></script>
	<link href="<%=request.getContextPath()%>/tools/jqgrid/css/themes/cupertino/jquery.ui.theme.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/tools/jqgrid/css/ui.jqgrid.css" rel="stylesheet" />
    <script src="<%=request.getContextPath()%>/tools/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="<%=request.getContextPath()%>/tools/jqgrid/js/jquery.jqGrid.min.js"></script>    
    <script src="<%=request.getContextPath()%>/scripts/jquery.twbsPagination.js"></script>
    <script>
        var web_conf = {
            rootPath : '/member'
        }
    </script>
    <style>
        /* .table-list tbody > tr > td {
            font-size:12px;
            padding: 2px 5px;
            line-height: 32px;;
        } */
        .btn {
            background-color:rgb(122, 196, 84) ;
            border:none;
        }
       
	table{font-size:12px;}
	.outline{ blr:expression_r(this.onFocus=this.blur()) }
    .outline:focus{ outline-style: none; } 　
	.uploadfy-input{
	background:#FF;color: gray;border-radius:10px;cursor:pointer;border:1px rgb(122, 196, 84) solid;line-height:32px;
	}
	img.img-close {
    top: -13px;
}
    </style>
</head>
<body>
<div class="ky-content content-iframe">
    <div class="ky-content-title">
        <span>优惠列表</span>
        <a href="../preferential/detail" class="btn btn-success  f-fr">新建优惠</a>
    </div>
	<div id="preferentialList" class="User-list" style="display:block;margin-top:30px;">
               <table id="info-list" ></table>
               <div id="gridPagerInfo"></div>
	</div>    
    <div class="pagingWrap"></div>
    <div class="modal fade dialog-sm in " id="modal_confirDel"  data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialog-sm-header">
                        <span class=" " style="color:black;">确认</span>
                        <img src="../images/close-sm.png" class="img-close" data-dismiss="modal">
                    </div>
                    <form action="" method="post" class="form-horizontal " name="">
                        <div class="dialog-sm-info">
                            <p class="p1" style="color:black;"><img src="../images/del-tip.png"></i>确认删除优惠吗?</p>
                        </div>
                        <div class="btn-operate  ">
                            <button class="btn btn-cancel  " type="button" data-dismiss="modal">取消</button>
                            <div  class="btn-division"></div>
                            <button id="rdel" rid="" class="btn btn-save"  type="button" data-dismiss="modal">确认</button>
                        </div>
                    </form>
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
					<label id="promptMsg">账号发送成功</label>
				</p>
			</div>
		</div>
	</div>
</div>

<input type="hidden" id="current" name="current" value="${current }" />
<input type="hidden" id="total" name="total" value="${total }" />
<input type="hidden" id="totalpage" name="totalpage" value="${totalpage }" />

<script src="../scripts/global.js"></script>
<script src="../scripts/member/preferential.js"></script>
<script type="text/javascript">
	var basePath = "<%=request.getContextPath()%>";
    $(function(){
    	consumptionInfo();
    });
</script>
</body>
</html>