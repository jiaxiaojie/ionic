<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>易宝测试入口</title>
<meta name="decorator" content="default"/>
	<script>
		
	</script>
</head>
<body>
	<div class="tabbable">
		<!-- Only required for left/right tabs -->
		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab1" data-toggle="tab">浏览器网关接口</a></li>
			<li><a href="#tab2" data-toggle="tab">直连接口</a></li>
		</ul>
		<div class="progress">
	      <div class="bar" style="width: 100%;"></div>
	    </div>
		<hr>
		<div class="tab-content">
			<div class="tab-pane active" id="tab1">
				<div class="tabbable tabs-left">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#lA1">1注册</a></li>
						<li class=""><a data-toggle="tab" href="#lA2">2充值</a></li>
						<li class=""><a data-toggle="tab" href="#lA3">3提现</a></li>
						<li class=""><a data-toggle="tab" href="#lA4">4绑卡</a></li>
						<li class=""><a data-toggle="tab" href="#lA5">5取消绑卡</a></li>
						<li class=""><a data-toggle="tab" href="#lA6">6企业用户注册</a></li>
						<li class=""><a data-toggle="tab" href="#lA7">7转账授权</a></li>
						<li class=""><a data-toggle="tab" href="#lA8">8自动投标授权</a></li>
						<li class=""><a data-toggle="tab" href="#lA9">9自动还款授权</a></li>
						<li class=""><a data-toggle="tab" href="#lA10">10重置密码</a></li>
						<li class=""><a data-toggle="tab" href="#lA11">11修改手机号</a></li>
					</ul>
					<div class="tab-content">
						<div id="lA1" class="tab-pane active">
							<h3>1注册明细</h3>
							<hr/>
							<form  class="form-horizontal" id="toRegisterReq" action="/fuding_p2p/a/yeepay/toRegister">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">商户平台会员标识：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">昵称：</label>
									<div class="controls">
										<input id="nickName"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">真实姓名：</label>
									<div class="controls">
										<input id="realName"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">身份证类型：</label>
									<div class="controls">
										<input id="idCardType"  value="G2_IDCARD"  readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">身份证号：</label>
									<div class="controls">
										<input id="idCardNo"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">手机号码：</label>
									<div class="controls">
										<input id="mobile"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">邮箱：</label>
									<div class="controls">
										<input id="email"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA1Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
						<div id="lA2" class="tab-pane">
							<h3>2充值明细</h3><hr/>
							<form  class="form-horizontal" id="toRechargeReq" action="/fuding_p2p/a/yeepay/toRecharge">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">平台用户编号：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">充值金额：</label>
									<div class="controls">
										<input id="amount"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">费率模式：</label>
									<div class="controls">
										<input id="feeMode"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA2Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
						<div id="lA3" class="tab-pane">
							<h3>3提现明细</h3><hr/>
							<form  class="form-horizontal" id="toWithdrawReq" action="/fuding_p2p/a/yeepay/toWithdraw">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">商户平台会员标识：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">费率模式：</label>
									<div class="controls">
										<input id="feeMode"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">超过此时间：</label>
									<div class="controls">
										<input id="expired"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">提现模式：</label>
									<div class="controls">
										<input id="withdrawType"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">提现金额：</label>
									<div class="controls">
										<input id="amount"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								
								<div class="form-actions">
									<input id="lA3Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
						<div id="lA4" class="tab-pane">
							<h3>4绑卡明细</h3><hr/>
							<form  class="form-horizontal" id="toBindBankCardReq" action="/fuding_p2p/a/yeepay/toBindBankCard">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">商户平台会员标识：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA4Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
						<div id="lA5" class="tab-pane">
							<h3>5取消绑卡明细</h3><hr/>
							<form  class="form-horizontal" id="toUnbindBankCardReq" action="/fuding_p2p/a/yeepay/toUnbindBankCard">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">商户平台会员标识：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA5Go" class="btn btn-success" type="button" value="确定"/>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
							</form>
						</div>
						<div id="lA6" class="tab-pane">
							<h3>6企业注册授权明细</h3><hr/>
							<form  class="form-horizontal" id="toEnterpriseRegisterReq" action="/fuding_p2p/a/yeepay/toEnterpriseRegister">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">商户平台会员标识：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">企业名称：</label>
									<div class="controls">
										<input id="enterpriseName"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">开户银行许可证：</label>
									<div class="controls">
										<input id="bankLicense"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">组织机构代码：</label>
									<div class="controls">
										<input id="orgNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">营业执照编号：</label>
									<div class="controls">
										<input id="businessLicense"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">税务登记号：</label>
									<div class="controls">
										<input id="taxNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">法人姓名：</label>
									<div class="controls">
										<input id="legal"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">法人身份证号：</label>
									<div class="controls">
										<input id="legalIdNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">企业联系人：</label>
									<div class="controls">
										<input id="contact"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">联系人手机号：</label>
									<div class="controls">
										<input id="contactPhone"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">联系人邮箱：</label>
									<div class="controls">
										<input id="email"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">企业会员类型：</label>
									<div class="controls">
									    select ENTERPRISE：企业借款人  GUARANTEE_CORP：担保公司
										<input id="memberClassType"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA6Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
						<div id="lA7" class="tab-pane">
							<h3>7转账授权明细</h3><hr/>
							<form  class="form-horizontal" id="toCpTransactionReq" action="/fuding_p2p/a/yeepay/toCpTransaction">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">出款人平台用户编号：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">出款人用户类型：</label>
									<div class="controls">
										<input id="userType" value="MEMBER"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">业务类型：</label>
									<div class="controls">
										<input id="bizType"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">超过此时间即不允许提交订单：</label>
									<div class="controls">
										<input id="expired"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								
								
								<div class="control-group">
									<label class="control-label">资金明细记录：</label>
									<div class="controls">
										<textarea id="detail"  htmlEscape="false" maxlength="20" class="input-xlarge required"/> 
											amount Y 转入金额
											targetUserType Y 用户类型,见【用户类型】
											targetPlatformUserNo Y 平台用户编号
									</div>
								</div>
								
								
								<div class="control-group">
									<label class="control-label">业务类型：</label>
									<div class="controls">
										<input id="bizType"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">业务扩展属性：</label>
									<div class="controls">
										<input id="extend"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA7Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
						<div id="lA8" class="tab-pane">
							<h3>8自动投标授权明细</h3><hr/>
							<form  class="form-horizontal" id="toAuthorizeAutoTransferReq" action="/fuding_p2p/a/yeepay/toAuthorizeAutoTransfer">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">商户平台会员标识：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA8Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
						<div id="lA9" class="tab-pane">
							<h3>9自动还款授权明细</h3><hr/>
							<form  class="form-horizontal" id="toAuthorizeAutoRepaymentReq" action="/fuding_p2p/a/yeepay/toAuthorizeAutoRepayment">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">标的号：</label>
									<div class="controls">
										<input id="orderNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA9Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
						<div id="lA10" class="tab-pane">
							<h3>10重置密码明细</h3><hr/>
							<form  class="form-horizontal" id="toResetPasswordReq" action="/fuding_p2p/a/yeepay/toResetPassword">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">商户平台会员标识：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA10Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
						<div id="lA11" class="tab-pane">
							<h3>11修改手机号明细</h3><hr/>
							<form  class="form-horizontal" id="toResetMobileReq" action="/fuding_p2p/a/yeepay/toResetMobile">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input id="platformNo" value="10012467598" readly="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input id="requestNo"  htmlEscape="false" maxlength="50" class="input-xlarge required"/>
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">商户平台会员标识：</label>
									<div class="controls">
										<input id="platformUserNo"  htmlEscape="false" maxlength="20" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">页面调回Url：</label>
									<div class="controls">
										<input id="callbackUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务通知URL：</label>
									<div class="controls">
										<input id="notifyUrl"  htmlEscape="false" maxlength="200" class="input-xlarge required"/>
									</div>
								</div>
								<div class="form-actions">
									<input id="lA11Go" class="btn btn-success" type="button" value="确定"/>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="tab2">
				<div class="tabbable tabs-left">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#lB1">1账户查询</a></li>
						<li class=""><a data-toggle="tab" href="#lB2">2资金冻结</a></li>
						<li class=""><a data-toggle="tab" href="#lB3">3资金解冻</a></li>
						<li class=""><a data-toggle="tab" href="#lB4">4直接转账</a></li>
						<li class=""><a data-toggle="tab" href="#lB5">5自动转账授权</a></li>
						<li class=""><a data-toggle="tab" href="#lB6">6单笔业务查询</a></li>
						<li class=""><a data-toggle="tab" href="#lB7">7转账确认</a></li>
						<li class=""><a data-toggle="tab" href="#lB8">8取消自动投标授权</a></li>
						<li class=""><a data-toggle="tab" href="#lB9">9取消自动还款授权</a></li>
						<li class=""><a data-toggle="tab" href="#lB10">10代扣充值</a></li>
						<li class=""><a data-toggle="tab" href="#lB11">11平台信息</a></li>
						<li class=""><a data-toggle="tab" href="#lB12">12项目标的查询</a></li>
					</ul>
					<div class="tab-content">
						<div id="lB1" class="tab-pane">
							<h3>1账户查询明细</h3><hr/>
						</div>
						<div id="lB2" class="tab-pane">
							<h3>2资金冻结明细</h3><hr/>
						</div>
						<div id="lB3" class="tab-pane active">
							<h3>3资金解冻明细</h3><hr/>
						</div>
						<div id="lB4" class="tab-pane">
							<h3>4直接转账明细</h3><hr/>
						</div>
						<div id="lB5" class="tab-pane">
							<h3>5自动转账授权明细</h3><hr/>
						</div>
						<div id="lB6" class="tab-pane active">
							<h3>6单笔业务查询明细</h3><hr/>
						</div>
						<div id="lB7" class="tab-pane">
							<h3>7转账确认明细</h3><hr/>
						</div>
						<div id="lB8" class="tab-pane">
							<h3>8取消自动投标授权明细</h3><hr/>
						</div>
						<div id="lB9" class="tab-pane active">
							<h3>9取消自动还款授权明细</h3><hr/>
						</div>
						<div id="lB10" class="tab-pane">
							<h3>10代扣充值明细</h3><hr/>
						</div>
						<div id="lB11" class="tab-pane">
							<h3>11平台信息明细</h3><hr/>
						</div>
						<div id="lB12" class="tab-pane">
							<h3>12项目标的查询明细</h3><hr/>
						</div>
					</div>
				</div>
			
			</div>
		</div>
	</div>
</body>
</html>