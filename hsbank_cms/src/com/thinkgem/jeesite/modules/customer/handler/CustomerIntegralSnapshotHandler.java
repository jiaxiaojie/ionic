package com.thinkgem.jeesite.modules.customer.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.customer.dao.CustomerIntegralHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerIntegralSnapshotDao;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralHis;

@Component
public class CustomerIntegralSnapshotHandler {
	@Autowired
	private CustomerIntegralSnapshotDao customerIntegralSnapshotDao;
	@Autowired
	private CustomerIntegralHisDao customerIntegralHisDao;

	/**
	 * 花生豆值变化
	 * 		1.修改customerIntegralSnapshot
	 * 		2.添加customerIntegralHis记录
	 * @param accountId
	 * @param changeValue
	 * @param changeType
	 * @param changeReason
	 * @param opTerm
	 */
	public void changeIntegralValue(long accountId, int changeValue, String changeType, String changeReason, String opTerm) {
		customerIntegralSnapshotDao.updateIntegralValue(accountId, changeValue);
		
		CustomerIntegralHis customerIntegralHis = new CustomerIntegralHis();
		customerIntegralHis.setAccountId(accountId);
		customerIntegralHis.setChangeVal(changeValue);
		customerIntegralHis.setChangeType(changeType);
		customerIntegralHis.setChangeReason(changeReason);
		customerIntegralHis.setOpDt(new Date());
		customerIntegralHis.setOpTermType(opTerm);
		customerIntegralHisDao.insert(customerIntegralHis);
	}

	/**
	 * 用户签到
	 * @param accountId
	 * @param changeValue
	 * @param consecutiveSignDays
	 * @param changeType
	 * @param changeReason
     * @param opTerm
     */
	public void customerSign(long accountId, int changeValue, int consecutiveSignDays,String changeType, String changeReason, String opTerm){
		//更新连续签到天数
		customerIntegralSnapshotDao.updateConsecutiveDays(accountId, consecutiveSignDays);
		this.changeIntegralValue(accountId, changeValue, changeType, changeReason, opTerm);

	}

}
