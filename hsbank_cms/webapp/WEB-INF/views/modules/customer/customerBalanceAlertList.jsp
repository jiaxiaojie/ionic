<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户余额警戒管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerBalanceAlert/">账户余额警戒列表</a></li>
		<shiro:hasPermission name="customer:customerBalanceAlert:edit"><li><a href="${ctx}/customer/customerBalanceAlert/form">账户余额警戒添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerBalanceAlert" action="${ctx}/customer/customerBalanceAlert/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台编号：</label>
				<form:input path="platformUserNo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerBalanceAlert.createDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
				<th>内容</th>
				<th>手机号</th>
				<th>警戒额度</th>
				<th>创建时间</th>
				<shiro:hasPermission name="customer:customerBalanceAlert:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerBalanceAlert">
			<tr>
				<td><a href="${ctx}/customer/customerBalanceAlert/form?id=${customerBalanceAlert.id}">
					${customerBalanceAlert.title}
				</a></td>
				<td>
					${customerBalanceAlert.content}
				</td>
				<td>
					${customerBalanceAlert.mobile}
				</td>
				<td>
					<fmt:formatNumber value="${customerBalanceAlert.amount}" pattern="#0.00" />
				</td>
				<td>
					<fmt:formatDate value="${customerBalanceAlert.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerBalanceAlert:edit"><td>
    				<a href="${ctx}/customer/customerBalanceAlert/form?id=${customerBalanceAlert.id}">修改</a>
					<a href="${ctx}/customer/customerBalanceAlert/delete?id=${customerBalanceAlert.id}" onclick="return confirmx('确认要删除该账户余额警戒吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>