<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员基本信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerBase/">会员基本信息列表</a></li>
		<shiro:hasPermission name="customer:customerBase:edit"><li><a href="${ctx}/customer/customerBase/form">会员基本信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerBase" action="${ctx}/customer/customerBase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>证件类型：</label>
			
				<form:radiobuttons path="certType" items="${fns:getDictList('cert_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>会员名称</th>
				<th>证件类型</th>
				<shiro:hasPermission name="customer:customerBase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerBase">
			<tr>
				<td><a href="${ctx}/customer/customerBase/form?id=${customerBase.id}">
					${customerBase.customerName}
				</a></td>
				<td>
					${fns:getDictLabel(customerBase.certType, 'cert_type_dict', '')}
				</td>
				<shiro:hasPermission name="customer:customerBase:edit"><td>
    				<a href="${ctx}/customer/customerBase/form?id=${customerBase.id}">修改</a>
					<a href="${ctx}/customer/customerBase/delete?id=${customerBase.id}" onclick="return confirmx('确认要删除该会员基本信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>