<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>加息券类型管理</title>
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
		<li class="active"><a href="${ctx}/sys/rateTicketType/">加息券类型列表</a></li>
		<shiro:hasPermission name="sys:rateTicketType:edit"><li><a href="${ctx}/sys/rateTicketType/form">加息券类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="rateTicketType" action="${ctx}/sys/rateTicketType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>加息券名称：</label>
				<form:input path="ticketTypeName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>加息：</label>
				<form:input path="rate" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>加息期限：</label>
				<form:input path="rateDuration" htmlEscape="false" maxlength="11" class="input-medium"/>
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
				<th>加息券名称</th>
				<th>加息</th>
				<th>加息期限（天）</th>
				<th>额度上限（元）</th>
				<th>有效期限制（天）</th>
				<th>状态</th>
				<shiro:hasPermission name="sys:rateTicketType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rateTicketType">
			<tr>
				<td><a href="${ctx}/sys/rateTicketType/form?id=${rateTicketType.id}">
					${rateTicketType.ticketTypeName}
				</a></td>
				<td>
					${rateTicketType.rate}
				</td>
				<td>
					${rateTicketType.rateDuration}
				</td>
				<td>
					<fmt:formatNumber value="${rateTicketType.maxAmount }" pattern="#.#"/>
				</td>
				<td>
					${rateTicketType.termOfValidity}
				</td>
				<td>
					${fns:getDictLabel(rateTicketType.status, 'del_flag', '')}
				</td>
				<shiro:hasPermission name="sys:rateTicketType:edit"><td>
    				<a href="${ctx}/sys/rateTicketType/form?id=${rateTicketType.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>