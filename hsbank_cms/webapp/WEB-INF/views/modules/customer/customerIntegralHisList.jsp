<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员花生豆变更流水管理</title>
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
		<li><a href="${ctx}/customer/customerBalance/list">会员账户余额汇总列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerIntegralHis/">会员花生豆变更流水列表</a></li>
		<shiro:hasPermission name="customer:customerIntegralHis:edit"><li><a href="${ctx}/customer/customerIntegralHis/form">会员花生豆变更流水添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerIntegralHis" action="${ctx}/customer/customerIntegralHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<form:hidden path="accountId"/>
			<li><label>变更分值：</label>
				<form:input path="beginChangeVal" htmlEscape="false" class="input-medium"/> - 
				<form:input path="endChangeVal" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>变更分值</th>
				<th>变更原因</th>
				<th>关联活动</th>
				<th>兑换物品</th>
				<th>操作时间</th>
				<th>操作终端</th>
				<shiro:hasPermission name="customer:customerIntegralHis:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerIntegralHis">
			<tr>
				<td><a href="${ctx}/customer/customerIntegralHis/form?id=${customerIntegralHis.id}">
					${customerIntegralHis.changeVal}
				</a></td>
				<td>
					${customerIntegralHis.changeReason}
				</td>
				<td>
					${customerIntegralHis.relActivity}
				</td>
				<td>
					${customerIntegralHis.exchangeGoods}
				</td>
				<td>
					<fmt:formatDate value="${customerIntegralHis.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(customerIntegralHis.opTermType, 'op_term_dict', '')}
				</td>
				<shiro:hasPermission name="customer:customerIntegralHis:edit"><td>
    				<a href="${ctx}/customer/customerIntegralHis/form?id=${customerIntegralHis.id}">修改</a>
					<a href="${ctx}/customer/customerIntegralHis/delete?id=${customerIntegralHis.id}" onclick="return confirmx('确认要删除该会员花生豆变更流水吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>