<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员信用认证信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerCreditAuth/">会员信用认证信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="customerCreditAuth" action="${ctx}/customer/customerCreditAuth/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>登录名：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>会员姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>用户姓名</th>
				<th>手机号</th>
				<th class="sort-column personCreditAuthCode">基本信息认证</th>
				<th class="sort-column identityCreditAuthCode">身份认证</th>
				<th class="sort-column educationCreditAuthCode">学历认证</th>
				<th class="sort-column addressCreditAuthCode">家庭情况</th>
				<th class="sort-column incomeCreditAuthCode">个人收入</th>
				<th class="sort-column housingCreditAuthCode">房产认证</th>
				<th class="sort-column carCreditAuthCode">车产认证</th>
				<th class="sort-column creditAuthCode">信用卡认证</th>
				<th class="sort-column creditReportAuthCode">信用报告</th>
				<th class="sort-column creditLevel">信用等级</th>
				<th>信用得分</th>
				<th>信用额度（单位：元）</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerCreditAuth">
			<tr>
				<td>
					${customerCreditAuth.accountName}
				</td>
				<td>
					${customerCreditAuth.customerName}
				</td>
				<td>
					${customerCreditAuth.mobile}
				</td>
				<td>
					${fns:getDictLabel(customerCreditAuth.personCreditAuthCode, 'customer_credit_auth', '')}
					<c:choose>
					<c:when test="${customerCreditAuth.personCreditAuthCode=='1'}">
					<a href="${ctx}/customer/customerCreditAuth/formBase?id=${customerCreditAuth.id}">审核</a></c:when>
					<c:when test="${customerCreditAuth.personCreditAuthCode=='-1' || customerCreditAuth.personCreditAuthCode=='2'}">
					<a href="${ctx}/customer/customerCreditAuth/formBase?id=${customerCreditAuth.id}">查看</a></c:when>
					</c:choose>
				</td>
				<td>
					${fns:getDictLabel(customerCreditAuth.identityCreditAuthCode, 'customer_credit_auth', '')}
					<c:choose>
					<c:when test="${customerCreditAuth.identityCreditAuthCode=='1'}">
					<a href="${ctx}/customer/customerCreditAuth/formIdentity?id=${customerCreditAuth.id}">审核</a></c:when>
					<c:when test="${customerCreditAuth.identityCreditAuthCode=='-1' || customerCreditAuth.identityCreditAuthCode=='2'}">
					<a href="${ctx}/customer/customerCreditAuth/formIdentity?id=${customerCreditAuth.id}">查看</a></c:when>
					</c:choose>
				</td>
				<td>
					${fns:getDictLabel(customerCreditAuth.educationCreditAuthCode, 'customer_credit_auth', '')}
					<c:choose>
					<c:when test="${customerCreditAuth.educationCreditAuthCode=='1'}">
					<a href="${ctx}/customer/customerCreditAuth/formEducation?id=${customerCreditAuth.id}">审核</a></c:when>
					<c:when test="${customerCreditAuth.educationCreditAuthCode=='-1' || customerCreditAuth.educationCreditAuthCode=='2'}">
					<a href="${ctx}/customer/customerCreditAuth/formEducation?id=${customerCreditAuth.id}">查看</a></c:when>
					</c:choose>
				</td>
				<td>
					${fns:getDictLabel(customerCreditAuth.addressCreditAuthCode, 'customer_credit_auth', '')}
					<c:choose>
					<c:when test="${customerCreditAuth.addressCreditAuthCode=='1'}">
					<a href="${ctx}/customer/customerCreditAuth/formAddress?id=${customerCreditAuth.id}">审核</a></c:when>
					<c:when test="${customerCreditAuth.addressCreditAuthCode=='-1' || customerCreditAuth.addressCreditAuthCode=='2'}">
					<a href="${ctx}/customer/customerCreditAuth/formAddress?id=${customerCreditAuth.id}">查看</a></c:when>
					</c:choose>
				</td>
				<td>
					${fns:getDictLabel(customerCreditAuth.incomeCreditAuthCode, 'customer_credit_auth', '')}
					<c:choose>
					<c:when test="${customerCreditAuth.incomeCreditAuthCode=='1'}">
					<a href="${ctx}/customer/customerCreditAuth/formIncome?id=${customerCreditAuth.id}">审核</a></c:when>
					<c:when test="${customerCreditAuth.incomeCreditAuthCode=='-1' || customerCreditAuth.incomeCreditAuthCode=='2'}">
					<a href="${ctx}/customer/customerCreditAuth/formIncome?id=${customerCreditAuth.id}">查看</a></c:when>
					</c:choose>
				</td>
				<td>
					${fns:getDictLabel(customerCreditAuth.housingCreditAuthCode, 'customer_credit_auth', '')}
					<c:choose>
					<c:when test="${customerCreditAuth.housingCreditAuthCode=='1'}">
					<a href="${ctx}/customer/customerCreditAuth/formHousing?id=${customerCreditAuth.id}">审核</a></c:when>
					<c:when test="${customerCreditAuth.housingCreditAuthCode=='-1' || customerCreditAuth.housingCreditAuthCode=='2'}">
					<a href="${ctx}/customer/customerCreditAuth/formHousing?id=${customerCreditAuth.id}">查看</a></c:when>
					</c:choose>
				</td>
				<td>
					${fns:getDictLabel(customerCreditAuth.carCreditAuthCode, 'customer_credit_auth', '')}
					<c:choose>
					<c:when test="${customerCreditAuth.carCreditAuthCode=='1'}">
					<a href="${ctx}/customer/customerCreditAuth/formCar?id=${customerCreditAuth.id}">审核</a></c:when>
					<c:when test="${customerCreditAuth.carCreditAuthCode=='-1' || customerCreditAuth.carCreditAuthCode=='2'}">
					<a href="${ctx}/customer/customerCreditAuth/formCar?id=${customerCreditAuth.id}">查看</a></c:when>
					</c:choose>
				</td>
				<td>
					${fns:getDictLabel(customerCreditAuth.creditAuthCode, 'customer_credit_auth', '')}
					<c:choose>
					<c:when test="${customerCreditAuth.creditAuthCode=='1'}">
					<a href="${ctx}/customer/customerCreditAuth/formCreditCard?id=${customerCreditAuth.id}">审核</a></c:when>
					<c:when test="${customerCreditAuth.creditAuthCode=='-1' || customerCreditAuth.creditAuthCode=='2'}">
					<a href="${ctx}/customer/customerCreditAuth/formCreditCard?id=${customerCreditAuth.id}">查看</a></c:when>
					</c:choose>
				</td>
				<td>
					${fns:getDictLabel(customerCreditAuth.creditReportAuthCode, 'customer_credit_auth', '')}
					<c:choose>
					<c:when test="${customerCreditAuth.creditReportAuthCode=='1'}">
					<a href="${ctx}/customer/customerCreditAuth/formCreditReport?id=${customerCreditAuth.id}">审核</a></c:when>
					<c:when test="${customerCreditAuth.creditReportAuthCode=='-1' || customerCreditAuth.creditReportAuthCode=='2'}">
					<a href="${ctx}/customer/customerCreditAuth/formCreditReport?id=${customerCreditAuth.id}">查看</a></c:when>
					</c:choose>
				</td>
				<td>
					${customerCreditAuth.creditLevel}
				</td>
				<td>
					${customerCreditAuth.creditScore}
				</td>
				<td>
					${customerCreditAuth.creditLimit}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>