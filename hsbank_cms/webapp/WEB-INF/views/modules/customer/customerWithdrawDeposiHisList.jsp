<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员提现额流水管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerWithdrawDeposiHis/">会员提现额流水列表</a></li>
		<shiro:hasPermission name="customer:customerWithdrawDeposiHis:edit"><li><a href="${ctx}/customer/customerWithdrawDeposiHis/form">会员提现额流水添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerWithdrawDeposiHis" action="${ctx}/customer/customerWithdrawDeposiHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>操作时间：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerWithdrawDeposiHis.beginOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerWithdrawDeposiHis.endOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>变更分值</th>
				<th>变更原因</th>
				<th>关联活动</th>
				<th>提现流水号</th>
				<th>操作时间</th>
				<th>操作终端</th>
				<shiro:hasPermission name="customer:customerWithdrawDeposiHis:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerWithdrawDeposiHis">
			<tr>
				<td><a href="${ctx}/customer/customerWithdrawDeposiHis/form?id=${customerWithdrawDeposiHis.id}">
					${customerWithdrawDeposiHis.accountId}
				</a></td>
				<td>
					${customerWithdrawDeposiHis.changeVal}
				</td>
				<td>
					${customerWithdrawDeposiHis.changeReason}
				</td>
				<td>
					${customerWithdrawDeposiHis.relActivity}
				</td>
				<td>
					${customerWithdrawDeposiHis.thirdPartyOrder}
				</td>
				<td>
					<fmt:formatDate value="${customerWithdrawDeposiHis.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerWithdrawDeposiHis.opTermType}
				</td>
				<shiro:hasPermission name="customer:customerWithdrawDeposiHis:edit"><td>
    				<a href="${ctx}/customer/customerWithdrawDeposiHis/form?id=${customerWithdrawDeposiHis.id}">修改</a>
					<a href="${ctx}/customer/customerWithdrawDeposiHis/delete?id=${customerWithdrawDeposiHis.id}" onclick="return confirmx('确认要删除该会员提现额流水吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>