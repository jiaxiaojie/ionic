using System;
using System.Text;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using System.Collections.Specialized;
using System.Net;
using System.IO;
using com.yeepay.utils;



namespace com.yeepay.fengzhangbiz
{   
	 // 分账业务处理类
   public class doRequestHttp
	 {
	    public doRequestHttp() 
        {
        }
	    private string [] hmacArray;
	    private string msgTitle;
	   
	   public string returnResult(string actionType,NameValueCollection nvc,string httpFlag)
	   {
	      string hmacResource = "";
	      string postParams = "";
		  string postUrl = "";
         // 从Web.config文件里读取商户密钥
		  string keyvalue = ConfigurationSettings.AppSettings["KeyValue"];               
		  if(actionType == "RefundExt")
         {
             // 从Web.config文件里读取通讯地址
		       postUrl = ConfigurationSettings.AppSettings["distributeRefundUrl"];           
		  }
          else if (actionType == "IndirectRegister")
         {
             // 从Web.config文件里读取网关地址
             postUrl = ConfigurationSettings.AppSettings["distributeRegisterUrl"];        
         }
		  else
		  {
              // 从Web.config文件里读取网关地址
		       postUrl = ConfigurationSettings.AppSettings["distributeUrl"];                 
		  }
		  
		  switch(actionType)
		  {
		     case "Buy":
		     hmacArray = buyHmacArray;
				 msgTitle = "分账支付";
				 break;
				 case "QueryBalance":
				 hmacArray = queryHmacArray;
				 msgTitle = "余额查询";
				 break;  
	       case "RefundExt":
				 hmacArray = refundHmacArray;
				 msgTitle = "分账退款";
				 break;
	       case "IndirectRegister":
	       hmacArray = regMerchantHmacArray;
				 msgTitle = "子商户注册";
				 break;
				 case "PaymentConfirm":
				 hmacArray = payConfirmHmacArray;
				 msgTitle = "分账解冻";
				 break;
		  }
		  
		  
		  
	    for(int i=0;i<hmacArray.Length;i++)
	    {
         hmacResource = hmacResource + nvc[hmacArray[i]];
		     postParams = postParams + hmacArray[i] + "=" + nvc[hmacArray[i]]+"&";
      }
         log.createLog(actionType, "sbold:" + hmacResource, "");
		     string hmac = Digest.HmacSign(hmacResource, keyvalue);
         log.createLog(actionType,"keyvalue"+":"+keyvalue,"hmac"+":"+hmac);
           
			postParams = postParams + "hmac=" + hmac;		   		
			
			if(httpFlag == "1")
      {
             // 记录重定向请求的url
         log.createLog(actionType, "requestUrl:" + postUrl + "?" + postParams, "");
		     return postUrl + "?" + postParams; 
            
			}			
           // 服务器点对点通讯
			else
      {
			
			   StringBuilder strResult = new StringBuilder();
            WebRequest req = WebRequest.Create(postUrl);
            // 记录通讯请求的url
            log.createLog(actionType, "requestUrl:" + postUrl + "?" + postParams, "");
            req.Method = "POST";
            req.ContentType = "application/x-www-form-urlencoded";

            byte[] tempBytes = Encoding.Default.GetBytes(postParams);

            req.ContentLength = tempBytes.Length;
            Stream newStream = req.GetRequestStream();
            newStream.Write(tempBytes, 0, tempBytes.Length);
            newStream.Close();
            WebResponse result = req.GetResponse();
            Stream ReceiveStream = result.GetResponseStream();
            Byte[] read = new Byte[512];
            int bytes = ReceiveStream.Read(read, 0, 512);
            while (bytes > 0)
            {
                // 注意：
                // 下面假定响应使用 UTF-8 作为编码方式。
                // 如果内容以 ANSI 代码页形式（例如，932）发送，则使用类似下面的语句：
                //  Encoding encode = System.Text.Encoding.GetEncoding("shift-jis");
                Encoding encode = System.Text.Encoding.Default;//.GetEncoding(encodingName);
                strResult.Append(encode.GetString(read, 0, bytes));
                bytes = ReceiveStream.Read(read, 0, 512);
            }
			
			     string resultStr = strResult.ToString();

            // 记录通讯返回信息

            log.createLog(actionType, "returnMsg:" + resultStr, "");

            NameValueCollection reParams = new NameValueCollection(); 
	  	  
	  		   string[] aryStr = resultStr.Split('\n');
	  
	         foreach(string i in aryStr)
	         {
	            
							string[] aryParams = i.Split('=');
							reParams.Add(aryParams[0],aryParams[1]);
	         } 	
			 
				  if(reParams["r1_Code"]=="1")
					{
					   string returnStr = msgTitle + "成功！<br>"+resultStr;
					   return returnStr;
					}
					else
					{  
					   string returnStr = msgTitle + "失败！<br>"+resultStr + "<br>失败原因：<font color=red>"+showErrorCode(reParams["r1_Code"])+"</font>"; 		 
				       return returnStr;
					}
			}
		}  
		  
		  
		  
		  
//-----------------------------分账支付请求---------------------------------//
		
		// 分账支付请求参数数组
		public string[] buyHmacArray = {"p0_Cmd","p1_MerId","p2_Order","p3_Amt","p4_Cur","p5_Pid","p6_Url","p7_MP","p8_FrpId","p9_TelNum","pa_Details","pc_AutoSplit","pm_Period","pn_Unit","pr_NeedResponse","py_IsYeePayName"};
		
		// 分账返回参数数组
		public string[] buyReHmacArray = {"p1_MerId","r0_Cmd","r1_Code","r2_TrxId","r3_Amt","r4_Cur","r5_Pid","r6_Order","r8_MP","r9_BType","ra_Details","rb_SplitStatus"};
		
//-----------------------------分账余额查询---------------------------------//
		
		// 分账查询请求参数数组
		public string[] queryHmacArray = {"p0_Cmd","p1_MerId","p2_TargetId","py_IsYeePayName"};
		
		// 分账查询返回参数数组
		public string[] queryReHmacArray = {"r0_Cmd","r1_Code","r2_Blance","r3_Frozen","errorMsg"};
		
//------------------------------分账退款------------------------------------//
		
		// 分账退款请求参数数组
		public string[] refundHmacArray = {"p0_Cmd","p1_MerId","p1_RequestId","p2_TrxId","p3_Desc","p4_Details","p5_Amt"};
		
		// 分账退款返回参数数组
		public string[] refundReHmacArray = {"r0_Cmd","r1_Code","r1_RequestId","r1_Time","errorMsg","r2_OrderAmount","r3_SrcAmount","r4_RefundAmount","r5_MerRefundAmount","r6_Details","r9_BType"};
	
//-----------------------------子商户注册--------------------------------------//
	
	    // 分账子商户注册请求参数数组
        public string[] regMerchantHmacArray = { "p0_Cmd", "p1_MerId"};
		
//--------------------------------分账解冻---------------------------------------//
		
		// 分账解冻请求参数数组
		public string[] payConfirmHmacArray = {"p0_Cmd","p1_MerId","p2_TrxId","py_IsYeePayName"};
		
		// 分账解冻返回参数数组
		public string[] payReConfirmHmacArray = {"r0_Cmd","r1_Code","errorMsg"};


	
		
       // 错误编码列表		
		private string showErrorCode(string err_Code)
		{
		
		    NameValueCollection errorCode = new NameValueCollection(); 
		    errorCode.Add("220000"," RequestId重复");
            errorCode.Add("220001","请求参数无效");
		    errorCode.Add("220002","订单号重复");
		    errorCode.Add("220003","订单号重复");
		    errorCode.Add("220004","远程方法调用失败");
		    errorCode.Add("223000","未找到分账记录");
		    errorCode.Add("223001","分账状态无效(冻结中)");
		    errorCode.Add("223002","分账状态无效(已确认)");
		    errorCode.Add("223003","分账状态无效(已取消)");
		    errorCode.Add("223004","分账状态无效(未冻结)");
		    errorCode.Add("223005","核心系统分账拒绝");
		    errorCode.Add("223006","重复的分账请求");
		    errorCode.Add("223007","分账金额超限");
		    errorCode.Add("223008","分账金额无效");
		    errorCode.Add("221001","交易状态无效(已完成)");
		    errorCode.Add("221002","易状态无效(未完成)");
		    errorCode.Add("221003","交易状态无效(已取消)");
		    errorCode.Add("221004","交易状态无效(已退款)");
		    errorCode.Add("221005","交易金额不匹配");
		    errorCode.Add("221006","核心系统退款拒绝");
		    errorCode.Add("221007","交易金额超限");
		    errorCode.Add("221008","未找到交易记录");
		    errorCode.Add("221009","交易金额无效");
		    errorCode.Add("222001","核心系统注册失败");
		    errorCode.Add("222002","用户无效");
		    errorCode.Add("222003","设置银行卡信息失败");
		    errorCode.Add("222004","设置结算周期失败");
		    errorCode.Add("222005","用户已注册");
		    errorCode.Add("222006","用户已存在");	
		    errorCode.Add("225001","查询结果集太大");
		    errorCode.Add("225002","核心系统拒绝查询");
		    errorCode.Add("224001","退款金额超限");
		    errorCode.Add("224002","退款已执行");	
    		  
		    return errorCode[err_Code];
	   }    
	}	
	
	
	// doResponseHttp类	加密返回时的数据生成hmac
	public class doResponseHttp
	{    	    
	   private string [] hmacArray;
	   private string hmacResource;
      // 从Web.config文件里读取商户密钥
		string keyvalue = ConfigurationSettings.AppSettings["KeyValue"];

       public string getCallBackParams(NameValueCollection nvc)
		{
    		   
		   hmacArray = buyReHmacArray;
    		   
		   for(int i=0;i<hmacArray.Length;i++)
	      {
	      	hmacResource = hmacResource + nvc[hmacArray[i]];    		    
        }
    		  
		   string hmac = Digest.HmacSign(hmacResource, keyvalue);
          //记录服务器返回的hmac和本地收到返回信息后生成的hmac
          log.createLog("Buy", "serverhmac" + ":" + nvc["hmac"], "localhmac" + ":" + "[" + hmac + "]");

          return hmac;          
          
		}

       // 分账返回参数数组
       public string[] buyReHmacArray = { "p1_MerId", "r0_Cmd", "r1_Code", "r2_TrxId", "r3_Amt", "r4_Cur", "r5_Pid", "r6_Order", "r8_MP", "r9_BType", "ra_Details", "rb_SplitStatus" };
			
	}	

}