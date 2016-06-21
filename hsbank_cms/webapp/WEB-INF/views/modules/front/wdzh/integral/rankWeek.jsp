<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/tzgl_skmx.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zjgl_jyjl.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var dateUtils = new DateUtils();
			function recentMonth(months) {
				$("#startDateTime").val(dateUtils.formatDate(new Date()));
				$("#endDateTime").val(dateUtils.addMonths(new Date(), months));
				submit()
			}
			function submit() {
				$("#searchForm").submit();
			}
			//时间选择
			$(function(){
				$("#startDateTime").click(function(){
					WdatePicker({minDate:new Date(), maxDate:$("#endDateTime").val()});
				});
				$("#endDateTime").click(function(){
					WdatePicker({minDate:$("#startDateTime").val()});
				});
			});
		</script>
	</head>
	<body>		

		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle" style="width:769px;">
			<div id="tab_bar" style="padding-left:20px;">
				<li id="item_1" onClick="selectItem(1)" class="item"><span>月排行</span></li>
				<li id="item_2" onClick="selectItem(2)" class="item"><span class="selected">周排行</span></li>
			</div>
			<!-- 待收款明细 -->
			<div id="record_list" class="show">
				<div class="div_height_15"></div>
				<div class="line_01">
					<form id="searchForm" method="post" action="${ctxFront }/customer/investmentRank/rankWeek" style="overflow:hidden;">
						<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo }"/>
						<span class="span_text_1">年</span>
						<form:select id="year" name="year" path="rank.year" class="select" onchange="showWeekList(this.value);">
		                    <form:options items="${fns:getDictList('investment_rank_year_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		                </form:select>
		                <span class="span_text_1">周</span>
		                <div id="weekShow">
						<form:select id="week" name="week" path="rank.week" class="select" onchange="changeWeekEvent();">
		                    <form:options items="${calendarList}" itemLabel="weekName" itemValue="week" htmlEscape="false"/>
		                </form:select>
		                </div>
<!-- 							<div id="bt_query" onclick="submit()" class="bt_yellow_85x24" style="float:left;margin-top:8px;margin-left:10px;">查&nbsp;询</div>
 -->					</form>
				</div>
				<div class="div_height_10"></div>
				<hr/>
				<table class="table table-hover">
					<thead>
						<tr>
							<th width="35%">昵称</th>
		                    <th width="35%">累计投资</th>
		                    <th width="30%">排名</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="investmentRank">
	                <tr>
	                    <td>${p2p:vagueName(investmentRank.accountName) }</td>
	                    <td><fmt:formatNumber value="${investmentRank.amount}" pattern="#0.00" /></td>
	                    <td>${investmentRank.rank }</td>
	                </tr>
	                </c:forEach>
					</tbody>
				</table>
				<!-- 分页开始 -->
		        <%-- <div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
		        <script type="text/javascript">
		        	function page(n,s) {
		        		$("#pageNo").val(n);
		        		$("#searchForm").submit();
		        	}
		        </script> --%>
		        <!-- 分页结束 -->
			</div>
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#tab_bar li').click(function(){
					if ($(this).children("span").hasClass("selected")) {
						return ;
					}
					$("#tab_bar li").each(function(){
				        if($(this).children("span").hasClass("selected")){
				            $(this).children("span").removeClass("selected");
				        }
				    });
					$(this).children("span").addClass("selected");
				});
			});
			
			/* $(function(){
				$("#week").change(function(){ 
					$("#searchForm").submit();
				});
			}); */
			
			function changeWeekEvent(){
				$("#searchForm").submit();
			}
			
			function selectItem(index) {
				if (index == 1) {
					window.location = "${ctxFront}/customer/investmentRank/rankMonth";
				}
				if (index == 2) {
					window.location = "${ctxFront}/customer/investmentRank/rankWeek";
				}
			}
			
			function showWeekList(id) {
				selectId = id;
				$.ajax({
					type : 'post',
					url : '${ctxFront}/customer/investmentRank/calendarList',
					data : {
						year : id
					},
					dataType : 'json',
					success : function(data) {
						var info = data.calendarList;
						var content = "";
						content += "<select id=\"week\" name=\"week\" name=\"week\" class=\"select\" onchange=\"changeWeekEvent();\">";
						for(var i=0;i<info.length;i++){
							content += "<option value=\"" + info[i].week + "\">";
							content += ""+info[i].weekName+"";
							content += "</option>";
						}
						content += "</select>";
						alert(content);
						$('#weekShow').html(content);
					}
				});
			}
			
		</script>
	</body>
</html>