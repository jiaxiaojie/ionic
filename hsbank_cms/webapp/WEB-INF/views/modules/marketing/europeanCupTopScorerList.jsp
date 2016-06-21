<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>欧洲杯射手榜管理</title>
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
		<li class="active"><a href="${ctx}/marketing/europeanCupTopScorer/">欧洲杯射手榜列表</a></li>
		<shiro:hasPermission name="marketing:europeanCupTopScorer:edit"><li><a href="${ctx}/marketing/europeanCupTopScorer/form">欧洲杯射手榜添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="europeanCupTopScorer" action="${ctx}/marketing/europeanCupTopScorer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>操作时间：</label>
				<input name="opDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${europeanCupTopScorer.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>用户编号</th>
				<th>活动编号</th>
				<th>累计进球总数</th>
				<th>已刮奖次数</th>
				<th>操作时间</th>
				<shiro:hasPermission name="marketing:europeanCupTopScorer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="europeanCupTopScorer">
			<tr>
				<td><a href="${ctx}/marketing/europeanCupTopScorer/form?id=${europeanCupTopScorer.id}">
					${europeanCupTopScorer.accountId}
				</a></td>
				<td>
					${europeanCupTopScorer.activityId}
				</td>
				<td>
					${europeanCupTopScorer.totalGoals}
				</td>
				<td>
					${europeanCupTopScorer.usedScratchTimes}
				</td>
				<td>
					<fmt:formatDate value="${europeanCupTopScorer.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="marketing:europeanCupTopScorer:edit"><td>
    				<a href="${ctx}/marketing/europeanCupTopScorer/form?id=${europeanCupTopScorer.id}">修改</a>
					<a href="${ctx}/marketing/europeanCupTopScorer/delete?id=${europeanCupTopScorer.id}" onclick="return confirmx('确认要删除该欧洲杯射手榜吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>