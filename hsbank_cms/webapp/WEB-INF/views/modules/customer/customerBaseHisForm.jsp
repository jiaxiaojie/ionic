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
		<li><a href="${ctx}/customer/customerBase/form?id=${customerBaseHis.customerBase.customerId}">会员<shiro:hasPermission name="customer:customerBase:edit">${not empty customerBaseHis.customerBase.customerId?'信息修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerBase:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/customer/customerBaseHis/list?customerBase.customerId=${customerBaseHis.customerBase.customerId}">会员基本信息变更历史列表</a></li>
		<li class="active"><a href="${ctx}/customer/customerBaseHis/form?id=${customerBaseHis.id}">会员基本信息变更历史<shiro:hasPermission name="customer:customerBaseHis:edit">${not empty customerBaseHis.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:customerBaseHis:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="customerBaseHis" action="${ctx}/customer/customerBaseHis/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="customerBase.accountId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员名称：</label>
			<div class="controls">
				<form:input path="customerBase.customerName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否实名认证：</label>
			<div class="controls">
				<form:radiobuttons path="customerBase.nameAuth" items="${fns:getDictList('has_auth_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">国籍：</label>
			<div class="controls">
				<form:select path="customerBase.nationality" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_nationality_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<c:choose>
			<c:when test="${customerBaseHis.customerBase.accountType=='0'}">
		<div class="control-group">
			<label class="control-label">身份证号码：</label>
			<div class="controls">
				<form:input path="customerBase.certNum" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<form:input path="customerBase.ageScore" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:radiobuttons path="customerBase.genderCode" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">婚姻状态：</label>
			<div class="controls">
				<form:radiobuttons path="customerBase.marriageCode" items="${fns:getDictList('marriage_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最高学历：</label>
			<div class="controls">
				<form:select path="customerBase.educationCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年收入：</label>
			<div class="controls">
				<form:select path="customerBase.incomeCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('income_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="customerBase.contactNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机1：</label>
			<div class="controls">
				<form:input path="customerBase.mobile1" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否手机认证：</label>
			<div class="controls">
				<form:radiobuttons path="customerBase.isMobileValidate" items="${fns:getDictList('has_auth_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机2：</label>
			<div class="controls">
				<form:input path="customerBase.mobile2" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">居住地址：</label>
			<div class="controls">
				<form:input path="customerBase.address" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">QQ号：</label>
			<div class="controls">
				<form:input path="customerBase.qqNum" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱：</label>
			<div class="controls">
				<form:input path="customerBase.email" htmlEscape="false" maxlength="50" class="input-xlarge email "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否邮箱认证：</label>
			<div class="controls">
				<form:radiobuttons path="customerBase.isEmailValidate" items="${fns:getDictList('has_auth_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">居住地邮编：</label>
			<div class="controls">
				<form:input path="customerBase.postCode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信用卡银行：</label>
			<div class="controls">
				<form:select path="customerBase.creditCardBank" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_bank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信用卡卡号：</label>
			<div class="controls">
				<form:input path="customerBase.creditCardNo" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否信用卡认证：</label>
			<div class="controls">
				<form:radiobuttons path="customerBase.creditCardAuth" items="${fns:getDictList('has_auth_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件扫描照1：</label>
			<div class="controls">
				<form:hidden id="certFile1" path="customerBase.certFile1" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<sys:ckfinder input="certFile1" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件扫描照2：</label>
			<div class="controls">
				<form:hidden id="certFile2" path="customerBase.certFile2" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<sys:ckfinder input="certFile2" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件扫描照3：</label>
			<div class="controls">
				<form:hidden id="certFile3" path="customerBase.certFile3" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<sys:ckfinder input="certFile3" type="images" uploadPath="/customer" selectMultiple="true"/>
			</div>
		</div>
			</c:when>
			<c:otherwise>
		<div class="control-group">
			<label class="control-label">组织机构代码：</label>
			<div class="controls">
				<form:input path="customerBase.customerOrgExtendInfo.orgCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照编号：</label>
			<div class="controls">
				<form:input path="customerBase.customerOrgExtendInfo.orgBusinessLicense" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">税务登记号：</label>
			<div class="controls">
				<form:input path="customerBase.customerOrgExtendInfo.orgTaxRegistration" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册年限：</label>
			<div class="controls">
				<form:input path="customerBase.customerOrgExtendInfo.registeredIfe" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册资金（万元）：</label>
			<div class="controls">
				<form:input path="customerBase.customerOrgExtendInfo.registeredCapital" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上年度经营现金流入（万元）：</label>
			<div class="controls">
				<form:input path="customerBase.customerOrgExtendInfo.cashInflows" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业：</label>
			<div class="controls">
				<form:input path="customerBase.customerOrgExtendInfo.industry" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营情况：</label>
			<div class="controls">
				<form:textarea path="customerBase.customerOrgExtendInfo.businessActivities" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">涉诉情况：</label>
			<div class="controls">
				<form:textarea path="customerBase.customerOrgExtendInfo.litigationSituatio" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">征信记录：</label>
			<div class="controls">
				<form:textarea path="customerBase.customerOrgExtendInfo.creditRecord" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建人：</label>
			<div class="controls">
				<form:input path="customerBase.customerOrgExtendInfo.createUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改人：</label>
			<div class="controls">
				<form:input path="customerBase.customerOrgExtendInfo.modifyUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="customerBase.customerOrgExtendInfo.status" class="input-xlarge ">
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
				<input name="customerBase.createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerBaseHis.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="customer:customerBaseHis:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<a href="${ctx}/customer/customerAccount/"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>
		</div>
	</form:form>
</body>
</html>