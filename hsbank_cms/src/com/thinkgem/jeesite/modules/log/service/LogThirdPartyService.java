/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.log.dao.LogThirdPartyDao;

/**
 * 第三方交互日志Service
 * @author yangtao
 * @version 2015-08-03
 */
@Service
@Transactional(readOnly = true)
public class LogThirdPartyService extends CrudService<LogThirdPartyDao, LogThirdParty> {

	public LogThirdParty get(String id) {
		return super.get(id);
	}
	
	public List<LogThirdParty> findList(LogThirdParty logThirdParty) {
		return super.findList(logThirdParty);
	}
	
	public Page<LogThirdParty> findPage(Page<LogThirdParty> page, LogThirdParty logThirdParty) {
		return super.findPage(page, logThirdParty);
	}
	
	@Transactional(readOnly = false)
	public void save(LogThirdParty logThirdParty) {
		super.save(logThirdParty);
	}
	
	@Transactional(readOnly = false)
	public void delete(LogThirdParty logThirdParty) {
		super.delete(logThirdParty);
	}

	/**
	 * 注册请求日志记录
	 * @param requestNo
	 * @param reqContent
	 */
	@Transactional(readOnly = false)
	public void insertToRegisterReq(String requestNo, String reqContent) {
		String service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOREGISTER_REQ;
		insert(requestNo, service, reqContent);
	}

	/**
	 * 充值请求日志记录
	 * @param requestNo
	 * @param reqContent
	 */
	@Transactional(readOnly = false)
	public void insertToRechargeReq(String requestNo, String reqContent) {
		String service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORECHARGE_REQ;
		insert(requestNo, service, reqContent);
	}

	/**
	 * 修改手机号请求日志记录
	 * @param requestNo
	 * @param reqContent
	 */
	@Transactional(readOnly = false)
	public void insertToResetMobileReq(String requestNo, String reqContent) {
		String service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_REQ;
		insert(requestNo, service, reqContent);
	}
	
	/**
	 * 重置交易密码请求日志
	 * @param requestNo
	 * @param reqContent
	 */
	@Transactional(readOnly = false)
	public void insertToResetTransPwdReq(String requestNo, String reqContent) {
		String service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_REQ;
		insert(requestNo, service, reqContent);
	}

	/**
	 * 绑卡请求日志记录
	 * @param requestNo
	 * @param reqContent
	 */
	@Transactional(readOnly = false)
	public void insertToBindBankCardReq(String requestNo, String reqContent) {
		String service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_REQ;
		insert(requestNo, service, reqContent);
	}

	private void insert(String requestNo, String service, String reqContent) {
		LogThirdParty logThirdParty = new LogThirdParty();
		logThirdParty.setRequestNo(requestNo);
		logThirdParty.setService(service);
		logThirdParty.setReqContent(reqContent);
		logThirdParty.setReqDt(new Date());
		
		dao.insert(logThirdParty);
	}
	
	/**
	 * 易宝callback通知处理
	 * @param requestNo
	 * @param resp
	 * @param respCode
	 */
	@Transactional(readOnly = false)
	public void updateWithCallback(String requestNo, String respContent, String respCode) {
		dao.updateWithCallback(requestNo, respContent, respCode, new Date());
	}

	/**
	 * 注册请求易宝notify通知处理
	 * @param requestNo
	 * @param respContent
	 * @param respCode
	 */
	@Transactional(readOnly = false)
	public void updateWithNotify(String requestNo, String notifyContent, String respCode) {
		dao.updateWithNotify(requestNo, notifyContent, respCode, new Date());
	}
	
	/**
	 * 取消绑卡请求日志记录
	 * @param requestNo
	 * @param reqContent
	 */
	@Transactional(readOnly = false)
	public void insertToUnbindBankCardReq(String requestNo, String reqContent) {
		String service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOUNBINDBANKCARD_REQ;
		insert(requestNo, service, reqContent);
	}
	/**
	 * 转账授权请求日志记录
	 * @param bizType
	 * @param requestNo
	 * @param reqContent
	 */
	@Transactional(readOnly = false)
	public void insertToCpTransaction(String bizType, String requestNo, String reqContent) {
		String service = "";
		if(YeepayConstant.BIZ_TYPE_TENDER.equals(bizType)){
			//投标（TENDER）
			service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ;
		} else if(YeepayConstant.BIZ_TYPE_CREDIT_ASSIGNMENT.equals(bizType)){
			//债权转让
			service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_CREDIT_ASSIGNMENT_REQ;
		} else if(YeepayConstant.BIZ_TYPE_REPAYMENT.equals(bizType)){
			//REPAYMENT
			service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_REPAYMENT_REQ;
		} else if(YeepayConstant.BIZ_TYPE_TRANSFER.equals(bizType)){
			//TRANSFER
			service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TRANSFER_REQ;
		}
		insert(requestNo, service, reqContent);
	}
	/**
	 * 根据requestNo获取日志
	 * @return
	 */
	public LogThirdParty getByRequestNo(String requestNo) {
		return dao.getByRequestNo(requestNo);
	}
	
	/**
	 * 根据requestNo和service获取日志
	 * @param requestNo
	 * @param service
	 * @return
	 */
	public LogThirdParty getByRequestNoAndService(String requestNo, String service) {
		return dao.getByRequestNoAndService(requestNo,service);
	}
	
	/**
	 * 判断是否充值过
	 * @param platformUserNo
	 * @param service
	 * @return
	 */
	public boolean isHasRecharged(String platformUserNo){
		String service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORECHARGE_REQ;
		LogThirdParty log =dao.getByPlatformUserNoAndService(platformUserNo, service);
		if(log != null && "1".equals(log.getRespCode())){
			return true;
		}
		return false;
	}
	/**
	 * 提现请求日志记录
	 * @param requestNo
	 * @param reqContent
	 */
	@Transactional(readOnly = false)
	public void insertToWithdrawReq(String requestNo, String reqContent) {
		insert(requestNo, YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_REQ, reqContent);
	}
	
	/**
	 * 提现请求日志记录
	 * @param requestNo
	 * @param reqContent
	 */
	@Transactional(readOnly = false)
	public void insertAuthorizeautoRepaymentReq(String requestNo, String reqContent) {
		insert(requestNo, YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_REQ, reqContent);
	}
	
}