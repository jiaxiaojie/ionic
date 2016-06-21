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
		<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<title></title>
		<script type="text/javascript">
			$(function(){
				$("#inputForm").validate({
					rules : {
						oldPassword: {required: true},
						password: {required: true,minlength: 5,maxlength: 30},
						confirmPassword: {required: true,equalTo: "#password"}
					},
					messages: {
						oldPassword: {required: "请输入原始密码."},
						password: {required: "请输入密码.",minlength:"密码最少为5位.",maxlength:"密码最多为30位."},
						confirmPassword: {required: "请输入确认密码.",equalTo: "确认密码输入不一致."},
					}
				});
			});
			function submit() {
				$("#inputForm").submit();
			}
		</script>
	</head>
	<body>
		<div class="bg_789_top"></div>
		<div class="wdzh-main">
		    <div class="wdzh-title">
		    	<span>修改密码</span>
		    </div>
		    <div class="wdzh-content">
		    	<div class="wdzh-ktzh zjgl_cz">
		        	<form id="inputForm" action="${ctxFront }/customer/account/changePassword" method="post">
		        		<c:if test="${not empty message }">
		        			${message }
		        		</c:if>
		              	<dl class="formList clearfix">
		                    <dt>原密码</dt>
		                    <dd class="center input_box"><input id="oldPassword" name="oldPassword" type="password" /></dd>
		                    <dd class="tips hide">原密码输入错误！</dd><!--显示时把hide替换成show-->
		                </dl>
		              	<dl class="formList clearfix">
		                    <dt>新密码</dt>
		                    <dd class="center input_box"><input id="password" name="password" type="password" /></dd>
		                    <dd class="tips hide">原密码输入错误！</dd><!--显示时把hide替换成show-->
		                </dl>
		              	<dl class="formList clearfix">
		                    <dt>确认原密码</dt>
		                    <dd class="center input_box"><input id="confirmPassword" name="confirmPassword" type="password" /></dd>
		                    <dd class="tips hide">原密码输入错误！</dd><!--显示时把hide替换成show-->
		                </dl>
		                <div class="div_height_20"></div>
		                <div class="pl120 text-left"><a href="javascript:void(0);" onclick="submit();" class="btn_brown_158x31 ml16">确认</a><a href="" class="btn_blue_158x31 ml16">取消</a></div>
		          </form>
		        </div>
		        <div class="bottom-grain"></div>
		        
		    </div>
		</div>
	</body>
</html>