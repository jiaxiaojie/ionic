<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动管理</title>
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
		<li><a href="${ctx}/content/activity/">活动列表</a></li>
		<li class="active"><a href="${ctx}/content/activity/reviewForm?id=${activity.id}">活动审批</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="activity" action="${ctx}/content/activity/review" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">活动名称：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="30" class="input-xlarge" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动描述：</label>
			<div class="controls">
				<form:input path="activityDescription" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动链接：</label>
			<div class="controls">
				<form:input path="activityJoin" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('photo_type')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">活动链接：</label>
			<div class="controls">
				<form:input path="activityJoin" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>

	<%--	<div class="control-group">
			<label class="control-label">目标参数：</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false" maxlength="300" class="input-xlarge  "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">活动封面：</label>
			<div class="controls">
				
				<form:hidden id="activityCover" path="activityCover" htmlEscape="false" maxlength="500" class="input-xlarge"/>
				<li><img src="${activity.activityCover}" url="${activity.activityCover}" style="max-width:200px;max-height:200px;_height:200px;border:0;padding:3px;"></li>
			</div>
		</div>
		
		
		
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${activity.startDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${activity.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					 />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">审批状态：</label>
			<div class="controls">
				<form:radiobutton path="activityStatus"  value="1" id="status_1" checked="checked"/> 通过
				<form:radiobutton path="activityStatus"  value="-1" id="status_0"/>不通过
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="content:activity:review:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="审核"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>