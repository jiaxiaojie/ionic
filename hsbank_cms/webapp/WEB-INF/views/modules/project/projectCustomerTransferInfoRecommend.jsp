<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>债权转让管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function setRecommend(transferId,flag){
			$.get('${ctx}/project/projectTransferInfo/recommend' ,
					{flag: flag,
					transferId:transferId
					}, 
					function(data) {
						if(data){
							top.$.jBox.tip('设置成功');
							if(flag=='0'){
								$('#btn_'+transferId).removeClass();
								$('#btn_'+transferId).addClass("btn-warning");
								$('#btn_'+transferId).html('推荐');
							}else{
								$('#btn_'+transferId).removeClass();
								$('#btn_'+transferId).addClass("btn btn-info");
								$('#btn_'+transferId).html('取消');
							}
						}else{
							top.$.jBox.tip('设置失败，请与管理员联系');
						}
						
		    			
		    });
			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerBalance/customerProjectView">转让项目推荐管理</a></li>
	</ul><br/>
		<form:form id="searchForm" modelAttribute="projectBaseInfo" action="${ctx}/project/projectBaseInfo/transferrecommend" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>转让人员：</label>
				<form:input path="transferName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
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
			<li><label>项目状态：</label>
				<form:select path="projectStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>新手项目：</label>
				<form:select path="isNewUser" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('project_is_new_user_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<form:input path="customerAccountId"  type="hidden" htmlEscape="false" maxlength="20" />
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
			</ul>
			</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目编号</th>
				<th>项目名称</th>
				<th>还款方式</th>
				<th>贷款用途</th>
				<th>本期融资金</th>
				<th>计划还款日期</th>
				<th>项目状态</th>
				<th>已融资金额</th>
				<th>已还款金额</th>
				<th>投资金额</th>
				<th>投资时间</th>
				<th>转让金额</th>
				<th>转让时间</th>
				<th>转让状态</th>
				<th>是否重点推荐</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectTransferInfo">
			<tr>
				<td>
					${p2p:abbrev(projectTransferInfo.projectCode,10)}
				</td>
				<td>
					${p2p:abbrev(projectTransferInfo.projectName,10)}
				</td>
				<td>
					${fns:getDictLabel(projectTransferInfo.repaymentMode, 'project_repayment_mode_dict', '')}
				</td>
				<td>
					${projectTransferInfo.useMethod}
				</td>
				<td>
				<fmt:formatNumber value="${projectTransferInfo.financeMoney}" pattern="#0.00"/>
				</td>
				<td>
					<fmt:formatDate value="${projectTransferInfo.plannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(projectTransferInfo.projectStatus, 'project_status_dict', '')}
				</td>
				<td>
				<fmt:formatNumber value="${projectTransferInfo.pes.endFinanceMoney}" pattern="#0.00"/>
				</td>
				<td>
				<fmt:formatNumber value="${projectTransferInfo.pes.endRepayMoney}" pattern="#0.00"/>
				</td>
				<td>
				<fmt:formatNumber value="${projectTransferInfo.pir.amount}" pattern="#0.00"/>
				</td>
				<td>
					<fmt:formatDate value="${projectTransferInfo.pir.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				<fmt:formatNumber value="${projectTransferInfo.pti.transferPrice}" pattern="#0.00"/>
				</td>
				<td>
					<fmt:formatDate value="${projectTransferInfo.pti.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(projectTransferInfo.pti.status, 'project_transfer_status_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(projectTransferInfo.pti.isRecommend, 'project_is_recommend_dict', '')}
				</td>
				<td>
					 <c:choose>
					    <c:when test="${projectTransferInfo.pti.isRecommend==1}">
					      <button class="btn btn-info" id="btn_${projectTransferInfo.pti.transferProjectId}" onclick="javascript:setRecommend('${projectTransferInfo.pti.transferProjectId}','0')">取消</button>
					    </c:when>
					    <c:otherwise>
					      <button class="btn btn-warning"  id="btn_${projectTransferInfo.pti.transferProjectId}" onclick="javascript:setRecommend('${projectTransferInfo.pti.transferProjectId}','1')">推荐</button>
					    </c:otherwise>   
					  </c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>