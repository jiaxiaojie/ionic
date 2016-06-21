package com.yeepay.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

// 生成hmac校验签名类
public class DigestUtil {
	private static String encodingCharset = "UTF-8";
	private static String beforeChangeCode = "iso-8859-1";
	private static String afterChangeCode = "gbk";
	private static String hmac_CONNECT_Flag = "";
	
	// 获得生成hmac时需要的sbold
	public static String getHmacSBOld(String[] HmacOrder,Map map){
		return getHmacSBOld(HmacOrder, map, hmac_CONNECT_Flag  );
	}
	// 获得生成hmac时需要的sbOld
	public static String getHmacSBOld(String[] HmacOrder,Map map, String connectFlag){
		int index = HmacOrder.length;
		String[] args = new String[index];
		String key = "";
		String value = "";
		for(int i = 0; i < index; i++){
			key = HmacOrder[i];
				value = (String)map.get(key);
				if(value == null){
					value = "";
				}
				args[i] = value;
		}
		return getHmacSBOld(args, connectFlag);
	}
	// 获得生成hmac时需要的sbOld
	public static String getHmacSBOld(String[] args, String connectFlag){
		StringBuffer returnString = new StringBuffer();
		int index = args.length;
		for(int i = 0; i < index; i++)
		{
			returnString.append(args[i]).append(connectFlag);
		}
		return returnString.substring(0, returnString.length() - hmac_CONNECT_Flag.length());
	}
	public static String formatString(String str){
		if(str == null){
			str = "";
		}
		return str;
	}
	public static String hmacSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			String str = aValue; 
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}
		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}
	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}
		return output.toString();
	}
	public static String getHmac(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return (hmacSign(str.toString(), key));
	}
	public static String digest(String aValue) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));
	}
}
