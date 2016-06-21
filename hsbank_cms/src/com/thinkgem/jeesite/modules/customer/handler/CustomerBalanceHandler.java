package com.thinkgem.jeesite.modules.customer.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerFreeWithdrawCountHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.CustomerFreeWithdrawCountHis;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

@Component
public class CustomerBalanceHandler {
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CustomerBalanceHisDao customerBalanceHisDao;
	@Autowired
	private CustomerFreeWithdrawCountHisDao customerFreeWithdrawCountHisDao;

	public void upDateByTransferFromPlatform(long accountId, double amount, String changeType, String changeReason, String ext1) {
		customerBalanceDao.updateBalance(accountId, amount);
		CustomerBalance customerBalance = customerBalanceDao.get(accountId + "");
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(accountId);
		customerBalanceHis.setChangeVal(amount);
		customerBalanceHis.setBalance(customerBalance.getGoldBalance());
		customerBalanceHis.setChangeType(changeType);
		customerBalanceHis.setChangeReason(changeReason);
		customerBalanceHis.setExt1(ext1);
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(ProjectConstant.OP_TERM_DICT_PC);
		customerBalanceHisDao.insert(customerBalanceHis);
	}

	public void updateFreeWithCount(long accountId, int changeValue, String changeType) {
		customerBalanceDao.updateFreeWithdrawCount(accountId, changeValue);
		CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis = new CustomerFreeWithdrawCountHis();
		customerFreeWithdrawCountHis.setAccountId(accountId);
		customerFreeWithdrawCountHis.setChangeVal(changeValue);
		customerFreeWithdrawCountHis.setChangeTypeCode(changeType);
		customerFreeWithdrawCountHis.setGetDt(new Date());
		customerFreeWithdrawCountHisDao.insert(customerFreeWithdrawCountHis);
	}

}
