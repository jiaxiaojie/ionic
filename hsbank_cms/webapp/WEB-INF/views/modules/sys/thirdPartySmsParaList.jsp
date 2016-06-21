<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信通道参数管理</title>
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
		<li class="active"><a href="${ctx}/sys/thirdPartySmsPara/">短信通道参数列表</a></li>
		<shiro:hasPermission name="sys:thirdPartySmsPara:edit"><li><a href="${ctx}/sys/thirdPartySmsPara/form">短信通道参数添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="thirdPartySmsPara" action="${ctx}/sys/thirdPartySmsPara/" method="post" class="breadcrumb form-search">
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
				<th>短信通道地址</th>
				<th>短信通道账号</th>
				<th>短信通道密码</th>
				<shiro:hasPermission name="sys:thirdPartySmsPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="thirdPartySmsPara">
			<tr>
				<td><a href="${ctx}/sys/thirdPartySmsPara/form?id=${thirdPartySmsPara.id}">
					${thirdPartySmsPara.smsApiUrl}
				</a></td>
				<td>
					${thirdPartySmsPara.smsApiKey}
				</td>
				<td>
					${thirdPartySmsPara.smsSecretKey}
				</td>
				<shiro:hasPermission name="sys:thirdPartySmsPara:edit"><td>
    				<a href="${ctx}/sys/thirdPartySmsPara/form?id=${thirdPartySmsPara.id}">修改</a>
					<a href="${ctx}/sys/thirdPartySmsPara/delete?id=${thirdPartySmsPara.id}" onclick="return confirmx('确认要删除该短信通道参数吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>