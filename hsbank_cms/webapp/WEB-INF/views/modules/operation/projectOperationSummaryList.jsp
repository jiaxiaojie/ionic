<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目运营数据汇总管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出运营数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var searchAction = $("#searchForm").attr("action");
						$("#searchForm").attr("action","${ctx}/operation/projectOperationSummary/export");
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
		<li class="active"><a href="${ctx}/operation/projectOperationSummary/">运营数据汇总列表</a></li>
		<shiro:hasPermission name="operation:projectOperationSummary:edit"><li><a href="${ctx}/operation/projectOperationSummary/form">运营数据汇总添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="projectOperationSummary" action="${ctx}/operation/projectOperationSummary/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日期：</label>
				<input name="beginDate" id="startdt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectOperationSummary.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddt\')}',isShowClear:true});"/> - 
				<input name="endDate" id="enddt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectOperationSummary.endDate}" pattern="yyyy-MM-dd"/>"
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
				<th>累计募资额</th>
				<th>累计偿还本金</th>
				<th>累计偿还利息</th>
				<th>新花生累计募资额</th>
				<th>月花生累计募资额</th>
				<th>双月花生累计募资额</th>
				<th>季花生累计募资额</th>
				<th>双季花生累计募资额</th>
				<th>年花生累计募资额</th>
			    <th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectOperationSummary">
			<tr>
			    <td>
					<fmt:formatDate value="${projectOperationSummary.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td><fmt:formatNumber value="${projectOperationSummary.financeAmount}" pattern="#0.00"/>
				</td>
				<td><fmt:formatNumber value="${projectOperationSummary.repayPrincipal}" pattern="#0.00"/>
				</td>
				<td><fmt:formatNumber value="${projectOperationSummary.repayInterest}" pattern="#0.00"/>
				</td>
				<td><fmt:formatNumber value="${projectOperationSummary.xinFinanceAmount}" pattern="#0.00"/>
				</td>
				<td><fmt:formatNumber value="${projectOperationSummary.yueFinanceAmount}" pattern="#0.00"/>
				</td>
				<td><fmt:formatNumber value="${projectOperationSummary.shuangyueFinanceAmount}" pattern="#0.00"/>
				</td>
				<td><fmt:formatNumber value="${projectOperationSummary.jiFinanceAmount}" pattern="#0.00"/>
				</td>
				<td><fmt:formatNumber value="${projectOperationSummary.shuangjiFinanceAmount}" pattern="#0.00"/>
				</td>
				<td><fmt:formatNumber value="${projectOperationSummary.nianFinanceAmount}" pattern="#0.00"/>
				</td>
			
			<shiro:hasPermission name="operation:projectOperationSummary:view"><td>
			<a href="${ctx}/operation/projectOperationSummary/form?id=${projectOperationSummary.id}">查看</a>
			</td></shiro:hasPermission>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>