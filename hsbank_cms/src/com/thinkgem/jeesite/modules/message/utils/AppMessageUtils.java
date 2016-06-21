package com.thinkgem.jeesite.modules.message.utils;

import org.json.JSONObject;

import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.message.MessageConstant;

import java.util.HashMap;
import java.util.Map;

public class AppMessageUtils {
	
	public static JSONObject push(MessageInstance messageInstance) {
		messageInstance.setContent(StringUtils.htmlUnescape(messageInstance.getContent()));
		if(MessageConstant.MESSAGE_CHANNEL_ANDROID.equals(messageInstance.getMessageChannel())) {
			return pushAndroid(messageInstance);
		} else if(MessageConstant.MESSAGE_CHANNEL_IOS.equals(messageInstance.getMessageChannel())) {
			return pushIOS(messageInstance);
		} else {
			throw new ServiceException("message instance channel is not app channel.");
		}
	}
	
	public static JSONObject pushAndroid(MessageInstance messageInstance) {
		XingeApp xinge = new XingeApp(MessageConstant.XINGE_ANDROID_ACCESSID, MessageConstant.XINGE_ANDROID_SECRETKEY);
		Message message = new Message();
		message.setExpireTime(0);
		message.setStyle(new Style(0,1,0,1,0));
		message.setTitle(messageInstance.getTitle());
		message.setContent(messageInstance.getContent());
		message.setType(Message.TYPE_NOTIFICATION);
		message.addAcceptTime(MessageConstant.XINGE_ACCEPTTIME);
		//增加自定义参数,消息类型
		Map<String,Object> custom = new HashMap<>();
		custom.put("type",messageInstance.getType());
		message.setCustom(custom);

		return xinge.pushSingleAccount(0, String.valueOf(messageInstance.getAccountId()), message);
	}

	public static JSONObject pushIOS(MessageInstance messageInstance) {
		XingeApp xinge = new XingeApp(MessageConstant.XINGE_IOS_ACCESSID, MessageConstant.XINGE_IOS_SECRETKEY);
		MessageIOS message = new MessageIOS();
		message.setExpireTime(0);
		message.setAlert(abbr(messageInstance.getContent(), 50));
		message.setSound("beep.wav");
		message.addAcceptTime(MessageConstant.XINGE_ACCEPTTIME);
		//增加自定义参数,消息类型
		Map<String,Object> custom = new HashMap<>();
		custom.put("type",messageInstance.getType());
		message.setCustom(custom);
		return xinge.pushSingleAccount(0, String.valueOf(messageInstance.getAccountId()), message, MessageConstant.IOS_ENVIRONMENT);
	}
	
	private static String abbr(String str, int length) {
		if(str == null) {
			return "";
		} else if(str.length() > length) {
			str = str.substring(0, length) + "...";
		}
		return str;
	}
	
	public static void main(String[] args) {
		//IOS
		XingeApp xinge = new XingeApp(MessageConstant.XINGE_IOS_ACCESSID, MessageConstant.XINGE_IOS_SECRETKEY);
//		for(int i = 1; i < 21; i++) {
			MessageIOS message = new MessageIOS();
			message.setExpireTime(0);
			message.setAlert("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1111");
			message.setSound("beep.wav");
			message.addAcceptTime(MessageConstant.XINGE_ACCEPTTIME);
			JSONObject data = xinge.pushSingleAccount(0, "564", message, MessageConstant.IOS_ENVIRONMENT);
			System.out.println(":" + data.getInt("ret_code"));
			System.out.println(":" + data);
//		}
		//Android
		/*XingeApp xinge = new XingeApp(MessageConstant.XINGE_ANDROID_ACCESSID, MessageConstant.XINGE_ANDROID_SECRETKEY);
		for(int i = 1; i < 30; i++) {
			Message message = new Message();
			message.setExpireTime(0);
			message.setTitle("qwr");
			message.setContent("12");
			System.out.println("type:"+Message.TYPE_NOTIFICATION);
			message.setType(Message.TYPE_NOTIFICATION);
			message.addAcceptTime(MessageConstant.XINGE_ACCEPTTIME);
			//JSONObject data = xinge.pushSingleAccount(0, "581", message);
			JSONObject data = xinge.pushAllDevice(0,message);
			
			System.out.println(":" + data.getInt("ret_code"));
			System.out.println(":" + data);
		}*/
	}
}
