<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/tzgl_zqzr.css?${version }" rel="stylesheet"/>
		<title></title>
	</head>
	<body>
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div id="tab_bar" style="padding-left:20px;">
				<li id="item_1" class="item"><span class="selected">可转让项目</span></li>
				<li id="item_2" class="item"><span>已转让项目</span></li>
			</div>
			<!-- 可转让项目 -->
			<div id="record_list" class="show">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>项目名称</th>
							<th>项目本金（元）</th>
							<th>起息日</th>
							<th>到期日</th>
							<th>还款方式</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="canTransfer" items="${canTransferList }" varStatus="status">
						<tr>
							<td>${status.index + 1 }</td>
							<td>${canTransfer.projectBaseInfo.projectName }</td>
							<td>${canTransfer.amount }</td>
							<td>${canTransfer.projectBaseInfo.beginPlannedRepaymentDate }</td>
							<td>${canTransfer.projectBaseInfo.endPlannedRepaymentDate }</td>
							<td>${fns:getDictLabel(canTransfer.projectBaseInfo.repaymentMode,'project_repayment_mode_dict','')}</td>
							<td><a>申请转让</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 已转让项目 -->
			<div id="finished_list" class="hide">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>项目名称</th>
							<th>项目本金（元）</th>
							<th>起息日</th>
							<th>到期日</th>
							<th>还款方式</th>
							<th>实收收益（元）</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>车辆抵押贷款200号</td>
							<td>15000</td>
							<td>2015-01-01</td>
							<td>2017-01-01</td>
							<td>到期还本付息</td>
							<td>1000.00</td>
						</tr>
						<tr>
							<td>1</td>
							<td>车辆抵押贷款200号</td>
							<td>15000</td>
							<td>2015-01-01</td>
							<td>2017-01-01</td>
							<td>到期还本付息</td>
							<td>1000.00</td>
						</tr>
						<tr>
							<td>1</td>
							<td>车辆抵押贷款200号</td>
							<td>15000</td>
							<td>2015-01-01</td>
							<td>2017-01-01</td>
							<td>到期还本付息</td>
							<td>1000.00</td>
						</tr>
						<tr>
							<td>1</td>
							<td>车辆抵押贷款200号</td>
							<td>15000</td>
							<td>2015-01-01</td>
							<td>2017-01-01</td>
							<td>到期还本付息</td>
							<td>1000.00</td>
						</tr>
						<tr>
							<td>1</td>
							<td>车辆抵押贷款200号</td>
							<td>15000</td>
							<td>2015-01-01</td>
							<td>2017-01-01</td>
							<td>到期还本付息</td>
							<td>1000.00</td>
						</tr>
					</tbody>
				</table>
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
						$('#record_list').show();
						$('#finished_list').hide();
					} else if ($('#item_2 span').hasClass("selected")){
						$('#record_list').hide();
						$('#finished_list').show();
					}
				});
			});
		</script>
	</body>
</html>