<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>营销活动分享记录管理</title>
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
		<li class="active"><a href="${ctx}/marketing/marketingShareRecord/">营销活动分享记录列表</a></li>
		<shiro:hasPermission name="marketing:marketingShareRecord:edit"><li><a href="${ctx}/marketing/marketingShareRecord/form">营销活动分享记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="marketingShareRecord" action="${ctx}/marketing/marketingShareRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动编号：</label>
				<form:input path="acticityId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>分享用户：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>分享时间：</label>
				<input name="beginShareDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingShareRecord.beginShareDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endShareDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingShareRecord.endShareDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>活动编号</th>
				<th>渠道编号</th>
				<th>分享用户</th>
				<th>分享时间</th>
				<th>分享说明</th>
				<shiro:hasPermission name="marketing:marketingShareRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="marketingShareRecord">
			<tr>
				<td><a href="${ctx}/marketing/marketingShareRecord/form?id=${marketingShareRecord.id}">
					${marketingShareRecord.acticityId}
				</a></td>
				<td>
					${marketingShareRecord.channelId}
				</td>
				<td>
					${marketingShareRecord.accountId}
				</td>
				<td>
					<fmt:formatDate value="${marketingShareRecord.shareDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${marketingShareRecord.shareReason}
				</td>
				<shiro:hasPermission name="marketing:marketingShareRecord:edit"><td>
    				<a href="${ctx}/marketing/marketingShareRecord/form?id=${marketingShareRecord.id}">修改</a>
					<a href="${ctx}/marketing/marketingShareRecord/delete?id=${marketingShareRecord.id}" onclick="return confirmx('确认要删除该营销活动分享记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>