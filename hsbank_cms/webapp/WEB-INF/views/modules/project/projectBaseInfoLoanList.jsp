<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借贷产品管理</title>
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
		<li class="active"><a href="${ctx}/project/projectBaseInfo/loanlist">合同放款列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="projectBaseInfo" action="${ctx}/project/projectBaseInfo/loanlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>项目类型：</label>
				<form:select path="projectTypeCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>还款方式：</label>
				<form:select path="repaymentMode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_repayment_mode_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>期限类型：</label>
				<form:select path="durationType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_duration_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否放款：</label>
				<form:select path="isLoan" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label title="计划还款日期">还款日期：</label>
				<input name="beginPlannedRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectBaseInfo.beginPlannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endPlannedRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectBaseInfo.endPlannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>项目编号</th>
				<th>项目类型</th>
				<th>期限类型</th>
				<th>是否放款</th>
				<th>项目名称</th>
				<th>还款方式</th>
				<th>本期融资金额</th>
				<th>已融资金额</th>
				<th>服务费</th>
				<th>自动还款授权</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectBaseInfo">
			<tr>
				<td>
				  <a href="${ctx}/project/projectBaseInfo/loanform?backPath=loanlist&id=${projectBaseInfo.projectId}">
						${p2p:abbrev(projectBaseInfo.projectCode,10)}
				  </a>
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.projectTypeCode, 'project_type_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.durationType, 'project_duration_type_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.isLoan, 'yes_no', '')}
				</td>
				<td>
					${p2p:abbrev(projectBaseInfo.projectName,10)}
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}
				</td>
				<td>
				<fmt:formatNumber value="${projectBaseInfo.financeMoney}" pattern="#0.00"/>
				</td>
				<td>
				<fmt:formatNumber value="${projectBaseInfo.pes.endFinanceMoney}" pattern="#0.00"/>
				</td>
				<td>
				    <fmt:formatNumber value="${projectBaseInfo.pes.sumServiceCharge}" pattern="#0.00"/>
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.isAutoRepay, 'project_is_auto_repay_dict', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>