<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息管理</title>
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
		<li class="active"><a href="${ctx}/message/message/">消息列表</a></li>
		<shiro:hasPermission name="message:message:edit"><li><a href="${ctx}/message/message/form">消息添加</a></li></shiro:hasPermission>
	</ul>
	<jsp:include page="../customer/common/customerMessageMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="message" action="${ctx}/message/message/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账户编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>来源类型：</label>
				<form:select path="fromType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_from_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>账户编号</th>
				<th>标题</th>
				<th>预计发送时间</th>
				<th>类型</th>
				<th>标签</th>
				<th>来源类型</th>
				<shiro:hasPermission name="message:message:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="message">
			<tr>
				<td><a href="${ctx}/message/message/form?id=${message.id}">
					${message.accountId}
				</a></td>
				<td>
					${message.title}
				</td>
				<td>
					<fmt:formatDate value="${message.sendDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${message.type}
				</td>
				<td>
					${fns:getDictLabel(message.label, 'message_type', '')}
				</td>
				<td>
					${fns:getDictLabel(message.fromType, 'message_from_type', '')}
				</td>
				<shiro:hasPermission name="message:message:edit"><td>
    				<a href="${ctx}/message/message/form?id=${message.id}">修改</a>
					<a href="${ctx}/message/message/delete?id=${message.id}" onclick="return confirmx('确认要删除该消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>