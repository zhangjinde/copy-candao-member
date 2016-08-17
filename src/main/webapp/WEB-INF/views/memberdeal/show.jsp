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

        img.img-close {
            top: -13px;
        }
        .classOne{
        color:gray;
        text-align:right;
        width:65px;
        height:25px;
        }
        .classOne_b{
        color:#000;
        width:50%;
        height:30px;
        }
        .classOne_b_text{
        padding-left:24px;
        }
        .btn-primary {
    color: #ffffff;
    background-color: #8cc253;
    border-color: #8cc253;
}
.btn-primary:hover, .btn-primary:focus, .btn-primary:active, .btn-primary.active {
    color: #ffffff;
    background-color: #78ba32;
    border-color: #78ba32;
}
#menber_table{  
    table-layout: fixed;  
}  
  
 #menber_table tr td{
 text-overflow: ellipsis; /* for IE */  
    -moz-text-overflow: ellipsis; /* for Firefox,mozilla */  
    overflow: hidden;  
    white-space: nowrap;  
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
                    <span>
                        会员储值总额 :<font color="red" id="TotalMoney"></font>元&nbsp;&nbsp;
                        (现金 :<font color="red">${countmap.cashCount}</font>元&nbsp;&nbsp;&nbsp;&nbsp;
                        银行卡 :<font color="red">${countmap.bankCount}</font>元&nbsp;&nbsp;&nbsp;&nbsp;
                        储值赠送:<font color="red">${countmap.presentCount}</font>元&nbsp;&nbsp;&nbsp;&nbsp;
                        会员注销退款 :<font color="red">${countmap.refundCount}</font>元)
                    </span>
                    <%-- 所有会员积分:${allPoint} --%>
                </div>
            </div>
        </div>

        <input type="hidden" name="tenantId" id="tenantId" value="${tenantId}">
        <input type="hidden" name="current" id="current" value="${current}">
        <input type="hidden" name="pagesize" id="pagesize" value="10">
        <input type="hidden" id="total" value="${total}">
        <ul class="VIPtab">
            <li class="cur" id="cur1"><a>会员基础信息</a></li>
            <li id="cur2"><a>会员资金信息</a></li>
            <li id="cur3"><a>会员导入</a></li>
            <li id="cur3"><a>会员新增</a></li>
        </ul>
        <div class="shop-search" id="VipOneTwo">
            <div class="form-group">
                <label class="control-label">会员卡号/手机号码：</label>
                <input style="width:150px" id="phone" type="text" class="form-control ng-pristine ng-valid" maxlength="30" size="12">
            </div>
            <div class="form-group shop-search-btn">
                <button class="btn btn-default btn-outline outline" onclick="iconsearch()" type="submit"><i class="icon-search"></i>搜索</button>
            </div>
            <div class="form-group shop-search-btn">
                <button class="btn btn-default btn-outline outline" data-toggle='modal' data-target='#ScreenSearch'>高级查询</button>
            </div>
            <div class="form-group shop-search-btn">
                <form id="formid" method='post' style="margin:0px" action='<%=request.getContextPath()%>/memberbase/meminfoexcel/exportExcel'>
                    <button onclick="exportLoad()" class="btn btn-default btn-outline outline">导出</button>
                    <input type='hidden' id="textone" name='id' value="" />
                </form>
            </div>

        </div>
        <div id="phone_erro" style="margin-top:-10px;margin-bottom:10px;color:red;padding-left:104px;"> </div>
        <div class="User-list" style="display:block">
            <table id="User-list"></table>
            <div id="gridPagerBaseInfo"></div>
        </div>
        <div class="User-list">
            <table id="User-consumption"></table>
            <div id="gridPager"></div>
        </div>
        <div class="User-list">
            <div class="doc-content jui-doc" id="J_doc" style="padding-top:10px">
                <h2>会员数据导入说明</h2>
                <p>
                    1、导入数据时请仔细填写表格中的每一项数据，并严格按照指定的格式录入。
                </p>
                <p>
                    2、一次性导入的数据量不易过大，对于大数据量建议分批次导入。
                </p>
                <p>
                    3、实体卡：门店发放到用户手中的会员卡；虚拟卡：用户在注册时，自动生成的卡号，当用户绑定实体卡时，将替换虚拟卡号。
                </p>
                <p style="color:red">
                    4、导入的会员卡号相同时，会覆盖之前的内容，请谨慎操作。
                </p>
                <h2>会员模板导入方法</h2>
                <p>
                    1、点击下载<a href="<%=request.getContextPath()%>/down/detailTemplate" style="color:rgb(122, 196, 84);">《会员导入模板》</a>
                </p>
                <p>
                    2、打开模板文件，根据模板内容添加数据，如下图所示。<span id="cheack_img" style="color:rgb(122, 196, 84);cursor: pointer;">查看示例</span><br />
                    <img id="example_img" src="<%=request.getContextPath()%>/images/shilie.png" style="padding:5px 30px;padding-right:0px;margin-bottom:5px;display:none;">
                </p>
                <p>
                   <div class="dligo-margin ">
                        <div class="dligo-margin-left  linheight-30" style="width:139px;">3、导入会员卡类型：</div>
                        <select class=" ztree-right-16" style="width:80px;" id="daoru_cardtype" >
                            <option value="1">实体卡</option>
                            <option value="0">虚拟卡</option>
                        </select>
                    </div>
                </p>
                <p>
                    <div class="shop-search" style="font-size:12px;text-indent: 12px;padding:0px">
                        <div class="form-group shop-search-btn">
                            4、上传填写完成的《会员导入模板》文件。
                            <input id="uploadfy" type="file" name="file" />
                        </div>
                    </div>
                </p>
                
                <p>
                    <span style="background:rgb(122, 196, 84);color:#fff;padding:5px 10px;border-radius:10px;cursor:pointer "
                          onclick="avc()">上传导入数据</span>


                </p>

            </div>
            <div id="J_doc_erro" class="doc-content jui-doc" style="padding:10px;display:none;margin-bottom:30px;">
                <p>

                    <div class="shop-search" style="font-size:12px;text-indent: 12px;padding:0px">
                        <div class="form-group shop-search-btn">
                            导入会员信息完毕：<font color="">成功导入<span id="successNum"></span>条 </font>，<font color="red">信息错误<span id="errorNum"></span>条</font>，以下为错误信息列表。
                            <span style="background:rgb(122, 196, 84);color:#fff;padding:5px 10px;border-radius:10px;cursor:pointer;margin-left:30px; "
                                  onclick="editInfo()">保存修改</span>
                        </div>
                    </div>
                </p>
                <div id="daoru_Erro_list">
                    <table id="User-consumptionIntoErroList"></table>
                    <div id="gridPager-ErrorList"></div>
                </div>
            </div>
        </div>

        <!-- 新增会员 -->
        <div class="User-list">
            <div id="newAddMember"  style="padding-top:10px">
                <div class="shop-search" >
                    <span style="background:rgb(122, 196, 84);color:#fff;padding:5px 10px;border-radius:10px;cursor:pointer;"
                           onclick="bindMemberCard()">绑定会员实体卡</span>
                </div>
                <div class="doc-content jui-doc" style="padding:1px 0px;">
                  <h2>新增会员信息</h2>
                 </div>
                <div class="dligo-margin " style="padding-top:10px;">
                    <div class="dligo-margin-left  linheight-30"><font color="red" style="padding-right:5px">*</font>手机号码：</div>
                    <span style="padding-left:16px"><input id="mobile" style="width:215px" type="text" maxlength="11" class="form-control "></span>

                </div>
                <div class="dligo-margin " style="padding-top:10px;">
                    <div class="dligo-margin-left  linheight-30">会员姓名：</div>
                    <span style="padding-left:16px"><input id="membername" style="width:215px" maxlength="30"  type="text" class="form-control "></span>

                </div>
                <div class="dligo-margin " style="padding-top:10px;">
                    <div class="dligo-margin-left  linheight-30">性别：</div>
                    <select class=" ztree-right-16" style="width:215px;" id="gender">
                        <option value="0">男</option>
                        <option value="1">女</option>
                    </select>
                </div>
                <div class="dligo-margin " style="padding-top:10px">
                    <div class="dligo-margin-left  linheight-30">地址：</div>
                    <span style="padding-left:16px"><input id="address" style="width:215px" type="text" class="form-control" maxlength="50"></span>

                </div>
                <div class="dligo-margin" style="padding-top:10px;">
                    <div class="dligo-margin-left  linheight-30">生日：</div>
                    <input type="text" class="form-control ztree-right-16" style="width:215px;" id="birthday" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'birthdayEnd\');}',minDate:'#F{$dp.$D(\'birthdayEnd\',{y:-1}\\);}'})" />
                </div>
                <div class="dligo-margin" style="padding-top:10px;">
                    <div class="dligo-margin-left  linheight-30">开卡员工：</div>
                    <input type="text" class="form-control ztree-right-16" style="width:215px;" id="createuser" maxlength="30" />
                </div>
                <div class="dligo-margin " style="padding-top:10px; padding-bottom:20px;">
                    <div class="dligo-margin-left  linheight-30"><font color="red" style="padding-right:5px">*</font>所属门店：</div>
                    <select class=" ztree-right-16" id="store"></select>
                </div>
                <button type="button" class="btn btn-default outline" onclick="newReset()" style='margin-left:100px;'>重置</button>
                <button type="button" class="btn  outline" id="memberAdd"style='margin-left:10px;background: rgb(122, 196, 84); color:#fff' disabled='disabled'  onclick="addMemberInfo()">提交</button>

            </div>
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
                <div style='padding:15px;' id="context">
                    <table id="dealDetail-List"></table>
                    <div id="dealDetail-gridPager"></div>
                </div>
            </div>
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
                        <div class="dligo-margin-left  linheight-30">会员卡名称：</div>
                        <select class=" ztree-right-16" id="cardname"></select>
                    </div>
                    <div class="dligo-margin clear dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">会员卡状态：</div>
                        <select class=" ztree-right-16" id="cardstat"></select>
                    </div>
                    <div class="dligo-margin dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">当前积分：</div>
                        <select class=" ztree-right-16" style="width:80px;" id="pointflag">
                            <option value="0">全部</option>
                            <option value="2">小于</option>
                            <option value="1">大于</option>
                            <option value="3">等于</option>
                            <option value="5">小于等于</option>
                            <option value="4">大于等于</option>
                        </select>
                        <span style="padding-left:16px"><input id="point" disabled="disabled" style="width:120px" type="text" class="form-control "></span>
                    </div>
                    <div class="dligo-margin dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">余额：</div>
                        <select class=" ztree-right-16" style="width:80px;" id="balanceflag">
                            <option value="0">全部</option>
                            <option value="2">小于</option>
                            <option value="1">大于</option>
                            <option value="3">等于</option>
                            <option value="5">小于等于</option>
                            <option value="4">大于等于</option>
                        </select>
                        <span style="padding-left:16px"><input id="balance" disabled="disabled" style="width:120px" type="text" class="form-control "></span>
                    </div>
                    <div class="dligo-margin dligo-margin-float" style="padding-top:6px;">
                        <div class="dligo-margin-left linheight-30">累计充值：</div>
                        <select class=" ztree-right-16" style="width:80px;" id="totalbalanceflag">
                            <option value="0">全部</option>
                            <option value="2">小于</option>
                            <option value="1">大于</option>
                            <option value="3">等于</option>
                            <option value="5">小于等于</option>
                            <option value="4">大于等于</option>
                        </select>
                        <span style="padding-left:16px"><input id="totalbalance" disabled="disabled" style="width:120px" type="text" class="form-control "></span>
                    </div>
                    <div class="dligo-margin clear" style="padding-top:6px;">
                        <div class="dligo-margin-left  linheight-30">生日：</div>
                        <input type="text" class="form-control ztree-right-16" style="width:90px;" id="birthdayStart" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'birthdayEnd\');}',minDate:'#F{$dp.$D(\'birthdayEnd\',{y:-1}\\);}'})" />
                        <span style="padding:0 10px;">至</span>
                        <input type="text" class="form-control" style="width:90px;" id="birthdayEnd" onfocus="WdatePicker({ minDate: '#F{$dp.$D(\'birthdayStart\');}', maxDate: '#F{$dp.$D(\'birthdayStart\',{y:1});}' })" />
                    </div>
                </div>
                <div class="modal-footer ">
                    <button type="button" class="btn btn-default outline" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-default outline" onclick="Reset()">重置</button>
                    <button type="button" class="btn btn-primary outline" onclick="heightsearch()">确定</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 导入成功提示 -->
    <div class="modal fade dialog-sm in " id="modal_success" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialog-sm-header">
                        <span class=" " style="color:#000;">导入成功</span>
                        <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" data-dismiss="modal">
                    </div>
                    <div class="dialog-sm-info">
                        <p class="p1" id="success_alert_MSg" style="color:rgb(122, 196, 84)">
                            <i class="glyphicon glyphicon-ok-circle"></i><span style="padding-left:5px"></span>
                        </p>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <!-- 导入错误提示 -->
    <div class="modal fade dialog-sm in " id="modal_Error" data-backdrop="static" style="z-index:9999">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" data-dismiss="modal">

                    <form action="" method="post" class="form-horizontal " name="">
                        <div class="dialog-sm-info">
                            <p class="p1" style="color:black;"><img src="<%=request.getContextPath()%>/images/del-tip.png"></i><span>请选择，您要上传的文件?</span></p>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
    <!-- 导入loading -->
    <div class="modal fade dialog-sm in " id="modal_Loading" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <img src="<%=request.getContextPath()%>/images/loading.gif" width="95%" style="margin:0 auto">
                </div>
            </div>

        </div>
    </div>
    <!-- 挂失 -->
    <div class="modal fade dialog-sm in " id="modal_confirm_cardlose" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialog-sm-header">
                        <span class=" " style="color:black;">挂失</span>
                        <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" data-dismiss="modal">
                    </div>
                    <form action="" method="post" class="form-horizontal " name="">
                        <div class="dialog-sm-info">
                            <p class="p1" id="cardlose_confirm_info" style="color:black;"></p>
                            <table id="guashi_member_algin_list">
                               
                            </table>
                        </div>
                        <div class="btn-operate">
                            <button class="btn btn-cancel" type="button" data-dismiss="modal">取消</button>
                            <div class="btn-division"></div>
                            <button class="btn btn-save" id="cardlose_ok" type="button" data-dismiss="modal">确认</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
    <!-- 解除挂失 -->
    <div class="modal fade dialog-sm in " id="modal_confirm_uncardlose" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialog-sm-header">
                        <span class=" " style="color:black;">解除挂失</span>
                        <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" data-dismiss="modal">
                    </div>
                    <form action="" method="post" class="form-horizontal " name="">
                        <div class="dialog-sm-info">
                            <p class="p1" id="uncardlose_confirm_info" style="color:black;"></p>
                            <table id="JcGs_member_algin_list">
                               
                            </table>
                        </div>
                        <div class="btn-operate">
                            <button class="btn btn-cancel" type="button" data-dismiss="modal">取消</button>
                            <div class="btn-division"></div>
                            <button class="btn btn-save" id="uncardlose_ok" type="button" data-dismiss="modal">确认</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
    <!-- 新增会员信息成功提示 -->
    <div class="modal fade dialog-sm in " id="modalmember_success" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialog-sm-header">
                        <span class=" " style="color:#000;">新增会员信息</span>
                        <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" data-dismiss="modal">
                    </div>
                    <div class="dialog-sm-info">
                        <p class="p1" id="success_alert_MSg" style="color:rgb(122, 196, 84)">
                            <i class="glyphicon glyphicon-ok-circle"></i><span style="padding-left:5px">新增会员成功</span><br/>
                                                                                    是否绑定会员实体卡？
                        </p>
                        <div class="btn-operate  ">
						<button class="btn btn-cancel  " type="button" data-dismiss="modal">取消</button>
						<div  class="btn-division"></div>
						<button class="btn btn-save  " id="dishes-type-save_success" type="button"  onclick="bindMemberCard(this)">绑定</button>
				    </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <!-- 新增会员信息再次确认-->
    <div class="modal fade dialog-sm in " id="modalmember_algin" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialog-sm-header">
                        <span class=" " style="color:#000;">新增会员信息</span>
                        <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" data-dismiss="modal">
                    </div>
                    <div class="dialog-sm-info" style="padding-top:0px;margin-top:5px">
                        <div class="doc-content jui-doc" id="J_doc" style="padding-top:0px">
                            <p style="text-indent:0">
                                                                                      确认以下信息是否有误：
                            </p>
                            <table id="member_algin_list">
                               
                            </table>

                        </div>
                    </div>
                    <div class="btn-operate  ">
						<button class="btn btn-cancel  " type="button" data-dismiss="modal">取消</button>
						<div  class="btn-division"></div>
						<button class="btn btn-save  " id="dishes-type-save" type="button"   onclick="addalgin()">确认</button>
				    </div>
                </div>
            </div>

        </div>
    </div>
    <!-- 会员修改 -->
    <div class="modal fade shop-dialog in " id="member_info_update" data-backdrop="static">
        <div class="modal-dialog" style="width: 800px;">
            <div class="modal-content">
                <div class="modal-header addDelicon">
                    <div class="modal-header tenantInfo">修改会员信息</div>
                    <img src="<%=request.getContextPath()%>/images/close.png" class="img-close" data-dismiss="modal">
                </div>
                <div class="modal-body">
                    <div class="dligo-margin dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">会员卡号：</div>
                        <span style="padding-left:16px"><input id="info_cardno" style="width:215px" type="text" class="form-control" disabled="disabled"></span>
                        <input type="hidden" id="info_id" />
                        <input type="hidden" id="info_tenantid" />
                        <input type="hidden" id="info_cardnotemp" />
                    </div>
                    <div class="dligo-margin dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">手机号码：</div>
                        <span style="padding-left:16px"><input type="text" style="width:215px" maxlength="11" id="info_mobile" class="form-control" /></span>
                    </div>
                    <div class="dligo-margin  dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">会员姓名：</div>
                        <span style="padding-left:16px"><input type="text" style="width:215px" maxlength="30" id="info_name" class="form-control" /></span>
                    </div>
                    <div class="dligo-margin dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">性别：</div>
                        <select class=" ztree-right-16" style="width:80px;" id="info_gender">
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                    </div>
                    <div class="dligo-margin clear dligo-margin-float">
                        <div class="dligo-margin-left  linheight-30">地址：</div>
                        <span style="padding-left:16px"><input type="text" style="width:215px" maxlength="50" id="info_address" class="form-control" /></span>
                    </div>
                    <div class="dligo-margin dligo-margin-float" style="padding-top:6px;">
                        <div class="dligo-margin-left  linheight-30">生日：</div>
                        <span style="padding-left:16px"><input type="text" class="form-control ztree-right-16" style="width:90px;" id="info_birthday"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:false})" /></span>
                    </div>
                    <div class="dligo-margin clear ">
                        <div class="dligo-margin-left  linheight-30">状态：</div>
                        <select class=" ztree-right-16" style="width:80px;background: #eeeeee" id="info_status" disabled="disabled">
                            <option value="0">注销</option>
                            <option value="1">正常</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer ">
                    <button type="button" class="btn btn-default outline" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary outline" id="member_info_update_ok" >确定</button>
                </div>
            </div>
        </div>
    </div>
     <!-- 会员信息修改二次确认 -->
    <div class="modal fade dialog-sm in " id="modal_confirm_againedit" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialog-sm-header">
                        <span class=" againeditheadr_info" style="color:black;" >解除挂失</span>
                        <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" data-dismiss="modal">
                    </div>
                    <form action="" method="post" class="form-horizontal " name="">
                        <div class="dialog-sm-info">
                            <p class="p1"  style="color:black;"></p>
                            <table id="Edit_member_algin_list">
                               
                            </table>
                        </div>
                        <div class="btn-operate">
                            <button class="btn btn-cancel" type="button" data-dismiss="modal">取消</button>
                            <div class="btn-division"></div>
                            <button class="btn btn-save"  type="button" onclick="againedit()" data-dismiss="modal">确认</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
     <!-- 会员卡实体卡绑定 -->
    <div class="modal fade shop-dialog in " id="BlindCardInfo" data-backdrop="static">
        <div class="modal-dialog" style="width: 700px;">
            <div class="modal-content">
                <div class="modal-header addDelicon">
                    <div class="modal-header tenantInfo">绑定会员实体卡 </div>
                    <img src="<%=request.getContextPath()%>/images/close.png" class="img-close" data-dismiss="modal">
                </div>
                <div class="modal-body">
                <div class="shop-search" style="margin-bottom:0px">
            <div class="form-group" style="margin-left:0px;">
                <label class="control-label" style="font-size:12px">手机号码：</label>
                <input id="Bindphone" type="text" class="form-control ng-pristine ng-valid" maxlength="11"  style="width:215px;margin-left:5px;">
            </div>
            <div class="form-group shop-search-btn">
                <button class="btn btn-default btn-outline outline" onclick="bindMemberSearch()" type="submit"><i class="icon-search"></i>搜索</button>
            </div>

        </div>
        <table width="100%"  id="menber_table" border="0" bordercolor="e5e5e5"style="border-collapse:collapse;margin-bottom:8px;" >
                 
                  
        </table> 
        <div class="dligo-margin addclone" style="margin-top:10px;">
                       
        </div>
                    
                </div>
                <div class="modal-footer ">
                    <button type="button" class="btn btn-default outline" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary outline" id="addmember_tijiao" disabled="disabled" onclick="bindmemberSave()">确定</button>
                    <!-- <button type="button" class="btn btn-primary outline" id="addmember_tijiao" disabled="disabled" onmouseover="bindmemberSave()">确定</button> -->
                </div>
            </div>
        </div>
    </div>
    <!-- 成功提示 -->
    <div class="modal fade dialog-sm in " id="success_MSGInfo" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialog-sm-header">
                        <span class=" " style="color:#000;" id="success_MSG">成功</span>
                        <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" data-dismiss="modal">
                    </div>
                    <div class="dialog-sm-info">
                        <p class="p1" id="success_MSG_infolist" >
                            <i class="glyphicon glyphicon-ok-circle"></i><span style="padding-left:5px"></span>
                        </p>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <!--会员导入密码提示 -->
    <div class="modal fade dialog-sm in " id="exportPassword_Info" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialog-sm-header">
                        <span class=" " style="color:black;">会员导入</span>
                        <img src="<%=request.getContextPath()%>/images/close-sm.png" class="img-close" onclick="exportPasswordcancel()">
                    </div>
                    <form action="" method="post" class="form-horizontal " name="">
                        <div class="dialog-sm-info">
                            <p class="p1" style="color:black;text-align: left;">请输入会员导入密码：</p>
                            <input type="password" style="width:215px" maxlength="" id="export_Password" class="form-control" />
                        </div>
                        
                        <div class="btn-operate">
                            <button class="btn btn-cancel" type="button" onclick="exportPasswordcancel()">取消</button>
                            <div class="btn-division"></div>
                            <button class="btn btn-save"  type="button" onclick="exportPasswordOk()" >确认</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</body>
<script type="application/javascript">
    var basePath = "${pageScope.basePath}";
</script>
<script src="${pageScope.basePath}/scripts/member/memberdealmanage.js"></script>
<script type="text/javascript">
    $("#cheack_img").click(function () {
        $("#example_img").toggle(1000)
    })
  var cardtype= $("#daoru_cardtype").val()
 $("#daoru_cardtype").change(function(){
	 cardtype= $("#daoru_cardtype").val()
	 uploadInfomsg();
 })
 function uploadInfomsg(){
    	 $("#uploadfy").uploadify({
             buttonClass: "uploadfy-input",
             'swf': basePath + '/tools/uploadify/uploadify.swf',
             uploader: basePath + '/excel/resolve?cardtype='+cardtype, //服务器响应地址
             cancelImg: basePath + '/tools/uploadify/uploadify-cancel.png',
             multi: false,//是否多文件,true时为多个
             auto: false,//是否自动上传，true，选择文件后上传，false，点击上传开始上传，默认true
             buttonText: "添加导入文件",//空间名称
             preventCaching: false,//是否缓存
             formData: { "beginRow": "2", "name": "file", "tenantId": $("#tenantId").val()},
             fileSizeLimit: 1024 * 1024 * 10,//单个文件限制大小
             fileTypeDesc: '*.xlsx;*.xls',//文件后缀描述
             fileTypeExts: "*.xlsx;*.xls",//文件后缀限制
             width: 100,
             fileObjName: "Filedata",
             onUploadSuccess: function (file, data, response) {//上传成功后事件
                 data = JSON.parse(data)   //转化为json对象
                 cheackInfo(data)
             }
         });
    }
    $(function () {
    	var TotalMoney=${countmap.cashCount + countmap.bankCount + countmap.refundCount + countmap.presentCount}//总金额
    	$("#TotalMoney").text(TotalMoney.toFixed(2))//保留两位小数的总金额
        memberBaseMSg();
        memberConsumptionMSg();
        Searchtext();
        uploadInfomsg()
    });
    $("#mobile").blur(function(){//增加会员信息电话失去焦点判断是否存在
    	$("#mobileNumber_erro").remove();
    	  var mobileNumber=$.trim($("#mobile").val());
    	  var phoneNumber = /^1[3|4|5|7|8]\d{9}$/  //电话号码
    	  if(mobileNumber.length ==11){
    		  if (!phoneNumber.test(mobileNumber)) {
    			  $("#memberAdd").attr("disabled","disabled");
    			  if ($("#mobileNumber_erro").size() < 1) {
	        			$("#mobile").parent().after("<div id='mobileNumber_erro' style='color:red;padding-left:95px;padding-top:8px;'>请输入正确的手机号码</div>");
	        			
	        	       }
    			  return
              }
    		  else{
    			  
    			  $("#memberAdd").removeAttr("disabled");
    		  }
    	  }
    	  else{
    		  $("#memberAdd").attr("disabled","disabled");
    		  if ($("#mobileNumber_erro").size() < 1) {
      			$("#mobile").parent().after("<div id='mobileNumber_erro' style='color:red;padding-left:95px;padding-top:8px;'>请输入正确的手机号码</div>");
      			
      	       }
    		  return
    	  }
    	  $.ajax({
    	        url: basePath + "/memberbase/membermanage/newcardmemberinfo",
    	        type: "post",
    	        //async: false,
    	        dataType: "json",
    	        data:{"mobile":mobileNumber},
    	        success: function (data) {
    	        	if(data.membernum !=0){
    	        		if ($("#mobileNumber_erro").size() < 1) {
    	        			$("#mobile").parent().after("<div id='mobileNumber_erro' style='color:red;padding-left:95px;padding-top:8px;'>该手机号码已存在，如需办理多张会员卡点击“绑定会员卡“</div>");
    	        	       }
    	        		$("#memberAdd").attr("disabled","disabled");
    	        	}
    	        	else{
    	        		$("#mobileNumber_erro").remove()
    	        	}
    	        }
    	    });
    	});
    $(window).resize(function () {   //窗口大小改变JQgrid的窗体大小
        $("#User-list").setGridWidth($(".User-list").width());
        $("#User-consumption").setGridWidth($(".User-list").width());
        $("#User-consumptionIntoErroList").setGridWidth($(".User-list").width());
    });
</script>
</html>