<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员可免费提现次数变更流水管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerFreeWithdrawCountHis/">会员可免费提现次数变更流水列表</a></li>
		<shiro:hasPermission name="customer:customerFreeWithdrawCountHis:edit"><li><a href="${ctx}/customer/customerFreeWithdrawCountHis/form">会员可免费提现次数变更流水添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerFreeWithdrawCountHis" action="${ctx}/customer/customerFreeWithdrawCountHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>变更类型：</label>
				<form:select path="changeTypeCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('customer_free_withdraw_count_change_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>获取时间：</label>
				<input name="beginGetDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerFreeWithdrawCountHis.beginGetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endGetDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerFreeWithdrawCountHis.endGetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>使用时间：</label>
				<input name="beginUseDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerFreeWithdrawCountHis.beginUseDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endUseDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerFreeWithdrawCountHis.endUseDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>变更值</th>
				<th>变更类型</th>
				<th>获取时间</th>
				<th>提现请求流水号</th>
				<th>使用时间</th>
				<shiro:hasPermission name="customer:customerFreeWithdrawCountHis:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerFreeWithdrawCountHis">
			<tr>
				<td><a href="${ctx}/customer/customerFreeWithdrawCountHis/form?id=${customerFreeWithdrawCountHis.id}">
					${customerFreeWithdrawCountHis.accountId}
				</a></td>
				<td>
					${customerFreeWithdrawCountHis.changeVal}
				</td>
				<td>
					${fns:getDictLabel(customerFreeWithdrawCountHis.changeTypeCode, 'customer_free_withdraw_count_change_type_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${customerFreeWithdrawCountHis.getDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerFreeWithdrawCountHis.requestNo}
				</td>
				<td>
					<fmt:formatDate value="${customerFreeWithdrawCountHis.useDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerFreeWithdrawCountHis:edit"><td>
    				<a href="${ctx}/customer/customerFreeWithdrawCountHis/form?id=${customerFreeWithdrawCountHis.id}">修改</a>
					<a href="${ctx}/customer/customerFreeWithdrawCountHis/delete?id=${customerFreeWithdrawCountHis.id}" onclick="return confirmx('确认要删除该会员可免费提现次数变更流水吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>