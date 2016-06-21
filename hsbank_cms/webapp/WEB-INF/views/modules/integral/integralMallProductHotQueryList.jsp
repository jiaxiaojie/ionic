<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品活动标签管理</title>
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
		<li><a href="${ctx}/integral/integralMallProduct/querylist">上架产品列表</a></li>
		<li class="active"><a href="${ctx}/integral/integralMallProduct/queryForm?id=${integralMallProductHot.productId}">上架产品信息</a></li>
	</ul>
	<br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/integral/integralMallProduct/queryForm?id=${integralMallProductHot.productId}">产品基本信息</a></li>
		<li><a href="${ctx}/integral/integralMallProductSps/queryList?productId=${integralMallProductHot.productId}">产品规格</a></li>
		<li><a href="${ctx}/integral/integralMallProductPrice/queryList?productId=${integralMallProductHot.productId}">产品价格</a></li>
		<li  class="active"><a href="${ctx}/integral/integralMallProductHot/queryList?productId=${integralMallProductHot.productId}">产品活动</a></li>
	</ul>
	<br/>
	<form:form id="searchForm" modelAttribute="integralMallProductHot" action="${ctx}/integral/integralMallProductHot/queryList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="productId"/>
		<ul class="ul-form">
			<li><label>标签类型：</label>
				<form:select path="hotType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('integral_project_hot_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>标签类型</th>
				<th>标签值</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="integralMallProductHot">
			<tr>
				<td><a href="${ctx}/integral/integralMallProductHot/queryForm?id=${integralMallProductHot.id}">
					${fns:getDictLabel(integralMallProductHot.hotType, 'integral_project_hot_type_dict', '')}
				</a></td>
				<td>
					${integralMallProductHot.hotValue}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>