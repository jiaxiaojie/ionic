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
		<title>提取收益</title>
		<script type="text/javascript">
			function poll() {
				$("#inputForm").submit();
				$("#poll").removeAttr("onclick");
				setTimeout(function() {
					$("#poll").attr("onclick","poll()");
				}, 3000);
			}
			$(function() {
				$("#inputForm").validate({
					rules: {interest: {required: true, amount: true, max: '<fmt:formatNumber value="${(currentProjectHoldInfo.interest >= 0.0049) ? (currentProjectHoldInfo.interest - 0.0049) : (currentProjectHoldInfo.interest + 0.0001) }" pattern="##0.00" />'}},
					messages: {interest: {required: "请输入提取金额.", max: "提取收益金额不能大于可提取收益."}},
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
		    	<span>提取收益：<em style="color:#999;">${currentProjectInfo.name }</em></span>
		        <div class="pull-right extract_title_right"><span class="red-text">*</span> 您提取的收益将会回到您的可用余额</div>
		    </div>
		    <div class="wdzh-content">
		    	<div class="wdzh-ktzh zjgl_cz">
		        	<form id="inputForm" action="${ctxFront }/currentAccount/pollInterest" method="post">
		        		<input type="hidden" name="projectId" value="${currentProjectInfo.id }" />
		                <dl class="formList clearfix">
		                    <dt>可提取收益</dt>
		                    <dd class="center">
								<span class="orange-text f20"><fmt:formatNumber value="${(currentProjectHoldInfo.interest >= 0.0049) ? (currentProjectHoldInfo.interest - 0.0049) : (currentProjectHoldInfo.interest + 0.0001) }" pattern="##0.00" /></span>元
							</dd>
		                </dl>
		                <dl class="formList clearfix">
		                    <dt><span class="red-text red_star"></span>输入提取金额</dt>
		                    <dd class="center input_box"><input id="interest" name="interest" type="text" value="" /><span class="yuan">元</span></dd>
		                    <dd class="tips"></dd>
		                </dl>
		                <div class="cz-btn"><a href="javascript:void(0);" id="poll" onclick="poll()" class="btn_orange_134x31">提取</a></div>     
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