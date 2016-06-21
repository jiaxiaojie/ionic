<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/views/include/head_for_front.jsp"%>
<link href="${ctxStatic}/modules/front/css/util/util.css?${version }" rel="stylesheet" />
<script type="text/javascript">
$(function(){
	$("#leaveMessage").validate({
		submitHandler: function(form){
			var data = {};
			data.name = $("#name").val();
			data.mobile = $("#mobile").val();
			data.email = $("#email").val();
			data.content = $("#content").val();
			$.getJSON("${ctxFront }/activity/hanworld/leaveMessage", data, function(result){
				console.log(result);
				console.log(result.isSuccess);
				if(result.isSuccess) {
					$("#submitTips").removeClass("hide").addClass("show");
				} else {
					$.jBox.tip(result.message);
				}
			});
		},
		rules: {
			name: {required: true, realName: true},
			mobile: {required: true, mobile: true},
			email: {email: true},
			content: {minlength: 5, maxlength: 200}
		},
		messages: {
			name: {required: "*请输入姓名", realName: "*姓名只能为2-10个汉字"},
			mobile: {required: "*请输入手机号", mobile: "*请输入正确的手机号"},
			email: {email: "*请输入正确的邮箱"},
			content: {minlength: "*留言不能少于5个字", maxlength: "*留言不能多于200个字"}
		},
		errorClass: "show",
		errorPlacement: function(error, element) {
			var tips = $("#" + element[0].name + "Tips");
			tips.html(error);
		}
	});
	$("#submitMessage").click(function(){
		$("#leaveMessage").submit();
	});
});
</script>

<title>千载难逢，美国土地投资时机已经来临！</title>
<style type="text/css">
body{ background-color:#1f4480;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto;}
.mc-1{ height:368px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-1.jpg) no-repeat center top;}
.mc-2{ height:365px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-2.jpg) no-repeat center top;}
.mc-3{ height:500px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-3.jpg) no-repeat center top;}
.mc-4{ height:413px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-4.jpg) no-repeat center top;}
.mc-5{ height:590px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-5.jpg) no-repeat center top;}
.mc-6{ height:464px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-6.jpg) no-repeat center top;}
.mc-7{ height:598px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-7.jpg) no-repeat center top;}
.mc-8{ height:402px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-8.jpg) no-repeat center top;}
.mc-9{ height:368px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-9.jpg) no-repeat center top;}
.mc-10{ height:248px; background:url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-10.jpg) no-repeat center top;}
.mc-11{ color:#fff; height:471px; background:#1f4480 url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-11.png) no-repeat center bottom;}
.mc-11 .message-title{ margin:0 auto; padding:40px 0 20px; width:562px; font-size:18px; line-height:28px; text-align:center;}
.mc-11 .message-input{ width:795px; margin:0 auto;}
.message-input .input-area{ float:left; width:260px; height:64px;}
.message-input .input-area p{ padding-left:46px; height:28px; line-height:28px; color:#F9941E; overflow:hidden;}
.message-input .input-area p label{ margin-bottom:0;}
.message-input .input-area dl dt, dd{ float:left;}
.message-input .input-area dl dt{ margin-right:10px; width:42px; height:30px; line-height:30px; text-align:right; font-size:14px;}
.message-input .input-area dl dd input{ padding:3px; width:194px; height:22px; line-height:28px; border:1px solid #d9dce2; border-radius:0;}
.message-input .input-area dl dd input:focus{ border-color:#5981c1;}
.message-textarea{ margin:0 auto; width:795px;}
.message-textarea p{ padding-left:46px; height:28px; line-height:28px; color:#F9941E; overflow:hidden;}
.message-textarea p label{ margin-bottom:0;}
.message-textarea dt, dd{ float:left;}
.message-textarea dt{ margin-right:10px; width:42px; text-align:right; font-size:14px;}
.message-textarea dd textarea{ padding:3px; width:714px; height:78px; line-height:20px; font-size:12px; border:1px solid #d9dce2; border-radius:0; resize:none;}
.message-textarea dd textarea:focus{ border-color:#5981c1;}
.message-button{ margin:10px auto 0; padding-left:32px; width:723px; text-align:center;}
.message-button .line{ margin-top:18px; width:270px; height:1px; background-color:#254d8c;}
.message-button .button-area{ margin:0 auto; width:115px; height:37px; display:inline-block;}
.message-button .button-area a{ width:115px; height:36px; line-height:36px; text-align:center; display:inline-block; border:1px solid #5981c1; color:#fff; font-size:18px; border-radius:6px; background: linear-gradient(#1f4480,#5981c1); background: -o-linear-gradient(#1f4480,#5981c1); background: -moz-linear-gradient(#1f4480,#5981c1); background: -webkit-linear-gradient(#1f4480,#5981c1);}
.message-popup{ position:fixed; top:50%; left:50%; margin:-140px 0 0 -190px; padding:20px; width:340px; height:240px; background-color:#5980c1; box-shadow:0 2px 10px rgba(0,0,0,.2);-o-box-shadow: 0 2px 10px rgba(0,0,0,.2); -ms-box-shadow: 0 2px 10px rgba(0,0,0,.2); -moz-box-shadow: 0 2px 10px rgba(0,0,0,.2); -webkit-box-shadow: 0 2px 10px rgba(0,0,0,.2);}
.message-popup .mp-content{ position:relative; margin:0 auto; width:340px; height:240px; background-color:#fff; border-radius:6px;}
.mp-content .mp-title{ position:absolute; top:-10px; left:50%; margin-left:-86px; width:172px; height:46px; line-height:46px; text-align:center; font-size:22px; color:#fff; background: url(${ctxStatic }/modules/front/images/activity/hanworld/hanworld-12.png) no-repeat;}
.mp-content .mp-text{ padding:80px 0 30px; color:#1f4480; font-size:18px; line-height:30px; text-align:center;}
.mp-content .mp-button{ margin:0 auto; width:112px; height:34px;}
.mp-content .mp-button a{ width:112px; height:34px; line-height:34px; text-align:center; display:block; color:#fff; font-size:18px; border-radius:3px; background: linear-gradient(#1f4480,#5981c1); background: -o-linear-gradient(#1f4480,#5981c1); background: -moz-linear-gradient(#1f4480,#5981c1); background: -webkit-linear-gradient(#1f4480,#5981c1);}
</style>
</head>
<body>
<div class="activity-header">
	<div class="display-area clearfix">
    	<div class="header-logo pull-left">
        	<a href="${ctxFront }/index">
				<img id="logo" class="logo" src="${ctxStatic }/modules/front/images/util/logo.png">
        	</a>
        </div>
    	<div class="header-btn pull-right">
        	<a href="${ctxFront }/index">进入首页</a>
        </div>
    </div>
</div>
<!--main-->
<div class="main">
    <!--main content-->
    <div class="main-content">
    	<div class="mc-1"></div>
    	<div class="mc-2"></div>
    	<div class="mc-3"></div>
    	<div class="mc-4"></div>
    	<div class="mc-5"></div>
    	<div class="mc-6"></div>
    	<div class="mc-7"></div>
    	<div class="mc-8"></div>
    	<div class="mc-9"></div>
    	<div class="mc-10"></div>
    	<div class="mc-11">
        	<div class="display-area">
            	<form id="leaveMessage" action="" method="post">
                    <div class="message-title">为了向更多对美国海外地产项目感兴趣的投资人士们提供最新的信息，请在下方填写您的基本信息，我们会尽快与您联系。</div>
                    <div class="message-input clearfix">
                    	<div class="input-area">
                        	<p><span class="show" id="nameTips"></span></p>
                            <dl class="clearfix">
                            	<dt>* 姓名</dt>
                                <dd><input id="name" name="name" type="text" placeholder="请输入您的姓名" autocomplete="off"></dd>
                            </dl>
                        </div>
                    	<div class="input-area">
                        	<p><span class="show" id="mobileTips"></span></p>
                            <dl class="clearfix">
                            	<dt>* 电话</dt>
                                <dd><input id="mobile" name="mobile" type="text" placeholder="请输入您的电话" autocomplete="off"></dd>
                            </dl>
                        </div>
                    	<div class="input-area">
                        	<p><span class="show" id="emailTips"></span></p>
                            <dl class="clearfix">
                            	<dt>邮箱</dt>
                                <dd><input id="email" name="email" type="text" placeholder="请输入您的邮箱" autocomplete="off"></dd>
                            </dl>
                        </div>
                    </div>
                    <div class="message-textarea clearfix">
                    	<p><span class="show" id="contentTips"></span></p>
                   	  <dl class="clearfix">
                        	<dt>留言</dt>
                            <dd><textarea id="content" name="content" cols="" rows="" placeholder="请输入您的留言内容..." autocomplete="off"></textarea></dd>
                        </dl> 
                    </div>
                    <div class="message-button clearfix">
                    	<div class="line fl"></div>
                        <div class="button-area"><a id="submitMessage" href="javascript:;">提交留言</a></div>
                    	<div class="line fr"></div>
                    </div>
                    <div class="message-popup hide" id="submitTips">
                    	<div class="mp-content">
                        	<div class="mp-title">提交成功</div>
                        	<div class="mp-text">感谢您的留言，<br>我们会尽快与您联系。</div>
                        	<div class="mp-button"><a href="javascript:;" onclick="$('#submitTips').removeClass('show').addClass('hide')">确定</a></div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- copyright -->
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
	<div class="footer_text">©2015上海蓄积金融信息服务有限公司&nbsp;&nbsp;All right reserved.&nbsp;&nbsp;沪ICP备15025483号</div>
   	<div class="div_height_20"></div>
   	<div class="footer_logo">
    	<a href="javascript:void(0);" class="logo_gs"></a>
    	<a href="javascript:void(0);" class="logo_nt"></a>
    	<a href="javascript:void(0);" class="logo_jc"></a>
   	</div>
</div>
</body>
</html>