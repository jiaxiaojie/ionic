<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>首页轮播图管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/cmsCarousel/">首页轮播图列表</a></li>
		<shiro:hasPermission name="cms:cmsCarousel:edit"><li><a href="${ctx}/cms/cmsCarousel/form">首页轮播图添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cmsCarousel" action="${ctx}/cms/cmsCarousel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>链接</th>
				<th>排序</th>
				<th>更新时间</th>
				<shiro:hasPermission name="cms:cmsCarousel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cmsCarousel">
			<tr>
				<td><a href="${ctx}/cms/cmsCarousel/form?id=${cmsCarousel.id}">
					${cmsCarousel.name}
				</a></td>
				<td>
					${cmsCarousel.href}
				</td>
				<td>
					${cmsCarousel.sort}
				</td>
				<td>
					<fmt:formatDate value="${cmsCarousel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cms:cmsCarousel:edit"><td>
    				<a href="${ctx}/cms/cmsCarousel/form?id=${cmsCarousel.id}">修改</a>
					<a href="${ctx}/cms/cmsCarousel/delete?id=${cmsCarousel.id}" onclick="return confirmx('确认要删除该首页轮播图吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>