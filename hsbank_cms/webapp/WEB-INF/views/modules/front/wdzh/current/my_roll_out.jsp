<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/layouts/my_account.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/rzgl_wdjk.css?${version }" rel="stylesheet"/>
		<title></title>
	</head>
	<body>		
		<div class="statistics my_pd_right">
            <div class="s_top"></div>
            <div class="s_middle column_2 clearfix">
            	<div class="clearfix p_title_area">
                    <div class="pull-left p_title">${p2p:abbrev(holdInfo.currentProjectInfo.name,100)}</div>
                    <div class="pull-left p_tip"><span>年化收益率<em><fmt:formatNumber  value="${holdInfo.currentProjectInfo.rate * 100 }" pattern="#.##" />%</em></span>|<span>每万元日收益<em>${dayProfit }元</em></span>|<span>收益起始日<em>T+0</em>（工作日）</span></div>
                </div>
                <div class="clearfix p_data">
                	<div class="pull-left one border_right">
                    	<dl class="dl1">
                        	<dt><strong>昨日收益（元）</strong></dt>
                        	<dd><strong><fmt:formatNumber value="${yesterdayProfit}" pattern="##0.0000" /></strong></dd>
                        </dl>
                    	<dl class="dl2">
                        	<dt>本金（元）</dt>
                        	<dd><fmt:formatNumber value="${holdInfo.principal}" pattern="##0.00" /></dd>
                        </dl>
                        <dl class="dl3">
                            <dd>
                            <c:if test="${holdInfo.currentProjectInfo.status eq '3' }">
                        		<a href="${ctxFront}/current/currentProjectDetail?id=${holdInfo.projectId}" target="_blank" class="btn_brown">投资</a>
                            </c:if>
                            </dd>
                            <dd><div class="div_height_5"></div></dd>
                        	<dd><a href="${ctxFront}/currentAccount/redeemPrincipal?projectId=${holdInfo.projectId }" target="_blank" class="btn_brown_border">赎回</a></dd>
                        </dl>
                    </div>
                	<div class="pull-left one">
                    	<dl class="dl1">
                        	<dt>累计收益（元）</dt>
                        	<dd><fmt:formatNumber value="${sumProfit }" pattern="##0.0000" /></dd>
                        </dl>
                    	<dl class="dl2">
                        	<dt>可提取收益（元）</dt>
                        	<dd><fmt:formatNumber value="${holdInfo.interest }" pattern="##0.0000" /></dd>
                        </dl>
                        <dl class="dl3">
                        	<dd><a href="${ctxFront}/currentAccount/pollInterest?projectId=${holdInfo.projectId }" target="_blank" class="btn_brown">提取</a></dd>
                        </dl>
                    </div>
                </div>
            </div>
            <div class="s_bottom"></div>
          </div>
		
		
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle" style="width:769px;">
			<div id="tab_bar" style="padding-left:20px;">
				<li id="item_1" onClick="selectItem(1)" class="item"><span>收益</span></li>
				<li id="item_2" onClick="selectItem(2)" class="item"><span>投资</span></li>
				<li id="item_3" onClick="selectItem(3)" class="item"><span  class="selected">赎回</span></li>
			</div>
			<!-- 待收款明细 -->
			<div id="earnings" style="display: block; padding:0 26px;">
				<div class="div_height_10"></div>
				<table class="table table-hover mb0">
					<thead>
						<tr>
							<th width="42%">时间</th>
                            <th width="36%">金额（元）</th>
                            <th width="22%">备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="interest" items="${page.list}" varStatus="status">
						<tr>
							<td><fmt:formatDate value="${interest.opDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatNumber value="${interest.changeValue }" pattern="##0.00" /></td>
							<td>${interest.changeReason }</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<!-- 分页开始 -->
		        <div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
		        <script type="text/javascript">
			        function page(n,s) {
						var url = "${ctxFront}/currentAccount/myRollOut?projectId=${holdInfo.projectId }&pageNo=" + n;
						window.location.href = url;
					}
		        </script>
		        <!-- 分页结束 -->
			</div>
			<div class="div_height_50"></div>
			
			<div class="buttom-tips">
                <div class="tips-top"></div>
                <div class="tips-center">
					<dl>
                        <dt>温馨提示</dt>
                        <dd></dd>
                        <dd>1.活花生的收益每日结算，转入当日开始计息，每日利息在次日结算。</dd>
                        <dd>2.活花生支持随时发起赎回，每日不限赎回次数，但是赎回金额必须为1元的整数倍，每人每天赎回限额5万元，赎回的资金当日没有利息。</dd>
                        <dd>3.申请赎回的资金半小时内到账。</dd>
                        <dd>4.若遇巨额赎回情况，花生金服有权拒绝客户当日的转出申请，您可以次日继续申请转出，具体以公司通知为准。</dd>
                    </dl>
                </div>
                <div class="tips-bottom"></div>
            </div>
			
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
				});
			});
			
			function selectItem(index) {
				if (index == 1) {
					window.location = "${ctxFront}/currentAccount/myDailyProfit?projectId=${holdInfo.projectId }";
				}else if (index == 2) {
					window.location = "${ctxFront}/currentAccount/myChangeInto?projectId=${holdInfo.projectId }";
				}else if (index == 3) {
					window.location = "${ctxFront}/currentAccount/myRollOut?projectId=${holdInfo.projectId }";
				}
			}
		</script>
	</body>
</html>