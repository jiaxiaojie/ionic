/**
 * 
 */
package com.thinkgem.jeesite.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;


import com.thinkgem.jeesite.common.config.EntityVoMapping;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import net.sf.json.JSONObject;

/**
 * @author 万端瑞
 *
 */
public class JsonUtils {


	
	public static <T> List<Map<String,Object>> beansToMap(List<T> beans,String entityVoMappingKey){
		List<Map<String,Object>> mapBeans = null;
		
		try{
			mapBeans = new  LinkedList<Map<String,Object>>();
			for(T bean : beans){
				if(!(bean instanceof BaseEntity)){
					continue;
				}
				
				mapBeans.add(beanToMap(bean,entityVoMappingKey));
			}
		}
		catch(Exception e){
			mapBeans = null;
		}
		
		return mapBeans;
	}

	
	public static <T> List<Map<String,Object>> beansToMap(List<T> beans){
		return beansToMap( beans, null);
	}
	

	
	/**
	 * 
	 * @author 万端瑞
	 * @param bean 待转换的bean
	 * @param entityVoMappingKey 转换映射规则key
	 * @return
	 */
	public static <T> Map<String, Object> beanToMap(T bean,String entityVoMappingKey) { 
		
		Map<String, Object> params = new LinkedHashMap<String, Object>(0); 
		
	    try { 
	    	Map<String,String> convertMappings = EntityVoMapping.getFieldMapping(entityVoMappingKey,bean.getClass());
	    	
	    	
	      //PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
	      //PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(bean); 
	      if(convertMappings != null){
		      for (String key : convertMappings.keySet()) { 
		    	  
			        String name = convertMappings.get(key);
			        Object value = null;
			        
			        try{
			        	value = Reflections.invokeGetter(bean,name);//propertyUtilsBean.getNestedProperty(bean, name);
			        }catch(Exception e){}
			        
			        
			        params.put(key, value); 

			        
			        
			      }
	      }

	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    } 
	    return params; 
	}

	/**
	 * 返回class的所有字段名列表
	 * @author wanduanrui
	 * @param obj
	 */
	protected static ArrayList<String> getFieldNamesByClazz(Class<?> clazz) {
		
		ArrayList<String> parentFieldNames = new ArrayList<String>(0); 
		Field[] fields = clazz.getDeclaredFields( );  
		for(Field field: fields){
			parentFieldNames.add(field.getName());
		}
		return parentFieldNames;
	}



    /**  
    * 把json数组串转换成map对象  
    * @param jsonArrayStr e.g. {'name':'get','int':1,'double',1.1,'null':null}  
    * @return Map  
    */  
    public static List<Map<String,Object>> getMapFromJsonArrayStr(String jsonArrayStr) {   
    	
        
        JSONArray jsonArray = JSONArray.fromObject(jsonArrayStr);  
        @SuppressWarnings("unchecked")
		List<Map<String,Object>> jsonObjects = (List<Map<String,Object>>)jsonArray;  
        
       
        return jsonObjects;   
    }

	public static Map<String, Object> getMapFormJsonObjStr(String jsonObjectStr){
		JSONObject jsonObject = JSONObject.fromObject(jsonObjectStr);
		Map<String,Object> jsonMap = (Map<String,Object>)jsonObject;
		return jsonMap;
	}

	public static Object getObjectByJson(String jsonObjectStr){
		jsonObjectStr = jsonObjectStr.replace("\n","").replace("\t","");
		Object result = null;
		Exception resultE = null;
		try{
			result = getMapFromJsonArrayStr(jsonObjectStr);
		}catch (Exception e){
			resultE =e;
			try{
				result = getMapFormJsonObjStr(jsonObjectStr);
				resultE = null;
			}catch (Exception e2){
				resultE = e2;
			}
		}

		if(resultE != null){
			resultE.printStackTrace();
		}
		return result;
	}
    

	
	public static void main(String[] args) {
		//List<Map<String,Object>> list     = getMapFromJsonArrayStr("[{left:'prev,next today',center:'title',right:'month,agendaWeek,agendaDay',map:{left:'123',center:'title',right:'month,agendaWeek,agendaDay'}},{left:'prev,next today',center:'title',right:'month,agendaWeek,agendaDay',map:{left:'123',center:'title',right:'month,agendaWeek,agendaDay'}}]");
		//System.out.println(((Map)((Map)list.get(1)).get("map")).get("left"));

		String json = "{count:{description:\"记录总数\",type:\"int\",optional:\"FALSE\"},resultList:[{planDate:{description:\"还款日期\",type:\"string\",optional:\"FALSE\"},planMoney:{description:\"还款金额\",type:\"number\",optional:\"FALSE\"},principal:{description:\"应还本金\",type:\"number\",optional:\"FALSE\"},interest:{description:\"应还利息\",type:\"number\",optional:\"FALSE\"},remainingPrincipal:{description:\"剩余应还本金\",type:\"number\",optional:\"FALSE\"},status:{description:\"状态\",type:\"string\",optional:\"FALSE\"},statusName:{description:\"状态名称\",type:\"String\",optional:\"FALSE\"},residualInterest:{description:\"剩余本息\",type:\"number\",optional:\"FALSE\"}}]}";
		Object list     = getObjectByJson(json);
		System.out.println(list);
	}
}
