<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>债权投资用户信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var action = $("#searchForm").attr("action");
						$("#searchForm").attr("action","${ctx}/credit/creditInvestUserInfo/export"+"?"+action.split("?")[1]);
						$("#searchForm").submit();
						$("#searchForm").attr("action",action);
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
		<li class="active"><a href="${ctx}/credit/creditInvestUserInfo/list">债权投资用户信息列表</a></li>
		<shiro:hasPermission name="credit:creditInvestUserInfo:edit"><li><a href="${ctx}/credit/creditInvestUserInfo/form">债权投资用户信息添加</a></li></shiro:hasPermission>
	</ul>
	<jsp:include page="./common/machineActionInfoMenu.jsp"/>
	<jsp:include page="./common/creditRemindMenu.jsp"/>
	
	<form:form id="searchForm" modelAttribute="creditInvestUserInfo" action="${ctx}/credit/creditInvestUserInfo/birthdayRemindList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		
		<ul class="ul-form">
			<li><label>债权：</label>
				<sys:selectPanel idName="resultCreditId" textName="resultCreditName" idField="id" callbackOnSelected="refreshForm2()" url="${ctx}/credit/creditBaseInfo/list?creditStatus=0" path="creditMachineAccount.creditId" title="选择所属债权" ></sys:selectPanel>
				<form:input path="creditMachineAccount.creditId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>投资人姓名：</label>
				<form:input path="investorName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			
			<li><label>手机号：</label>
				<form:input path="investorContactInfo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>生日：</label>
			
				<input name="beginInvestorBirthday" id="startdt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${creditInvestUserInfo.beginInvestorBirthday}"
					onclick="WdatePicker({dateFmt:'MM-dd',maxDate:'#F{$dp.$D(\'enddt\')}',isShowClear:true});"/> - 
				<input name="endInvestorBirthday" isShowClear='true' id="enddt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${creditInvestUserInfo.endInvestorBirthday}"
					onclick="WdatePicker({dateFmt:'MM-dd',minDate:'#F{$dp.$D(\'startdt\')}',isShowClear:true});"/>
				
				
			</li>
			<!-- 
			<li><label>证件号：</label>
				<form:input path="idNumber" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			
			<li><label>投资人性别：</label>
				<form:select path="investorGender" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('gender')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>证件类型：</label>
				<form:select path="idType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('id_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			 -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>投资人姓名</th>
				<th>投资人性别</th>
				<th>联系方式(手机号)</th>
				
				<th>生日</th>
				<shiro:hasPermission name="credit:creditInvestUserInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="creditInvestUserInfo">
			<input type="hidden" name="resultInvestorId" value="${creditInvestUserInfo.id}" />
			<input type="hidden" name="resultInvestorName" value="${creditInvestUserInfo.investorName}" />
			<tr>
				
				<td>
					${creditInvestUserInfo.investorName}
				</td>
				<td>
					
					<c:if test="${empty creditInvestUserInfo.investorGender}">
						不详
					</c:if>
					<c:if test="${!empty creditInvestUserInfo.investorGender}">
						${fns:getDictLabel(creditInvestUserInfo.investorGender, 'sex', '')}
					</c:if>
				</td>
				<td>
					${creditInvestUserInfo.investorContactInfo}
				</td>
				
				<td>
					<c:if test="${empty creditInvestUserInfo.investorBirthday}">
						不详
					</c:if>
					<c:if test="${!empty creditInvestUserInfo.investorBirthday}">
						<fmt:formatDate value="${creditInvestUserInfo.investorBirthday}" pattern="yyyy-MM-dd"/>
					</c:if>
				</td>
				<shiro:hasPermission name="credit:creditInvestUserInfo:edit"><td>
    				<a href="${ctx}/credit/creditInvestUserInfo/form?id=${creditInvestUserInfo.id}">修改</a>
					<a href="${ctx}/credit/creditInvestUserInfo/delete?id=${creditInvestUserInfo.id}" onclick="return confirmx('确认要删除该债权投资用户信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>