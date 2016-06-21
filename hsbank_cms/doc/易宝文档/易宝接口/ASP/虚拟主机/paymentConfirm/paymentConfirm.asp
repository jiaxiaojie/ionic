<!-- #include file="../common/yeepayCommon.asp" -->

<%
'**************************************************
'* @Title 易宝支付分账范例
'* @Description 解冻分账支付款项
'* @Copyriht (c) 北京通融通信息有限公司（易宝支付）
'* @Author    yang.yue
'* @Version   V2.0  
'**************************************************

' 开始获取解冻分账表单提交的信息 
For Each objItem in request.form

	objItemName = Trim(objItem)
    objItemValue = Request.Form(objItem)
    requestMap.add objItem,Request.Form(objItem)
	  
Next
' 1为：使用重定向方式提交退款请求   
' Call doRequestHttp("PaymentConfirm",requestMap,PaymentConfirmHmacArray,1)   
' 2为：使用通讯方式提交退款请求  
returnStr = doRequestHttp("PaymentConfirm",requestMap,PaymentConfirmHmacArray,2)   
' 显示返回结果字符串
Response.Write returnStr
%>
