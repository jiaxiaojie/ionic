<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期项目还款授权</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		function confirmAuthorizeAutoRepayment() {
			$("#authorizeAutoRepaymentForm").submit();
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/current/currentProjectInfo/list">产品列表</a></li>
</ul><br/>
<form:form id="authorizeAutoRepaymentForm" modelAttribute="currentProjectInfo" action="${authorizeAutoRepaymentUrl }" method="post" class="form-horizontal" >
    <textarea name="req" style="display:none;">${req }</textarea>
    <input type="hidden" name="sign" value="${sign }"/>
	<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				<form:input path="rate" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
				<form:input path="startingAmount" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大投资金额：</label>
			<div class="controls">
				<form:input path="maxAmount" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资金额：</label>
			<div class="controls">
				<form:input path="financeMoney" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布日期：</label>
			<div class="controls">
				<input name="publishDt" id="publishDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${currentProjectInfo.publishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投标截止时间：</label>
			<div class="controls">
				<input name="endInvestmentDt" id="endInvestmentDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${currentProjectInfo.endInvestmentDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资人：</label>
			<div class="controls">
				<form:input path="borrowerAccountId" class="input-xlarge  number" value="${customerAccount.customerBase.customerName }" readonly="true"/>
			</div>
		</div>
    <div class="form-actions">
		<shiro:hasPermission name="current:currentProjectInfo:authorize"><input id="btnSubmit" class="btn btn-primary" type="button" value="前往授权" onclick="confirmAuthorizeAutoRepayment()" />&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>  	
</form:form>	
</body>
</html>