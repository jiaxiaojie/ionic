/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.util.HtmlUtils;

import com.google.common.collect.Lists;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * 
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str
	 *            验证字符串
	 * @param strs
	 *            字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (str != null) {
			for (String s : strs) {
				if (str.equals(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}
    
	/**
	 * 替换特殊字符
	 * @param str
	 * @return
	 */
	public static String replaceSpecialStr(String str){
		if(StringUtils.isBlank(str)){
			return "";
		}
		String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern   p   =   Pattern.compile(regEx); 
		return p.matcher(str).replaceAll("").trim();
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt) {
		if (txt == null) {
			return "";
		}
		return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t",
				"&nbsp; &nbsp; ");
	}


	/**
	 * 缩略字符串（不区分中英文字符）
	 * 
	 * @param str
	 *            目标字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String abbrev(String str, int length) {
		if(str == null) {
			return "<span titile=''></span>";
		}
		str = htmlUnescape(str);
		if(str.length() <= length) {
			return "<span title='" + str + "'>" + str + "</span>";
		}
		return "<span title='" + str + "'>" + str.substring(0, length) + "...</span>";
	}
	
	/**
	 * 缩略字符串（不区分中英文字符）
	 * 
	 * @param str
	 *            目标字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str))
					.toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 将制定字符串截取（末尾加A）或补充（前缀加0）为指定长度的字符串
	 * @param src
	 * @param length
	 * 
	 */
	public static String fixLength(String src, int length) {
		StringBuffer sb = new StringBuffer();
		if (src == null) {
			for (int i = 0; i < length; i++) {
				sb.append("0");
			}
		} else if (src.length() > length) {
			sb.append(src.substring(0, length - 1)).append("a");
		} else {
			int slength = src.length();
			for (int i =slength; i <  length; i++) {
				sb.append("0");
			}
			sb.append(src);
		}
		return sb.toString();

	}

	public static String abbr2(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
				"$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result
				.replaceAll(
						"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
						"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
				"$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = Lists.newArrayList();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}

	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val) {
		if (val == null) {
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val) {
		return toLong(val).intValue();
	}

	/**
	 * 获得i18n字符串
	 */
	public static String getMessage(String code, Object[] args) {
		LocaleResolver localLocaleResolver = (LocaleResolver) SpringContextHolder
				.getBean(LocaleResolver.class);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Locale localLocale = localLocaleResolver.resolveLocale(request);
		return SpringContextHolder.getApplicationContext().getMessage(code,
				args, localLocale);
	}

	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 如果不为空，则设置值
	 * 
	 * @param target
	 * @param source
	 */
	public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)) {
			target = source;
		}
	}

	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 * 
	 * @param objectString
	 *            对象串 例如：row.user.id
	 *            返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (int i = 0; i < vals.length; i++) {
			val.append("." + vals[i]);
			result.append("!" + (val.substring(1)) + "?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}
	/**
	 * 模糊化姓名
	 * @param name
	 * @return
	 */
	public static String vagueName(String name){
		if((name==null)||(name.equals(""))){
			return "***";
		}else{
			StringBuffer sb=new StringBuffer();
			/*for(int i=0;i<name.length();i++){
				if(i%2==0){
					sb.append(name.charAt(i));
				}else{
					sb.append("*");
				}
			}*/
			int length = name.length();
			sb.append(name.substring(0, 1));
			for(int i=0; i < length - 1; i++) {
				sb.append("*");
			}
			return sb.toString();
		}
	}
	
	/**
	 * 模糊化姓名
	 * @param name
	 * @return
	 */
	public static String vagueAccountName(String name){
		if((name==null)||(name.equals(""))){
			return "***";
		}else{
			StringBuffer sb=new StringBuffer();

			int length = name.length();
			sb.append(name.substring(0, 2));
			for(int i=0; i < length - 3; i++) {
				sb.append("*");
			}
			sb.append(name.substring(name.length()-2));
			return sb.toString();
		}
	}
	
	
	/**
	 * 模糊化身份证号
	 * @param name
	 * @return
	 */
	public static String vagueCertNum(String certNum){
		if((certNum==null)||(certNum.equals(""))||(certNum.length()<18)){
			return "***";
		}else{
			StringBuffer sb=new StringBuffer();
			sb.append(certNum.substring(0, 6)).append("********").append(certNum.substring(14));
			return sb.toString();
		}
	}
	/**
	 * 模糊化邮箱
	 * @param name
	 * @return
	 */
	public static String vagueEmail(String email){
		if((email==null)||(email.equals(""))||(email.indexOf("@")==-1)){
			return "***";
		}else{
			String name=email.substring(0,email.indexOf("@"));
			StringBuffer sb1=new StringBuffer();
			if(name.length()>6){
				for(int i=0;i<name.length();i++){
					if((i<3)||(i>name.length()-3)){
						sb1.append(name.charAt(i));
					}else{
						sb1.append("*");
					}
				}
			}else{
				for(int i=0;i<name.length();i++){
					if(i<3){
						sb1.append(name.charAt(i));
					}else{
						sb1.append("*");
					}
				}
			}
			StringBuffer sb=new StringBuffer();
			sb.append(sb1).append(email.substring(email.indexOf("@")));
			return sb.toString();
		}
	}
	
	/**
	 * 模糊化手机号码
	 * @param name
	 * @return
	 */
	public static String vagueMobile(String mobile){
		if((mobile==null)||(mobile.equals(""))||(mobile.length()<10)){
			return "***";
		}else{
			StringBuffer sb=new StringBuffer();
			sb.append(mobile.substring(0, 3)).append("****").append(mobile.substring(7));
			return sb.toString();
		}
	}
	
	/**
	 * 模糊化毕业院校
	 * @param name
	 * @return
	 */
	public static String vagueEducationSchool(String educationSchool){
		if((educationSchool==null)||(educationSchool.equals(""))){
			return "***";
		}else{
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<educationSchool.length();i++){
				if((i==0)||(i==educationSchool.length()-1)){
					sb.append(educationSchool.charAt(i));
				}else{
					sb.append("*");
				}
			}
			return sb.toString();
		}
	}
	/**
	 * 模糊化地址
	 * @param address
	 * @return
	 */
	public static String vagueAddress(String address){
		if((address==null)||(address.equals(""))){
			return "***";
		}else{
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<address.length();i++){
				if((i%6==1)||(i%6==0)){
					sb.append("*");
				}else{
					sb.append(address.charAt(i));
				}
			}
			return sb.toString();
		}
	}
	
	
	/**
	 * 模糊化户籍地址
	 * @param address
	 * @return
	 */
	public static String vagueFamilyRegister(String familyRegister){
		if((familyRegister==null)||(familyRegister.equals(""))){
			return "***";
		}else{
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<familyRegister.length();i++){
				if((i%6==1)||(i%6==0)){
					sb.append("*");
				}else{
					sb.append(familyRegister.charAt(i));
				}
			}
			return sb.toString();
		}
	}
	
	/**
	 * 模糊化信用卡卡号
	 * @param address
	 * @return
	 */
	public static String vagueCreditCardNo(String creditCardNo){
		if((creditCardNo==null)||(creditCardNo.equals(""))||(creditCardNo.length()<=10)){
			return "***";
		}else{
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<creditCardNo.length();i++){
				if((i<6)||(i>=creditCardNo.length()-4)){
					sb.append(creditCardNo.charAt(i));
				}else{
					sb.append("*");
				}
			}
			return sb.toString();
		}
	}
	
	/**
     * 验证密码是否合法
     * @param password
     * @return
     */
    public static boolean validatePwd(String password){
		String regex = "^[0-9a-zA-Z]{6,16}$";
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(password);
        return m.matches();
	}
    
    /**
     * 昵称校验
     * @param password
     * @return
     */
    public static boolean validateNickName(String password){
		String regex = "^[0-9a-zA-Z]{3,20}$";
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(password);
        return m.matches();
	}
	
	/**
     * 符号环绕
     * 将例如string=a,b,c regex=，symbol=‘  返回形式为'a','b','c'
     * @param string  要换换的字符串
     * @param regex   分隔符
     * @param symbol  需要环绕的符号
     * @return
     */
    public static String surroundSymbol(String string ,String regex,String symbol){
		if(StringUtils.isBlank(string)){
			return "";
		}
        String[] array = string.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:array){
            stringBuilder.append(symbol).append(s).append(symbol).append(regex);
        }
        if(stringBuilder.indexOf(",")!=-1)
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }
    /**
     * 判断是否是同一个主机的url
     * @param pathA
     * @param pathB
     * @return
     */
    public static boolean isSameDomain(String pathA,String pathB){
    	try{
    		URL urlA = new URL(pathA);
    		URL urlB = new URL(pathB);
    		String hostA=urlA.getHost();
    		String hostB=urlB.getHost();
    		if(hostA.equals(hostB)){
    			return true;
    		}else{
    			return false;
    		}
    	}catch(Exception e){
    		return false;
    	}
    }
    
    public static  String createValidateCode(int length) {
		char[] codeSeq = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
				'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);//random.nextInt(10));
			s.append(r);
		}
		return s.toString();
	}
    
    /**
     * 将key value的字符串转换为map，然后倒转过来将value作为key，key作为value再存一遍，例如 key1:value1;key2:value2
     * @author wanduanrui
     * @param keyValues key-value字符串，要求必须为一一对应
     * @param itemRegex key value项之间的分隔符，例如上面的;
     * @param keyValueRegex key value之间的分隔符，例如上面的:
     * @return
     */
    public static HashMap<String,String> toMap(String keyValues,String itemRegex,String keyValueRegex, Boolean isReversal,Boolean copyKeyOnValueIsEmpty){
    	HashMap<String,String> map = new LinkedHashMap<String,String>();
    	
    	try{
	    	if(keyValues != null && itemRegex != null && keyValueRegex != null && !keyValueRegex.equals(itemRegex)){
	        	String[] keyValueItems = keyValues.split(itemRegex);
	        	for(String item : keyValueItems){
	        		String[] keyValue = item.split(keyValueRegex);
	        		
	        		if(keyValue != null && keyValue.length!=0){
	        			String key = keyValue[0];
	        			String value = null;
	        			
	        			if(copyKeyOnValueIsEmpty){
	        				value = keyValue.length==2?keyValue[1]:keyValue[0];
	        			}
	        			else{
	        				value = keyValue.length==2?keyValue[1]:null;
	        			}
	        			
    	        		map.put(key, value);
    	        		
    	        		if(isReversal){
        	        		map.put(value, key);
    	        		}
	        			
	        		}
	        		
	        	}
	    	}
    	}
    	catch(Exception e){
    		map = null;
    	}
    	
    	return map;
    }
    
    public static HashMap<String,String> toMap(String keyValues,String itemRegex,String keyValueRegex, boolean isReversal){
    	return toMap( keyValues, itemRegex, keyValueRegex,  isReversal,true);
    }
    
    public static Map<String,String> toMap(String keyValues,String itemRegex,String keyValueRegex){
    	return toMap( keyValues, itemRegex, keyValueRegex,  true,true);
    }
    
    
 
    
	public static String generateLetterString(int letterCount){
		String str="";
		for(int i=0;i<letterCount;i++){
		str= str+(char) (Math.random ()*26+'A');
		}
		
		return str;
	}
    
	public static String toString(Object[] objs,String separator){
		StringBuffer sb = new StringBuffer();
		if(objs != null){
			if(separator==null){
				separator = "";
			}
			
			
			for(int i = 0; i < objs.length; i++){
				
				if(objs[i]==null || "".equals(objs[i])){
					continue;
				}
				
				sb.append(objs[i]);
				if(i < objs.length-1){
					sb.append(separator);
				}
			}
		}
		return sb.toString();
	}
	
	public static String[] toArray(String str,String separator){
		String[] array = null;
		if(str != null){
			array = str.split(separator);
		}
		return array;
	}
	public static String[] toArray(String str){
		return toArray(str,",");
	}
	
	public static String replaceNull(String str,String replace){
		String result = str;
		if(str==null){
			result = replace;
		}
		return result;
	}
	
	/**
	 * 首字母小写
	 * @param string
	 * @return
	 */
	public static String lowerCaseFirstLetter(String string) {
		char[] cs = string.toCharArray();
		if(cs[0] >= 'A' && cs[0] <= 'Z') {
			cs[0] += 32;
		}
		return String.valueOf(cs);
	}
	
	/**
	 * img src 图片路径增加前缀
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static String prefixSrc(String str,String prefix){
		if(StringUtils.isBlank(str)){
			return "";
		}
		return str = str.replaceAll("(.*?)src=\"(.*?)", "$1src=\"" + prefix + "$2");
	}
	
	/**
	 * html解码
	 * @param str
	 * @return
	 */
	public static String htmlUnescape(String str){
		if(StringUtils.isBlank(str)){
			return "";
		}
		String regEx = "\\&[a-zA-Z]{1,10};";
		Pattern p_special = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m_special = p_special.matcher(str);
		boolean isTrue = m_special.find();
		if(isTrue){
			str = HtmlUtils.htmlUnescape(str);
		}
		return str;
	}

	/**
	 * 替换字符串中全部的特殊子串
	 * @param mainString 被字符串
	 * @param oldString 原有子串
	 * @param newString 新的子串
	 * @return 替换后字符串
	 */
	public static String replaceString(String mainString, String oldString,
									   String newString) {
		if (mainString == null) {
			return null;
		}
		if (oldString == null || oldString.length() == 0) {
			return mainString;
		}
		if (newString == null) {
			newString = "";
		}
		int i = mainString.lastIndexOf(oldString);
		if (i < 0)
			return mainString;
		StringBuffer mainSb = new StringBuffer(mainString);
		while (i >= 0) {
			mainSb.replace(i, i + oldString.length(), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}

	/**
	 * 将Object对象转换为字符串，如果为null or ""，则输出默认值
	 * @param obj
	 * @param defVal
     * @return
     */
	public static String toStr(Object obj, String defVal){
		String result = defVal;
		if(obj != null && !"".equals(obj)){
			result = obj.toString();
		}
		return result;
	}

	public static String toStr(Object obj){
		return toStr(obj, "");
	}

	/**
	 * 替换StringBuffer中第一个oldStr为oldStr
	 * @param sb
	 * @param oldStr
	 * @param newStr
     * @return
     */
	public static StringBuffer replaceFirst(StringBuffer sb, String oldStr, String newStr) {
		int i = sb.indexOf(oldStr);
		int oldLen = oldStr.length();
		int newLen = newStr.length();
		if (i > -1) {
			sb.delete(i, i + oldLen);
			sb.insert(i, newStr);
		}
		return sb;
	}
    
    public static void main(String[] args){
    	System.out.println(StringUtils.toHtml("123\r\n123"));
    	System.out.println(toString(new String[]{"a","b","c"},":"));
    	String str = "<p>\r\n\t<img alt=\"\" src=\"/userfiles/1/images/integral/integralMallProduct/2015/09/1(4).jpg\" style=\"width: 680px; height: 1187px;\" /><img alt=\"\" src=\"/userfiles/1/images/integral/integralMallProduct/2015/09/2(4).jpg\" style=\"width: 680px; height: 1220px;\" /></p>";
    	String prefix = "http://139.196.5.141:8080/fd/f";
    	System.out.println("before str:::::"+str);
    	System.out.println("after replaceStr:::::"+prefixSrc(str,prefix));
//		System.out.println(vagueName("张忌sss"));
//		System.out.println(vagueCertNum("610523197803181672"));
//		System.out.println(vagueMobile("13816713626"));
//		System.out.println(vagueEducationSchool("多大地方爱的色放爱的色放啊"));
//		System.out.println(vagueAddress("上海市宝山区湄浦路"));
//		System.out.println(vagueCreditCardNo("6222810000671116"));
//    	try{
//    		URL url = new URL("http://www.whpu.edu.cn//index.html");
//    		System.out.println(url.getHost());
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
	}
    
}
