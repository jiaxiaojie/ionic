package com.yeepay.util;

import java.util.Map;

import com.yeepay.util.UpgradeMap;
import com.yeepay.util.ProcessUtil;

public class errorMsgInfo{
	static{
		methodsAtStaticBlock();
	}
	private static Map errorMsg = null;
	private static String errorMsgCode = "gbk";
	private static void methodsAtStaticBlock(){
		initErrorMsg();
	}
	public static String getErrorMsg(String key){
		String returnString = (String)errorMsg.get(key);
		return returnString;
	}
	private static void changeErrorMsgCode(String afterChangeCode){
		String beforeChangeCode = errorMsgCode;
		errorMsgCode = afterChangeCode;
		errorMsg = ProcessUtil.changeMapCode(errorMsg, beforeChangeCode, afterChangeCode);
	}
	private static void initErrorMsg(){
		errorMsg = getDefaultErrorMsg();
		changeErrorMsgCode("iso-8859-1");
	}
	private static Map getDefaultErrorMsg(){
		Map returnMap = new UpgradeMap();
		returnMap.put("220000", "RequestId重复");
		returnMap.put("220001", "请求参数无效");
		returnMap.put("220002", "订单号重复");
		returnMap.put("220003", "订单号重复");
		returnMap.put("220004", "远程方法调用失败");
		returnMap.put("223000", "未找到分账记录");
		returnMap.put("223001", "分账状态无效(冻结中)");
		returnMap.put("223002", "分账状态无效(已确认)");
		returnMap.put("223003", "分账状态无效(已取消)");
		returnMap.put("223004", "分账状态无效(未冻结)");
		returnMap.put("223005", "核心系统分账拒绝");
		returnMap.put("223006", "重复的分账请求");
		returnMap.put("223007", "分账金额超限");
		returnMap.put("223008", "分账金额无效");
		returnMap.put("221001", "交易状态无效(已完成)");
		returnMap.put("221002", "交易状态无效(未完成)");
		returnMap.put("221003", "交易状态无效(已取消)");
		returnMap.put("221004", "交易状态无效(已退款)");
		returnMap.put("221005", "交易金额不匹配");
		returnMap.put("221006", "核心系统退款拒绝");
		returnMap.put("221007", "交易金额超限");
		returnMap.put("221008", "未找到交易记录");
		returnMap.put("221009", "交易金额无效");
		returnMap.put("222001", "核心系统注册失败");
		returnMap.put("222002", "用户无效");
		returnMap.put("222003", "设置银行卡信息失败");
		returnMap.put("222004", "设置结算周期失败");
		returnMap.put("222005", "用户已注册");
		returnMap.put("222006", "用户已存在");
		returnMap.put("225001", "查询结果集太大");
		returnMap.put("225002", "核心系统拒绝查询");
		returnMap.put("224001", "退款金额超限");
		returnMap.put("224002", "退款已执行");
		return returnMap;
	}
}
