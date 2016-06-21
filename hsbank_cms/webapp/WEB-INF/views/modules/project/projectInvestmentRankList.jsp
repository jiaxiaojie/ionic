<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投资排行管理</title>
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
		<li class="active"><a href="${ctx}/project/projectInvestmentRank/list">投资排行列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="projectInvestmentRank" action="${ctx}/project/projectInvestmentRank/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>投资人：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>投资金额：</label>
				<form:input path="beginAmount" htmlEscape="false" class="input-medium" size="8"/> -
				<form:input path="endAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('investment_rank_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>数据时间：</label>
				<input name="beginDataDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectInvestmentRank.beginDataDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endDataDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectInvestmentRank.endDataDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>投资人</th>
				<th>投资金额</th>
				<th>名次</th>
				<th>类型</th>
				<th>数据时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectInvestmentRank">
			<tr>
				<td><a href="${ctx}/project/projectInvestmentRank/form?id=${projectInvestmentRank.id}">
					${projectInvestmentRank.customerName}
				</a></td>
				<td>
				<fmt:formatNumber value="${projectInvestmentRank.amount}" pattern="#0.00"/>
				</td>
				<td>
					${projectInvestmentRank.rank}
				</td>
				<td>
					${fns:getDictLabel(projectInvestmentRank.type, 'investment_rank_type_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${projectInvestmentRank.dataDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>