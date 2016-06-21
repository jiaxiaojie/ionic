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
			
			$(":radiobutton").attr("disabled",true); 
			//$("#name").focus();
			projectTypeChange($('#projectTypeCode').val());
			$('#repaymentMode').select2("readonly", true);
			$('#isNewUser').select2("readonly", true);
			$('#projectTypeCode').select2("readonly", true);
			$('#serviceChargeTypeCode').select2("readonly", true);
			$('#isIncreaseInterest').select2("readonly", true);
			$('input:radio[name=projectStatus]')[0].checked = true;
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/project/projectBaseInfo/reviewlist">借贷产品列表</a></li>
		<li class="active"><a href="${ctx}/project/projectBaseInfo/reviewform?id=${projectBaseInfo.id}">借贷产品审批</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectBaseInfo" action="${ctx}/project/projectBaseInfo/review" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目编号：</label>
			<div class="controls">
				<form:input path="projectCode" htmlEscape="false" maxlength="50" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关联债权：</label>
			<div class="controls">
				<form:input path="creditName" htmlEscape="false" maxlength="500" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目类型：</label>
			<div class="controls">
				<form:select path="projectTypeCode"  class="input-xlarge" disabled="true" id="projectTypeCode">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_type_dict')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">项目区域位置：</label>
			<div class="controls">
			<form:input path="area.name" value ="${projectBaseInfo.area.name}" disabled="true" htmlEscape="false" maxlength="20" class="input-xlarge "  readonly="true"/>
			<form:hidden path="area.id" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">项目提示：</label>
			<div class="controls">
				<form:textarea path="projectTips" htmlEscape="false" rows="2" maxlength="200" class="input-xxlarge" readonly="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">项目概述：</label>
			<div class="controls">
				<form:textarea path="projectIntroduce" htmlEscape="false" rows="4" maxlength="2048" class="input-xxlarge" readonly="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">贷款用途：</label>
			<div class="controls">
				<form:input path="useMethod" htmlEscape="false" maxlength="100" class="input-xlarge" readonly="true"/>
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
			<label class="control-label">本次授信金额：</label>
			<div class="controls">
				<input id="creditExtensionMoney" name="creditExtensionMoney" readonly="true" type="text" value="<fmt:formatNumber value="${projectBaseInfo.creditExtensionMoney}" pattern="#0.00"/>" />
			</div>
		</div>
		
			<div class="control-group">
			<label class="control-label">本期融资金额：</label>
			<div class="controls">
				<input id="financeMoney" name="financeMoney"  type="text" readonly="true" value="<fmt:formatNumber value="${projectBaseInfo.financeMoney}" pattern="#0.00"/>" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
				<input type="text" id="startingAmount" name="startingAmount"  readonly="true" value="<fmt:formatNumber value="${projectBaseInfo.startingAmount}" pattern="#0"/>"  />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">最大投资金额：</label>
			<div class="controls">
				<input id="maxAmount" type="text" name="maxAmount" readonly="true" value="<fmt:formatNumber value="${projectBaseInfo.maxAmount}"  pattern="#0"/>" />
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">项目期限类型：</label>
			<div class="controls">
				<form:select path="durationType" disabled="true" class="input-xlarge required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('project_duration_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目期限(日/月)：</label>
			<div class="controls">
				<form:input path="projectDuration" htmlEscape="false"
					class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				<form:input path="annualizedRate" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
				<form:select path="repaymentMode" class="input-xlarge" disabled="true" id="repaymentMode">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_repayment_mode_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">项目发布日期：</label>
			<div class="controls">
				<input name="publishDt" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${projectBaseInfo.publishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">投标截止时间：</label>
			<div class="controls">
				<input name="biddingDeadline" type="text" readonly="readonly" maxlength="20" class="input-medium "
					value="<fmt:formatDate value="${projectBaseInfo.biddingDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">计划还款日期：</label>
			<div class="controls">
				<input name="plannedRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.plannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					"/>
			</div>
		</div>
		
		
		
		<div class="control-group">
			<label class="control-label">是否新手项目：</label>
			<div class="controls">
				<form:select path="isNewUser" class="input-xlarge" disabled="true" id="isNewUser">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_is_new_user_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否推荐项目：</label>
			<div class="controls">
				<form:select path="isRecommend" disabled="true" class="input-xlarge  required">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictList('project_is_recommend_dict')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">是否可用加息券：</label>
			<div class="controls">
				<form:select path="canUseRateTicket" disabled="true" class="input-xlarge">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictList('yes_no')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">债权转让限制：</label>
			<div class="controls">
				<form:select id="transferCode" path="transferCode" disabled="true"  class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_transfer_code')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">担保方式：</label>
			<div class="controls">
				<form:select path="safeguardMode" disabled="true" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_safeguard_mode_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<c:if test="${projectBaseInfo.projectTypeCode eq '2' }">
		    <div class="control-group">
				<label class="control-label">服务费收取策略：</label>
				<div class="controls">
					<form:select path="serviceChargeTypeCode" disabled="true" id="serviceChargeTypeCode" class="input-xlarge">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('project_service_charge_type_dict')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<div class="control-group">
			<label class="control-label">服务费：</label>
			<div class="controls">
				<input type="text" id="serviceCharge" disabled="true" value="<fmt:formatNumber value="${projectBaseInfo.serviceCharge}" pattern="#0.000"/>"  htmlEscape="false" class="input-xlarge  number" />
			</div>
		   </div>
	   </c:if>
		
		<div class="control-group">
			<label class="control-label">是否加息：</label>
			<div class="controls">
				<form:select path="isIncreaseInterest" disabled="true" id="isIncreaseInterest" class="input-xlarge">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictList('is_increase_interest_dict')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加息比例：</label>
			<div class="controls">
				<input type="text" id="increaseInterestRate" disabled="true"  value="<fmt:formatNumber value="${projectBaseInfo.increaseInterestRate}" pattern="#0.00"/>"   htmlEscape="false" maxlength="5"
					min="0" max="1" class="input-xlarge  number" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">提前还款费率(%)：</label>
			<div class="controls">
				<input id="earlyRepaymentRate" type="text"  disabled="true" value="<fmt:formatNumber value="${projectBaseInfo.earlyRepaymentRate}" pattern="#0.00"/>"  minmore="0" max="1" readonly="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" htmlEscape="false" class="input-xlarge  number" maxlength="5"/>
			</div>
		
		</div>
		
		<div class="control-group">
			<label class="control-label">风控信息:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true"  disabled="true"  path="riskInfo" rows="4" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/project/risk" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文件：</label>
			<div class="controls">
				<form:hidden id="filesImage" path="aboutFiles" disabled="false"  htmlEscape="false" maxlength="1000" class="input-xlarge "/>
				<sys:ckfinder input="filesImage" type="images" readonly="true" uploadPath="/project/att" selectMultiple="true" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		
		
		
		
		<%-- <div id="extend_1" style="display:none;">
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
		</div> --%>
	
		<%-- <div class="control-group">
			<label class="control-label">实际还款日期：</label>
			<div class="controls">
				<input name="actualRepaymentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.actualRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					"/>
			</div>
		</div>  --%>
		<%-- 
		
		<div class="control-group">
			<label class="control-label">项目创建日期：</label>
			<div class="controls">
			<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目创建人：</label>
			<div class="controls">
				<form:hidden path="createUserId" />
				<form:input path="cUser.name" htmlEscape="false" maxlength="20" class="input-xlarge " readonly="true"/>
			</div>
		</div>
 --%>

		
		
		<div class="control-group">
			<label class="control-label">适用终端：</label>
			<div class="controls">
				<form:checkboxes path="showTermList" disabled="true" items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">项目创建人：</label>
			<div class="controls">
				<form:hidden path="createUserId" />
				<form:input path="cUser.name" htmlEscape="false" maxlength="20" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		
			<div class="control-group">
			<label class="control-label">项目创建日期：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" disabled="true" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${projectBaseInfo.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">审核状态：</label>
			<div class="controls">
				<form:radiobutton path="projectStatus"  value="2" id="projectStatus_2"/> 通过
				<form:radiobutton path="projectStatus"  value="0" id="projectStatus_0"/>不通过
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">审核意见：</label>
			<div class="controls">
				<form:textarea path="reviewRemark" htmlEscape="false" rows="4" maxlength="250" class="input-xxlarge required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="project:projectBaseInfo:review"><input id="btnSubmit" class="btn btn-primary" type="submit" value="审 批"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>