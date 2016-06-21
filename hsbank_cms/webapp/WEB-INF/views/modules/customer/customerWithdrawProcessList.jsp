<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员提现审批管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerWithdrawProcess/">会员提现审批列表</a></li>
		<shiro:hasPermission name="customer:customerWithdrawProcess:edit"><li><a href="${ctx}/customer/customerWithdrawProcess/form">会员提现审批添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerWithdrawProcess" action="${ctx}/customer/customerWithdrawProcess/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>提现提交时间：</label>
				<input name="beginCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerWithdrawProcess.beginCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerWithdrawProcess.endCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>账号编号</th>
				<th>提现提交时间</th>
				<th>提现金额</th>
				<th>手续费</th>
				<th>用券金额</th>
				<th>审批时间</th>
				<th>审批状态</th>
				<th>审批人员</th>
				<th>提现执行流水号</th>
				<th>提现执行结果</th>
				<shiro:hasPermission name="customer:customerWithdrawProcess:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerWithdrawProcess">
			<tr>
				<td><a href="${ctx}/customer/customerWithdrawProcess/form?id=${customerWithdrawProcess.id}">
					${customerWithdrawProcess.accountId}
				</a></td>
				<td>
					<fmt:formatDate value="${customerWithdrawProcess.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerWithdrawProcess.money}
				</td>
				<td>
					${customerWithdrawProcess.serviceMoney}
				</td>
				<td>
					${customerWithdrawProcess.ticketMoney}
				</td>
				<td>
					<fmt:formatDate value="${customerWithdrawProcess.applyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerWithdrawProcess.applyStatus}
				</td>
				<td>
					${customerWithdrawProcess.applyUserId}
				</td>
				<td>
					${customerWithdrawProcess.thirdPartyReq}
				</td>
				<td>
					${customerWithdrawProcess.thirdPartyResult}
				</td>
				<shiro:hasPermission name="customer:customerWithdrawProcess:edit"><td>
    				<a href="${ctx}/customer/customerWithdrawProcess/form?id=${customerWithdrawProcess.id}">修改</a>
					<a href="${ctx}/customer/customerWithdrawProcess/delete?id=${customerWithdrawProcess.id}" onclick="return confirmx('确认要删除该会员提现审批吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>