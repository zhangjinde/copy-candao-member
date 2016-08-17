/**
 * 树形下拉JQuery扩展函数
 * 1. 参数说明如下
 * {
 * 	"txtIds":"zyDepIds", //存放选中值的input的Id
 * 	"width":180,		 //高度 
 * 	"height":300,		 //宽度
 *  "showLine" true,	 //是否显示连接线
 * 	"url":xxx.action 	 //异步请求的url地址, action返回的格式参考data, 如果传递了data则url参数无效
 *  "afterChanged":null  //值改变的回调函数
 *  "chkStyle":checkbox	 //单选还是复选
 * 	"data": [
 * 		{id:6, pId:0, name:"重庆"},
 *		{id:4, pId:0, name:"河北省", open:true, nocheck:true},
 *		{id:41, pId:4, name:"石家庄", checked:true}
 * 	]	//data的格式请参考zTree的data格式
 * },
 * "chkboxType":{"Y": "ps", "N": "ps" } //Y属性定义checkbox被勾选后的情况; N属性定义checkbox取消勾选后的情况; "p"表示操作会影响父级节点,"s"表示操作会影响子级节点 }
 * 2. 编辑初始化选中有2个方式 1）传递txtIds参数自动根据id选中checkbox 2）传递的data数据自己指定checked属性
 * @author yuehan
 * @date 2012-05-31
 */
$.fn.advSelect = function(options) {
	var $this = $(this);
	options = options || {};
	
	// 需要执行change回调方法, 此函数需要写到调用之前
	var assignValue = function(newVal) {
		var oldVal = $this.val();
		$this.val(newVal);
		if ((oldVal != newVal) && options.afterChanged) {
			options.afterChanged();
		}			
	};
	
	// 页面加载的时候根据id初始化name
	if (options.txtIds && options.data) {
		var checkedIds = $("#" + options.txtIds).val().split(",");
		var checkedTxts = "";
		for ( var i = 0; i < checkedIds.length; i++) {
			if ($.trim(checkedIds[i]) != ",") {
				for ( var j = 0; j < options.data.length; j++) {
					if (checkedIds[i] == options.data[j].id) {
						checkedTxts += options.data[j].name + ",";
					}
				}
			}
		}
		if (checkedTxts != "") {
			var newVal = checkedTxts.substring(0, checkedTxts.length - 1);
			assignValue(newVal);
		}
	}	 

	// 绑定onclick事件
	$this.focus(function() {
		show();
	});

	// 绑定关联的button
	if ($this.next().is("input[type=button]")) {
		$this.next().click(function() {
			show();
		});
	}

	var show = function() {
		var width = options.width ? options.width : 180;
		var height = options.height ? options.height : 300;
		// 取当前对象的id属性作为下拉菜单的id的前缀
		var idPrefix = $this.attr("id");
		if (!idPrefix) {
			idPrefix = $this.attr("name").replace(".", "-");
		}
		var divId = idPrefix + "treeDropDown";
		var menuId = idPrefix + "tree";
		var html = '<div id="'
				+ divId
				+ '" style="position: absolute;"><ul id="'
				+ menuId
				+ '" class="ztree" style="margin-top:0; width:'
				+ width
				+ 'px; height: '
				+ height
				+ 'px;border: 1px solid #617775;background: #f0f6e4;overflow-y:scroll;overflow-x:auto;"></ul></div>';
		if ($("#" + divId).length <= 0) {
			$("body").append(html);
		}
		
		var chkStyle = (typeof options.chkStyle != 'undefined') ? options.chkStyle : "checkbox";
		var setting = {
			check : {
				enable : true,
				chkStyle: chkStyle,
				radioType: "all",
				chkboxType : (typeof options.chkboxType != 'undefined') ? options.chkboxType
						: {
							"Y" : "",
							"N" : ""
						}
			},
			view : {
				showLine : (typeof options.showLine != 'undefined') ? options.showLine
						: true
			},
			callback : {
				beforeClick : function(treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj(menuId);
					zTree.checkNode(treeNode, !treeNode.checked, null, true);
					return false;
				},
				onCheck : function(e, treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj(menuId);
					nodes = zTree.getCheckedNodes(true);
					var txtVal = "";
					var idVal = "";
					for ( var i = 0, len = nodes.length; i < len; i++) {
						txtVal += nodes[i].name + ",";
						idVal += nodes[i].id + ",";
					}
					if (txtVal.length > 0)
						txtVal = txtVal.substring(0, txtVal.length - 1);
					if (idVal.length > 0)
						idVal = idVal.substring(0, idVal.length - 1);
					// 
					if (options.txtIds) {
						$("#" + options.txtIds).val(idVal);
					}
					assignValue(txtVal);
				}
			}
		};

		if (options.data) {
			setting.data = {
				simpleData : {
					enable : true
				}
			};
		} else if (options.url) {
			setting.async = {
				enable : true,
				url : options.url,
				autoParam : [ "id" ]
			};
			//默认采用简单JSON格式
			setting.data = {
				simpleData : {
					enable : true
				}
			};
		}
		var onBodyDown = function(event) {
			if (!($(event.target) == $this || event.target.id == divId || $(
					event.target).parents("#" + divId).length > 0)) {
				hideTree();
			}
		};

		var showTree = function() {
			$("#" + divId).css({
				left : $this.offset().left + "px",
				top : $this.offset().top + $this.outerHeight() + "px"
			}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		};

		var hideTree = function() {
			$("#" + divId).fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		};

		var init = false;
		if (!init) {
			if (options.data) {
				$.fn.zTree.init($("#" + menuId), setting, options.data);
			} else {
				$.fn.zTree.init($("#" + menuId), setting);
			}
			// 处理初始化
			var zTree = $.fn.zTree.getZTreeObj(menuId);
			var nodes = zTree.transformToArray(zTree.getNodes());
			if (options.txtIds) {
				var checkedIds = $("#" + options.txtIds).val().split(",");
				var checkedTxts = "";
				for ( var i = 0; i < checkedIds.length; i++) {
					if ($.trim(checkedIds[i]) != ",") {
						for ( var j = 0; j < nodes.length; j++) {
							if (nodes[j].id == checkedIds[i]) {
								// 第四个参数为true会有问题
								// callbackFlag = false 表示执行此方法时不触发事件回调函数
								zTree.checkNode(nodes[j], true, false, false);
								// 保存文本值
								checkedTxts += nodes[j].name + ",";
							}
						}
					}
				}
				if (checkedTxts != "") {
					var newVal = checkedTxts.substring(0, checkedTxts.length - 1);
					assignValue(newVal);
				}
			}
		}		
		showTree();
	};	
};
