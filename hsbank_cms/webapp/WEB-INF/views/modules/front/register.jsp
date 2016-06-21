<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_without_menu"/>
		<link href="${ctxStatic}/modules/front/css/register.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var message = "${message}";
			
			$(document).ready(function() {
				$(document).on('click', '#agree', function() {
					if ($('#agree').hasClass("checkbox_blue")) {
						$('#agree').removeClass("checkbox_blue");
						$('#agree').addClass("checkbox_selected_blue");
					} else {
						$('#agree').removeClass("checkbox_selected_blue");
						$('#agree').addClass("checkbox_blue");
					}
				});
			});
			
			$(function(){
				$("#registerForm").validate({
					submitHandler: function(form){
						if(!$("#agree").hasClass("checkbox_selected_blue")) {
							$("#agreeTips").show().html("<b class=\"icon_arrow_yellow\" style=\"left:22%\"></b>请同意我们的条款.").fadeOut(3000);
							return false;
						}
						if(!$("#register").attr("disabled")) {
							form.submit();
							$("#register").attr("disabled", "disabled");
						}
					},
					rules: {
						mobile: {remote: {
			                    	type: "get",
			                    	url: "${ctxFront}/register/checkMobileCanUse",
			                    	data: {
			                        	mobile: function() {return $("#mobile").val();}
			                    	}},
									required: true,mobile: true},
						password: {required: true,minlength: 5,maxlength: 50},
						conPassword:{required: true,equalTo: "#password"},
						validateCode: {required:true,remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"},
						smsCode:{required:true},
						recommendMobile: {mobile: true}
					},
					messages: {
						mobile: {required: "<b class=\"icon_arrow_yellow\"></b>请输入手机号.",remote:"<b class=\"icon_arrow_yellow\"></b>手机号已存在，请重新输入.", mobile: "<b class=\"icon_arrow_yellow\"></b>手机号码格式不正确，请重新输入."},
						password: {required: "<b class=\"icon_arrow_yellow\"></b>请输入密码.",minlength:"<b class=\"icon_arrow_yellow\"></b>密码最少为5位.",maxlength:"<b class=\"icon_arrow_yellow\"></b>密码最多为50位."},
						conPassword: {required: "<b class=\"icon_arrow_yellow\"></b>请输入确认密码.",equalTo: "<b class=\"icon_arrow_yellow\"></b>确认密码输入不一致."},
						validateCode: {remote: "<b class=\"icon_arrow_yellow\" style=\"left:25%\"></b>验证码不正确.", required: "<b class=\"icon_arrow_yellow\" style=\"left:25%\"></b>请输入验证码."},
						smsCode: {required:"<b class=\"icon_arrow_yellow\" style=\"left:25%\"></b>请输入短信验证码."},
						recommendMobile: {mobile: "<b class=\"icon_arrow_yellow\"></b>推荐人手机号码格式不正确."}
					},
					errorClass: "tips1",
					errorPlacement: function(error, element) {
						error.insertAfter(element);
					}
				});
				$("#register").click(function(){
					$("#registerForm").submit();
				});
				if(message != "") {
					$.jBox.tip(message);
				}
			});

			var sendSmsCode = function(targetObj) {
				if(!$("#sendSmsCode").attr("disabled")) {
					
					//判断手机号、密码、确认密码、验证码验证是否通过，通过了才能发送短信验证码
					if($("#registerForm").validate().element($("#mobile"))
							&& $("#registerForm").validate().element($("#password"))
							&& $("#registerForm").validate().element($("#conPassword"))
							&& $("#registerForm").validate().element($("#validateCode"))) {
						var data = {};
						data.validateCode = $("#validateCode").val();
						data.mobile = $("#mobile").val();
						$.get("${ctxFront}/smsCode/sendToRegister",data,function(msg){
							if(msg=="overdueValidateCode"){
								$.jBox.tip("验证码已失效，请重新输入");
								$('#vc_refresh').click();
								$("#validateCode").val("");
							}
							
						});
						$("#sendSmsCode").attr("disabled", "disabled");
						sendSmsCodeFunction();
					}
				}
			}
			var seconds = 60;
			var sendSmsCodeFunction = function() {
				$("#sendSmsCode").html(seconds + "秒后可重发");
				var intervalFunction = window.setInterval(function() {
					seconds--;
					$("#sendSmsCode").html(seconds + "秒后可重发");
					if(seconds <= 0) {
						window.clearInterval(intervalFunction);
						seconds = 60;
						$("#sendSmsCode").removeAttr("disabled");
						$("#sendSmsCode").html("重发验证码");
					}
				},1000);
			}
			
			/**只能输入数字*/
			function onlyNum() {
				if (!(event.keyCode == 46) && !(event.keyCode == 8) && !(event.keyCode == 37) && !(event.keyCode == 39)) { 
					if (!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105))) {
						event.returnValue = false;
					}
				}
			}
		</script>
	</head>
	<body>
			<!-- 注册 -->
			<div id="div_register">
				<div class="register_area">
					<form id="registerForm" action="" method="post">
                        
						<div class="account_row pr">
                            <input type="text" placeholder="手机号" maxLength="11" onkeydown="onlyNum();" id="mobile" name="mobile" autocomplete="off">
                        </div>
                        
						<div class="password_row pr">
                        	<input type="password" placeholder="请输入密码" id="password" name="password" autocomplete="off">
                        </div>
                        
						<div class="password_row pr">
                        	<input type="password" placeholder="请输入确认密码" id="conPassword" name="conPassword" autocomplete="off">
                        </div>
                        
	                 	<div class="captcha_row pr">
							<input type="text" id="validateCode" name="validateCode" autocomplete="off" maxlength="5" placeholder="请输入验证码" title="请输入验证码">
							<img id="vc_image" src="${pageContext.request.contextPath}/servlet/validateCodeServlet" onClick="$('#vc_refresh').click();">
							<a id="vc_refresh" href="javascript:void(0)" onClick="$('#vc_image').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());">看不清</a>
						</div>
                        
						<div class="captcha_row pr">
							<input type="text" placeholder="短信验证码"  id="smsCode" name="smsCode" autocomplete="off" maxlength="6" >
							<a id="sendSmsCode"  class="bt_sms_code" href="javascript:void(0);" onClick="sendSmsCode(this)">发送短信验证码</a>
						</div>
                        
                        <div class="password_row invitation_code pr">
                        	<a href="${ctxFront }/activity/marketing" target="_blank" class="float_button" style="display:block;">送豪礼啦</a>
                        	<input id="recommendMobile" name="recommendMobile" autocomplete="off" type="text" placeholder="推荐人手机号（选填）">
                        </div>
	                 	
                        <div id="message_box" class="error_row ${empty message ? 'hide' : ''}">
							${message}
						</div>
                        
	                 	<div class="agreement_row pr">
	                   		<div id="agree" class="agree checkbox_selected_blue"></div>
	                   		<span>&nbsp;&nbsp;我已阅读并同意</span><a target="_blank" href="${ctxFront }/agreement/register">《花生金服注册协议》</a>
                            <label class="tips1" id="agreeTips" style="display:none"></label>
	                 	</div>
	                 	<div id="register" class="register_row">立即注册</div>
	                 	<div class="captcha_row text-center">已有账号？<a href="${ctxFront }/login">马上登录</a></div>
                 	</form>
				</div>
			</div>
	</body>
</html>