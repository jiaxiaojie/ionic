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
						$("#searchForm").attr("action","${ctx}/operation/projectOperation/export1");
						$("#searchForm").submit();
						$("#searchForm").attr("action",searchAction);
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			
			
			//计划还款日期
			$("#startDate").click(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,isShowToday:true,maxDate:$("#endDate").val()});
			});
			//投标截止时间
			$("#endDate").click(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,isShowToday:true,minDate:$("#startDate").val()});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	
	<script type="text/javascript">
			$(document).ready(function() {
				var last3DayAmount = $("#last3DayAmount").val();
				var platformUserNo = $("#platformUserNo").val();
				if(platformUserNo==''){
					document.getElementById("a").innerHTML="请输入用户名(查询易宝账户余额、三天内还款额及账户资金状态)"; 
				}
				$.ajax({
					type : 'post',
					url : '${ctx}/yeepay/direct/account_info',
					data : {
						platformUserNo : platformUserNo
					},
					dataType : 'json',
					success : function(data) {
						$("#availableAmount").text(data.availableAmount==null?0:data.availableAmount );
						var capitalStatus = $("#capitalStatus");
						if(parseFloat(data.availableAmount)<parseFloat(last3DayAmount)){
							capitalStatus.text("警告");
							capitalStatus.attr("class","redStyle");
						}else{
							capitalStatus.text("正常");
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						$("#availableAmount").text("查询失败");
						$("#capitalStatus").text("查询失败");
					}
				});
				
				
				
				
				
			});
			
		
			</script>
			<style type="text/css">
				.redStyle{
					color:red;
					font-size: 13px;
					font-weight: 600;
				}
			</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/operation/projectOperation/projectRepayPlanList">项目还款计划列表</a></li>
	</ul>
	<input id="platformUserNo"     type="hidden" value="${platformUserNo}"/>
	<input id="last3DayAmount"  type="hidden" value="${last3DayAmount==null?0:last3DayAmount}"/>
	<form:form id="searchForm" modelAttribute="projectRepaymentSplitRecord"  action="${ctx}/operation/projectOperation/projectRepayPlanList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li>
				<label>用户名：</label>
				<form:select  path="customerAccount.accountName"  class="input-large">
				<form:option value="" label="请选择"/><form:options   items="${accountNameList}"   htmlEscape="false"/>
				</form:select>   
			</li>
			<li>
				<label>还款日期：</label>
				<input id="startDate" name="startDate" value="<fmt:formatDate value="${projectRepaymentSplitRecord.startDate}" pattern="yyyy-MM-dd" />"
				onclick="WdatePicker({isShowClear:true});"
				 type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" /> - 
				<input id="endDate" name="endDate" value="<fmt:formatDate value="${projectRepaymentSplitRecord.endDate}" pattern="yyyy-MM-dd" />" 
					onclick="WdatePicker({isShowClear:true});"
				type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" />
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li id="a" class="btns">${platformUserNo} 账户当前余额为【<span id="availableAmount" >正在查询……</span>】元，三天内即将要还款额为【${last3DayAmount==null?0:last3DayAmount }】元，资金状况为：<span id="capitalStatus">正在查询……</span></li><!-- 正常/警告 -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>还款日期</th>
				<th>应还本金</th>
				<th>应还利息</th>
				<th>应还总额</th> 
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="map">
			<tr>
				<td>
					<fmt:formatDate value="${map.date}" pattern="yyyy-MM-dd" />
				</td>
				<td>
					<fmt:formatNumber value="${map.principal}" pattern="#0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${map.interest}" pattern="#0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${map.amount}" pattern="#0.00" />
				</td>
				<td>
					<a href="${ctx}/operation/projectOperation/projectRepayPlanDetail?date=<fmt:formatDate value="${map.date}" pattern="yyyy-MM-dd" />">详情</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>