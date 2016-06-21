<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>还款记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/project/projectBaseInfo/${modelMenus.backPath}">借贷产品列表</a></li>
		<li class="active"><a HERF="#">借贷产品信息</a></li>
	</ul><br/>
	<jsp:include page="./common/jdprojectInfoMenu.jsp"/>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>还款日期</th>
				<th>还款金额</th>
				<th>本金</th>
				<th>利息</th>
				<th>剩余本金</th>
				<th>剩余本息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectRepaymentPlan">
			<tr>
				<td>
					<fmt:formatDate value="${projectRepaymentPlan.planDate}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
				<fmt:formatNumber value="${projectRepaymentPlan.planMoney}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentPlan.principal}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentPlan.interest}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentPlan.remainingPrincipal}" pattern="#0.00"/>
				
				</td>
				<td>
					<fmt:formatNumber value="${projectRepaymentPlan.remainingPrincipalInterest}" pattern="#0.00"/>

				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>