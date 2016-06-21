<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>还款记录管理</title>
<meta name="decorator" content="default" />
<style type="text/css">
#div1 {height: 50px;width: 400px;float: left;font-size: medium;}
#div2 {height: 50px;width: 200px;float:right;font-size: medium;padding:0px;200px;0px;0px;}
#div_table {padding: 12px;12px;12px;12px;}
#td_second {font-size: 18px;}
</style>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
<form id="searchForm" action="${ctx}/project/projectRepaymentSplitRecord/repaymentList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<div id="div_table">
		<table>
			<tr id="tr_first" bgcolor="#FFFFF ">
				<td id="td_second" height="50" width="1000" align="center">${projectBaseInfo.projectName}</td>
			</tr>
		</table>
		<div>
			<div align="left" id="div1">
				<font color="red">项目本金(元):</font><br>${repaymentPlans.sumPrincipal }</div>
			<div align="right" id="div2">
				<font color="red">项目利息(元):</font><br>${repaymentPlans.sumInterest }</div>
		</div>
	</div>
			</form>
		<sys:message content="${message}" />
		<table id="contentTable"
			class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>还款日期</th>
					<th>应还本金(元)</th>
					<th>应还利息(元)</th>
					<th>支付情况</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="map">
					<tr>
						<td><fmt:formatDate value="${map.repaymentDt}" pattern="yyyy-MM-dd" /></td>
						<td>${map.AllPrincipal}</td>
						<td>${map.allInterest}</td>	
						<td>${fns:getDictLabel(map.status, 'project_repay_status_dict', '')}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pagination">${page}</div>
	</div>
</body>
</html>