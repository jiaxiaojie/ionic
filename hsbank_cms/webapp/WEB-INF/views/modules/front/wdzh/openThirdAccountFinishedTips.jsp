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
			var timeout = 3;
			$(function(){
				$("#remainSeconds").html(timeout);
				var interval = window.setInterval(function(){
					timeout--;
					$("#remainSeconds").html(timeout);
					if(timeout <= 0) {
						window.location.href = "${ctxFront }/customer/summary";
						window.clearInterval(interval);
					}
				}, 1000);
			});
		</script>
	</head>
	<body>
		<div class="content980">
			<div class="zjgl_xxqr">
		    	<div class="xxqr_title">您已开通易宝支付资金托管账号，无需再次开通</div>
		        <div class="zhgl_wxkt">
		        	<p>页面将会在<span id="remainSeconds" style="color:red;"></span>秒钟后跳转至我的账户首页，如果没有跳转，请点击</p>
		        </div>
		        <div class="btn_text"><a href="${ctxFront }/customer/summary">立即跳转</a></div>
		    </div>
		    
		    <div class="bottom-grain"></div>
		</div>
	</body>
</html>