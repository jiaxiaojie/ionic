<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信用等级信息管理</title>
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
		<li><a href="${ctx}/sys/creditLevelInfo/">信用等级信息列表</a></li>
		<li class="active"><a href="${ctx}/sys/creditLevelInfo/form?id=${creditLevelInfo.id}">信用等级信息<shiro:hasPermission name="sys:creditLevelInfo:edit">${not empty creditLevelInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:creditLevelInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="creditLevelInfo" action="${ctx}/sys/creditLevelInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">得分范围最小值：</label>
			<div class="controls">
				<form:input path="minScore" htmlEscape="false" maxlength="11" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">得分范围最大值：</label>
			<div class="controls">
				<form:input path="maxScore" htmlEscape="false" maxlength="11" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信用等级：</label>
			<div class="controls">
				<form:input path="creditLevel" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信用额度：</label>
			<div class="controls">
				<form:input path="creditLimit" htmlEscape="false" maxlength="20" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:creditLevelInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>