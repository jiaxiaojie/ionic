<?php

 /**
  * @Title 易宝支付分账范例
  * @Description 退款请求
  * @Copyriht (c) 北京通融通信息有限公司（易宝支付）
  * @Author    wenhua.cheng
  * @Version   V2.0  
  */
require_once 'Refund/DistributeBizRefund.class.php';	
require_once 'function/Map.class.php';

$map = new Map();
foreach($_POST as $key=>$value)
{
  $map->put($key, $value);
}

$distributeBizRefund=new DistributeBizRefund();
$rspHttpString=$distributeBizRefund->doRequestHttp("refund",$map,$refundHmacParaAry,$refundFixParaAry,2,$actionURL_ref);
$resultString=$distributeBizRefund->doResponseHttp($rspHttpString,$refundRspHmacParaAry,2);
if(gettype($resultString)==boolean){
	echo "交易签名被篡改！";
	}
else if(gettype($resultString)==object){
	// 退款继承类，打印退款信息
	$paraInfo=$distributeBizRefund->doParaInfo($resultString);
	echo $paraInfo;
	
	}
	
	
	
?> 

