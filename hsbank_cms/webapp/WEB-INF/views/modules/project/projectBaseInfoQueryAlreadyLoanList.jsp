<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借贷产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".projectDetailA").bind('click', function() {
				openPanel($(this).attr("href"));
				return false;
			});
			$(".projectInvestmentRecordListA").bind('click', function() {
				openPanel($(this).attr("href"));
				return false;
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openPanel(url){
			top.$.jBox.open("iframe:"+url, '项目详情', 1200, $(top.document).height()-180, {
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
		<li class="active"><a href="${ctx}/project/projectBaseInfo/querylist">借贷产品列表</a></li>
		<shiro:hasPermission name="project:projectBaseInfo:edit"><li><a href="${ctx}/project/projectBaseInfo/form">借贷产品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="projectBaseInfo" action="${ctx}/project/projectBaseInfo/queryAlreadyLoanlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="creditId" htmlEscape="false" maxlength="20" class="input-medium"/>
		
		<ul class="ul-form">
			
			
			
			<li><label>项目类型：</label>
				<form:select path="projectTypeCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>还款方式：</label>
				<form:select path="repaymentMode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_repayment_mode_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label title="计划还款日期">还款日期：</label>
				<input name="beginPlannedRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectBaseInfo.beginPlannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endPlannedRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${projectBaseInfo.endPlannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			
			<li><label>新手项目：</label>
				<form:select path="isNewUser" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_is_new_user_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>推荐项目：</label>
				<form:select path="isRecommend" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_is_recommend_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>项目编号</th>
				<th>是否新手项目</th>
				<th>是否推荐项目</th>
				
				<th>项目名称</th>
				<th>本期融资金额</th>
				<th>计划还款日期</th>
				<th>项目状态</th>
				<th>项目发布日期</th>
				<th>投资人数</th>
				<th>募集的总金额</th>
				<th>项目类型</th>
				<th>还款方式</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectBaseInfo">
			<tr>
				<td>
					${p2p:abbrev(projectBaseInfo.projectCode,10)}
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.isNewUser, 'project_is_new_user_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.isRecommend, 'project_is_recommend_dict', '')}
				</td>
				
				<td>
					${p2p:abbrev(projectBaseInfo.projectName,10)}
				</td>
				
				
				<td>
					<fmt:formatNumber value="${projectBaseInfo.financeMoney}" pattern="#0.00"/>
				</td>
				<td>
					<fmt:formatDate value="${projectBaseInfo.plannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.projectStatus, 'project_status_dict', '')}
				</td>
				
				<td>
					<fmt:formatDate value="${projectBaseInfo.publishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				<a class="projectInvestmentRecordListA" href="${ctx}/project/projectInvestmentRecord/list?pageType=jdProjectInfoMenus&queryProjectId=${projectBaseInfo.projectId}">
				${projectBaseInfo.touzirenshu }
				</a>
				</td>
				<td>
						<fmt:formatNumber value="${projectBaseInfo.endFinanceMoney}" pattern="#0.##"/>
				</td>
				
				
				<td>
					${fns:getDictLabel(projectBaseInfo.projectTypeCode, 'project_type_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}
				</td>
				<td>
					<a class="projectDetailA" href="${ctx}/project/projectBaseInfo/form?pageType=jdProjectInfoMenus&id=${projectBaseInfo.projectId}">详情</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>