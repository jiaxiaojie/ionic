<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息提醒设置管理</title>
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
		<li class="active"><a href="${ctx}/message/messageAlertSetting/">消息提醒设置列表</a></li>
		<shiro:hasPermission name="message:messageAlertSetting:edit"><li><a href="${ctx}/message/messageAlertSetting/form">消息提醒设置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="messageAlertSetting" action="${ctx}/message/messageAlertSetting/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_alert_purpose')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>内容：</label>
				<form:input path="content" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="1000" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDt" type="text" readonly="readonly"  class="input-medium Wdate"
					value="<fmt:formatDate value="${messageAlertSetting.createDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
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
				<th>类型</th>
				<th>手机号</th>
				<th>创建时间</th>
				<shiro:hasPermission name="message:messageAlertSetting:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="messageAlertSetting">
			<tr>
				<td><a href="${ctx}/message/messageAlertSetting/form?id=${messageAlertSetting.id}">
							${messageAlertSetting.title}
				</a></td>
				<td>
						${fns:getDictLabel(messageAlertSetting.type, 'message_alert_purpose', '')}
				</td>
				<td>
					${messageAlertSetting.mobile}
				</td>
				<td>
					<fmt:formatDate value="${messageAlertSetting.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="message:messageAlertSetting:edit"><td>
    				<a href="${ctx}/message/messageAlertSetting/form?id=${messageAlertSetting.id}">修改</a>
					<a href="${ctx}/message/messageAlertSetting/delete?id=${messageAlertSetting.id}" onclick="return confirmx('确认要删除该消息提醒设置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>