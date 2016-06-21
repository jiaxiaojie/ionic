<!-- #include file="../common/yeepayCommon.asp" -->
<%
'**************************************************
'* @Title 易宝支付分账范例
'* @Description 获取支付通知结果，并预留业务处理模块
'* @Copyriht (c) 北京通融通信息有限公司（易宝支付）
'* @Author    yang.yue
'* @Version   V2.0  
'**************************************************
'在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
Set backMap = Server.CreateObject( "Scripting.Dictionary")

' 开始获得通知返回参数
For Each objItem in request.querystring
	objItemName = trim(objItem)
	objItemValue = request(objItem)
	backMap.add objItem,request(objItem)
Next

' 将通知返回字符串写入日志
writestr = cstr(now) & "," & cstr(timer) & ",[callback],[str]" & request.QueryString
Call logStr(logFileName,writestr)

' 开始生成本地Hmac并对返回的Hmac进行比对
For i=0 to ubound(BuyResHmacArray)
    ' 拼凑生成hmac的字符串        
	hmacSource=hmacSource&backMap.item(BuyResHmacArray(i))  
Next
' 生成本地hmac	
localHmac = HmacMd5(hmacSource,keyValue)  
' 获得返回hmac	
returnHmac = backMap.item("hmac")         
' 判断交易签名(hmac)是否一致	
If localHmac <> returnHmac Then
	Response.Write "交易签名被篡改"
	Response.End 
End If		
	
	
If cint(backMap.item("r1_Code")) = 1 Then

	If backMap.item("r9_BType") = 1 Then 
	Response.Write "支付成功！"
	ElseIf backMap.item("r9_BType") = 2 Then 
	    ' 回写success，大小写不敏感
    	Response.Write "success"    
	End If
	' 调用商户业务处理函数
	Call Business(backMap) 	
	   
End If

%>