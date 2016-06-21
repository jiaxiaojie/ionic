<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借贷产品管理</title>
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
		<li class="active"><a href="${ctx}/project/projectBaseInfo/createlist">项目下线列表</a></li>
		
	</ul>
	<form:form id="searchForm" modelAttribute="projectBaseInfo" action="${ctx}/project/projectBaseInfo/createlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>项目类型：</label>
				<form:select path="projectTypeCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>还款方式：</label>
				<form:select path="repaymentMode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_repayment_mode_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label title="计划还款日期">还款日期：</label>
				<input name="beginPlannedRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectBaseInfo.beginPlannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endPlannedRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectBaseInfo.endPlannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			
			<li><label>新手项目：</label>
				<form:select path="isNewUser" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_is_new_user_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>推荐项目：</label>
				<form:select path="isRecommend" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_is_recommend_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<form:input path="createUserId"  type="hidden" htmlEscape="false" maxlength="20" class="input-medium" value="${fns:getUser().id}"/>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目编号</th>
				<th>是否新手项目</th>
				<th>是否推荐项目</th>
				<th>项目类型</th>
				<th>项目名称</th>
				<th>还款方式</th>
				<th>贷款用途</th>
				<th>本期融资金额</th>
				<th>计划还款日期</th>
				<th>项目状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectBaseInfo">
			<tr>
				<td><a href="${ctx}/project/projectBaseInfo/createform?backPath=createlist&id=${projectBaseInfo.projectId}">
					${p2p:abbrev(projectBaseInfo.projectCode,10)}
				</a></td>
				<td>
					${fns:getDictLabel(projectBaseInfo.isNewUser, 'project_is_new_user_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.isRecommend, 'project_is_recommend_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.projectTypeCode, 'project_type_dict', '')}
				</td>
				<td>
				    ${p2p:abbrev(projectBaseInfo.projectName,10)}
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}
				</td>
				<td>
					${projectBaseInfo.useMethod}
				</td>
				<td>
				<fmt:formatNumber value="${projectBaseInfo.financeMoney}" pattern="#0.00"/>
				</td>
				<td>
					<fmt:formatDate value="${projectBaseInfo.plannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.projectStatus, 'project_status_dict', '')}
				</td>
				<td>
    				<a href="${ctx}/project/projectBaseInfo/downline?projectId=${projectBaseInfo.projectId}" onclick="return confirmx('确认要下线该借贷产品吗？', this.href)">快速下线</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>