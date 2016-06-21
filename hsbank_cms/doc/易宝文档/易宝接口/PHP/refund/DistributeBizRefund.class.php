<?php

/**
  * @Title 易宝支付分账范例
  * @Description 退款继承类
  * @Copyriht (c) 北京通融通信息有限公司（易宝支付）
  * @Author    wenhua.cheng
  * @Version   V2.0  
  */
require_once 'baseclass/DistributeBizBase.class.php';

class DistributeBizRefund extends  DistributeBizBase
{
function doParaInfo($sourceString){
	$paraInfo="退款成功";
	return $paraInfo;

}



}

?>