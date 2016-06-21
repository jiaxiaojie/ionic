<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>易宝测试入口</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<%@include file="/WEB-INF/views/include/head.jsp" %>

<script>
	
</script>
</head>
<body>
	<div id="lA6" class="tab-pane">
		<h3>6企业注册授权明细</h3>
		<hr />
		<form:form class="form-horizontal" modelAttribute="toEnterpriseRegisterReq"
			action="${ctx}/yeepay/toEnterpriseRegister1">
			<div class="control-group">
				<label class="control-label">商户编号：</label>
				<div class="controls">
					<form:input path="platformNo"  readonly="true"
						htmlEscape="false" maxlength="20" class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">请求流水号：</label>
				<div class="controls">
					<form:input path="requestNo" htmlEscape="false" maxlength="50"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">商户平台会员标识：</label>
				<div class="controls">
					<form:input path="platformUserNo" htmlEscape="false" maxlength="20"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">企业名称：</label>
				<div class="controls">
					<form:input path="enterpriseName" htmlEscape="false" maxlength="50"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">开户银行许可证：</label>
				<div class="controls">
					<form:input path="bankLicense" htmlEscape="false" maxlength="20"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">组织机构代码：</label>
				<div class="controls">
					<form:input path="orgNo" htmlEscape="false" maxlength="50"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">营业执照编号：</label>
				<div class="controls">
					<form:input path="businessLicense" htmlEscape="false" maxlength="20"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">税务登记号：</label>
				<div class="controls">
					<form:input path="taxNo" htmlEscape="false" maxlength="50"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">法人姓名：</label>
				<div class="controls">
					<form:input path="legal" htmlEscape="false" maxlength="20"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">法人身份证号：</label>
				<div class="controls">
					<form:input path="legalIdNo" htmlEscape="false" maxlength="50"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">企业联系人：</label>
				<div class="controls">
					<form:input path="contact" htmlEscape="false" maxlength="20"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">联系人手机号：</label>
				<div class="controls">
					<form:input path="contactPhone" htmlEscape="false" maxlength="50"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">联系人邮箱：</label>
				<div class="controls">
					<form:input path="email" htmlEscape="false" maxlength="20"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">企业会员类型：</label>
				<div class="controls">
					select ENTERPRISE：企业借款人 GUARANTEE_CORP：担保公司
					
					<form:input path="memberClassType" htmlEscape="false" maxlength="20"
						class="input-xlarge required" style="height:32px;width:440px" /> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">页面调回Url：</label>
				<div class="controls">
					<form:input path="callbackUrl" htmlEscape="false" maxlength="200"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">服务通知URL：</label>
				<div class="controls">
					<form:input path="notifyUrl" htmlEscape="false" maxlength="200"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="form:form-actions">
				<input id="lA6Go" class="btn btn-success" type="submit" value="确定" />
			</div>
		</form:form>
	</div>
</body>
</html>