<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>手机号中奖记录管理</title>
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
		<li class="active"><a href="${ctx}/marketing/mobileAwardRecord/">手机号中奖记录列表</a></li>
		<shiro:hasPermission name="marketing:mobileAwardRecord:edit"><li><a href="${ctx}/marketing/mobileAwardRecord/form">手机号中奖记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="mobileAwardRecord" action="${ctx}/marketing/mobileAwardRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动编号：</label>
				<form:select path="activityId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>奖品编号：</label>
				<form:input path="prizeId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>活动编号</th>
				<th>手机号</th>
				<th>奖品编号</th>
				<th>状态</th>
				<th>用户编号</th>
				<shiro:hasPermission name="marketing:mobileAwardRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mobileAwardRecord">
			<tr>
				<td><a href="${ctx}/marketing/mobileAwardRecord/form?id=${mobileAwardRecord.id}">
					${fns:getDictLabel(mobileAwardRecord.activityId, '', '')}
				</a></td>
				<td>
					${mobileAwardRecord.mobile}
				</td>
				<td>
					${mobileAwardRecord.prizeId}
				</td>
				<td>
					${fns:getDictLabel(mobileAwardRecord.status, '', '')}
				</td>
				<td>
					${mobileAwardRecord.accountId}
				</td>
				<shiro:hasPermission name="marketing:mobileAwardRecord:edit"><td>
    				<a href="${ctx}/marketing/mobileAwardRecord/form?id=${mobileAwardRecord.id}">修改</a>
					<a href="${ctx}/marketing/mobileAwardRecord/delete?id=${mobileAwardRecord.id}" onclick="return confirmx('确认要删除该手机号中奖记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>