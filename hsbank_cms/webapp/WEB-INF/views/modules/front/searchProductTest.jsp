<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic }/modules/front/css/index.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var pageNo = ${page.pageNo};
			var status = ${status};
			var duration = ${duration};
			var rate = ${rate};
			$(function(){
				$("#status li[value='${status}']").addClass('active');
				$("#duration li[value='${duration}']").addClass('active');
				$("#rate li[value='${rate}']").addClass('active');
				$("ul li").click(function(){
					$(this).siblings().removeClass('active').end().addClass('active');
				});
				$("#status li").click(function(){
					status = $(this).val();
					window.location.href = "${ctxFront }/project/search?status="
						+ status + "&duration=" + duration + "&rate="
						+ rate + "&pageNo=" + pageNo;
				});
				$("#duration li").click(function(){
					duration = $(this).val();
					window.location.href = "${ctxFront }/project/search?status="
						+ status + "&duration=" + duration + "&rate="
						+ rate + "&pageNo=" + pageNo;
				});
				$("#rate li").click(function(){
					rate = $(this).val();
					window.location.href = "${ctxFront }/project/search?status="
						+ status + "&duration=" + duration + "&rate="
						+ rate + "&pageNo=" + pageNo;
				});
			});
			function page(n,s){
				pageNo = n;
				window.location.href = "${ctxFront }/project/search?status="
					+ status + "&duration=" + duration + "&rate="
					+ rate + "&pageNo=" + pageNo;
			}
		</script>
</head>
<body>
	<div style="margin:0 auto;width:1360px;">
		<form:form id="searchForm" modelAttribute="projectBaseInfo" action="/fuding_p2p/f/project/search" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			项目状态：
			<ul id="status" class="nav nav-pills">
				<li value="0"><a href="javascript:void(0)">全部</a></li>
				<li value="1"><a href="javascript:void(0)">投标中</a></li>
				<li value="2"><a href="javascript:void(0)">投标结束</a></li>
				<li value="3"><a href="javascript:void(0)">还款中</a></li>
				<li value="4"><a href="javascript:void(0)">还款结束</a></li>
			</ul>
			项目期限：
			<ul id="duration" class="nav nav-pills">
				<li value="0"><a href="javascript:void(0)">全部</a></li>
				<li value="1"><a href="javascript:void(0)">0-3个月</a></li>
				<li value="2"><a href="javascript:void(0)">3-6个月</a></li>
				<li value="3"><a href="javascript:void(0)">6-9个月</a></li>
				<li value="4"><a href="javascript:void(0)">9-12个月</a></li>
				<li value="5"><a href="javascript:void(0)">12-15个月</a></li>
				<li value="6"><a href="javascript:void(0)">15个月以上</a></li>
			</ul>
			项目收益：
			<ul id="rate" class="nav nav-pills">
				<li value="0"><a href="javascript:void(0)">全部</a></li>
				<li value="1"><a href="javascript:void(0)">9%以下</a></li>
				<li value="2"><a href="javascript:void(0)">9-10%</a></li>
				<li value="3"><a href="javascript:void(0)">10-11%</a></li>
				<li value="4"><a href="javascript:void(0)">11-12%</a></li>
				<li value="5"><a href="javascript:void(0)">12%以上</a></li>
			</ul>
		</form:form>
	</div>
	<div class="bottom_area">
		<div class="product">
			<c:forEach items="${page.list}" var="project">
			<div class="list">
				<div class="item">
					<div class="top">
						<img src="${ctxStatic }/modules/front/images/asset_flag.png" alt="">
						<span>${project.aUser.customerName }</span>
						<img class="img" src="${ctxStatic }/modules/front/images/line-je.png" alt="">
						<span>${fns:getDictLabel(project.repaymentMode, 'project_repayment_mode_dict', '')}</span>
					</div>
					<div class="line"></div>
					<div class="item-title">${fns:getDictLabel(project.projectTypeCode, 'project_type_dict', '')} ${project.projectCode }</div>
					<div class="line"></div>
					<div class="item-line-1">
						<span class="span-1">${project.annualizedRate }</span>
						<span class="span-2">%</span>
						<span class="span-3">预期年化收益率</span>
					</div>
					<div class="clearfix"></div>
					<div class="item-line-2">
						<span>起投金额</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span>期限</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
					<div class="item-line-3">
						<span class="span-1">100</span>
						<span class="span-2">元</span>
						<span class="span-3">${project.projectDuration }</span>
						<span class="span-4">月</span>
					</div>
					<div class="line"></div>
					<div class="item-line-4">立即投资</div>
					<progress class="item-line-5" value="60" max="100"></progress>
					<div class="item-line-6">${project.pes.endFinanceMoney } / ${project.pes.financeMoney }</div>
					<div class="item-line-7">${project.projectIntroduce }</div>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>