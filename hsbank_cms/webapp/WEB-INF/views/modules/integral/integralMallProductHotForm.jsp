<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品活动标签管理</title>
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
		<li><a href="${ctx}/integral/integralMallProductHot/list?productId=${integralMallProductHot.productId}">产品活动标签列表</a></li>
		<li class="active"><a href="${ctx}/integral/integralMallProductHot/form?id=${integralMallProductHot.id}">产品活动标签<shiro:hasPermission name="integral:integralMallProductHot:edit">${not empty integralMallProductHot.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="integral:integralMallProductHot:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="integralMallProductHot" action="${ctx}/integral/integralMallProductHot/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="productId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标签类型：</label>
			<div class="controls">
				<form:select path="hotType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('integral_project_hot_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签值：</label>
			<div class="controls">
				<form:input path="hotValue" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="integral:integralMallProductHot:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>