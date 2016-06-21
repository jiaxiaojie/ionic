/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 项目运营数据汇总Entity
 * @author ydt
 * @version 2015-12-25
 */
public class ProjectOperationSummary extends DataEntity<ProjectOperationSummary> {
	
	private static final long serialVersionUID = 1L;
	private Double financeAmount;		// 累计募资额
	private Double repayPrincipal;		// 累计偿还本金
	private Double repayInterest;		// 累计偿还利息
	private Double xinFinanceAmount;		// 新花生累计募资额
	private Double xinRepayPrincipal;		// 新花生累计偿还本金
	private Double xinRepayInterest;		// 新花生累计偿还利息
	private Double yueFinanceAmount;		// 月花生累计募资额
	private Double yueRepayPrincipal;		// 月花生累计偿还本金
	private Double yueRepayInterest;		// 月花生累计偿还利息
	private Double shuangyueFinanceAmount;		// 双月花生累计募资额
	private Double shuangyueRepayPrincipal;		// 双月花生累计偿还本金
	private Double shuangyueRepayInterest;		// 双月花生累计偿还利息
	private Double jiFinanceAmount;		// 季花生累计募资额
	private Double jiRepayPrincipal;		// 季花生累计偿还本金
	private Double jiRepayInterest;		// 季花生累计偿还利息
	private Double shuangjiFinanceAmount;		// 双季花生累计募资额
	private Double shuangjiRepayPrincipal;		// 双季花生累计偿还本金
	private Double shuangejiRepayInterest;		// 双季花生累计偿还利息
	private Double nianFinanceAmount;		// 年花生累计募资额
	private Double nianRepayPrincipal;		// 年花生累计偿还本金
	private Double nianRepayInterest;		// 年花生累计偿还利息
	private Date date;		// 日期
	private Date beginDate;		// 开始 日期
	private Date endDate;		// 结束 日期
	
	
	
	public ProjectOperationSummary() {
		super();
	}
	

	public ProjectOperationSummary(String id){
		super(id);
	}
	@ExcelField(title="日期", type=0, align=2, sort=10)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@ExcelField(title="累计募资额", align=2, sort=15)
	@Length(min=0, max=13, message="累计募资额长度必须介于 0 和 13之间")
	public Double getFinanceAmount() {
		return financeAmount;
	}

	public void setFinanceAmount(Double financeAmount) {
		this.financeAmount = financeAmount;
	}
	@ExcelField(title="累计偿还本金", align=2, sort=20)
	public Double getRepayPrincipal() {
		return repayPrincipal;
	}

	public void setRepayPrincipal(Double repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}
	@ExcelField(title="累计偿还利息", align=2, sort=25)
	@Length(min=0, max=11, message="累计偿还利息长度必须介于 0 和 13 之间")
	public Double getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(Double repayInterest) {
		this.repayInterest = repayInterest;
	}
	@ExcelField(title="新花生累计募资额", align=2, sort=30)
	@Length(min=0, max=11, message="新花生累计募资额长度必须介于 0 和 13 之间")
	public Double getXinFinanceAmount() {
		return xinFinanceAmount;
	}

	public void setXinFinanceAmount(Double xinFinanceAmount) {
		this.xinFinanceAmount = xinFinanceAmount;
	}
	@ExcelField(title="新花生累计偿还本金", align=2, sort=40)
	public Double getXinRepayPrincipal() {
		return xinRepayPrincipal;
	}

	public void setXinRepayPrincipal(Double xinRepayPrincipal) {
		this.xinRepayPrincipal = xinRepayPrincipal;
	}
	@ExcelField(title="新花生累计 偿还利息", align=2, sort=50)
	public Double getXinRepayInterest() {
		return xinRepayInterest;
	}

	public void setXinRepayInterest(Double xinRepayInterest) {
		this.xinRepayInterest = xinRepayInterest;
	}
	@ExcelField(title="月花生累计募资额", align=2, sort=60)
	public Double getYueFinanceAmount() {
		return yueFinanceAmount;
	}

	public void setYueFinanceAmount(Double yueFinanceAmount) {
		this.yueFinanceAmount = yueFinanceAmount;
	}
	@ExcelField(title="月花生累计偿还本金", align=2, sort=70)
	public Double getYueRepayPrincipal() {
		return yueRepayPrincipal;
	}

	public void setYueRepayPrincipal(Double yueRepayPrincipal) {
		this.yueRepayPrincipal = yueRepayPrincipal;
	}
	@ExcelField(title="月花生累计偿还利息", align=2, sort=80)
	public Double getYueRepayInterest() {
		return yueRepayInterest;
	}

	public void setYueRepayInterest(Double yueRepayInterest) {
		this.yueRepayInterest = yueRepayInterest;
	}
	@ExcelField(title="双月花生累计募资额", align=2, sort=90)
	public Double getShuangyueFinanceAmount() {
		return shuangyueFinanceAmount;
	}

	public void setShuangyueFinanceAmount(Double shuangyueFinanceAmount) {
		this.shuangyueFinanceAmount = shuangyueFinanceAmount;
	}
	@ExcelField(title="双月花生累计偿还本金", align=2, sort=100)
	public Double getShuangyueRepayPrincipal() {
		return shuangyueRepayPrincipal;
	}

	public void setShuangyueRepayPrincipal(Double shuangyueRepayPrincipal) {
		this.shuangyueRepayPrincipal = shuangyueRepayPrincipal;
	}
	@ExcelField(title="双月花生累计偿还利息", align=2, sort=110)
	public Double getShuangyueRepayInterest() {
		return shuangyueRepayInterest;
	}

	public void setShuangyueRepayInterest(Double shuangyueRepayInterest) {
		this.shuangyueRepayInterest = shuangyueRepayInterest;
	}
	@ExcelField(title="季花生累计募资额", align=2, sort=120)
	public Double getJiFinanceAmount() {
		return jiFinanceAmount;
	}

	public void setJiFinanceAmount(Double jiFinanceAmount) {
		this.jiFinanceAmount = jiFinanceAmount;
	}
	@ExcelField(title="季花生累计偿还本金", align=2, sort=130)
	public Double getJiRepayPrincipal() {
		return jiRepayPrincipal;
	}

	public void setJiRepayPrincipal(Double jiRepayPrincipal) {
		this.jiRepayPrincipal = jiRepayPrincipal;
	}
	@ExcelField(title="新季花生累计偿还利息", align=2, sort=140)
	public Double getJiRepayInterest() {
		return jiRepayInterest;
	}

	public void setJiRepayInterest(Double jiRepayInterest) {
		this.jiRepayInterest = jiRepayInterest;
	}
	@ExcelField(title="双季花生累计募资额", align=2, sort=150)
	public Double getShuangjiFinanceAmount() {
		return shuangjiFinanceAmount;
	}
	public void setShuangjiFinanceAmount(Double shuangjiFinanceAmount) {
		this.shuangjiFinanceAmount = shuangjiFinanceAmount;
	}
	@ExcelField(title="双季花生累计偿还本金", align=2, sort=160)
	public Double getShuangjiRepayPrincipal() {
		return shuangjiRepayPrincipal;
	}

	public void setShuangjiRepayPrincipal(Double shuangjiRepayPrincipal) {
		this.shuangjiRepayPrincipal = shuangjiRepayPrincipal;
	}
	@ExcelField(title="双季花生累计偿还利息", align=2, sort=170)
	public Double getShuangejiRepayInterest() {
		return shuangejiRepayInterest;
	}

	public void setShuangejiRepayInterest(Double shuangejiRepayInterest) {
		this.shuangejiRepayInterest = shuangejiRepayInterest;
	}
	@ExcelField(title="年花生累计募资额", align=2, sort=180)
	public Double getNianFinanceAmount() {
		return nianFinanceAmount;
	}

	public void setNianFinanceAmount(Double nianFinanceAmount) {
		this.nianFinanceAmount = nianFinanceAmount;
	}
	@ExcelField(title="年花生累计偿还本金", align=2, sort=190)
	public Double getNianRepayPrincipal() {
		return nianRepayPrincipal;
	}

	public void setNianRepayPrincipal(Double nianRepayPrincipal) {
		this.nianRepayPrincipal = nianRepayPrincipal;
	}
	@ExcelField(title="年花生累计偿还利息", align=2, sort=200)
	public Double getNianRepayInterest() {
		return nianRepayInterest;
	}

	public void setNianRepayInterest(Double nianRepayInterest) {
		this.nianRepayInterest = nianRepayInterest;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}