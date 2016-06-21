<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/tzgl_tztj.css?${version }" rel="stylesheet"/>
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
				<span class="span_text_1">投资总额</span>
				<span class="span_text_2 font_color_orange"><fmt:formatNumber value="${customerBalance.sumInvestment }" pattern="##0.00" /></span>
				<span class="span_text_2">元</span>
			</div>
			<div class="line_02">
				<span class="required">&nbsp;&nbsp;</span>
				<span class="span_text_1">收款总额</span>
				<span class="span_text_2 font_color_orange"><fmt:formatNumber value="${customerBalance.receiveMoney }" pattern="##0.00" /></span>
				<span class="span_text_2">元</span>
			</div>
			<div class="line_03">
				<span class="span_text">
					本息：<fmt:formatNumber value="${customerBalance.receivePrincipal }" pattern="##0.00" /> 元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					逾期罚款：<fmt:formatNumber value="${customerBalance.receiveLateMoney }" pattern="##0.00" /> 元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					提前还款违约金：<fmt:formatNumber value="${customerBalance.receivePreMoney }" pattern="##0.00" /> 元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					转让收款：<fmt:formatNumber value="${customerBalance.receiveTransferMoney }" pattern="##0.00" /> 元
				</span>
			</div>
			<div class="div_height_30"></div>
			<div class="wdzh_right_title">
				<span>项目</span>
		    </div>
			<div class="div_height_10"></div>
			<div class="line_04">
				<span class="span_text">
					持有中：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="font_color_orange">${runningCount }</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					可转让：${canTransferCount }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					转让中：${transferCount }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					逾期：${overdueCount } &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					代偿中：${compensatoryRunningCount }
				</span>
			</div>
			<div class="line_04">
				<span class="span_text">
					申请中：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="font_color_orange">${congealCount }</span>
				</span>
			</div>
			<div class="line_04">
				<span class="span_text">
					已结束：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="font_color_orange">${endCount }</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					已转让：${transferEndCount }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					已代偿：${compensatoryEndCount }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					提前还款：${preRepaymentCount }
				</span>
			</div>
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
	</body>
</html>