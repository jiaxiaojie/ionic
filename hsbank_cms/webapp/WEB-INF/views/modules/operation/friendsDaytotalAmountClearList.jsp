<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小伙伴投资额度清单</title>
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
		<li>小伙伴投资额度清单</li>
	</ul><br/>
	<form id="searchForm" action="${ctx}/operation/operationData/InvestmentStatistics" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>投资日期：</label>
				<input type="text" disabled="disabled" value="<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"  />" />
			</li> 
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>投资人</th>
				<th>投资时间</th>
				<th>投资金额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="map">
			<tr>
			    <td>
				    ${map.customerName}
				</td>
			    <td id="date">
					<fmt:formatDate value="${map.date}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				    <fmt:formatNumber value="${map.amount}" pattern="#0.00" />
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>