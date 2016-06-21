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
		.result-banner-1{ height:383px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/result-bg-01.jpg) no-repeat center top;}
		.result-banner-2{ height:303px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/result-bg-02.jpg) no-repeat center top;}
		.result-one{ height:131px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/result-bg-03.jpg) no-repeat center top;}
		.result-one .text-1{ padding-top:16px; text-align:center; font-size:28px; color:#b1571f; line-height:48px;}
		.result-one .text-1 em{ width:30px; display:inline-block;}
		.result-one .text-1 a{ color:#b1571f; text-decoration:underline;}
		.result-one .text-2{ padding-top:16px; text-align:center; font-size:28px; color:#b1571f; line-height:48px;}
		.result-one .text-2 b{ margin-right:30px; color:#ff4527;}
		.result-one .text-3{ padding-top:56px; text-align:center; font-size:28px; color:#b1571f; }
		.result-two{ height:454px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/result-bg-04.jpg) no-repeat center top;}
		.result-three{ height:594px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/result-bg-05.jpg) no-repeat center top;}
		.result-three .display-area{ padding-top:100px; color:#333; font-size:16px;}
		.result-three .fl{ margin-left:16px; width:467px; height:473px;}
		.result-three .fr{ margin-right:17px;}
		.result-three dl .odd{ background-color:#fff;}
		.result-three dl .even{ background-color:#fff5ee;}
		.result-three dl dt span{ float:left; height:52px; line-height:52px; text-align:center; display:block;}
		.result-three dl dt .row1{ width:52px;}
		.result-three dl dt .row2{ width:124px;}
		.result-three dl dt .row3{ width:150px;}
		.result-three dl dt .row4{ width:140px;}
		.result-three dl dd b{ margin-top: 10px; width:22px; height:22px; line-height:22px; text-align:center; color:#fff; font-size:14px; background-color:#c9c0c0; display:inline-block; font-weight:normal;    border-radius: 22px; -o-border-radius: 22px; -ms-border-radius: 22px; -moz-border-radius: 22px; -webkit-border-radius: 22px;}
		.result-three dl dd span{ float:left; height:42px; line-height:42px; text-align:center; display:block;}
		.result-three dl dd .row1{ width:52px;}
		.result-three dl dd .row2{ width:124px;}
		.result-three dl dd .row3{ width:150px;}
		.result-three dl dd .row4{ width:140px;}
		.result-three dl .tow-icon-first b{ margin:5px auto 0 auto; padding-top:6px; width:22px; height:30px; line-height:30px; display:inline-block; background: url(${ctxStatic}/modules/front/images/activity/tugofwar/icon-first.png) no-repeat; border-radius:0; -webkit-border-radius:0;}
		.result-three dl .tow-icon-second b{ margin:5px auto 0 auto; padding-top:6px; width:22px; height:30px; line-height:30px; display:inline-block; background: url(${ctxStatic}/modules/front/images/activity/tugofwar/icon-second.png) no-repeat; border-radius:0; -webkit-border-radius:0;}
		.result-three dl .tow-icon-third b{ margin:5px auto 0 auto; padding-top:6px; width:22px; height:30px; line-height:30px; display:inline-block; background: url(${ctxStatic}/modules/front/images/activity/tugofwar/icon-third.png) no-repeat; border-radius:0; -webkit-border-radius:0;}
		.result-four{ height:114px; background:url(${ctxStatic}/modules/front/images/activity/tugofwar/result-bg-06.jpg) no-repeat center top;}
	</style>
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
	<!--main content-->
	<div class="main-content">
		<div class="result-banner-1"></div>
		<div class="result-banner-2"></div>
		<div class="result-one">
			<div class="display-area">
				<c:choose>
					<c:when test="${hasLogin}">
						<c:choose>
							<c:when test="${empty side}">
								<p class="text-3" style="display:block">您没有参与此次活动</p>
							</c:when>
							<c:otherwise>
								<p class="text-2" style="display:block">我获得的奖金：<b><fmt:formatNumber value="${awardAmount }" pattern="0.00"/>元</b>
									所属团队：<span>${side eq 'red' ? '红' : '蓝'}队</span><br>我的投资金额：<span><fmt:formatNumber value="${investmentAmount }" pattern="0.00"/>元</span></p>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<p class="text-1" style="display:block">我获得的奖金：-- 元<em></em>所属团队：-- 队<br><a href="${ctxFront}/login">登录查看</a></p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="result-two"></div>
		<div class="result-three">
			<div class="display-area">
				<div class="fl">
					<dl>
						<dt class="clearfix">
							<span class="row1"> </span>
							<span class="row2">用户名</span>
							<span class="row3">投资金额(元)</span>
							<span class="row4">获得奖金(元)</span>
						</dt>
						<c:forEach var="investmentInfo" items="${redList }" varStatus="status">
						<c:choose>
							<c:when test="${status.index % 2 == 0}">
								<dd class="clearfix odd">
							</c:when>
							<c:otherwise>
								<dd class="clearfix even">
							</c:otherwise>
						</c:choose>
							<c:choose>
								<c:when test="${status.index == 0 }">
									<span class="row1 tow-icon-first"><b>1</b></span>
								</c:when>
								<c:when test="${status.index == 1 }">
									<span class="row1 tow-icon-second"><b>2</b></span>
								</c:when>
								<c:when test="${status.index == 2 }">
									<span class="row1 tow-icon-third"><b>3</b></span>
								</c:when>
								<c:otherwise>
									<span class="row1"><b>${status.index + 1 }</b></span>
								</c:otherwise>
							</c:choose>
							<span class="row2">${investmentInfo.mobile }</span>
							<span class="row3"><fmt:formatNumber value="${investmentInfo.amount }" pattern="#.00"/></span>
							<span class="row4"><fmt:formatNumber value="${investmentInfo.awardAmount }" pattern="#.00"/></span>
						</dd>
						</c:forEach>
					</dl>
				</div>
				<div class="fr">
					<dl>
						<dt class="clearfix">
							<span class="row1"> </span>
							<span class="row2">用户名</span>
							<span class="row3">投资金额(元)</span>
							<span class="row4">获得奖金(元)</span>
						</dt>
						<c:forEach var="investmentInfo" items="${blueList }" varStatus="status">
							<c:choose>
								<c:when test="${status.index % 2 == 0}">
									<dd class="clearfix odd">
								</c:when>
								<c:otherwise>
									<dd class="clearfix even">
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${status.index == 0 }">
									<span class="row1 tow-icon-first"><b>1</b></span>
								</c:when>
								<c:when test="${status.index == 1 }">
									<span class="row1 tow-icon-second"><b>2</b></span>
								</c:when>
								<c:when test="${status.index == 2 }">
									<span class="row1 tow-icon-third"><b>3</b></span>
								</c:when>
								<c:otherwise>
									<span class="row1"><b>${status.index + 1 }</b></span>
								</c:otherwise>
							</c:choose>
							<span class="row2">${investmentInfo.mobile }</span>
							<span class="row3"><fmt:formatNumber value="${investmentInfo.amount }" pattern="#.00"/></span>
							<span class="row4"><fmt:formatNumber value="${investmentInfo.awardAmount }" pattern="#.00"/></span>
							</dd>
						</c:forEach>
					</dl>
				</div>
			</div>
		</div>
		<div class="result-four"></div>
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