<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息产生规则审批</title>
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
		<li><a href="${ctx}/message/messageCreateRule/reviewList">消息产生规则审批列表</a></li>
		<li class="active"><a href="${ctx}/message/messageCreateRule/reviewForm?id=${messageCreateRule.id}">消息产生规则<shiro:hasPermission name="message:messageCreateRule:edit">${not empty messageCreateRule.id?'审批':'添加'}</shiro:hasPermission><shiro:lacksPermission name="message:messageCreateRule:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="messageCreateRule" action="${ctx}/message/messageCreateRule/review" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" readonly="true" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户行为：</label>
			<div class="controls">
				<form:select path="behaviorCode" disabled="true" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${messageBehaviorTypeList}" itemLabel="behaviorName" itemValue="behaviorCode" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送渠道：</label>
			<div class="controls">
				<form:checkboxes path="messageChannelList" readonly="true" items="${fns:getDictList('message_channel')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline">现仅支持：web、短信</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">适用终端：</label>
			<div class="controls">
				<form:checkboxes path="termList" readonly="true" items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息标题：</label>
			<div class="controls">
				<form:input path="messageTitle" readonly="true" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息内容：</label>
			<div class="controls">
				<form:textarea path="messageContent" readonly="true" rows="5" cols="30" htmlEscape="false" maxlength="280" class="input-xlarge required" />
				<span class="help-inline">支持变量#accountId#,#customerName#,#mobile#,#amount#,#date#,#count#,#params#</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息类型：</label>
			<div class="controls">
				<form:select path="messageType" disabled="true" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签：</label>
			<div class="controls">
				<form:input path="label" readonly="true" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实现类名：</label>
			<div class="controls">
				<form:input path="className" readonly="true" htmlEscape="false" maxlength="40" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始日期：</label>
			<div class="controls">
				<input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${messageCreateRule.startDate}" pattern="yyyy-MM-dd"/>" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${messageCreateRule.endDate}" pattern="yyyy-MM-dd"/>" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间段：</label>
			<div class="controls">
				<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="${messageCreateRule.startTime}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间段：</label>
			<div class="controls">
				<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="${messageCreateRule.endTime}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatDate value="${messageCreateRule.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="true" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批：</label>
			<div class="controls">
				<input type="radio" name="status" value="1" class="required"/>通过
				<input type="radio" name="status" value="-1" class="required"/>不通过
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="message:messageCreateRule:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>