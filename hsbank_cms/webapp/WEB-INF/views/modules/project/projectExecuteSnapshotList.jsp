<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同执行快照管理</title>
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
		<li class="active"><a href="${ctx}/project/projectExecuteSnapshot/">合同执行快照列表</a></li>
		<shiro:hasPermission name="project:projectExecuteSnapshot:edit"><li><a href="${ctx}/project/projectExecuteSnapshot/form">合同执行快照添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="projectExecuteSnapshot" action="${ctx}/project/projectExecuteSnapshot/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目流水号：</label>
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-medium"/>
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
				<th>借款金额</th>
				<th>已融资金额</th>
				<th>已还款金额</th>
				<th>已冻结服务费</th>
				<shiro:hasPermission name="project:projectExecuteSnapshot:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectExecuteSnapshot">
			<tr>
				<td><a href="${ctx}/project/projectExecuteSnapshot/form?id=${projectExecuteSnapshot.id}">
					${projectExecuteSnapshot.projectId}
				</a></td>
				<td>
				<fmt:formatNumber value="${projectExecuteSnapshot.financeMoney}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectExecuteSnapshot.endFinanceMoney}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectExecuteSnapshot.endRepayMoney}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectExecuteSnapshot.sumServiceCharge}" pattern="#0.00"/>
					
				</td>
				<shiro:hasPermission name="project:projectExecuteSnapshot:edit"><td>
    				<a href="${ctx}/project/projectExecuteSnapshot/form?id=${projectExecuteSnapshot.id}">修改</a>
					<a href="${ctx}/project/projectExecuteSnapshot/delete?id=${projectExecuteSnapshot.id}" onclick="return confirmx('确认要删除该合同执行快照吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>