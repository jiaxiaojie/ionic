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
		<li><a href="${ctx}/customer/customerAccountInfo/customerAccountInfoList">会员列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerAccountInfo/customerAccountInfo?accountId=${model.accountId}">会员详细信息</a></li>
	</ul><br/>
	<jsp:include page="./common/customerAccountInfoMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="customerFreeWithdrawCountHis" action="${ctx}/customer/customerAccountInfo/customerFreeWithdrawCountHisInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="accountId" name="accountId" type="hidden" value="${model.accountId}"/>
		<ul class="ul-form">
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
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerFreeWithdrawCountHis">
			<tr>
				<td>
					${customerFreeWithdrawCountHis.accountId}
				</td>
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
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>