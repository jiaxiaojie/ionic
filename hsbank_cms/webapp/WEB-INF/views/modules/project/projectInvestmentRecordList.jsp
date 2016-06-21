<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投资记录管理</title>
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
	<form:form id="searchForm" modelAttribute="projectInvestmentRecord" action="${ctx}/project/projectInvestmentRecord/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="queryProjectId" name="queryProjectId" type="hidden" value="${projectInvestmentRecord.queryProjectId}"/>
		<ul class="ul-form">
			<li><label>投资时间：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectInvestmentRecord.beginOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectInvestmentRecord.endOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>投资状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_investment_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>投资人登录名</th>
				<th>投资人姓名</th>
				<th>投资人手机号</th>
				<th>投资金额</th>
				<th>实际投资金额</th>
				<th>现金券抵用金额</th>
				<th>平台垫付金额</th>
				<th>投资方式</th>
				<th>操作终端</th>
				<th>投资时间</th>
				<th>投资状态</th>
				<th>投资交易第三方流水编号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectInvestmentRecord">
			<tr>
				<td>
					${projectInvestmentRecord.ca.accountName}
				</td>
				<td>
					${projectInvestmentRecord.cb.customerName}
				</td>
				<td>
					${projectInvestmentRecord.cb.mobile}
				</td>
				<td>
				<fmt:formatNumber value="${projectInvestmentRecord.amount}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectInvestmentRecord.actualAmount}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectInvestmentRecord.ticketAmount}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectInvestmentRecord.platformAmount}" pattern="#0.00"/>
					
				</td>
				<td>
					${fns:getDictLabel(projectInvestmentRecord.investmentType, 'project_investment_type_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(projectInvestmentRecord.opTerm, 'op_term_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${projectInvestmentRecord.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(projectInvestmentRecord.status, 'project_investment_status_dict', '')}
				</td>
				<td>
					${projectInvestmentRecord.thirdPartyOrder}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>