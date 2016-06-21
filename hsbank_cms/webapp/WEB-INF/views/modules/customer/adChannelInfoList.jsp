<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推广渠道信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/adChannelInfo/">推广渠道信息列表</a></li>
		<shiro:hasPermission name="customer:adChannelInfo:edit"><li><a href="${ctx}/customer/adChannelInfo/form">推广渠道信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adChannelInfo" action="${ctx}/customer/adChannelInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>渠道编号：</label>
				<form:input path="channel" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>渠道名称：</label>
				<form:input path="channelName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>渠道编号</th>
				<th>渠道名称</th>
				<shiro:hasPermission name="customer:adChannelInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="adChannelInfo">
			<tr>
				<td><a href="${ctx}/customer/adChannelInfo/form?id=${adChannelInfo.id}">
					${adChannelInfo.channel}
				</a></td>
				<td>
					${adChannelInfo.channelName}
				</td>
				<shiro:hasPermission name="customer:adChannelInfo:edit"><td>
    				<a href="${ctx}/customer/adChannelInfo/form?id=${adChannelInfo.id}">修改</a>
					<a href="${ctx}/customer/adChannelInfo/delete?id=${adChannelInfo.id}" onclick="return confirmx('确认要删除该推广渠道信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>