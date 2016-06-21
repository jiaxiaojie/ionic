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
			function toSubmit() {
				$("#popup").css("display", "block");
				$("#inputForm").submit();
				checkHasReceivedMes();
			}
			function closePop() {
				$("#popup").hide()
			}
			//定时刷新检查是否收到易宝的通知
			function checkHasReceivedMes() {
				var targetUrl = "${pageContext.request.contextPath}/yeepay/gateWay/callback/hasReceivedMes?requestNo=${requestNo}";
				var interval = window.setInterval(function(){
					$.get(targetUrl,function(hasReceived){
						if(hasReceived) {
							window.location.href = "${ctxFront}/customer/investment/project_cyz";
							window.clearInterval(interval);
						}
					});
				},1000);
			}
		</script>
	</head>
	<body>
	<div class="content980">
		<div class="zjgl_xxqr">
	    	<div class="xxqr_title">前往易宝支付进行投资支付</div>
	    	<div class="xxqr_info">
	        	<dl class="formList clearfix">
	                <dt>项目名称：</dt>
	                <dd class="center">${p2p:abbrev(projectBaseInfo.projectName, 100) }</dd>
	            </dl>
	        	<dl class="formList clearfix">
	                <dt>年利率：</dt>
	                <dd class="center"><fmt:formatNumber value="${projectBaseInfo.annualizedRate }" type="percent" maxFractionDigits="1" /></dd>
	            </dl>
	        	<dl class="formList clearfix">
	                <dt>项目周期：</dt>
	                <dd class="center">${projectBaseInfo.projectDuration }个月</dd>
	            </dl>
	        	<dl class="formList clearfix">
	                <dt>投资金额：</dt>
	                <dd class="center"><fmt:formatNumber value="${amount }" pattern="#0.00"/>元</dd>
	            </dl>
	        	<dl class="formList clearfix">
	                <dt>应付金额：</dt>
	                <dd class="center"><fmt:formatNumber value="${actualAmount }" pattern="#0.00"/>元</dd>
	            </dl>
	        </div>
	        <div class="xxqr_tips"></div>
	        <div class="div_height_25"></div>
	        <form id="inputForm" action="${yeepayURL }" method="post" target="_blank">
	        	<textarea name="req" style="display:none;">${req }</textarea>
	        	<input type="hidden" name="sign" value="${sign }"/>
	        </form>
		    <div class="btn_brown_158x38"><a href="javascript:void(0);" onclick="toSubmit()">立即支付</a></div>
	    </div>
	    
	    <div class="bottom-grain"></div>
	</div>

<!--请前往新打开的页面完成投资付款 弹窗 默认为display:none，显示出来为display:block-->
<div id="popup" class="pop_bg" style="display:none">
	<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
    <div class="pop_main" style=" width:530px; height:178px; margin-left:-265px; margin-top:-79px;">
        <div class="pop_title">请前往新打开的页面完成投资支付<a href="javascript:void(0);" onclick="closePop()" class="close_pop"></a></div>
        <div class="pop_content">
            <div class="btn_group_one">
                <c:choose>
                   <c:when test="${type eq '1' }">
                     <a href="${ctxFront }/customer/investment/project_tbz" class="btn_brown_158x31">已完成投资支付</a>
                   </c:when>
                   <c:otherwise>
                     <a href="${ctxFront }/customer/investment/project_cyz" class="btn_brown_158x31">已完成投资支付</a>
                   </c:otherwise>
                </c:choose>
                <a href="${ctxFront }/wytz" class="btn_blue_158x31">投资支付遇到问题</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>