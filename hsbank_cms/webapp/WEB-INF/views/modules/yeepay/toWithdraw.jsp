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
	<div id="lA3" class="tab-pane">
		<h3>3提现明细</h3>
		<hr />
		<form:form class="form-horizontal" modelAttribute="toWithdrawReq"
			action="${ctx}/yeepay/toWithdraw1">
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
				<label class="control-label">费率模式：</label>
				<div class="controls">
					<form:select path="feeMode">
						<form:option value="PLATFORM" label="收取商户手续费"/>  
					    <form:option value="USER" label="收取用户手续费"/>  
					</form:select>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">超过此时间：</label>
				<div class="controls">
					<form:input path="expired" htmlEscape="false" maxlength="20"
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
			<div class="control-group">
				<label class="control-label">提现模式：</label>
				<div class="controls">
					<form:select path="withdrawType">
						<form:option value="NORMAL" label="正常提现，T+1 天到账"/>  
					    <form:option value="URGENT" label="加急提现，T+0 当日到账"/>  
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">提现金额：</label>
				<div class="controls">
					<form:input path="amount" htmlEscape="false" maxlength="200"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>

			<div class="form:form-actions">
				<input id="lA3Go" class="btn btn-success" type="submit" value="确定" />
			</div>
		</form:form>
	</div>
</body>
</html>