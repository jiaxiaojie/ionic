/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.api.po.TransRecordReq;
import com.thinkgem.jeesite.modules.api.to.PageResponse;
import com.thinkgem.jeesite.modules.api.to.TransRecordResp;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 会员账户余额变更流水Service
 * @author ydt
 * @version 2015-06-26
 */
@Service
@Transactional(readOnly = true)
public class CustomerBalanceHisService extends CrudService<CustomerBalanceHisDao, CustomerBalanceHis> {
    
	public CustomerBalanceHis get(String id) {
		return super.get(id);
	}
	
	public List<CustomerBalanceHis> findList(CustomerBalanceHis customerBalanceHis) {
		return super.findList(customerBalanceHis);
	}
	
	public Page<CustomerBalanceHis> findPage(Page<CustomerBalanceHis> page, CustomerBalanceHis customerBalanceHis) {
		return super.findPage(page, customerBalanceHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerBalanceHis customerBalanceHis) {
		super.save(customerBalanceHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerBalanceHis customerBalanceHis) {
		super.delete(customerBalanceHis);
	}

	/**
	 * 根据查询字段得到page：【accountId】【changeType】【startDateTime】【endDateTime】
	 * @param accountId
	 * @param changeType
	 * @param pageListSearchBean
	 * @return
	 */
	public Page<CustomerBalanceHis> searchPage(Long accountId,String changeType, PageSearchBean pageSearchBean) {
		Page<CustomerBalanceHis> page = new Page<CustomerBalanceHis>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("changeType", changeType);
		map.put("accountId", accountId);
		map.put("startDateTime", pageSearchBean.getStartDateTime());
		map.put("endDateTime", pageSearchBean.getEndDateTime());
		page.setList(dao.searchList(map));
		return page;
	}

	/**
	 * 分页查询交易记录
	 * @param page
	 * @param accountId
     * @return
     */
	public PageResponse findBalanceHisPage(Page<CustomerBalanceHis> page, Long accountId, TransRecordReq recordReq) {
		Map<String, Object> map = new HashMap<String,Object>();
		PageResponse<TransRecordResp> pageResponse =  new PageResponse<TransRecordResp>();
		PageSearchBean pageSearchBean = new PageSearchBean();
		pageSearchBean.setStartDateTime(DateUtils.stringToDateShort(recordReq.getBeginDate()));
		pageSearchBean.setEndDateTime(DateUtils.stringToDateShort(recordReq.getEndDate()));
		if(pageSearchBean.getStartDateTime() == null && pageSearchBean.getEndDateTime() == null){
			pageSearchBean.setDefaultDateRangeWithMonths(-6);
		}
		map.put("page", page);
		map.put("changeType", getChangeType(String.valueOf(recordReq.getType())));
		map.put("accountId", accountId);
		map.put("startDateTime", pageSearchBean.getStartDateTime());
		map.put("endDateTime", pageSearchBean.getEndDateTime());
		List<CustomerBalanceHis> list = dao.findPageList(map);
		page.setList(list);
		this.getRecordList(list,pageResponse,page);
		return pageResponse;
	}

	/**
	 * 获取交易记录
	 * @param page
	 * @return
	 */
	public static PageResponse getRecordList(List<CustomerBalanceHis> list, PageResponse<TransRecordResp> pageResponse,Page<CustomerBalanceHis> page){
		List<TransRecordResp> pRespList = new ArrayList<TransRecordResp>();
		for(CustomerBalanceHis balanceHis : list){
			TransRecordResp recordResp = new TransRecordResp();
			recordResp.setRecordId(NumberUtils.toLong(balanceHis.getId()));
			recordResp.setOpDate(DateUtils.formatDateTime(balanceHis.getOpDt()));
			recordResp.setType(NumberUtils.toLong(balanceHis.getChangeType()));
			recordResp.setTypeName(DictUtils.getDictLabel(balanceHis.getChangeType(), "customer_balance_change_type_dict", ""));
			recordResp.setDetails(balanceHis.getChangeReason());
			recordResp.setRemarks(balanceHis.getChangeReason());
			recordResp.setBalance(LoanUtil.formatAmount(balanceHis.getBalance()));
			recordResp.setAmount(LoanUtil.formatAmount(balanceHis.getChangeVal()));
			pRespList.add(recordResp);
		}
		pageResponse.setResultList(pRespList);
		pageResponse.setCount(new Long(page.getCount()).intValue());
		return pageResponse;
	}

	/**
	 * 获取查询类型
	 * @param type
	 * @return
     */
	public static String getChangeType(String type){
		StringBuffer changeType = new StringBuffer();
        if(ApiConstant.CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_RECHARGE.equals(type)){
			changeType.append(ProjectConstant.CHANGE_TYPE_BALANCE_NET_B2B_RECHARGE).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_NET_B2C_RECHARGE).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_A_PAY_RECHARGE).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_WH_NO_CARD_RECHARGE).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_SWIFT_RECHARGE).append(",");
		}else if(ApiConstant.CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_WITHDRAW.equals(type)){
			changeType.append(ProjectConstant.CHANGE_TYPE_BALANCE_NORMAL_WITHDRAW).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_URGENT_WITHDRAW).append(",");
		}else if(ApiConstant.CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_INVEST.equals(type)){
			changeType.append(ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_FREEZE).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_CANCEL_FREEZE).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_CONFIRM);
		}else if(ApiConstant.CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_REPAYMENT.equals(type)){
			changeType.append(ProjectConstant.CHANGE_TYPE_BALANCE_REPAYMENT);
		}else if(ApiConstant.CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_REWARD.equals(type)){
			changeType.append(ProjectConstant.CHANGE_TYPE_BALANCE_RECHAGE_GET).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_RECHAGE_RECOMMEND_INVESTMENT).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_RECHARGE_GIVE_AMOUNT).append(",")
			        .append(ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT);
		}else if(ApiConstant.CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_OTHER.equals(type)){
			changeType.append(ProjectConstant.CHANGE_TYPE_BALANCE_CURRENT_PROJECT_REDEEM_PRINCIPAL).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_CURRENT_PROJECT_POLL_INTEREST).append(",")
					.append(ProjectConstant.CHANGE_TYPE_BALANCE_CURRENT_PROJECT_WINDING_UP);
		}
        return StringUtils.surroundSymbol(changeType.toString(), ",","'");
	}
	/**
	 * 我的交易记录api
	 * @param accountId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<CustomerBalanceHis> getTransactionRecordPage(Long accountId, Integer pageNumber, Integer pageSize) {
		Map<String, Object> map = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		map.put("accountId", accountId);
		return dao.getTransactionRecordList(map);
	}

	/**
	 * 获取用户当天的提现次数
	 * @param accountId
	 * @return
	 */
	public int getCustomerTodayWithdrawCount(Long accountId) {
		Date startDateTime = DateUtils.dateFormate(new Date());
		Date endDateTime = new Date();
		return dao.getCustomerWithdrawCountDuringDateTime(accountId, startDateTime, endDateTime);
	}

	/**
	 * 根据accountId、变更值和变更原因获取用户余额变更列表
	 * @param accountId
	 * @param changeValue
	 * @param changeReason
	 * @return
	 */
	public List<CustomerBalanceHis> getListByAccountIdAndChangeValAndChangeReason(long accountId, double changeValue, String changeReason) {
		return dao.getListByAccountIdAndChangeValAndChangeReason(accountId, changeValue, changeReason);
	}
	
	/**
	 * 活动奖励统计
	 * @param accountId
	 * @return
	 */
	public Double getActivityReward(Long accountId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		String changeType = ProjectConstant.CHANGE_TYPE_BALANCE_RECHAGE_RECOMMEND_INVESTMENT
				+ "," + ProjectConstant.CHANGE_TYPE_BALANCE_RECHARGE_GIVE_AMOUNT
				+ "," + ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT;
		map.put("changeType", changeType);
		map.put("changeReason", MarketConstant.CUSTOMER_BLANCE_ALIGNMENT_REMARK);
		return dao.getActivityReward(map);
	}
	
}