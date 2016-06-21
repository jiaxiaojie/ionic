<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>营销活动奖励记录管理</title>
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
		<li class="active"><a href="${ctx}/marketing/marketingActivityAwardRecord/">营销活动奖励记录列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="marketingActivityAwardRecord" action="${ctx}/marketing/marketingActivityAwardRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动编号：</label>
				<form:select path="activityId" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${marketingActivityInfoList}" itemLabel="name" itemValue="acticityId" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>行为编号：</label>
				<form:select path="behaviorCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('marketing_behavior_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>渠道编号：</label>
				<form:select path="channelId" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>用户姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>用户手机号：</label>
				<form:input path="customerMobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>产品类型：</label>
				<form:select path="awardType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('marketing_award_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>奖励时间：</label>
				<input name="beginAwardDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingActivityAwardRecord.beginAwardDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endAwardDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${marketingActivityAwardRecord.endAwardDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>奖励值：</label>
				<form:input path="awardValue" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>活动编号</th>
				<th>行为编号</th>
				<th>渠道编号</th>
				<th>用户姓名</th>
				<th>用户手机号</th>
				<th>奖励产品类型</th>
				<th>获得奖励时间</th>
				<th>奖励值</th>
				<shiro:hasPermission name="marketing:marketingActivityAwardRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="marketingActivityAwardRecord">
			<tr>
				<td>
					<c:forEach var="marketingActivityInfo" items="${marketingActivityInfoList}">  
						<c:if test="${marketingActivityInfo.acticityId eq marketingActivityAwardRecord.activityId }">
							<c:out value="${marketingActivityInfo.name }"></c:out>
						</c:if>
					</c:forEach>
				</td>
				<td>
					${fns:getDictLabel(marketingActivityAwardRecord.behaviorCode, 'marketing_behavior_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(marketingActivityAwardRecord.channelId, 'op_term_dict', '')}
				</td>
				<td>
					${marketingActivityAwardRecord.customerName}
				</td>
				<td>
					${marketingActivityAwardRecord.customerMobile}
				</td>
				<td>
					${fns:getDictLabel(marketingActivityAwardRecord.awardType, 'marketing_award_type_dict', '')}
				</td>
				<td>
					<fmt:formatDate value="${marketingActivityAwardRecord.awardDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${marketingActivityAwardRecord.awardValue}
				</td>
				<shiro:hasPermission name="marketing:marketingActivityAwardRecord:edit"><td>
    				<a href="${ctx}/marketing/marketingActivityAwardRecord/form?id=${marketingActivityAwardRecord.id}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>