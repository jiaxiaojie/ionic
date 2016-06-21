<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>私人订制管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

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
		<li class="active"><a href="${ctx}/personal/personalTailor/">私人订制列表</a></li>
		<shiro:hasPermission name="personal:personalTailor:edit"><li><a href="${ctx}/personal/personalTailor/form">私人订制添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="personalTailor" action="${ctx}/personal/personalTailor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>项目类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personal_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personal_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发布时间：</label>
				<input name="publishTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${personalTailor.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>

			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目名称</th>
				<th>项目类型</th>
				<th>项目金额</th>
				<th>年化利率</th>
				<th>发布时间</th>
				<th>投标截止日期</th>
				
				<th>起投金额</th>
				<th>备注信息</th>
				<th>状态</th>
				<shiro:hasPermission name="personal:personalTailor:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personalTailor">
			<tr>
				<td><a href="${ctx}/personal/personalTailor/form?id=${personalTailor.id}">
					${personalTailor.name}
				</a></td>
				<td>
					${fns:getDictLabel(personalTailor.type, 'personal_type', '')}
				</td>
				<td>
					${personalTailor.amount}
				</td>
				<td>
					${personalTailor.rate}
				</td>
				<td>
					<fmt:formatDate value="${personalTailor.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${personalTailor.deadline}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${personalTailor.startingAmount}
				</td>
				<td>
					${personalTailor.remarks}
				</td>
				<td>
				    ${fns:getDictLabel(personalTailor.state, 'personal_state', '')}
				</td>
				<shiro:hasPermission name="personal:personalTailor:edit"><td>
					<c:if test="${personalTailor.state==3||personalTailor.state==0}">
    				   <a href="${ctx}/personal/personalTailor/form?id=${personalTailor.id}">修改</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>