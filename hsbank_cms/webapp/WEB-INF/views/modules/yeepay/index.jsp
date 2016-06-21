<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>易宝测试入口</title>
<meta name="decorator" content="default" />
<script>
	function menuChange(ids) {
		var list = $("li[id^='menu']");
		for (var i = 0; i < list.length; i++) {
			var item = list[i];
			$('#' + item.id).attr("class", "");
		}
		$('#menu' + ids).attr("class", "active");
	}

	function indexChange(ids) {
		var list = $("li[id^='index']");
		for (var i = 0; i < list.length; i++) {
			var item = list[i];
			$('#' + item.id).attr("class", "");
		}
		if (ids == '1') {
			$('#tab1').show();
			$('#tab1').attr("class","active");
			$('#tab2').hide();
			$('#tab2').attr("class","");
		} else {
			$('#tab2').show();
			$('#tab2').attr("class","active");
			$('#tab1').hide();
			$('#tab1').attr("class","");
		}
		$('#index' + ids).attr("class", "active");
	}
	indexChange(1);

	function b1Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/account_info',
			data : {
				platformUserNo : $('#platformUserNoB1').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B1_result').val(data);
			}
		});

	}
	function b2Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/freeze',
			data : {
				platformUserNo : $('#platformUserNoB2').val(),
				amount : $('#amountB2').val(),
				expired : $('#expiredB2').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B2_result').val(data);
			}
		});
	}
	function b3Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/unfreeze',
			data : {
				freezeRequestNo : $('#freezeRequestNoB3').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B3_result').val(data);
			}
		});
	}
	function b4Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/direct_transaction',
			data : {
				platformUserNo : $('#freezeRequestNoB4').val(),
				requestNo : $('#requestNoB4').val(),
				userType : $('#userTypeB4').val(),
				bizType : $('#bizTypeNoB4').val(),
				d_amount : $('#d_amountB4').val(),
				d_targetUserType : $('#d_targetUserTypeB4').val(),
				d_targetPlatformUserNo : $('#d_targetPlatformUserNoB4').val(),
				d_bizType : $('#d_bizTypeB4').val(),
				notifyUrl : $('#notifyUrlB4').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B4_result').val(data);
			}
		});
	}
	function b5Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/auto_transcation',
			data : {
				platformUserNo : $('#freezeRequestNoB5').val(),
				requestNo : $('#requestNoB5').val(),
				userType : $('#userTypeB5').val(),
				bizType : $('#bizTypeNoB5').val(),
				d_amount : $('#d_amountB5').val(),
				d_targetUserType : $('#d_targetUserTypeB5').val(),
				d_targetPlatformUserNo : $('#d_targetPlatformUserNoB5').val(),
				d_bizType : $('#d_bizTypeB5').val(),
				notifyUrl : $('#notifyUrlB5').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B5_result').val(data);
			}
		});
	}
	function b6Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/query',
			data : {
				requestNo : $('#requestNoB6').val(),
				mode : $('#modeB6').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B6_result').val(data);
			}
		});
	}
	function b7Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/complete_transaction',
			data : {
				requestNo : $('#requestNoB7').val(),
				mode : $('#modeB7').val(),
				platformUserNo:$('#platformUserNoB7').val(),
				notifyUrl : $('#notifyUrlB7').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B7_result').val(data);
			}
		});
	}
	function b8Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/cancel_authorize_auto_transfer',
			data : {
				platformUserNo : $('#platformUserNoB8').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B8_result').val(data);
			}
		});
	}
	function b9Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/cancel_authorize_auto_repayment',
			data : {
				platformUserNo : $('#platformUserNoB9').val(),
				orderNo : $('#orderNoB9').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B9_result').val(data);
			}
		});
	}
	function b10Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/whdebitnocard_recharge',
			data : {
				platformUserNo : $('#platformUserNoB10').val(),
				payWay : $('#payWayB10').val(),
				amount : $('#amountB10').val(),
				feeMode : $('#feeModeB10').val(),
				realName : $('#realNameB10').val(),
				idCardNo : $('#idCardNoB10').val(),
				bankCardNo : $('#bankCardNoB10').val(),
				notifyUrl : $('#notifyUrlB10').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B10_result').val(data);
			}
		});
	}
	function b11Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/platform_info',
			data : {
				notifyUrl : $('#notifyUrlB11').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B11_result').val(data);
			}
		});
	}
	function b12Req() {
		$.ajax({
			type : 'post',
			url : '${ctx}/yeepay/direct/project_query',
			data : {
				orderNo : $('#orderNoB12').val()
			},
			dataType : 'text',
			success : function(data) {
				$('#B12_result').val(data);
			}
		});
	}
</script>
</head>
<body>
	<div class="tabbable">
		<!-- Only required for left/right tabs -->
		<ul class="nav nav-tabs">
			<li onclick='indexChange(1)' class="active" id="index1"><a
				href="#tab1">浏览器网关接口</a></li>
			<li onclick='indexChange(2)' id="index2"><a href="#tab2">直接接口</a></li>
		</ul>
		<hr>
		<div class="tab-content">
			<div class="tab-pane active" id="tab1">
				<div class="tabbable tabs-left">
					<ul class="nav nav-tabs">
						<li id="menu1_1" onclick="menuChange('1_1')" class="active"><a
							target="mainFrame1" href="${ctx}/yeepay/toRegister">1注册</a></li>
						<li id="menu1_2" onclick="menuChange('1_2')" class=""><a
							target="mainFrame1" href="${ctx}/yeepay/toRecharge">2充值</a></li>
						<li id="menu1_3" onclick="menuChange('1_3')" class=""><a
							target="mainFrame1" href="${ctx}/yeepay/toWithdraw">3提现</a></li>
						<li id="menu1_4" onclick="menuChange('1_4')" class=""><a
							target="mainFrame1" href="${ctx}/yeepay/toBindBankCard">4绑卡</a></li>
						<li id="menu1_5" onclick="menuChange('1_5')" class=""><a
							target="mainFrame1" href="${ctx}/yeepay/toUnbindBankCard">5取消绑卡</a></li>
						<li id="menu1_6" onclick="menuChange('1_6')" class=""><a
							target="mainFrame1"
							href="${ctx}/yeepay/toEnterpriseRegister">6企业用户注册</a></li>
						<li id="menu1_7" onclick="menuChange('1_7')" class=""><a
							target="mainFrame1" href="${ctx}/yeepay/toCpTransaction">7转账授权</a></li>
						<li id="menu1_8" onclick="menuChange('1_8')" class=""><a
							target="mainFrame1"
							href="${ctx}/yeepay/toAuthorizeAutoTransfer">8自动投标授权</a></li>
						<li id="menu1_9" onclick="menuChange('1_9')" class=""><a
							target="mainFrame1"
							href="${ctx}/yeepay/toAuthorizeAutoRepayment">9自动还款授权</a></li>
						<li id="menu1_10" onclick="menuChange('1_10')" class=""><a
							target="mainFrame1" href="${ctx}/yeepay/toResetPassword">10重置密码</a></li>
						<li id="menu1_11" onclick="menuChange('1_11')" class=""><a
							target="mainFrame1" href="${ctx}/yeepay/toResetMobile">11修改手机号</a></li>
						<li id="menu1_12" onclick="menuChange('1_12')" class=""><a
							target="mainFrame1" href="${ctx}/yeepay/toGetMoney">12用户转账至平台</a></li>
					</ul>
					<div class="tab-content">
						<iframe id="mainFrame1" width="100%" height="750" frameborder="no"
							scrolling="yes" style="overflow: visible; height: 590px;"
							src="${ctx}/yeepay/toRegister" name="mainFrame1">
						</iframe>
					</div>
				</div>
			</div>

			<div class="tab-pane " id="tab2">
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
						<div id="lB1" class="tab-pane active">
							<h3>1账户查询明细</h3>
							<hr />
							<form id="formB1" class="form-horizontal">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input type="text" id="platformNo1" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">平台会员编号：</label>
									<div class="controls">
										<input type="text" id="platformUserNoB1" value=""
											htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<input type="button" align="center" id="btnB1"
										onclick="b1Req()" value="查询 " class="btn btn-primary" />
								</div>
								<div class="control-group">
									<textarea rows="6" id="B1_result" style="width: 640px"></textarea>
								</div>
							</form>
						</div>
						<div id="lB2" class="tab-pane">
							<h3>2资金冻结明细</h3>
							<hr />
							<form id="formB2" class="form-horizontal">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input type="text" id="platformNo2" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">平台会员编号：</label>
									<div class="controls">
										<input type="text" id="platformUserNoB2" htmlEscape="false"
											maxlength="20" class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input type="text" id="requestNoB2" readonly="true"
											value="可以不填，后台自动填写" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">冻结金额：</label>
									<div class="controls">
										<input type="text" id="amountB2" htmlEscape="false"
											maxlength="20" class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">到期自动解冻：</label>
									<div class="controls">
										<input type="text" id="expiredB2" htmlEscape="false"
											maxlength="20" class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<input type="button" align="center" id="btnB2"
										onclick="b2Req()" value="查询 " class="btn btn-primary" />
								</div>
								<div class="control-group">
									<textarea rows="6" id="B2_result" style="width: 640px"></textarea>
								</div>
							</form>
						</div>
						<div id="lB3" class="tab-pane">
							<h3>3资金解冻明细</h3>
							<hr />
							<form id="formB3" class="form-horizontal">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input type="text" id="platformNo3" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">冻结时的请求流水号：</label>
									<div class="controls">
										<input type="text" id="freezeRequestNoB3" 
											value="" htmlEscape="false" maxlength="50"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<input type="button" align="center" id="btnB3"
										onclick="b3Req()" value="查询 " class="btn btn-primary" />
								</div>
								<div class="control-group">
									<textarea rows="6" id="B3_result" style="width: 640px"></textarea>
								</div>
							</form>
						</div>
						<div id="lB4" class="tab-pane">
							<h3>4直接转账明细</h3>
							<hr />
							<form id="formB4" class="form-horizontal">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input type="text" id="platformNo4" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input type="text" id="requestNoB4" readonly="true"
											value="可以不填" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">出款人用户编号：</label>
									<div class="controls">
										<input type="text" id="platformUserNoB4" readonly="true"
											htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">用户类型：</label>
									<div class="controls">
										<input type="text" id="userTypeB4" readonly="true"
											value="MERCHANT" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">bizType：</label>
									<div class="controls">
										<input type="text" id="bizTypeB4" readonly="true"
											value="TRANSFER" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">转入金额：</label>
									<div class="controls">
										<input type="text" id="d_amountB4" 
											value="" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">用户类型：</label>
									<div class="controls">
										<input type="text" id="d_targetUserTypeB4" readonly="true"
											value="MEMBER" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">平台用户编号：</label>
									<div class="controls">
										<input type="text" id="d_targetPlatformUserNoB4" 
											value="yangtao" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">资金明细业务类型：</label>
									<div class="controls">
										<input type="text" id="d_bizTypeB4" readonly="true"
											value="TRANSFER" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务器通知 URL：</label>
									<div class="controls">
										<input type="text" id="notifyUrlB4" readonly="true"
											value="" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								
								<div class="control-group">
									<input type="button" align="center" id="btnB4"
										onclick="b4Req()" value="赠送 " class="btn btn-primary" />
								</div>
								<div class="control-group">
									<textarea rows="6" id="B4_result" style="width: 640px"></textarea>
								</div>
						</form>
					</div>
					<div id="lB5" class="tab-pane">
						<h3>5自动转账授权明细</h3>
						<hr />
						<form id="formB5" class="form-horizontal">
								<div class="control-group">
									<label class="control-label">商户编号：</label>
									<div class="controls">
										<input type="text" id="platformNo5" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input type="text" id="requestNoB5" readonly="true"
											value="可以不填" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">出款人用户编号：</label>
									<div class="controls">
										<input type="text" id="platformUserNoB5" readonly="true"
											htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">用户类型：</label>
									<div class="controls">
										<input type="text" id="userTypeB5" readonly="true"
											value="MERCHANT" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">bizType：</label>
									<div class="controls">
										<input type="text" id="bizTypeB5" readonly="true"
											value="TRANSFER" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">转入金额：</label>
									<div class="controls">
										<input type="text" id="d_amountB5" 
											value="" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">用户类型：</label>
									<div class="controls">
										<input type="text" id="d_targetUserTypeB5" readonly="true"
											value="MEMBER" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">平台用户编号：</label>
									<div class="controls">
										<input type="text" id="d_targetPlatformUserNoB5" readonly="true"
											value="yangtao" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">资金明细业务类型：</label>
									<div class="controls">
										<input type="text" id="d_bizTypeB5" readonly="true"
											value="TRANSFER" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务器通知 URL：</label>
									<div class="controls">
										<input type="text" id="notifyUrlB5" readonly="true"
											value="" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								
								<div class="control-group">
									<input type="button" align="center" id="btnB5"
										onclick="b5Req()" value="查询 " class="btn btn-primary" />
								</div>
								<div class="control-group">
									<textarea rows="6" id="B5_result" style="width: 640px"></textarea>
								</div>
						</form>
					</div>
					<div id="lB6" class="tab-pane">
						<h3>6单笔业务查询明细</h3>
						<hr />
						<form id="formB6" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">商户编号：</label>
								<div class="controls">
									<input type="text" id="platformNo6" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">各业务的请求流水号：</label>
								<div class="controls">
									<input type="text" id="requestNoB6" value="" htmlEscape="false"
										 class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">查询模式：</label>
								<div class="controls">
									<select id="modeB6">
										<option value="WITHDRAW_RECORD">提现记录</option>
										<option value="RECHARGE_RECORD">充值记录</option>
										<option value="CP_TRANSACTION">转账记录</option> 
										<option value="FREEZERE_RECORD">冻结/解冻接口</option>
										</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<input type="button" align="center" id="btnB6" onclick="b6Req()"
									value="查询 " class="btn btn-primary" />
							</div>
							<div class="control-group">
								<textarea rows="6" id="B6_result" style="width: 640px"></textarea>
							</div>
						</form>
					</div>
					<div id="lB7" class="tab-pane">
						<h3>7转账确认明细</h3>
						<hr />
						<form id="formB7" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">商户编号：</label>
								<div class="controls">
									<input type="text" id="platformNo7" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">商户编号：</label>
								<div class="controls">
									<input type="text" id="platformUserNoB7" 
										value="yangtao" htmlEscape="false"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">请求流水号(同转账授权时设定)：</label>
								<div class="controls">
									<input type="text" id="requestNoB7" value="" htmlEscape="false"
										 class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">确认模式：</label>
								<div class="controls">
									<select id="modeB7">
										<option value="CONFIRM">确认</option>
										<option value="CANCEL">取消</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">服务器通知 URL：</label>
								<div class="controls">
									<input type="text" id="notifyUrlB7" value="" htmlEscape="false"
										maxlength="120" class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<input type="button" align="center" id="btnB7" onclick="b7Req()"
									value="查询 " class="btn btn-primary" />
							</div>
							<div class="control-group">
								<textarea rows="6" id="B7_result" style="width: 640px"></textarea>
							</div>
						</form>
					</div>
					<div id="lB8" class="tab-pane">
						<h3>8取消自动投标授权明细</h3>
						<hr />
						<form id="formB8" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">商户编号：</label>
								<div class="controls">
									<input type="text" id="platformNo8" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">平台会员编号：</label>
								<div class="controls">
									<input type="text" id="platformUserNoB8" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">请求流水号(同转账授权时设定？)：</label>
								<div class="controls">
									<input type="text" id="requestNoB8" value="" htmlEscape="false"
										maxlength="20" class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<input type="button" align="center" id="btnB8" onclick="b8Req()"
									value="查询 " class="btn btn-primary" />
							</div>
							<div class="control-group">
								<textarea rows="6" id="B8_result" style="width: 640px"></textarea>
							</div>
						</form>
					</div>
					<div id="lB9" class="tab-pane">
						<h3>9取消自动还款授权明细</h3>
						<hr />
						<form id="formB9" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">商户编号：</label>
								<div class="controls">
									<input type="text" id="platformNo9" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">平台会员编号：</label>
								<div class="controls">
									<input type="text" id="platformUserNoB9" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">请求流水号(同转账授权时设定？)：</label>
								<div class="controls">
									<input type="text" id="requestNoB9" value="" htmlEscape="false"
										maxlength="20" class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">标的号(同转账授权时设定？)：</label>
								<div class="controls">
									<input type="text" id="orderNoB9" value="" htmlEscape="false"
										maxlength="20" class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<input type="button" align="center" id="btnB9" onclick="b9Req()"
									value="查询 " class="btn btn-primary" />
							</div>
							<div class="control-group">
								<textarea rows="6" id="B9_result" style="width: 640px"></textarea>
							</div>
						</form>
					</div>
					<div id="lB10" class="tab-pane">
						<h3>10代扣充值明细</h3>
						<hr />
						<form id="formB10" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">商户编号：</label>
								<div class="controls">
									<input type="text" id="platformNo10" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
								<div class="control-group">
									<label class="control-label">请求流水号：</label>
									<div class="controls">
										<input type="text" id=requestNoB10 " readonly="true"
											htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">平台用户编号：</label>
									<div class="controls">
										<input type="text" id="platformUserNoB10" readonly="true"
											htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">代扣银行编码：</label>
									<div class="controls">
										<input type="text" id="payWayB10" readonly="true"
											htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">充值金额：</label>
									<div class="controls">
										<input type="text" id="amountB10" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">固定值：</label>
									<div class="controls">
										<input type="text" id="feeModeB10" readonly="true"
											htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">真实姓名：</label>
									<div class="controls">
										<input type="text" id="realNameB10" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">身份证号：</label>
									<div class="controls">
										<input type="text" id="idCardNoB10" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">验证通过的卡号：</label>
									<div class="controls">
										<input type="text" id="bankCardNoB10" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label"> 服务器通知 URL：</label>
									<div class="controls">
										<input type="text" id="notifyUrlB10" readonly="true"
											value="10012467598" htmlEscape="false" maxlength="20"
											class="input-xlarge required"
											style="height: 32px; width: 440px" />
									</div>
								</div>
								<div class="control-group">
									<input type="button" align="center" id="btnB10"
										onclick="b10Req()" value="查询 " class="btn btn-primary" />
								</div>
								<div class="control-group">
									<textarea rows="6" id="B10_result" style="width: 640px"></textarea>
								</div>
							</div>
						</form>
					</div>
					<div id="lB11" class="tab-pane">
						<h3>11平台信息明细</h3>
						<hr />
						<form id="formB11" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">商户编号：</label>
								<div class="controls">
									<input type="text" id="platformNo11" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">notifyUrl：</label>
								<div class="controls">
									<input type="text" id="notifyUrlB11" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<input type="button" align="center" id="btnB11"
									onclick="b11Req()" value="查询 " class="btn btn-primary" />
							</div>
							<div class="control-group">
								<textarea rows="6" id="B11_result" style="width: 640px"></textarea>
							</div>
						</form>
					</div>
					<div id="lB12" class="tab-pane">
						<h3>12项目标的查询明细</h3>
						<hr />
						<form id="formB12" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">商户编号：</label>
								<div class="controls">
									<input type="text" id="platformNo12" readonly="true"
										value="10012467598" htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">标的号：</label>
								<div class="controls">
									<input type="text" id="orderNoB12"
										 htmlEscape="false" maxlength="20"
										class="input-xlarge required"
										style="height: 32px; width: 440px" />
								</div>
							</div>
							<div class="control-group">
								<input type="button" align="center" id="btnB12"
									onclick="b12Req()" value="查询 " class="btn btn-primary" />
							</div>
							<div class="control-group">
								<textarea rows="6" id="B12_result" style="width: 640px"></textarea>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	</div>
</body>
</html>