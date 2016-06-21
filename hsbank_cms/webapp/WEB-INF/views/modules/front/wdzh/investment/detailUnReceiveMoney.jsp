<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/tzgl_skmx.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zjgl_jyjl.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var dateUtils = new DateUtils();
			function recentMonth(months) {
				$("#startDateTime").val(dateUtils.formatDate(new Date()));
				$("#endDateTime").val(dateUtils.addMonths(new Date(), months));
				submit()
			}
			function submit() {
				$("#searchForm").submit();
			}
			//时间选择
			$(function(){
				$("#startDateTime").click(function(){
					WdatePicker({minDate:new Date(), maxDate:$("#endDateTime").val()});
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
					<dt class=""><strong>待收款总额</strong></dt>
					<dd class="font_color_red"><strong><fmt:formatNumber value="${empty unReceivedMoneySummary['money'] ? 0 : unReceivedMoneySummary['money'] }" pattern="##0.00" />元</strong></dd>
				</dl>
				<dl class="two border_right">
					<dt class="">待收本金</dt>
					<dd class="font_color_red"><fmt:formatNumber value="${empty unReceivedMoneySummary['principal'] ? 0 : unReceivedMoneySummary['principal'] }" pattern="##0.00" />元</dd>
				</dl>
				<dl class="three">
					<dt class="">待收利息</dt>
					<dd class="font_color_red"><fmt:formatNumber value="${empty unReceivedMoneySummary['interest'] ? 0 : unReceivedMoneySummary['interest'] }" pattern="##0.00" />元</dd>
				</dl>
			</div>
			<div class="s_bottom"></div>
		</div>
		
		
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle" style="width:769px;">
			<div id="tab_bar" style="padding-left:20px;">
				<li id="item_1" onClick="selectItem(1)" class="item"><span class="selected">待收款明细</span></li>
				<li id="item_2" onClick="selectItem(2)" class="item"><span>已收款明细</span></li>
			</div>
			<!-- 待收款明细 -->
			<div id="record_list" class="show">
				<div class="div_height_15"></div>
				<div class="line_01">
					<form id="searchForm" method="post" action="${ctxFront }/customer/investment/detailUnReceiveMoney" style="overflow:hidden;">
						<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo }"/>
						<span class="span_text_1">日期</span>
						<input id="startDateTime"  style="cursor:pointer"  name="startDateTime" value="<fmt:formatDate value="${pageSearchBean.startDateTime}" pattern="yyyy-MM-dd"/>" readonly="true" type="text" maxLength="10" class="input"/>
						<span class="span_text_2">至</span>
						<input id="endDateTime"  style="cursor:pointer" name="endDateTime" value="<fmt:formatDate value="${pageSearchBean.endDateTime}" pattern="yyyy-MM-dd"/>" readonly="true" type="text" maxLength="10" class="input"/>
						<div id="bt_query" onclick="submit()" class="bt_yellow_85x24" style="float:left;margin-top:8px;margin-left:10px;">查&nbsp;询</div>
						<a href="javascript:void(0)" onclick="recentMonth(6)">未来6个月</a>
						<a href="javascript:void(0)" onclick="recentMonth(3)">未来3个月</a>
						<a href="javascript:void(0)" onclick="recentMonth(1)">未来1个月</a>
					</form>
				</div>
				<div class="div_height_10"></div>
				<hr/>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>预计到账时间</th>
							<th>项目名称</th>
							<th>预期收款额（元）</th>
							<th>预收本金（元）</th>
							<th>预收利息（元）</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="willRepayment" items="${page.list}" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td><fmt:formatDate value="${willRepayment.repaymentDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${p2p:abbrev(willRepayment.projectBaseInfo.projectName,10) }</td>
							<td><fmt:formatNumber value="${willRepayment.money }" pattern="##0.00" /></td>
							<td><fmt:formatNumber value="${willRepayment.principal }" pattern="##0.00" /></td>
							<td><fmt:formatNumber value="${willRepayment.interest }" pattern="##0.00" /></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<!-- 分页开始 -->
		        <div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
		        <script type="text/javascript">
		        	function page(n,s) {
		        		$("#pageNo").val(n);
		        		$("#searchForm").submit();
		        	}
		        </script>
		        <!-- 分页结束 -->
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
				});
			});
			
			function selectItem(index) {
				if (index == 1) {
					window.location = "${ctxFront}/customer/investment/detailUnReceiveMoney";
				}
				if (index == 2) {
					window.location = "${ctxFront}/customer/investment/detailReceivedMoney";
				}
			}
		</script>
	</body>
</html>