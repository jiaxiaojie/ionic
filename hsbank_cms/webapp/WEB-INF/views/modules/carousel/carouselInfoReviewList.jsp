<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>轮播图管理</title>
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
		<li class="active"><a href="${ctx}/carousel/carouselInfo/">轮播图列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="carouselInfo" action="${ctx}/carousel/carouselInfo/reviewList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			 <li><label class="control-label">类型：</label>
			  <form:select path="typeCode" class="input-xlarge required">
					<form:option value="" label="全部"/>
					<form:options path="typeCode" items="${fns:getDictList('photo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>标题</th>
				<th>活动或项目开始时间</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>状态</th>
				<shiro:hasPermission name="carousel:carouselInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="carouselInfo">
				<td><a href="${ctx}/carousel/carouselInfo/reviewForm?carouselId=${carouselInfo.carouselId}">
 					${p2p:abbrev(carouselInfo.title,10)}
				</a></td>
				
				<td>
					<fmt:formatDate value="${carouselInfo.activityTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${carouselInfo.startDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${carouselInfo.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(carouselInfo.status, 'shenghe', '')} 
				</td>
				<shiro:hasPermission name="carousel:carouselInfo:edit"><td>
				    <a href="${ctx}/carousel/carouselInfo/reviewForm?carouselId=${carouselInfo.carouselId}">审批</a>
				</td></shiro:hasPermission>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>