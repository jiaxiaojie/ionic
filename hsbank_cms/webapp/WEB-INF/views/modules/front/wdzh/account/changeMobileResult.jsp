<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/util/bank_logo.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_yhkgl.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<title>修改手机号<c:choose><c:when test="${isSuccess }">成功</c:when><c:otherwise>失败</c:otherwise></c:choose></title>
		<c:if test="${isSuccess }">
		<script type="text/javascript">
			var timeout = 3;
			$(function(){
				$("#remainSeconds").html(timeout);
				var interval = window.setInterval(function(){
					timeout--;
					$("#remainSeconds").html(timeout);
					if(timeout <= 0) {
						window.location.href = "${ctxFront }/customer/account/safeCenter";
						window.clearInterval(interval);
					}
				}, 1000);
			});
		</script>
		</c:if>
	</head>
	<body>
		<div class="content980">
			<div class="zjgl_xxqr">
		    	<!--成功为蓝色文字（blue-text），失败为红色文字（red-text）;成功蓝色图标（blue_check），失败红色图标（red_check）-->
		    	<c:choose>
		    	<c:when test="${isSuccess }">
		    	<div class="xxqr_title blue-text"><i class="blue_check"></i>修改手机号成功！</div>
		        <div class="zhgl_wxkt">
		        	<p>页面会在<span id="remainSeconds" style="color:red;"></span>秒钟后跳转至安全中心。如果没有跳转，请直接点击</p>
		        </div>
		    	</c:when>
		    	<c:otherwise>
		    	<div class="xxqr_title red-text"><i class="red_check"></i>修改手机号失败！</div>
		        <div class="zhgl_wxkt">
		        	<p>修改手机号失败，请重试</p>
		        </div>
		    	</c:otherwise>
		    	</c:choose>
		        <div class="btn_text"><a href="${ctxFront }/customer/account/safeCenter">立即跳转</a></div>
		    </div>
		    
		    <div class="bottom-grain"></div>
		</div>
	</body>
</html>