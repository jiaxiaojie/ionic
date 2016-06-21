<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>私人订制管理</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript">
	    $(function(){
	    	//投标截止时间验证
			$("#deadline").focus(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,isShowToday:false,minDate:$("#publishTime").val()});
			});
	    });
		function validateForm(){
			return $("#inputForm").validate({
				rules: {
					name:{
						remote: {
							type:"post",
							url:"${ctx}/personal/personalTailor/checkName",
		                    data: {
		                    	 name: function() {return $("#name").val();}
		                    }
						},
					}
				},
				messages: {
					name: {remote: "项目名已存在"}
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
		}
		function saveDraft(){
			$('#state').val('0');
			if(validateForm().form()){
					loading('正在提交，请稍等...');
					$('#inputForm').submit();
			}
		}
		function approved(){
			$('#state').val('1');
			if(validateForm().form()){
				confirmx("此私人订制项目确认提交审核吗？",function() {
					loading('正在提交，请稍等...');
					$('#inputForm').submit();
				});
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/personal/personalTailor/">私人订制列表</a></li>
		<li class="active"><a href="${ctx}/personal/personalTailor/form?id=${personalTailor.id}">私人订制<shiro:hasPermission name="personal:personalTailor:edit">${not empty personalTailor.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="personal:personalTailor:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="personalTailor" action="${ctx}/personal/personalTailor/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<form:hidden path="state" id="state" />
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="30" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目类型：</label>
			<div class="controls">
				<form:select path="type" class="input-medium required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personal_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目金额：</label>
			<div class="controls">
				<form:input path="amount" id="amount" htmlEscape="false" maxlength="20" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
				<form:input path="startingAmount" id="startingAmount" htmlEscape="false" maxlength="20" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目期限：</label>
			<div class="controls">
				<form:input path="duration" htmlEscape="false" maxlength="11" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				<form:input path="rate" htmlEscape="false" maxlength="8" class="input-large required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input name="publishTime" id="publishTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${personalTailor.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})"
					   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:00'})"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投标截止日期：</label>
			<div class="controls">
				<input name="deadline" id="deadline" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${personalTailor.deadline}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-large "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目描述图片：</label>
			<div class="controls">
				<form:textarea id="content"  htmlEscape="false" path="descPic"  rows="4"  class="input-xlarge required"/>
		        <sys:ckeditor replace="content"  uploadPath="/personal/personalTailor" />
		      
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="form-actions">

			<c:if test="${personalTailor.state==null||personalTailor.state==3||personalTailor.state==0}">
			    <shiro:hasPermission name="personal:personalTailor:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveDraft();" value="保存 草稿"/>&nbsp;</shiro:hasPermission>
				<shiro:hasPermission name="personal:personalTailor:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="approved();" value="提交 审批"/>&nbsp;</shiro:hasPermission>
			</c:if>

		</div>
	</form:form>
</body>
</html>