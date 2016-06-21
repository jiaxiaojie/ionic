<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品规格参数管理</title>
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
		<li><a href="${ctx}/integral/integralMallProductSps/list?productId=${integralMallProductSps.productId}">产品规格参数列表</a></li>
		<li class="active"><a href="${ctx}/integral/integralMallProductSps/form?id=${integralMallProductSps.paraId}">产品规格参数<shiro:hasPermission name="integral:integralMallProductSps:edit">${not empty integralMallProductSps.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="integral:integralMallProductSps:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="integralMallProductSps" action="${ctx}/integral/integralMallProductSps/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="productId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">参数名称：</label>
			<div class="controls">
				<form:input path="paraName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参数值：</label>
			<div class="controls">
			    <form:textarea path="paraVal" htmlEscape="false" rows="4" maxlength="1024" class="input-xxlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参数顺序：</label>
			<div class="controls">
				<form:input path="paraSeq" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('integral_mall_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="integral:integralMallProductSps:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>