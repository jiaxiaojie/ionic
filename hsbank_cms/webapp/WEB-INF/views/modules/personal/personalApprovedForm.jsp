<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>私人订制审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function validateForm(){
			return $("#inputForm").validate({
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
		}
		function pass(){
			$('#state').val('2');
			if(validateForm().form()){
				confirmx("此私人订制项目确认通过吗？",function() {
					loading('正在提交，请稍等...');
					$('#inputForm').submit();
				});
			}
		}
		function nopass(){
			$('#state').val('3');
			if(validateForm().form()){
				confirmx("此私人订制项目确认不通过吗？",function() {
					loading('正在提交，请稍等...');
					$('#inputForm').submit();
				});
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/personal/personalTailor/">私人订制审核列表</a></li>
		<li class="active"><a href="${ctx}/personal/personalApproved/form?id=${personalTailor.id}">私人订制审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="personalTailor" action="${ctx}/personal/personalApproved/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="state" id="state" />
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="128" class="input-medium required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目类型：</label>
			<div class="controls">
				<form:select path="type" class="input-medium required" disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personal_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-medium required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
				<form:input path="startingAmount" htmlEscape="false" class="input-medium required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目期限：</label>
			<div class="controls">
				<form:input path="duration" htmlEscape="false" maxlength="11" class="input-medium required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				<form:input path="rate" htmlEscape="false" class="input-medium required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投标截止日期：</label>
			<div class="controls">
				<input name="deadline" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personalTailor.deadline}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input name="publishTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personalTailor.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-large required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目描述图片：</label>
			<div class="controls">
				<form:textarea id="descPic"  htmlEscape="false" path="descPic"  rows="2" maxlength="100" class="input-xlarge"/>
		        <sys:ckeditor replace="descPic" uploadPath="/personal/personalTailor" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="personal:personalApproved:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="pass();" value="通 过"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="personal:personalApproved:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="nopass();" value="不 通过"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>