<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>营销活动管理</title>
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
		<li><a href="${ctx}/marketing/marketingActivityInfo/reviewList">营销活动列表</a></li>
		<li class="active"><a href="${ctx}/marketing/marketingActivityInfo/reviewForm?acticityId=${marketingActivityInfo.acticityId}">营销活动<shiro:hasPermission name="marketing:marketingActivityInfo:edit">审核</shiro:hasPermission><shiro:lacksPermission name="marketing:marketingActivityInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="marketingActivityInfo" action="${ctx}/marketing/marketingActivityInfo/review" method="post" class="form-horizontal">
		<form:hidden path="acticityId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">活动名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动渠道限制：</label>
			<div class="controls">
				<form:checkboxes path="channelIdList" items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"  onclick="return false;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员行为限制：</label>
			<div class="controls">
				<form:checkboxes path="actionTypeList" items="${actionTypeList}" itemLabel="behaviorName" itemValue="behaviorCode" htmlEscape="false"  onclick="return false;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动开始时间：</label>
			<div class="controls">
				<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${marketingActivityInfo.beginDate}" pattern="yyyy-MM-dd"/>"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动结束时间：</label>
			<div class="controls">
				<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${marketingActivityInfo.endDate}" pattern="yyyy-MM-dd"/>"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动有效开始时间段：</label>
			<div class="controls">
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="${marketingActivityInfo.beginTime}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动有效结束时间段：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="${marketingActivityInfo.endTime}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关联实现类名：</label>
			<div class="controls">
				<form:input path="bizClassName" htmlEscape="false" maxlength="200" class="input-xlarge required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动说明：</label>
			<div class="controls">
				<form:textarea path="introduction" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批状态：</label>
			<div class="controls">
				<form:radiobutton path="status"  value="1" id="status_1" checked="checked"/> 通过
				<form:radiobutton path="status"  value="-1" id="status_0"/>不通过
			</div>
		</div>
		<div class="form-actions">
		    
			<shiro:hasPermission name="marketing:marketingActivityInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="审批"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>