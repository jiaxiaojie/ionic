<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>大转盘中奖记录管理</title>
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
		<li><a href="${ctx}/marketing/marketingWheelPrizeInfo/">大转盘奖品列表</a></li>
		<li class="active"><a href="${ctx}/marketing/marketingWheelPrizeInfo/form?id=${marketingWheelPrizeInfo.id}">大转盘奖品<shiro:hasPermission name="marketing:marketingWheelPrizeInfo:edit">${not empty marketingWheelPrizeInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="marketing:marketingWheelPrizeInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="marketingWheelPrizeInfo" action="${ctx}/marketing/marketingWheelPrizeInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">活动名称：</label>
			<div class="controls">
				<form:select path="activityId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${activityList}" itemLabel="name" itemValue="acticityId" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖品名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖品标签：</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖品类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('marketing_award_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖品值：</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">获奖提示：</label>
			<div class="controls">
				<form:input path="getTips" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖品描述：</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖品logo：</label>
			<div class="controls">
				<form:input path="logo" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖品数量：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="11" readonly="${empty marketingWheelPrizeInfo.id ? false : true }" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">中奖角度：</label>
			<div class="controls">
				<form:input path="rotate" htmlEscape="false" class="input-xlarge number required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="marketing:marketingWheelPrizeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>