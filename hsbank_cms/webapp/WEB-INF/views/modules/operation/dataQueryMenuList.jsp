<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据查询表单</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var dictOptions = "";
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$("input[type=checkbox]").each(function(){
						$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
								+($(this).attr("checked")?"1":"0")+"\"/>");
						$(this).attr("name", "_"+$(this).attr("name"));
					});
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			$("#btnAdd").click(function() {
				var thisTrIndex = 0;
				if($("#contentTable tr:last").find("td:last").find("a").attr("index")) {
					thisTrIndex = parseInt($("#contentTable tr:last").find("td:last").find("a").attr("index")) + 1;
				}
				var trHTML = '<tr><td nowrap><input type="hidden" name="dataQueryMenuList[' + thisTrIndex + '].queryId" value="${dataQuery.id}"/>'
								+ '<input type="text" name="dataQueryMenuList[' + thisTrIndex + '].menuId" maxlength="200" class="required"/></td>'
								+ '<td><input type="text" name="dataQueryMenuList[' + thisTrIndex + '].title" maxlength="200" class="required"/></td>'
								+ '<td><input type="checkbox" name="dataQueryMenuList[' + thisTrIndex + '].exportable" value="1" checked/></td>'
								+ '<td><a index="' + thisTrIndex + '" href="javascript:void(0);" onclick="delTr(this)">删除</a></td></tr>';
				$("#contentTable").append(trHTML);
				$("select[selectType='select2']").select2();
			});
		});
		
		function delTr(dom) {
			$(dom).parent().parent().remove();
		}
	</script>
</head>
<body>
	<c:forEach items="${config.showTypeList}" var="dict">
		<c:set var="dictOptions" value='${dictOptions}<option value="${dict.value}" title="${dict.description}">${dict.label}</option>'/>
	</c:forEach>
	<script type="text/javascript">
		dictOptions = '${dictOptions}';
	</script>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operation/dataQuery/">数据查询列表</a></li>
		<li class="active"><a href="${ctx}/operation/dataQuery/configQueryMenu?queryId=${dataQuery.id}">配置关联菜单</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="dataQuery" action="${ctx}/operation/dataQuery/saveQueryMenu" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<fieldset>
			<legend>基本信息</legend>
			<div class="control-group">
				<label class="control-label">数据查询名称:</label>
				<div class="controls">
					<form:input path="name" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
				</div>
			</div>
			<legend>关联菜单配置</legend>
			<div class="control-group">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead><tr>
						<th>菜单id</th>
						<th>标题</th>
						<th>能否导出数据</th>
						<th>操作</th>
					</tr></thead>
					<tbody>
					<c:forEach items="${dataQuery.dataQueryMenuList}" var="dataQueryMenu" varStatus="vs">
						<tr>
							<td nowrap>
								<input type="hidden" name="dataQueryMenuList[${vs.index}].queryId" value="${dataQuery.id}"/>
								<input type="text" name="dataQueryMenuList[${vs.index}].menuId" value="${dataQueryMenu.menuId}" maxlength="200" class="required"/>
							</td>
							<td>
								<input type="text" name="dataQueryMenuList[${vs.index}].title" value="${dataQueryMenu.title}" maxlength="200" class="required"/>
							</td>
							<td>
								<input type="checkbox" name="dataQueryMenuList[${vs.index}].exportable" value="1" ${dataQueryMenu.exportable eq '1' ? 'checked' : ''}/>
							</td>
							<td>
								<a index="${vs.index}" href="#" onclick="delTr(this)">删除</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</fieldset>
		<div class="form-actions">
			<input id="btnAdd" class="btn btn-danger" type="button" value="添加关联菜单"/>&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
