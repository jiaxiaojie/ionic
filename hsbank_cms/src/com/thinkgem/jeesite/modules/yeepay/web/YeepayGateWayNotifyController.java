/**
 * 
 */
package com.thinkgem.jeesite.modules.yeepay.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.autoTranscation.AutoTransactionNotify;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTransactionNotify;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTransactionNotify;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoRepayment.ToAuthorizeAutoRepaymentNotify;
import com.thinkgem.jeesite.common.yeepay.toBindBankCard.ToBindBankCardNotify;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionNotify;
import com.thinkgem.jeesite.common.yeepay.toRecharge.ToRechargeNotify;
import com.thinkgem.jeesite.common.yeepay.toRegister.ToRegisterNotify;
import com.thinkgem.jeesite.common.yeepay.toResetMobile.ToResetMobileNotify;
import com.thinkgem.jeesite.common.yeepay.toWithdraw.ToWithdrawNotify;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.current.service.investment.CurrentInvestmentServiceImpl;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBankCard;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.project.service.assignment.AssignmentService;
import com.thinkgem.jeesite.modules.project.service.investment.InvestmentService;
import com.thinkgem.jeesite.modules.project.service.repayment.RepaymentService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * @author ydt
 *
 */
@Controller
@RequestMapping(value = "/yeepay/gateWay/notify")
public class YeepayGateWayNotifyController extends BaseController {
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBankCardService customerBankCardService;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private ProjectInvestmentRecordService investmentRecordService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectExecuteSnapshotService executeSnapshotService;
	@Autowired
	private RepaymentService repaymentService;
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private MarketFacadeService marketFacadeService;
	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	@Autowired
	private CurrentProjectExecuteSnapshotService currentProjectExecuteSnapshotService;
	@Autowired
	private CurrentInvestmentServiceImpl currentInvestmentServiceImpl;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 开通易宝账号notify操作
	 * 		1.开通成功
	 * 			1.更新customerAccount表指定requestNo的hasOpenThirdAccount为1（表示开通易宝账号成功）、更新requestNo
	 * 		  开通失败
	 * 			1.更新customerAccount表指定requestNo的hasOpenThirdAccount为0（表示开通易宝账号失败）、更新requestNo
	 * 		2.更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toRegister")
	@ResponseBody
	public String toRegister(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			ToRegisterNotify bean=JaxbMapper.fromXml(notify, ToRegisterNotify.class);
			String requestNo = bean.getRequestNo();
			String notifyCode = bean.getCode();
			String platformUserNo = bean.getPlatformUserNo();
			
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			if(StringUtils.isBlank(logThirdParty.getNotifyCode())) {
				if("1".equals(notifyCode)) {
					CustomerAccount customerAccount = customerAccountService.getByPlatformUserNo(platformUserNo);
					int changeRowNumber = customerAccountService.updateHasOpenThirdAccount(customerAccount.getAccountId(), ProjectConstant.HASOPENED);
					if(changeRowNumber > 0) {
						//调用开通第三方账号活动
						doOpenThirdAccountActivity(customerAccount.getAccountId());
						//成功开通账号后更新cache中的信息
						CustomerUtils.refreshCache(customerAccount.getAccountId());
					}
				}
				logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			}
			return "SUCCESS";
		}
		return "ERROR";
	}
	
	/**
	 * 调用开通第三方账号活动
	 * @param accountId
	 */
	private void doOpenThirdAccountActivity(long accountId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, accountId);
		map.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_OPEN_THIRD_PARTY);
		map.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
		marketFacadeService.openThirdParty(map);
	}
	
	/**
	 * 绑卡易宝notify操作
	 * 		1.更新customerBankCard表
	 * 		2.更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toBindBankCard")
	@ResponseBody
	public String toBindBankCard(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			ToBindBankCardNotify bean=JaxbMapper.fromXml(notify, ToBindBankCardNotify.class);
			String requestNo = bean.getRequestNo();
			String notifyCode = bean.getCode();
			String cardNo = bean.getCardNo();
			String cardStatus = bean.getCardStatus();
			String bank = bean.getBank();
			String message = bean.getMessage();
			String platformUserNo = bean.getPlatformUserNo();
			
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			if(StringUtils.isBlank(logThirdParty.getNotifyCode())) {
				if("1".equals(notifyCode)) {
					CustomerAccount customerAccount = customerAccountService.getByPlatformUserNo(platformUserNo);
					customerBankCardService.updateWithBindBankCardNotify(requestNo, cardNo, cardStatus, bank, message);
					//调用绑卡活动
					doBindBankCardActivity(customerAccount.getAccountId());
				}
				logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			}
			
			return "SUCCESS";
		}
		return "ERROR";
	}
	
	/**
	 * 调用绑卡活动
	 * @param accountId
	 */
	private void doBindBankCardActivity(long accountId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, accountId);
		map.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_BIND_BANK_CARD);
		map.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
		marketFacadeService.bindBankCard(map);
	}
	
	/**
	 * 充值易宝notify操作
	 * 		如果是第一次notify通知
	 * 			1.更新customerBalance表
	 * 			2.根据充值金额补助可抵扣金额
	 * 			3.更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toRecharge")
	@ResponseBody
	public String toRecharge(HttpServletRequest request) {
		logger.info("===== recharge notify start===");
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			ToRechargeNotify bean=JaxbMapper.fromXml(notify, ToRechargeNotify.class);
			String requestNo = bean.getRequestNo();
			String notifyCode = bean.getCode();
			String amount = bean.getAmount();
			String fee = bean.getFee();
			String platformUserNo = bean.getPlatformUserNo();
			String payProduct = bean.getPayProduct();
			//如果充值手续费由平台出 则对用户来说充值费用为0
			String feeMode = bean.getFeeMode();
			if(YeepayConstant.YEEPAY_FEE_MODE_PLATFORM.equals(feeMode)) {
				fee = "0";
			}
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			CustomerAccount customerAccount = customerAccountService.getByPlatformUserNo(platformUserNo);
			logger.info("notifyCode:" + logThirdParty.getNotifyCode());
			if(StringUtils.isBlank(logThirdParty.getNotifyCode()) && amount != null) {
				logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
				logger.info("update with notify start");
				logger.info("update with notify end");
				if("1".equals(notifyCode)) {
					logger.info("update balance info start. platformUserNo:" + platformUserNo + ", amount:" +amount + "fee:" + fee + ", payProduct:" + payProduct);
					customerBalanceService.updateWithRechargeNotify(platformUserNo, Double.parseDouble(amount), Double.parseDouble(fee), payProduct);
					logger.info("update balance info end.");
					//调用充值活动
					logger.info("recharge marketing activity method invoked start");
					doRechargeActivity(customerAccount.getAccountId(), Double.parseDouble(amount));
					logger.info("recharge marketing activity method invoked end");
				}
			} else if("1".equals(bean.getCode()) && amount == null) {
				//若为快捷充值 需要对银行卡状态进行判断，必要时更新用户银行卡信息
				logger.info("update customer bankCard start");
				CustomerBankCard customerBankCard = customerBankCardService.getByAccountId(customerAccount.getAccountId());
				AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
				String cardNo = accountInfoResp.getCardNo();
				String cardStatus = accountInfoResp.getCardStatus();
				String bank = accountInfoResp.getBank();
				//若在易宝查询到的银行卡信息与数据库中不一致，则更新数据库中的信息
				if(needUpdate(customerBankCard, cardNo, cardStatus)) {
					customerBankCard.setCardNo(cardNo);
					customerBankCard.setCardStatusCode(cardStatus);
					customerBankCard.setBankCode(bank);
					customerBankCard.setOpDt(new Date());
					customerBankCard.setLastModifyDt(new Date());
					customerBankCardService.update(customerBankCard);
				}
				logger.info("update customer bankCard end");
			}
			logger.info("===== recharge notify end===");
			return "SUCCESS";
		}
		logger.info("===== recharge notify end===");
		return "ERROR";
	}

	private boolean needUpdate(CustomerBankCard customerBankCard, String cardNo, String cardStatus) {
		if((cardNo != null && !cardNo.equals(customerBankCard.getCardNo())) || (cardStatus != null && !cardStatus.equals(customerBankCard.getCardStatusCode()))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 调用充值活动
	 * @param accountId
	 * @param amount
	 */
	private void doRechargeActivity(long accountId, double amount) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, accountId);
		map.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_RECHARGE);
		map.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
		map.put(MarketConstant.AMOUNT_PARA, amount);
		marketFacadeService.recharge(map);
	}
	
	/**
	 * 提现易宝notify操作
	 * 		如果是第一次notify通知
	 * 			1.更新customerBalance表
	 * 			2.更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toWithdraw")
	@ResponseBody
	public String toWithdraw(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			ToWithdrawNotify bean=JaxbMapper.fromXml(notify, ToWithdrawNotify.class);
			String requestNo = bean.getRequestNo();
			String notifyCode = bean.getCode();
			String platformUserNo = bean.getPlatformUserNo();
			String amount = bean.getAmount();
			String feeMode = bean.getFeeMode();
			String fee = bean.getFee();
			String withdrawType = bean.getWithdrawType();
			String bankCardNo = bean.getBankCardNo();
			String bank = bean.getBank();
			
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			if(StringUtils.isBlank(logThirdParty.getNotifyCode())) {
				if("1".equals(notifyCode)) {
					CustomerAccount customerAccount = customerAccountService.getByPlatformUserNo(platformUserNo);
					customerBalanceService.updateWithWithdrawNotify(platformUserNo, Double.parseDouble(amount), feeMode, Double.parseDouble(fee), withdrawType, requestNo, bankCardNo, bank);
					//调用提现活动
					doWithdrawActivity(customerAccount.getAccountId(), Double.parseDouble(amount));
				}
				logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			}
			return "SUCCESS";
		}
		return "ERROR";
	}

	/**
	 * 调用提现活动
	 * @param accountId
	 * @param amount
	 */
	private void doWithdrawActivity(long accountId, double amount) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, accountId);
		map.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_WITHDRAW);
		map.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
		map.put(MarketConstant.AMOUNT_PARA, amount);
		marketFacadeService.withDraw(map);
	}
	
	/**
	 * 修改手机号易宝notify操作
	 * 		如果是第一次通知
	 * 			1.
	 * 				若newMobile与原mobile不相同
	 * 					1.若newMobile已存在，修改当前newMobile的customerAccount statusCode为锁定状态
	 * 					2.修改platformUserNo的customerBase mobile为newMobile
	 * 					3.更新cache
	 * 			2.更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toResetMobile")
	@ResponseBody
	public String toResetMobile(String requestNo, HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			ToResetMobileNotify bean=JaxbMapper.fromXml(notify, ToResetMobileNotify.class);
			String notifyCode = bean.getCode();
			String platformUserNo = bean.getPlatformUserNo();
			String newMobile = bean.getMobile();
			
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			if(StringUtils.isBlank(logThirdParty.getNotifyCode())) {
				if("1".equals(notifyCode)) {
					CustomerAccount customerAccount = customerAccountService.getByPlatformUserNo(platformUserNo);
					CustomerBase customerBase = customerBaseService.getByAccountId(customerAccount.getAccountId());
					if(!newMobile.equals(customerBase.getMobile())) {
						customerBaseService.updateWithResetMobileNotify(platformUserNo, newMobile);
						CustomerUtils.refreshCache(customerAccount.getAccountId());
					}
				}
				logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			}
			return "SUCCESS";
		}
		return "ERROR";
	}
	
	/**
	 * 投标易宝notify操作
	 * 		如果是第一次notify通知
	 * 			1.根据成功表示更新相关表
	 * 			2.更新logThirdParty表
	 * @param request
	 * @return
	 */
	@RequestMapping("toCpTransaction")
	@ResponseBody
	public String toCpTransaction(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		boolean result = false;
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			ToCpTransactionNotify bean = JaxbMapper.fromXml(notify, ToCpTransactionNotify.class);
			String requestNo = bean.getRequestNo();
			String notifyCode = bean.getCode();
			String service = YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ + "," + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_CREDIT_ASSIGNMENT_REQ;
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNoAndService(requestNo,StringUtils.surroundSymbol(service,",","'"));
			//根据请求流水号获取投资记录信息
			List<ProjectInvestmentRecord> recordList = investmentRecordService.findListByThirdPartyOrder(requestNo);
			Long projectId = 0L;
			if(recordList == null || recordList.size() <= 0){
				return "ERROR";
			} else {
				projectId = recordList.get(0).getProjectId();
			}
			//获取项目信息
			ProjectBaseInfo projectBaseInfo = new ProjectBaseInfo();
			projectBaseInfo.setId(String.valueOf(projectId));
			projectBaseInfo = projectBaseInfoService.get(projectBaseInfo);
			ProjectExecuteSnapshot pesInvest = executeSnapshotService.getByProjectId(projectBaseInfo.getProjectId());
			projectBaseInfo.setPes(pesInvest);
			if(StringUtils.isBlank(logThirdParty.getNotifyCode())) {
				if("1".equals(notifyCode)) {
					result = true;
				}
				if (YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ.equals(logThirdParty.getService())) {
					//投标
					investmentService.afterInvest(projectBaseInfo, recordList.get(0), result);
				} else if (YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_CREDIT_ASSIGNMENT_REQ.equals(logThirdParty.getService())) {
					//债权转让
					//转让项目流水号
					String transferProjectId = String.valueOf(recordList.get(0).getTransferProjectId());
					ProjectTransferInfo projectTransferInfo = projectTransferInfoService.get(transferProjectId);
					//投资记录
					Long investmentRecordId = projectTransferInfo.getInvestmentRecordId();
					ProjectInvestmentRecord pir = projectInvestmentRecordService.get(String.valueOf(investmentRecordId));
					projectTransferInfo.setPir(pir);
					//执行快照
					ProjectExecuteSnapshot pesAssign = executeSnapshotService.getByProjectIdAndTransferId(String.valueOf(projectId), transferProjectId);
					projectTransferInfo.setProjectExecuteSnapshot(pesAssign);
					//原始项目信息
					projectTransferInfo.setProjectBaseInfo(projectBaseInfo);
					//债权转出人
					String transferor = projectTransferInfo.getTransferor() != null ? projectTransferInfo.getTransferor().longValue() + "" : "";
					ProjectInvestmentRecord recordAssignment = null;
					ProjectInvestmentRecord recordRemaining = null;
					for(ProjectInvestmentRecord pr : recordList){
						String investmentUserId = pr.getInvestmentUserId().longValue() + "";
						if (investmentUserId.equals(transferor)){
							//投资人=债权转出人，就是剩余债权对应的投资记录
							recordRemaining = pr;
						} else {
							recordAssignment = pr;
						}
					}
					assignmentService.afterAssign(projectTransferInfo, recordAssignment, recordRemaining, result);
				} else if (YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TRANSFER_REQ.equals(logThirdParty.getService())) {
					//资金转出
				} else if (YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_REPAYMENT_REQ.equals(logThirdParty.getService())) {
					//还款
				}
				//更新日志
				logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			}
			return "SUCCESS";
		}
		return "ERROR";
	}
	
	/**
	 * 活期产品：投标易宝notify操作
	 * @param request
	 * @return
	 */
	@RequestMapping("toCurrentCpTransaction")
	@ResponseBody
	public String toCurrentCpTransaction(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		boolean result = false;
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			ToCpTransactionNotify bean = JaxbMapper.fromXml(notify, ToCpTransactionNotify.class);
			String requestNo = bean.getRequestNo();
			String notifyCode = bean.getCode();
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			//根据请求流水号获取投资记录信息
			CurrentAccountPrincipalChangeHis cHis = currentAccountPrincipalChangeHisService.getByThirdPartyOrder(requestNo, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT);
			Long projectId = cHis != null ? cHis.getProjectId() : 0L;
			//获取活期项目信息
			CurrentProjectInfo cInfo = currentProjectInfoService.get(String.valueOf(cHis.getProjectId()));
			CurrentProjectExecuteSnapshot executeSnapshot = currentProjectExecuteSnapshotService.getByProjectId(projectId);
			cInfo.setSnapshot(executeSnapshot);
			if(StringUtils.isBlank(logThirdParty.getNotifyCode())) {
				if("1".equals(notifyCode)) {
					result = true;
				}
				currentInvestmentServiceImpl.alterCurrentInvest(cInfo, cHis, result);
				//更新日志
				logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			}
			return "SUCCESS";
		}
		return "ERROR";
	}

	/**
	 * 授权自动还款易宝notify操作
 * 			1.更新projectBaseInfo表
 * 			2.更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toAuthorizeAutoRepayment")
	@ResponseBody
	public String toAuthorizeAutoRepayment(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			ToAuthorizeAutoRepaymentNotify bean=JaxbMapper.fromXml(notify, ToAuthorizeAutoRepaymentNotify.class);
			String requestNo = bean.getRequestNo();
			String notifyCode = bean.getCode();
			//orderNo即为projectId
			String orderNo = bean.getOrderNo();
			orderNo = orderNo.substring(YeepayConstant.YEEPAY_TENDERORDERNO_PREFIX.length(), orderNo.length());
			
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			if(StringUtils.isBlank(logThirdParty.getNotifyCode())) {
				if("1".equals(notifyCode)) {
					projectBaseInfoService.updateIsAutoRepay(orderNo, "1");
				}
				logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			}
			return "SUCCESS";
		}
		return "ERROR";
	}
	
	/**
	 * 活期产品：自动还款授权
	 * @param request
	 * @return
	 */
	@RequestMapping("toAuthorizeCurrentAutoRepayment")
	@ResponseBody
	public String toAuthorizeCurrentAutoRepayment(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			ToAuthorizeAutoRepaymentNotify bean=JaxbMapper.fromXml(notify, ToAuthorizeAutoRepaymentNotify.class);
			String requestNo = bean.getRequestNo();
			String notifyCode = bean.getCode();
			//orderNo即为projectId
			String orderNo = bean.getOrderNo();
			orderNo = orderNo.substring(YeepayConstant.YEEPAY_CURRENT_TENDERORDERNO_PREFIX.length(), orderNo.length());
			
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			if(StringUtils.isBlank(logThirdParty.getNotifyCode())) {
				if("1".equals(notifyCode)) {
					currentProjectInfoService.updateAutoRepay(orderNo, "1");;
				}
				logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			}
			return "SUCCESS";
		}
		return "ERROR";
	}
	/**
	 * 自动还款授权的notify 3.5接口 暂未实现
	 * @param request
	 * @return
	 */
	@RequestMapping("autoTranscation")
	@ResponseBody
	public String autoTranscation(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			AutoTransactionNotify bean=JaxbMapper.fromXml(notify, AutoTransactionNotify.class);
			String notifyCode = bean.getCode();
			String requestNo = bean.getRequestNo();
			//根据请求流水号查询第三方日志表
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			if(logThirdParty==null){
				logger.error("autoTranscation notify is "+ notify +" fund "+requestNo+" is null ");
			}
			//根据请求流水号获取投资记录信息
			//ProjectInvestmentRecord investmentRecord = investmentRecordService.getByThirdPartyOrder(requestNo);
//			String  projectId = String.valueOf(investmentRecord.getProjectId());
			String service = logThirdParty.getService();
			if(YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_NORMAL_REQ.equals(service)){
				//正常还款
				repaymentService.repayNotify(notify);
			}else if(YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_EARLY_REQ.equals(service)){
				//提前还款
				repaymentService.earlyRepayNotify(notify);
			}else if(YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_OVERDUE_REQ.equals(service)){
				//逾期还款
				repaymentService.overdueRepayNotify(notify);
			}
			//根据业务编号调用不同的还款类型RepaymentService 的notify
			logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			return "SUCCESS";
		}
		return "ERROR";
	}
	/**
	 * 活期转账notify操作
	 * @param request
	 * @return
	 */
	@RequestMapping("current/autoTranscation")
	@ResponseBody
	public String currentAutoTranscation(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			AutoTransactionNotify bean=JaxbMapper.fromXml(notify, AutoTransactionNotify.class);
			String notifyCode = bean.getCode();
			String requestNo = bean.getRequestNo();
			//根据请求流水号查询第三方日志表
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			if(logThirdParty==null){
				logger.error("autoTranscation notify is "+ notify +" fund "+requestNo+" is null ");
			}
			logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			return "SUCCESS";
		}
		return "ERROR";
	}
	/**
	 * 转账确认notify 3.7章节 暂未实现
	 * @param request
	 * @return
	 */
	@RequestMapping("completeTransaction")
	@ResponseBody
	public String completeTransaction(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			CompleteTransactionNotify bean=JaxbMapper.fromXml(notify, CompleteTransactionNotify.class);
			String notifyCode = bean.getCode();
			String requestNo=bean.getRequestNo();
			if("1".equals(notifyCode)) {
			}
			
			logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			return "SUCCESS";
		}
		return "ERROR";
	}
	/**
	 * 直接转账的notify 3.4接口 
	 * @param request
	 * @return
	 */
	@RequestMapping("directTransaction")
	@ResponseBody
	public String directTransaction(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(notify, sign)) {
			DirectTransactionNotify bean=JaxbMapper.fromXml(notify, DirectTransactionNotify.class);
			String notifyCode = bean.getCode();

			if("1".equals(notifyCode)) {
				
			}

			String requestNo = bean.getRequestNo();
			logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
			return "SUCCESS";
		}
		return "ERROR";
	}
}
