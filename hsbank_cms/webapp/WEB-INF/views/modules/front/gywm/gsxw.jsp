<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="about_us"/>
		<link href="${ctxStatic}/modules/front/css/gywm/gsxw.css?${version }" rel="stylesheet"/>
		<title></title>
		<style type="text/css">
			.pagination input{width:190px;}
			.pagination a{border:0px}
		</style>
	</head>
	<body>
		<div id="content_center" class="about_right_content">
        	<div class="title"><span class="font_color_brown">公司新闻</span></div>
			<ul class="news_list">
				<c:if test="${category.module eq 'article'}">
			   <c:forEach items="${page.list}" var="article">
				<li class="news clearfix"><a href="${article.urlWithFdxw}" style="color:${article.color}"><span class="">${fns:abbr(article.title,96)}</span></a><span class="date"><fmt:formatDate value="${article.createDate}" pattern="yyyy.MM.dd"/> </span></li>
			   </c:forEach>
				<script type="text/javascript">
					function page(n,s){
						location="${ctx}/list-${category.id}${urlSuffix}?pageNo="+n+"&pageSize="+s;
					}
				</script>
			</c:if>	
			<c:if test="${category.module eq 'link'}">
				<ul><c:forEach items="${page.list}" var="link">
					<li><a href="${link.href}" target="_blank" style="color:${link.color}"><c:out value="${link.title}" /></a></li>
				</c:forEach></ul>
			</c:if>
			</ul>
		</div>
	</body>
</html>