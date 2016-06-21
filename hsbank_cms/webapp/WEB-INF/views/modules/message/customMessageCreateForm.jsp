<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义消息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		    $("#type").select2("val","0");
		    $("#type").select2("readonly",true);
			
			var dateUtils = new DateUtils();
			// 发送时间校验
			$("#sendDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:dateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")});
			});

			$('#btnSave').click(function() {
				$("input[name='status']").val('0');
				$('#inputForm').submit();
			});
			$('#btnSubmit').click(function() {
				$("input[name='status']").val('1');
				$('#inputForm').submit();
			});
			//$("#name").focus();
			$("#inputForm").validate({
				ignore: "",
				rules: {
					  receiverData:{
						  excelOnly: true
					},  
					title:{
						remote: {
							type:"post",
							url:"${ctx}/message/customMessage/checkTitle",
		                    data: {
		                    	 id:$('#id').val(),
		                    	 title: function() {return $("#title").val();}
		                    }
						}
					}
				},
				messages: {
					title: {remote: " 消息标题已存在 "},
					receiverData:{remote:"上传文件不能为空 "}
				},
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
			
			jQuery.validator.addMethod("excelOnly", function(value, element) {
				var suffix = ".xlsx";
				return value.substring(value.length - suffix.length) == suffix;
			}, "上文文件必须为.xlsx文件");
		});
	</script>
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/message/customMessage/createList">创建消息列表</a></li>
 		<li class="active"><a href="${ctx}/message/customMessage/createForm?id=${customMessage.id}">创建消息<shiro:hasPermission name="message:customMessage:create">${not empty customMessage.id?'修改':' '}</shiro:hasPermission><shiro:lacksPermission name="message:customMessage:create">查看</shiro:lacksPermission></a></li>
 	</ul><br/>
	<form:form id="inputForm" modelAttribute="customMessage" action="${ctx}/message/customMessage/create" method="post" enctype="multipart/form-data" class="form-horizontal">
		<form:hidden path="id"/>
			<form:hidden path="status"  id="status" />
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">消息类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="140" class="input-xxlarge  required"/>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">标签：</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" maxlength="20" class="input-xlarge  required"/>
			</div>
		</div> 
		
		<div class="control-group">
			<label class="control-label">是否紧急：</label>
			<div class="controls">
				<form:select path="isUrgent" class="input-xlarge required ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">目标类型：</label>
			<div class="controls">
				<form:select path="targetType" class="input-xlarge required">
				    <form:option value="" label=""/>
					<form:options items="${fns:getDictList('photo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
				 <div class="control-group">
			<label class="control-label">目标参数：</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false"  maxlength="100" class="input-xlarge required"/>
				<span class="help-inline">如无目标参数填无！</span>
			</div>
		</div> 
				<div class="control-group">
			<label class="control-label">是否可点击：</label>
			<div class="controls">
				<form:select path="isClick" class="input-xlarge  required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预计发送时间：</label>
			<div class="controls">
				<input name="sendDt" id="sendDt" type="text" readonly="readonly" maxlength="20" class="input-medium required " 
					value="<fmt:formatDate value="${customMessage.sendDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>

		 <div class="control-group">
			<label class="control-label">接收用户：</label>
			<div class="controls">
				<form:hidden id="attr2" path="receiverData" htmlEscape="false" maxlength="200" class="input-xlarge" />
				<sys:ckfinder input="attr2" type="files" uploadPath="/message" selectMultiple="false"/>
					<a href="${ctx}/message/customMessage/import/template" style="font-size: 16px">下载excel模板</a>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">发送渠道：</label>
			<div class="controls">
				<form:checkboxes path="messageChannelList"  items="${fns:getDictList('message_channel')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">接收用户类型：</label>
			<div class="controls">
				
				<input id="receiverType" name="receiverType" value="0" type="hidden" />
				<input   value="${fns:getDictLabel('0', 'custom_message_receiver_type', '')}" type="text" readonly="readonly"/>
			</div>
		</div>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="message:customMessage:create"><c:choose>
				<c:when test="${customMessage.status=='0'}">
					<shiro:hasPermission name="message:customMessage:create">
					<input id="btnSave" class="btn btn-primary" type="button"
						value="保 存" id="saveBtn" />&nbsp;<input id="btnSubmit"
						class="btn btn-danger" type="button" value="保存并提交审批" id="" />&nbsp;</shiro:hasPermission>
				</c:when>
				<c:when test="${empty customMessage.status}">  
				 	<shiro:hasPermission name="message:customMessage:create">
					<input id="btnSave" class="btn btn-primary" type="button"
						value="保 存" id="saveBtn" />&nbsp;<input id="btnSubmit"
						class="btn btn-danger" type="button" value="保存并提交审批" id="" />&nbsp;</shiro:hasPermission>
 				 </c:when> 
			</c:choose></shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>