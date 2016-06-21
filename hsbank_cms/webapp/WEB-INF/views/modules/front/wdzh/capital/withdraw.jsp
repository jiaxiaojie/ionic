<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="decorator" content="my_account"/>
        <link href="${ctxStatic}/modules/front/css/util/bank_logo.css?${version }" rel="stylesheet"/>
        <link href="${ctxStatic}/modules/front/css/wdzh/zjgl_tx.css?${version }" rel="stylesheet"/>
        <title></title>
        <script type="text/javascript">
        	var freeWithdrawCount = "${customerBalance.freeWithdrawCount }";
        	var canWithdrawAmount = parseFloat('<fmt:formatNumber value="${customerBalance.goldBalance - customerBalance.congealVal }" pattern="#0.00"/>');
            $(function(){
                $("#amount").blur(function(){
                    changeSubAmount()
                });
                $("#checkboxBrown").click(function(){
                    if($("#checkboxBrown").hasClass("checkbox_selected_brown")) {
                    	$("#amount").attr("max", (canWithdrawAmount - 2) >= 0 ? (canWithdrawAmount - 2) : 0);
                        $("#checkboxBrown").removeClass("checkbox_selected_brown").addClass("checkbox_brown");
                        $("#useFreeWithdrawCount").val("0");
                    } else {
                    	$("#amount").attr("max", canWithdrawAmount);
                        $("#checkboxBrown").removeClass("checkbox_brown").addClass("checkbox_selected_brown");
                        $("#useFreeWithdrawCount").val("1");
                    }
                    changeSubAmount();
                });
                $("#inputForm").validate({
                	rules: {amount: {amount: true}},
                	messages: {amount: {max: "实际扣除金额大于可提现金额."}},
                	errorLabelContainer: "#tipsContainer"
                });
                if(parseInt(freeWithdrawCount) > 0) {
                	$("#checkboxBrown").click();
                }
            });
            //修改实际扣除金额数额
            function changeSubAmount() {
                if($("#amount").val() != "") {
                    var subAmount = parseFloat($("#amount").val()) + 2;
                    if($("#checkboxBrown").length > 0 && $("#checkboxBrown").hasClass("checkbox_selected_brown")) {
                    	$("#amount").attr("max", canWithdrawAmount);
                        subAmount = $("#amount").val();
                    } else {
                    	$("#amount").attr("max", (canWithdrawAmount - 2) >= 0 ? (canWithdrawAmount - 2) : 0);
                    }
                    $("#subAmount").html((new Number(subAmount)).toFixed(2));
                }
            }
            function submit() {
                $("#inputForm").submit();
            }
        </script>
    </head>
    <body>
        <c:choose>
			<c:when test="${need_third_account_tip && not empty p2p:getPrincipal() && p2p:getCustomerAccount().hasOpenThirdAccount != '1'}">
				<div id="content_top" class="bg_781x82">
					<img src="${ctxStatic}/modules/front/images/util/!.png"/>
					<span class="span_text">您还未开通第三方托管账户， 开通后才能进行充值操作&nbsp;</span>
					<span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="花生金服采用全程第三方资金托管的方式，全程保障您的资金安全"></span>
					<div class="bt_orange_134x31" style="float:right;margin-right:30px;"><a href="${ctxFront }/customer/thirdAccount/open">马上开通</a></div>
				</div>
			</c:when>
			<c:otherwise>
		        <div class="bg_789_top"></div>
		        <div id="content_center" class="bg_789_middle">
		            <div class="wdzh_right_title">
		                <span>提现</span>
		            </div>
		            <div class="div_height_10"></div>
		            <c:choose>
		                <c:when test="${customerBankCard.cardStatusCode eq 'VERIFIED' }">
		            <form id="inputForm" action="${ctxFront }/customer/capital/withdraw/sign" method="post">
		                <input type="hidden" id="useFreeWithdrawCount" name="useFreeWithdrawCount" value="0"/>
		                <div class="line_02">
		                    <span class="form_label"><span>银行卡</span><span class="required">*</span></span>
		                    <!-- <b class="bankLogo bank_paic" style="float:left;"></b> -->
		                    <span class="span_text_2" style="margin-left:20px;">${customerBankCard.cardNo }</span>
		                </div>
		                <div class="line_02">
		                    <span class="form_label"><span>可提现金额</span><span class="required">&nbsp;&nbsp;</span></span>
		                     	<span class="span_text_2"><span class="font_color_orange"><fmt:formatNumber value="${customerBalance.goldBalance - customerBalance.congealVal }" pattern="#0.00"/></span></span>
		                    <span class="span_text_2">元</span>
		                    <span class="span_text">（可提现金额=账户余额-冻结金额）</span>
		                </div>
		                <div class="line_02">
		                    <span class="form_label"><span>提现金额</span><span class="required">*</span></span>
		                    <input id="amount" name="amount" class="amount required" max="<fmt:formatNumber value='${customerBalance.goldBalance - customerBalance.congealVal }' pattern='#0.00'/>" type="text" maxlength="10"/>
		                    <span class="span_text_2">元（平台允许用户每日提现一次）</span>
		                    <div id="tipsContainer" style="width:270px; height:40px; display: inline-block; margin-left:5px;"></div>
		                </div>
		                <!-- 提现模式开始 -->
		                <!-- 
		                <div class="line_02">
		                    <span class="form_label"><span>提现模式</span><span class="required">*</span></span>
		                    <input name="withdrawType" type="radio" value="NORMAL" checked="checked" style="width:20px;" /><span class="span_text_2">正常提现，T+1 天到账</span>
		                    <input name="withdrawType" type="radio" value="URGENT" style="width:20px;" /><span class="span_text_2">加急提现，T+0 当日到账</span>
		                </div>
		                 -->
		                <!-- 提现模式结束 -->
		                <div class="line_02">
		                    <span class="form_label"><span>提现费用</span><span class="required">&nbsp;&nbsp;</span></span>
		                    <span class="span_text_2"><span class="font_color_orange">2</span></span>
		                    <span class="span_text_2">元/笔&nbsp;</span>
		                    
		                    <span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="第三方平台将收取每笔2元的提现费用，用户可以使用花生金服赠送的免费提现服务进行提现。每位注册用户首次投资平台将提供10次免费提现服务。"></span>
		                    
		                </div>
		                <c:if test="${customerBalance.freeWithdrawCount > 0 }">
		                <div class="line_03">
		                    <div id="checkboxBrown" class="checkbox_brown" style="padding-top:1px;"></div>
		                    <span class="span_text">使用提现券（提现券剩余张数：${customerBalance.freeWithdrawCount }）</span>
		                </div>
		                </c:if>
		                <div class="line_02">
		                    <span class="form_label"><span>实际扣除金额</span><span class="required">&nbsp;&nbsp;</span></span>
		                    <span class="span_text_2"><span class="font_color_orange" id="subAmount">0.00</span></span>
		                    <span class="span_text_2">元</span>
		                </div>
		                <!-- <div class="line_02">
		                    <span class="form_label"><span>预计到账日期</span><span class="required">&nbsp;&nbsp;</span></span>
		                    <span class="span_text_2">&nbsp;&nbsp;&nbsp;2015-07-22&nbsp;&nbsp;&nbsp;</span>
		                    <span class="span_text_2">1-2个工作日（节假日、双休日除外）之内到账</span>
		                </div> -->
		                <div class="div_height_30"></div>
		                <div class="bt_brown_134x31" style="margin-left:180px;" onClick="submit()">提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现</div>
		                <div class="div_height_50"></div>
		                <!--<div id="bottom" class="bg_660x212">
		                    <div class="line_01">
		                        <img src="${ctxStatic}/modules/front/images/util/msg.png"/>
		                        <span class="span_text_1">温馨提示&nbsp;</span>
		                    </div>
		                    <div class="line_02">
		                        <span class="span_text">1.请确保您输入的提现金额，以及银行账户信息准确无误；</span>
		                    </div>
		                    <div class="line_02">
		                        <span class="span_text">2.如果您填写的提现信息不正确可能会导致提现失败，由此产生的提现费用将不予返还；</span>
		                    </div>
		                    <div class="line_02">
		                        <span class="span_text">3.双休日和法定节假日期间申请提现，会在下一个工作日处理。由此造成的不便，请多多谅解！</span>
		                    </div>
		                    <div class="line_02">
		                        <span class="span_text">4.平台禁止洗钱、信用卡套现、虚假交易等行为，一经发现并确认，将终止该账户的使用。</span>
		                    </div>
		                </div>-->
                        <div class="buttom-tips">
				        	<div class="tips-top"></div>
				            <div class="tips-center">
				            	<dl>
				                	<dt>温馨提示</dt>
				                    <dd></dd>
				                    <dd>1.请确保您输入的提现金额，以及银行账户信息准确无误；</dd>
				                    <dd>2.如果您填写的提现信息不正确可能会导致提现失败，由此产生的提现费用将不予返还；</dd>
				                    <dd>3.双休日和法定节假日期间申请提现，会在下一个工作日处理。由此造成的不便，请多多谅解！</dd>
				                    <dd>4.平台禁止洗钱、信用卡套现、虚假交易等行为，一经发现并确认，将终止该账户的使用。</dd>
				                </dl>
				            </div>
				        	<div class="tips-bottom"></div>
				        </div>
                        
		            </form>
		                </c:when>
		                <c:when test="${customerBankCard.cardStatusCode eq 'VERIFYING' }"></c:when>
		                <c:otherwise>您还未绑定银行卡<div class="bt_orange_134x31" style="display:inline-block;margin-left:20px;"><a href="${ctxFront }/customer/account/bankCard">请先绑卡</a></div></c:otherwise>
		            </c:choose>
		        </div>
		        <div class="bg_789_bottom"></div>
		    </c:otherwise>
		</c:choose>
    </body>
</html>