<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户余额警戒管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	

		$(document).ready(function() {
			$(function(){
		        $("#mobile").keyup(function(){
		            var test = $(this).val();
		            if(test.trim().length<10){
		               if(test.trim().length>10){
		                    $(this).val(test+',');
		                }else{
		                    $(this).val(test);
		                } 
		            }else{
		                var arr = test.split(',');
		                var txtArr='';
		                for(var i=0;i<arr.length;i++){
		                    if(arr[i].length>10){
		                       txtArr+=arr[i]+','; 
		                    } else{
		                       txtArr+=arr[i];
		                    } 
		                }
		                $(this).val(txtArr);
		            }
		             
		        });
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerBalanceAlert/">账户余额警戒列表</a></li>
		<li><shiro:hasPermission name="customer:customerBalanceAlert:selectPlatformUserNo"><a href="${ctx}/customer/customerBalanceAlert/selectPlatformUserNo">查询易宝平台编号</a></shiro:hasPermission></li> 
		<li class="active"><a href="${ctx}/customer/customerBalanceAlert/form?id=${customerBalanceAlert.id}">账户余额警戒<shiro:hasPermission name="customer:customerBalanceAlert:edit">${not empty customerBalanceAlert.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerBalanceAlert:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerBalanceAlert" action="${ctx}/customer/customerBalanceAlert/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">平台编号：</label>
			<div class="controls">
				<form:input path="platformUserNo" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="50" class="input-xlarge required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="3" maxlength="70" class="input-xxlarge required"/>
				<span class="help-inline">支持变量"#customerName#,#balance#" 消息示例：#customerName#当前账户余额为:#balance#元，请尽快充值！</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
			<form:textarea path="mobile" htmlEscape="false" id="mobile" rows="3"    maxlength="200" class="input-xxlarge required"/>
			<span class="help-inline">可添加多个手机号(以' , '分隔)</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警戒额度：</label>
			<div class="controls">
				<input id="amount"  name="amount" htmlEscape="false" type="text"   maxlength="9" value="${customerBalanceAlert.amount}"  class="input-xlarge  number required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerBalanceAlert.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerBalanceAlert:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>