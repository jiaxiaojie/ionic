<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员信用认证信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var creditScore = $("#educationScore");
			/*
			博士		7分			code:5
			硕士		5-6分		code:4
			本科		3-4分		code:3
			专科		2分			code:2
			专科以下	1分			code:0,1
			*/
			var code = $("#educationCode").val();
			if(code == "5") {
				creditScore.attr("min", "7").attr("max", "7");
			} else if(code == "4") {
				creditScore.attr("min", "5").attr("max", "6");
			} else if(code == "3") {
				creditScore.attr("min", "3").attr("max", "4");
			} else if(code == "2") {
				creditScore.attr("min", "2").attr("max", "2");
			} else{
				creditScore.attr("min", "1").attr("max", "1");
			}
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				messages: {
					educationCreditAuthRemark:{required:"请填写认证说明"}
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
			$("#btnReject").click(function(){
				var minScore = creditScore.attr("min");
				creditScore.attr("min", "0").removeClass("required");
				creditScore.val("");
				$("#educationCreditAuthCode").val("-1");
				$("#inputForm").submit();
				creditScore.attr("min", minScore).addClass("required");
			});
			$("#btnAgree").click(function(){
				$("#educationCreditAuthCode").val("2");
				$("#inputForm").submit();
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerCreditAuth/">会员信用认证信息列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerCreditAuth/formEducation?id=${customerCreditAuth.id}">审核会员学历信息<shiro:lacksPermission name="customer:customerCreditAuth:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form modelAttribute="customerBase" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">最高学历：</label>
			<div class="controls">
				<form:select path="educationCode" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业院校：</label>
			<div class="controls">
				<form:input path="educationSchool" readonly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历证明：</label>
			<div class="controls">
				<form:hidden id="educationFile" path="educationFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="educationFile" readonly="true" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
	</form:form>
	<form:form id="inputForm" modelAttribute="customerCreditAuth" action="${ctx}/customer/customerCreditAuth/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<form:hidden path="educationCreditAuthCode"/>
		<div class="control-group">
			<label class="control-label">认证说明：</label>
			<div class="controls">
				<form:textarea path="educationCreditAuthRemark" placeholder="请填写认证说明" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历认证得分：</label>
			<div class="controls">
				<form:input path="educationScore" htmlEscape="false" min="1" max="7" maxlength="11" class="input-xlarge number required"/>
				<span class="help-inline">分数范围：1分-7分。</span>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${customerCreditAuth.educationCreditAuthCode==1 }">
			<shiro:hasPermission name="customer:customerCreditAuth:edit">
			<input id="btnReject" class="btn btn-danger" type="button" value="驳 回"/>&nbsp;
			<input id="btnAgree" class="btn btn-success" type="button" value="通 过"/>&nbsp;
			</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>