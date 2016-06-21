<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员工作信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerWork.accountId}">会员<shiro:hasPermission name="customer:customerWork:edit">${not empty customerWork.accountId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerWork:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerAccount/form?id=${customerWork.accountId}">账号信息</a></li>
		<c:if test="${not empty customerWork.accountId}">
		<li><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerWork.accountId}">基本信息</a></li>
		<c:if test="${customerWork.accountType=='0'}">
		<c:if test="${not empty customerWork.customerId}">
		<li class="active"><a href="${ctx}/customer/customerWork/formByCustomerId?customerId=${customerWork.customerId}">工作信息</a></li>
		<li><a href="${ctx}/customer/customerHousing/formByCustomerId?customerId=${customerWork.customerId}">房产信息</a></li>
		<li><a href="${ctx}/customer/customerCar/formByCustomerId?customerId=${customerWork.customerId}">车产信息</a></li>
		</c:if>
		<li><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${customerWork.accountId}">银行卡信息</a></li>
		</c:if>
		</c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerWork" action="${ctx}/customer/customerWork/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">公司名称：</label>
			<div class="controls">
				<form:input path="companyName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司性质：</label>
			<div class="controls">
				<form:select path="companyTypeCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司规模：</label>
			<div class="controls">
				<form:select path="companySizeCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_company_size')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位：</label>
			<div class="controls">
				<form:input path="position" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在职证明：</label>
			<div class="controls">
				<form:hidden id="positionFile" path="positionFile" htmlEscape="false" maxlength="500" class="input-xlarge"/>
				<sys:ckfinder input="positionFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年收入：</label>
			<div class="controls">
				<form:select path="incomeCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_income')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收入证明：</label>
			<div class="controls">
				<form:hidden id="incomeFile" path="incomeFile" htmlEscape="false" maxlength="500" class="input-xlarge"/>
				<sys:ckfinder input="incomeFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在岗时间（单位：年）：</label>
			<div class="controls">
				<form:input path="workYears" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司联系人：</label>
			<div class="controls">
				<form:input path="contacts" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司联系电话：</label>
			<div class="controls">
				<form:input path="companyPhone" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司所在城市：</label>
			<div class="controls">
				<form:input path="companyAddress" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerWork.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后一次修改时间：</label>
			<div class="controls">
				<input name="lastModifyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerWork.lastModifyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerWork:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>