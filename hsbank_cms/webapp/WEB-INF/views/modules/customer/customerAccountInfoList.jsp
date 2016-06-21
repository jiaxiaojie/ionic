<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账号信息管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/formUtils.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#searchForm').bind('click', function() {
				 //if()
			});

			 
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
		<li class="active"><a href="${ctx}/customer/customerAccountInfo/customerAccountInfoList">会员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="customerAccount" action="${ctx}/customer/customerAccountInfo/customerAccountInfoList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>会员手机号：</label>
				<form:input path="customerBase.mobile" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>会员名：</label>
				<form:input path="customerBase.customerName" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="customerBase.certNum" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>银行卡号：</label>
				<form:input path="customerBankCard.cardNo" htmlEscape="false" maxlength="18" class="input-medium"/>
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
				<th>用户状态</th>
				<shiro:hasPermission name="customer:customerAccount:edit"><th>操作</th></shiro:hasPermission> 
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerAccount">
			
			<input type="hidden" name="resultAccountId" value="${customerAccount.accountId}" />
			<input type="hidden" name="resultAccountName" value="${customerAccount.accountName}" />
			<tr>
				<td>
					${fns:getDictLabel(customerAccount.accountType, 'account_type_dict', '')}
				</td>
				<td><a href="${ctx}/customer/customerAccountInfo/customerAccountInfo?pageType=customerAccountInfoMenu&accountId=${customerAccount.accountId}">
					${customerAccount.accountName}
				</a></td>
				<td><a href="${ctx}/customer/customerAccountInfo/baseInfo?backPath=customerAccountInfoList&pageType=customerAccountInfoMenu&accountId=${customerAccount.accountId}">
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
				<td>
					${customerAccount.userStatus}
				</td>
				<td>
					<a href="${ctx}/current/currentAccountSummary/form?pageType=accountMenus&readonly=true&accountId=${customerAccount.accountId}&queryFieldName=accountId">活期投资</a>
					<a href="${ctx}/message/receiveMessageSwitch/customerSwitchSettingList?pageType=customerMessageMenu&currentMenuId=0&accountId=${customerAccount.accountId}">消息设置</a>
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>