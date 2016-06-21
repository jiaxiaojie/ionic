function SetCookie(name,value)
{
var Days = 30; //此 cookie 将被保存 30 天
var exp = new Date();
exp.setTime(exp.getTime() + Days*24*60*60*1000);
document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

function getCookie(name)
{
var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
if(arr != null)
return unescape(arr[2]);
return null;
}

function clearCookie(name) {  
    setCookie(name, "", -1);  
}  