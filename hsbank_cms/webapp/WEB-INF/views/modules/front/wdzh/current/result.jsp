<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/util/bank_logo.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_yhkgl.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<title>${result.message }</title>
		<c:if test="${isSuccess }">
		<script type="text/javascript">
		</script>
		</c:if>
	</head>
	<body>
		<div class="content980">
			<div class="zjgl_xxqr">
		    	<div class="xxqr_title ${result.isSuccess ? 'blue-text' : 'red-text' }"><i class="${result.isSuccess ? 'blue-check' : 'red-check' }"></i>${result.message }</div>
		        <div class="zhgl_wxkt">
		        	<p>最快一个工作日到账哦！</p>
		            <div class="div_height_20"></div>
		        </div>
		        <div class=" text-center">
		        	<a href="${ctxFront}/currentAccount/myCurrentPeanut" class="btn_brown">查看我的活花生</a>
		            <div class="div_height_10"></div>
		            <c:if test="${!result.isSuccess }">
		            	<a href="javascript:;" onclick="history.back(-1)" class="brown-text">返回修改</a>
		            </c:if>
		        </div>
		    </div>
		    <div class="bottom-grain"></div>
		</div>
	</body>
</html>