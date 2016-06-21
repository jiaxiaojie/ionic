<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>加息券类型管理</title>
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
		<li><a href="${ctx}/sys/rateTicketType/">加息券类型列表</a></li>
		<li class="active"><a href="${ctx}/sys/rateTicketType/form?id=${rateTicketType.id}">加息券类型<shiro:hasPermission name="sys:rateTicketType:edit">${not empty rateTicketType.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:rateTicketType:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rateTicketType" action="${ctx}/sys/rateTicketType/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">加息券名称：</label>
			<div class="controls">
				<form:input path="ticketTypeName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加息：</label>
			<div class="controls">
				<form:input path="rate" htmlEscape="false" maxlength="5" max="1" class="input-xlarge  number required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加息期限（天）：</label>
			<div class="controls">
				<form:input path="rateDuration" htmlEscape="false" maxlength="9" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">额度上限（元）：</label>
			<div class="controls">
				<input type="text" name="maxAmount" id="maxAmount" value='<fmt:formatNumber value="${rateTicketType.maxAmount }" pattern="#.#"/>' htmlEscape="false" maxlength="11" class="input-xlarge number required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效期限制（天）：</label>
			<div class="controls">
				<form:input path="termOfValidity" htmlEscape="false" maxlength="9" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用说明：</label>
			<div class="controls">
				<form:textarea path="useDescription" htmlEscape="false" rows="3" maxlength="1000" class="input-xlarge required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:rateTicketType:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>