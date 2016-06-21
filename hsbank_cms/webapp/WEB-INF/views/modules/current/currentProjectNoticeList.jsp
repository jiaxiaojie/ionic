<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期产品公告管理</title>
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
		<li class="active"><a href="${ctx}/current/currentProjectNotice/">活期产品公告列表</a></li>
		<shiro:hasPermission name="current:currentProjectNotice:edit"><li><a href="${ctx}/current/currentProjectNotice/form">活期产品公告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="currentProjectNotice" action="${ctx}/current/currentProjectNotice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目编号：</label>
				<form:input path="currentProjectInfo.code" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>项目名称：</label>
				<form:input path="currentProjectInfo.name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>发布时间：</label>
				<input name="beginPublishDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${currentProjectNotice.beginPublishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endPublishDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${currentProjectNotice.endPublishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('current_project_notice_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>标题</th>
				<th>发布时间</th>
				<th>状态</th>
				<shiro:hasPermission name="current:currentProjectNotice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="currentProjectNotice">
			<tr>
				<td><a href="${ctx}/current/currentProjectNotice/form?id=${currentProjectNotice.id}">
					${p2p:abbrev(currentProjectNotice.currentProjectInfo.code,abbrevLength)}
				</a></td>
				<td>
					${p2p:abbrev(currentProjectNotice.currentProjectInfo.name,abbrevLength)}
					
				</td>
				<td>
					${currentProjectNotice.title}
				</td>
				<td>
					<fmt:formatDate value="${currentProjectNotice.publishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(currentProjectNotice.status, 'current_project_notice_status', '')}
				</td>
				<shiro:hasPermission name="current:currentProjectNotice:edit"><td>
    				<a href="${ctx}/current/currentProjectNotice/form?id=${currentProjectNotice.id}">修改</a>
					<a href="${ctx}/current/currentProjectNotice/delete?id=${currentProjectNotice.id}" onclick="return confirmx('确认要删除该活期产品公告吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>