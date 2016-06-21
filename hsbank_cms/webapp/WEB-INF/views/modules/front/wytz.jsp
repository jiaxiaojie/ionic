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
			var status = ${projectSearchBean.status};
			var duration = ${projectSearchBean.duration};
			var rate = ${projectSearchBean.rate};
			var repaymentMode = ${projectSearchBean.repaymentMode};
			var projectType = ${projectSearchBean.projectType};
			var target = "${ctxFront}/wytz";
			$(function(){
				$("#status li[value='${projectSearchBean.status}']").addClass('active');
				$("#duration li[value='${projectSearchBean.duration}']").addClass('active');
				$("#rate li[value='${projectSearchBean.rate}']").addClass('active');
				$("#repaymentMode li[value='${projectSearchBean.repaymentMode}']").addClass('active');
				$("#projectType li[value='${projectSearchBean.projectType}']").addClass('active');
				$("ul li").click(function(){
					$(this).siblings().removeClass('active').end().addClass('active');
				});
				$("#status li").click(function(){
					status = $(this).val();
					searchProject();
				});
				$("#duration li").click(function(){
					duration = $(this).val();
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
				$("#projectType li").click(function(){
					projectType = $(this).val();
					searchProject();
				});
			});
			function page(n,s){
				pageNo = n;
				searchProject();
			}
			function searchProject() {
				window.location.href = target + "?status=" + status + "&duration=" + duration + "&rate="
					+ rate + "&repaymentMode=" + repaymentMode + "&projectType=" + projectType + "&pageNo=" + pageNo;
			}
		</script>
	</head>
	<body>
		<div class="div_width_980">
			<div id="condition_top"></div>
			<div id="condition_middle">
				<div class="div_height_10"></div>
				<div id="tab_bar" style="padding-left:20px;">
					<a href="${ctxFront }/wytz"><span class="item"><span class="selected">项目列表</span></span></a>
					<a href="${ctxFront }/projectTransfer/list"><span class="item"><span>债权转让</span></span></a>
					<a href="${ctxFront}/current/toFirstTenderingCurrentProject"><span class="item"><span>活期产品</span></span></a>
				</div>
				<div id="condition">
					<div class="line_1">
	                    <label>项目状态：</label>
	                    <ul id="status">
	                        <li value="0" data-href="#" class="item ">全部</li>
	                        <li value="1" data-href="#" class="item ">投标中</li>
	                        <li value="2" data-href="#" class="item ">投标结束</li>
	                        <li value="3" data-href="#" class="item ">还款中</li>
	                        <li value="4" data-href="#" class="item ">还款完成</li>
	                    </ul>
	                </div>
	                <div class="line_1">
	                    <label>项目期限：</label>
	                    <ul id="duration">
	                        <li value="0" data-href="#" class="item ">全部</li>
	                        <li value="1" data-href="#" class="item ">0 - 3个月</li>
	                        <li value="2" data-href="#" class="item ">3 - 6个月</li>
	                        <li value="3" data-href="#" class="item ">6 - 9个月</li>
	                        <li value="4" data-href="#" class="item ">9 - 12个月</li>
	                        <li value="5" data-href="#" class="item ">12 - 15个月</li>
	                        <li value="6" data-href="#" class="item ">15个月以上</li>
	                    </ul>
	                </div>
	                <div class="line_1">
	                    <label>年化利率：</label>
	                    <ul id="rate">
	                        <li value="0" data-href="#" class="item ">全部</li>
	                        <li value="1" data-href="#" class="item ">9%以下</li>
	                        <li value="2" data-href="#" class="item ">9 - 10%</li>
	                        <li value="3" data-href="#" class="item ">10 - 11%</li>
	                        <li value="4" data-href="#" class="item ">11 - 12%</li>
	                        <li value="5" data-href="#" class="item ">12%以上</li>
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
	                <div class="line_1">
	                    <label>项目类型：</label>
	                    <ul id="projectType">
	                        <li value="0" data-href="#" class="item ">全部</li>
	                        <li value="1" data-href="#" class="item ">商圈贷</li>
	                        <li value="2" data-href="#" class="item ">抵押</li>
	                        <li value="3" data-href="#" class="item ">个人信用贷</li>
	                        <li value="4" data-href="#" class="item ">质押</li>
	                        <li value="5" data-href="#" class="item ">融资租赁</li>
	                        <li value="6" data-href="#" class="item ">资管计划</li>
	                    </ul>
	                </div>
				</div>
			</div>
			<div id="condition_bottom"></div>
			<div id="overview">
				<span><span>融资项目<em>${onlineProjectCount }</em></span></span>
				<span><span>投标中<em>${tenderingProjectCount }</em></span></span>
       			<span><span>投标结束<em>${tenderedProjectCount }</em></span></span>
       			<span><span>还款中<em>${repaymentingProjectCount }</em></span></span>
       			<span><span>还款完成<em>${repaymentedProjectCount }</em></span></span>
       		</div>
        	<div class="line_dotted"></div>
       		<div id="project_list">
       			<c:if test="${empty page.list }">
       				<div class="nocontent" style="display:block">
                    	<div class="nocontent_box"></div>
                        <div class="nocontent_text red-text">目前没有满足条件的融资项目</div>
                    </div>
       			</c:if>
       			<c:forEach items="${page.list}" var="project">
				<div class="item">
					<c:if test="${project.isNewUser eq '0' }"><div class="icon_newhand"></div></c:if>
					<div class="top">
						<c:if test="${project.projectTypeCode eq '1' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_di.png" alt="">
						</c:if>
						<c:if test="${project.projectTypeCode eq '2' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_ge.png" alt="">
						</c:if>
						<c:if test="${project.projectTypeCode eq '3' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_shang.png" alt="">
						</c:if>
						<c:if test="${project.projectTypeCode eq '4' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zhi.png" alt="">
						</c:if>
						<c:if test="${project.projectTypeCode eq '5' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zu.png" alt="">
						</c:if>
						<c:if test="${project.projectTypeCode eq '6' }">
							<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zi.png" alt="">
						</c:if>
						<span>花生金服</span>
						<img class="img" src="${ctxStatic}/modules/front/images/util/line_split.png" alt="">
						<span>${fns:getDictLabel(project.repaymentMode, 'project_repayment_mode_dict', '')}</span>
					</div>
					<div class="line"></div>
					<div class="item-title">${p2p:abbrev(project.projectName,100)}</div>
					<div class="line"></div>
					<div class="item-line-1">
						<span class="span-3">预期年化收益率</span>
						<div class="percent">
							<span class="span-1">
								<c:choose>
									<c:when test="${project.isIncreaseInterest eq '1' && project.increaseInterestRate > 0 }">
								<fmt:formatNumber value="${(project.annualizedRate - project.increaseInterestRate) * 100 }" pattern="#.#" />+<fmt:formatNumber value="${project.increaseInterestRate * 100 }" pattern="#.#" />
									</c:when>
									<c:otherwise>
								<fmt:formatNumber value="${project.annualizedRate * 100 }" pattern="#.#" />
									</c:otherwise>
								</c:choose>
							</span>
							<span class="span-2">%</span>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="item-line-2">
						<span class="fl">起投金额</span>
						<span class="fr">期限</span>
					</div>
					<div class="item-line-3">
						<div class="fl">
							<span class="span-1"><fmt:formatNumber value="${project.startingAmount }" pattern="##0" /></span>
							<span class="span-2">元</span>
						</div>
						<div class="fr">
							<span class="span-3">${project.projectDuration }</span>
							<span class="span-4">月</span>
						</div>
					</div>
					<div class="line"></div>
					<div class="item-line-4">
						<c:choose> 
							<c:when test="${project.projectStatus eq '3'}">
								<c:choose>
									<c:when test="${project.showTermList.indexOf('0') != -1 }">
						<a href="${ctxFront }/project_detail?id=${project.projectId }">立即投资</a>
									</c:when>
									<c:otherwise>
						<a href="${ctxFront }/project_detail?id=${project.projectId }">
							移动端专享
						</a>
									</c:otherwise>
								</c:choose>
							</c:when> 
							<c:otherwise> 
						<a href="${ctxFront }/project_detail?id=${project.projectId }" class="bt_gray_210x43">查看详情</a>
							</c:otherwise>
						</c:choose>
					</div>
					<c:if test="${project.projectStatus eq '3'}">
					<progress class="item-line-5" value="${project.pes.endFinanceMoney }" max="${project.pes.financeMoney }"></progress>
					<div class="item-line-6"><fmt:formatNumber value="${project.pes.endFinanceMoney }" pattern="##0.00" /> / <fmt:formatNumber value="${project.pes.financeMoney }" pattern="###.00" /></div>
					</c:if>
					<div class="item-line-7">
						<c:choose> 
							<c:when test="${fn:length(project.projectIntroduce) > 30}"> 
						<c:out value="${fn:substring(project.projectIntroduce, 0, 30)}......" /> 
							</c:when> 
							<c:otherwise> 
						<c:out value="${project.projectIntroduce}" /> 
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