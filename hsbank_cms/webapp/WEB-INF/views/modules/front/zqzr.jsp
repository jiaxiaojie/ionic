<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/wytz.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var pageNo = ${page.pageNo};
			var remainAmount = '${projectTransferSearchBean.remainAmount}';
			var minAmount = '${projectTransferSearchBean.minAmount}';
			var maxAmount = '${projectTransferSearchBean.maxAmount}';
			var remainDuration = '${projectTransferSearchBean.remainDuration}';
			var rate = '${projectTransferSearchBean.rate}';
			var repaymentMode = '${projectTransferSearchBean.repaymentMode}';
			$(function(){
				$("#remainAmount li[value='${projectTransferSearchBean.remainAmount}']").addClass('active');
				$("#remainDuration li[value='${projectTransferSearchBean.remainDuration}']").addClass('active');
				$("#rate li[value='${projectTransferSearchBean.rate}']").addClass('active');
				$("#repaymentMode li[value='${projectTransferSearchBean.repaymentMode}']").addClass('active');
				
				$("ul li").click(function(){
					$(this).siblings().removeClass('active').end().addClass('active');
				});
				
				$("#remainAmount li").click(function(){
					$("#minAmount").val("");
					$("#maxAmount").val("");
					remainAmount = $(this).val();
					searchProject();
				});
				
				$("#remainDuration li").click(function(){
					remainDuration = $(this).val();
					searchProject();
				});
				
				$("#rate li").click(function(){
					rate = $(this).val();
					searchProject();
				});
				
				$("#repaymentMode li").click(function(){
					repaymentMode = $(this).val();
					searchProject();
				});
			});
			function page(n,s){
				pageNo = n;
				searchProject();
			}
			function searchProjectWithAmount() {
				remainAmount = "-1";
				searchProject();
			}
			function searchProject() {
				window.location.href = "${ctxFront}/projectTransfer/list?remainAmount=" + remainAmount + "&remainDuration=" + remainDuration + "&rate="
					+ rate + "&repaymentMode=" + repaymentMode + "&minAmount=" + $("#minAmount").val() + "&maxAmount=" + $("#maxAmount").val() + "&pageNo=" + pageNo;
			}
		</script>
	</head>
	<body>
		<div class="div_width_980">
			<div id="condition_top"></div>
			<div id="condition_middle">
				<div class="div_height_10"></div>
				<div id="tab_bar" style="padding-left:20px;">
					<a href="${ctxFront }/wytz"><span class="item"><span class="">项目列表</span></span></a>
					<a href="${ctxFront }/projectTransfer/list"><span class="item"><span class="selected">债权转让</span></span></a>
					<a href="${ctxFront}/current/toFirstTenderingCurrentProject"><span class="item"><span>活期产品</span></span></a>
					
				</div>
				<div id="condition">
					<div class="line_1">
	                    <label>可投金额：</label>
	                    <ul id="remainAmount">
	                        <li value="0" data-href="#" class="item ">全部</li>
	                        <li value="1" data-href="#" class="item ">1万元以下</li>
	                        <li value="2" data-href="#" class="item ">1万 - 5万</li>
	                        <li value="3" data-href="#" class="item ">5万 - 20万</li>
	                        <li value="4" data-href="#" class="item ">20万以上</li>
	                    </ul>
                        <div class="enter_amount">
                        	<p class="ea_text">直接输入</p>
                        	<p class="ea_input"><input id="minAmount" name="minAmount" type="text" value="${projectTransferSearchBean.minAmount }" /></p>
                        	<p class="ea_text">元</p>
                        	<p class="ea_text ml0">~</p>
                        	<p class="ea_input"><input id="maxAmount" name="maxAmount" type="text" value="${projectTransferSearchBean.maxAmount }" /></p>
                        	<p class="ea_text">元</p>
                        	<p class="ea_btn"><a href="javascript:;" onclick="searchProjectWithAmount()">确认</a></p>
                        </div>
	                </div>
	                <div class="line_1">
	                    <label>年化利率：</label>
	                    <ul id="rate">
	                        <li value="0" data-href="#" class="item ">全部</li>
	                        <li value="1" data-href="#" class="item ">9%以下</li>
	                        <li value="2" data-href="#" class="item ">9 - 10%</li>
	                        <li value="3" data-href="#" class="item ">10 - 11%</li>
	                        <li value="4" data-href="#" class="item ">11 - 12%</li>
	                        <li value="5" data-href="#" class="item ">12 - 13%</li>
	                        <li value="6" data-href="#" class="item ">13 - 14%</li>
	                        <li value="7" data-href="#" class="item ">14%以上</li>
	                    </ul>
	                </div>
	                <div class="line_1">
	                    <label>剩余期限：</label>
	                    <ul id="remainDuration">
	                        <li value="0" data-href="#" class="item ">全部</li>
	                        <li value="1" data-href="#" class="item ">6个月以下</li>
	                        <li value="2" data-href="#" class="item ">6-12个月</li>
	                        <li value="3" data-href="#" class="item ">12个月以上</li>
	                    </ul>
	                </div>
	                <div class="line_1">
	                    <label>还款方式：</label>
	                    <ul id="repaymentMode">
	                        <li value="0" data-href="#" class="item ">全部</li>
	                        <li value="1" data-href="#" class="item ">到期还本付息</li>
	                        <li value="2" data-href="#" class="item ">先息后本</li>
	                        <li value="3" data-href="#" class="item ">等额本息</li>
	                    </ul>
	                </div>
				</div>
			</div>
			<div id="condition_bottom"></div>
			<div class="div_height_20"></div>
        	<div class="line_dotted"></div>
       		<div id="project_list">
       			<c:if test="${empty page.list }">
       				<div class="nocontent" style="display:block">
                    	<div class="nocontent_box"></div>
                        <div class="nocontent_text red-text">目前没有满足条件的债权转让项目</div>
                    </div>
       			</c:if>
       			<c:forEach items="${page.list}" var="transferProject">
       			<div class="item">
					<div class="top">
						<c:if test="${transferProject.projectBaseInfo.projectTypeCode eq '1' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_di.png" alt="">
						</c:if>
						<c:if test="${transferProject.projectBaseInfo.projectTypeCode eq '2' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_ge.png" alt="">
						</c:if>
						<c:if test="${transferProject.projectBaseInfo.projectTypeCode eq '3' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_shang.png" alt="">
						</c:if>
						<c:if test="${transferProject.projectBaseInfo.projectTypeCode eq '4' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zhi.png" alt="">
						</c:if>
						<c:if test="${transferProject.projectBaseInfo.projectTypeCode eq '5' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zu.png" alt="">
						</c:if>
						<c:if test="${transferProject.projectBaseInfo.projectTypeCode eq '6' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zi.png" alt="">
						</c:if>
						<span>花生金服</span>
						<img class="img" src="${ctxStatic}/modules/front/images/util/line_split.png" alt="">
						<span>${fns:getDictLabel(transferProject.projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}</span>
					</div>
					<div class="line"></div>
					<div class="item-title">${p2p:abbrev(transferProject.projectBaseInfo.projectName,100)}</div>
					<div class="line"></div>
                       
					<div class="field_1 clearfix">
                       	<dl class="fl">
                           	<dt>年化利率</dt>
                               <dd><span><fmt:formatNumber  value="${transferProject.projectBaseInfo.annualizedRate * 100 }" pattern="#.#" /></span>%</dd>
                           </dl>
                       	<dl class="fr text-right">
                           	<dt>剩余期限</dt>
                               <dd><span class="brown-text">${transferProject.projectBaseInfo.pes.remainingTime }</span>个月</dd>
                           </dl>
                       </div>
                       <c:if test="${transferProject.status eq '0'}"> 
                       <div class="item-line-7">已完成<fmt:formatNumber  value="${transferProject.projectExecuteSnapshot.endFinanceMoney / transferProject.transferPrice }" type="percent" minFractionDigits="2" maxFractionDigits="2" /></div>
                       <progress class="item-line-5" value="${transferProject.projectExecuteSnapshot.endFinanceMoney / transferProject.transferPrice }" max="1"></progress>
                       </c:if>
                       <div class="field_2 clearfix">
                       		<dl class="fl">
                               	<dd>
                               		<div style="width:200px;text-align:center;">
                               			<span class="deepblue-text">
                               				<fmt:formatNumber value="${transferProject.projectExecuteSnapshot.endFinanceMoney }" pattern="##0.00" /> / <fmt:formatNumber value="${transferProject.transferPrice }" pattern="##0.00" />
                               			</span>
                               		</div>
                               	</dd>
                            </dl>
                       </div>
                       
					<div class="clearfix"></div>
					
					<div class="line"></div>
					<div class="item-line-4">
						<c:choose> 
							<c:when test="${transferProject.status eq '0'}"> 
								<c:choose>
									<c:when test="${transferProject.projectBaseInfo.showTermList.indexOf('0') != -1 }">
						<a href="${ctxFront }/projectTransfer/detail?transferProjectId=${transferProject.transferProjectId }">立即投资</a>
									</c:when>
									<c:otherwise>
						<a href="${ctxFront }/projectTransfer/detail?transferProjectId=${transferProject.transferProjectId }">
							移动端专享
						</a>
									</c:otherwise>
								</c:choose>
							</c:when> 
							<c:otherwise> 
						<a href="${ctxFront }/projectTransfer/detail?transferProjectId=${transferProject.transferProjectId }" class="bt_gray_210x43">查看详情</a>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="div_height_15"></div>
					<div class="item-line-7">
						<c:choose> 
							<c:when test="${fn:length(transferProject.projectBaseInfo.projectIntroduce) > 30}"> 
						<c:out value="${fn:substring(transferProject.projectBaseInfo.projectIntroduce, 0, 30)}......" /> 
							</c:when> 
							<c:otherwise> 
						<c:out value="${transferProject.projectBaseInfo.projectIntroduce}" /> 
							</c:otherwise>
						</c:choose>
					</div>
				</div>
       			</c:forEach>
			</div>
			<div class="div_height_20"></div>
			<div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
			<div class="div_height_50"></div>
		</div>
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