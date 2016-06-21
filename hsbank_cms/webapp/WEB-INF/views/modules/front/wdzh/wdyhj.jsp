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
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div id="tab_bar">
				<li id="item_1" class="item"><span class="selected">可使用现金券</span></li>
				<li id="item_2" class="item"><span>已使用现金券</span></li>
				<li id="item_3" class="item"><span>已失效现金券</span></li>
			</div>
			<!-- 可使用现金券 -->
			
			<div class="coupons_list" style=" width:770px;">
	            <div style="width: 756px; margin: auto; position: relative;">
		            <div class="dumascroll_area">
		                <div id="col clearfix">
		                <!-- 可使用现金券 -->
		                <div id="can_be_used_list" >
			                <c:forEach var="customerInvestmentTicket" items="${canUseInvestmentTicketList }">
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
			             <!-- 已使用现金券 -->
		                 <div id="has_been_used_list" style="display:none">
			                <c:forEach var="customerInvestmentTicket" items="${usedInvestmentTicketList }">
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
			             <!-- 已失效现金券 -->
		                 <div id="expired_list" style="display:none">
			                <c:forEach var="customerInvestmentTicket" items="${expiredInvestmentTicketList }">
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
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
		<script type="text/javascript">
			$(document).ready(function() {
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
						$('#can_be_used_list').show();
						$('#has_been_used_list').hide();
						$('#expired_list').hide();
					} else if ($('#item_2 span').hasClass("selected")){
						$('#can_be_used_list').hide();
						$('#has_been_used_list').show();
						$('#expired_list').hide();
					} else if ($('#item_3 span').hasClass("selected")){
						$('#can_be_used_list').hide();
						$('#has_been_used_list').hide();
						$('#expired_list').show();
					}
				});
			});
		</script>
	</body>
</html>