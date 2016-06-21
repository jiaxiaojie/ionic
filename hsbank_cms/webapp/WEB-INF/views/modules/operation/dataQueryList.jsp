<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据查询管理</title>
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
		<li class="active"><a href="${ctx}/operation/dataQuery/">数据查询列表</a></li>
		<shiro:hasPermission name="operation:dataQuery:edit"><li><a href="${ctx}/operation/dataQuery/form">数据查询添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="dataQuery" action="${ctx}/operation/dataQuery/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="operation:dataQuery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dataQuery">
			<tr>
				<td><a href="${ctx}/operation/dataQuery/form?id=${dataQuery.id}">
					${dataQuery.name}
				</a></td>
				<shiro:hasPermission name="operation:dataQuery:edit"><td>
    				<a href="${ctx}/operation/dataQuery/form?id=${dataQuery.id}">修改</a>
    				<a href="${ctx}/operation/dataQuery/configRow?queryId=${dataQuery.id}">配置查询列</a>
    				<a href="${ctx}/operation/dataQuery/configQueryForm?queryId=${dataQuery.id}">配置查询表单</a>
    				<a href="${ctx}/operation/dataQuery/configQueryMenu?queryId=${dataQuery.id}">关联菜单</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>