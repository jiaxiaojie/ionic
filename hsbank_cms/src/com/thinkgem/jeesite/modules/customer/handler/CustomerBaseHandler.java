package com.thinkgem.jeesite.modules.customer.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.customer.dao.CustomerBaseDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerOrgExtendInfoDao;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerBaseHis;
import com.thinkgem.jeesite.modules.entity.CustomerOrgExtendInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

@Component
public class CustomerBaseHandler {
	@Autowired
	private CustomerBaseDao customerBaseDao;
	@Autowired
	private CustomerOrgExtendInfoDao customerOrgExtendInfoDao;

	/**
	 * 保存会员基本信息
	 * 		1.如果是新会员信息，则添加会员基本信息 记录；否则更新会员基本信息
	 * 		2.添加会员基本信息变更历史记录
	 * 		3.如果为企业会员，则更新企业会员扩展信息
	 * @param customerBase 会员基本信息
	 */
	public void save(CustomerBase customerBase) {
		boolean isNewRecord = false;
		if (customerBase.getIsNewRecord()){
			customerBase.setCreateDt(new Date());
			customerBaseDao.insert(customerBase);
			isNewRecord = true;
		}else {
			customerBase.setLastModifyDt(new Date());
			customerBaseDao.update(customerBase);
		}
		CustomerBaseHis customerBaseHis = new CustomerBaseHis();
		customerBaseHis.setCustomerBase(customerBase);
		customerBaseHis.setCreateDt(new Date());
		//暂未实现
//		customerBaseHisDao.save(customerBaseHis);
		CustomerOrgExtendInfo customerOrgExtendInfo = customerBase.getCustomerOrgExtendInfo();
		if(!ProjectConstant.DICT_DEFAULT_VALUE.equals(customerBase.getAccountType())) {
			customerOrgExtendInfo.setCustomerId(customerBase.getCustomerId());
			if(!isNewRecord) {
				customerOrgExtendInfoDao.insert(customerOrgExtendInfo);
			} else {
				customerOrgExtendInfoDao.update(customerOrgExtendInfo);
			}
		}	
	}
	
	
}
