<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账户余额汇总管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerBalance/list">会员账户余额汇总列表</a></li>
		<shiro:hasPermission name="customer:customerBalance:edit"><li><a href="${ctx}/customer/customerBalance/form">会员账户余额汇总添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerBalance" action="${ctx}/customer/customerBalance/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登录名：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>会员姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>账户余额值：</label>
				<form:input path="beginGoldBalance" htmlEscape="false" class="input-medium"/> - 
				<form:input path="endGoldBalance" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>当前花生豆值：</label>
				<form:input path="customerIntegralSnapshot.beginIntegralBalance" htmlEscape="false" class="input-medium"/> - 
				<form:input path="customerIntegralSnapshot.endIntegralBalance" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>当前代币值：</label>
				<form:input path="customerGoldCoinSnapshot.beginGoldBalance" htmlEscape="false" class="input-medium"/> - 
				<form:input path="customerGoldCoinSnapshot.endGoldBalance" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>用户名</th>
				<th>当前账户余额值</th>
				<th>冻结资金</th>
				<th>待还借款</th>
				<th>累计融资</th>
				<th>还款总额</th>
				<th>还款本息</th>
				<th>还款逾期罚款</th>
				<th>还款提前还款</th>
				<th>30天内应还款项</th>
				<th>待收收益</th>
				<th>累计收益</th>
				<th>累计投资</th>
				<th>收款总额</th>
				<th>收款本息</th>
				<th>收款逾期罚款</th>
				<th>收款提前还款</th>
				<th>收款转让金</th>
				<th>账户净资产</th>
				<th>理财资产</th>
				<th>已充值总额</th>
				<th>已提现总额</th>
				<th>充值次数</th>
				<th>提现次数</th>
				<th>投资次数</th>
				<th>撤消次数</th>
				<th>转让次数</th>
				<th>受转让次数</th>
				<th>第一笔充值时间</th>
				<th>最后一笔变更时间</th>
				<shiro:hasPermission name="customer:customerBalance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerBalance">
			<tr>
				<td>
					${customerBalance.accountName}
				</td>
				<td>
					${customerBalance.customerName}
				</td>
				<td>
					${customerBalance.goldBalance}
				</td>
				<td>
					${customerBalance.congealVal}
				</td>
				<td>
					${customerBalance.willLoan}
				</td>
				<td>
					${customerBalance.sumLoan}
				</td>
				<td>
					${customerBalance.repaymentMoney}
				</td>
				<td>
					${customerBalance.repaymentPrincipal}
				</td>
				<td>
					${customerBalance.repaymentLateMoney}
				</td>
				<td>
					${customerBalance.repaymentPreMoney}
				</td>
				<td>
					${customerBalance.repayment30dWill}
				</td>
				<td>
					${customerBalance.willProfit}
				</td>
				<td>
					${customerBalance.sumProfit}
				</td>
				<td>
					${customerBalance.sumInvestment}
				</td>
				<td>
					${customerBalance.receiveMoney}
				</td>
				<td>
					${customerBalance.receivePrincipal}
				</td>
				<td>
					${customerBalance.receiveLateMoney}
				</td>
				<td>
					${customerBalance.receivePreMoney}
				</td>
				<td>
					${customerBalance.receiveTransferMoney}
				</td>
				<td>
					${customerBalance.netAssets}
				</td>
				<td>
					${customerBalance.financialAssets}
				</td>
				<td>
					${customerBalance.sumRecharge}
				</td>
				<td>
					${customerBalance.sumWithdraw}
				</td>
				<td>
					${customerBalance.rechargeCount}
				</td>
				<td>
					${customerBalance.withdrawCount}
				</td>
				<td>
					${customerBalance.investmentCount}
				</td>
				<td>
					${customerBalance.cancelCount}
				</td>
				<td>
					${customerBalance.transferCount}
				</td>
				<td>
					${customerBalance.acceptCount}
				</td>
				<td>
					<fmt:formatDate value="${customerBalance.firstGetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${customerBalance.lastChangeDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerBalance:view"><td>
    				<a href="${ctx}/customer/customerBalance/form?id=${customerBalance.accountId}">详情</a>
    				<a href="${ctx}/customer/customerBalanceHis/list?accountId=${customerBalance.accountId}">余额流水</a>
    				<a href="${ctx}/customer/customerIntegralHis/list?accountId=${customerBalance.accountId}">花生豆流水</a>
    				<a href="${ctx}/customer/customerGoldCoinHis/list?accountId=${customerBalance.accountId}">代金币流水</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>