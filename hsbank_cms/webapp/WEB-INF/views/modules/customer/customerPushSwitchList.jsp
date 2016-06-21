<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户接收push消息开关管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerPushSwitch/">用户接收push消息开关列表</a></li>
		<shiro:hasPermission name="customer:customerPushSwitch:edit"><li><a href="${ctx}/customer/customerPushSwitch/form">用户接收push消息开关添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerPushSwitch" action="${ctx}/customer/customerPushSwitch/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>渠道：</label>
				<form:select path="pushChannel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>账户编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>是否接收：</label>
				<form:select path="isReceive" class="input-medium">
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
				<th>渠道</th>
				<th>账户编号</th>
				<th>是否接收</th>
				<shiro:hasPermission name="customer:customerPushSwitch:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerPushSwitch">
			<tr>
				<td><a href="${ctx}/customer/customerPushSwitch/form?id=${customerPushSwitch.id}">
					${fns:getDictLabel(customerPushSwitch.pushChannel, 'op_term_dict', '')}
				</a></td>
				<td>
					${customerPushSwitch.accountId}
				</td>
				<td>
					${fns:getDictLabel(customerPushSwitch.isReceive, 'yes_no', '')}
				</td>
				<shiro:hasPermission name="customer:customerPushSwitch:edit"><td>
    				<a href="${ctx}/customer/customerPushSwitch/form?id=${customerPushSwitch.id}">修改</a>
					<a href="${ctx}/customer/customerPushSwitch/delete?id=${customerPushSwitch.id}" onclick="return confirmx('确认要删除该用户接收push消息开关吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>