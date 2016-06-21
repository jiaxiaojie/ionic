<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>信用卡认证</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(function(){
			$("#creditCardInfoForm").validate({
				/*submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},*/
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
	<form:form id="creditCardInfoForm" modelAttribute="customerBase" action="${ctxFront }/customer/account/authInfo/creditCardInfoPost" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerId"/>
		<form:hidden path="accountId"/>
		<form:hidden path="accountType"/>
		
		<form:hidden path="customerName"/>
		<form:hidden path="genderCode"/>
		<form:hidden path="nationalityCode"/>
		<form:hidden path="certNum"/>
		<form:hidden path="certFile"/>
		<form:hidden path="nameAuthCode"/>
		<form:hidden path="mobile"/>
		<form:hidden path="mobileAuthCode"/>
		<form:hidden path="email"/>
		<form:hidden path="emailAuthCode"/>
		<form:hidden path="educationCode"/>
		<form:hidden path="educationSchool"/>
		<form:hidden path="educationFile"/>
		<form:hidden path="marriageCode"/>
		<form:hidden path="address"/>
		<form:hidden path="familyRegister"/>
		<form:hidden path="familyRegisterFile"/>
		<form:hidden path="creditCardAuthCode"/>
		<div style="height:20px;"></div>
		<div class="control-group">
			<label class="control-label">信用卡银行：</label>
			<div class="controls">
				<form:select path="creditCardBankCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_bank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信用卡卡号：</label>
			<div class="controls">
				<form:input path="creditCardNo" htmlEscape="false" minlength="10" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信用卡额度：</label>
			<div class="controls">
				<form:input path="creditCardLimit" htmlEscape="false" maxlength="9" class="input-xlarge digits required"/>
			</div>
		</div>
	</form:form>
</body>