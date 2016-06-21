<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运营数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出运营数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var searchAction = $("#searchForm").attr("action");
						$("#searchForm").attr("action","${ctx}/operation/operationData/export");
						$("#searchForm").submit();
						$("#searchForm").attr("action",searchAction);
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
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
		<li class="active"><a href="${ctx}/operation/operationData/">运营数据列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="operationData" action="${ctx}/operation/operationData/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="id" name="id" type="hidden" value="" />
		<ul class="ul-form">
			 <li><label>数据时间段：</label>
				<input name="beginDt" id="startdt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${operationData.beginDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddt\')}',isShowClear:true});"/> - 
				<input name="endDt" isShowClear='true' id="enddt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate  value="${operationData.endDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdt\')}',isShowClear:true});"/>
			</li>
			 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>日期</th>
				<th>注册人数</th>
				<th>开通第三方账号人数</th>
				<th>绑卡用户数</th>
				<th>充值额度</th>
				<th>提现额度</th>
				<th>首次投资人数</th>
				<th>首次投资总额度</th>
				<th>复投用户人数</th>
				<th>复投用户投资总额度</th>
				<th>投资次数</th>
				<th>投资额度</th>
				<shiro:hasPermission name="operation:operationData:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="operationData">
			<tr>
				<td><a href="${ctx}/operation/operationData/form?id=${operationData.id}"><fmt:formatDate value="${operationData.date}" pattern="yyyy-MM-dd"/></a>
				</td>
				<td>${operationData.registerCount}</td>
				<td>${operationData.openThirdAccountCount}</td>
				<td>${operationData.bindBankCardCount}</td>
				<td>${operationData.rechargeAmount}</td>
				<td>${operationData.withdrawAmount}</td>
				<td>${operationData.investmentFistNumber}</td>
				<td>${operationData.investmentFistTotalAmount}</td>
				<td>${operationData.repeatedInvestmentNumber}</td>
				<td>${operationData.repeatedInvestmentTotalAmount}</td>
				<td>${operationData.investmentTimes}</td>
				<td>${operationData.investmentAmount}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>