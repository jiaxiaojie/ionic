<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
	    <meta name="decorator" content="default" />
		<c:if test="${isSuccess }">
		<script type="text/javascript">
			var timeout = 3;
			$(function(){
				$("#remainSeconds").html(timeout);
				var interval = window.setInterval(function(){
					timeout--;
					$("#remainSeconds").html(timeout);
					if(timeout <= 0) {
						window.location.href = "${ctx }/current/currentProjectInfo/list";
						window.clearInterval(interval);
					}
				}, 1000);
			}); 
		</script>
		</c:if>
	</head>
	<body>
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/current/currentProjectInfo/list">目信息列表</a></li>
		</ul><br/>
		<form:form id="authorizeAutoRepaymentForm" modelAttribute="currentProjectInfo" action="${authorizeAutoRepaymentUrl }" method="post" class="form-horizontal" target="_blank">
		    <c:choose>
		    	<c:when test="${isSuccess }">
		    	  <div class="control-group">自动还款授权成功！</div>
		    	   <div class="control-group">
		        	<p>您已成功完成授权自动还款，<span id="remainSeconds" style="color:red;"></span>秒钟后会跳转至产品列表，如果没有跳转，请直接点击</p>
		        </div>
		        </c:when>
		        <c:otherwise>
		           <div class="control-group">授权自动还款失败，请重试</div>
		        </c:otherwise>
		    </c:choose>
		    <div class="btn_text"><a href="${ctx }/current/currentProjectInfo/list">立即跳转</a></div>	
		</form:form>
	</body>
</html>