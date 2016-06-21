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
		<shiro:hasPermission name="project:projectRepaymentRecord:edit"><li><a href="${ctx}/project/projectRepaymentRecord/">还款记录列表</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/project/projectRepaymentRecord/form?id=${projectRepaymentRecord.id}">还款记录<shiro:hasPermission name="project:projectRepaymentRecord:edit">${not empty projectRepaymentRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="project:projectRepaymentRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectRepaymentRecord" action="${ctx}/project/projectRepaymentRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">还款流水号：</label>
			<div class="controls">
				<form:input path="recordId" htmlEscape="false" maxlength="20" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目流水号：</label>
			<div class="controls">
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对应计划流水号：</label>
			<div class="controls">
				<form:input path="planId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款日期：</label>
			<div class="controls">
				<input name="repaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectRepaymentRecord.repaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款渠道：</label>
			<div class="controls">
				<form:select path="repaymentChannelId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款总金额：</label>
			<div class="controls">
			<input id="sumMoney" name="sumMoney"  type="text" value="<fmt:formatNumber value="${projectRepaymentRecord.sumMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款状态：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款拆分余额：</label>
			<div class="controls">
			<input id="splitBalance" name="splitBalance" type="text"  value="<fmt:formatNumber value="${projectRepaymentRecord.splitBalance}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="project:projectRepaymentRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>