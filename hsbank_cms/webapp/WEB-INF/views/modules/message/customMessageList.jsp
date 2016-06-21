<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义消息管理</title>
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
		//该变消息是否是紧急状态 
		function changeStatus(url){
			var submit = function(v, h, f) {
				if (v == 1) {
					 $.ajax({
				            type:"post",//发送请求类型
				            url:url, //请求路径
				            success : function(data) {
				            	 window.setTimeout('$("#searchForm").submit()');
				            }
					 })   
				} else {
					jBox.tip("取消", 'info');
				}
				return true;
			};
			$.jBox.confirm("确认要修改为非紧急消息吗？ ", "操作提示",
					submit, {
						buttons : {
							'确定' : 1,
							'取消' : -1
						}
					});
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/message/customMessage/list">自定义消息列表</a></li>
		<%-- <shiro:hasPermission name="message:customMessage:edit"><li><a href="${ctx}/message/customMessage/form">自定义消息添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="customMessage" action="${ctx}/message/customMessage/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>消息类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否紧急：</label>
				<form:select path="isUrgent" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>目标类型：</label>
				<form:select path="targetType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('photo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('custom_message_stauts')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable"  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<!-- <th>内容</th>
				<th>接收用户类型</th> -->
				<th>消息类型</th>
			<!-- 	<th>标签</th> -->
				<th>是否紧急</th>
				<th>目标类型</th>
				<th>目标参数</th>
			 <!--    <th>是否可点击</th> -->
				<th>预计发送时间</th>
				<th>创建时间</th>
			   <!--  <th>审批时间</th> -->
				 <th>状态</th> 
				
				<shiro:hasPermission name="message:customMessage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customMessage">
			<tr>
				<td><a href="${ctx}/message/customMessage/form?id=${customMessage.id}">
					${customMessage.title}
				</a></td>
				<%-- <td>
					${customMessage.content}
				</td>
				<td>
					${fns:getDictLabel(customMessage.receiverType, 'custom_message_receiver_type', '')}
				</td> --%>
				<td>
					${fns:getDictLabel(customMessage.type, 'message_type', '')}
				</td>
				<%-- <td>
					${customMessage.label}
				</td> --%>
				<td>
				${fns:getDictLabel(customMessage.isUrgent, 'yes_no', '')}
				</td>
				<td>
				${fns:getDictLabel(customMessage.targetType, 'photo_type', '')}
				</td>
				<td>
				${customMessage.target}
				</td>
				<%-- <td>
				${fns:getDictLabel(customMessage.isClick, 'yes_no', '')}
				</td> --%>
				<td>
					<fmt:formatDate value="${customMessage.sendDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${customMessage.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%-- <c:if test="${customMessage.reviewDt !=null}">
			    <td>
					<fmt:formatDate value="${customMessage.reviewDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> 
				</c:if>
				<c:if test="${empty customMessage.reviewDt}">
			    <td>
					空
				</td> 
				</c:if> --%>
				<td>
					${fns:getDictLabel(customMessage.status, 'custom_message_stauts', '')}
				</td>
					
				<shiro:hasPermission name="message:customMessage:edit"><td>
				<c:if test="${customMessage.status=='-1' }">
    				<a href="${ctx}/message/customMessage/updateform?id=${customMessage.id}">修改</a>
    			</c:if>
    				<a href="${ctx}/message/customMessage/form?id=${customMessage.id}">查看</a>
    					<c:if test="${customMessage.isUrgent=='1' &&  customMessage.status=='2'}">
				<a href="#"  onclick="changeStatus('${ctx}/message/customMessage/changeStatus?id=${customMessage.id}');">
				<font color="red">修改为非紧急消息</font>
					</a>
					</c:if>
				</td></shiro:hasPermission> 
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>