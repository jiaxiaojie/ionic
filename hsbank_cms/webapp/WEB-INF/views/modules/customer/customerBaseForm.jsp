<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员基本信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					/*var certType = $("input[name='certType']:checked").val();
					if(typeof(certType) == "undefined" || certType == "") {
						$("#helpCertType").html("请选择证件类型");
						return false;
					}else {
						$("#helpCertType").html("");
					}*/
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

			$("#certType1").click(function(){
				$("#helpCertType").html("");
				$("#company").hide();
				$("#presonal").show();
			});
			$("#certType2").click(function(){
				$("#helpCertType").html("");
				$("#presonal").hide();
				$("#company").show();
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerAccount/">会员列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerAccount/form?id=${customerBase.accountId}">会员<shiro:hasPermission name="customer:customerBase:edit">${not empty customerBase.accountId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerBase:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/customer/customerAccount/form?id=${customerBase.accountId}">账号信息</a></li>
		<c:if test="${not empty customerBase.accountId}">
		<li class="active"><a href="${ctx}/customer/customerBase/formByAccountId?accountId=${customerBase.accountId}">基本信息</a></li>
			<c:if test="${customerBase.accountType=='0'}">
				<c:if test="${not empty customerBase.customerId}">
		<li><a href="${ctx}/customer/customerWork/formByCustomerId?customerId=${customerBase.customerId}">工作信息</a></li>
		<li><a href="${ctx}/customer/customerHousing/formByCustomerId?customerId=${customerBase.customerId}">房产信息</a></li>
		<li><a href="${ctx}/customer/customerCar/formByCustomerId?customerId=${customerBase.customerId}">车产信息</a></li>
				</c:if>
		<li><a href="${ctx}/customer/customerBankCard/formByAccountId?accountId=${customerBase.accountId}">银行卡信息</a></li>
			</c:if>
		</c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerBase" action="${ctx}/customer/customerBase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="accountId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员名称：</label>
			<div class="controls">
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<c:choose>
			<c:when test="${customerBase.accountType=='0'}">
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:radiobuttons path="genderCode" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">国籍：</label>
			<div class="controls">
				<form:select path="nationalityCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_nationality_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号码：</label>
			<div class="controls">
				<form:input path="certNum" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否实名认证：</label>
			<div class="controls">
				<form:radiobuttons path="nameAuthCode" items="${fns:getDictList('has_auth_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<input type="text" value="${customerBase.age }" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否手机认证：</label>
			<div class="controls">
				<form:radiobuttons path="mobileAuthCode" items="${fns:getDictList('has_auth_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" class="input-xlarge email "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否邮箱认证：</label>
			<div class="controls">
				<form:radiobuttons path="emailAuthCode" items="${fns:getDictList('has_auth_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最高学历：</label>
			<div class="controls">
				<form:select path="educationCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业院校：</label>
			<div class="controls">
				<form:input path="educationSchool" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历附件：</label>
			<div class="controls">
				<form:hidden id="educationFile" path="educationFile" htmlEscape="false" maxlength="500" class="input-xlarge"/>
				<sys:ckfinder input="educationFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">婚姻状况：</label>
			<div class="controls">
				<form:select path="marriageCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_marriage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">居住地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍：</label>
			<div class="controls">
				<form:input path="familyRegister" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍附件：</label>
			<div class="controls">
				<form:hidden id="familyRegisterFile" path="familyRegisterFile" htmlEscape="false" maxlength="500" class="input-xlarge"/>
				<sys:ckfinder input="familyRegisterFile" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信用卡银行：</label>
			<div class="controls">
				<form:select path="creditCardBankCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_bank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信用卡卡号：</label>
			<div class="controls">
				<form:input path="creditCardNo" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信用卡额度：</label>
			<div class="controls">
				<form:input path="creditCardLimit" htmlEscape="false" maxlength="9" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否信用卡认证：</label>
			<div class="controls">
				<form:radiobuttons path="creditCardAuthCode" items="${fns:getDictList('has_auth_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
			</c:when>
			<c:otherwise>
		<form:hidden path="customerOrgExtendInfo.customerId"/>
		<div class="control-group">
			<label class="control-label">组织机构代码：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.orgCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照编号：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.orgBusinessLicense" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">税务登记号：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.orgTaxRegistration" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册年限：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.registeredIfe" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册资金（万元）：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.registeredCapital" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资产净值（万元）：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.netWorth" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上年度经营现金流入（万元）：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.cashInflows" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.industry" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营情况：</label>
			<div class="controls">
				<form:textarea path="customerOrgExtendInfo.businessActivities" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">涉诉情况：</label>
			<div class="controls">
				<form:textarea path="customerOrgExtendInfo.litigationSituatio" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">征信记录：</label>
			<div class="controls">
				<form:textarea path="customerOrgExtendInfo.creditRecord" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建人：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.createUserId" readonly="true" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改人：</label>
			<div class="controls">
				<form:input path="customerOrgExtendInfo.modifyUserId" readonly="true" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="customerOrgExtendInfo.status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_org_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
			</c:otherwise>
		</c:choose>
		
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerBase.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后一次修改时间：</label>
			<div class="controls">
				<input name="lastModifyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerBase.lastModifyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerBase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
				<c:if test="${customerBase.accountType!='0'}">
					<shiro:hasPermission name="customer:customerBase:edit">
			<input class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/customer/customerOrgFinanceYearRecord/list?customerId=${customerBase.customerId}'" value="组织会员财务年表"/>&nbsp;
					</shiro:hasPermission>
				</c:if>
				<c:if test="${not empty customerBase.customerId}">
					<shiro:hasPermission name="customer:customerBaseHis:view">
			<input class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/customer/customerBaseHis/list?customerBase.customerId=${customerBase.customerId}'" value="查看修改记录"/>&nbsp;
					</shiro:hasPermission>
				</c:if>
			<a href="${ctx}/customer/customerAccount/"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>
		</div>
	</form:form>
</body>
</html>