<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员举报信息管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerTipoff/">会员举报信息列表</a></li>
		<li><a href="${ctx}/customer/customerTipoff/form">会员举报信息添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="customerTipoff" action="${ctx}/customer/customerTipoff/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>举报人：</label>
				<form:input path="informantsName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>举报时间：</label>
				<input name="beginCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerTipoff.beginCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerTipoff.endCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>举报查证人员：</label>
				<form:input path="verifyName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>回复时间：</label>
				<input name="beginReplyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerTipoff.beginReplyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endReplyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerTipoff.endReplyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>举报人</th>
				<th>举报时间</th>
				<th>举报查证人员</th>
				<th>回复时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerTipoff">
			<tr>
				<td><a href="${ctx}/customer/customerTipoff/form?id=${customerTipoff.id}">
					${customerTipoff.informantsName}
				</a></td>
				<td>
					<fmt:formatDate value="${customerTipoff.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerTipoff.verifyName}
				</td>
				<td>
					<fmt:formatDate value="${customerTipoff.replyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
    				<a href="${ctx}/customer/customerTipoff/form?id=${customerTipoff.id}">修改</a>
					<a href="${ctx}/customer/customerTipoff/delete?id=${customerTipoff.id}" onclick="return confirmx('确认要删除该会员举报信息吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>