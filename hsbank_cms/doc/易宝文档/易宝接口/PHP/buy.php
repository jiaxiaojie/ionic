<?php

 /**
  * @Title 易宝支付分账范例
  * @Description 支付请求
  * @Copyriht (c) 北京通融通信息有限公司（易宝支付）
  * @Author    wenhua.cheng
  * @Version   V2.0  
  */


require_once 'pay/DistributeBizPay.class.php';	
require_once 'function/Map.class.php';
require_once 'business.php';


$map = new Map();
foreach($_POST as $key=>$value)
{
  $map->put($key, $value);
} 


/*-----------------------------------------------*/
// 接入程序员关注
// 获取表单提交数据, 商户根据自己需要做业务处理。
/*-----------------------------------------------*/
// 函数体在business.php 中实现，建议此处将订单号以及产品信息写入数据库，并置标志位示"未支付"
doBeforPay();


// 重定向方式提交，请求到易宝网关
$distributeBizPay=new DistributeBizPay();
$rspHttpString=$distributeBizPay->doRequestHttp("pay",$map,$payHmacParaAry,$payFixParaAry,1,$actionURL);

?> 

