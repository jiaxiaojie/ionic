<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息实例管理</title>
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
		<li class="active"><a href="${ctx}/message/messageInstance/">消息实例列表</a></li>
		<shiro:hasPermission name="message:messageInstance:edit"><li><a href="${ctx}/message/messageInstance/form">消息实例添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="messageInstance" action="${ctx}/message/messageInstance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>消息编号：</label>
				<form:input path="messageId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>渠道：</label>
				<form:select path="messageChannel" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>消息编号</th>
				<th>渠道</th>
				<th>创建时间</th>
				<th>状态</th>
				<shiro:hasPermission name="message:messageInstance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="messageInstance">
			<tr>
				<td><a href="${ctx}/message/messageInstance/form?id=${messageInstance.id}">
					${messageInstance.messageId}
				</a></td>
				<td>
					${fns:getDictLabel(messageInstance.messageChannel, 'message_channel', '')}
				</td>
				<td>
					<fmt:formatDate value="${messageInstance.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(messageInstance.status, 'message_type', '')}
				</td>
				<shiro:hasPermission name="message:messageInstance:edit"><td>
    				<a href="${ctx}/message/messageInstance/form?id=${messageInstance.id}">修改</a>
					<a href="${ctx}/message/messageInstance/delete?id=${messageInstance.id}" onclick="return confirmx('确认要删除该消息实例吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>