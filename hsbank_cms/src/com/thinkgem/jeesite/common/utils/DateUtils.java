/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd",
			"yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		
		String formatDate = null;
		if(date != null){
			if (pattern != null && pattern.length > 0) {
				formatDate = DateFormatUtils.format(date, pattern[0].toString());
			} else {
				formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
			}
		}
		return formatDate;
	}

	public static String formatDateByPattern(Date date,String pattern){
		return formatDate( date, pattern);//"yyyy-MM-dd"
	}



	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 对日期进行格式化
	 * @param date 日期
	 * @param sf 日期格式
	 * @return 字符串
	 */
	public static String FormatDate(Date date, String sf) {
		if (date == null)
			return "";
		SimpleDateFormat dateformat = new SimpleDateFormat(sf);
		return dateformat.format(date);
	}
	
	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}
	
	/**
	 *  获取连个日期相差的天数
	 * @param endDate
	 * @param beginDate
	 * @return
	 */
	public static long differDay(Date endDate, Date beginDate){
		long t = endDate.getTime() - beginDate.getTime();
		return t / (24 * 60 * 60 * 1000);
	}
	
	

	/**
	 * 获取过去的小时
	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
				* 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
				+ sss;
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 获取两个日期之间的月份 
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static int getDistanceMonthOfTwoDate(Date before, Date after) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(before);
		cal2.setTime(after);
		int distanceYear = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		int distanceMonth = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		int distanceDay = cal2.get(Calendar.DATE) - cal1.get(Calendar.DATE);
		return distanceDay >= 0 ? distanceMonth + 1 + 12 * distanceYear : distanceMonth + 12 * distanceYear;
	}
	
	/**
	 * 获取两个日期之间的月数
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static int getDifferMonthOfTwoDate(String beginDate, String endDate)
			throws Exception {
		int result = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(sdf.parse(beginDate));
		cal2.setTime(sdf.parse(endDate));
		result = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		return result == 0 ? 1 : Math.abs(result);
	}

	/**
	 * 指定日期加分钟数
	 * 
	 * @param theDate
	 * @param days
	 * @return
	 */
	public static Date dateAddMinute(Date theDate, int minutes) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(theDate);
		calendar.add(Calendar.MINUTE, minutes);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 日期加减运算
	 * 
	 * @param theDate
	 * @param days
	 * @return
	 */
	public static Date dateAddDay(Date theDate, int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(theDate);
		calendar.add(Calendar.DATE, days);// 把日期往后增加一天.整数往后推,负数往前移动
		Date date = calendar.getTime();
		return date;
	}
	
	public static Date dateAddDayByStr(String theDate, Integer days) {
		
		return dateAddDay(parseDate(theDate), days);
	}

	/**
	 * 指定日期加几个月
	 * 
	 * @param startDate
	 * @param val
	 * @return
	 */
	public static Date dateAddMonth(Date theDate, int val) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(theDate);
		if (val != 0) {
			calendar.add(Calendar.MONTH, val);
		}
		Date date = calendar.getTime();
		return date;
	}
	
	/**
	 * 格式化日期，去掉时分秒部分
	 * @param theDate
	 * @return
	 */
	public static Date dateFormate(Date theDate){
		return parseDate(formatDate(theDate,"yyyy-MM-dd"));
	}
	
	/** 字符串转换为日期
	 *  @param dateString 日期格式字符串
	 *  @param  sf 日期格式化定义
	 *  @return 转换后的日期
	 */
	public static Date stringToDate(String dateString, String sf) {
		ParsePosition pos = new ParsePosition(0);
		SimpleDateFormat sdf = new SimpleDateFormat(sf);
		Date dt = sdf.parse(dateString, pos);
		return dt;
	}
	
	/**
	 * 字符串转换为日期
	 * @param dateString yyyy-MM-dd
	 * @return 日期
	 */
	public static Date stringToDateShort(String dateString) {
		if(StringUtils.isBlank(dateString)){
			return null;
		}
		String sf = "yyyy-MM-dd";
		Date dt = stringToDate(dateString, sf);
		return dt;
	}
	

	/**
	 * 格式化日期，一天中的最后一个时刻
	 * @param theDate
	 * @return
	 */
	public static Date dateFormateDayOfTheLastTime(Date theDate){
		return parseDate(formatDate(theDate,"yyyy-MM-dd") + " 23:59:59");
	}
	
	/**
	 * 格式化日期，一天中的开始时刻
	 * @param theDate
	 * @return
	 */
	public static Date dateFormateDayOfTheBeginTime(Date theDate){
		return parseDate(formatDate(theDate,"yyyy-MM-dd") + " 00:00:00");
	}
	
	/**
	 * 指定日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekBegin(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday
		return calendar.getTime();
	}

	/**
	 * 指定日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.getTime();
	}

	/**
	 * 指定日期的月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthBegin(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		return calendar.getTime();
	}

	/**
	 * 指定日期的月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 指定日期的年的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearBegin(Date date) {
		int year = Integer.parseInt(FormatDate(date, "yyyy"));
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 指定日期的年的最后天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearEnd(Date date) {
		int year = Integer.parseInt(FormatDate(date, "yyyy"));
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}
	
	/**
     * 根据年获取日历
     * @param year
     * @return
     */
    private static Calendar getCalendarFormYear(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
        cal.set(Calendar.YEAR, year);
        return cal;
    }
    
    /**
     * 根据年份及第几周获取开始日期
     * @param year
     * @param weekNo
     * @return
     */
    public static String getStartDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
               cal.get(Calendar.DAY_OF_MONTH);    
        
    }
    
    /**
     * 根据年份及第几周获取结束日期
     * @param year
     * @param weekNo
     * @return
     */
    public static String getEndDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
               cal.get(Calendar.DAY_OF_MONTH);    
    }
    
    /** 
     * 取得当前日期是多少周 
     * 
     * @param date 
     * @return 
     */ 
     public static int getWeekOfYear(Date date) { 
     Calendar c = new GregorianCalendar(); 
     c.setFirstDayOfWeek(Calendar.MONDAY); 
     c.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek(date)); 
     c.setTime (date);
     return c.get(Calendar.WEEK_OF_YEAR); 
     }
     
     /**
      * 一年的第一周本年有几天
      * @param date
      * @return
      */
     public static int minimalDaysInFirstWeek(Date date){
    	 String year = formatDate(date, "yyyy");
    	 int day = 0;
         if("2015".equals(year)){
        	 day = 4;
         }else if("2016".equals(year)){
        	 day = 3;
         }else if("2017".equals(year) || "2018".equals(year)){
        	 day = 7;
         }else if("2019".equals(year)){
        	 day = 6;
         }
         return day;
     }
     
    /**
     * 取得当前日期是本月的第几周
     * @param date
     * @return
     */
	public static int getWeekOfMonth(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(6);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_MONTH);
	}
    
    /** 
     * 得到某一年周的总数 
     * 
     * @param year 
     * @return 
     */ 
     public static int getMaxWeekNumOfYear(int year) { 
     Calendar c = new GregorianCalendar(); 
     c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
     return getWeekOfYear(c.getTime()); 
     }

	/**
	 * 过去时间
	 * @param date
	 * @return
     */
	public static String  pastTime(Date date) {
		long t = new Date().getTime() - date.getTime();
		long d = 1000*24*60*60;//一天的毫秒数
		long h = 1000*60*60;//一小时的毫秒数
		long m = 1000*60;//一分钟的毫秒数
		long s = 1000;//一秒钟的毫秒数
		String pastTime = "";
		if(t >= d){
			pastTime = t/d + "天前";
		}else if(t >= h){
			pastTime = t/h + "小时前";
		}else if(t >= m){
			pastTime = t/m + "分钟前";
		}else if(t >= s){
			pastTime = t/s + "秒前";
		}
		return pastTime;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getDistanceMonthOfTwoDate(parseDate("2016-01-02", "yyyy-MM-dd"), parseDate("2015-01-01", "yyyy-MM-dd")));
		System.out.println(getDistanceMonthOfTwoDate(parseDate("2016-01-02", "yyyy-MM-dd"), parseDate("2016-01-02", "yyyy-MM-dd")));
		System.out.println(getDistanceMonthOfTwoDate(parseDate("2016-01-02", "yyyy-MM-dd"), parseDate("2016-01-03", "yyyy-MM-dd")));
		System.out.println(getDistanceMonthOfTwoDate(parseDate("2016-01-02", "yyyy-MM-dd"), parseDate("2016-02-01", "yyyy-MM-dd")));
		System.out.println(getDistanceMonthOfTwoDate(parseDate("2016-01-02", "yyyy-MM-dd"), parseDate("2016-02-02", "yyyy-MM-dd")));
		System.out.println(getDistanceMonthOfTwoDate(parseDate("2016-01-02", "yyyy-MM-dd"), parseDate("2018-02-03", "yyyy-MM-dd")));
	}

	public static Object toRealEndDate(Date endDateTime) {
		Date result = endDateTime;
		if("00:00:00".equals(DateUtils.formatDate(endDateTime, "HH:mm:ss"))){
			result = dateAddDay(result,1);
		}
		return result;
	}
}
