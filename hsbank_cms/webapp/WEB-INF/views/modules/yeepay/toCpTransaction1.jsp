<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>易宝测试入口</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<%@include file="/WEB-INF/views/include/head.jsp"%>

<script>
	
</script>
</head>
<body>
	<div id="lA7" class="tab-pane">
		<h3>7转账授权明细</h3>
		<hr />
		<form:form class="form-horizontal" modelAttribute="toCpTransactionReq"
			action="${ctx}/yeepay/toCpTransaction">
			<div class="control-group">
				<label class="control-label">商户编号：</label>
				<div class="controls">
					<label><%=request.getParameter("platformNo")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">请求流水号：</label>
				<div class="controls">
					<label><%=request.getParameter("requestNo")%></label>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">出款人平台用户编号：</label>
				<div class="controls">
					<label><%=request.getParameter("platformUserNo")%></label>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">出款人用户类型：</label>
				<div class="controls">
					<label><%=request.getParameter("userType")%> </label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">业务类型：</label>
				<div class="controls">
					<label><%=request.getParameter("bizType")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">超过此时间即不允许提交订单：</label>
				<div class="controls">
					<label><%=request.getParameter("expired")%></label>
				</div>
			</div>


			<div class="control-group">
				<label class="control-label">资金明细记录：</label>
				<div class="controls">
					<label><%=request.getParameter("detail_remark")%></label>
				
				</div>
			</div>


			<div class="control-group">
				<label class="control-label">业务类型：</label>
				<div class="controls">
					<label><%=request.getParameter("bizType")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">业务扩展属性：</label>
				<div class="controls">
					<label><%=request.getParameter("extend")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">页面调回Url：</label>
				<div class="controls">
					<label><%=request.getParameter("callbackUrl")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">服务通知URL：</label>
				<div class="controls">
					<label><%=request.getParameter("notifyUrl")%></label>
				</div>
			</div>
		</form:form>
		<form method="post"  action="<%=request.getAttribute("yeepayURL")%>">

			<textarea style="width: 500px; height: 200px; display: none"
				name="req"><%=request.getAttribute("req")%></textarea>
			<input type="hidden" name="sign"
				value="<%=request.getAttribute("sign")%>" />

			<div class="form:form-actions">
				<input id="lA1Go" class="btn btn-success" type="submit" value="确定" />

			</div>
		</form>
	</div>

</body>
</html>