<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>家庭情况</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(function(){
			$("#addressInfoForm").validate({
				/*submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},*/
				messages: {familyRegisterFile: {required: "请上传户口薄照片"}},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				},
				ignore: ""
			});
		});
	</script>
</head>
<body>
	<form:form id="addressInfoForm" modelAttribute="customerBase" action="${ctxFront }/customer/account/authInfo/addressInfoPost" method="post" class="form-horizontal">
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
		<form:hidden path="creditCardBankCode"/>
		<form:hidden path="creditCardNo"/>
		<form:hidden path="creditCardLimit"/>
		<form:hidden path="creditCardAuthCode"/>
		<div style="height:20px;"></div>
		<div class="control-group">
			<label class="control-label">婚姻状况：</label>
			<div class="controls">
				<form:select path="marriageCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_marriage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">居住地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍：</label>
			<div class="controls">
				<form:input path="familyRegister" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户口薄照片：</label>
			<div class="controls">
				<form:hidden id="familyRegisterFile" path="familyRegisterFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="familyRegisterFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
	</form:form>
</body>