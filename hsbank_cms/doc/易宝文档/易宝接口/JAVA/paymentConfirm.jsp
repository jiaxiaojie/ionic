<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<%@page import="com.yeepay.distribute.*,java.util.Map,com.yeepay.util.*"%>
<%! 
// 提交前处理业务逻辑
public void logicBeforeSendReq() {
}
// 提交后处理业务逻辑
public void logicAfterSendReq() {
}
%>
<%
logicBeforeSendReq();
Map map = Distribute.getDistributePaymentConfirmBackMap(request);

if((Boolean)map.get("httpConnection") == false){
out.println("通讯失败" + "<br/>");
}else{
Map parameter = (Map)map.get("parameter");
Boolean checkHmac = (Boolean)map.get("checkHmac");
if((Boolean)map.get("checkHmac") ){
if(((String)parameter.get("r1_Code")).equals("1")){
out.println("确认交易成功" + "<br/>");
}else{
out.println("确认交易失败" + "<br/>");
}
out.println("业务类型:" + parameter.get("r0_Cmd") + "<br>");
out.println("业务类型:" + new String(((String)parameter.get("errorMsg")).getBytes("gbk"), "iso-8859-1") + "<br>");

logicAfterSendReq();
}else{
out.println("交易签名无效" + "<br/>");
}
}
%> 