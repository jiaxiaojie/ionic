<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目运营数据汇总管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
		<li><a href="${ctx}/operation/projectOperationSummary/">运营数据汇总列表</a></li>
		<li class="active"><a href="${ctx}/operation/projectOperationSummary/form?id=${projectOperationSummary.id}">运营数据汇总<shiro:hasPermission name="operation:projectOperationSummary:edit">${not empty projectOperationSummary.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="operation:projectOperationSummary:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectOperationSummary" action="${ctx}/operation/projectOperationSummary/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">日期：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectOperationSummary.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">累计募资额：</label>
			<div class="controls">
				<input type="text" id="financeAmount" value="<fmt:formatNumber value="${projectOperationSummary.financeAmount}" pattern="#0.00"/>" name="financeAmount" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计偿还本金：</label>
			<div class="controls">
			<input type="text" id="repayPrincipal" value="<fmt:formatNumber value="${projectOperationSummary.repayPrincipal}" pattern="#0.00"/>" name="repayPrincipal" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计偿还利息：</label>
			<div class="controls">
			<input type="text" id="repayInterest" value="<fmt:formatNumber value="${projectOperationSummary.repayInterest}" pattern="#0.00"/>" name="repayInterest" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新花生累计募资额：</label>
			<div class="controls">
			<input type="text" id="xinFinanceAmount" value="<fmt:formatNumber value="${projectOperationSummary.xinFinanceAmount}" pattern="#0.00"/>" name="xinFinanceAmount" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新花生累计偿还本金：</label>
			<div class="controls">
			<input type="text" id="xinRepayPrincipal" value="<fmt:formatNumber value="${projectOperationSummary.xinRepayPrincipal}" pattern="#0.00"/>" name="xinRepayPrincipal" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新花生累计偿还利息：</label>
			<div class="controls">
			<input type="text" id="xinRepayInterest" value="<fmt:formatNumber value="${projectOperationSummary.xinRepayInterest}" pattern="#0.00"/>" name="xinRepayInterest" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月花生累计募资额：</label>
			<div class="controls">
			<input type="text" id="yueFinanceAmount" value="<fmt:formatNumber value="${projectOperationSummary.yueFinanceAmount}" pattern="#0.00"/>" name="yueFinanceAmount" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月花生累计偿还本金：</label>
			<div class="controls">
			<input type="text" id="yueRepayPrincipal" value="<fmt:formatNumber value="${projectOperationSummary.yueRepayPrincipal}" pattern="#0.00"/>" name="yueRepayPrincipal" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月花生累计偿还利息：</label>
			<div class="controls">
			<input type="text" id="yueRepayInterest" value="<fmt:formatNumber value="${projectOperationSummary.yueRepayInterest}" pattern="#0.00"/>" name="yueRepayInterest" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">双月花生累计募资额：</label>
			<div class="controls">
			<input type="text" id="shuangyueFinanceAmount" value="<fmt:formatNumber value="${projectOperationSummary.shuangyueFinanceAmount}" pattern="#0.00"/>" name="shuangyueFinanceAmount" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">双月花生累计偿还本金：</label>
			<div class="controls">
			<input type="text" id="shuangyueRepayPrincipal" value="<fmt:formatNumber value="${projectOperationSummary.shuangyueRepayPrincipal}" pattern="#0.00"/>" name="shuangyueRepayPrincipal" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">双月花生累计偿还利息：</label>
			<div class="controls">
			<input type="text" id="shuangyueRepayInterest" value="<fmt:formatNumber value="${projectOperationSummary.shuangyueRepayInterest}" pattern="#0.00"/>" name="shuangyueRepayInterest" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">季花生累计募资额：</label>
			<div class="controls">
			<input type="text" id="jiFinanceAmount" value="<fmt:formatNumber value="${projectOperationSummary.jiFinanceAmount}" pattern="#0.00"/>" name="jiFinanceAmount" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">季花生累计偿还本金：</label>
			<div class="controls">
			<input type="text" id="jiRepayPrincipal" value="<fmt:formatNumber value="${projectOperationSummary.jiRepayPrincipal}" pattern="#0.00"/>" name="jiRepayPrincipal" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">季花生累计偿还利息：</label>
			<div class="controls">
			<input type="text" id="jiRepayInterest" value="<fmt:formatNumber value="${projectOperationSummary.jiRepayInterest}" pattern="#0.00"/>" name="jiRepayInterest" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">双季花生累计募资额：</label>
			<div class="controls">
			<input type="text" id="shuangjiFinanceAmount" value="<fmt:formatNumber value="${projectOperationSummary.shuangjiFinanceAmount}" pattern="#0.00"/>" name="shuangjiFinanceAmount" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">双季花生累计偿还本金：</label>
			<div class="controls">
			<input type="text" id="shuangjiRepayPrincipal" value="<fmt:formatNumber value="${projectOperationSummary.shuangjiRepayPrincipal}" pattern="#0.00"/>" name="shuangjiRepayPrincipal" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">双季花生累计偿还利息：</label>
			<div class="controls">
			<input type="text" id="shuangejiRepayInterest" value="<fmt:formatNumber value="${projectOperationSummary.shuangejiRepayInterest}" pattern="#0.00"/>" name="shuangejiRepayInterest" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年花生累计募资额：</label>
			<div class="controls">
			<input type="text" id="nianFinanceAmount" value="<fmt:formatNumber value="${projectOperationSummary.nianFinanceAmount}" pattern="#0.00"/>" name="nianFinanceAmount" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年花生累计偿还本金：</label>
			<div class="controls">
			<input type="text" id="nianRepayPrincipal" value="<fmt:formatNumber value="${projectOperationSummary.nianRepayPrincipal}" pattern="#0.00"/>" name="nianRepayPrincipal" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年花生累计偿还利息：</label>
			<div class="controls">
			<input type="text" id="nianRepayInterest" value="<fmt:formatNumber value="${projectOperationSummary.nianRepayInterest}" pattern="#0.00"/>" name="nianRepayInterest" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
	</form:form>
</body>
</html>