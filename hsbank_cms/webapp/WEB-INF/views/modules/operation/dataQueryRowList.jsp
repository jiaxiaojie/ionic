<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查询数据列</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
				var trHTML = '<tr><td nowrap><input type="hidden" name="dataQueryRowList[' + thisTrIndex + '].queryId" value="${dataQuery.id}"/>'
							+ '<input type="text" name="dataQueryRowList[' + thisTrIndex + '].rowName" maxlength="200" class="required"/></td>'
							+ '<td><input type="text" name="dataQueryRowList[' + thisTrIndex + '].showRowName" maxlength="200" class="required"/></td>'
							+ '<td><input type="text" name="dataQueryRowList[' + thisTrIndex + '].sort" maxlength="200" class="required digits"/></td>'
							+ '<td><input type="checkbox" name="dataQueryRowList[' + thisTrIndex + '].sortable" value="1"/></td>'
							+ '<td><input type="text" name="dataQueryRowList[' + thisTrIndex + '].dictType" maxlength="200"/></td>'
							+ '<td><input type="text" name="dataQueryRowList[' + thisTrIndex + '].dateFormat" maxlength="200"/></td>'
							+ '<td><a index="' + thisTrIndex + '" href="javascript:void(0);" onclick="delTr(this)">删除</a></td></tr>';
				$("#contentTable").append(trHTML);
			});
		});
		
		function delTr(dom) {
			$(dom).parent().parent().remove();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operation/dataQuery/">数据查询列表</a></li>
		<li class="active"><a href="${ctx}/operation/dataQuery/configRow?queryId=${dataQuery.id}">配置查询列</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="dataQuery" action="${ctx}/operation/dataQuery/saveDataQueryRow" method="post" class="form-horizontal">
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
			<legend>查询数据列配置</legend>
			<div class="control-group">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead><tr>
						<th>列名</th>
						<th>列显示名</th>
						<th>排序</th>
						<th>是否可排序</th>
						<th>数据字典</th>
						<th>日期格式化</th>
						<th>操作</th>
					</tr></thead>
					<tbody>
					<c:forEach items="${dataQuery.dataQueryRowList}" var="dataQueryRow" varStatus="vs">
						<tr>
							<td nowrap>
								<input type="hidden" name="dataQueryRowList[${vs.index}].queryId" value="${dataQuery.id}"/>
								<input type="text" name="dataQueryRowList[${vs.index}].rowName" value="${dataQueryRow.rowName}" maxlength="200" class="required"/>
							</td>
							<td>
								<input type="text" name="dataQueryRowList[${vs.index}].showRowName" value="${dataQueryRow.showRowName}" maxlength="200" class="required"/>
							</td>
							<td>
								<input type="text" name="dataQueryRowList[${vs.index}].sort" value="${dataQueryRow.sort}" maxlength="200" class="required digits"/>
							</td>
							<td>
								<input type="checkbox" name="dataQueryRowList[${vs.index}].sortable" value="1" ${dataQueryRow.sortable eq '1' ? 'checked' : ''}/>
							</td>
							<td>
								<input type="text" name="dataQueryRowList[${vs.index}].dictType" value="${dataQueryRow.dictType}" maxlength="200"/>
							</td>
							<td>
								<input type="text" name="dataQueryRowList[${vs.index}].dateFormat" value="${dataQueryRow.dateFormat}" maxlength="200"/>
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
			<input id="btnAdd" class="btn btn-danger" type="button" value="添加列"/>&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
