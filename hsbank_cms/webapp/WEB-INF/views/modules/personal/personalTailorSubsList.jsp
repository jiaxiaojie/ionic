<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>私人订制预约管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出私人订制数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/personal/personalTailorSubs/export");
						$("#searchForm").submit();
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
		<li class="active"><a href="${ctx}/personal/personalTailorSubs/">私人订制预约列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="customerLeaveMessage" action="${ctx}/personal/personalTailorSubs/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>操作时间：</label>
				<input name="beginOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${customerLeaveMessage.beginOpDt}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> -
				<input name="endOpDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${customerLeaveMessage.endOpDt}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">姓名</th>
				<th width="10%">项目名称</th>
				<th width="10%">手机号</th>
				<th width="10%">邮箱</th>
				<th width="20%">内容</th>
				<th width="10%">账户编号</th>
				<th width="10%">预约时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personalTailorSubs">
			<tr>
				<td>
					${personalTailorSubs.name}
				</td>
				<td>
				    ${personalTailorSubs.projectName}
				</td>
				<td>
					${personalTailorSubs.mobile}
				</td>
				<td>
					${personalTailorSubs.email}
				</td>
				<td>
					${personalTailorSubs.content}
				</td>
				<td>
				    ${personalTailorSubs.accountId}
				</td>
				<td>
					<fmt:formatDate value="${personalTailorSubs.opDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>