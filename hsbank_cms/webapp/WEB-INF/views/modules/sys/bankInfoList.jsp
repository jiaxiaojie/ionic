<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行信息管理</title>
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
		<li class="active"><a href="${ctx}/sys/bankInfo/">银行信息列表</a></li>
		<shiro:hasPermission name="sys:bankInfo:edit"><li><a href="${ctx}/sys/bankInfo/form">银行信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bankInfo" action="${ctx}/sys/bankInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>银行代码：</label>
				<form:input path="bankCode" htmlEscape="false" maxlength="15" class="input-medium"/>
			</li>
			<li><label>银行名称：</label>
				<form:input path="bankName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('bank_info_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<!-- 
			<li><label>单笔限额：</label>
				<form:input path="shortcutIndividual" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			
			<li><label>单日限额：</label>
				<form:input path="shortcutSingleDay" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>当月限额：</label>
				<form:input path="shortcutSingleMonth" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			 -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>银行代码</th>
				<th>银行名称</th>
				<th>快捷单笔限额</th>
				<th>快捷单日限额</th>
				<th>快捷当月限额</th>
				<th>状态</th>
				<shiro:hasPermission name="sys:bankInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bankInfo">
			<tr>
				<td><a href="${ctx}/sys/bankInfo/form?id=${bankInfo.id}">
					${bankInfo.bankCode}
				</a></td>
				<td>
					${bankInfo.bankName}
				</td>
				<td>
					${bankInfo.shortcutIndividual}
				</td>
				<td>
					${bankInfo.shortcutSingleDay}
				</td>
				<td>
					${bankInfo.shortcutSingleMonth}
				</td>
				<td>
					${fns:getDictLabel(bankInfo.status, 'bank_info_status_dict', '')}
				</td>
				<shiro:hasPermission name="sys:bankInfo:edit"><td>
    				<a href="${ctx}/sys/bankInfo/form?id=${bankInfo.id}">修改</a>
					<a href="${ctx}/sys/bankInfo/delete?id=${bankInfo.id}" onclick="return confirmx('确认要删除该银行信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>