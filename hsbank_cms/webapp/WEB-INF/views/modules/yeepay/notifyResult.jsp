<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import="java.util.Enumeration"%>
<html>
<head>
<title>易宝测试入口</title>
<meta name="decorator" content="default" />
<script>
	
</script>
</head>
<body>
	<%=request.getParameter("requestNo")%>>>>>步骤测试结束！！
	<%
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			if (name.startsWith("**")) {

				//如果是数组参数，则逐个打印
	%>
	<h1><%=name%>:
		<%
		String canshu[] = request.getParameterValues(name);
				int i;
				for (i = 0; i < canshu.length; i++) {
					System.out.println(canshu[i]);
	%>
		<%=canshu[i]%>
		<%
			}
		%>
	</h1>
	<%
		} else {
				//如果不是数组参数，直接打印
				System.out.println(name + "=" + request.getParameter(name));
	%>
	<h1><%=name%>:<%=request.getParameter(name)%></h1>
	<%
		}
		}
	%>
</body>
</html>