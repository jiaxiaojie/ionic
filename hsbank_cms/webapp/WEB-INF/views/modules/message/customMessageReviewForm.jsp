<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义消息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(":input").not($("[name=status]")).not($("#reviewRemark")).not($("#btnSubmit")).not($("#btnCancel")).not($("[type=hidden]")).attr("disabled",true);
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
		<li><a href="${ctx}/message/customMessage/reviewList">消息审批列表</a></li>
				<li class="active"><a href="${ctx}/message/customMessage/reviewForm?id=${customMessage.id}">消息审批</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customMessage" action="${ctx}/message/customMessage/review" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="280" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收用户类型：</label>
			<div class="controls">
				<form:select path="receiverType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('custom_message_receiver_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">接收用户：</label>
			<div class="controls">
				<form:hidden id="attr2" path="receiverData" htmlEscape="false" maxlength="200" class="input-xlarge required" />
				<sys:ckfinder input="attr2" type="files" uploadPath="/message" selectMultiple="false" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送渠道：</label>
			<div class="controls">
				<form:checkboxes path="messageChannelList"  items="${fns:getDictList('message_channel')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">标签：</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">是否紧急：</label>
			<div class="controls">
				<form:select path="isUrgent" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">目标类型：</label>
			<div class="controls">
				<form:select path="targetType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('photo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
				 <div class="control-group">
			<label class="control-label">目标参数：</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div> 
				<div class="control-group">
			<label class="control-label">是否可点击：</label>
			<div class="controls">
				<form:select path="isClick" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预计发送时间：</label>
			<div class="controls">
				<input name="sendDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customMessage.sendDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium  Wdate "
					value="<fmt:formatDate value="${customMessage.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批意见：</label>
			<div class="controls">
				<form:textarea path="reviewRemark" htmlEscape="false" maxlength="500" rows="4"  class="input-xlarge required"/>
			</div>
		</div>
		  <div class="control-group">
			<label class="control-label">审批状态：</label>
			<div class="controls">
				<form:radiobutton path="status"  value="2" class="required"/> 通过
 				<form:radiobutton path="status"  value="-1" class="required"/>不通过
 			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="message:customMessage:review"><input id="btnSubmit" class="btn btn-primary" type="submit" value="审批"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>