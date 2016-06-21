<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信用等级信息管理</title>
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
		<li class="active"><a href="${ctx}/sys/creditLevelInfo/">信用等级信息列表</a></li>
		<shiro:hasPermission name="sys:creditLevelInfo:edit"><li><a href="${ctx}/sys/creditLevelInfo/form">信用等级信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="creditLevelInfo" action="${ctx}/sys/creditLevelInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>信用等级：</label>
				<form:input path="creditLevel" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>得分范围最小值</th>
				<th>得分范围最大值</th>
				<th>信用等级</th>
				<th>信用额度</th>
				<shiro:hasPermission name="sys:creditLevelInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="creditLevelInfo">
			<tr>
				<td><a href="${ctx}/sys/creditLevelInfo/form?id=${creditLevelInfo.id}">
					${creditLevelInfo.minScore}
				</a></td>
				<td>
					${creditLevelInfo.maxScore}
				</td>
				<td>
					${creditLevelInfo.creditLevel}
				</td>
				<td>
					${creditLevelInfo.creditLimit}
				</td>
				<shiro:hasPermission name="sys:creditLevelInfo:edit"><td>
    				<a href="${ctx}/sys/creditLevelInfo/form?id=${creditLevelInfo.id}">修改</a>
					<a href="${ctx}/sys/creditLevelInfo/delete?id=${creditLevelInfo.id}" onclick="return confirmx('确认要删除该信用等级信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>