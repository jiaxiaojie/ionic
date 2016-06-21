<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>债权投资用户信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				rules: {
					investorContactInfo: {
						isMobile: true
			        	
			        },
			        idNumber:{
			        	isIdCardNo:true,
			        	idNumberRemoteValid:true
			        },
			        bankCardNo:{
			        	isBankCardNo:true
			        }
				},
				messages: {
					creditName: {remote: "债权名称已存在"},
					idNumber:{remote:"此证件号已被使用"},
					idNumber:{remote:"所填证件号在"}
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
		
		jQuery.validator.addMethod("idNumberRemoteValid", function (value, element) {
			var result = true;
			$.ajax({
				type:"post",
				url:"${ctx}/credit/creditInvestUserInfo/checkIdNumber",
				async: false,
                data: {
               	 id:$('#id').val(),
               	 idNumber: function() {return $("#idNumber").val();},
               	 idType: function() {return $("#idType").val();}
               },
				success : function(data) {
					
					result = data;
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					//alert("出现异常");
				}
			});
	        return this.optional(element) || result;
	    }, "此证件号已被使用");
		
		// 手机号码验证
		jQuery.validator.addMethod("isMobile", function(value, element) {
			var length = value.length;
			var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
			return this.optional(element) || (length == 11 && mobile.test(value));
		}, "请正确填写您的手机号码");
		
		jQuery.validator.addMethod("isIdCardNo", function (value, element) {
	        return this.optional(element) || $("#idType").val()!=0 || (isIdCardNo(value) && $("#idType").val()==0);
	    }, "请正确输入您的身份证号码");
		
		jQuery.validator.addMethod("isBankCardNo", function (value, element) {
	        return this.optional(element) || luhmCheck(value);
	    }, "请正确输入您的银行卡号码");
		
		
		//增加身份证验证
		function isIdCardNo(num) {
		    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
		    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
		    var varArray = new Array();
		    var intValue;
		    var lngProduct = 0;
		    var intCheckDigit;
		    var intStrLen = num.length;
		    var idNumber = num;
		    // initialize
		    if ((intStrLen != 15) && (intStrLen != 18)) {
		        return false;
		    }
		    // check and set value
		    for (i = 0; i < intStrLen; i++) {
		        varArray[i] = idNumber.charAt(i);
		        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
		            return false;
		        } else if (i < 17) {
		            varArray[i] = varArray[i] * factorArr[i];
		        }
		    }

		    if (intStrLen == 18) {
		        //check date
		        var date8 = idNumber.substring(6, 14);
		        if (isDate8(date8) == false) {
		            return false;
		        }
		        // calculate the sum of the products
		        for (i = 0; i < 17; i++) {
		            lngProduct = lngProduct + varArray[i];
		        }
		        // calculate the check digit
		        intCheckDigit = parityBit[lngProduct % 11];
		        // check last digit
		        if (varArray[17] != intCheckDigit) {
		            return false;
		        }
		    }
		    else {        //length is 15
		        //check date
		        var date6 = idNumber.substring(6, 12);
		        if (isDate6(date6) == false) {
		            return false;
		        }
		    }
		    return true;
		}
		function isDate6(sDate) {
		    if (!/^[0-9]{6}$/.test(sDate)) {
		        return false;
		    }
		    var year, month, day;
		    year = sDate.substring(0, 4);
		    month = sDate.substring(4, 6);
		    if (year < 1700 || year > 2500) return false
		    if (month < 1 || month > 12) return false
		    return true
		}

		function isDate8(sDate) {
		    if (!/^[0-9]{8}$/.test(sDate)) {
		        return false;
		    }
		    var year, month, day;
		    year = sDate.substring(0, 4);
		    month = sDate.substring(4, 6);
		    day = sDate.substring(6, 8);
		    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
		    if (year < 1700 || year > 2500) return false
		    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
		    if (month < 1 || month > 12) return false
		    if (day < 1 || day > iaMonthDays[month - 1]) return false
		    return true
		}
		
		
		//Create Time:  07/28/2011
		//Operator:     刘政伟
		//Description:  银行卡号Luhm校验
		 
		//Luhm校验规则：16位银行卡号（19位通用）:
		 
		// 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
		// 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
		// 3.将加法和加上校验位能被 10 整除。
		 
		//方法步骤很清晰，易理解，需要在页面引用Jquery.js    
		 
		 
		//bankno为银行卡号 banknoInfo为显示提示信息的DIV或其他控件
		function luhmCheck(bankno){
		    var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）
		 
		    var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
		    var newArr=new Array();
		    for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
		        newArr.push(first15Num.substr(i,1));
		    }
		    var arrJiShu=new Array();  //奇数位*2的积 <9
		    var arrJiShu2=new Array(); //奇数位*2的积 >9
		     
		    var arrOuShu=new Array();  //偶数位数组
		    for(var j=0;j<newArr.length;j++){
		        if((j+1)%2==1){//奇数位
		            if(parseInt(newArr[j])*2<9)
		            arrJiShu.push(parseInt(newArr[j])*2);
		            else
		            arrJiShu2.push(parseInt(newArr[j])*2);
		        }
		        else //偶数位
		        arrOuShu.push(newArr[j]);
		    }
		     
		    var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
		    var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
		    for(var h=0;h<arrJiShu2.length;h++){
		        jishu_child1.push(parseInt(arrJiShu2[h])%10);
		        jishu_child2.push(parseInt(arrJiShu2[h])/10);
		    }        
		     
		    var sumJiShu=0; //奇数位*2 < 9 的数组之和
		    var sumOuShu=0; //偶数位数组之和
		    var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
		    var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
		    var sumTotal=0;
		    for(var m=0;m<arrJiShu.length;m++){
		        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
		    }
		     
		    for(var n=0;n<arrOuShu.length;n++){
		        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
		    }
		     
		    for(var p=0;p<jishu_child1.length;p++){
		        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
		        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
		    }      
		    //计算总和
		    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
		     
		    //计算Luhm值
		    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
		    var luhm= 10-k;
		     
		    if(lastNum==luhm){
		    
		    return true;
		    }
		    else{
		    
		    return false;
		    }        
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/credit/creditInvestUserInfo/list">债权投资用户信息列表</a></li>
		<li class="active"><a href="${ctx}/credit/creditInvestUserInfo/form?id=${creditInvestUserInfo.id}">债权投资用户信息<shiro:hasPermission name="credit:creditInvestUserInfo:edit">${not empty creditInvestUserInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:creditInvestUserInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="creditInvestUserInfo" action="${ctx}/credit/creditInvestUserInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">投资人姓名：</label>
			<div class="controls">
				<form:input path="investorName" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">联系方式(手机号)：</label>
			<div class="controls">
				<form:input path="investorContactInfo" htmlEscape="false" maxlength="11" minlength="11" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件类型：</label>
			<div class="controls">
				<form:select path="idType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('id_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">开户行：</label>
			<div class="controls">
				<form:select path="openingBank" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_bank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				<form:input path="bankCardNo" htmlEscape="false" maxlength="30" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
	
		<div class="control-group">
			<label class="control-label">证件地址：</label>
			<div class="controls">
				<form:textarea path="idAddress" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:creditInvestUserInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>