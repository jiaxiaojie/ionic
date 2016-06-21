<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>借贷产品管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function projectTypeChange(value){
		if(value!=''){
			$("div[id^='extend_']").hide();
			$('#extend_'+value).show();
		}
	}

	$(document).ready(
			function() {
				projectTypeChange($('#projectTypeCode').val());
				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
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
		<li><a href="${ctx}/project/projectBaseInfo/${modelMenus.backPath}">借贷产品列表</a></li>
		<li class="active"><a HERF="#">借贷产品信息</a></li>
	</ul><br/>
	<jsp:include page="./common/jdprojectInfoMenu.jsp"/>
	<form:form id="inputForm" modelAttribute="projectDateNode"
		action="${ctx}/project/projectBaseInfo/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">上线时间：</label>
			<div class="controls">
				<input name="onLineDt" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${model.onLineDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					 />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始募资时间：</label>
			<div class="controls">
				<input name="startFundingDt" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${model.startFundingDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					 />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">募资结束时间：</label>
			<div class="controls">
				<input name="endFundingDt" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${model.endFundingDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					 />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款结束时间：</label>
			<div class="controls">
				<input name="finishRepayDt" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${model.finishRepayDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					 />
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>