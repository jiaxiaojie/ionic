<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/wytz/project_detail.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			$(function() {
				$("#cannotInvestment").click(function() {
					$.jBox.tip("此项目" + $(this).html());
				});
			});
		</script>
	</head>
	<body>
		<div id="top_area">
			<div class="title">
				<c:if test="${projectBaseInfo.projectTypeCode eq '1' }">
					<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_di.png" alt="">
				</c:if>
				<c:if test="${projectBaseInfo.projectTypeCode eq '2' }">
					<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_ge.png" alt="">
				</c:if>
				<c:if test="${projectBaseInfo.projectTypeCode eq '3' }">
					<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_shang.png" alt="">
				</c:if>
				<c:if test="${projectBaseInfo.projectTypeCode eq '4' }">
					<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zhi.png" alt="">
				</c:if>
				<c:if test="${projectBaseInfo.projectTypeCode eq '5' }">
					<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zu.png" alt="">
				</c:if>
				<c:if test="${projectBaseInfo.projectTypeCode eq '6' }">
					<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zi.png" alt="">
				</c:if>
				<img src="${ctxStatic}/modules/front/images/wytz/project_detail/line-zs.png"/>
				<span>${p2p:abbrev(projectBaseInfo.projectName,100)}</span>
				<c:if test="${projectBaseInfo.isNewUser eq '0' }"><div class="tag_newhand">新手专享</div></c:if>
			</div>
			<hr/>
			<div class="content">
				<div class="left">
					<div class="div_height_10"></div>
					<div class="div_line_1">
						<span class="span_text_1">标的总额（元）</span>
						<span class="span_text_2">年利率</span>
						<span class="span_text_3">还款期限（月）</span>
					</div>
					<div class="div_line_2">
						<span class="span_text_1">￥<fmt:formatNumber value="${projectBaseInfo.financeMoney }" pattern="#0.00" /></span>
						<span class="span_text_2">
							<span class="span_text_2_1">
								<c:choose>
									<c:when test="${projectBaseInfo.isIncreaseInterest eq '1' && projectBaseInfo.increaseInterestRate > 0 }">
								<fmt:formatNumber value="${(projectBaseInfo.annualizedRate - projectBaseInfo.increaseInterestRate) * 100 }" pattern="#.#" />+<fmt:formatNumber value="${projectBaseInfo.increaseInterestRate * 100 }" pattern="#.#" />
								
									</c:when>
									<c:otherwise>
								<fmt:formatNumber value="${projectBaseInfo.annualizedRate * 100 }" pattern="#.#" />
									</c:otherwise>
								</c:choose>
							</span>
							<span class="span_text_2_2">%</span>
							<c:if test="${projectBaseInfo.isIncreaseInterest eq '1' && projectBaseInfo.increaseInterestRate > 0 }">
								<span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="平台贴息<fmt:formatNumber value="${projectBaseInfo.increaseInterestRate }" type="percent" maxFractionDigits="1" />。"></span>
							</c:if>
						</span>
						<span class="span_text_3">${projectBaseInfo.projectDuration }</span>
					</div>
					<div class="div_height_40"></div>
					<div class="div_line_3">
						<span class="span_text_1">担保方式</span>
						<span class="span_text_2">
							<span class="span_text_2_1">${fns:getDictLabel(projectBaseInfo.safeguardMode, 'project_safeguard_mode_dict', '') }</span>
						</span>
						<span class="span_text_3">起投金额（元）</span>
						<span class="span_text_4">
							<span class="span_text_4_1"><fmt:formatNumber value="${projectBaseInfo.startingAmount}" pattern="#0" /></span>
						</span>
					</div>
					<div class="div_line_4">
						<span class="span_text_1">还款方式</span>
						<span class="span_text_2">
							<span class="span_text_2_1">${fns:getDictLabel(projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}</span>
						</span>
						<c:choose>
							<c:when test="${projectBaseInfo.repaymentMode eq '3' }">
							<span class="span_text_3">月还本息（元）</span>
							<span class="span_text_4"><fmt:formatNumber value="${projectBaseInfo.monthRepayMoney }" pattern="#0.00" /></span>
							</c:when>
							<c:otherwise>
							<span class="span_text_3">发布时间</span>
							<span class="span_text_4"><fmt:formatDate value="${projectBaseInfo.publishDt }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="div_height_50"></div>
					<div class="div_line_5">
						<c:if test="${projectBaseInfo.projectStatus eq '3' }">
						<span class="span_text_1">投标进度</span>
						<div class="div_progress_1">
							<progress class="progress_1" value="${projectExecuteSnapshot.endFinanceMoney }" max="${projectBaseInfo.financeMoney }"></progress>
						</div>
						<span class="span_text_1"><fmt:formatNumber value="${projectExecuteSnapshot.endFinanceMoney / projectBaseInfo.financeMoney }" type="percent" maxFractionDigits="1" /></span>
						<span class="span_text_2">剩余时间<span class="number">${remainDay }</span>天<span class="number">${remainHour }</span>时<span class="number">${remainMinute }</span>分</span>
						</c:if>
					</div>
				</div>
				<div class="right">
					<div class="div_height_30"></div>
					<c:choose>
						<c:when test="${projectBaseInfo.projectStatus eq '3' }">
					<div class="div_line_1">
						<span class="span_text_1">剩余金额（元）</span></div>
					<div class="div_line_2">
						<input type="hidden" id="lack_project_amount" value="<fmt:formatNumber value="${projectBaseInfo.financeMoney - projectExecuteSnapshot.endFinanceMoney }" pattern="#0.00" />" />
						<span class="span_text_1">￥<fmt:formatNumber value="${projectBaseInfo.financeMoney - projectExecuteSnapshot.endFinanceMoney }" pattern="#0.00" /></span>
					</div>
					<div class="div_height_10"></div>
							<c:choose>
							    <c:when test="${not empty p2p:getPrincipal()}">
					<div class="div_line_3">
						<span class="span_text_1">账户可用余额：<fmt:formatNumber value="${customerBalance.goldBalance - customerBalance.congealVal + customerBalance.platformAmount }" pattern="#0.00" />元</span>
						<span class="span_text_2"><a target="_blank" href="${ctxFront }/customer/capital/recharge">充值</a></span>
					</div>
					<div class="div_line_4">
						<input class="text_input" type="text" id="amount"  name="amount"  maxLength="10" placeholder="请输入金额，<fmt:formatNumber value="${projectBaseInfo.startingAmount }" pattern="#0" />元起投"/>
					</div>
					<div id="error" style='color:red;text-align:center;'></div>
					<div class="div_height_5"></div>
					<div class="div_line_5">
						<c:choose>
								<c:when test="${projectBaseInfo.showTermList.indexOf('0') != -1 }">
						<a id="bt_submit_invest">立即投资</a>
								</c:when>
								<c:otherwise>
						<a id="cannotInvestment">
							移动端专享
						</a>
								</c:otherwise>
						</c:choose>
					</div>
							    </c:when>
							    <c:otherwise>
			        <div class="div_height_50"></div>
					<div id="error" style='color:red;text-align:center;'></div>
					<div class="div_height_5"></div>
					<div class="div_line_5"><a href="${ctxFront}/login">请先登录</a></div>
							   </c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
					<div class="div_height_50"></div>
					<div id="error" style='color:red;text-align:center;'></div>
					<div class="div_height_5"></div>
					<div class="div_line_3 yhk clearfix">已还款金额：<span><fmt:formatNumber value="${projectExecuteSnapshot.endRepayMoney }" pattern="#0.00" />元</span></div>
							<c:if test="${projectExecuteSnapshot.remainingTime > 0 }">
					<div class="div_line_3 syqx clearfix">剩余期限：<span>${projectExecuteSnapshot.remainingTime }个月</span></div>
					<div class="div_line_3 hkr clearfix">下一还款日：<span><fmt:formatDate value="${nextRepayDay }" pattern="yyyy-MM-dd"/></span></div>
							</c:if>
						</c:otherwise>
					</c:choose>
					
				</div>
			</div>
			<hr/>
			<div class="info">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${p2p:abbrev(projectBaseInfo.projectIntroduce,350)}
			</div>
		</div>
		<div class="div_height_10"></div>
		<div class="bg_980_top" style="margin:0 auto 0 auto"></div>
		<div class="bg_980_middle" style="margin:0 auto 0 auto">
			<div class="div_height_10"></div>
			<div id="tab_bar" style="padding-left:20px;">
				<span id="item_1" class="item"><span class="selected">项目信息</span></span>
				<span id="item_2" class="item"><span>风控信息</span></span>
				<span id="item_3" class="item"><span>相关文件</span></span>
				<span id="item_4" class="item"><span>还款计划</span></span>
				<span id="item_5" class="item"><span>项目历程</span></span>
				<span id="item_6" class="item"><span>投资列表</span></span>
			</div>
			<!-- 项目信息 -->
			<div id="project_info">
				<div class="div_height_5"></div>
				<div class="line_2">
					<span class="span_text_1">融资方</span>
					<span class="span_text_2">${p2p:vagueName(borrowersName) }</span>
					<span class="span_text_3">年化利率</span>
					<span class="span_text_4"><fmt:formatNumber value="${projectBaseInfo.annualizedRate }" type="percent" maxFractionDigits="1" /></span>
					<span class="span_text_5">融资金额</span>
					<span class="span_text_6"><fmt:formatNumber value="${projectBaseInfo.financeMoney }" pattern="#0.00" />元</span>
				</div>
				<div class="line_3">
					<span class="span_text_1">项目区域</span>
					<span class="span_text_2">${projectBaseInfo.area.name}</span>
					<span class="span_text_3">借款用途</span>
					<span class="span_text_4">${projectBaseInfo.useMethod }</span>
					<span class="span_text_5">投资截止日期</span>
					<span class="span_text_6"><fmt:formatDate value="${projectBaseInfo.biddingDeadline }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</div>
				<%-- <div class="line_3">
					<span class="span_text_1">计划还款日期</span>
					<span class="span_text_2"><fmt:formatDate value="${projectBaseInfo.plannedRepaymentDate }" pattern="yyyy-MM-dd"/></span>
					<span class="span_text_3">实际还款日期（结束后）</span>
					<span class="span_text_4"><fmt:formatDate value="${projectBaseInfo.actualRepaymentDate }" pattern="yyyy-MM-dd"/></span>
					<span class="span_text_5"></span>
					<span class="span_text_6"></span>
				</div> --%>
				<div class="line_4">
					<span class="span_text_1">债权转让限制</span>
					<span class="span_text_2">
						<c:choose>
							<c:when test="${projectBaseInfo.transferCode eq -1}">
						此项目不允许债权转让
							</c:when>
							<c:when test="${projectBaseInfo.transferCode eq 0}">
						无限制
							</c:when>
							<c:otherwise>
						此项目在${projectBaseInfo.transferCode }天后允许债权转让
							</c:otherwise>
						</c:choose>
					</span>
				</div>
				<div class="line_dotted"></div>
				<%-- <c:if test="${not empty projectFactorCarFlow }">
				<div class="line_4">
					<span class="span_text_1">车辆品牌型号</span>
					<span class="span_text_2">${projectFactorCarFlow.carModel }</span>
				</div>
				<div class="line_4">
					<span class="span_text_1">购买价格</span>
					<span class="span_text_2"><fmt:formatNumber value="${projectFactorCarFlow.carPrice }" pattern="#0.00" />元</span>
				</div>
				<div class="line_4">
					<span class="span_text_1">购买历史</span>
					<span class="span_text_2">${projectFactorCarFlow.degreesDepreciation }</span>
				</div>
				</c:if> --%>
				<div class="project_info_bottom"></div>
			</div>
			<!-- 风控信息 -->
			<div id="risk_control_info" class="hide">
				<div>${projectBaseInfo.riskInfo}</div>
			</div>
			<!-- 相关文件 -->
			<div id="file_info" class="hide">
				<div class="controls">
					<input type="hidden" id="aboutFiles" name="aboutFiles" value="${projectBaseInfo.aboutFiles }"  maxlength="200" class="input-xlarge"/>
					<sys:ckfinder input="aboutFiles" type="images" canOpenImg="true" readonly="true" uploadPath="/customer" selectMultiple="true"/>
				</div>
			</div>
			<!-- 还款计划 -->
			<div id="repayment_plan" class="hide">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>期次</th>
							<th>收款日</th>
							<th>应收本息（元）</th>
							<th>应收本金（元）</th>
							<th>应收利息（元）</th>
							<th>剩余本金（元）</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="projectRepaymentPlan" items="${projectRepaymentPlanList }" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td><fmt:formatDate value="${projectRepaymentPlan.planDate }" pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatNumber value="${projectRepaymentPlan.planMoney }" pattern="#0.00" /></td>
							<td><fmt:formatNumber value="${projectRepaymentPlan.principal }" pattern="#0.00" /></td>
							<td><fmt:formatNumber value="${projectRepaymentPlan.interest }" pattern="#0.00" /></td>
							<td><fmt:formatNumber value="${projectRepaymentPlan.remainingPrincipal }" pattern="#0.00" /></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 项目历程 -->
			<div id="project_process" class="project_process hide">
				<div class="row_01 item">
					<c:choose>
						<c:when test="${not empty projectDateNode.onLineDt }">
					<i class="blue"></i>
						</c:when>
						<c:otherwise>
					<i class="gray"></i>
						</c:otherwise>
					</c:choose>
					<span class="span_01">项目上线</span>
					<span class="span_02"><fmt:formatDate value="${projectDateNode.onLineDt }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</div>
				<div class="row_02 item">
                	<c:choose>
						<c:when test="${not empty projectDateNode.onLineDt }">
					<i class="blue"></i>
						</c:when>
						<c:otherwise>
					<i class="gray"></i>
						</c:otherwise>
					</c:choose>
					<span class="span_01">募资开始</span>
					<span class="span_02"><fmt:formatDate value="${projectDateNode.startFundingDt }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</div>
				<div class="row_03 item">
                	<c:choose>
						<c:when test="${not empty projectDateNode.endFundingDt }">
					<i class="blue"></i>
						</c:when>
						<c:otherwise>
					<i class="gray"></i>
						</c:otherwise>
					</c:choose>
					<span class="span_01">募资结束</span>
					<span class="span_02"><fmt:formatDate value="${projectDateNode.endFundingDt }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</div>
				<div class="row_04 item last">
                	<c:choose>
						<c:when test="${not empty projectDateNode.finishRepayDt }">
					<i class="blue"></i>
						</c:when>
						<c:otherwise>
					<i class="gray"></i>
						</c:otherwise>
					</c:choose>
					<span class="span_01">还款结束</span>
					<span class="span_02"><fmt:formatDate value="${projectDateNode.finishRepayDt }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</div>
			</div>
			<!-- 投资列表 -->
			<div id="investment_list" class="hide">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>时间</th>
							<th>用户</th>
							<th>投资金额（元）</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="projectInvestmentRecord" items="${projectInvestmentRecordList }">
						<tr>
							<td><fmt:formatDate value="${projectInvestmentRecord.opDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${projectInvestmentRecord.ca.accountName }</td>
							<td><fmt:formatNumber value="${projectInvestmentRecord.amount }" pattern="#0.00" /></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="bg_980_bottom" style="margin:0 auto 0 auto"></div>
		<div class="div_height_20"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#tab_bar span').click(function(){
					if ($(this).children("span").hasClass("selected")) {
						return ;
					}
					$("#tab_bar span").each(function(){
				        if($(this).children("span").hasClass("selected")){
				            $(this).children("span").removeClass("selected");
				        }
				    });
					$(this).children("span").addClass("selected");
					if ($('#item_1 span').hasClass("selected")){
						$('#project_info').show();
						$('#risk_control_info').hide();
						$('#file_info').hide();
						$('#repayment_plan').hide();
						$('#project_process').hide();
						$('#investment_list').hide();
					} else if ($('#item_2 span').hasClass("selected")){
						$('#project_info').hide();
						$('#risk_control_info').show();
						$('#file_info').hide();
						$('#repayment_plan').hide();
						$('#project_process').hide();
						$('#investment_list').hide();
					} else if ($('#item_3 span').hasClass("selected")){
						$('#project_info').hide();
						$('#risk_control_info').hide();
						$('#file_info').show();
						$('#repayment_plan').hide();
						$('#project_process').hide();
						$('#investment_list').hide();
					} else if ($('#item_4 span').hasClass("selected")){
						$('#project_info').hide();
						$('#risk_control_info').hide();
						$('#file_info').hide();
						$('#repayment_plan').show();
						$('#project_process').hide();
						$('#investment_list').hide();
					} else if ($('#item_5 span').hasClass("selected")){
						$('#project_info').hide();
						$('#risk_control_info').hide();
						$('#file_info').hide();
						$('#repayment_plan').hide();
						$('#project_process').show();
						$('#investment_list').hide();
					} else if ($('#item_6 span').hasClass("selected")){
						$('#project_info').hide();
						$('#risk_control_info').hide();
						$('#file_info').hide();
						$('#repayment_plan').hide();
						$('#project_process').hide();
						$('#investment_list').show();
					}
				});
				
		    	//立即投资
		    	$(document).on('click', '#bt_submit_invest', function() {
					var amount = $("#amount").val();
					var lackProjectAmount = $("#lack_project_amount").val();
					var startingAmount = ${projectBaseInfo.startingAmount};
					var maxAmount = ${projectBaseInfo.maxAmount };
					var isNewUser = ${isNewUser};
					var error = $('#error').text();
					if (amount == '') {
						$('#error').html("请输入投资金额！");
					} else if (error != '' && isNaN(amount)) {
						$('#error').html("金额必须为数字！");
					} else if (parseFloat(amount) <= 0){
						$('#error').html("金额必须大于0！");
					} else if (error != '' && parseFloat(amount) < parseFloat(startingAmount) && parseFloat(lackProjectAmount) > parseFloat(startingAmount)) {
						$('#error').html("金额不能小于起投金额" + startingAmount + "元！");
					} else if (error != '' && parseFloat(amount) > parseFloat(lackProjectAmount)) {
						$('#error').html("金额不能大于剩余金额" + investAmount + "元！");
					} else if (error != '' && parseFloat(amount) > parseFloat(maxAmount)) {
						$('#error').html("金额不能大于最大投资金额" + maxAmount + "元！");
					} else {
						if(isNewUser == '0'){
							var data = {};
							data.accountId = ${accountId};
							$.get("${ctxFront}/customer/investment/checkIsNewCustomer",data,function(isNewUser){
								if(!isNewUser){
									$('#error').html("新手专享，您不能投资！");
								}else{
									window.location.href = "${ctxFront}/project_buy?id=${projectBaseInfo.projectId}&type=1&amount=" + amount;
								} 
							});
						}else{
							window.location.href = "${ctxFront}/project_buy?id=${projectBaseInfo.projectId}&type=1&amount=" + amount;
						}
						
					}
				});
		    	
		    	//输入投资金额change事件
				$('#amount').change(function(){   
					var val = $(this).val();
					var investAmount = $("#lack_project_amount").val();
					var startingAmount = ${projectBaseInfo.startingAmount };
					var maxAmount = ${projectBaseInfo.maxAmount };
					if (isNaN(val)) {     
						$('#error').html("金额必须为数字！");  
					}else if ((parseFloat(investAmount) > parseFloat(startingAmount)) && parseFloat(val) < parseFloat(startingAmount)) {     
						$('#error').html("金额不能小于起投金额" + startingAmount + "元！");  
					}else if (parseFloat(val) > parseFloat(investAmount)) {  
						$('#error').html("金额不能大于剩余金额" + investAmount + "元！"); 
					}else if (parseFloat(val) > parseFloat(maxAmount)) {  
						$('#error').html("金额不能大于最大投资金额" + maxAmount + "元！"); 
					} else {
						$('#error').html("");
					}  
				});
				//限制只能输入金额
				limit_amount_input('amount');
				$(function(){
					var investAmount = $("#lack_project_amount").val();
					var startingAmount = ${projectBaseInfo.startingAmount };
					if (parseFloat(investAmount) <= parseFloat(startingAmount)) {
						$("#amount").val(investAmount);
						$("#amount").attr("readonly", "readonly");
					}
				});
			});
		</script>
	</body>
</html>