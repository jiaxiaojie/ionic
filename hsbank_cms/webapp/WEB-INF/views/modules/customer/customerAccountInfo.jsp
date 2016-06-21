<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账号信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$(":input").attr("disabled",true);
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				rules: {
					accountName: {
		                remote: {
		                    type: "get",
		                    url: "checkAccountNameCanUse",
		                    data: {
		                    	accountId: function() {return $("#accountId").val();},
		                        accountName: function() {return $("#accountName").val();}
		                    }
		                }
		            }
		        },
		        messages: {
		        	accountName: {remote:"登录名已存在，请重新输入"}
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
		<li><a href="${ctx}/customer/customerAccountInfo/customerAccountInfoList">会员列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerAccountInfo/customerAccountInfo?accountId=${model.accountId}">会员详细信息</a></li>
	</ul><br/>
	<jsp:include page="./common/customerAccountInfoMenu.jsp"/>
	<form:form id="inputForm" modelAttribute="customerAccount" action="${ctx}/customer/customerAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="accountId"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">登录名：</label>
			<div class="controls">
				<form:input path="accountName" htmlEscape="false" minlength="3" maxlength="50" class="input-xlarge required userName accountName"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账号类型：</label>
			<div class="controls">
				<form:radiobuttons path="accountType" items="${fns:getDictList('account_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">昵称：</label>
			<div class="controls">
				<form:input path="nickname" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		
			<c:choose>
				<c:when test="${empty model.id}">
		
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
			<c:if test="${!empty model.id}">
		<div class="control-group">
			<label class="control-label">是否开通第三方账号：</label>
			<div class="controls">
				<form:select path="hasOpenThirdAccount" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账号状态：</label>
			<div class="controls">
				<form:select path="statusCode" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册时间：</label>
			<div class="controls">
				<input name="registerDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${model.registerDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册渠道：</label>
			<div class="controls">
				<form:select path="registerChannel" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('register_channel_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册IP：</label>
			<div class="controls">
				<form:input path="registerIp" readonly="true" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后一次登录时间：</label>
			<div class="controls">
				<input name="registerDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${model.lastLoginDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后一次登录终端类型：</label>
			<div class="controls">
				<form:select path="lastLoginTermCode" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后一次登录IP：</label>
			<div class="controls">
				<form:input path="lastLoginIp" readonly="true" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">错误登录次数：</label>
			<div class="controls">
				<form:input path="errCount" readonly="true" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">错误登录时间：</label>
			<div class="controls">
				<input name="errDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${model.lastLoginDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">错误登录终端类型：</label>
			<div class="controls">
				<form:select path="errTermCode" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">错误登录IP：</label>
			<div class="controls">
				<form:input path="errIp" readonly="true" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
			</c:if>
		
	</form:form>
</body>
</html>