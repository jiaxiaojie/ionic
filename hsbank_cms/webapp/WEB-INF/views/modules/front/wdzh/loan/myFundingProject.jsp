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
			function closeProjectDetail() {
				$("#projectDetail").hide();
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
		        <a href="${ctxFront }/customer/loan/myFundingProject"><span class="item"><span class="selected">募集中</span></span></a>
		        <a href="${ctxFront }/customer/loan/myRepayingProject"><span id="item_1" class="item"><span>还款中融资</span></span></a>
		        <a href="${ctxFront }/customer/loan/myEndedProject"><span id="item_2" class="item"><span>已还清融资</span></span></a>
		    </div>
		    
		    <!--募集中-->
		    <div id="record_list" class="show">
		        <table class="table table-hover">
		            <thead>
		                <tr>
		                    <th width="19%">项目名称</th>
		                    <th width="13%">融资金额</th>
		                    <th width="11%">期限</th>
		                    <th width="12%">服务费</th>
		                    <th width="12%">月费率</th>
		                    <th width="11%">月还款额</th>
		                    <th width="12%">还款总额</th>
		                    <th width="10%">操作</th>
		                </tr>
		            </thead>
		            <tbody>
						<c:forEach var="projectBaseInfo" items="${fundingProjectPage.list }" varStatus="status">
		                <tr>
		                    <td>${p2p:abbrev(projectBaseInfo.projectName,10) }</td>
		                    <td><fmt:formatNumber value="${projectBaseInfo.financeMoney }" pattern="##0.00" />元</td>
		                    <td>${projectBaseInfo.projectDuration }个月</td>
		                    <td><fmt:formatNumber value="${projectBaseInfo.serviceCharge }" pattern="##0.00" />元</td>
		                    <td>1%</td>
		                    <td><fmt:formatNumber value="${projectBaseInfo.monthRepayMoney }" pattern="##0.00" />元</td>
		                    <td><fmt:formatNumber value="${projectBaseInfo.repaymentMoney }" pattern="##0.00" />元</td>
		                    <td class="operate_area">
		                        <a href="javascript:;" onclick="showProjectDetail(${projectBaseInfo.projectId })" class="icon_xq" title="详情"></a>
		                        <c:choose>
		                        	<c:when test="${projectBaseInfo.isAutoRepay eq '1' }">
                        		<a href="javascript:;" onclick="" class="icon_zdhk_cg" title="已授权自动还款"></a>
		                        	</c:when>
		                        	<c:otherwise>
		                        		<c:choose>
		                        			<c:when test="${projectBaseInfo.autoRepayCode eq '1' }">
                        		<a href="${ctxFront }/customer/loan/authorizeAutoRepayment?projectId=${projectBaseInfo.projectId }" class="icon_zdhk" title="授权自动还款" target="_blank"></a>
		                        			</c:when>
		                        			<c:otherwise>
                        		<!-- <a href="javascript:void(0)" class="icon_zdhk" title="等待投标，暂不可授权自动还款"></a> -->
		                        			</c:otherwise>
		                        		</c:choose>
	                        	
		                        	</c:otherwise>
		                        </c:choose>
		                    </td>
		                </tr>
		                </c:forEach>
		            </tbody>
		        </table>
		        
		        <!-- 分页开始 -->
		        <div id="page_navigation"><div style="display:inline-block;">${fundingProjectPage.toStringFront() }</div></div>
		        <script type="text/javascript">
		        	function page(n,s) {
		        		var url = "${ctxFront}/customer/loan/myFundingProject?pageNo=" + n;
		        		window.location.href = url;
		        	}
		        </script>
		        <!-- 分页结束 -->
		        
		    </div>
		    <div class="div_height_50"></div>
		    <div class="bottom"></div>
		</div>
		
		<!--点击“详情”图标后弹出此窗口，弹窗默认为display:none，显示出来为display:block-->
		<div class="pop_bg" id="projectDetail" style="display:none">
		    <!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
		    <div class="pop_main" style=" width:690px; height:502px; margin-left:-345px; margin-top:-251px;">
		        <div class="pop_title">融资申请详情<a href="javascript:;" onclick="closeProjectDetail()" class="close_pop"></a></div>
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
		                            <dd id="financeMoney"><fmt:formatNumber value="${projectBaseInfo.financeMoney }" pattern="##0.00" />元</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>融资期限</dt>
		                            <dd id="projectDuration">${projectBaseInfo.projectDuration }个月</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>平台手续费</dt>
		                            <dd id="serviceCharge"><fmt:formatNumber value="${projectBaseInfo.serviceCharge }" pattern="##0.00" />元</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>合同金额</dt>
		                            <dd id="contractMoney"><fmt:formatNumber value="${projectBaseInfo.financeMoney + projectBaseInfo.serviceCharge }" pattern="##0.00" />元</dd>
		                        </dl>
		                        <dl class="item">
		                            <dt>还款合计</dt>
		                            <dd id="repaymentMoney"><fmt:formatNumber value="${projectBaseInfo.repaymentMoney }" pattern="##0.00" />元</dd>
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