<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户端缓存信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerClientToken/">客户端缓存信息列表</a></li>
		<shiro:hasPermission name="customer:customerClientToken:edit"><li><a href="${ctx}/customer/customerClientToken/form">客户端缓存信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerClientToken" action="${ctx}/customer/customerClientToken/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员编号：</label>
				<form:input path="customerId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>最后一次更改时间：</label>
				<input name="lastDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerClientToken.lastDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>会员编号</th>
				<th>令牌</th>
				<th>最后一次更改时间</th>
				<shiro:hasPermission name="customer:customerClientToken:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerClientToken">
			<tr>
				<td><a href="${ctx}/customer/customerClientToken/form?id=${customerClientToken.id}">
					${customerClientToken.customerId}
				</a></td>
				<td>
					${customerClientToken.token}
				</td>
				<td>
					<fmt:formatDate value="${customerClientToken.lastDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerClientToken:edit"><td>
    				<a href="${ctx}/customer/customerClientToken/form?id=${customerClientToken.id}">修改</a>
					<a href="${ctx}/customer/customerClientToken/delete?id=${customerClientToken.id}" onclick="return confirmx('确认要删除该客户端缓存信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>