<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动管理</title>
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
		<li class="active"><a href="${ctx}/content/activity/">活动列表</a></li>
		<shiro:hasPermission name="content:activity:edit"><li><a href="${ctx}/content/activity/form">活动添加</a></li></shiro:hasPermission>
		
	</ul>
	<form:form id="searchForm" modelAttribute="activity" action="${ctx}/content/activity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</li>
			<li><label class="control-label">类型：</label>
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label="全部"/>
					<form:options path="type" items="${fns:getDictList('photo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>审核状态：</label>
				<form:select path="activityStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('shenghe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>活动状态：</label>
				<form:select path="actStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('act_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>活动名称</th>
				<th>类型</th>
				<th>活动描述</th>
				<th>活动链接</th>
				<th>审核状态</th>
				<th>活动状态</th>
				<shiro:hasPermission name="content:activity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="activity">
			<tr>
				<td>
					${activity.id}
				</td>
				<td>
					${activity.title}
				</td>
				<td>
						${fns:getDictLabel(activity.type, 'photo_type', '')}
				</td>
				<td>
					${fns:abbr(activity.activityDescription,80)}
				</td>
				<td>
					${activity.activityJoin}
				</td>

				<td>
					${fns:getDictLabel(activity.activityStatus, 'shenghe', '')}
				</td>
				<td>
					<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
					<c:if test="${activity.startDt.time-nowDate>0}">
						${fns:getDictLabel('0', 'act_status', '')}
					</c:if>
					<c:if test="${activity.startDt.time-nowDate<=0 and activity.endDt.time-nowDate>0}">
						${fns:getDictLabel('1', 'act_status', '')}
					</c:if>
					<c:if test="${activity.startDt.time-nowDate<=0 and activity.endDt.time-nowDate<=0}">
						${fns:getDictLabel('2', 'act_status', '')}
					</c:if>
				</td>
				<shiro:hasPermission name="content:activity:edit"><td>
    				
					<c:if test="${method=='review'}">
					<a href="${ctx}/content/activity/reviewForm?id=${activity.id}">审核</a>
					</c:if>
					<c:if test="${method!='review'}">
					<a href="${ctx}/content/activity/form?id=${activity.id}">修改</a>
					<a href="${ctx}/content/activity/delete?id=${activity.id}" onclick="return confirmx('确认要删除该活动吗？', this.href)">删除</a>
					</c:if>
    				
					
				</td></shiro:hasPermission>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>