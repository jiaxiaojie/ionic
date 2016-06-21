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
	</head>
	<body>
		<div class="content980">
			<div class="zjgl_xxqr">
		    	<div class="xxqr_title red-text"><i class="red_check"></i>提现失败！</div>
		        <div class="zhgl_wxkt">
		        	<p>已超过当天提现次数限制， 请明天再试！</p>
		        </div>
		        <div class="btn_text"><a href="${ctxFront }/customer/summary">跳转至我的账户</a></div>
		    </div>
		    <div class="bottom-grain"></div>
		</div>
	</body>
</html>