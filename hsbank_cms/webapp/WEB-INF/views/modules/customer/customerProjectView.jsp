<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员相关项目查询</title>
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
		<li class="active"><a href="${ctx}/customer/customerBalance/customerProjectView">会员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="customerBalance" action="${ctx}/customer/customerBalance/customerProjectView" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登录名：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>会员姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>用户姓名</th>
				<th>账户余额</th>
				<th>投资次数</th>
				<th>转让次数</th>
				<shiro:hasPermission name="customer:customerBalance:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerBalance">
			<tr>
				<td>
					${customerBalance.accountName}
				</td>
				<td>
					${customerBalance.customerName}
				</td>
				<td>
					${customerBalance.goldBalance}
				</td>
				<td>
					${customerBalance.investmentCount}
				</td>
				<td>
					${customerBalance.transferCount}
				</td>
				<shiro:hasPermission name="customer:customerAccount:view"><td>
    				<a href="${ctx}/project/projectBaseInfo/borrowerslist?accountId=${customerBalance.accountId}">融资项目</a>
					<a href="${ctx}/project/projectBaseInfo/investmentlist?accountId=${customerBalance.accountId}">投资项目</a>
					<a href="${ctx}/project/projectBaseInfo/transferlist?accountId=${customerBalance.accountId}">转让项目</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>