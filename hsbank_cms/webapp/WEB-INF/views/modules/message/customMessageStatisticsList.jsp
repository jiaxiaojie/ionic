<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息发送统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/message/messageInstance/messageStatistics">消息发送统计</a></li>
	</ul>
	
	<form:form id="searchForm" modelAttribute="messageInstance"  action="${ctx}/message/messageInstance/messageStatistics" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
		<li class="clearfix"></li>
		    <li><label>用户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			 <li><label>预计发送时间段：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${messageInstance.beginOpDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> - 
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${messageInstance.endOpDt}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li> 
			<li><label>消息类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit"  value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<div style="display: block;" id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><div>总共查询到:${messageNum}条记录</div></div>

</body>
</html>