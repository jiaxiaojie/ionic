<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>债权基本信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function setRequired(id,trueOrFalse){
            $("#"+id).rules("add", { required: trueOrFalse});
		}
		
		function setAllRequired(nullable){
			var trueOrFalse = nullable;
			setRequired("creditProjectType",trueOrFalse);
			setRequired("creditOriginalMoney",trueOrFalse);
			setRequired("creditFinancingMoney",trueOrFalse);
			setRequired("creditInterest",trueOrFalse);
			setRequired("financierName",trueOrFalse);
			setRequired("realBorrowerName",trueOrFalse);
			setRequired("receivablesAnalogueName",trueOrFalse);
			setRequired("isAssigned",trueOrFalse);
			setRequired("creditStatus",trueOrFalse);
			
			setRequired("creditRealBeginDate",trueOrFalse);
			setRequired("creditRealEndDate",trueOrFalse);
			setRequired("creditFinancingBeginDate",trueOrFalse);
			setRequired("creditFinancingEndDate",trueOrFalse);
			setRequired("raiseBeginDate",trueOrFalse);
			setRequired("raiseEndDate",trueOrFalse);
		}
	
		$(document).ready(function() {
			
			
			$("#btnSubmit").bind('click', function() {
				$("#isDraft").attr("value","0");
				setAllRequired(true);
			});
			
			$("#btnSubmitToDraft").bind('click', function() {
				$("#isDraft").attr("value","1");
				setAllRequired(false);
			});

			
			var cid = $("#id").val();
			var isDraft = $("#isDraft").val();
			
			if(cid != null && "" != cid && isDraft != 1){
				
				$(":input").not($("#id")).not($("#creditStatus")).not($("#btnSubmit")).not($("#btnCancel")).attr("disabled",true);
			}

			
			//$("#name").focus();
			$("#inputForm").validate({
				
				rules: {
					creditInterest: {
						required:true,
			        	valiDecimals2: true
			        	
			        },
			        creditName:{
			        	required:true,
						remote: {
							type:"post",
							url:"${ctx}/credit/creditBaseInfo/checkName",
		                    data: {
		                    	 id:$('#id').val(),
		                    	 name: function() {return $("#creditName").val();}
		                    }
						},
						
					},
					creditProjectType:{
						required:true
					},
					creditOriginalMoney:{
						required:true,
						valiDecimals2: true
					},
					creditFinancingMoney:{
						required:true,
						valiDecimals2: true
					},
					financierName:{
						required:true
					},
					realBorrowerName:{
						required:true
					},
					receivablesAnalogueName:{
						required:true
					},
					isAssigned:{
						required:true
					},
					creditStatus:{
						required:true
					}
					
					
					
				},
				messages: {
					creditName: {remote: "债权名称已存在"},
				},
				
				submitHandler: function(form){
					var result = true; 
					if($("#creditStatusDefaultVal").val() != $("#creditStatus").val()){
						result = confirm('确认要手动修改债权状态吗？');
					}
					
					if(result){
						loading('正在提交，请稍等...');
						form.submit();
					}
					
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
			
			
			if(isDraft != 1){
				
				setAllRequired(true);
			}else{
				
				setAllRequired(false);
			}
			
		});
		
		jQuery.validator.addMethod("valiDecimals2", function(value, element) {         
		    return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value);         
		}, "小数位不能超过两位");  
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/credit/creditBaseInfo/">债权基本信息列表</a></li>
		<li class="active"><a href="${ctx}/credit/creditBaseInfo/form?id=${creditBaseInfo.id}">债权基本信息<shiro:hasPermission name="credit:creditBaseInfo:edit">${not empty creditBaseInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:creditBaseInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="creditBaseInfo" action="${ctx}/credit/creditBaseInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="isDraft" id="isDraft" value="${ empty creditBaseInfo.isDraft?0:creditBaseInfo.isDraft}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">债权名称：</label>
			<div class="controls">
				<form:input path="creditName" htmlEscape="false" maxlength="30" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权项目类型：</label>
			<div class="controls">
				<form:select path="creditProjectType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('credit_project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权原始金额：</label>
			<div class="controls">
				
				
				<input id="creditOriginalMoney" name="creditOriginalMoney" class="input-xlarge required number" value="<fmt:formatNumber value="${ creditBaseInfo.creditOriginalMoney}" pattern="0.##" />" maxlength="10" type="text">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权融资金额：</label>
			<div class="controls">
				
				
				<input id="creditFinancingMoney" name="creditFinancingMoney" class="input-xlarge required number" value="<fmt:formatNumber value="${ creditBaseInfo.creditFinancingMoney}" pattern="0.##" />" maxlength="10" type="text">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		<!--  -->
		<div class="control-group">
			<label class="control-label">债权实际起始时间：</label>
			<div class="controls">
					
				<input name="creditRealBeginDate" id="creditRealBeginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${creditBaseInfo.creditRealBeginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'creditRealEndDate\')}',isShowClear:true});"/>
					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权实际结束时间：</label>
			<div class="controls">
				<input name="creditRealEndDate" isShowClear='true' id="creditRealEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate  value="${creditBaseInfo.creditRealEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'creditRealBeginDate\')}',isShowClear:true});"/>
					
					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<!--  -->	
		
		<div class="control-group">
			<label class="control-label">债权融资起始时间：</label>
			<div class="controls">
					
				<input name="creditFinancingBeginDate" id="creditFinancingBeginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${creditBaseInfo.creditFinancingBeginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'creditFinancingEndDate\')}',isShowClear:true});"/>
					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权融资结束时间：</label>
			<div class="controls">
				<input name="creditFinancingEndDate" isShowClear='true' id="creditFinancingEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate  value="${creditBaseInfo.creditFinancingEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'creditFinancingBeginDate\')}',isShowClear:true});"/>
					
					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		
		
		<!--  -->	
		
		<div class="control-group">
			<label class="control-label">债权利率（%）：</label>
			<div class="controls">
				
				
				<input id="creditInterest" name="creditInterest" min="0" max="100" class="input-xlarge  number" value="<fmt:formatNumber value="${ creditBaseInfo.creditInterest}" pattern="0.##" />" type="text">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资人名称：</label>
			<div class="controls">
				<form:input path="financierName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际借款人名称：</label>
			<div class="controls">
				<form:input path="realBorrowerName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应收账款对手方名称：</label>
			<div class="controls">
				<form:input path="receivablesAnalogueName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否已做转让：</label>
			<div class="controls">
				<form:select path="isAssigned" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<!-- 
		
		 - 
				
		
		
		 -->
		<div class="control-group">
			<label class="control-label">募集起始时间：</label>
			<div class="controls">
					
				<input name="raiseBeginDate" id="raiseBeginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${creditBaseInfo.raiseBeginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'raiseEndDate\')}',isShowClear:true});"/>
					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">募集结束时间：</label>
			<div class="controls">
				<input name="raiseEndDate" isShowClear='true' id="raiseEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate  value="${creditBaseInfo.raiseEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'raiseBeginDate\')}',isShowClear:true});"/>
					
					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<!-- 
		
		<div class="control-group">
			<label class="control-label">募集中金额：</label>
			<div class="controls">
				<form:input path="raisingMoney" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">待募集金额：</label>
			<div class="controls">
				<form:input path="toRaiseMoney" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		 
		
		 -->
		 <div class="control-group">
			<label class="control-label">债权状态：</label>
			<div class="controls">
				<input  type="hidden" id="creditStatusDefaultVal" disabled="disabled" value="${creditBaseInfo.creditStatus }"/> 
				<form:select path="creditStatus" class="input-xlarge " disabled="${empty creditBaseInfo.id || creditBaseInfo.isDraft==1?true:false}">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('credit_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group" style="border-bottom: 1px dotted #FF0033;">
			<label class="control-label">相关文件原件：</label>
			<div class="controls">
				<form:hidden id="relevantDocumentOriginal" path="relevantDocumentOriginal" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="relevantDocumentOriginal" first="true" type="files" uploadPath="/credit/creditBaseInfo" selectMultiple="true"/>
			</div>
		</div>
		
		
		
		
		
		<div class="control-group">
			<label class="control-label">公开文件：</label>
			<div class="controls">
				<form:hidden id="publicDocument" path="publicDocument" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="publicDocument" type="images" first="false" uploadPath="/credit/creditBaseInfo" selectMultiple="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">营业执照：</label>
			<div class="controls">
				<form:hidden id="businessLicense" path="businessLicense" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="businessLicense" type="images" first="false" uploadPath="/credit/creditBaseInfo" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贸易合同：</label>
			<div class="controls">
				<form:hidden id="tradingContract" path="tradingContract" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="tradingContract" type="images" first="false" uploadPath="/credit/creditBaseInfo" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款合同：</label>
			<div class="controls">
				<form:hidden id="loanContract" path="loanContract" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="loanContract" type="images" first="false" uploadPath="/credit/creditBaseInfo" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">央行登记信息：</label>
			<div class="controls">
				<form:hidden id="centralBankRegistrationInformation" path="centralBankRegistrationInformation" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="centralBankRegistrationInformation" type="images" first="false" uploadPath="/credit/creditBaseInfo" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实地考察照片：</label>
			<div class="controls">
				<form:hidden id="fieldTripPhotos" path="fieldTripPhotos" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="fieldTripPhotos" type="images" first="false" uploadPath="/credit/creditBaseInfo" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票：</label>
			<div class="controls">
				<form:hidden id="invoice" path="invoice" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="invoice" type="images" first="false" uploadPath="/credit/creditBaseInfo" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流签收单据：</label>
			<div class="controls">
				<form:hidden id="logisticsSignReceipts" path="logisticsSignReceipts" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="logisticsSignReceipts" type="images" first="false" uploadPath="/credit/creditBaseInfo" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:creditBaseInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;<input id="btnSubmitToDraft" class="btn btn-primary" type="submit" value="保存为草稿"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>