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
		
		function changeSortInList(projectId) {
			var sort = $("#sortInList_" + projectId).val();
			if(isNaN(sort)) {
				alert("请输入整数");
			} else {
				window.location.href = "${ctx}/content/activity/updateSort?ids=" + projectId + "&sorts=" + sort;
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/content/activity/">活动排序列表</a></li>
		
	</ul>
	<form:form id="searchForm" modelAttribute="activity" action="${ctx}/content/activity/sortList/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</li>
			<li><label>活动状态：</label>
				<form:select path="actStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('act_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form action="${ctx}/content/activity/updateSort" id="sortForm">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>活动编号</th>
				<th>活动名称</th>
				<th>活动描述</th>
				<th>活动链接</th>
				<th>审核状态</th>
				<th>活动状态</th>
				<th>排序</th>
				
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
				<td>
						<shiro:hasPermission name="content:activity:edit">
							<input type="hidden" name="ids" value="${activity.id}"/>
							<input name="sorts" id="sortInList_${activity.id}" type="text" value="${activity.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="修改" onclick="changeSortInList(${activity.id})" />
						
					</td>
				
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="pagination">${page}</div>
	<shiro:hasPermission name="cms:category:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存排序" />
		</div></shiro:hasPermission>
		</form>
</body>
</html>