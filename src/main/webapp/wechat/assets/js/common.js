var projectPath=window.location.href.split("/")[3],path="/"+projectPath+"/weChatMember/",InterFaceUrl={findDealDetailByCardno:path+"myRecord",balance:path+"balance",register:path+"register.json",binding:path+"binding.json",getVerifyCode:path+"getVerifyCode.json",getMemberInfo:path+"getMemberInfo.json",modifyPassword:path+"modifyPassword.json",modifyMember:path+"modifyMember.json",verifyDefaultInfo:path+"verifyDefaultInfo.json"},util={ajaxSubmit:function(e,t){},getFormJson:function(e){var t={},n=e.serializeArray();return $.each(n,function(){void 0!==t[this.name]?(t[this.name].push||(t[this.name]=[t[this.name]]),t[this.name].push(this.value||"")):t[this.name]=this.value||""}),t},getRequestName:function(e){var t=new RegExp("(^|&)"+e+"=([^&]*)(&|$)","i"),n=window.location.search.substr(1).match(t);return null!=n?n[2]:null},setCookie:function(e,t,n){var r=new Date;r.setTime(r.getTime()+24*n*60*60*1e3);var o="expires="+r.toUTCString();document.cookie=e+"="+t+"; "+o},getCookie:function(e){for(var t=e+"=",n=document.cookie.split(";"),r=0;r<n.length;r++){for(var o=n[r];" "==o.charAt(0);)o=o.substring(1);if(-1!=o.indexOf(t))return o.substring(t.length,o.length)}return""},delCookie:function(e){this.setCookie(e,"",-1)},base64_encode:function(e){for(var t,n,r,o="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",a=0,i=e.length,s="";i>a;){if(t=255&e.charCodeAt(a++),a==i){s+=o.charAt(t>>2),s+=o.charAt((3&t)<<4),s+="==";break}if(n=e.charCodeAt(a++),a==i){s+=o.charAt(t>>2),s+=o.charAt((3&t)<<4|(240&n)>>4),s+=o.charAt((15&n)<<2),s+="=";break}r=e.charCodeAt(a++),s+=o.charAt(t>>2),s+=o.charAt((3&t)<<4|(240&n)>>4),s+=o.charAt((15&n)<<2|(192&r)>>6),s+=o.charAt(63&r)}return s},base64_decode:function(e){for(var t,n,r,o,a=new Array(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,-1,-1,-1,63,52,53,54,55,56,57,58,59,60,61,-1,-1,-1,-1,-1,-1,-1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,-1,-1,-1,-1,-1,-1,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,-1,-1,-1,-1,-1),i=0,s=e.length,h="";s>i;){do t=a[255&e.charCodeAt(i++)];while(s>i&&-1==t);if(-1==t)break;do n=a[255&e.charCodeAt(i++)];while(s>i&&-1==n);if(-1==n)break;h+=String.fromCharCode(t<<2|(48&n)>>4);do{if(r=255&e.charCodeAt(i++),61==r)return h;r=a[r]}while(s>i&&-1==r);if(-1==r)break;h+=String.fromCharCode((15&n)<<4|(60&r)>>2);do{if(o=255&e.charCodeAt(i++),61==o)return h;o=a[o]}while(s>i&&-1==o);if(-1==o)break;h+=String.fromCharCode((3&r)<<6|o)}return h}};Date.prototype.pattern=function(e){var t={"M+":this.getMonth()+1,"d+":this.getDate(),"h+":this.getHours()%12==0?12:this.getHours()%12,"H+":this.getHours(),"m+":this.getMinutes(),"s+":this.getSeconds(),"q+":Math.floor((this.getMonth()+3)/3),S:this.getMilliseconds()},n={0:"/u65e5",1:"/u4e00",2:"/u4e8c",3:"/u4e09",4:"/u56db",5:"/u4e94",6:"/u516d"};/(y+)/.test(e)&&(e=e.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length))),/(E+)/.test(e)&&(e=e.replace(RegExp.$1,(RegExp.$1.length>1?RegExp.$1.length>2?"/u661f/u671f":"/u5468":"")+n[this.getDay()+""]));for(var r in t)new RegExp("("+r+")").test(e)&&(e=e.replace(RegExp.$1,1==RegExp.$1.length?t[r]:("00"+t[r]).substr((""+t[r]).length)));return e};var validate={phone:function(e){return/^\d+$/.test(e)&&/^0?1[3|4|5|7|8][0-9]\d{8}$/.test(parseInt(e))},cardno:function(e){return/^\d{5,12}$/.test(parseInt(e))},password:function(e){return/^\d+$/.test(e)&&/^\d{6}$/.test(e)},isInt:function(e){return/^\d+$/.test(e)&&/^\+?[1-9][0-9]*$/.test(e)}},SmsMessage=function(e){function t(e){return s.hasClass("disabled")?void 0:(i.hide(),i.find("span").text(""),o=a,validate.phone(e)?(s.addClass("disabled"),s.addClass("counting"),s.text(o+"s后再次获取"),r=window.setInterval(n,1e3),$.ajax({url:InterFaceUrl.getVerifyCode,type:"POST",dataType:"json",contentType:"application/json; charset=utf-8",data:JSON.stringify({phoneNumber:e}),success:function(e){console.info(e),"0"===e.code?util.setCookie("verifyCode",e.data.verifyCode,365):(console.info("获取验证码失败"),i.find("span").text(e.msg),i.show())}}),!1):(i.find("span").text("手机号码错误"),void i.show()))}function n(){0==o?(window.clearInterval(r),s.removeClass("disabled"),s.removeClass("counting"),s.text("重新发送验证码"),util.delCookie("verifyCode")):(o--,s.text(o+"s后再次获取"))}var r,o,a=60,i=$(".err"),s=$(".J-send-code");t(e)};