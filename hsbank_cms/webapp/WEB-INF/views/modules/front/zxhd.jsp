<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="front_with_menu" />
<link href="${ctxStatic}/modules/front/css/zxhd.css?${version }" rel="stylesheet" />
<title></title>
<script type="text/javascript">
	$(function() {
		//幻灯片
		$('.slides_list').slidesjs({
			width : 1078,
			height : 248,
			navigation : false,
			start : 1,
			play : {
				auto : true
			}
		})
	});
</script>
</head>
<body>
	<div class="div_bg_001">
		<div class="activity_page">
        	
            <!--最新活动banner-->
			<div class="activity_banner"><a href="${ctxFront }/activity/marketing"><img src="${ctxStatic}/modules/front/images/zxhd/latest_activity_04.jpg" alt="some text" /></a></div>
            
			<!-- 轮播图 -->
			<div class="slides_wrapper activity_slides" style="display:none">
				<ul class="slides_list">
					<li><a href="#"><img src="${ctxStatic}/modules/front/images/zxhd/slide_test_1.png" alt="some text" /></a></li>
					<li><a href="#"><img src="${ctxStatic}/modules/front/images/zxhd/slide_test_1.png" alt="some text" /></a></li>
					<li><a href="#"><img src="${ctxStatic}/modules/front/images/zxhd/slide_test_1.png" alt="some text" /></a></li>
					<li><a href="#"><img src="${ctxStatic}/modules/front/images/zxhd/slide_test_1.png" alt="some text" /></a></li>
				</ul>
			</div>
			<div class="activity_list_wrapper">
				<h2 class="activity_list_title">最新活动</h2>
				<ul class="activity_list clearfix">
				 <c:forEach items="${page.list}" var="activity" varStatus="varStatus">
				 <li class="activity_item">
						<div class="activity_wrapper">
							<img src="${activity.activityCover}"  alt="some text" class="activity_cover" />
							
							<!-- 
							<c:if test="${varStatus.index==0 and page.pageNo==1}">
								最新任务
							</c:if>
							<c:if test="${varStatus.index!=0 or  page.pageNo!=1}">
								往期任务
							</c:if>
							 -->
							
							 
							
							
							<c:if test="${activity.endDt==null or activity.endDt.time>sysDate.time}">
								<span class="activity_type">最新任务</span>
								<span class="last_days">长期有效</span>
								<span class="activity_description">${activity.activityDescription }</span>
								<a href="${ctxFront }${activity.activityJoin}" class="activity_join">参加活动</a>
							</c:if>
							<c:if test="${activity.endDt!=null and activity.endDt.time<sysDate.time}">
								<span class="activity_type">往期任务</span>
								<span class="last_days_gray">活动结束</span>
								<span class="activity_description">${activity.activityDescription }</span>
								<a href="${ctxFront }${activity.activityJoin}" class="activity_join_gray">参加活动</a>
							</c:if>
							
							
						</div>
					</li>
				 </c:forEach>
					
				</ul>
			</div>
			<div class="div_height_50"></div>
			 <!-- 分页开始 -->
			<div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
			<script type="text/javascript">
				function page(n,s) {
					var url = "${ctxFront}/zxhd?pageNo=" + n;
					window.location.href = url;
			}
			</script>
			<!-- 分页结束 -->
			<div class="bottom"></div>
		</div>
	</div>
</body>
</html>