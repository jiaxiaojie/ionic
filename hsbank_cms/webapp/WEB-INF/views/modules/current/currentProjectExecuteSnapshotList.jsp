<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期项目执行快照管理</title>
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
		<li class="active"><a href="${ctx}/current/currentProjectExecuteSnapshot/">活期项目执行快照列表</a></li>
		<shiro:hasPermission name="current:currentProjectExecuteSnapshot:edit"><li><a href="${ctx}/current/currentProjectExecuteSnapshot/form">活期项目执行快照添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="currentProjectExecuteSnapshot" action="${ctx}/current/currentProjectExecuteSnapshot/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目流水号：</label>
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>已融资金额：</label>
				<form:input path="hasFinancedMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>剩余本金：</label>
				<form:input path="realPrincipal" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目流水号</th>
				<th>已融资金额</th>
				<th>剩余本金</th>
				<th>已产生利息</th>
				<th>已提取利息</th>
				<shiro:hasPermission name="current:currentProjectExecuteSnapshot:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="currentProjectExecuteSnapshot">
			<tr>
				<td><a href="${ctx}/current/currentProjectExecuteSnapshot/form?id=${currentProjectExecuteSnapshot.id}">
					${currentProjectExecuteSnapshot.projectId}
				</a></td>
				<td>
					${currentProjectExecuteSnapshot.hasFinancedMoney}
				</td>
				<td>
					${currentProjectExecuteSnapshot.realPrincipal}
				</td>
				<td>
					${currentProjectExecuteSnapshot.hasRepaidMoney}
				</td>
				<td>
					${currentProjectExecuteSnapshot.hasRedeemInterest}
				</td>
				<shiro:hasPermission name="current:currentProjectExecuteSnapshot:edit"><td>
    				<a href="${ctx}/current/currentProjectExecuteSnapshot/form?id=${currentProjectExecuteSnapshot.id}">修改</a>
					<a href="${ctx}/current/currentProjectExecuteSnapshot/delete?id=${currentProjectExecuteSnapshot.id}" onclick="return confirmx('确认要删除该活期项目执行快照吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>