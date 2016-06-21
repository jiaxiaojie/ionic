<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投资记录管理</title>
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
		<li><a href="${ctx}/project/projectInvestmentRecord/">投资记录列表</a></li>
		<li class="active"><a href="${ctx}/project/projectInvestmentRecord/form?id=${projectInvestmentRecord.id}">投资记录<shiro:hasPermission name="project:projectInvestmentRecord:edit">${not empty projectInvestmentRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="project:projectInvestmentRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectInvestmentRecord" action="${ctx}/project/projectInvestmentRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目流水号：</label>
			<div class="controls">
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资人：</label>
			<div class="controls">
				<form:input path="investmentUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资金额：</label>
			<div class="controls">
			<input id="money" name="money" type="text"  value="<fmt:formatNumber value="${projectInvestmentRecord.money}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际金额：</label>
			<div class="controls">
			<input id="actualAmount" name="actualAmount"  type="text" value="<fmt:formatNumber value="${projectInvestmentRecord.actualAmount}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现金券抵用金额：</label>
			<div class="controls">
			<input id="ticketMoney" name="ticketMoney" type="text"  value="<fmt:formatNumber value="${projectInvestmentRecord.ticketMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现金券清单：</label>
			<div class="controls">
				<form:input path="ticketIds" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资方式：</label>
			<div class="controls">
				<form:select path="investmentType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_investment_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作终端：</label>
			<div class="controls">
				<form:select path="opTerm" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资时间：</label>
			<div class="controls">
				<input name="opDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectInvestmentRecord.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_investment_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资交易第三方流水编号：</label>
			<div class="controls">
				<form:input path="thirdPartyOrder" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="project:projectInvestmentRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>