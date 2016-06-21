<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/util/bank_logo.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_yhkgl.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/forgetpassword.css?${version }" />
		<title>重置密码<c:choose><c:when test="${isSuccess }">成功</c:when><c:otherwise>失败</c:otherwise></c:choose></title>
	</head>
	<body>
		<div class="content980">
		    <div class="fp_area">
		        <div class="fp_content">
		        	<div class="div_height_100"></div>
		            <!--成功的图标为蓝色“blue_check”，未成功的图标为红色“red_check”-->
		            <c:choose>
			            <c:when test="${isSuccess }">
			            	<div class="text-center"><i class="blue_check noposition nomargin"></i></div>
			            </c:when>
			            <c:otherwise>
			            	<div class="text-center"><i class="red_check noposition nomargin"></i></div>
			            </c:otherwise>
		            </c:choose>
		            <div class="div_height_10"></div>
		        	<div class="text-center font_size_18">重置密码<c:choose><c:when test="${isSuccess }">成功</c:when><c:otherwise>失败</c:otherwise></c:choose></div>
		        	<div class="div_height_40"></div>
		            <div class="btn_brown_158x38"><a href="${ctxFront }/login">重新登录</a></div>
		        </div>
		    </div>
		    <div class="bottom-grain"></div>
		</div>
	</body>
</html>