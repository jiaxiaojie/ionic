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
				<th>还款流水号</th>
				<th>还款日期</th>
				<th>还款渠道</th>
				<th>还款总金额</th>
				<th>还款状态</th>
				<th>还款拆分余额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectRepaymentRecord">
			<tr>
				<td><a href="${ctx}/project/projectRepaymentRecord/form?id=${projectRepaymentRecord.recordId}">
					${projectRepaymentRecord.recordId}
				</a></td>
				<td>
					<fmt:formatDate value="${projectRepaymentRecord.repaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(projectRepaymentRecord.repaymentChannelId, 'op_term_dict', '')}
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentRecord.sumMoney}" pattern="#0.00"/>
				</td>
				<td>
					${projectRepaymentRecord.status}
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentRecord.splitBalance}" pattern="#0.00"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>