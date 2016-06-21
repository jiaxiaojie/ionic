<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期产品赎回申请管理</title>
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
	
	
	 <ul class="nav nav-tabs" id="topMenu">
		<li class="active"><a href="${ctx}/current/currentProjectRedemptionApply/">活期产品赎回申请列表</a></li>
		<shiro:hasPermission name="current:currentProjectRedemptionApply:edit"><li><a href="${ctx}/current/currentProjectRedemptionApply/form">活期产品赎回申请添加</a></li></shiro:hasPermission>
	</ul> 
	
	<jsp:include page="./common/projectInfoMenu.jsp"/>
	<jsp:include page="../customer/common/customerPandectMenu.jsp"/>
	
	
	
	<form:form id="searchForm" modelAttribute="currentProjectRedemptionApply" action="${ctx}/current/currentProjectRedemptionApply/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<form:hidden path="projectId" htmlEscape="false" maxlength="20" class="input-medium"/>
		<form:hidden path="pageType" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员手机号：</label>
				<form:input path="currentProjectHoldInfo.customerBase.mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			
			
			<li><label>申请时间：</label>
				<input name="beginApplyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${currentProjectRedemptionApply.beginApplyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endApplyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${currentProjectRedemptionApply.endApplyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('current_project_redemption_apply_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>活期产品持有流水号</th>
				<th>会员手机号</th>
				<th>项目编号</th>
				<th>项目名称</th>
				<th>赎回本金</th>
				<th>申请终端</th>
				<th>申请时间</th>
				<th>状态</th>
				<th>完成时间</th>
				<shiro:hasPermission name="current:currentProjectRedemptionApply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="currentProjectRedemptionApply">
			<tr>
			<%--
				<td><a href="${ctx}/current/currentProjectRedemptionApply/form?id=${currentProjectRedemptionApply.id}">
					${currentProjectRedemptionApply.holdId}
				</a></td>
				 --%>
				 <td>
					${currentProjectRedemptionApply.holdId}
				</td>
				<td>
					${currentProjectRedemptionApply.currentProjectHoldInfo.customerBase.mobile}
				</td>
				<td>
					
					${p2p:abbrev(currentProjectRedemptionApply.currentProjectHoldInfo.currentProjectInfo.code,abbrevLength)}
				</td>
				<td>
					
					${p2p:abbrev(currentProjectRedemptionApply.currentProjectHoldInfo.currentProjectInfo.name,abbrevLength)}
				</td>
				<td>
					<fmt:formatNumber value="${currentProjectRedemptionApply.redeemPrincipal}" pattern="#0.##" />
				</td>
				
				<td>
					${fns:getDictLabel(currentProjectRedemptionApply.applyTerm, 'op_term_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${currentProjectRedemptionApply.applyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(currentProjectRedemptionApply.status, 'current_project_redemption_apply_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${currentProjectRedemptionApply.finishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="current:currentProjectRedemptionApply:edit"><td>
    				<a href="${ctx}/current/currentProjectRedemptionApply/form?id=${currentProjectRedemptionApply.id}">修改</a>
					<a href="${ctx}/current/currentProjectRedemptionApply/delete?id=${currentProjectRedemptionApply.id}" onclick="return confirmx('确认要删除该活期产品赎回申请吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>