<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据查询管理</title>
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
		<li><a href="${ctx}/operation/dataQuery/">数据查询列表</a></li>
		<li class="active"><a href="${ctx}/operation/dataQuery/form?id=${dataQuery.id}">数据查询<shiro:hasPermission name="operation:dataQuery:edit">${not empty dataQuery.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="operation:dataQuery:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dataQuery" action="${ctx}/operation/dataQuery/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">fromContent：</label>
			<div class="controls">
				<form:textarea path="fromContent" htmlEscape="false" rows="4" class="input-xxlarge "/>
				<span class="help-inline">无需关键字from</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">filterContent：</label>
			<div class="controls">
				<form:textarea path="filterContent" htmlEscape="false" rows="4" class="input-xxlarge "/>
				<span class="help-inline">无需关键字where</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">groupByContent：</label>
			<div class="controls">
				<form:textarea path="groupByContent" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
				<span class="help-inline">无需关键字group by</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">havingContent：</label>
			<div class="controls">
				<form:textarea path="havingContent" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
				<span class="help-inline">无需关键字having</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">orderByContent：</label>
			<div class="controls">
				<form:textarea path="orderByContent" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
				<span class="help-inline">无需关键字order by</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">limitContent：</label>
			<div class="controls">
				<form:textarea path="limitContent" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
				<span class="help-inline">无需关键字limit</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">说明：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="operation:dataQuery:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>