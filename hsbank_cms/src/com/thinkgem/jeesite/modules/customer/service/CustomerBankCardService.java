/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.query.QueryReq;
import com.thinkgem.jeesite.common.yeepay.toUnbindBankCard.ToUnbindBankCardReq;
import com.thinkgem.jeesite.common.yeepay.toUnbindBankCard.ToUnbindBankCardResp;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.Record;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.UnbindRecordResp;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBankCardDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBankCardHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerCreditAuthDao;
import com.thinkgem.jeesite.modules.entity.CustomerBankCard;
import com.thinkgem.jeesite.modules.entity.CustomerBankCardHis;
import com.thinkgem.jeesite.modules.entity.CustomerCreditAuth;

/**
 * 会员银行卡信息Service
 * @author ydt
 * @version 2015-06-25
 */
@Service
@Transactional(readOnly = true)
public class CustomerBankCardService extends CrudService<CustomerBankCardDao, CustomerBankCard> {
	@Autowired
	private CustomerCreditAuthDao customerCreditAuthDao;
	@Autowired
	private CustomerBankCardHisDao customerBankCardHisDao;
	@Autowired
	private  CustomerBankCardDao customerBankCardDao;
	@Autowired
	private DirectReqUtils directReqUtils;
	
	public CustomerBankCard get(String id) {
		return super.get(id);
	}
	
	public List<CustomerBankCard> findList(CustomerBankCard customerBankCard) {
		return super.findList(customerBankCard);
	}
	
	public Page<CustomerBankCard> findPage(Page<CustomerBankCard> page, CustomerBankCard customerBankCard) {
		return super.findPage(page, customerBankCard);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerBankCard customerBankCard) {
		super.save(customerBankCard);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerBankCard customerBankCard) {
		super.delete(customerBankCard);
	}

	public CustomerBankCard getByAccountId(Long accountId) {
		return dao.getByAccountId(accountId);
	}

	/**
	 * 用户发起绑卡请求时更新customerBankCard表requestNo
	 * 		1.若指定accountId的customerBankCard记录存在，则做更新操作；否则做插入操作
	 * 		2.插入customerBankCardHis表新记录
	 * @param customerBankCard
	 */
	@Transactional(readOnly = false)
	public void updateBindBankCardRequestNo(CustomerBankCard customerBankCard) {
		boolean recoredIsExist = dao.recordIsExistWithAccountId(customerBankCard.getAccountId());
		if(recoredIsExist) {
			customerBankCard.setLastModifyDt(customerBankCard.getOpDt());
			customerBankCard.setOpDt(new Date());
			dao.update(customerBankCard);
		} else {
			customerBankCard.setOpDt(new Date());
			dao.insert(customerBankCard);
		}
		
//		CustomerBankCardHis customerBankCardHis = new CustomerBankCardHis();
//		customerBankCardHis.setAccountId(customerBankCard.getAccountId());
//		customerBankCardHis.setServiceCode(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_REQ);
//		customerBankCardHis.setRequestNo(customerBankCard.getRequestNo());
//		customerBankCardHis.setOpDt(new Date());
//		customerBankCardHis.setOpTermCode(ProjectConstant.DICT_DEFAULT_VALUE);
//		customerBankCardHisDao.insert(customerBankCardHis);
	}

	/**
	 * 用户发起取消绑卡请求时
	 * 		1.更新customerBankCard表requestNo
	 * 		2.插入customerBankCardHis表新记录
	 * @param customerBankCard
	 */
	@Transactional(readOnly = false)
	public void updateUnbindBankCardRequestNo(CustomerBankCard customerBankCard) {
		customerBankCard.setLastModifyDt(customerBankCard.getOpDt());
		customerBankCard.setOpDt(new Date());
		dao.update(customerBankCard);
		
//		CustomerBankCardHis customerBankCardHis = new CustomerBankCardHis();
//		customerBankCardHis.setAccountId(customerBankCard.getAccountId());
//		customerBankCardHis.setServiceCode(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOUNBINDBANKCARD_REQ);
//		customerBankCardHis.setRequestNo(customerBankCard.getRequestNo());
//		customerBankCardHis.setOpDt(new Date());
//		customerBankCardHis.setOpTermCode(ProjectConstant.DICT_DEFAULT_VALUE);
//		customerBankCardHisDao.insert(customerBankCardHis);
	}

	@Transactional(readOnly = false)
	public void update(CustomerBankCard customerBankCard) {
		dao.update(customerBankCard);
	}

	/**
	 * 绑定银行卡易宝notify处理
	 * 		1.根据requestNo更新customerBankCard表
	 * 		2.根据requestNo更新customerBankCardHis表
	 * @param requestNo
	 * @param cardNo
	 * @param cardStatus
	 * @param bank
	 */
	@Transactional(readOnly = false)
	public void updateWithBindBankCardNotify(String requestNo, String cardNo,
			String cardStatus, String bank, String message) {
		
		CustomerBankCard customerBankCard = customerBankCardDao.getByRequestNo(requestNo);
		if(customerBankCard != null){
			customerBankCard.setRequestNo(requestNo);
			customerBankCard.setCardNo(cardNo);
			customerBankCard.setCardStatusCode(cardStatus);
			customerBankCard.setBankCode(bank);
			customerBankCard.setMessage(message);
			customerBankCard.setOpDt(new Date()); 
			customerBankCard.setLastModifyDt(new Date()); 
			dao.update(customerBankCard);
		}
		
		
//		CustomerBankCardHis customerBankCardHis = new CustomerBankCardHis();
//		customerBankCardHis.setRequestNo(requestNo);
//		customerBankCardHis.setCardNo(cardNo);
//		customerBankCardHis.setCardStatusCode(cardStatus);
//		customerBankCardHis.setBankCode(bank);
//		customerBankCardHis.setMessage(message);
//		customerBankCardHisDao.updateInfoWithRequestNo(customerBankCardHis);
	}

	

	/**
	 * 取消绑卡易宝callback处理
	 * 		1.根据requestNo更新customerBackCard表：cardNo、cardStatusCode、bankCode置为空
	 * 		2.根据requestNo更新customerBankCardHis表
	 * @param requestNo
	 * @param message
	 */
	public void updateWithUnbindBankCardCallback(String requestNo, String message) {
		CustomerBankCard customerBankCard = new CustomerBankCard();
		customerBankCard.setRequestNo(requestNo);
		customerBankCard.setMessage(message);
		dao.updateInfoWithRequestNo(customerBankCard);
		
//		CustomerBankCardHis customerBankCardHis = new CustomerBankCardHis();
//		customerBankCardHis.setRequestNo(requestNo);
//		customerBankCardHis.setMessage(message);
//		customerBankCardHisDao.updateInfoWithRequestNo(customerBankCardHis);
	}

	/**
	 * 信用报告认证
	 * 		1.更新customerBankCard表creditReportFile
	 * 		2.插入customerBankCardHis表新记录
	 * 		3.更新customerCreditAuth表creditReportAuthCode为提交审核状态（'1'）
	 * @param customerBankCard
	 */
	@Transactional(readOnly = false)
	public void authCreditReportInfo(CustomerBankCard customerBankCard) {
		dao.update(customerBankCard);
		
//		CustomerBankCardHis customerBankCardHis = new CustomerBankCardHis();
//		customerBankCardHis.setAccountId(customerBankCard.getAccountId());
//		customerBankCardHis.setCreditReportFile(customerBankCard.getCreditReportFile());
//		customerBankCardHis.setOpDt(new Date());
//		customerBankCardHis.setOpTermCode(ProjectConstant.DICT_DEFAULT_VALUE);
//		customerBankCardHisDao.insert(customerBankCardHis);
		
		CustomerCreditAuth customerCreditAuth = customerCreditAuthDao.getByAccountId(customerBankCard.getAccountId());
		customerCreditAuth.setCreditReportAuthCode("1");
		customerCreditAuthDao.update(customerCreditAuth);
	}
	
	/**
	 * 检查是否绑定银行卡
	 * @param accountId
	 * @return
	 */
	public boolean hasBindBankCard(AccountInfoResp accountInfoResp){
		if(accountInfoResp == null){
			return false;
		}
		if(accountInfoResp != null && StringUtils.isNotBlank(accountInfoResp.getCardNo())){
			return true;
		}
		return false;
	}

	/**
	 * 解绑银行卡
	 * @param platformUserNo
	 * @param accountId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ToUnbindBankCardResp toUnbindBankCard(String platformUserNo,Long accountId ) {
		
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOUNBINDBANKCARD_REQ, platformUserNo);
		
		ToUnbindBankCardReq toUnbindBankCardReq = new ToUnbindBankCardReq();
		toUnbindBankCardReq.setRequestNo(requestNo);
		toUnbindBankCardReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		toUnbindBankCardReq.setPlatformUserNo(platformUserNo);
		
		String unbindBankCardResp = directReqUtils.dirReq(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_UNBIND_BANK_CARD_REQ,
				toUnbindBankCardReq.toReq(),
				YeepayConstant.PROJECT_INTERFACE_DIRECT_UNBIND_BANK_CARD_SERVICE);
		ToUnbindBankCardResp toUnbindBankCardRespObj = JaxbMapper.fromXml(
				unbindBankCardResp, ToUnbindBankCardResp.class);
		
		if(toUnbindBankCardRespObj != null){
			if("1".equals(toUnbindBankCardRespObj.getCode())){//如果解绑申请成功，更新绑卡信息
				CustomerBankCard customerBankCard = customerBankCardDao.getByAccountId(accountId);
				
				//插入一条历史记录
				CustomerBankCardHis customerBankCardHis = new CustomerBankCardHis();
				customerBankCardHis.setAccountId(customerBankCard.getAccountId());
				customerBankCardHis.setCardNo(customerBankCard.getCardNo());
				customerBankCardHis.setCardStatusCode(customerBankCard.getCardStatusCode());
				customerBankCardHis.setBankCode(customerBankCard.getBankCode());
				customerBankCardHis.setMessage(customerBankCard.getMessage());
				customerBankCardHis.setRequestNo(customerBankCard.getRequestNo());
				customerBankCardHis.setOpDt(customerBankCard.getOpDt());
				customerBankCardHis.setCreditReportFile(customerBankCard.getCreditReportFile());
				customerBankCardHis.setChangeDt(new Date());
				customerBankCardHisDao.insert(customerBankCardHis);
				
				//清空customerBankCard
				customerBankCard.setCardNo(null);
				customerBankCard.setCardStatusCode(null);
				customerBankCard.setBankCode(null);
				customerBankCard.setMessage(null);
				customerBankCard.setRequestNo(null);
				customerBankCard.setCreditReportFile(null);
				customerBankCard.setOpDt(null);
				customerBankCard.setUnbindRequestNo(requestNo);
				customerBankCard.setLastModifyDt(new Date());
				dao.update(customerBankCard);
			}
		}
		
		return toUnbindBankCardRespObj;
	}

	/**
	 * 更新RequestNo
	 * @param accountId
	 * @param requestNo
	 */
	@Transactional(readOnly = false)
	public void updateBankCardRequestNo(Long accountId, String requestNo) {
		CustomerBankCard customerBankCard = this.getByAccountId(accountId);
		customerBankCard.setRequestNo(requestNo);
		customerBankCard.setLastModifyDt(new Date());
		this.update(customerBankCard);
		
	}
	
	

	
	

	/**
	 * //易宝远程验证当前有没有正预约解绑的卡,
	 * @param unbindRequestNo 解绑是发送给易宝的请求号
	 * @return 三种情况，1没查到返回1, 2有预约解绑返回2, 3没有预约解绑返回3
	 */
	public int hasAppointment(String unbindRequestNo) {
		
		QueryReq req = new QueryReq();
		req.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		req.setRequestNo(unbindRequestNo);
		req.setMode("UNBIND_RECORD");
		String respStr = directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_QUERY_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_QUERY_SERVICE);
		UnbindRecordResp unbindRecordResp = JaxbMapper.fromXml(
				respStr, UnbindRecordResp.class);
		
		
		Record record = (unbindRecordResp != null && unbindRecordResp.getRecords() != null && unbindRecordResp.getRecords().size()==1 ? unbindRecordResp.getRecords().get(0) : null);
		int hasAppointmentYeePayVerify = 1;
		if(record != null){
			hasAppointmentYeePayVerify = "INIT".equals(record.getStatus())?2:3;
		}
		return hasAppointmentYeePayVerify;
	}

}