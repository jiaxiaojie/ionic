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
			<li id="item_4" onClick="selectItem(4)" class="item"><span>已转出</span></li>
			<li id="item_5" onClick="selectItem(5)" class="item"><span
				class="selected">已结束</span></li>
		</div>

		<div class="list_info clearfix">
			<div class="fl">结束：项目结清时，用户持有的债权。</div>
			<div class="fr"></div>
		</div>
		<table class="table table-hover table_list_1">
			<thead>
				<tr>
					<th width="22%">项目名称</th>
					<th width="12%">年化利率</th>
					<th width="15%">原始投资金额</th>
					<th width="14%">赚取金额</th>
					<th width="15%">结清日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="yjs" items="${page.list }"
					varStatus="status">
					<tr>
						<td title="${yjs.projectBaseInfo.projectName }"><c:choose>
								<c:when test="${fn:length(yjs.projectBaseInfo.projectName)>9}">
									<c:out
										value="${fn:substring(yjs.projectBaseInfo.projectName, 0, 8)}." />
								</c:when>
								<c:otherwise>
									<c:out value="${yjs.projectBaseInfo.projectName}" />
								</c:otherwise>
							</c:choose></td>
						<!--标题字数限制在9个以内-->
						<td><fmt:formatNumber
								value="${yjs.projectBaseInfo.annualizedRate}" type="number"
								pattern="0.0%" /></td>
						<td><fmt:formatNumber value="${yjs.amount}" type="number"
								pattern="0.00" />元</td>
						<td><fmt:formatNumber value="${yjs.willProfit}" type="number"
								pattern="0.00" />元</td>
						<td><fmt:formatDate value="${yjs.finishDate}"
								pattern="yyyy-MM-dd" /></td>
					</tr>
				</c:forEach>
			</tbody>
			<!-- 分页开始 -->
			<div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
			<script type="text/javascript">
				function page(n,s) {
					var url = "${ctxFront}/customer/investment/project_yjs?pageNo=" + n;
					window.location.href = url;
			}
			</script>
			<!-- 分页结束 -->
		</table>

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
	</script>

</body>
</html>
