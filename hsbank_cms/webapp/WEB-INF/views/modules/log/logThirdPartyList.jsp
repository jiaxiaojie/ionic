<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>第三方交互日志管理</title>
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
		<li class="active"><a href="${ctx}/log/logThirdParty/">第三方交互日志列表</a></li>
		<shiro:hasPermission name="log:logThirdParty:edit"><li><a href="${ctx}/log/logThirdParty/form">第三方交互日志添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="logThirdParty" action="${ctx}/log/logThirdParty/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>请求流水号：</label>
				<form:input path="requestNo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>关联业务编号：</label>
				<form:input path="service" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>请求时间：</label>
				<input name="beginReqDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${logThirdParty.beginReqDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endReqDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${logThirdParty.endReqDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>请求流水号</th>
				<th>关联业务编号</th>
				<th>请求报文</th>
				<th>请求时间</th>
				<th>返回报文</th>
				<th>返回结果</th>
				<th>返回时间</th>
				<th>通知报文</th>
				<th>通知结果</th>
				<th>通知时间</th>
				<th>查询时间</th>
				<th>查询返回报文</th>
				<th>查询结果</th>
				<shiro:hasPermission name="log:logThirdParty:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="logThirdParty">
			<tr>
				<td><a href="${ctx}/log/logThirdParty/form?id=${logThirdParty.id}">
					${logThirdParty.requestNo}
				</a></td>
				<td>
					${logThirdParty.service}
				</td>
				<td>
					${logThirdParty.reqContent}
				</td>
				<td>
					<fmt:formatDate value="${logThirdParty.reqDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${logThirdParty.respContent}
				</td>
				<td>
					${logThirdParty.respCode}
				</td>
				<td>
					<fmt:formatDate value="${logThirdParty.respDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${logThirdParty.notifyContent}
				</td>
				<td>
					${logThirdParty.notifyCode}
				</td>
				<td>
					<fmt:formatDate value="${logThirdParty.notifyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${logThirdParty.queryDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${logThirdParty.queryContent}
				</td>
				<td>
					${logThirdParty.queryCode}
				</td>
				<shiro:hasPermission name="log:logThirdParty:edit"><td>
    				<a href="${ctx}/log/logThirdParty/form?id=${logThirdParty.id}">修改</a>
					<a href="${ctx}/log/logThirdParty/delete?id=${logThirdParty.id}" onclick="return confirmx('确认要删除该第三方交互日志吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>