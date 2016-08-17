<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>交易记录</title>
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
    <div  class="page page-record" id="page-record">
        <div class="content infinite-scroll" data-distance="100">
            <div class="list-block">
                <ul class="list-container"></ul>
            </div>
            <!-- 加载提示符 -->
            <div class="infinite-scroll-preloader">
                <div class="preloader">

                </div>
                <span>加载更多</span>
            </div>
    </div>
    </div>
</div>
<script src="http://m.sui.taobao.org/dist/js/sm.js"></script>
<script src="../wechat/src/js/common.js"></script>

<!--mock start   生产环境 请移除-->
<%--<script src="../wechat/src/js/mock-min.js"></script>--%>
<%--<script src="../wechat/src/js/mockData.js"></script>--%>
<!--mock end   生产环境 请移除-->

<script src="../wechat/src/js/recorder.js"></script>
</body>
</html>
