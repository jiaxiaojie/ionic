<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/yqhy.css?${version }" rel="stylesheet"/>
		<title></title>
	</head>
	<body>
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div id="tab_bar">
				<div id="item_1" onClick="selectItem(1)" class="item"><span>我的好友</span></div>
				<div id="item_2" onClick="selectItem(2)" class="item"><span class="selected">我的收益</span></div>
			</div>
			<!-- 我的收益 -->
			<div class="myearning">
				<div class="div_height_15"></div>
            	<div class="line_01">
                    <form id="searchForm" method="post" class="clearfix" action="${ctxFront}/customer/inviteFriends/myEarning">
                        <input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo }"/>
                        <span class="span_text_1">日期</span>
                        <input id="startDateTime" style="cursor:pointer" name="startDateTime" value="<fmt:formatDate value="${pageSearchBean.startDateTime }" pattern="yyyy-MM-dd"/>" readonly type="text" maxlength="10" class="input">
                        <span class="span_text_2">至</span>
                        <input id="endDateTime" style="cursor:pointer" name="endDateTime" value="<fmt:formatDate value="${pageSearchBean.endDateTime }" pattern="yyyy-MM-dd"/>" readonly type="text" maxlength="10" class="input">
                        <div id="bt_query" onClick="submit()" class="bt_yellow_85x24" style="float:left;margin-top:8px;margin-left:10px;">查&nbsp;询</div>
                    </form>
                </div>
            
            	<table class="table table-hover">
                	<thead>
						<tr>
							<th width="40">用户名</th>
							<th width="30">奖励时间</th>
							<th width="20">收益</th>
							<th width="30">备注</th>
						</tr>
					</thead>
                	<tbody>
                	    <c:forEach var="marketingActivityAwardRecord" items="${page.list }" varStatus="status">
						<tr>
							<td>${p2p:vagueName(marketingActivityAwardRecord.customerName)}</td>
							<td><fmt:formatDate value="${marketingActivityAwardRecord.awardDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatNumber value="${marketingActivityAwardRecord.awardValue }" pattern="##0.00" />元</td>
							<td>${marketingActivityAwardRecord.awardReason }</td>
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
            </div>
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
		<script type="text/javascript">
			//时间选择
			$(function(){
				$("#startDateTime").click(function(){
					WdatePicker({maxDate:$("#endDateTime").val()});
				});
				$("#endDateTime").click(function(){
					WdatePicker({minDate:$("#startDateTime").val(), maxDate:new Date()});
				});
			});
			function submit() {
				$("#searchForm").submit();
			}
			
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
			
			function selectItem(index) {
				if (index == 1) {
					window.location = "${ctxFront}/customer/inviteFriends/myFriends";
				}
				if (index == 2) {
					window.location = "${ctxFront}/customer/inviteFriends/myEarning";
				}
			}
		</script>
	</body>
</html>