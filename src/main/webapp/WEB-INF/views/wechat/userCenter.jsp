<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>会员中心</title>
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
<div>
    <div id="page-modal" class="page page-user">
        <div class="content">
            <div class="ui-card ui-card-s1">
                <div class="ui-card-name">尊享卡</div>
                <div class="ui-card-cnt">
                    <div class="ui-card-thumb"><img src="../wechat/assets/i/card_logo.png" alt=""></div>
                    <div class="ui-card-info">
                        <div class="name">餐道</div>
                        <div class="info">开启智能餐厅时代</div>
                        <div class="card-no J-card-no">
                            --
                        </div>
                    </div>
                </div>
                <img src="../wechat/assets/i/card.png" class="ui-card-bg" alt="">
            </div>
            <div class="list-block">
                <ul>
                    <li>
                        <a href="javascript:void(0)" onclick="window.location.href='./mybalance'"  class="item-content item-link notPadding">
                            <div class="item-media"><i class="icon icon-f7"></i></div>
                            <div class="item-inner">
                                <div class="item-title">余额</div>
                                <div class="item-after">￥<span class="J-balance"></span></div>
                            </div>
                        </a>
                    </li>
                    <li >
                    	<a href="javascript:void(0)" onclick="window.location.href='./personalInfo'" target="_blank" class="item-content item-link notPadding">
	                    	<div class="item-media"><i class="icon icon-f7"></i></div>
	                        <div class="item-inner">
	                            <div class="item-title">个人设置</div>
	                        </div>
                    	</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>

</div>
<script src="http://m.sui.taobao.org/dist/js/sm.js"></script>
<script src="../wechat/src/js/common.js"></script>


<script>
    sessionStorage.isFirstVist= "false";
    $(function(){
        var $balance = $('.J-balance');
        var $cardNo = $('.J-card-no');
        var cardNo =  util.getCookie('Cardno');
        if(cardNo.length == 12) {
            $cardNo.text(cardNo.replace(/[\s]/g, '').replace(/(\d{4})(?=\d)/g, "$1-"));
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
                        console.info('获取验证码失败');
                    }
                }
            });
        }
    })
</script>
</body>
</html>
