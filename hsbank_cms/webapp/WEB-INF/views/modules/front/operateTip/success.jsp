<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>操作成功</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		var seconds = 3;
		$(function(){
			var interFun = window.setInterval(function(){
				seconds--;
				$("#remainSeconds").html(seconds);
				if(seconds <= 0) {
					window.clearInterval(interFun);
					closeJboxAndReloadParentPage();
				}
			},1000);
		});
		var closeJboxAndReloadParentPage = function() {
			if(window.parent!=window.self) {
				window.parent.location.reload();
			}
			top.$.jBox.close(true);
		}
	</script>
	<style>
		.success{ margin:200px auto 0 auto; width:300px; line-height:26px; text-align:center;}
	</style>
</head>
<body>
	<div class="success">操作成功，<span style="color:red;" id="remainSeconds">3</span>秒后关闭当前窗口，并刷新页面<br/><a href="javascript:void(0)" onClick="closeJboxAndReloadParentPage()">关闭窗口并刷新页面</a></div>
</body>