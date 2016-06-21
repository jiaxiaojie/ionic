<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>债权转让管理</title>
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
		<li><a href="${ctx}/project/projectBaseInfo/${modelMenus.backPath}">借贷产品列表</a></li>
		<li class="active"><a HERF="#">借贷产品信息</a></li>
	</ul><br/>
	<jsp:include page="./common/jdprojectInfoMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="projectTransferInfo" action="${ctx}/project/projectTransferInfo/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="queryProjectId" name="queryProjectId" type="hidden" value="${projectTransferInfo.queryProjectId}"/>
		<ul class="ul-form">
			<li><label>转让人名称：</label>
				<form:input path="transferName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>转让时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectTransferInfo.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectTransferInfo.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>转让状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_transfer_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>转让编号</th>
				<th>原始项目编号</th>
				<th>原投资记录编号</th>
				<th>原始项目还款结束日期</th>
				<th>转让人登录名</th>
				<th>转让人名称</th>
				<th>转让价格</th>
				<th>公允价格</th>
				<th>转让截止日期</th>
				<th>手续费收取</th>
				<th>转让时间</th>
				<th>剩余债权</th>
				<th>转让状态</th>
				<shiro:hasPermission name="project:projectTransferInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectTransferInfo">
			<tr>
				<td><a href="${ctx}/project/projectTransferInfo/form?id=${projectTransferInfo.transferProjectId}">
					${projectTransferInfo.transferProjectId}
				</a></td>
				<td>
					${projectTransferInfo.projectId}
				</td>
				<td>
					${projectTransferInfo.investmentRecordId}
				</td>
				<td>
					<fmt:formatDate value="${projectTransferInfo.projectEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${projectTransferInfo.transferAccount.accountName}
				</td>
				<td>
					${projectTransferInfo.transferCustomer.customerName}
				</td>
				<td>
				<fmt:formatNumber value="${projectTransferInfo.transferPrice}" pattern="#0.00"/>
					
				</td>
				<td>
				<fmt:formatNumber value="${projectTransferInfo.fairPrice}" pattern="#0.00"/>
					
				</td>
				<td>
					${projectTransferInfo.discountDate}
				</td>
				<td>
					${projectTransferInfo.serviceChargeType}
				</td>
				<td>
					<fmt:formatDate value="${projectTransferInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${projectTransferInfo.remainderCreditor}
				</td>
				<td>
					${fns:getDictLabel(projectTransferInfo.status, 'project_transfer_dict', '')}
				</td>
				<shiro:hasPermission name="project:projectTransferInfo:edit"><td>
    				<a href="${ctx}/project/projectTransferInfo/form?id=${projectTransferInfo.id}">修改</a>
					<a href="${ctx}/project/projectTransferInfo/delete?id=${projectTransferInfo.id}" onclick="return confirmx('确认要删除该债权转让吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>