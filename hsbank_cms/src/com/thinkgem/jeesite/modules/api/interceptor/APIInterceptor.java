package com.thinkgem.jeesite.modules.api.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.TokenUtils;
import com.thinkgem.jeesite.common.utils.TokenUtils.VerifyAction;
import com.thinkgem.jeesite.modules.api.annotation.Authenticate;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;

/**
 * 
 * @author 万端瑞
 *
 */
public class APIInterceptor extends BaseService implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse response, Object arg2, Exception e)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {

		//System.out.println("");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		final Boolean[] result = {true};
		 
		response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");

		Boolean needVali = false;
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod =  (HandlerMethod)handler;
			needVali = (handlerMethod.getMethodAnnotation(Authenticate.class) != null);
		}



		//表示调用这个请求需要登录权限
		if(needVali){

			//获得token String
			String token = request.getParameter("token");
			String client = request.getParameter("client");
			CustomerClientToken[] curCustomerClientToken = {null};

			//登陆校验
			Map<String, Object> resultMap = TokenUtils.verify(token,client, new VerifyAction() {
				@Override
				public void failAction(Map<String, Object> returnContent) {
					try {
						result[0] = false;
						response.getWriter().print(JsonMapper.toJsonString(returnContent));
						response.flushBuffer();
					} catch (IOException e) {
					}
				}
				@Override
				public void successAction(Map<String, Object> returnContent, CustomerClientToken customerClientToken) {
					result[0] = true;
					curCustomerClientToken[0] = customerClientToken;
				}
			});

			//登陆成功
			if(curCustomerClientToken[0] != null && result[0]){
				//将customerClientToken写入request中
				request.setAttribute("customerClientToken", curCustomerClientToken[0]);
				//request.setAttribute(curCustomerClientToken[0].getToken(), curCustomerClientToken[0].getCustomerId());
			}
		}
		
		return result[0];
	}

}
