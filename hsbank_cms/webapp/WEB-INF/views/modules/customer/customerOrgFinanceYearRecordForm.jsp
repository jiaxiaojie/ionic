<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业会员财务年表管理</title>
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
		<li><a href="${ctx}/customer/customerAccount/">会员列表</a></li>
		<li><a href="${ctx}/customer/customerBase/form?id=${customerOrgFinanceYearRecord.customerId}">会员<shiro:hasPermission name="customer:customerBase:edit">${not empty customerOrgFinanceYearRecord.customerId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerBase:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/customer/customerOrgFinanceYearRecord/list?customerId=${customerOrgFinanceYearRecord.customerId}">企业会员财务年表列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerOrgFinanceYearRecord/form?id=${customerOrgFinanceYearRecord.id}">企业会员财务年表<shiro:hasPermission name="customer:customerOrgFinanceYearRecord:edit">${not empty customerOrgFinanceYearRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerOrgFinanceYearRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerOrgFinanceYearRecord" action="${ctx}/customer/customerOrgFinanceYearRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
				<form:input path="yearId" htmlEscape="false" maxlength="4" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主营收入（万）：</label>
			<div class="controls">
				<form:input path="mainRevenue" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毛利润（万）：</label>
			<div class="controls">
				<form:input path="grossProfit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">净利润（万）：</label>
			<div class="controls">
				<form:input path="netProfit" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总资产（万）：</label>
			<div class="controls">
				<form:input path="totalAssets" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">净资产（万）：</label>
			<div class="controls">
				<form:input path="netAssets" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerOrgFinanceYearRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>