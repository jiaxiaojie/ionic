package com.thinkgem.jeesite.modules.sms.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.modules.sms.SmsConstant;

public class SmsUtils {
	
	private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);
	
	/**
	 * 发送单条短信
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static void sendSms(String mobile, String content) {
		mobile = mobile.startsWith("86") ? mobile : "86" + mobile;
		String response = doGetRequest(generateRequestContent(mobile, content, SmsConstant.COMMAND));
		logger.info("send sms message to mobile:" + mobile + ",content:" + content + ",response:" + response);
	}

	/**
	 * 相同内容群发
	 * @param mobiles
	 * @param content
	 */
	public static void sendSms(List<String> mobiles, String content) {
		int count = 0;
		StringBuilder mobileContent = new StringBuilder();
		for(String mobile : mobiles) {
			mobileContent.append(mobile.startsWith("86") ? mobile : "86" + mobile).append(",");
			count++;
			if(count % SmsConstant.MAX_MULTI_NUMBER == 0) {
				mobileContent = mobileContent.deleteCharAt(mobileContent.length() - 1);
				String response = doGetRequest(generateRequestContent(mobileContent.toString(), content, SmsConstant.COMMAND_MULTI));
				logger.info("send sms message to mobiles:" + mobileContent + ",content:" + content + ",response:" + response);
				mobileContent = new StringBuilder();
			}
		}
		if(count != 0 && count % SmsConstant.MAX_MULTI_NUMBER != 0) {
			mobileContent = mobileContent.deleteCharAt(mobileContent.length() - 1);
			String response = doGetRequest(generateRequestContent(mobileContent.toString(), content, SmsConstant.COMMAND_MULTI));
			logger.info("send sms message to mobiles:" + mobileContent + ",content:" + content + ",response:" + response);
		}
	}

	private static String generateRequestContent(String mobile, String content, String command) {
		try {
			String sm = new String(Hex.encodeHex(content.getBytes(SmsConstant.ECODEFORM)));
			StringBuilder smsUrl = new StringBuilder();
			smsUrl.append(SmsConstant.MTURL);
			smsUrl.append("?command=" + command);
			smsUrl.append("&spid=" + SmsConstant.SPID);
			smsUrl.append("&sppassword=" + SmsConstant.SPPASSWORD);
			smsUrl.append("&spsc=" + SmsConstant.SPSC);
			smsUrl.append("&sa=" + SmsConstant.SA);
			if(SmsConstant.COMMAND.equals(command)) {
				smsUrl.append("&da=" + mobile);
			} else {
				smsUrl.append("&das=" + mobile);
			}
			smsUrl.append("&sm=" + sm);
			smsUrl.append("&dc=" + SmsConstant.DC);
			System.out.println(smsUrl);
			return smsUrl.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ServiceException("catch exception when generateRequestContent, mobile:" + mobile
					+ ",content:" + content + ",command" + command);
		}
	}
	
	/**
	 * 发送http GET请求，并返回http响应字符串
	 * @param url
	 * @return
	 */
	public static String doGetRequest(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String strJson = "";
		try {
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				strJson = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return strJson;
	}
	
	/**
	 * 将 短信下行 请求响应字符串解析到一个HashMap中
	 * 
	 * @param resStr
	 * @return
	 */
	static Map<String, String> parseResStr(String resStr) {
		Map<String, String> pp = new HashMap<String, String>();
		try {
			String[] ps = resStr.split("&");
			for (int i = 0; i < ps.length; i++) {
				int ix = ps[i].indexOf("=");
				if (ix != -1) {
					pp.put(ps[i].substring(0, ix), ps[i].substring(ix + 1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pp;
	}
	
	public static void main(String[] args) {
		List<String> mobiles = new ArrayList<String>();
		mobiles.add("13122633697");
		sendSms(mobiles, "你好，111");
		sendSms(mobiles, "你好，222");
		sendSms(mobiles, "你好，333");
	}
}
