<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/index/zxgg/article.css?${version }" rel="stylesheet"/>
		<title></title>
	</head>
	<body>
		<div id="content_area">
			<div class="div_width_980 detail">
				<h3 class="detail_title">${article.title}</h3>
				<c:if test="${not empty article.description}"><div>摘要：${article.description}</div></c:if>
				<div>${article.articleData.content}</div>
				<div class="bottom_info"></div>
		     </div>
		     <div class="div_width_980">
				<div id="comment" class="hide span10">
					正在加载评论...
				</div>
		    </div>
		</div>
	</body>
</html>