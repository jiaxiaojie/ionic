<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投资购买</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		function confirm() {
			$("#inputForm").submit();
		}
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
			});
		});
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/current/currentProjectInfo/list">产品列表</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="currentProjectInfo" action="${ctx}/current/currentProjectInfo/currentProjectBuyConfirm" method="post" class="form-horizontal" >
    <form:hidden path="id"/>
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
			<label class="control-label">手机号：</label>
			<div class="controls">
				<input type="text" placeholder="手机号" maxLength="11"  id="mobile" name="mobile" class="input-xlarge  mobile required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资金额：</label>
			<div class="controls">
				<input type="text" maxLength="10"  id="amount" name="amount" class="input-xlarge  number required">
			</div>
		</div>
    <div class="form-actions">
		<shiro:hasPermission name="current:currentProjectInfo:authorize"><input id="btnSubmit" class="btn btn-primary" type="button" value="立即投资" onclick="confirm()" />&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>  	
</form:form>	
</body>
</html>