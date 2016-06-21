<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期项目执行快照管理</title>
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
	
		function formatResult(repo) {
			if (repo.loading)
				return repo.text;
			var markup = "<div class='row' onclick='b'>" + "<div class='span1'>"
					+ repo.text + "</div>" + "<div class='span1'>" + repo.val
					+ "</div>" + "</div>";
			return markup;
		}
		function formatSelection(repo) {
			if(repo.val=='产品名称'){
				$.jBox.tip("第一行为标题，不能选择");
				return false;
			}
			return repo.val;
		}
		
		function initSelect2(){
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
		}
		
		
		
		
		
		

	
	
		$(document).ready(function() {
			
			window.setTimeout("initSelect2()", "50");
			
			
			
			
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
	

</head>
<body>
	
	
	
		<ul class="nav nav-tabs">
		<li><a href="${ctx}/current/currentProjectExecuteSnapshot/">活期项目执行快照列表</a></li>
		<li class="active"><a href="${ctx}/current/currentProjectExecuteSnapshot/form?id=${currentProjectExecuteSnapshot.id}">活期项目执行快照<shiro:hasPermission name="current:currentProjectExecuteSnapshot:edit">${not empty currentProjectExecuteSnapshot.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="current:currentProjectExecuteSnapshot:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
		
		<jsp:include page="./common/projectInfoMenu.jsp"/>
	
	 
	 
	<form:form id="inputForm" modelAttribute="currentProjectExecuteSnapshot" action="${ctx}/current/currentProjectExecuteSnapshot/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">项目：</label>
			<div class="controls">
				<form:input path="projectId" disabled="${empty id ? 'false' : 'true'}" class="input-xlarge select2-container bigdrop required" /><span id="span_projectId_error" style="color:red;"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已融资金额：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatNumber value="${currentProjectExecuteSnapshot.hasFinancedMoney}" pattern="#0.##" />" name="hasFinancedMoney" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余本金：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatNumber value="${currentProjectExecuteSnapshot.realPrincipal}" pattern="#0.##" />"  name="realPrincipal" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已产生利息：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatNumber value="${currentProjectExecuteSnapshot.hasRepaidMoney}" pattern="#0.####" />"  name="hasRepaidMoney" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已提取利息：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatNumber value="${currentProjectExecuteSnapshot.hasRedeemInterest}" pattern="#0.##" />"  name="hasRedeemInterest" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		
		<c:if test="${!empty modelMenus.pageType}">
			<script type="text/javascript">
			$(document).ready(function() {
				$.ajax({
					type : 'post',
					url : '${ctx}/current/currentProjectExecuteSnapshot/borrowerAccountInfoByProjectId',
					data : {
						projectId : '${currentProjectExecuteSnapshot.projectId}'
					},
					dataType : 'json',
					success : function(data) {
						$("#availableAmount").text(data.availableAmount);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						$("#availableAmount").text("查询失败");
					}
				});
			});
			</script>
			<style type="text/css">
				.redStyle{
					color:red;
					font-size: 13px;
					font-weight: 600;
				}
			</style>
			<div class="control-group">
				<label class="control-label">可提取利息：</label>
				<div class="controls">
					<label class="redStyle"><fmt:formatNumber value="${currentProjectExecuteSnapshot.hasRepaidMoney-currentProjectExecuteSnapshot.hasRedeemInterest}" pattern="#0.##" /></label>
					
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">融资人账户可用余额：</label>
				<div class="controls">
					<label class="redStyle" id="availableAmount">查询中……</label>
				</div>
			</div>
		</c:if>
		
		<div class="form-actions">
			<shiro:hasPermission name="current:currentProjectExecuteSnapshot:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>