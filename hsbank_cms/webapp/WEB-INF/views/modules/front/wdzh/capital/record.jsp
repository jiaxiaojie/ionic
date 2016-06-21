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
		<div id="content_top" class="bg_yellow_789x216">
			<div class="left">
				<div class="title">账户余额</div>
				<div class="content">
					<span class="spanUnit">元</span>
					<span class="spanText"><fmt:formatNumber value="${customerBalance.goldBalance }" pattern="##0.00" /></span>
				</div>
			</div>
			<div class="vline"></div>
			<div class="center">
				<div class="row">
					<div class="column_1">可用资金</div>
					<div class="column_2">已充值总额</div>
				</div>
				<div class="row">
					<div class="column_1"><fmt:formatNumber value="${customerBalance.goldBalance - customerBalance.congealVal }" pattern="##0.00" />元</div>
					<div class="column_2"><fmt:formatNumber value="${customerBalance.sumRecharge }" pattern="##0.00" />元</div>
				</div>
				<div class="row">
					<div class="column_1">冻结资金</div>
					<div class="column_2">已提现总额</div>
				</div>
				<div class="row">
					<div class="column_1"><fmt:formatNumber value="${customerBalance.congealVal }" pattern="##0.00" />元</div>
					<div class="column_2"><fmt:formatNumber value="${customerBalance.sumWithdraw }" pattern="##0.00" />元</div>
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
			<div class="div_height_10"></div>
			<div class="line_01">
	            <form id="searchForm" action="${ctxFront}/customer/capital/record" method="post">
	                <span class="span_text_1">查询类型</span>
	                <form:select id="changeType" name="changeType" path="changeType" class="select">
	                    <form:option value="">全部</form:option>
	                    <form:options items="${fns:getDictList('customer_balance_change_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	                </form:select>
	                <input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo }"/>
	                <span class="span_text_1">日期</span>
					<input id="startDateTime" style="cursor:pointer" name="startDateTime" value="<fmt:formatDate value="${pageSearchBean.startDateTime}" pattern="yyyy-MM-dd"/>" readonly="true" type="text" maxLength="10" class="input"/>
					<span class="span_text_2">至</span>
					<input id="endDateTime"  style="cursor:pointer"  name="endDateTime" value="<fmt:formatDate value="${pageSearchBean.endDateTime}" pattern="yyyy-MM-dd"/>" readonly="true" type="text" maxLength="10" class="input"/>
					<div id="bt_query" onclick="submit()" class="bt_yellow_85x24" style="float:left;margin-top:8px;margin-left:10px;">查&nbsp;询</div>
	                <a href="javascript:void(0)" onclick="recentMonth(-6)">最近六月</a>
	                <a href="javascript:void(0)" onclick="recentMonth(-3)">最近三月</a>
                </form>
            </div>
			<div class="div_height_15"></div>
			<hr/>
			<div class="div_height_15"></div>
			<hr class="hr_dashed"/>
			
			<table class="table table-hover" style="width: 749px; margin-left: 20px;">
	            <thead>
	                <tr>
	                    <th width="20%">日期</th>
	                    <th width="20%">类型</th>
	                    <th width="15%">收入</th>
	                    <th width="15%">结余</th>
	                    <th width="30%">备注</th>
	                </tr>
	            </thead>
	            <tbody>
					<c:forEach items="${page.list}" var="customerBalanceHis">
	                <tr>
	                    <td><fmt:formatDate value="${customerBalanceHis.opDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                    <td>${fns:getDictLabel(customerBalanceHis.changeType, 'customer_balance_change_type_dict', '')}</td>
	                    <td><fmt:formatNumber value="${customerBalanceHis.changeVal }" pattern="##0.00" />元</td>
	                    <td><fmt:formatNumber value="${customerBalanceHis.balance }" pattern="##0.00" />元</td>
	                    <td>${customerBalanceHis.changeReason }</td>
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
			<hr/>
			<!-- <div class="line_03"><a>导出查询结果</a></div> -->
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
	</body>
</html>