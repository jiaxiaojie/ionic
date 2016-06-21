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
	<div id="lA8" class="tab-pane">
		<h3>8自动投标授权明细</h3>
		<hr />
		<form:form class="form-horizontal" modelAttribute="toAuthorizeAutoTransferReq"
			action="${ctx}/yeepay/toAuthorizeAutoTransfer1">
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
				<input id="lA8Go" class="btn btn-success" type="submit" value="确定" />
			</div>
		</form:form>
	</div>

</body>
</html>