package com.thinkgem.jeesite.common.utils;

import java.math.BigDecimal;

public class NumberUtils {
	private static final int DEF_DIV_SCALE = 10;
	private static final double EPS = 0.000000001;
	
	/**
	 * 保留两位小数（四舍五入）
	 * @param v
	 * @return
	 */
	public static Double formatWithTwoScale(Double v) {
		return formatWithScale(v, 2);
	}

	/**
	 * 格式化double数据，保留scale位小数（不四舍五入）
	 * @param v
	 * @param scale
	 * @return
	 */
	public static String formatNotRoundWithScale(String vStr, int scale) {
		int endIndex = (vStr.indexOf(".")==-1?vStr.length():vStr.indexOf(".")+scale+1);
		for(int i = 0; i < endIndex-vStr.length();i++){
			vStr += "0";
		}
		vStr =  vStr.substring(0,endIndex);
		return vStr;
	}
	
	public static Double formatNotRoundWithScale(Double v, int scale){
		BigDecimal bv = new BigDecimal(v);
		Double result = Double.parseDouble(formatNotRoundWithScale(bv.toString(), scale));
		result = NumberUtils.formatWithScale(result,scale);
		return result;
	}
	
	

	/**
	 * 格式化double数据，保留scale位小数（四舍五入）
	 * @param v
	 * @param scale
	 * @return
	 */
	public static double formatWithScale(Double v, int scale) {
		if(v == null){
			return 0.00;
		}
		return new BigDecimal(v.toString()).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 保留两位小数（四舍五入）
	 * @param v
	 * @param scale
	 * @return
	 */
	public static Double formatWithScaleOnNotNull(Double v) {
		Double result = v;
		if(result != null){
			result = formatWithScale(v, 2);
		}
		return result;
	}
	
	/**
	 * 获取数字的小数位数
	 * @param v
	 * @return
	 */
	public static int getScale(Double v) {
		return new BigDecimal(v.toString()).scale();
	}
	
	/**
	 * * 两个Double数相加 *
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double add(Double... vs) {
		BigDecimal b = new BigDecimal("0");
		for(Double v : vs) {
			if(v != null) {
				b = b.add(new BigDecimal(v.toString()));
			}
		}
		return new Double(b.doubleValue());
	}

	/**
	 * * 两个Double数相减 *
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1 == null ? "0" : v1.toString());
		BigDecimal b2 = new BigDecimal(v2 == null ? "0" : v2.toString());
		return new Double(b1.subtract(b2).doubleValue());
	}

	/**
	 * * 两个Double数相乘 *
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1 == null ? "0" : v1.toString());
		BigDecimal b2 = new BigDecimal(v2 == null ? "0" : v2.toString());
		return new Double(b1.multiply(b2).doubleValue());
	}

	/**
	 * * 两个Double数相除 *
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double div(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1 == null ? "0" : v1.toString());
		BigDecimal b2 = new BigDecimal(v2 == null ? "0" : v2.toString());
		return new Double(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	/**
	 * * 两个Double数相除，并保留scale位小数 *
	 * 
	 * @param v1
	 * @param v2
	 * @param scale
	 * @return Double
	 */
	public static Double div(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
	/**
	 * 两数比较操作
	 * @param v1
	 * @param v2
	 * @return -1:v1<v2; 0:v1=v2; 1:v1>v2
	 */
	public static int compare(Double v1, Double v2) {
		return Double.compare(v1, v2);
	}
	
	/**
	 * 两数比较操作
	 * @param v1
	 * @param v2
	 * @return -1:v1<v2; 0:v1=v2; 1:v1>v2
	 */
	public static int compare(Long v1, Long v2) {
		return Long.compare(v1, v2);
	}
	
	/**
	 * 取绝对值
	 * @param v
	 * @return
	 */
	public static double abs(Double v) {
		return Math.abs(v);
	}
	
	/**
	 * 判断double是否为整数
	 * @param v
	 * @return
	 */
	public static boolean isInt(double v) {
		return v-Math.round(v) <= EPS ? true : false;
	}
	
	/**
	 * 判断是否1的整数倍
	 * @param v
	 * @return
	 */
	public static boolean isIntMulOfOne(double v){
		return v%1 == 0 ? true : false;
	}
	
	/**
	 * 
	 * @param numberStr 字符串数字
	 * @param defaultVal  如果转换失败，则使用此值
	 * @return
	 */
	public static Integer parseInt(String numberStr, Integer defaultVal){
		Integer result = defaultVal;
		try{
			result = Integer.parseInt(numberStr);
		}
		catch(Exception e){
		}
		
		return result;
	}

	/**
	 * 转化为Double数据
	 * @param val
	 * @return
	 */
	public static Double parseDouble(Object val) {
		return val == null ? null : Double.parseDouble(val.toString());
	}
}
