<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动现金奖励记录管理</title>
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
		<li class="active"><a href="${ctx}/marketing/marketingMoneyAwardRecord/">活动现金奖励记录列表</a></li>
		<shiro:hasPermission name="marketing:marketingMoneyAwardRecord:edit"><li><a href="${ctx}/marketing/marketingMoneyAwardRecord/form">活动现金奖励记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="marketingMoneyAwardRecord" action="${ctx}/marketing/marketingMoneyAwardRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>金额：</label>
				<form:input path="amount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('marketing_money_award_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingMoneyAwardRecord.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>用户编号</th>
				<th>金额</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>完成时间</th>
				<shiro:hasPermission name="marketing:marketingMoneyAwardRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="marketingMoneyAwardRecord">
			<tr>
				<td><a href="${ctx}/marketing/marketingMoneyAwardRecord/form?id=${marketingMoneyAwardRecord.id}">
					${marketingMoneyAwardRecord.accountId}
				</a></td>
				<td>
					${marketingMoneyAwardRecord.amount}
				</td>
				<td>
					${fns:getDictLabel(marketingMoneyAwardRecord.status, 'marketing_money_award_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${marketingMoneyAwardRecord.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${marketingMoneyAwardRecord.finishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="marketing:marketingMoneyAwardRecord:edit"><td>
    				<a href="${ctx}/marketing/marketingMoneyAwardRecord/form?id=${marketingMoneyAwardRecord.id}">修改</a>
					<a href="${ctx}/marketing/marketingMoneyAwardRecord/delete?id=${marketingMoneyAwardRecord.id}" onclick="return confirmx('确认要删除该活动现金奖励记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>