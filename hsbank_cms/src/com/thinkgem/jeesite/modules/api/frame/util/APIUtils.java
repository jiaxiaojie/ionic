package com.thinkgem.jeesite.modules.api.frame.util;

import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.api.ApiConstant;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * API相关类 已过时 不建议使用 请使用APIGenerator
 * @author 万端瑞
 *
 */
@Deprecated
public class APIUtils {


	public static class ConvertAction<T>{
		/**
		 * bean转api的map形式的实际处理方法
		 * @param dataEntity 就是转换后的api的map形式
		 * @return
		 */
		public Map<String, Object> convert(T dataEntity){
			Map<String,Object> dataMap = JsonUtils.beanToMap(dataEntity, dataEntity.getClass().getSimpleName());
			return dataMap;
		}
	}

	/**
	 * 返回api的map形式
	 * @param dataEntity 要转换成api的数据
	 * @param action 回调方法类
	 * @return
	 */
	public static <T> Map<String,Object> toAPIMap(T dataEntity,ConvertAction<T> action){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		if(dataEntity != null){
			putApiSuccessStatus(map);
			map.put(ApiConstant.API_RESP_DATA, action.convert(dataEntity));
		}
		else{
			putApiSuccessStatus(map);
			map.put(ApiConstant.API_RESP_DATA, new LinkedHashMap<String, Object>(0));
		}

		return map;
	}

	public static <T> Map<String,Object> toAPIMap(T dataEntity){
		return toAPIMap(dataEntity,new ConvertAction<T>());
	}

	public static <T> Map<String,Object> toAPIMap(List<T> dataEntitys){
		return toAPIMap(dataEntitys,new ConvertAction<T>());
	}

	public static <T> Map<String,Object> toAPIMap(List<T> dataEntitys,ConvertAction<T> action){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		if(dataEntitys != null){
			putApiSuccessStatus(map);
			List<Map<String, Object> > data = new LinkedList<Map<String, Object> >();
			for(T t : dataEntitys){
				data.add(action.convert(t));
			}
			map.put(ApiConstant.API_RESP_DATA, data);
		}
		else{
			putApiSuccessStatus(map);
			map.put(ApiConstant.API_RESP_DATA, new LinkedList<Map<String, Object> >() );
		}
		return map;
	}



	/**
	 * 向APIMAP数据中添加状态信息
	 * @param jsonMap
	 * @param apiStatusCode
	 * @param apiStatusText
	 */
	public static void putApiStatus(Map<String,Object> jsonMap,Integer apiStatusCode,String apiStatusText){
		Object currentStatusCode = jsonMap.get(ApiConstant.API_STATUS_CODE);
		if(currentStatusCode == null || (Integer)currentStatusCode ==ApiConstant.API_OPERA_SUCCESS){
			jsonMap.put(ApiConstant.API_STATUS_CODE, apiStatusCode);
			jsonMap.put(ApiConstant.API_STATUS_TEXT, apiStatusText);
		}
	}

	public static void putApiStatus(Map<String,Object> jsonMap,Integer apiStatusCode){
		if(apiStatusCode == ApiConstant.API_OPERA_SUCCESS){
			putApiStatus(jsonMap, apiStatusCode, "ok");
		}else{
			putApiStatus(jsonMap, apiStatusCode, "fail");
		}
	}

	public static void putApiSuccessStatus(Map<String,Object> jsonMap){
		putApiStatus(jsonMap, ApiConstant.API_OPERA_SUCCESS);
	}

	public static void putApiFailStatus(Map<String,Object> jsonMap){
		putApiStatus(jsonMap, ApiConstant.API_OPERA_FAIL);
	}
	public static void putApiFailStatus(Map<String, Object> apiMap, String apiStatusText) {
		putApiStatus(apiMap, ApiConstant.API_OPERA_FAIL,apiStatusText);
	}

	/**
	 * 将dataMap中的状态数据复制到target中
	 * @param target 目标map
	 * @param dataMap 包含状态数据的map
	 */
	public static void transferApiStatus(Map<String,Object> target, Map<String,Object> dataMap){
		Object apiStatusCode = dataMap.get(ApiConstant.API_STATUS_CODE);
		Object apiStatusText = dataMap.get(ApiConstant.API_STATUS_TEXT);
		if(apiStatusCode != null && apiStatusText != null){
			putApiStatus(target, (Integer)apiStatusCode, (String)apiStatusText);
		}

	}

	public static Map<String,Object> createAPIMapByDataKeyVals(String[] dataKeys, Object[] dataVals){
		Map<String,Object> apiMap = createAPIMap();
		Map<String,Object> data = new LinkedHashMap<String, Object>();
		apiMap.put(ApiConstant.API_RESP_DATA, data);

		if(dataKeys != null && dataVals != null && dataVals.length == dataKeys.length){

			for(int i = 0; i < dataKeys.length; i++){
				data.put(dataKeys[i], dataVals[i]);
			}
		}
		return apiMap;
	}

	public static Map<String,Object> createAPIMap(){
		Map<String,Object> APIMap = new LinkedHashMap<String, Object>();
		putApiSuccessStatus(APIMap);
		return APIMap;
	}

	public static Map<String,Object> createSuccessAPIMap(){
		Map<String,Object> APIMap = new LinkedHashMap<String, Object>();
		putApiSuccessStatus(APIMap);
		return APIMap;
	}
}
