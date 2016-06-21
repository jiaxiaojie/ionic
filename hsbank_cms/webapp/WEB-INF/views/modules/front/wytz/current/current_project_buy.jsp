<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/wytz/project_buy.css?${version }" rel="stylesheet"/>
		<title></title>
		<link href="${ctxStatic}/modules/front/css/wdzh/zjgl_cz.css?${version }" rel="stylesheet"/>
		<script type="text/javascript">
			//本次投资金额
			var amount= ${amount};
			var seletedTicketAmount = 0.00;
		</script>
	</head>
	<body>
	<!--以下内容放入“<div class="juanzhou_middle"></div>”里面-->
	<div class="content980">
	<form id="inputForm" action="currentBuyConfirm" method="post">
	<input type="hidden" id="projectId" name="projectId" value="${cInfo.id }" />
	<input type="hidden" id="amount" name="amount" value="${amount }" />
	<div class="buy_1">
    	<div class="buy_list"><b>真实姓名</b><p>${p2p:vagueName(customerBase.customerName) }</p></div>
        <div class="buy_list"><b>身份证号</b><p>${p2p:vagueCertNum(customerBase.certNum) }</p></div>
        <div class="buy_list"><b>投资项目</b><p class="blue-text">${p2p:abbrev(cInfo.name,100)}</p></div>
        <div class="buy_list"><b>投资金额</b><p>${amount }</p></div>
        <div class="buy_list"><b>年化利率</b><p><fmt:formatNumber  value="${cInfo.rate * 100 }" pattern="#.##" />%</p></div>
        <div class="buy_list"><b>收益计算</b><p>按日计息</p></div>
    </div>
    
    <div class="buy_1">
        <div class="buy_list">
        	<b>应付金额</b>
        	<p><span id="span_payable_amount">0.00</span> 元</p>
        </div>
        <div class="buy_list">
        	<b>可用余额</b>
        	<p>
        		<span id="span_balance">0.00</span> 元
        		<span id="span_difference_amount"></span>
        	</p>
        </div>
    </div>
    
    <div class="buy_2">
    	<div class="code_area clearfix">
    		<b>验证码</b>
    		<div class="clearfix">
    			<div style="float:left">
    				<input type="text"  id="validateCode" name="validateCode" maxlength="5" placeholder="请输入验证码" title="请输入验证码" />
    			</div>
    			<div style="float:left">&nbsp;&nbsp;&nbsp;
    				<img id="vc_image" style="margin-bottom:10px;" src="${pageContext.request.contextPath}/servlet/validateCodeServlet" onClick="$('#vc_refresh').click();">
    				<a id="vc_refresh" href="javascript:void(0);" style="margin-bottom:10px;" onClick="$('#vc_image').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());">看不清</a>
    			</div>
    		</div>
    	</div>
    	<div class="code_area clearfix"><b></b><p>如遇流标情况，投标期内冻结的资金将在流标后解冻。</p></div>
        <div class="code_area clearfix">
        	<b></b>
            <p>
            	<!--选中的class为“selected”，未选中的class为“unselected”-->
            	<span class="checkbox_2" style="position:relative; top:3px; margin-right:5px;">
            		<i id="checkbox_agreement" class="selected"></i>
            	</span>我已阅读并同意签署 
            	<a href="${ctxFront}/agreement/currentInvestment?projectId=${cInfo.id}&amount=${amount}" target="_blank" class="btn_text_blue2">《活花生投资协议》</a></p>
        </div>
    </div>
    
    <div class="btn_group_one">
        <a id="bt_submit_invest" class="btn_brown_158x31">确认</a>
        <a href="javascript:void(0);" onclick="history.go(-1)" class="btn_blue_158x31">取消</a>
    </div>
    </form>  
</div>

	<!--请前往新打开的页面完成充值 弹窗 默认为display:none，显示出来为display:block-->
	<div id="div_charge_tip" class="pop_bg" style="display:none">
		<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
	    <div class="pop_main" style=" width:530px; height:178px; margin-left:-265px; margin-top:-79px;">
	        <div class="pop_title">请前往新打开的页面完成充值<a id="bt_close_charge_tip" class="close_pop"></a></div>
	        <div class="pop_content">
	            <div class="btn_group_one">
	                <a id="bt_already_recharge"  class="btn_brown_158x31">已完成充值</a>
	                <a href="javascript:void(0);" class="btn_blue_158x31">充值遇到问题</a>
	            </div>
	        </div>
	    </div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			//阅读并同意签署借款协议：选择或不选
			$(document).on('click', '#checkbox_agreement', function() {
				$(this).toggleClass("selected");
				$(this).toggleClass("unselected");
			});
			
			//马上充值
			$(document).on('click', '#bt_charge_tip', function() {
				$('#div_charge_tip').toggle();
			});
			
			//关闭马上充值
			$(document).on('click', '#bt_close_charge_tip', function() {
				$('#div_charge_tip').toggle();
			});
			
			//已完成充值
			$(document).on('click', '#bt_already_recharge', function() {
				//计算本次投资的相关金额
				calculateInvestAmount(amount, seletedTicketAmount);
				$('#div_charge_tip').toggle();
			});
			
			//表单检验
			$("#inputForm").validate({
				rules : {
					validateCode : {
						required : true,
						remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"
					}
				},
				messages : {
					validateCode : {
						required : "请输入验证码！",
						remote : "验证码不正确！"
					}
				}
			});
			
			//提交投资
			$(document).on('click', '#bt_submit_invest', function() {
				var payableAmount = $('#span_payable_amount').text();
				var goldBalance = $('#span_balance').text();
				if(parseFloat(payableAmount) > parseFloat(goldBalance)){
					$.jBox.alert("可用余额不足，请先充值！","提示");
				}else if($('#checkbox_agreement').hasClass("unselected")){
					$.jBox.alert("您必须同意活花生投资协议，才能进行下一步操作！","提示");
				}else{
					$("#inputForm").submit();
				}
			});
			
			//取消投资
			$(document).on('click', '#bt_cancel_invest', function() {
				window.location.href="${ctxFront}/wytz";
			});
			
			//计算本次投资的相关金额
			calculateInvestAmount(amount, seletedTicketAmount);
		});
		
		/**
		 * 计算某次投资的相关金额
		 * @arg amount				投资金额
		 * @arg sumTicketAmount		现金券金额
		 */
		function calculateInvestAmount(amount, sumTicketAmount) {
			var usePlatformAmount = false;
			var investmentType = '1';
			$.ajax({
	 			type:"get",
				url:"${ctxFront}/calculate_invest_amout",
				data:{investmentType:investmentType, amount:amount, sumTicketAmount:sumTicketAmount, usePlatformAmount:usePlatformAmount},
				dataType:"json",
				success:function(obj){
					//应付金额
					$('#span_payable_amount').html(obj.payableAmount);
					//当前用户余额
					$('#span_balance').html(obj.balance);
					//差额
					if (obj.differenceAmount > 0) {
						$('#span_difference_amount').html("，可用余额不足，还需<span id='span_difference_amount'>" + obj.differenceAmount + "</span>元 &nbsp;<a href='${ctxFront}/customer/capital/recharge' target='_blank' id='bt_charge_tip' class='btn_text_blue'>马上充值></a>");
					}else{
						$('#span_difference_amount').html("");
					}
				}
			});
		}
	</script>
</body>
</html>