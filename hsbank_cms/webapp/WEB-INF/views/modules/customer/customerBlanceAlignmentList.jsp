<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账户余额对齐管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerBlanceAlignment/list">会员账户余额对齐列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="customerBlanceAlignment" action="${ctx}/customer/customerBlanceAlignment/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号编号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
			    <input name="beginCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerBlanceAlignment.beginCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerBlanceAlignment.endCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>账号编号</th>
				<th>姓名</th>
				<th>task_平台账户余额</th>
				<th>task_易宝账户余额</th>
				<th>状态</th>
				<th>创建时间</th>
				<shiro:hasPermission name="customer:customerBlanceAlignment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerBlanceAlignment">
			<tr>
				<td><a href="${ctx}/customer/customerBlanceAlignment/form?id=${customerBlanceAlignment.id}">
					${customerBlanceAlignment.accountId}
				</a></td>
				<td>
					${customerBlanceAlignment.customerName}
				</td>
				<td>
					<fmt:formatNumber value="${customerBlanceAlignment.taskGoldBalance}" pattern="#0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${customerBlanceAlignment.taskYeepayBalance}" pattern="#0.00" />
				</td>
				<td>
					${fns:getDictLabel(customerBlanceAlignment.status, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${customerBlanceAlignment.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerBlanceAlignment:edit"><td>
				  <c:if test="${customerBlanceAlignment.status != '1' }">
    				<a href="${ctx}/customer/customerBlanceAlignment/alignment?id=${customerBlanceAlignment.id}">余额对齐</a>
    			  </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>