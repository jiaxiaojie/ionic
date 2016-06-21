<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账户余额变更流水管理</title>
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
		<li><a href="${ctx}/customer/customerAccountInfo/customerAccountInfoList">会员列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerAccountInfo/customerAccountInfo?accountId=${model.accountId}">会员详细信息</a></li>
	</ul><br/>
	<jsp:include page="./common/customerAccountInfoMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="customerBalanceHis" action="${ctx}/customer/customerAccountInfo/customerBalanceHisInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="accountId" name="accountId" type="hidden" value="${model.accountId}"/>
		<ul class="ul-form">
			
			<li><label>变更值：</label>
				<form:input path="beginChangeVal" htmlEscape="false" class="input-medium"/> - 
				<form:input path="endChangeVal" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>变更类型：</label>
				<form:select path="changeType" class="input-medium">
					<form:option value=""  label="全部"/>
					<form:options items="${fns:getDictList('customer_balance_change_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label title="变更日期">变更日期：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerBalanceHis.beginOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerBalanceHis.endOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>变更值</th>
				<th>变更类型</th>
				<th>变更原因</th>
				<th>关联项目</th>
				<th>操作时间</th>
				<th>操作终端</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerBalanceHis">
			<tr>
				<td>
					${customerBalanceHis.changeVal}
				</td>
				<td>
					${fns:getDictLabel(customerBalanceHis.changeType, 'customer_balance_change_type_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(customerBalanceHis.changeReason, 'customer_balance_change_reason_dict', '')}
				</td>
				<td>
					${customerBalanceHis.relProject}
				</td>
				<td>
					<fmt:formatDate value="${customerBalanceHis.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(customerBalanceHis.opTermType, 'op_term_dict', '')}
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>