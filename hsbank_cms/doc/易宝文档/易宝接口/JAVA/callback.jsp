<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<%@page import="com.yeepay.distribute.Distribute,java.util.Map"%>
<%
Map map = Distribute.distributePayCallback(request);

if(((String)request.getParameter("r9_BType")).equals("2")){
out.println("success</br>");
//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
}
	if((Boolean)map.get("checkHmac")){
	out.println("商户编号:" + request.getParameter("p1_MerId") + "</br>");
	out.println("业务类型:" + request.getParameter("r0_Cmd") + "</br>");
	out.println("支付结果:" + request.getParameter("r1_Code") + "</br>");
	out.println("易宝支付交易流水号:" + request.getParameter("r2_TrxId") + "</br>");
	out.println("商品价格:" + request.getParameter("r3_Amt") + "</br>");
	out.println("交易币种:" + request.getParameter("r4_Cur") + "</br>");
	out.println("商品编号:" + request.getParameter("r5_Pid") + "</br>");
	out.println("订单号:" + request.getParameter("r6_Order") + "</br>");
	out.println("商家扩展信息:" + request.getParameter("r8_MP") + "</br>");
	out.println("返回类型:" + request.getParameter("r9_BType") + "</br>");
	out.println("分账单列表:" + request.getParameter("ra_Details") + "</br>");
	
	logicAtCallback();
	
	}else{
	out.println("交易签名无效");
	}
%>
<%!
public void logicAtCallback(){

}
%>