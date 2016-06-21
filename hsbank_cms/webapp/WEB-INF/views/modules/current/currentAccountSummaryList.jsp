<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期账户总览管理</title>
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
		<li class="active"><a href="${ctx}/current/currentAccountSummary/">活期账户总览列表</a></li>
		<shiro:hasPermission name="current:currentAccountSummary:edit"><li><a href="${ctx}/current/currentAccountSummary/form">活期账户总览添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="currentAccountSummary" action="${ctx}/current/currentAccountSummary/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账户编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>累计投资金额：</label>
				<form:input path="totalInvestmentMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>当前持有本金：</label>
				<form:input path="currentPrincipal" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>账户编号</th>
				<th>累计投资金额</th>
				<th>累计获取利息</th>
				<th>累计赎回本金</th>
				<th>累计赎回利息</th>
				<th>当前持有本金</th>
				<shiro:hasPermission name="current:currentAccountSummary:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="currentAccountSummary">
			<tr>
				<td><a href="${ctx}/current/currentAccountSummary/form?id=${currentAccountSummary.id}">
					${currentAccountSummary.accountId}
				</a></td>
				<td>
					${currentAccountSummary.totalInvestmentMoney}
				</td>
				<td>
					${currentAccountSummary.totalGetInterest}
				</td>
				<td>
					${currentAccountSummary.totalRedeemPrincipal}
				</td>
				<td>
					${currentAccountSummary.totalRedeemInterest}
				</td>
				<td>
					${currentAccountSummary.currentPrincipal}
				</td>
				<shiro:hasPermission name="current:currentAccountSummary:edit"><td>
    				<a href="${ctx}/current/currentAccountSummary/form?id=${currentAccountSummary.id}">修改</a>
					<a href="${ctx}/current/currentAccountSummary/delete?id=${currentAccountSummary.id}" onclick="return confirmx('确认要删除该活期账户总览吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>