package com.yeepay.interFace;

import java.util.Map;

import com.yeepay.util.DigestUtil;


public class DigestUtilInterFace {
	private static LogUtilInterFace logUtil = LogUtilInterFace.createLog(DigestUtilInterFace.class);
	// 在map中添加hmac
	public static Map addHmac(String[] HmacOrder, Map map, String keyValue){
		map = formatMap(HmacOrder, map);
		String sbold = DigestUtil.getHmacSBOld(HmacOrder, map);
		
		String hmac = "";
		String str = sbold; 
		hmac = DigestUtil.hmacSign(sbold, keyValue);
		map.put("hmac", hmac);
		logUtil.log("[KeyValue]" + keyValue + ",[sbold]" + sbold + ",[hmac]" + hmac);
		return map;
	}
	// 格式化参数Map，如HmacOrder中有的参数名在Map中没有此键值对的话，在Map中添加键值对
	public static Map formatMap(String[] HmacOrder, Map map){
		String key = "";
		String value = "";
		for(int i = 0; i < HmacOrder.length; i++){
			key = HmacOrder[i];
			value = (String)map.get(key);
			if(value == null){
				map.put(key, "");
			}
		}
		return map;
	}
	// 检查map中的hmac与在map中的以HmacOrder为键的值所组成的hmac是否一致
	public static boolean checkHmac(String[] HmacOrder,Map map, String keyValue){
		boolean returnBoolean = false;
		Object hmacObj = map.get("hmac");
		String hmac = (hmacObj == null) ? "" : (String)hmacObj ;
		String sbold = DigestUtil.getHmacSBOld(HmacOrder, map);
		String newHmac = "";
		newHmac = DigestUtil.hmacSign(sbold, keyValue);
		if(hmac.equals(newHmac)){
			returnBoolean = true;
		}
		logUtil.log("[hmac]" + hmac + ",[keyvalue]" + keyValue + ",[sbold]" + sbold + ",[newHmac]" + newHmac);
		return returnBoolean; 
	}

}
