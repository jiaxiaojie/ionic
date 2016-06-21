<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员财务信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerAccount/">会员列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerFinance.accountId}">会员<shiro:hasPermission name="customer:customerFinance:edit">${not empty customerWorkFinance.accountId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerFinance:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerAccount/form?id=${customerFinance.accountId}">账号信息</a></li>
		<c:if test="${not empty customerFinance.accountId}">
		<li><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerFinance.accountId}">基本信息</a></li>
		<c:if test="${customerFinance.accountType=='0'}">
		<c:if test="${not empty customerFinance.customerId}">
		<li><a href="${ctx}/customer/customerWork/formByCustomerId?customerId=${customerFinance.customerId}">工作信息</a></li>
		<li><a href="${ctx}/customer/customerHousing/formByCustomerId?customerId=${customerFinance.customerId}">房产信息</a></li>
		<li><a href="${ctx}/customer/customerCar/formByCustomerId?customerId=${customerFinance.customerId}">车产信息</a></li>
		</c:if>
		<li><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${customerFinance.accountId}">银行卡信息</a></li>
		</c:if>
		</c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerFinance" action="${ctx}/customer/customerFinance/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">月均收入：</label>
			<div class="controls">
				<form:input path="monthIncome" htmlEscape="false" maxlength="11" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收入构成描述：</label>
			<div class="controls">
				<form:textarea path="incomeRemark" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月均支出：</label>
			<div class="controls">
				<form:input path="monthSpend" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支出构成描述：</label>
			<div class="controls">
				<form:textarea path="spendRemark" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">住房条件：</label>
			<div class="controls">
				<form:input path="housingConditions" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">房产价值（单位:万）：</label>
			<div class="controls">
				<form:input path="housingVal" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有房贷：</label>
			<div class="controls">
				<form:radiobuttons path="hasHousingLoan" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否购车：</label>
			<div class="controls">
				<form:radiobuttons path="hasCar" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车辆价值（单位:万）：</label>
			<div class="controls">
				<form:input path="carVal" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有车贷：</label>
			<div class="controls">
				<form:radiobuttons path="hasCarLoan" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车辆信息描述：</label>
			<div class="controls">
				<form:textarea path="carRemark" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参股企业名称：</label>
			<div class="controls">
				<form:input path="shareholderEnt" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参股企业资额（单位:万）：</label>
			<div class="controls">
				<form:input path="shareholderVal" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他资产描述：</label>
			<div class="controls">
				<form:textarea path="otherRemark" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerFinance:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<a href="${ctx}/customer/customerAccount/"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>
		</div>
	</form:form>
</body>
</html>