package com.thinkgem.jeesite.modules.api;

import com.hsbank.api.util.ApiStatusConstant;
import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.Base64Utils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiUtil {
	
	private static Validator validator = SpringContextHolder.getBean("validator");

	/**
	 * 操作成功
	 * @param resultValue
	 */
	public static Map<String, Object> success(Map<String, Object> resultValue) {
		resultValue = resultValue == null ? new HashMap<>() : resultValue;
		resultValue.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_SUCCESS);
		resultValue.put(ApiConstant.API_STATUS_TEXT, ApiStatusConstant.SUCCESS_TEXT);
		return  resultValue;
	}

	/**
	 * 操作成功
	 * @param resultValue
	 * @param statusText
	 */
	public static Map<String, Object> success(Map<String, Object> resultValue, String statusText) {
		resultValue = resultValue == null ? new HashMap<>() : resultValue;
		resultValue.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_SUCCESS);
		resultValue.put(ApiConstant.API_STATUS_TEXT, statusText);
		return  resultValue;
	}

	/**
	 * 操作失败
	 * @param resultValue
	 */
	public static Map<String, Object> fail(Map<String, Object> resultValue) {
		resultValue = resultValue == null ? new HashMap<>() : resultValue;
		resultValue.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
		resultValue.put(ApiConstant.API_STATUS_TEXT, ApiStatusConstant.FAIL_TEXT);
		return  resultValue;
	}

	/**
	 * 操作失败
	 * @param resultValue
	 * @param statusText
	 */
	public static Map<String, Object> fail(Map<String, Object> resultValue, String statusText) {
		resultValue = resultValue == null ? new HashMap<>() : resultValue;
		resultValue.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
		resultValue.put(ApiConstant.API_STATUS_TEXT, statusText);
		return  resultValue;
	}
	
	/**
	 * 操作渠道转码
	 * @param channel
	 * @return
	 */
	public static String getOperaChannel(String channel) {
		channel = StringUtil.dealString(channel).toUpperCase();
		String opTerm = ProjectConstant.OP_TERM_DICT_PC;
		if(ApiConstant.OP_TERM_DICT_WEB.equals(channel)){
			opTerm = ProjectConstant.OP_TERM_DICT_PC;
		} else if (ApiConstant.OP_TERM_DICT_WECHAT.equals(channel)) {
			opTerm = ProjectConstant.OP_TERM_DICT_WEIXIN;
		} else if (ApiConstant.OP_TERM_DICT_ANDROID.equals(channel)) {
			opTerm = ProjectConstant.OP_TERM_DICT_ANDROID;
		} else if (ApiConstant.OP_TERM_DICT_IOS.equals(channel)) {
			opTerm = ProjectConstant.OP_TERM_DICT_IOS;
		}
		return opTerm;
	}
	
	/**
	 * 格式化投资进度不做四舍五入(取整数)
	 * @param rate
	 * @return
	 */
	public static Double formatRate(Double rate) {
		return NumberUtils.formatNotRoundWithScale(rate,0);
	}
	
	/**
	 * 图片url转换
	 * @param url
	 * @return
	 */
	public static String imageUrlConver(String url){
		if(StringUtils.isBlank(url)){
			return "";
		}
		String cPath = getCurRequest().getContextPath();
		String path = YeepayConstant.HSBANK_FILE_ACCESS_PREFIX_URL;
		path = path.substring(0, path.length() - 1);
		if(url.contains(cPath)){
			url = url.replaceAll(cPath, "");
		}
		return StringUtil.dealString(path + url);
	}

	/**
	 * 获取当前request
	 * @return
	 */
	public static HttpServletRequest getCurRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	/**
	 *src统一加前缀
	 * @param str
     * @return
     */
	public static String prefixSrc (String str){
		String cPath = getCurRequest().getContextPath();
		if(str!=null && str.contains(cPath)){
			str = str.replaceAll(cPath, "");
		}
		String path = YeepayConstant.HSBANK_FILE_ACCESS_PREFIX_URL;
		path = path.substring(0, path.length() - 1);
		if(str.contains(cPath)){
			str = str.replaceAll(cPath, "");
		}
		return com.thinkgem.jeesite.common.utils.StringUtils.prefixSrc(str,path);
	}
	
	/**
	 * 获取ClientProperty
	 * @param client
	 * @return
	 */
	public static ClientProperty getClient(String client){
		String jsonStr = Base64Utils.getDecodeBASE64(client);
		ClientProperty cProperty = (ClientProperty)JsonMapper.fromJsonString(jsonStr, new ClientProperty().getClass());
		if(cProperty == null){
			cProperty = new ClientProperty();
		}
		return cProperty;
	}

	/**
	 * href加前缀(href=\"..)
	 * @param str
	 * @return
	 */
	public static String prefixHref(String str){
		if(com.thinkgem.jeesite.common.utils.StringUtils.isBlank(str)){
			return "";
		}
		String path = YeepayConstant.HSBANK_FILE_ACCESS_PREFIX_URL;
		path = path.substring(0, path.length() - 1);
		str = str.replaceAll("(.*?)href=\"..(.*?)", "$1href=\"" + path + "$2");
		return str;
	}
	
	/**
	 * 获取操作终端
	 * @param client
	 * @return
	 */
	public static String getOperTerm(String client) {
		ClientProperty cProperty = getClient(client);
		return getOperaChannel(cProperty.getType());
	}
	/**
	 * 判断是否强制更新
	 * @param index
	 * @param oldVersion
	 * @param newVersion
	 * @return
	 */
	public static boolean isNewVersion(int index, String oldVersion,
			String newVersion) {
		boolean resultFlag = false;
		int forCount = 0;
		int oldVersionCount = subStringCount(oldVersion, ".");
		int newVersionCount = subStringCount(newVersion, ".");
		String oldVersionArray[] = null;
		String newVersionArray[] = null;
		if (oldVersionCount > newVersionCount) {
			forCount = oldVersionCount - newVersionCount;
			for (int i = 0; i < forCount; i++)
				newVersion = newVersion + ".0";
		} else {
			forCount = newVersionCount - oldVersionCount;
			for (int i = 0; i < forCount; i++)
				oldVersion = oldVersion + ".0";
		}
		oldVersionArray = oldVersion.split("\\.");
		newVersionArray = newVersion.split("\\.");
		forCount = oldVersionArray.length;
		if (index >= 0) {
			if (index < forCount) {
				resultFlag = Integer.parseInt(newVersionArray[index]) > Integer
						.parseInt(oldVersionArray[index]);
			}
		} else {
			for (int i = 0; i < forCount; i++) {
				if (Integer.parseInt(newVersionArray[i]) > Integer
						.parseInt(oldVersionArray[i])) {
					resultFlag = true;
					break;
				}else if(Integer.parseInt(oldVersionArray[i]) > Integer
						.parseInt(newVersionArray[i])){
					break;
				}
			}
		}
		return resultFlag;
	}
	
	public static int subStringCount(String strTest, String strSub) {
        int count = 0, start = 0;
        while ((start = strTest.indexOf(strSub, start)) >= 0) {
            start += strSub.length();
            count++;
        }
        return count;
    }
	
	/**
	 * 数字金额大写转换
	 */
	public static String digitUppercase(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}
	
	/**
	 * 结果返回数据
	 * @param resultCode
	 * @param resultText
	 * @param statusCode
	 * @param statusText
	 * @param reMap
	 * @param map
	 */
	public static void resultMapData(int resultCode, String resultText,
			int statusCode, String statusText, Map<String, Object> reMap,
			HashMap<String, Object> map) {
		Map<Object,Object> dataMap = new HashMap<Object,Object>();
		dataMap.put("resultCode", resultCode);
		dataMap.put("resultText", resultText);
		map.put(ApiConstant.API_STATUS_CODE, statusCode);
		map.put(ApiConstant.API_STATUS_TEXT, statusText);
		map.put(ApiConstant.API_RESP_DATA, dataMap);
	}
	
	/**
	 * token失效提示
	 * @param map
	 */
	public static void tokenInvalidRespMap(Map<String, Object> map){
		map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_INVALID_TOKEN);
		map.put(ApiConstant.API_STATUS_TEXT, "请重新登录");
	}
	
	/**
	 * 后台异常
	 * @param map
	 */
	public static void sysExceptionRespMap(Map<String, Object> map){
		map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
		map.put(ApiConstant.API_STATUS_TEXT, ApiConstant.API_EXCEPTION_RESP_TEXT);
	}

	/**
	 * 
	 * @param map
	 * @param dataObject
	 * @param textObject
	 * @param isSuccess
     */
	public static void mapRespData(Map<String, Object> map, Object dataObject, Object textObject, boolean isSuccess){
		if(isSuccess){
			textObject = textObject !=null && !"".equals(textObject) ? textObject : "ok";
			map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_SUCCESS);
		}else{
			textObject = textObject != null && !"".equals(textObject) ? textObject : "fail";
			map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
		}
		map.put(ApiConstant.API_STATUS_TEXT, textObject);
		if(dataObject != null && !"".equals(dataObject)){
			map.put(ApiConstant.API_RESP_DATA, dataObject);
		}
	}
	
	/**
	 * 入参校验
	 * @param entity
	 * @return
	 */
	public static <T> List<String> validateBean(T entity) {
		List<String> messages = null;
		try{
			BeanValidators.validateWithException(validator, entity);
		}catch(ConstraintViolationException ex){
			messages =  BeanValidators.extractMessage(ex);
		}
		return messages;
	}
	
	/**
	 * 消息发送渠道转化
	 * @param opTerm
	 * @return
	 */
	public static String getPushChannel(String opTerm) {
		String pushChannel = "";
		if (ProjectConstant.OP_TERM_DICT_PC.equals(opTerm)) {
			pushChannel = MessageConstant.MESSAGE_CHANNEL_WEB;
		} else if (ProjectConstant.OP_TERM_DICT_ANDROID.equals(opTerm)) {
			pushChannel = MessageConstant.MESSAGE_CHANNEL_ANDROID;
		} else if (ProjectConstant.OP_TERM_DICT_IOS.equals(opTerm)) {
			pushChannel = MessageConstant.MESSAGE_CHANNEL_IOS;
		} else if (ProjectConstant.OP_TERM_DICT_WEIXIN.equals(opTerm)) {
			pushChannel = MessageConstant.MESSAGE_CHANNEL_WECHAT;
		}
		return pushChannel;
	}

	/**
	 * 请求易宝浏览器网关地址前缀
	 * @param mobileType
	 * @return
     */
	public static String getYeepayUrlPrefix(String mobileType){
		String yeepayUrlPrefix = YeepayConstant.YEEPAY_GATE_WIRELESS_URL_PREFIX;
		mobileType = StringUtil.dealString(mobileType).toUpperCase();
		//操作终端为WEB，调用网页端地址前缀，否则调用移动端地址前缀
		if(ApiConstant.OP_TERM_DICT_WEB.equals(mobileType)){
			yeepayUrlPrefix = YeepayConstant.YEEPAY_GATE_URL_PREFIX;
		}
		return yeepayUrlPrefix;
	}

	/**
	 * 获取剩余时间
	 * @param endDate
	 * @return
     */
	public static String getSurplusTime(Date endDate,String status){
		StringBuffer strBuf = new StringBuffer();
		if(!status.equals(ProjectConstant.PROJECT_STATUS_INVESTMENT)){
			long residualTime=0;
			strBuf.append(residualTime);
		}else{
		//剩余天、时、分
		long distanceMillis = endDate.getTime() - new Date().getTime();
		long remainDay = distanceMillis / (1000 * 60 * 60 * 24);
		long remainHour = (distanceMillis - remainDay * (1000 * 60 * 60 * 24))/(1000 * 60 * 60);
		long remainMinute = (distanceMillis - remainDay * (1000 * 60 * 60 * 24) - remainHour * (1000 * 60 * 60))/(1000 * 60);
		strBuf.append(remainDay).append("天").append(remainHour).append("时").append(remainMinute).append("分");
	}
		return strBuf.toString();
	}

}
