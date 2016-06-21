<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/rzgl_jksqcx.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var dateUtils = new DateUtils();
			function recentWeek() {
				$("#startDateTime").val(dateUtils.addWeeks(new Date(), -1));
				$("#endDateTime").val(dateUtils.formatDate(new Date()));
				submit();
			}
			function lastMonth(months) {
				$("#startDateTime").val(dateUtils.addMonths(new Date(), months));
				$("#endDateTime").val(dateUtils.formatDate(new Date()));
				submit();
			}
			function submit() {
				$("#searchForm").submit();
			}
			$(function(){
				$("#status").val("${status}");
				$("#status").change(function(){ 
					$("#searchForm").submit();
				});
				$("#startDateTime").click(function(){
					WdatePicker({maxDate:$("#endDateTime").val()});
				});
				$("#endDateTime").click(function(){
					WdatePicker({minDate:$("#startDateTime").val()});
				});
			});
			function showDetail(id) {
				var url = "${ctxFront}/customer/loan/getProjectWillLoanById?id=" + id;
				$.getJSON(url,function(projectWillLoan){
					$("#detail_useType").html(projectWillLoan.useType);
					$("#detail_loanMoney").html(projectWillLoan.loanMoney + "元");
					$("#detail_duration").html(projectWillLoan.duration + "个月");
					$("#detail_serviceCharge").html(projectWillLoan.serviceCharge + "元");
					$("#detail_contractMoney").html(projectWillLoan.contractMoney + "元");
					$("#detail_sumRepayMoney").html(projectWillLoan.sumRepayMoney + "元");
					$("#detail_status").html(projectWillLoan.status);
					$("#detail_remark").html(projectWillLoan.remark);
					$("#detail_confirmRemark").html(projectWillLoan.confirmRemark);
					console.log(projectWillLoan.confirmRemark);
					$("#detail").show();
				});
			}
			function showConfirm(id) {
				$("#cancelProjectWillLoanId").val(id);
				$("#confirm").show();
			}
			function closePop(popId) {
				$("#cancelProjectWillLoanId").val(0);
				$("#" + popId).hide();
			}
			function cancelProjectWillLoan() {
				$("#confirm").hide();
				var url = "${ctxFront}/customer/loan/cancelProjectWillLoan?id=" + $("#cancelProjectWillLoanId").val();
				$.get(url,function(data){
					if(data == "success") {
						$("#searchForm").submit();
					}
				});
			}
		</script>
	</head>
	<body>
		<c:choose>
			<c:when test="${not empty p2p:getPrincipal() && p2p:getCustomerAccount().hasOpenThirdAccount != '1'}">
		<div id="content_top" class="bg_781x82">
			<img src="${ctxStatic}/modules/front/images/util/!.png"/>
			<span class="span_text">您还未开通第三方托管账户， 开通后才能进行融资操作&nbsp;</span>
			<span class="icon_tip" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="花生金服采用全程第三方资金托管的方式，全程保障您的资金安全"></span>
			<div class="bt_orange_134x31" style="float:right;margin-right:30px;"><a href="${ctxFront }/customer/thirdAccount/open">马上开通</a></div>
		</div>
			</c:when>
			<c:otherwise>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div class="line_01">
				<form id="searchForm" method="post" action="${ctxFront }/customer/loan/applyForQuery">
				<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo }"/>
					<span class="span_text_1">日期</span>
					<input id="startDateTime" style="cursor:pointer"  name="startDateTime" value="<fmt:formatDate value="${pageSearchBean.startDateTime }" pattern="yyyy-MM-dd"/>" readonly type="text" maxLength="10" class="input"/>
					<span class="span_text_2">至</span>
					<input id="endDateTime"  style="cursor:pointer"  name="endDateTime" value="<fmt:formatDate value="${pageSearchBean.endDateTime }" pattern="yyyy-MM-dd"/>" readonly type="text" maxLength="10" class="input"/>
					<div id="bt_query" onClick="submit()" class="bt_yellow_85x24" style="float:left;margin-top:8px;margin-left:10px;">查&nbsp;询</div>
					<span class="select_area fr ml80">
						<select id="status" name="status" class="w80 mb0">
							<option value="-1">全部</option>
							<option value="0">申请中</option>
							<option value="1">审核通过</option>
							<option value="2">被拒绝</option>
						</select>
					</span>
					<a href="javascript:void(0)" onClick="lastMonth(-6)">最近6个月</a>
					<a href="javascript:void(0)" onClick="lastMonth(-1)">最近1个月</a>
					<a href="javascript:void(0)" onClick="recentWeek()">最近1周</a>
				</form>
			</div>
			<div class="div_height_15"></div>
			<hr/>
			<div class="div_height_15"></div>
			<hr class="hr_dashed"/>
			<table class="table table-hover" style="width: 769px; ">
	            <thead>
	                <tr>
	                    <th width="18%">融资标题</th>
	                    <th width="15%">融资金额</th>
	                    <th width="10%">融资期限</th>
	                    <th width="10%">年利率</th>
	                    <th width="13%">审批状态</th>
	                    <th width="18%">审批信息</th>
	                    <th width="16%">操作</th>
	                </tr>
	            </thead>
	            <tbody>
					<c:forEach items="${page.list}" var="projectWillLoan">
	                <tr>
	                    <td>${p2p:abbrev(projectWillLoan.title,10)}</td>
	                    <td><fmt:formatNumber value="${projectWillLoan.loanMoney }" pattern="##0.00" />元</td>
	                    <td>${projectWillLoan.duration }月</td>
	                    <td><fmt:formatNumber value="${projectWillLoan.annualizedRate }" type="percent" maxFractionDigits="1" /></td>
	                    <td>${fns:getDictLabel(projectWillLoan.status,"project_will_loan_status_dict","" )}</td>
	                    <td>
							${p2p:abbrev(projectWillLoan.confirmRemark, 10) }
	                    </td>
	                    <td>
	                    	<a href="javascript:void(0);" onclick="showDetail(${projectWillLoan.id })">详情</a>
							<input type="hidden" id="cancelProjectWillLoanId" value="0"/>
							<c:if test="${projectWillLoan.status eq '0' }">&nbsp;｜&nbsp;<a href="javascript:void(0)" onclick="showConfirm(${projectWillLoan.id })">取消</a></c:if>
						</td>
	                </tr>
	                </c:forEach>
	            </tbody>
	        </table>
			
			<!-- 分页开始 -->
	        <div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
	        <script type="text/javascript">
	        	function page(n,s) {
	        		$("#pageNo").val(n);
					$("#searchForm").submit();
	        	}
	        </script>
	        <!-- 分页结束 -->
			
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
			</c:otherwise>
		</c:choose>
		<div class="bg_789_bottom"></div>
        
        <!--点击“详情”按钮弹出此窗口，弹窗默认为display:none，显示出来为display:block-->
        <div id="detail" class="pop_bg" style="display:none">
            <!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
            <div class="pop_main" style=" width:690px; height:502px; margin-left:-345px; margin-top:-251px;">
                <div class="pop_title">融资申请详情<a href="javascript:;" onclick="closePop('detail')" class="close_pop"></a></div>
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
                                    <dd id="detail_useType"></dd>
                                </dl>
                                <dl class="item">
                                    <dt>融资金额</dt>
                                    <dd id="detail_loanMoney"></dd>
                                </dl>
                                <dl class="item">
                                    <dt>融资期限</dt>
                                    <dd id="detail_duration"></dd>
                                </dl>
                                <dl class="item">
                                    <dt>平台手续费</dt>
                                    <dd id="detail_serviceCharge"></dd>
                                </dl>
                                <dl class="item">
                                    <dt>合同金额</dt>
                                    <dd id="detail_contractMoney"></dd>
                                </dl>
                                <dl class="item">
                                    <dt>还款合计</dt>
                                    <dd id="detail_sumRepayMoney"></dd>
                                </dl>
                                <dl class="item">
                                    <dt>审批状态</dt>
                                    <dd id="detail_status"></dd>
                                </dl>
                            </div>
                            <div class="jk_description">
                                <div class="jkd_title">融资描述</div>
                                <div class="jkd_text" id="detail_remark"></div>
                            </div>
                            <div class="jk_description">
                                <div class="jkd_title">审批信息</div>
                                <div class="jkd_text" id="detail_confirmRemark"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!--点击“取消”按钮后弹出此窗口，弹窗默认为display:none，显示出来为display:block-->
        <div id="confirm" class="pop_bg" style="display:none">
            <!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
            <div class="pop_main" style=" width:530px; height:178px; margin-left:-265px; margin-top:-79px;">
                <div class="pop_title">是否取消此申请？<a href="javascript:;" onclick="closePop('confirm')" class="close_pop"></a></div>
                <div class="pop_content">
                    <div class="btn_group_one">
                        <a href="javascript:;" onclick="cancelProjectWillLoan()" class="btn_brown_158x31">是</a>
                        <a href="javascript:;" onclick="closePop('confirm')" class="btn_blue_158x31">否</a>
                    </div>
                </div>
            </div>
        </div>
        
	</body>
</html>