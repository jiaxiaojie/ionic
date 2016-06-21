<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>花生乐园上架产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
		function updateForm(){
			var priceType = $("select[name='integralMallProductPrice.priceType']").attr("value");
			if(priceType==1){
				$("input[name='integralMallProductPrice.marketDiscount']").attr("disabled","disabled");
				$("input[name='integralMallProductPrice.marketNewPrice']").removeAttr("disabled");
			}
			else if(priceType==2){
				$("input[name='integralMallProductPrice.marketNewPrice']").attr("disabled","disabled");
				$("input[name='integralMallProductPrice.marketDiscount']").removeAttr("disabled");
			}else{
				$("input[name='integralMallProductPrice.marketDiscount']").attr("disabled","disabled");
				$("input[name='integralMallProductPrice.marketNewPrice']").attr("disabled","disabled");
			}
			
		}
		$(document).ready(function() {
			updateForm();
			
			$("select[name='integralMallProductPrice.priceType']").bind('change', function() {
				updateForm();
				
			});

			
			
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					
					var filesImage = $("#productLogoNormal").val();
					if(filesImage == ''){
						$.jBox.error("产品logo(normal)不能为空", "提示");
						return false;
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					}
					
				},
				messages:{upDt: {notEqual: "上架时间不能等于下架时间。"}},
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
			typeChange();
			$("#relTicketId").select2("val", "${integralMallProduct.relTicketId}");
		});
		
		//日期校验
		var dateUtils = new DateUtils();
		$(function(){
			$("#upDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:dateUtils.formatDate(new Date(), "yyyy-MM-dd"),maxDate:$("#dowDt").val()});
			});
			
			$("#dowDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:$("#upDt").val()});
			});
			
			
			
			$("#productPriceBeginDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:dateUtils.formatDate(new Date(), "yyyy-MM-dd"),maxDate:$("#productPricEndDt").val()});
			});
			
			$("#productPricEndDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:$("#productPriceBeginDt").val()});
			});
		});
		function typeChange(){
			var typeVal=$("#productTypeId").val();
			if(typeVal==2){
				$('#tickets').show();
				$("#relTicketId").select2("val", "");
				$("#parValue").hide();
			}else{
				$('#tickets').hide();
				$("#relTicketId").select2("val", "");
				$("#parValue").show();
			}
		}
	</script>
	
			
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/integral/integralMallProduct/list">上架产品列表</a></li>
		<li class="active"><a href="${ctx}/integral/integralMallProduct/form?id=${integralMallProduct.id}">上架产品<shiro:hasPermission name="integral:integralMallProduct:edit">${not empty integralMallProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="integral:integralMallProduct:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="integralMallProduct" action="${ctx}/integral/integralMallProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="status"  id="status" />
		<sys:message content="${message}"/>	
		
		<form:hidden path="integralMallProductPrice.id"/>
			
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="productName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品类别：</label>
			<div class="controls">
				<form:select path="productTypeId" class="input-xlarge required" onchange="typeChange()">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('marketing_product_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> 
		
		<div class="control-group" id="tickets" style="display:none;">
			<label class="control-label">选择现金券：</label>
			<div class="controls">
				<form:select path="relTicketId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${ticketList}" itemLabel="ticketTypeName" itemValue="id" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group" id="parValue">
			<label class="control-label">产品面值：</label>
			<div class="controls">
				<form:input path="productParValue" htmlEscape="false" maxlength="8" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品logo(min)：</label>
			<div class="controls">
				<form:hidden id="productLogoMin" path="productLogoMin" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<sys:ckfinder input="productLogoMin" type="images"  uploadPath="/integral/integralMallProduct" selectMultiple="false"/>
				
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">产品logo(normal)：</label>
			<div class="controls">
				<form:hidden id="productLogoNormal" path="productLogoNormal" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<sys:ckfinder input="productLogoNormal" type="images" first="false" uploadPath="/integral/integralMallProduct" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品logo(max)：</label>
			<div class="controls">
				<form:hidden id="productLogoMax" path="productLogoMax" htmlEscape="false" maxlength="50" class="input-xlarge"/>
				<sys:ckfinder input="productLogoMax" type="images" first="false" uploadPath="/integral/integralMallProduct" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品简介：</label>
			<div class="controls">
			    <form:textarea id="content" htmlEscape="true" path="productIntroduction" rows="4" maxlength="200" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/integral/integralMallProduct" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否重点推荐：</label>
			<div class="controls">
				<form:select path="isRecommend" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原始价格：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" maxlength="9" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动价格类型：</label>
			<div class="controls">
				<form:select path="integralMallProductPrice.priceType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('integral_project_price_type_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">活动价格：</label>
			<div class="controls">
				<form:input path="integralMallProductPrice.marketNewPrice" htmlEscape="false" min="0" maxlength="9" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动折扣：</label>
			<div class="controls">
				<form:input path="integralMallProductPrice.marketDiscount" htmlEscape="false" min="0" max="1" maxlength="5" class="input-xlarge number required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动开始时间：</label>
			<div class="controls">
				<input id="productPriceBeginDt" name="integralMallProductPrice.beginDt" type="text"  readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${integralMallProduct.integralMallProductPrice.beginDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动结束时间：</label>
			<div class="controls">
				<input id="productPricEndDt" name="integralMallProductPrice.endDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${integralMallProduct.integralMallProductPrice.endDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动状态：</label>
			<div class="controls">
				<form:select path="integralMallProductPrice.status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('integral_mall_status_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">上架时间：</label>
			<div class="controls">
				<input id="upDt" name="upDt" type="text" notEqual="#dowDt" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${integralMallProduct.upDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下架时间：</label>
			<div class="controls">
				<input id="dowDt" name="dowDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${integralMallProduct.dowDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上架数量：</label>
			<div class="controls">
				<form:input path="productCount" htmlEscape="false" maxlength="9" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">库存数量：</label>
			<div class="controls">
				<form:input path="productSurplus" htmlEscape="false" maxlength="9" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="integral:integralMallProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>