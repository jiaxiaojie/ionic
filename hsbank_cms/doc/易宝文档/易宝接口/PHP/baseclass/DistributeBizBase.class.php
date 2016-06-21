<?php

 /**
  * @Title 易宝支付分账范例
  * @Description 基类，含数据请求doRequestHttp()，点对点通讯返回/回调doResponseHttp(),签名校验checkHmac()等方法
  * @Copyriht (c) 北京通融通信息有限公司（易宝支付）
  * @Author    wenhua.cheng
  * @Version   V2.0  
  */
  
ob_start();
require_once 'function/merchantProperties.php';
require_once 'function/HttpClient.class.php';
require_once 'function/Map.class.php';
class DistributeBizBase{

/*--------- 分隔符及固定参数名 -------------*/
// 返回文本参数分割符
private $rspHttpParaFlag="\n";

// 回调参数分隔符
private $rspCallbackParaFlag="&";

// 键名与键值分割符
private $paraItemFlag="=";

// hmac键名
private $paraHmacName="hmac";

// 错误代码键名
private $codeFlag="r1_Code";



/**
 * @Description 遍历的Map
 * @$map  传入的Map
 * @$typeFlag 为1：生成拼接参数值作为签名源；为2：生成需要传递的参数名/值以及Hmac的请求串
 */
function traverseMap($map, $typeFlag){
	$resultString="";
	foreach($map as $key=>$value){
		if($typeFlag==1){
			//$value=urldecode($value); 
			$resultString.=$value;
	}
	else if($typeFlag==2){
        $querystring = '';
    		foreach($map as $key=>$val){
    				$querystring .= urlencode($key).'='.urlencode($val).'&';
    			}
    		$querystring = substr($querystring, 0, -1); 
    	return $querystring;
    }
	else {
			echo "遍历Map类型标志位出错";
	}
	}
	return $resultString;
}


/**
 * @Description  发起请求
 * @$actionType  请求类型，用于日志打印
 * @$requestMap  表单中所有的参数构造的Map
 * @$hmacParaAry 需要加入签名的参数数组
 * @$fixParaAry  固定值的参数
 * @$httpFlag 	 重定向/点对点通讯标志位。1为重定向；2为点对点通讯
 */
public function doRequestHttp($actionType,$requestMap,$hmacParaAry, $fixParaAry,$httpFlag,$actionURL)
{	
	global $merchantKey;
	
	// 实例化需要加入签名的Map
	$hmacMap=new Map();
	
	// 将含的固定值参数键值对copy进传入的Map
	$requestMap->copyData($fixParaAry);
	
	// 加需要加入签名的参数键值对放入hmacMap
	foreach($hmacParaAry as $value){
		$returnValue=$requestMap->getValueAt($value);
		$hmacMap->put($value,$returnValue);
	}
	
	// 生成签名源
	$hmacSource=$this->traverseMap($hmacMap,1);
	
	// 生成需传递的Hmac
	$paraHmacValue=$this->HmacMd5($hmacSource, $merchantKey);
	
	// 记录生成签名的日志
	$this->loghmac($hmacSource,$merchantKey,$paraHmacValue);
	
	// 放入需传递的Hmac键值
	$requestMap->put($this->paraHmacName,$paraHmacValue);

	// 拼接请求参数
	$resultHttpString=$this->traverseMap($requestMap,2);
	$actionString=$actionURL.'?'.$resultHttpString;
	
	// 记录发起请求日志
	$this->logurl($actionType,$actionString);
	
	// 重定向
	if($httpFlag==1){
		header("Location:".$actionString);
	    exit;
	}
	
	// 点对点通讯
	else if($httpFlag==2)  {
	
	// 发起请求
	$pageContents=HttpClient::quickPost($actionURL,$resultHttpString);
	
	$this->logurl($actionType,$pageContents);
	// echo $pageContents;
	if($pageContents==""){
		echo "连接超时!";
	}
	else {
	    return $pageContents;
	}
	}
}


/**
 * @Description      接收响应
 * @$responseString  易宝返回数据或回调时易宝向商户请求的链接
 * @$rspHmacParaAry  需要加入签名的参数数组
 * @$isRspFlag   	 回调/通讯提交返回标志位。1为回调；2为通讯提交返回
 */
public function doResponseHttp($responseString,$rspHmacParaAry,$isRspFlag)
{	
	global $errorCodeAry;
	
	// Hamc校验正常后需获得的信息
    $rspMapAll=new Map();
	
	$rspHmacMap=new Map();
	$rspHmacValue="";
	
	//  分割回调的参数键值，此时分隔符http默认的"&"
	if($isRspFlag==1){
		$result=explode($this->rspCallbackParaFlag,$responseString);
	}
	
	// 分调点对点通讯易宝返回的文本，此时分隔符为"\n"
	else if($isRspFlag==2){
		$result=explode($this->rspHttpParaFlag,$responseString);
		}
	
	// 将获取的信息拆分成键值对放入Map	
	for($index=0;$index<count($result);$index++){		
		$result[$index] = trim($result[$index]);
		if (strlen($result[$index]) == 0) {
			continue;
		}
		$aryReturn= explode($this->paraItemFlag,$result[$index]);
		$sKey= $aryReturn[0];
		$sValue	= $aryReturn[1];
		$rspMapAll->put($sKey,$sValue);
		}
	$resultCode=$rspMapAll->getValueAt($this->codeFlag);

	$rspHmacValue=$rspMapAll->getValueAt($this->paraHmacName);
	foreach($rspHmacParaAry as $value){
		$returnValue=$rspMapAll->getValueAt($value);
		$rspHmacMap->put($value,$returnValue);
	}
		

	if($this->checkHmac($rspHmacMap,$rspHmacValue)){
			
		
		// 如果是回调或者通讯提交返回为1则return $rspMapAll
		if($resultCode==1&&$isRspFlag==2 || $isRspFlag==1){
		
			return $rspMapAll;
		}
		else  if($resultCode!=1&&$isRspFlag==2){
		
          // 如果是通讯提交返回r1_Code	
			if(array_key_exists($resultCode,$errorCodeAry)){
			 	$errorCodeValue=$rspMapAll->getValueAt('errorMsg');
				if($errorCodeValue==''){
					$errorCodeValue=$errorCodeAry[$resultCode];
				}
				$errorResult="错误代码:".$resultCode."</br>"."错误原因:".$errorCodeValue;
	 		}		
			
	 		else{
				 $errorResult="未知错误！";
			}
			
			echo $errorResult;
	}
		}	
	else {
	
	
	
		return  false;
	}

	
		// 记录日志
		$this->logurl("通讯返回",$errorResult);
	
}



/*-----------------------------------*/
//-- 校验hmac
/*-----------------------------------*/
function checkHmac($sourceMap,$hmacValue){
	global $merchantKey;
	$hmacSource=$this->traverseMap($sourceMap,1);
	$localHmacValue=$this->HmacMd5($hmacSource, $merchantKey);
	if($localHmacValue==$hmacValue){

		return true;
	}
	else{
	
		return false;
	}
}



/*------------------------------*/
//-- 生成hmac的函数
/*-----------------------------*/
public function HmacMd5($data,$key)
{
	//logurl("iconv Q logdata",$data);
	$logdata=$data;
	$logkey=$key;

	// RFC 2104 HMAC implementation for php.
	// Creates an md5 HMAC.
	// Eliminates the need to install mhash to compute a HMAC
	// Hacked by Lance Rushing(NOTE: Hacked means written)

	// 需要配置环境支持iconv，否则中文参数不能正常处理
	$ke=iconv("GB2312","UTF-8",$key);
	$data=iconv("GB2312","UTF-8",$data);
	$b=64; // byte length for md5
	if (strlen($key) > $b) {
			$key = pack("H*",md5($key));
								}
	$key=str_pad($key, $b, chr(0x00));
	$ipad=str_pad('', $b, chr(0x36));
	$opad=str_pad('', $b, chr(0x5c));
	$k_ipad=$key ^ $ipad ;
	$k_opad=$key ^ $opad;
	$log_hmac=md5($k_opad . pack("H*",md5($k_ipad . $data)));
	return md5($k_opad . pack("H*",md5($k_ipad . $data)));
}


/**
 * @Description   记录请求URL到日志
 * @$title        日志类型
 * @$content      打印的数据
 */
public function logurl($title,$content)
{
	global $logName;
	$james=fopen($logName,"a+");
	date_default_timezone_set(PRC);
	fwrite($james,"\r\n".date("Y-m-d H:i:s,A")." 【".$title."】   ".$content."\n");
	fclose($james);
}


/*------------------------------*/
//-- 记录生成hmac时的日志信息
/*-----------------------------*/
public function loghmac($str,$merchantKey,$hmac)
{
	global $logName;
	global $merchantKey;
	$james=fopen($logName,"a+");
	date_default_timezone_set(PRC);
	fwrite($james,"\r\n".date("Y-m-d H:i:s,A")."  [构成签名的参数:]".$str."  [商户密钥:]".$merchantKey."   [本地HMAC:]".$hmac);
	fclose($james);
}
}

?>