<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>微信会员</title>
    <meta name="description" content="MSUI: Build mobile apps with simple HTML, CSS, and JS components.">
    <meta name="author" content="微信会员">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="../wechat/favicon.ico">
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

<script src="http://m.sui.taobao.org/dist/js/sm.js"></script>
<script src="../wechat/src/js/common.js"></script>
<script>
    //util.delCookie('Cardno');
    //alert(util.getCookie('Cardno'))
    //window.location.href = './apply'
    var cardno = util.getCookie('Cardno');
    var tenantId = util.getRequestName('tenantId');
    //debugger;
    if(tenantId !== null) {
    	util.setCookie("tenantId",tenantId,365);
    }

    function onBridgeReady(){
        if(sessionStorage.isFirstVist) {
            WeixinJSBridge.call('closeWindow');
            return false;
        }

        if(cardno) {//用户登陆过


            $.ajax({
                url: InterFaceUrl.binding,
                type: 'POST',
                dataType : "json",
                contentType : "application/json; charset=utf-8",
                data: JSON.stringify({
                    'phoneNumber' : util.getCookie('phone'),
                    'passWord' : util.base64_decode(util.getCookie('password')),
                    'tenantId' : tenantId
                }),
                success: function(result){
                    if(result.code === '0'){//判断cooke中的登陆信息是否有效  ‘0’表示有效
                        $.ajax({
                            url: InterFaceUrl.verifyDefaultInfo,
                            type: 'POST',
                            dataType : "json",
                            contentType : "application/json; charset=utf-8",
                            data: JSON.stringify({
                                tenantId : tenantId,
                                cardno : cardno
                            }),
                            success: function(result){

                                console.info(result.data.isDefaultInfo);
                                if(result.data.isDefaultInfo) {//个人信息是否默认信息
                                    window.location.href = './perfectInfo';//完善个人信息
                                } else {
                                    window.location.href = './userCenter';
                                }
                            }
                        });
                    }else{
                        window.location.href = './apply';
                    }
                }
            });

        } else {
            window.location.href = './apply'
        }
    }

    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }

</script>

</body>
</html>
