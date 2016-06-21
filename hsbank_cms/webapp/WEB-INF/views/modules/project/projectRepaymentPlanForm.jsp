<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>还款记录管理</title>
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
		<li><a href="${ctx}/project/projectRepaymentPlan/">还款记录列表</a></li>
		<li class="active"><a href="${ctx}/project/projectRepaymentPlan/form?id=${projectRepaymentPlan.id}">还款记录<shiro:hasPermission name="project:projectRepaymentPlan:edit">${not empty projectRepaymentPlan.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="project:projectRepaymentPlan:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectRepaymentPlan" action="${ctx}/project/projectRepaymentPlan/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目流水号：</label>
			<div class="controls">
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款日期：</label>
			<div class="controls">
				<input name="planDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectRepaymentPlan.planDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款金额：</label>
			<div class="controls">
			<input id="planMoney" name="planMoney" type="text"  value="<fmt:formatNumber value="${projectRepaymentPlan.planMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本金：</label>
			<div class="controls">
			<input id="principal" name="principal" type="text"  value="<fmt:formatNumber value="${projectRepaymentPlan.principal}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利息：</label>
			<div class="controls">
			<input id="interest" name="interest"  type="text" value="<fmt:formatNumber value="${projectRepaymentPlan.interest}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余本金：</label>
			<div class="controls">
			<input id="remainingPrincipal" name="remainingPrincipal" type="text"  value="<fmt:formatNumber value="${projectRepaymentPlan.remainingPrincipal}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="project:projectRepaymentPlan:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>