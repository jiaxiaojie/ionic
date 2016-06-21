package com.yeepay.distribute;

import com.yeepay.util.Configuration;
import java.util.Map;
import com.yeepay.util.UpgradeMap;

// 信息类
public class Info {
	private static String merchantID = Configuration.getInstance().getValue("merchantID");
	private static String keyValue = Configuration.getInstance().getValue("keyValue");
	private static String distributePayUrl = Configuration.getInstance().getValue("distributePayUrl");
	private static String distributeRefundExtUrl = Configuration.getInstance().getValue("distributeRefundExtUrl");
	private static String distributeRegistMerchantUrl = Configuration.getInstance().getValue("distributeIndirectRegisterUrl");
	private static String distributePaymentConfirmUrl = Configuration.getInstance().getValue("distributePaymentConfirmUrl");

	private static String byteCharsetName = "gbk";

	public static String getByteCharsetName(){
		return byteCharsetName;
	}
	public static String getMerchantID(){
		return merchantID;
	}
	public static String getKeyValue(){
		return keyValue;
	}
	public static String getDistributePayUrl(){
		return distributePayUrl;
	}
	public static String getDistributeRefundExtUrl(){
		return distributeRefundExtUrl;
	}
	public static String getDistributeRegistMerchantUrl(){
		return distributeRegistMerchantUrl;
	}
	public static String getDistributePaymentConfirmUrl(){
		return distributePaymentConfirmUrl;
	}
	
	
	// 支付
	public static String[] getDistributePayHmacOrder(){
		return new String[]{"p0_Cmd", "p1_MerId", "p2_Order", "p3_Amt", "p4_Cur", "p5_Pid", "p6_Url", 
				"p7_MP", "p8_FrpId", "p9_TelNum", "pa_Details", "pc_AutoSplit", "pm_Period", 
				"pn_Unit", "pr_NeedResponse", "py_IsYeePayName"};
	}
	public static Map getDistributePayFixParameter(){
		Map returnMap = new UpgradeMap();
		returnMap.put("p0_Cmd", "Buy");
		returnMap.put("p1_MerId", getMerchantID());
		//returnMap.put("p4_Cur", "RMB");
		returnMap.put("pr_NeedResponse", "1");
		returnMap.put("py_IsYeePayName", "1");
		return returnMap;
	}
	public static String[] getDistributePayCallbackHmacOrder(){
		return new String[] {"p1_MerId", "r0_Cmd", "r1_Code", "r2_TrxId", "r3_Amt", "r4_Cur", "r5_Pid",
				"r6_Order", "r8_MP", "r9_BType", "ra_Details", "rb_SplitStatus", "rc_SourceFee", "rd_TargetFee"};
	}
	
	
	// 退款
	public static String[] getDistributeRefundExtHmacOrder(){
		return new String[] {"p0_Cmd", "p1_MerId", "p1_RequestId", "p2_TrxId", "p3_Desc",
				"p4_Details", "p5_Amt"};
	}
	public static Map getDistributeRefundExtFixParameter(){
		Map returnMap = new UpgradeMap();
		returnMap.put("p0_Cmd", "RefundExt");
		returnMap.put("p1_MerId", getMerchantID());
		returnMap.put("py_IsYeePayName", "1");
		return returnMap;
	}
	public static String[] getDistributeRefundExtBackHmacOrder(){
		return new String[] {"r0_Cmd", "r1_Code", "r1_RequestId", "r1_Time", "r2_OrderAmount", 
				"r3_SrcAmount", "r4_RefundAmount", "r5_MerRefundAmount", "r6_Details", "errorMsg", "r9_BType"};
	}
	
	
	// 子商户注册
	public static String[] getDistributeRegistMerchantHmacOrder(){
		return new String[] {"p0_Cmd", "p1_MerId", "p2_RemoteLoginName", "p8_Url", "pa0_Mode", 
				"pa1_Mode", "pa2_Mode"};
	}

		//"p0_Cmd", "p1_MerId", "p2_LoginName", "p3_Password", "p4_RealName", "p5_BriefName",
		//				"p6_Email", "p7_Telephone", "p8_MobilePhone", "p9_Question", "pa_Answer"};
	public static Map getDistributeRegistMerchantFixParameter(){
		Map returnMap = new UpgradeMap();
		returnMap.put("p0_Cmd", "IndirectRegister");//RegistMerchant
		returnMap.put("p1_MerId", getMerchantID());
		return returnMap;
	}
	public static String[] getDistributeRegistMerchantBackHmacOrder(){
		return new String[] {"p1_MerId", "r0_Cmd", "r1_Code", "r2_AgentUserName", 
				"r3_AgentCustomerNumber"};
	}
	//"r0_Cmd", "r1_Code", "rc_bailAmountRetCode", "r2_MerId", "errorMsg"


	// 解冻分账
	public static String[] getDistributePaymentConfirmHmacOrder(){
		return new String[] {"p0_Cmd", "p1_MerId", "p2_TrxId"};//, "py_IsYeePayName"};
	}
	public static Map getDistributePaymentConfirmFixParameter(){
		Map returnMap = new UpgradeMap();
		returnMap.put("p0_Cmd", "PaymentConfirm");
		returnMap.put("p1_MerId", getMerchantID());
		returnMap.put("py_IsYeePayName", "1");
		return returnMap;
	}
	public static String[] getDistributePaymentConfirmBackHmacOrder(){
		return new String[] {"r0_Cmd", "r1_Code", "errorMsg"};
	}
}
