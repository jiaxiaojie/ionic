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
Map map = Distribute.getDistributeRefundExtBackMap(request);

if((Boolean)map.get("httpConnection") == false){
out.println("通讯失败" + "<br/>");
}else{
Map parameter = (Map)map.get("parameter");
Boolean checkHmac = (Boolean)map.get("checkHmac");
if((Boolean)map.get("checkHmac") ){
if(((String)parameter.get("r1_Code")).equals("1")){
out.println("退款成功" + "<br/>");
}else{
out.println("退款失败" + "<br/>");
}

out.println("业务类型:" + parameter.get("r0_Cmd") + "<br>");
out.println("支付结果:" + parameter.get("r1_Code") + "<br>");
out.println("唯一请求号:" + parameter.get("r1_RequestId") + "<br>");
out.println("业务完成的时间:" + parameter.get("r1_Time") + "<br>");
out.println("错误描述:" + new String(((String)parameter.get("errorMsg")).getBytes("gbk"), "iso-8859-1") + "<br>");
out.println("订单金额:" + parameter.get("r2_OrderAmount") + "<br>");
out.println("支付金额:" + parameter.get("r3_SrcAmount") + "<br>");
out.println("退款金额:" + parameter.get("r4_RefundAmount") + "<br>");
out.println("平台退款金额:" + parameter.get("r5_MerRefundAmount") + "<br>");
out.println("退款明细:" + parameter.get("r6_Details") + "<br>");
out.println("通知类型:" + parameter.get("r9_BType") + "<br>");

logicAfterSendReq();
}else{
out.println("交易签名无效" + "<br/>");
}
}
%> 