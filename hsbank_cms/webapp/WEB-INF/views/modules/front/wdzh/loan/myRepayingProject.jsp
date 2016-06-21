<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/rzgl_wdjk.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			function showProjectDetail(projectId) {
				var url = "${ctxFront}/customer/loan/getProjectBaseInfoByProjectId?projectId=" + projectId;
				$.getJSON(url,function(projectBaseInfo){
					$("#useMethod").html(projectBaseInfo.useMethod);
					$("#financeMoney").html(projectBaseInfo.financeMoney + "元");
					$("#projectDuration").html(projectBaseInfo.projectDuration + "个月");
					$("#serviceCharge").html(projectBaseInfo.serviceCharge + "元");
					$("#contractMoney").html((projectBaseInfo.financeMoney + projectBaseInfo.serviceCharge) + "元");
					$("#repaymentMoney").html(projectBaseInfo.repaymentMoney + "元");
					$("#projectIntroduce").html(projectBaseInfo.projectIntroduce);
					$("#projectDetail").show();
				});
			}
			function showRepaymentPlan(projectId) {
				var url = "${ctxFront}/customer/loan/getProjectRepaymentPlanListByProjectId?projectId=" + projectId;
				$.getJSON(url,function(projectRepaymentPlanList){
					$("#repaymentPlan table tbody").empty();
					var tbody = "";
					for(var i=0; i<projectRepaymentPlanList.length; i++) {
						var projectRepaymentPlan = projectRepaymentPlanList[i];
						tbody += "<tr><td>" + projectRepaymentPlan.planDate + "</td>"
									+ "<td>" + projectRepaymentPlan.planMoney + "</td>"
									+ "<td>" + projectRepaymentPlan.principal + "</td>"
									+ "<td>" + projectRepaymentPlan.interest + "</td>"
									+ "<td>" + projectRepaymentPlan.status + "</td></tr>";
					}
					$("#repaymentPlan table").append(tbody);
				});
				$("#repaymentPlan").show();
			}
			function closePop(popId) {
				$("#" + popId).hide();
			}
		</script>
	</head>
	<body>
		<div class="statistics">
			<div class="s_top"></div>
			<div class="s_middle column_4  clearfix">
				<dl class="one border_right">
					<dt class=""><strong>融资总额</strong></dt>
					<dd class="font_color_red"><strong><fmt:formatNumber value="${customerBalance.sumLoan }" pattern="##0.00" />元</strong></dd>
				</dl>
				<dl class="two border_right">
					<dt class="">待还金额</dt>
					<dd class="font_color_red"><fmt:formatNumber value="${customerBalance.willLoan }" pattern="##0.00" />元</dd>
				</dl>
				<dl class="three border_right">
					<dt class="">30天内应还金额</dt>
					<dd class="font_color_red"><fmt:formatNumber value="${customerBalance.repayment30dWill }" pattern="##0.00" />元</dd>
				</dl>
				<dl class="three">
					<dt class="">逾期金额</dt>
					<dd class="font_color_red"><fmt:formatNumber value="${customerBalance.repaymentLateMoney }" pattern="##0.00" />元</dd>
				</dl>
			</div>
			<div class="s_bottom"></div>
		</div>
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		
		<div id="content_center" class="bg_789_middle">
		    <div id="tab_bar">
		        <a href="${ctxFront }/customer/loan/myFundingProject"><span class="item"><span>募集中</span></span></a>
		        <a href="${ctxFront }/customer/loan/myRepayingProject"><span id="item_1" class="item"><span class="selected">还款中融资</span></span></a>
		        <a href="${ctxFront }/customer/loan/myEndedProject"><span id="item_2" class="item"><span>已还清融资</span></span></a>
		    </div>
		    
		    <!-- 还款中融资 -->
		    <div id="record_list" class="show">
		        <div class="list_info clearfix">
		            <div class="fl"></div>
		            <div class="fr">正常<span class="bg_green color_block" title="绿色背景为正常"></span>&nbsp;&nbsp;&nbsp;&nbsp;逾期<span class="bg_red color_block" title="红色背景为逾期"></span></div>
		        </div>
		        <table class="table table-hover">
		            <thead>
		                <tr>
		                    <th width="17%">项目名称</th>
		                    <th width="15%">融资金额</th>
		                    <th width="15%">剩余期限</th>
		                    <th width="15%">月还款额</th>
		                    <th width="15%">还款总额</th>
		                    <th width="13%">已还款</th>
		                    <th width="10%">操作</th>
		                </tr>
		            </thead>
		            <tbody>
						<c:forEach var="projectBaseInfo" items="${repayingProjectPage.list }">
							<c:choose>
								<c:when test="${projectBaseInfo.pes.status eq '1' }">
						<tr class="bg_red">
								</c:when>
								<c:otherwise>
						<tr class="bg_green">
								</c:otherwise>
							</c:choose>
		                    <td>${p2p:abbrev(projectBaseInfo.projectName,10) }</td>
		                    <td><fmt:formatNumber value="${projectBaseInfo.financeMoney }" pattern="##0.00" />元</td>
		                    <td>${projectBaseInfo.pes.remainingTime }个月</td>
		                    <td><fmt:formatNumber value="${projectBaseInfo.monthRepayMoney }" pattern="##0.00" />元</td>
		                    <td><fmt:formatNumber value="${projectBaseInfo.repaymentMoney }" pattern="##0.00" />元</td>
		                    <td><fmt:formatNumber value="${projectBaseInfo.pes.endRepayMoney }" pattern="##0.00" />元</td>
		                    <td class="operate_area">
		                        <a href="javascript:void(0);" onclick="showProjectDetail(${projectBaseInfo.projectId })" class="icon_xq" title="详情"></a><!--点击弹出“详情”窗口-->
		                        <a href="javascript:void(0);" onclick="showRepaymentPlan(${projectBaseInfo.projectId })" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
		                    </td>
		                </tr>
		                </c:forEach>
		            </tbody>
		        </table>
		    </div>
	        <div id="page_navigation"><div style="display:inline-block;">${repayingProjectPage.toStringFront() }</div></div>
	        <script type="text/javascript">
	        	function page(n,s) {
	        		var url = "${ctxFront}/customer/loan/myRepayingProject?pageNo=" + n;
	        		window.location.href = url;
	        	}
	        </script>
		    
		    <div class="div_height_50"></div>
		    <div class="bottom"></div>
		</div>
		
		<!--点击“详情”图标后弹出此窗口，弹窗默认为display:none，显示出来为display:block-->
		<div class="pop_bg" id="projectDetail" style="display:none">
		    <!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
		    <div class="pop_main" style=" width:690px; height:502px; margin-left:-345px; margin-top:-251px;">
		        <div class="pop_title">融资申请详情<a href="javascript:;" onclick="closePop('projectDetail')" class="close_pop"></a></div>
		        <div class="pop_content">
		            <div class="dumascroll" style="width:650px; height:404px; margin:auto">
		                <div id="col">
		                    <div class="pop_field clearfix">
		                        <dl class="item">
		                            <dt>真实姓名</dt>
		                            <dd>${customerBase.customerName }</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>身份证号</dt>
		                            <dd>${p2p:vagueCertNum(customerBase.certNum) }</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>手机号码</dt>
		                            <dd>${customerBase.mobile }</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>邮箱地址</dt>
		                            <dd>${customerBase.email }</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>性别</dt>
		                            <dd>${fns:getDictLabel(customerBase.genderCode, 'sex', '')}</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>出生日期</dt>
		                            <dd>${customerBase.getBirthday() }</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>融资用途</dt>
		                            <dd id="useMethod">${projectBaseInfo.useMethod }</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>融资金额</dt>
		                            <dd id="financeMoney">${projectBaseInfo.financeMoney }元</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>融资期限</dt>
		                            <dd id="projectDuration">${projectBaseInfo.projectDuration }个月</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>平台手续费</dt>
		                            <dd id="serviceCharge">${projectBaseInfo.serviceCharge }元</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>合同金额</dt>
		                            <dd id="contractMoney">${projectBaseInfo.financeMoney + projectBaseInfo.serviceCharge }元</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>还款合计</dt>
		                            <dd id="repaymentMoney">${projectBaseInfo.repaymentMoney }元</dd>
		                        </dl>
		                    </div>
		                    <div class="jk_description">
		                        <div class="jkd_title">融资描述</div>
		                        <div id="projectIntroduce" class="jkd_text">${projectBaseInfo.projectIntroduce }</div>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		
		<!--点击“还款计划”图标后弹出此窗口-->
		<div class="pop_bg" id="repaymentPlan" style="display:none">
			<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
		    <div class="pop_main" style=" width:690px; height:472px; margin-left:-345px; margin-top:-236px;">
		        <div class="pop_title">还款计划表<a href="javascript:;" onclick="closePop('repaymentPlan')" class="close_pop"></a></div>
		        <div class="pop_content">
		        
		        	<dl class="plan_1 clearfix">
		                <dd class="investment">当账户余额充足时，系统到期会自动付款</dd>
		                <dd class="earnings"><a href="${ctxFront }/customer/capital/recharge" class="brown-text">充值</a></dd>
		            </dl>
		            
		        	<div class="plan_2">
		            	<div class="dumascroll" style="width:650px; height:324px; margin:auto">
		                	<div id="col">
	                    	<table border="0" class="table table-hover">
		                      <thead>
		                        <tr>
		                            <th width="141">还款日期</th>
		                            <th width="111">本息总计（元）</th>
		                            <th width="127">应收本金（元）</th>
		                            <th width="120">应收利息（元）</th>
		                            <th width="111">还款情况</th>
		                        </tr>
		                      </thead>
		                      <tbody>
		                        <tr>
		                            <td>2015-08-08</td>
		                            <td>113</td>
		                            <td>58</td>
		                            <td>113</td>
		                            <td>已还款</td>
		                        </tr>
		                      </tbody>
		                    </table>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		
		<div class="bg_789_bottom"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#tab_bar span').click(function(){
					if ($(this).children("span").hasClass("selected")) {
						return ;
					}
					$("#tab_bar span").each(function(){
				        if($(this).children("span").hasClass("selected")){
				            $(this).children("span").removeClass("selected");
				        }
				    });
					$(this).children("span").addClass("selected");
				});
			});
		</script>
	</body>
</html>