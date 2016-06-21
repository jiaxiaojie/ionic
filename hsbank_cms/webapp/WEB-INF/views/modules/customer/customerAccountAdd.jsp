<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账号信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#accountName").focus();
			$("#inputForm").validate({
				rules: {
					accountName: {
		                remote: {
		                    type: "get",
		                    url: "${ctx}/customer/customerAccount/checkAccountNameCanUse",
		                    data: {
		                        accountName: function() {return $("#accountName").val();}
		                    }
		                }
		            },
					platformUserNo: {remote: {url:"${ctx}/customer/customerAccount/checkPlatformUserNoCanUse",data:{platformUserNo:function(){return $("#platformUserNo").val()}}}}
		        },
		        messages: {
		        	accountName: {remote:"登录名已存在，请重新输入"},
		        	platformUserNo: {remote:"平台用户编号已存在，请重新输入"}
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
	<sys:message content="${message}"/>
	<form id="inputForm" modelAttribute="customerAccount" action="${ctx}/customer/customerAccount/addSave" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">登录名：</label>
			<div class="controls">
				<input type="text" id="accountName" name="accountName" class="input-xlarge accountname" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<input type="text" id="customerName" name="customerName" class="input-xlarge required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<input type="text" id="mobile" name="mobile" class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<input type="text" id="email" name="email" class="input-xlarge email" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">易宝平台用户编号：</label>
			<div class="controls">
				<input type="text" id="platformUserNo" name="platformUserNo" class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否已开通第三方账号：</label>
			<div class="controls">
				<input type="radio" checked="checked" name="hasOpenThirdAccount" value="0">否<input type="radio" name="hasOpenThirdAccount" value="1">是
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form>
</body>
</html>