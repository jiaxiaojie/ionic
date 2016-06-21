<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>身份认证</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(function(){
			$("#identityInfoForm").validate({
				/*submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},*/
				messages: {certFile: {required: "请上传身份证照片"}},
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
	<form:form id="identityInfoForm" modelAttribute="customerBase" action="${ctxFront }/customer/account/authInfo/identityInfoPost" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerId"/>
		<form:hidden path="accountId"/>
		<form:hidden path="accountType"/>
		
		<form:hidden path="genderCode"/>
		<form:hidden path="nationalityCode"/>
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
		<form:hidden path="creditCardBankCode"/>
		<form:hidden path="creditCardNo"/>
		<form:hidden path="creditCardLimit"/>
		<form:hidden path="creditCardAuthCode"/>
		<div style="height:20px;"></div>
		<div class="control-group">
			<label class="control-label">真实姓名：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${customerBase.nameAuthCode eq '1' }">
				<form:input path="customerName" readonly="true" title="已实名认证" htmlEscape="false" class="input-xlarge required realName"/>
					</c:when>
					<c:otherwise>
				<form:input path="customerName" htmlEscape="false" class="input-xlarge required realName"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号码：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${customerBase.nameAuthCode eq '1' }">
				<form:input path="certNum" readonly="true" title="已实名认证" htmlEscape="false" class="input-xlarge required card"/>
					</c:when>
					<c:otherwise>
				<form:input path="certNum" htmlEscape="false" class="input-xlarge required card"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证照片：</label>
			<div class="controls">
				<form:hidden id="certFile" path="certFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="certFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本人身份证原件的正、反两面照片，示例：</label>
			<div class="controls">
				<img alt="本人身份证原件的正、反两面照片" src="${ctxStatic }/modules/front/images/wdzh/example/cert.jpg" />
			</div>
			<label class="control-label">本人手持身份证正面头部照，示例：</label>
			<div class="controls">
				<img alt="本人手持身份证正面头部照" src="${ctxStatic }/modules/front/images/wdzh/example/person_cert.jpg"  />
			</div>
		</div>
	</form:form>
</body>