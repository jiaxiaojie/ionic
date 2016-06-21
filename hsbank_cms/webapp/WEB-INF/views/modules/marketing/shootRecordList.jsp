<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>射门记录管理</title>
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
		<li class="active"><a href="${ctx}/marketing/shootRecord/">射门记录列表</a></li>
		<shiro:hasPermission name="marketing:shootRecord:edit"><li><a href="${ctx}/marketing/shootRecord/form">射门记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shootRecord" action="${ctx}/marketing/shootRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>是否进球(0:否，1:是)：</label>
				<form:select path="isGoal" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户</th>
				<th>活动编号</th>
				<th>射门时间</th>
				<th>是否进球(0:否，1:是)</th>
				<shiro:hasPermission name="marketing:shootRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shootRecord">
			<tr>
				<td><a href="${ctx}/marketing/shootRecord/form?id=${shootRecord.id}">
					${shootRecord.accountId}
				</a></td>
				<td>
					${shootRecord.activityId}
				</td>
				<td>
					<fmt:formatDate value="${shootRecord.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(shootRecord.isGoal, 'yes_no', '')}
				</td>
				<shiro:hasPermission name="marketing:shootRecord:edit"><td>
    				<a href="${ctx}/marketing/shootRecord/form?id=${shootRecord.id}">修改</a>
					<a href="${ctx}/marketing/shootRecord/delete?id=${shootRecord.id}" onclick="return confirmx('确认要删除该射门记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>