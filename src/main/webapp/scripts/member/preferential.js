/**
 * Created by goujianjun on 2016/4/13.
 */
var interfaceUrl = {
    modifyStatus : global_Path + '/preferential/modifyStatus',
    getBranch : global_Path + '/preferential/getBranch',
    updatePreferential : global_Path + '/preferential/modify'
}

function consumptionInfo(){//会员基本信息
	var witdh = $(".User-list").width();
	$("#info-list").jqGrid({
		url: basePath + "/preferential/list",
        datatype: "json",
        mtype: "post",
        width: witdh,
        //shrinkToFit: true,
        forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略。
        altRows: true,
        height: 'auto',
        colNames: ['优惠名称','优惠类型', '优惠规则', '餐厅数量','微信会员','状态','操作'],
        
        colModel: [
            { name: 'name', index: 'name',width:"209",   align: "center"},
            { name: 'type', index: 'type',  align: "center",
            	formatter: function (value, rowData, obj) {
            		var result = "";
            		if(obj.type == 1)
            			result = "充值赠送";
            		else if(obj.type == 2)
            			result = "消费积分";
            		return result;
            	}
            },
            { name: 'rule', index: 'rule',  align: "center",
            	formatter: function (value, rowData, obj) {
            		var result = "";
            		if(obj.type == 1) {
            			result = "充值"+obj.dealValue+"赠送"+obj.presentValue;
            			if(obj.rule == 1)
            				result += ",多充多送";
            		}
            		return result;
            	}
            },
            { name: 'countInfo', index: 'countInfo', align: "center",width: "120" },
            { name: 'weixinStatus', index: 'weixinStatus', align: "center",
            	formatter: function (value, rowData, obj) {
            		var result = "";
            		if(obj.weixinStatus == 1)
            			result = "启用";
            		else if(obj.weixinStatus == 0)
            			result = "不启用";
            		return result;
            	}
            },
            { name: 'status', index: 'status',  align: "center" ,
            	formatter: function (value, rowData, obj) {
            		var result,flag = "";
            		if(obj.status == 0)
            			flag = "checked";
            		result = "<label class='label-switch'>"
            			+ "<input onClick='updateStatus(this,"+obj.id+")' type='checkbox' class='J-btn-checkbox' " + flag +">"
                        + "<div class='checkbox'></div>"
                        + "<span class='ac'>启用</span>"
                        + "<span class='dis'>禁用</span></label>";            		
            		return result;
            	}
            },
            {
                name: 'id', index: 'id', width: "120", align: "center", sorttype: "float",//操作
                formatter: function (value, rowData, obj) {
                	var result = "";
					result += "<a href='../preferential/detail?id="+obj.id+"' >编辑</a> ";
					result += "<a href='javascript:deleteShow("+obj.id+")' class='ml10 J-btn-del'>删除</a> ";
                    return result;
                }
            }
        ],
        rownumbers: true,
        gridview: true,
        grouping: true,
        pager: "#gridPagerInfo",
        rowNum: 10,//gridParameters.grid_rowNum,
        rowList: [10,30,50], //gridParameters.grid_rowList,
        prmNames: {
            search: "search"
        },
        jsonReader: {
            root: "resultList", // (2)    json中代表实际模型数据的入口
            page: "page", // 当前页
            total: "total", //总页数
            records: "records", // 总记录数 (3)   json中代表数据行总数的数据 ，既总记录数
            repeatitems: false // (4)如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定
        },
        loadComplete: function () {//没有查询到符合条件的记录
            var re_records = $("#info-list").getGridParam('records');
            if (re_records == 0 || re_records == null) {
                var str = '<div id="history-nodata" class=\"norecords\" style="height:60px;padding-top:20px;">'
                str += ' <span class=" glyphicon glyphicon-info-sign" style="vertical-align: middle"></span>'
                str += ' <span style="font: 14px/1.6 Tahoma,microsoft yahei,"微软雅黑","宋体";padding-left:10px;">没有查询到符合条件的记录</span></div>'
                $("#gridPagerInfo").hide();
                if ($(".norecords").html() == null) {
                    $("#info-list").parent().append(str);
                }
                else {
                    $(".norecords").show();
                }
            }
            else {
                $(".norecords").hide();
                $("#gridPagerInfo").show()
            }
        },
        hidegrid: true,
        viewrecords: true,
        multiselect: false	 //是否支持多选
    });
        
}

function deleteShow(id) {
    $("#rdel").attr("rid",id);
    var $modalConfirDel = $('#modal_confirDel');
    $modalConfirDel.modal("show");
}

function updateStatus(obj, id) {
	var me = $(obj);
	var isChecked = me.is(':checked');
    $.ajax({
		type : "post",
		async : false,
		data : { 'id':id,'status': isChecked ? 0 : 1, 'updateStatus':'updateStatus'},
		url:interfaceUrl.modifyStatus,
		dataType : "json",
		success : function(result) {
			if(result.code == '0') {
                sendMsg(true,'操作成功!');
                $("#info-list").jqGrid("setGridParam", {search: true,mtype : "post" }).trigger("reloadGrid", [{page:1}]);
			} else {
                sendMsg(false,result.msg);
            }
	}});	
}

var Preferential = {
    init : function(){
        this.bindEvent();
    },

    bindEvent : function(){
        var that = this;
        var $preferentialList = $('#preferentialList');
        var $modalConfirDel = $('#modal_confirDel');
        var $addStore = $("#addstore");
        var $storeSelectDialog = $('#store-select-dialog');
        var $preferentialForm = $('#preferentialForm');
        var $typeBox = $('#preferential-type-box');

        //删除优惠 弹窗
        $preferentialList.delegate('.J-btn-del','click', function(){
            var me = $(this);
            var id = me.parents('tr').data('id');
            me.parents('tr').addClass('tr-active');
            $modalConfirDel.modal("show");
        });

        //删除优惠弹窗 确认
        $modalConfirDel.delegate('.btn-save','click', function(){
        	var id = $("#rdel").attr("rid");
            $.ajax({
                type : "post",
                async : false,
                data : { 'id':id,'status': 2, 'updateStatus':'del'},
        		url:interfaceUrl.modifyStatus,
        		dataType : "json",
        		success : function(result) {
        			if(result.code === '0') {
                        sendMsg(true,'删除成功!');
                        $("#info-list").jqGrid("setGridParam", {search: true,mtype : "post" }).trigger("reloadGrid", [{page:1}]);
        			} else {
                        sendMsg(false,result.msg);
                    }
        	}});
        });

        //删除优惠弹窗 取消
        $modalConfirDel.delegate('.btn-cancel','click', function(){
            $preferentialList.find('tr.tr-active').removeClass('tr-active');
        });

        //更新门店信息 弹窗
        $addStore.click(function(){
            that.updateStoreCount();
            $storeSelectDialog.modal("show");
        });

        $preferentialForm.delegate('.J-type-select','change',function(){
            var me = $(this);
            if(me.val() === '1') {
                $typeBox.show();
            } else {
                $typeBox.hide();
            }

        });

        //更新门店信息弹窗 确认
        $storeSelectDialog.delegate(".btn-save",'click',function(){
            var selectedBranch = $storeSelectDialog.find("input:checked[type='checkbox']");
            var selectBranchs=[];
            var html = '<ul id="selectStoreList">';

            if(selectedBranch.length > 0){

                $.each(selectedBranch,function(i,obj){
                    var me = $(obj);
                    selectBranchs.push(me.val() + '|' + me.next('label').text());
                    var name = me.next("label").text();
                    html += ("<li>"+name+"</li>")
                });
                html += '</ul>';

                if($addStore.data('bs.popover') === undefined) {
                    $addStore.popover({
                        html : true,
                        trigger : 'hover',
                        content: html
                    });
                } else {
                    $addStore.data('bs.popover').options.content = html;
                }


                $("#selectBranchs").val( selectBranchs.join(","));
                $addStore.text("已选中"+selectedBranch.length + "家店").addClass("selectBranch");
            }else{

                if($addStore.data('bs.popover') === undefined) {
                    $addStore.popover({
                        html : true,
                        trigger : 'hover',
                        content: ''
                    });
                } else {
                    $addStore.data('bs.popover').options.content = '';
                }
                $("#selectBranchs").val("");
                $addStore.html('<i class="icon-plus"></i>选择门店 ').removeClass("selectBranch").next(".popover").remove();
            }
            $("#store-select-dialog").modal("hide");
        });

        //bind Store event
        $storeSelectDialog.delegate("input[type='checkbox']",'click',that.updateStoreCount);

        $storeSelectDialog.find(".radio-inline input").click(function(){
            var me = $(this);
            var $storeSelectContent = $('#store-select-dialog .store-select-content');
            if(me.val()=='1'){
                $storeSelectContent.find("input[type='checkbox']").each(function(i,ch){
                    ch.checked = true;
                });
            }else{
                $storeSelectContent.find("input[type='checkbox']").each(function(i,ch){
                    ch.checked = false;
                });
            }
        });

        $preferentialForm.delegate('.J-btn-submit','click',function(){

        })
    },

    getAllBranch : function(){
        var that = this;
        $.ajax({
            type : "post",
            async : false,
            url:interfaceUrl.getBranch,
            dataType : "json",
            success : function(result) {
                console.dir(result);
                if(result.code === '0') {
                    that.setBranch(result.data);
                } else {
                    //sendMsg(false,result.msg);
                }
            }});
    },

    getSelectedBranchById : function(id){
        var that = this;
        if(id.length ==0) return false;
        $.ajax({
            type : "post",
            async : false,
            url:interfaceUrl.getBranch,
            data :{id:id},
            dataType : "json",
            success : function(result) {
                if(result.code === '0') {
                    $('[data-toggle="popover"]').popover();
                    if(result.data.length > 0) {
                        var html = '<ul id="selectStoreList">';
                        var selectBranchs = [];
                        $('#addstore').find('span').text("已选中"+result.data.length + "家店").end().find('.icon-plus').hide();


                        $.each(result.data,function(i,v){
                            html += '<li>' + v.branchname + '</li>';
                            selectBranchs.push(v.branchid + '|' + v.branchname);
                        });
                        html += '</ul>';

                        $("#selectBranchs").val(selectBranchs.join(','));

                        that.loadSelectStore();

                        //自定义popover显示的内容
                        $('#addstore').popover({
                            html : true,
                            trigger : 'hover',
                            content: html
                        });
                    } else {
                        $('#addstore').find('span').text('选择门店').end().find('.icon-plus').show();
                    }
                } else {
                    //sendMsg(false,result.msg);
                }
            }});

    },

    setBranch : function(data){
        var $storeSelectContent = $('#store-select-dialog .store-select-content');
        $storeSelectContent.html();
        var html="<tr>";
        $.each(data, function(i, obj) {
            html=html+" <td><input type='checkbox' value="+obj.branchid+" id='id_"+obj.branchid+"'><label for='id_"+obj.branchid+"'>"+obj.branchname+"</label></td>";
            if( (i+1)%4==0){//因为计数从0开始，所以要加一个才能显示正常
                html+="</tr><tr>"
            }
        });
        html=html+"</tr>"
        $storeSelectContent.html(html);
    },

    loadSelectStore : function(){
        $("table.store-select-content input[type='checkbox']").prop("checked", false);
        //如果当前已经有选择的门店，需要将选择的门店，重新在页面显示为选中的状态
        if( $("#selectBranchs").val() != ""){
            $.each( $("#selectBranchs").val().split(","),function(i,obj){
                console.info(obj.split('|')[0]);
                $("table.store-select-content input[type='checkbox'][value='" + obj.split('|')[0] + "']").prop("checked", true);
            });
        }
        this.updateStoreCount();
    },

    ////将弹出的选择门店的层中，更新选择的门店数量的方法抽象出来。当选择的门店为0个的时候，标题显示“选择门店”，否则显示“ 选择门店（已选1家店）”
    updateStoreCount : function(){
        var $storeSelectContent = $('#store-select-dialog .store-select-content');
        var len = $storeSelectContent.find("input[type='checkbox']").length;
        var count = $storeSelectContent.find("input[type='checkbox']:checked").length;
        var $checkAll = $("input[name='checkAll'][value='0']");
        var $checkNone = $("input[name='checkAll'][value='0']");
        var $storeCount = $("#store-count");

        if( count != 0){
            $storeCount.parent().html("选择门店（已选<font id='store-count'>"+count+"</font>家店）");
            $checkAll.prop("checked", false);
            $checkNone.prop("checked", true);
        }else{
            $storeCount.parent().html("选择门店<font id='store-count'></font>");
            $checkAll.prop("checked", true);
            $checkNone.prop("checked", false);
        }

        if(count == len) {
            $checkAll.prop("checked", false);
            $checkNone.prop("checked", true);
        } else if(count == 0) {
            $checkAll.prop("checked", true);
            $checkNone.prop("checked", false);
        } else {
            $checkAll.prop("checked", true);
            $checkNone.prop("checked", true);
        }
    },

    setPager : function(total,totalPages,visiblePages,startPage){
        if( $("#total").val() > 10 ){
            $(".pagingWrap").html('<ul class="paging clearfix">');
            $(".paging").twbsPagination({
                totalPages: totalPages + '',
                visiblePages: visiblePages + '',
                startPage : startPage,
                first: '...',
                prev : '<',
                next : '>',
                last: '...',
                onPageClick: function (event, page){
                    console.info(page);
                   window.location.href=global_Path+ '/preferential/list?current='+page;
                }
            });
        }else {
            $(".pagingWrap").empty();
        }
    }
};

Preferential.init();