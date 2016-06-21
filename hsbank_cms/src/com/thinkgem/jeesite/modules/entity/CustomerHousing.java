/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员房产信息Entity
 * @author ydt
 * @version 2015-06-25
 */
public class CustomerHousing extends DataEntity<CustomerHousing> {
      
      private static final long serialVersionUID = 1L;
      private Long customerId;            // 会员编号
      private String housingAddress;      //房产地址
      private String housingFile;         //房产附件
      private String loanYear;            //贷款年限
      private Integer loanValue;                //未还贷款
      
      private Long accountId;
      private String accountType;   //账号类型

      public CustomerHousing() {
            super();
      }

      public CustomerHousing(String id){
            super(id);
      }
      
      public Long getCustomerId() {
            return customerId;
      }

      public void setCustomerId(Long customerId) {
            this.customerId = customerId;
      }

      @Length(min=0, max=100, message="房产地址长度必须介于 0 和 100 之间")
      public String getHousingAddress() {
            return housingAddress;
      }

      public void setHousingAddress(String housingAddress) {
            this.housingAddress = housingAddress;
      }

      @Length(min=0, max=500, message="房产附件长度必须介于 0 和 500 之间")
      public String getHousingFile() {
            return housingFile;
      }

      public void setHousingFile(String housingFile) {
            this.housingFile = housingFile;
      }

      @Length(min=0, max=4, message="贷款年限长度必须介于 0 和 4 之间")
      public String getLoanYear() {
            return loanYear;
      }

      public void setLoanYear(String loanYear) {
            this.loanYear = loanYear;
      }

      public Integer getLoanValue() {
            return loanValue;
      }

      public void setLoanValue(Integer loanValue) {
            this.loanValue = loanValue;
      }

      public String getAccountType() {
            return accountType;
      }

      public void setAccountType(String accountType) {
            this.accountType = accountType;
      }
      
      public Long getAccountId() {
            return accountId;
      }

      public void setAccountId(Long accountId) {
            this.accountId = accountId;
      }

     /**
  	 * 个人收入得分
  	 * 无房 0分 租房 2分 所有或购买 8分
  	 * @return
  	 */
	public Float getHousingScore() {
		return null;
	}

}