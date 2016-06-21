<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户意见反馈管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/feedback/userFeedbackInfo/">用户意见反馈列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="userFeedbackInfo" action="${ctx}/feedback/userFeedbackInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>渠道：</label>
				<form:select path="channelId" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>反馈时间：</label>
				<input name="beginCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userFeedbackInfo.beginCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endCreateDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userFeedbackInfo.endCreateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>渠道</th>
				<th>反馈内容</th>
				<th>反馈时间</th>
				<shiro:hasPermission name="feedback:userFeedbackInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userFeedbackInfo">
			<tr>
				<td>
					${fns:getDictLabel(userFeedbackInfo.channelId, 'op_term_dict', '')}
				</td>
				<td>
				  <c:choose>
				     <c:when test="${userFeedbackInfo.result == 0}">
				        <a href="${ctx}/feedback/userFeedbackInfo/reviewform?id=${userFeedbackInfo.id}">
							${p2p:abbrev(userFeedbackInfo.content,10)}
						</a>
				     </c:when>
				     <c:otherwise>
				        <a href="${ctx}/feedback/userFeedbackInfo/view?id=${userFeedbackInfo.id}">
							${p2p:abbrev(userFeedbackInfo.content,10)}
						</a>
				     </c:otherwise>
				   </c:choose>
				  
				</td>
				<td>
					<fmt:formatDate value="${userFeedbackInfo.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="feedback:userFeedbackInfo:edit"><td>
				   <c:choose>
				     <c:when test="${userFeedbackInfo.result == 0}">
				        <a href="${ctx}/feedback/userFeedbackInfo/reviewform?id=${userFeedbackInfo.id}">审核</a>
				     </c:when>
				     <c:otherwise>
				        <a href="${ctx}/feedback/userFeedbackInfo/view?id=${userFeedbackInfo.id}">查看</a>
				     </c:otherwise>
				   </c:choose>
    				
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>