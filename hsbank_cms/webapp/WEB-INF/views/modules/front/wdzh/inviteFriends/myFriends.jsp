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
				<div id="item_1" onClick="selectItem(1)" class="item"><span class="selected">我的好友</span></div>
				<div id="item_2" onClick="selectItem(2)" class="item"><span>我的收益</span></div>
			</div>
			<!-- 我的好友 -->
			<div class="myfriends">
            	<table class="table table-hover">
                	<thead>
						<tr>
							<th width="40">名字</th>
							<th width="20">账号</th>
							<th width="20">状态</th>
							<th width="30">注册时间</th>
						</tr>
					</thead>
                	<tbody>
                		<c:forEach var="customerAccount" items="${page.list }">
                			<tr>
								<td>${p2p:vagueName(customerAccount.customerBase.customerName) }</td>
								<td>${p2p:vagueMobile(customerAccount.customerBase.mobile) }</td>
								<td>${customerAccount.accountName }</td>
								<td><fmt:formatDate value="${customerAccount.registerDt }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
                		</c:forEach>
					</tbody>
                </table>
                
                <!-- 分页开始 -->
		        <div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
		        <script type="text/javascript">
		        	function page(n,s) {
		        		var url = "${ctxFront}/customer/inviteFriends/myFriends?pageNo=" + n;
		        		window.location.href = url;
		        	}
		        </script>
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