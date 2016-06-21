<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运营数据管理</title>
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
		<li><a href="${ctx}/operation/projectOperation/projectRepayPlanList">项目还款计划列表</a></li>
		<li class="active"><a href="${ctx}/operation/projectOperation/projectRepayPlanDetail?date=<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" />">单日项目还款计划详情</a></li>
	</ul>
	<form id="searchForm" action="${ctx}/operation/projectOperation/projectRepayPlanList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>还款日期：</label>
				<input type="text" value="<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" />"
				onclick="WdatePicker({isShowClear:true});" readonly="true" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li> 
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目名称</th>
				<th>应还本金</th>
				<th>应还利息</th>
				<th>应还总额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="map">
			<tr>
				<td>
					${map.projectName}
				</td>
				<td>
					<fmt:formatNumber value="${map.principal}" pattern="#0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${map.interest}" pattern="#0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${map.amount}" pattern="#0.00" />
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>