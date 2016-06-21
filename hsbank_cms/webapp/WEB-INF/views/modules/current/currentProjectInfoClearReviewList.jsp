<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期项目信息管理</title>
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
		<li class="active"><a href="${ctx}/current/currentProjectInfo/clearReviewList">活期项目清盘审批列表</a></li>
		
	</ul>
	<form:form id="searchForm" modelAttribute="currentProjectInfo" action="${ctx}/current/currentProjectInfo/clearReviewList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目编号：</label>
				<form:input path="code" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li><label>项目名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>年化利率：</label>
				<form:input path="rate" htmlEscape="false" class="input-medium"/>
			</li>
			
			<li><label>清盘状态：</label>
				<form:select path="windingUpStatus" class="input-medium" disabled="true">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('current_project_winding_up_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目编号</th>
				<th>项目名称</th>
				<th>年化利率</th>
				<th>融资金额</th>
				<th>最大投资额度</th>
				<th>发布日期</th>
				<th>清盘申请日期</th>
				<th>项目状态</th>
				<th>清盘状态</th>
				<shiro:hasPermission name="current:currentProjectInfo:clearReview"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="currentProjectInfo">
			<tr>
				<td><a href="${ctx}/current/currentProjectInfo/clearReviewForm?id=${currentProjectInfo.id}">
					${p2p:abbrev(currentProjectInfo.code,abbrevLength)}
				</a></td>
				<td>
					${p2p:abbrev(currentProjectInfo.name,abbrevLength)}
				</td>
				<td>
					<fmt:formatNumber value="${currentProjectInfo.rate}" pattern="#0.###" />
				</td>
				<td>
					<fmt:formatNumber value="${currentProjectInfo.financeMoney}" pattern="#0.##" />
				</td>
				<td>
					<fmt:formatNumber value="${currentProjectInfo.maxAmount}" pattern="#0.##" />
				</td>
				<td>
					<fmt:formatDate value="${currentProjectInfo.publishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${currentProjectInfo.windingUpApplyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<td>
					${fns:getDictLabel(currentProjectInfo.status, 'current_project_status', '')}
				</td>
				<td>
					${fns:getDictLabel(currentProjectInfo.windingUpStatus, 'current_project_winding_up_status', '')}
				</td>
				<shiro:hasPermission name="current:currentProjectInfo:clearReview"><td>
    				<a href="${ctx}/current/currentProjectInfo/clearReviewForm?id=${currentProjectInfo.id}">审批</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>