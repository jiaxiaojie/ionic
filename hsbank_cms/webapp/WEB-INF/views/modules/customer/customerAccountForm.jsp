<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账号信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
		<li><a href="${ctx}/customer/customerAccount/">会员列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerAccount.accountId}">会员<shiro:hasPermission name="customer:customerAccount:edit">${not empty customerAccount.accountId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerAccount.accountId}">账号信息</a></li>
		<c:if test="${not empty customerAccount.accountId}">
		<li><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerAccount.accountId}">基本信息</a></li>
		<c:if test="${customerAccount.accountType=='0'}">
		<c:if test="${not empty customerAccount.customerBase.customerId}">
		<li><a href="${ctx}/customer/customerWork/formByCustomerId?customerId=${customerAccount.customerBase.customerId}">工作信息</a></li>
		<li><a href="${ctx}/customer/customerHousing/formByCustomerId?customerId=${customerAccount.customerBase.customerId}">房产信息</a></li>
		<li><a href="${ctx}/customer/customerCar/formByCustomerId?customerId=${customerAccount.customerBase.customerId}">车产信息</a></li>
		</c:if>
		<li><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${customerAccount.accountId}">银行卡信息</a></li>
		</c:if>
		</c:if>
	</ul><br/>
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
			<label class="control-label">推荐账号：</label>
			<div class="controls">
				<form:input path="recommendAccountId" htmlEscape="false" maxlength="50" class="input-xlarge userName"/>
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
		<div class="control-group">
			<label class="control-label">头像：</label>
			<div class="controls">
				<form:hidden id="avatarImage" path="avatarImage" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<sys:ckfinder input="avatarImage" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邀请码：</label>
			<div class="controls">
				<form:input path="inviteCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
			<c:choose>
				<c:when test="${empty customerAccount.id}">
		<div class="control-group">
			<label class="control-label">密码：</label>
			<div class="controls">
				<form:input path="accountPwd" htmlEscape="false" minlength="1" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
			<c:if test="${!empty customerAccount.id}">
		<div class="control-group">
			<label class="control-label">第三方账号：</label>
			<div class="controls">
				<form:input path="platformUserNo" readonly="true" htmlEscape="false" maxlength="50" class="input-xlarge "/>
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
					value="<fmt:formatDate value="${customerAccount.registerDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
					value="<fmt:formatDate value="${customerAccount.lastLoginDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
					value="<fmt:formatDate value="${customerAccount.lastLoginDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<a href="${ctx}/customer/customerAccount/"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>
		</div>
	</form:form>
</body>
</html>