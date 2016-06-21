<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/index/fdxw.css?${version }" rel="stylesheet"/>
		<title></title>
	</head>
	<body>
		<div id="content_area">
			<div class="div_width_980">
				<c:if test="${category.module eq 'article'}">
					<table class="table table-hover">
						<tbody>
						<c:forEach items="${page.list}" var="article">
						<tr>
							<td width="90%"><a href="${article.urlWithFdxw}" style="color:${article.color}">${fns:abbr(article.title,96)}</a></td>
							<td width="10%"><fmt:formatDate value="${article.createDate}" pattern="yyyy.MM.dd"/></td>
						</tr>
						</c:forEach>
						</tbody>
					</table>
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
			</div>
		</div>
	</body>
</html>