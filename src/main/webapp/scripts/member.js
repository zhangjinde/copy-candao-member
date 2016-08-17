/*$(function(){
	//$("#dealListDialog").modal("hidden");
	$("#dishTypeShow").click(function(){
		//toggleClass() 对设置或移除被选元素的一个或多个类进行切换。
		$("#dishTypeSelect").toggleClass("hidden");
	});
	$("img.img-close").hover(function(){
	 	$(this).attr("src",global_Path+"/images/close-active.png");	 
	},function(){
			$(this).attr("src",global_Path+"/images/close-sm.png");
	});
});*/

function doQuery() {
	$("#current").val("1");
}
function levelselect(id,name) {
	$("#selectLevelName").val(name);
	$("#level").val(id);
	$("#dishTypeSelect").toggleClass("hidden");
}


