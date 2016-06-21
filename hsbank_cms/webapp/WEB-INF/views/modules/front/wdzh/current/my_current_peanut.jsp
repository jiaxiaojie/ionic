<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zjgl_jyjl.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var dateUtils = new DateUtils();
			function recentMonth(months) {
				$("#startDateTime").val(dateUtils.addMonths(new Date(), months));
				$("#endDateTime").val(dateUtils.formatDate(new Date()));
				submit();
			}
			function submit() {
				$("#searchForm").submit();
			}
			$(function(){
				$("#changeType").val("${changeType }");
				$("#startDateTime").click(function(){
					WdatePicker({maxDate:$("#endDateTime").val()});
				});
				$("#endDateTime").click(function(){
					WdatePicker({minDate:$("#startDateTime").val()});
				});
			});
		</script>
	</head>
	<body>
		<div class="statistics">
            <div class="s_top"></div>
            <div class="s_middle column_3 clearfix">
            	<dl class="one border_right">
                    <dt class="">持有本金总额</dt>
                    <dd class="font_color_red"><fmt:formatNumber value="${summary.currentPrincipal}" pattern="##0.00" />元</dd>
                </dl>
                <dl class="two border_right">
                    <dt class=""><strong>昨日收益</strong></dt>
                    <dd class="font_color_red"><strong><fmt:formatNumber value="${yesterdayProfit}" pattern="##0.0000" />元</strong></dd>
                </dl>
                <dl class="three">
                    <dt class="">累计收益</dt>
                    <dd class="font_color_red"><fmt:formatNumber value="${summary.totalGetInterest }" pattern="##0.0000" />元</dd>
                </dl>
            </div>
            <div class="s_bottom"></div>
         </div>
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<table class="table table-hover" style="width: 749px; margin-left: 20px;">
	            <thead>
	                <tr>
	                    <th width="11%">项目名称</th>
	                    <th width="9%">年化利率</th>
                        <th width="14%">持有本金（元）</th>
                        <th width="14%">申请赎回（元）</th>
                        <th width="14%">累计收益（元）</th>
                        <th width="15%">可提取收益（元）</th>
                        <th width="21%">操作</th>
	                </tr>
	            </thead>
	            <tbody>
					<c:forEach items="${page.list}" var="holdInfo">
	                <tr>
	                    <td class="brown-text"><a href="${ctxFront}/currentAccount/myDailyProfit?projectId=${holdInfo.projectId}"  title="查看" class="blue-text">${p2p:abbrev(holdInfo.currentProjectInfo.name,100)}</a></td>
	                    <td><fmt:formatNumber  value="${holdInfo.currentProjectInfo.rate * 100 }" pattern="#.##" />%</td>
	                    <td><fmt:formatNumber value="${holdInfo.principal }" pattern="##0.00" /></td>
	                    <td><fmt:formatNumber value="${holdInfo.applyRedeemPrincipal }" pattern="##0.00" /></td>
	                    <td><fmt:formatNumber value="${holdInfo.totalProfit }" pattern="##0.0000" /></td>
	                    <td><fmt:formatNumber value="${holdInfo.interest }" pattern="##0.0000" /></td>
	                    <td>
							<a href="${ctxFront}/currentAccount/pollInterest?projectId=${holdInfo.projectId }" title="提取收益" class="blue-text">提取收益</a>&nbsp;&nbsp;
							<a href="${ctxFront}/currentAccount/redeemPrincipal?projectId=${holdInfo.projectId }" title="赎回本金" class="blue-text">赎回</a>&nbsp;&nbsp;
							<c:if test="${holdInfo.currentProjectInfo.status eq '3'}">
								<a href="${ctxFront}/current/currentProjectDetail?id=${holdInfo.projectId}" target="_blank" title="投资" class="blue-text">投资</a>
							</c:if>
                        </td>
	                </tr>
	                </c:forEach>
	            </tbody>
	        </table>
			<!-- 分页开始 -->
	        <div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
	        <script type="text/javascript">
		        function page(n,s) {
					var url = "${ctxFront}/currentAccount/myCurrentPeanut?pageNo=" + n;
					window.location.href = url;
			    }
	        </script>
	        <!-- 分页结束 -->
			<hr/>
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
	</body>
</html>