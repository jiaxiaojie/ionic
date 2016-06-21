<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>托管账号参数管理</title>
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
		<li class="active"><a href="${ctx}/sys/thirdPartyYeepayPara/form?id=${thirdPartyYeepayPara.id}">托管账号参数<shiro:hasPermission name="sys:thirdPartyYeepayPara:edit">${not empty thirdPartyYeepayPara.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:thirdPartyYeepayPara:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="thirdPartyYeepayPara" action="${ctx}/sys/thirdPartyYeepayPara/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">易宝商户编号：</label>
			<div class="controls">
				<form:input path="yeepayPlatformNo" htmlEscape="false" maxlength="50" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">易宝浏览器网关地址前缀：</label>
			<div class="controls">
				<form:input path="yeepayGateUrlPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">易宝直接接口地址前缀：</label>
			<div class="controls">
				<form:input path="yeepayDirectUrlPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">易宝加密校验地址前缀：</label>
			<div class="controls">
				<form:input path="yeepaySignRulPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">易宝浏览器返回地址前缀(验证)：</label>
			<div class="controls">
				<form:input path="yeepayCallbackUrlPrefixDemo" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">易宝浏览器通知地址前缀(验证)：</label>
			<div class="controls">
				<form:input path="yeepayNotifyUrlPrefixDemo" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">易宝浏览器返回地址前缀(生产)：</label>
			<div class="controls">
				<form:input path="yeepayCallbackUrlPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">易宝浏览器通知地址前缀(生产)：</label>
			<div class="controls">
				<form:input path="yeepayNotifyUrlPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">测试环境传给易宝trenderNo前缀：</label>
			<div class="controls">
				<form:input path="yeepayTenderordernoPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">易宝前端浏览器callback地址前缀：</label>
			<div class="controls">
				<form:input path="yeepayGateWayCallbackUrlPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">易宝前端浏览器notify地址前缀：</label>
			<div class="controls">
				<form:input path="yeepayGateWayNotifyPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">易宝直连notify地址前缀：</label>
			<div class="controls">
				<form:input path="yeepayDirectNotifyUrlPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">易宝前端浏览器移动端callback地址前缀：</label>
			<div class="controls">
				<form:input path="yeepayGateWayWirelessCallbackUrlPrefix" htmlEscape="false" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="sys:thirdPartyYeepayPara:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>