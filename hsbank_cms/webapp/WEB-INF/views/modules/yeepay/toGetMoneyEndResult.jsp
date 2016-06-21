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
	<%=request.getParameter("requestNo") %>>>>>授权操作结束！！
		<br/>
		<table>
			<tr>
			<td>
				转账授权编号
			</td>
			<td>
				<input type="text" name="requestNo" value="<%=request.getParameter("requestNo") %>">
			</td>
			</tr>
		</table>	
</body>
</html>