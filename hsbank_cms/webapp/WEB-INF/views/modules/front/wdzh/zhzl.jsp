<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhzl.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var dateUtils = new DateUtils();
			$(function(){
				$("#today").html(dateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
			});
		</script>
	</head>
	<body>
		<div id="content_top" class="bg_white_789x216">
			<div class="left">
				<img id="avatar" class="img" src="${pageContext.request.contextPath}${avatar_image}" title="点击更换头像" alt="点击更换头像"/>
				<div class="vline"></div>
			</div>
			<div class="center">
				<div class="row_1">
					<span>
						<c:choose>
							<c:when test="${empty customerAccount.nickname }">
						${customerAccount.accountName }
							</c:when>
							<c:otherwise>
						${customerAccount.nickname }
							</c:otherwise>
						</c:choose>
					</span>
				</div>
				<div class="row_2">
					<span>账户资产&nbsp;&nbsp;<fmt:formatNumber value="${customerBalance.goldBalance + willPrincipal}" pattern="##0.00" /><span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="账户资产=可用余额+待收本金+冻结资产"></span></span>
				</div>
				<div class="row_3">
					<span>可用余额&nbsp;&nbsp;&nbsp;<fmt:formatNumber value="${customerBalance.goldBalance - customerBalance.congealVal }" pattern="##0.00" /></span>
					
				</div>
				<div class="row_4">
					<span>安全等级&nbsp;&nbsp;&nbsp;</span>
					<progress class="item-line-5" value="${safeScore }" max="100"></progress>
					<span><fmt:formatNumber value="${safeScore / 100 }" type="percent" maxFractionDigits="1" /></span>
				</div>
			</div>
			<div class="right">
				<div class="bt_orange_134x31"><a href="${ctxFront}/customer/capital/recharge">充&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值</a></div>
				<div class="div_height_20"></div>
				<div class="bt_brown_134x31"><a href="${ctxFront}/customer/capital/withdraw">提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现</a></div>
			</div>
		</div>
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div class="line_01">数据截止到<span id="today"></span></div>
			<div class="div_height_10"></div>
			<div class="line_04">
				<span class="span_text">账户余额</span>
				<span class="span_text">可用余额</span>
				
				<span class="span_text"><span style="float:left;">冻结资金</span><span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="平台采用第三方资金托管，投标成功后，资金将被冻结，冻结资金不能用于再次投资。项目满标后冻结资金将自动划转给借款方，并开始计息。如遇项目流标，冻结资金将退回用户账户。"></span></span>
			</div>
			<div class="line_05">
				<span class="span_text_1"><fmt:formatNumber value="${customerBalance.goldBalance }" pattern="##0.00" />元</span>
				<span class="span_text_2">=</span>
				<span class="span_text_1"><fmt:formatNumber value="${customerBalance.goldBalance - customerBalance.congealVal }" pattern="##0.00" />元</span>
				<span class="span_text_2">+</span>
				<span class="span_text_1"><fmt:formatNumber value="${customerBalance.congealVal }" pattern="##0.00" />元</span>
			</div>
			<hr/>
			<div class="line_02">
				<span class="span_text"><span style="float:left;">待收本金</span><span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="待收本金=定期投资+活期投资" data-original-title="" title=""></span></span>
				<span class="span_text">待收收益</span>
				<span class="span_text">累计收益</span>
			</div>
			<div class="line_03">
				<span class="span_text"><fmt:formatNumber value="${willPrincipal }" pattern="##0.00" />元</span>
				<span class="span_text"><fmt:formatNumber value="${customerBalance.willProfit }" pattern="##0.00" />元</span>
				<span class="span_text"><fmt:formatNumber value="${customerBalance.sumProfit }" pattern="##0.00" />元</span>
			</div>
			<hr/>
			<!-- 
			<div class="line_02">
				<span class="span_text">待还本金</span>
				<span class="span_text">待还利息</span>
				<span class="span_text">累计融资</span>
			</div>
			<div class="line_03">
				<span class="span_text"><fmt:formatNumber value="${customerBalance.willLoan }" pattern="##0.00" />元</span>
				<span class="span_text"><fmt:formatNumber value="${customerBalance.willLoan }" pattern="##0.00" />元</span>
				<span class="span_text"><fmt:formatNumber value="${customerBalance.willLoan }" pattern="##0.00" />元</span>
			</div>
			 -->
			<hr/>
			<div class="div_height_20"></div>
			
			<div class="bg_789_top"></div>
			<div id="tab_bar" style="padding-left:20px; margin-top:-1px;">
				<li id="item_1"  class="item"><span class="selected">我的债权</span></li>
				<li id="item_2"  class="item"><span>我的活花生</span></li>
				<!-- “详情”按钮根据选项卡标题而切换 -->
				<li class="pull-right">
					<a id="detail_info" href="${ctxFront}/customer/investment/project_cyz" class="blue-text" style=" margin:13px 20px 0 0; display:inline-block;">详情</a>
				</li>
			</div>
			
			<div id="my_investment_list" class="show">
			<table class="table table-hover" style="margin-left:20px;width:749px;margin-bottom:0px;">
	            <thead>
	                <tr>
	                    <th width="22%">项目名称</th>
	                    <th width="20%">投标日期</th>
	                    <th width="11%">项目状态</th>
	                    <th width="13%">中标额度</th>
	                    <th width="13%">预计收益</th>
	                    <th width="9%">是否转让</th>
	                </tr>
	            </thead>
	            <tbody>
					<c:forEach var="projectInvestmentRecord" items="${investmentProjectList }" varStatus="status">
						<c:if test="${status.index < 6 }">
					<tr>
						<td>${p2p:abbrev(projectInvestmentRecord.projectBaseInfo.projectName,10)}</td>
						<td><fmt:formatDate value="${projectInvestmentRecord.opDt }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${fns:getDictLabel(projectInvestmentRecord.projectBaseInfo.projectStatus, 'project_status_dict', '')}</td>
						<td><fmt:formatNumber value="${projectInvestmentRecord.amount }" pattern="##0.00" />元</td>
						<td><fmt:formatNumber value="${projectInvestmentRecord.willProfit }" pattern="##0.00" />元</td>
						<td>${fns:getDictLabel(projectInvestmentRecord.status, 'project_investment_status_dict', '')}</td>
					</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			</div>
			
			<!--这部分是“我的活花生”的内容-->
			<div id="my_current_peanut_list" class="hide">
			<table width="851" class="table table-hover" style="margin-left:20px;width:749px;margin-bottom:0px;">
	            <thead>
	                <tr>
	                    <th width="126">项目名称</th>
	                    <th width="210">投标日期</th>
	                    <th width="111">项目状态</th>
	                    <th width="181">持有金额（元）</th>
	                    <th width="97">年化利率</th>
	                </tr>
	            </thead>
	            <tbody>
	            	<c:forEach var="myPeanut" items="${myPeanutList }" varStatus="status">
						<c:if test="${status.index < 6 }">
						<tr>
							<td>${p2p:abbrev(myPeanut.pi.name,10)}</td>
							<td><fmt:formatDate value="${myPeanut.opDt }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${fns:getDictLabel(myPeanut.pi.status, 'current_project_status', '')}</td>
							<td><fmt:formatNumber value="${myPeanut.changeValue }" pattern="##0.00" /></td>
							<td><fmt:formatNumber value="${myPeanut.pi.rate}" type="number" pattern="0.0%" /></td>
						</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			</div>
			
			<div class="div_height_40"></div>
			<div class="bg_789_top"></div>
			<div class="div_height_10"></div>
			<div class="line_06">
				<span class="span_text_1">融资账户</span>
				<!-- 
				<span class="span_text_2">待还金额</span>
				<span class="span_text_3"><fmt:formatNumber value="${sumLoanInProject }" pattern="##0.00" /></span>
				<span class="span_text_4">元</span>
				 -->
				<span style="float:left;margin-left:30px;"><a style="color:#68a7e4;margin-right:10px;" href="${ctxFront}/customer/loan/myFundingProject">详情</a></span>
			</div>
			<hr class="hr_dashed"/>
			<table class="table table-hover" style="margin-left:20px;width:749px;margin-bottom:0px;">
	            <thead>
	                <tr>
	                    <th width="35%">借款名称</th>
	                    <th width="30%">待还本金</th>
	                    <th width="23%">待还利息</th>
	                    <th width="22%">逾期费用</th>
	                </tr>
	            </thead>
	            <tbody>
					<c:forEach var="projectRepaymentSplitRecord" items="${loanProjectList }" varStatus="status">
						<c:if test="${status.index < 6 }">
					<tr>
						<td>${p2p:abbrev(projectRepaymentSplitRecord.projectBaseInfo.projectName,10)}</td>
						<td><fmt:formatNumber value="${projectRepaymentSplitRecord.sumPrincipalShow }" pattern="##0.00" />元</td>
						<td><fmt:formatNumber value="${projectRepaymentSplitRecord.sumInterestShow }" pattern="##0.00" />元</td>
						<td>
							<c:choose>
								<c:when test="${empty projectRepaymentSplitRecord.sumPenaltyMoneyShow }">
							无
								</c:when>
								<c:otherwise>
							<fmt:formatNumber value="${projectRepaymentSplitRecord.sumPenaltyMoneyShow }" pattern="##0.00" />元
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
		<div class="div_height_10"></div>
		<div class="bg_789_bottom"></div>
		<div id="content_bottom" class="bg_789_middle">
			<div class="line_01">
				<span class="span_text_1">为您推荐：</span>
			</div>
			
			<table class="table table-hover" style="margin-left:20px;width:749px;margin-bottom:0px;">
	            <tbody>
					<c:forEach var="projectBaseInfo" items="${recommendProjectList }">
					<tr>
						<td>${p2p:abbrev(projectBaseInfo.projectName,10)}</td>
						<td>${projectBaseInfo.projectDuration }个月</td>
						<td><fmt:formatNumber value="${projectBaseInfo.annualizedRate }" type="percent" maxFractionDigits="1" /></td>
						<td><fmt:formatNumber value="${projectBaseInfo.startingAmount }" pattern="#0" />元起</td>
						<td><a style="color:#68a7e4" href="${ctxFront}/project_detail?id=${projectBaseInfo.projectId}">详情</a></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="div_height_10"></div>
		</div>
		<div class="bg_789_bottom"></div>
		<script>$(function () 
	      { $("[data-toggle='popover']").popover();
	      });
	   	</script>
		<script type="text/javascript">
    		$(document).ready(function() {
				$(document).on('click', '#avatar', function() {
					window.location.href="${ctxFront}/customer/account/change_avatar";
				});
				//页签切换
				$('#tab_bar li').click(function(){
					if ($(this).children("span").hasClass("selected")) {
						return ;
					}
					$("#tab_bar li").each(function(){
				        if($(this).children("span").hasClass("selected")){
				            $(this).children("span").removeClass("selected");
				        }
				    });
					$(this).children("span").addClass("selected");
					if ($('#item_1 span').hasClass("selected")){
						$('#my_investment_list').show();
						$('#my_current_peanut_list').hide();
						$('#detail_info').attr('href','${ctxFront}/customer/investment/project_cyz');
					} else if ($('#item_2 span').hasClass("selected")){
						$('#my_investment_list').hide();
						$('#my_current_peanut_list').show();
						$('#detail_info').attr('href','${ctxFront}/currentAccount/myCurrentPeanut');
					}
				});
			});
		</script>
	</body>
</html>