<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>赎回数据管理</title>
	<meta name="decorator" content="default"/>
	 <script type="text/javascript">
		$(document).ready(function() {
			//开始日期
			$("#startDate").click(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,isShowToday:false,maxDate:$("#endDate").val()});
			});
			//截止时间
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
	</script> 
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/current/redemptionInfo/redemptionInfoList">赎回数据列表</a></li>
	</ul>
	<form id="searchForm" action="${ctx}/current/redemptionInfo/redemptionInfoList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			 <li>
				<label>赎回日期：</label>
				<input id="startDate" name="startDate" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd" />" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" /> - 
				<input id="endDate" name="endDate" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" />
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>赎回日期</th>
				<th>申请赎回总人数</th>
				<th>通过申请人数</th>
				<th>已赎回人数</th>
			</tr>
		</thead>
		<tbody>
		 <c:forEach items="${page.list}" var="map">
			<tr>
				<td>
					<fmt:formatDate value="${map.date}" pattern="yyyy-MM-dd" />
				</td>
			 	<td>
			 	${map.ALLRedemptionNum}
				</td>
				<td>
				${map.totalNum}
				</td>
				<td>
				${map.RedemptionFinish}
				</td>
			</tr>
		</c:forEach> 
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>