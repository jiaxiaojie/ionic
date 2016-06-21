<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${title }</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function() {
				var action = $("#searchForm").attr("action");
				$("#searchForm").attr("action", "${ctx}/operation/dataQuery/dataQueryExport");
				$("#searchForm").submit();
				$("#searchForm").attr("action", action);
			});
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
		<li class="active"><a href="${ctx}/operation/dataQuery/dataQuery?menuId=${menuId}">${title }</a></li>
	</ul>
	<form id="searchForm" action="${ctx}/operation/dataQuery/dataQuery" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input name="menuId" type="hidden" value="${menuId}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<c:if test="${!empty dataQueryFormList }">
			<c:forEach items="${dataQueryFormList }" var="dataQueryForm">
			<li><label>${dataQueryForm.label }</label>
			<c:choose>
				<c:when test="${dataQueryForm.showType eq 'input' || dataQueryForm.showType eq 'textarea' }">
					<input type="text" name="${dataQueryForm.parameter }" value="${dataQueryForm.parameterValue }"/>
				</c:when>
				<c:when test="${dataQueryForm.showType eq 'select'}">
					<select name="${dataQueryForm.parameter }" class="input-medium">
						<option value="" ${empty dataQueryForm.dictType ? "selected='selected'": ""}>全部</option>
						<c:forEach items="${fns:getDictList(dataQueryForm.dictType)}" var="dict">
						<option value="${dict.value }" ${dataQueryForm.parameterValue eq dict.value ? "selected='selected'": ""}>${dict.label }</option>
						</c:forEach>
					</select>
				</c:when>
				<c:when test="${dataQueryForm.showType eq 'checkbox'}">
					<c:forEach items="${fns:getDictList(dataQueryForm.dictType)}" var="dict">
					<input type="checkbox" name="${dataQueryForm.parameter }" value="${dict.value }" ${dataQueryForm.parameterValue eq dict.value ? "checked='checked'": ""}/><label>${dict.label }</label>
					</c:forEach>
				</c:when>
				<c:when test="${dataQueryForm.showType eq 'radiobox'}">
					<c:forEach items="${fns:getDictList(dataQueryForm.dictType)}" var="dict">
					<input type="radio" name="${dataQueryForm.parameter }" value="${dict.value }" ${dataQueryForm.parameterValue eq dict.value ? "checked='checked'": ""}/><label>${dict.label }</label>
					</c:forEach>
				</c:when>
				<c:when test="${dataQueryForm.showType eq 'dateselect'}">
					<input name="${dataQueryForm.parameter }" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						value="${dataQueryForm.parameterValue}" onclick="WdatePicker({dateFmt:'${dataQueryForm.dateFormat}'});"/>
				</c:when>
			</c:choose>
			</li>
			</c:forEach>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:if test="${exportable eq '1' }">
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			</c:if>
			<li class="clearfix"></li>
			</c:if>
			<c:if test="${exportable eq '1' && empty dataQueryFormList }">
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="clearfix"></li>
			</c:if>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:forEach items="${dataQueryRowList }" var="dataQueryRow">
					<c:choose>
						<c:when test="${dataQueryRow.sortable eq '1' }">
				<th class="sort-column ${dataQueryRow.rowName}">${dataQueryRow.showRowName }</th>
						</c:when>
						<c:otherwise>
				<th>${dataQueryRow.showRowName }</th>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="data">
			<tr>
				<c:forEach items="${dataQueryRowList }" var="dataQueryRow">
					<c:choose>
						<c:when test="${not empty dataQueryRow.dateFormat }">
				<td><fmt:formatDate value="${data[dataQueryRow.showRowName] }" pattern="${dataQueryRow.dateFormat }"/></td>
						</c:when>
						<c:when test="${not empty dataQueryRow.dictType }">
				<td>${fns:getDictLabels(data[dataQueryRow.showRowName], dataQueryRow.dictType, '')}</td>
						</c:when>
						<c:otherwise>
				<td>${data[dataQueryRow.showRowName] }</td>
						</c:otherwise>
					</c:choose>
				
				</c:forEach>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>