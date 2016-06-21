<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员车辆信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerCar.accountId}">会员<shiro:hasPermission name="customer:customerCar:edit">${not empty customerCar.accountId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerCar:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerAccount/form?id=${customerCar.accountId}">账号信息</a></li>
		<c:if test="${not empty customerCar.accountId}">
		<li><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerCar.accountId}">基本信息</a></li>
		<c:if test="${customerCar.accountType=='0'}">
		<c:if test="${not empty customerCar.customerId}">
		<li><a href="${ctx}/customer/customerWork/formByCustomerId?customerId=${customerCar.customerId}">工作信息</a></li>
		<li><a href="${ctx}/customer/customerHousing/formByCustomerId?customerId=${customerCar.customerId}">房产信息</a></li>
		<li class="active"><a href="${ctx}/customer/customerCar/formByCustomerId?customerId=${customerCar.customerId}">车产信息</a></li>
		</c:if>
		<li><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${customerCar.accountId}">银行卡信息</a></li>
		</c:if>
		</c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerCar" action="${ctx}/customer/customerCar/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">车辆型号：</label>
			<div class="controls">
				<form:input path="carType" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">未还贷款（元）：</label>
			<div class="controls">
				<form:input path="loanValue" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行驶里程（公里）：</label>
			<div class="controls">
				<form:input path="distance" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买年份：</label>
			<div class="controls">
				<form:input path="buyYear" htmlEscape="false" maxlength="4" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerCar:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>