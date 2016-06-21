/**
 * 
 */
package com.thinkgem.jeesite.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangtao
 *
 */
public class PayMoneyUtil {
	public static void main(String[] args) {
//		 初始化成员变量
		 double payment = 51.5;
		 int num = 24;
		 double ret = 0.12;
		 // 调用 payPerMonth() 方法
		 double y = payPerMonthByYearRate(payment, ret, num);
		
		 // 打印输出 y 的值
		 System.out.println("每月还:" + y);
		 System.out.println("总计还:" + y * num);
		 System.out.println("月综合费（千分之）："
		 + getMonthFeeByYearRate(payment, ret, num) * 1000);

		// @param loanAmount 还款金额
		// * @param loanPeriod 还款期限
		// * @param rates 还款年利率
		// * @param repayment 还款方式
		//* @param addMoney 增加手续费
		// bill("2", "12", "18", "4", "2012-02-29","0");
		bill("0.5555","3","13","1","2001-01-01","0");
		// bill("29", "12", "18.9", "1", "2013-07-01","0");
//		bill("5", "10", "13", "2", "2013-07-01","0");

	}

	/**
	 * 计算等额本息还款
	 * 
	 * @param payment
	 *            总贷款数额 10000
	 * @param ret
	 *            年利率 如 10.0%
	 * @param num
	 *            分期月份数量 12
	 * @return 每月还款金额 879.16
	 */
	public static double payPerMonthByYearRate(double payment, double ret,
			int num) {
		ret = ret / 12;
		double payPerMonth = payment * ret * Math.pow((1 + ret), num)
				/ (Math.pow((1 + ret), num) - 1);
		BigDecimal bd = new BigDecimal(payPerMonth);
		payPerMonth = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return (double) payPerMonth;
	}

	/**
	 * 计算等额本息还款,计算最后一个月的还款额
	 * 
	 * @param payment
	 *            总贷款数额 10000
	 * @param ret
	 *            年利率 如 10.0%
	 * @param num
	 *            分期月份数量 12
	 * @return 每月还款金额 879.16
	 */
	public static double payLastMonthYearRate(double payment, double ret,
			int num) {
		ret = ret / 12;
		double payPerMonth = payment * ret * Math.pow((1 + ret), num)
				/ (Math.pow((1 + ret), num) - 1);
		BigDecimal bd = new BigDecimal(payPerMonth);
		double retPerMonth = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		double abs = (payPerMonth - retPerMonth) * num;
		retPerMonth = retPerMonth - abs;
		return (double) retPerMonth;
	}

	/**
	 * 计算等额月服务费，精确到千分之几点几
	 * 
	 * @param payment
	 *            总贷款数额 10000
	 * @param ret
	 *            年利率 如 10.0%
	 * @param num
	 *            分期月份数量 12
	 *            
	 * @return 每月服务费 3.8
	 */
	public static double getMonthFeeByYearRate(double payment, double ret,
			int num) {
		if(payment==0){
			return 0;
		}
		// 每月还款额
		double monthPer = payPerMonthByYearRate(payment, ret, num);
		// 总计还款额
		double sumFee = monthPer * num;
		// 每月利息额
		double monthFee = (sumFee - payment) / num;
		// 每月服务费
		double monthRate = monthFee / payment;
		
		BigDecimal bd = new BigDecimal(monthRate);
		monthRate = bd.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
		return monthRate;
	}

	/**
	 * 还款账单 anjianchao add
	 * 
	 * @param loanAmount
	 *            还款金额 (单位:万)
	 * @param loanPeriod
	 *            还款期限
	 * @param rates
	 *            还款年利率
	 * @param repayment
	 *            还款方式
	 *  // 1 到期还本付息
		// 2 先息后本
		// 3 等额本息
		// 4 等额本金
	 * @param addMoney 每期增加手续费
	 * @return List<Map<String,String>>( 键值 principal：存储本金 键值 interest：存储利息 键值
	 *         benxi：存储本息 键值endDate：还款日期)
	 * 
	 */
	
	public static List<Map<String, String>> bill(String loanAmount,
			String loanPeriod, String rates, String repayment,
			String tradingStartTime,String addMoney) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();// list集合
		Map<String, String> map = null;// map集合
		DecimalFormat df = new DecimalFormat("#.00");// 保留小数点后两位
		/* 数据暂定为 */
		BigDecimal loanmoney = new BigDecimal(loanAmount);// 借款金额

		BigDecimal paramMoney = new BigDecimal(10000);// 借款金额基数(10000元数据库存储数据以万元为单位)
		BigDecimal totalmomey = loanmoney.multiply(paramMoney); // 贷款总金额
		BigDecimal remainingPrincipal = totalmomey;// 剩余本金
		BigDecimal rate = new BigDecimal(rates);// 年利率
		BigDecimal paramRate = new BigDecimal(100);// 利率参数(数据库存储为例如：20)
		BigDecimal paramMonth = new BigDecimal(12);// 一年12个月
		BigDecimal taxrate = rate
				.divide(paramRate, 6, BigDecimal.ROUND_HALF_UP); // 当前利率 (年利率)
		BigDecimal tax = taxrate
				.divide(paramMonth, 9, BigDecimal.ROUND_HALF_UP); // 当前利率 (月利率)
		BigDecimal time = new BigDecimal(loanPeriod);// 借款期限（月）
		BigDecimal num = new BigDecimal(1);// 用于计算本息
		BigDecimal root = num.add(tax);// 跟

		/* 等额本金*/
		if ("4".equals(repayment)) {
			// 根据借款期限计算每月所还本金
			BigDecimal oneMonthMoney = totalmomey.divide(time, 6,
					BigDecimal.ROUND_HALF_UP);
			for (int j = 1; j <= Integer.parseInt(loanPeriod); j++) {
				// 把变量转换为BigDecimal数据格式
				BigDecimal paramj = new BigDecimal(j);
				// 计算每月所还利息
				BigDecimal taxMoney = new BigDecimal(0);
				if (j == 1) {
					taxMoney = totalmomey.multiply(tax);
				} else {
					taxMoney = (totalmomey.subtract(oneMonthMoney
							.multiply(paramj.subtract(new BigDecimal(1)))))
							.multiply(tax);
				}
				//增加手续费至利息
				taxMoney=taxMoney.add(new BigDecimal(addMoney));
				
				
				BigDecimal money = oneMonthMoney.add(taxMoney);// 每月所还本息
				remainingPrincipal = remainingPrincipal.subtract(oneMonthMoney);
				map = new HashMap<String, String>();// map集合
				map.put("endDate",
						timeSubtraction(tradingStartTime, String.valueOf(j)));// 还款时间
				map.put("principal", df.format(oneMonthMoney));// 每月本金
				map.put("interest", df.format(taxMoney));// 实际每月利息
				map.put("benxi", df.format(money));// 每月还款金额
				map.put("shengyu", df.format(remainingPrincipal));
				list.add(map);// 填充到list集合
				System.out.println("每月应还款金额 = " + df.format(money) + "实际每月利息="
						+ df.format(taxMoney) + "每月应还款本金= "
						+ df.format(oneMonthMoney) + "剩余本金="
						+ df.format(remainingPrincipal) + "还款日期="
						+ timeSubtraction(tradingStartTime, String.valueOf(j)));
			}
		} else if ("3".equals(repayment)) {//等额本息
			for (int i = 1; i <= Integer.parseInt(loanPeriod); i++) {
				// 每月利息额
				BigDecimal monthinterestmoney = totalmomey
						.multiply(tax)
						.multiply(
								root.pow(Integer.parseInt(loanPeriod))
										.subtract(root.pow(i - 1)))
						.divide((root.pow(Integer.parseInt(loanPeriod))
								.subtract(num)),
								6, BigDecimal.ROUND_HALF_UP);
				// 每月还款额
				BigDecimal monthmoney = totalmomey
						.multiply(tax)
						.multiply(root.pow(Integer.parseInt(loanPeriod)))
						.divide((root.pow(Integer.parseInt(loanPeriod))
								.subtract(num)),
								6, BigDecimal.ROUND_HALF_UP);
				// 每月本金
				BigDecimal principalmoney = monthmoney
						.subtract(monthinterestmoney);
				// 实际每月利率
				BigDecimal monthacive = monthinterestmoney.divide(monthmoney,
						6, BigDecimal.ROUND_HALF_UP);
				remainingPrincipal = remainingPrincipal
						.subtract(principalmoney);
				map = new HashMap<String, String>();// map集合
				map.put("endDate",
						timeSubtraction(tradingStartTime, String.valueOf(i)));// 还款时间
				//增加手续费至利息
				monthmoney=monthmoney.add(new BigDecimal(addMoney));
				monthinterestmoney=monthinterestmoney.add(new BigDecimal(addMoney));
				
				map.put("benxi", df.format(monthmoney));// 每月还款金额
				map.put("principal", df.format(principalmoney));// 每月本金
				map.put("interest", df.format(monthinterestmoney));// 实际每月利息
				map.put("monthacive", df.format(monthacive));// 实际每月利率
				map.put("shengyu", df.format(remainingPrincipal));
				list.add(map);
				System.out.println("每月还款金额=," + df.format(monthmoney) + ",每月本金="
						+ df.format(principalmoney) + ",实际每月利息=,"
						+ df.format(monthinterestmoney) + ",剩余本金=,"
						+ df.format(remainingPrincipal) + ",还款日期=,"
						+ timeSubtraction(tradingStartTime, String.valueOf(i)));
			}

		} else if ("1".equals(repayment)) {//到期还本付息
			// 本息
			BigDecimal money = totalmomey.multiply(tax).multiply(time)
					.add(totalmomey);

			BigDecimal oneMonthMoney = totalmomey;// 本金
			BigDecimal taxMoney = totalmomey.multiply(tax).multiply(time);// 利息
			
			//增加手续费至利息
			taxMoney=taxMoney.add((new BigDecimal(addMoney)).multiply(new BigDecimal(loanPeriod)));
			
			map = new HashMap<String, String>();// map集合
			map.put("benxi", df.format(money));// 本息
			map.put("endDate",
					timeSubtraction(tradingStartTime,
							String.valueOf(loanPeriod)));// 还款时间

			map.put("principal", df.format(oneMonthMoney));// 存储本金
			map.put("interest", df.format(taxMoney));// 存储利息
			map.put("shengyu", df.format(0));// 存储利息
			list.add(map);
			System.out.println("本息="
					+ df.format(money)
					+ "本金="
					+ df.format(oneMonthMoney)
					+ "利息="
					+ df.format(taxMoney)
					+ "剩余本金="
					+ df.format(0)
					+ "还款日期="
					+ timeSubtraction(tradingStartTime,
							String.valueOf(loanPeriod)));
		} else if ("2".equals(repayment)) {//先息后本
			Date repayDate = DateUtils.parseDate(tradingStartTime);
			// 本息
			BigDecimal dayRate = (new BigDecimal(rates)).divide(new BigDecimal(
					360),6,BigDecimal.ROUND_HALF_DOWN);// 日利率
			BigDecimal dayMoney = totalmomey.multiply(dayRate);
			for (int j = 1; j <= Integer.parseInt(loanPeriod); j++) {
				map = new HashMap<String, String>();// map集合
				String nextDate=timeSubtraction(DateUtils.formatDate(repayDate,"yyyy-MM-dd"),
						String.valueOf(1));
				map.put("endDate",nextDate);// 还款时间
				int days=(int)DateUtils.getDistanceOfTwoDate(repayDate,DateUtils.parseDate(nextDate)); //月还款额
				BigDecimal monthMoney=dayMoney.multiply(new BigDecimal(days)).divide(new BigDecimal(100));
				map.put("principal", df.format(0));// 存储本金
				map.put("benxi", df.format(monthMoney));// 本息
				map.put("interest", df.format(monthMoney));// 存储利息
				map.put("shengyu", df.format(totalmomey));// 存储利息
				list.add(map);
				repayDate=DateUtils.parseDate(nextDate);
				System.out.println("本息="
						+ df.format(monthMoney)
						+ "本金=0"
						+ "利息="
						+ df.format(monthMoney)
						+ "剩余本金="
						+ df.format(totalmomey)
						+ "还款日期="
						+ nextDate);
				if(j == Integer.parseInt(loanPeriod)){
					map = new HashMap<String, String>();// map集合
					map.put("principal", df.format(totalmomey));// 存储本金
					map.put("benxi", df.format(totalmomey));// 本息
					map.put("interest", df.format(0));// 存储利息
					map.put("shengyu", df.format(0));// 存储利息
					map.put("endDate",nextDate);// 还款时间
					list.add(map);
					System.out.println("本息="
							+ map.get("benxi")
							+ "本金="+map.get("principal")
							+ "利息="+map.get("interest")
							+ "剩余本金="+map.get("shengyu")
							+ "还款日期="+map.get("endDate"));
				}
			}

		}
		return list;
	}

	/**
	 * 指定日期加几个月
	 * 
	 * @param startDate
	 * @param val
	 * @return
	 */
	public static String timeSubtraction(String startDate, String val) {
		int month = Integer.parseInt(val);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sFmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			cal.setTime(sFmt.parse(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (month != 0) {
			cal.add(Calendar.MONTH, month);
		}
		return sFmt.format(cal.getTime());
	}

}
