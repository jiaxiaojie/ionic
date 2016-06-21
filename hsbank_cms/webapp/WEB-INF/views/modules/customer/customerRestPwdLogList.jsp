<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>密码重置记录管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerRestPwdLog/">密码重置记录列表</a></li>
		<shiro:hasPermission name="customer:customerRestPwdLog:edit"><li><a href="${ctx}/customer/customerRestPwdLog/form">密码重置记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerRestPwdLog" action="${ctx}/customer/customerRestPwdLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登录名：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="50" class="input-medium"/>
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
				<th>重置时间</th>
				<th>重置方式</th>
				<th>临时密码</th>
				<th>状态</th>
				<shiro:hasPermission name="customer:customerRestPwdLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerRestPwdLog">
			<tr>
				<td>
					${customerRestPwdLog.accountName}
				</td>
				<td>
					<fmt:formatDate value="${customerRestPwdLog.resetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(customerRestPwdLog.resetType, 'customer_reset_pwd_type_dict', '')}
				</td>
				<td>
					${customerRestPwdLog.tmpPwd}
				</td>
				<td>
					${fns:getDictLabel(customerRestPwdLog.status, 'customer_reset_pwd_status', '')}
				</td>
				<shiro:hasPermission name="customer:customerRestPwdLog:edit"><td>
    				<a href="${ctx}/customer/customerRestPwdLog/form?id=${customerRestPwdLog.id}">修改</a>
					<a href="${ctx}/customer/customerRestPwdLog/delete?id=${customerRestPwdLog.id}" onclick="return confirmx('确认要删除该密码重置记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>