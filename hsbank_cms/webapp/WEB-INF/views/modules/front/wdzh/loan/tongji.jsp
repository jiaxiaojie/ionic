<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/rzgl_jktj.css?${version }" rel="stylesheet"/>
		<title></title>
	</head>
	<body>
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div class="wdzh_right_title">
				<span>资金</span>
		    </div>
			<div class="div_height_10"></div>
			<div class="line_02">
				<span class="required">&nbsp;&nbsp;</span>
				<span class="span_text_1">融资总额</span>
				<span class="span_text_2 font_color_orange"><fmt:formatNumber value="${customerBalance.sumLoan }" pattern="##0.00" /></span>
				<span class="span_text_2">元</span>
			</div>
			<div class="line_02">
				<span class="required">&nbsp;&nbsp;</span>
				<span class="span_text_1">还款总额</span>
				<span class="span_text_2 font_color_orange"><fmt:formatNumber value="${customerBalance.repaymentMoney }" pattern="##0.00" /></span>
				<span class="span_text_2">元</span>
			</div>
			<div class="line_03">
				<span class="span_text">
					本息：<fmt:formatNumber value="${customerBalance.repaymentPrincipal }" pattern="##0.00" />元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					逾期罚款：<fmt:formatNumber value="${customerBalance.repaymentLateMoney }" pattern="##0.00" />元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					提前还款违约金：<fmt:formatNumber value="${customerBalance.repaymentPreMoney }" pattern="##0.00" />元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
			</div>
			<div class="div_height_30"></div>
			<div class="wdzh_right_title">
				<span>融资</span>
		    </div>
			<div class="div_height_10"></div>
			<div class="line_04">
				<span class="span_text">
					融资中：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="font_color_orange">${runningProjectCount }</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					逾期：${overdueCount }
				</span>
			</div>
			<div class="line_04">
				<span class="span_text">
					申请中：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="font_color_orange">${applyCount }</span>
				</span>
			</div>
			<div class="line_04">
				<span class="span_text">
					已结束：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="font_color_orange">${endProjectCount }</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					提前还款：${preRepayCount }
				</span>
			</div>
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
	</body>
</html>