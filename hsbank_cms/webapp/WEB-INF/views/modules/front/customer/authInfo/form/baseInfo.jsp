<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>个人基础信息认证</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(function(){
			$("#baseInfoForm").validate({
				/*submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},*/
				rules: {email: {remote: {
					url: "${ctxFront}/customer/account/checkEmailCanUse",
					data: {
						accountId: function(){
							return $("#accountId").val()
						},
						email: function(){
							return $("#email").val()
						}
					}
				}}},
				messages: {email: {remote: "邮箱地址已被使用，请重新输入."}},
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
		function toSafeCenter() {
			window.top.location.href = "${ctxFront}/customer/account/safeCenter";
		}
	</script>
</head>
<body>
	<form:form id="baseInfoForm" modelAttribute="customerBase" action="${ctxFront }/customer/account/authInfo/baseInfoPost" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerId"/>
		<form:hidden path="accountId"/>
		<form:hidden path="accountType"/>
		
		<form:hidden path="certFile"/>
		<form:hidden path="nameAuthCode"/>
		<form:hidden path="mobileAuthCode"/>
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
			<label class="control-label">姓名：</label>
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
			<label class="control-label">国籍：</label>
			<div class="controls">
				<form:select path="nationalityCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_nationality_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
			<label class="control-label">手机号：</label>
			<div class="controls">
					<c:choose>
					<c:when test="${customerBase.mobileAuthCode eq '1' }">
				<form:input path="mobile" readonly="true" title="已认证" htmlEscape="false" class="input-xlarge required mobile"/>
					</c:when>
					<c:otherwise>
				<form:input path="mobile" htmlEscape="false" class="input-xlarge required mobile"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${customerBase.emailAuthCode eq '1' }">
				<form:input path="email" readonly="true" title="已认证" htmlEscape="false" maxlength="100" class="input-xlarge required email"/>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="toSafeCenter()">修改邮箱</a>
					</c:when>
					<c:otherwise>
				<form:input path="email" htmlEscape="false" maxlength="50" class="input-xlarge required email"/>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
	</form:form>
</body>