<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/util/bank_logo.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_yhkgl.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<title></title>
		<script type="text/javascript">
			function toBindBankCard() {
				$("#bindBankCardForm").submit();
				$("#popup").css("display", "block");
				checkHasReceivedMes();
			}
			

			//定时刷新检查是否收到易宝的通知
			function checkHasReceivedMes() {
				var targetUrl = "${pageContext.request.contextPath}/yeepay/gateWay/callback/hasReceivedMes?requestNo=${requestNo}";
				var interval = window.setInterval(function(){
					$.get(targetUrl,function(hasReceived){
						if(hasReceived) {
							window.location.href = "${ctxFront}/customer/account/bankCard";
							window.clearInterval(interval);
						}
					});
				},1000);
			}

			function closePop() {
				$("#popup").hide()
			}
		</script>
	</head>
	<body>
		<c:choose>
			<c:when test="${need_third_account_tip && not empty p2p:getPrincipal() && p2p:getCustomerAccount().hasOpenThirdAccount != '1'}">
				<div id="content_top" class="no_open">
					<img src="${ctxStatic}/modules/front/images/util/!_blue.png"/>
					<span class="span_text">您还未开通第三方托管账户， 开通后才能进行充值操作&nbsp;</span>
					<span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="花生金服采用全程第三方资金托管的方式，全程保障您的资金安全"></span>
					<div class="bt_orange_134x31" style="float:right;margin-right:30px;"><a href="${ctxFront }/customer/thirdAccount/open">马上开通</a></div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="bg_789_top"></div>
				<c:choose>
					<c:when test="${cardStatus eq 'UNBIND'}">
				<!-- 未绑定银行卡内容开始 -->
				<div class="wdzh-main">
					<div class="icon_card_area"><i class="icon_card"></i></div>
				    <div class="bdyhk_tips"><c:choose><c:when test="${hasOpenThirdAccount }">您还未在易宝支付绑定银行卡</c:when><c:otherwise>您还未开通易宝支付账号，请先开通</c:otherwise></c:choose></div>
				    <div class="btn_brown_158x38">
				    	<form id="bindBankCardForm" action="${bindBankCardUrl }" method="post" target="_blank">
				        	<textarea name="req" style="display:none;">${req }</textarea>
				        	<input type="hidden" name="sign" value="${sign }"/>
				        </form>
				        <c:choose><c:when test="${hasOpenThirdAccount }"><a href="javascript:;" onClick="toBindBankCard()">立即绑定</a></c:when>
				        <c:otherwise><a href="${ctxFront }/customer/thirdAccount/open">开通账号</a></c:otherwise>
				    	</c:choose>
				    </div>
				    <div class="bank_logo">
                    	<div class="bank_title">支持银行</div>
                        <ul class="clearfix">
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/BOC.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/ABC.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/ICBC.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CCB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/BOCO.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CMBCHINA.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CEB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/ECITIC.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/SPDB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CIB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/GDB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/HX.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CMBC.gif"></li>
                        </ul>
                    </div>
					<div class="brown_dividing_line"></div>
				    <div class="wdzh-content">
				        <div class="buttom-tips">
				        	<div class="tips-top"></div>
				            <div class="tips-center">
				            	<dl>
				                	<dt>为什么要在易宝支付绑定银行卡？</dt>
				                    <dd><span class="red-text">*</span>绑定银行卡后才能在易宝支付进行提现操作。 </dd>
				                </dl>
				            </div>
				        	<div class="tips-bottom"></div>
				        </div>
				    </div>
				</div>
				<!-- 未绑定银行卡内容结束 -->
					</c:when>
					
					<c:otherwise>
				<!-- 已绑定银行卡内容开始 -->
				<div class="wdzh-main">
				    <div class="bdyhk_tips">
				    <c:if test="${cardStatus eq 'VERIFIED' }">您已在易宝支付绑定银行卡</c:if>
				    <c:if test="${cardStatus eq 'VERIFYING' }">您绑定的银行卡正在认证中……</c:if>
				    </div>
				    <div class="xxqr_info ybdyhk_info">
				        <dl class="formList clearfix">
				            <dt>姓名：</dt>
				            <dd class="center">${customerAccount.customerBase.customerName }</dd>
				        </dl>
				        <dl class="formList clearfix">
				            <dt>卡号：</dt>
				            <dd class="center">${customerBankCard.cardNo }</dd>
				        </dl>
				        <dl class="formList clearfix">
				            <dt>银行：</dt>
				            <dd class="center">${fns:getDictLabel(customerBankCard.bankCode,'yeepay_bank_code_dict','')}</dd>
				        </dl>
				        
				        <!-- 
				        <dl class="formList clearfix">
				            <dt>绑定日期：</dt>
				            <dd class="center"><fmt:formatDate value="${customerBankCard.opDt }" pattern="yyyy-MM-dd"/></dd>
				        </dl>
				         -->
				    </div>
				    <div class="btn_brown_158x38">
				    	<c:if test="${customerBankCard.cardStatusCode eq 'VERIFIED'}">
				    	 
				    	<c:if test="${ unbindStatus != 'INIT'}">
					    	<form id="unbindBankCardForm" action="${unbindBankCardUrl }" method="post" target="_blank">
					        	<textarea name="req" style="display:none;">${req }</textarea>
					        	<input type="hidden" name="sign" value="${sign }"/>
					        </form>
					        
					    	<a href="${ctxFront}/customer/account/unbindBankCard"  onclick="return confirm('确认要解除绑定此银行卡吗？')">解除绑定</a>
				    	</c:if>
				    	<c:if test="${unbindStatus == 'INIT' }">
				    		<c:if test="${appointmentDate != null}">
				    			<font color=red>*&nbsp;您绑定的银行卡已预约解绑，
					    		将于<fmt:formatDate value="${appointmentDate}" pattern="yyyy年MM月dd日HH点mm分"/>之前生效。</font>
				    		</c:if>
				    		<c:if test="${appointmentDate == null}">
				    			<font color=red>*&nbsp;您绑定的银行卡已预约解绑，2个自然日自动生效。</font>
				    		</c:if>
				    		
				    	</c:if>
				    	<%----%>
				    	</c:if>
				    </div>
				    <div class="bank_logo">
                    	<div class="bank_title">支持银行</div>
                        <ul class="clearfix">
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/BOC.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/ABC.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/ICBC.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CCB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/BOCO.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CMBCHINA.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CEB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/ECITIC.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/SPDB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CIB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/GDB.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/HX.gif"></li>
                        	<li><img src="${ctxStatic}/modules/front/images/util/bank_logo/CMBC.gif"></li>
                        </ul>
                    </div>
					<div class="brown_dividing_line"></div>
				    <div class="wdzh-content">
				        <div class="buttom-tips">
				        	<div class="tips-top"></div>
				            <div class="tips-center">
				            	<dl>
				                	<dt>为什么要在易宝支付绑定银行卡？</dt>
				                    <dd><span class="red-text">*</span>绑定银行卡后才能在易宝支付进行提现操作。 </dd>
				                </dl>
				            </div>
				        	<div class="tips-bottom"></div>
				        </div>
				    </div>
				</div>
				<!-- 已绑定银行卡内容结束 -->
					</c:otherwise>
				</c:choose>
				
				<div class="bg_789_bottom"></div>
				
				<!--请前往新打开的页面完成充值 弹窗 默认为display:none，显示出来为display:block-->
				<div id="popup" class="pop_bg" style="display:none">
					<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
				    <div class="pop_main" style=" width:530px; height:178px; margin-left:-265px; margin-top:-79px;">
				        <div class="pop_title">请前往新打开的页面完成绑卡操作<a href="javascript:;" onClick="closePop()" class="close_pop"></a></div>
				        <div class="pop_content">
				            <div class="btn_group_one">
				                <a href="${ctxFront }/customer/account/bankCard" class="btn_brown_158x31">已完成绑卡</a>
				                <a href="${ctxFront }/customer/account/bankCard" class="btn_blue_158x31">绑卡遇到问题</a>
				            </div>
				        </div>
				    </div>
				</div>
			 </c:otherwise>
		</c:choose>
	</body>
</html>