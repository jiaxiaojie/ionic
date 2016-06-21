<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_without_menu"/>
		<link href="${ctxStatic}/modules/front/css/login.css?${version }"  type="text/css" rel="stylesheet"/>
		<title></title>
    	<script type="text/javascript">
    		var bIcon = "<b class=\"icon_arrow_yellow\">";
			$(document).ready(function() {
				$(document).on('click', '#remember_me', function() {
					if ($('#remember_me').hasClass("checkbox_blue")) {
						$('#remember_me').removeClass("checkbox_blue").addClass("checkbox_selected_blue");
						$("#rememberMe").val("true");
					} else {
						$('#remember_me').removeClass("checkbox_selected_blue").addClass("checkbox_blue");
						$("#rememberMe").val("false");
					}
				});
				
				$("#login_form").validate({
					rules: {
						username: {required: true},
						password: {required: true},
						validateCode: {required: true, remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
					},
					messages: {
						username: {required: "<b class=\"icon_arrow_yellow\"></b>请输入账户."},password: {required: "<b class=\"icon_arrow_yellow\"></b>请输入密码."},
						validateCode: {remote: "<b class=\"icon_arrow_yellow\" style=\"left:25%\"></b>验证码不正确.", required: "<b class=\"icon_arrow_yellow\" style=\"left:25%\"></b>请输入验证码."}
					},
					errorClass: "tips1",
					errorPlacement: function(error, element) {
						error.insertAfter(element);
					}
				});
				
				$(document).on('click', '#bt_login', function() {
					$('#login_form').submit();
				});
				
				$("#username").keydown(function(e){ 
					var curKey = e.which; 
					if(curKey == 13){ 
						$('#login_form').submit();
					} 
				});
				$("#password").keydown(function(e){ 
					var curKey = e.which; 
					if(curKey == 13){ 
						$('#login_form').submit();
					} 
				});
				$("#validateCode").keydown(function(e){ 
					var curKey = e.which; 
					if(curKey == 13){ 
						$('#login_form').submit();
					} 
				});
			});
		</script>
	</head>
	<body>
		<!-- 登录 -->
		<div id="div_login">
			<div id="login_area" class="login_area">
				<form id="login_form" action="${ctxFront}/login" method="post">
					<div class="account_row pr">
						<input type="text" id="username" name="username" placeholder="请输入账户" title="请输入账户">
					</div>
					<div class="password_row pr">
						<input type="password" id="password" name="password" placeholder="请输入密码" title="请输入密码">
					</div>
					<c:if test="${isValidateCodeLogin}">
						<div class="validate_code_row pr">
							<input type="text" id="validateCode" name="validateCode" maxlength="5" placeholder="请输入验证码" title="请输入验证码">
							<img id="vc_image" src="${pageContext.request.contextPath}/servlet/validateCodeServlet" onclick="$('#vc_refresh').click();">
							<a id="vc_refresh" href="javascript:void(0)" onclick="$('#vc_image').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());">看不清</a>
						</div>
					</c:if>
					<div class="tip_row">
                   		<div id="remember_me" class="checkbox_blue"></div>
                   		<span class="spanLeftGray">记住账户</span>
                   		<span class="spanRightBlue"><a href="${ctxFront }/resetPassword/mobile">忘记密码</a></span>
                 	</div>
                 	<div id="message_box" class="error_row ${empty message ? 'hide' : ''}">
						<label id="error_message" class="tips-err" style="color:#bf4c00;display:block;align:center;">${message}</label>
					</div>
					<div class="div_height_45 ${empty message ? '' : 'hide'}"></div>
                 	<div id="bt_login" class="login_row">立即登录</div>
                 	<c:if test="${!isValidateCodeLogin}">
						<div class="div_height_45"></div>
					</c:if>
                 	<div class="tip_row">
                   		<span class="spanLeftGray" style="margin-left:70px;">没有账户？</span>
                   		<input type="hidden" id="rememberMe" name="rememberMe" value="false"/>
                   		<span class="spanLeftBlue"><a href="register">免费注册</a></span>
                 	</div>
                 	<div class="line_row"></div>
                 	<!-- <div class="tip_row">
                   		<span class="spanLeftGray" style="margin-left:20px;">您还可以使用合作账户登录&nbsp;&nbsp;&nbsp;</span>
                   		<span class="spanSNS"><span class="weixin"><a href="#"></a>&nbsp;</span></span>
						<span class="spanSNS"><span class="weibo"><a href="#"></a></span></span>
                 	</div> -->
                 </form>
			</div>
		</div>
	</body>
</html>