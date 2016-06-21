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
<title>每日竞猜-猜中可得1000花生豆</title>
<style type="text/css">
body{ background-color:#f2ebeb; -webkit-font-smoothing: antialiased;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto; color:#fff;}
.guess-banner{ height:454px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/guess-banner.jpg) no-repeat center top;}
.guess-banner p{ padding-top:422px; text-align:center; font-size:26px; color:#2e95fe;}
.guess-one{ height:385px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/guess-bg-01.jpg) no-repeat center top;}
.guess-one .btn-area{}
.guess-one .tip-enter{ padding-top:22px; text-align:center; font-size:16px;}
.guess-one .tip-enter a{ color:#d5641c;}
.guess-one .tip-enter a:hover{ color:#ffc800;}
.guess-one .btn-area.btn-left{ float:left; padding:252px 0 0 126px; color:#ff4527;}
.guess-one .btn-area.btn-right{ float:right; padding:252px 126px 0 0; color:#026bc6;}
.guess-one .btn-area a{ width:130px; height:40px; line-height:40px; text-align:center; background-color:#fff; font-size:26px; font-weight:bold; display:inline-block; transition: color 0.3s ease-out 0s; -o-transition: color 0.3s ease-out 0s; -moz-transition: color 0.3s ease-out 0s; -webkit-transition: color 0.3s ease-out 0s;}
.guess-one .btn-area.btn-left a{ color:#ff4527; border:1px solid #ff4527;}
.guess-one .btn-area.btn-left a:hover{ background-color:#ff4527; color:#fff; border:1px solid #fff;}
.guess-one .btn-area.btn-right a{ color:#026bc6; border:1px solid #026bc6;}
.guess-one .btn-area.btn-right a:hover{ background-color:#026bc6; color:#fff; border:1px solid #fff;}
.guess-one .sel-red-team .btn-area.btn-left a{ color:#ff4527; border:1px solid #ff4527; text-indent:-9999px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/guess-btn-red-sel.png) no-repeat; cursor:default;}
.guess-one .sel-red-team .btn-area.btn-right a{ color:#8a8a8a; border:1px solid #026bc6; background-color:#d0d0d0; cursor:default;}
.guess-one .sel-blue-team .btn-area.btn-left a{ color:#8a8a8a; border:1px solid #ff4527; background-color:#d0d0d0; cursor:default;}
.guess-one .sel-blue-team .btn-area.btn-right a{ color:#8a8a8a; border:1px solid #026bc6; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/guess-btn-blue-sel.png) no-repeat;text-indent:-9999px; cursor:default;}
.guess-two{ background:url(${ctxStatic}/modules/front/images/activity/tugofwar/guess-bg-02.jpg) no-repeat center top;}
.guess-two .guess-two-title{ height:137px; line-height:137px; text-align:center; font-size:40px; color:#87461d;}
.guess-two dl{ margin:0 auto; width:780px;}
.guess-two dl dt{ height:70px; line-height:70px; background-color:#ff9562; color:#fff;}
.guess-two dl dt span{ font-size:22px; font-weight:normal;}
.guess-two dl span{ float:left; text-align:center; display:inline-block;}
.guess-two dl .gt-row1, .gt-row2, .gt-row3{ width:194px;}
.guess-two dl .gt-row4{ width:164px;}
.gt-btn-gold{ margin-top:18px; width:150px; height:46px; line-height:46px; display:inline-block; background-color:#ffc800; text-align:center; color:#87461d; border-radius:10px; -o-border-radius:10px; -moz-border-radius:10px; -webkit-border-radius:10px;}
.gt-btn-gray{ margin-top:18px; width:150px; height:46px; line-height:46px; display:inline-block; background-color:#d0d0d0; text-align:center; color:#8a8a8a; border-radius:10px; -o-border-radius:10px; -moz-border-radius:10px; -webkit-border-radius:10px;}
.guess-two dl dd{ height:82px; line-height:82px; border-bottom:1px solid #f5f5f5; background-color:#fff; color:#87461d; font-size:16px;}
.guess-two dl .gt-row5{ width:34px;}
.guess-two dl .gt-row5 a{ width:34px; height:35px; line-height:35px; text-align:center; display:block; color:#87461d; background-color:#e77946; font-family:sans-serif; font-weight:bold;transition: color 0.3s ease-out 0s; -o-transition: color 0.3s ease-out 0s; -moz-transition: color 0.3s ease-out 0s; -webkit-transition: color 0.3s ease-out 0s;}
.guess-two dl .gt-row5 a:hover{ color:#fff;}
.guess-two dl .gt-row5 a.btn-arrow-up{transform:rotate(-90deg); -ms-transform:rotate(-90deg); -moz-transform:rotate(-90deg); -webkit-transform:rotate(-90deg); -o-transform:rotate(-90deg);}
.guess-two dl .gt-row5 a.btn-arrow-down{transform:rotate(90deg); -ms-transform:rotate(90deg); -moz-transform:rotate(90deg); -webkit-transform:rotate(90deg); -o-transform:rotate(90deg);}
.guess-two dl .gt-row5 a.btn-arrow-disable{ color:#d0d0d0; cursor:no-drop;}
.guess-three .display-area { height:504px; background-color:#f7edcb;}
.guess-three .guess-three-title{ height:80px; line-height:80px; text-align:center; font-size:40px; color:#87461d;}
.guess-three .guess-three-text{ margin:0 auto; padding: 30px 40px 0 40px; width:698px; height:360px; border:1px solid #87461d; border-radius:10px; -o-border-radius:10px; -ms-border-radius:10px; -moz-border-radius:10px; -webkit-border-radius:10px;}
.guess-three .guess-three-text p{ color:#87461d; font-size:16px; line-height:26px; padding-bottom:10px;}
.tip-not-logged{ margin:0 auto; width:780px; height:160px; background-color:#fff; color:#87461d; font-size:16px; text-align:center;}
.tip-not-logged p{ padding-top:40px;}
.tip-has-logged{ margin:0 auto; width:780px; height:160px; background-color:#fff; color:#87461d; font-size:16px; text-align:center;}
.tip-has-logged p{ padding-top:70px;}
/*pop up window*/
.popup-bg{ position:fixed; top:0; left:0; width:100%; height:100%; background:url(${ctxStatic}/modules/front/images/util/bg_transparent.png) repeat; z-index:99;}
.popup-bg .popup-main{ position:absolute; top:50%; left:50%; margin-top:-100px; margin-left:-200px; padding:8px; width:400px; min-height:154px; background:#ff9562; border-radius:8px; -o-border-radius:8px; -ms-border-radius:8px; -moz-border-radius:8px; -webkit-border-radius:8px;}
.popup-bg .poput-title{ height:46px; line-height:46px; text-align:center; font-size:24px; color:#fff;}
.poput-content{ background:#fff; padding-top:30px; padding-bottom:24px; border-radius:8px; -o-border-radius:8px; -ms-border-radius:8px; -moz-border-radius:8px; -webkit-border-radius:8px;}
.poput-content .pupup-text{ margin:0 auto 20px auto; width:300px; display:block; font-size:18px; color:#b1571e; line-height:32px; text-align:center;}
.poput-content .pupup-text span{ color:#fc3414;}
.poput-content .pupup-btn{ margin:0 auto; width:156px; height:39px; line-height:39px; font-size:16px; display:block; text-align:center; color:#fff; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/tugofwar-btn-popup.png) no-repeat;}
</style>
<script type="text/javascript">
	var pageNo = 1;
	var maxPageNo = 0;
	$(function() {
		if("${hasLogin}" != "true") {
			$("#choiceRed").click(function() {
				window.location.href = "${ctxFront}/login";
			});
			$("#choiceBlue").click(function() {
				window.location.href = "${ctxFront}/login";
			});
		} else if("${choiceSide}" == "red") {
			$("#choiceBlue").click(function() {
				$("#popUp").show();
				$("#popUp .poput-title").hide();
				$("#popUpText").html("今天已经猜过啦，明天再来猜！");
			});
		} else if("${choiceSide}" == "blue") {
			$("#choiceRed").click(function() {
				$("#popUp").show();
				$("#popUp .poput-title").hide();
				$("#popUpText").html("今天已经猜过啦，明天再来猜！");
			});
		} else {
			$("#choiceRed").click(function() {
				choiceSide("red");
			});
			$("#choiceBlue").click(function() {
				choiceSide("blue");
			});
		}
		if($(".page-4").size() > 0) {
			maxPageNo = 4;
		} else if($(".page-3").size() > 0) {
			maxPageNo = 3;
		} else if($(".page-2").size() > 0) {
			maxPageNo = 2;
		} else if($(".page-1").size() > 0) {
			maxPageNo = 1;
		}
		if(maxPageNo <= 1) {
			$("#pageDown").addClass("btn-arrow-disable");
		}
		$("#pageUp").click(function() {
			changePage(-1);
		});
		$("#pageDown").click(function() {
			changePage(1);
		});
		$("#month").html((new Date()).getMonth() + 1);
		$("#day").html((new Date()).getDate());
	});
	function changePage(value) {
		if(pageNo + value >= 1 && pageNo + value <= maxPageNo) {
			pageNo = pageNo + value;
			$(".page-1").hide();
			$(".page-2").hide();
			$(".page-3").hide();
			$(".page-4").hide();
			$(".page-" + pageNo).show();
			$("#pageUp").removeClass("btn-arrow-disable");
			$("#pageDown").removeClass("btn-arrow-disable");
		}
		if(pageNo == 1) {
			$("#pageUp").addClass("btn-arrow-disable");
		}
		if(pageNo == maxPageNo) {
			$("#pageDown").addClass("btn-arrow-disable");
		}
	}
	function closePop() {
		$("#popUp").hide();
		$("#popUp .poput-title").show();
		$("#popUpBtn").click(function() {
			closePop();
		});
		$("#popUpBtn").html("我知道了");
	}
	function choiceSide(side) {
		$.getJSON("${ctxFront}/activity/gambel/choiceTerm?choiceSide=" + side,function(data) {
			if(typeof(data.isSuccess) == "undefined") {
				$("#popUp").show();
				$("#popUp .poput-title").hide();
				$("#popUpText").html("竞猜活动已结束!");
			} else if (data.isSuccess) {
				$("#popUp").show();
				$("#popUp .poput-title").html("恭喜您已参与今日竞猜！");
				$("#popUpText").html("记得明天来看结果！");
				$("#joinTeam").addClass("sel-" + side + "-team");
				if(side == "red") {
					$("#choiceRed").unbind("click");
					$("#choiceBlue").unbind("click");
					$("#choiceBlue").click(function() {
						$("#popUp").show();
						$("#popUp .poput-title").hide();
						$("#popUpText").html("今天已经猜过啦，明天再来猜！");
					});
				} else if(side == "blue") {
					$("#choiceRed").unbind("click");
					$("#choiceBlue").unbind("click");
					$("#choiceRed").click(function() {
						$("#popUp").show();
						$("#popUp .poput-title").hide();
						$("#popUpText").html("今天已经猜过啦，明天再来猜！");
					});
				}
				$("#popUpBtn").click(function() {
					location.reload();
				});
			} else {
				$("#popUp").show();
				$("#popUp .poput-title").hide();
				$("#popUpText").html(data.message);
				if(data.message.indexOf("投资") != -1) {
					$("#popUpBtn").click(function() {
						window.location.href = "${ctxFront}/wytz";
					});
					$("#popUpBtn").html("投资赚花生豆");
				}
			}
		});
	}
	function getIntegral(gambleId) {
		$.getJSON("${ctxFront}/activity/gambel/getIntegral?gambleId=" + gambleId,function(data) {
			if(data.isSuccess) {
				$("#getIntegral_" + gambleId).removeAttr("onclick");
				$("#getIntegral_" + gambleId).removeClass("gt-btn-gold").addClass("gt-btn-gray");
				$("#getIntegral_" + gambleId).html("已领取");
				$("#popUp").show();
				$("#popUp .poput-title").html("恭喜您猜中！");
				$("#popUpText").html("已领取<span>1000</span>花生豆！");
			} else {
				$.jBox.alert(data.message,"提示");
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
<!--main-->
<div class="main" style="display:block">
    <!--pop up window-->
    <div id="popUp" class="popup-bg" style="display:none">
        <div class="popup-main">
            <div class="poput-title">恭喜您已加入红队！</div>
            <div class="poput-content">
                <p class="pupup-text" id="popUpText"></p>
                <a href="javascript:void(0);" id="popUpBtn" onclick="closePop()" class="pupup-btn">我知道了</a>
            </div>
        </div>
    </div>
    <!--main content-->
    <div class="main-content">
		<div class="guess-banner">
        	<div class="display-area">
            	<p><span id="month"></span>月<span id="day"></span>日竞猜中</p>
            </div>
        </div>
    	<div class="guess-one" id="one">
        	<div id="joinTeam" class="display-area clearfix ${choiceSide eq 'red' ? 'sel-red-team' : (choiceSide eq 'blue' ? 'sel-blue-team' : '')}"><!--选择红队后变换按钮状态需要加上class“sel-red-team”，选择蓝队后变换按钮状态需要加上class“sel-blue-team”。-->
            	<div class="btn-area btn-left"><a id="choiceRed" href="javascript:void(0);">猜TA赢</a></div>
                <div class="btn-area btn-right"><a id="choiceBlue" href="javascript:void(0);">猜TA赢</a></div>
            </div>
            <div class="display-area">
            	<p class="tip-enter"><a href="${ctxFront }/activity/tugWar">进入拔河擂台赛>></a></p>
            </div>
        </div>
        <div class="guess-two">
        	<div class="display-area">
            	<div class="guess-two-title">我的竞猜记录</div>
                <dl>
                	<dt class="clearfix">
                    	<span class="gt-row1">日期</span>
                    	<span class="gt-row2">我猜</span>
                    	<span class="gt-row3">结果</span>
                    	<span class="gt-row4">奖励</span>
                    	<span class="gt-row5">
                        	<a href="javascript:void(0);" id="pageUp" class="btn-arrow-up btn-arrow-disable">></a><!--按钮灰掉不可点击加上class“btn-arrow-disable”-->
                        	<a href="javascript:void(0);" id="pageDown" class="btn-arrow-down">></a>
                        </span>
                    </dt>
                    <c:forEach var="gamble" items="${gambleList }" varStatus="status">
                    <c:choose>
                    	<c:when test="${status.index < 8 }">
                   	<dd class="clearfix page-1">
                    	</c:when>
                    	<c:when test="${status.index >= 8 && status.index < 16 }">
                   	<dd class="clearfix page-2" style="display:none">
                    	</c:when>
                    	<c:when test="${status.index >= 16 && status.index < 24 }">
                   	<dd class="clearfix page-3" style="display:none">
                    	</c:when>
                    	<c:otherwise>
                  	<dd class="clearfix page-4" style="display:none">
                    	</c:otherwise>
                    </c:choose>
                    	<span class="gt-row1"><fmt:formatDate value="${gamble.opDt }" pattern="yyyy-MM-dd" /></span>
                    	<span class="gt-row2">${gamble.choiceSide eq 'red' ? '红' : '蓝' }队赢</span>
                    	<span class="gt-row3">${gamble.rightSide eq 'red' ? '红队赢' : (gamble.rightSide eq 'blue' ? '蓝队赢' : '比赛未结束') }</span>
                    	<span class="gt-row4">
                    		<c:choose>
                    			<c:when test="${empty gamble.rightSide }">
                    			<a href="javascript:void(0);">---</a>
                    			</c:when>
                    			<c:when test="${not empty gamble.awardDt }">
                    			<a href="javascript:void(0);" class="gt-btn-gray">已领取</a>
                    			</c:when>
                    			<c:when test="${gamble.choiceSide ne gamble.rightSide }">
                    			<a href="javascript:void(0);" class="gt-btn-gray">未猜中</a>
                    			</c:when>
                    			<c:when test="${(gamble.choiceSide eq gamble.rightSide) && (empty gamble.awardDt) }">
                    			<a id="getIntegral_${gamble.id}" href="javascript:void(0);" onclick="getIntegral(${gamble.id })" class="gt-btn-gold">领取1000花生豆</a>
                    			</c:when>
                    			<c:otherwise>
                    			<a href="javascript:void(0);">---</a>
                    			</c:otherwise>
                    		</c:choose>
                        </span>
                    </dd>
                    </c:forEach>
                    <c:if test="${hasLogin && (empty gambleList) }">
                    <div class="tip-has-logged">
	                	<p>马上参与竞猜，花生豆尽情赚！</p>
	                </div>
                    </c:if>
                    <c:if test="${!hasLogin }">
                    <div class="tip-not-logged">
	                	<p>请登录后查看您的记录</p>
	                    <a href="${ctxFront }/login" class="gt-btn-gold">立即登录</a>
	                </div>
                    </c:if>
                </dl>
            </div>
        </div>
        <div class="guess-three">
        	<div class="display-area">
            	<div class="guess-three-title">活动规则</div>
                <div class="guess-three-text">
                	<p>1、竞猜时间：2016.5.1-5.31，奖励领取时间：2016.5.2-6.1</p>
                	<p>2、参与对象：花生金服平台用户</p>
                	<p>3、参与规则：红蓝2队进行拔河擂台赛，每天投资额高的一方赢得当天比赛，每天每用户可猜1次，每次消耗100花生豆，次日在本活动页面“我的竞猜记录”中查看竞猜结果，若猜中可领取奖励1000花生豆</p>
                	<p>4、奖励查看：①电脑端在“我的账户”--“我的花生豆”中查看所获奖励；②APP、微信端在“我的”--“花生豆”中查看所获奖励</p>
                	<p>5、花生豆奖励仅限在领取时间内领，逾期作废不可领</p>
                	<p style="text-align:center; padding-top:20px; padding-bottom:0px;">花生金服保留此次活动的最终解释权</p>
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