<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/tzgl_tzxm.css?${version }" rel="stylesheet"/>
		<title></title>
		<script>
	function selectItem(index){
		if(index==1){window.location="${ctxFront}/customer/investment/project_cyz";}
		if(index==2){window.location="${ctxFront}/customer/investment/project_zcz";}
		if(index==3){window.location="${ctxFront}/customer/investment/project_tbz";}
		if(index==4){window.location="${ctxFront}/customer/investment/project_zrz";}
		if(index==5){window.location="${ctxFront}/customer/investment/project_yzc";}
		if(index==6){window.location="${ctxFront}/customer/investment/project_yjs";}
	}
</script>
	</head>
	<body>
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div id="tab_bar" style="padding-left:20px;">
				<li id="item_1" onclick="selectItem(1)" class="item"><span class="selected">持有中</span></li>
				<li id="item_2" onclick="selectItem(2)" class="item"><span>转出中</span></li>
				<li id="item_3" onclick="selectItem(3)" class="item"><span>投标中</span></li>
				<li id="item_4" onclick="selectItem(4)" class="item"><span>转出中</span></li>
				<li id="item_5" onclick="selectItem(5)" class="item"><span>已转出</span></li>
				<li id="item_6" onclick="selectItem(6)" class="item"><span>已结束</span></li>
			</div>
			<!-- 持有中项目 -->
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
						<c:forEach var="investmentRunningProject" items="${investmentRunningProjectList }" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${investmentRunningProject.projectBaseInfo.projectName }</td>
							<td>${investmentRunningProject.amount }</td>
							<td><fmt:formatDate value="${investmentRunningProject.projectBaseInfo.biddingDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${investmentRunningProject.projectBaseInfo.lastRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${fns:getDictLabel(investmentRunningProject.projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}</td>
							<td><a>申请转让</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 已结束项目 -->
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
						<c:forEach var="investmentEndProject" items="${investmentEndProjectList }" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${investmentEndProject.projectBaseInfo.projectName }</td>
							<td>${investmentEndProject.amount }</td>
							<td>${investmentEndProject.projectBaseInfo.biddingDeadline }</td>
							<td>${investmentEndProject.projectBaseInfo.lastRepaymentDate }</td>
							<td>${fns:getDictLabel(investmentEndProject.projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}</td>
							<td>${investmentEndProject.realProfit }</td>
						</tr>
						</c:forEach>
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