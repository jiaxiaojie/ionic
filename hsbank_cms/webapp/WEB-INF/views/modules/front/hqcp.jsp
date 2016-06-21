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
			var status = ${status};
			var rate = ${rate};
			var target = "${ctxFront}/hqcp";
			$(function(){
				$("#status li[value='${status}']").addClass('active');
				$("#rate li[value='${rate}']").addClass('active');
				$("ul li").click(function(){
					$(this).siblings().removeClass('active').end().addClass('active');
				});
				$("#status li").click(function(){
					status = $(this).val();
					searchProject();
				});
				$("#rate li").click(function(){
					rate = $(this).val();
					searchProject();
				});
			});
			function page(n,s){
				pageNo = n;
				searchProject();
			}
			function searchProject() {
				window.location.href = target + "?status=" + status +"&rate="
					+ rate + "&pageNo=" + pageNo;
			}
		</script>
	</head>
	<body>
		<div class="div_width_980">
			<div id="condition_top"></div>
			<div id="condition_middle">
				<div class="div_height_10"></div>
				<div id="tab_bar" style="padding-left:20px;">
					<a href="${ctxFront }/wytz"><span class="item"><span  class="item">项目列表</span></span></a>
					<a href="${ctxFront }/current/hqcp"><span class="selected"><span>活期产品</span></span></a>
				</div>
				<div id="condition">
					<div class="line_1">
	                    <label>项目状态：</label>
	                    <ul id="status">
	                        <li value="0" data-href="#" class="item ">全部</li>
	                        <li value="1" data-href="#" class="item ">投标中</li>
	                        <li value="2" data-href="#" class="item ">投标结束</li>
	                        <li value="3" data-href="#" class="item ">已清算</li>
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
				</div>
			</div>
			<div id="condition_bottom"></div>
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
					<div class="top">
						<span>花生金服</span>
						<img class="img" src="${ctxStatic}/modules/front/images/util/line_split.png" alt="">
						<%-- <span>${fns:getDictLabel(project.repaymentMode, 'project_repayment_mode_dict', '')}</span> --%>
					</div>
					<div class="line"></div>
					<div class="item-title">${p2p:abbrev(project.name,100)}</div>
					<div class="line"></div>
					<div class="item-line-1">
						<span class="span-3">预期年化收益率</span>
						<div class="percent">
							<span class="span-1">
								<fmt:formatNumber value="${project.rate * 100 }" pattern="#.#" />
							</span>
							<span class="span-2">%</span>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="item-line-2">
						<span class="fl">起投金额</span>
					</div>
					<div class="item-line-3">
						<div class="fl">
							<span class="span-1"><fmt:formatNumber value="${project.startingAmount }" pattern="##0" /></span>
							<span class="span-2">元</span>
						</div>
					</div>
					<div class="line"></div>
					<div class="item-line-4">
						<c:choose> 
							<c:when test="${project.status eq '3'}">
								<a href="${ctxFront }/current/currentProjectDetail?id=${project.id }">立即投资</a>
							</c:when> 
							<c:otherwise> 
						<a href="${ctxFront }/project_detail?id=${project.id }" class="bt_gray_210x43">查看详情</a>
							</c:otherwise>
						</c:choose>
					</div>
					<c:if test="${project.status eq '3'}">
					<progress class="item-line-5" value="${project.snapshot.hasFinancedMoney }" max="${project.financeMoney }"></progress>
					<div class="item-line-6"><fmt:formatNumber value="${project.snapshot.hasFinancedMoney }" pattern="##0.00" /> / <fmt:formatNumber value="${project.financeMoney }" pattern="###.00" /></div>
					</c:if>
					<div class="item-line-7">
						<c:choose> 
							<c:when test="${fn:length(project.introduce) > 30}"> 
						<c:out value="${fn:substring(project.introduce, 0, 30)}......" /> 
							</c:when> 
							<c:otherwise> 
						<c:out value="${project.introduce}" /> 
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