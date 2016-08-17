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
<div  class="page page-user">
</div>
<script src="http://m.sui.taobao.org/dist/js/sm.js"></script>
<script src="../wechat/src/js/common.js"></script>
<script>
    $(function(){
        var msg = unescape(util.getRequestName('msg')),
                time = parseInt(util.getRequestName('time')),
                type = parseInt(util.getRequestName('type')),
                url = unescape(util.getRequestName('url'));

//        if(/userCenter/.text(document.referrer)) {
//            window.location.href = './' + url
//        }

        $.toast( msg, time, type);
        setTimeout(function(){
            window.location.href = './' + url
        },time)
        $.init();
    })
</script>

</body>
</html>
