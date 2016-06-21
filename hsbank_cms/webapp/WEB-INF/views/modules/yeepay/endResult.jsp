<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>易宝测试入口</title>
<meta name="decorator" content="default" />
<script>
</script>
</head>
<body>
	<%=request.getParameter("requestNo") %>>>>>步骤测试结束！！
		<br/>
	resp is <%=request.getParameter("resp") %>
	<br/>
	sign is <%=request.getParameter("sign") %>
</body>
</html>