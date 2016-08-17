<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>忘记密码</title>
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
    <div id="page-modal" class="page page-apply">
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
                <form action="" method="post" class="J-apply-form">
                    <div class="list-block">
                        <ul>
                            <li>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title label">手机</div>
                                        <div class="item-input">
                                            <input type="number" name="phone" id="phone" class="ui-ipt-text" maxlength="11" oninput="if(value.length>11)value=value.slice(0,11)"  placeholder="请输入手机号码">
                                            <i class="btn-em"></i>
                                        </div>

                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="item-content verification">
                                    <div class="item-inner">
                                        <div class="item-title label">验证码</div>
                                        <div class="item-input">
                                            <div class="row">
                                                <div class="col-50"><input type="number" name="verifyCode" maxlength="6" oninput="if(value.length>6)value=value.slice(0,6)" autocomplete="off" class="ui-ipt-text"   placeholder="请输入验证码"></div>
                                                <div class="col-60"><a href="#" class="button button-light btn-get-code disabled J-send-code">获取验证码</a></div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title label">新密码</div>
                                        <div class="item-input">
                                            <input type="password" name="password" maxlength="6" autocomplete="off" class="ui-ipt-text" placeholder="6位数字密码">
                                            <i class="btn-em"></i>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <div class="err" style="display: none"><i></i><span>账号或密码错误</span></div>
                    </div>
                    <p><a href="javascript:void(0);"class="button button-fill button-big disabled J-btn-submit">确认</a></p>
                </form>
            </div>
        </div>
    </div>

</div>
<script src="http://m.sui.taobao.org/dist/js/sm.js"></script>
<script src="../wechat/src/js/common.js"></script>



<script>
    sessionStorage.isFirstVist= "false";
    $(function(){
        var $applyForm = $('.J-apply-form');
        var $err = $applyForm.find('.err');
        var $applyFormSubmit = $applyForm.find('.J-btn-submit');
        var $btnSendCode = $applyForm.find('.J-send-code');

        $btnSendCode.bind('click',function(){
            if($(this).hasClass('disabled')) return ;
            SmsMessage($("#phone").val())
        });

        $applyForm.find('input').bind('input',function(){
            var me = $(this);
            if(me.length > 0) {
                me.parent().addClass('hasInput');
            } else {
                me.parent().removeClass('hasInput');
            }

            if(validate.phone($('input[name=phone]').val())  && !$btnSendCode.hasClass('counting')) {
                $btnSendCode.removeClass('disabled');
            } else {
                $btnSendCode.addClass('disabled');
            }

            if(validate.phone($('input[name=phone]').val())
                    && validate.password($('input[name=password]').val())
                    &&  validate.password($('input[name=verifyCode]').val())) {
                $applyFormSubmit.removeClass('disabled');
            }else {
                $applyFormSubmit.addClass('disabled');
            }
        });

        $applyForm.find('.btn-em').click(function(){
            var me = $(this);
            me.parent().removeClass('hasInput');
            me.prev().val('');
            $applyFormSubmit.addClass('disabled');
        })

        //表单提交逻辑
        $applyFormSubmit.bind('click', function(){
            var me = $(this);
            //异步submit
            var dataPara = util.getFormJson($applyForm);

            if(me.hasClass('disabled')) return;
            if(!util.getCookie('verifyCode')) {
                console.info('验证码已经过期');
                $err.find('span').text('验证码已经过期');
                $err.show();
                return false;
            }
            $.ajax({
                url: InterFaceUrl.modifyPassword,
                type: 'POST',
                dataType : "json",
                contentType : "application/json; charset=utf-8",
                data: JSON.stringify({
                    phoneNumber : dataPara.phone,
                    passWord : dataPara.password,
                    tenantId : util.getCookie('tenantId'),
                    verifyCode : $('input[name=verifyCode]').val(),
                }),
                dataType : "json",
                success: function(result){
                    console.info(result);
                    if(result.code === '0') {
                        util.setCookie('phone',dataPara.phone,365);
                        util.setCookie('password',util.base64_encode(dataPara.password),365);
                        window.location.href ='./wxTips?msg=' + escape('修改密码成功') + '&time=' + '1000' + '&url=' + 'login'  + '&type=success';
                    } else {
                        console.info('修改失败');
                        $err.find('span').text(result.msg);
                        $err.show();
                    }
                }
            });
            return false;
        });
    })
</script>
</body>
</html>
