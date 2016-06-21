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
	<div id="lA6" class="tab-pane">
		<h3>6企业注册授权明细</h3>
		<hr />
		<form:form class="form-horizontal"
			modelAttribute="toEnterpriseRegisterReq"
			action="${ctx}/yeepay/toEnterpriseRegister">
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
				<label class="control-label">商户平台会员标识：</label>
				<div class="controls">
					<label><%=request.getParameter("platformUserNo")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">企业名称：</label>
				<div class="controls">
					<label><%=request.getParameter("enterpriseName")%></label>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">开户银行许可证：</label>
				<div class="controls">
					<label><%=request.getParameter("bankLicense")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">组织机构代码：</label>
				<div class="controls">
					<label><%=request.getParameter("orgNo")%></label>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">营业执照编号：</label>
				<div class="controls">
					<label><%=request.getParameter("businessLicense")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">税务登记号：</label>
				<div class="controls">
					<label><%=request.getParameter("taxNo")%></label>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">法人姓名：</label>
				<div class="controls">
					<label><%=request.getParameter("legal")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">法人身份证号：</label>
				<div class="controls">
					<label><%=request.getParameter("legalIdNo")%></label>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">企业联系人：</label>
				<div class="controls">
					<label><%=request.getParameter("contact")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">联系人手机号：</label>
				<div class="controls">
					<label><%=request.getParameter("contactPhone")%></label>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">联系人邮箱：</label>
				<div class="controls">
					<label><%=request.getParameter("email")%></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">企业会员类型：</label>
				<div class="controls">
					select ENTERPRISE：企业借款人 GUARANTEE_CORP：担保公司 <input
						id="memberClassType")%></label>
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