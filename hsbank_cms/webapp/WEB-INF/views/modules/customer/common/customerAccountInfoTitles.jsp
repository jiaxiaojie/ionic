<%@ page contentType="text/html;charset=UTF-8" %>


<ul class="nav nav-tabs">
		<li  class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerAccount.accountId}">账号信息</a></li>
		<c:if test="${not empty customerAccount.accountId}">
		<li ><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerAccount.accountId}">基本信息</a></li>
		<c:if test="${customerAccount.accountType=='0'}">
		<c:if test="${not empty customerAccount.customerBase.customerId}">
		<li ><a href="${ctx}/customer/customerWork/formByCustomerId?customerId=${customerAccount.customerBase.customerId}">工作信息</a></li>
		<li ><a href="${ctx}/customer/customerHousing/formByCustomerId?customerId=${customerAccount.customerBase.customerId}">房产信息</a></li>
		<li ><a href="${ctx}/customer/customerCar/formByCustomerId?customerId=${customerAccount.customerBase.customerId}">车产信息</a></li>
		</c:if>
		<li ><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${customerAccount.accountId}">银行卡信息</a></li>
		</c:if>
		</c:if>
	</ul>
