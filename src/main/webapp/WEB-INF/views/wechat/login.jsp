<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>绑定会员</title>
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
    <div id="page-modal" class="page page-login">

        <div class="content">
            <div class="ui-card ui-card-s1">
                <div class="ui-card-name">尊享卡</div>
                <div class="ui-card-cnt">
                    <div class="ui-card-thumb"><img src="../wechat/assets/i/card_logo.png" alt=""></div>
                    <div class="ui-card-info">
                        <div class="name">餐道</div>
                        <div class="info">开启智能餐厅时代</div>
                    </div>
                </div>
                <img src="../wechat/assets/i/card.png" class="ui-card-bg" alt="">
            </div>
            <div class="content-padded">
                <form class="J-login-form">
                    <div class="list-block">
                        <ul>
                            <li>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title label">账号</div>
                                        <div class="item-input">
                                            <input type="number" name="name" class="ui-ipt-text"  autocomplete="off" maxlength="20" oninput="if(value.length>20)value=value.slice(0,20)"  placeholder="手机号/会员卡号">
                                            <i class="btn-em"></i>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title label">密码</div>
                                        <div class="item-input">
                                            <input type="password" name="password" class="ui-ipt-text" autocomplete="off"  maxlength="6" placeholder="6位数字密码">
                                            <i class="btn-em"></i>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <div class="err" style="display: none"><i></i><span>账号或密码错误</span></div>
                    </div>
                    <!--<a href="#" class="button button-fill button-big J-">绑定</a>-->
                    <p><a href="javascript:void(0);"class="button button-fill button-big disabled J-btn-submit">绑定</a></p>
                </form>
                <p class="f-tac"><a href="./forgetPassword">忘记密码？</a></p>
            </div>
        </div>
    </div>

</div>
<script src="http://m.sui.taobao.org/dist/js/sm.js"></script>
<script src="../wechat/src/js/common.js"></script>
<script>
    sessionStorage.isFirstVist= "false";
    $(function(){
        var $loginForm = $('.J-login-form');
        var $err = $loginForm.find('.err');
        var $loginFormSubmit = $loginForm.find('.J-btn-submit');

        $loginForm.find('input').bind('input',function(){
            var me = $(this);
            if(me.length > 0) {
                me.parent().addClass('hasInput');
            } else {
                me.parent().removeClass('hasInput');
            }
            validateForm();
        });

        $loginForm.find('.btn-em').click(function(){
            var me = $(this);
            me.parent().removeClass('hasInput');
            me.prev().val('');
            $loginFormSubmit.addClass('disabled');
        })

        //表单提交逻辑
        $loginFormSubmit.bind('click', function(){
        	if($(this).hasClass('disabled')) return false;
            //异步submit
            var dataPara = util.getFormJson($loginForm);
            
            $.ajax({
                url: InterFaceUrl.binding,
                type: 'POST',
                dataType : "json",
                contentType : "application/json; charset=utf-8",
                data: JSON.stringify({
					'phoneNumber' : $('input[name=name]').val(),
					'passWord' : $('input[name=password]').val(),
					  'tenantId' : util.getCookie('tenantId'),
                }),
                success: function(result){
                    if(result.code === '0'){
                    	 util.setCookie('phone',dataPara.name,365);
                        util.setCookie('password',util.base64_encode($('input[name=password]').val()),365);
                           if(result.data.Cardno.length == 12) {
                               util.setCookie('Cardno',result.data.Cardno,365);
                           }

                        $.ajax({
                            url: InterFaceUrl.verifyDefaultInfo,
                            type: 'POST',
                            dataType : "json",
                            contentType : "application/json; charset=utf-8",
                            data: JSON.stringify({
                                tenantId : util.getCookie('tenantId'),
                                cardno : result.data.Cardno
                            }),
                            success: function(result){
                                if(result.data.isDefaultInfo) {//个人信息是否默认信息
                                    window.location.href = './perfectInfo';//完善个人信息
                                } else {
                                    window.location.href = './userCenter';
                                }
                            }
                        });
                    }else{
                    	 $err.find('span').text(result.msg);
                    	 $err.show();
                         
                    }
                }
            });
            return false;
        });

        function validateForm(){
            var dataPara = util.getFormJson($loginForm);
            $err.hide();
            $err.find('span').text('');

            //验证
            if(validate.cardno(dataPara.name) && validate.password(dataPara.password)) {
                $loginFormSubmit.removeClass('disabled');
            } else {
                $loginFormSubmit.addClass('disabled');
            }
        }


    })
</script>
</body>
</html>
