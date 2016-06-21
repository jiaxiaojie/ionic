package com.thinkgem.jeesite.modules.api.to;


public class InvestmentRankResp {

	private String ranking;		// 名次
	private String mobile;		// 手机号
	private String amount;		// 投资金额
	private String type;			//类型（week:周排行；month:月排行）
	private String year;			//年份
	private String month;			//月份
	private String week;			//周
	private String genre;		//类型2（1:投资排行[default],2,年化排行）
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	

}
