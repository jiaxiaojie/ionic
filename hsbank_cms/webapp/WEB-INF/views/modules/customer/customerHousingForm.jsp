<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员房产信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerHousing.accountId}">会员<shiro:hasPermission name="customer:customerHousing:edit">${not empty customerHousing.accountId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerHousing:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerAccount/form?id=${customerHousing.accountId}">账号信息</a></li>
		<c:if test="${not empty customerHousing.accountId}">
		<li><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerHousing.accountId}">基本信息</a></li>
		<c:if test="${customerHousing.accountType=='0'}">
		<c:if test="${not empty customerHousing.customerId}">
		<li><a href="${ctx}/customer/customerWork/formByCustomerId?customerId=${customerHousing.customerId}">工作信息</a></li>
		<li class="active"><a href="${ctx}/customer/customerHousing/formByCustomerId?customerId=${customerHousing.customerId}">房产信息</a></li>
		<li><a href="${ctx}/customer/customerCar/formByCustomerId?customerId=${customerHousing.customerId}">车产信息</a></li>
		</c:if>
		<li><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${customerHousing.accountId}">银行卡信息</a></li>
		</c:if>
		</c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerHousing" action="${ctx}/customer/customerHousing/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">房产地址：</label>
			<div class="controls">
				<form:input path="housingAddress" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">房产证明：</label>
			<div class="controls">
				<form:hidden id="housingFile" path="housingFile" htmlEscape="false" maxlength="500" class="input-xlarge"/>
				<sys:ckfinder input="housingFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款年限（单位：年）：</label>
			<div class="controls">
				<form:input path="loanYear" htmlEscape="false" maxlength="4" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">未还贷款（单位：元）：</label>
			<div class="controls">
				<form:input path="loanValue" htmlEscape="false" class="input-xlarge digital"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerHousing:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>