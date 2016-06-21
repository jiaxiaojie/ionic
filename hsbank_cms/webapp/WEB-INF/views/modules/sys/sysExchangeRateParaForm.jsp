<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇率参数管理</title>
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
		<li><a href="${ctx}/sys/sysExchangeRatePara/">汇率参数列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysExchangeRatePara/form?id=${sysExchangeRatePara.id}">汇率参数<shiro:hasPermission name="sys:sysExchangeRatePara:edit">${not empty sysExchangeRatePara.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysExchangeRatePara:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysExchangeRatePara" action="${ctx}/sys/sysExchangeRatePara/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">汇率类型：</label>
			<div class="controls">
				<form:select path="rateType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exchange_rate_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">汇率名称：</label>
			<div class="controls">
				<form:input path="rateName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				<form:input path="annualizedRate" htmlEscape="false" minmore="0" max="1" maxlength="6" class="input-xlarge number required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysExchangeRatePara:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>