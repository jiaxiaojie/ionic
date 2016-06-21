<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>现金券类型管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
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
		<li><a href="${ctx}/sys/investmentTicketType/">现金券类型列表</a></li>
		<li class="active"><a
			href="${ctx}/sys/investmentTicketType/form?id=${investmentTicketType.id}">现金券类型<shiro:hasPermission
					name="sys:investmentTicketType:edit">${not empty investmentTicketType.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="sys:investmentTicketType:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="investmentTicketType"
		action="${ctx}/sys/investmentTicketType/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">现金券名称：</label>
			<div class="controls">
				<form:input path="ticketTypeName" htmlEscape="false" maxlength="20"
					class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面值：</label>
			<div class="controls">
				<form:input path="denomination" htmlEscape="false" maxlength="11"
					class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用说明：</label>
			<div class="controls">
				<form:input path="useInfo" htmlEscape="false" maxlength="500"
					class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用限制：</label>
			<div class="controls">
				<form:input path="useLimit" htmlEscape="false" maxlength="11"
					class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效期限制：</label>
			<div class="controls">
				<form:input path="termOfValidity" htmlEscape="false" maxlength="11"
					class="input-xlarge " />
			</div>
		</div>

		<c:choose>
			<c:when test="${empty investmentTicketType.id}">

			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">状态：</label>
					<form:hidden path="status"/>
					<div class="controls">
						<label class="control-label">${fns:getDictLabel(investmentTicketType.status, 'del_flag', '')}</label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">创建人：</label>
					<div class="controls">
						<form:input path="cUser.name" htmlEscape="false" maxlength="20"
							class="input-xlarge " readonly="true" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">创建时间：</label>
					<div class="controls">
						<input name="createDt" type="text" readonly="readonly"
							maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${investmentTicketType.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">最后一次修改人：</label>
					<div class="controls">
						<form:input path="mUser.name" htmlEscape="false" maxlength="20"
							class="input-xlarge " readonly="true" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">修改时间：</label>
					<div class="controls">
						<input name="lastModifyDt" type="text" readonly="readonly"
							maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${investmentTicketType.lastModifyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="form-actions">
			<c:choose>
				<c:when test="${investmentTicketType.status=='1'}">
					
				</c:when>
				<c:otherwise>
					<shiro:hasPermission name="sys:investmentTicketType:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit"
							value="保 存" />&nbsp;</shiro:hasPermission>
				</c:otherwise>
			</c:choose>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>