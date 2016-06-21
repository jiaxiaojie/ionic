<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/views/include/head_for_front.jsp" %>
<link href="${ctxStatic }/modules/front/css/util/util.css?${version }" rel="stylesheet" />
<title>花生金服 hsbank360.com-更优质、更安全、更便捷、更有诚意的互联网金融P2P平台</title>
<script type="text/javascript">
	$(function(){
		
		$("#register").click(function(){
			$("#registerForm").submit();
		});
		$("#registerForm").validate({
			submitHandler: function(form){
				if(!$("#readProtocol").attr("checked")) {
					$("#agreeTips").show().html("<b class=\"icon_arrow_yellow\" style=\"left:22%\"></b>请同意我们的条款.").fadeOut(3000);
					return false;
				}
				form.submit();
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
				$.get("${ctxFront}/smsCode/sendToRegister",data,function(){});
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
	function refreshValidateCode() {
		$('#vc_image').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());
	}
	function showPop() {
		document.getElementById("pop").style.display = "block";
	}
	function closePop() {
		document.getElementById("pop").style.display = "none";
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

<style type="text/css">
body{ background-color:#fff8d7;}
input::-webkit-input-placeholder, textarea::-webkit-input-placeholder{ color:#fff;}
input:-moz-placeholder, textarea:-moz-placeholder{ color:#fff;}
input::-moz-placeholder, textarea::-moz-placeholder{ color:#fff;}
input:-ms-input-placeholder, textarea:-ms-input-placeholder{ color:#fff;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto;}
.main-banner{ height:500px; background: url(${ctxStatic }/modules/front/images/activity/marketing/20151030-01.jpg) no-repeat center 0;}
.main-banner .register{ position:relative; margin-top:40px; width:286px; height:420px; color:#fff;}
.main-banner .register .bg-register{ position:absolute; top:0; left:0; width:286px; height:420px; border-radius:6px;-webkit-border-radius:6px; filter:Alpha(Opacity=80); background-color:rgba(96, 19, 136, 0.8); z-index:1;}
.main-banner .register form{ position:absolute; top:0; left:0; width:246px; height:400px; z-index:2; padding:20px 20px 0;}
.main-banner input{ width:232px; height:26px; margin:0; background-color:transparent; border-color:#ad74c4; color:#fff; box-sizing: initial;}
.main-banner dl dd{ position:relative; margin-bottom:10px;}
.main-banner dl dd a{ color:#fff;}
.main-banner dl .vcode .pull-left{ width:137px;}
.main-banner dl .vcode .pull-left input{ width:117px;}
.main-banner dl .vcode .pull-right{ width:100px;}
.main-banner dl .vcode .pull-right a{ margin-top:1px; width:100px; height:34px; line-height:34px; text-align:center; display:inline-block; color:#666; background-color:#fff; border-radius:4px; -webkit-border-radius:4px;}
.main-banner dl .btn-haoli{ position: absolute; right: 6px; top: 6px; padding: 2px 5px; background-color: #d92929; color: #fff; -webkit-border-radius: 3px; -moz-border-radius: 3px; -ms-border-radius: 3px; -o-border-radius: 3px; border-radius: 3px; cursor:default;}
.main-banner dl label{ font-size:12px;}
.main-banner dl label input{ width:auto; height:auto; margin-right:5px;}
.main-banner dl .recommend_mobile input{ border-color:#f04a45;}
.main-banner dl .btn-register{ margin:4px 0; width:246px; height:36px; line-height:36px; display:inline-block; text-align:center; color:#b70000; background-color:#ffc600; font-size:16px; border-radius:4px; -webkit-border-radius:4px;}
.main-content .img-two{ height:213px; background: url(${ctxStatic }/modules/front/images/activity/marketing/20151030-02.jpg) no-repeat center 0;}
.main-content .img-three{ height:317px; background: url(${ctxStatic }/modules/front/images/activity/marketing/20151030-03.jpg) no-repeat center 0;}
.main-content .img-four{ height:320px; background: url(${ctxStatic }/modules/front/images/activity/marketing/20151030-04.jpg) no-repeat center 0;}
.main-content .img-five{ height:466px; background: url(${ctxStatic }/modules/front/images/activity/marketing/20151030-05.jpg) no-repeat center 0;}
.main-content .img-six{ height:466px; background: url(${ctxStatic }/modules/front/images/activity/marketing/20151030-06.jpg) no-repeat center 0;}
.main-content .img-seven{ height:340px; background: url(${ctxStatic }/modules/front/images/activity/marketing/20151030-07.jpg) no-repeat center 0;}
.main-content .btn-yellow{ position:absolute; width:108px; height:34px; line-height:34px; text-align:center; color:#b70000; font-size:16px; background-color:#ffc600;  border-radius:6px; -webkit-border-radius:6px; box-shadow:0px 3px 0px #ee770a; z-index:2;}
.main-content .btn-yellow-lg{ width:158px; height:44px; line-height:44px; text-align:center; color:#b70000; display:inline-block; font-size:16px; background-color:#ffc600;  border-radius:6px; -webkit-border-radius:6px; box-shadow:0px 3px 0px #ee770a; z-index:2;}
.main-content .btn-yellow-lg{}
.enter-home{ padding:50px 0 80px; text-align:center;}
.a-pop-tip{ width:446px; height:249px; background:url(${ctxStatic }/modules/front/images/activity/marketing/20151030-tip.png) no-repeat;}
.activity-tdc{ position:fixed; top:0px; right:20px; width:130px; height:401px; z-index:5;}
</style>

</head>

<body>
<!--二维码-->
<div class="activity-tdc">
	<div class=""><img src="${ctxStatic }/modules/front/images/activity/marketing/20151030-tdc.png"></div>
</div>
<!--头部-->
<div class="activity-header">
	<div class="display-area clearfix">
    	<div class="header-logo pull-left">
        	<a href="${ctxFront }/index"><img id="logo" class="logo" src="${ctxStatic }/modules/front/images/util/logo.png"></a>
        </div>
    	<div class="header-btn pull-right">
        	<a href="${ctxFront }/index">进入首页</a>
        </div>
    </div>
</div>
<!--主要的-->
<div class="activity-main">
	<!--banner-->
    <div class="main-banner">
    	<div class="display-area clearfix">
        	<div class="pull-left"></div>
        	<div class="pull-right register" id="zc">
            	<div class="bg-register"></div>
            	<form id="registerForm" action="${ctxFront}/register" method="post">
            		<sys:message content="${message}"/>
                    <dl>
                    	<dd><input id="mobile" name="mobile" autocomplete="off" type="text" maxLength="11" onkeydown="onlyNum();" autocomplete="off" placeholder="手机号"></dd>
                        <dd><input id="password" name="password" autocomplete="off" type="password" autocomplete="off" placeholder="请输入密码"></dd>
                        <dd><input id="conPassword" name="conPassword" autocomplete="off" type="password" autocomplete="off" placeholder="请输入确认密码"></dd>
                        <dd class="clearfix vcode">
                        	<div class="pull-left"><input id="validateCode" name="validateCode" autocomplete="off" type="text" autocomplete="off" placeholder="请输入验证码"></div>
                        	
                        	<div class="pull-right "><img id="vc_image" onclick="refreshValidateCode()" src="${pageContext.request.contextPath}/servlet/validateCodeServlet"></div>
                        </dd>
                        <dd class="clearfix vcode">
                        	<div class="pull-left"><input id="smsCode" name="smsCode" autocomplete="off" type="text" autocomplete="off" placeholder="短信验证码"></div>
                        	<div class="pull-right"><a id="sendSmsCode" href="#" onClick="sendSmsCode(this)">发送短信验证码</a></div>
                        </dd>
                        <dd class="recommend_mobile"><input id="recommendMobile" name="recommendMobile" autocomplete="off" type="text" autocomplete="off" placeholder="推荐人手机号（选填）"><span class="btn-haoli">送豪礼啦</span></dd>
                        <dd><label class="tips1" id="agreeTips" style="display:none"></label><label><input id="readProtocol" name="readProtocol" autocomplete="off" type="checkbox" value="" checked="checked" >我已阅读并同意《花生金服注册协议》</label></dd>
                        <dd><a href="javascript:void(0);" id="register" class="btn-register">立即注册</a></dd>
                        <dd class="font-size-12 text-center">已有账号？<a href="${ctxFront }/login">立即登录</a></dd>
                    </dl>
                </form>
            </div>
        </div>
    </div>
    <!--主要内容-->
    <div class="main-content">
    	<div class="img-two"></div>
    	<div class="img-three">
        	<div class="display-area"><a href="#zc" class="btn-yellow" style="left:373px; top:110px;">马上领钱</a></div>            
        </div>
    	<div class="img-four">
            <div class="display-area"><a href="${ctxFront }/customer/capital/recharge" target="_blank" class="btn-yellow" style="left:474px; top:242px;">立即充值</a></div>
        </div>
    	<div class="img-five">
            <div class="display-area"><a href="${ctxFront }/wytz" target="_blank" class="btn-yellow" style="left:132px; top:261px;">赚钱走起</a></div>
        </div>
    	<div class="img-six"></div>
    	<div class="img-seven"></div>
    	<div class="enter-home">
        	<div class="display-area"><a href="${ctxFront }/index" target="_blank" class="btn-yellow-lg">进入首页</a></div>        	
        </div>
  </div>
</div>
<!--点击“招唤好友”按钮弹出此窗口-->
<div class="a-pop" id="pop"  style="display:none">
	<div class="a-pop-main" style="margin-left:-243px; margin-top:-144px;">
    	<a href="javascript:;" class="a-btn-colse" onclick="closePop()"></a>
    	<div class="a-pop-content">
        	<div class="a-pop-tip"></div>
        </div>
    </div>
</div>
<!-- 版权申明区域 -->
<div id="footer_area" class="footer_area">
	<div class="footer_menu">
       	<span class=""><a href="${ctxFront }/gywm/index">关于我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/gywm/jrwm">加入我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/gywm/lxwm">联系我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/index#cooperationAgency">友情链接</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">网站地图</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">法律声明</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">黑名单</a></span>
    </div>
	<div class="div_height_20"></div>
	<div class="footer_text">
    	<span>客服邮箱：service@fdjf.net</span>&nbsp;&nbsp;&nbsp;&nbsp;
        <span>服务热线：<b class="font_size_18">400-969-6599</b>&nbsp;(工作日9:00~18:00)</span>
    </div>
    <div class="div_height_10"></div>
	<div class="footer_text">©2015上海富定金融信息服务股份有限公司&nbsp;&nbsp;All right reserved.&nbsp;&nbsp;沪ICP备15025483号</div>
   	<div class="div_height_20"></div>
   	<div class="footer_logo">
    	<!--<a href="javascript:void(0);" class="logo_kx"></a>-->
    	<!--<a href="javascript:void(0);" class="logo_aq"></a>-->
    	<!--<a href="javascript:void(0);" class="logo_x"></a>-->
    	<a href="javascript:void(0);" class="logo_gs"></a>
    	<a href="javascript:void(0);" class="logo_nt"></a>
    	<a href="javascript:void(0);" class="logo_jc"></a>
   	</div>
</div>

</body>
</html>