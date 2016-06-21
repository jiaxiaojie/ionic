<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_ghtx.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/jcrop/css/jquery.Jcrop.min.css" rel="stylesheet"/>
		<script src="${ctxStatic}/jcrop/js/jquery.Jcrop.min.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/front/css/wdzh/zhgl_grjcxx.css?${version }" />
		<title></title>
		<script type="text/javascript">
			$(function(){
				//设置昵称部分
				$("#showOrHideSetNickname").click(function() {
					if($("#sectionSetNickname").css("display") == "none") {
						$("#sectionSetNickname").show();
						$(this).html("取消");
					} else {
						$("#sectionSetNickname").hide();
						$(this).html("设置");
					}
				});
				$("#btnSubmitSetNickname").click(function() {
					$("#setNicknameForm").submit();
				});
				$("#setNicknameForm").validate({
					submitHandler: function(form){
						var url = "${ctxFront}/customer/account/setNickname";
						var data = {};
						data.accountId = $("#accountId").val();
						data.nickname = $("#nickname").val();
						$.getJSON(url, data, function(data) {
							if(data.success) {
								$("#unSetNickname").html($("#nickname").val());
								$("#showOrHideSetNickname").parent().html("<i class=\"icon_check_blue\"></i>已设置");
								$("#topShowLoginName").html($("#nickname").val());
								$("#sectionSetNickname").hide();
								$.jBox.tip('设置昵称成功！', 'success');
							} else {
								$("#setNicknameTipes").show();
								$("#setNicknameTipes").html(data.message);
							}
						});
					},
					rules: {
						nickname: {
							required: true,
							minlength: 3,
							maxlength: 20,
							abc: true,
							remote: {
								url: "${ctxFront}/customer/account/checkNicknameCanUse",
								data: {
									nickname: function() {
										return $("#nickname").val()
									}
								}
							}
						}
					},
					errorClass: "tips-err",
					messages: {
						nickname: {required: "请输入昵称.", minlength: "昵称不能少于3位.", maxlength: "昵称不能多于20位.", abc: "昵称必须由数字、字母或下划线组成.", remote: "昵称已存在，请重新输入."}
					},
					errorLabelContainer: "#setNicknameTipes"
				});
				
				//修改密码部分
				$("#showOrHideChangePassword").click(function() {
					if($("#sectionChangePassword").css("display") == "none") {
						$("#sectionChangePassword").show();
						$(this).html("取消");
					} else {
						$("#sectionChangePassword").hide();
						$(this).html("修改");
					}
				});
				$("#btnSubmitChangePassword").click(function() {
					$("#changePasswordForm").submit();
				});
				$("#changePasswordForm").validate({
					submitHandler: function(form){
						var url = "${ctxFront}/customer/account/changePassword";
						var data = {};
						data.accountId = $("#accountId").val();
						data.oldPassword = $("#oldPassword").val();
						data.newPassword = $("#newPassword").val();
						$.getJSON(url, data, function(data) {
							if(data.success) {
								$("#sectionChangePassword").hide();
								$("#changePasswordForm")[0].reset();
								$("#showOrHideChangePassword").html("修改");
								$.jBox.tip('修改密码成功！', 'success');
							} else {
								$("#changePasswordTips").show();
								$("#changePasswordTips").html(data.message);
							}
						});
					},
					rules: {
						oldPassword: {required: true, minlength: 5, maxlength: 50},
						newPassword: {required: true, minlength: 5, maxlength: 50},
						confirmPassword: {required: true, equalTo: "#newPassword"}
					},
					errorClass: "tips-err",
					messages: {
						oldPassword: {required: "请输入原始密码.", minlength: "原始密码不能少于3位.", maxlength: "原始密码不能多于50位."},
						newPassword: {required: "请输入新密码.", minlength: "新密码不能少于3位.", maxlength: "新密码不能多于50位."},
						confirmPassword: {required: "请输入确认密码.", equalTo: "确认密码输入不一致."}
					},
					errorLabelContainer: "#changePasswordTips"
				});
				
				//修改邮箱部分
				$("#showOrHideChangeEmail").click(function() {
					if($("#sectionChangeEmail").css("display") == "none") {
						$("#sectionChangeEmail").show();
						$(this).html("取消");
					} else {
						$("#sectionChangeEmail").hide();
						$(this).html("修改");
					}
				});
				$("#submitChangeEmail").click(function() {
					$("#changeEmailForm").submit();
				});
				$("#changeEmailForm").validate({
					submitHandler: function(form){
						var url = "${ctxFront}/customer/account/changeEmail";
						var data = {};
						data.accountId = $("#accountId").val();
						data.newEmail = $("#newEmail").val();
						data.emailCode = $("#emailCode").val();
						data.password = $("#changeEmailPassword").val();
						$.getJSON(url, data, function(data) {
							if(data.success) {
								var newEmail = $("#newEmail").val();
								$("#sectionChangeEmail").hide();
								$("#showOrHideChangeEmail").html("修改");
								$("#unChangeEmail").html(newEmail);
								$("#changeEmailForm")[0].reset();
								$("#oldEmail").val(newEmail);
								clearInter("#btnSendChangeEmailCode", "获取验证码");
								$.jBox.tip('修改邮箱成功！', 'success');
							} else {
								$("#changeEmailTips").show();
								$("#changeEmailTips").html(data.message);
							}
						});
					},
					rules: {
						newEmail: {required: true, email: true, notEqual: "#oldEmail", remote: {
							url: "${ctxFront}/customer/account/checkEmailCanUse",
							data: {
								accountId: function(){
									return $("#accountId").val()
								},
								email: function(){
									return $("#newEmail").val()
								}
							}
						}},
						emailCode: {required: true, remote:{url:"${ctxFront}/emailCode/auth",data:{email:function(){return $("#newEmail").val()},emailCode:function(){return $("#emailCode").val()}}}},
						changeEmailPassword: {required:true, minlength: 5, maxlength: 50}
					},
					errorClass: "tips-err",
					messages: {
						newEmail: {required: "请输入邮箱地址.", email: "邮箱地址输入不正确，请重新输入.", notEqual: "不能修改为原始邮箱地址.", remote: "邮箱地址已被使用，请重新输入."},
						emailCode: {required: "请输入邮箱验证码.", remote: "邮箱验证码输入不正确."},
						changeEmailPassword: {required: "请输入登录密码.", minlength: "登录密码不能少于3位.", maxlength: "登录密码不能多于50位."}
					},
					errorLabelContainer: "#changeEmailTips"
				});
				$("#btnSendChangeEmailCode").click(function() {
					if(!$("#btnSendChangeEmailCode").attr("disabled") && $("#changeEmailForm").validate().element($("#newEmail"))) {
						var data = {};
						data.accountId = $("#accountId").val();
						data.email = $("#newEmail").val();
						$.get("${ctxFront}/emailCode/sendToChangeEmail",data,function(){});
						$("#btnSendChangeEmailCode").attr("disabled", "disabled");
						//$("#changeEmailTips").show();
						sendCodeFunction(this, 60);
						$.jBox.alert("验证码已发送,请尽快去您的" + $("#newEmail").val() + "邮箱查看！");
					}
				});
				
				//激活邮箱部分
				$("#showOrHideActivateEmail").click(function() {
					if($("#sectionActivateEmail").css("display") == "none") {
						$("#sectionActivateEmail").show();
						$(this).html("取消");
					} else {
						$("#sectionActivateEmail").hide();
						$(this).html("修改");
					}
				});
				$("#submitActivateEmail").click(function() {
					$("#activateEmailForm").submit();
				});
				$("#activateEmailForm").validate({
					submitHandler: function(form){
						var url = "${ctxFront}/customer/account/activateEmail";
						var data = {};
						data.accountId = $("#accountId").val();
						data.email = $("#email").val();
						data.emailCode = $("#activateEmailCode").val();
						$.getJSON(url, data, function(data) {
							if(data.success) {
								$("#sectionActivateEmail").hide();
								$("#showOrHideActivateEmail").html("修改");
								$("#unChangeEmail").html($("#email").val());
								$("#showOrHideActivateEmail").parent().html("<i class=\"icon_check_blue\" title=\"已绑定\"></i><a href=\"javascript:void(0);\" id=\"showOrHideChangeEmail\">修改</a>");
								$("#showOrHideChangeEmail").click(function() {
									if($("#sectionChangeEmail").css("display") == "none") {
										$("#sectionChangeEmail").show();
										$(this).html("取消");
									} else {
										$("#sectionChangeEmail").hide();
										$(this).html("修改");
									}
								});
								clearInter("#btnSendActivateEmailCode", "获取验证码");
								$.jBox.tip('激活邮箱成功！', 'success');
							} else {
								$("#activateEmailTips").show();
								$("#activateEmailTips").html(data.message);
							}
						});
					},
					rules: {
						email: {required: true, email: true, remote: {
							url: "${ctxFront}/customer/account/checkEmailCanUse",
							data: {
								accountId: function() {
									return $("#accountId").val()
								},
								email: function(){
									return $("#email").val()
								}
							}
						}},
						activateEmailCode: {required: true, remote:{url:"${ctxFront}/emailCode/auth",data:{email:function(){return $("#email").val()},emailCode:function(){return $("#activateEmailCode").val()}}}},
					},
					errorClass: "tips-err",
					messages: {
						email: {required: "请输入邮箱地址.", email: "邮箱地址输入不正确，请重新输入.", remote: "邮箱地址已被使用，请重新输入."},
						activateEmailCode: {required: "请输入邮箱验证码.", remote: "邮箱验证码输入不正确."}
					},
					errorLabelContainer: "#activateEmailTips"
				});
				$("#btnSendActivateEmailCode").click(function() {
					if(!$("#btnSendActivateEmailCode").attr("disabled") && $("#activateEmailForm").validate().element($("#email"))) {
						var data = {};
						data.accountId = $("#accountId").val();
						data.email = $("#email").val();
						$.get("${ctxFront}/emailCode/sendToChangeEmail",data,function(){});
						$("#btnSendActivateEmailCode").attr("disabled", "disabled");
						$("#activateEmailTips").show();
						sendCodeFunction(this, 60);
						$.jBox.alert("验证码已发送,请尽快去您的" + $("#email").val() + "邮箱查看！");
					}
				});
			});

			var intervalFunction;
			var seconds = 60;
			var sendCodeFunction = function(element) {
				$(element).html(seconds + "秒后可重发");
				intervalFunction = window.setInterval(function() {
					seconds--;
					$(element).html(seconds + "秒后可重发");
					if(seconds <= 0) {
						clearInter(element, "重发验证码");
					}
				},1000);
			}
			var clearInter = function(element, text) {
				seconds = 60;
				window.clearInterval(intervalFunction);
				$(element).removeAttr("disabled");
				$(element).html(text);
			}
			
			var changeMobile = function() {
				<c:choose>
					<c:when test="${customerAccount.hasOpenThirdAccount eq '1' }">
						window.open("${ctxFront }/customer/account/changeMobile/sign");
					</c:when>
					<c:otherwise>
						$.jBox.alert("您还未开通第三方账号，不能修改手机号", "提示");
					</c:otherwise>
				</c:choose>
			}
			
			var resetTransPwd = function() {
				<c:choose>
					<c:when test="${customerAccount.hasOpenThirdAccount eq '1' }">
						window.open("${ctxFront }/customer/account/resetTransPwd/sign");
					</c:when>
					<c:otherwise>
						$.jBox.alert("您还未开通第三方账号，不能重置交易密码", "提示");
					</c:otherwise>
				</c:choose>
			}
		</script>
	</head>
	<body>
		<div class="right">
		    <div class="bg_789_top"></div>
		    <div id="content_layout" class="bg_area_004">
				<div class="wdzh_right_title">
					<span>安全信息</span>
			    </div>		        
		        <div id="content_center" style="display:block; padding:0;">
               		<input type="hidden" id="accountId" name="accountId" value="${customerAccount.accountId }"/>
		            <div class="user_info user_info_list no_border_bottom">
		                <dl class="clearfix">
		                    <dt><span class="red-text">*</span>昵称</dt>
		                    <!--未设置昵称显示为“未设置”，已设置昵称则显示为设置的昵称。-->
		                    <c:choose>
		                    	<c:when test="${empty customerAccount.nickname }">
		                    <dd id="unSetNickname">未设置</dd>
		                    <dd class="text_tip fr"><a href="javascript:void(0);" id="showOrHideSetNickname">设置</a></dd>
		                    <dd class="hide_area" id="sectionSetNickname" style="display:none">
		                    	<form id="setNicknameForm" action="">
			                    	<div class="content">
			                        	<!--默认隐藏“hide”，需要时显示“show”-->
			                        	<div id="setNicknameTipes" class="tips hide"></div>
			                        	<div class="item">
			                            	<label>昵称设置</label>
			                                <input id="nickname" name="nickname" autocomplete="off" type="text">
			                            </div>
			                            <div class="bt_orange_134x31"><a href="javascript:void(0);" id="btnSubmitSetNickname">设置</a></div>
			                        </div>
		                        </form>
		                    </dd>
		                    	</c:when>
		                    	<c:otherwise>
		                    <dd>${customerAccount.nickname }</dd>
		                    <dd class="text_tip fr"><i class="icon_check_blue"></i>已设置</dd>
		                    	</c:otherwise>
		                    </c:choose>
		                </dl>
		                <dl class="clearfix">
		                    <dt><span class="red-text">*</span>登录密码</dt>
		                    <dd>******</dd>
		                    <dd class="text_tip fr"><a href="javascript:void(0);" id="showOrHideChangePassword">修改</a></dd>
		                    <dd class="hide_area" id="sectionChangePassword" style="display:none">
		                    	<form id="changePasswordForm" action="">
			                    	<div class="content">
			                        	<!--默认隐藏“hide”，需要时显示“show”-->
			                        	<div class="tips hide" id="changePasswordTips"></div>
			                        	<div class="item">
			                            	<label>原密码</label>
			                                <input id="oldPassword" name="oldPassword" autocomplete="off" type="password">
			                            </div>
			                            <div class="item">
			                            	<label>新密码</label>
			                                <input id="newPassword" name="newPassword" autocomplete="off" type="password">
			                            </div>
			                            <div class="item">
			                            	<label>确认密码</label>
			                                <input id="confirmPassword" name="confirmPassword" autocomplete="off" type="password">
			                            </div>
			                            <div class="bt_orange_134x31"><a href="javascript:void(0);" id="btnSubmitChangePassword">确认修改</a></div>
			                        </div>
		                        </form>
		                    </dd>
		                </dl>
		                
		                <dl class="clearfix">
		                    <dt><span class="red-text">*</span>交易密码</dt>
		                    <dd>******</dd>
		                    <dd class="text_tip fr"><i class="icon_check_blue"></i><a onclick="resetTransPwd()" href="javascript:void(0);">重置</a></dd>
		                </dl>
		                
		                <dl class="clearfix">
		                    <dt><span class="red-text">*</span>绑定邮箱</dt>
		                    <c:choose>
		                    	<c:when test="${customerBase.emailAuthCode eq '1' }">
		                    <dd id="unChangeEmail">${customerBase.email }</dd>
		                    <dd class="text_tip fr"><i class="icon_check_blue" title="已绑定"></i><a href="javascript:void(0);" id="showOrHideChangeEmail">修改</a></dd>
		                    	</c:when>
		                    	<c:otherwise>
		                    		<c:choose>
		                    			<c:when test="${empty customerBase.email }">
	                    	<dd id="unChangeEmail">未绑定</dd>
		                    <dd class="text_tip fr"><i class="icon_check_gray"></i><a href="javascript:void(0);" id="showOrHideActivateEmail">绑定</a></dd>
		                    			</c:when>
		                    			<c:otherwise>
	                    	<dd id="unChangeEmail">${customerBase.email }</dd>
		                    <dd class="text_tip fr"><i class="icon_check_gray"></i><a href="javascript:void(0);" id="showOrHideActivateEmail">激活</a></dd>
		                    			</c:otherwise>
		                    		</c:choose>
		                    	</c:otherwise>
		                    </c:choose>
		                    <!--这里的内容默认是隐藏的“display:none”，点击“修改”按钮则显示“display:block”  【修改邮箱】-->
		                    <dd class="hide_area" id="sectionChangeEmail" style="display:none">
		                    	<form id="changeEmailForm" action="">
		                    		<input id="oldEmail" name="oldEmail" autocomplete="off" type="hidden" value="${customerBase.email }" />
			                    	<div class="content">
			                        	<!--默认隐藏“hide”，需要时显示“show”-->
			                        	<div class="tips hide" id="changeEmailTips"></div>
			                        	<div class="item">
			                            	<label>新邮箱</label>
			                                <input id="newEmail" name="newEmail" autocomplete="off" type="text">
			                            </div>
			                        	<div class="item">
			                            	<label>邮箱验证码</label>
			                                <input id="emailCode" name="emailCode" autocomplete="off" type="text"><a href="javascript:void(0)" class="bt_sms_code" id="btnSendChangeEmailCode">获取验证码</a>
			                            </div>
			                            <div class="item">
			                            	<label>登录密码</label>
			                                <input id="changeEmailPassword" name="changeEmailPassword" autocomplete="off" type="password">
			                            </div>
			                            <div class="bt_orange_134x31"><a href="javascript:void(0);" id="submitChangeEmail">提交</a></div>
			                        </div>
		                        </form>
		                    </dd>
		                    <!--这里的内容默认是隐藏的“display:none”，点击“修改”按钮则显示“display:block”  【激活邮箱】-->
		                    <dd class="hide_area" id="sectionActivateEmail" style="display:none">
		                    	<form id="activateEmailForm" action="">
			                    	<div class="content">
			                        	<!--默认隐藏“hide”，需要时显示“show”-->
			                        	<div class="tips hide" id="activateEmailTips"></div>
			                        	<div class="item">
			                            	<label>邮箱地址</label>
			                                <input id="email" name="email" autocomplete="off" type="text" value="${customerBase.email }" />
			                            </div>
			                        	<div class="item">
			                            	<label>邮箱验证码</label>
			                                <input id="activateEmailCode" name="emailCode" autocomplete="off" type="text"><a href="javascript:void(0)" class="bt_sms_code" id="btnSendActivateEmailCode">获取验证码</a>
			                            </div>
			                            <div class="bt_orange_134x31"><a href="javascript:void(0);" id="submitActivateEmail">激活</a></div>
			                        </div>
		                        </form>
		                    </dd>
		                </dl>
		                
		                <dl class="clearfix">
		                    <dt><span class="red-text">*</span>绑定手机</dt>
		                    <dd>${customerBase.mobile }</dd>
		                    <dd class="text_tip fr"><i class="icon_check_blue"></i><a onclick="changeMobile()" href="javascript:void(0);">修改</a></dd>
		                </dl>
		                           
		            </div>
		        </div>
		                  
		        <div class="div_height_50"></div>
		        <div class="bottom"></div>
		        
		    </div>
		    <div class="bg_789_bottom"></div>
		</div>
	</body>
</html>