<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员账户余额对齐管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysMobileVersionPara/">版本列表</a></li>
		<shiro:hasPermission name="sys:sysMobileVersionPara:edit"><li><a href="${ctx}/sys/sysMobileVersionPara/form">版本添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysMobileVersionPara" action="${ctx}/sys/sysMobileVersionPara/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>版本：</label>
				<form:input path="version" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>移动端类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>版本</th>
				<th>apk文件URL</th>
				<th>版本大小</th>
				<th>渠道</th>
				<th>是否强制更新</th>
				<th>移动端类型</th>
				<th>当前状态</th>
				<shiro:hasPermission name="sys:sysMobileVersionPara:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysMobileVersionPara">
			<tr>
				<td><a href="${ctx}/sys/sysMobileVersionPara/form?id=${sysMobileVersionPara.id}">
					${sysMobileVersionPara.version}
				</a></td>
				<td>
					${sysMobileVersionPara.url}
				</td>
				<td>
					${sysMobileVersionPara.versionSize}
				</td>
				<td>
					${sysMobileVersionPara.channel}
				</td>
				<td>
					${fns:getDictLabel(sysMobileVersionPara.isForcedUpdate, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(sysMobileVersionPara.type, 'op_term_dict', '')}
				</td>
				<td>
					${fns:getDictLabel(sysMobileVersionPara.mark, 'mobile_version_mark', '')}
					
				</td>
				<shiro:hasPermission name="sys:sysMobileVersionPara:edit"><td>
    				<c:if test="${sysMobileVersionPara.mark==0}">
						<a></a>
						<a href="${ctx}/sys/sysMobileVersionPara/updateMark?id=${sysMobileVersionPara.id}&mark=1">${fns:getDictLabel('1', 'mobile_version_mark', '')}</a>
					</c:if>
					<c:if test="${sysMobileVersionPara.mark==1}">
						<a href="${ctx}/sys/sysMobileVersionPara/updateMark?id=${sysMobileVersionPara.id}&mark=0">${fns:getDictLabel('0', 'mobile_version_mark', '')}</a>
					</c:if>
					<a href="${ctx}/sys/sysMobileVersionPara/form?id=${sysMobileVersionPara.id}">修改</a>
					<a href="${ctx}/sys/sysMobileVersionPara/delete?id=${sysMobileVersionPara.id}" onclick="return confirmx('确认要删除该版本参数吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>