<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品价格策略管理</title>
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
		//日期校验
		var dateUtils = new DateUtils();
		$(function(){
			$("#beginDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:dateUtils.formatDate(new Date(), "yyyy-MM-dd")});
			});
			
			$("#endDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:$("#beginDt").val()});
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/integral/integralMallProductPrice/list?productId=${integralMallProductPrice.productId}">产品价格策略列表</a></li>
		<li class="active"><a href="${ctx}/integral/integralMallProductPrice/form?id=${integralMallProductPrice.id}">产品价格策略<shiro:hasPermission name="integral:integralMallProductPrice:edit">${not empty integralMallProductPrice.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="integral:integralMallProductPrice:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="integralMallProductPrice" action="${ctx}/integral/integralMallProductPrice/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="productId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">产品价格类型：</label>
			<div class="controls">
				<form:select path="priceType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('integral_project_price_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动价格：</label>
			<div class="controls">
				<form:input path="marketNewPrice" htmlEscape="false" class="input-xlarge number" maxlength="8"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动折扣：</label>
			<div class="controls">
				<form:input path="marketDiscount" htmlEscape="false" max="0.99" min="0.01" maxlength="5" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input id="beginDt" name="beginDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${integralMallProductPrice.beginDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input id="endDt" name="endDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${integralMallProductPrice.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
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
			<shiro:hasPermission name="integral:integralMallProductPrice:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>