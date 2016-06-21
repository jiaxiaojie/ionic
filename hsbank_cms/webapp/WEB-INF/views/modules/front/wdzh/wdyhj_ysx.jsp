<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/wdyhj.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wytz/project_buy.css?${version }" rel="stylesheet"/>
		<title></title>
		
	</head>
	<body>
		<div class="statistics">
			<div class="s_top"></div>
			<div class="s_middle column_2  clearfix">
				<dl class="one border_right">
					<dt class="">已失效现金券总额</dt>
					<dd class="font_color_red" id="ticketSum1"><fmt:formatNumber value="${ticketStatistics.sumDenomination }" pattern="#" />元</strong></dd>
				</dl>
				<dl class="two border_right">
					<dt class="">已失效现金券数量</dt>
					<dd class="font_color_red"><fmt:formatNumber value="${ticketStatistics.count }" pattern="#" />张</dd>
				</dl>
			</div>
			<div class="s_bottom"></div>
		</div>
		
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div id="tab_bar">
				<li id="item_1" onClick="selectItem(1)" class="item"><span>可使用现金券</span></li>
				<li id="item_2" onClick="selectItem(2)" class="item"><span>已使用现金券</span></li>
				<li id="item_3" onClick="selectItem(3)" class="item"><span class="selected">已失效现金券</span></li>
			</div>
			<!-- 可使用现金券 -->
			<div class="coupons_list" style=" width:770px;">
	            <div style="width: 756px; margin: auto; position: relative;">
		            <div class="dumascroll_area">
		                <div id="col clearfix">
		                <!-- 可使用现金券 -->
		                <div id="can_be_used_list" >
			                <c:forEach var="customerInvestmentTicket" items="${page.list }">
			                    <div class="item item124" title="${customerInvestmentTicket.getRemark }">
			                        <div class="coupons clearfix">
			                            <div class="coupons_lt">
			                                <div class="digital"><span>${customerInvestmentTicket.investmentTicketType.denomination }</span>元</div>
			                                <p>使用条件：<span>${customerInvestmentTicket.investmentTicketType.useInfo }</span></p>
			                                <p>到期时间：<span><fmt:formatDate value="${customerInvestmentTicket.invalidDt}" pattern="yyyy-MM-dd"/></span></p>
			                            </div>
			                            <div class="coupons_rt"><span>现 金 券</span></div>
			                        </div>
			                    </div>
			                 </c:forEach>
			             </div>   
		                </div>
		            </div>
            	</div>
	        </div>
			<div class="div_height_50"></div>
			 <!-- 分页开始 -->
			<div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
			<script type="text/javascript">
				function page(n,s) {
					var url = "${ctxFront}/customer/ticket/wdyhj_ysx?pageNo=" + n;
					window.location.href = url;
			}
			</script>
			<!-- 分页结束 -->
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
		<script type="text/javascript">
			function selectItem(index) {
				if (index == 1) {
					window.location = "${ctxFront}/customer/ticket/my";
				}
				if (index == 2) {
					window.location = "${ctxFront}/customer/ticket/wdyhj_ysy";
				}
				if (index == 3) {
					window.location = "${ctxFront}/customer/ticket/wdyhj_ysx";
				}
			}
		</script>
	</body>
</html>