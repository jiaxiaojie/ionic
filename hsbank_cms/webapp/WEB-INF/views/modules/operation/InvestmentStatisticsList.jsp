<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运营数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		 	$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出日投资额统计数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var searchAction = $("#searchForm").attr("action");
						$("#searchForm").attr("action","${ctx}/operation/operationData/exports");
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
		<li class="active"><a href="${ctx}/operation/operationData/InvestmentStatistics">日投资额统计列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="projectInvestmentRecord" action="${ctx}/operation/operationData/InvestmentStatistics" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="id" name="id" type="hidden" value="" />
		<ul class="ul-form">
			  <li><label>查询时间段：</label>
				<input name="beginOpDt" id="startdt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${beginOpDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddt\')}',isShowClear:true});"/> - 
				<input name="endOpDt" isShowClear='true' id="enddt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate  value="${endOpDt}" pattern="yyyy-MM-dd"/>"
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
				<th>日投资总额度</th>
				<th>小伙伴投资总额度</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="map">
			 <tr>
				<td>${map.date}</td>
				<td><a href="${ctx}/operation/operationData/daytotalAmountClearList?date=${map.date}"><fmt:formatNumber value="${map.sumAmount}" pattern="#0.00" /></a></td>
			    <td><a href="${ctx}/operation/operationData/friendsDaytotalAmountClearList?date=${map.date}"><fmt:formatNumber value="${map.friendsSumAmount}" pattern="#0.00" /></a></td>
			</tr> 
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>