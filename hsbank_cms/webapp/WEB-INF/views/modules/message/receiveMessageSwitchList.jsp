<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户接收消息开关管理</title>
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
		<li class="active"><a href="${ctx}/message/receiveMessageSwitch/">用户接收消息开关列表</a></li>
		<shiro:hasPermission name="message:receiveMessageSwitch:edit"><li><a href="${ctx}/message/receiveMessageSwitch/form">用户接收消息开关添加</a></li></shiro:hasPermission>
	</ul>
	<jsp:include page="../customer/common/customerMessageMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="receiveMessageSwitch" action="${ctx}/message/receiveMessageSwitch/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>规则编号：</label>
				<form:input path="ruleId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>渠道：</label>
				<form:input path="messageChannel" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>账户编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>是否接收：</label>
				<form:select path="isReceive" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('is_receive_message')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>规则编号</th>
				<th>渠道</th>
				<th>账户编号</th>
				<th>是否接收</th>
				<shiro:hasPermission name="message:receiveMessageSwitch:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="receiveMessageSwitch">
			<tr>
				<td><a href="${ctx}/message/receiveMessageSwitch/form?id=${receiveMessageSwitch.id}">
					${receiveMessageSwitch.ruleId}
				</a></td>
				<td>
					${receiveMessageSwitch.messageChannel}
				</td>
				<td>
					${receiveMessageSwitch.accountId}
				</td>
				<td>
					${fns:getDictLabel(receiveMessageSwitch.isReceive, 'is_receive_message', '')}
				</td>
				<shiro:hasPermission name="message:receiveMessageSwitch:edit"><td>
    				<a href="${ctx}/message/receiveMessageSwitch/form?id=${receiveMessageSwitch.id}">修改</a>
					<a href="${ctx}/message/receiveMessageSwitch/delete?id=${receiveMessageSwitch.id}" onclick="return confirmx('确认要删除该用户接收消息开关吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>