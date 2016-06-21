<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户还款信息</title>
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
	<form:form id="searchForm" modelAttribute="repaymentSplitRecord" action="${ctx}/customer/customerAccountInfo/repaymentSplitRecordInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="payeeUserId" name="payeeUserId" type="hidden" value="${repaymentSplitRecord.payeeUserId}"/>
		<ul class="ul-form">
			<li><label>还款日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${repaymentSplitRecord.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${repaymentSplitRecord.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="0" label="待还款"/>
					<form:option value="1" label="已还款"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form:form id ="infoForm" class="breadcrumb form-search">
		<ul class="ul-form">

			<li><label><b>本金总额：</b></label>
				<b><fmt:formatNumber value="${sumPrincipal}" pattern="#0.00"/></b>
			</li>
			<li><label><b>利息总额：</b></label>
				<b><fmt:formatNumber value="${sumInterest}" pattern="#0.00"/></b>
			</li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>还款日期</th>
				<th>项目名称</th>
				<th>年化利率</th>
				<th>投资金额</th>
				<th>投资时间</th>
				<th>应收本金</th>
				<th>应收利息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="repaymentSplitRecordInfo">
			<tr>
				<td>
					<fmt:formatDate value="${repaymentSplitRecordInfo.repaymentDt}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${repaymentSplitRecordInfo.projectBaseInfo.projectName}
				</td>
				<td>
					<fmt:formatNumber value="${repaymentSplitRecordInfo.projectBaseInfo.annualizedRate * 100 }" pattern="#.#" />
				</td>
				<td>
					<fmt:formatNumber value="${repaymentSplitRecordInfo.investmentRecord.amount}" pattern="#0.00"/>
				</td>
				<td>
					<fmt:formatDate value="${repaymentSplitRecordInfo.investmentRecord.opDt}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${repaymentSplitRecordInfo.principal}
				</td>
				<td>
					${repaymentSplitRecordInfo.interest}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>