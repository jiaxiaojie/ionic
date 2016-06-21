<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员信用认证信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var creditScore = $("#incomeScore");
			/*
			年薪	分值
			低于50,000	1—6分			code:0
			50,000—100,000	6—12分		code:1
			100,000—300,000	13—18分		code:2
			300,000—500,000	19—24分		code:3
			500,000以上	24—30分			code:4
			*/
			var incomeCode = $("#incomeCode").val();
			if(incomeCode == "1") {
				creditScore.attr("min", "6").attr("max", "12");
			} else if(incomeCode == "2") {
				creditScore.attr("min", "13").attr("max", "18");
			} else if(incomeCode == "3") {
				creditScore.attr("min", "19").attr("max", "24");
			} else if(incomeCode == "4") {
				creditScore.attr("min", "24").attr("max", "30");
			} else{
				creditScore.attr("min", "1").attr("max", "6");
			}
			
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				messages: {
					incomeCreditAuthRemark:{required:"请填写认证说明"}
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
				creditScore.attr("min", "0");
				creditScore.removeClass("required");
				creditScore.val("");
				$("#incomeCreditAuthCode").val("-1");
				$("#inputForm").submit();
				creditScore.attr("min", minScore);
				creditScore.addClass("required");
			});
			$("#btnAgree").click(function(){
				$("#incomeCreditAuthCode").val("2");
				$("#inputForm").submit();
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerCreditAuth/">会员信用认证信息列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerCreditAuth/formIncome?id=${customerCreditAuth.id}">审核会员收入信息<shiro:lacksPermission name="customer:customerCreditAuth:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form modelAttribute="customerWork" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">公司名称：</label>
			<div class="controls">
				<form:input path="companyName" readonly="true" htmlEscape="false" maxlength="20" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司性质：</label>
			<div class="controls">
				<form:select path="companyTypeCode" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司规模：</label>
			<div class="controls">
				<form:select path="companySizeCode" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_company_size')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位：</label>
			<div class="controls">
				<form:input path="position" readonly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在职证明：</label>
			<div class="controls">
				<form:hidden id="positionFile" path="positionFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="positionFile" readonly="true" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年收入：</label>
			<div class="controls">
				<form:select path="incomeCode" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_income')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收入证明：</label>
			<div class="controls">
				<form:hidden id="incomeFile" path="incomeFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="incomeFile" readonly="true" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在岗时间（年）：</label>
			<div class="controls">
				<form:input path="workYears" readonly="true" htmlEscape="false" maxlength="5" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司联系人：</label>
			<div class="controls">
				<form:input path="contacts" readonly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司联系电话：</label>
			<div class="controls">
				<form:input path="companyPhone" readonly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司所在城市：</label>
			<div class="controls">
				<form:input path="companyAddress" readonly="true" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
	</form:form>
	<form:form id="inputForm" modelAttribute="customerCreditAuth" action="${ctx}/customer/customerCreditAuth/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<form:hidden path="incomeCreditAuthCode"/>
		<div class="control-group">
			<label class="control-label">认证说明：</label>
			<div class="controls">
				<form:textarea path="incomeCreditAuthRemark" placeholder="请填写认证说明" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收入认证得分：</label>
			<div class="controls">
				<form:input path="incomeScore" htmlEscape="false" min="1" max="30" maxlength="11" class="input-xlarge number required"/>
				<span class="help-inline">分数范围：1分-30分。</span>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${customerCreditAuth.incomeCreditAuthCode==1 }">
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