<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="my_account" />
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/modules/front/css/tipso.css" />
<!--提示框样式-->
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/modules/front/css/wdzh/tzgl_wdzq.css?${version }" />
<script type="text/javascript"
	src="${ctxStatic}/modules/front/js/tipso.js"></script>
<!--滚动条-->
<title></title>
</head>
<body>

	<div class="bg_789_top"></div>
	<div class="wdzh-main">

		<div id="tab_bar">
			<li id="item_1" onClick="selectItem(1)" class="item"><span>持有中</span></li>
			<li id="item_2" onClick="selectItem(2)" class="item"><span>转出中</span></li>
			<li id="item_3" onClick="selectItem(3)" class="item"><span
				class="selected">投标中</span></li>
			<li id="item_4" onClick="selectItem(4)" class="item"><span>已转出</span></li>
			<li id="item_5" onClick="selectItem(5)" class="item"><span>已结束</span></li>
		</div>

		<div class="list_info clearfix">
			<div class="fl">投标中：已投资，尚未放款的项目。</div>
			<div class="fr"></div>
		</div>
		<table class="table table-hover table_list_1">
			<thead>
				<tr>
					<th width="19%">项目名称</th>
					<th width="9%">年化利率</th>
					<th width="11%">投资金额</th>
					<th width="13%">到期日</th>
					<th width="11%">使用现金券</th>
					<th width="12%">平台垫付金额</th>
					<th width="12%">应付金额</th>
					<th width="13%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tbz" items="${page.list}" varStatus="status">
					<tr>
						<td title="${tbz.projectBaseInfo.projectName }"><c:choose>
								<c:when test="${fn:length(tbz.projectBaseInfo.projectName)>9}">
									<c:out
										value="${fn:substring(tbz.projectBaseInfo.projectName, 0, 8)}." />
								</c:when>
								<c:otherwise>
									<c:out value="${tbz.projectBaseInfo.projectName}" />
								</c:otherwise>
							</c:choose></td>
						<!--标题字数限制在9个以内-->
						<td><fmt:formatNumber
								value="${tbz.projectBaseInfo.annualizedRate}" type="number"
								pattern="0.0%" /></td>
						<td><fmt:formatNumber value="${tbz.amount}" type="number"
								pattern="0.00" />元</td>
						<td><fmt:formatDate
								value="${tbz.projectBaseInfo.lastRepaymentDate}"
								pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatNumber value="${tbz.ticketAmount}"
								type="number" pattern="0.00" />元</td>
						<td><fmt:formatNumber value="${tbz.platformAmount}"
								type="number" pattern="0.00" />元</td>
						<td><fmt:formatNumber value="${tbz.actualAmount}"
								type="number" pattern="0.00" />元</td>
						<td class="operate_area"><a
							href="javascript:showPlanList('${tbz.id}')" class="icon_jh"
							title="还款计划"></a> <!--点击弹出“还款计划”窗口--></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
		<!-- 分页开始 -->
		<div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
		<script type="text/javascript">
			function page(n,s) {
				var url = "${ctxFront}/customer/investment/project_tbz?pageNo=" + n;
				window.location.href = url;
		}
		</script>
		<!-- 分页结束 -->
	</div>

	<!--还款计划 弹窗 默认为display:none，显示出来为display:block-->
	<div class="pop_bg" style="display: none" id="listPop">
		<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
		<div class="pop_main"
			style="width: 690px; height: 520px; margin-left: -345px; margin-top: -260px;">
			<div class="pop_title">
				还款计划表<a href="javascript:closePlan();" class="close_pop"></a>
			</div>
			<div class="pop_content">

				<dl class="plan_1 clearfix">
					<dt id="project_show"></dt>
					<dd class="investment">
						投资金额：<span id="investment_amount"></span>元
					</dd>
					<dd class="earnings">
						投资到期可获得总收益：<span id="sum_profit"></span>元
					</dd>
				</dl>

				<div class="plan_2">
					<div class="dumascroll"
						style="width: 650px; height: 324px; margin: auto">
						<div id="col">
							<table border="0" class="table table-hover" id="plan_table">

								<thead>
									<tr>
										<th width="231">还款日期</th>
										<th width="220">应收本金（元）</th>
										<th width="183">应收利息（元）</th>
										<th width="183">还款状态</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
    <div class="bg_789_bottom"></div>
	
	<!--提示框JS-->
	<script type="text/javascript">
		var selectId = "";
		$(function() {

			// 1
			$('.tip1').tipso({
				useTitle : false
			});

		});
		function selectItem(index) {
			if (index == 1) {
				window.location = "${ctxFront}/customer/investment/project_cyz";
			}
			if (index == 2) {
				window.location = "${ctxFront}/customer/investment/project_zcz";
			}
			if (index == 3) {
				window.location = "${ctxFront}/customer/investment/project_tbz";
			}
			if (index == 4) {
				window.location = "${ctxFront}/customer/investment/project_yzc";
			}
			if (index == 5) {
				window.location = "${ctxFront}/customer/investment/project_yjs";
			}
		}
		function closePlan() {
			$('#listPop').hide();
		}
		function showPlanList(id) {
			$('#listPop').show();
			selectId = id;
			$.ajax({
				type : 'post',
				url : '${ctxFront}/customer/investment/project_plan_list',
				data : {
					recordId : id
				},
				dataType : 'json',
				success : function(data) {
					$('#project_show').html(data.project_show);
					$('#investment_amount').html(data.investment_amount);
					$('#sum_profit').html(data.sum_profit);
					$("#plan_table tbody").empty();
					var info = data.splitList;
					var row;
					var html = '';
					for (var i = 0; i < info.length; i++) {
						var row = info[i];
						html += "<tr>";
						html += "<td>" + row.repaymentDt + "</td>";
						html += "<td>" + row.principal + "</td>";
						html += "<td>" + row.interest + "</td>";
						html += "<td>" + row.statusShow + "</td>";
						html += "</tr>";
					}
					$('#plan_table').append(html);

				}
			});
		}
	</script>

</body>
</html>
