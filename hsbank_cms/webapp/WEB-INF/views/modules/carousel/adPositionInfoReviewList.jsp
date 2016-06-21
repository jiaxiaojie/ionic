<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告位显示信息管理</title>
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
		<li class="active"><a href="${ctx}/carousel/adPositionInfo/reviewList">广告位显示信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adPositionInfo" action="${ctx}/carousel/adPositionInfo/reviewList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>

			<li><label class="control-label">终端：</label>
				<form:select path="termCode" class="input-xlarge required">
					<form:option value="" label="全部"/>
					<form:options path="termCode" items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label class="control-label">类型：</label>
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label="全部"/>
					<form:options path="type" items="${fns:getDictList('photo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label class="control-label">状态：</label>
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label="全部"/>
					<form:options path="status" items="${fns:getDictList('shenghe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<%--<li><label>创建时间：</label>
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${adPositionInfo.createDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>终端</th>
				<th>位置</th>

				<th>名称</th>

				<th>是否可点击</th>
			<%--	<th>类型</th>--%>
				<%--<th>目标参数</th>
				<th>描述</th>--%>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>状态</th>
				<th>创建时间</th>
				<%--<th>审批时间</th>--%>
				<shiro:hasPermission name="carousel:adPositionInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="adPositionInfo">
			<tr>
				<td><a href="${ctx}/carousel/adPositionInfo/reviewForm?id=${adPositionInfo.id}">
						${fns:getDictLabel(adPositionInfo.termCode, 'op_term_dict', '')}
				</a></td>
				<td>
						${fns:getDictLabel(adPositionInfo.code, 'ad_code', '')}
				</td>
				<td>
						${adPositionInfo.name}
				</td>

				<td>
						${fns:getDictLabel(adPositionInfo.canClick, 'yes_no', '')}
				</td>
			<%--	<td>
						${fns:getDictLabel(adPositionInfo.type, 'photo_type', '')}
				</td>--%>
			<%--	<td>
					${adPositionInfo.target}
				</td>
				<td>
					${adPositionInfo.description}
				</td>--%>
				<td>
					<fmt:formatDate value="${adPositionInfo.startDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${adPositionInfo.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(adPositionInfo.status, 'shenghe', '')}
				</td>
				<td>
					<fmt:formatDate value="${adPositionInfo.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			<%--	<td>
					<fmt:formatDate value="${adPositionInfo.reviewDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>--%>

				<shiro:hasPermission name="carousel:adPositionInfo:edit"><td>
					<a href="${ctx}/carousel/adPositionInfo/reviewForm?id=${adPositionInfo.id}">审批</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>