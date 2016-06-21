<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>花生乐园上架产品类别管理</title>
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
		<li class="active"><a href="${ctx}/integral/integralMallProductType/list">产品类别列表</a></li>
		<shiro:hasPermission name="integral:integralMallProductType:edit"><li><a href="${ctx}/integral/integralMallProductType/form">产品类别添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="integralMallProductType" action="${ctx}/integral/integralMallProductType/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类别名称：</label>
				<form:input path="typeName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>类别编号</th>
				<th>类别名称</th>
				<shiro:hasPermission name="integral:integralMallProductType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="integralMallProductType">
			<tr>
				<td><a href="${ctx}/integral/integralMallProductType/form?id=${integralMallProductType.typeId}">
					${integralMallProductType.typeId}
				</a></td>
				<td>
					${integralMallProductType.typeName}
				</td>
				<shiro:hasPermission name="integral:integralMallProductType:edit"><td>
    				<a href="${ctx}/integral/integralMallProductType/form?id=${integralMallProductType.typeId}">修改</a>
					<a href="${ctx}/integral/integralMallProductType/delete?id=${integralMallProductType.typeId}" onclick="return confirmx('删除后此类别产品将被一同删除，确认要删除该花生乐园上架产品类别吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>