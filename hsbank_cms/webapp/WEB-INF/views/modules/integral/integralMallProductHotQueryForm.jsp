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
		<li class="active"><a href="${ctx}/integral/integralMallProductHot/queryList?productId=${integralMallProductHot.productId}">产品活动</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="integralMallProductHot" action="${ctx}/integral/integralMallProductHot/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="productId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标签类型：</label>
			<div class="controls">
				<form:select path="hotType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('integral_project_hot_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签值：</label>
			<div class="controls">
				<form:input path="hotValue" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>