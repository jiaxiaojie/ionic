<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_menu"/>
		<script src="${ctxStatic}/jquery/jq_scroll.js" type="text/javascript"></script>
		<link href="${ctxStatic}/modules/front/css/index.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wytz.css?${version }" rel="stylesheet">
		<script src="${ctxStatic}/common/cookieUtils.js?${version }" type="text/javascript"></script>
		<script src="${ctxStatic}/modules/front/js/jquery.flexslider-min.js" type="text/javascript"></script>
		<title></title>
		<script type="text/javascript">
			function closePop() {
				$("#activityPopup").hide();
			}
			var cookieUtils = new CookieUtils();
			$(function(){
				if(cookieUtils.getCookie("hasVisited") != "yes") {
					$("#activityPopup").show();
					cookieUtils.setCookie("hasVisited", "yes");
				}
			});
		</script>
        <script type="text/javascript">
		$(document).ready(function(){
			$('.flexslider').flexslider({
				slideshowSpeed: 10000,
				directionNav: true,
				animation: "fade",
				pauseOnAction: false
			});
		});
		</script>
		<!--[if lte IE 8]><script type="text/javascript">window.location.href="${ctxStatic}/modules/front/html/update_browser.html";</script><![endif]-->
		<!--[if lte IE 7]><script type="text/javascript">window.location.href="${ctxStatic}/modules/front/html/update_browser.html";</script><![endif]-->
		<!--[if lte IE 6]><script type="text/javascript">window.location.href="${ctxStatic}/modules/front/html/update_browser.html";</script><![endif]-->
	</head>
	<body>
    	<!--全屏活动窗口-->
    	<div class="activity_popup" id="activityPopup" style="display:none;">
        	<div class="activity_bg"></div>
            <div class="activity_main">
            	<a href="javascript:void(0);" onClick="closePop()" class="activity_close"></a>
                <a href="${ctxFront }/activity/marketing" class="activity_enter"></a>
            </div>
        </div>
		<!-- 上半部分 -->
		<div class="div_bg_001">
			<!-- 轮播图 -->
			<div id="div_carousel_area" class="div_width_1360 banner_area">
				<div id="myCarousel" class="carousel slide banner">
					<div class="banner_top"></div>
					<div class="banner_right"></div>
					<div class="banner_bottom"></div>
					<div class="banner_left"></div>                    
                    <div class="flexslider">
                        <ul class="slides">
                       <c:forEach items="${carouselInfos}" var="carouselInfo">
                      <li style="background:url(${fns:encodeFileNameByUrl(carouselInfo.pictureBig)}) 100% 0 no-repeat;">
                          <!-- 活动倒计时 -->
                        <c:if test="${carouselInfo.dispaly eq '1' }">
                          <div class="downCount">
	                       <div class="countdown-area">
	                       <div class="csslb">
	                       <div class="countdown-title">距离此活动开始还有</div>
	        	           <div class="countdown-dividingline"></div>
						        	<div id="countdown" class="countdown-time clearfix">
						            	<span class="day${carouselInfo.carouselId}">-</span><span>天</span>
						                <span class="hour${carouselInfo.carouselId}">-</span><span>时</span>
						                <span class="minute${carouselInfo.carouselId}">-</span><span>分</span>
						                <span class="second${carouselInfo.carouselId}">-</span><span>秒</span>
						            </div>
						         </div>   
						        </div>
	                       </div>
                       </c:if> 
                       <!-- 活动开始时间 -->
                     <%--  <div id="activityTime${carouselInfo.carouselId}">${carouselInfo.activityTime}</div>
                     <div id="theDate${carouselInfo.carouselId}">${theDate}</div>  --%>
                           <a href="${ctxFront }${carouselInfo.target }" target="_blank" ></a> 
                      </li> 
							<script type="text/javascript">
							$(function(){
								countDown${carouselInfo.carouselId}("${fns:format(carouselInfo.activityTime,'yyyy/MM/dd HH:mm:ss')}","#countdown .day${carouselInfo.carouselId}","#countdown .hour${carouselInfo.carouselId}","#countdown .minute${carouselInfo.carouselId}","#countdown .second${carouselInfo.carouselId}");
							});
							function countDown${carouselInfo.carouselId}(time,day_elem,hour_elem,minute_elem,second_elem){
								//if(typeof end_time == "string")
								var end_time = new Date(time).getTime(),//月份是实际月份-1
								//current_time = new Date().getTime(),
								sys_second = (end_time-new Date().getTime())/1000;
								var timer = setInterval(function(){
									if (sys_second > 0) {
										sys_second -= 1;
										var day = Math.floor((sys_second / 3600) / 24);
										var hour = Math.floor((sys_second / 3600) % 24);
										var minute = Math.floor((sys_second / 60) % 60);
										var second = Math.floor(sys_second % 60);
										day_elem && $(day_elem).text(day);//计算天
										$(hour_elem).text(hour<10?"0"+hour:hour);//计算小时
										$(minute_elem).text(minute<10?"0"+minute:minute);//计算分
										$(second_elem).text(second<10?"0"+second:second);// 计算秒
									} else { 
										clearInterval(timer);
									}
								}, 1000);
							}
							
							</script>              
                        </c:forEach> 
                        </ul>
							</div>
							
                          
                        <!-- 
                            <li style="background:url(${ctxStatic}/modules/front/images/index/banner-11.jpg) 50% 0 no-repeat;"><a href="${ctxFront }/activity/marketing" target="_blank"></a></li>
                            <li style="background:url(${ctxStatic}/modules/front/images/index/banner-10.jpg) 50% 0 no-repeat;"><a href="${ctxFront }/gywm/aqbz" target="_blank"></a></li>
                        -->
                        
                    </div>
                    
				</div>
			</div>
			<div class="index-dotted-line"></div>
			<!-- 新闻公告 -->
			<div id="div_news" class="div_width_980">
				<div id="scroll_div_zxgg" class="news_left"><span class="title"><a href="${ctxFront }/index/zxgg">最新公告</a>：&nbsp;</span><span class="content">
				<ul>
				 <c:forEach items="${pageZxgg.list}" var="zxgg">
					<li style="height:26px;"><a href="${zxgg.urlWithZxgg}" style="color:${zxgg.color}">${fns:abbr(zxgg.title,96)}</a></li>
			     </c:forEach>
			     </ul>
				</span>
				<span style="float:right;" class="title pl0">
				   <a href="${ctxFront }/index/zxgg" class="more">更多<i>>></i></a>
				</span>
				</div>
				<div id="scroll_div_fdxw" class="news_right"><span class="title"><a href="${ctxFront }/index/fdxw">花生新闻</a>：&nbsp;</span><span class="content">
				<ul>
				   <c:forEach items="${pageFdxw.list}" var="fdxw">
					<li style="height:26px;">
					  <a href="${fdxw.urlWithFdxw}" style="color:${fdxw.color}">${fns:abbr(fdxw.title,45)}</a>
					</li>
				   </c:forEach>
				</ul>
				</span>
				<span style="float:right;" class="title pl0">
				   <a href="${ctxFront }/index/fdxw" class="more">更多<i>>></i></a>
				</span>
				</div>
			</div>
			<div class="index-dotted-line"></div>
			<!-- 大按钮导航 -->
			<!--<div id="div_bt_nav" class="div_width_980">
				<div id="bt_invest" class="bt_nav_left"></div>
				<div id="bt_loan" class="bt_nav_right"></div>
			</div> -->
			
			<c:if test="${currentProjectInfo != null }">
			<div class="current_financial">
				<div class="div_width_980 clearfix">
					<div class="pull-left cf_left">
						<p class="cf_left_title">${currentProjectInfo.name }</p>
						<div class="cf_left_text clearfix">
							<div class="pull-left">
								<span><em><fmt:formatNumber value="${currentProjectInfo.rate*100 }" pattern="#0.#" /></em><i>%</i></span>
								<p>年化收益率</p>
							</div>
							<div class="pull-left">
								<span><em><fmt:formatNumber value="${everyWanDayEarnings }" pattern="#0.00" /></em>元</span>
								<p>每万元日收益</p>
							</div>
						</div>
					</div>
					<div class="pull-left cf_right">
						<p>剩余金额<span><fmt:formatNumber value="${currentProjectInfo.financeMoney- currentProjectInfo.snapshot.hasFinancedMoney}" pattern="#0.00" /></span>元</p>
						<c:choose>
							<c:when test="${currentProjectInfo.isAutoRepay == 1 }">
								<a href="${ctxFront}/current/currentProjectDetail?id=${currentProjectInfo.id}" class="button_cf">立即投资</a>
							</c:when>
							<c:otherwise>
								<a class="button_gray_cf" >立即投资</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			
			</c:if>
			
		</div>
		<!-- 下半部分 -->
		<div class="div_bg_002">
			<div class="index-dotted-line"></div>
			<div class="ad_01"></div>
			<div class="index-dotted-line"></div>
			<div id="product" class="div_width_980">
				<div class="title"><span>推荐项目：</span><a href="${ctxFront }/wytz" class="more">更多<i>>></i></a></div>
				<c:if test="${empty projectBaseInfoList }">
                <div class="project_list project_list_no clearfix">
                	<div class="item">
                    	<div class="no_content_style" style=" margin-top:150px;"><p>推荐项目未开启</p></div>
                    </div>
                	<div class="item">
                    	<div class="no_content_style" style=" margin-top:150px;"><p>推荐项目未开启</p></div>
                    </div>
                	<div class="item">
                    	<div class="no_content_style" style=" margin-top:150px;"><p>推荐项目未开启</p></div>
                    </div>
                	<div class="item">
                    	<div class="no_content_style" style=" margin-top:150px;"><p>推荐项目未开启</p></div>
                    </div>
                </div>
                </c:if>
				<div id="project_list">
					<c:forEach var="projectBaseInfo" items="${projectBaseInfoList }">
					<div class="item">
						<c:if test="${projectBaseInfo.isNewUser eq '0' }"><div class="icon_newhand"></div></c:if>
						<div class="top">
						    <c:if test="${projectBaseInfo.projectTypeCode eq '1' }">
								<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_di.png" alt="">
							</c:if>
							<c:if test="${projectBaseInfo.projectTypeCode eq '2' }">
								<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_ge.png" alt="">
							</c:if>
							<c:if test="${projectBaseInfo.projectTypeCode eq '3' }">
								<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_shang.png" alt="">
							</c:if>
							<c:if test="${projectBaseInfo.projectTypeCode eq '4' }">
								<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zhi.png" alt="">
							</c:if>
							<c:if test="${projectBaseInfo.projectTypeCode eq '5' }">
								<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zu.png" alt="">
							</c:if>
							<c:if test="${projectBaseInfo.projectTypeCode eq '6' }">
								<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zi.png" alt="">
							</c:if>
							<span>花生金服</span>
							<img class="img" src="${ctxStatic}/modules/front/images/util/line_split.png" alt="">
							<span>${fns:getDictLabel(projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}</span>
						</div>
						<div class="line"></div>
						<div class="item-title">${p2p:abbrev(projectBaseInfo.projectName,100)}</div>
						<div class="line"></div>
						<div class="item-line-1">
							<span class="span-3">预期年化收益率</span>
							<div class="percent">
								<span class="span-1">
                                	<c:choose>
                                    	<c:when test="${projectBaseInfo.isIncreaseInterest eq '1' && projectBaseInfo.increaseInterestRate > 0 }">
                                    <fmt:formatNumber value="${(projectBaseInfo.annualizedRate - projectBaseInfo.increaseInterestRate) * 100 }" pattern="#.#" />+<fmt:formatNumber value="${projectBaseInfo.increaseInterestRate * 100 }" pattern="#.#" />
                                    	</c:when>
                                        <c:otherwise>
                                    <fmt:formatNumber value="${projectBaseInfo.annualizedRate * 100 }" pattern="#.#" />
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
								<span class="span-1">${projectBaseInfo.startingAmount }</span>
								<span class="span-2">元</span>
							</div>
							<div class="fr">
								<span class="span-3">${projectBaseInfo.projectDuration }</span>
								<span class="span-4">月</span>
							</div>
						</div>
						<div class="line"></div>
						<div class="item-line-4">
						<c:choose> 
							<c:when test="${projectBaseInfo.projectStatus eq '3'}"> 
								<c:choose>
									<c:when test="${projectBaseInfo.showTermList.indexOf('0') != -1 }">
								<a href="${ctxFront }/project_detail?id=${projectBaseInfo.projectId }">立即投资</a>
									</c:when>
									<c:otherwise>
								<a href="${ctxFront }/project_detail?id=${projectBaseInfo.projectId }">移动端专享</a>
									</c:otherwise>
								</c:choose>
							</c:when> 
							<c:otherwise> 
						<a href="${ctxFront }/project_detail?id=${projectBaseInfo.projectId }" class="bt_gray_210x43">查看详情</a>
							</c:otherwise>
						</c:choose>
						</div>
						<c:if test="${projectBaseInfo.projectStatus eq '3'}">
						<progress class="item-line-5" value="${projectBaseInfo.pes.endFinanceMoney }" max="${projectBaseInfo.pes.financeMoney }"></progress>
						<div class="item-line-6"><fmt:formatNumber value="${projectBaseInfo.pes.endFinanceMoney }" pattern="##0.00" /> / <fmt:formatNumber value="${projectBaseInfo.pes.financeMoney }" pattern="##0.00" /></div>
						</c:if>
						<div class="item-line-7">
							<c:choose> 
								<c:when test="${fn:length(projectBaseInfo.projectIntroduce) > 30}"> 
							<c:out value="${fn:substring(projectBaseInfo.projectIntroduce, 0, 30)}......" /> 
								</c:when> 
								<c:otherwise> 
							<c:out value="${projectBaseInfo.projectIntroduce}" /> 
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
			<div class="index-dotted-line"></div>
			<div class="ad_02"></div>
			<div class="index-dotted-line"></div>
			<div id="product" class="div_width_980">
				<div class="title"><span>债权转让：</span><a href="${ctxFront }/projectTransfer/list" class="more">更多<i>>></i></a></div>
				<c:if test="${empty projectTransferInfoList }">
                <div class="project_list project_list_no clearfix">
                	<div class="item">
                    	<div class="no_content_style" style=" margin-top:150px;"><p>债权转让未开启</p></div>
                    </div>
                	<div class="item">
                    	<div class="no_content_style" style=" margin-top:150px;"><p>债权转让未开启</p></div>
                    </div>
                	<div class="item">
                    	<div class="no_content_style" style=" margin-top:150px;"><p>债权转让未开启</p></div>
                    </div>
                	<div class="item">
                    	<div class="no_content_style" style=" margin-top:150px;"><p>债权转让未开启</p></div>
                    </div>
                </div>
                </c:if>
				<div id="project_list">
					<c:forEach items="${projectTransferInfoList}" var="transferProject">
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
							<a href="${ctxFront }/projectTransfer/detail?transferProjectId=${transferProject.transferProjectId }">移动端专享</a>
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
			</div>
			<div id="cooperation" class="div_width_980 cooperation_agency">
				<div class="title" style="width:auto; padding-left:10px;"><a id="cooperationAgency" name="cooperationAgency">合作机构：</a></div>
				<ul class="ca_list clearfix">
                	<li><a href="javascript:;" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_zljt.jpg" width="235" height="68"></a></li>
                	<li><a href="http://www.fdjf.net/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_fdjf.jpg" width="235" height="68"></a></li>
                	<li><a href="javascript:;" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_hwfo.jpg" width="235" height="68"></a></li>
                	<li><a href="http://www.abccapital.cn/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_yszb.jpg" width="235" height="68"></a></li>
                	<li><a href="javascript:;" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_yrsj.jpg" width="235" height="68"></a></li>
                	<li><a href="http://www.ljzfin.com/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_ljzfin.jpg" width="235" height="68"></a></li>
                	<!-- <li><a href="http://www.jianwei.com/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_jw.jpg" width="235" height="68"></a></li> -->
                	<li><a href="https://www.yeepay.com/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_ybzf.jpg" width="235" height="68"></a></li>
                    <li><a href="http://www.allinpay.com/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_tlzf.jpg" width="235" height="68"></a></li>
                	<li><a href="http://www.bqasset.com/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_bqzc.jpg" width="235" height="68"></a></li>
                	<!-- <li><a href="http://www.wangdaizhijia.com/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_wdzj.jpg" width="235" height="68"></a></li> -->
                	<!-- <li><a href="http://www.p2peye.com/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_wdty.jpg" width="235" height="68"></a></li> -->
                	<li><a href="http://www.cmbchina.com/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_zsyh.jpg" width="235" height="68"></a></li>
                	<li><a href="http://bank.pingan.com/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_payh.jpg" width="235" height="68"></a></li>
                	<li><a href="http://www.cbhb.com.cn/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_bhyh.jpg" width="235" height="68"></a></li>
                    <li><a href="http://www.cmbc.com.cn/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_msyh.jpg" width="235" height="68"></a></li>
                    <li><a href="http://www.icbc.com.cn/" target="_blank"><img src="../static/modules/front/images/util/cooperation/cooperation_gsyh.jpg" width="235" height="68"></a></li>
                	
                </ul>
			</div>
		</div>

    	<script type="text/javascript">
			$(document).ready(function() {
				$('.carousel').carousel({
					interval: 5000
				});
				
				$(document).on('click', '#bt_invest', function() {
					window.location.href="${ctxFront}/wytz";
				});
				
				$(document).on('click', '#bt_loan', function() {
					window.location.href="${ctxFront}/willloan/willloan";
				});
				
				$("#scroll_div_zxgg").Scroll({line:1,speed:500,timer:3000});
				
				$("#scroll_div_fdxw").Scroll({line:1,speed:500,timer:3000});
			});
		</script>
	</body>
</html>