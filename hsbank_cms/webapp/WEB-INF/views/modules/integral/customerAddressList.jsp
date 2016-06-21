<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>花生乐园用户地址管理</title>
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
		<li class="active"><a href="${ctx}/integral/customerAddress/">花生乐园用户地址列表</a></li>
		<shiro:hasPermission name="integral:customerAddress:edit"><li><a href="${ctx}/integral/customerAddress/form">花生乐园用户地址添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerAddress" action="${ctx}/integral/customerAddress/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>收件人名称：</label>
				<form:input path="showName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>收件人手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>收件人地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('integral_mall_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
				<th>收件人名称</th>
				<th>收件人手机号</th>
				<th>收件人地址</th>
				<th>收件人邮编</th>
				<th>状态</th>
				<shiro:hasPermission name="integral:customerAddress:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerAddress">
			<tr>
				<td><a href="${ctx}/integral/customerAddress/form?id=${customerAddress.id}">
					${customerAddress.accountId}
				</a></td>
				<td>
					${customerAddress.showName}
				</td>
				<td>
					${customerAddress.mobile}
				</td>
				<td>
					${customerAddress.address}
				</td>
				<td>
					${customerAddress.postCode}
				</td>
				<td>
					${fns:getDictLabel(customerAddress.status, 'integral_mall_status_dict', '')}
				</td>
				<shiro:hasPermission name="integral:customerAddress:edit"><td>
    				<a href="${ctx}/integral/customerAddress/form?id=${customerAddress.id}">修改</a>
					<a href="${ctx}/integral/customerAddress/delete?id=${customerAddress.id}" onclick="return confirmx('确认要删除该花生乐园用户地址吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>