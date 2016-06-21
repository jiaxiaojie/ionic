<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>还款明细管理</title>
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
		<shiro:hasPermission name="project:projectRepaymentSplitRecord:edit"><li><a href="${ctx}/project/projectRepaymentSplitRecord/projectRepaymentSplitRecordInfoList?projectId=${projectRepaymentSplitRecord.projectId}">还款明细列表</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/project/projectRepaymentSplitRecord/form?id=${projectRepaymentSplitRecord.id}">还款明细<shiro:hasPermission name="project:projectRepaymentSplitRecord:edit">${not empty projectRepaymentSplitRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="project:projectRepaymentSplitRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectRepaymentSplitRecord" action="${ctx}/project/projectRepaymentSplitRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">还款流水号：</label>
			<div class="controls">
				<form:input path="recordId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目流水号：</label>
			<div class="controls">
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款人：</label>
			<div class="controls">
				<form:input path="repaymentUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资流水编号：</label>
			<div class="controls">
				<form:input path="investmentRecordId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款人账号：</label>
			<div class="controls">
				<form:input path="repaymentAccount" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款人：</label>
			<div class="controls">
				<form:input path="payeeUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款人账号：</label>
			<div class="controls">
				<form:input path="payeeAccount" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分配金额：</label>
			<div class="controls">
				<input id="money" name="money"  type="text" value="<fmt:formatNumber value="${projectRepaymentSplitRecord.money}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款类型：</label>
			<div class="controls">
				<form:select path="repayType" class="input-xlarge  required">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictList('project_repayment_type_dict')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">违约金额：</label>
			<div class="controls">
			<input id="penaltyMoney" name="penaltyMoney"  type="text" value="<fmt:formatNumber value="${projectRepaymentSplitRecord.prePenaltyMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本金：</label>
			<div class="controls">
			<input id="principal" name="principal"  value="<fmt:formatNumber value="${projectRepaymentSplitRecord.principal}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利息：</label>
			<div class="controls">
			<input id="interest" name=interest  value="<fmt:formatNumber value="${projectRepaymentSplitRecord.interest}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余本金：</label>
			<div class="controls">
			<input id="remainedPrincipal" name="remainedPrincipal"  value="<fmt:formatNumber value="${projectRepaymentSplitRecord.remainedPrincipal}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款时间：</label>
			<div class="controls">
				<input name="repaymentDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectRepaymentSplitRecord.repaymentDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分配第三方编号：</label>
			<div class="controls">
				<form:input path="thirdPartyOrder" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分配返回码：</label>
			<div class="controls">
				<form:input path="repayResult" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分配状态：</label>
			<div class="controls">
				<form:checkboxes path="status" items="${fns:getDictList('project_transer_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectRepaymentSplitRecord.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改时间：</label>
			<div class="controls">
				<input name="modifyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectRepaymentSplitRecord.modifyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改备注：</label>
			<div class="controls">
				<form:input path="modifyRemark" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="project:projectRepaymentSplitRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>