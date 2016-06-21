<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>学历认证</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(function(){
			$("#educationInfoForm").validate({
				/*submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},*/
				messages: {educationFile: {required: "请上传学历证明"}},
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
	<form:form id="educationInfoForm" modelAttribute="customerBase" action="${ctxFront }/customer/account/authInfo/educationInfoPost" method="post" class="form-horizontal">
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
		<form:hidden path="marriageCode"/>
		<form:hidden path="address"/>
		<form:hidden path="familyRegister"/>
		<form:hidden path="familyRegisterFile"/>
		<form:hidden path="creditCardBankCode"/>
		<form:hidden path="creditCardNo"/>
		<form:hidden path="creditCardLimit"/>
		<form:hidden path="creditCardAuthCode"/>
		<div style="height:20px;"></div>
		<div class="control-group">
			<label class="control-label">最高学历：</label>
			<div class="controls">
				<form:select path="educationCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业院校：</label>
			<div class="controls">
				<form:input path="educationSchool" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历证明：</label>
			<div class="controls">
				<form:hidden id="educationFile" path="educationFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="educationFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
	</form:form>
</body>