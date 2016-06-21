package com.thinkgem.jeesite.common.utils.sms_auth.handler;

import com.hsbank.util.arithmetic.Base64;
import com.hsbank.util.arithmetic.HmacSHA1;
import com.hsbank.util.http.HttpClientUtil;
import com.hsbank.util.http.UrlUtil;
import com.hsbank.util.http.util.bean.HttpResponseBean;
import com.hsbank.util.tool.JsonUtil;
import com.thinkgem.jeesite.common.utils.sms_auth.util.CustomApiCommandConstant;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.UUID;

/**
 * 短信验证处理器
 * @author arthur_xie
 * 2013-05-27
 */
public class SmsAuthHandler {
    /**log对象*/
    private static Logger _log = Logger.getLogger(SmsAuthHandler.class);
    /**单例*/
	private static SmsAuthHandler instance = null;
    
    public static void main(String[] args) {
    	//_log.debug(RbacUtil.encrypt("meplus@12345!"));
        new SmsAuthHandler().sendRequest("13621925191");
        //new SmsAuthHandler().authRequest("13621925191", "790417");
    }
    
    /**私有构造函数*/
	private SmsAuthHandler() {
	}
	
	/**
     * 得到单例
     * @return  
     */
	public static synchronized SmsAuthHandler getInstance() {
		return instance == null ? instance = new SmsAuthHandler() : instance;
	}
    
    /**
     * 发送短信验证码_请求消息
     * @param mobile
     */
    public Map<String, Object> sendRequest(String mobile) {
    	//apiKey
		_log.debug("apiKey: " + ProjectConfig.getInstance().getSmsApiKey());
		//secretKey
		_log.debug("secretKey: " + ProjectConfig.getInstance().getSmsSecretKey());
  		String base64ApiKey = Base64.encodeUTF8(ProjectConfig.getInstance().getSmsApiKey());
  		String urlEncodeBase64ApiKey = UrlUtil.encodeUTF8(base64ApiKey);
  		_log.debug("base64ApiKey: " + base64ApiKey);
  		String timestamp = System.currentTimeMillis() + "";
  		//签名【signature = HmacSHA1(Base64(account) + timestamp, secretKey)】
  		String signature = UrlUtil.encodeUTF8(HmacSHA1.getHmacSHA1String_utf_8(base64ApiKey + timestamp, ProjectConfig.getInstance().getSmsSecretKey()));
  		_log.debug("signature: " + signature);
  		String url = new StringBuffer()
  			.append(ProjectConfig.getInstance().getSmsApiUrl())
  			.append("?seq_id=").append(UUID.randomUUID().toString())
  			.append("&command=").append(CustomApiCommandConstant.SEND_REQUEST)
  			.append("&api_key=").append(urlEncodeBase64ApiKey)
  			.append("&signature=").append(signature)
  			.append("&timestamp=").append(timestamp)
  			.append("&mobile=")
  			.append(mobile)
  			.toString();
  		_log.debug("url: " + url);
        HttpResponseBean bean = HttpClientUtil.doGet(url);
        _log.debug("响应字符串为：" + bean.getContent());
        return JsonUtil.toMap(bean.getContent());
    }
    
    /**
     * 鉴权短信验证码_请求消息
     * @param mobile
     * @param authCode
     */
    public Map<String, Object> authRequest(String mobile, String authCode) {
    	//apiKey
		_log.debug("apiKey: " + ProjectConfig.getInstance().getSmsApiKey());
		//secretKey
		_log.debug("secretKey: " + ProjectConfig.getInstance().getSmsSecretKey());
		String base64ApiKey = Base64.encodeUTF8(ProjectConfig.getInstance().getSmsApiKey());
		String urlEncodeBase64ApiKey = UrlUtil.encodeUTF8(base64ApiKey);
		_log.debug("base64ApiKey: " + base64ApiKey);
		String timestamp = System.currentTimeMillis() + "";
		//签名【signature = HmacSHA1(Base64(account) + timestamp, secretKey)】
		String signature = UrlUtil.encodeUTF8(HmacSHA1.getHmacSHA1String_utf_8(base64ApiKey + timestamp, ProjectConfig.getInstance().getSmsSecretKey()));
		_log.debug("signature: " + signature);
		String url = new StringBuffer()
			.append(ProjectConfig.getInstance().getSmsApiUrl())
			.append("?seq_id=").append(UUID.randomUUID().toString())
			.append("&command=").append(CustomApiCommandConstant.AUTH_REQUEST)
			.append("&api_key=").append(urlEncodeBase64ApiKey)
			.append("&signature=").append(signature)
			.append("&timestamp=").append(timestamp)
			.append("&mobile=").append(mobile)
			.append("&auth_code=").append(authCode)
			.toString();
		_log.debug("url: " + url);
		HttpResponseBean bean = HttpClientUtil.doGet(url);
        _log.debug("响应字符串为：" + bean.getContent());
        return JsonUtil.toMap(bean.getContent());
    }
}
