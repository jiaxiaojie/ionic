<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期产品公告管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	function queryEntiyById(myurl,id){
		
		var ey = null;
			$.ajax({
			   type: "POST",
			   url:myurl ,
			   data: "id="+id,
			   async: false,
			   success: function(entity){
				  ey = entity;
			   }
			});
			
		return ey;
	}
	
	function formatStr(str){
		var s = str;//要展示的字符串
		var length = 7;
		if(str.length>length){
		  s=str.substring(0,length)+"...";
		}
		return s;
	}
	
		function formatResult(repo) {
			if (repo.loading)
				return repo.text;
			var markup = "<div class='row' onclick='b'>" + "<div class='span1'>"
					+ formatStr(repo.text) + "</div>" + "<div class='span1'>" + formatStr(repo.val)
					+ "</div>" + "</div>";
			return markup;
		}
		function formatSelection(repo) {
			
			if(repo.val=='产品编号'){
				$.jBox.tip("第一行为标题，不能选择");
				return false;
			}
			return repo.val;
		}
	
		$(document).ready(function() {
			
			$("#projectId").select2({
				placeholder : "请输入",
				minimumInputLength : 2,
				initSelection : function(element, callback) { // 初始化时设置默认值
					 callback({id:$('#projectId').val(),text:'',val:queryEntiyById("${ctx}/current/currentProjectInfo/queryById",$('#projectId').val()).name});
				},
				formatSelection : formatSelection, // 选择结果中的显示
				formatResult : formatResult, // 搜索列表中的显示
				dropdownCssClass : "bigdrop",
				ajax : {
					url : "${ctx}/current/currentProjectInfo/query", // 异步请求地址
					dataType : "json", // 数据类型
					 type: "POST",
					data : function(term, page) { // 请求参数（GET）
						term= term.replace(/^\s+|\s+$/g,"");
						if(term.length==0){
							$.jBox.tip("查询条件不能全部为空格");
							return false;
						}
						return {
							queryParas : term
						};
					},
					results : function(data, page) {
						var aResults = [];
						aResults.push({
							id : "产品id",
							text : "项目名",
							val : "产品编号"
						});

						$.each(data, function(index, item) {
							aResults.push({
								id : item.id,
								text : item.name,
								val : item.code
							});
						});
						return {
							results : aResults
						};

					} // 构造返回结果
				}
			});
			
			
			
			
			
			
			
			//发布日期限制
			$("#publishDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:dateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")});
			});
			
			
			
			
			
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
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
		});
	</script>
	<style type="text/css">
		.span1{
			width: 100px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/current/currentProjectNotice/">活期产品公告列表</a></li>
		<li class="active"><a href="${ctx}/current/currentProjectNotice/form?id=${currentProjectNotice.id}">活期产品公告<shiro:hasPermission name="current:currentProjectNotice:edit">${not empty currentProjectNotice.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="current:currentProjectNotice:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="currentProjectNotice" action="${ctx}/current/currentProjectNotice/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">项目：</label>
			<div class="controls">
				<form:input path="projectId" disabled="${empty id ? 'false' : 'true'}" class="input-xlarge select2-container bigdrop required" /><span id="span_projectId_error" style="color:red;"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="500"  class="input-xxlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input name="publishDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${currentProjectNotice.publishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'${fns:getDate('yyyy-MM-dd HH:mm:ss')}'});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('current_project_notice_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="current:currentProjectNotice:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>