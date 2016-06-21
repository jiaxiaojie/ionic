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
				var trHTML = '<tr><td nowrap><input type="hidden" name="dataQueryFormList[' + thisTrIndex + '].queryId" value="${dataQuery.id}"/>'
								+ '<input type="text" name="dataQueryFormList[' + thisTrIndex + '].label" maxlength="200" class="required"/></td>'
								+ '<td><input type="text" name="dataQueryFormList[' + thisTrIndex + '].parameter" maxlength="200" class="required"/></td>'
								+ '<td><input type="text" name="dataQueryFormList[' + thisTrIndex + '].expression" maxlength="200" class="required"/></td>'
								+ '<td><input type="checkbox" name="dataQueryFormList[' + thisTrIndex + '].nullable" value="1" checked/></td>'
								+ '<td><select selectType="select2" name="dataQueryFormList[' + thisTrIndex + '].showType" class="required">' + dictOptions + '</select></td>'
								+ '<td><input type="text" name="dataQueryFormList[' + thisTrIndex + '].dateFormat" maxlength="200"/></td>'
								+ '<td><input type="text" name="dataQueryFormList[' + thisTrIndex + '].dictType" maxlength="200"/></td>'
								+ '<td><input type="text" name="dataQueryFormList[' + thisTrIndex + '].sort" maxlength="200" class="required digits"/></td>'
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
		<li class="active"><a href="${ctx}/operation/dataQuery/configQueryForm?queryId=${dataQuery.id}">配置查询表单</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="dataQuery" action="${ctx}/operation/dataQuery/saveDataQueryForm" method="post" class="form-horizontal">
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
			<legend>数据查询表单配置</legend>
			<div class="control-group">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead><tr>
						<th>标签</th>
						<th>变量名</th>
						<th>表达式</th>
						<th>是否可空</th>
						<th>表单显示类型</th>
						<th>日期格式化</th>
						<th>数据字典</th>
						<th>排序</th>
						<th>操作</th>
					</tr></thead>
					<tbody>
					<c:forEach items="${dataQuery.dataQueryFormList}" var="dataQueryForm" varStatus="vs">
						<tr>
							<td nowrap>
								<input type="hidden" name="dataQueryFormList[${vs.index}].queryId" value="${dataQuery.id}"/>
								<input type="text" name="dataQueryFormList[${vs.index}].label" value="${dataQueryForm.label}" maxlength="200" class="required"/>
							</td>
							<td>
								<input type="text" name="dataQueryFormList[${vs.index}].parameter" value="${dataQueryForm.parameter}" maxlength="200" class="required"/>
							</td>
							<td>
								<input type="text" name="dataQueryFormList[${vs.index}].expression" value="${dataQueryForm.expression}" maxlength="200" class="required"/>
							</td>
							<td>
								<input type="checkbox" name="dataQueryFormList[${vs.index}].nullable" value="1" ${dataQueryForm.nullable eq '1' ? 'checked' : ''}/>
							</td>
							<td>
								<select name="dataQueryFormList[${vs.index}].showType" class="required">
									<c:forEach items="${config.showTypeList}" var="dict">
										<option value="${dict.value}" ${dict.value==dataQueryForm.showType?'selected':''} title="${dict.description}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input type="text" name="dataQueryFormList[${vs.index}].dateFormat" value="${dataQueryForm.dateFormat}" maxlength="200"/>
							</td>
							<td>
								<input type="text" name="dataQueryFormList[${vs.index}].dictType" value="${dataQueryForm.dictType}" maxlength="200"/>
							</td>
							<td>
								<input type="text" name="dataQueryFormList[${vs.index}].sort" value="${dataQueryForm.sort}" maxlength="200" class="required digits"/>
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
			<input id="btnAdd" class="btn btn-danger" type="button" value="添加表单"/>&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
