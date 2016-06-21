<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务参数设置管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysBizPara/form?id=${sysBizPara.id}">业务参数设置<shiro:hasPermission name="sys:sysBizPara:edit">${not empty sysBizPara.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysBizPara:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysBizPara" action="${ctx}/sys/sysBizPara/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">缺省风控文章Id：</label>
			<div class="controls">
				<form:input path="projectDefaultRiskArticleId" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最新公告栏目编号：</label>
			<div class="controls">
				<form:input path="cmsZxggCategoryId" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最新活动栏目编号：</label>
			<div class="controls">
				<form:input path="cmsZxhdCategoryId" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">富定新闻栏目编号：</label>
			<div class="controls">
				<form:input path="cmsZxxwCategoryId" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提前还款罚息比例：</label>
			<div class="controls">
				<form:input path="projectEarlyRepayRatio" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">逾期还款日罚息比例：</label>
			<div class="controls">
				<form:input path="projectOverdueRepayRatio" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人信用贷年化利率：</label>
			<div class="controls">
				<form:input path="projectPersonCreditLoanYearRate" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人信用贷服务费费率：</label>
			<div class="controls">
				<form:input path="projectPersonalCreditLoanServiceRate" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人信用贷最高额度：</label>
			<div class="controls">
				<form:input path="projectPersonalCreditLoanMaxValue" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人信用贷缺省期数：</label>
			<div class="controls">
				<form:input path="projectPersonalCreditLoanMaxTerms" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资转让手续费费率：</label>
			<div class="controls">
				<form:input path="projectTransferServiceRate" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">逾期代偿天数：</label>
			<div class="controls">
				<form:input path="projectMaxOverdueDayCount" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">距下一个收款日N天内不允许转让：</label>
			<div class="controls">
				<form:input path="projectTransferDayCountToNextRepay" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权转让上家费率：</label>
			<div class="controls">
				<form:input path="projectTransferServiceUpRate" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权转让下家费率：</label>
			<div class="controls">
				<form:input path="projectTrnasferServiceDownRate" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台垫付金额比例：</label>
			<div class="controls">
				<form:input path="platformAmountRate" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资时收取服务费的比例：</label>
			<div class="controls">
				<form:input path="projectServiceChargeRate" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资时可使用现金券的比例：</label>
			<div class="controls">
				<form:input path="projectTicketAmountRate" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权转让周期：</label>
			<div class="controls">
				<form:input path="projectTransferMaxAssignmentHours" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">最大投资金额：</label>
			<div class="controls">
				<form:input path="projectMaxAmountDefault" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">每日最多提现次数：</label>
			<div class="controls">
				<form:input path="onedayMaxWithdrawCount" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div> 
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysBizPara:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>