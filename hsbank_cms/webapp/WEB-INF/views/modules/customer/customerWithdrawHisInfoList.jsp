<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员体现记录表管理</title>
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
		<li><a href="${ctx}/customer/customerAccountInfo/customerAccountInfoList">会员列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerAccountInfo/customerAccountInfo?accountId=${repaymentSplitRecord.payeeUserId}">会员详细信息</a></li>
	</ul><br/>
    <jsp:include page="./common/customerAccountInfoMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="customerWithdrawHis" action="${ctx}/customer/customerAccountInfo/customerWithdrawHisInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="accountId" name="accountId" type="hidden" value="${model.accountId}"/>
		<ul class="ul-form">
			<li><label>银行卡号：</label>
				<form:input path="bankCardNo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>提现时间：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerWithdrawHis.beginOpDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerWithdrawHis.endOpDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>体现金额</th>
				<th>银行卡号</th>
				<th>所属银行</th>
				<th>体现流水号</th>
				<th>提现时间</th>
				<shiro:hasPermission name="customer:customerWithdrawHis:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerWithdrawHis">
			<tr>
				<td>
					<fmt:formatNumber value="${customerWithdrawHis.amount}" pattern="#0.00"/>
				</td>
				<td>
					${customerWithdrawHis.bankCardNo}
				</td>
				<td>
				    ${fns:getDictLabel(customerWithdrawHis.bankCode, 'yeepay_bank_code_dict', '')}
				</td>
				<td>
					${customerWithdrawHis.thirdPartyReq}
				</td>
				<td>
					<fmt:formatDate value="${customerWithdrawHis.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerWithdrawHis:edit"><td>
    				<a href="${ctx}/customer/customerWithdrawHis/form?id=${customerWithdrawHis.id}">修改</a>
					<a href="${ctx}/customer/customerWithdrawHis/delete?id=${customerWithdrawHis.id}" onclick="return confirmx('确认要删除该会员体现记录表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>