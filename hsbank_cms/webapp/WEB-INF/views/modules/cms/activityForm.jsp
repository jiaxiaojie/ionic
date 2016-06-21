<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				
				submitHandler: function(form){
					var filesImage = $("#activityCover").val();
					if(filesImage == ''){
						$.jBox.error("活动封面不能为空", "提示");
						return false;
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					}
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
		
		
		
		$(function(){
			
			var maxDt = function(){
				
				var result = "";
				var endDt = $("#endDt").val()
				if(endDt!=null && endDt != ""){
					result = endDt
				}
				return result;
			};
			
			$("#startDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'${fns:getDate("yyyy-MM-dd")}',maxDate:maxDt()});
				
			});
			
			$("#endDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:$("#startDt").val()});
			});
			
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/content/activity/">活动列表</a></li>
		<li class="active"><a href="${ctx}/content/activity/form?id=${activity.id}">活动<shiro:hasPermission name="content:activity:edit">${not empty activity.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="content:activity:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="activity" action="${ctx}/content/activity/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="sort"/>
		<sys:message content="${message}"/>
		
		<div class="control-group">
			<label class="control-label">活动名称：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="30" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动描述：</label>
			<div class="controls">
				<form:input path="activityDescription" htmlEscape="false" maxlength="255" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('photo_type')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">活动链接：</label>
			<div class="controls">
				<form:input path="activityJoin" htmlEscape="false" maxlength="100" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>



		<%--	<div class="control-group">
                <label class="control-label">目标参数：</label>
                <div class="controls">
                    <form:input path="target" htmlEscape="false" maxlength="300" class="input-xlarge  "/>
                        <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>--%>
		
		<div class="control-group">
			<label class="control-label">终端限制：</label>
			<div class="controls">
				<form:checkboxes path="termCodes"  items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">活动封面：</label>
			<div class="controls">
				<form:hidden id="activityCover" path="activityCover" htmlEscape="false" maxlength="120" class="input-xlarge "/>
				<sys:ckfinder input="activityCover" type="images" uploadPath="/content/activity" selectMultiple="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input id="startDt" name="startDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${activity.startDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input id="endDt" name="endDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${activity.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="content:activity:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>