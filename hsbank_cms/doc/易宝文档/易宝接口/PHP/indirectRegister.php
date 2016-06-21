<?php

/**
  * @Title 易宝支付分账范例
  * @Description 子商户注册
  * @Copyriht (c) 北京通融通信息有限公司（易宝支付）
  * @Author    wenhua.cheng
  * @Version   V2.0  
  */
require_once 'Subreg/DistributeBizSubreg.class.php';	
require_once 'function/Map.class.php';

$map = new Map();
foreach($_POST as $key=>$value)
{
  $map->put($key, $value);
}

// 重定向方式提交，引导到易宝网关注册
$distributeBizSubreg=new DistributeBizSubreg();
$rspHttpString=$distributeBizSubreg->doRequestHttp("subreg",$map,$subregHmacParaAry,$subregFixParaAry,1,$actionURL_reg);


	
?> 

