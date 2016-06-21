<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息产生规则管理</title>
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
		<li class="active"><a href="${ctx}/message/messageCreateRule/list">消息产生规则列表</a></li>
		<shiro:hasPermission name="message:messageCreateRule:edit"><li><a href="${ctx}/message/messageCreateRule/form">消息产生规则添加</a></li></shiro:hasPermission>
	</ul>
	<jsp:include page="../customer/common/customerMessageMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="messageCreateRule" action="${ctx}/message/messageCreateRule/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>消息类型：</label>
				<form:select path="messageType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_rule_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>名称</th>
				<th>消息类型</th>
				<th>标签</th>
				<th>状态</th>
				<th>开始日期</th>
				<th>结束日期</th>
				<th>实现类名</th>
				<shiro:hasPermission name="message:messageCreateRule:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="messageCreateRule">
		<input type="hidden" name="ruleId" value="${messageCreateRule.id}"/>
		<input type="hidden" name="ruleName" value="${messageCreateRule.name}"/>
			<tr>
				<td><a href="${ctx}/message/messageCreateRule/form?id=${messageCreateRule.id}">
					${messageCreateRule.name}
				</a></td>
				<td>
					${fns:getDictLabel(messageCreateRule.messageType, 'message_type', '')}
				</td>
				<td>
					${messageCreateRule.label}
				</td>
				<td>
					${fns:getDictLabel(messageCreateRule.status, 'message_rule_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${messageCreateRule.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${messageCreateRule.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${messageCreateRule.className}
				</td>
				<shiro:hasPermission name="message:messageCreateRule:edit"><td>
					
    				<a href="${ctx}/message/messageCreateRule/form?id=${messageCreateRule.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
</body>
</html>