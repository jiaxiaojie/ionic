<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/util/bank_logo.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_yhkgl.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<title></title>
		<script type="text/javascript">
			function confirmAuthorizeAutoRepayment() {
				$("#authorizeAutoRepaymentForm").submit();
				$("#popup").css("display", "block");
				checkHasReceivedMes();
			}
			//定时刷新检查是否收到易宝的通知
			function checkHasReceivedMes() {
				var targetUrl = "${pageContext.request.contextPath}/yeepay/gateWay/callback/hasReceivedMes?requestNo=${requestNo}";
				var interval = window.setInterval(function(){
					$.get(targetUrl,function(hasReceived){
						if(hasReceived) {
							window.location.href = "${ctxFront}/customer/loan/myFundingProject";
							window.clearInterval(interval);
						}
					});
				},1000);
			}
			function closePop() {
				$("#popup").hide();
			}
		</script>
	</head>
	<body>
		<div class="content980">
			<div class="jump_page">
		    	<div class="jump_title">前往易宝支付授权自动还款</div>
		    	<div class="xxqr_info">
		        	<dl class="formList clearfix">
		                <dt>项目名称：</dt>
		                <dd class="center">${p2p:abbrev(projectBaseInfo.projectName, 10) }</dd>
		            </dl>
		        	<dl class="formList clearfix">
		                <dt>融资金额：</dt>
		                <dd class="center"><fmt:formatNumber value="${projectBaseInfo.financeMoney }" pattern="#0.00"/>元</dd>
		            </dl>
		        	<dl class="formList clearfix">
		                <dt>融资期限：</dt>
		                <dd class="center">${projectBaseInfo.projectDuration }个月</dd>
		            </dl>
		        </div>
		        <div class="xxqr_tips">只有授权第三方支付自动还款后系统才会放款给你。</div>
		        <div class="div_height_25"></div>
		        <form id="authorizeAutoRepaymentForm" action="${authorizeAutoRepaymentUrl }" method="post" target="_blank">
		        	<textarea name="req" style="display:none;">${req }</textarea>
		        	<input type="hidden" name="sign" value="${sign }"/>
		        </form>
		        <div class="btn_brown_158x38"><a href="javascript:;" onclick="confirmAuthorizeAutoRepayment()">前往授权</a></div>
		    </div>
		    
		    <div class="bottom-grain"></div>
		</div>
	
		<!--请前往新打开的页面完成充值 弹窗 默认为display:none，显示出来为display:block-->
		<div id="popup" class="pop_bg" style="display:none">
			<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
		    <div class="pop_main" style=" width:530px; height:178px; margin-left:-265px; margin-top:-79px;">
		        <div class="pop_title">请前往新打开的页面完成第三方支付授权自动还款<a href="javascript:;" onclick="closePop()" class="close_pop"></a></div>
		        <div class="pop_content">
		            <div class="btn_group_one">
		                <a href="${ctxFront }/customer/loan/myFundingProject" class="btn_brown_158x31">已完成授权</a>
		                <a href="${ctxFront }/customer/loan/myFundingProject" class="btn_blue_158x31">授权遇到问题</a>
		            </div>
		        </div>
		    </div>
		</div>
	</body>
</html>