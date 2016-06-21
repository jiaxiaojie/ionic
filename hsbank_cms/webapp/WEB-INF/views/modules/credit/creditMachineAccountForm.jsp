<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>债权台帐管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function getDate(strDate) {
	        var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
	         function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
	        return date;
	    }
		var valueDate = '<fmt:formatDate value="${creditMachineAccount.valueDate}" pattern="yyyy-MM-dd"/>';
		var dateUtils = new DateUtils();
		function calculateExpiringDate(){
			var investmentHorizonStr = $("#investmentHorizon").val();
			var investmentHorizon = parseInt(investmentHorizonStr);
			
			
			if(investmentHorizonStr != null && investmentHorizonStr != "" && !isNaN(investmentHorizon) && valueDate != null && valueDate != "" && investmentHorizon >= 0){
				//alert(123);
				
				var mydate = getDate(valueDate);
				
				var expiringDate = dateUtils.addMonths(mydate,investmentHorizon,"yyyy-MM-dd");
				
				$("#expiringDate").val(expiringDate); 
			
			}
			else{
				$("#expiringDate").val(""); 
			}
		}
		
		jQuery.validator.addMethod("valiValueDate", function(value, element) {   
			valueDate = value;
			calculateExpiringDate();
		    return this.optional(element) || true;         
		}, ""); 
		
		var credit;
		$(document).ready(function() {
			
			refreshForm2();
			
			$("#investmentHorizon").bind('change', function() {
				calculateExpiringDate();
			});
			$("#valueDate").bind('change', function() {
				calculateExpiringDate();
			});
			

			
			//$("#name").focus();
			$("#inputForm").validate({
				ignore: "",
				rules: {
					interestRate: {
			        	valiDecimals2: true
			        	
			        },
			        investMoney:{
			        	valiDecimals2:true,
			        	valiInvestMoney:true
			        	
			        },
			        contractNo:{
						remote: {
							type:"post",
							url:"${ctx}/credit/creditMachineAccount/checkContractNo",
		                    data: {
		                    	 id:$('#id').val(),
		                    	 name: function() {return $("#contractNo").val();}
		                    }
						},
						
					},
					commissionCharge:{
						valiDecimals2:true
					},
					valueDate:{
						valiValueDate:true
					}
					
				},
				messages: {
					contractNo: {remote: "合同编号已存在"},
				},
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
		
		
		
		jQuery.validator.addMethod("valiDecimals2", function(value, element) {         
		    return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value);         
		}, "小数位不能超过两位");  
		
		jQuery.validator.addMethod("valiInvestMoney", function(value, element) {   
			
		    return this.optional(element) || !(parseInt(value)>parseInt(credit.toRaiseMoney));         
		}, "投资金额不能大于债权可投金额");  
		

		
		function refreshForm2(){
			
			var creditId = $("#creditId").val();
			if(creditId != null){
				$.ajax({
					   type: "POST",
					   url: "${ctx}/credit/creditBaseInfo/getById",
					   data: "id="+creditId,
					   success: function(data){
						 if(data.id != null){
							 $("#ketou").text("剩余可投金额："+data.toRaiseMoney+"元");
						     $("#investMoney").attr("max",parseFloat(data.toRaiseMoney)+($("#investMoney").val()==null||$("#investMoney").val()==""?0:parseFloat($("#investMoney").val())));
						     
						     //$("#creditDurationInfo").text("债权结束时间为:"+new DateUtils().formatDate(new Date(data.raiseEndDate), "yyyy年MM月dd日"));
						     credit = data;
						 }
						 
					   }
					});
			}
			
			
			
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/credit/creditMachineAccount/">债权台帐列表</a></li>
		<li class="active"><a href="${ctx}/credit/creditMachineAccount/form?id=${creditMachineAccount.id}">债权台帐<shiro:hasPermission name="credit:creditMachineAccount:edit">${not empty creditMachineAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:creditMachineAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="creditMachineAccount" action="${ctx}/credit/creditMachineAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属债权：</label>
			<div class="controls">
				<sys:selectPanel idName="resultCreditId" textName="resultCreditName" idField="id" callbackOnSelected="refreshForm2()" url="${ctx}/credit/creditBaseInfo/list?creditStatus=0" path="creditId" title="选择所属债权" ></sys:selectPanel>
				<form:input path="creditId" htmlEscape="false" maxlength="20" class="input-xlarge  digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权投资人：</label>
			<div class="controls">
				<sys:selectPanel idName="resultInvestorId" textName="resultInvestorName" idField="id" callbackOnSelected="refreshForm1()" url="${ctx}/credit/creditInvestUserInfo/list" path="creditInvestUserId" title="选择债权投资人" ></sys:selectPanel>
				<form:input path="creditInvestUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同编号：</label>
			<div class="controls">
				<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资金额：</label>
			<div class="controls">
				
				
				<input id="investMoney" name="investMoney" class="input-xlarge required number" value="<fmt:formatNumber value="${ creditMachineAccount.investMoney}" pattern="0.##" />" maxlength="10" type="text">
				<span class="help-inline"><font color="red">*</font> </span>
				<br><font id="ketou" color="gray"></font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利率（%）：</label>
			<div class="controls">
				
				
				<input id="interestRate" name="interestRate" min="0" max="100" class="input-xlarge required number" value="<fmt:formatNumber value="${ creditMachineAccount.interestRate}" pattern="0.##" />" type="text">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计息方式：</label>
			<div class="controls">
				<form:select path="interestCalculation" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('interest_calculation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起息日：</label>
			<div class="controls">
				<input name="valueDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${creditMachineAccount.valueDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资期限（月）：</label>
			<div class="controls">
				<form:input path="investmentHorizon" htmlEscape="false" maxlength="3" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">到期日：</label>
			<div class="controls">
				<input name="expiringDate" id="expiringDate" type="text" readonly="readonly"  maxlength="20" class="input-medium  required"
					value="<fmt:formatDate value="${creditMachineAccount.expiringDate}" pattern="yyyy-MM-dd"/>"
					/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label">理财经理：</label>
			<div class="controls">
				<form:input path="financialManager" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业部经理：</label>
			<div class="controls">
				<form:input path="businessManager" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款方式：</label>
			<div class="controls">
				<form:select path="paymentMethod" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('payment_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费：</label>
			<div class="controls">
				<form:input path="commissionCharge" htmlEscape="false" min="0" maxlength="7" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同地址：</label>
			<div class="controls">
				<form:input path="contractAddress" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文件：</label>
			<div class="controls">
				<form:hidden id="relevantDocument" path="relevantDocument" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="relevantDocument" type="images" uploadPath="/credit/creditMachineAccount" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续文件：</label>
			<div class="controls">
				<form:checkboxes path="procedureDocuments" items="${fns:getDictList('procedure_documents')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼品领用：</label>
			<div class="controls">
				<form:input path="giftRecipients" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提前赎回：</label>
			<div class="controls">
				<form:input path="aheadRedemptive" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="credit:creditMachineAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>