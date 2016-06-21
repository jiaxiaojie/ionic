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
						$("#searchForm").attr("action","${ctx}/operation/projectOperation/export");
						$("#searchForm").submit();
						$("#searchForm").attr("action",searchAction);
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			//计息开始日期
			$("#startDate").click(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,isShowToday:false,maxDate:$("#endDate").val()});
			});
			//计息截止时间
			$("#endDate").click(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,isShowToday:false,minDate:$("#startDate").val()});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//查看还款计划
		function replayPlan(url){
			$.jBox.open("iframe:" + url + "", "还款计划表",1000, 800, { buttons: {'关闭': true}})
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/operation/projectOperation/list">运营数据列表</a></li>
 		<shiro:hasPermission name="operation:operationData:edit"><li><a href="${ctx}/operation/operationData/form">运营数据添加</a></li></shiro:hasPermission>
 	</ul>
	<form:form id="searchForm" modelAttribute="projectBaseInfo" action="${ctx}/operation/projectOperation/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		 <li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-medium"/>
		</li> 
		 <li><label>计息时间段：</label>
				<input name="beginDt" id="startdt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectBaseInfo.beginDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddt\')}',isShowClear:true});"/> - 
				<input name="endDt" id="enddt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate  value="${projectBaseInfo.endDt}" pattern="yyyy-MM-dd"/>"
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
				<th>项目名称</th>
				<th>项目发布时间</th>
				<th>开始计息时间</th>
				<th>借款额</th>
				<th>募资额</th>
				<th>已还本金</th>
				<th>已还利息</th>
				<th>项目状态</th>
 				<shiro:hasPermission name="project:projectRepaymentPlan:view"><th>操作</th></shiro:hasPermission>
 			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="map">
			<tr>
				<td>
					${map.projectName}
			    </td>
			    
			    <td>
					${map.publishDt}
				</td>
			    
			    <td>
 			    <fmt:formatDate value="${map.biddingDeadline}" pattern="yyyy-MM-dd"/>
				</td>
			    
				<td>
				   <fmt:formatNumber value="${map.financeMoney}" pattern="#0.00"/>
				</td>
				<td>
				  <fmt:formatNumber value="${map.hasFinancedMoney}" pattern="#0.00"/>
				</td>
				<td>
				 <fmt:formatNumber value="${map.hasRepaiedPrincipal}" pattern="#0.00"/>
				</td>
				<td>
				<fmt:formatNumber value="${map.hasRepaiedInterest}" pattern="#0.00"/>
				</td>
				<td>
				${fns:getDictLabel(map.projectStatus, 'project_status_dict', '')}
				</td>
				   <shiro:hasPermission name="project:projectRepaymentSplitRecord:view"><td>
				<a href="#" onclick="replayPlan('${ctx}/project/projectRepaymentSplitRecord/repaymentList?queryProjectId=${map.projectId}');">还款计划</a>
				</td></shiro:hasPermission>  
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>