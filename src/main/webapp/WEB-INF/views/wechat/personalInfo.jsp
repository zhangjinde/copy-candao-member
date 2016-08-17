<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>个人信息</title>
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
    <div  class="page page-user" id="page-user">
        <div class="content content-padded">
            <div class="list-block personal-list">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">姓名</div>
                                <div class="item-after c-gray J-name">
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">性别</div>
                                <div class="item-after c-gray J-gender">
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">生日</div>
                                <div class="item-after c-gray J-birthday">
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <a href="javascript:void(0)" onclick="window.location.href='./updatePassword'" class="item-content item-link">
                            <div class="item-inner">
                                <div class="item-title label">修改密码</div>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
             <div class="content-padded">
                <p><a href="javascript:void(0);"class="button button-light   J-btn-quit">退出登录</a></p>
            </div>
        </div>
    </div>

</div>
<script src="http://m.sui.taobao.org/dist/js/sm.js"></script>
<script src="../wechat/src/js/common.js"></script>

<script>
    sessionStorage.isFirstVist= "false";
    var $quit = $(".J-btn-quit");
    $(function(){
        var cardNo =  util.getCookie('Cardno');
        $quit.bind('click',function(){
            util.delCookie('Cardno');
            util.delCookie('phone');
            util.delCookie('password');
            setTimeout(function(){
                window.location.href='./apply';
            },10)
        })
        if(cardNo.length == 12) {
            $.ajax({
                url: InterFaceUrl.getMemberInfo,
                type: 'POST',
                dataType : "json",
                contentType : "application/json; charset=utf-8",
                data:JSON.stringify({
                    'cardno' : cardNo
                }),
                success: function(result){
                    console.info(result);
                    if(result.code === '0') {
                        $('.J-name').text(result.data.name);
                        $('.J-gender').text(result.data.gender === "0" ? "男" : "女");
                        $('.J-birthday').text(new Date(parseInt(result.data.birthday)).pattern("yyyy-MM-dd"));
                    } else {
                        console.info('返回个人信息错误');
                    }
                },
                error : function(){
                    console.info('获取个人信息失败');
                }
            });
        }
    })
</script>
</body>
</html>
