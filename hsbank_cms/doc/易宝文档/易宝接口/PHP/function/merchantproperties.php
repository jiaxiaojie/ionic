<?php

 /**
  * @Title 易宝支付分账范例
  * @Description 信息配置类，含商户ID，密钥，请求地址，需加入签名的参数名等信息
  * @Copyriht (c) 北京通融通信息有限公司（易宝支付）
  * @Author    wenhua.cheng
  * @Version   V2.0  
  */
  
/*----------------------------- 接入程序员关注部分 -----------------------------------*/ 
 
// 商户ID。若正式交易请改为正式商户ID
// 正式 商户编号和密钥需测试通过后,联系一下,与贵公司联系的易宝业务人员(非技术支持工程师)获得
$p1_MerIdAry=Array('p1_MerId'=>'10001126856');

// 商户密钥,用于生成hmac(hmac的说明详见文档)
$merchantKey='69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl';


// 分帐支付、余额查询、解冻分帐接口请求地址
$actionURL='https://www.yeepay.com/app-airsupport/AirSupportService.action';


// 退款请求地址
$actionURL_ref="https://www.yeepay.com/app-airsupport/AirSupportCommand.action";


// 子商户注册请求地址
$actionURL_reg="https://www.yeepay.com/selfservice/indirectRegister.action";



 
/*----------------------------- 接入程序员关注部分 ----------------------------------*/ 



/*------------ 日志文件路径 ---------------*/
$logName='YeePay_Distribute.log';


/*---- 支付需加入签名的参数以及固定值 -----*/
// 支付
$payHmacParaAry=Array('p0_Cmd','p1_MerId','p2_Order','p3_Amt','p4_Cur','p5_Pid','p6_Url','p7_MP','p8_FrpId','p9_TelNum','pa_Details',
'pc_AutoSplit','pm_Period','pn_Unit','pr_NeedResponse','py_IsYeePayName');
$payFixParaAry_pro=Array('p0_Cmd'=>'Buy');
$payFixParaAry=array_merge($payFixParaAry_pro,$p1_MerIdAry);

// 支付返回
$payRspHmacParaAry=Array('p1_MerId','r0_Cmd','r1_Code','r2_TrxId','r3_Amt','r4_Cur','r5_Pid','r6_Order','r8_MP','r9_BType','ra_Details','rb_SplitStatus');


/*---- 退款需加入签名的参数以及固定值 -----*/
// 退款
$refundHmacParaAry=Array('p0_Cmd','p1_MerId','p1_RequestId','p2_TrxId','p3_Desc','p4_Details','p5_Amt');


$refundFixParaAry_pro=Array('p0_Cmd'=>'RefundExt');
$refundFixParaAry=array_merge($refundFixParaAry_pro,$p1_MerIdAry);

// 退款返回.errorMsg表示r1_Code为非1时需加入签名的参数
$refundRspHmacParaAry=Array('r0_Cmd','r1_Code','r1_RequestId','r1_Time','errorMsg','r2_OrderAmount','r3_SrcAmount','r4_RefundAmount','r5_MerRefundAmount','r6_Details','r9_BType');




/*---- 子商户注册需加入签名的参数以及固定值 -----*/
// 子商户注册
$subregHmacParaAry=Array('p0_Cmd','p1_MerId');
$subregFixParaAry_pro=Array('p0_Cmd'=>'IndirectRegister');
$subregFixParaAry=array_merge($subregFixParaAry_pro,$p1_MerIdAry);

// 子商户注册为引导到易宝平台，无返回.
// $subregRspHmacParaAry=Array('r0_Cmd','r1_Code','r2_MerId');




/*---- 解冻需加入签名的参数以及固定值 -----*/
// 解冻
$thawHmacParaAry=Array('p0_Cmd','p1_MerId','p2_TrxId');


$thawFixParaAry_pro=Array('p0_Cmd'=>'PaymentConfirm');
$thawFixParaAry=array_merge($thawFixParaAry_pro,$p1_MerIdAry);

// 解冻返回.errorMsg表示r1_Code为非1时需加入签名的参数
$thawRspHmacParaAry=Array('r0_Cmd','r1_Code','errorMsg');




/*---- 错误编码列表 -----*/
// 错误编码
$errorCodeAry=array('220000'=>'RequestId重复','220001'=>'请求参数无效','220002'=>'订单号重复','220003'=>'订单号重复','220004'=>'远程方法调用失败',
'223000'=>'未找到分账记录','223001'=>'分账状态无效(冻结中)','223002'=>'分账状态无效(已确认)','223003'=>'分账状态无效(已取消)','223004'=>'分账状态无效(未冻结)',
'223005'=>'核心系统分账拒绝','223006'=>'重复的分账请求','223007'=>'分账金额超限','223008'=>'分账金额无效','221001'=>'交易状态无效(已完成)',
'221002'=>'易状态无效(未完成)','221003'=>'交易状态无效(已取消)','221004'=>'交易状态无效(已退款)','221005'=>'交易金额不匹配','221006'=>'核心系统退款拒绝',
'221007'=>'交易金额超限','221008'=>'未找到交易记录','221009'=>'交易金额无效','222001'=>'核心系统注册失败','222002' =>'用户无效','222003'=>'设置银行卡信息失败',
'222004'=>'设置结算周期失败','222005'=>'用户已注册','222006'=>'用户已存在','225001'=>'查询结果集太大','225002'=>'核心系统拒绝查询','224001'=>'退款金额超限',
'224002'=>'退款已执行','-1'=>'系统故障');


?> 