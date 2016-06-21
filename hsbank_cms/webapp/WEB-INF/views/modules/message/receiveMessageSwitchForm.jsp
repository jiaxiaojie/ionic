<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户接收消息开关管理</title>
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
		<li><a href="${ctx}/message/receiveMessageSwitch/">用户接收消息开关列表</a></li>
		<li class="active"><a href="${ctx}/message/receiveMessageSwitch/form?id=${receiveMessageSwitch.id}">用户接收消息开关<shiro:hasPermission name="message:receiveMessageSwitch:edit">${not empty receiveMessageSwitch.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="message:receiveMessageSwitch:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<jsp:include page="../customer/common/customerMessageMenu.jsp"/>
	<form:form id="inputForm" modelAttribute="receiveMessageSwitch" action="${ctx}/message/receiveMessageSwitch/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">规则编号：</label>
			<div class="controls">
				<form:input path="ruleId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">渠道：</label>
			<div class="controls">
				<form:input path="messageChannel" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户编号：</label>
			<div class="controls">
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否接收：</label>
			<div class="controls">
				<form:select path="isReceive" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_receive_message')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="message:receiveMessageSwitch:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>