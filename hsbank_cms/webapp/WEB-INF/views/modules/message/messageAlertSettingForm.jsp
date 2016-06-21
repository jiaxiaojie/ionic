<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息提醒设置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">


		$(document).ready(function() {

			$(function(){
				$("#mobile").keyup(function(){
					var test = $(this).val();
					if(test.trim().length<10){
						if(test.trim().length>10){
							$(this).val(test+',');
						}else{
							$(this).val(test);
						}
					}else{
						var arr = test.split(',');
						var txtArr='';
						for(var i=0;i<arr.length;i++){
							if(arr[i].length>10){
								txtArr+=arr[i]+',';
							} else{
								txtArr+=arr[i];
							}
						}
						$(this).val(txtArr);
					}

				});
			});

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
		<li><a href="${ctx}/message/messageAlertSetting/">消息提醒设置列表</a></li>
		<li class="active"><a href="${ctx}/message/messageAlertSetting/form?id=${messageAlertSetting.id}">消息提醒设置<shiro:hasPermission name="message:messageAlertSetting:edit">${not empty messageAlertSetting.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="message:messageAlertSetting:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="messageAlertSetting" action="${ctx}/message/messageAlertSetting/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('message_alert_purpose')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="control-group" >
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge required"/>
				<span class="help-inline">消息实例：项目名称为#projectName#已满标，请尽快上架新的项目。</span>
			</div>

		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:textarea path="mobile" htmlEscape="false" id="mobile" rows="3"    maxlength="200" class="input-xxlarge required"/>
				<span class="help-inline">可添加多个手机号(以' , '分隔)</span>
			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate  required"
					value="<fmt:formatDate value="${messageAlertSetting.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="message:messageAlertSetting:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>