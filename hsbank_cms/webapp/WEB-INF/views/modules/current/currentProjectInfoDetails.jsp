<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活期项目信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	

	
		function queryPage(url){
			
			var myurl = '${ctx}/current/currentProjectInfo/details?id=${currentProjectInfo.id}';
			window.location.href=myurl+"&toUrl="+encodeURIComponent(url); 
		}
		
	</script>
	
	
</head>
<body>
	
	<jsp:include page="./common/projectInfoMenu.jsp"/>
	
</body>
</html>