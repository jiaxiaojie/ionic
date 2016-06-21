<!-- #include file="../common/yeepayCommon.asp" -->

<%
'**************************************************
'* @Title 易宝支付分账范例
'* @Description 子商户注册
'* @Copyriht (c) 北京通融通信息有限公司（易宝支付）
'* @Author    yang.yue
'* @Version   V2.0  
'**************************************************

' 开始获取注册表单提交的信息 
For Each objItem in request.form

	objItemName = Trim(objItem)
	objItemValue = Request.Form(objItem)
	requestMap.add objItem,Request.Form(objItem)
	  
Next  
' 调用提交函数
returnStr = doRequestHttp("IndirectRegister",requestMap,RegistMerchantHmacArray,1) 

%>