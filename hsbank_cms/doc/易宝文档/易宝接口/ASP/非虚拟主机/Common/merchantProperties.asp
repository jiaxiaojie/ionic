<%
'**************************************************
'* @Title 易宝支付分账范例
'* @Description 信息配置类，含商户ID，密钥，请求地址，需加入签名的参数名等信息
'* @Copyriht (c) 北京通融通信息有限公司（易宝支付）
'* @Author    yang.yue
'* @Version   V2.0  
'**************************************************

'*----------------------------- 接入程序员关注部分 -----------------------------*' 
    

' 商户编号（ID）。若正式交易请改为正式商户ID
' 正式 商户编号和密钥需测试通过后,联系一下,与贵公司联系的易宝业务人员(非技术支持工程师)获得
Dim p1_MerId

' 商户密钥,用于生成hmac(hmac的说明详见文档)
Dim keyValue

' 测试用商户编号
merIdname="p1_MerId"
p1_MerId = "10001126856"
' 测试用商户密钥
keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"

' 生成日志的文件名
logFileName = "YeePay_Distribute.log"
 

' 正式地址
reqURL= "https://www.yeepay.com/app-airsupport/AirSupportService.action"  
' 子商户退款地址
reqURLRefund = "http://www.yeepay.com/app-airsupport/AirSupportCommand.action"
' 子商注册地址
reqURLReg = "https://www.yeepay.com/selfservice/indirectRegister.action"

Set requestMap = Server.CreateObject( "Scripting.Dictionary")

'*----------------------------- 分账需加入签名的参数以及固定值 ------------------------------*'


' 分账请求生成hmac的参数
Dim buyHmacArray
buyHmacArray = Array("p0_Cmd","p1_MerId","p2_Order","p3_Amt","p4_Cur","p5_Pid","p6_Url","p7_MP","p8_FrpId","p9_TelNum","pa_Details","pc_AutoSplit","pm_Period","pn_Unit","pr_NeedResponse","py_IsYeePayName")


' 分账返回生成hmac的参数 ,"rb_BankId","ro_BankOrderId","rp_PayDate"
Dim BuyResHmacArray
BuyResHmacArray = Array("p1_MerId","r0_Cmd","r1_Code","r2_TrxId","r3_Amt","r4_Cur","r5_Pid","r6_Order","r8_MP","r9_BType","ra_Details","rb_SplitStatus")


'*----------------------------- 退款需加入签名的参数以及固定值 ------------------------------*'
' 退款请求参数
Dim RefundExtHmacArray
RefundExtHmacArray = Array("p0_Cmd","p1_MerId","p1_RequestId","p2_TrxId","p3_Desc","p4_Details","p5_Amt")
' 退款返回参数
Dim RefundExtResHmacArray
RefundExtResHmacArray = Array("r0_Cmd","r1_Code","r1_RequestId","r1_Time","errorMsg","r2_OrderAmount","r3_SrcAmount","r4_RefundAmount","r5_MerRefundAmount","r6_Details","r9_BType")



'*----------------------------- 子商户注册需加入签名的参数以及固定值 ------------------------------*'
' 子商户注册请求参数
Dim RegistMerchantHmacArray
RegistMerchantHmacArray = Array("p0_Cmd","p1_MerId","p2_RemoteLoginName","p8_Url","pa0_Mode","pa1_Mode","pa2_Mode")
' 子商户注册返回参数
Dim RegistMerchantResHmacArray
RegistMerchantResHmacArray = Array("p1_MerId","r0_Cmd","r1_Code","r2_AgentUserName","r3_AgentCustomerNumber")



'*----------------------------- 解冻需加入签名的参数以及固定值 ------------------------------*'
' 解冻请求参数
Dim PaymentConfirmHmacArray
PaymentConfirmHmacArray = Array("p0_Cmd","p1_MerId","p2_TrxId")
' 解冻返回参数
Dim PaymentConfirmResHmacArray
PaymentConfirmResHmacArray = Array("r0_Cmd","r1_Code","errorMsg")


'*---- 错误编码列表 -----*'
Dim errorCodeAry
errorCodeAry = Array("220000=RequestId重复","220001=请求参数无效","220002=订单号重复","220003=订单号重复","220004=远程方法调用失败","223000=未找到分账记录","223001=分账状态无效(冻结中)","223002=分账状态无效(已确认)","223003=分账状态无效(已取消)","223004=分账状态无效(未冻结)","223005=核心系统分账拒绝","223006=重复的分账请求","223007=分账金额超限","223008=分账金额无效","221001=交易状态无效(已完成)","221002=易状态无效(未完成)","221003=交易状态无效(已取消)","221004=交易状态无效(已退款)","221005=交易金额不匹配","221006=核心系统退款拒绝","221007=交易金额超限","221008=未找到交易记录","221009=交易金额无效","222001=核心系统注册失败","222002=用户无效","222003=设置银行卡信息失败","222004=设置结算周期失败","222005=用户已注册","222006=用户已存在","225001=查询结果集太大","225002=核心系统拒绝查询","224001=退款金额超限","224002=退款已执行")
%>