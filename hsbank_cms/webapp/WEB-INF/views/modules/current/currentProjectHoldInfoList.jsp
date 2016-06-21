<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期产品持有信息管理</title>
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
				<li class="active"><a href="${ctx}/current/currentProjectHoldInfo/">活期产品持有信息列表</a></li>
				<shiro:hasPermission name="current:currentProjectHoldInfo:edit"><li><a href="${ctx}/current/currentProjectHoldInfo/form">活期产品持有信息添加</a></li></shiro:hasPermission>
			</ul>
	
		<jsp:include page="./common/projectInfoMenu.jsp"/>
	<jsp:include page="../customer/common/customerPandectMenu.jsp"/>
	
	<form:form id="searchForm" modelAttribute="currentProjectHoldInfo" action="${ctx}/current/currentProjectHoldInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="projectId" />
		<ul class="ul-form">
			
			<li><label>会员手机号：</label>
				<form:input path="customerBase.mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<%--
			<li><label>持有本金：</label>
				<input name="beginPrincipal" type="text" value='<fmt:formatNumber value="${currentProjectHoldInfo.beginPrincipal}" pattern="#0.##" />' htmlEscape="false" class="input-medium"/>-
				<input name="endPrincipal"  type="text" value='<fmt:formatNumber value="${currentProjectHoldInfo.endPrincipal}" pattern="#0.##" />' htmlEscape="false" class="input-medium"/>
			</li> --%>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('current_project_hold_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>会员手机号</th>
				<th>项目编号</th>
				<th>项目名称</th>
				<th>投资账户编号</th>
				<th>持有本金</th>
				<th>申请赎回本金</th>
				<th>持有利息</th>
				<th>状态</th>
				<shiro:hasPermission name="current:currentProjectHoldInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="currentProjectHoldInfo">
			<tr>
				<td>
					${currentProjectHoldInfo.customerBase.mobile}
				</td>
				<td>
					
					${p2p:abbrev(currentProjectHoldInfo.currentProjectInfo.code,abbrevLength)}
				</td>
				<td>
					${p2p:abbrev(currentProjectHoldInfo.currentProjectInfo.name,abbrevLength)}
				</td>
				<td>
					${currentProjectHoldInfo.accountId}
				</td>
				<td>
					
					<fmt:formatNumber value="${currentProjectHoldInfo.principal}" pattern="#0.##" />
				</td>
				<td>
					<fmt:formatNumber value="${currentProjectHoldInfo.applyRedeemPrincipal}" pattern="#0.##" />
				</td>
				<td>
					<fmt:formatNumber value="${currentProjectHoldInfo.interest}" pattern="#0.####" />
				</td>
				<td>
					${fns:getDictLabel(currentProjectHoldInfo.status, 'current_project_hold_status', '')}
				</td>
				<shiro:hasPermission name="current:currentProjectHoldInfo:edit"><td>
    				<a href="${ctx}/current/currentProjectHoldInfo/form?id=${currentProjectHoldInfo.id}">修改</a>
					<a href="${ctx}/current/currentProjectHoldInfo/delete?id=${currentProjectHoldInfo.id}" onclick="return confirmx('确认要删除该活期产品持有信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>