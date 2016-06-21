<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>房产证明认证</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(function(){
			$("#housingInfoForm").validate({
				/*submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},*/
				messages: {housingFile: {required: "请上传房产证明"}},
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
	<form:form id="housingInfoForm" modelAttribute="customerHousing" action="${ctxFront }/customer/account/authInfo/housingInfoPost" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="accountId"/>
		<form:hidden path="customerId"/>
		<div style="height:20px;"></div>
		<div class="control-group">
			<label class="control-label">房产地址：</label>
			<div class="controls">
				<form:input path="housingAddress" htmlEscape="false" minlength="5" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">房产证明：</label>
			<div class="controls">
				<form:hidden id="housingFile" path="housingFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="housingFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款年限（年）：</label>
			<div class="controls">
				<form:input path="loanYear" htmlEscape="false" maxlength="4" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">未还贷款（元）：</label>
			<div class="controls">
				<form:input path="loanValue" htmlEscape="false" maxlength="12" class="input-xlarge digits required"/>
			</div>
		</div>
	</form:form>
</body>