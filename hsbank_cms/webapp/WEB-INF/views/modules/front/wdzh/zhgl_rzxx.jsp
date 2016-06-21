<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_rzxx.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var submitButton = {"提交":1, "关闭":-1};
			var noSubmitButton = {"关闭":-1};
			//如果认证信息为认证中或者已认证，则只能查看提交的信息，否则显示提交按钮
			var baseInfoButton = submitButton;
			var identityInfoButton = submitButton;
			var incomeInfoButton = submitButton;
			var creditCardInfoButton = submitButton;
			var housingInfoButton = submitButton;
			var carInfoButton = submitButton;
			var addressInfoButton = submitButton;
			var educationInfoButton = submitButton;
			var creditReportInfoButton = submitButton;
			<c:if test="${customerCreditAuth.personCreditAuthCode=='1' || customerCreditAuth.personCreditAuthCode=='2'}">
			baseInfoButton = noSubmitButton;</c:if>
			<c:if test="${customerCreditAuth.identityCreditAuthCode=='1' || customerCreditAuth.identityCreditAuthCode=='2'}">
			identityInfoButton = noSubmitButton</c:if>
			<c:if test="${customerCreditAuth.incomeCreditAuthCode=='1' || customerCreditAuth.incomeCreditAuthCode=='2'}">
			incomeInfoButton = noSubmitButton</c:if>
			<c:if test="${customerCreditAuth.creditAuthCode=='1' || customerCreditAuth.creditAuthCode=='2'}">
			creditCardInfoButton = noSubmitButton</c:if>
			<c:if test="${customerCreditAuth.housingCreditAuthCode=='1' || customerCreditAuth.housingCreditAuthCode=='2'}">
			housingInfoButton = noSubmitButton</c:if>
			<c:if test="${customerCreditAuth.carCreditAuthCode=='1' || customerCreditAuth.carCreditAuthCode=='2'}">
			carInfoButton = noSubmitButton</c:if>
			<c:if test="${customerCreditAuth.addressCreditAuthCode=='1' || customerCreditAuth.addressCreditAuthCode=='2'}">
			addressInfoButton = noSubmitButton</c:if>
			<c:if test="${customerCreditAuth.educationCreditAuthCode=='1' || customerCreditAuth.educationCreditAuthCode=='2'}">
			educationInfoButton = noSubmitButton</c:if>
			<c:if test="${customerCreditAuth.creditReportAuthCode=='1' || customerCreditAuth.creditReportAuthCode=='2'}">
			creditReportInfoButton = noSubmitButton</c:if>
			$(function(){
				$("#baseInfo").click(function(){
					top.$.jBox.open("iframe:${ctxFront}/customer/account/authInfo/baseInfo","个人基本信息认证",700,600,{
						buttons:baseInfoButton, submit:function(v, h, f){
							if(v==1) {
								var baseInfoForm = h.find("iframe")[0].contentWindow.$("#baseInfoForm");
								if(!baseInfoForm.valid()) {
									return false;
								}
								baseInfoForm.submit();
								return false;
							}
						}
					});
				});

				$("#identityInfo").click(function(){
					top.$.jBox.open("iframe:${ctxFront}/customer/account/authInfo/identityInfo","身份认证",700,600,{
						buttons:identityInfoButton, submit:function(v, h, f){
							if(v==1) {
								var identityInfoForm = h.find("iframe")[0].contentWindow.$("#identityInfoForm");
								if(!identityInfoForm.valid()) {
									return false;
								}
								identityInfoForm.submit();
								return false;
							}
						}
					});
				});

				$("#incomeInfo").click(function(){
					top.$.jBox.open("iframe:${ctxFront}/customer/account/authInfo/incomeInfo","收入认证",700,600,{
						buttons:incomeInfoButton, submit:function(v, h, f){
							if(v==1) {
								var incomeInfoForm = h.find("iframe")[0].contentWindow.$("#incomeInfoForm");
								if(!incomeInfoForm.valid()) {
									return false;
								}
								incomeInfoForm.submit();
								return false;
							}
						}
					});
				});

				$("#creditCardInfo").click(function(){
					top.$.jBox.open("iframe:${ctxFront}/customer/account/authInfo/creditCardInfo","信用卡认证",700,600,{
						buttons:creditCardInfoButton, submit:function(v, h, f){
							if(v==1) {
								var creditCardInfoForm = h.find("iframe")[0].contentWindow.$("#creditCardInfoForm");
								if(!creditCardInfoForm.valid()) {
									return false;
								}
								creditCardInfoForm.submit();
								return false;
							}
						}
					});
				});

				$("#housingInfo").click(function(){
					top.$.jBox.open("iframe:${ctxFront}/customer/account/authInfo/housingInfo","房产证明认证",700,600,{
						buttons:housingInfoButton, submit:function(v, h, f){
							if(v==1) {
								var housingInfoForm = h.find("iframe")[0].contentWindow.$("#housingInfoForm");
								if(!housingInfoForm.valid()) {
									return false;
								}
								housingInfoForm.submit();
								return false;
							}
						}
					});
				});

				$("#carInfo").click(function(){
					top.$.jBox.open("iframe:${ctxFront}/customer/account/authInfo/carInfo","车辆证明认证",700,600,{
						buttons:carInfoButton, submit:function(v, h, f){
							if(v==1) {
								var carInfoForm = h.find("iframe")[0].contentWindow.$("#carInfoForm");
								if(!carInfoForm.valid()) {
									return false;
								}
								carInfoForm.submit();
								return false;
							}
						}
					});
				});

				$("#addressInfo").click(function(){
					top.$.jBox.open("iframe:${ctxFront}/customer/account/authInfo/addressInfo","居住地认证",700,600,{
						buttons:addressInfoButton, submit:function(v, h, f){
							if(v==1) {
								var addressInfoForm = h.find("iframe")[0].contentWindow.$("#addressInfoForm");
								if(!addressInfoForm.valid()) {
									return false;
								}
								addressInfoForm.submit();
								return false;
							}
						}
					});
				});

				$("#educationInfo").click(function(){
					top.$.jBox.open("iframe:${ctxFront}/customer/account/authInfo/educationInfo","学历认证",700,600,{
						buttons:educationInfoButton, submit:function(v, h, f){
							if(v==1) {
								var educationInfoForm = h.find("iframe")[0].contentWindow.$("#educationInfoForm");
								if(!educationInfoForm.valid()) {
									return false;
								}
								educationInfoForm.submit();
								return false;
							}
						}
					});
				});

				$("#creditReportInfo").click(function(){
					top.$.jBox.open("iframe:${ctxFront}/customer/account/authInfo/creditReportInfo","信用报告认证",700,600,{
						buttons:creditReportInfoButton, submit:function(v, h, f){
							if(v==1) {
								var creditReportInfoForm = h.find("iframe")[0].contentWindow.$("#creditReportInfoForm");
								if(!creditReportInfoForm.valid()) {
									return false;
								}
								creditReportInfoForm.submit();
								return false;
							}
						}
					});
				});
			});
		</script>
	</head>
	<body>
		<div id="content_top" class="bg_area_004">
			<div class="row_1">
				<div class="column_1">信用等级</div>
				<div class="column_2">信用总分</div>
				<div class="column_3">信用额度</div>
				<div class="column_4">可用额度</div>
			</div>
			<div class="row_2">
				<div class="column_1">${customerCreditAuth.creditLevel }</div>
				<div class="column_2"><fmt:formatNumber value="${customerCreditAuth.creditScore }" pattern="##0.0" /></div>
				<div class="column_3"><fmt:formatNumber value="${customerCreditAuth.creditLimit }" pattern="###.00" /></div>
				<div class="column_4"><fmt:formatNumber value="${canUseLimit }" pattern="###.00" /></div>
			</div>
		</div>
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div class="row_1">
				<div class="column_1"><strong>项目</strong></div>
				<div class="column_2"><strong>状态</strong></div>
				<!-- <div class="column_3">信用分数</div> -->
			</div>
			<hr/>
			<div class="div_height_5"></div>
			<div class="row_2">
				<div class="column_1">个人基本信息</div>
				<div class="column_2">${fns:getDictLabel(customerCreditAuth.personCreditAuthCode, 'customer_credit_auth', '')}</div>
				<div class="column_3"><div class="bt_yellow_97x23">
				<a href="javascript:void(0)" id="baseInfo">
				<c:choose>
				<c:when test="${customerCreditAuth.personCreditAuthCode=='1' || customerCreditAuth.personCreditAuthCode=='2'}">查看</c:when>
				<c:otherwise>提交资料</c:otherwise></c:choose>
				</a>
				</div></div>
			</div>
			<hr class="hr_dashed"/>
			<div class="row_2">
				<div class="column_1">身份证认证</div>
				<div class="column_2">${fns:getDictLabel(customerCreditAuth.identityCreditAuthCode, 'customer_credit_auth', '')}</div>
				<div class="column_3"><div class="bt_yellow_97x23">
				<a href="javascript:void(0)" id="identityInfo">
				<c:choose>
				<c:when test="${customerCreditAuth.identityCreditAuthCode=='1' || customerCreditAuth.identityCreditAuthCode=='2'}">查看</c:when>
				<c:otherwise>提交资料</c:otherwise></c:choose>
				</a>
				</div></div>
			</div>
			<hr class="hr_dashed"/>
			<div class="row_2">
				<div class="column_1">学历认证</div>
				<div class="column_2">${fns:getDictLabel(customerCreditAuth.educationCreditAuthCode, 'customer_credit_auth', '')}</div>
				<div class="column_3"><div class="bt_yellow_97x23">
				<a href="javascript:void(0)" id="educationInfo">
				<c:choose>
				<c:when test="${customerCreditAuth.educationCreditAuthCode=='1' || customerCreditAuth.educationCreditAuthCode=='2'}">查看</c:when>
				<c:otherwise>提交资料</c:otherwise></c:choose>
				</a>
				</div></div>
			</div>
			<hr class="hr_dashed"/>
			<div class="row_2">
				<div class="column_1">家庭情况</div>
				<div class="column_2">${fns:getDictLabel(customerCreditAuth.addressCreditAuthCode, 'customer_credit_auth', '')}</div>
				<div class="column_3"><div class="bt_yellow_97x23">
				<a href="javascript:void(0)" id="addressInfo">
				<c:choose>
				<c:when test="${customerCreditAuth.addressCreditAuthCode=='1' || customerCreditAuth.addressCreditAuthCode=='2'}">查看</c:when>
				<c:otherwise>提交资料</c:otherwise></c:choose>
				</a>
				</div></div>
			</div>
			<hr/>
			<div class="div_height_10"></div>
			<div class="row_2">
				<div class="column_1">个人收入</div>
				<div class="column_2">${fns:getDictLabel(customerCreditAuth.incomeCreditAuthCode, 'customer_credit_auth', '')}</div>
				<div class="column_3"><div class="bt_yellow_97x23">
				<a href="javascript:void(0)" id="incomeInfo">
				<c:choose>
				<c:when test="${customerCreditAuth.incomeCreditAuthCode=='1' || customerCreditAuth.incomeCreditAuthCode=='2'}">查看</c:when>
				<c:otherwise>提交资料</c:otherwise></c:choose>
				</a>
				</div></div>
			</div>
			<hr class="hr_dashed"/>
			<div class="row_2">
				<div class="column_1">房产认证</div>
				<div class="column_2">${fns:getDictLabel(customerCreditAuth.housingCreditAuthCode, 'customer_credit_auth', '')}</div>
				<div class="column_3"><div class="bt_yellow_97x23">
				<a href="javascript:void(0)" id="housingInfo">
				<c:choose>
				<c:when test="${customerCreditAuth.housingCreditAuthCode=='1' || customerCreditAuth.housingCreditAuthCode=='2'}">查看</c:when>
				<c:otherwise>提交资料</c:otherwise></c:choose>
				</a>
				</div></div>
			</div>
			<hr class="hr_dashed"/>
			<div class="row_2">
				<div class="column_1">车辆认证</div>
				<div class="column_2">${fns:getDictLabel(customerCreditAuth.carCreditAuthCode, 'customer_credit_auth', '')}</div>
				<div class="column_3"><div class="bt_yellow_97x23">
				<a href="javascript:void(0)" id="carInfo">
				<c:choose>
				<c:when test="${customerCreditAuth.carCreditAuthCode=='1' || customerCreditAuth.carCreditAuthCode=='2'}">查看</c:when>
				<c:otherwise>提交资料</c:otherwise></c:choose>
				</a>
				</div></div>
			</div>
			<hr class="hr_dashed"/>
			<div class="row_2">
				<div class="column_1">信用卡认证</div>
				<div class="column_2">${fns:getDictLabel(customerCreditAuth.creditAuthCode, 'customer_credit_auth', '')}</div>
				<div class="column_3"><div class="bt_yellow_97x23">
				<a href="javascript:void(0)" id="creditCardInfo">
				<c:choose>
				<c:when test="${customerCreditAuth.creditAuthCode=='1' || customerCreditAuth.creditAuthCode=='2'}">查看</c:when>
				<c:otherwise>提交资料</c:otherwise></c:choose>
				</a>
				</div></div>
			</div>
			<hr class="hr_dashed"/>
			<div class="row_2">
				<div class="column_1">信用报告认证</div>
				<div class="column_2">${fns:getDictLabel(customerCreditAuth.creditReportAuthCode, 'customer_credit_auth', '')}</div>
				<div class="column_3"><div class="bt_yellow_97x23">
				<a href="javascript:void(0)" id="creditReportInfo">
				<c:choose>
				<c:when test="${customerCreditAuth.creditReportAuthCode=='1' || customerCreditAuth.creditReportAuthCode=='2'}">查看</c:when>
				<c:otherwise>提交资料</c:otherwise></c:choose>
				</a>
				</div></div>
			</div>
			<hr/>
			<div class="div_height_10"></div>
			<div class="row_2">
				<div class="column_1">正常还款次数（+1分/次，加分间隔28天）</div>
				<div class="column_2">0次</div>
				<div class="column_3">&nbsp;</div>
				<!-- <div class="column_4">0</div> -->
			</div>
			<hr class="hr_dashed"/>
			<div class="row_2">
				<div class="column_1">逾期次数（-3分/次）</div>
				<div class="column_2">0次</div>
				<div class="column_3">&nbsp;</div>
			</div>
			<hr class="hr_dashed"/>
			<div class="row_2">
				<div class="column_1">严重逾期笔数（扣除全部信用分数）</div>
				<div class="column_2">0次</div>
				<div class="column_3">&nbsp;</div>
			</div>
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
	</body>
</html>