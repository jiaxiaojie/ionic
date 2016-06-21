<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>行为编码管理</title>
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
		<li class="active"><a href="${ctx}/marketing/messageBehaviorType/">行为编码列表</a></li>
		<shiro:hasPermission name="marketing:messageBehaviorType:edit"><li><a href="${ctx}/marketing/messageBehaviorType/form">行为编码添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="messageBehaviorType" action="${ctx}/marketing/messageBehaviorType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>行为编号：</label>
				<form:input path="behaviorCode" htmlEscape="false" maxlength="6" class="input-medium"/>
			</li>
			<li><label>行为名称：</label>
				<form:input path="behaviorName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>行为编号</th>
				<th>行为名称</th>
				<shiro:hasPermission name="marketing:messageBehaviorType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="messageBehaviorType">
			<tr>
				<td><a href="${ctx}/marketing/messageBehaviorType/form?id=${messageBehaviorType.id}">
					${messageBehaviorType.behaviorCode}
				</a></td>
				<td>
					${messageBehaviorType.behaviorName}
				</td>
				<shiro:hasPermission name="marketing:messageBehaviorType:edit"><td>
    				<a href="${ctx}/marketing/messageBehaviorType/form?id=${messageBehaviorType.id}">修改</a>
					<a href="${ctx}/marketing/messageBehaviorType/delete?id=${messageBehaviorType.id}" onclick="return confirmx('确认要删除该行为编码吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>