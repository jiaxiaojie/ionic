<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>参赛记录管理</title>
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
		<li class="active"><a href="${ctx}/marketing/joinMatchRecord/">参赛记录列表</a></li>
		<shiro:hasPermission name="marketing:joinMatchRecord:edit"><li><a href="${ctx}/marketing/joinMatchRecord/form">参赛记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="joinMatchRecord" action="${ctx}/marketing/joinMatchRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>队伍：</label>
				<form:input path="side" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>用户：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>活动编号：</label>
				<form:input path="activityId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>参赛时间：</label>
				<input name="opDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${joinMatchRecord.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>队伍</th>
				<th>用户</th>
				<th>活动编号</th>
				<th>参赛时间</th>
				<shiro:hasPermission name="marketing:joinMatchRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="joinMatchRecord">
			<tr>
				<td><a href="${ctx}/marketing/joinMatchRecord/form?id=${joinMatchRecord.id}">
					${fns:getDictLabel(joinMatchRecord.side, 'term', '')}
				</a></td>
				<td>
					${joinMatchRecord.accountId}
				</td>
				<td>
					${joinMatchRecord.activityId}
				</td>
				<td>
					<fmt:formatDate value="${joinMatchRecord.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="marketing:joinMatchRecord:edit"><td>
    				<a href="${ctx}/marketing/joinMatchRecord/form?id=${joinMatchRecord.id}">修改</a>
					<a href="${ctx}/marketing/joinMatchRecord/delete?id=${joinMatchRecord.id}" onclick="return confirmx('确认要删除该参赛记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>