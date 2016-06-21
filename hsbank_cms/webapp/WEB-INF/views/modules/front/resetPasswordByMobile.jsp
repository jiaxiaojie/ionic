<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/wytz.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/forgetpassword.css?${version }" />
		<title></title>
		<script type="text/javascript">
			var errorMsg = "${errorMsg}";
			var successTips = "<i class='blue_check bg_20x20'></i>";
			var seconds = 60;
			var sendSmsCodeFunction = function() {
				$("#btnSendSmsCode").html(seconds + "秒后可重发");
				var intervalFunction = window.setInterval(function() {
					seconds--;
					$("#btnSendSmsCode").html(seconds + "秒后可重发");
					if(seconds <= 0) {
						window.clearInterval(intervalFunction);
						seconds = 60;
						$("#btnSendSmsCode").removeAttr("disabled");
						$("#btnSendSmsCode").html("重发验证码");
					}
				},1000);
			}
			$(function() {
				$("#btnSendSmsCode").click(function() {
					if(!$("#btnSendSmsCode").attr("disabled")) {
						//判断手机号、验证码验证是否通过，通过了才能发送短信验证码
						if($("#inputForm").validate().element($("#mobile")) && $("#inputForm").validate().element($("#validateCode"))) {
							var data = {};
							data.validateCode = $("#validateCode").val();
							data.mobile = $("#mobile").val();
							$.get("${ctxFront}/smsCode/sendToResetPassword",data,function(msg){
								
								if(msg=="overdueValidateCode"){
									$.jBox.tip("验证码已失效，请重新输入");
									$('#vc_refresh').click();
									$("#validateCode").val("");
								}
							});
							$("#btnSendSmsCode").attr("disabled", "disabled");
							sendSmsCodeFunction();
						}
					}
				});
				$("#inputForm").validate({
					rules : {
						mobile: {required: true, mobile: true, remote: "${ctxFront}/resetPassword/checkMobileExist"},
						validateCode: {required: true,remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"},
                    	newPassword: {required: true,minlength: 5,maxlength: 30},
						confirmPassword: {required: true,equalTo: "#newPassword"},
						smsCode: {required:true}
					},
					messages: {
						mobile: {required: "请输入手机号.", mobile: "请输入正确的手机号.", remote:"手机号未注册，请重新输入."},
						newPassword: {required: "请输入密码.",minlength:"密码最少为5位.",maxlength:"密码最多为30位."},
						confirmPassword: {required: "请输入确认密码.",equalTo: "确认密码输入不一致."},
						validateCode: {required: "请输入验证码.", remote: "验证码不正确."},
						smsCode: {required:"请输入短信验证码."}
					},
					errorClass: "tips-err",
					errorPlacement: function(error, element) {
						var tips = element.parent().parent().find(".tips");
						tips.show();
						tips.html(error);
					},
					success:function(element){
						if(element.parent().attr("name") != "smsCodeTips") {
							element.parent().html(successTips);
						}
				    }
				});
				if(errorMsg != "") {
					$.jBox.error(errorMsg);
				}
				window.setTimeout(function(){
					$("#mobile").val("");
					$("#smsCode").val("");
					$("#newPassword").val("");
					console.log("clear");
				},10);
				
			});
			function submitResetPassword() {
				$("#inputForm").submit();
			}
			function showEmailTab() {
				$("#mobileTab").removeClass("current");
				$("#emailTab").addClass("current");
			}
		</script>
	</head>
	<body>
		<div class="content980">
		    <div class="fp_area">
		    	<div class="fp_tab">
		        	<!--选项卡选中状态加上类“current”-->
		            <a href="${ctxFront }/resetPassword/mobile" id="mobileTab" class="current">使用绑定手机找回密码</a>
		            <a href="${ctxFront }/resetPassword/email" onclick="showEmailTab()" id="emailTab">使用绑定邮箱找回密码</a>
		        </div>
		        
		        <!--“使用绑定手机找回密码”内容-->
		        <div id="mobileTab" class="fp_content show">
		        	<form id="inputForm" action="${ctxFront }/resetPassword/mobile" method="post">
		                <dl class="formList clearfix">
		                    <dt>手机号码</dt>
		                    <dd class="center"><input id="mobile" name="mobile" autocomplete="off" type="text"></dd>
		                    <dd class="tips hide" name="mobileTips"></dd><!--“hide”为隐藏，“show”为显示；输入正确显示蓝色图标“blue_check”，输入错误显示红色图标“red_check”。-->
		                </dl>
		                <dl class="formList clearfix">
		                  <dt>验证码</dt>
		                    <dd class="center"><input id="validateCode" name="validateCode" autocomplete="off" type="text"></dd>
		                    <dd class="sc">
		                    	<div style="margin-left:10px;">
		                    		<img id="vc_image" src="${pageContext.request.contextPath}/servlet/validateCodeServlet" onclick="$('#vc_refresh').click();">
									<a id="vc_refresh" href="javascript:void(0)" onclick="$('#vc_image').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());">看不清</a>
								</div>
		                    </dd>
		                    <dd class="tips hide"></dd>
		                </dl>
		                <dl class="formList clearfix">
		                  <dt>短信验证码</dt>
		                    <dd class="center"><input id="smsCode" name="smsCode" autocomplete="off" type="text"></dd>
		                    <dd class="sc">&nbsp;<a href="javascript:void(0)" class="bt_sms_code" id="btnSendSmsCode">获取验证码</a>&nbsp;</dd>
		                    <dd class="tips hide" name="smsCodeTips"></dd>
		                </dl>
		                <dl class="formList clearfix">
		                  <dt>新密码</dt>
		                    <dd class="center"><input id="newPassword" name="newPassword" autocomplete="off" type="password"></dd>
		                    <dd class="tips hide"></dd>
		                </dl>
		                <dl class="formList clearfix">
		                  <dt>确认密码</dt>
		                    <dd class="center"><input id="confirmPassword" name="confirmPassword" autocomplete="off" type="password"></dd>
		                    <dd class="tips hide"></dd>
		                </dl>
		                <div class="div_height_20"></div>
		                <div class="btn_brown_158x38"><a href="javascript:;" onclick="submitResetPassword()">提交</a></div>
		          </form>
		        </div>
		    </div>
		    
		    <div class="bottom-grain"></div>
		</div>
	</body>
</html>