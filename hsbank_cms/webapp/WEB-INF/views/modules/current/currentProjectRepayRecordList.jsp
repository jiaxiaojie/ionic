<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期产品付款记录管理</title>
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
		<li class="active"><a href="${ctx}/current/currentProjectRepayRecord/">活期产品付款记录列表</a></li>
		<shiro:hasPermission name="current:currentProjectRepayRecord:edit"><li><a href="${ctx}/current/currentProjectRepayRecord/form">活期产品付款记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="currentProjectRepayRecord" action="${ctx}/current/currentProjectRepayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目流水号：</label>
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>本金：</label>
				<form:input path="principal" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>利息：</label>
				<form:input path="interest" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>付款日期：</label>
				<input name="beginRepayDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${currentProjectRepayRecord.beginRepayDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endRepayDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${currentProjectRepayRecord.endRepayDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>项目流水号</th>
				<th>本金</th>
				<th>利息</th>
				<th>付款日期</th>
				<shiro:hasPermission name="current:currentProjectRepayRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="currentProjectRepayRecord">
			<tr>
				<td><a href="${ctx}/current/currentProjectRepayRecord/form?id=${currentProjectRepayRecord.id}">
					${currentProjectRepayRecord.projectId}
				</a></td>
				<td>
					${currentProjectRepayRecord.principal}
				</td>
				<td>
					${currentProjectRepayRecord.interest}
				</td>
				<td>
					<fmt:formatDate value="${currentProjectRepayRecord.repayDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="current:currentProjectRepayRecord:edit"><td>
    				<a href="${ctx}/current/currentProjectRepayRecord/form?id=${currentProjectRepayRecord.id}">修改</a>
					<a href="${ctx}/current/currentProjectRepayRecord/delete?id=${currentProjectRepayRecord.id}" onclick="return confirmx('确认要删除该活期产品付款记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>