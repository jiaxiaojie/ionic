package com.thinkgem.jeesite.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.frame.util.APIUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
/**
 * 
 * @author 万端瑞
 *
 */
public class TokenUtils {
	public static final String RETURN_CONTENT = "RETURN_CONTENT";
	
	public static HttpServletRequest getRequst(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
	}
	
	public static class VerifyAction{
		public void successAction(Map<String,Object> returnContent, CustomerClientToken customerClientToken){};
		public void failAction(Map<String,Object> returnContent){};
	}
	
	public static CustomerClientToken getCurrentCustomerClientToken(){
		return (CustomerClientToken)getRequst().getAttribute("customerClientToken");
	}
	
	public static String getCurrentToken(){
		return (String)getRequst().getAttribute("token");
	}
	
	/**
	 * 验证token是否有效，有效则执行VerifySuccessAction的action回调方法
	 * @param token
	 * @param action
	 * @return
	 */
	public static Map<String,Object> verify(String token,String client,VerifyAction action){
		Map<String,Object> returnContent = new LinkedHashMap<String, Object>();
		
		
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null){
			try{
				APIUtils.putApiSuccessStatus(returnContent);
				action.successAction(returnContent,clientToken);
				
			}catch(Exception e){
				APIUtils.putApiFailStatus(returnContent);
				e.printStackTrace();
			}
		}
		else{
			try{
				APIUtils.putApiStatus(returnContent, ApiConstant.API_INVALID_TOKEN, "请重新登录"); 
				action.failAction(returnContent);
			}catch(Exception e){
				
			}finally{
				
			}
		}
 		return returnContent;
	}
	

}
