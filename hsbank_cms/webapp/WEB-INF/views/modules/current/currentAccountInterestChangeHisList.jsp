<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期账户利息变更历史管理</title>
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
				<li class="active"><a href="${ctx}/current/currentAccountInterestChangeHis/">活期账户利息变更历史列表</a></li>
				<shiro:hasPermission name="current:currentAccountInterestChangeHis:edit"><li><a href="${ctx}/current/currentAccountInterestChangeHis/form">活期账户利息变更历史添加</a></li></shiro:hasPermission>
			</ul>
	
	<jsp:include page="./common/projectInfoMenu.jsp"/>
	<jsp:include page="../customer/common/customerPandectMenu.jsp"/>
	
	<form:form id="searchForm" modelAttribute="currentAccountInterestChangeHis" action="${ctx}/current/currentAccountInterestChangeHis/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="projectId" />
		<form:hidden path="pageType" />
		<ul class="ul-form">
			
			<li><label>会员手机号：</label>
				<form:input path="cb.mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			
			<li><label>变更类型：</label>
				<form:select path="changeType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('current_account_interest_change_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>操作时间：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${currentAccountInterestChangeHis.beginOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${currentAccountInterestChangeHis.endOpDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>账户编号</th>
				<th>会员手机号</th>
				<th>项目编号</th>
				<th>项目名称</th>
				<th>变更值</th>
				<th>变更类型</th>
				<th>操作终端</th>
				<th>操作时间</th>
				<shiro:hasPermission name="current:currentAccountInterestChangeHis:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="currentAccountInterestChangeHis">
			<tr>
				<%-- <td><a href="${ctx}/current/currentAccountInterestChangeHis/form?id=${currentAccountInterestChangeHis.id}">
					${currentAccountInterestChangeHis.accountId}
				</a></td>--%>
				<td>
					${currentAccountInterestChangeHis.accountId}
				</td>
				<td>
					${currentAccountInterestChangeHis.cb.mobile}
				</td>
				<td>
					${p2p:abbrev(currentAccountInterestChangeHis.currentProjectInfo.code,abbrevLength)}
				</td>
				<td>
					${p2p:abbrev(currentAccountInterestChangeHis.currentProjectInfo.name,abbrevLength)}
				</td>
				<td>
					<fmt:formatNumber value="${currentAccountInterestChangeHis.changeValue}" pattern="#0.####" />
				</td>
				<td>
					${fns:getDictLabel(currentAccountInterestChangeHis.changeType, 'current_account_interest_change_type', '')}
				</td>
				<td>
					${fns:getDictLabel(currentAccountInterestChangeHis.opTerm, 'op_term_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${currentAccountInterestChangeHis.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="current:currentAccountInterestChangeHis:edit"><td>
    				<a href="${ctx}/current/currentAccountInterestChangeHis/form?id=${currentAccountInterestChangeHis.id}">修改</a>
					<a href="${ctx}/current/currentAccountInterestChangeHis/delete?id=${currentAccountInterestChangeHis.id}" onclick="return confirmx('确认要删除该活期账户利息变更历史吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>