<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账户余额变更流水管理</title>
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
		<li><a href="${ctx}/customer/customerAccountInfo/customerAccountInfoList">会员列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerAccountInfo/customerAccountInfo?accountId=${model.accountId}">会员详细信息</a></li>
	</ul><br/>
	<jsp:include page="./common/customerAccountInfoMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="CustomerAccount" action="${ctx}/customer/customerAccountInfo/customerFriendsInfo" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="accountId" name="accountId" type="hidden" value="${model.accountId}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名字</th>
				<th>账号</th>
				<th>状态</th>
				<th>注册时间</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerAccount">
			<tr>
								<td>${customerAccount.customerBase.customerName }</td>
								<td>${customerAccount.customerBase.mobile }</td>
								<td>${customerAccount.accountName }</td>
								<td><fmt:formatDate value="${customerAccount.registerDt }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>