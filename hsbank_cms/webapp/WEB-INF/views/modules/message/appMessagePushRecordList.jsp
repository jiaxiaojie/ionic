<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户端消息推送记录管理</title>
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
		<li class="active"><a href="${ctx}/message/appMessagePushRecord/">客户端消息推送记录列表</a></li>
		<shiro:hasPermission name="message:appMessagePushRecord:edit"><li><a href="${ctx}/message/appMessagePushRecord/form">客户端消息推送记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appMessagePushRecord" action="${ctx}/message/appMessagePushRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>消息实例编号：</label>
				<form:input path="messageInstanceId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>推送时间：</label>
				<input name="pushDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${appMessagePushRecord.pushDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>消息实例编号</th>
				<th>推送时间</th>
				<shiro:hasPermission name="message:appMessagePushRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appMessagePushRecord">
			<tr>
				<td><a href="${ctx}/message/appMessagePushRecord/form?id=${appMessagePushRecord.id}">
					${appMessagePushRecord.messageInstanceId}
				</a></td>
				<td>
					<fmt:formatDate value="${appMessagePushRecord.pushDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="message:appMessagePushRecord:edit"><td>
    				<a href="${ctx}/message/appMessagePushRecord/form?id=${appMessagePushRecord.id}">修改</a>
					<a href="${ctx}/message/appMessagePushRecord/delete?id=${appMessagePushRecord.id}" onclick="return confirmx('确认要删除该客户端消息推送记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>