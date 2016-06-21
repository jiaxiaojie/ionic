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
			//选中的现金券
			var seletedTicketArray = new Array();
			var seletedTicketIds = "";
			var seletedTicketAmount = 0.00;
			var maxTicketAmount = ${maxTicketAmount};
			//选中的加息券
			var seletedRateTicketIds = "";
		</script>
	</head>
	<body>
	<!--以下内容放入“<div class="juanzhou_middle"></div>”里面-->
	<div class="content980">
	<form id="inputForm" action="project_confirm" method="post">
	<input type="hidden" id="project_id" name="project_id" value="${projectBaseInfo.projectId }" />
	<input type="hidden" id="transfer_project_id" name="transfer_project_id" value="${transferProjectId}" />
	<input type="hidden" id="type" name="type" value="${type}" />
	<input type="hidden" id="amount" name="amount" value="${amount }" />
	<input type="hidden" id="ticketIds" name="ticketIds" value="" />
	<input type="hidden" id="rateTicketIds" name="rateTicketIds" value="" />
	<input type="hidden" id="ticketAmount" name="ticketAmount" value="" />
	<input type="hidden" id="platformAmount" name="platformAmount" value="${platformAmount}" />
	<input type="hidden" id="investmentType" name="investmentType" value="${investment_type}" />
	<div class="buy_1">
    	<div class="buy_list"><b>真实姓名</b><p>${p2p:vagueName(customerBase.customerName) }</p></div>
        <div class="buy_list"><b>身份证号</b><p>${p2p:vagueCertNum(customerBase.certNum) }</p></div>
        <div class="buy_list"><b>投资项目</b><p class="blue-text">${p2p:abbrev(projectBaseInfo.projectName,100)}</p></div>
        <div class="buy_list"><b>投资金额</b><p>${amount }</p></div>
        <div class="buy_list"><b>年化利率</b><p><fmt:formatNumber  value="${projectBaseInfo.annualizedRate * 100 }" pattern="#.##" />%</p></div>
        <div class="buy_list"><b>还款方式</b><p>${fns:getDictLabel(projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}</p></div>
        <div class="buy_list"><b>还款日期</b>
        	<p>
        	   <c:choose>
            	  <c:when test="${projectBaseInfo.repaymentMode eq '1' && repaymentPlan.size() > 0 }">
            	      <fmt:formatDate value="${repaymentPlan.get(0).getEndDate() }" pattern="yyyy-MM-dd"/>
            	  </c:when>
            	  <c:otherwise>
            	      <fmt:formatDate value="${projectBaseInfo.plannedRepaymentDate }" pattern="yyyy-MM-dd"/>
            	  </c:otherwise>
               </c:choose>
        	&nbsp;&nbsp;&nbsp;
        		<a id="bt_repayment_plan" href="javascript:void(0);" class="btn_text_blue">点击查看还款计划</a>
        	</p>
        </div>
    </div>
    
    <div class="buy_1">
    	<c:if test="${empty projectBaseInfo.isNewUser || projectBaseInfo.isNewUser eq '1'}">
    	<div class="buy_list"><b>现金券</b><p><a id="bt_ticket" href="javascript:void(0);" class="btn_text_blue">+选择现金券</a></p></div>
        </c:if>
        
        <div id="span_ticket_list" class="coupons_list" style="width:850px; margin:30px 0 30px 94px;display:none">
            <div class="dumascroll" style="width:798px; height:160px; margin:auto">
                <div id="div_selected_ticket" class="col clearfix"></div>
            </div>
        </div>
        <%-- <c:if test="${empty projectBaseInfo.canUseRateTicket || projectBaseInfo.canUseRateTicket eq '1'}">
    	<div class="buy_list"><b>加息券</b><p><a id="bt_rate_ticket" href="javascript:void(0);" class="btn_text_blue">+选择加息券</a></p></div>
        </c:if>
        
        <div id="span_rate_ticket_list" class="coupons_list" style="width:850px; margin:30px 0 30px 94px;display:none">
            <div class="dumascroll" style="width:798px; height:160px; margin:auto">
                <div id="div_selected_rate_ticket" class="col clearfix"></div>
            </div>
        </div> --%>
        <div class="buy_list">
        	<p class="ml94">
        		<!--选中的class为“selected”，未选中的class为“unselected”-->
        		<span class="checkbox_2" style="position:relative; top:3px; margin-right:5px;">
        			<i id="checkbox_platform_amount" class="selected"></i>
        		</span>使用可抵扣金额：
        		<span id="span_platform_amount">0.00</span>元
        		<span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="可抵扣金额为平台抵充用户充值费用的补贴金额，一般为投资金额的0.18%，上限为用户户充值产生的充值费用总和"></span>
        	</p>
        </div>
        <div class="buy_list">
        	<b>应付金额</b>
        	<p><span id="span_payable_amount">0.00</span></p>
        	<span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="应付金额为本次投资额减去可抵扣金额以及使用现金券的面额，为本次投资用户实际支出的金额"></span>
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
            	<c:choose>
            	  <c:when test="${type eq 1}">
            	    <a href="${ctxFront}/agreement/investment?projectId=${projectBaseInfo.projectId }&amount=${amount}" target="_blank" class="btn_text_blue2">《借款协议》</a></p>
            	  </c:when>
            	  <c:otherwise>
            	    <a href="${ctxFront}/agreement/transfer?transferProjectId=${transferProjectId}&type=1&amount=${amount}" target="_blank" class="btn_text_blue2">《借款协议》</a></p>
            	  </c:otherwise>
            	</c:choose>
        </div>
    </div>
    
    <div class="btn_group_one">
        <a id="bt_submit_invest" class="btn_brown_158x31">确认</a>
        <a href="javascript:void(0);" onclick="history.go(-1)" class="btn_blue_158x31">取消</a>
    </div>
    </form>  
</div>

	<!--还款计划 弹窗 默认为display:none，显示出来为display:block-->
	<div id="div_repayment_plan" class="pop_bg" style="display:none">
		<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
	    <div class="pop_main" style=" width:690px; height:520px; margin-left:-345px; margin-top:-260px;">
	        <div class="pop_title">还款计划表<a id="bt_close_repayment_plan" class="close_pop"></a></div>
	        <div class="pop_content">
	        
	        	<dl class="plan_1 clearfix">
	            	<dt>${p2p:abbrev(projectBaseInfo.projectName,100)}</dt>
	                <dd class="investment">投资金额：<span>${amount }</span>元</dd>
	                <dd class="earnings">投资到期可获得总收益：<span>${willProfit }</span>元</dd>
	            </dl>
	            
	        	<div class="plan_2">
	            	<div class="dumascroll" style="width:650px; height:324px; margin:auto">
	                	<div id="col">
	                    	<table style="border:0px;" class="table table-hover">
		                      	<thead>
			                        <tr>
			                            <th width="189">还款日期</th>
			                            <th width="160">还款金额（元）</th>
			                            <th width="149">应收本金（元）</th>
			                            <th width="134">应收利息（元）</th>
			                        </tr>
		                      	</thead>
		                      	<tbody>
		                      		<c:forEach var="repaymentPlanItem" items="${repaymentPlan}" varStatus="status">
										<tr>
											<td><fmt:formatDate value="${repaymentPlanItem.endDate}" pattern="yyyy-MM-dd"/></td>
											<td>${repaymentPlanItem.principalAndInterest}</td>
											<td><fmt:formatNumber  value="${repaymentPlanItem.principal}" pattern="#.##" /></td>
											<td>${repaymentPlanItem.interest}</td>
										</tr>
									</c:forEach>
		                      	</tbody>
	                    	</table>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<c:if test="${empty projectBaseInfo.isNewUser || projectBaseInfo.isNewUser eq '1'}">
	<!--选择现金券 弹窗 默认为display:none，显示出来为display:block-->
	<div id="div_ticket" class="pop_bg" style="display:none">
		<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
	    <div class="pop_main" style=" width:838px; height:530px; margin-left:-419px; margin-top:-260px;">
	        <div class="pop_title">最大可使用额度 ${maxTicketAmount} 元，已选择额度 <span id="seletedTicketAmount">0</span> 元 <a id="bt_close_ticket" class="close_pop"></a></div>
	        <div class="pop_content">
	        
	        	<div class="coupons_list">
	            	<div class="dumascroll" style="width:798px; height:376px; margin:auto">
	                	<div id="col clearfix" style="">
		                	<c:choose>
								<c:when test="${investmentTicketCount > 0}">
									<c:forEach var="investmentTicket" items="${investmentTicketList}">
				                    	<div class="item">
				                        	<div class="coupons clearfix">
				                        		<c:if test="${!investmentTicket.enabled}">
				                        			<!--不能用的现金券则灰掉-->
			                                		<div class="cant_use"><span>此现金券本次投资不可用！</span></div>
				                        		</c:if>
				                            	<div class="coupons_lt">
				                                	<div class="digital"><span>${investmentTicket.investmentTicketType.denomination}</span>元</div>
				                                    <p>使用条件：<span>${investmentTicket.investmentTicketType.useInfo}</span></p>
				                                    <p>到期时间：<span><fmt:formatDate value="${investmentTicket.invalidDt}" pattern="yyyy-MM-dd"/></span></p>
				                                </div>
				                                <div class="coupons_rt"><span>现 金 券</span></div>
				                            </div>
				                            <c:if test="${investmentTicket.enabled}">
					                            <div class="icon_checkbox">
					                            	<!--选中的class为“selected”，未选中的class为“unselected”-->
					                            	<span class="checkbox_1">
					                            		<i class="unselected" data-id="${investmentTicket.id}" 
					                            			data-denomination="${investmentTicket.investmentTicketType.denomination}"
					                            		    data-useInfo="${investmentTicket.investmentTicketType.useInfo}"
					                            		    data-invalidDt="<fmt:formatDate value="${investmentTicket.invalidDt}" pattern="yyyy-MM-dd"/>"></i>
					                            	</span>
					                            </div>
					                        </c:if>
				                        </div>
				                    </c:forEach>
								</c:when>
								<c:otherwise>
									 <!--没有可用现金券-->
			                        <div class="nocontent" style="display:block">
			                        	<div class="nocontent_box"></div>
			                            <div class="nocontent_text">没有现金券？去<a target="_blank" href="${ctxFront}/zxhd">最新活动</a>看看吧！</div>
			                        </div>
								</c:otherwise>
							</c:choose>
						</div>
	                </div>
	            </div>
	            
	            <div class="btn_group_one">
	            	<input type="hidden" id="max_platform_amount" value="${maxPlatformAmount}"/>
	                <a id="bt_ok_ticket" href="javascript:void(0);" class="btn_brown_158x31">确认</a>
	                <a id="bt_cancel_ticket" href="javascript:void(0);" class="btn_blue_158x31">取消</a>
	            </div>
	            
	        </div>
	    </div>
	</div>
	</c:if>
	
	<c:if test="${empty projectBaseInfo.canUseRateTicket || projectBaseInfo.canUseRateTicket eq '1'}">
	<!--选择加息券 弹窗 默认为display:none，显示出来为display:block-->
	<div id="div_rate_ticket" class="pop_bg" style="display:none">
		<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
	    <div class="pop_main" style=" width:838px; height:530px; margin-left:-419px; margin-top:-260px;">
	        <div class="pop_title"> <a id="bt_close_rate_ticket" class="close_pop"></a></div>
	        <div class="pop_content">
	        
	        	<div class="coupons_list">
	            	<div class="dumascroll" style="width:798px; height:376px; margin:auto">
	                	<div id="col clearfix" style="">
		                	<c:choose>
								<c:when test="${rateTicketCount > 0}">
									<c:forEach var="rateTicket" items="${rateTicketList}">
				                    	<div class="item">
				                        	<div class="coupons clearfix">
				                        		<c:if test="${!rateTicket.enabled}">
				                        			<!--不能用的加息券则灰掉-->
			                                		<div class="cant_use"><span>此加息券本次投资不可用！</span></div>
				                        		</c:if>
				                            	<div class="coupons_lt">
				                                	<div class="digital"><span>${rateTicket.rateTicketType.rate * 100}</span>%</div>
				                                    <p>天数限额：<span>${rateTicket.rateTicketType.rateDuration}天，${rateTicket.rateTicketType.maxAmount}元</span></p>
				                                    <p>到期时间：<span><fmt:formatDate value="${rateTicket.invalidDt}" pattern="yyyy-MM-dd"/></span></p>
				                                </div>
				                                <div class="coupons_rt"><span>加 息 券</span></div>
				                            </div>
				                            <c:if test="${rateTicket.enabled}">
					                            <div class="icon_checkbox">
					                            	<!--选中的class为“selected”，未选中的class为“unselected”-->
					                            	<span>
					                            		<input type="radio" name="radio" id="radio" value="${rateTicket.id }" onclick="getConfirmRateTicket('${rateTicket.id }');">
					                            	</span>
					                            </div>
					                        </c:if>
				                        </div>
				                    </c:forEach>
								</c:when>
								<c:otherwise>
									 <!--没有可用现金券-->
			                        <div class="nocontent" style="display:block">
			                        	<div class="nocontent_box"></div>
			                            <div class="nocontent_text">没有加息券？去<a target="_blank" href="${ctxFront}/zxhd">最新活动</a>看看吧！</div>
			                        </div>
								</c:otherwise>
							</c:choose>
						</div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	</c:if>
	
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
			//查看还款计划
			$(document).on('click', '#bt_repayment_plan', function() {
				$('#div_repayment_plan').toggle();
			});
			
			//关闭还款计划
			$(document).on('click', '#bt_close_repayment_plan', function() {
				$('#div_repayment_plan').toggle();
			});
			
			//选择现金券
			$("#bt_ticket").click(function() {
				$('#div_ticket').toggle();
				$('#seletedTicketAmount').html(seletedTicketAmount);
			});
			
			//选择现金券：关闭
			$(document).on('click', '#bt_close_ticket', function() {
				$('#div_ticket').toggle();
			});
			
			//选择现金券：确定
			$(document).on('click', '#bt_ok_ticket', function() {
				$("#div_selected_ticket").empty();
				//得到选中的现金券
				getSelectedTickets();
				//添加到选中列表
				var iLength = seletedTicketArray.length;
				var content = "";
				for (var i = 0; i < iLength; i++) {
					content += "<div id=\"ticket_item_" + seletedTicketArray[i].id + "\" class=\"item item124\">";
					content += "	<div class=\"delete_bg\"><a href=\"javascript:void(0);\" onclick=\"unseletTicket('" + seletedTicketArray[i].id + "')\">删除</a></div>";
					content += "	<div class=\"coupons clearfix\">";
					content += "		<div class=\"coupons_lt\">";
					content += "			<div class=\"digital\"><span>" + seletedTicketArray[i].denomination + "</span>元</div>";
					content += "			<p>使用条件：<span>" + seletedTicketArray[i].useInfo + "</span></p>";
					content += "			<p>到期时间：<span>" + seletedTicketArray[i].invalidDt + "</span></p>";
					content += "		</div>";
					content += "		<div class=\"coupons_rt\"><span>现 金 券</span></div>";
					content += "	</div>";
					content += "</div>";
			    }
				$('#div_selected_ticket').append(content);
				if(iLength > 0 ){
					$('#span_ticket_list').show();
				}else{
					$('#span_ticket_list').hide();
				}
				//计算本次投资的相关金额
				calculateInvestAmount(amount, seletedTicketAmount);
				//关闭选择现金券DIV
				$('#div_ticket').toggle();
			});
			
			//选择现金券：取消
			$(document).on('click', '#bt_cancel_ticket', function() {
				$('#div_ticket').toggle();
			});
			
			//选择现金券：选择或不选
			$(document).on('click', '.checkbox_1 i', function() {
				$(this).toggleClass("selected");
				$(this).toggleClass("unselected");
				//得到选中的现金券
				getSelectedTickets();
				if (seletedTicketAmount > maxTicketAmount) {
					//超过最大额度
					$(this).toggleClass("selected");
					$(this).toggleClass("unselected");
				} else {
					$('#seletedTicketAmount').html(seletedTicketAmount);
				}
			});
			
			
			/***********************************************************************************/
			//选择加息券
			$("#bt_rate_ticket").click(function() {
				$('#div_rate_ticket').toggle();
			});
			//选择加息券：关闭
			$(document).on('click', '#bt_close_rate_ticket', function() {
				$('#div_rate_ticket').toggle();
			});
			/***********************************************************************************/
			
			//使用可抵扣金额：选择或不选
			$(document).on('click', '#checkbox_platform_amount', function() {
				$(this).toggleClass("selected");
				$(this).toggleClass("unselected");
				//计算某次投资的相关金额
				calculateInvestAmount(amount, seletedTicketAmount);
			});
			
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
					$.jBox.alert("您必须同意借款协议，才能进行下一步操作！","提示");
				}else{
					if($('#checkbox_platform_amount').hasClass("unselected")){
						$("#platformAmount").val(0.00);
					}
					$("#ticketIds").val(seletedTicketIds);
					$("#rateTicketIds").val(seletedRateTicketIds);
					$("#ticketAmount").val(seletedTicketAmount);
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
		
		/**去掉选中的现金券*/
        function unseletTicket(id){
        	var iLength = seletedTicketArray.length;
			for (var i = 0; i < iLength; i++) {
				if (seletedTicketArray[i].id == id) {
					seletedTicketArray.splice(i,1);
					$("#ticket_item_" + id).remove();
					$(".checkbox_1").each(function(){
						if ($(this).children("i").attr("data-id") == id) {
							$(this).children("i").removeClass("selected");
							$(this).children("i").addClass("unselected")
						}
				    });
					break;
				}
			}
			seletedTicketAmount = 0;
			seletedTicketIds = "";
			var iLength = seletedTicketArray.length;
			for (var i = 0; i < iLength; i++) {
				seletedTicketAmount += parseFloat(seletedTicketArray[i].denomination);
				seletedTicketIds += seletedTicketIds == "" ? seletedTicketArray[i].id : "," + seletedTicketArray[i].id;
			}
			if(iLength > 0 ){
				$('#span_ticket_list').show();
			}else{
				$('#span_ticket_list').hide();
			}
			//计算本次投资的相关金额
			calculateInvestAmount(amount, seletedTicketAmount);
        }
		
		/**得到选中的现金券*/
		function getSelectedTickets() {
			seletedTicketArray = new Array();
			seletedTicketIds = "";
			seletedTicketAmount = 0;
			$(".checkbox_1").each(function(){
				if ($(this).children("i").hasClass("selected")) {
					//选中
					var ticketObj = new Object();
					ticketObj.id = $(this).children("i").attr("data-id");
					ticketObj.denomination = $(this).children("i").attr("data-denomination");
					ticketObj.useInfo = $(this).children("i").attr("data-useInfo");
					ticketObj.invalidDt = $(this).children("i").attr("data-invalidDt");
					seletedTicketArray.push(ticketObj)
					seletedTicketIds += seletedTicketIds == "" ? ticketObj.id : "," + ticketObj.id;
					seletedTicketAmount += parseFloat(ticketObj.denomination);
				}
		    });
		}
		
		//加息券确认
		function getConfirmRateTicket(value){
			$("#div_selected_rate_ticket").empty();
			$.ajax({
	 			type:"get",
				url:"${ctxFront}/getRateTicketInfo",
				data:{rateTicketId:value},
				dataType:"json",
				success:function(obj){
					var content = "";
					content += "<div id=\"rate_ticket_item_" + obj.id + "\" class=\"item item124\">";
					content += "	<div class=\"delete_bg\"><a href=\"javascript:void(0);\" onclick=\"unseletRateTicket('" + obj.id + "')\">删除</a></div>";
					content += "	<div class=\"coupons clearfix\">";
					content += "		<div class=\"coupons_lt\">";
					content += "			<div class=\"digital\"><span>" + obj.rate + "</span>%</div>";
					content += "			<p>天数限额：<span>" + obj.rateDuration + "天，" + obj.maxAmount + "元" + "</span></p>";
					content += "			<p>到期时间：<span>" + obj.invalidDt  + "</span></p>";
					content += "		</div>";
					content += "		<div class=\"coupons_rt\"><span>加 息 券</span></div>";
					content += "	</div>";
					content += "</div>";
					$('#div_selected_rate_ticket').html(content);
					//加息券ids
					seletedRateTicketIds = obj.id;
					if(obj !=null){
						$('#span_rate_ticket_list').show();
					}else{
						$('#span_rate_ticket_list').hide();
					}
					$('#div_rate_ticket').toggle();
				}
			});
		}
		
		/**去掉选中的加息券*/
        function unseletRateTicket(id){
        	$("#rate_ticket_item_" + id).remove();
			seletedRateTicketIds = "";
			$('#span_rate_ticket_list').hide();
        }
		
		/**
		 * 计算某次投资的相关金额
		 * @arg amount				投资金额
		 * @arg sumTicketAmount		现金券金额
		 */
		function calculateInvestAmount(amount, sumTicketAmount) {
			var usePlatformAmount = false;
			if($('#checkbox_platform_amount').hasClass("selected")){
				usePlatformAmount = true;
			}
			var investmentType = '${investment_type}';
			$.ajax({
	 			type:"get",
				url:"${ctxFront}/calculate_invest_amout",
				data:{investmentType:investmentType, amount:amount, sumTicketAmount:sumTicketAmount, usePlatformAmount:usePlatformAmount},
				dataType:"json",
				success:function(obj){
					//可用平台垫付金额
					$("#platformAmount").val(obj.platformAmount);
					$('#span_platform_amount').html(obj.platformAmount);
					//应付金额
					if (investmentType == '1') {
						//直投
						$('#span_payable_amount').html(obj.payableAmount);
					} else if (investmentType == '2') {
						//债权转让
						if (obj.downServiceCharge <= 0.00) {
							$('#span_payable_amount').html(obj.payableAmount + "元");
						} else {
							$('#span_payable_amount').html(obj.payableAmount + "元（其中包括转让手续费：" + obj.downServiceCharge + "元）");
						}
					}
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