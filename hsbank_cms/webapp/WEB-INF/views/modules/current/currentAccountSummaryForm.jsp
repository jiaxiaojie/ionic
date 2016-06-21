<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期账户总览管理</title>
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
		<li><a href="${ctx}/current/currentAccountSummary/">活期账户总览列表</a></li>
		<li class="active"><a href="${ctx}/current/currentAccountSummary/form?id=${currentAccountSummary.id}">活期账户总览<shiro:hasPermission name="current:currentAccountSummary:edit">${not empty currentAccountSummary.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="current:currentAccountSummary:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<jsp:include page="../customer/common/customerPandectMenu.jsp"/>
	<form:form id="inputForm" modelAttribute="currentAccountSummary" action="${ctx}/current/currentAccountSummary/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">账户编号：</label>
			<div class="controls">
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计投资金额：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatNumber value="${currentAccountSummary.totalInvestmentMoney}" pattern="#0.##" />" name="totalInvestmentMoney" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计获取利息：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatNumber value="${currentAccountSummary.totalGetInterest}" pattern="#0.##" />" name="totalGetInterest" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计赎回本金：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatNumber value="${currentAccountSummary.totalRedeemPrincipal}" pattern="#0.##" />" name="totalRedeemPrincipal" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计赎回利息：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatNumber value="${currentAccountSummary.totalRedeemInterest}" pattern="#0.##" />" name="totalRedeemInterest" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前持有本金：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatNumber value="${currentAccountSummary.currentPrincipal}" pattern="#0.##" />" name="currentPrincipal" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${currentAccountSummary.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改时间：</label>
			<div class="controls">
				<input name="modifyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${currentAccountSummary.modifyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="current:currentAccountSummary:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>