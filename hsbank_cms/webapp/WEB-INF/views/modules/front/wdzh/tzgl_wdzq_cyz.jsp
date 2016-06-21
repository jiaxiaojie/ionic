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
			<li id="item_1" onClick="selectItem(1)" class="item"><span
				class="selected">持有中</span></li>
			<li id="item_2" onClick="selectItem(2)" class="item"><span>转出中</span></li>
			<li id="item_3" onClick="selectItem(3)" class="item"><span>投标中</span></li>
			<li id="item_4" onClick="selectItem(4)" class="item"><span>已转出</span></li>
			<li id="item_5" onClick="selectItem(5)" class="item"><span>已结束</span></li>
		</div>

		<div class="list_info clearfix">
			<div class="fl">持有中：直接投资和债权认购而来的投资记录，是当前持有的所有债权的快照。</div>
			<div class="fr">
				正常<span class="bg_green color_block" title="绿色背景为正常"></span>&nbsp;&nbsp;&nbsp;&nbsp;逾期<span
					class="bg_red color_block" title="红色背景为逾期"></span>
			</div>
		</div>
		<table class="table table-hover table_list_1">
			<thead>
				<tr>
					<th width="22%">项目名称</th>
					<th width="11%">年化利率</th>
					<th width="15%">原始投资金额</th>
					<th width="13%">待收利息</th>
					<th width="13%">取得方式</th>
					<th width="13%">剩余天数</th>
					<th width="13%">操作</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach var="cyz" items="${page.list }" varStatus="status">
					<tr
						<c:choose>
								<c:when test="${cyz.pes.status==0}">
									class="bg_green"
								</c:when>
								<c:otherwise>
									class="bg_red"
								</c:otherwise>
							</c:choose>>
						<td title="${cyz.projectBaseInfo.projectName }"><c:choose>
								<c:when test="${fn:length(cyz.projectBaseInfo.projectName)>9}">
									<c:out
										value="${fn:substring(cyz.projectBaseInfo.projectName, 0, 8)}." />
								</c:when>
								<c:otherwise>
									<c:out value="${cyz.projectBaseInfo.projectName}" />
								</c:otherwise>
							</c:choose></td>

						<td><fmt:formatNumber
								value="${cyz.projectBaseInfo.annualizedRate}" type="number"
								pattern="0.0%" /></td>
						<td><fmt:formatNumber value="${cyz.amount}" type="number"
								pattern="0.00" />元</td>
						<td><fmt:formatNumber value="${cyz.willProfit}" type="number"
								pattern="0.00" />元</td>
						<td><c:choose>
								<c:when test="${cyz.transferProjectId==0}">
									直接投资
								</c:when>
								<c:otherwise>
									债权转让
								</c:otherwise>
							</c:choose> 
							  <span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="投资日期：<fmt:formatDate value="${cyz.opDt}" pattern="yyyy-MM-dd" />"></span>
						</td>
						<td>${cyz.pes.remainingTime}天</td>
						<td class="operate_area"><c:if
								test="${cyz.canTransferFlag==true}">
								<a href="javascript:toTransfer('${cyz.id}')" class="icon_zc"
									title="转出"></a>
							</c:if> <a href="javascript:showPlanList('${cyz.id}')" class="icon_jh"
							title="还款计划"></a>
							<c:choose>
								<c:when test="${cyz.transferProjectId eq 0 }">
							<a href="${ctxFront }/agreement/investmentProject?recordId=${cyz.id}" target="_blank" class="icon_xy" title="借款协议"></a>
								</c:when>
								<c:otherwise>
							<a href="${ctxFront }/agreement/transfer?transferProjectId=${cyz.transferProjectId}&type=0&amount=${cyz.amount}" target="_blank" class="icon_xy" title="债权转让协议"></a>
								</c:otherwise>
							</c:choose></td>
							</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
		<!-- 分页开始 -->
		<div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
		<script type="text/javascript">
			function page(n,s) {
				var url = "${ctxFront}/customer/investment/project_cyz?pageNo=" + n;
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
						style="width: 650px; height: 324px; margin: auto; overflow-y: scroll;">
						<div id="col">
							<table border="0" class="table table-hover" id="plan_table">

								<thead>
									<tr>
										<th width="141">还款日期</th>
										<th width="127">应收本金（元）</th>
										<th width="120">应收利息（元）</th>
										<th width="111">支付情况</th>
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

	<!--点击“转出”按钮弹出此窗口， 弹窗默认为display:none，显示出来为display:block-->
	<div class="pop_bg" style="display: none" id="detailPop">
		<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
		<div class="pop_main"
			style="width: 690px; height: 501px; margin-left: -345px; margin-top: -250px;">
			<div class="pop_title">
				债权转出<a href="javascript:closeTranser();" class="close_pop"></a>
			</div>
			<div class="pop_content">
				<div class="pop_field clearfix">
				    <input type="hidden" id="transfer_project_id" name="transfer_project_id" value="" />
					<dl class="item">
						<dt>项目名称</dt>
						<dd title="" id="ts_project_name"></dd>
					</dl>
					<dl class="item">
						<dt>年化利率</dt>
						<dd id="ts_project_annualized_rate">%</dd>
					</dl>
					<dl class="item">
						<dt>到期时间</dt>
						<dd id="ts_end_date"></dd>
					</dl>
					<dl class="item">
						<dt>债权取得方式</dt>
						<dd id="ts_get_type"></dd>
					</dl>
					<dl class="item">
						<dt>债权取得日期</dt>
						<dd id="ts_op_dt"></dd>
					</dl>
					<dl class="item">
						<dt>原始投资金额</dt>
						<dd id="ts_amount">元</dd>
					</dl>
					<dl class="item">
						<dt>已收本息</dt>
						<dd id="ts_has_recive">元</dd>
					</dl>
					<dl class="item">
						<dt>当前债权金额</dt>
						<dd id="ts_will_revive_principal">元</dd>
					</dl>
					<dl class="item">
						<dt>当前待收本息</dt>
						<dd id="ts_will_revive">元</dd>
					</dl>
					<dl class="item">
						<dt>预计转出金额</dt>
						<dd>
							<span><input name="" type="text" placeholder="0"
								readonly="true" class="w100 mb0" id="transferMoney" /></span> 元
						</dd>
					</dl>
					<dl class="item">
						<dt>转让费用</dt>
						<dd id="ts_service_charge">元</dd>
					</dl>
					<dl class="item">
						<dt>截止日期</dt>
						<dd>
							<span><input name=""  style="cursor:pointer" type="text" id="transferDateLine"
								readonly="true" class="w100 mb0"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" /></span>&nbsp;
						</dd>
					</dl>
				</div>

				<div class="mtb20 clearfix lh36">
					<p class="fl">
						<!--选中的class为“selected”，未选中的class为“unselected”-->
						<span class="checkbox_2"
							style="position: relative; top: 3px; margin-right: 5px;"><i id="checkbox_agreement"
							class="selected"></i></span>我已阅读并同意签署 <a href="javascript:viewAgreement();"  class="btn_text_blue2">《债权转让协议》</a>
					</p>
					<div class="code_area fr mb0 clearfix">
						<b>验证码</b>
						<p class="clearfix">
							<input name="" type="text" class="w100" id="validateCodeInput"
								maxlength="4"><a href="#" class="vcode"><img
								src="${ctxStatic}/modules/front/images/util/img_vcode.png"
								id="validateCode" onClick="changeCode()"></a>
						</p>
					</div>
				</div>

				<div class="btn_group_one tline mg0 pt20">
					<a href="javascript:doTransfer();" class="btn_brown_bc">转出</a>
					<a href="javascript:closeTranser();" class="btn_gray_bc">取消</a>
				</div>
			</div>
		</div>
	</div>
	<div class="bg_789_bottom"></div>

	<!--提示框JS-->
	<script type="text/javascript">
		var selectId = "";
		$(function() {
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
		function closeTranser() {
			$('#detailPop').hide();
		}
		function toTransferDemo() {
			toTransfer(3);
		}
		function showPlanListDemo() {
			showPlanList(3);
		}
		function doTransfer() {
			if($('#checkbox_agreement').hasClass("unselected")){
				$.jBox.alert("您必须同意借款协议，才能进行下一步操作！","提示");
				return false;
			}
			var transferMoney = $('#transferMoney').val();

			var transferDateLine = $('#transferDateLine').val();
			var now = new Date();
			var nowStr = now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();
			if(!dateCompare(nowStr,transferDateLine)){
				$.jBox.alert("转让截止日期不能在当前日期之前","提示");
				return ;
			}
			var validateCode = $('#validateCodeInput').val();
			$.ajax({
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
										$.ajax({
											type : 'get',
											url : '${ctxFront}/customer/investment/do_transfer',
											data : {
												recordId : selectId,
												money : transferMoney,
												dateLine : transferDateLine
											},
											dataType : 'json',
											success : function(data) {
												if (data.flag == 'ok') {
													$.jBox.tip("转让成功","success");
													window.location = "${ctxFront}/customer/investment/project_cyz";
												} else {
													$.jBox.error(data.mes,"提示");
												}
											}
										});
									} else {
										jBox.tip("取消", 'info');
									}
									return true;
								};
								$.jBox.confirm("你将要转让此投资债权，转让额为" + transferMoney
										+ "元，请确认?", "操作提示",
										submit, {
											buttons : {
												'确定' : 1,
												'取消' : -1
											}});
							}else {
								$.jBox.error("验证码填写错误！",'提示');
							}
						}
			});
		}
		function changeCode() {
			$('#validateCode').attr(
					'src',
					'${pageContext.request.contextPath}/servlet/validateCodeServlet?'
							+ new Date().getTime());
		}
		function toTransfer(id) {
			$('#detailPop').show();
			selectId = id;
			$
					.ajax({
						type : 'post',
						url : '${ctxFront}/customer/investment/project_cyz_transfer_detail',
						data : {
							recordId : id
						},
						dataType : 'json',
						success : function(data) {
							var project = data.pro;
							var invest = data.invest;
							$('#ts_project_name').html(project.projectName);
							$('#ts_project_annualized_rate').html(
									(project.annualizedRate * 100).toFixed(2) + "%");
							var endDate = new Date(project.lastRepaymentDate);
							$('#ts_end_date').html(formatDate(endDate));
							if (invest.transferProjectId == '0') {
								$('#ts_get_type').html('直接投资');
							} else {
								$('#ts_get_type').html('债权投资');
							}
							$('#ts_op_dt').html(invest.opDt);
							$('#ts_amount').html(invest.amount + "元");
							$('#ts_amount').html(invest.amount + "元");
							$('#transferMoney').val(invest.willReceivePrincipal);
							$('#transfer_project_id').val(invest.transferProjectId);
							$('#ts_has_recive').html(invest.hasReceive + "元");
							$('#ts_will_revive_principal').html(
									invest.willReceivePrincipal + "元");
							var willRevive = invest.willReceivePrincipal
									+ invest.willReceiveInterest;
							
							$('#ts_will_revive').html(
									willRevive.toFixed(2) + "元");
							$('#ts_service_charge').html(data.serviceCharge);
							$('#transferDateLine').val(data.discountDate);
							$("#validateCode")
									.attr("src",
											"${pageContext.request.contextPath}/servlet/validateCodeServlet");
						}
					});
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

		function formatDate(now) {
			var year = now.getFullYear();
			var month = now.getMonth() + 1; //js从0开始取 
			var date1 = now.getDate();
			var hour = now.getHours();
			var minutes = now.getMinutes();
			var second = now.getSeconds();
			return year + "-" + month + "-" + date1;
		}

		function dateCompare(startdate, enddate) {
			var arr = startdate.split("-");
			var starttime = new Date(arr[0], arr[1], arr[2]);
			var starttimes = starttime.getTime();

			var arrs = enddate.split("-");
			var lktime = new Date(arrs[0], arrs[1], arrs[2]);
			var lktimes = lktime.getTime();

			if (starttimes >= lktimes) {
				return false;
			} else
				return true;

		}
		$(function(){
			//阅读并同意签署协议：选择或不选
			$("#checkbox_agreement").click(function(){
				$(this).toggleClass("selected");
				$(this).toggleClass("unselected");
			});
		});
		//查看借款协议
		function viewAgreement(){
			var transferProjectId = $('#transfer_project_id').val();
			var amount = $('#transferMoney').val();
			window.open("${ctxFront}/agreement/transfer?transferProjectId=" + transferProjectId + "&type=0&amount="+amount);
		}
	</script>

</body>
</html>
