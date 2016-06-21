<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员基本信息变更历史管理</title>
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
		<li><a href="${ctx}/customer/customerAccount/">会员列表</a></li>
		<li><a href="${ctx}/customer/customerBase/form?id=${customerId}">会员<shiro:hasPermission name="customer:customerBase:edit">${not empty customerId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerBase:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a href="${ctx}/customer/customerBaseHis/">会员基本信息变更历史列表</a></li>
		<shiro:hasPermission name="customer:customerBaseHis:edit"><li><a href="${ctx}/customer/customerBaseHis/form">会员基本信息变更历史添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerBaseHis" action="${ctx}/customer/customerBaseHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>会员姓名</th>
				<th>更改时间</th>
				<shiro:hasPermission name="customer:customerBaseHis:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerBaseHis">
			<tr>
				<td>
					${customerBaseHis.accountName}
				</td>
				<td>
					${customerBaseHis.customerBase.customerName}
				</td>
				<td>
					<fmt:formatDate value="${customerBaseHis.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerBaseHis:view"><td>
    				<a href="${ctx}/customer/customerBaseHis/form?id=${customerBaseHis.id}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>