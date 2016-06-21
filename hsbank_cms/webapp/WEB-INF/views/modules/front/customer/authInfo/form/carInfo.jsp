<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>车产证明认证</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(function(){
			$("#carInfoForm").validate({
				/*submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},*/
				messages: {carFile: {required: "请上传人车合照"}},
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
	<form:form id="carInfoForm" modelAttribute="customerCar" action="${ctxFront }/customer/account/authInfo/carInfoPost" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="accountId"/>
		<form:hidden path="customerId"/>
		<div style="height:20px;"></div>
		<div class="control-group">
			<label class="control-label">车辆型号：</label>
			<div class="controls">
				<form:input path="carType" htmlEscape="false" minlength="5" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">未还贷款（元）：</label>
			<div class="controls">
				<form:input path="loanValue" htmlEscape="false" maxlength="12" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行驶里程（公里）：</label>
			<div class="controls">
				<form:input path="distance" htmlEscape="false" maxlength="7" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买年份：</label>
			<div class="controls">
				<form:input path="buyYear" htmlEscape="false" minlength="4" maxlength="4" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人车合照：</label>
			<div class="controls">
				<form:hidden id="carFile" path="carFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="carFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
	</form:form>
</body>