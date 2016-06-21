package com.thinkgem.jeesite.modules.api.frame.util;

/**
 * 分数类
 * Created by 万端瑞 on 2016/6/15.
 */
public class Fraction{

    private Integer denominator;//分母
    private Integer member; //分子

    public Fraction(Integer denominator, Integer member) {
        this.denominator = denominator;
        this.member = member;
    }

    public Integer getDenominator() {
        return denominator;
    }

    public void setDenominator(Integer denominator) {
        this.denominator = denominator;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }
}