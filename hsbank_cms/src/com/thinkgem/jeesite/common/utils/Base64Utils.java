package com.thinkgem.jeesite.common.utils;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import sun.misc.BASE64Decoder;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.api.client.AndroidProperty;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.client.IosProperty;

public class Base64Utils {

	/**
	 * 将字符串进行 BASE64 编码 
	 * @param s
	 * @return
	 */
	public static String getCodeBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	/**
	 * 将 BASE64 编码的字符串 进行解码
	 * @param s
	 * @return
	 */
	public static String getDecodeBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取对象
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getObject(String jsonString, Class clazz){     
        JSONObject jsonObject = null;     
        try{     
            setDataFormat2JAVA();      
            jsonObject = JSONObject.fromObject(jsonString);     
        }catch(Exception e){     
            e.printStackTrace();     
        }     
        return JSONObject.toBean(jsonObject, clazz);     
    }   
   
   /**
    * 设定日期转换格式 
    */
   public static void setDataFormat2JAVA(){     
        //设定日期转换格式     
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));     
    }
   
   
   
public static void main(String[] args)  
{  
	//Map<String,Object> map = new HashMap<String,Object>();
	ClientProperty client =new ClientProperty();
	AndroidProperty android = new AndroidProperty();
	IosProperty ios = new IosProperty();
	android.setChannel("android_001");
	android.setMd5("hsjinfu");
	android.setSdkVersion("1.0.01");
	ios.setDeviceModel("iphone6s");
	ios.setSystemVersion("9.0.1");
	client.setLanguage("English");
	client.setType("wechat");
	client.setVersion("1.0");
	client.setAndroid(android);
	client.setIos(ios);
	String strJson = JsonMapper.getInstance().toJson(client);
	
	
    System.out.println("strJson:::::::"+strJson);  
    /*JSONObject jsonobject = JSONObject.fromObject(strJson);
    System.out.println("jsonobject.......:::::"+jsonobject);*/
	String str = JsonMapper.toJsonString(client);
	System.out.println("str.......:::::"+str);
	String decode = "eyJ0eXBlIjoid2VjaGF0IiwidmVyc2lvbiI6IjEuMCIsIndlY2hhdCI6e319";
	String getBase64 = getCodeBASE64(str);
	
	System.out.print("getBASE64.......:::::"+getBase64);
	
	String unBase64 = getDecodeBASE64(decode);
	//ClientProperty clis = (ClientProperty) JsonMapper.fromJsonString(unBase64,client.getClass());
	
	//ClientProperty clients = (ClientProperty) getObject(unBase64,client.getClass());
	System.out.println("unBase64.......:::::"+unBase64);
	
	
	 
    //String strImg = GetImageStr("D://8-14012G004350-L.jpg");  
   // System.out.println(strImg);  
   // GenerateImage(strImg,"D://0000000_1.jpg");  
} 

}
