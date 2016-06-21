<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异常交易记录表管理</title>
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
		<li class="active"><a href="${ctx}/project/projectTransErrorRecord/list">异常交易列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="projectTransErrorRecord" action="${ctx}/project/projectTransErrorRecord/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>请求流水：</label>
				<form:input path="thirdPartySeq" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectTransErrorRecord.beginCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectTransErrorRecord.endCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>账号编号</th>
				<th>平台编号</th>
				<th>请求流水</th>
				<th>创建时间</th>
				<%-- <shiro:hasPermission name="project:projectTransErrorRecord:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectTransErrorRecord">
			<tr>
				<td><a href="${ctx}/project/projectTransErrorRecord/form?id=${projectTransErrorRecord.id}">
					${projectTransErrorRecord.accountId}
				</a></td>
				<td>
					${projectTransErrorRecord.ca.platformUserNo}
				</td>
				<td>
					${projectTransErrorRecord.thirdPartySeq}
				</td>
				<td>
					<fmt:formatDate value="${projectTransErrorRecord.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%-- <shiro:hasPermission name="project:projectTransErrorRecord:edit"><td>
    				<a href="${ctx}/project/projectTransErrorRecord/form?id=${projectTransErrorRecord.id}">修改</a>
					<a href="${ctx}/project/projectTransErrorRecord/delete?id=${projectTransErrorRecord.id}" onclick="return confirmx('确认要删除该异常交易记录表吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>