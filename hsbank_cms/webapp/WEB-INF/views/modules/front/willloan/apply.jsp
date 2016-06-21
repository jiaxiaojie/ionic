<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="front_with_juanzhou" />
<link href="${ctxStatic}/modules/front/css/wyrz/apply.css?${version }"
	rel="stylesheet" />
<style>
.description{ position:relative;}
.apply_form .form_group label.error_tips {
	float: none;
	width: auto;
	display: inline-block;
	width: auto;
	color: red;
}

.apply_form .description textarea {
	width: 650px;
}
.apply_form .form_group label.remark_help{ position:absolute; top:-90px; left:0; padding:20px; display:none; margin:0; width:616px; height:48px; line-height:24px; text-align:left; background-color:#F9F6ED; border:1px solid #d0d0d0; box-shadow:0 -5px 12px rgba(0,0,0,.15); -o-box-shadow:0 5px 12px rgba(0,0,0,.15); -ms-box-shadow:0 -5px 12px rgba(0,0,0,.15); -moz-box-shadow:0 -5px 12px rgba(0,0,0,.15); -webkit-box-shadow: 0 -5px 12px rgba(0,0,0,.15);}
</style>
<title></title>
<script>
	function doSubmit() {
		if($('#checkbox_agreement').hasClass("unselected")){
			$.jBox.alert("您必须同意借款协议，才能进行下一步操作！","提示");
			return false;
		}
		var willLoanMoney = $('#loanMoney').val();
		var canLoanMoney = $('#canLoanMoney').val();
		var title = $('#title').val();

		if ($("#projectWillLoan").valid()) {
			var submit = function(v, h, f) {
				if (v == 1) {
					$('#wantLoanMoney').val(willLoanMoney);
					$('#projectWillLoan').submit();
				} else {
					jBox.tip("取消", 'info');
				}
				return true;
			};
			$.jBox.confirm("确认提交借款额度为" + willLoanMoney + "元的个人融资申请？", "操作提示",
					submit, {
						buttons : {
							'确定' : 1,
							'取消' : -1
						}
					});
		}

	}
	function doBack(){
		window.location="${ctxFront}/willloan/willloan"
		
	}
	function showHelp(){
		$('#remark_help').show();
	}
	function hideHelp(){
		$('#remark_help').hide();
	}
	function doBudgetary() {
		if ($("#projectWillLoan").valid()) {
			$.ajax({
				type : 'post',
				url : '${ctxFront}/willloan/willloan/budgetary',
				data : {
					loanMoney : $('#loanMoney').val(),
					terms : $('#duration').val()
				},
				dataType : 'json',
				success : function(data) {
					$('#serviceChargeShow').html(data.serviceCharge + "元");
					$('#monthRateShow').html(data.monthFeeRate + "%");
					$('#sumRepayShow').html(data.sumRepay + "元");
					$('#contractMoneyShow').html(data.contractMoney + "元");
					$('#monthRepayMoneyShow').html(data.monthRepay + "元");
					$('#serviceCharge').val(data.serviceCharge);
					$('#monthRate').val(data.monthFeeRate);
					$('#sumRepay').val(data.sumRepay);
					$('#contractMoney').val(data.contractMoney);
					$('#monthRepayMoney').val(data.monthRepay);
				}
			});
		}
	}
	$(function() {
		$("#projectWillLoan").validate({
			rules : {
				title : {
					required : true
				},
				remark : {
					required : true,
					minlength : 50,
					maxlength : 500
				},
				loanMoney : {
					required : true,
					amount : true
				}
			},
			messages : {
				title : {
					required : "申请标题不能为空."
				},
				remark : {
					required : "借款描述不能为空.",
					minlength : "借款描述不能少于50字.",
					maxlength : "借款描述不能多于500字."
				},
				loanMoney : {
					required : "借款金额不能为空.",
					amount : "借款金额必须为数字."
				}
			},
			errorClass : "error_tips",
			errorPlacement : function(error, element) {
				error.insertAfter(element)
			}
		});
		//阅读并同意签署协议：选择或不选
		$("#checkbox_agreement").click(function(){
			$(this).toggleClass("selected");
			$(this).toggleClass("unselected");
		});
	});
	
	function loanAgreement() {
		if($("#projectWillLoan").valid()) {
			var useType = $("#useType").val();
			var loanMoney = $("#loanMoney").val();
			var duration = $("#duration").val();
			var url = "${ctxFront}/agreement/willLoan?useType=" + useType + "&loanMoney=" + loanMoney + "&duration=" + duration;
			window.open(url);
		}
	}
</script>
</head>
<body>
	<div class="div_width_980">
		<div class="apply_page" id="wyrz_apply">
			<div class="step_box">
				<div class="step_text">
					<p>填写借款信息</p>
				</div>
			</div>
			<div class="apply_box">
				<form:form modelAttribute="projectWillLoan" class="apply_form"
					action="${ctxFront}/willloan/willloan/save">
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>真实姓名</label>
						<div class="">${p2p:vagueCertNum(projectWillLoan.createCustomer.customerName) }</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>身份证号</label>
						<div class="">${p2p:vagueCertNum(projectWillLoan.createCustomer.certNum) }</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>手机号码</label>
						<div class="">${projectWillLoan.createCustomer.mobile}</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>邮箱地址</label>
						<div class="">${projectWillLoan.createCustomer.email}</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>借款用途</label>
						<div class="">
							<form:select path="useType" id="useType">
								<form:options
									items="${fns:getDictList('customer_credit_loan_use_dict')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>借款金额</label>
						<div class="money_box">
							<input type="hidden" id="canLoanMoney"
								value="${projectWillLoan.canLoanMoney}" />
							<form:input path="loanMoney" htmlEscape="false" id="loanMoney"
								class="input-xlarge amount " onblur="doBudgetary();" min="100" maxlength="7"
								max="${projectWillLoan.canLoanMoneyStr}" />
							<br /> <span>根据您的信用评分，您在平台最多可以借款${projectWillLoan.creditLimit}元。您有申请中${projectWillLoan.applyingMoney}元,借贷中${projectWillLoan.loaningMoney}元，此次申请最多贷款${projectWillLoan.canLoanMoney}元。</span><a
								href="${ctxFront}/customer/account/authInfo">马上提升个人信用>></a>
						</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>申请标题</label>
						<div class="money_box">
							<form:input path="title" htmlEscape="false" class="input-xlarge"
								maxlength="30"  onblur="doBudgetary();"/>
						</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>借款期限</label>
						<div class="">
							<form:select path="duration" id="duration"
								onchange="doBudgetary()">
								<form:options
									items="${fns:getDictList('customer_credit_loan_term_dict')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>借款描述</label>
						<div class="description">
							<form:textarea path="remark" rows="10" cols="3" maxlength="500" onblur="doBudgetary();hideHelp();" onfocus="hideHelp();showHelp();"></form:textarea>
							<label id="remark_help" class="remark_help">  示例：本人为上海某知名外资企业中层管理人员，新买的房子需装修，每月有固定薪水，名下有上海市区无贷住房一套，福特翼虎一辆，均可作抵押担保，在其他信贷网站有过借款记录，且还款信用良好。</label>
						</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>平台手续费</label>
						<div class="" id="serviceChargeShow">${projectWillLoan.serviceCharge}元</div>
						<form:hidden path="serviceCharge" id="serviceCharge" />
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>合同金额</label>
						<div class="" id="contractMoneyShow">${projectWillLoan.contractMoney}元</div>
						<form:hidden path="contractMoney" id="contractMoney" />
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>月综合费率</label>
						<div class="" id="monthRateShow">${projectWillLoan.monthRate}%</div>
						<form:hidden path="monthRate" id="monthRate" />
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>月还款额</label>
						<div class="" id="monthRepayMoneyShow">${projectWillLoan.monthRepayMoney}元</div>
						<form:hidden path="monthRepayMoney" id="monthRepayMoney" />
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b>*</b>还款合计约</label>
						<div class="" id="sumRepayShow">${projectWillLoan.sumRepayMoney}元</div>
						<form:hidden path="sumRepayMoney" id="sumRepayMoney" />
					</div>
					<div class="form_group clearfix">
						<label for="" class=""><b></b></label>
						<div class="">
							<!--选中的class为“selected”，未选中的class为“unselected”-->
							<span class="checkbox_2"
								style="position: relative; top: 3px; margin-right: 5px;"><i id="checkbox_agreement"
								class="selected"></i></span>我已阅读并同意签署 <a href="javascript:void(0);" onClick="loanAgreement()" class="btn_text_blue2">《借款协议》</a>
						</div>
					</div>
					<div class="form_group clearfix">
						<label for="" class=""></label>
						<div class="btn_brown_158x38 tline mtb20 mlr10 pt20 floatnone">
							<a href="javascript:void(0);" onClick="doSubmit()">提交申请</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <a href="javascript:void(0);" onClick="doBack()"> 返回 </a>
						</div>
						
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>