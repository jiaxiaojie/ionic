<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期产品赎回审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(":input").not($("#btnSubmit")).not($("[name='status']")).not($("#btnCancel")).not($("[type=hidden]")).attr("disabled",true);

			
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
		<li><a href="${ctx}/current/currentProjectRedemptionApply/reviewList">活期产品赎回审批列表</a></li>
		<li class="active"><a href="${ctx}/current/currentProjectRedemptionApply/reviewForm?id=${currentProjectRedemptionApply.id}">活期产品赎回审批</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="currentProjectRedemptionApply" action="${ctx}/current/currentProjectRedemptionApply/review" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">活期产品持有流水号：</label>
			<div class="controls">
				<form:input path="holdId" htmlEscape="false" maxlength="20" class="input-xlarge  digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赎回本金：</label>
			<div class="controls">
				<form:input path="redeemPrincipal" htmlEscape="false" class="input-xlarge  number required"/>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">申请终端：</label>
			<div class="controls">
				<form:select path="applyTerm" class="input-xlarge  required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<input name="applyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${currentProjectRedemptionApply.applyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		
	<%-- 	<c:if test="${currentProjectRedemptionApply.status=='0'  }">
		  <div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:radiobutton path="status"  value="1" id="Status_2" class="required"/> 通过
 				<form:radiobutton path="status"  value="-1" id="Status_0" class="required"/>不通过
 			</div>
		</div>
			</c:if>  --%>
		<div class="form-actions">
		<%-- <c:if test="${currentProjectRedemptionApply.status=='0' && currentProjectRedemptionApply.status=='1' }">
			<shiro:hasPermission name="current:currentProjectRedemptionApply:review">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="审 批"/>
			</shiro:hasPermission>
			</c:if>  --%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	
	</form:form>
</body>
</html>