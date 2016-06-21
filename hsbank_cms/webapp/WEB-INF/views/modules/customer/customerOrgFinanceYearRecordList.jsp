<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业会员财务年表管理</title>
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
		<li><a href="${ctx}/customer/customerAccount/">会员列表</a></li>
		<li><a href="${ctx}/customer/customerBase/form?id=${customerId}">会员<shiro:hasPermission name="customer:customerBase:edit">${not empty customerId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerBase:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a href="${ctx}/customer/customerOrgFinanceYearRecord/list?customerId=${customerId}">企业会员财务年表列表</a></li>
		<shiro:hasPermission name="customer:customerOrgFinanceYearRecord:edit"><li><a href="${ctx}/customer/customerOrgFinanceYearRecord/form?customerId=${customerId}">企业会员财务年表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerOrgFinanceYearRecord" action="${ctx}/customer/customerOrgFinanceYearRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>企业名称</th>
				<th>年度</th>
				<th>主营收入（万）</th>
				<th>毛利润（万）</th>
				<th>净利润（万）</th>
				<th>总资产（万）</th>
				<th>净资产（万）</th>
				<shiro:hasPermission name="customer:customerOrgFinanceYearRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerOrgFinanceYearRecord">
			<tr>
				<td>
					${customerOrgFinanceYearRecord.accountName}
				</td>
				<td>
					${customerOrgFinanceYearRecord.customerName}
				</td>
				<td>
					${customerOrgFinanceYearRecord.yearId}
				</td>
				<td>
					${customerOrgFinanceYearRecord.mainRevenue}
				</td>
				<td>
					${customerOrgFinanceYearRecord.grossProfit}
				</td>
				<td>
					${customerOrgFinanceYearRecord.netProfit}
				</td>
				<td>
					${customerOrgFinanceYearRecord.totalAssets}
				</td>
				<td>
					${customerOrgFinanceYearRecord.netAssets}
				</td>
				<shiro:hasPermission name="customer:customerOrgFinanceYearRecord:edit"><td>
    				<a href="${ctx}/customer/customerOrgFinanceYearRecord/form?id=${customerOrgFinanceYearRecord.id}">修改</a>
					<a href="${ctx}/customer/customerOrgFinanceYearRecord/delete?id=${customerOrgFinanceYearRecord.id}" onclick="return confirmx('确认要删除该企业会员财务年表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>