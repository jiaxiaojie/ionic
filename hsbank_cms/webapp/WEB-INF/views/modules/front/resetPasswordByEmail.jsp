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
			var successTips = "<i class='blue_check bg_20x20'></i>";
			var seconds = 60;
			var sendEmailCodeFunction = function() {
				$("#btnSendEmailCode").html(seconds + "秒后可重发");
				var intervalFunction = window.setInterval(function() {
					seconds--;
					$("#btnSendEmailCode").html(seconds + "秒后可重发");
					if(seconds <= 0) {
						window.clearInterval(intervalFunction);
						seconds = 60;
						$("#btnSendEmailCode").removeAttr("disabled");
						$("#btnSendEmailCode").html("重发验证码");
					}
				},1000);
			}
			$(function() {
				$("#btnSendEmailCode").click(function() {
					if(!$("#btnSendEmailCode").attr("disabled")) {
						//判断手机号、验证码验证是否通过，通过了才能发送短信验证码
						if($("#inputForm").validate().element($("#email")) && $("#inputForm").validate().element($("#validateCode"))) {
							var data = {};
							data.validateCode = $("#validateCode").val();
							data.eamil = $("#email").val();
							$.get("${ctxFront}/emailCode/sendToResetPassword",data,function(){});
							$("#btnSendEmailCode").attr("disabled", "disabled");
							sendEmailCodeFunction();
						}
					}
				});
				$("#inputForm").validate({
					rules : {
						email: {required: true, email: true, remote: "${ctxFront}/resetPassword/checkEmailHasAuthed"},
						validateCode: {required: true,remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"},
                    	newPassword: {required: true,minlength: 5,maxlength: 30},
						confirmPassword: {required: true,equalTo: "#newPassword"},
						emailCode: {required:true,
							remote:{url:"${ctxFront}/emailCode/auth",data:{email:function(){return $("#email").val()},emailCode:function(){return $("#emailCode").val()}}}
						}
					},
					messages: {
						email: {required: "请输入邮箱地址.", email: "请输入正确的邮箱地址.", remote:"邮箱地址未注册或未验证，请重新输入."},
						newPassword: {required: "请输入密码.",minlength:"密码最少为5位.",maxlength:"密码最多为30位."},
						confirmPassword: {required: "请输入确认密码.",equalTo: "确认密码输入不一致."},
						validateCode: {required: "请输入验证码.", remote: "验证码不正确."},
						emailCode: {required:"请输入邮箱验证码.",remote:"邮箱验证码不正确."}
					},
					errorClass: "tips-err",
					errorPlacement: function(error, element) {
						var tips = element.parent().parent().find(".tips");
						tips.show();
						tips.html(error);
					},
					success:function(element){
						//if(element.parent().attr("name") == "emailTips") {
							element.parent().html(successTips);
						//}
				    }
				});
			});
			function submitResetPassword() {
				$("#inputForm").submit();
			}
			function showMobielTab() {
				$("#mobileTab").addClass("current");
				$("#emailTab").removeClass("current");
			}
		</script>
	</head>
	<body>
		<div class="content980">
		    <div class="fp_area">
		    	<div class="fp_tab">
		        	<!--选项卡选中状态加上类“current”-->
		            <a href="${ctxFront }/resetPassword/mobile" onclick="showMobielTab()" id="mobileTab">使用绑定手机找回密码</a>
		            <a href="${ctxFront }/resetPassword/email" id="emailTab" class="current">使用绑定邮箱找回密码</a>
		        </div>
		        
		        <!--“使用绑定邮箱找回密码”内容-->
		        <div id="mobileTab" class="fp_content show">
		        	<form id="inputForm" action="${ctxFront }/resetPassword/email" method="post">
		                <dl class="formList clearfix">
		                    <dt>邮箱地址</dt>
		                    <dd class="center"><input id="email" name="email" autocomplete="off" type="text"></dd>
		                    <dd class="tips hide" name="emailTips"></dd><!--“hide”为隐藏，“show”为显示；输入正确显示蓝色图标“blue_check”，输入错误显示红色图标“red_check”。-->
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
		                  <dt>邮箱验证码</dt>
		                    <dd class="center"><input id="emailCode" name="emailCode" autocomplete="off" type="text"></dd>
		                    <dd class="sc">&nbsp;<a href="javascript:void(0)" class="bt_sms_code" id="btnSendEmailCode">获取验证码</a>&nbsp;</dd>
		                    <dd class="tips hide"></dd>
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