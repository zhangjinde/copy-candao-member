<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    pageContext.setAttribute("basePath",basePath);
%>

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
    <link href="<%=request.getContextPath()%>/tools/jqgrid/css/themes/cupertino/jquery.ui.theme.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/tools/jqgrid/css/ui.jqgrid.css" rel="stylesheet" />
    <script src="<%=request.getContextPath()%>/tools/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="<%=request.getContextPath()%>/tools/jqgrid/js/jquery.jqGrid.min.js"></script>
    <script src="<%=request.getContextPath()%>/tools/uploadify/jquery.uploadify.min.js"></script>
    <link href="<%=request.getContextPath()%>/tools/uploadify/uploadify.css" rel="stylesheet" />
    <style>
        table {
            font-size: 12px;
        }

        .outline {
            blr: expression_r(this.onFocus=this.blur());
        }

            .outline:focus {
                outline-style: none;
            }

        .uploadfy-input {
            background: #FF;
            color: gray;
            border-radius: 10px;
            cursor: pointer;
            border: 1px rgb(122, 196, 84) solid;
            line-height: 32px;
        }

        .no-radius button {
            border-radius: 0px;
        }

        .colorgreen button {
            background: #8cc253;
            color: #fff;
            border: 1px solid #8cc253;
        }

        .colorgreen .btn-default:focus, .colorgreen .btn-default:active, .colorgreen .btn-default.active {
            background: #8cc253;
            border: 1px solid #8cc253;
            color: #fff;
        }
    </style>
</head>
<body>
    <div class="ky-content content-iframe">
        <input type="hidden" name="page" id="page" value="${page}">
        <input type="hidden" name="rows" id="rows" value="10">
        <input type="hidden" id="total" value="${total}">

        <ul class="VIPtab">
            <li class="cur"><a>综合报表</a></li>
            <li><a>会员消费明细</a></li>
        </ul>
        <div class="User-list" style="display:block">
            <div class="shop-search zhongheachrt">
                <div class="form-group">
                    <label class="control-label" style="width:100px; text-align: right">日期：</label>
                    <input type="text" class="form-control ztree-right-16" style="width:150px;" id="choeStartData" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'choeEndData\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                    <span style="padding:0 5px;">至</span>
                    <input type="text" class="form-control" style="width:150px;" id="choeEndData" onfocus="WdatePicker({ minDate: '#F{$dp.$D(\'choeStartData\');}',maxDate:'%y-%M-%d 23:59:59', dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                </div>
                <div class="form-group shop-search-btn no-radius" style="padding-left:5px;">
                    <button class="btn btn-default btn-outline outline " onclick="selectTime(this)" nametype="choseStor" timetype="day" type="submit">今天</button>
                </div>
                <div class="form-group shop-search-btn no-radius" style="padding-left:0px;">
                    <button class="btn btn-default btn-outline outline" onclick="selectTime(this)" nametype="choseStor" timetype="yesterday" type="submit">昨天</button>
                </div>
                <div class="form-group shop-search-btn no-radius" style="padding-left:0px;">
                    <button class="btn btn-default btn-outline outline" onclick="selectTime(this)" nametype="choseStor" timetype="month" type="submit">本月</button>
                </div>
                <div class="form-group shop-search-btn no-radius" style="padding-left:0px;">
                    <button class="btn btn-default btn-outline outline" onclick="selectTime(this)" nametype="choseStor" timetype="lastmonth" type="submit">上月</button>
                </div>
                <div style="margin-top:10px">
                    <div class="form-group shop-search-btn" style="padding-left:0px;">
                        <label class="control-label" style="width:100px; text-align: right">选择门店：</label>
                        <select class=" ztree-right-16" style="width:150px;height:30px;padding-left:5px;" id="Choose-StoreID">
                        </select>
                    </div>
                    <div class="form-group shop-search-btn" style="padding-left:20px;">
                        <button class="btn btn-default btn-outline outline" onclick="Choosesearch()" type="submit"><i class="icon-search"></i>搜索</button>
                    </div>
                    <div class="form-group shop-search-btn">
                        <form id="formchartid" method='post' style="margin:0px" action='<%=request.getContextPath()%>/report/exportReport'> 
                            <button onclick="exportchart()" class="btn btn-default btn-outline outline">导出</button>
                            <input type='hidden' id="textonecheart1" name='startTime' value="" />
                            <input type='hidden' id="textonecheart2" name='endTime' value="" />
                            <input type='hidden' id="textonecheart3" name='branchId' value="" />
                        </form>
                    </div>
                </div>
            </div>
            <div class="shop-btn btn-add">
                <div class="btn-group-title" style="background:#f6fab8;padding:5px;line-height:23px">
                    <div class="col-xs-12" >
                        <span><label class="control-label" style="width:120px; text-align: right">新增会员卡总数 ：</label><font color="red" id="cardnumber"></font>张 </span><br />
                        <span><label class="control-label" style="width:120px; text-align: right">会员总收入 ：</label><font color="red" id="TotalMoney"></font>元&nbsp;&nbsp;
                            (新会员收入 :<font color="red" id="newuserMoney"></font>元&nbsp;&nbsp;&nbsp;&nbsp; 老会员收入 :<font color="red" id="olduserMoney"></font>元)
                        </span><br />
                        <span><label class="control-label" style="width:120px; text-align: right">赠送金额 ：</label><font color="red" id="givelMoney"></font>元&nbsp;&nbsp;
                            (新会员赠送 :<font color="red" id="givenewlMoney"></font>元&nbsp;&nbsp;&nbsp;&nbsp; 老会员赠送 :<font color="red" id="giveoldlMoney"></font>元)
                        </span><br />
                        <span><label class="control-label" style="width:120px; text-align: right">详细 ：</label>&nbsp;&nbsp;
                            (现金收入 :<font color="red" id="paycash"></font>元&nbsp;&nbsp;&nbsp;&nbsp; 银联收入 :<font color="red" id="Unionpay"></font>元&nbsp;&nbsp;&nbsp;&nbsp;
                            微信收入 :<font color="red" id="weixinpay"></font>元&nbsp;&nbsp;&nbsp;&nbsp; 支付宝收入 :<font color="red" id='zhifubpay'></font>元)
                        </span>
                    </div>
                </div>
                <div class="btn-group-title" style="background:#f6fab8;padding:5px;line-height:23px;margin-top:7px;">
                    <div class="col-xs-12" >
                        <span><label class="control-label" style="width:120px; text-align: right">消费金额 ：</label><font color="red" id="TotalMoneyconsume"></font>元&nbsp;&nbsp;
                            <!-- (充值金额消费 :<font color="red" id='consume-cz'></font>元&nbsp;&nbsp;&nbsp;&nbsp; 赠送金额消费 :<font color="red" id='consume-zs'></font>元) -->
                        </span><br/>
                        <span><label class="control-label" style="width:120px; text-align: right">消费次数 ：</label><font color="red" id="consumetime"></font>次
                        </span>
                    </div>
                </div>
            </div>
            <table id="statement-list"></table>
            <div id="statementInfo"></div>
        </div>
        <div class="User-list">
            <div class="shop-search xiaofeiinfo">
                <div class="form-group">
                    <label class="control-label" style="width:100px; text-align: right">日期：</label>
                    <input type="text" class="form-control ztree-right-16" style="width:150px;" id="StartData" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'EndData\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                    <span style="padding:0 5px;">至</span>
                    <input type="text" class="form-control" style="width:150px;" id="EndData" onfocus="WdatePicker({ minDate: '#F{$dp.$D(\'StartData\');}',maxDate:'%y-%M-%d 23:59:59', dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                </div>
                <div class="form-group shop-search-btn no-radius" style="padding-left:5px;">
                    <button class="btn btn-default btn-outline outline " onclick="selectTime(this)" timetype="day" type="submit">今天</button>
                </div>
                <div class="form-group shop-search-btn no-radius" style="padding-left:0px;">
                    <button class="btn btn-default btn-outline outline" onclick="selectTime(this)" timetype="yesterday" type="submit">昨天</button>
                </div>
                <div class="form-group shop-search-btn no-radius" style="padding-left:0px;">
                    <button class="btn btn-default btn-outline outline" onclick="selectTime(this)" timetype="month" type="submit">本月</button>
                </div>
                <div class="form-group shop-search-btn no-radius" style="padding-left:0px;">
                    <button class="btn btn-default btn-outline outline" onclick="selectTime(this)" timetype="lastmonth" type="submit">上月</button>
                </div>
                <div style="margin-top:10px">
                    <div class="form-group shop-search-btn" style="padding-left:0px;">
                        <label class="control-label" style="width:100px; text-align: right">票据号：</label>
                        <input type="text" class="form-control ztree-right-16" style="width:165px;" id="OrderNumber" />
                    </div>
                    <div class="form-group shop-search-btn" style="padding-left:20px;">
                        <button class="btn btn-default btn-outline outline" onclick="iconsearch()" type="submit"><i class="icon-search"></i>搜索</button>
                    </div>
                    <div class="form-group shop-search-btn">
                        <button class="btn btn-default btn-outline outline" data-toggle='modal' data-target='#ScreenSearch'>高级查询</button>
                    </div>
                    <div class="form-group shop-search-btn">
                        <form id="formid" method='post' style="margin:0px" action='<%=request.getContextPath()%>/consumption/exportTransInfo'>
                            <button onclick="exportLoad()" class="btn btn-default btn-outline outline">导出</button>
                            <input type='hidden' id="textone" name='id' value="" />
                        </form>
                    </div>
                </div>
            </div>
            <div id="phone_erro" style="margin-top:-10px;margin-bottom:10px;color:red;padding-left:104px;"> </div>

            <table id="info-list"></table>
            <div id="gridPagerInfo"></div>
        </div>

    </div>

    <!-- 高级查询 -->
    <div class="modal fade shop-dialog in " id="ScreenSearch" data-backdrop="static">
        <div class="modal-dialog" style="width: 800px;">
            <div class="modal-content">
                <div class="modal-header addDelicon">
                    <div class="modal-header tenantInfo">高级查询 </div>
                    <img src="<%=request.getContextPath()%>/images/close.png" class="img-close" data-dismiss="modal">
                </div>
                <div class="modal-body">
                    <div class="dligo-margin dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">会员姓名：</div>
                        <span style="padding-left:16px"><input id="username" style="width:215px" type="text" class="form-control "></span>

                    </div>
                    <div class="dligo-margin dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">会员卡号：</div>
                        <span style="padding-left:16px"><input id="usercardName" style="width:215px" type="text" maxlength="30" class="form-control "></span>
                    </div>
                    <div class="dligo-margin clear dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">手机号码：</div>
                        <span style="padding-left:16px"><input id="phone" style="width:215px" maxlength="11" type="text" class="form-control "></span>
                    </div>
                    <div class="dligo-margin dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">门店：</div>
                        <select class=" ztree-right-16" id="storeNubner"></select>
                    </div>
                    <div class="dligo-margin clear" style="padding-top:10px;">
                        <div class="dligo-margin-left  linheight-30">会员卡名称：</div>
                        <select class=" ztree-right-16" id="cardname"></select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default outline" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-default outline" onclick="Reset()">重置</button>
                    <button type="button" class="btn btn-primary outline" onclick="heightsearch()">确定</button>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="application/javascript">
    var basePath = "${pageScope.basePath}";
</script>
<script src="${pageScope.basePath}/scripts/member/consumptionInfo.js"></script>
<script type="text/javascript">
    $(function () {
        dataTime();
        Searchtext();
        collectText();
        consumptionInfo();
        statementInfo();
    });


    $(window).resize(function () {   //窗口大小改变JQgrid的窗体大小
        $("#info-list").setGridWidth($(".User-list").width());
    });



</script>
</html>