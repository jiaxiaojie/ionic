<!-- #include file="../common/yeepayCommon.asp" -->

<%
'**************************************************
'* @Title 易宝支付分账范例
'* @Description 分账支付接口
'* @Copyriht (c) 北京通融通信息有限公司（易宝支付）
'* @Author    yang.yue
'* @Version   V2.0  
'**************************************************


' 开始获取支付表单提交的信息 
 For Each objItem in request.form

	objItemName = Trim(objItem)
	objItemValue = Request.Form(objItem)
    requestMap.add objItem,Request.Form(objItem)
  
Next

' 支付接口只能使用重定向提交方式。1为重定向；2为点对点通讯。
Call doRequestHttp("Buy",requestMap,buyHmacArray,1) 
' 显示返回结果字符串
Response.Write returnStr & "abc"
%>
