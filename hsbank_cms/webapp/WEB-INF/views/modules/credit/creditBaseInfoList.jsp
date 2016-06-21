<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>债权基本信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".raisedMoneyBelowLineA").bind('click', function() {
				openPanel($(this).attr("href"),'线下已经募集金额详情列表');
				return false;
			});
			$(".raisedMoneyOnLineA").bind('click', function() {
				openPanel($(this).attr("href"),'线上已经募集金额详情列表');
				return false;
			});
			$(".addCreditMachineAccount").bind('click', function() {
				openPanel($(this).attr("href"),'添加债权台帐');
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
		<li class="active"><a href="${ctx}/credit/creditBaseInfo/">债权基本信息列表</a></li>
		<shiro:hasPermission name="credit:creditBaseInfo:edit"><li><a href="${ctx}/credit/creditBaseInfo/form">债权基本信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="creditBaseInfo" action="${ctx}/credit/creditBaseInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>项目类型：</label>
				<form:select path="creditProjectType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('credit_project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否已转让：</label>
				<form:select path="isAssigned" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li><label>债权名称：</label>
				<form:input path="creditName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="clearfix"></li>
			<li><label>债权状态：</label>
				<form:select path="creditStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('credit_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否为草稿：</label>
				<form:select path="isDraft" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li><label>募集期：</label>
			
				<input name="raiseBeginDate" id="startdt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${creditBaseInfo.raiseBeginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddt\')}',isShowClear:true});"/> - 
				<input name="raiseEndDate" isShowClear='true' id="enddt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate  value="${creditBaseInfo.raiseEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdt\')}',isShowClear:true});"/>
				
				
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
				<th>是草稿</th>
				<th>项目类型</th>
				<th>融资人名称</th>
				<th>是否已做转让</th>
				<th>债权状态</th>
				<th>债权融资金额</th>
				<th>待募集金额</th>
				
				<th>线上已募金额</th>
				<th>线下已募金额</th>
				<th>募集中金额</th>
				<th>募集起始时间</th>
				<th>募集结束时间</th>
				
				<th>创建时间</th>
				<shiro:hasPermission name="credit:creditBaseInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="creditBaseInfo">
			<input type="hidden" name="resultCreditId" value="${creditBaseInfo.id}" />
			<input type="hidden" name="resultCreditName" value="${creditBaseInfo.creditName}" />
			<tr>
				<td><a href="${ctx}/credit/creditBaseInfo/form?id=${creditBaseInfo.id}">
					${p2p:abbrev(creditBaseInfo.creditName,abbrevLength)}
				</a></td>
				<td>
					${fns:getDictLabel(empty creditBaseInfo.isDraft?0:creditBaseInfo.isDraft, 'yes_no', '')}
				</td>
				<td>
					
					${fns:getDictLabel(creditBaseInfo.creditProjectType, 'credit_project_type', '')}
				</td>
				<td>
					
					${p2p:abbrev(creditBaseInfo.financierName,abbrevLength)}
				</td>
				<td>
					${fns:getDictLabel(creditBaseInfo.isAssigned, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(creditBaseInfo.creditStatus, 'credit_status', '')}
				</td>
				<td>
					
					<fmt:formatNumber value="${creditBaseInfo.creditFinancingMoney}" pattern="#0.####" />
				</td>
				<td>
					
					<fmt:formatNumber value="${creditBaseInfo.toRaiseMoney}" pattern="#0.####" />
				</td>
				
				<td>
					<a class="raisedMoneyOnLineA" href="${ctx}/project/projectBaseInfo/queryAlreadyLoanlist?creditId=${creditBaseInfo.id}">
					<fmt:formatNumber value="${creditBaseInfo.raisedMoneyOnLine}" pattern="#0.####" />
					</a>
				</td>
				<td>
					<a class="raisedMoneyBelowLineA" href="${ctx}/credit/creditMachineAccount/list?creditId=${creditBaseInfo.id}">
					<fmt:formatNumber value="${creditBaseInfo.raisedMoneyBelowLine}" pattern="#0.####" />
					</a>
				</td>
				
				<td>
					
					<fmt:formatNumber value="${creditBaseInfo.raisingMoney}" pattern="#0.####" />
				</td>
				
				<td><fmt:formatDate value="${creditBaseInfo.raiseBeginDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${creditBaseInfo.raiseEndDate}" pattern="yyyy-MM-dd"/></td>
				
				<td><fmt:formatDate value="${creditBaseInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="credit:creditBaseInfo:edit"><td>
    				<a href="${ctx}/credit/creditBaseInfo/form?id=${creditBaseInfo.id}">修改</a>
					<a href="${ctx}/credit/creditMachineAccount/list?pageType=machineAccountInfomenus&creditId=${creditBaseInfo.id}">台帐信息</a>
					<a class="addCreditMachineAccount" href="${ctx}/credit/creditMachineAccount/form?creditId=${creditBaseInfo.id}">添加台帐</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>