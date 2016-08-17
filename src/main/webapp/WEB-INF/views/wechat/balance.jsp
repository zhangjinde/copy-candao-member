<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的余额</title>
    <meta name="description" content="">
    <meta name="author" content="微信会员">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">

    <!-- Google Web Fonts -->
    <link rel="stylesheet" href="http://m.sui.taobao.org/dist/css/sm.css">
    <link rel="stylesheet" href="http://m.sui.taobao.org/dist/css/sm-extend.css">
    <link rel="stylesheet" href="../wechat/assets/css/app.css">
    <script src="http://m.sui.taobao.org/assets/js/zepto.js"></script>
</head>
<body>
<div  class="page page-balance">
    <div class="content">
        <div class="content-padded">
            <div class="my-balance-box f-tac">
                <i></i>
                <p class="mt">我的余额</p>
                <p class="mc">￥<span class="J-balance"></span></p>
                <p class="btn-box"><a href="javascript:void(0)" onclick="window.location.href='./record'" class="button button-light button-big J-btn-submit f-fwb">交易记录</a></p>
            </div>
            <!--<p><a href="javascript:void(0);"class="button button-fill button-big disabled J-btn-submit">充值</a></p>-->

        </div>
    </div>
    <div class="wx-tip">
        <i></i>微信安全支付
    </div>
</div>
<script src="http://m.sui.taobao.org/dist/js/sm.js"></script>
<script src="../wechat/src/js/common.js"></script>
<script>
    sessionStorage.isFirstVist= "false";
    $(function(){
        var $balance = $('.J-balance');
        var cardNo =  util.getCookie('Cardno');
        $.ajax({
            url: InterFaceUrl.balance,
            type: 'POST',
            dataType : "json",
            contentType : "application/json; charset=utf-8",
            data: JSON.stringify({
                'cardno' : cardNo
            }),
            success: function(result){
                console.info(result);
                if(result.code === '0') {
                    $balance.text(result.data.balance);
                } else {
                    console.info('获取数据失败');
                }
            }
        });
    })
</script>

</body>
</html>
