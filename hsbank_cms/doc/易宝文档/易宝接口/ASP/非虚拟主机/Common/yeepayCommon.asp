<!-- #include file="merchantProperties.asp" -->

<%
'''''''''''''''''''''''''''''''''''''''''''''''''''
'' @Title 易宝支付分账范例
'' @Description 含数据请求doRequestHttp()，点对点通讯返回回调doResponseHttp()方法
'' @Copyriht (c) 北京通融通信息有限公司（易宝支付）
'' @Author  yang.yue
'' @Version  V2.0
'''''''''''''''''''''''''''''''''''''''''''''''''''



'''''''''''''''''''''''''''''''''''''''''''''
' @Description  发起请求
' @actionType  请求类型，用于日志打印
' @requestMap  表单中所有的参数构造的Map
' @hmacParaAry 需要加入签名的参数数组
' @httpFlag 	 重定向点对点通讯标志位。1为重定向；2为点对点通讯
'''''''''''''''''''''''''''''''''''''''''''''

Set mctSDK = Server.CreateObject("HmacMd5API.HmacMd5Com")

Function doRequestHttp(actionType,requestMap,hmacParaAry,httpFlag)

	If actionType = "RefundExt" then
		reqURL = reqURLRefund
    End If

	If actionType = "IndirectRegister" then
		reqURL = reqURLReg
    End If
		
	Set hmacMap = Server.CreateObject( "Scripting.Dictionary")
	hmacSource=""
	requestString=""    
	returnString=""           
	requestMap.add merIdName,p1_MerId

	
	' 加需要加入签名的参数键值对放入requestMap
	For i=0 to ubound(hmacParaAry)
		If requestMap.Exists(hmacParaAry(i))=false Then
			requestMap.add hmacParaAry(i),""
		End If
	Next
	
	' 生成签名源
	For i=0 to ubound(hmacParaAry)
		hmacSource=hmacSource&requestMap.item(hmacParaAry(i))
	Next	
	
	' 生成需传递的Hmac
	hmacValue=mctSDK.HmacMd5(hmacSource,keyValue)
	' 将生成的hmac加入到集合中
	requestMap.add "hmac",hmacValue
	
	itemKeys=requestMap.keys
	itemValue=requestMap.Items   
	For  each  itemObject in itemKeys
		requestStringItem=itemObject&"="&Server.URLEncode(requestMap.item(itemObject))&"&"
		requestString=requestString&requestStringItem
	Next
	' 拼凑写入日志的字符串
	writestr = cstr(now) & "," & cstr(timer) & "["&actionType&"] ,[KeyValue]" & keyValue & ",[sbold]" & hmacSource & ",[hmac]" & hmacValue
	
    Call logStr(logFileName,writestr)
	' 重定向
	If httpFlag=1 Then 	
		writestr = cstr(now) & "," & cstr(timer) & ",["&actionType&"] ,[url]" & reqURL & ",[content]" & requestString 
    	Call logStr(logFileName,writestr)
		doRequestHttp=reqURL&"?"&requestString
		response.write "重定向的doRequestHttp"&"</br>"
		response.write "<a target='_blank' href='"& doRequestHttp &"'>" & doRequestHttp & "</a>"
	
	' 发送点对点请求
	Else If httpFlag=2  Then 
	' 拼凑写入日志的字符串
	writestr = cstr(now) & "," & cstr(timer) & "["&actionType&"] ,[KeyValue]" & keyValue & ",[sbold]" & hmacSource & ",[hmac]" & hmacValue & "[reqURL]" & reqURL & "[requestString]" & requestString
	' 将通讯提交参数写入日志
    Call logStr(logFileName,writestr)
		set objHttp = Server.CreateObject("MSXML2.ServerXMLHTTP")
		objHttp.open "post", reqURL , false
		objHttp.setRequestHeader "Content-Type","application/x-www-form-urlencoded"
    	objHttp.setRequestHeader "Connection", "Keep-Alive"
    	objHttp.Send requestString

		If (objHttp.status <> 200 ) Then
	        	writestr = cstr(now) & "," & cstr(timer) & "["&actionType&"]" & ",[objHttp.status]" & objHttp.status
	        	' 将通讯返回参数写入日志
			Call logStr(logFileName,writestr)
		    ' HTTP 错误处理
	  		response.Write("Status="&objHttp.status&"服务器异常")
			response.End
		Else
    		'strCallBack = objHttp.ResponseText
            strCallBack = BytesToBstr(objHttp.ResponseBody)

	    	set objHttp = nothing

	        writestr = cstr(now) & "," & cstr(timer) & "["&actionType&"]" & ",[strCallBack]" & strCallBack
		' 将通讯返回参数写入日志
 
        	Call logStr(logFileName,writestr)

			actionName = ""
			Select Case actionType
	   		Case "PaymentConfirm"
	    	backResHmacArray= PaymentConfirmResHmacArray
	    	Case "RefundExt"
	    	backResHmacArray= RefundExtResHmacArray
	    	Case "IndirectRegister"
	    	backResHmacArray= RegistMerchantResHmacArray
			End Select

        	resultStr =  doResponseHttp(actionType,strCallBack,backResHmacArray)
	    	doRequestHttp = resultStr	
	 	End If 
	  
	End If 
	End If 
	
	
End Function


'''''''''''''''''''''''''''''''''''''''''''''
' @Description  接收返回
' @strCallBack  返回流
' @rspHmacParaAry 需要加入签名的参数数组
'''''''''''''''''''''''''''''''''''''''''''''

Function doResponseHttp(actionType,strCallBack,rspHmacParaAry)    
	actionName = ""
	Select Case actionType
	   Case "PaymentConfirm"
	   actionName = "解冻支付款项"
	   Case "RefundExt"
	   actionName = "退款操作"
	   Case "IndirectRegister"
	   actionName = "子商户注册"
	   Case "QueryBalance"
	   actionName = "账户余额查询"
	End Select
	
	hmacSource = ""
	Set rspMap = Server.CreateObject( "Scripting.Dictionary")
	aryCallBack = Split(strCallBack,vbLf)
	returnMsg = ""
	
	For i = LBound(aryCallBack) To UBound(aryCallBack)
		If InStr(aryCallBack(i),"=") =< 0 Then
			returnMsg = strCallBack
			doResponseHttp = returnMsg				
			Exit Function
		End If

		aryReturn = Split(aryCallBack(i),"=")
		sKey = aryReturn(0)
		sValue = aryReturn(1)
	    ' 将返回参数写入Dictionary
		rspMap.add sKey,sValue
	Next

	writestr = cstr(now) & "," & cstr(timer) & "["&actionType&"] ,[returnString]" & strCallBack
		
    Call logStr(logFileName,writestr)
	
	If rspMap.item("r1_Code")=1 Then 
		
		For i=0 to ubound(rspHmacParaAry)
			hmacSource=hmacSource&rspMap.item(rspHmacParaAry(i))
		Next
		' 拼凑生成Hmac的字符串
		hmacValue=mctSDK.HmacMd5(hmacSource,keyValue)	
		' 生成Hmac
		If hmacValue = rspMap.item("hmac") Then	
			resultStr = actionName & "成功<br>"
			itemKeys=rspMap.keys	     
	    For Each itemObject In itemKeys
		 	resultStr = resultStr & itemObject & "=" & rspMap.item(itemObject) & "<br>"
	    Next
		doResponseHttp = resultStr
		Else
		resultStr = actionName & "<br>交易签名被篡改！"
		doResponseHttp = resultStr		
		End if
	Else 
         
	     resultStr = actionName & "失败：<br>"
		 
		 itemKeys=rspMap.keys 
	     For each itemObject in itemKeys
		 	resultStr = resultStr & itemObject & "=" & rspMap.item(itemObject) & "<br>"
	     Next
		 
		 For i = 0 to Ubound(errorCodeAry)
		   If instr(errorCodeAry(i),rspMap.Item("r1_Code")) then
		      resultStr = resultStr & "错误原因：<font color='red'>" & replace(errorCodeAry(i),rspMap.Item("r1_Code")&"=","") & "</font><br>"
			  Exit For
		   End If
		   
		 Next
		 
         doResponseHttp = resultStr
		 
	End If 
		
End function



' 日志
Sub logStr(logFileName, str)
    filename = "../" & logFileName
    content = str
    
    Set FSO = Server.CreateObject("Scripting.FileSystemObject")   
    Set TS = FSO.OpenTextFile(Server.MapPath(filename),8,true,-1) 
    TS.write content   
    TS.Writeline ""
    TS.Writeline ""
    Set TS = Nothing   
    Set FSO = Nothing   
End Sub


' 商户收到易宝返回信息后业务处理函数
Function Business(paraMap)

	Response.Write "<br> Return parameter list:<br>"
    ' 将接收到的参数显示出来
    For i=0 to Ubound(BuyResHmacArray)
		Response.Write BuyResHmacArray(i) & " = " & paraMap.Item(BuyResHmacArray(i)) & "<br>"	   
    Next
   
End function

Function BytesToBstr(body)
set objstream = Server.CreateObject("adodb.stream")
objstream.Type = 1
objstream.Mode = 3
objstream.Open
objstream.Write body
objstream.Position = 0
objstream.Type = 2
objstream.Charset = "gb2312"
BytesToBstr = objstream.ReadText 
objstream.Close
set objstream = nothing
End Function

%>