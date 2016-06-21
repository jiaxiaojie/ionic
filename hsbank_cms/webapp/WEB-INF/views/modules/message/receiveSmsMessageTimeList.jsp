<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户接收短信时间段管理</title>
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
		<li class="active"><a href="${ctx}/message/receiveSmsMessageTime/">用户接收短信时间段列表</a></li>
		<shiro:hasPermission name="message:receiveSmsMessageTime:edit"><li><a href="${ctx}/message/receiveSmsMessageTime/form">用户接收短信时间段添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="receiveSmsMessageTime" action="${ctx}/message/receiveSmsMessageTime/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账户编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>开始时间段：</label>
				<form:input path="startTime" htmlEscape="false" class="input-medium" onclick="WdatePicker({isShowClear:true});"/>
			</li>
			<li><label>结束时间段：</label>
				<form:input path="endTime" htmlEscape="false" class="input-medium" onclick="WdatePicker({isShowClear:true});"/>
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
				<th>开始时间段</th>
				<th>结束时间段</th>
				<shiro:hasPermission name="message:receiveSmsMessageTime:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="receiveSmsMessageTime">
			<tr>
				<td><a href="${ctx}/message/receiveSmsMessageTime/form?id=${receiveSmsMessageTime.id}">
					${receiveSmsMessageTime.accountId}
				</a></td>
				<td>
					${receiveSmsMessageTime.startTime}
				</td>
				<td>
					${receiveSmsMessageTime.endTime}
				</td>
				<shiro:hasPermission name="message:receiveSmsMessageTime:edit"><td>
    				<a href="${ctx}/message/receiveSmsMessageTime/form?id=${receiveSmsMessageTime.id}">修改</a>
					<a href="${ctx}/message/receiveSmsMessageTime/delete?id=${receiveSmsMessageTime.id}" onclick="return confirmx('确认要删除该用户接收短信时间段吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>