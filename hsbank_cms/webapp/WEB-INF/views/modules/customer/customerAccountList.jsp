<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账号信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerAccount/">会员列表</a></li>
		<shiro:hasPermission name="customer:customerAccount:edit"><li><a href="${ctx}/customer/customerAccount/form">会员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerAccount" action="${ctx}/customer/customerAccount/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号类型：</label>
				<form:select path="accountType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('account_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>登录名：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>会员姓名：</label>
				<form:input path="customerBase.customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			
			<li class="clearfix"></li>
			<li><label>账号状态：</label>
				<form:select path="statusCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('customer_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>会员手机号：</label>
				<form:input path="customerBase.mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			
			<li><label>注册时间：</label>
				<input name="beginRegisterDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerAccount.beginRegisterDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endRegisterDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerAccount.endRegisterDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>发标身份</th>
				<th>登录名</th>
				<th>会员姓名</th>
				<th>会员手机号</th>
				<th>账号状态</th>
				<th>余额</th>
				<th>注册时间</th>
				<!--<shiro:hasPermission name="customer:customerAccount:edit"><th>操作</th></shiro:hasPermission> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerAccount">
			<tr>
				<td>
					${fns:getDictLabel(customerAccount.accountType, 'account_type_dict', '')}
				</td>
				<td><a href="${ctx}/customer/customerAccount/form?id=${customerAccount.accountId}">
					${customerAccount.accountName}
				</a></td>
				<td><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerAccount.accountId}">
					${customerAccount.customerBase.customerName}
				</a></td>
				<td>
					${customerAccount.customerBase.mobile}
				</td>
				<td>
					${fns:getDictLabel(customerAccount.statusCode, 'customer_status_dict', '')}
				</td>
				<td>
					${customerAccount.customerBalance.goldBalance}
				</td>
				<td>
					<fmt:formatDate value="${customerAccount.registerDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<!--<shiro:hasPermission name="customer:customerAccount:edit"><td>
    				<a href="${ctx}/customer/customerAccount/form?id=${customerAccount.accountId}">修改</a>
					<a href="${ctx}/customer/customerAccount/delete?id=${customerAccount.id}" onclick="return confirmx('确认要删除该会员账号信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission> -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>