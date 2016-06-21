<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员投资额度分布</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		     
		});
		function page(n,s){
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/operation/operationData/customerInvestmentAmountDistributionList">会员投资额度分布列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="projectInvestmentRecord" action="${ctx}/operation/operationData/customerInvestmentAmountDistributionList" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
             	     <li><label>投资时间段：</label>
				<input name="beginOpDt" id="startdt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${beginOpDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddt\')}',isShowClear:true});"/> - 
				<input name="endOpDt" id="enddt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate  value="${endOpDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdt\')}',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<!-- <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
	           <tr><td>投资额度范围(元)</td><td>各额度档次投资人数(人)</td><td>平均投资额度(元)</td></tr>
				<tr><td id="contentTable">0＜累计投资额度≤5000</td> <td id="zeroToFiveThousandCount">${map.zeroToFiveThousandCount}</td><td><fmt:formatNumber value="${map1.avgAmount1!=null?map1.avgAmount1:0.0}" pattern="#0.00" /></td></tr>
				 <tr><td>5000＜累计投资额度≤10000</td><td>${map.fiveThousandToTenThousandCount}</td><td><fmt:formatNumber value="${ map1.avgAmount2!=null?map1.avgAmount2:0.0}" pattern="#0.00" /></td></tr>
				<tr><td>10000＜累计投资额度≤20000</td><td>${map.tenThousandToTwentyThousandCount}</td><td><fmt:formatNumber value="${ map1.avgAmount3!=null?map1.avgAmount3:0.0}" pattern="#0.00" /></td></tr>
				<tr><td>累计投资额度＞20000</td><td>${map.twentyThousandCount}</td><td> <fmt:formatNumber value="${map1.avgAmount4!=null?map1.avgAmount4:0.0}" pattern="#0.00" /></td></tr>
				<tr><td>投资总人数</td><td><c:out value="${map.zeroToFiveThousandCount +map.fiveThousandToTenThousandCount+map.tenThousandToTwentyThousandCount+map.twentyThousandCount}"></c:out></td>
				<td><fmt:formatNumber value="${map2.sumAmountAvg!=null?map2.sumAmountAvg:0.0}" pattern="#0.00" /></td></tr>
		</tbody>
	</table>
</body>
</html>