<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客服数据查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			 $("#btnExport").click(function(){
				top.$.jBox.confirm(" 确认要导出客服查询数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var searchAction = $("#searchForm").attr("action");
						$("#searchForm").attr("action","${ctx}/customer/customerServiceInquiry/export1");
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
		<li class="active"><a href="${ctx}/customer/customerServiceInquiry/list">客服查询栏</a></li>
	</ul>
	
	<form:form id="searchForm" modelAttribute="customerAccount"  action="${ctx}/customer/customerServiceInquiry/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<li class="clearfix"></li>
		    <li><label>用户状态：</label>
				<form:select path="userStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('user_status')}" itemLabel="label"  itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>注册时间：</label>
				<input name="beginRegisterDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerAccount.beginRegisterDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> - 
				<input name="endRegisterDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerAccount.endRegisterDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			 <li><label>投资时间：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectInvestmentRecord.beginOpDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> - 
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectInvestmentRecord.endOpDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit"  value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<div style="display: block;" id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><div>总共查询到:${coustomerCount}条记录</div></div>
</body>
</html>