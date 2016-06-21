<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务日志管理</title>
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
		<li class="active"><a href="${ctx}/log/logTimerTask/">定时任务日志列表</a></li>
		<shiro:hasPermission name="log:logTimerTask:edit"><li><a href="${ctx}/log/logTimerTask/form">定时任务日志添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="logTimerTask" action="${ctx}/log/logTimerTask/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<li><label>项目名称：</label>
				<form:input path="taskName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>任务时间：</label>
				<input name="beginTime"  id="d4311"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${logTimerTask.beginTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'d4312\')}',isShowClear:true});"/> - 
				<input name="endTime" id="d4312" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${logTimerTask.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d4311\')}',isShowClear:true});"/>
			</li>
			
			<li><label class="control-label">任务状态：</label>
			  <form:select path="status" class="input-xlarge required">
					<form:option value="" label="全部"/>
					<form:options path="status" items="${fns:getDictList('timer_task_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>任务名称</th>
				<th>任务开始时间</th>
				<th>任务结束时间</th>
				<th>任务执行状态</th>
				<th>任务运行信息</th>
 				<shiro:hasPermission name="log:logTimerTask:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="logTimerTask">
			<tr>
			<td><a href="${ctx}/log/logTimerTask/form?id=${logTimerTask.id}">
					${logTimerTask.taskName}
				</a></td>
				<td>
					<fmt:formatDate value="${logTimerTask.beginDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				<fmt:formatDate value="${logTimerTask.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				${fns:getDictLabel(logTimerTask.status, 'timer_task_result', '')}
				</td>
				<td>
					${logTimerTask.remark}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>