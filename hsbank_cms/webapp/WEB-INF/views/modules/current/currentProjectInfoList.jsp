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
		<li class="active"><a href="${ctx}/current/currentProjectInfo/">活期项目信息列表</a></li>
		<shiro:hasPermission name="current:currentProjectInfo:edit"><li><a href="${ctx}/current/currentProjectInfo/form">活期项目信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="currentProjectInfo" action="${ctx}/current/currentProjectInfo/" method="post" class="breadcrumb form-search">
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
			<li><label>项目状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('current_project_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>清盘状态：</label>
				<form:select path="windingUpStatus" class="input-medium">
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
				<th>创建日期</th>
				<th>项目状态</th>
				<th>清盘状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="currentProjectInfo">
			<tr>
				<td><a href="${ctx}/current/currentProjectInfo/form?pageType=menus&readonly=true&id=${currentProjectInfo.id}&queryFieldName=id">
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
					<fmt:formatDate value="${currentProjectInfo.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(currentProjectInfo.status, 'current_project_status', '')}
				</td>
				<td>
					${fns:getDictLabel(currentProjectInfo.windingUpStatus, 'current_project_winding_up_status', '')}
				</td>
				<td>
				
					<a href="${ctx}/current/currentProjectInfo/form?pageType=menus&readonly=true&id=${currentProjectInfo.id}&queryFieldName=id">详情</a>
					<shiro:hasPermission name="current:currentProjectInfo:create">
						<a href="${ctx}/current/currentProjectInfo/cloneForm?id=${currentProjectInfo.id}">克隆</a>
					</shiro:hasPermission>
					<c:if test="${currentProjectInfo.status eq '3'}">
					 	<a href="${ctx}/current/currentProjectInfo/currentProjectBuyDetail?id=${currentProjectInfo.id}">投资</a>
					</c:if>
					<shiro:hasPermission name="current:currentProjectInfo:authorize">
					   <c:if test="${currentProjectInfo.isAutoRepay eq '0'}">
					       <c:if test="${currentProjectInfo.isAuthorizeShow eq '1'}">
					            <a href="${ctx}/current/currentProjectInfo/authorizeCurrentAutoRepaymentDo?id=${currentProjectInfo.id}">还款授权</a>
					       </c:if>
					   </c:if>
					</shiro:hasPermission>
				</td>


			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>