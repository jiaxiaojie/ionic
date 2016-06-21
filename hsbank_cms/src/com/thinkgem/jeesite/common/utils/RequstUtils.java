/**
 * 
 */
package com.thinkgem.jeesite.common.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotWritablePropertyException;

import com.thinkgem.jeesite.common.config.EntityVoMapping;

/**
 * @author 万端瑞
 *
 */
public class RequstUtils {
	
	/**
	 * 将request请求参数包装成实体类，目前只能包装基本类型，如果要支持更多类型，需要为BeanWrapper添加PropertyEditors
	 * @author 万端瑞
	 * @param request
	 * @param clazz
	 * @param entityVoMappingKey
	 * @return
	 */
	public static <T> T toEntity(HttpServletRequest request, Class<T> clazz,String entityVoMappingKey){
		Map<String,String> convertMappings = EntityVoMapping.getFieldMapping(entityVoMappingKey,clazz);
		T entity = null;
		try {
			entity = clazz.newInstance();
			BeanWrapper bw = new BeanWrapperImpl(entity);
			Enumeration<String> paraNames=request.getParameterNames();
			for(Enumeration<String> e=paraNames;e.hasMoreElements();){
			       String thisName=e.nextElement().toString();
			       String thisValue=StringUtils.trim(request.getParameter(thisName));
			       
			       
			       //如果存在昵称，则将属性名字改为昵称
			       String mappingName= convertMappings.get(thisName);
			       if(StringUtils.isNotBlank(mappingName)){
			    	   thisName = mappingName;
			       }
			       try{
			    	   bw.setPropertyValue(thisName, thisValue);
			       }
			       catch(NotWritablePropertyException e2){
			    	   
			       }
			       
			       
			}
		} catch (InstantiationException | IllegalAccessException e1) {
			
		}
		
		return entity;
	}
	
	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
	    if(maxAge>0)  cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
	
    /** 
     * 清空cookie 
     */  
    public static void clearCookie(HttpServletResponse response, String name) {  
      
      try  
      {  
          
            Cookie cookie = new Cookie(name, null);  
            cookie.setMaxAge(0);   
            response.addCookie(cookie);  
           
      }catch(Exception ex)  
      {  
           System.out.println("清空Cookies发生异常！");  
      }   
       
    }  
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	
	public static <T> T toEntity(HttpServletRequest request, Class<T> clazz){
		return toEntity(request, clazz, null);
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getModel(Object requestScope,String modelName){
		Map<String,String> map = null;
		try{
			Object myobj = Reflections.invokeMethodByName(requestScope, "getAttribute",new Object[]{modelName});
			map = (myobj instanceof Map?(Map<String,String>)myobj:null);
			if(map==null){
				Object request1 = Reflections.getFieldValue(requestScope,"request");
				Object request2 = Reflections.getFieldValue(request1,"request");
				String queryString = (String)Reflections.getFieldValue(request2,"queryString");
				map = StringUtils.toMap(queryString, "&", "=",false,false);
				if(map != null){
					map.put("queryString", queryString);
				}
			}
		}
		catch(Exception e){
			
		}

		
		
		return map;
	}
	
}
