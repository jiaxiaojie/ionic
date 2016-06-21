<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head_for_front.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctxStatic}/modules/front/images/util/favicon.ico" rel="shortcut icon">
<link href="${ctxStatic}/bootstrap/2.3.2/css_cerulean/bootstrap.css" rel="stylesheet"/>
<link href="${ctxStatic}/modules/front/css/util/util.css" rel="stylesheet" />

<title>超值春游攻略，让你嗨翻天!</title>
<style type="text/css">
body{ background-color:#51ae56;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto; color:#fff;}
.spring-banner{ min-width:1000px; height:476px; background:url(${ctxStatic}/modules/front/images/activity/spring/spring-banner.jpg) no-repeat center top;}
.spring-cloud-03{ position:absolute; top:20px; left:105px; z-index:1; -webkit-animation: cloud-03 5s linear infinite backwards;-moz-animation: cloud-03 5s linear infinite backwards;-ms-animation: cloud-03 5s linear infinite backwards;-o-animation: cloud-03 5s linear infinite backwards;animation: cloud-03 5s linear infinite backwards;}
@-webkit-keyframes cloud-03{
	0{left: 105px;}
	50%{left: 185px;}
	100%{left: 105px;}
}
@-moz-keyframes cloud-03{
	0{left: 105px;}
	50%{left: 185px;}
	100%{left: 105px;}
}
@-ms-keyframes cloud-03{
	0{left: 105px;}
	50%{left: 185px;}
	100%{left: 105px;}
}
@-o-keyframes cloud-03{
	0{left: 105px;}
	50%{left: 185px;}
	100%{left: 105px;}
}
@keyframes cloud-03{
	0{left: 105px;}
	50%{left: 185px;}
	100%{left: 105px;}
}
.spring-cloud-02{ position:absolute; top:226px; left:240px; z-index:1;-webkit-animation: cloud-02 5s linear infinite backwards;	-moz-animation: cloud-02 5s linear infinite backwards;-ms-animation: cloud-02 5s linear infinite backwards;	-o-animation: cloud-02 5s linear infinite backwards;animation: cloud-02 5s linear infinite backwards;}
@-webkit-keyframes cloud-02{
	0{left: 240px;}
	50%{left: 185px;}
	100%{left: 240px;}
}
@-moz-keyframes cloud-02{
	0{left: 240px;}
	50%{left: 185px;}
	100%{left: 240px;}
}
@-ms-keyframes cloud-02{
	0{left: 240px;}
	50%{left: 185px;}
	100%{left: 240px;}
}
@-o-keyframes cloud-02{
	0{left: 240px;}
	50%{left: 185px;}
	100%{left: 240px;}
}
@keyframes cloud-02{
	0{left: 240px;}
	50%{left: 185px;}
	100%{left: 240px;}
}
.spring-cloud-01{ position:absolute; top:45px; left:845px; z-index:1;-webkit-animation: cloud-01 5s linear infinite backwards;-moz-animation: cloud-01 5s linear infinite backwards;-ms-animation: cloud-01 5s linear infinite backwards;-o-animation: cloud-01 5s linear infinite backwards;	animation: cloud-01 5s linear infinite backwards;}
@-webkit-keyframes cloud-01{
	0{left: 845px;}
	50%{left: 800px;}
	100%{left: 845px;}
}
@-moz-keyframes cloud-01{
	0{left: 845px;}
	50%{left: 800px;}
	100%{left: 845px;}
}
@-ms-keyframes cloud-01{
	0{left: 845px;}
	50%{left: 800px;}
	100%{left: 845px;}
}
@-o-keyframes cloud-01{
	0{left: 845px;}
	50%{left: 800px;}
	100%{left: 845px;}
}
@keyframes cloud-01{
	0{left: 845px;}
	50%{left: 800px;}
	100%{left: 845px;}
}
.spring-slogan{ position:absolute; top:43px; left:268px; width:464px; height:388px; z-index:2;-webkit-animation: slogan 1s .2s ease both;-moz-animation: slogan 1s .2s ease both;-ms-animation: slogan 1s .2s ease both;-o-animation: slogan 1s .2s ease both;animation: slogan 1s .2s ease both;}
@-webkit-keyframes slogan{
0%{opacity:0;-webkit-transform:scale(.3);-moz-transform: scale(.3);-ms-transform: scale(.3);-o-transform: scale(.3);transform: scale(.3);}
50%{opacity:1;-webkit-transform:scale(1.05);-moz-transform: scale(1.05);-ms-transform: scale(1.05);-o-transform: scale(1.05);transform: scale(1.05);}
70%{-webkit-transform:scale(.9);-moz-transform: scale(.9);-ms-transform: scale(.9);-o-transform: scale(.9);transform: scale(.9);}
100%{-webkit-transform:scale(1);-moz-transform: scale(1);-ms-transform: scale(1);-o-transform: scale(1);transform: scale(1);}
}
@-moz-keyframes slogan{
0%{opacity:0;-webkit-transform:scale(.3);-moz-transform: scale(.3);-ms-transform: scale(.3);-o-transform: scale(.3);transform: scale(.3);}
50%{opacity:1;-webkit-transform:scale(1.05);-moz-transform: scale(1.05);-ms-transform: scale(1.05);-o-transform: scale(1.05);transform: scale(1.05);}
70%{-webkit-transform:scale(.9);-moz-transform: scale(.9);-ms-transform: scale(.9);-o-transform: scale(.9);transform: scale(.9);}
100%{-webkit-transform:scale(1);-moz-transform: scale(1);-ms-transform: scale(1);-o-transform: scale(1);transform: scale(1);}
}
.spring-balloon{ position:absolute; top:208px; left:860px; z-index:1;-webkit-animation:balloon 3s linear infinite backwards;-moz-animation: balloon 3s linear infinite backwards;-ms-animation: balloon 3s linear infinite backwards;-o-animation: balloon 3s linear infinite backwards; animation: balloon 3s linear infinite backwards;}
@-webkit-keyframes balloon{
   0%{top:208px}
   50%{top:230px}
   100%{top:208px}
}
@-moz-keyframes balloon{
   0%{top:208px}
   50%{top:230px}
   100%{top:208px}
}
@-ms-keyframes balloon{
   0%{top:208px}
   50%{top:230px}
   100%{top:208px}
}
@-o-keyframes balloon{
   0%{top:208px}
   50%{top:230px}
   100%{top:208px}
}
@keyframes balloon{
   0%{top:208px}
   50%{top:230px}
   100%{top:208px}
}
.spring-airship{ position:absolute; top:217px; left:77px; z-index:1;-webkit-animation:airship 3s linear infinite backwards;	-moz-animation: airship 3s linear infinite backwards;-ms-animation: airship 3s linear infinite backwards;-o-animation: airship 3s linear infinite backwards; animation: airship 3s linear infinite backwards;}
@-webkit-keyframes airship{
   0%{top:217px}
   50%{top:230px}
   100%{top:217px}
}
@-moz-keyframes airship{
   0%{top:217px}
   50%{top:230px}
   100%{top:217px}
}
@-ms-keyframes airship{
   0%{top:217px}
   50%{top:230px}
   100%{top:217px}
}
@-o-keyframes airship{
   0%{top:217px}
   50%{top:230px}
   100%{top:217px}
}
@keyframes airship{
   0%{top:217px}
   50%{top:230px}
   100%{top:217px}
}
.spring-one{ min-width:1000px; height:240px; background:url(${ctxStatic}/modules/front/images/activity/spring/spring-bg-01.jpg) no-repeat center top;}
.spring-one .so-rule{ padding:138px 0 20px 109px; font-size:14px;}
.spring-one .so-my{ padding:0 0 0 266px;}
.spring-one .so-my p{ float:left;}
.spring-one .so-my .so-invest{ width:150px; margin-top:7px; margin-right:177px;}
.spring-one .so-my .so-invest span{ padding:2px 5px; display:inline-block; background-color:#42843d; font-size:16px; color:#ffc702;}
.spring-one .so-my .so-receive{ width:110px; margin-top:7px;}
.spring-one .so-my .so-receive span{ padding:2px 5px; display:inline-block; background-color:#42843d; font-size:16px; color:#ffc702;}
.spring-one .so-my .so-login{ margin-top:9px; margin-right:20px;}
.spring-one .so-my .so-login a{ font-size:16px; color:#fff; text-decoration:underline;}
.btn-gold-l a{ width:174px; height:40px; line-height:40px; font-size:16px; text-align:center; display:inline-block; color:#331d00; background:url(${ctxStatic}/modules/front/images/activity/spring/btn-gold-l.png) no-repeat;}
.spring-two{ min-width:1000px; height:720px; background:url(${ctxStatic}/modules/front/images/activity/spring/spring-bg-02.jpg) no-repeat center top;}
.spring-two .card-item{ position:relative; margin-right:20px; float:left; width:158px; height:184px; border-radius:8px; -o-border-radius:8px; -ms-border-radius:8px; -moz-border-radius:8px; -webkit-border-radius:8px; transition:all 0.4s ease-in-out;	-webkit-transition:all 0.4s ease-in-out; -moz-transition:all 0.4s ease-in-out;	-o-transition:all 0.4s ease-in-out;}
.spring-two .card-item:hover{transform:translate(0,-10px); -webkit-transform:translate(0,-10px); -moz-transform:translate(0,-10px);	-o-transform:translate(0,-10px);
 -ms-transform:translate(0,-10px);}
.card-item .icon-signage{ position:absolute; top:-19px; left:50%; margin-left:-20px; width:40px; height:48px; background:url(${ctxStatic}/modules/front/images/activity/spring/icon-signage-gray.png) no-repeat; z-index:3;}
.card-item .icon-triangle{ position:absolute; top:2px; left:2px; width:46px; height:46px; line-height:26px; font-size:16px; background:url(${ctxStatic}/modules/front/images/activity/spring/icon-triangle-gold.png) no-repeat; z-index:3;}
.card-item .icon-triangle i{ margin-left:5px;}
.card-item .card-light{ position:relative; width:154px; height:180px; overflow:hidden; border:2px solid #ffd954; background:#fff url(${ctxStatic}/modules/front/images/activity/spring/bg-light.png) no-repeat center center; border-radius:8px; -o-border-radius:8px; -ms-border-radius:8px; -moz-border-radius:8px; -webkit-border-radius:8px; z-index:2;}
.card-item .card-content{ position:absolute; top:0; left:0; width:158px; height:184px; z-index:5; text-align:center;}
.card-item .card-title{ padding:40px 0 15px 0; font-size:16px; color:#4c2700;}
.card-item .card-money{ margin:0 auto; width:120px; height:37px; line-height:37px; text-align:center; color:#f00; font-size:20px; text-shadow:0px 1px 1px #ffc600; background:url(${ctxStatic}/modules/front/images/activity/spring/bg-line.png) no-repeat;}
.card-item .card-btn{ margin:15px auto 0 auto;}
.card-item .card-btn a{ width:120px; height:38px; line-height:38px; text-align:center; display:inline-block; font-size:14px; color:#4c2700; background-color:#d0d0d0; border-radius:4px; -o-border-radius:4px; -ms-border-radius:4px; -moz-border-radius:4px; -webkit-border-radius:4px;-webkit-animation: card-btn 3s 0s infinite;-moz-animation: card-btn 3s 0s infinite;-ms-animation: card-btn 3s 0s infinite;	-o-animation: card-btn 3s 0s infinite;animation: card-btn 3s 0s infinite;}
@-webkit-keyframes card-btn{
	0%,100%{ -webkit-transform:translate(0,0) rotate(0);}
	15%{ -webkit-transform: translate(-5%,0) rotate(-3deg);}
	30%{ -webkit-transform: translate(5%,0) rotate(3deg);}
	45%{ -webkit-transform: translate(-5%,0) rotate(-3deg);}
	60%{ -webkit-transform: translate(5%,0) rotate(1deg);}
	75%{ -webkit-transform: translate(-5%,0) rotate(-1deg);}
}
@-moz-keyframes card-btn{
	0%,100%{ -moz-transform:translate(0,0) rotate(0);}
	15%{ -moz-transform: translate(-5%,0) rotate(-3deg);}
	30%{ -moz-transform: translate(5%,0) rotate(3deg);}
	45%{ -moz-transform: translate(-5%,0) rotate(-3deg);}
	60%{ -moz-transform: translate(5%,0) rotate(1deg);}
	75%{ -moz-transform: translate(-5%,0) rotate(-1deg);}
}
@-ms-keyframes card-btn{
	0%,100%{ -ms-transform:translate(0,0) rotate(0);}
	15%{ -ms-transform: translate(-5%,0) rotate(-3deg);}
	30%{ -ms-transform: translate(5%,0) rotate(3deg);}
	45%{ -ms-transform: translate(-5%,0) rotate(-3deg);}
	60%{ -ms-transform: translate(5%,0) rotate(1deg);}
	75%{ -ms-transform: translate(-5%,0) rotate(-1deg);}
}
@-o-keyframes card-btn{
	0%,100%{ -o-transform:translate(0,0) rotate(0);}
	15%{ -o-transform: translate(-5%,0) rotate(-3deg);}
	30%{ -o-transform: translate(5%,0) rotate(3deg);}
	45%{ -o-transform: translate(-5%,0) rotate(-3deg);}
	60%{ -o-transform: translate(5%,0) rotate(1deg);}
	75%{ -o-transform: translate(-5%,0) rotate(-1deg);}
}
@keyframes card-btn{
	0%,100%{ transform:translate(0,0) rotate(0);}
	15%{ transform: translate(-5%,0) rotate(-3deg);}
	30%{ transform: translate(5%,0) rotate(3deg);}
	45%{ transform: translate(-5%,0) rotate(-3deg);}
	60%{ transform: translate(5%,0) rotate(1deg);}
	75%{ transform: translate(-5%,0) rotate(-1deg);}
}
.card-item .card-btn a:hover{
	-webkit-animation: card-btn3 3s 0s infinite;
	-moz-animation: card-btn3 3s 0s infinite;
	-ms-animation: card-btn3 3s 0s infinite;
	-o-animation: card-btn3 3s 0s infinite;
	animation: card-btn3 3s 0s infinite;
}
@-webkit-keyframes card-btn3{
	0%,100%{ -webkit-transform:translate(0,0) rotate(0);}
	15%{ -webkit-transform:translate(0,0) rotate(0);}
	30%{ -webkit-transform:translate(0,0) rotate(0);}
	45%{ -webkit-transform:translate(0,0) rotate(0);}
	60%{ -webkit-transform:translate(0,0) rotate(0);}
	75%{ -webkit-transform:translate(0,0) rotate(0);}
}
.card-item .card-btn a i{ color:#f00;}
.card-item .card-shadow{ position:absolute; bottom:-7px; left:-6px; width:170px; height:13px; background:url(${ctxStatic}/modules/front/images/activity/spring/bg-shadow.png) no-repeat;}
.card-item.card-one{ position:absolute; top:50px; left:10px; transform:rotate(-4deg);-o-transform:rotate(-4deg);-ms-transform:rotate(-4deg);-moz-transform:rotate(-4deg);-webkit-transform:rotate(-4deg);}
.card-item.card-two{ position:absolute; top:80px; left:214px; transform:rotate(2deg);-o-transform:rotate(2deg);-ms-transform:rotate(2deg);-moz-transform:rotate(2deg);-webkit-transform:rotate(2deg);}
.card-item.card-three{ position:absolute; top:40px; left:420px; transform:rotate(-3deg);-o-transform:rotate(-3deg);-ms-transform:rotate(-3deg);-moz-transform:rotate(-3deg);-webkit-transform:rotate(-3deg);}
.card-item.card-four{ position:absolute; top:80px; left:628px; transform:rotate(3deg);-o-transform:rotate(3deg);-ms-transform:rotate(3deg);-moz-transform:rotate(3deg);-webkit-transform:rotate(3deg);}
.card-item.card-five{ position:absolute; top:64px; left:830px; transform:rotate(-3deg);-o-transform:rotate(-3deg);-ms-transform:rotate(-3deg);-moz-transform:rotate(-3deg);-webkit-transform:rotate(-3deg);}
.card-item.card-six{ position:absolute; top:430px; left:10px; transform:rotate(3deg);-o-transform:rotate(3deg);-ms-transform:rotate(3deg);-moz-transform:rotate(3deg);-webkit-transform:rotate(3deg);}
.card-item.card-seven{ position:absolute; top:390px; left:214px; transform:rotate(-2deg);-o-transform:rotate(-2deg);-ms-transform:rotate(-2deg);-moz-transform:rotate(2deg);-webkit-transform:rotate(-2deg);}
.card-item.card-eight{ position:absolute; top:470px; left:420px; transform:rotate(4deg);-o-transform:rotate(4deg);-ms-transform:rotate(4deg);-moz-transform:rotate(4deg);-webkit-transform:rotate(4deg);}
.card-item.card-nine{ position:absolute; top:416px; left:628px; transform:rotate(-2deg);-o-transform:rotate(-2deg);-ms-transform:rotate(-2deg);-moz-transform:rotate(-2deg);-webkit-transform:rotate(-2deg);}
.card-item.card-ten{ position:absolute; top:447px; left:830px; transform:rotate(3deg);-o-transform:rotate(3deg);-ms-transform:rotate(3deg);-moz-transform:rotate(3deg);-webkit-transform:rotate(3deg);}
.card-item.card-ten .icon-triangle{ background:url(${ctxStatic}/modules/front/images/activity/spring/icon-triangle-red.png) no-repeat;}
.card-item.card-ten .card-light{ border:2px solid #f00;}
.card-item.received .card-btn a{ background-color:#d0d0d0;-webkit-animation:card-btn2 3s linear infinite backwards;	-moz-animation: card-btn2 3s linear infinite backwards;
-ms-animation: card-btn2 3s linear infinite backwards;-o-animation: card-btn2 3s linear infinite backwards; animation: card-btn2 3s linear infinite backwards;}
@-webkit-keyframes card-btn2{
	0%,100%{ -webkit-transform:translate(0,0) rotate(0);}
	15%{ -webkit-transform:translate(0,0) rotate(0);}
	30%{ -webkit-transform:translate(0,0) rotate(0);}
	45%{ -webkit-transform:translate(0,0) rotate(0);}
	60%{ -webkit-transform:translate(0,0) rotate(0);}
	75%{ -webkit-transform:translate(0,0) rotate(0);}
}
.card-item.received .icon-signage{ background:url(${ctxStatic}/modules/front/images/activity/spring/icon-signage-gold.png) no-repeat;}
.card-item.receive .card-btn a{ background-color:#ffc600;}
.card-item.receive .icon-signage{ background:url(${ctxStatic}/modules/front/images/activity/spring/icon-signage-gold.png) no-repeat;}
.spring-three{ min-width:1000px; height:240px; background:url(${ctxStatic}/modules/front/images/activity/spring/spring-bg-03.jpg) no-repeat center top;}
.spring-three .fl{ padding:197px 0 0 160px; font-size:14px;}
.spring-three .fr{ padding:192px 39px 0 0;}
.spring-four{ min-width:1000px; height:340px; background:url(${ctxStatic}/modules/front/images/activity/spring/spring-bg-04.jpg) no-repeat center top;}
.spring-four p{ padding:250px 0 0 80px;}
.spring-four .btn-gold-s{ margin-right:76px; width:150px; height:38px; line-height:38px; text-align:center; color:#4c2700; font-size:16px; display:inline-block; background-color:#ffc600; border-radius:5px; -o-border-radius:5px; -ms-border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px;}
.spring-five{ min-width:1000px; height:658px; background:url(${ctxStatic}/modules/front/images/activity/spring/spring-bg-05.jpg) no-repeat center top;}
.spring-five ul{ padding:140px 140px 20px 144px; font-size:14px;}
.spring-five ul li{ margin-bottom:10px;}
.spring-five ul li span{ float:left; display: inline-block; margin-right:5px; width:24px; height:24px; line-height:24px; text-align:center; background-color:#74c663; border-radius:24px; -o-border-radius:24px; -ms-border-radius:24px; -moz-border-radius:24px; -webkit-border-radius:24px;}
.spring-five ul li b{ float:left; width:670px; font-weight:normal;}
.spring-five p{ position:relative; width:1000px; height:40px; font-size:14px; text-align:center;}
.spring-five p:after{ position:absolute; top:20px; left:144px; content:""; display:block; width:176px; height:1px; background-color:#439c48;}
.spring-five p:before{ position:absolute; top:20px; right:144px; content:""; display:block; width:176px; height:1px; background-color:#439c48;}
/*pop up window*/
.popup-bg{ position:fixed; top:0; left:0; width:100%; height:100%; background:url(${ctxStatic}/modules/front/images/util/bg_transparent.png) repeat; z-index:99;}
.popup-bg .popup-main{ position:absolute; top:50%; left:50%; margin-top:-100px; margin-left:-200px; width:400px; height:200px; background:#fff; border:2px solid #ffc600; border-radius:8px; -o-border-radius:8px; -ms-border-radius:8px; -moz-border-radius:8px; -webkit-border-radius:8px;}
.popup-bg .popup-colse{ position:absolute; top:0; right:0; width:46px; height:46px; background:url(${ctxStatic}/modules/front/images/activity/spring/icon-triangle-gold.png) no-repeat;-webkit-transform: rotateY(180deg);}
.popup-bg .popup-colse:before{ position:absolute; top:15px; right:26px; content: ''; width:16px; height:3px; margin-top:-2px; background:#fff; border-radius:5px;  -webkit-border-radius:5px; -webkit-transform:rotate(45deg); -moz-transform:rotate(45deg); -ms-transform:rotate(45deg); -o-transform:rotate(45deg);transform:rotate(45deg);}
.popup-bg .popup-colse:after{ position:absolute; top:15px; right:26px; content: ''; width:16px; height:3px; margin-top:-2px; background:#fff; border-radius:5px; -webkit-border-radius:5px; -webkit-transform: rotate(-45deg); -moz-transform: rotate(-45deg); -ms-transform: rotate(-45deg); -o-transform: rotate(-45deg);transform: rotate(-45deg);}
.poput-content .pupup-text{ margin:40px auto 30px auto; width:240px; display:block; font-size:16px; color:#4c2700; line-height:32px; text-align:center;}
.poput-content .pupup-btn{ margin:0 auto; width:140px; height:38px; line-height:38px; font-size:16px; display:block; text-align:center; color:#4c2700; background:#ffc600; border-radius:4px; -webkit-border-radius:4px;}
/*countdown content*/
.countdown-bg{ height:924px; background:url(${ctxStatic}/modules/front/images/activity/spring/spring-countdown-bg.jpg) no-repeat;}
.countdown-area{ position:absolute; left:50%; top:482px; margin-left:-230px; width:460px; height:388px; background-color:#fff; border:2px solid #000; border-radius:10px; -o-border-radius:10px; -ms-border-radius:10px; -moz-border-radius:10px; -webkit-border-radius:10px;}
.countdown-title{ margin:19px auto 0 auto; padding:55px 42px 0; width:320px; height:64px; line-height:64px; text-align:center; font-size:28px; color:#fff; background:url(${ctxStatic}/modules/front/images/activity/countdown/countdown-title-bg.png) no-repeat;}
.countdown-dividingline{ margin:24px auto; width:318px; height:24px; background:url(${ctxStatic}/modules/front/images/activity/countdown/countdown-dividingline.png) no-repeat;}
.countdown-time{ margin:0 auto; width:410px; height:92px;}
.countdown-time span{ position:relative; float:left; margin-right:14px; width:92px; height:92px; line-height:92px; font-size:48px; font-family:Arial, Helvetica, sans-serif; display:block; text-align:center; color:#fff; background:#000; border-radius:5px; -o-border-radius:5px; -ms-border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px;
background: -webkit-linear-gradient(#333, #000, #333); background: -o-linear-gradient(#333, #000, #333); background: -moz-linear-gradient(#333, #000, #333); background: linear-gradient(#333, #000, #333);}
.countdown-time span:before{ position:absolute; top:45px; left:0; content:""; display:block; width:92px; height:1px; background-color:#333;}
.countdown-time span:after{ position:absolute; top:46px; left:0; content:""; display:block; width:92px; height:1px; background-color:#eee;}
.countdown-time span.second{ margin-right:0;}
.countdown-text{ padding:20px 0 0 23px;}
.countdown-text span{ margin-right:14px; width:92px; height:28px; line-height:28px; font-size:24px; color:#000; text-align:center; display:inline-block;}
</style>
<script type="text/javascript">
function closePopup() {
	$(".popup-bg").css("display", "none");
}
function openPopup() {
	$(".popup-bg").css("display", "block");
}
function openPrompt(title, btnTitle,btnUrl){
	btnTitle = (btnTitle==null?"关闭":btnTitle);
	btnUrl = (btnUrl==null?"javascript:closePopup();":btnUrl);
	$("#promptTitle").text(title);
    $("#promptBtn").text(btnTitle);
    $("#promptBtn").attr("href", btnUrl);
    openPopup();
}

	function refreshPage(){
		 $.ajax({
			   type: "POST",
			   url: "${ctxFront}/activity/aprilSpringOutingInfo",
			   dataType : 'json',
			   success: function(data){
				   var receiveAmount = data.receiveAmount;
				   $(".so-receive").find("span").text(receiveAmount +"元");
			   }
			});
	}

	function toReceiveAward(code,obj) {
		 
		  
		 
		  
		  $.ajax({
			   type: "POST",
			   url: "${ctxFront}/activity/aprilSpringOutingReceiveAward?receiveAwardCode="+code,
			   dataType : 'json',
			   success: function(msg){
				   //alert(JSON.stringify(msg)  );
				  
				   if(msg.code == 0){
				    	 var resultCode = msg.data.resultCode;
				    	 
				    	 if(resultCode==-1){
				    		 openPrompt(msg.data.result, "去登录","${ctxFront}/login");
				    	 }
				    	 else if(resultCode==0){
				    		 openPrompt(msg.data.result, "马上投资","${ctxFront}/wytz");
				    	 }
				    	 else {
				    		 openPrompt(msg.data.result);
				    		 if(resultCode==2){
				    			 
				    			 var mydiv = $(obj).parent().parent().parent();
				    			
				    			 mydiv.removeClass("receive");
				    			 mydiv.addClass("received");
				    			 refreshPage();
					    	 }
				    	 }
				     }
				     else{
					     openPrompt("操作失败！");
				     }
			   }
			});
		 
		  
	}



</script>
</head>
<body>
<div class="activity-header">
	<div class="display-area clearfix">
    	<div class="header-logo pull-left">
        	<a href="${ctxFront}/index">
				<img id="logo" class="logo" src="${ctxStatic}/modules/front/images/util/logo.png">
        	</a>
        </div>
    	<div class="header-btn pull-right">
        	<a href="${ctxFront}/index">进入首页</a>
        </div>
    </div>
</div>










<!-- 
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"超值春游攻略，玩嗨皮！","bdDesc":"踏春出游正当时，来花生金服领旅行经费，最高可领3888元，户外潮流尖货等你来挑！","bdMini":"1","bdMiniList":["qzone","tsina","weixin","tqq","copy"],"bdPic":"http://139.196.5.141:8080/fd/static/modules/front/images/activity/spring/spring-countdown-bg.jpg","bdStyle":"1","bdSize":"24"},"slide":{"type":"slide","bdImg":"1","bdPos":"right","bdTop":"192.5"}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
 -->







<script type="text/javascript">
$(function(){//2016/03/26 00:00:00 
	countDown("${fns:format(activityInfo.marketingActivityInfo.beginDate,'yyyy/MM/dd HH:mm:ss')}","#countdown .day","#countdown .hour","#countdown .minute","#countdown .second");
});

function countDown(time,day_elem,hour_elem,minute_elem,second_elem){
	//if(typeof end_time == "string")
	var end_time = new Date(time).getTime(),//月份是实际月份-1
	//current_time = new Date().getTime(),
	sys_second = (end_time-new Date("${fns:getDate('yyyy/MM/dd HH:mm:ss')}").getTime())/1000;
	
	var timer = setInterval(function(){
		if (sys_second > 0) {
			sys_second -= 1;
			var day = Math.floor((sys_second / 3600) / 24);
			var hour = Math.floor((sys_second / 3600) % 24);
			var minute = Math.floor((sys_second / 60) % 60);
			var second = Math.floor(sys_second % 60);
			day_elem && $(day_elem).text(day);//计算天
			$(hour_elem).text(hour<10?"0"+hour:hour);//计算小时
			$(minute_elem).text(minute<10?"0"+minute:minute);//计算分
			$(second_elem).text(second<10?"0"+second:second);// 计算秒
			
			if(day ==0 && hour == 0 && minute == 0 && second == 0){
				window.location.reload(true);
			}
		} else { 
			clearInterval(timer);
		}
	}, 1000);
}
</script>
<!--countdown content-->
<c:if test="${!activityInfo.activityBegin }">


<div class="countdown-bg" style="display:block;">
	<div class="display-area">
    	<div class="countdown-area">
        	<div class="countdown-title">距离活动开始还有</div>
        	<div class="countdown-dividingline"></div>
        	<div id="countdown" class="countdown-time clearfix">
            	<span class="day">-</span>
                <span class="hour">-</span>
                <span class="minute">-</span>
                <span class="second">-</span>
            </div>
            <div class="countdown-text">
            	<span>天</span>
            	<span>时</span>
            	<span>分</span>
            	<span>秒</span>
            </div>
        </div>
    </div>
	
</div>
</c:if>
<c:if test="${activityInfo.activityBegin }">
<!--pop up window-->
<div id="huodongMain" style="display:block;">
<div class="popup-bg" style="display:none;">
	<div class="popup-main">
    	<a href="javascript:closePopup();" class="popup-colse"></a>
    	<div class="poput-content">
        	<p class="pupup-text" id="promptTitle">您当前累计投资额度暂未达标先去投资，过会再来领吧</p>
            <a href="#" class="pupup-btn" id="promptBtn">马上投资</a>
        </div>
    </div>
</div>
<!--main-->
<div class="main">
    <!--main content-->
    <div class="main-content">
        <div class="spring-banner">
        	<div class="display-area">
                <img src="${ctxStatic}/modules/front/images/activity/spring/spring-cloud-03.png" class="spring-cloud-03">
                <img src="${ctxStatic}/modules/front/images/activity/spring/spring-cloud-02.png" class="spring-cloud-02">
                <img src="${ctxStatic}/modules/front/images/activity/spring/spring-cloud-01.png" class="spring-cloud-01">
                <img src="${ctxStatic}/modules/front/images/activity/spring/spring-balloon.png" class="spring-balloon">
                <img src="${ctxStatic}/modules/front/images/activity/spring/spring-airship.png" class="spring-airship">
                <img src="${ctxStatic}/modules/front/images/activity/spring/spring-slogan.png" class="spring-slogan">
            </div>
       	</div>        
        <div class="spring-one">
        	<div class="display-area">
           	  	<div class="so-rule">领取规则：活动期间，累计投资额达到相应额度，即可领取对应的旅行经费，最高可领取3888元现金。（活花生、新花生不参与本活动）</div>
                <div class="so-my clearfix">
                	<p class="so-invest"><span><fmt:formatNumber value="${activityInfo.investAmount==null?0:activityInfo.investAmount}" pattern="#0.##" />元</span></p>
                	<p class="so-receive"><span><fmt:formatNumber value="${activityInfo.receiveAmount==null?0:activityInfo.receiveAmount}" pattern="#0.##" />元</span></p>
                	<c:if test="${empty p2p:getPrincipal()}">
                	<p class="so-login"><a href="${ctxFront}/login">登录查看</a></p>
                	</c:if>
                	<p class="btn-gold-l"><a href="${ctxFront}/wytz">去投资，领更多经费</a></p>
                </div>
          </div>
        </div>        
        <div class="spring-two">
        <!-- 
        	<c:forEach items="${activityInfo.count}" var="countItem">
        		${countItem  }
        	</c:forEach><br>
        	<c:forEach items="${activityInfo.used}" var="countItem">
        		${countItem  }
        	</c:forEach><br>
        	<c:forEach items="${activityInfo.over}" var="countItem">
        		${countItem  }
        	</c:forEach>
        	 -->
        	<div class="display-area">
            	<div class="card-item card-one ${activityInfo.count[0]==1?(activityInfo.over[0]==1?'receive':'received'):''}"><!--class加上“received”的为已领取状态-->
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>1</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满 </p>
                        <div class="card-money">1千</div>
                        <div class="card-btn"><a href="javascript:return false;" onclick="javascript:toReceiveAward(1,this);"><c:if test="${activityInfo.used[0]==1 }">已</c:if>领<i>2000</i>花生豆</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            	<div class="card-item card-two ${activityInfo.count[1]==1?(activityInfo.over[1]==1?'receive':'received'):''}"><!--class加上“receive”的为可领取状态-->
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>2</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满</p>
                        <div class="card-money">5千</div>
                        <div class="card-btn"><a  href="javascript:return false;"  onclick="javascript:toReceiveAward(2,this);"><c:if test="${activityInfo.used[1]==1 }">已</c:if>领<i>10000</i>花生豆</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            	<div class="card-item card-three ${activityInfo.count[2]==1?(activityInfo.over[2]==1?'receive':'received'):''}">
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>3</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满</p>
                        <div class="card-money">1万</div>
                        <div class="card-btn"><a  href="javascript:return false;"  onclick="javascript:toReceiveAward(4,this);"><c:if test="${activityInfo.used[2]==1 }">已</c:if>领<i>10</i>元</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            	<div class="card-item card-four ${activityInfo.count[3]==1?(activityInfo.over[3]==1?'receive':'received'):''}">
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>4</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满</p>
                        <div class="card-money">2万</div>
                        <div class="card-btn"><a  href="javascript:return false;"  onclick="javascript:toReceiveAward(8,this);"><c:if test="${activityInfo.used[3]==1 }">已</c:if>领<i>18</i>元</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            	<div class="card-item card-five ${activityInfo.count[4]==1?(activityInfo.over[4]==1?'receive':'received'):''}">
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>5</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满</p>
                        <div class="card-money">5万</div>
                        <div class="card-btn"><a  href="javascript:return false;"  onclick="javascript:toReceiveAward(16,this);"><c:if test="${activityInfo.used[4]==1 }">已</c:if>领<i>28</i>元</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            	<div class="card-item card-six ${activityInfo.count[5]==1?(activityInfo.over[5]==1?'receive':'received'):''}">
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>6</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满</p>
                        <div class="card-money">10万</div>
                        <div class="card-btn"><a  href="javascript:return false;" onclick="javascript:toReceiveAward(32,this);"><c:if test="${activityInfo.used[5]==1 }">已</c:if>领<i>88</i>元</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            	<div class="card-item card-seven ${activityInfo.count[6]==1?(activityInfo.over[6]==1?'receive':'received'):''}">
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>7</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满</p>
                        <div class="card-money">20万</div>
                        <div class="card-btn"><a  href="javascript:return false;" onclick="javascript:toReceiveAward(64,this);"><c:if test="${activityInfo.used[6]==1 }">已</c:if>领<i>168</i>元</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            	<div class="card-item card-eight ${activityInfo.count[7]==1?(activityInfo.over[7]==1?'receive':'received'):''}">
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>8</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满</p>
                        <div class="card-money">50万</div>
                        <div class="card-btn"><a  href="javascript:return false;" onclick="javascript:toReceiveAward(128,this);"><c:if test="${activityInfo.used[7]==1 }">已</c:if>领<i>588</i>元</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            	<div class="card-item card-nine ${activityInfo.count[8]==1?(activityInfo.over[8]==1?'receive':'received'):''}">
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>9</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满</p>
                        <div class="card-money">100万</div>
                        <div class="card-btn"><a  href="javascript:return false;" onclick="javascript:toReceiveAward(256,this);"><c:if test="${activityInfo.used[8]==1 }">已</c:if>领<i>988</i>元</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            	<div class="card-item card-ten ${activityInfo.count[9]==1?(activityInfo.over[9]==1?'receive':'received'):''}">
                	<span class="icon-signage"></span>
                    <span class="icon-triangle"><i>10</i></span>
                    <div class="card-light"></div>
                    <div class="card-content">
                        <p class="card-title">累计投资满</p>
                        <div class="card-money">200万</div>
                        <div class="card-btn"><a  href="javascript:return false;" onclick="javascript:toReceiveAward(512,this);"><c:if test="${activityInfo.used[9]==1 }">已</c:if>领<i>2000</i>元</a></div>
                    </div>
                    <div class="card-shadow"></div>
                </div>
            </div>
        </div>        
        <div class="spring-three">
        	<div class="display-area">
            	<div class="fl">户外运动，释放活力，你想要的都在这！</div>
                <div class="fr"><p class="btn-gold-l"><a href="https://www.hsbank360.com/f/integralMall/index" target="_blank">去花生乐园</a></p></div>
            </div>
        </div>        
        <div class="spring-four">
        	<div class="display-area">
            	<p>
                    <a href="https://www.hsbank360.com/f/integralMall/commodityDetails?productId=24" target="_blank" class="btn-gold-s">去兑换</a>
                    <a href="https://www.hsbank360.com/f/integralMall/commodityDetails?productId=42" target="_blank" class="btn-gold-s">去兑换</a>
                    <a href="https://www.hsbank360.com/f/integralMall/commodityDetails?productId=43" target="_blank" class="btn-gold-s">去兑换</a>
                    <a href="https://www.hsbank360.com/f/integralMall/commodityDetails?productId=41" target="_blank" class="btn-gold-s">去兑换</a>
                </p>
            </div>
        </div>        
        <div class="spring-five">
        	<div class="display-area">
            	<ul>
                	<li class="clearfix"><span>1</span><b>活动时间：2016.4.1-4.30</b></li>
                	<li class="clearfix"><span>2</span><b>参与用户：花生金服平台所有投资用户</b></li>
                	<li class="clearfix"><span>3</span><b>投资范围：除“活花生”、“新花生”外所有投资项目</b></li>
                	<li class="clearfix"><span>4</span><b>奖励说明：投资领取的旅行经费均为现金</b></li>
                	<li class="clearfix"><span>5</span><b>奖励发放：投资领取的花生豆、旅行经费（现金），在领取后24小时内发放至用户账户</b></li>
                	<li class="clearfix"><span>6</span><b>奖励查看：①APP、微信端，用户可在“我的”--“可用余额”查看现金奖励，在“花生豆”查看花生豆奖励；②电脑端，用户可在“我的账户”--“可用余额”查看现金奖励，在“我的花生豆”中查看花生豆奖励</b></li>
                </ul>
                <p>花生金服保留此次活动的最终解释权<br>如有疑问请拨打客服热线：400-969-6599</p>
            </div>
        </div>               
    </div>
</div>
</div>
</c:if>
<!-- copyright -->
<div id="footer_area" class="footer_area">
	<div class="footer_menu">
       	<span class=""><a href="${ctxFront}/gywm/index">关于我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront}/gywm/jrwm">加入我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront}/gywm/lxwm">联系我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront}/index#cooperationAgency">友情链接</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">网站地图</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">法律声明</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">黑名单</a></span>
    </div>
	<div class="div_height_20"></div>
	<div class="footer_text">
    	<span>客服邮箱：service@fdjf.net</span>&nbsp;&nbsp;&nbsp;&nbsp;
        <span>服务热线：<b class="font_size_18">400-969-6599</b>&nbsp;(工作日9:00~18:00)</span>
    </div>
    <div class="div_height_10"></div>
	<div class="footer_text">©2015上海富定金融信息服务股份有限公司&nbsp;&nbsp;All right reserved.&nbsp;&nbsp;沪ICP备15025483号</div>
   	<div class="div_height_20"></div>
   	<div class="footer_logo">
    	<a href="javascript:void(0);" class="logo_gs"></a>
    	<a href="javascript:void(0);" class="logo_nt"></a>
    	<a href="javascript:void(0);" class="logo_jc"></a>
   	</div>
</div>

</body>
</html>