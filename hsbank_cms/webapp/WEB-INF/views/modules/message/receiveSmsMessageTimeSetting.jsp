<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户接收短信时间段管理</title>
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
			//alert(ey);
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
		if(repo.val=='会员名称'){
			$.jBox.tip("第一行为标题，不能选择");
			return false;
		}
		return repo.val;
	}
	
	function initSelect(){
		$("#accountId").select2({
			placeholder : "请输入",
			minimumInputLength : 2,
			initSelection : function(element, callback) { // 初始化时设置默认值
				 callback({id:$('#accountId').val(),text:'',val:queryEntiyById("${ctx}/customer/customerAccount/queryById",$('#accountId').val()).accountName});
			},
			formatSelection : formatSelection, // 选择结果中的显示
			formatResult : formatResult, // 搜索列表中的显示
			dropdownCssClass : "bigdrop",
			ajax : {
				url : "${ctx}/customer/customerAccount/query", // 异步请求地址
				dataType : "json", // 数据类型
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
						id : "账号编号",
						text : "登录名",
						val : "会员名称"
					});

					$.each(data, function(index, item) {
						aResults.push({
							id : item.accountId,
							text : item.accountName,
							val : item.customerBase.customerName
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
		
		//日期相关校验
		var dateUtils = new DateUtils();
		var maxDt = function(){
			
			var result = "";
			var endDt = $("#endTime").val()
			if(endDt!=null && endDt != ""){
				result = endDt
			}
			return result;
		};
		$(function(){
			
			
			
			//开始时间限制
			$("#startTime").click(function() {
				var accountId = $("#accountId");
				if(accountId.val() != "" && accountId.attr("disabled") != "disabled"){
					WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false,maxDate:maxDt()});
				}
				
			});
			
			//结束时间限制
			$("#endTime").click(function(){
				var accountId = $("#accountId");
				if(accountId.val() != ""&& accountId.attr("disabled") != "disabled"){
					WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false,isShowToday:false,minDate:$("#startTime").val()});
				}
			});
			
			
			
			
		});
		
		function refreshForm(){
			refreshFormByAccountId($("#inputForm").find("#accountId").val())
		}
		function refreshFormByAccountId(accountId){
			var a = $("#menu_2").find(".active").find("a");
			var url = a.attr("href").split("?")[0].substr(1);
			
			post(url+"?pageType=${modelMenus.pageType}", "disabledForm=false&accountId="+accountId);
		}
		
	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/message/receiveSmsMessageTime/">用户接收短信时间段列表</a></li>
		<li class="active"><a href="${ctx}/message/receiveSmsMessageTime/form?id=${receiveSmsMessageTime.id}">用户接收短信时间段<shiro:hasPermission name="message:receiveSmsMessageTime:edit">${not empty receiveSmsMessageTime.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="message:receiveSmsMessageTime:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<jsp:include page="../customer/common/customerMessageMenu.jsp"/>
	<form:form id="inputForm" modelAttribute="receiveSmsMessageTime" action="${ctx}/message/receiveSmsMessageTime/save?disabledForm=false" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">账户：</label>
			<div class="controls">
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
				<sys:selectPanel idName="resultAccountId" textName="resultAccountName" callbackOnSelected="refreshForm()" url="${ctx}/customer/customerAccountInfo/customerAccountInfoList" path="accountId" title="选择账户" ></sys:selectPanel>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				
				<input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="${receiveSmsMessageTime.startTime}"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				
				<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="${receiveSmsMessageTime.endTime}"
					/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="message:receiveSmsMessageTime:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>