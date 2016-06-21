<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/util/bank_logo.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_yhkgl.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<title></title>
		<script type="text/javascript">
			$(function(){
				$("#inputForm").validate({
					
					rules: {
						realName: {required: true, realName: true},
						idCardNo:{required: true,minlength: 18,maxlength: 18,card: true,
							remote:{url:"${ctxFront}/customer/thirdAccount/isIdCardNoLessThanUseTimesLimit",data:{idCardNo:function(){return $("#idCardNo").val()}}}}
					},
					messages: {
						realName: {required: "请输入真实姓名."},
						idCardNo: {required: "请输入身份证号码.",minlength:"请输入18位身份证号码.",maxlength:"请输入18位身份证号码.",card: "身份证号码输入不正确，请重新输入.",remote:"身份证号码使用次数超过限制."}
					}
				});
				$("#register").click(function(){
					$("#registerForm").submit();
				});
				
			});
		</script>
	</head>
	<body>
		<div class="div_height_5"></div>
		<div class="bg_789_top"></div>
		
		<div class="wdzh-main">
			<div class="wdzh-title">
		    	<span>开通易宝支付资金托管账号</span>
		    </div>
		    <div class="wdzh-content">
		    	<div class="wdzh-ktzh">
		        	<form id="inputForm" action="${ctxFront }/customer/thirdAccount/sign" method="post">
		                <dl class="formList clearfix">
		                    <dt>手机号码</dt>
		                    <dd class="center"><input name="mobile" value="${customerBase.mobile }" readonly type="text"/></dd>
		                    <dd class="tips hide">您输入的手机号码不格式不正确</dd>
		                </dl>
		                <dl class="formList clearfix">
		                    <dt>真实姓名</dt>
		                    <dd class="center"><input id="realName" name="realName" type="text" /></dd>
		                    <dd class="tips">请仔细确认身份证号码和真实姓名，开通后不能再次修改</dd>
		                </dl>
		              	<dl class="formList clearfix">
		                    <dt>身份证号码</dt>
		                    <dd class="center"><input id="idCardNo" name="idCardNo" type="text"/></dd>
		                    <dd class="tips hide">您输入的身份证号码不格式不正确</dd>
		                </dl>
		                <div class="bottom-text">只有开通了易宝支付资金托管账号才能进行正常投资和融资操作！</div>   
		                <div class="bottom-btn"><input type="submit" value="提交" /></div>     
		          </form>
		        </div>
		        <div class="bottom-grain"></div>
		        <div class="buttom-tips">
		        	<div class="tips-top"></div>
		            <div class="tips-center">
		            	<dl>
		                	<dt>为什么要开通第三方资金托管账号？</dt>
		                    <dd><span class="red-text">*</span>仅在用户授权的前提下方可通过平台对用户本人资金账户进行充值、预授权、提现、查询，确保用户资金安全</dd>
		                    <dd><span class="red-text">*</span>方便用户线上、线下快捷支付</dd>
		                    <dd><span class="red-text">*</span>操作简单，满足用户资金快速到账需求</dd>
		                    <dd><span class="red-text">*</span>交易轻松掌握，款项如期而至，财务记账清晰可见</dd>
		                    <dd><span class="red-text">*</span>手机验证、账户交易码均由用户授权</dd>
		                </dl>
		            </div>
		        	<div class="tips-bottom"></div>
		        </div>
		    </div>
		</div>
		
		<div class="bg_789_bottom"></div>
	</body>
</html>