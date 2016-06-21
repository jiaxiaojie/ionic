<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>大转盘中奖记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate({
				
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
		<li class="active"><a href="${ctx}/marketing/marketingWheelGetPrizeRecord/">大转盘中奖记录列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="marketingWheelGetPrizeRecord" action="${ctx}/marketing/marketingWheelGetPrizeRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>*活动名称：</label>
				<form:select path="activityId" class="input-medium required">
					<form:option value="" label=""/>
					<form:options items="${activityList}" itemLabel="name" itemValue="acticityId" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>用户姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>奖品名称：</label>
				<form:input path="prizeName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>奖品类型：</label>
				<form:select path="prizeType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('marketing_award_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('marketing_wheel_get_prize_record_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>中奖时间：</label>
				<input name="beginGetDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingWheelGetPrizeRecord.beginGetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endGetDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingWheelGetPrizeRecord.endGetDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>用户姓名</th>
				<th>奖品名称</th>
				<th>奖品类型</th>
				<th>奖品值</th>
				<th>中奖时间</th>
				<th>失效时间</th>
				<th>状态</th>
				<shiro:hasPermission name="marketing:marketingWheelGetPrizeRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="marketingWheelGetPrizeRecord">
			<tr>
				<td><a href="${ctx}/marketing/marketingWheelGetPrizeRecord/form?id=${marketingWheelGetPrizeRecord.id}">
					${marketingWheelGetPrizeRecord.mobile}
				</a></td>
				<td>
					${marketingWheelGetPrizeRecord.customerName}
				</td>
				<td>
					${marketingWheelGetPrizeRecord.prizeName}
				</td>
				<td>
					${fns:getDictLabel(marketingWheelGetPrizeRecord.prizeType, 'marketing_award_type_dict', '')}
				</td>
				<td>
					${marketingWheelGetPrizeRecord.prizeValue}
				</td>
				<td>
					<fmt:formatDate value="${marketingWheelGetPrizeRecord.getDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${marketingWheelGetPrizeRecord.invalidDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(marketingWheelGetPrizeRecord.status, 'marketing_wheel_get_prize_record_status_dict', '')}
				</td>
				<shiro:hasPermission name="marketing:marketingWheelGetPrizeRecord:edit"><td>
				<c:if test="${marketingWheelGetPrizeRecord.status == '1' && marketingWheelGetPrizeRecord.prizeType == '5' }">
					<a href="${ctx}/marketing/marketingWheelGetPrizeRecord/given?id=${marketingWheelGetPrizeRecord.id}" onclick="return confirmx('确定已将此奖品送给用户了吗？', this.href)">确定已赠送</a>
				</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>