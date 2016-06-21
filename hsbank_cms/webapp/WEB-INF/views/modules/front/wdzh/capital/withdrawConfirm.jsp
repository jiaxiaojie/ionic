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
			function toWithdraw() {
				$("#withdrawForm").submit();
				$("#popup").css("display", "block");
				checkHasReceivedMes();
			}
			
			//定时刷新检查是否收到易宝的通知
			function checkHasReceivedMes() {
				var targetUrl = "${pageContext.request.contextPath}/yeepay/gateWay/callback/hasReceivedMes?requestNo=${requestNo}";
				var interval = window.setInterval(function(){
					$.get(targetUrl,function(hasReceived){
						if(hasReceived) {
							window.location.href = "${ctxFront}/customer/summary";
							window.clearInterval(interval);
						}
					});
				},1000);
			}
			function closePop() {
				$("#popup").hide()
			}
		</script>
	</head>
	<body>
		<div class="content980">
			<div class="zjgl_xxqr">
		    	<div class="xxqr_title">前往易宝支付进行提现</div>
		    	<div class="xxqr_info">
		        	<dl class="formList clearfix">
		                <dt>姓名：</dt>
		                <dd class="center">${customerBase.customerName }</dd>
		            </dl>
		        	<dl class="formList clearfix">
		                <dt>卡号：</dt>
		                <dd class="center">${customerBankCard.cardNo }</dd>
		            </dl>
		        	<dl class="formList clearfix">
		                <dt>银行：</dt>
		                <dd class="center">${fns:getDictLabel(customerBankCard.bankCode,'yeepay_bank_code_dict','')}</dd>
		            </dl>
		        </div>
		        <div class="xxqr_tips"></div>
		        <div class="div_height_25"></div>
		        <form id="withdrawForm" action="${withdrawUrl }" method="post" target="_blank">
		        	<textarea name="req" style="display:none;">${req }</textarea>
		        	<input type="hidden" name="sign" value="${sign }"/>
		        </form>
		        <div class="btn_brown_158x38"><a href="javascript:;" onclick="toWithdraw()">前往提现</a></div>
		    </div>
		    
		    <div class="bottom-grain"></div>
		</div>
		
		<!--请前往新打开的页面完成充值 弹窗 默认为display:none，显示出来为display:block-->
		<div id="popup" class="pop_bg" style="display:none">
			<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
		    <div class="pop_main" style=" width:530px; height:178px; margin-left:-265px; margin-top:-79px;">
		        <div class="pop_title">请前往新打开的页面完成充值<a href="javascript:;" onclick="closePop()" class="close_pop"></a></div>
		        <div class="pop_content">
		            <div class="btn_group_one">
		                <a href="${ctxFront }/customer/capital/record" class="btn_brown_158x31">已完成提现</a>
		                <a href="${ctxFront }/customer/capital/withdraw" class="btn_blue_158x31">提现遇到问题</a>
		            </div>
		        </div>
		    </div>
		</div>
	</body>
</html>