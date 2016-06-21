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
			<li id="item_3" onClick="selectItem(3)" class="item"><span>投标中</span></li>
			<li id="item_4" onClick="selectItem(4)" class="item"><span
				class="selected">已转出</span></li>
			<li id="item_5" onClick="selectItem(5)" class="item"><span>已结束</span></li>
		</div>

		<div class="list_info clearfix">
			<div class="fl">已转出：曾经持有且现在不持有的债权。</div>
			<div class="fr"></div>
		</div>
		<table class="table table-hover table_list_1">
			<thead>
				<tr>
					<th width="22%">项目名称</th>
					<th width="12%">年化利率</th>
					<th width="15%">原始投资金额</th>
					<th width="14%">已转出金额</th>
					<th width="15%">交易费用</th>
					<th width="15%">取得方式</th>
					<th width="7%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="yzc" items="${page.list }" varStatus="status">
					<tr>
						<td title="${yzc.projectBaseInfo.projectName }"><c:choose>
								<c:when test="${fn:length(yzc.projectBaseInfo.projectName)>9}">
									<c:out
										value="${fn:substring(yzc.projectBaseInfo.projectName, 0, 8)}." />
								</c:when>
								<c:otherwise>
									<c:out value="${yzc.projectBaseInfo.projectName}" />
								</c:otherwise>
							</c:choose></td>
						<!--标题字数限制在9个以内-->
						<td><fmt:formatNumber
								value="${yzc.projectBaseInfo.annualizedRate/100}" type="number"
								pattern="0.0%" /></td>
						<td><fmt:formatNumber value="${yzc.pir.amount}" type="number"
								pattern="0.00元" /></td>
						<td><fmt:formatNumber
								value="${yzc.projectExecuteSnapshot.endFinanceMoney}"
								type="number" pattern="0.00元" /></td>
						<td><fmt:formatNumber value="${yzc.platformMoney}"
								type="number" pattern="0.00元" /></td>
						<td><c:choose>
								<c:when test="${yzc.pir.transferProjectId==0}">
									直接投资
								</c:when>
								<c:otherwise>
									债权转让
								</c:otherwise>
							</c:choose> 
							<span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="投资日期：<fmt:formatDate value="${yzc.pir.opDt}" pattern="yyyy-MM-dd" />"></span>
						</td>
						<td class="operate_area"><a href="#"
							onclick="javascript:showList('${yzc.transferProjectId }');"
							class="icon_zcxq" title="转出详情"></a>
						<!--点击弹出“转出详情”窗口--></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页开始 -->
		<div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
		<script type="text/javascript">
			function page(n,s) {
				var url = "${ctxFront}/customer/investment/project_yzc?pageNo=" + n;
				window.location.href = url;
		}
		</script>
		<!-- 分页结束 -->
	</div>

	<!--点击“转出详情”弹出此窗口 弹窗默认为display:none，显示出来为display:block-->
	<div class="pop_bg" style="display: none" id="listPop">
		<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
		<div class="pop_main"
			style="width: 690px; height: 457px; margin-left: -345px; margin-top: -228px;">
			<div class="pop_title">
				债权转出详情<a href="javascript:closeList();" class="close_pop"></a>
			</div>
			<div class="pop_content">

				<div class="zcz_outline">
					<p>
						原始债权：<span id="ld_amount"></span>元
					</p>
					<p>
						剩余债权：<span id="ld_surplusMoney"></span>元
					</p>
					<p>
						转出债权：<span id="ld_endFinanceMoney"></span>元
					</p>
					<p>
						手续费：<span id="ld_serviceCharge"></span>元
					</p>
				</div>

				<div class="plan_2">
					<div class="dumascroll"
						style="width: 650px; height: 324px; margin: auto">
						<div id="col">
							<table border="0" class="table table-hover" id="investTable">
								<thead>
									<tr>
										<th width="91">用户名</th>
										<th width="108">转出债权（元）</th>
										<th width="174">时间</th>
										<th width="82">手续费（元）</th>
										<th width="59">协议</th>
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

		function showList(id) {
			$('#listPop').show();
			selectId = id;
			$.ajax({
				type : 'post',
				url : '${ctxFront}/customer/investment/transfer_list',
				data : {
					transferProjectId : selectId
				},
				dataType : 'json',
				success : function(data) {
					$('#ld_amount').html(data.investAmount);
					$('#ld_surplusMoney').html(data.surplusMoney);
					$('#ld_endFinanceMoney').html(data.endFinanceMoney);
					$('#ld_serviceCharge').html(data.serviceCharge);
					$("#investTable tbody").empty();
					var info = data.investList;
					var row;
					var html = '';
					for (var i = 0; i < info.length; i++) {
						var row = info[i];
						html += "<tr>";
						html += "<td>" + row.ca.accountName + "</td>";
						html += "<td>" + row.amount + "</td>";
						html += "<td>" + row.opDt + "</td>";
						html += "<td>" + row.upToPlatformMoney + "</td>";
						html += "<td> <a href='${ctxFront}/agreement/transfer_already?id=" + row.id + "&type=1' target='_blank' >协议</a></td>";
						html += "</tr>";
					}
					$('#investTable').append(html);
				}
			});
		}

		function closeList() {
			$('#listPop').hide();
		}
	</script>

</body>
</html>
