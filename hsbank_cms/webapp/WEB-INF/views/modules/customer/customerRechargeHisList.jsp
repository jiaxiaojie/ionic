<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员充值记录管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerRechargeHis/">会员充值记录列表</a></li>
		<shiro:hasPermission name="customer:customerRechargeHis:edit"><li><a href="${ctx}/customer/customerRechargeHis/form">会员充值记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerRechargeHis" action="${ctx}/customer/customerRechargeHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>充值发起时间：</label>
				<input name="beginRequestDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerRechargeHis.beginRequestDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endRequestDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerRechargeHis.endRequestDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>充值发起时间</th>
				<th>充值金额</th>
				<th>充值方式</th>
				<th>充值备注</th>
				<th>充值响应时间</th>
				<th>充值响应状态</th>
				<th>充值流水号</th>
				<th>充值状态查询时间</th>
				<th>充值状态查询响应状态</th>
				<th>充值状态</th>
				<shiro:hasPermission name="customer:customerRechargeHis:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerRechargeHis">
			<tr>
				<td><a href="${ctx}/customer/customerRechargeHis/form?id=${customerRechargeHis.id}">
					${customerRechargeHis.accountId}
				</a></td>
				<td>
					<fmt:formatDate value="${customerRechargeHis.requestDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerRechargeHis.money}
				</td>
				<td>
					${fns:getDictLabel(customerRechargeHis.rechargeType, 'customer_recharge_type_dict', '')}
				</td>
				<td>
					${customerRechargeHis.remark}
				</td>
				<td>
					<fmt:formatDate value="${customerRechargeHis.responseDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerRechargeHis.responseCode}
				</td>
				<td>
					${customerRechargeHis.thirdPartyReq}
				</td>
				<td>
					<fmt:formatDate value="${customerRechargeHis.queryDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerRechargeHis.queryResult}
				</td>
				<td>
					${fns:getDictLabel(customerRechargeHis.status, 'customer_recharge_status_dict', '')}
				</td>
				<shiro:hasPermission name="customer:customerRechargeHis:edit"><td>
    				<a href="${ctx}/customer/customerRechargeHis/form?id=${customerRechargeHis.id}">修改</a>
					<a href="${ctx}/customer/customerRechargeHis/delete?id=${customerRechargeHis.id}" onclick="return confirmx('确认要删除该会员充值记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>