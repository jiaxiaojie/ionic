<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台账号流水管理</title>
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
		<li class="active"><a href="${ctx}/log/p2pFdAccountFlow/">平台账号流水列表</a></li>
		<shiro:hasPermission name="log:p2pFdAccountFlow:edit"><li><a href="${ctx}/log/p2pFdAccountFlow/form">平台账号流水添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="p2pFdAccountFlow" action="${ctx}/log/p2pFdAccountFlow/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>变化值：</label>
				<form:input path="changeVal" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>项目编号：</label>
				<form:input path="projectId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>转让项目编号：</label>
				<form:input path="transferProjectId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>投资编号：</label>
				<form:input path="investmentId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>变化值</th>
				<th>项目编号</th>
				<th>转让项目编号</th>
				<th>投资编号</th>
				<th>关联会员编号</th>
				<th>详情</th>
				<th>变化时间</th>
				<th>第三方交易号</th>
				<th>交易结果</th>
				<shiro:hasPermission name="log:p2pFdAccountFlow:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="p2pFdAccountFlow">
			<tr>
				<td><a href="${ctx}/log/p2pFdAccountFlow/form?id=${p2pFdAccountFlow.id}">
					${p2pFdAccountFlow.changeVal}
				</a></td>
				<td>
					${p2pFdAccountFlow.projectId}
				</td>
				<td>
					${p2pFdAccountFlow.transferProjectId}
				</td>
				<td>
					${p2pFdAccountFlow.investmentId}
				</td>
				<td>
					${p2pFdAccountFlow.accountId}
				</td>
				<td>
					${p2pFdAccountFlow.detail}
				</td>
				<td>
					<fmt:formatDate value="${p2pFdAccountFlow.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${p2pFdAccountFlow.thirdPartySeq}
				</td>
				<td>
					${p2pFdAccountFlow.result}
				</td>
				<shiro:hasPermission name="log:p2pFdAccountFlow:edit"><td>
    				<a href="${ctx}/log/p2pFdAccountFlow/form?id=${p2pFdAccountFlow.id}">修改</a>
					<a href="${ctx}/log/p2pFdAccountFlow/delete?id=${p2pFdAccountFlow.id}" onclick="return confirmx('确认要删除该平台账号流水吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>