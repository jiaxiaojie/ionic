<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/util/bank_logo.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_yhkgl.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<title>
		<c:choose>
		<c:when test="${code==1}">
			解绑银行卡成功
		</c:when>
		<c:when test="${code==2 }">
			银行卡预约解绑成功
		</c:when>
		<c:when test="${code==3 }">
			操作失败！
		</c:when>
		<c:otherwise>解绑银行卡失败</c:otherwise></c:choose>
		
		</title>
		
		<c:if test="${code==1}">
		<script type="text/javascript">
	
			var timeout = 3;
			$(function(){
				$("#remainSeconds").html(timeout);
				var interval = window.setInterval(function(){
					timeout--;
					$("#remainSeconds").html(timeout);
					if(timeout <= 0) {
						window.location.href = "${ctxFront }/customer/account/bankCard";
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
		    	
		    	<c:when test="${code==1 }">
		    		<div class="xxqr_title blue-text"><i class="blue_check"></i>解绑银行卡成功！</div>
			        <div class="zhgl_wxkt">
			        		<p>您已成功解绑银行卡，<span id="remainSeconds" style="color:red;"></span>秒钟后会跳转至我的账户，如果没有跳转，请直接点击</p>
			        </div>
		    	</c:when>
		    	<c:when test="${code==2 }">
			    	<div class="xxqr_title blue-text"><i class="blue_check"></i>预约解绑银行卡成功！</div>
			        <div class="zhgl_wxkt">
			        	<p>
			        	<c:if test="${paySwift == 'UPGRADE' }">
			        		您是快捷绑卡用户，
			        	</c:if>
			        	银行卡预约解绑已经成功，
			        	<c:if test="${paySwift == 'UPGRADE' }">
			        		2个自然日自动生效，
			        	</c:if>
			        	请勿重复提交申请 </p>
			        </div>
		    	</c:when>
		    	<c:when test="${code==0 }">
			    	<div class="xxqr_title blue-text"><i class="blue_check"></i>解绑银行卡失败！</div>
			        <div class="zhgl_wxkt">
			        	<p>未找到要解绑的银行卡信息，请联系客服！</p>
			        </div>
		    	</c:when>
		    	<c:when test="${code==3 }">
			    	<div class="xxqr_title blue-text"><i class="blue_check"></i>操作失败！</div>
			        <div class="zhgl_wxkt">
			        	<p>操作失败，请联系客服！</p>
			        </div>
		    	</c:when>
		    	</c:choose>
		        <div class="btn_text"><a href="${ctxFront }/customer/account/bankCard">立即跳转</a></div>
		    </div>
		    
		    <div class="bottom-grain"></div>
		</div>
		
	</body>
</html>