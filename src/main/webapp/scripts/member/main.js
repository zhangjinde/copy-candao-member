/**
 * Created by goujianjun on 2016/4/12.
 */

var MemberMain = {
    init : function(){
        this.bindEvent();
    },

    bindEvent : function(){
        var that = this;
        var $btnToMember = $('.J-btn-to-member');
        var $btnToConsumption = $('.J-btn-to-consumption');
        var $btnToPreferential = $('.J-btn-to-preferential');
        var $btnExit = $('.J-btn-exit');
        var $modalConfirmExit = $("#modal_confirmExit");
        var $btnTransInfo=$('.J-btn-to-transInfo');

        //会员信息
        $btnToMember.click(function(){
            $(parent.document.all("detail")).attr("src",web_conf.rootPath + "/memberdeal/searchPage");
            $("#allSearch").css("visibility","hidden");
        });

        //会员消费信息
        $btnToConsumption.click(function(){
            $(parent.document.all("detail")).attr("src",web_conf.rootPath + "/consumption/index");
            $("#allSearch").css("visibility","hidden");
        }); 
        
       //优惠管理
        $btnToPreferential.click(function(){
            $(parent.document.all("detail")).attr("src",web_conf.rootPath + "/preferential/index");
            $("#allSearch").css("visibility","hidden");
        });

        $btnExit.bind('click',function(){
            $modalConfirmExit.modal("show");
        });
        
        $btnTransInfo.click(function(){
            $(parent.document.all("detail")).attr("src",web_conf.rootPath + "/consumption/transInfo");
            $("#allSearch").css("visibility","hidden");
        }); 
    }
};

MemberMain.init();

$(document).ready(function() {

    //dialog中右上角关闭按钮，鼠标经过效果
    $("img.img-close").hover(function(){
        $(this).attr("src", web_conf.rootPath + "/images/close-active.png");
    },function(){
        $(this).attr("src", web_conf.rootPath + "/images/close-sm.png");
    });

    //门店管理 鼠标经过事件
    $('.ky-navbar-menu > .ky-nav > li > a.ky-menu-primary').css({
        'background-color' : '#7AC454',
        'color' : '#ffffff'
    });

    //一、二级菜单 点击事件
    $('.ky-nav li a').click(function() {
        var _this = $(this);

        //判断是否为报表，非报表菜单 删除报表中选择的门店信息
        if(_this.parent().attr("id") == "bb" || (_this.parent().parent().hasClass('ky-dropdown-menu') && _this.parent().parent().parent().attr("id") == "bb")){
            //此为报表
        }else{
            //删除报表选择门店
            localStorage.removeItem("currentStore");
        }

        //点击任意菜单，取消报表菜单的选中状态
        $('.ky-menu-success').removeClass("ky-menu-active");

        //  改变文本
        $("#title_p").html(_this.html());
        //若是报表分析，显示第一个子菜单名称
        if(_this.parent().attr("id") == "bb"){
            $("#title_p").html($(".ky-dropdown-menu").find("li").eq(0).find("a").text());
        }
        //获取当前菜单对应的颜色代码
        var bgcolor = _this.css('border-left-color');

        //将其他菜单置为灰色
        $('.ky-nav li a').css({
            'background-color' : '#E8E7E4',
            'color' : '#282828'
        });
        //设置当前菜单的颜色
        _this.css({
            'background-color' : bgcolor,
            'color' : '#ffffff'
        });

        //将系统header设为当前菜单一致的颜色
        $('.ky-title').css('background-color', bgcolor);

        //判断是否有二级菜单
        if (_this.parent().hasClass('ky-dropdown')) {
            _this.next('ul').find('a').css({
                'background-color' : bgcolor,
                'color' : '#ffffff'
            });
        }

        //若点击的是二级菜单中一项
        if (_this.parent().parent().hasClass('ky-dropdown-menu')) {
            //将父菜单颜色设为对应颜色（未选中状态的颜色）
            _this.parent().parent().prev('a').css({
                'background-color' : bgcolor,
                'color' : '#ffffff'
            });
            //隐藏二级菜单
            _this.parent().parent('ul.ky-dropdown-menu').css('display', 'none');
        }
    });
    //餐台管理
    //bindEventForsecondMenu("ct");
    //报表管理

// 	$("#allSearch").css("visibility","hidden");

    //二级菜单鼠标经过事件
    $('.ky-dropdown-menu a').mouseover(function() {
        var bgcolor = $(this).css('border-left-color');
        //将父菜单颜色设为对应色（未选中状态的颜色）
        $(this).parent().parent().prev('a').css({
            'background-color' : bgcolor,
            'color' : '#ffffff'
        });

        //鼠标经过变颜色
        $(this).addClass("ky-menu-sub-hover");
    });
    //鼠标离开二级菜单，将父菜单置为灰色
    $('.ky-dropdown-menu a').mouseout(function() {
        //鼠标移开变颜色
        $(this).removeClass("ky-menu-sub-hover");
        //是否有被选中的报表菜单
        if(!$(".ky-menu-success").hasClass("ky-menu-active")){
            $(this).parent().parent().prev('a').css({
                'background-color' : '#E8E7E4',
                'color' : '#282828'
            });
        }
    });
    //报表分析子菜单点击事件
    $('.ky-menu-success').click(function(){
        $('.ky-menu-success').removeClass("ky-menu-active");
        $(this).addClass("ky-menu-active");
    });
    //搜索按钮点击
    $("#basic-addon1").click(function(){
        var text = $("#searchText").val();
        var iframes = $(parent.document.all("detail"));
        if(iframes.length>0){
            iframes[0].contentWindow.searchDataFromMain(text);
        }
    });

    //默认加载第一个左侧的菜单
    if($("#ul_left_menu").find("li").length!=0){
        $("#ul_left_menu").find("li").first().find("a").trigger("click");
    }else{
        toMyAccount();
        $(".ky-container-iframe").find(".ky-title p#title_p").html("我的账户");
    }
});
//报表分析一级菜单鼠标经过
function bindEventForsecondMenu(id){
    $('#'+id).mouseover(function() {
        $('#'+id+' > ul.ky-dropdown-menu').css('display', 'block');
        var bgcolor = $(this).children('a').css('border-left-color');
        $(this).find('.ky-dropdown-menu a').css({
            'background-color' : bgcolor,
            'color' : '#ffffff'
        });
    });

    $('#'+id).mouseout(function() {
        $('.ky-dropdown > ul.ky-dropdown-menu').css('display', 'none');
    });
}
/**
 * 调用子页面查询功能
 */
function searchText(){
    $(parent.document.all("detail")).window.queryData();
}