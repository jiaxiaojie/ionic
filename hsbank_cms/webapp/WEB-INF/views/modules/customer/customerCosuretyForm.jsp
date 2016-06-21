<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员联保人管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerCosurety.accountId}">会员<shiro:hasPermission name="customer:customerCosurety:edit">${not empty customerCosurety.accountId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerCosurety:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerAccount/form?id=${customerCosurety.accountId}">账号信息</a></li>
		<c:if test="${not empty customerCosurety.accountId}">
		<li><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerCosurety.accountId}">基本信息</a></li>
		<c:if test="${customerCosurety.accountType=='0'}">
		<c:if test="${not empty customerCosurety.customerId}">
		<li><a href="${ctx}/customer/customerWork/formByCustomerId?customerId=${customerCosurety.customerId}">工作信息</a></li>
		<li><a href="${ctx}/customer/customerHousing/formByCustomerId?customerId=${customerCosurety.customerId}">房产信息</a></li>
		<li><a href="${ctx}/customer/customerCar/formByCustomerId?customerId=${customerCosurety.customerId}">车产信息</a></li>
		</c:if>
		<li><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${customerCosurety.accountId}">银行卡信息</a></li>
		</c:if>
		</c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerCosurety" action="${ctx}/customer/customerCosurety/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">联保人：</label>
			<div class="controls">
				<form:input path="cosuretyName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联保人电话：</label>
			<div class="controls">
				<form:input path="cosuretyMobile" htmlEscape="false" maxlength="20" class="input-xlarge simplePhone"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联保人关系：</label>
			<div class="controls">
				<form:input path="cosuretyRel" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联保人身份证号：</label>
			<div class="controls">
				<form:input path="cosuretyCertNum" htmlEscape="false" maxlength="20" class="input-xlarge card"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerCosurety:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<a href="${ctx}/customer/customerAccount/"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>
		</div>
	</form:form>
</body>
</html>