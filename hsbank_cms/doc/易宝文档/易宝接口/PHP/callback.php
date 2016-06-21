<?php

 /**
  * @Title 易宝支付分账范例
  * @Description 回调页面，用户通知商户处理是否发货等业务逻辑
  * @Copyriht (c) 北京通融通信息有限公司（易宝支付）
  * @Author    wenhua.cheng
  * @Version   V2.0  
  */
 
require_once 'pay/DistributeBizPay.class.php';
require_once 'business.php';

// 向易宝回写文本 
echo "success";
//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
// 获得易宝发起的回调的QUERY_STRING,并进行urldecode
$rspHttpString=urldecode($_SERVER["QUERY_STRING"]);

$distributeBizPay=new DistributeBizPay();
$resultString=$distributeBizPay->doResponseHttp($rspHttpString,$payRspHmacParaAry,1);

if(gettype($resultString)==boolean){
	echo "交易签名被篡改!";
	
}

else if(gettype($resultString)==object){


// Hmac校验正常，获得支付结果。1为成功
$r1CodeValue=$resultString->getValueAt("r1_Code");
$r9BTypeValue=$resultString->getValueAt("r9_BType");

if ($r1CodeValue==1){

     /*--------------------------------------------------------------------------------------*/
	 // 接入程序员关注部分
	 // 调用您的业务逻辑处理函数，比如：更新商户数据库的商品是否发货的状态（重复订单的校验），进行商品价格校验等
	 // doSuccessAfterPay(),doFailAfterPay();函数体在business.php添加
	/*--------------------------------------------------------------------------------------*/
	// 重定向. 通常此处显示商户的用户可以看到的支付结果
	if($r9BTypeValue==1){
		// echo "这里是浏览者能看到的信息";
		doSuccessAfterPay();
	
	}
	
	// 点对点。通常此处商户处理自己的业务，比如：更新商户数据库的商品是否发货的状态（重复订单的校验），进行商品价格校验等
	else if($r9BTypeValue==2){
		// echo "这里是服务器点点对点通知";
		doSuccessAfterPay();
	
	
	}

}

// 支付失败
else{
	doFailAfterPay();
}
	  
	 
	}
	
?> 

