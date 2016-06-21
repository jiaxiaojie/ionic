<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户留言管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出留言数据吗？","系统提示",function(v,h,f){
					if(v=="ok") {
						var searchAction = $("#searchForm").attr("action");
						$("#searchForm").attr("action","${ctx}/feedback/customerLeaveMessage/export");
						console.log($("#searchForm").attr("action"));
						$("#searchForm").submit();
						$("#searchForm").attr("action",searchAction);
					}
				},{buttonsFocus:1});
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
		<li class="active"><a href="${ctx}/feedback/customerLeaveMessage/hanworldList">客户留言列表</a></li>
		<shiro:hasPermission name="feedback:customerLeaveMessage:edit"><li><a href="${ctx}/feedback/customerLeaveMessage/form">客户留言添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerLeaveMessage" action="${ctx}/feedback/customerLeaveMessage/hanworldList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<!-- <li><label>留言类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('customer_leave_message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> -->
			<li><label>操作时间：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerLeaveMessage.beginOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerLeaveMessage.endOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>手机号</th>
				<th>邮箱</th>
				<th>留言</th>
				<th>操作时间</th>
				<shiro:hasPermission name="feedback:customerLeaveMessage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerLeaveMessage">
			<tr>
				<td><a href="${ctx}/feedback/customerLeaveMessage/form?id=${customerLeaveMessage.id}">
					${customerLeaveMessage.name}
				</a></td>
				<td>
					${customerLeaveMessage.mobile}
				</td>
				<td>
					${customerLeaveMessage.email}
				</td>
				<td>
					${p2p:abbrev(customerLeaveMessage.content,50)}
				</td>
				<td>
					<fmt:formatDate value="${customerLeaveMessage.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="feedback:customerLeaveMessage:edit"><td>
    				<a href="${ctx}/feedback/customerLeaveMessage/form?id=${customerLeaveMessage.id}">修改</a>
					<a href="${ctx}/feedback/customerLeaveMessage/delete?id=${customerLeaveMessage.id}" onclick="return confirmx('确认要删除该客户留言吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>