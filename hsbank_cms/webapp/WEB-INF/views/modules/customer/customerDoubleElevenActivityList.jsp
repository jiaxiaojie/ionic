<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>双11活动</title>
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
		<li class="active"><a href="${ctx}/customer/customerDoubleElevenActivity/list">双11活动</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="customerDoubleElevenActivity" action="${ctx}/customer/customerDoubleElevenActivity/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('has_gived_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>账号编号</th>
				<th>姓名</th>
				<th>金额</th>
				<th>状态</th>
				<th>操作时间</th>
				<shiro:hasPermission name="customer:customerDoubleElevenActivity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerDoubleElevenActivity">
			<tr>
				<td><a href="${ctx}/customer/customerDoubleElevenActivity/form?id=${customerDoubleElevenActivity.id}">
					${customerDoubleElevenActivity.accountId}
				</a></td>
				<td>
					${customerDoubleElevenActivity.customerName}
				</td>
				<td>
					${customerDoubleElevenActivity.amount}
				</td>
				<td>
					${fns:getDictLabel(customerDoubleElevenActivity.status, 'has_gived_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${customerDoubleElevenActivity.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerDoubleElevenActivity:edit"><td>
				   <c:if test="${customerDoubleElevenActivity.status != '1' }">
				    <a href="${ctx}/customer/customerDoubleElevenActivity/give?id=${customerDoubleElevenActivity.id}">赠送现金</a>
				   </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>