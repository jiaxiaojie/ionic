<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>花生乐园订单管理</title>
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
		<li class="active"><a href="${ctx}/integral/integralMallProductOrder/list">订单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="integralMallProductOrder" action="${ctx}/integral/integralMallProductOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品类型：</label>
				<form:select path="productTypeId" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('marketing_product_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>产品名称：</label>
				<form:input path="productName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>用户：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>订单状态：</label>
				<form:select path="orderStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('integral_mall_order_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建渠道：</label>
				<form:select path="createChannelId" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>用户</th>
				<th>产品类型</th>
				<th>产品名称</th>
				
				<th>数量</th>
				<th>单价</th>
				<th>订单号</th>
				<th>下单时间</th>
				<th>订单状态</th>
				<th>创建渠道</th>
				<shiro:hasPermission name="integral:integralMallProductOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="integralMallProductOrder">
			<tr>
				<td>
					${integralMallProductOrder.customerBase.customerName}
				</td>
				<td>
					${fns:getDictLabel(integralMallProductOrder.productTypeId, 'marketing_product_type_dict', '')}
					
				</td>
				<td><a href="${ctx}/integral/integralMallProductOrder/form?id=${integralMallProductOrder.id}">
					${integralMallProductOrder.product.productName}
					</a>
				</td>
				<td>
					${integralMallProductOrder.productCount}
				</td>
				<td>
					${integralMallProductOrder.productPrice}
				</td>
				<td>
					${integralMallProductOrder.orderNo}
				</td>
				<td>
					<fmt:formatDate value="${integralMallProductOrder.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(integralMallProductOrder.orderStatus, 'integral_mall_order_status_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(integralMallProductOrder.createChannelId, 'op_term_dict', '')}
				</td>
				<shiro:hasPermission name="integral:integralMallProductOrder:edit"><td>
    				<a href="${ctx}/integral/integralMallProductOrder/form?id=${integralMallProductOrder.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>