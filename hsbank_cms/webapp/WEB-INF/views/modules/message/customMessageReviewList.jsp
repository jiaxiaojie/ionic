<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审批消息管理</title>
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
		<li class="active"><a href="${ctx}/message/customMessage/reviewList">审批消息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="customMessage" action="${ctx}/message/customMessage/reviewList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>消息类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
				<li><label>目标类型：</label>
				<form:select path="targetType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('photo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>消息状态：</label>
				<form:select path="status" class="input-medium" disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('custom_message_stauts')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>标题</th>
				<th>接收用户类型</th>
				<th>消息类型</th>
				<!-- <th>标签</th> -->
				<th>是否紧急</th>
				<th>目标类型</th>
				<th>目标参数</th>
			  <!--   <th>是否可点击</th> -->
				<th>预计发送时间</th>
				<th>创建时间</th>
				<th>状态</th>
				<shiro:hasPermission name="message:customMessage:review"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customMessage">
			<tr>
				<td><a href="${ctx}/message/customMessage/reviewForm?id=${customMessage.id}">
					${customMessage.title}
				</a></td>
				<td>
					${fns:getDictLabel(customMessage.receiverType, 'custom_message_receiver_type', '')}
				</td>
				<td>
					${fns:getDictLabel(customMessage.type, 'message_type', '')}
				</td>
				<%-- <td>
					${customMessage.label}
				</td> --%>
				<td>
				${fns:getDictLabel(customMessage.isUrgent, 'yes_no', '')}
				</td>
				<td>
				${fns:getDictLabel(customMessage.targetType, 'photo_type', '')}
				</td>
				<td>
				${customMessage.target}
				</td>
				<%-- <td>
				${fns:getDictLabel(customMessage.isClick, 'yes_no', '')}
				</td> --%>
				<td>
					<fmt:formatDate value="${customMessage.sendDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${customMessage.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
					<td>
					${fns:getDictLabel(customMessage.status, 'custom_message_stauts', '')}
				</td>
				<shiro:hasPermission name="message:customMessage:review"><td>
    				<a href="${ctx}/message/customMessage/reviewForm?id=${customMessage.id}">审批</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>