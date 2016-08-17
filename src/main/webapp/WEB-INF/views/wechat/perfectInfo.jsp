<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>完善资料</title>
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

                <form action="" class="J-user-modify-form">
                    <div class="list-block">
                        <ul>
                            <li>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title label">姓名</div>
                                        <div class="item-input">
                                            <input type="text" name="username" class="ui-ipt-text" maxlength="50" placeholder="请输入姓名">
                                            <i class="btn-em"></i>
                                        </div>

                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title label">性别</div>
                                        <div class="item-input">
                                            <div class="button-holder">
                                            <span class="radio-box">
                                                <input type="radio" id="radio-1-2" checked value="0" name="Gender" class="regular-radio"><label for="radio-1-2"><span class="radio-w">男</span></label>
                                            </span>
                                            <span class="radio-box">
                                                <input type="radio" id="radio-1-32" value="1" name="Gender" class="regular-radio"><label for="radio-1-32"><span class="radio-w">女</span></label>
                                            </span>

                                            </div>
                                            <i class="btn-em"></i>
                                        </div>

                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title label">生日</div>
                                        <div class="item-input">
                                            <!--<input type="text" name="name" class="ui-ipt-text J-date-pick" id="datetime-picker" data-toggle='date'  placeholder="请选择生日">-->
                                            <input type="text" name="Birthday" placeholder="请选择日期" id="datetime-picker" readonly="">
                                            <i class="btn-em"></i>
                                        </div>

                                    </div>
                                </div>
                            </li>
                        </ul>
                        <div class="err" style="display: none"><i></i><span>账号或密码错误</span></div>
                    </div>

                    <p><a href="javascript:void(0);"class="button button-fill button-big disabled J-btn-submit">保存</a></p>
                </form>
            </div>

        </div>
    </div>

</div>
<script src="../wechat/assets/js/sm.js"></script>
<script src="../wechat/src/js/common.js"></script>
<script>
    sessionStorage.isFirstVist= "false";
    $(function(){

        //禁止返回
        window.history.forward(1);


    	  var $userForm = $('.J-user-modify-form');
          var $err = $userForm.find('.err');
          var $userFormSubmit = $userForm.find('.J-btn-submit');
        var cardNo =  util.getCookie('Cardno');

        $userForm.find('input').bind('input blur change keyup keydown',function(){
            var me = $(this);
            if(me.length > 0) {
                me.parent().addClass('hasInput');
            } else {
                me.parent().removeClass('hasInput');
            }


            if($('input[name=username]').val().length > 0
                    &&  $('input[name=Birthday]').val().length > 0) {
                $userFormSubmit.removeClass('disabled');
            }
        });

        $userForm.find('.btn-em').click(function(){
            var me = $(this);
            me.parent().removeClass('hasInput');
            me.prev().val('');
            $userFormSubmit.addClass('disabled');
        })

        $userFormSubmit.bind('click',function(){
            if($(this).hasClass('disabled')) return false;
            $.ajax({
                url: InterFaceUrl.modifyMember,
                type: 'POST',
                dataType : "json",
                contentType : "application/json; charset=utf-8",
                data: JSON.stringify({
                    Id : '',
                    cardno : cardNo,
                    Name : $('input[name=username]').val(),
                    Gender :  $("input[name='Gender']:checked").val(),
                    Birthday : $('input[name=Birthday]').val(),
                }),
                success: function(result){
                    console.info(result);
                    if(result.code === '0') {
                        console.info('修改成功');
                        window.location.href="./userCenter";
                    } else {
                        console.info('修改失败');
                        $err.find('span').text(result.msg);
                        $err.show();
                    }
                }
            });
        });


          
	        $(document).on("pageInit", "#page-user", function(e) {
                var maxDate = new Date().pattern('yyyy-MM-dd');
	            $("#datetime-picker").datetimePicker({
                    value: ['1990', '01', '01'],
                    maxDate : maxDate,
                    cssClass : "datePicker",
                    onClose : function(){
                        $('input[name=Birthday]').change();
                    }
	            });
	        });



        $.init();
    })
</script>
</body>
</html>
