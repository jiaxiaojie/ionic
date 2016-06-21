<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告位显示信息管理</title>
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
		<%--<li><a href="${ctx}/carousel/adPositionInfo/">广告位显示信息列表</a></li>--%>
		<li class="active"><a href="${ctx}/carousel/adPositionInfo/reviewForm?id=${adPositionInfo.id}">广告位显示信息<shiro:hasPermission name="carousel:adPositionInfo:edit">${not empty adPositionInfo.id?'审核':'添加'}</shiro:hasPermission><shiro:lacksPermission name="carousel:adPositionInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="adPositionInfo" action="${ctx}/carousel/adPositionInfo/review" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">终端：</label>
			<div class="controls">
				<form:select path="termCode" class="input-xlarge required">
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">位置：</label>
			<div class="controls">
				<form:select path="code" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('ad_code')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<form:hidden id="image" path="image" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="image" type="images" uploadPath="/carousel/adPositionInfo" selectMultiple="false" readonly="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">是否可点击：</label>
			<div class="controls">
				<form:radiobuttons path="canClick" items="${fns:getDictList('yes_no')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
         <c:if test="${!empty adPositionInfo.type}">
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('photo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
      </c:if>
		<c:if test="${!empty adPositionInfo.target}">
		<div class="control-group">
			<label class="control-label">目标参数：</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${adPositionInfo.startDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${adPositionInfo.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
			<shiro:hasPermission name="carousel:adPositionInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="审批"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>