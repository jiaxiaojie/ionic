<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借贷产品审核记录管理</title>
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
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>审核流水号</th>
				<th>审核人员</th>
				<th>审核备注</th>
				<th>审核结果</th>
				<th>审核日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectReviewRecord">
			<tr>
				<td>
					${projectReviewRecord.id}
				</td>
				<td>
					${projectReviewRecord.user.name}
				</td>
				<td>
					${projectReviewRecord.reviewRemark}
				</td>
				<td>
					${fns:getDictLabel(projectReviewRecord.reviewResult, 'project_status_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${projectReviewRecord.reviewDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>