<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投资排行日历管理</title>
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
		<li class="active"><a href="${ctx}/project/projectInvestmentRankCalendar/">投资排行日历列表</a></li>
		<shiro:hasPermission name="project:projectInvestmentRankCalendar:edit"><li><a href="${ctx}/project/projectInvestmentRankCalendar/form">投资排行日历添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="projectInvestmentRankCalendar" action="${ctx}/project/projectInvestmentRankCalendar/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>年份：</label>
				<form:input path="year" htmlEscape="false" maxlength="6" class="input-medium"/>
			</li>
			<li><label>周数：</label>
				<form:input path="week" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>年份</th>
				<th>周数</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>状态</th>
				<shiro:hasPermission name="project:projectInvestmentRankCalendar:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectInvestmentRankCalendar">
			<tr>
				<td><a href="${ctx}/project/projectInvestmentRankCalendar/form?id=${projectInvestmentRankCalendar.id}">
					${projectInvestmentRankCalendar.year}
				</a></td>
				<td>
					${projectInvestmentRankCalendar.week}
				</td>
				<td>
					<fmt:formatDate value="${projectInvestmentRankCalendar.beginDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${projectInvestmentRankCalendar.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${projectInvestmentRankCalendar.status}
				</td>
				<shiro:hasPermission name="project:projectInvestmentRankCalendar:edit"><td>
    				<a href="${ctx}/project/projectInvestmentRankCalendar/form?id=${projectInvestmentRankCalendar.id}">修改</a>
					<a href="${ctx}/project/projectInvestmentRankCalendar/delete?id=${projectInvestmentRankCalendar.id}" onclick="return confirmx('确认要删除该投资排行日历吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>