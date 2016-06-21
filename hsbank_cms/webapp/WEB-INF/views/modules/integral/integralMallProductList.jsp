<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>花生乐园上架产品管理</title>
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
		<li class="active"><a href="${ctx}/integral/integralMallProduct/list">上架产品列表</a></li>
		<shiro:hasPermission name="integral:integralMallProduct:edit"><li><a href="${ctx}/integral/integralMallProduct/form">上架产品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="integralMallProduct" action="${ctx}/integral/integralMallProduct/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				
				<form:input path="productName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>产品类别：</label>
				<form:select path="productTypeId" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('marketing_product_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>重点推荐：</label>
				<form:select path="isRecommend" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>上架时间：</label>
				<input name="beginUpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${integralMallProduct.beginUpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endUpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${integralMallProduct.endUpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>产品状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('integral_project_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>产品名称</th>
				<th>产品类型</th>
				<th>是否重点推荐</th>
				<th>原始价格</th>
				<th>上架时间</th>
				<th>下架时间</th>
				<th>上架数量</th>
				<th>剩余数量</th>
				<th>产品状态</th>
				<shiro:hasPermission name="integral:integralMallProduct:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="integralMallProduct">
			<tr>
				<td><a href="${ctx}/integral/integralMallProduct/form?id=${integralMallProduct.productId}">
					${p2p:abbrev(integralMallProduct.productName,20)}
				</a></td>
				<td>
					${fns:getDictLabel(integralMallProduct.productTypeId, 'marketing_product_type_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(integralMallProduct.isRecommend, 'yes_no', '')}
				</td>
				<td>
					${integralMallProduct.price}
				</td>
				<td>
					<fmt:formatDate value="${integralMallProduct.upDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${integralMallProduct.dowDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${integralMallProduct.productCount}
				</td>
				<td>
					${integralMallProduct.productSurplus}
				</td>
				<td>
					${fns:getDictLabel(integralMallProduct.status, 'integral_project_status_dict', '')}
				</td>
				<shiro:hasPermission name="integral:integralMallProduct:edit"><td>
				    <a href="${ctx}/integral/integralMallProductSps/list?productId=${integralMallProduct.productId}">产品规格</a>
				    <!-- <a href="${ctx}/integral/integralMallProductPrice/list?productId=${integralMallProduct.productId}">产品价格</a> -->
				    <!-- <a href="${ctx}/integral/integralMallProductHot/list?productId=${integralMallProduct.productId}">产品活动</a> -->
    				<a href="${ctx}/integral/integralMallProduct/form?id=${integralMallProduct.productId}">修改</a>
					<!-- <a href="${ctx}/integral/integralMallProduct/delete?id=${integralMallProduct.productId}" onclick="return confirmx('确认要删除该花生乐园上架产品吗？', this.href)">删除</a> -->
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>