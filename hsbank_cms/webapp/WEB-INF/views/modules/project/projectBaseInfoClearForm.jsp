<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借贷产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function projectTypeChange(value){
			if(value!=''){
				$("div[id^='extend_']").hide();
				$('#extend_'+value).show();
			}
		}
		$(document).ready(function() {
			//$("#name").focus();
			projectTypeChange($('#projectTypeCode').val());
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
		<li><a href="${ctx}/project/projectBaseInfo/">借贷产品列表</a></li>
		<li class="active"><a href="${ctx}/project/projectBaseInfo/form?id=${projectBaseInfo.id}">借贷产品<shiro:hasPermission name="project:projectBaseInfo:edit">${not empty projectBaseInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="project:projectBaseInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectBaseInfo" action="${ctx}/project/projectBaseInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目编号：</label>
			<div class="controls">
				<form:input path="projectCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否新手项目：</label>
			<div class="controls">
				<form:select path="isNewUser" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_is_new_user_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否推荐项目：</label>
			<div class="controls">
				<form:select path="isRecommend" class="input-xlarge ">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictList('project_is_recommend_dict')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目类型：</label>
			<div class="controls">
				<form:select path="projectTypeCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div id="extend_2" style="display:none;">
			<div class="control-group">
				<label class="control-label">服务费：</label>
				<div class="controls">
					<input id="serviceCharge" value="${projectBaseInfo.serviceCharge}" htmlEscape="false" maxlength="200"
						class="input-xlarge " />
				</div>
			</div>
		</div>
		<div id="extend_1" style="display:none;">
			<div class="control-group">
				<label class="control-label">车辆型号：</label>
				<div class="controls">
					<form:input path="pfcf.carModel" htmlEscape="false" maxlength="200"
						class="input-xlarge " />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">新旧状况：</label>
				<div class="controls">
					<form:input path="pfcf.degreesDepreciation" htmlEscape="false"
						maxlength="20" class="input-xlarge " />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">购买价格：</label>
				<div class="controls">
					<form:input path="pfcf.carPrice" htmlEscape="false"
						class="input-xlarge " />
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目概述：</label>
			<div class="controls">
				<form:textarea path="projectIntroduce" htmlEscape="false" rows="4" maxlength="2048" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
				<form:select path="repaymentMode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_repayment_mode_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资人：</label>
			<div class="controls">
				<form:hidden path="borrowersUser"/>
				<form:input path="bUser.customerName" htmlEscape="false" maxlength="20" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资代理人：</label>
			<div class="controls">
				<form:hidden path="agentUser"/>
				<form:input path="aUser.customerName" htmlEscape="false" maxlength="20" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目区域位置：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${projectBaseInfo.area.id}" labelName="area.name" labelValue="${projectBaseInfo.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目期限(月)：</label>
			<div class="controls">
				<form:input path="projectDuration" htmlEscape="false"
					class="input-xlarge  number" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				<input id="annualizedRate" value="${projectBaseInfo.annualizedRate}" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">贷款用途：</label>
			<div class="controls">
				<form:input path="useMethod" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本期融资金额：</label>
			<div class="controls">
			<input id="financeMoney" name="financeMoney" type="text"  value="<fmt:formatNumber value="${projectBaseInfo.financeMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本次授信金额：</label>
			<div class="controls">
			<input id="creditExtensionMoney" name="creditExtensionMoney" type="text"  value="<fmt:formatNumber value="${projectBaseInfo.creditExtensionMoney}" pattern="#0.00" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划还款日期：</label>
			<div class="controls">
				<input name="plannedRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.plannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
			<input id="startingAmount" name="startingAmount" type="text"  value="<fmt:formatNumber value="${projectBaseInfo.startingAmount}" pattern="#0" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">担保方式：</label>
			<div class="controls">
				<form:select path="safeguardMode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_safeguard_mode_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风控信息:</label>
			<div class="controls">
				<form:textarea id="riskInfo" htmlEscape="false" path="riskInfo" rows="4" maxlength="1000" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/project/risk" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月还本息：</label>
			<div class="controls">
				<input id="monthRepayMoney" <fmt:formatNumber value="${projectBaseInfo.monthRepayMoney}" pattern="#0.00"/> htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文件：</label>
			<div class="controls">
				<form:hidden id="filesImage" path="aboutFiles" htmlEscape="false" maxlength="1000" class="input-xlarge"/>
				<sys:ckfinder input="filesImage" type="images" uploadPath="/project/att" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提前还款费率(%)：</label>
			<div class="controls">
				<input id="earlyRepaymentRate" value="${projectBaseInfo.earlyRepaymentRate}" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际还款日期：</label>
			<div class="controls">
				<input name="actualRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.actualRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投标截止时间：</label>
			<div class="controls">
				<input name="biddingDeadline" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.biddingDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权转让限制：</label>
			<div class="controls">
				<form:input path="transferCode" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目创建日期：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目创建人：</label>
			<div class="controls">
				<form:input path="createUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目审核日期：</label>
			<div class="controls">
				<input name="reviewDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.reviewDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目审核人：</label>
			<div class="controls">
				<form:input path="reviewUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目发布日期：</label>
			<div class="controls">
				<input name="publishDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.publishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目关闭日期：</label>
			<div class="controls">
				<input name="closeDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.closeDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目关闭确认人：</label>
			<div class="controls">
				<form:input path="closeUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目状态：</label>
			<div class="controls">
				<form:select path="projectStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">适用终端：</label>
			<div class="controls">
				<form:checkboxes path="showTermList" items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否删除：</label>
			<div class="controls">
				<form:input path="isDel" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="project:projectBaseInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>