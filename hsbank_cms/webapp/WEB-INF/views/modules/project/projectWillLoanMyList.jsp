<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借贷意向审批管理</title>
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
		<li class="active"><a href="${ctx}/project/projectWillLoan/mylist">借贷意向列表</a></li>
		<shiro:hasPermission name="project:projectWillLoan:edit"><li><a href="${ctx}/project/projectWillLoan/createform">借贷意向添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="projectWillLoan" action="${ctx}/project/projectWillLoan/mylist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>联系人：</label>
				<form:input path="contactName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>手机号码：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>融资金额：</label>
				<form:input path="loanMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>备注说明：</label>
				<form:input path="remark" htmlEscape="false" maxlength="1000" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectWillLoan.beginCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectWillLoan.endCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>标题</th>
				<th>期限</th>
				<th>年化利率</th>
				<th>联系人</th>
				<th>手机号码</th>
				<th>融资金额</th>
				<th>所在区域</th>
				<th>创建时间</th>
				<th>确认人员</th>
				<th>确认状态</th>
				<shiro:hasPermission name="project:projectWillLoan:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectWillLoan">
			<tr>
				<td>
					${projectWillLoan.title}
				</td>
				<td>
					${projectWillLoan.duration}
				</td>
				<td>
					${projectWillLoan.annualizedRate}
				</td>
				<td>
					${projectWillLoan.contactName}
				</td>
				<td>
					${projectWillLoan.mobile}
				</td>
				<td>
				<fmt:formatNumber value="${projectWillLoan.loanMoney}" pattern="#0.00"/>
					
				</td>
				<td>
					${projectWillLoan.area.name}
				</td>
				<td>
					<fmt:formatDate value="${projectWillLoan.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${projectWillLoan.confirmUserId}
				</td>
				<td>
					${fns:getDictLabel(projectWillLoan.status, 'project_will_loan_status_dict', '')}
				</td>
				<shiro:hasPermission name="project:projectWillLoan:edit"><td>
    				<a href="${ctx}/project/projectWillLoan/createform?id=${projectWillLoan.id}">修改</a>
					<a href="${ctx}/project/projectWillLoan/delete?id=${projectWillLoan.id}" onclick="return confirmx('确认要删除该借贷意向审批吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>