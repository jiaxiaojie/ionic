<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借贷意向审批管理</title>
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
		<li><a href="${ctx}/project/projectWillLoan/reviewlist">借贷意向审批列表</a></li>
		<li class="active"><a href="${ctx}/project/projectWillLoan/form?id=${projectWillLoan.id}">借贷意向审批<shiro:hasPermission name="project:projectWillLoan:edit">${not empty projectWillLoan.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="project:projectWillLoan:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectWillLoan" action="${ctx}/project/projectWillLoan/review" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				${projectWillLoan.title}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期限：</label>
			<div class="controls">
				${projectWillLoan.duration}个月
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				${projectWillLoan.annualizedRate}
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				${projectWillLoan.contactName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				${projectWillLoan.mobile}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资金额：</label>
			<div class="controls">
			 <fmt:formatNumber value="${projectWillLoan.loanMoney}" pattern="#0.00" /> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务费金额：</label>
			<div class="controls">
			 <fmt:formatNumber value="${projectWillLoan.serviceCharge}" pattern="#0.00" /> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同金额：</label>
			<div class="controls">
			 <fmt:formatNumber value="${projectWillLoan.contractMoney}" pattern="#0.00" /> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款用途：</label>
			<div class="controls">
				${fns:getDictLabel(projectWillLoan.useType, 'customer_credit_loan_use_dict', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在区域：</label>
			<div class="controls">
				${projectWillLoan.area.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注说明：</label>
			<div class="controls">
				${projectWillLoan.remark}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<fmt:formatDate value="${projectWillLoan.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批状态：</label>
			<div class="controls">
			<form:radiobutton path="status"  value="1" id="status_1" checked="checked"/> 通过
			<form:radiobutton path="status"  value="2" id="status_2"/>不通过
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认备注：</label>
			<div class="controls">
				<form:textarea path="confirmRemark" htmlEscape="false" rows="4" minlength="10" maxlength="1000" class="input-xxlarge required" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="project:projectWillLoan:review"><input id="btnSubmit" class="btn btn-primary" type="submit" value="审批"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>