<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/wytz/project_tzcg.css?${version }" rel="stylesheet"/>
		<title></title>
	</head>
	<body>
<!--以下内容放入“<div class="juanzhou_middle"></div>”里面-->
<div class="content980">

	<div class="tzcg">
    	<div class="tzcg_1"></div>
        
        <div class="tzcg_2">
        	<div class="buy_list"><b>真实姓名</b><p>${p2p:vagueName(investmentRecord.cb.customerName) }</p></div>
        	<div class="buy_list"><b>身份证号</b><p>${p2p:vagueCertNum(investmentRecord.cb.certNum) }</p></div>
        	<div class="buy_list"><b>投资项目</b><p><span class="blue-text">${p2p:abbrev(projectBaseInfo.projectName,100)}</span></p></div>
        	<div class="buy_list"><b>投资金额</b><p><span>${investmentRecord.amount }</span>元<c:if test="${investmentRecord.ticketAmount > 0 }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>使用了 ${investmentRecord.ticketAmount }元 现金券</span></c:if></p></div>
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
        	&nbsp;&nbsp;&nbsp;&nbsp;<a id="bt_repayment_plan" href="javascript:void(0);" class="btn_text_blue">点击查看还款计划</a></p></div>
        </div>
        
        <div class="btn_group_one" style="margin-bottom:80px;">
            <!--<a href="javascript:;" class="btn_brown_158x31">下载合同</a>-->
            <a href="${ctxFront }/customer/investment/project_cyz" class="btn_blue_158x31">查看我的债权</a>
        </div>
    </div>

</div>


<!--还款计划 弹窗 默认为display:none，显示出来为display:block-->
<div id="div_repayment_plan" class="pop_bg" style="display:none">
	<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
    <div class="pop_main" style=" width:690px; height:520px; margin-left:-345px; margin-top:-260px;">
        <div class="pop_title">还款计划表<a id="bt_close_repayment_plan" class="close_pop"></a></div>
        <div class="pop_content">
        
        	<dl class="plan_1 clearfix">
            	<dt>${fns:getDictLabel(projectBaseInfo.projectTypeCode, 'project_type_dict', '')} ${projectBaseInfo.projectCode }</dt>
                <dd class="investment">投资金额：<span>${investmentRecord.amount }</span>元</dd>
                <dd class="earnings">投资到期可获得总收益：<span>${investmentRecord.willProfit }</span>元</dd>
            </dl>
            
        	<div class="plan_2">
            	<div class="dumascroll" style="width:650px; height:324px; margin:auto">
                	<div id="col">
                    	<table style="border:0" class="table table-hover">
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
	});
</script>
</body>
</html>