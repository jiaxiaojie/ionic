<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>轮播图管理</title>
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
		<li><a href="${ctx}/carousel/carouselInfo/">轮播图列表</a></li>
        <li class="active"><a href="${ctx}/carousel/carouselInfo/reviewForm?carouselId=${carouselInfo.carouselId}">轮播图<shiro:hasPermission name="carousel:carouselInfo:edit">审核</shiro:hasPermission><shiro:lacksPermission name="carousel:carouselInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="carouselInfo" action="${ctx}/carousel/carouselInfo/review" method="post" class="form-horizontal">
		<form:hidden path="carouselId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">轮播图终端：</label>
			<div class="controls">
				<form:checkboxes path="termCodeList" items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" onclick="return false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<form:hidden id="pictureBig" path="pictureBig" htmlEscape="false" maxlength="500" class="input-xlarge"/>
				<sys:ckfinder input="pictureBig" type="images" uploadPath="/carousel/carouselInfo" selectMultiple="false" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动或项目开始时间：</label>
			<div class="controls">
				<input name="activityTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${carouselInfo.activityTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="typeCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('photo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
		    </div>
		</div>

		<div class="control-group">
			<label class="control-label">是否新网站使用：</label>
			<div class="controls">
				<form:select path="isNewWebsite" class="input-xlarge required">
					<form:options items="${fns:getDictList('is_increase_interest_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">目标：</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
			
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${carouselInfo.startDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${carouselInfo.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
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
			<shiro:hasPermission name="carousel:carouselInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="审批"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> 
			
		</div>
	</form:form>
</body>
</html>