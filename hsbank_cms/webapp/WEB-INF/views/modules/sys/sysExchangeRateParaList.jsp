<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇率参数管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysExchangeRatePara/">汇率参数列表</a></li>
		<shiro:hasPermission name="sys:sysExchangeRatePara:edit"><li><a href="${ctx}/sys/sysExchangeRatePara/form">汇率参数添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysExchangeRatePara" action="${ctx}/sys/sysExchangeRatePara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>汇率名称：</label>
				<form:input path="rateName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>年化利率：</label>
				<form:input path="annualizedRate" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>汇率名称</th>
				<th>年化利率</th>
				<th>创建时间</th>
				<shiro:hasPermission name="sys:sysExchangeRatePara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysExchangeRatePara">
			<tr>
				<td><a href="${ctx}/sys/sysExchangeRatePara/form?id=${sysExchangeRatePara.id}">
					${sysExchangeRatePara.rateName}
				</a></td>
				<td>
					<fmt:formatNumber value="${sysExchangeRatePara.annualizedRate}" pattern="#0.0000" />
				</td>
				<td>
					<fmt:formatDate value="${sysExchangeRatePara.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sys:sysExchangeRatePara:edit"><td>
    				<a href="${ctx}/sys/sysExchangeRatePara/form?id=${sysExchangeRatePara.id}">修改</a>
					<a href="${ctx}/sys/sysExchangeRatePara/delete?id=${sysExchangeRatePara.id}" onclick="return confirmx('确认要删除该汇率参数吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>