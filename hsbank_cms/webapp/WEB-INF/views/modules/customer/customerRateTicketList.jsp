<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员加息券清单管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerRateTicket/">会员加息券清单列表</a></li>
		<shiro:hasPermission name="customer:customerRateTicket:edit"><li><a href="${ctx}/customer/customerRateTicket/form">会员加息券清单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerRateTicket" action="${ctx}/customer/customerRateTicket/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>加息券类型编号：</label>
				<form:input path="ticketTypeId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_investment_ticket_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>加息券类型编号</th>
				<th>获得时间</th>
				<th>失效时间</th>
				<th>使用时间</th>
				<th>使用项目</th>
				<th>状态</th>
				<shiro:hasPermission name="customer:customerRateTicket:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerRateTicket">
			<tr>
				<td><a href="${ctx}/customer/customerRateTicket/form?id=${customerRateTicket.id}">
					${customerRateTicket.accountId}
				</a></td>
				<td>
					${customerRateTicket.ticketTypeId}
				</td>
				<td>
					<fmt:formatDate value="${customerRateTicket.getDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${customerRateTicket.invalidDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${customerRateTicket.useDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerRateTicket.useProjectId}
				</td>
				<td>
					${fns:getDictLabel(customerRateTicket.status, 'customer_investment_ticket_dict', '')}
				</td>
				<shiro:hasPermission name="customer:customerRateTicket:edit"><td>
    				<a href="${ctx}/customer/customerRateTicket/form?id=${customerRateTicket.id}">修改</a>
					<a href="${ctx}/customer/customerRateTicket/delete?id=${customerRateTicket.id}" onclick="return confirmx('确认要删除该会员加息券清单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>