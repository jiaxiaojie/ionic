<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同执行快照管理</title>
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
		<li><a href="${ctx}/project/projectBaseInfo/${modelMenus.backPath}">借贷产品列表</a></li>
		<li class="active"><a HERF="#">借贷产品信息</a></li>
	</ul><br/>
	<jsp:include page="./common/jdprojectInfoMenu.jsp"/>
	<form:form id="inputForm" modelAttribute="projectExecuteSnapshot" action="${ctx}/project/projectExecuteSnapshot/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目流水号：</label>
			<div class="controls">
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转让项目流水号：</label>
			<div class="controls">
				<form:input path="transferProjectId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款金额：</label>
			<div class="controls">
			<input id="financeMoney" name="financeMoney"  type="text" value="<fmt:formatNumber value="${projectExecuteSnapshot.financeMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已融资金额：</label>
			<div class="controls">
			<input id="endFinanceMoney" name="endFinanceMoney" type="text"  value="<fmt:formatNumber value="${projectExecuteSnapshot.endFinanceMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已还款金额：</label>
			<div class="controls">
			<input id="endRepayMoney" name="endRepayMoney" type="text"  value="<fmt:formatNumber value="${projectExecuteSnapshot.endRepayMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已冻结服务费：</label>
			<div class="controls">
		<input id="sumServiceCharge" name="sumServiceCharge" type="text"  value="<fmt:formatNumber value="${projectExecuteSnapshot.sumServiceCharge}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已冻结抵用额：</label>
			<div class="controls">
			<input id="sumPlatformAmount" name="sumPlatformAmount" type="text"  value="<fmt:formatNumber value="${projectExecuteSnapshot.sumPlatformAmount}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已冻结抵用卷额：</label>
			<div class="controls">
			<input id="sumTicketMoney" name="sumTicketMoney" type="text"  value="<fmt:formatNumber value="${projectExecuteSnapshot.sumTicketMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余期限：</label>
			<div class="controls">
				<form:input path="remainingTime" htmlEscape="false" class="input-xlarge "/> 月
			</div>
		</div>
	</form:form>
</body>
</html>