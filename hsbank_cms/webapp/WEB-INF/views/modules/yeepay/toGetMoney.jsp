<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>易宝测试入口</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<%@include file="/WEB-INF/views/include/head.jsp"%>


</head>
<body>
	<div id="lA7" class="tab-pane">
		<h3>12用户转账授权明细</h3>
		<hr/>
		<form:form class="form-horizontal" modelAttribute="toCpTransactionReq"
			action="${ctx}/yeepay/toGetMoney1">
			<div class="control-group">
				<label class="control-label">商户编号：</label>
				<div class="controls">
					<form:input path="platformNo" readonly="true" htmlEscape="false"
						maxlength="20" class="input-xlarge required"
						style="height:32px;width:440px" />
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
				<label class="control-label">出款人平台用户编号：</label>
				<div class="controls">
					<form:input path="platformUserNo" htmlEscape="false" maxlength="20"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">出款人金额：</label>
				<div class="controls">
					<input  type="text" name="amount" htmlEscape="false" maxlength="50"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">出款人用户类型：</label>
				<div class="controls">
					<form:input path="userType" value="MEMBER" htmlEscape="false"
						maxlength="20" class="input-xlarge required"
						style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">业务类型：</label>
				<div class="controls">
					<form:select path="bizType"  class="input-xlarge">
						<form:option value="TRANSFER" label="转账" />
					</form:select>
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
				<input id="lA7Go" class="btn btn-success" type="submit" value="确定" />
			</div>
		</form:form>
	</div>

</body>
</html>