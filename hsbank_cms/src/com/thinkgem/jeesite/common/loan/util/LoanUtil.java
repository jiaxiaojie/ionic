package com.thinkgem.jeesite.common.loan.util;

import java.math.BigDecimal;

/**
 * 借贷相关公共方法类
 * @author Arthur.Xie
 * 2015-07-26
 */
public class LoanUtil {
	/** 
     * 年利率转换为月利率 
     * @param annualInterestRate 
     * @return 
     */  
    public static double monthlyInterestRate(double annualInterestRate){  
        return annualInterestRate / 12;  
    }
    
    /**
     * 年化率转化为日利率
     * @param annualInterestRate
     * @return
     */
    public static double dailyInterestRate(double annualInterestRate){
    	return annualInterestRate / 365;
    }
    
    /**
	 * 格式化金额：
	 * <1>.保留两位小数
	 * <2>.ROUND_HALF_UP: 遇到.5的情况时往上近似,例: 1.5 -> 2
	 * @param amount
	 * @return
	 */
	public static Double formatAmount(Double amount) {
		if(amount == null){
			return 0.00;
		}
		return new BigDecimal(amount.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
