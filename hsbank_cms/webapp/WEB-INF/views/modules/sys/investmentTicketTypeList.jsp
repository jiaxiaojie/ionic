<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>现金券类型管理</title>
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
		<li class="active"><a href="${ctx}/sys/investmentTicketType/">现金券类型列表</a></li>
		<shiro:hasPermission name="sys:investmentTicketType:edit"><li><a href="${ctx}/sys/investmentTicketType/form">现金券类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="investmentTicketType" action="${ctx}/sys/investmentTicketType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>现金券名称：</label>
				<form:input path="ticketTypeName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>面值：</label>
				<form:input path="denomination" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>有效期限制：</label><!-- onclick="WdatePicker({isShowClear:true});" -->
				<form:input path="termOfValidity" htmlEscape="false" maxlength="11" class="input-medium" />
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>种类编号</th>
				<th>现金券名称</th>
				<th>面值</th>
				<th>使用说明</th>
				<th>使用限制</th>
				<th>有效期限制</th>
				<th>状态</th>
				<shiro:hasPermission name="sys:investmentTicketType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="investmentTicketType">
			<tr>
				<td><a href="${ctx}/sys/investmentTicketType/form?id=${investmentTicketType.id}">
					${investmentTicketType.id}
				</a></td>
				<td>
					${investmentTicketType.ticketTypeName}
				</td>
				<td>
					${investmentTicketType.denomination}
				</td>
				<td>
					${investmentTicketType.useInfo}
				</td>
				<td>
					${investmentTicketType.useLimit}
				</td>
				<td>
					${investmentTicketType.termOfValidity}
				</td>
				<td>
					${fns:getDictLabel(investmentTicketType.status, 'del_flag', '')}
				</td>
				<shiro:hasPermission name="sys:investmentTicketType:edit"><td>
    				<a href="${ctx}/sys/investmentTicketType/form?id=${investmentTicketType.id}">修改</a>
					<a href="${ctx}/sys/investmentTicketType/delete?id=${investmentTicketType.id}" onclick="return confirmx('确认要删除该现金券类型吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>