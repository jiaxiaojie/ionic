<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>营销活动操作流水管理</title>
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
		<li class="active"><a href="${ctx}/marketing/marketingActivityOpHis/list?acticityId=${marketingActivityOpHis.acticityId}">营销活动操作流水列表</a></li>
		<shiro:hasPermission name="marketing:marketingActivityOpHis:edit"><li><a href="${ctx}/marketing/marketingActivityOpHis/form">营销活动操作流水添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="marketingActivityOpHis" action="${ctx}/marketing/marketingActivityOpHis/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="acticityId"/>
		<ul class="ul-form">
			<li><label>行为：</label>
			    <form:select path="behaviorCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${actionTypeList}" itemLabel="behaviorName" itemValue="behaviorCode" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>用户：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>操作时间：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingActivityOpHis.beginOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingActivityOpHis.endOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			    <th>操作流水号</th>
				<th>行为</th>
				<th>用户名</th>
				<th>渠道</th>
				<th>操作时间</th>
				<th>操作结果</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="marketingActivityOpHis">
			<tr>
			    <td><a href="${ctx}/marketing/marketingActivityOpHis/form?id=${marketingActivityOpHis.id}">
					${marketingActivityOpHis.id}
				</a></td>
				<td>
					${marketingActivityOpHis.behaviorName}
				</td>
				<td>
					${marketingActivityOpHis.customerName}
				</td>
				<td>
					${fns:getDictLabel(marketingActivityOpHis.channelId, 'op_term_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${marketingActivityOpHis.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				    ${fns:getDictLabel(marketingActivityOpHis.resultCode, 'activity_result_code_dict', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>