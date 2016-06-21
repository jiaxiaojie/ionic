<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账户余额变更流水管理</title>
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
		<li><a href="${ctx}/customer/customerAccountInfo/customerAccountInfoList">会员列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerAccountInfo/customerAccountInfo?accountId=${model.accountId}">会员详细信息</a></li>
	</ul><br/>
	<jsp:include page="./common/customerAccountInfoMenu.jsp"/>
	<form:form id="searchForm" style=""  modelAttribute="customerIntegralHis"  action="${ctx}/customer/customerAccountInfo/customerIntegralDetailInfo?accountId=${model.accountId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>花生豆来源：</label>
				<form:select path="changeType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('customer_integral_change_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li><label>
				</label>

				<select id="receiveOrUsed" name="receiveOrUsed" >
					<option ${empty customerIntegralHis.receiveOrUsed?"selected='selected'":""}>操作时间</option>
					<option ${customerIntegralHis.receiveOrUsed=='1'?"selected='selected'":""} value="1">获得时间</option>
					<option ${customerIntegralHis.receiveOrUsed=='2'?"selected='selected'":""} value="2">使用时间</option>
				</select>
				：
				<input name="beginOpDate" id="beginOpDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${customerIntegralHis.beginOpDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endOpDate\')}',isShowClear:true});"/>
				<input name="endOpDate" isShowClear='true' id="endOpDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate  value="${customerIntegralHis.endOpDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginOpDate\')}',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;&nbsp;<spam>可用花生豆：${empty customerIntegralSnapshot.integralBalance?0:customerIntegralSnapshot.integralBalance }颗</spam>&nbsp;&nbsp;
				<spam>本月获得：${empty getIntegralCurrentMonth ? 0 : getIntegralCurrentMonth }颗</spam>&nbsp;&nbsp;
				<spam>累计获得：${empty getTotalIntegral ? 0 : getTotalIntegral }颗</spam></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="35%">时间</th>
	            <th width="35%">操作</th>
	            <th width="30%">花生豆变化</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerIntegralHis">
			<tr>
								<td><fmt:formatDate value="${customerIntegralHis.opDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                    <td>${fns:getDictLabel(customerIntegralHis.changeType, "customer_integral_change_type_dict"	,"")}</td>
	                    <td>${customerIntegralHis.changeVal }</td>
							</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>