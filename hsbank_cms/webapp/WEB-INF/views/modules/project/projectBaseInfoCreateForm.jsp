<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>借贷产品管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	var credit = null;
	var validateFlag=true;
	var wFinancingMaturity = null;
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
	function projectTypeChange(value){
		if(value!=''){
			$("div[id^='extend_']").hide();
			$('#extend_'+value).show();
		}
	}
	function isNewUserChange(value){
		if(value != ''){
			if(value == '0'){
				$("#transferCode").select2("val", "-1");
				 $.jBox.tip('新手项目将不允许转让');
				 $("#transferCode").select2("readonly", true);
				 //加息券限制
				 $("#canUseRateTicket").select2("val", "0");
				 $("#canUseRateTicket").select2("readonly", true);



			}else{
				$("#transferCode").select2("val", "");
				$("#transferCode").select2("readonly", false);
				//加息券限制
				 $("#canUseRateTicket").select2("val", "");
				 $("#canUseRateTicket").select2("readonly", false);
			}
		}
	}
	function isDurationTypeChange(value){
		if(value != ''){
			if(value == '1'){
				$("#repaymentMode").select2("val", "1");
				$("#repaymentMode").select2("readonly", true);
				$("#projectDuration").attr("max", "");
			}else{
				$("#repaymentMode").select2("val", "");
				$("#repaymentMode").select2("readonly", false);
				$("#projectDuration").attr("max", "48");
			}
		}
	}
	$(document).ready(
			function() {
				//暂时项目期限类型设置为按月只读
//				$("#durationType").select2("val","2");
//			    $("#durationType").select2("readonly",true);
				
				$("#isIncreaseInterest").bind('change', function() {
					 var val = $(this).val();
					 if(val == "0"){
						 $("#increaseInterestRate").val("");
						 $("#increaseInterestRate").attr("disabled","disabled");
					 }
					 else if(val == "1")
					 {
						 $("#increaseInterestRate").removeAttr("disabled");
					 }
				});
				
				$("#serviceChargeTypeCode").bind('change', function() {
					 var val = $(this).val();
					 if(val == "0"){
						 $("#serviceCharge").val("");
						 $("#serviceCharge").attr("disabled","disabled");
					 }else{
						 $("#serviceCharge").removeAttr("disabled");
					 }
				});
				
				
				
				projectTypeChange($('#projectTypeCode').val());
				$('#btnSave').click(function() {
					$("input[name='projectStatus']").val('0');
					$('#inputForm').submit();
				});
				$('#btnSubmit').click(function() {
					$("input[name='projectStatus']").val('1');
					$('#inputForm').submit();
				});
				$("#borrowersUser").select2({
					placeholder : "请输入",
					minimumInputLength : 2,
					initSelection : function(element, callback) { // 初始化时设置默认值
						 callback({id:$('#borrowersUser').val(),text:'',val:$('#bUserName').val()});
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
				
		/* 		//计划还款时间小于项目发布时间验证
				 $("#plannedRepaymentDate").change( function() {
					var publishDt=$('#publishDt').val();
					var plannedRepaymentDate=$(this).val();
					var biddingDeadline=$('#biddingDeadline').val();
						if(plannedRepaymentDate<publishDt){
							$("#plannedRepaymentDate").attr("value","");
							$("#plannedRepaymentDate").after("<div id='hah' style='display:inline;color:red;'>计划还款时间不能小于项目发布时间</div>"); 
							$("#hah").fadeOut(2000);
						}
				});  */ 
				
				$("#agentUser").select2({
					placeholder : "请输入",
					minimumInputLength : 2,
					initSelection: function (element, callback) {
			             callback({id:$('#agentUser').val(),text:'',val:$('#aUserName').val()});
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
							var bResults = [];
							bResults.push({
								id : "账号编号",
								text : "登录名",
								val : "会员名称"
							});

							$.each(data, function(index, item) {
								bResults.push({
									id : item.accountId,
									text : item.accountName,
									val : item.customerBase.customerName
								});
							});
							return {
								results : bResults
							};

						} // 构造返回结果
					}
				});
				$("#inputForm")
						.validate(
								{
									ignore: "",
									submitHandler : function(form) {
										if($('#isRecommend').val()==''){
											$('#isRecommend').val('0');
										}
										if($('#isNewUser').val()==''){
											$('#isNewUser').val('0');
										}
										if($('#areaId').val()==''){
											$('#areaId').val('0');
										}
										//融资人不能为空
										var borrowersUser = $("#borrowersUser").val();
										if(borrowersUser == ''){
											$('#span_borrowersUser_error').html("融资人不能为空");
										}else{
											$('#span_borrowersUser_error').html("");
										}
										var error = $('#span_borrowersUser_error').text();
										if(error != '' && borrowersUser == ''){
											$('#span_borrowersUser_error').html("融资人不能为空");
										}else{
											$('#span_borrowersUser_error').html("");
											var filesImage = $("#filesImage").val();
											if(filesImage == ''){
												if(confirm('你确定不上传相关文件吗？')){
													loading('正在提交，请稍等...');
													form.submit();
									            }
											}else{
												loading('正在提交，请稍等...');
												form.submit();
											}
										}
									},
									rules: {
										projectCode: {
							                remote: {
							                    type: "get",
							                    url: "${ctx}/project/projectBaseInfo/checkDuplicate",
							                    data: {
							                    	 projectId:$('#id').val(),
							        				 projectName:'',
							        				 projectCode: function() {return $("#projectCode").val();}
							                    }
							                }
							            },
										projectName: {
							                remote: {
							                    type: "get",
							                    url: "${ctx}/project/projectBaseInfo/checkDuplicate",
							                    data: {
							                    	 projectId:$('#id').val(),
							        				 projectName:function() {return $("#projectName").val();},
							        				 projectCode:''
							                    }
							                }
							            },
							            biddingDeadline:{
							            	valiFinancingMaturity:true
							            },
							            projectDuration:{
							            	valiProjectDuration:true
							            }
							        },
							        messages: {
							        	projectCode: {remote:"项目编号已存在，请重新输入"},
							        	projectName: {remote:"项目名称已存在，请重新输入"},
							        	increaseInterestRate : {max : "不能超过年化率."},
							        	plannedRepaymentDate:{min:"计划还款时间不能小于项目发布时间"},
							        	financeMoney : {min : "本期融资金额不能小于最大投资金额."},
							        	maxAmount : {min : "最大投资金额不能小于起投金额.",max:"最大投资金额不能大于本期融资金额"},
							        	increaseInterestRate:{required:"请输入加息比例"}
							        },

									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									},
									ignore: ""
								});
			});
	
	
    //日期相关校验
	var dateUtils = new DateUtils();
	var maxDt = function(){
		var result = "";
		var endDt = $("#biddingDeadline").val()
		if(endDt!=null && endDt != ""){
			result = endDt
		}
		return result;
	};
	
	
	$(function(){
		var applySrcId = "${projectBaseInfo.applySrcId }";
		if(applySrcId == "") {//plannedRepaymentDate publishDt
			var biddingDeadline=$('#biddingDeadline').val();
			//计划还款日期
			$("#plannedRepaymentDate").click(function(){
				var projectDuration = $('#projectDuration').val() =='' ? 1 : $('#projectDuration').val();
				var difMonth = parseFloat(projectDuration);
				
				var biddingDeadline = $("#biddingDeadline").val();
				var biddingDeadlineDate = new Date(Date.parse(biddingDeadline.replace(/-/g, "/")));  
				var difMonths = parseFloat(biddingDeadline);
				var durationType = $("#durationType").val();
				if($("#repaymentMode").val()==1){
					if(durationType==2){//项目期限类型按月
						WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,isShowToday:false,minDate:dateUtils.addDaysAndMonths(biddingDeadlineDate,0,difMonth,"yyyy-MM-dd HH:mm:ss")});
					}else{
						WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,isShowToday:false,minDate:dateUtils.addDaysAndMonths(biddingDeadlineDate,difMonth,0,"yyyy-MM-dd HH:mm:ss")});
					}
					
				}
				else{
					WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,isShowToday:false,minDate:dateUtils.addDaysAndMonths(biddingDeadlineDate,0, 1,"yyyy-MM-dd HH:mm:ss")});
				}
				
				
			});
			//投标截止时间
			$("#biddingDeadline").click(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,isShowToday:false,minDate:$("#publishDt").val()});
				
				
				
			});
		}
		
		
		//发布日期限制
		$("#publishDt").click(function() {
			WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:dateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"),maxDate:maxDt()});
		});
		
		$("#projectDuration").change(function() {
			$("#plannedRepaymentDate").attr("value","");
		});
		

		
		
		$("#repaymentMode").change(function() {
			if($("#repaymentMode").val()==1){
				/* var projectDuration = $('#projectDuration').val() =='' ? 1 : $('#projectDuration').val();
				var difMonth = parseFloat(projectDuration)+ 1;
				var biddingDeadline = $("#biddingDeadline").val();
				var biddingDeadlineDate = new Date(Date.parse(biddingDeadline.replace(/-/g, "/")));  
				var min = dateUtils.addDaysAndMonths(biddingDeadlineDate,1, difMonth,"yyyy-MM-dd HH:mm:ss start");
				
				var plannedRepayment = $("#plannedRepaymentDate").val();
				var plannedRepaymentDate = new Date(Date.parse(plannedRepayment.replace(/-/g, "/"))); 
				
				alert();
				if(min.getTime()<plannedRepaymentDate.getTime()){} */
					$("#plannedRepaymentDate").attr("value","");
				
				
			}
		});
		
		//初始化校验
		$("#increaseInterestRate").attr("max", $("#annualizedRate").val());
		$("#financeMoney").attr("min", $("#maxAmount").val());
		$("#maxAmount").attr("min", $("#startingAmount").val());
		$("#maxAmount").attr("max", $("#financeMoney").val());
		
		
		//加息比例校验
		$("#increaseInterestRate").change(function() {
			$("#increaseInterestRate").attr("max", $("#annualizedRate").val());
		});
		//年华率校验
		$("#annualizedRate").change(function() {
			$("#increaseInterestRate").attr("max", $("#annualizedRate").val());
		});
		//融资金额校验
		$("#financeMoney").blur(function() {
			$("#financeMoney").attr("min", $("#maxAmount").val());
		});
		$("#financeMoney").change(function() {
			$("#financeMoney").attr("min", $("#maxAmount").val());
		});
		//最大投资金额校验
		$("#maxAmount").blur(function() {
			$("#maxAmount").attr("min", $("#startingAmount").val());
			$("#maxAmount").attr("max", $("#financeMoney").val());
		});
		$("#maxAmount").change(function() {
			$("#maxAmount").attr("min", $("#startingAmount").val());
			$("#maxAmount").attr("max", $("#financeMoney").val());
		});
		
		
		//起头金额校验
		$("#startingAmount").change(function() {
			$("#financeMoney").attr("min", $("#startingAmount").val());
			$("#maxAmount").attr("min", $("#startingAmount").val());
		});
		
		
		
		
		
		//最大投资金额校验
		//$("#biddingDeadline").change(function() {
			
		//});
		
	});
	
	
	//计算两个日期的相差天数
	function DateDiff(startDate, endDate){
	   var aDate, oDate1, oDate2, iDays ;
	   aDate = startDate.split('-');
	   oDate1 = new Date(aDate[1]+'-'+aDate[2]+'-'+aDate[0]) ;
	   aDate = endDate.split('-');
	   oDate2 = new Date(aDate[1]+'-'+ aDate[2] +'-'+aDate[0]);
	   iDays = parseInt(Math.abs(oDate1 -oDate2)/1000/60/60/24); //把相差的毫秒数转换为天数
	   return iDays ;
	}   
	
	function getDays(strDateStart,strDateEnd){
		   var strSeparator = "-"; //日期分隔符
		   var oDate1;
		   var oDate2;
		   var iDays;
		   oDate1= strDateStart.split(strSeparator);
		   oDate2= strDateEnd.split(strSeparator);
		   var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
		   var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
		   
		   var temp = strDateS - strDateE;
		   
		   iDays = (strDateS>strDateE?-1:1)*parseInt(Math.abs(temp ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数
		   return iDays ;
		}
	
	 function getDate(strDate) {
         var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
          function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
         return date;
     }
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	function StringToDate(DateStr)  
	{   
	  
	    var converted = Date.parse(DateStr);  
	    var myDate = new Date(converted);  
	    if (isNaN(myDate))  
	    {   
	        //var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';  
	        var arys= DateStr.split('-');  
	        myDate = new Date(arys[0],--arys[1],arys[2]);  
	    }  
	    return myDate;  
	}   
	
	jQuery.validator.addMethod("valiFinancingMaturity", function(value, element) {   
		
		wFinancingMaturity = value;
		
		var financingMaturity = value;
		var projectDuration = $("#projectDuration").val();
		
		return validatorProjectFinancingDate(financingMaturity,projectDuration,this.optional(element));     
	}, "投标截止时间<=债权募集结束时间-项目期限");  
	

	
	jQuery.validator.addMethod("valiProjectDuration", function(value, element) { 
		
		
		var financingMaturity = wFinancingMaturity;
		
		var projectDuration = value
		
		return validatorProjectFinancingDate(financingMaturity,projectDuration,this.optional(element));
		   
	}, "项目期限<=债权募集结束时间-投标截止时间");  
	
	
	function refreshForm2(){
		
		var creditId = $("#creditId").val();
		if(creditId != null){
			$.ajax({
				   type: "POST",
				   url: "${ctx}/credit/creditBaseInfo/getById",
				   data: "id="+creditId,
				   success: function(data){
					 if(data.id != null){
						 $("#ketou").text("剩余融资金额："+data.toRaiseMoney+"元");
					     $("#financeMoney").attr("max",data.toRaiseMoney);
					     
					     $("#creditDurationInfo").text("债权结束时间为:"+new DateUtils().formatDate(new Date(data.raiseEndDate), "yyyy年MM月dd日"));
					     credit = data;
					 }
					 
				   }
				});
		}
		
		
		
	}
	
	function validatorProjectFinancingDate(financingMaturity,projectDuration,optional){
		var result = true;
		
		if(financingMaturity != null && financingMaturity != "" && projectDuration != null && projectDuration != "" && credit != null && credit != ""){
			var day= 0;
			var months = 0;
			
			var durationType = $("#durationType").val();
			if(durationType == 1){
				day = parseInt(projectDuration);
			}
			else{
				months = parseInt(projectDuration);
			}


			
			var wprojectEndDate = getDate(dateUtils.addDaysAndMonths(getDate(financingMaturity),day, months,"yyyy-MM-dd HH:mm:ss").toString());
			var raiseEndDate = getDate(dateUtils.addDaysAndMonths(new Date(credit.raiseEndDate),1, 0,"yyyy-MM-dd HH:mm:ss").toString());;



			if(wprojectEndDate.getTime()>raiseEndDate.getTime()){
				
				result = false;
			}
		}
		
	    return optional || result;   
	}

	$(document).ready(function() {


		$("#projectDuration").change(function() {

			initProjectTips();
		});
		$("#durationType").change(function() {

			initProjectTips();
		});

		$("#isNewUser").change(function() {

			initProjectTips();
		});
	});

	function initProjectTips(){
		var isNewUser = $("#isNewUser").val();

		if(isNewUser == "0"){

			$("#projectTips").attr("value","新手专享，加息5%");
		}else{
			var projectDuration = $("#projectDuration").val();


			var day= 0;
			var months = 0;
			var durationType = $("#durationType").val();
			if(durationType == 1){
				day = parseInt(projectDuration);
			}
			else{
				months = parseInt(projectDuration);
			}

			var projectTips1 = "短期首选";
			var projectTips2 = "月月盈利";
			var projectTips3 = "季季收息";
			var projectTips4 = "双季稳赚";
			var projectTips5 = "年年有余";


			if(months == 1){
				$("#projectTips").attr("value",projectTips1);
			}else if(months == 2){
				$("#projectTips").attr("value",projectTips2);
			}else if(months == 3){
				$("#projectTips").attr("value",projectTips3);
			}else if(months == 6){
				$("#projectTips").attr("value",projectTips4);
			}else if(months == 12){
				$("#projectTips").attr("value",projectTips5);
			}else{
				if($("#projectTips").val() == "新手专享，加息5%" || $("#projectTips").val() == projectTips1  || $("#projectTips").val() == projectTips2
						|| $("#projectTips").val() == projectTips3  || $("#projectTips").val() == projectTips4   || $("#projectTips").val() == projectTips5 ){
					$("#projectTips").attr("value","");
				}
			}

		}








	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/project/projectBaseInfo/createlist">借贷产品列表</a></li>
		<li class="active"><a
			href="${ctx}/project/projectBaseInfo/createform?id=${projectBaseInfo.id}">${not empty projectBaseInfo.projectStatus ? '借贷产品查看' : '借贷产品创建'} </a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="projectBaseInfo"
		action="${ctx}/project/projectBaseInfo/create" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="bUser.customerName"  id="bUserName"/>
		<form:hidden path="aUser.customerName"  id="aUserName"/>
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">项目编号：</label>
			<div class="controls">
				<form:input path="projectCode" htmlEscape="false" maxlength="50"
					class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">关联债权：</label>
			<div class="controls">
				<sys:selectPanel idName="resultCreditId" textName="resultCreditName" idField="id" callbackOnSelected="refreshForm2()" url="${ctx}/credit/creditBaseInfo/list?creditStatus=0" path="creditId" title="选择所属债权" ></sys:selectPanel>
				<form:input path="creditId" htmlEscape="false" maxlength="50"
					class="input-xlarge  digits required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">项目类型：</label>
			<div class="controls">
				<form:select path="projectTypeCode" class="input-xlarge required" disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" onchange="projectTypeChange(this.value);">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('project_type_dict')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="projectName" htmlEscape="false" maxlength="200"
					class="input-xlarge required" id="projectName"/>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">项目区域位置：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" disabled="${empty projectBaseInfo.applySrcId ? '' : 'disabled'}"
					value="${projectBaseInfo.area.id}" labelName="area.name" 
					labelValue="${projectBaseInfo.area.name}" title="区域"
					url="/project/projectBaseInfo/treeData" cssClass="" allowClear="true"
					notAllowSelectParent="true" />
			</div>
		</div>



		<div class="control-group">
			<label class="control-label">项目概述：</label>
			<div class="controls">
				<form:textarea path="projectIntroduce" htmlEscape="false" rows="4" 
					maxlength="2048" class="input-xxlarge " />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">贷款用途：</label>
			<div class="controls">
				<form:input path="useMethod" readonly="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" htmlEscape="false" maxlength="100"
					class="input-xlarge required" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">融资人：</label>
			<div class="controls">
				<!-- 邦权资产 -->
				
				<form:hidden path="borrowersUser" disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" class="input-xlarge select2-container bigdrop required" id="borrowersUser" /><span id="span_borrowersUser_error" style="color:red;"></span>
				
				<input type="hidden" name="borrowersUser" value="3"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">融资代理人：</label>
			<div class="controls">
				<!-- 邦权资产 -->
				
				<form:hidden path="agentUser" disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" class="input-xlarge select2-container bigdrop" id="agentUser" />
				 
				<input type="hidden" name="agentUser" value="3"/>
			</div>
		</div>
		
		<div class="control-group">
		<div style="height: 1px; background-color: red; width: 800px;"></div>
		</div>
		
		<div class="control-group">
			<label class="control-label">本次授信金额：</label>
			<div class="controls">
			<input id="creditExtensionMoney" name="creditExtensionMoney" maxlength="10" class="input-xlarge  number required"   type="text"  value="<fmt:formatNumber value="${projectBaseInfo.creditExtensionMoney}" pattern="#0.00" />" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">本期融资金额：</label>
			<div class="controls">
				<input id="financeMoney" name="financeMoney"  type="text" min="${projectBaseInfo.maxAmount }" maxlength="10"  class="input-xlarge  number required" rereadonly ="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" value="<fmt:formatNumber value="${projectBaseInfo.financeMoney}" pattern="#0.00" />" />
				<br><font id="ketou" color="gray"></font>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
			<input id="startingAmount" name="startingAmount"  type="text"  min="100" maxlength="10"  class="input-xlarge digits required" rereadonly="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" value="<fmt:formatNumber value="${projectBaseInfo.startingAmount}" pattern="#0" />" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大投资金额：</label>
			<div class="controls">
			<input id="maxAmount" name="maxAmount" type="text" min="${projectBaseInfo.startingAmount }" max="${projectBaseInfo.financeMoney }" class="input-xlarge digits required" maxlength="10" rereadonly="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" value="<fmt:formatNumber value="${projectBaseInfo.maxAmount}" pattern="#0" />" />
			</div>
		</div>
		
		<div class="control-group">
		<div style="height: 1px; background-color: red; width: 800px;"></div>
		</div>
		<div class="control-group">
			<label class="control-label">项目期限类型：</label>
			<div class="controls">
				<form:select path="durationType" disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" onchange="isDurationTypeChange(this.value);" class="input-xlarge required">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictList('project_duration_type_dict')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目期限(日/月)：</label>
			<div class="controls">
				<form:input path="projectDuration" disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" htmlEscape="false" maxlength="3" max="48" min="1"
					class="input-xlarge  digits required" />
				<br><font id="creditDurationInfo" color="gray"></font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				<form:input path="annualizedRate"  readonly="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" htmlEscape="false" maxlength="5"
					minmore="0" max="1" class="input-xlarge  number required" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
				<form:select path="repaymentMode" disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" class="input-xlarge required">
					<form:option value="" label="" />
					<form:option value="1" label="到期还本付息" />
					<form:option value="2" label="先息后本" />
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
		<div style="height: 1px; background-color: red; width: 800px;"></div>
		</div>
	  
		<div class="control-group">
			<label class="control-label">项目发布日期：</label>
			<div class="controls">
				<input id="publishDt" name="publishDt" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${projectBaseInfo.publishDt}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">投标截止时间：</label>
			<div class="controls">
				<input id="biddingDeadline" name="biddingDeadline" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${projectBaseInfo.biddingDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
				
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">计划还款日期：</label>
			<div class="controls">
				<input id="plannedRepaymentDate" name="plannedRepaymentDate" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate required" 
					value="<fmt:formatDate value="${projectBaseInfo.plannedRepaymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
					/><div id="plannedRepaymentDates"></div>
			</div>
		</div>
		
		<div class="control-group">
		<div style="height: 1px; background-color: red; width: 800px;"></div>
		</div>
		
		<div class="control-group">
			<label class="control-label">是否新手项目：</label>
			<div class="controls">
				<form:select path="isNewUser" class="input-xlarge  required" onchange="isNewUserChange(this.value);">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictList('project_is_new_user_dict')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否推荐项目：</label>
			<div class="controls">
				<form:select path="isRecommend" class="input-xlarge  required">
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
				<form:select path="canUseRateTicket" class="input-xlarge  required">
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
				<form:select id="transferCode" path="transferCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_transfer_code')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">担保方式：</label>
			<div class="controls">
				<form:select path="safeguardMode" disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_safeguard_mode_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div id="extend_2" style="display:none;">
		    <div class="control-group">
				<label class="control-label">服务费收取策略：</label>
				<div class="controls">
					<form:select path="serviceChargeTypeCode" class="input-xlarge "  disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('project_service_charge_type_dict')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">服务费：</label>
				<div class="controls">
					<form:input path="serviceCharge"  readonly="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" htmlEscape="false" maxlength="8"
					class="input-xlarge  number" />
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">是否加息：</label>
			<div class="controls">
				<form:select path="isIncreaseInterest" disabled="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" class="input-xlarge required">
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
				<form:input path="increaseInterestRate"  readonly="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" htmlEscape="false" maxlength="5"
					min="0" max="1" class="input-xlarge  number " />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">提前还款费率(%)：</label>
			<div class="controls">
				<form:input path="earlyRepaymentRate"  minmore="0" max="1" readonly="${empty projectBaseInfo.applySrcId ? 'false' : 'true'}" htmlEscape="false" class="input-xlarge  number required" maxlength="5"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">项目提示：</label>
			<div class="controls">
				<form:textarea path="projectTips" htmlEscape="false" rows="2"
							   maxlength="200" class="input-xxlarge " />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">风控信息:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="riskInfo" rows="4" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/project/risk" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文件：</label>
			<div class="controls">
				<form:hidden id="filesImage" path="aboutFiles" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
				<sys:ckfinder input="filesImage" type="images" uploadPath="/project/att" selectMultiple="true" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">适用终端：</label>
			<div class="controls">
				<form:checkboxes path="showTermList" items="${fns:getDictList('op_term_dict')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
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
						maxlength="10" class="input-xlarge number" />
				</div>
			</div>
		</div> --%>
		
		
		
		
		
		<form:hidden path="projectStatus"  id="projectStatus" />
		<div class="form-actions">
			<c:choose>
				<c:when test="${projectBaseInfo.projectStatus=='0'}">
					<shiro:hasPermission name="project:projectBaseInfo:create">
					<input id="btnSave" class="btn btn-primary" type="button"
						value="创 建" id="saveBtn" />&nbsp;<input id="btnSubmit"
						class="btn btn-danger" type="button" value="创建并提交审批" id="" />&nbsp;</shiro:hasPermission>
				</c:when>
				<c:when test="${empty projectBaseInfo.projectStatus}">  
				 	<shiro:hasPermission name="project:projectBaseInfo:create">
					<input id="btnSave" class="btn btn-primary" type="button"
						value="创 建" id="saveBtn" />&nbsp;<input id="btnSubmit"
						class="btn btn-danger" type="button" value="创建并提交审批" id="" />&nbsp;</shiro:hasPermission>
 				 </c:when> 
				<c:otherwise >
		    		 <a href="#" class="btn  disabled btn-warning">项目提交后不能修改</a>
				</c:otherwise>
			</c:choose>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>