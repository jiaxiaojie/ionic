<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员现金券管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function clearTicket(accountId){
		
		
		var yesOrNo =  confirm('确认要合并会员现金券吗？');
		
		if(yesOrNo){
			$.ajax({
				   type: "POST",
				   url: "${ctx}/customer/customerInvestmentTicket/clearTicket?accountId="+accountId,
				   success: function(result){
				     if(result.success){
				    	 alert("合并现金券成功！");
				     }else{
				    	 alert("合并现金券失败！");
				     }
				     $("#searchForm").submit();
				   }
			});
		}
		
		return false;
	}
		$(document).ready(function() {
			$("#clearTicketBtn").bind('click', function() {
				var accountId = $("#accountId").val();
				if(accountId == null || accountId == ""){
					alert("请选择一个账户！");
					return false;
				}
				clearTicket(accountId);
				
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function submitSearchForm(){
			$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/customer/customerAccountInfo/customerAccountInfoList">现金券合并列表</a></li>
		
	</ul><br/>
	
	<form:form id="searchForm" modelAttribute="customerInvestmentTicket" action="${ctx}/customer/customerInvestmentTicket/clearList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="status" name="status" type="hidden" value="0"/>
		<ul class="ul-form">
			<li><label>账户：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
				<%-- <li class="btns"><a href="#" id="accountList">选择</a></li> --%>
				<sys:selectPanel idName="resultAccountId" textName="resultAccountName" callbackOnSelected="submitSearchForm()" url="${ctx}/customer/customerAccountInfo/customerAccountInfoList" path="accountId" title="选择账户" ></sys:selectPanel>
			</li>
			
			<li class="btns"><input id="clearTicketBtn" class="btn btn-primary" type="button" value="合并现金券"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="刷新列表"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>账号编号</th>
				<th>现金券面值</th>
				<th>获得时间</th>
				<th>失效时间</th>
				<th>获得备注</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerInvestmentTicket">
			<tr>
				<td>
					${customerInvestmentTicket.accountId}
				</td>
				<td>
					${customerInvestmentTicket.investmentTicketType.denomination}
				</td>
				<td>
					<fmt:formatDate value="${customerInvestmentTicket.getDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${customerInvestmentTicket.invalidDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerInvestmentTicket.getRemark}
				</td>
				
				
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>