<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>营销活动补送奖励</title>
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
		<li><a href="${ctx}/marketing/marketingActivityAwardRecord/">营销活动奖励记录列表</a></li>
		<li class="active"><a href="${ctx}/marketing/marketingActivityAwardRecord/patchAward">营销活动补送奖励<shiro:hasPermission name="marketing:marketingActivityAwardRecord:edit"></shiro:hasPermission><shiro:lacksPermission name="marketing:marketingActivityAwardRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="marketingActivityAwardRecord" action="${ctx}/marketing/marketingActivityAwardRecord/patchAward" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">关联活动：</label>
			<div class="controls">
				<form:select path="activityId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${marketingActivityInfoList}" itemLabel="name" itemValue="acticityId" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户行为：</label>
			<div class="controls">
				<form:select path="behaviorCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('marketing_behavior_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户手机号：</label>
			<div class="controls">
				<form:input path="customerMobile" htmlEscape="false" maxlength="20" class="input-xlarge required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖励产品类型：</label>
			<div class="controls">
				<form:select path="awardType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('marketing_award_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖励值：</label>
			<div class="controls">
				<form:input path="awardValue" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖励来源类型：</label>
			<div class="controls">
				<form:select path="causeType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('market_award_cause_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">因何种原因活动的奖励</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">来源编号：</label>
			<div class="controls">
				<form:input path="causeId" htmlEscape="false" maxlength="100" class="input-xlarge digits"/>
				<span class="help-inline">获得奖励时相关的记录编号（如好友会员编号、好友投资记录编号，可为空或0）</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖励说明：</label>
			<div class="controls">
				<form:input path="awardReason" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="marketing:marketingActivityAwardRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="补送奖励"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>