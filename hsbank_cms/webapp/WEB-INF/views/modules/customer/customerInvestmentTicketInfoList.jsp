<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员现金券管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerAccountInfo/customerAccountInfo?accountId=${model.accountId}">会员详细信息</a></li>
	</ul><br/>
	<jsp:include page="./common/customerAccountInfoMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="customerInvestmentTicket" action="${ctx}/customer/customerAccountInfo/investmentTicketInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="accountId" name="accountId" type="hidden" value="${model.accountId}"/>
		<ul class="ul-form">
			<li><label>获得时间：</label>
				<input name="beginGetDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerInvestmentTicket.beginGetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endGetDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerInvestmentTicket.endGetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li class="clearfix"></li>
			<li><label>使用时间：</label>
				<input name="beginUseDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerInvestmentTicket.beginUseDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endUseDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerInvestmentTicket.endUseDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				&nbsp;【可用现金券&nbsp;总额:<fmt:formatNumber value="${ticketStatistics.sumDenomination }" pattern="#" />元&nbsp;数量:${ticketStatistics.count}】&nbsp;

				【本月获得 总额:<fmt:formatNumber value="${ticketStatistics.currentMonthSum }" pattern="#" />元&nbsp;数量:${ticketStatistics.currentMonthCount}】&nbsp;
				【累计获得 总额:<fmt:formatNumber value="${ticketStatistics.totalSum }" pattern="#" />元 &nbsp;数量:${ticketStatistics.totalCount}】</li>

		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>账号编号</th>
				<th>现金券类型编号</th>
				<th>现金券面值</th>
				<th>获得时间</th>
				<th>获得备注</th>
				<th>使用时间</th>
				<th>使用备注</th>
				<th>状态</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerInvestmentTicket">
			<tr>
				<td>
					${customerInvestmentTicket.accountId}
				</td>
				<td>
					${customerInvestmentTicket.ticketTypeId}
				</td>
				<td>
					${customerInvestmentTicket.investmentTicketType.denomination}
				</td>
				<td>
					<fmt:formatDate value="${customerInvestmentTicket.getDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerInvestmentTicket.getRemark}
				</td>
				<td>
					<fmt:formatDate value="${customerInvestmentTicket.useDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerInvestmentTicket.useRemark}
				</td>
				<td>
					${customerInvestmentTicket.status}
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>