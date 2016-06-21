<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>债权台帐管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".creditBaseInfoForm").bind('click', function() {
				openPanel($(this).attr("href"),'债权详细信息');
				return false;
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function openPanel(url,title){
			top.$.jBox.open("iframe:"+url, title, 1200, $(top.document).height()-180, {
		        buttons:{"确定":"ok","关闭":true}, submit:function(v, h, f){
		            
		        }, loaded:function(h){
		            $(".jbox-content", top.document).css("overflow-y","hidden");
		        }
		    });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/credit/creditMachineAccount/">债权台帐列表</a></li>
		<shiro:hasPermission name="credit:creditMachineAccount:edit"><li><a href="${ctx}/credit/creditMachineAccount/form">债权台帐添加</a></li></shiro:hasPermission>
	</ul>
	
	<jsp:include page="./common/machineActionInfoMenu.jsp"/>
	
	<form:form id="searchForm" modelAttribute="creditMachineAccount" action="${ctx}/credit/creditMachineAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		
			<li><label>债权：</label>
				<sys:selectPanel idName="resultCreditId" textName="resultCreditName" idField="id" callbackOnSelected="refreshForm2()" url="${ctx}/credit/creditBaseInfo/list" path="creditId" title="选择所属债权" ></sys:selectPanel>
				<form:input path="creditId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>合同编号：</label>
				<form:input path="contractNo" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li><label>利率：</label>
				<form:input path="interestRate" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>付款方式：</label>
				<form:select path="paymentMethod" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('payment_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>债权名称</th>
				<th>合同编号</th>
				<th>投资金额</th>
				<th>利率</th>
				<th>计息方式</th>
				<th>起息日</th>
				<th>投资期限（月）</th>
				<th>到期日</th>
				<th>付款方式</th>
				<th>创建时间</th>
				<shiro:hasPermission name="credit:creditMachineAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="creditMachineAccount">
			<tr>
			
			<td>
					
					
					<a class="creditBaseInfoForm" href="${ctx}/credit/creditBaseInfo/form?id=${creditMachineAccount.creditId}">
					${p2p:abbrev(creditMachineAccount.creditBaseInfo.creditName,abbrevLength)}
				</a>
				</td>
				<td><a href="${ctx}/credit/creditMachineAccount/form?id=${creditMachineAccount.id}">
					${p2p:abbrev(creditMachineAccount.contractNo,abbrevLength)}
				</a></td>
				<td>
					
					<fmt:formatNumber value="${creditMachineAccount.investMoney}" pattern="#0.####" />
				</td>
				<td>
					<fmt:formatNumber value="${creditMachineAccount.interestRate}" pattern="#0.##" />
					
				</td>
				<td>
					${fns:getDictLabel(creditMachineAccount.interestCalculation, 'interest_calculation', '')}
				</td>
				<td>
					<fmt:formatDate value="${creditMachineAccount.valueDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${creditMachineAccount.investmentHorizon}
				</td>
				<td>
					<fmt:formatDate value="${creditMachineAccount.expiringDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(creditMachineAccount.paymentMethod, 'payment_method', '')}
				</td>
				<td><fmt:formatDate value="${creditMachineAccount.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="credit:creditMachineAccount:edit"><td>
    				<a href="${ctx}/credit/creditMachineAccount/form?id=${creditMachineAccount.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>