<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zjgl_cz.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/tipso.css" /><!--提示框样式-->
		<script type="text/javascript" src="${ctxStatic}/modules/front/js/tipso.js"></script><!--提示框JS-->
		<title>赎回本金</title>
		<script type="text/javascript">
			function apply() {
				$("#inputForm").submit();
			}
			$(function() {
				$("#inputForm").validate({
					rules: {principal: {required: true, digits: true, min: 1, max: "${currentProjectHoldInfo.principal - currentProjectHoldInfo.applyRedeemPrincipal }"}},
					messages: {principal: {required: "请输入赎回金额.", digits: "请输入正确的赎回金额，必须为1的整数倍.", min: "请输入正确的赎回金额.", max: "赎回金额不能大于可赎回金额."}},
					errorClass: "tips",
					errorLabelContainer: "dd.tips"
				});
			});
		</script>
	</head>
	<body>
		
		<div class="bg_789_top"></div>
		<div class="wdzh-main extract">
			<div class="wdzh-title">
		    	<span>赎回本金：<em style="color:#999;">${currentProjectInfo.name }</em></span>
		        <div class="pull-right extract_title_right"><span class="red-text">*</span> 您赎出的本金将会回到您的可用余额</div>
		    </div>
		    <div class="wdzh-content">
		    	<div class="wdzh-ktzh zjgl_cz">
		        	<form id="inputForm" action="${ctxFront }/currentAccount/applyRedeemPrincipal" method="post">
		        		<input type="hidden" name="projectId" value="${currentProjectInfo.id }" />
		                <dl class="formList clearfix">
		                    <dt>可赎回本金</dt>
		                    <dd class="center">
		                    	<span class="orange-text f20"><fmt:formatNumber value="${currentProjectHoldInfo.principal - currentProjectHoldInfo.applyRedeemPrincipal }" pattern="##0.00" /></span>元
	                    	</dd>
		                </dl>
		                <dl class="formList clearfix">
		                    <dt><span class="red-text red_star"></span>输入赎回金额</dt>
		                    <dd class="center input_box"><input id="principal" name="principal" type="text" value="" /><span class="yuan">元</span></dd>
		                    <dd class="tips" style="height:40px; line-height:40px;"></dd>
		                </dl>
		                <div class="cz-btn"><a href="javascript:void(0);" onclick="apply()" class="btn_orange_134x31">赎回本金</a></div>
		          </form>
		        </div>
		        <div class="bottom-grain"></div>
		        <div class="buttom-tips">
		        	<div class="tips-top"></div>
		            <div class="tips-center">
						<dl>
	                        <dt>温馨提示</dt>
	                        <dd></dd>
	                        <dd>1.活花生的收益每日结算，转入当日开始计息，每日利息在次日结算。</dd>
	                        <dd>2.活花生支持随时发起赎回，每日不限赎回次数，但是赎回金额必须为1元的整数倍，每人每天赎回限额5万元，赎回的资金当日没有利息。</dd>
	                        <dd>3.申请赎回的资金半小时内到账。</dd>
	                        <dd>4.若遇巨额赎回情况，花生金服有权拒绝客户当日的转出申请，您可以次日继续申请转出，具体以公司通知为准。</dd>
	                    </dl>
		            </div>
		        	<div class="tips-bottom"></div>
		        </div>
		    </div>
		</div>
		<div class="bg_789_bottom"></div>
		<!--提示框JS-->
		<script type="text/javascript">
		$(function() {
		
			// 1
			$('.tip1').tipso({
				useTitle: false
			});
			
		});
		</script>
	</body>
</html>