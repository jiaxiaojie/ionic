<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><sitemesh:title default="花生金服 hsbank360.com-更优质、更安全、更便捷、更有诚意的互联网金融P2P平台"/></title>
		<meta name="keywords" content="花生金服|网络理财|个人理财|投资理财|P2P理财|互联网金融|债权转让|理财计划|贷款|房产贷款|汽车贷款|个人贷款|无抵押贷款|个人无抵押贷款">
		<meta name="description" content="花生金服(www.hsbank360.com) - 更优质、更安全、更便捷、更有诚意的互联网金融平台。花生金服有严格的风险控制,对接优选项目,第三方资金托管,保障资金安全。">
		<%@include file="/WEB-INF/views/include/head_for_front.jsp" %>
		<link href="${ctxStatic}/modules/front/css/util/util.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/layouts/front_without_menu.css?${version }" rel="stylesheet"/>
		<sitemesh:head/>
	</head>
	<body>
		<c:if test="${need_third_account_tip && not empty p2p:getPrincipal() && p2p:getCustomerAccount().hasOpenThirdAccount != '1'}">
			<div id="third_account_tip">
				<div class="div_width_980 text-center">
					<img src="${ctxStatic}/modules/front/images/util/!.png">
					<span>&nbsp;&nbsp;&nbsp;您需要先开通第三方托管账户，才能开启您的【花生金服】安全投资之旅&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<a class="btn" target="_blank" href="${ctxFront }/customer/thirdAccount/open">立即开通</a>
				</div>
			</div>
		</c:if>
		<div class="div_bg_001">
			<div id="div_header_top" class="div_width_1080">
				<div class="div_left">
						<img id="logo" class="logo" src="${ctxStatic}/modules/front/images/util/logo.png"/>
					</div>
					<div class="div_right">
						<span class="spanLine"><img src="${ctxStatic}/modules/front/images/login/v_line.gif"/></span>
						<span class="spanText"><sitemesh:title/></span>
					</div>
			</div>
			<!-- 分隔线 -->
			<div id="div_line" class="div_line">
				<div class="dotted-line">
                	<div class="line"></div>
                </div>
			</div>
	   		<sitemesh:body/>
   		</div>
		<%@include file="/WEB-INF/views/modules/front/layouts/util/footer.jsp" %>
	</body>
</html>