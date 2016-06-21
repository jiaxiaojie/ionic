<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="menu" value="wytz"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="front_with_juanzhou" />
<link href="${ctxStatic}/modules/front/css/wytz/project_detail.css?${version }"
	rel="stylesheet" />
<title></title>
</head>
<body>
	<input type="hidden" id="startingAmount" value="<fmt:formatNumber value="${cInfo.startingAmount }" pattern="#0.##" />">
	<input type="hidden" id="maxAmount" value="<fmt:formatNumber value="${cInfo.maxAmount }" pattern="#0.##" />">
	<input type="hidden" id="investable" value="<fmt:formatNumber value="${investable }" pattern="#0.##" />">
	<div id="top_area" class="current_finance_top">
		<div class="title current_title">
			<span><b>${cInfo.name }</b><em><fmt:formatNumber value="${cInfo.startingAmount }" pattern="#0.##" />元起投，随时存取，按日计息</em></span>
			<div class="tag_newhand tag_current">活期理财</div> 
		</div>
		<hr />
		<div class="content cf_content">
			
            <div class="left">
            	<div class="clearfix current_detail_item">
                	<p class="common one">
                    	<em>年化收益率</em>
                    	<span><fmt:formatNumber value="${cInfo.rate*100 }" pattern="#0.#" /><i>%</i></span>
                    </p>
                    <p class="two"></p>
                    <p class="common three">
                    	<em>每万元日收益</em>
                    	<span class="gray-text"><fmt:formatNumber value="${everyWanDayEarnings }" pattern="#0.00" /><i>元</i></span>
                    </p>
                </div>
            	<div class="clearfix current_detail_item">
                	<p class="common one">
                    	<em>收益起始日</em>
                    	<span class="gray-text">T+0<i>（工作日）</i></span>
                    </p>
                    <p class="two"></p>
                    <p class="common three">
                    	<em>剩余金额（元）</em>
                    	<span class="gray-text">￥<fmt:formatNumber value="${cInfo.financeMoney- cInfo.snapshot.hasFinancedMoney}" pattern="#0.00" /></span>
                    </p>
                </div>
                
                <c:if test="${cInfo.status eq '3' }">
            	<div class="clearfix current_detail_item">
                	<p class="common one">
                    	<em>项目进度</em>
                    	<span class="clearfix item-progress">
							<progress class="progress_1 pull-left" value="${cInfo.snapshot.hasFinancedMoney}" max="${cInfo.financeMoney }"></progress>
							<i class="pull-left"><fmt:formatNumber value="${cInfo.snapshot.hasFinancedMoney/cInfo.financeMoney*100}" pattern="#0.#" />%</i>
						</span>
                    </p>
                    <p class="two"></p>
                    <p class="common three">
                    	<c:choose>
                        	<c:when test="${cInfo.status eq '3' }">
                    		<em>剩余时间</em>
                    		<span class="gray-text" style="font-size:13px;">
                            	<span class="number gray-text" style="font-size:13px;">${remainDay }</span>天
                                <span class="number gray-text" style="font-size:13px;">${remainHour }</span>时
                                <span class="number gray-text" style="font-size:13px;">${remainMinute }</span>分
                            </span>
                            </c:when>
                            <c:when test="${cInfo.status eq '4' or  cInfo.status eq '5'}">已结束</c:when>
                        </c:choose>
                    </p>
                </div>
                </c:if>
                				
            </div>
            
            <div class="right">
				<div class="div_height_30"></div>
				<c:choose>
						<c:when test="${cInfo.status eq '3' }">
				<div class="div_line_1">
					<span class="span_text_1">我的可购买额度（元）<span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="每位用户总共可认购10万元活期产品" data-original-title="" title=""></span></span>
				</div>
				<div class="div_line_2">
					<input type="hidden" id="lack_project_amount" value="<fmt:formatNumber
							value="${cInfo.financeMoney- cInfo.snapshot.hasFinancedMoney}"
							pattern="#0.##" />">
					<span class="span_text_1">￥<fmt:formatNumber value="${investable==null?100000:investable}" pattern="#0.##" /></span>
				</div>
				<div class="div_height_10"></div>
				<c:choose>
					<c:when test="${not empty p2p:getPrincipal()}">
						
						<div class="div_line_3">
							<span class="span_text_1">账户可用余额：<fmt:formatNumber value="${customerBalance.goldBalance - customerBalance.congealVal + customerBalance.platformAmount }" pattern="#0.00" />元</span> <span
								class="span_text_2"><a target="_blank"
								href="${ctxFront}/customer/capital/recharge" class="brown-text">充值</a></span>
						</div>
						<div class="div_line_4">
							<input class="text_input" type="text" id="amount" name="amount"
								maxlength="10" placeholder="请输入金额，<fmt:formatNumber value="${cInfo.startingAmount }" pattern="#0.##" />元起投">
						</div>
						<div id="error" style="color: red; text-align: center;"></div>
						<div class="earnings_tip">
							每日收益：<span id="everydayEarnings">0.00</span>元
						</div>
						<div class="div_line_5">
							<c:if test="${cInfo.isAutoRepay==1 }">
								<a  id="bt_submit_invest">立即投资</a>
							</c:if>
							<c:if test="${cInfo.isAutoRepay != 1 }">
								<a  class="bt_gray_210x43" >立即投资</a>
							</c:if>
						</div>
						
					</c:when>
					<c:otherwise>
						<div class="div_height_50"></div>
						<div id="error" style='color: red; text-align: center;'></div>
						<div class="div_height_5"></div>
						<div class="div_line_5">
							<a href="${ctxFront}/login">请先登录</a>
						</div>
					</c:otherwise>
					</c:choose>
					</c:when>
					<c:otherwise>
					<div class="div_height_50"></div>
					<div id="error" style='color:red;text-align:center;'></div>
					<div class="div_height_5"></div>
					<div class="div_line_3 yhk clearfix">已融资金额：<span><fmt:formatNumber value="${cInfo.snapshot.hasFinancedMoney }" pattern="#0.00" />元</span></div>
					<div class="div_line_3 syqx clearfix">已产生利息：<span><fmt:formatNumber value="${cInfo.snapshot.hasRepaidMoney }" pattern="#0.00" />元</span></div>
					</c:otherwise>				
				</c:choose>
			</div>
            
		</div>

	</div>
	
	  <div class="current_statistics">
            <ul class="clearfix">
                <li class="pull-left">累计投资总额：<span><fmt:formatNumber value="${cInfo.snapshot.hasFinancedMoney}" pattern="#0.00" /></span> 元</li>
                <li class="pull-left text-center">成功购买次数：<span>${buyCount }</span></li>
                <li class="pull-left text-right mr10">累计已赚：<span><fmt:formatNumber value="${cInfo.snapshot.hasRepaidMoney }" pattern="#0.00" /></span> 元</li>
            </ul>
        </div>     
	
	<div class="bg_980_top" style="margin: 0 auto 0 auto"></div>
	<div class="bg_980_middle" style="margin: 0 auto 0 auto">
		<div class="div_height_10"></div>
		<div id="tab_bar" style="padding-left: 20px;">
			<span id="item_1" class="item"><span class="selected">项目信息</span></span>
			<span id="item_2" class="item"><span>常见问题</span></span>
		</div>

		<!-- 项目信息 -->
		<div id="project_info">
			<div class="div_height_5"></div>

			<div class="clearfix cf_info">
				<div class="pull-left cf_info_list">
					<div class="clearfix line_2">
						<span class="span_text_1">项目简介</span> <span class="span_text_2">${cInfo.introduce }</span>
					</div>
					<div class="clearfix line_2">
						<span class="span_text_1">投资范围</span> <span class="span_text_2">${cInfo.investmentScope }</span>
					</div>
					<div class="clearfix line_2">
						<span class="span_text_1">期限</span> <span class="span_text_2">${cInfo.duration }</span>
					</div>
					<div class="clearfix line_2">
						<span class="span_text_1">预期年化收益率</span> <span class="span_text_2"><fmt:formatNumber value="${cInfo.rate*100 }" pattern="#0.#" />%</span>
					</div>
					<div class="clearfix line_2">
						<span class="span_text_1">起投金额</span> <span class="span_text_2"><fmt:formatNumber value="${cInfo.startingAmount }" pattern="#0.##" />元</span>
					</div>
					<div class="clearfix line_2">
						<span class="span_text_1">购买规则</span> <span class="span_text_2">${fns:toHtml(cInfo.buyRule) }</span>
					</div>
					
					<div class="clearfix line_2">
						<span class="span_text_1">计息规则</span> <span class="span_text_2">${fns:toHtml(cInfo.interestCalculateRule ) }</span>
					</div>
					<div class="clearfix line_2">
						<span class="span_text_1">赎回规则</span> <span class="span_text_2">${fns:toHtml(cInfo.redeemRule ) }</span>
					</div>
					<div class="clearfix line_2">
						<span class="span_text_1">费用</span> <span class="span_text_2">加入费用：0元；退出费用：0元</span>
					</div>
					<div class="clearfix line_2" style="margin-left: 26px; margin-top: 5px;">
						<span class="span_text_1">活花生投资协议</span><span class="span_text_2"><a href="${ctxFront}/agreement/currentInvestment?projectId=${cInfo.id}"  target="_blank"  class="brown-text">点击查看</a></span>
					</div>
				</div>
				
			</div>

			<div class="line_dotted"></div>
			<div class="project_info_bottom"></div>
		</div>

		<!-- 投资列表 -->
		<div id="investment_list" class="hide">
			<div class="pull-right cf_info_qa">
					
					<div class="qa_list">
						<ul>
							<li>
								<p><strong>1.收益是如何计算的？</strong></p>
								<p>活花生的收益每日结算，投资当日开始计息，每日利息在次日结算。<br>每日收益 =计息金额*昨日预期年化收益率/365<br>举例：您当日投资10000元，预期年化收益率为6.8%，代入计算公式，您的当日收益为：1.86元。</p>
							</li>
							<li>
								<p><strong>2.什么时候可以看到收益？</strong></p>
								<p>一般情况下，您可以在投资后的第二日凌晨看到收益。由于当日收益次日到账，您当日看到收益为前一日收益。</p>
							</li>
							<li>
								<p><strong>3.每次加入活花生时的可购买额度是怎么得出的？</strong></p>
								<p>系统默认每人加入活花生总金额不得超过100,000元，主要是为了公平的向每位用户提供投资机会。每次加入活花生的最大可购买额度为：100,000元减去您当前的活花生投资总额。</p>
							</li>
							<li>
								<p><strong>4.赎回到账户有限制吗？</strong></p>
								<p>活花生支持随时发起赎回，每日不限赎回次数，但是赎回金额必须为1元的整数倍，每人每天赎回限额5万元，赎回的资金当日没有利息。</p>
							</li>
							<li>
								<p><strong>5.申请赎回的资金什么时间可以到账？</strong></p>
								<p>系统在您提出赎回申请后，资金预计于半小时内入账您的账户。</p>
							</li>
							<li>
								<p><strong>6.赎回的资金会到哪里？</strong></p>
								<p>赎回成功后，资金会回到用户的花生金服可用余额中。</p>
							</li>
							<li>
								<p><strong>7.我什么时候可以提取收益？收益会自动发放吗？</strong></p>
								<p>您每日的收益在每日凌晨00:00计算，随后会显示在可提取收益中。收益需要您发起提取才会发放，只要可提取收益大于0元时都可以发起提取收益，不受时间限制。</p>
							</li>
							<li>
								<p><strong>8.活花生中的借款项目有何安全保障？</strong></p>
								<p>活花生中的借款项目100%适用于平台风险备用金制度。</p>
							</li>
							<li>
								<p><strong>9.花生金服投资组合的分散投资策略是什么？</strong></p>
								<p>活花生秉承组合投资的原则，协同合作机构，选取多种无风险关联的资产项目，进行平台系统匹配，保证用户投资收益。</p>
							</li>
						</ul>
					</div>
				</div>
		</div>

	</div>
	<div class="bg_980_bottom" style="margin: 0 auto 0 auto"></div>
	<div class="div_height_20"></div>

	<script type="text/javascript">
	function amountChangeMonitor() {
		
		if($("#amount").length==0){
			return;
		}
		var val = $("#amount").val();
		
		
		
		var investAmount = $(
				"#lack_project_amount")
				.val();
		var investable = $("#investable").val();
		
		var startingAmount = $("#startingAmount").val();
		var maxAmount = $("#maxAmount").val();
		$(".earnings_tip").attr("style","display:none;");
		if (isNaN(val)) {
			$('#error')
					.html("金额必须为数字！");
		} else if (!/^[0-9]+.?[0]*$/.test(val) && val != "") {
			$('#error')
			.html("金额必须为整数");
		} else if ((parseFloat(investAmount) > parseFloat(startingAmount))
				&& parseFloat(val) < parseFloat(startingAmount)) {
			$('#error')
					.html(
							"金额不能小于起投金额"
									+ startingAmount
									+ "元！");
		} else if (parseFloat(val) > parseFloat(investAmount)) {
			$('#error')
					.html(
							"金额不能大于剩余金额"
									+ investAmount
									+ "元！");
		} else if (parseFloat(val) > parseFloat(maxAmount)) {
			$('#error').html(
					"金额不能大于最大投资金额"
							+ maxAmount
							+ "元！");
		}  else if (parseFloat(val) > parseFloat(investable)) {
			$('#error').html(
					"金额不能大于还可投资金额"
							+ investable
							+ "元！");
		}else {
			$('#error').html("");
			
			var rate = ${cInfo.rate};
			var DAYS_IN_YEAR = ${DAYS_IN_YEAR};
			$("#everydayEarnings").text((val*rate/DAYS_IN_YEAR).toFixed(2));
			$(".earnings_tip").attr("style","");
		}
	};
	
	
		$(document)
				.ready(
						function() {
							
							

							
							$('#tab_bar span')
									.click(
											function() {
												if ($(this).children("span")
														.hasClass("selected")) {
													return;
												}
												$("#tab_bar span")
														.each(
																function() {
																	if ($(this)
																			.children(
																					"span")
																			.hasClass(
																					"selected")) {
																		$(this)
																				.children(
																						"span")
																				.removeClass(
																						"selected");
																	}
																});
												$(this).children("span")
														.addClass("selected");
												if ($('#item_1 span').hasClass(
														"selected")) {
													$('#project_info').show();
													$('#investment_list')
															.hide();
												} else if ($('#item_2 span')
														.hasClass("selected")) {
													$('#project_info').hide();
													$('#investment_list')
															.show();
												}
											});

							//立即投资
							$(document)
									.on(
											'click',
											'#bt_submit_invest',
											function() {
												var amount = $("#amount").val();
												var lackProjectAmount = $(
														"#lack_project_amount")
														.val();
												var investable = $("#investable").val();
												
												var startingAmount = $("#startingAmount").val();
												var maxAmount = $("#maxAmount").val();
												var isNewUser = 1;
												var error = $('#error').text();
												
												var flagHasError = true;
												
												if (amount == '') {
													$('#error')
															.html("请输入投资金额！");
												} else if (error != ''
														&& isNaN(amount)) {
													$('#error')
															.html("金额必须为数字！");
												} else if (parseFloat(amount) <= 0) {
													$('#error')
															.html("金额必须大于0！");
												} else if (!/^[0-9]+.?[0]*$/.test(amount)  && amount != "") {
													$('#error')
													.html("金额必须为整数");
												} else if (error != ''
														&& parseFloat(amount) < parseFloat(startingAmount)
														&& parseFloat(lackProjectAmount) > parseFloat(startingAmount)) {
													$('#error')
															.html(
																	"金额不能小于起投金额"
																			+ startingAmount
																			+ "元！");
												} else if (error != ''
														&& parseFloat(amount) > parseFloat(lackProjectAmount)) {
													$('#error')
															.html(
																	"金额不能大于剩余金额"
																			+ investAmount
																			+ "元！");
												} else if (error != ''
														&& parseFloat(amount) > parseFloat(maxAmount)) {
													$('#error').html(
															"金额不能大于最大投资金额"
																	+ maxAmount
																	+ "元！");
												}   else if (parseFloat(amount) > parseFloat(investable)) {
													$('#error').html(
															"金额不能大于还可投资金额"
																	+ investable
																	+ "元！");
												} else {
													flagHasError = false;
													if (isNewUser == '0') {
														var data = {};
														data.accountId = 0;
														$
																.get(
																		"/f/customer/investment/checkIsNewCustomer",
																		data,
																		function(
																				isNewUser) {
																			if (!isNewUser) {
																				$(
																						'#error')
																						.html(
																								"新手专享，您不能投资！");
																			} else {
																				window.location.href = "/f/project_buy?id=1157&type=1&amount="
																						+ amount;
																			}
																		});
													} else {
														window.location.href = "${ctxFront}/current/currentProjectBuy?id=${cInfo.id}&amount="
																+ amount;
													}

												}
												
												if(flagHasError){
													$(".earnings_tip").attr("style","display:none;");
												}
											});
							
							
							amountChangeMonitor();
							
							

							//输入投资金额change事件
							$('#amount')
									.change(amountChangeMonitor);
							//限制只能输入金额
							limit_amount_input('amount');
							$(function() {
								var investAmount = $("#lack_project_amount")
										.val();
								var startingAmount = $("#startingAmount").val();
								if (parseFloat(investAmount) <= parseFloat(startingAmount)) {
									$("#amount").val(investAmount);
									$("#amount").attr("readonly", "readonly");
								}
							});
						});
	</script>

	</div>
	<div class="juanzhou_bottom"></div>
	</div>




	</div>



</body>
</html>