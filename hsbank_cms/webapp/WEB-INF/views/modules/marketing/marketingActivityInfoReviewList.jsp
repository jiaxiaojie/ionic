<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>营销活动管理</title>
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
		<li class="active"><a href="${ctx}/marketing/marketingActivityInfo/">营销活动列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="marketingActivityInfo" action="${ctx}/marketing/marketingActivityInfo/reviewList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>开始时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingActivityInfo.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>结束时间：</label>
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingActivityInfo.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>活动名称</th>
				<th>活动开始时间</th>
				<th>活动结束时间</th>
				<th>活动有效开始时间段</th>
				<th>活动有效结束时间段</th>
				<th>关联实现类名</th>
				<shiro:hasPermission name="marketing:marketingActivityInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="marketingActivityInfo">
			<tr>
				<td><a href="${ctx}/marketing/marketingActivityInfo/reviewForm?acticityId=${marketingActivityInfo.acticityId}">
					${p2p:abbrev(marketingActivityInfo.name,10)}
				</a></td>
				<td>
					<fmt:formatDate value="${marketingActivityInfo.beginDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${marketingActivityInfo.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${marketingActivityInfo.beginTime}
				</td>
				<td>
					${marketingActivityInfo.endTime}
				</td>
				<td>
					${p2p:abbrev(marketingActivityInfo.bizClassName,20)}
				</td>
				<td>
					${fns:getDictLabel(marketingActivityInfo.status, 'marketing_status_dict', '')}
				</td>
				<shiro:hasPermission name="marketing:marketingActivityInfo:edit"><td>
				    <a href="${ctx}/marketing/marketingActivityInfo/reviewForm?acticityId=${marketingActivityInfo.acticityId}">审批</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>