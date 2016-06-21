<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账户余额变更流水管理</title>
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
		<li><a href="${ctx}/customer/customerBalance/list">会员账户余额汇总列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerBalanceHis/">会员账户余额变更流水</a></li>
		<shiro:hasPermission name="customer:customerBalanceHis:edit"><li><a href="${ctx}/customer/customerBalanceHis/form">会员账户余额变更流水添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerBalanceHis" action="${ctx}/customer/customerBalanceHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<form:hidden path="accountId"/>
			<li><label>变更值：</label>
				<form:input path="beginChangeVal" htmlEscape="false" class="input-medium"/> - 
				<form:input path="endChangeVal" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>变更类型：</label>
				<form:select path="changeType" class="input-medium">
					<form:option value="" label="全部">
					<form:options items="${fns:getDictList('customer_balance_change_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label title="变更日期">变更日期：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerBalanceHis.beginOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerBalanceHis.beginOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>变更值</th>
				<th>变更类型</th>
				<th>变更原因</th>
				<th>关联项目</th>
				<th>操作时间</th>
				<th>操作终端</th>
				<shiro:hasPermission name="customer:customerBalanceHis:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerBalanceHis">
			<tr>
				<td><a href="${ctx}/customer/customerBalanceHis/form?id=${customerBalanceHis.id}">
					${customerBalanceHis.changeVal}
				</a></td>
				<td>
					${fns:getDictLabel(customerBalanceHis.changeType, 'customer_balance_change_type_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(customerBalanceHis.changeReason, 'customer_balance_change_reason_dict', '')}
				</td>
				<td>
					${customerBalanceHis.relProject}
				</td>
				<td>
					<fmt:formatDate value="${customerBalanceHis.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(customerBalanceHis.opTermType, 'op_term_dict', '')}
				</td>
				<shiro:hasPermission name="customer:customerBalanceHis:edit"><td>
    				<a href="${ctx}/customer/customerBalanceHis/form?id=${customerBalanceHis.id}">修改</a>
					<a href="${ctx}/customer/customerBalanceHis/delete?id=${customerBalanceHis.id}" onclick="return confirmx('确认要删除该会员账户余额变更流水吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>