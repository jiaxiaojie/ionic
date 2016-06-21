<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<%@page import="com.yeepay.distribute.*, java.util.Map"%>
<%! 
// 生成表单前处理业务逻辑
public void logicBeforeSendReq() {
}
// 生成表单后处理业务逻辑
public void logicAfterSendReq() {
}
%>
<%
logicBeforeSendReq();
%>
<html>
<body onload="window.<%
out.print("form");
%>.submit();">
<%
try{
out.println(Distribute.getDistributeIndirectRegisterForm(request,"form","提交"));

}catch(Exception e){
out.println(e);
}
%>
<%
logicAfterSendReq();
%>
</body>
</html>
