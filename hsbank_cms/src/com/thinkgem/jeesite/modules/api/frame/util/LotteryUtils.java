package com.thinkgem.jeesite.modules.api.frame.util;

import static oracle.net.aso.C01.r;

/**
 * Created by pc on 2016/6/15.
 */
public class LotteryUtils {
    /**
     * 根据概率抽奖
     * @param probability 概率
     * @return
     */
    public static Boolean doLottery(Fraction probability){
        Boolean result = false;
        int randomNumber = (int) ( probability.getDenominator() * Math.random() + 1);
        if(randomNumber <= probability.getMember()){
            result = true;
        }
        return result;
    }
}
