<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>易宝测试入口</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<%@include file="/WEB-INF/views/include/head.jsp"%>

<script>
	function doSelect(val) {
		if(val=='TENDER'){
			$('#detail').val('投标可以附加项目编号及名称等信息，模拟为yangtao投资编号为A00001的项目10元，其中1元给平台，9元给借款人yudetao');
		}else if(val=='REPAYMENT'){
			$('#detail').val('还款在易宝侧有订单号校验，故暂不通过界面模拟');
		}else if(val=='CREDIT_ASSIGNMENT'){
			$('#detail').val('债权转让在易宝侧有借款关系校验，故暂不通过界面模拟');
		}else if(val='TRANSFER'){
			$('#detail').val('此转账授权模拟为yangtao用户 给平台0.01元，给固定用户yudetao1元');
		}
	}
</script>
</head>
<body>
	<div id="lA7" class="tab-pane">
		<h3>7转账授权明细</h3>
		<hr/>
		<form:form class="form-horizontal" modelAttribute="toCpTransactionReq"
			action="${ctx}/yeepay/toCpTransaction1">
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
					<form:select path="bizType" onchange="doSelect(this.value)" class="input-xlarge">
						<form:option value="TENDER" label="投标" />
						<form:option value="REPAYMENT" label="还款" />
						<form:option value="CREDIT_ASSIGNMENT" label="债权转让" />
						<form:option value="TRANSFER" label="转账" />
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">超过此时间即不允许提交订单：</label>
				<div class="controls">
					<form:input path="expired" htmlEscape="false" maxlength="20"
						class="input-xlarge required" style="height:32px;width:440px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">资金明细记录：</label>
				<div class="controls">
					<textarea id="detail" htmlEscape="false" name="detail_remark"
						class="input-xlarge required" style="height: 34px; width: 640px"></textarea>
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