/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.sign.Sign;
import com.thinkgem.jeesite.modules.api.sign.WechatTicket;
import com.thinkgem.jeesite.modules.api.sign.WechatToken;
import com.thinkgem.jeesite.modules.api.to.WechatSignatureResp;
import com.thinkgem.jeesite.modules.sys.utils.WechatCacheUtils;

/**
 * 微信Controller
 * 
 * @author lzb
 * @version 2015-10-16
 */
@Controller
@RequestMapping("${frontPath}/api/wechat")
public class WechatController extends APIBaseController {
	@Autowired
	private DirectReqUtils directReqUtils;

	/**
	 * 微信js SDK签名字符串
	 * @param response
	 * @param projectId
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "jsSignature", method = RequestMethod.POST)
	public String jsSignature(HttpServletResponse response, String client, String url) {
		String wechat_appid= "WECHAT_APPID";
		String wechat_secret ="WECHAT_SECRET";
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api wechat start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		WechatCacheObject wechatCacheObject = new WechatCacheObject();
		WechatCacheObject wechatTicketObject = new WechatCacheObject();
		String access_token ="";
		Date theDate = DateUtils.addMinutes(new Date(), ApiConstant.API_CACHE_WECHAT_JSSDK_INVALID_TIME);
		WechatCacheObject wechatSecretObject = WechatCacheUtils.get(wechat_secret);
		String jsapi_ticket = "";
		WechatCacheObject accessTokenObject = WechatCacheUtils.get(wechat_appid);
		if(accessTokenObject!=null){
			logger.info("jsapi_ticket::::" + wechatSecretObject.getJsapi_ticket() + "lastDt:"+accessTokenObject.getLastDt().getTime());
		}
		if(wechatSecretObject == null || (wechatSecretObject !=null && theDate.compareTo(wechatSecretObject.getLastDt()) > 0)){
			if(accessTokenObject == null || (accessTokenObject !=null && theDate.compareTo(accessTokenObject.getLastDt()) > 0)){
				access_token = directReqUtils.get(ApiConstant.API_WECHAT_APPID_URL);
				WechatToken wechatToken = (WechatToken)JsonMapper.fromJsonString(access_token, new WechatToken().getClass());
				wechatCacheObject.setCacheKey(wechat_appid);
				wechatCacheObject.setAccess_token(wechatToken.getAccess_token());
				wechatCacheObject.setLastDt(new Date());
				WechatCacheUtils.add(wechatCacheObject);
			}else{
				wechatCacheObject.setAccess_token(accessTokenObject.getAccess_token());
			}
			
			jsapi_ticket = directReqUtils.get(ApiConstant.API_WECHAT_SECRET_URL + wechatCacheObject.getAccess_token());
			WechatTicket wechatTicket = (WechatTicket)JsonMapper.fromJsonString(jsapi_ticket, new WechatTicket().getClass());
			wechatTicketObject.setCacheKey(wechat_secret);
			wechatTicketObject.setJsapi_ticket(wechatTicket.getTicket());
			wechatTicketObject.setLastDt(new Date());
			WechatCacheUtils.add(wechatTicketObject);
		}else{
			wechatTicketObject.setJsapi_ticket(wechatSecretObject.getJsapi_ticket());
		}
		Map<String, String>  ticketMap = new HashMap<String, String>();
		//签名
		ticketMap = Sign.sign(wechatTicketObject.getJsapi_ticket(), url);
		WechatSignatureResp signatureResp = new WechatSignatureResp();
		signatureResp.setAppId(ApiConstant.API_WECHAT_APPID);
		signatureResp.setNonceStr(String.valueOf(ticketMap.get("nonceStr")));
		signatureResp.setSignature(String.valueOf(ticketMap.get("signature")));
		signatureResp.setTimestamp(String.valueOf(ticketMap.get("timestamp")));
		ApiUtil.mapRespData(map, signatureResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api wechat end...");
		logger.info("api wechat total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
}
