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
		<li class="active"><a href="${ctx}/customer/customerBalance/list">会员现金券列表</a></li>
		<shiro:hasPermission name="customer:customerInvestmentTicket:edit"><li><a href="${ctx}/customer/customerInvestmentTicket/form">会员现金券添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerInvestmentTicket" action="${ctx}/customer/customerInvestmentTicket/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>获得时间：</label>
				<input name="beginGetDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerInvestmentTicket.beginGetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endGetDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerInvestmentTicket.endGetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>账号编号</th>
				<th>现金券类型编号</th>
				<th>获得时间</th>
				<th>获得备注</th>
				<th>使用时间</th>
				<th>使用备注</th>
				<th>状态</th>
				<shiro:hasPermission name="customer:customerInvestmentTicket:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerInvestmentTicket">
			<tr>
				<td><a href="${ctx}/customer/customerInvestmentTicket/form?id=${customerInvestmentTicket.id}">
					${customerInvestmentTicket.accountId}
				</a></td>
				<td>
					${customerInvestmentTicket.ticketTypeId}
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
				<shiro:hasPermission name="customer:customerInvestmentTicket:edit"><td>
    				<a href="${ctx}/customer/customerInvestmentTicket/form?id=${customerInvestmentTicket.id}">修改</a>
					<a href="${ctx}/customer/customerInvestmentTicket/delete?id=${customerInvestmentTicket.id}" onclick="return confirmx('确认要删除该会员现金券吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>