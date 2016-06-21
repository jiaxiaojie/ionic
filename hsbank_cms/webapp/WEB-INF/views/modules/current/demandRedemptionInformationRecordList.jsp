<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期赎回信息记录管理</title>
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
		<li class="active"><a href="${ctx}/current/demandRedemptionInformationRecord/">活期赎回记录列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="demandRedemptionInformationRecord" action="${ctx}/current/demandRedemptionInformationRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>记录编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li> --%>
			<li><label>申请编号：</label>
				<form:input path="redemptionId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>申请时间：</label>
				<input name="reedmptionDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${demandRedemptionInformationRecord.reedmptionDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<%-- <li><label>记录原因：</label>
				<form:input path="infoReason" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li> --%>
			<li><label>状态：</label>
				<form:select path="status"  class="input-medium" >
					 <form:option value="" label="全部"/> 
					<form:options items="${fns:getDictList('redemption_execution_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<!-- <th>记录编号</th> -->
				<th>赎回申请编号</th>
				<th>赎回申请时间</th>
				<th>记录产生原因</th>
				<th>融资人账户余额</th>
				<th>申请赎回本金</th>
				<th>操作结果</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="demandRedemptionInformationRecord">
			<tr>
				<td><a href="${ctx}/current/demandRedemptionInformationRecord/form?id=${demandRedemptionInformationRecord.id}">
					<%-- ${demandRedemptionInformationRecord.id} --%>
					${demandRedemptionInformationRecord.redemptionId}
				</a></td>
				<%-- <td>
					${demandRedemptionInformationRecord.redemptionId}
				</td> --%>
				<td>
					<fmt:formatDate value="${demandRedemptionInformationRecord.reedmptionDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${demandRedemptionInformationRecord.infoReason}
				</td>
				<td>
				<fmt:formatNumber value="${demandRedemptionInformationRecord.accountAmount}" pattern="#0.00"/>
				</td>
				<td>
				<fmt:formatNumber value="${demandRedemptionInformationRecord.redeemPrincipal}" pattern="#0.00"/>
				</td>
				<td>
					${fns:getDictLabel(demandRedemptionInformationRecord.status, 'redemption_execution_status', '')}
				</td>
				<%--  <shiro:hasPermission name="current:demandRedemptionInformationRecord:edit"><td>
					<a href="${ctx}/current/demandRedemptionInformationRecord/delete?id=${demandRedemptionInformationRecord.id}" onclick="return confirmx('确认要删除该活期赎回信息记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>  --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>