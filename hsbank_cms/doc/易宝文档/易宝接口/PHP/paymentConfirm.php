<?php

/**
  * @Title 易宝支付分账范例
  * @Description 解冻
  * @Copyriht (c) 北京通融通信息有限公司（易宝支付）
  * @Author    wenhua.cheng
  * @Version   V2.0  
  */
require_once 'Thaw/DistributeBizThaw.class.php';	
require_once 'function/Map.class.php';

$map = new Map();
foreach($_POST as $key=>$value)
{
  $map->put($key, $value);
}
$distributeBizThaw=new DistributeBizThaw();
$rspHttpString=$distributeBizThaw->doRequestHttp("thaw",$map,$thawHmacParaAry,$thawFixParaAry,2,$actionURL);
$resultString=$distributeBizThaw->doResponseHttp($rspHttpString,$thawRspHmacParaAry,2);
if(gettype($resultString)==boolean){
	echo "交易签名被篡改！";
}
else if(gettype($resultString)==object){
		
	// 调用解冻继承类，打印解冻信息
	$paraInfo=$distributeBizThaw->doParaInfo($resultString);
	echo $paraInfo;
	
}
	
	
	
?> 

