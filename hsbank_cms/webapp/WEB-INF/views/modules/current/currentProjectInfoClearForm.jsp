<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期项目信息管理</title>
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
	
		$(document).ready(function() {
			
			$(":input").not($("#btnSubmit")).not($("#windingUpApplyReason")).not($("#btnCancel")).not($("[type=hidden]")).attr("disabled",true);
			
			
			$("#borrowerAccountId").select2({
				placeholder : "请输入",
				minimumInputLength : 2,
				initSelection : function(element, callback) { // 初始化时设置默认值
					 callback({id:$('#borrowerAccountId').val(),text:'',val:queryEntiyById("${ctx}/customer/customerAccount/queryById",$('#borrowerAccountId').val()).accountName});
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
			
			
			//$("#name").focus();
			$("#inputForm").validate({
				rules: {
					code:{
						remote: {
							type:"post",
							url:"${ctx}/current/currentProjectInfo/checkCode",
		                    data: {
		                    	 id:$('#id').val(),
		                    	 code: function() {return $("#code").val();}
		                    }
						},
						
					},
					name:{
						remote: {
							type:"post",
							url:"${ctx}/current/currentProjectInfo/checkName",
		                    data: {
		                    	 id:$('#id').val(),
		                    	 name: function() {return $("#name").val();}
		                    }
						},
						
					}
				},
				messages: {
					code: {remote: "项目编号已存在"},
					name: {remote: "项目名称已存在"},
		        	financeMoney : {min : "融资金额不能小于起投金额."},
		        	maxAmount : {min : "最大投资金额不能小于起投金额."}
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
		
		
		//日期相关校验
		var dateUtils = new DateUtils();
		var maxDt = function(){
			
			var result = "";
			var endDt = $("#endInvestmentDt").val()
			if(endDt!=null && endDt != ""){
				result = endDt
			}
			return result;
		};
		$(function(){
			//发布日期限制
			$("#publishDt").click(function() {
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:dateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"),maxDate:maxDt()});
			});
			
			//投标截止时间
			$("#endInvestmentDt").click(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,isShowToday:false,minDate:$("#publishDt").val()});
			});
			
			//融资金额校验
			$("#financeMoney").change(function() {
				$("#financeMoney").attr("min", $("#startingAmount").val());
			});
			//最大投资金额校验
			$("#maxAmount").change(function() {
				$("#maxAmount").attr("min", $("#startingAmount").val());
			});
			//起头金额校验
			$("#startingAmount").change(function() {
				$("#financeMoney").attr("min", $("#startingAmount").val());
				$("#maxAmount").attr("min", $("#startingAmount").val());
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/current/currentProjectInfo/clearList">活期项目清盘列表</a></li>
		<li class="active"><a href="${ctx}/current/currentProjectInfo/clearForm?id=${currentProjectInfo.id}">活期项目清盘申请</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="currentProjectInfo" action="${ctx}/current/currentProjectInfo/clear" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目编号：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				<input id="rate" name="rate" minmore="0" max="1" class="input-xlarge  number required " value="<fmt:formatNumber value="${currentProjectInfo.rate}" pattern="#0.###" />" maxlength="5" type="text">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">单位净值：</label>
			<div class="controls">
				<input id="netWorth" name="netWorth"  class="input-xlarge  number required" value="<fmt:formatNumber value="${currentProjectInfo.netWorth}" pattern="#0.####" />" maxlength="7" type="text">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
				<input id="startingAmount" name="startingAmount" min="1" class="input-xlarge  number required" value="<fmt:formatNumber value="${currentProjectInfo.startingAmount}" pattern="#0.##" />" maxlength="11" type="text">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大投资额度：</label>
			<div class="controls">
				<input id="maxAmount" name="maxAmount"  class="input-xlarge  number required error" value="<fmt:formatNumber value="${currentProjectInfo.maxAmount}" pattern="#0.##" />" maxlength="11" type="text">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资金额：</label>
			<div class="controls">
				<input id="financeMoney" name="financeMoney"  class="input-xlarge  number required" value="<fmt:formatNumber value="${currentProjectInfo.financeMoney}" pattern="#0.##" />" maxlength="11" type="text">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布日期：</label>
			<div class="controls">
				<input name="publishDt" id="publishDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${currentProjectInfo.publishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投标截止时间：</label>
			<div class="controls">
				<input name="endInvestmentDt" id="endInvestmentDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${currentProjectInfo.endInvestmentDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">融资人：</label>
			<div class="controls">
				<form:input path="borrowerAccountId" disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" class="input-xlarge select2-container bigdrop required" id="borrowerAccountId" /><span id="span_borrowersUser_error" style="color:red;"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风控信息：</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="riskInfo" rows="4" maxlength="200" class="input-xxlarge "/>
				<sys:ckeditor replace="content" uploadPath="/currentProject/risk" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文件：</label>
			<div class="controls">
				<form:hidden id="filesImage" path="aboutFiles" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<!-- <img src="${currentProjectInfo.aboutFiles }" url="${currentProjectInfo.aboutFiles }" style="max-width:100px;max-height:100px;_height:100px;border:0;padding:3px;"> -->
				<sys:ckfinder input="filesImage" type="images" uploadPath="/currentProject/att" selectMultiple="true" maxWidth="100" maxHeight="100" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自动还款授权：</label>
			<div class="controls">
				<form:select path="isAutoRepay"  class="input-xlarge required">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictList('yes_no')}"
						itemLabel="label" itemValue="value" htmlEscape="false"  />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目概述：</label>
			<div class="controls">
				
				<form:textarea path="introduce" htmlEscape="false" rows="4" 
					maxlength="2048" class="input-xxlarge required" />
			</div>
		</div>
		
				<div class="control-group">
			<label class="control-label">投标范围：</label>
			<div class="controls">
				<form:textarea path="investmentScope" htmlEscape="false" rows="4" 
					maxlength="2048" class="input-xxlarge required" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">项目提示：</label>
			<div class="controls">
			<textarea rows="" id="tips" name="tips" htmlEscape="false"
					  maxlength="1000" class="input-xxlarge required">
${currentProjectInfo.tips!=null?currentProjectInfo.tips:'一元起投，灵活存取，按日计息'}</textarea>

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">购买规则：</label>
			<div class="controls">
				
				<textarea rows="4" id="buyRule" readonly="readonly" name="buyRule" htmlEscape="false"
					maxlength="1000" class="input-xxlarge required">
${currentProjectInfo.buyRule}</textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赎回规则：</label>
			<div class="controls">
				
				<textarea rows="5" style="width:45%" id="redeemRule" readonly="readonly" name="redeemRule" htmlEscape="false"
					maxlength="1000" class="input-xxlarge required">
${currentProjectInfo.redeemRule}</textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收益计算规则：</label>
			<div class="controls">
				
				
					<textarea rows="4" id="interestCalculateRule" readonly="readonly" name="interestCalculateRule" htmlEscape="false"
					maxlength="1000" class="input-xxlarge required">
${currentProjectInfo.interestCalculateRule}</textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障方式：</label>
			<div class="controls">
				
				<form:input path="safeDescription" htmlEscape="false"
					maxlength="100" class="input-xxlarge required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用说明：</label>
			<div class="controls">
				
				<form:input path="feeDescription" htmlEscape="false" 
					maxlength="100" class="input-xxlarge required" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">期限：</label>
			<div class="controls">
				
				<input type="text" value="活期" readonly="readonly" name="duration" htmlEscape="false"
					maxlength="100" class="input-xxlarge required" />
			</div>
		</div>
		
		<%-- 
		<!-- 
		<div class="control-group">
			<label class="control-label">项目状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('current_project_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${currentProjectInfo.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label">创建人：</label>
			<div class="controls">
				<form:input path="createUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">审核时间：</label>
			<div class="controls">
				<input name="reviewDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${currentProjectInfo.reviewDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
				<form:input path="reviewUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">清盘状态：</label>
			<div class="controls">
				<form:select path="windingUpStatus" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('current_project_winding_up_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">清盘申请时间：</label>
			<div class="controls">
				<input name="windingUpApplyDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${currentProjectInfo.windingUpApplyDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">清盘申请原因：</label>
			<div class="controls">
				<form:input path="windingUpApplyReason" htmlEscape="false" maxlength="2048" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">清盘申请人：</label>
			<div class="controls">
				<form:input path="windingUpApplyUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">清盘审核时间：</label>
			<div class="controls">
				<input name="windingUpReviewDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${currentProjectInfo.windingUpReviewDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">清盘审核意见：</label>
			<div class="controls">
				<form:input path="windingUpReviewRemark" htmlEscape="false" maxlength="2048" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">清盘审核人：</label>
			<div class="controls">
				<form:input path="windingUpReviewUserId" htmlEscape="false" maxlength="20" class="input-xlarge  digits required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="finishDt" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${currentProjectInfo.finishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		-->
		
		<div class="control-group">
			<label class="control-label">产品审核状态：</label>
			<div class="controls">
				<form:radiobutton path="status"  value="2" id="projectStatus_2" class="required"/> 通过
				<form:radiobutton path="status"  value="0" id="projectStatus_0" class="required"/>不通过
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">产品审核意见：</label>
			<div class="controls">
				<form:textarea path="reviewRemark" htmlEscape="false" maxlength="500" rows="4"  class="input-xlarge required"/>
			</div>
		</div>
		--%>
		
		<div class="control-group">
			<label class="control-label">已融资金额：</label>
			<div class="controls">
				<form:input path="snapshot.hasFinancedMoney" htmlEscape="false" maxlength="500"  class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前实际本金：</label>
			<div class="controls">
				<form:input path="snapshot.realPrincipal" htmlEscape="false" maxlength="500"  class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已产生利息：</label>
			<div class="controls">
				<form:input path="snapshot.hasRepaidMoney" htmlEscape="false" maxlength="500"  class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已提取利息：</label>
			<div class="controls">
				<form:input path="snapshot.hasRedeemInterest" htmlEscape="false" maxlength="500"  class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">清盘申请原因：</label>
			<div class="controls">
				<form:textarea path="windingUpApplyReason" htmlEscape="false" maxlength="500" rows="4"  class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="current:currentProjectInfo:clear">
				<c:if test="${currentProjectInfo.windingUpStatus=='0'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交审批"/>
				</c:if>
				<c:if test="${currentProjectInfo.windingUpStatus!='0'}">
					 <a href="#" class="btn  disabled btn-warning">项目提交清盘后不能修改</a>
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	</form:form>
</body>
</html>