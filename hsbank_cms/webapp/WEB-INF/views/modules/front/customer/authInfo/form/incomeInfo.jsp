<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>收入认证</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(function(){
			$("#incomeInfoForm").validate({
				/*submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},*/
				messages: {incomeFile: {required: "请上传收入证明"}},
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
	<form:form id="incomeInfoForm" modelAttribute="customerWork" action="${ctxFront }/customer/account/authInfo/incomeInfoPost" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="accountId"/>
		<form:hidden path="customerId"/>
		<div style="height:20px;"></div>
		<div class="control-group">
			<label class="control-label">公司名称：</label>
			<div class="controls">
				<form:input path="companyName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司性质：</label>
			<div class="controls">
				<form:select path="companyTypeCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司规模：</label>
			<div class="controls">
				<form:select path="companySizeCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_company_size')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位：</label>
			<div class="controls">
				<form:input path="position" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在职证明：</label>
			<div class="controls">
				<form:hidden id="positionFile" path="positionFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="positionFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年收入：</label>
			<div class="controls">
				<form:select path="incomeCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_income')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收入证明：</label>
			<div class="controls">
				<form:hidden id="incomeFile" path="incomeFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="incomeFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在岗时间（单位：年）：</label>
			<div class="controls">
				<form:input path="workYears" htmlEscape="false" maxlength="5" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司联系人：</label>
			<div class="controls">
				<form:input path="contacts" htmlEscape="false" class="input-xlarge required realName"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司联系电话：</label>
			<div class="controls">
				<form:input path="companyPhone" htmlEscape="false" maxlength="20" class="input-xlarge required simplePhone"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司所在城市：</label>
			<div class="controls">
				<form:input path="companyAddress" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
	</form:form>
</body>