<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品价格策略管理</title>
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
		<li class="active"><a href="${ctx}/integral/integralMallProduct/queryForm?id=${integralMallProductPrice.productId}">上架产品信息</a></li>
	</ul>
	<br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/integral/integralMallProduct/queryForm?id=${integralMallProductPrice.productId}">产品基本信息</a></li>
		<li><a href="${ctx}/integral/integralMallProductSps/queryList?productId=${integralMallProductPrice.productId}">产品规格</a></li>
		<li  class="active"><a href="${ctx}/integral/integralMallProductPrice/queryList?productId=${integralMallProductPrice.productId}">产品价格</a></li>
		<li><a href="${ctx}/integral/integralMallProductHot/queryList?productId=${integralMallProductPrice.productId}">产品活动</a></li>
	</ul>
	<br/>
	<form:form id="searchForm" modelAttribute="integralMallProductPrice" action="${ctx}/integral/integralMallProductPrice/queryList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="productId"/>
		<ul class="ul-form">
			<li><label>价格类型：</label>
				<form:select path="priceType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('integral_mall_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('integral_mall_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>产品价格类型</th>
				<th>活动价格</th>
				<th>活动折扣</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="integralMallProductPrice">
			<tr>
				<td><a href="${ctx}/integral/integralMallProductPrice/queryForm?id=${integralMallProductPrice.id}">
					${fns:getDictLabel(integralMallProductPrice.priceType, 'integral_mall_status_dict', '')}
				</a></td>
				<td>
					${integralMallProductPrice.marketNewPrice}
				</td>
				<td>
					${integralMallProductPrice.marketDiscount}
				</td>
				<td>
					<fmt:formatDate value="${integralMallProductPrice.beginDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${integralMallProductPrice.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(integralMallProductPrice.status, 'integral_mall_status_dict', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>