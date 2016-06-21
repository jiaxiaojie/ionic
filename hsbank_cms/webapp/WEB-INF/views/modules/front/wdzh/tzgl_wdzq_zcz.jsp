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
<!--提示框JS-->
<title></title>
</head>
<body>

	<div class="bg_789_top"></div>
	<div class="wdzh-main">

		<div id="tab_bar">
			<li id="item_1" onClick="selectItem(1)" class="item"><span>持有中</span></li>
			<li id="item_2" onClick="selectItem(2)" class="item"><span
				class="selected">转出中</span></li>
			<li id="item_3" onClick="selectItem(3)" class="item"><span>投标中</span></li>
			<li id="item_4" onClick="selectItem(4)" class="item"><span>已转出</span></li>
			<li id="item_5" onClick="selectItem(5)" class="item"><span>已结束</span></li>
		</div>

		<div class="list_info clearfix">
			<div class="fl">转出中：当前持有的且正在转出的债权。</div>
			<div class="fr"></div>
		</div>
		<table class="table table-hover table_list_1">
			<thead>
				<tr>
					<th width="20%">项目名称</th>
					<th width="9%">年化利率</th>
					<th width="12%">原始投资金额</th>
					<th width="11%">预转出金额</th>
					<th width="12%">已转出金额</th>
					<th width="14%">交易费用</th>
					<th width="12%">转出到期日</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="zcz" items="${page.list }" varStatus="status">
					<tr>
						<td title="${zcz.projectBaseInfo.projectName }"><c:choose>
								<c:when test="${fn:length(zcz.projectBaseInfo.projectName)>9}">
									<c:out
										value="${fn:substring(zcz.projectBaseInfo.projectName, 0, 8)}." />
								</c:when>
								<c:otherwise>
									<c:out value="${zcz.projectBaseInfo.projectName}" />
								</c:otherwise>
							</c:choose></td>
						<td><fmt:formatNumber
								value="${zcz.projectBaseInfo.annualizedRate}" type="number"
								pattern="0.0%" /></td>
						<td><fmt:formatNumber value="${zcz.pir.amount}" type="number"
								pattern="0.00元" /></td>
						<td><fmt:formatNumber value="${zcz.pir.amount}" type="number"
								pattern="0.00元" /></td>
						<td><fmt:formatNumber
								value="${zcz.projectExecuteSnapshot.endFinanceMoney}"
								type="number" pattern="0.00元" /></td>
						<td><fmt:formatNumber value="${zcz.platformMoney}"
								type="number" pattern="0.00元" /></td>
						<td><fmt:formatDate value="${zcz.discountDate}"
								pattern="yyyy-MM-dd" /></td>
						<td class="operate_area"><a href="#"
							onClick="javascript:showDetail('${zcz.transferProjectId }');"
							class="icon_cx" title="撤消"></a>
						<!--点击弹出“撤消”窗口--> <a href="#"
							onClick="javascript:showList('${zcz.transferProjectId }');"
							class="icon_zcxq" title="转出详情"></a>
						<!--点击弹出“转出详情”窗口--></td>
					</tr>
				</c:forEach>
			</tbody>
			<!-- 分页开始 -->
			<div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
			<script type="text/javascript">
				function page(n,s) {
					var url = "${ctxFront}/customer/investment/project_zcz?pageNo=" + n;
					window.location.href = url;
			}
			</script>
			<!-- 分页结束 -->
		</table>

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
						原始债权：<span id="ld_amount">1000</span>元
					</p>
					<p>
						剩余债权：<span id="ld_surplusMoney">8800</span>元
					</p>
					<p>
						转出债权：<span id="ld_endFinanceMoney">1200</span>元
					</p>
					<p>
						手续费：<span id="ld_serviceCharge">20</span>元
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


	<!--点击“撤销”按钮弹出此窗口， 弹窗默认为display:none，显示出来为display:block-->
	<div class="pop_bg" style="display: none" id="detailPop">
		<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
		<div class="pop_main"
			style="width: 690px; height: 520px; margin-left: -345px; margin-top: -260px;">
			<div class="pop_title">
				撤销债权转出<a href="javascript:closeDetail()" class="close_pop"></a>
			</div>
			<div class="pop_content">
				<div class="pop_field clearfix">
					<dl class="item">
						<dt>项目名称</dt>
						<dd id="ts_projectName"></dd>
					</dl>
					<dl class="item">
						<dt>年化利率</dt>
						<dd id="ts_annualizedRate">%</dd>
					</dl>
					<dl class="item">
						<dt>到期时间</dt>
						<dd id="ts_discountDate"></dd>
					</dl>
					<dl class="item">
						<dt>债权取得方式</dt>
						<dd id="ts_getType"></dd>
					</dl>
					<dl class="item">
						<dt>债权取得日期</dt>
						<dd id="ts_investDate"></dd>
					</dl>
					<dl class="item">
						<dt>原始投资金额</dt>
						<dd id="ts_investAmount">元</dd>
					</dl>
					<dl class="item">
						<dt>已收本息</dt>
						<dd id="ts_hasReciveAmount">元</dd>
					</dl>
					<dl class="item">
						<dt>预计转出金额</dt>
						<dd id="ts_willTransferAmount">元</dd>
					</dl>
					<dl class="item">
						<dt>已转出金额</dt>
						<dd id="ts_endFinanceMoney">元</dd>
					</dl>
					<dl class="item">
						<dt>剩余金额</dt>
						<dd id="ts_surplusMoney">元</dd>
					</dl>
					<dl class="item">
						<dt>转让费用</dt>
						<dd id="ts_serviceCharge">元</dd>
					</dl>
					<dl class="item">
						<dt>收入金额</dt>
						<dd id="incomeAmount">元</dd>
					</dl>
				</div>

				<div class="mtb20 clearfix lh36">
					<div class="code_area fl mb0 clearfix">
						<b class="w0">验证码</b>
						<p class="clearfix">
							<input name="" type="text" class="w100" id="validateCodeInput"
								maxlength="4"><a href="#" class="vcode"><img
								src="${ctxStatic}/modules/front/images/util/img_vcode.png"
								id="validateCode" onClick="changeCode()"></a>
						</p>
					</div>
				</div>

				<div class="btn_group_one tline mg0 pt20">
					<a href="javascript:doCancelTransfer();" class="btn_brown_bc">撤消</a>
					<a href="javascript:closeDetail()" class="btn_gray_bc">取消</a>
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

		function doCancelTransfer() {
			var validateCode = $('#validateCodeInput').val();
			$
					.ajax({
						type : 'get',
						url : '${pageContext.request.contextPath}/servlet/validateCodeServlet',
						data : {
							validateCode : validateCode
						},
						dataType : 'text',
						success : function(data) {
							if (data == 'true') {
								var submit = function(v, h, f) {
									if (v == 1) {
										$
												.ajax({
													type : 'post',
													url : '${ctxFront}/customer/investment/transfer_cancel',
													data : {
														transferProjectId : selectId
													},
													dataType : 'json',
													success : function(data) {
														if (data.flag == 'ok') {
															$.jBox.success(
																	"取消成功",
																	"提示");
															window.location = "${ctxFront}/customer/investment/project_zcz";
														} else {
															$.jBox
																	.error(
																			"服务器异常，取消失败",
																			"提示");
														}
													}
												});
									} else {
										jBox.tip("取消", 'info');
									}
									return true;
								};
								$.jBox.confirm("你将要取消此债权转让,请确认?", "操作提示",
										submit, {
											buttons : {
												'确定' : 1,
												'取消' : -1
											}
										});

							} else {
								$.jBox.error("验证码填写错误！", "提示");
							}
						}
					});
		}
		function showDetail(id) {
			$('#detailPop').show();
			selectId = id;
			$
					.ajax({
						type : 'post',
						url : '${ctxFront}/customer/investment/transfer_detail',
						data : {
							transferProjectId : id
						},
						dataType : 'json',
						success : function(data) {
							$('#ld_amount').html(data.projectName);
							$('#ts_annualizedRate').html(
									data.annualizedRate + "%");
							$('#ts_discountDate').html(data.discountDate);
							$('#ts_getType').html(data.getType);

							$('#ts_investDate').html(data.investDate);
							$('#ts_investAmount').html(data.investAmount + "元");
							$('#ts_amount').html(data.amount + "元");
							$('#ts_hasReciveAmount').val(
									data.hasReciveAmount + "元");
							$('#ts_endFinanceMoney').html(
									data.endFinanceMoney + "元");
							$('#ts_surplusMoney').html(data.surplusMoney + "元");
							$('#ts_serviceCharge').html(
									data.serviceCharge + "元");
							$('#incomeAmount').html(data.endFinanceMoney + "元");
							$('#ts_willTransferAmount').html(
									data.investAmount + "元");
							$("#validateCode")
									.attr("src",
											"${pageContext.request.contextPath}/servlet/validateCodeServlet");
						}
					});
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
						html += "<td>" + row.to_borrowers_user_money + "</td>";
						html += "<td>" + row.op_dt + "</td>";
						html += "<td>" + row.up_to_platform_money + "</td>";
						html += "</tr>";
					}
					$('#investTable').append(html);
				}
			});
		}
		function closeDetail() {
			$('#detailPop').hide();
		}
		function closeList() {
			$('#listPop').hide();
		}
		function changeCode() {
			$('#validateCode').attr(
					'src',
					'${pageContext.request.contextPath}/servlet/validateCodeServlet?'
							+ new Date().getTime());
		}
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
