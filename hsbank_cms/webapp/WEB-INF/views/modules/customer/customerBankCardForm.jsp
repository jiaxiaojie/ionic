<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员银行卡信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerBankCard.accountId}">会员<shiro:hasPermission name="customer:customerBankCard:edit">${not empty customerBankCard.accountId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerBankCard:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerAccount/form?id=${customerBankCard.accountId}">账号信息</a></li>
		<c:if test="${not empty customerBankCard.accountId}">
		<li><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerBankCard.accountId}">基本信息</a></li>
		<c:if test="${customerBankCard.accountType=='0'}">
		<c:if test="${not empty customerBankCard.customerId}">
		<li><a href="${ctx}/customer/customerWork/formByCustomerId?customerId=${customerBankCard.customerId}">工作信息</a></li>
		<li><a href="${ctx}/customer/customerHousing/formByCustomerId?customerId=${customerBankCard.customerId}">房产信息</a></li>
		<li><a href="${ctx}/customer/customerCar/formByCustomerId?customerId=${customerBankCard.customerId}">车产信息</a></li>
		</c:if>
		<li class="active"><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${customerBankCard.accountId}">银行卡信息</a></li>
		</c:if>
		</c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerBankCard" action="${ctx}/customer/customerBankCard/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="accountId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">当前银行卡号：</label>
			<div class="controls">
				<form:input path="cardNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡种类：</label>
			<div class="controls">
				<form:select path="cardType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_bank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡归属：</label>
			<div class="controls">
				<form:select path="bankInfo" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_bank_card_area_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡币种：</label>
			<div class="controls">
				<form:select path="currencyType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_bank_card_currency_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡填写时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerBankCard.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后一次修改时间：</label>
			<div class="controls">
				<input name="lastModifyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerBankCard.lastModifyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerBankCard:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<c:if test="${not empty customerBankCard.accountId}">
			<shiro:hasPermission name="customer:customerBankCardHis:view">
			<input class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/customer/customerBankCardHis/list?accountId=${customerBankCard.accountId}'" value="查看修改记录"/>&nbsp;
			</shiro:hasPermission>
			</c:if>
			<a href="${ctx}/customer/customerAccount/"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>
		</div>
	</form:form>
</body>
</html>