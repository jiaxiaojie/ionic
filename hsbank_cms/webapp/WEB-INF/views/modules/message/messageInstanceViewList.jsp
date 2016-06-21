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
		<li class="active"><a href="${ctx}/message/messageInstance/searchPageList">消息查询</a></li>
	</ul>
	<jsp:include page="../customer/common/customerMessageMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="messageInstance" action="${ctx}/message/messageInstance/searchPageList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>消息内容：</label>
				<form:input path="content" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>消息类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>消息渠道：</label>
				<form:select path="messageChannel" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>用户名称</th>
				<th>手机号码</th>
				<th>标题</th>
				<th>消息内容</th>
				<th>消息渠道</th>
				<th>消息类型</th>
				<th>创建时间</th>
				
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="messageInstance">
			<tr>
				<td><a href="${ctx}/message/messageInstance/viewForm?id=${messageInstance.id}">
					${messageInstance.customerName}
				</a></td>
				<td>
				    ${messageInstance.mobile}
				</td>
				<td>
				    ${p2p:abbrev(messageInstance.title,50)}
				</td>
				<td>
				    ${p2p:abbrev(messageInstance.content, 50) }
				</td>
				<td>
					${fns:getDictLabel(messageInstance.messageChannel, 'message_channel', '')}
				</td>
				<td>
					${fns:getDictLabel(messageInstance.type, 'message_type', '')}
				</td>
				
				
				<td>
					<fmt:formatDate value="${messageInstance.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				
				
				
				<td>
					${fns:getDictLabel(messageInstance.status, 'message_status', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>