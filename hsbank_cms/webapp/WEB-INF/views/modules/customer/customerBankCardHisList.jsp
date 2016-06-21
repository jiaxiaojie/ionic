<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员银行卡历史变更管理</title>
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
		<li><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${accountId}">会员<shiro:hasPermission name="customer:customerBankCard:edit">${not empty accountId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerBankCard:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a href="${ctx}/customer/customerBankCardHis/">会员银行卡历史变更列表</a></li>
		<shiro:hasPermission name="customer:customerBankCardHis:edit"><li><a href="${ctx}/customer/customerBankCardHis/form">会员银行卡历史变更添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerBankCardHis" action="${ctx}/customer/customerBankCardHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>当前银行卡号</th>
				<th>银行卡填写时间</th>
				<th>银行卡种类</th>
				<th>银行卡归属</th>
				<th>银行卡币种</th>
				<th>最后一次修改时间</th>
				<th>操作终端</th>
				<th>操作时间</th>
				<shiro:hasPermission name="customer:customerBankCardHis:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerBankCardHis">
			<tr>
				<td>
					${customerBankCardHis.accountName}
				</td>
				<td>
					${customerBankCardHis.cardNum}
				</td>
				<td>
					<fmt:formatDate value="${customerBankCardHis.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(customerBankCardHis.cardType, 'customer_bank', '')}
				</td>
				<td>
					${fns:getDictLabel(customerBankCardHis.bankInfo, 'customer_bank_card_area_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(customerBankCardHis.currencyType, 'customer_bank_card_currency_type_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${customerBankCardHis.lastModifyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(customerBankCardHis.opTermType, 'op_term_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${customerBankCardHis.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerBankCardHis:edit"><td>
    				<a href="${ctx}/customer/customerBankCardHis/form?id=${customerBankCardHis.id}">修改</a>
					<a href="${ctx}/customer/customerBankCardHis/delete?id=${customerBankCardHis.id}" onclick="return confirmx('确认要删除该会员银行卡历史变更吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>