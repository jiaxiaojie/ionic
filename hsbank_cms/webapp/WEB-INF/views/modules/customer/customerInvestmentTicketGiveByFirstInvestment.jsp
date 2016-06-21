<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员现金券管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
			
			$("#btnSubmit").click(function(){
				if($("#inputForm").valid()) {
					top.$.jBox.confirm("请确认手机号码格式正确（多个手机号以“;”分隔）并且为10天内首次投资，确定赠送以上人员200元现金券红包吗？", "操作提示",
						function(v, h, f){
							if(v==1) {
								$("#inputForm").submit();
							}
						}, {
						buttons: {
							'确定' : 1,
							'取消' : -1
						}});
				}
			});
		});
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<form id="inputForm" modelAttribute="customerAccount" action="${ctx}/customer/customerInvestmentTicket/doFirstInvestmentGiveTicket" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				<textarea id="mobiles" name="mobiles" rows="4" class="input-xxlarge required"></textarea><br/>
				<span class="help-inline">多个手机号以“;”隔开<br/>200元现金券礼包：一张100元、一张50元、一张20元、两张10元、两张5元现金券。</span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" value="赠送200元现金券红包"/>
		</div>
	</form>
</body>
</html>