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
<title>拔河擂台赛-花生金服 你的主场！</title>
<style type="text/css">
body{ background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-bg-color.jpg) repeat-y center top; -webkit-font-smoothing: antialiased;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto; color:#fff;}
.tow-banner{ height:370px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-banner.jpg) no-repeat center top;}
.tow-one{ height:383px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-bg-01.jpg) no-repeat center top;}
.tow-one .tow-one-left{ float:left; margin:150px 0 0 159px; position:relative; width:180px; height:180px;}
.tow-one .tow-btn-red{ position:absolute; top:50%; left:50%; margin:-68px 0 0 -68px; width:136px; height:136px; display:block; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-red-nor.png) no-repeat; z-index:5;}
.tow-one .tow-btn-red:hover{ background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-red-hov.png) no-repeat;}
.tow-one .ripple{ position:absolute; top:50%; left:50%; background-color:#fff; border-radius:100%; -o-border-radius:100%; -moz-border-radius:100%; -webkit-border-radius:100%; }
@-webkit-keyframes ripple {
0% { -webkit-transform: scale(0.6)}
100% {-webkit-transform: scale(1.0);opacity: 0;}} 
@-moz-keyframes ripple {
0% { -moz-transform: scale(0.6)}
100% {-moz-transform: scale(1.0);opacity: 0;}} 
@-o-keyframes ripple {
0% { -o-transform: scale(0.6)}
100% {-o-transform: scale(1.0);opacity: 0;}} 
@keyframes ripple {
0% {transform: scale(0.6);} 
100% {transform: scale(1.0);opacity: 0;}}
.tow-one .ripple.s{ margin:-80px 0 0 -80px; width:160px; height:160px; -webkit-animation: ripple 2.0s infinite ease-in-out; -moz-animation: ripple 2.0s infinite ease-in-out; -o-animation: ripple 2.0s infinite ease-in-out; animation: ripple 2.0s infinite ease-in-out;}
.tow-one .ripple.m{ margin:-85px 0 0 -85px; width:170px; height:170px; -webkit-animation: ripple 2.0s infinite ease-in-out; -moz-animation: ripple 2.0s infinite ease-in-out; -o-animation: ripple 2.0s infinite ease-in-out; animation: ripple 2.0s infinite ease-in-out;}
.tow-one .ripple.l{ margin:-90px 0 0 -90px; width:180px; height:180px; -webkit-animation: ripple 2.0s infinite ease-in-out; -moz-animation: ripple 2.0s infinite ease-in-out; -o-animation: ripple 2.0s infinite ease-in-out; animation: ripple 2.0s infinite ease-in-out;}
.tow-one .tow-one-right{ float:right; margin:150px 159px 0 0; position:relative; width:180px; height:180px;}
.tow-one .tow-btn-blue{ position:absolute; top:50%; left:50%; margin:-68px 0 0 -68px; width:136px; height:136px; display:block; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-blue-nor.png) no-repeat; z-index:5;}
.tow-one .tow-btn-blue:hover{ background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-blue-hov.png) no-repeat;}
.tow-one .join-blue-team .tow-btn-red{ background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-red-disable.png) no-repeat; cursor:default;}
.tow-one .join-blue-team .tow-btn-blue{ background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-blue-sel.png) no-repeat; cursor:default;}
.tow-one .join-blue-team .ripple{ display:none}
.tow-one .join-red-team .tow-btn-red{ background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-red-sel.png) no-repeat; cursor:default;}
.tow-one .join-red-team .tow-btn-blue{ background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-blue-disable.png) no-repeat; cursor:default;}
.tow-one .join-red-team .ripple{ display:none}
.tow-one .tow-one-center{ float:left; margin:230px 0 0 0; width:310px; text-align:center;}
.tow-one .tow-one-center p{ font-size:16px; line-height:28px}
.tow-one .tow-one-center p strong{ font-size:20px;}
.tow-two{ height:593px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-bg-02.jpg) no-repeat center top;}
.tow-two .tow-two-area{ padding:466px 0 0 437px;}
.tow-btn-yellow{ padding-top:26px; width:125px; height:99px; font-size:20px; font-weight:bold; line-height:26px; text-align:center; color:#87461d; display:inline-block; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-yellow-nor.png) no-repeat;}
.tow-btn-yellow:hover{ color:#87461d; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-yellow-hov.png) no-repeat;}
.tow-three{ height:873px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-bg-03.png) no-repeat center top;}
.tow-three .tow-three-tip{ padding:132px 0 36px 0; font-size:16px; color:#4f270e; text-align:center;}
.tow-three-title{ margin:0 auto; width:300px; height:42px; line-height:42px; border:2px solid #4f270e; font-size:24px; color:#87461d; border-bottom:none; text-align:center; border-radius:10px 10px 0 0; -o-border-radius:10px 10px 0 0; -moz-border-radius:10px 10px 0 0; -webkit-border-radius:10px 10px 0 0;}
.tow-three-title span{ text-decoration:underline;}
.tow-three dl{ margin:0 auto; width:770px; height:436px; color:#87461d; border:2px solid #4f270e; border-radius:10px; display:block; -o-border-radius:10px; -moz-border-radius:10px; -webkit-border-radius:10px; overflow:hidden;}
.tow-three dl dt{ height:46px; line-height:46px; border-bottom:1px solid #4f270e;}
.tow-three dl dd{}
.tow-three dl dd tr{ text-align:center;}
.tow-three dl span{ float:left; display:inline-block; text-align:center; height:38px; line-height:38px; font-size:16px; border:1px solid #e5dfdb; border-left:none; border-top:none;}
.tow-three dl span img{ margin-top:10px;}
.tow-three dl dt span{ height:46px; line-height:46px; border-color:#fff; border-bottom:none; font-size:18px; font-weight:normal;}
.tow-three dl .row1{ width:96px;}
.tow-three dl .row2{ width:170px;}
.tow-three dl .row3{ width:188px;}
.tow-three dl .row4{ width:150px;}
.tow-three dl .row5{ width:162px; border-right:none;}
.tow-three dl .row5 em{ font-style:normal; color:#ff5300;}
.tow-three dl b{ font-weight:normal;}
.tow-three dl dd .tow-icon-first b{ padding-top:2px; margin-top:3px; width:22px; height:30px; display:inline-block; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/icon-first.png) no-repeat;}
.tow-three dl dd .tow-icon-second b{ padding-top:2px; margin-top:3px; width:22px; height:30px; display:inline-block; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/icon-second.png) no-repeat;}
.tow-three dl dd .tow-icon-third b{ padding-top:2px; margin-top:3px; width:22px; height:30px; display:inline-block; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/icon-third.png) no-repeat;}
.tow-three dl dd:last-child span{ /*border-bottom:none;*/}
.tow-three .tow-three-tip2{ padding-left:114px; padding-top:10px; color:#87461d; font-size:16px;}
.tow-three-btn{ text-align:center;}
.tow-four{ height:441px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-bg-04.png) no-repeat center top;}
.tow-four .tow-four-area{ padding:276px 0 0 437px}
.tow-five{ margin-bottom:50px; height:735px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-bg-05.png) no-repeat center top;}
.tow-five .tow-five-area{ padding:90px 86px 0 86px; font-size:16px; color:#4f270e;}
.tow-five .tow-five-area p{ line-height:24px; padding-bottom:7px;}
.tow-five .tow-five-area p span{ color:#fa2d0c;}
.tip-text{margin: 170px auto 0 auto;width: 500px;text-align: center;font-size: 16px;}
/*floating on the right navigation*/
.float-nav{ position:fixed; left:50%; top:50%; margin-left:540px; margin-top:-168px; padding-top:97px; width:163px; height:239px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-floatnav-bg.png) no-repeat;}
.float-nav a{ margin:0 25px; text-align:center; display:block; line-height:44px; font-size:14px; color:#fff;}
.float-nav .a-last{ color:#fed7c4;}
/*pop up window*/
.popup-bg{ position:fixed; top:0; left:0; width:100%; height:100%; background:url(${ctxStatic}/modules/front/images/util/bg_transparent.png) repeat; z-index:99;}
.popup-bg .popup-main{ position:absolute; top:50%; left:50%; margin-top:-100px; margin-left:-200px; padding:8px; width:400px; min-height:154px; background:#ff9562; border-radius:8px; -o-border-radius:8px; -ms-border-radius:8px; -moz-border-radius:8px; -webkit-border-radius:8px;}
.popup-bg .poput-title{ height:46px; line-height:46px; text-align:center; font-size:24px; color:#fff;}
.poput-content{ background:#fff; padding-top:30px; padding-bottom:24px; border-radius:8px; -o-border-radius:8px; -ms-border-radius:8px; -moz-border-radius:8px; -webkit-border-radius:8px;}
.poput-content .pupup-text{ margin:0 auto 20px auto; width:240px; display:block; font-size:18px; color:#b1571e; line-height:32px; text-align:center;}
.poput-content .pupup-text span{ color:#fc3414;}
.poput-content .pupup-btn{ margin:0 auto; width:156px; height:39px; line-height:39px; font-size:16px; display:block; text-align:center; color:#fff; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-popup.png) no-repeat;}
/*countdown content*/
.countdown-bg{ height:924px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-countdown-bg.jpg) no-repeat center top;}
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
var noJoinMatchTip = "<strong>加入团队，领取红包</strong><br>选择团队并加入，领取10元现金券红包</p>";
var joinRedSideTip = "<strong>您已加入红队</strong><br>获得10元现金券红包";
var joinBlueSideTip = "<strong>您已加入蓝队</strong><br>获得10元现金券红包";
var contribute = function() {
	window.location.href = "${ctxFront}/wytz";
};
$(function(){
	countDown("2016/05/01 00:00:00","#countdown .day","#countdown .hour","#countdown .minute","#countdown .second");
	if("${hasLogin}" != "true") {
		$("#joinRed").click(function() {
			window.location.href = "${ctxFront}/login";
		});
		$("#joinBlue").click(function() {
			window.location.href = "${ctxFront}/login";
		});
		contribute = function() {
			window.location.href = "${ctxFront}/login";
		};
	} else if("${side}" == "red") {
		$("#joinBlue").click(function() {
			$("#popUp").show();
			$("#popUp .poput-title").html('您已加入红队！');
		});
		$("#joinMatchTip").html(joinRedSideTip);
	} else if("${side}" == "blue") {
		$("#joinRed").click(function() {
			$("#popUp").show();
			$("#popUp .poput-title").html('您已加入蓝队！');
		});
		$("#joinMatchTip").html(joinBlueSideTip);
	} else {
		$("#joinRed").click(function() {
			joinSide("red");
		});
		$("#joinBlue").click(function() {
			joinSide("blue");
		});
		contribute = function() {
			$("#popUp").show();
			$("#popUp .poput-title").html('温馨提示');
			$("#popUpText").html("请先选择队伍再投资。");
			$('#roll_top').click();
		}
	}
	$("#month").html((new Date()).getMonth() + 1);
	$("#day").html((new Date()).getDate());
});

function countDown(time,day_elem,hour_elem,minute_elem,second_elem){
	//if(typeof end_time == "string")
	var end_time = new Date(time).getTime(),//月份是实际月份-1
	//current_time = new Date().getTime(),
	sys_second = (end_time-new Date().getTime())/1000;
	if(sys_second > 0) {
		$(".countdown-bg").show();
		$(".main").hide();
	}
	var timer = setInterval(function(){
		if(sys_second > 0) {
			sys_second -= 1;
			var day = Math.floor((sys_second / 3600) / 24);
			var hour = Math.floor((sys_second / 3600) % 24);
			var minute = Math.floor((sys_second / 60) % 60);
			var second = Math.floor(sys_second % 60);
			day_elem && $(day_elem).text(day);//计算天
			$(hour_elem).text(hour<10?"0"+hour:hour);//计算小时
			$(minute_elem).text(minute<10?"0"+minute:minute);//计算分
			$(second_elem).text(second<10?"0"+second:second);// 计算秒
		} 
		if(sys_second <= 0) { 
			$(".countdown-bg").hide();
			$(".main").show();
			clearInterval(timer);
		}
	}, 1000);
}

function joinSide(side) {
	$.getJSON("${ctxFront}/activity/tugWar/joinMatch?side=" + side, function(data) {
		if(data.isSuccess) {
			$("#popUp").show();
			$("#popUp .poput-title").html("恭喜您已加入" + (side == "red" ? "红" : "蓝") + "队！");
			$("#joinTeam").removeClass("join-before").addClass("join-" + side + "-team");
			if(side == "red") {
				$("#joinRed").unbind("click");
				$("#joinBlue").unbind("click");
				$("#joinBlue").click(function() {
					$("#popUp").show();
					$("#popUp .poput-title").html('您已加入红队！');
				});
				$("#joinMatchTip").html(joinRedSideTip);
			} else if(side == "blue") {
				$("#joinRed").unbind("click");
				$("#joinBlue").unbind("click");
				$("#joinRed").click(function() {
					$("#popUp").show();
					$("#popUp .poput-title").html('您已加入蓝队！');
				});
				$("#joinMatchTip").html(joinBlueSideTip);
			}
			contribute = function() {
				window.location.href = "${ctxFront}/wytz";
			};
		} else {
			$.jBox.alert(data.message,"提示");
		}
	});
}

function closePop() {
	$("#popUp").hide();
	$("#popUpText").html("<span>10</span>元现金券已入账，请笑纳！");
}
</script>
<script type="text/javascript">	
$(document).ready(function(){
	/*返回顶部*/
	$('#roll_top').click(function () {
		$('html,body').animate({
			scrollTop : '0px'
		}, 300);//返回顶部所用的时间 返回顶部也可调用goto()函数
	});
});
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
<!--countdown content-->
<div class="countdown-bg" style="display:none">
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
<!--main-->
<div class="main" style="display:block">
    <!--pop up window-->
    <div id="popUp" class="popup-bg" style="display:none">
        <div class="popup-main">
            <div class="poput-title"></div>
            <div class="poput-content">
                <p class="pupup-text" id="popUpText"><span>10</span>元现金券已入账，请笑纳！</p>
                <a href="javascript:void(0);" onclick="closePop()" class="pupup-btn">我知道了</a>
            </div>
        </div>
    </div>
    <!--floating on the right navigation-->
    <div class="float-nav">
        <a href="#one"><strong>加入团队</strong></a>
        <a href="#two"><strong>拔河擂台赛</strong></a>
        <a href="#three"><strong>大力士排行榜</strong></a>
        <a href="#four"><strong>每日竞猜</strong></a>
        <a href="javascript:;" id="roll_top" class="a-last">返回顶部</a>
    </div>
    <!--main content-->
    <div class="main-content">
    	<div class="tow-banner"></div>
    	<div class="tow-one" name="one" id="one">
        	<div id="joinTeam" class="display-area clearfix ${side eq 'red' ? 'join-red-team' : (side eq 'blue' ? 'join-blue-team' : 'join-before') }"><!--加入前的class“join-before”,加入蓝队后把jion-before替换成“join-blue-team”，加入红队后把jion-before替换成“join-red-team”-->
            	<div class="tow-one-left">
                	<a id="joinRed" href="javascript:void(0);" class="tow-btn-red"></a>
                	<div class="ripple s"></div>
                	<div class="ripple m"></div>
                	<div class="ripple l"></div>
                </div>
            	<div class="tow-one-center">
                	<p id="joinMatchTip"><strong>加入团队，领取红包</strong><br>选择团队并加入，领取10元现金券红包</p><!--加入前的文字-->
                </div>
            	<div class="tow-one-right">
                	<a id="joinBlue" href="javascript:void(0);" class="tow-btn-blue"></a>
                	<div class="ripple s"></div>
                	<div class="ripple m"></div>
                	<div class="ripple l"></div>
                </div>
            </div>
        </div>
    	<div class="tow-two" name="two" id="two">
        	<div class="display-area">
            	<div class="tow-two-area"><a href="javascript:void(0);" onclick="contribute()" class="tow-btn-yellow">我要<br>出力</a></div>
            </div>
        </div>
    	<div class="tow-three" name="three" id="three">
        	<div class="display-area">
            	<div class="tow-three-tip">参与拔河擂台赛，每天投资排名前10名的用户获得现金券奖励</div>
            	<div class="tow-three-title"><span id="month"></span>月<span id="day"></span>日大力士排行榜</div>
                <dl>
                	<dt class="clearfix">
                    	<span class="row1">排名</span>
                    	<span class="row2">用户名</span>
                    	<span class="row3">投资金额(元)</span>
                    	<span class="row4">所属团队</span>
                    	<span class="row5">预计可获奖励</span>
                    </dt>
                    <c:forEach var="investmentInfo" items="${investmentRank }" varStatus="status">
                   	<dd class="clearfix">
                   		<c:choose>
                   			<c:when test="${status.index == 0 }">
                   			<span class="row1 tow-icon-first"><b>${investmentInfo.rank }</b></span>
                   			</c:when>
                   			<c:when test="${status.index == 1 }">
                   			<span class="row1 tow-icon-second"><b>${investmentInfo.rank }</b></span>
                   			</c:when>
                   			<c:when test="${status.index == 2 }">
                   			<span class="row1 tow-icon-third"><b>${investmentInfo.rank }</b></span>
                   			</c:when>
                   			<c:otherwise>
                   			<span class="row1"><b>${investmentInfo.rank }</b></span>
                   			</c:otherwise>
                   		</c:choose>
                    	<span class="row2">${investmentInfo.mobile }</span>
                    	<span class="row3"><fmt:formatNumber value="${investmentInfo.amount }" pattern="#.00"/></span>
                    	<span class="row4"><img src="${ctxStatic}/modules/front/images/activity/tugofwar/icon-flag-${investmentInfo.side }.png"></span>
                    	<span class="row5"><em>${investmentInfo.ticketDenomination }</em>元现金券</span>
                    </dd>
                    </c:forEach>
                    <c:if test="${empty investmentRank }">
                   	<div class="tip-text">争做大力士， 立即榜上有名！</div>
                    </c:if>
                </dl>
                <div class="tow-three-tip2">*本榜单实时更新，最终排名以每日23:59:59排名为准</div>
                <div class="tow-three-btn"><a href="javascript:void(0);" onclick="contribute()" class="tow-btn-yellow">争做<br>大力士</a></div>
            </div>
        </div>
    	<div class="tow-four" name="four" id="four">
        	<div class="display-area">
            	<div class="tow-four-area"><a href="${ctxFront}/activity/gamble" class="tow-btn-yellow">我要<br>竞猜</a></div>
            </div>
        </div>
    	<div class="tow-five">
        	<div class="display-area">
            	<div class="tow-five-area">
                    <p>1、活动时间：2016.5.1-5.31</p>
                    <p>2、活动说明：红蓝2队进行拔河擂台赛，每位用户可选择加入1个团队（加入后不可更改），<span>自加入团队后用户在活动期间的投资金额计入所在团队</span>，按团队在活动期间累计投资总额裁定拔河赛输赢，团队成员按投资比例分享奖金。每日大力士排行榜评选当天累计投资排名前10位的参赛用户，可获得现金券奖励。</p>
                    <p>3、活花生、新花生不参与本活动</p>
                    <p>4、活动奖励：</p>
                    <p>a)参与奖：加入团队即可获得10元现金券</p>
                    <p>b)拔河赛奖金：按团队在活动期间累计投资总额，裁定比赛输赢。获胜团队获得现金奖励2万元，失败团队获得现金奖励1万元，团队成员按投资比例分享奖金。<span>拔河赛结果在活动结束后次日公布，敬请留意关注</span></p>
                    <p>c)大力士奖：每天累计投资排名前10位的参赛用户可获得现金券奖励（第1名奖励100元现金券，第2名奖励80元现金券，第3名奖励60元现金券，第4-10名分别奖励20元现金券）</p>
                    <p>5、奖励发放：</p>
                    <p>a)参与奖：实时发放 </p>
                    <p>b)拔河赛奖金：活动结束后1个工作日发放</p>
                    <p>c)大力士奖：每日大力士排行榜公布后次日发放</p>
                    <p>6、每日竞猜活动规则请至竞猜页面查看</p>
                    <p style="text-align:center; padding-top:18px; padding-bottom:0;">花生金服保留此次活动的最终解释权</p>
                    <p style="text-align:center;">如有疑问请拨打客服热线：400-969-6599</p>
                </div>
            </div>
        </div>
    </div>
</div>

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