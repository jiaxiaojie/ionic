<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zjgl_cz.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/tipso.css" /><!--提示框样式-->
		<script type="text/javascript" src="${ctxStatic}/modules/front/js/tipso.js"></script><!--提示框JS-->
		<title></title>
		<script type="text/javascript">
			function toSign() {
				$("#inputForm").submit();
			}
			$(function(){
				$("#inputForm").validate({
					rules: {amount: {required: true, digits: true, min: 100}},
					messages: {amount: {required: "请输入充值金额.", min: "充值金额应大于等于100元."}}
				});
			});
		</script>
	</head>
	<body>
		<c:choose>
			<c:when test="${need_third_account_tip && not empty p2p:getPrincipal() && p2p:getCustomerAccount().hasOpenThirdAccount != '1'}">
				<div id="content_top" class="bg_781x82">
					<img src="${ctxStatic}/modules/front/images/util/!.png"/>
					<span class="span_text">您还未开通第三方托管账户， 开通后才能进行充值操作&nbsp;</span>
					<span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="花生金服采用全程第三方资金托管的方式，全程保障您的资金安全"></span>
					<div class="bt_orange_134x31" style="float:right;margin-right:30px;"><a href="${ctxFront }/customer/thirdAccount/open">马上开通</a></div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="bg_789_top"></div>
				<div class="wdzh-main">
					<div class="wdzh-title">
				    	<span>充值</span>
				    </div>
				    <div class="wdzh-content">
				    	<div class="wdzh-ktzh zjgl_cz">
				        	<form id="inputForm" action="${ctxFront }/customer/capital/recharge/sign" method="post">
				        		<!-- <div class="impor_tips">充值手续费按充值金额的0.18%由第三方支付平台收取。目前充值，该手续费由花生金服承担。</div> -->
				                <dl class="formList clearfix">
				                    <dt>账户余额</dt>
				                    <dd class="center"><span class="orange-text f20">￥<fmt:formatNumber value="${customerBalance.goldBalance }" pattern="##0.00" /></span>元</dd>
				                </dl>
				                <dl class="formList clearfix">
				                    <dt><span class="red-text red_star">*</span>充值金额</dt>
				                    <dd class="center input_box"><input id="amount" name="amount" type="text" maxlength="10"/><span class="yuan">元</span></dd>
				                    <dd class="tips">充值到账时间约为5至10分钟，如有问题请联系客服</dd><!--显示时把hide替换成show-->
				                </dl>
				              	<!-- <dl class="formList clearfix">
				                    <dt>充值费用</dt>
				                    <dd class="center"><span class="orange-text f20">￥0.00</span>元<span class="icon_tip"><a href="javascript:;" class="tip1"  data-tipso="这里是提示的文字内容这里是提示的这里是提示的文字内容这里是提示的文字内容这里是提示的文字内容文字内容"></a></span></dd>
				                </dl> -->
				                <div class="cz-btn"><br/>
				                <a href="javascript:void(1)" onclick="toSign()" class="btn_orange_134x31">充值</a>
				                </div>
				          </form>
				        </div>
				        <div class="div_height_30"></div>
				        <div class="buttom-tips">
				        	<div class="tips-top"></div>
				            <div class="tips-center">
				            	<dl>
				                	<dt>温馨提示 </dt>
				                    <dd></dd>
				                    <dd>1.网银最低充值金额应大于等于100元；</dd>
				                    <dd>2.请投资人根据发标计划合理充值，因富定金服无法触及用户资金账户，无法收取用户任何费用，为防止套现，所充资金必须经投标回款后才能提现；</dd>
				                    <dd>3.充值/提现必须为银行借记卡，不支持存折、信用卡充值；</dd>
				                    <dd>4.严禁利用充值功能进行信用卡套现、转账、洗钱等行为，一经发现，将封停账号30天；</dd>
				                    <dd>5.充值期间，请勿关闭浏览器，待充值成功并返回首页后，所充资金才能入账，如有疑问，请联系客服；</dd>
				                    <dd>6.充值需开通银行卡网上支付功能，如有疑问请咨询开户行客服。</dd>
				                </dl>
				            </div>
				        	<div class="tips-bottom"></div>
				        </div>
				    </div>
				<div class="bottom-grain"></div>
				</div>
				<!--提示框JS-->
				<script type="text/javascript">
				$(function() {
					// 1
					$('.tip1').tipso({
						useTitle: false
					});
				});
				</script>
				<div class="bg_789_bottom"></div>
			</c:otherwise>
		</c:choose>
	</body>
</html>