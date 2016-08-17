var loading = false;
var curPage = 1;
// 每次加载添加多少条目
var itemsPerLoad = 20;
// 最多可加载的条目
var maxItems = 100;
var isLastPage = false;

sessionStorage.isFirstVist= "false";

$(function(){
    //无限滚动
    $(document).on("pageInit", "#page-record", function(e, id, page) {
        var lastIndex = $('.list-container li').length;

        //初始化加载 第一页
        addItems(itemsPerLoad,lastIndex,12);

        $(page).on('infinite', function() {
            // 如果正在加载，则退出
            if (loading) return;
            // 设置flag
            loading = true;
            // 模拟1s的加载过程
            setTimeout(function() {
                // 重置加载flag
                loading = false;
                if (lastIndex >= maxItems) {
                    // 加载完毕，则注销无限加载事件，以防不必要的加载
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    // 删除加载提示符
                    $('.infinite-scroll-preloader').remove();
                    return;
                } 
                !isLastPage && addItems(itemsPerLoad,lastIndex,12);
                // 更新最后加载的序号
                lastIndex = $('.list-container li').length;
                $.refreshScroller();
            }, 1000);
        });
    });
    $.init();
});


//根据type id 获取name
function getConsumptionTypeName(id) {
    var type = {};
    if(/^\d+$/.test(id)) {
        id = id + "";
    }

    switch (id) {
        case "0" : type = {name : "现金消费",type:false}; break;    //type  true为收入，false为支出
        case "1" : type = {name : "现金消费积分",type:true}; break;
        case "2" : type = {name : "储值消费",type:false}; break;
        case "3" : type = {name : "储值消费积分",type:true}; break;
        case "4" : type = {name : "积分消费",type:false}; break;
        case "5" : type = {name : "现金充值",type:true}; break;
        case "6" : type = {name : "现金消费反结算",type:true}; break;
        case "7" : type = {name : "现金消费积分反结算",type:false}; break;
        case "8" : type = {name : "储值消费反结算",type:true}; break;
        case "9" : type = {name : "储值消费积分反结算",type:false}; break;
        case "10" : type = {name : "积分消费反结算",type:true}; break;
        case "11" : type = {name : "银行卡充值",type:true}; break;

        case "13" : type = {name : "微信扫码支付",type:false}; break;
        case "14" : type = {name : "微信扫码支付反结算",type:true}; break;
        case "15" : type = {name : "微信扫码支付积分",type:true}; break;
        case "16" : type = {name : "微信扫码支付积分反结算",type:false}; break;
        
        case "17" : type = {name : "储值赠送",type:true}; break;

        default: type = {name : "未知",type:true}; break;
    }
    return type;
}

function addItems(number, lastIndex,pagesize) {
    // 生成新条目的HTML
    // 添加新条目
    $.ajax({
        url: InterFaceUrl.findDealDetailByCardno,
        type: 'POST',
        dataType : "json",
        data :{cardno:util.getCookie('Cardno'),current:curPage,pagesize:pagesize},
        success :function(result){
            var html = '';
            var data = result.detailDatas;

            if(result.detailTotalpage === result.detailCurrent) {
            	isLastPage = true;
            	 $('.infinite-scroll-preloader').remove();
            }
            $.each(data,function(i,v){
                html += '<li class="item-content notPadding"><div class="item-inner"><div class="item-title"><span class="item-title-type">' + getConsumptionTypeName(v.deal_type)['name'] + '</span><p class="item-title-time">' + new Date(parseInt(v.deal_time)).pattern("yyyy-MM-dd HH:mm:ss") + '</p></div> <div class="item-after item-after-value-' + (getConsumptionTypeName(v.deal_type).type ? 'in' : 'out') + '">' + (getConsumptionTypeName(v.deal_type).type  ? '+' : '-') + Math.abs(v.amount) + '</div></div></li>';
            });

            if(lastIndex ===0 && data.length === 0) {
                $('.infinite-scroll').html('<div style="text-align: center;padding:5rem 0;">暂无数据</div>');
                $('.infinite-scroll-preloader').remove();
                return;
            }

            if(result.detailTotalpage == 1) {
                $('.infinite-scroll-preloader').remove();
            }
            $('.infinite-scroll .list-container').append(html);

            curPage++;

            maxItems = result.detailTotal;

            console.info(result.detailTotal);

        },
        error :function(){
            var html = '';
            console.info('err');
            $('.infinite-scroll .list-container').append('<li><div class="item-inner">请求数据错误！</div></li>');
        }
    });
}