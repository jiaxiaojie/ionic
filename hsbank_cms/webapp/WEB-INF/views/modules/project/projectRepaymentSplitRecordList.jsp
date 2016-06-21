<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>还款明细管理</title>
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
		<li class="active"><a href="${ctx}/project/projectRepaymentSplitRecord/">还款明细列表</a></li>
		<shiro:hasPermission name="project:projectRepaymentSplitRecord:edit"><li><a href="${ctx}/project/projectRepaymentSplitRecord/form">还款明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="projectRepaymentSplitRecord" action="${ctx}/project/projectRepaymentSplitRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>还款流水号：</label>
				<form:input path="recordId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>项目流水号：</label>
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>还款人：</label>
				<form:input path="repaymentUserId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>投资流水编号：</label>
				<form:input path="investmentRecordId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>收款人：</label>
				<form:input path="payeeUserId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>还款流水号</th>
				<th>项目流水号</th>
				<th>还款人</th>
				<th>投资流水编号</th>
				<th>还款人账号</th>
				<th>收款人</th>
				<th>收款人账号</th>
				<th>分配金额</th>
				<th>本金</th>
				<th>利息</th>
				<th>剩余本金</th>
				<th>还款方式</th>
				<th>提前还款违约金额</th>
				<th>逾期还款违约金额</th>
				<th>还款时间</th>
				<th>分配第三方编号</th>
				<th>分配返回码</th>
				<th>分配状态</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<th>修改备注</th>
				<shiro:hasPermission name="project:projectRepaymentSplitRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectRepaymentSplitRecord">
			<tr>
				<td><a href="${ctx}/project/projectRepaymentSplitRecord/form?id=${projectRepaymentSplitRecord.id}">
					${projectRepaymentSplitRecord.recordId}
				</a></td>
				<td>
					${projectRepaymentSplitRecord.projectId}
				</td>
				<td>
					${projectRepaymentSplitRecord.repaymentUserId}
				</td>
				<td>
					${projectRepaymentSplitRecord.investmentRecordId}
				</td>
				<td>
					${projectRepaymentSplitRecord.repaymentAccount}
				</td>
				<td>
					${projectRepaymentSplitRecord.payeeUserId}
				</td>
				<td>
					${projectRepaymentSplitRecord.payeeAccount}
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentSplitRecord.money}" pattern="#0.00"/>					
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentSplitRecord.principal}" pattern="#0.00"/>				
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentSplitRecord.interest}" pattern="#0.00"/>
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentSplitRecord.remainedPrincipal}" pattern="#0.00"/>
				</td>
				<td>
					${fns:getDictLabel(projectRepaymentSplitRecord.repayType, 'project_repayment_type_dict', '')}
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentSplitRecord.prePenaltyMoney}" pattern="#0.00"/>
				</td>
				<td>
				<fmt:formatNumber value="${projectRepaymentSplitRecord.latePenaltyMoney}" pattern="#0.00"/>
				</td>
				<td>
					<fmt:formatDate value="${projectRepaymentSplitRecord.repaymentDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${projectRepaymentSplitRecord.thirdPartyOrder}
				</td>
				<td>
					${projectRepaymentSplitRecord.repayResult}
				</td>
				<td>
					${fns:getDictLabel(projectRepaymentSplitRecord.status, 'project_transer_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${projectRepaymentSplitRecord.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${projectRepaymentSplitRecord.modifyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${projectRepaymentSplitRecord.modifyRemark}
				</td>
				<shiro:hasPermission name="project:projectRepaymentSplitRecord:edit"><td>
    				<a href="${ctx}/project/projectRepaymentSplitRecord/form?id=${projectRepaymentSplitRecord.id}">修改</a>
					<a href="${ctx}/project/projectRepaymentSplitRecord/delete?id=${projectRepaymentSplitRecord.id}" onclick="return confirmx('确认要删除该还款明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>