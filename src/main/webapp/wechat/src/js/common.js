/**
 * Created by goujianjun on 2016/4/17.
 */

var projectPath = window.location.href.split('/')[3];
var path =  '/' + projectPath + '/weChatMember/';
var InterFaceUrl = {
    findDealDetailByCardno : path + 'myRecord',
    balance : path + 'balance',
    register : path + 'register.json',
    binding : path + 'binding.json',
    getVerifyCode : path + 'getVerifyCode.json',
    getMemberInfo : path + 'getMemberInfo.json',
    modifyPassword : path + 'modifyPassword.json',
    modifyMember : path + 'modifyMember.json',
    verifyDefaultInfo : path + 'verifyDefaultInfo.json',
};

var util = {
    ajaxSubmit: function (frm, fn) {

    },
    getFormJson: function (frm) {
        var o = {};
        var a = frm.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    },

    getRequestName : function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
        var r = window.location.search.substr(1).match(reg);
        if (r!=null) return (r[2]); return null;
    },

    setCookie :function(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays*24*60*60*1000));
        var expires = "expires="+d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
    },

    getCookie : function(cname){
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i=0; i<ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1);
            if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
        }
        return "";
    },
    delCookie:  function (name) {
        this.setCookie(name, "", -1);
    },

    /*
     * Javascript base64_encode() base64加密函数
     用于生成字符串对应的base64加密字符串
     * @param string str 原始字符串
     * @return string 加密后的base64字符串
     */
    base64_encode : function(str){
        var c1, c2, c3;
        var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        var i = 0, len= str.length, string = '';

        while (i < len){
            c1 = str.charCodeAt(i++) & 0xff;
            if (i == len){
                string += base64EncodeChars.charAt(c1 >> 2);
                string += base64EncodeChars.charAt((c1 & 0x3) << 4);
                string += "==";
                break;
            }
            c2 = str.charCodeAt(i++);
            if (i == len){
                string += base64EncodeChars.charAt(c1 >> 2);
                string += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
                string += base64EncodeChars.charAt((c2 & 0xF) << 2);
                string += "=";
                break;
            }
            c3 = str.charCodeAt(i++);
            string += base64EncodeChars.charAt(c1 >> 2);
            string += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            string += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
            string += base64EncodeChars.charAt(c3 & 0x3F);
        }
        return string;
    },

    /*
     * Javascript base64_decode() base64解密函数
     用于解密base64加密的字符串
     * @param string str base64加密字符串
     * @return string 解密后的字符串
     */
    base64_decode :function(str){
        var c1, c2, c3, c4;
        var base64DecodeChars = new Array(
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57,
            58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0,  1,  2,  3,  4,  5,  6,
            7,  8,  9,  10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
            25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,
            37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
            -1, -1
        );
        var i=0, len = str.length, string = '';

        while (i < len){
            do{
                c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
            } while (
            i < len && c1 == -1
                );

            if (c1 == -1) break;

            do{
                c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
            } while (
            i < len && c2 == -1
                );

            if (c2 == -1) break;

            string += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));

            do{
                c3 = str.charCodeAt(i++) & 0xff;
                if (c3 == 61)
                    return string;

                c3 = base64DecodeChars[c3];
            } while (
            i < len && c3 == -1
                );

            if (c3 == -1) break;

            string += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));

            do{
                c4 = str.charCodeAt(i++) & 0xff;
                if (c4 == 61) return string;
                c4 = base64DecodeChars[c4];
            } while (
            i < len && c4 == -1
                );

            if (c4 == -1) break;

            string += String.fromCharCode(((c3 & 0x03) << 6) | c4);
        }
        return string;
    }
};


Date.prototype.pattern=function(fmt) {
    var o = {
        "M+" : this.getMonth()+1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
        "H+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S" : this.getMilliseconds() //毫秒
    };
    var week = {
        "0" : "/u65e5",
        "1" : "/u4e00",
        "2" : "/u4e8c",
        "3" : "/u4e09",
        "4" : "/u56db",
        "5" : "/u4e94",
        "6" : "/u516d"
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    if(/(E+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
};

var validate = {
    phone : function(str){
        return /^\d+$/.test(str) && /^0?1[3|4|5|6|7|8|9][0-9]\d{8}$/.test(parseInt(str));
    },
    cardno : function(str){
        return /^\d{5,20}$/.test(parseInt(str));
    },

    password : function(str){
        return /^\d+$/.test(str) &&  /^\d{6}$/.test(str);
    },

    isInt : function(str){
        return /^\d+$/.test(str) &&  /^\+?[1-9][0-9]*$/.test(str);
    }
};

var SmsMessage = function(phoneNumber){
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数
    var code = ""; //验证码

    var $err = $('.err');
    var $btnSendCode = $('.J-send-code');

    function sendMessage(phoneNumber) {
        if($btnSendCode.hasClass('disabled')) return;

        $err.hide();
        $err.find('span').text('');
        curCount = count;

        if(validate.phone(phoneNumber)){
            //产生验证码
//                for (var i = 0; i < codeLength; i++) {
//                    code += parseInt(Math.random() * 9).toString();
//                }
            //设置button效果，开始计时
            $btnSendCode.addClass('disabled');
            $btnSendCode.addClass('counting');
            $btnSendCode.text(curCount + "s后再次获取");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
            //向后台发送处理数据

            $.ajax({
                url: InterFaceUrl.getVerifyCode,
                type: 'POST',
                dataType : "json",
                contentType : "application/json; charset=utf-8",
                data: JSON.stringify({
                    phoneNumber : phoneNumber
                }),
                success: function(data){
                    console.info(data);
                    if(data.code === '0') {
                        util.setCookie('verifyCode',data.data.verifyCode,365);
                    } else {
                        console.info('获取验证码失败');
                        $err.find('span').text(data.msg);
                        $err.show();
                    }
                }
            });
            return false;
        }else{
            $err.find('span').text('手机号码错误');
            $err.show();
        }
    }
    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $btnSendCode.removeClass("disabled");//启用按钮
            $btnSendCode.removeClass('counting');
            $btnSendCode.text("重新发送验证码");
            util.delCookie('verifyCode'); //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
        }
        else {
            curCount--;
            $btnSendCode.text(curCount + "s后再次获取");
        }
    }

    sendMessage(phoneNumber);
};


