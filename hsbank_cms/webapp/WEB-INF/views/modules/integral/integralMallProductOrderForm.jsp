<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>花生乐园订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$('#productTypeId').select2("readonly", true);
			$('#createChannelId').select2("readonly", true);
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
		<li><a href="${ctx}/integral/integralMallProductOrder/">订单列表</a></li>
		<li class="active"><a href="${ctx}/integral/integralMallProductOrder/form?id=${integralMallProductOrder.id}">订单<shiro:hasPermission name="integral:integralMallProductOrder:edit">${not empty integralMallProductOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="integral:integralMallProductOrder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="integralMallProductOrder" action="${ctx}/integral/integralMallProductOrder/updateProductOrderAndAddress" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="addressId"/>
		<form:hidden path="flowUserId"/>
		
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">产品类型：</label>
			<div class="controls">
				<form:select path="productTypeId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('marketing_product_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="product.productName" htmlEscape="false" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品数量：</label>
			<div class="controls">
				<form:input path="productCount" htmlEscape="false" maxlength="11" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品单价：</label>
			<div class="controls">
				<form:input path="productPrice" htmlEscape="false" maxlength="11" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品总价：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" maxlength="11" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户：</label>
			<div class="controls">
				<form:input path="customerBase.customerName" htmlEscape="false" maxlength="20" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " readonly="true"
					value="<fmt:formatDate value="${integralMallProductOrder.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建渠道：</label>
			<div class="controls">
				<form:select path="createChannelId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				<form:select path="orderStatus" class="input-xlarge " readonly="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('integral_mall_order_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收件人姓名：</label>
			<div class="controls">
				<form:input path="showName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收件人手机：</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属区域:</label>
			<div class="controls">
				<sys:treeselect id="area" name="address.districtId" value="${integralMallProductOrder.address.district.id}" labelName="area.name" labelValue="${integralMallProductOrder.address.province.name} ${integralMallProductOrder.address.city.name} ${integralMallProductOrder.address.district.name}"
								title="区域" url="/sys/area/treeData" cssClass="input-xlarge" cssStyle="width:450px"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收件人地址：</label>
			<div class="controls">
				<form:textarea path="addressShow" htmlEscape="false" rows="4" maxlength="250" class="input-xxlarge required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">区域（区，县）：</label>
			<div class="controls">
				<form:input path="districtIds" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="form-actions">
			<c:if test="${integralMallProductOrder.orderStatus != 4 && integralMallProductOrder.orderStatus != -1}">
			<shiro:hasPermission name="integral:integralMallProductOrder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>