package com.thinkgem.jeesite.modules.project.service.util.handler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerInvestmentMoneyHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentMoneyHis;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.hsbank.util.type.NumberUtil;

/**
 * 平台垫付金额处理
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
@Component("platformAmountHandler")
public class PlatformAmountHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CustomerInvestmentMoneyHisDao customerInvestmentMoneyHisDao;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	
	/**
	 * 参数校验
	 * <p/>
	 * if(【本次投资使用的平台垫付金额】= 0) {return;}
	 * if(【本次投资使用的平台垫付金额】< 0) {抛出异常;}
	 * if(【本次投资使用的平台垫付金额】> 0) {
	 * 		if(【本次投资使用的平台垫付金额】>【当前用户总的平台垫付金额】) {抛出异常;}
	 * }
	 * <p/>
	 * @param accountId 		   	 投资人账户Id
	 * @param platformAmount 		 平台垫付金额
	 */
	public void check(Long accountId, Double platformAmount) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":check Account 【" + accountId + "】  platformAmount :" + platformAmount + " start...");
		if (platformAmount == 0) {
			return;
		}
		if (platformAmount < 0){
			throw new ServiceException("本次投资使用的平台垫付金额【" + platformAmount+"】< 0");
		}
		//查询会员账户汇总信息
		CustomerBalance customerBalance = customerBalanceDao.get(String.valueOf(accountId));
		Double investmentMoney = customerBalance.getPlatformAmount();
		if (investmentMoney != null && investmentMoney.compareTo(platformAmount) < 0) {
			throw new ServiceException("平台垫付金额不足：本次投资使用的平台垫付金额 =【" + platformAmount + "】， 当前用户总的平台垫付金额 =【" + investmentMoney+"】");
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":check Account 【" + accountId + "】  platformAmount :" + platformAmount + " end...");
	}
	
	/**
	 * 数据入库：更新投资人的【可使用平台垫付金额】
	 * @param accountId 		    	投资人账户Id
	 * @param platformAmount 			平台垫付金额
	 */
	public void updatePlatformAmount(Long accountId, Double platformAmount) {
		logger.debug("updatePlatformAmount the accountId:" + accountId +" ,platformAmount:" + platformAmount);
		if(platformAmount !=null && platformAmount.compareTo(0.00) > 0){
			customerBalanceDao.updatePlatformAmount(accountId, platformAmount);
		}
	}
	
	/**
	 * 数据入库：新增【平台垫付金额】使用记录
	 * <p/>
	 * @param accountId 		           	 投资人账户Id
	 * @param recordId 						 投资记录Id
	 * @param platformAmount 				 平台垫付金额
	 */
	public void addPlatformAmountRecord(Long accountId, String recordId, Double platformAmount){
		if(platformAmount !=null && platformAmount.compareTo(0.00) > 0){
			logger.info("---------" + DateUtils.formatDateTime(new Date())
					+ ":addPlatformAmountRecord start...");
			CustomerInvestmentMoneyHis customerInvestmentMoneyHis = new CustomerInvestmentMoneyHis();
			customerInvestmentMoneyHis.setAccountId(accountId);
			customerInvestmentMoneyHis.setRecordId(NumberUtil.toLong(recordId, 0L));
			customerInvestmentMoneyHis.setChangeVal(platformAmount);
			customerInvestmentMoneyHis.setChangeTypeCode(ProjectConstant.CHANGE_TYPE_TICKET_INVEST);
			customerInvestmentMoneyHis.setUseDt(new Date());
			customerInvestmentMoneyHisDao.insert(customerInvestmentMoneyHis);
			logger.info("---------" + DateUtils.formatDateTime(new Date())
					+ ":addPlatformAmountRecord end...");
		}
	}
	
	/**
	 * 新手项目，检查是否新手
	 * @param accountId
	 */
	public void checkIsNewCustomer(Long accountId,ProjectBaseInfo projectInfo){
		if(ProjectConstant.DICT_DEFAULT_VALUE.equals(projectInfo.getIsNewUser())){
			Integer investCount = projectInvestmentRecordDao.getInvestCountIsNewCustomerByAccountId(String.valueOf(accountId));
			if(investCount != null && investCount > 0){
				throw new ServiceException("新手专享，您不能投资！");
			}
		}
		
	}
}
