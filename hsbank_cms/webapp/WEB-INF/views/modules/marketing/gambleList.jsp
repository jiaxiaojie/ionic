<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>竞猜管理</title>
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
		<li class="active"><a href="${ctx}/marketing/gamble/">竞猜列表</a></li>
		<shiro:hasPermission name="marketing:gamble:edit"><li><a href="${ctx}/marketing/gamble/form">竞猜添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gamble" action="${ctx}/marketing/gamble/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<%-- <li><label>活动编号：</label>
				<form:input path="activityId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li> --%>
			<%-- <li><label>选择队伍：</label>
				<form:input path="choiceSide" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li> --%>
			<li><label class="control-label">竞猜终端：</label>
			  <form:select path="opTerm" class="input-xlarge ">
					<form:option value="" label="全部"/>
					<form:options path="opTerm" items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				</li>
			<li><label>竞猜时间：</label>
				<input name="opDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${gamble.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label class="control-label">比赛结果：</label>
			 <form:select path="rightSide" class="input-xlarge ">
					<form:option value="" label="全部"/>
					<form:options path="rightSide" items="${fns:getDictList('win_term')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			 <th>竞猜时间</th> 
				<!-- <th>用户</th> -->
				<!-- <th>活动编号</th> -->
				<th>选择队伍</th>
				
				<!-- <th>竞猜终端</th> -->
				<th>比赛结果</th>
				<th>领奖时间</th>
				<shiro:hasPermission name="marketing:gamble:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gamble">
			<tr>
				<td><a href="${ctx}/marketing/gamble/form?id=${gamble.id}">
					<%-- ${gamble.accountId} --%>
					<fmt:formatDate value="${gamble.opDt}" pattern="yyyy-MM-dd"/>
				</a></td>
			<%-- 	<td>
					${gamble.activityId}
				</td> --%>
				<td>
				${fns:getDictLabel(gamble.choiceSide, 'term', '')}
				</td>
			<!-- <td>
					
				</td> --> 
				<%-- <td>
					${fns:getDictLabel(gamble.opTerm, 'op_term_dict', '')}
				</td> --%>
				<td>
					${fns:getDictLabel(gamble.rightSide, 'win_term', '')}
				</td>
				<td>
					<fmt:formatDate value="${gamble.awardDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="marketing:gamble:edit"><td>
    				<a href="${ctx}/marketing/gamble/form?id=${gamble.id}">修改</a>
					<a href="${ctx}/marketing/gamble/delete?id=${gamble.id}" onclick="return confirmx('确认要删除该竞猜吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>