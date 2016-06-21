<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>摇一摇活动管理</title>
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
		<li class="active"><a href="${ctx}/customer/customerShakeActivity">摇一摇活动列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="customerShakeActivity" action="${ctx}/customer/customerShakeActivity/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>是否已赠送：</label>
				<form:select path="hasGived" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('has_gived_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>摇奖时间：</label>
				<input name="beginShakeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerShakeActivity.beginShakeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endShakeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerShakeActivity.endShakeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>手机号</th>
				<th>姓名</th>
				<th>面额</th>
				<th>是否已赠送</th>
				<th>ip</th>
				<th>摇奖时间</th>
				<th>操作人员</th>
				<th>操作时间</th>
				<shiro:hasPermission name="customer:customerShakeActivity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerShakeActivity">
			<tr>
				<td>
					${customerShakeActivity.mobile}
				</td>
				<td>
					${customerShakeActivity.customerName}
				</td>
				<td>
					${customerShakeActivity.denomination}
				</td>
				<td>
					${fns:getDictLabel(customerShakeActivity.hasGived, 'has_gived_dict', '未赠送')}
				</td>
				<td>
					${customerShakeActivity.ip}
				</td>
				<td>
					<fmt:formatDate value="${customerShakeActivity.shakeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getUserById(customerShakeActivity.updateBy).loginName}
				</td>
				<td>
					<fmt:formatDate value="${customerShakeActivity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:customerShakeActivity:edit"><td>
					<c:if test="${customerShakeActivity.hasGived != '1' }">
					<a href="${ctx}/customer/customerShakeActivity/give?id=${customerShakeActivity.id}" onclick="resetTip()">赠送现金券</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>