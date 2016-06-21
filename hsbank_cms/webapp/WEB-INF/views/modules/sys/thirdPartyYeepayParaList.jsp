<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>托管账号参数管理</title>
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
		<li class="active"><a href="${ctx}/sys/thirdPartyYeepayPara/">托管账号参数列表</a></li>
		<shiro:hasPermission name="sys:thirdPartyYeepayPara:edit"><li><a href="${ctx}/sys/thirdPartyYeepayPara/form">托管账号参数添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="thirdPartyYeepayPara" action="${ctx}/sys/thirdPartyYeepayPara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="sys:thirdPartyYeepayPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="thirdPartyYeepayPara">
			<tr>
				<shiro:hasPermission name="sys:thirdPartyYeepayPara:edit"><td>
    				<a href="${ctx}/sys/thirdPartyYeepayPara/form?id=${thirdPartyYeepayPara.id}">修改</a>
					<a href="${ctx}/sys/thirdPartyYeepayPara/delete?id=${thirdPartyYeepayPara.id}" onclick="return confirmx('确认要删除该托管账号参数吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>