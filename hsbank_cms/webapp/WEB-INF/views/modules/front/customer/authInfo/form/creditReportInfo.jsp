<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>信用报告认证</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(function(){
			$("#creditReportInfoForm").validate({
				messages: {creditReportFile: {required: "请上传个人信用报告证明"}},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				},
				ignore: ""
			});
		});
	</script>
</head>
<body>
	<form:form id="creditReportInfoForm" modelAttribute="customerBankCard" action="${ctxFront }/customer/account/authInfo/creditReportInfoPost" method="post" class="form-horizontal">
		<form:hidden path="accountId"/>
		<div style="height:20px;"></div>
		<div class="control-group">
			<label class="control-label">个人信用报告：</label>
			<div class="controls">
				<form:hidden id="creditReportFile" path="creditReportFile" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<sys:ckfinder input="creditReportFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">认证说明：</label>
			<div class="controls">
				<p>（1）个人信用报告是由中国人民银行出具，全面记录个人信用活动，反映个人信用基本状况的文件。本报告是花生金服了解您信用状况的一个重要参考资料。您信用报告内体现的信用记录和信用卡额度等数据，将在您发布借款时经花生金服工作人员整理，在充分保护您隐私的前提下披露给花生金服理财人，作为理财人投标的依据。</p>
				<p>（2）个人信用报告需15日内开具。</p>
				<p>（3）认证条件：信用记录良好。</p>
				<p>（4）认证有效期：6个月。</p>
				<p>（5）如何办理个人信用报告：可去当地人民银行打印，部分地区可登陆个人信用信息服务平台。</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人信用报告示例：</label>
			<div class="controls">
				<img alt="个人信用报告" src="${ctxStatic }/modules/front/images/wdzh/example/creditReport.jpg" width="450" />
			</div>
		</div>
	</form:form>
</body>