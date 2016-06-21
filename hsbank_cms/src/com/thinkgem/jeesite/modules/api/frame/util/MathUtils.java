package com.thinkgem.jeesite.modules.api.frame.util;

import java.math.BigDecimal;

/**
 * Created by pc on 2016/6/15.
 */
public class MathUtils {
    /**
     * 计算阶乘
     * @return
     */
    public static BigDecimal factorial(int n){
        BigDecimal result = new BigDecimal(1);
        BigDecimal a;
        for(int i = 2; i <= n; i++){
            a = new BigDecimal(i);//将i转换为BigDecimal类型
            result = result.multiply(a);//不用result*a，因为BigDecimal类型没有定义*操作</span><span>
        }
        return result;
    }
}
