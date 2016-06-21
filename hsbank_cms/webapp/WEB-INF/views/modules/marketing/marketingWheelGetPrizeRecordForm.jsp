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
		<li><a href="${ctx}/marketing/marketingWheelGetPrizeRecord/">大转盘中奖记录列表</a></li>
		<li class="active"><a href="${ctx}/marketing/marketingWheelGetPrizeRecord/form?id=${marketingWheelGetPrizeRecord.id}">大转盘中奖记录<shiro:hasPermission name="marketing:marketingWheelGetPrizeRecord:edit">${not empty marketingWheelGetPrizeRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="marketing:marketingWheelGetPrizeRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="marketingWheelGetPrizeRecord" action="${ctx}/marketing/marketingWheelGetPrizeRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">用户手机号：</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">用户姓名：</label>
			<div class="controls">
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">奖品名称：</label>
			<div class="controls">
				<form:input path="prizeName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">奖品类型：</label>
			<div class="controls">
				<form:select path="prizeType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('marketing_award_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">奖品值：</label>
			<div class="controls">
				<form:input path="prizeValue" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('marketing_wheel_get_prize_record_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">中奖时间：</label>
			<div class="controls">
				<input name="getDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${marketingWheelGetPrizeRecord.getDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作终端：</label>
			<div class="controls">
				<form:input path="opTerm" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">失效时间：</label>
			<div class="controls">
				<input name="invalidDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${marketingWheelGetPrizeRecord.invalidDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">奖品赠送时间：</label>
			<div class="controls">
				<input name="giveDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${marketingWheelGetPrizeRecord.giveDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>