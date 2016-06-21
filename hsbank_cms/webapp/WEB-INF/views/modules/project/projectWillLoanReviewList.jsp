<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>借贷意向审批管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function makeNewProject(willId){
		$.ajax({
			type : 'post',
			url : '${ctx}/project/projectWillLoan/copy',
			data : {
				willId : willId
			},
			dataType : 'json',
			success : function(data) {
				if(data.flag=='ok'){
					$.jBox.success("项目生成成功！");
					setTimeout(function(){
						window.location="${ctx}/project/projectWillLoan/reviewlist";
					},2000);
				}
			}
		});
	}
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a
			href="${ctx}/project/projectWillLoan/reviewlist">借贷意向待审批列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="projectWillLoan"
		action="${ctx}/project/projectWillLoan/reviewlist" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<input id="createUserId" name="createUserId" type="hidden" value="" />
		<ul class="ul-form">
			<li><label>标题：</label> <form:input path="title"
					htmlEscape="false" maxlength="1000" class="input-medium"
					style="width:120px" /></li>
			<li><label>联系人：</label> <form:input path="contactName"
					htmlEscape="false" maxlength="100" class="input-medium"
					style="width:120px" /></li>
			<li><label>手机号码：</label> <form:input path="mobile"
					htmlEscape="false" maxlength="20" class="input-medium"
					style="width:120px" /></li>
			<li><label>融资金额：</label> <form:input path="beginMoney"
					htmlEscape="false" class="input-medium" style="width:120px" />&nbsp;-&nbsp;<form:input
					path="endMoney" htmlEscape="false" class="input-medium"
					style="width:120px" /></li>
			<li><label>状态：</label> <form:select path="status"
					class="input-medium">
					<form:option value="" label="全部" />
					<form:options
						items="${fns:getDictList('project_will_loan_status_dict')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
			<li><label>申请时间：</label> <input name="beginCreateDt" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${projectWillLoan.beginCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" />
				- <input name="endCreateDt" type="text" readonly="readonly"
				maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${projectWillLoan.endCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>用途</th>
				<th>期限</th>
				<th>年化利率</th>
				<th>联系人</th>
				<th>手机号码</th>
				<th>融资金额</th>
				<th>服务费</th>
				<th>所在区域</th>
				<th>申请时间</th>
				<th>确认人员</th>
				<th>确认状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="projectWillLoan">
				<tr>
					<td>${projectWillLoan.title}</td>
					<td>${fns:getDictLabel(projectWillLoan.useType, 'customer_credit_loan_use_dict', '')}
					</td>
					<td>${projectWillLoan.duration}</td>
					<td>${projectWillLoan.annualizedRate}</td>

					<td>${projectWillLoan.contactName}</td>
					<td>${projectWillLoan.mobile}</td>
					<td><fmt:formatNumber value="${projectWillLoan.loanMoney}" pattern="#0.00"/></td>
					<td>${projectWillLoan.serviceCharge}</td>
					<td>${projectWillLoan.area.name}</td>
					<td><fmt:formatDate value="${projectWillLoan.createDt}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${projectWillLoan.confirmUser.name}</td>
					<td>${fns:getDictLabel(projectWillLoan.status, 'project_will_loan_status_dict', '')}
					</td>
					<td>
						 <shiro:hasPermission name="project:projectWillLoan:review">
							<c:choose>
								<c:when test="${projectWillLoan.status=='0'}">
									<a
								href="${ctx}/project/projectWillLoan/reviewform?id=${projectWillLoan.id}">审批</a>
								</c:when>
								
								<c:when test="${(projectWillLoan.status=='1')&&(projectWillLoan.hasCreateProject=='0')}">
									<a href="#" onclick="makeNewProject('${projectWillLoan.id}')">生成项目</a>
								</c:when>
								<c:otherwise >
								</c:otherwise>
							</c:choose>
						</shiro:hasPermission> <shiro:hasPermission name="project:projectWillLoan:view">
							<a
								href="${ctx}/project/projectWillLoan/viewform?id=${projectWillLoan.id}">查看</a>
						</shiro:hasPermission></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>