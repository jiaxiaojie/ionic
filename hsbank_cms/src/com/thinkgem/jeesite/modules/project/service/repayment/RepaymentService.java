package com.thinkgem.jeesite.modules.project.service.repayment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.common.yeepay.XStreamHandle;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoReq;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.autoTranscation.AutoTransactionNotify;
import com.thinkgem.jeesite.common.yeepay.autoTranscation.AutoTranscationReq;
import com.thinkgem.jeesite.common.yeepay.autoTranscation.AutoTranscationResp;
import com.thinkgem.jeesite.common.yeepay.common.Property;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationReq;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationResp;
import com.thinkgem.jeesite.common.yeepay.directTranscation.MoneyDetail;
import com.thinkgem.jeesite.common.yeepay.freeze.FreezeReq;
import com.thinkgem.jeesite.common.yeepay.freeze.FreezeResp;
import com.thinkgem.jeesite.common.yeepay.query.QueryCpTranscationResp;
import com.thinkgem.jeesite.common.yeepay.query.QueryReq;
import com.thinkgem.jeesite.common.yeepay.unfreeze.UnfreezeReq;
import com.thinkgem.jeesite.common.yeepay.unfreeze.UnfreezeResp;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentPlan;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.log.dao.LogThirdPartyDao;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.MessageFacade;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectDateNodeDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentPlanDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;
import com.thinkgem.jeesite.modules.project.service.repayment.handler.RepaymentPlanHandler;
import com.hsbank.util.collection.MapUtil;
import com.hsbank.util.type.NumberUtil;

@Service
public class RepaymentService {
	public static Logger logger = Logger.getLogger(RepaymentService.class);

	@Autowired
	RepaymentPlanHandler repaymentHandler;
	@Autowired
	CustomerAccountDao customerAccountDao;
	@Autowired
	ProjectRepaymentRecordDao projectRepaymentRecordDao;
	@Autowired
	ProjectRepaymentSplitRecordDao projectRepaymentSplitRecordDao;
	@Autowired
	LogThirdPartyDao logThirdPartyDao;
	@Autowired
	ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	ProjectDateNodeDao projectDateNodeDao;
	@Autowired
	CustomerBalanceHisDao customerBalanceHisDao;
	@Autowired
	CustomerBalanceDao customerBalanceDao;
	@Autowired
	ProjectExecuteSnapshotDao projectExecuteSnapshotDao;
	@Autowired
	ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	ProjectRepaymentPlanDao projectRepaymentPlanDao;
	@Autowired
	MessageFacade messageFacade;
	@Autowired
	private DirectReqUtils directReqUtils;

	/**
	 * 提前还款处理器 <1>.判断项目状态 <2>.根据计算额度判断用户余额 <3>.批量更新老还款计划，逐条生成新还款计划
	 * <4>.根据新还款计划批量还款 <5>.更新项目快照及项目状态 <6>.新生成项目流水
	 * 
	 * @return
	 */
	public Map<String, Object> earlyRepay(String projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date startDateTime = new Date();
		// 获取项目借款人
		ProjectBaseInfo pbi = projectBaseInfoDao.get(projectId);
		String customerId = getCustomerId(pbi);
		logger.info("---------" + DateUtils.formatDateTime(startDateTime)
				+ ":earlyRepay start. projectId:" + projectId
				+ ", borrower's accountId:" + customerId + ".");
		String platformUserNo = customerAccountDao.get(customerId)
				.getPlatformUserNo();
		// 获取项目状态
		String status = pbi.getProjectStatus();
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":project status :" + status + ".");
		// 检验项目状态是否为还款中
		if (status.equals(ProjectConstant.PROJECT_STATUS_REPAYMENTING)) {
			// 获得下一期的还款记录
			ProjectRepaymentSplitRecord prsrNext = repaymentHandler
					.getNextRepayRecord(projectId, new Date());
			// 获取最近一期将还款的总明细
			Map<String, Object> moneyDetail = earlyRepayEstimate(projectId);
			double sumMoney = (double) moneyDetail.get("sumMoney"); // 总额
			float rate = (float) moneyDetail.get("termInterestRate");// 当期利息比例
			// 获得下一期的还款日期
			Date nextRepayDate = DateUtils.dateFormate(prsrNext
					.getRepaymentDt());
			// 获得所有未偿还的还款记录列表
			List<ProjectRepaymentSplitRecord> willRepayList = repaymentHandler
					.getWillRepayList(projectId, nextRepayDate);
			logger.info("---------willRepayList size is :"
					+ willRepayList.size());
			// 查询余额状态
			AccountInfoResp accountInfoRespObj = queryAccountInfo(platformUserNo);
			if (!accountInfoRespObj.getCode().equals("1")) {
				map.put("code", "err");
				map.put("desc", "自动还款失败,查询账号余额异常");
				logger.info("---------" + DateUtils.formatDateTime(new Date())
						+ ":earlyRepay accountInfoResp exception desc :"
						+ map.get("desc") + ".");
				throw new ServiceException("自动还款失败");
			}
			// 可用余额
			double availableAmount = new Double(
					accountInfoRespObj.getAvailableAmount()).doubleValue();
			logger.info("---------" + DateUtils.formatDateTime(new Date())
					+ ":borrower's availableAmount :" + availableAmount + ".");
			// 判断可用余额是否足够支付费用
			if (availableAmount >= sumMoney) {
				logger.info("---------" + DateUtils.formatDateTime(new Date())
						+ ":freezeReq sumMoney: " + sumMoney + ".");
				// 冻结资金
				String freeRequestNo = YeepayUtils.getSequenceNumber(
						YeepayConstant.PROJECT_INTERFACE_DIRECT_FREEZE_REQ,
						platformUserNo);
				FreezeResp freezeRespObj = freeze(platformUserNo, sumMoney,
						freeRequestNo);
				if (!freezeRespObj.getCode().equals("1")) {
					map.put("code", "err");
					map.put("desc", "自动还款失败,冻结资金" + sumMoney + "元异常");
					logger.info("---------"
							+ DateUtils.formatDateTime(new Date())
							+ ":earlyRepay freezeResp exception desc :"
							+ map.get("desc") + ".");
					throw new ServiceException("自动还款失败");
				}
				// 创建汇总的还款记录
				String sumRepayRecordId = createSumRepayRecord(projectId,
						sumMoney, (double) moneyDetail.get("sumPrincipal"),
						(double) moneyDetail.get("termHasInterest"),
						(double) moneyDetail.get("penaltyMoney"), 0,
						(double) moneyDetail.get("oldMoney"), freeRequestNo,
						"1", "");
				// 获取收款人列表
				List<String> customerList = projectRepaymentSplitRecordDao
						.getRepaymentPayeeUserListByProjectAndDate(projectId,
								DateUtils.formatDate(
										DateUtils.dateFormate(new Date()),
										"yyyy-MM-dd"));
				logger.info("---------" + DateUtils.formatDateTime(new Date())
						+ ":investorList size: " + customerList.size() + ".");
				// 每个人的还款情况
				List<MoneyDetail> moneyList = new ArrayList<MoneyDetail>();
				List<ProjectRepaymentSplitRecord> newRepayList = new ArrayList<ProjectRepaymentSplitRecord>();
				// 查询每个收款人的未还款记录状态修改为已还款
				logger.info("---------"
						+ DateUtils.formatDateTime(new Date())
						+ ":change every investor's repayment record to repaied start.");
				for (String item : customerList) {
					String userId = item;
					List<ProjectRepaymentSplitRecord> splitList = projectRepaymentSplitRecordDao
							.getRepaymentListByProjectAndUser(projectId, userId);
					// 计算本金总额、已发生利息总额、未发生利息总额、罚息总额四个字段
					Map<String, Object> customerSummary = summaryEarlyList(
							splitList, nextRepayDate, rate);

					for (ProjectRepaymentSplitRecord record : splitList) {
						// 批量更新对应投资记录的待收本金利息为0
						String investRecordId = record.getInvestmentRecordId()
								.longValue() + "";
						projectInvestmentRecordDao
								.updateWillReceiveInfoClean(investRecordId);
						projectRepaymentSplitRecordDao
								.updateStatusByInvestmentRecordId(
										record.getId(),
										ProjectConstant.PROJECT_REPAY_STATUS_ALREADY);
					}
					// 创建针对每个人的新的收款记录,计算剩余本金，剩余利息，提现还款违约金等信息,设置状态为冻结中
					ProjectRepaymentSplitRecord temp = splitList.get(0);
					ProjectRepaymentSplitRecord newRecord = createNewRepaySplitRecord(
							sumRepayRecordId, customerSummary, temp);
					newRepayList.add(newRecord);

					// 构造还款请求明细
					Double tempMoney = (Double) customerSummary.get("sumMoney");
					if (tempMoney.doubleValue() > 0) {
						MoneyDetail mDetail = new MoneyDetail();
						mDetail.setAmount(tempMoney.doubleValue() + "");
						mDetail.setBizType("REPAYMENT");
						mDetail.setTargetPlatformUserNo(temp
								.getRepaymentAccount());
						mDetail.setTargetUserType("MEMBER");
						moneyList.add(mDetail);
					}
				}
				logger.info("---------"
						+ DateUtils.formatDateTime(new Date())
						+ ":change every investor's repayment record to repaied end.");
				UnfreezeResp unfreezeRespObj = unfreeze(freeRequestNo);
				if (!unfreezeRespObj.getCode().equals("1")) {
					map.put("code", "err");
					map.put("desc", "自动还款失败,解冻资金" + sumMoney + "元异常");
					logger.info("---------"
							+ DateUtils.formatDateTime(new Date())
							+ ":earlyRepay unfreezeResp exception desc :"
							+ map.get("desc") + ".");
					throw new ServiceException("自动还款失败");
				}
				// 直接发起批量还款接口，将所有新的收款记录的状态修改为冻结，等待notify来处理
				logger.info("---------" + DateUtils.formatDateTime(new Date())
						+ ":autoTranscationReq start.");
				String repayRequestNo = YeepayUtils
						.getSequenceNumber(
								YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_EARLY_REQ,
								platformUserNo);
				AutoTranscationResp autoResp = autoTranscation(
						projectId,
						platformUserNo,
						moneyList,
						repayRequestNo,
						YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_EARLY_REQ);

				ProjectRepaymentRecord sumRecord = projectRepaymentRecordDao
						.get(sumRepayRecordId);
				sumRecord.setThirdPartyOrder(repayRequestNo);
				projectRepaymentRecordDao.update(sumRecord);
				if (!autoResp.getCode().equals("1")) {
					map.put("code", "err");
					map.put("desc", "自动还款失败,发起批量还款异常");
					logger.info("---------"
							+ DateUtils.formatDateTime(new Date())
							+ ":earlyRepay autoTranscationResp exception desc :"
							+ map.get("desc") + ".");
					throw new ServiceException("自动还款失败");

				} else {
					CompleteTranscationResp completeResp = completeTransaction(repayRequestNo);
					if (completeResp.getCode().equals("1")) {
						map.put("code", "OK");
						map.put("desc", "还款金额资金" + sumMoney + "元");
						logger.info("---------"
								+ DateUtils.formatDateTime(new Date())
								+ ":completeTranscationResp ok desc :"
								+ map.get("desc") + ".");
					} else {
						map.put("code", "err");
						map.put("desc", "还款金额资金" + sumMoney + "元,确认失败");
						logger.info("---------"
								+ DateUtils.formatDateTime(new Date())
								+ ":completeTranscationResp err desc :"
								+ map.get("desc") + ".");
					}
					// 设置还款拆分记录为冻结状态
					logger.info("---------"
							+ DateUtils.formatDateTime(new Date())
							+ ":set repaySplitRecord to freeze start.");
					for (ProjectRepaymentSplitRecord record : newRepayList) {
						logger.info("freeze split record="
								+ record.getSplitRecordId() + " record is ="
								+ sumRepayRecordId);
						projectRepaymentSplitRecordDao.updateToFreeze(record
								.getSplitRecordId().longValue() + "");
					}
					logger.info("---------"
							+ DateUtils.formatDateTime(new Date())
							+ ":set repaySplitRecord to freeze end.");
				}
			} else {
				map.put("code", "err");
				map.put("desc", "用户可用余额为：" + availableAmount + "元，不足以支付金额："
						+ sumMoney + "元");
				logger.info("---------" + DateUtils.formatDateTime(new Date())
						+ ":earlyRepay desc: " + map.get("desc") + ".");
			}
		} else {
			map.put("code", "err");
			map.put("desc", "项目状态不是还款中，不能提前还款");
			logger.info("---------" + DateUtils.formatDateTime(new Date())
					+ ":earlyRepay desc: " + map.get("desc") + ".");
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":earlyRepay end. Total cost time : "
				+ (new Date().getTime() - startDateTime.getTime()) / 1000
				+ "s.");
		return map;
	}

	private String getCustomerId(ProjectBaseInfo pbi) {
		if(pbi.getAgentUser() != null && pbi.getAgentUser().longValue() != 0) {
			return pbi.getAgentUser().longValue() + "";
		} else if(pbi.getBorrowersUser() != null) {
			return pbi.getBorrowersUser().longValue() + "";
		}
		return "";
	}

	/**
	 * 冻结资金 接口3.2
	 * 
	 * @param platformUserNo
	 * @param sumMoney
	 * @param freeRequestNo
	 * @return
	 */
	private FreezeResp freeze(String platformUserNo, double sumMoney,
			String freeRequestNo) {
		Date startDate;
		Date endDate;
		FreezeReq fr = new FreezeReq();
		fr.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		fr.setAmount(sumMoney + "");
		fr.setPlatformUserNo(platformUserNo);
		fr.setRequestNo(freeRequestNo);
		fr.setExpired(DateUtils.formatDate(
				DateUtils.addMinutes(new Date(), 30), "yyyy-MM-dd HH:mm:ss"));
		startDate = new Date();
		String freezeResp = directReqUtils.dirReq(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_FREEZE_REQ, fr.toReq(),
				YeepayConstant.PROJECT_INTERFACE_DIRECT_FREEZE_SERVICE);
		endDate = new Date();
		FreezeResp freezeRespObj = JaxbMapper.fromXml(freezeResp,
				FreezeResp.class);
		saveThirdPartLog(freeRequestNo,
				YeepayConstant.PROJECT_INTERFACE_DIRECT_FREEZE_REQ, fr.toReq(),
				startDate, freezeResp, freezeRespObj.getCode(), endDate);
		return freezeRespObj;
	}

	/**
	 * 查询账号情况 接口 3.1
	 * 
	 * @param platformUserNo
	 * @return
	 */
	private AccountInfoResp queryAccountInfo(String platformUserNo) {
		Date startDate;
		Date endDate;
		// 查询用户余额状态
		startDate = new Date();
		AccountInfoReq req = new AccountInfoReq();
		req.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		req.setPlatformUserNo(platformUserNo);
		String accountInfoResp = directReqUtils.dirReq(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_REQ,
				req.toReq(),
				YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_SERVICE);
		endDate = new Date();
		AccountInfoResp accountInfoRespObj = JaxbMapper.fromXml(
				accountInfoResp, AccountInfoResp.class);
		saveThirdPartLog("",
				YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_REQ,
				req.toReq(), startDate, accountInfoResp,
				accountInfoRespObj.getCode(), endDate);
		return accountInfoRespObj;
	}

	/**
	 * @param customerSummary
	 * @param temp
	 * @param newRecord
	 */
	private ProjectRepaymentSplitRecord createNewRepaySplitRecord(
			String sumRepayRecordId, Map<String, Object> customerSummary,
			ProjectRepaymentSplitRecord temp) {
		ProjectRepaymentSplitRecord newRecord = new ProjectRepaymentSplitRecord();
		newRecord.setRecordId(new Long(sumRepayRecordId));
		newRecord.setProjectId(temp.getProjectId());
		newRecord.setInvestmentRecordId(temp.getInvestmentRecordId());
		newRecord.setRepaymentUserId(temp.getRepaymentUserId());
		newRecord.setRepaymentAccount(temp.getRepaymentAccount());
		newRecord.setPayeeUserId(temp.getPayeeUserId());
		newRecord.setPayeeAccount(temp.getPayeeAccount());
		newRecord.setMoney((double) customerSummary.get("sumMoney"));
		newRecord.setRepayType(ProjectConstant.PROJECT_REPAYMENT_TYPE_EARLY);
		newRecord.setSumInterest((Double) customerSummary.get("sumInterest"));
		newRecord.setPrePenaltyMoney((double) customerSummary
				.get("penaltyMoney"));
		newRecord.setLatePenaltyMoney(new Double("0"));
		newRecord.setPrincipal(MapUtil.getDouble(customerSummary,
				"sumPrincipal"));
		newRecord.setInterest(MapUtil.getDouble(customerSummary,
				"sumHasInterest"));
		newRecord.setRemainedPrincipal(0.0);
		newRecord.setRepaymentDt(DateUtils.addDays(new Date(), 1));
		newRecord.setActualRepaymentDt(null);
		newRecord.setThirdPartyOrder("");
		newRecord.setRepayResult("");
		newRecord.setStatus(ProjectConstant.PROJECT_REPAY_STATUS_FREEZE);
		newRecord.setCreateDt(new Date());
		newRecord.setModifyDt(new Date());
		newRecord.setModifyRemark("提前还款创建");
		projectRepaymentSplitRecordDao.insert(newRecord);
		return newRecord;
	}

	/**
	 * 创建汇总的还款记录
	 * 
	 * @param projectId
	 * @param sumMoney
	 * @param principal
	 * @param interest
	 * @param pre_penalty_money
	 * @param late_penalty_money
	 * @param oldMoney
	 * @param freezeResp
	 * @param flag
	 *            1 提前还款 2 正常还款 3 逾期还款
	 * @return
	 */
	@Transactional(readOnly = false)
	private String createSumRepayRecord(String projectId, double sumMoney,
			double principal, double interest, double pre_penalty_money,
			double late_penalty_money, double oldMoney, String freezeReq,
			String flag, String oldday) {
		ProjectRepaymentRecord prr = new ProjectRepaymentRecord();
		prr.setProjectId(new Long(projectId));
		String today = DateUtils.formatDate(new Date(), "yyy-MM-dd");
		prr.setPlanId(new Long("0"));
		if (flag.equals("1")) {// 提前还款
			prr.setRepayType(new Long(
					ProjectConstant.PROJECT_REPAYMENT_TYPE_EARLY));
		} else if (flag.equals("2")) {// 正常还款
			prr.setRepayType(new Long(
					ProjectConstant.PROJECT_REPAYMENT_TYPE_NORMAL));
			ProjectRepaymentPlan plan = projectRepaymentPlanDao
					.getByProjectIdAndDay(projectId, today);
			if (plan == null) {
				logger.info("project id =" + projectId + " today=" + today
						+ " 没有找到对应的还款计划");
				prr.setPlanId(new Long("0"));
			} else {
				prr.setPlanId(plan.getPlanId());
			}
		} else if (flag.equals("3")) {// 逾期还款
			prr.setRepayType(new Long(
					ProjectConstant.PROJECT_REPAYMENT_TYPE_LATE));
			ProjectRepaymentPlan plan = projectRepaymentPlanDao
					.getByProjectIdAndDay(projectId, oldday);
			if (plan == null) {
				logger.info("project id =" + projectId + " today=" + oldday
						+ " 没有找到对应的还款计划");
				prr.setPlanId(new Long("0"));
			} else {
				prr.setPlanId(plan.getPlanId());
			}
			today = oldday;
		}
		prr.setThirdPartyOrder(freezeReq);
		prr.setRepaymentDate(new Date());
		prr.setRepaymentChannelId(new Long(ProjectConstant.OP_TERM_DICT_PC));
		prr.setSumMoney(sumMoney + "");
		// 设置汇总还款记录为冻结状态
		prr.setStatus(new Long(ProjectConstant.PROJECT_INVESTMENT_STATUS_FREEZE));
		prr.setSplitBalance("0");
		prr.setPrincipal(principal);
		prr.setInterest(interest);
		prr.setPrePenaltyMoney(pre_penalty_money);
		prr.setOldMoney(oldMoney);
		prr.setLatePenaltyMoney(late_penalty_money);
		projectRepaymentRecordDao.insert(prr);
		return prr.getRecordId().longValue() + "";
	}

	/**
	 * 保存第三方交互记录
	 * 
	 * @param requestNo
	 * @param service
	 * @param req_content
	 * @param req_dt
	 * @param resp_content
	 * @param resp_code
	 * @param resp_dt
	 */
	@Transactional(readOnly = false)
	public void saveThirdPartLog(String requestNo, String service,
			String reqContent, Date reqDt, String respContent, String respCode,
			Date respDt) {
		LogThirdParty ltp = new LogThirdParty();
		ltp.setRequestNo(requestNo);
		ltp.setService(service);
		ltp.setReqContent(reqContent);
		ltp.setReqDt(reqDt);
		ltp.setRespContent(respContent);
		ltp.setRespCode(respCode);
		ltp.setRespDt(respDt);
		logThirdPartyDao.insert(ltp);
	}

	/**
	 * 更新确认信息
	 * 
	 * @param requestNo
	 * @param service
	 * @param req_content
	 * @param req_dt
	 * @param resp_content
	 * @param resp_code
	 * @param resp_dt
	 */
	@Transactional(readOnly = false)
	public void updateWithConfirm(String requestNo, String confirmReqContent,
			String confirmRespContent, String respCode) {
		Date date = new Date();
		logThirdPartyDao.updateWithConfirm(requestNo, date, confirmReqContent,
				confirmRespContent, respCode);
	}

	/**
	 * 计算汇总提前还款引起的每个会员的应收款项数字
	 * 
	 * @param splitList
	 * @param today
	 * @return
	 */
	public Map<String, Object> summaryEarlyList(
			List<ProjectRepaymentSplitRecord> splitList, Date theday,
			double rate) {
		Map<String, Object> map = new HashMap<String, Object>();
		double sumPrincipal = 0.0;
		double sumHasInterest = 0.0;
		double sumWillInterest = 0.0;
		double penaltyMoney = 0.0;
		for (ProjectRepaymentSplitRecord item : splitList) {
			if (DateUtils.formatDateTime(item.getRepaymentDt()).equals(
					DateUtils.formatDateTime(theday))) {
				sumHasInterest += new Double(item.getInterest()).doubleValue()
						* rate;
				sumWillInterest += new Double(item.getInterest()).doubleValue()
						- sumHasInterest;
			} else {
				sumWillInterest += new Double(item.getInterest()).doubleValue();
			}
			sumPrincipal += new Double(item.getPrincipal()).doubleValue();
		}
		penaltyMoney = sumWillInterest
				* ProjectConstant.PROJECT_EARLY_REPAY_DEFAULT_PAYMENT_RATIO;
		map.put("sumPrincipal", sumPrincipal);
		map.put("sumHasInterest", sumHasInterest);
		map.put("sumInterest", sumHasInterest + sumWillInterest);
		map.put("penaltyMoney", penaltyMoney);
		map.put("sumMoney", sumPrincipal + sumHasInterest + penaltyMoney);
		map.put("oldMoney", sumPrincipal + sumHasInterest + sumWillInterest);
		return map;
	}

	/**
	 * 收到提前还款notify
	 * 
	 * @param thirdPartCode
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> earlyRepayNotify(String notifyStr) {
		AutoTransactionNotify notify = JaxbMapper.fromXml(notifyStr,
				AutoTransactionNotify.class);
		String requestNo = notify.getRequestNo();
		// 根据第三方编号获取交互日志
		LogThirdParty log = logThirdPartyDao.getByRequestNo(requestNo);
		if (log == null) {
			logger.info("notify " + notifyStr + " 没有找到对应的日志记录");
		} else if ((log.getNotifyCode() != null)
				&& (!log.getNotifyCode().equals(""))) {
			logger.info("notify " + notifyStr + " 重复返回，不用处理");
			return null;
		} else {
			String notifyCode = notify.getCode();
			logThirdPartyDao.updateWithNotify(requestNo, notifyStr, notifyCode,
					new Date());
			// 如果交易成功
			if (notifyCode.equals("1")) {
				earlyRepaySuccess(requestNo);
			} else {
				// 失败暂不考虑

			}
		}
		return null;
	}

	/**
	 * 提前还款成功
	 * 
	 * @param requestNo
	 */
	private void earlyRepaySuccess(String requestNo) {
		// 根据第三方编号获得还款记录
		List<ProjectRepaymentRecord> sumRecoreList = projectRepaymentRecordDao
				.getProjectRepaymentRecordByThirdPartyCode(requestNo);

		if ((sumRecoreList == null) || (sumRecoreList.size() == 0)) {
			logger.info("notify " + requestNo + " 没有找到对应的还款记录");
		} else {
			// 更新总记录状态
			ProjectRepaymentRecord projectRepaymentRecord = sumRecoreList
					.get(0);
			projectRepaymentRecord.setStatus(new Long(
					ProjectConstant.PROJECT_REPAY_STATUS_ALREADY));
			projectRepaymentRecordDao.update(projectRepaymentRecord);
			// 获取项目借款人
			ProjectBaseInfo pbi = projectBaseInfoDao.get(projectRepaymentRecord
					.getProjectId().longValue() + "");
			String customerId = getCustomerId(pbi);
			// 根据还款记录获得拆分明细记录列表
			List<ProjectRepaymentSplitRecord> splitList = projectRepaymentSplitRecordDao
					.getRepaymentListBySumRecordId(projectRepaymentRecord
							.getRecordId().longValue() + "",
							ProjectConstant.PROJECT_REPAY_STATUS_FREEZE);
			for (ProjectRepaymentSplitRecord item : splitList) {
				item.setStatus(ProjectConstant.PROJECT_REPAY_STATUS_ALREADY);
				item.setModifyDt(new Date());
				item.setModifyRemark("提前还款");
				item.setActualRepaymentDt(new Date());
				projectRepaymentSplitRecordDao.update(item);
				// 更新对应的投资记录信息
				ProjectInvestmentRecord projectInvestmentRecord = projectInvestmentRecordDao
						.get(item.getInvestmentRecordId().longValue() + "");
				projectInvestmentRecord.setWillReceiveInterest(new Double("0"));
				projectInvestmentRecord
						.setWillReceivePrincipal(new Double("0"));
				projectInvestmentRecord
						.setStatus(ProjectConstant.PROJECT_INVESTMENT_STATUS_END);
				projectInvestmentRecordDao.update(projectInvestmentRecord);
				// 更新相关人员的投资、收益等信息
				// 收款人的信息资料
				reciveMoney(item);
			}
			// customerId作为借款人的还款信息资料
			repayMoney(customerId, projectRepaymentRecord);
			// 更新项目状态为还款结束
			pbi.setProjectStatus(ProjectConstant.PROJECT_STATUS_REPAYMENT_END);
			pbi.setLastRepaymentDate(new Date());
			projectBaseInfoDao.update(pbi);
			// 更新项目快照为提现还款结束
			ProjectExecuteSnapshot pes = projectExecuteSnapshotDao
					.getByProjectId(NumberUtil.toLong(pbi.getProjectId(), 0L),
							0L);
			pes.setEndRepayMoney(pes.getEndRepayMoney()
					+ new Double(projectRepaymentRecord.getSumMoney()));
			pes.setStatus(ProjectConstant.PROJECT_EXECUTE_SNAPSHOT_STATUS_EARLY);
			projectExecuteSnapshotDao.update(pes);
			// 生成还款结束流水节点
			projectDateNodeDao.updateRepayEndTime(projectRepaymentRecord
					.getProjectId().longValue() + "", new Date());
			// 将指定日期之后的还款记录修改为提前还款
			projectRepaymentPlanDao.updateStatusForEarlyRepay(
					projectRepaymentRecord.getProjectId().longValue() + "",
					DateUtils.dateFormate(new Date()),
					ProjectConstant.PROJECT_REPAY_PLAN_EARLY_REPAY);
		}
	}

	/**
	 * 付款处理
	 * 
	 * @param userId
	 * @param payRecord
	 */
	@Transactional(readOnly = false)
	public void repayMoney(String userId, ProjectRepaymentRecord payRecord) {
		Double principal = payRecord.getPrincipal(); // 本金
		Double interest = payRecord.getInterest(); // 利息
		Double prePenaltyMoney = payRecord.getPrePenaltyMoney(); // 提前还款罚金
		Double latePenaltyMoney = payRecord.getLatePenaltyMoney(); // 逾期还款罚金
		Double sumMoney = new Double(payRecord.getSumMoney()); // 还款总金额
		Double oldMoney = payRecord.getOldMoney();// 还款前本息总额
		// 更新余额汇总表
		customerBalanceDao.updateRepayMoney(new Long(userId), sumMoney,
				principal, interest, prePenaltyMoney, latePenaltyMoney,
				oldMoney);
		// 获取下一期应还金额
		String projectId = payRecord.getProjectId().longValue() + "";
		// 获取下一还款计划日期
		List<ProjectRepaymentPlan> planList = projectRepaymentPlanDao
				.findNextPlan(projectId, DateUtils.addDays(new Date(), 1));
		if ((planList == null) || (planList.size() == 0)) {

		} else {
			ProjectRepaymentPlan plan = planList.get(0);
			Double planMoney = new Double(plan.getPlanMoney());
			// 更新30天将要归还金额
			customerBalanceDao.update30DayWillRepayMoney(new Long(userId),
					planMoney);
		}
		// 添加余额变更流水
		ProjectBaseInfo pbi = projectBaseInfoDao.get(payRecord.getProjectId()
				.longValue() + "");
		CustomerBalance customerBalance = customerBalanceDao.get(userId);
		CustomerBalanceHis cbhis = new CustomerBalanceHis();
		cbhis.setAccountId(new Long(userId));
		cbhis.setChangeVal(-sumMoney);
		cbhis.setChangeReason("项目:" + pbi.getProjectName() + "还款");
		cbhis.setChangeType(ProjectConstant.CHANGE_TYPE_BALANCE_REPAYMENT);
		cbhis.setBalance(customerBalance.getGoldBalance());
		cbhis.setRelProject(payRecord.getProjectId().longValue() + "");
		cbhis.setOpDt(new Date());
		cbhis.setOpTermType(ProjectConstant.OP_TERM_DICT_PC);
		customerBalanceHisDao.insert(cbhis);
	}

	/**
	 * 收款处理
	 * 
	 * @param record
	 */
	@Transactional(readOnly = false)
	public void reciveMoney(ProjectRepaymentSplitRecord record) {
		Double money = record.getMoney(); // 总金额
		Double prePenaltyMoney = record.getPrePenaltyMoney()==null ? 0 : record.getPrePenaltyMoney(); // 提前还款违约金额
		Double latePenaltyMoney = record.getLatePenaltyMoney()==null ? 0 : record.getLatePenaltyMoney(); // 逾期还款违约金额
		Double principal = new Double(record.getPrincipal()); // 本金
		Double interest = new Double(record.getInterest()); // 利息
		// 更新余额汇总表
		customerBalanceDao.updateReciveMoney(record.getPayeeUserId(), money,
				prePenaltyMoney, latePenaltyMoney, principal, interest);

		ProjectBaseInfo pbi = projectBaseInfoDao.get(record.getProjectId()
				.longValue() + "");

		// 添加余额变更流水
		CustomerBalance customerBalance = customerBalanceDao.get(record
				.getPayeeUserId().longValue() + "");
		CustomerBalanceHis cbhis = new CustomerBalanceHis();
		cbhis.setAccountId(record.getPayeeUserId());
		cbhis.setChangeVal(money);
		cbhis.setChangeReason("项目:" + pbi.getProjectName() + "还款");
		cbhis.setChangeType(ProjectConstant.CHANGE_TYPE_BALANCE_REPAYMENT);
		cbhis.setBalance(customerBalance.getGoldBalance());
		cbhis.setRelProject(record.getProjectId().longValue() + "");
		cbhis.setOpDt(new Date());
		cbhis.setOpTermType(ProjectConstant.OP_TERM_DICT_PC);
		customerBalanceHisDao.insert(cbhis);
	}

	/**
	 * 计算提前还款要还款额度 <1>.计算当期到下一日期的利息分隔比例 <2>.获取剩余本金及利息 <3>.根据剩余利息计算应该还款利息
	 * <4>.计算额度
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> earlyRepayEstimate(String projectId) {
		Date today = new Date();
		Date tomorrow = DateUtils.dateFormate(DateUtils.addDays(today, 1));

		// 获取下一次还款记录
		ProjectRepaymentSplitRecord prsrNext = repaymentHandler
				.getNextRepayRecord(projectId, DateUtils.dateFormate(today));
		// 获取上一次还款记录
		ProjectRepaymentSplitRecord prsrOld = repaymentHandler
				.getOldRepayRecord(projectId, DateUtils.dateFormate(today));

		// 上一个还款日期
		Date upRepayDay = null;
		// 下一还款日期
		Date nextRepayDay = DateUtils.dateFormate(prsrNext.getRepaymentDt());
		if (prsrOld != null) {
			// 如果有上一笔还款记录，则从上一笔还款记录日期开始本周期计息
			upRepayDay = prsrOld.getRepaymentDt();
		} else {
			// 如果没有上一笔还款记录，则从项目募集结束时间点开始本周期计息
			upRepayDay = DateUtils.dateFormate(projectBaseInfoDao
					.get(projectId).getBiddingDeadline());
		}
		// 本周期计息总天数
		int termDays = (int) DateUtils.getDistanceOfTwoDate(upRepayDay,
				nextRepayDay);
		// 已发生利息天数
		int hasDays = (int) DateUtils
				.getDistanceOfTwoDate(upRepayDay, tomorrow);
		// 计息比例
		float rate = 0;
		if (termDays > 0) {
			rate = hasDays / termDays;
		}
		// 本期已发生利息
		double termHasInterest = new Double(prsrNext.getInterest())
				.doubleValue() * rate;
		// 本期未发生利息
		double termWillInterest = new Double(prsrNext.getInterest())
				.doubleValue() - termHasInterest;
		// 获取尚未归还的本金总额
		double sumPrincipal = repaymentHandler.getWillRepayPrincipal(projectId,
				today);
		// 获取不包含下一期的尚未归还的利息总额
		double sumInterest = repaymentHandler.getWillRepayInterest(projectId,
				DateUtils.addDays(nextRepayDay, 1));

		double sumWillInterest = sumInterest + termWillInterest;

		double penaltyMoney = sumWillInterest
				* ProjectConstant.PROJECT_EARLY_REPAY_DEFAULT_PAYMENT_RATIO;
		// 返回本金总额+已发生利息+未发生利息*比例的数字
		double sumMoney = sumPrincipal + termHasInterest + penaltyMoney;
		double oldMoney = sumPrincipal + prsrNext.getInterest() + sumInterest;
		// 返回数据结构
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sumMoney", sumMoney); // 总额
		map.put("termHasInterest", termHasInterest); // 本期已发生利息
		map.put("sumWillInterest", sumWillInterest); // 总计未发生利息
		map.put("sumPrincipal", sumPrincipal); // 尚未归还本金
		map.put("termInterestRate", rate); // 当期利息比重
		map.put("penaltyMoney", penaltyMoney); // 当期利息比重
		map.put("oldMoney", oldMoney);// 原来本金利息总额度
		return map;
	}

	/**
	 * 正常还款处理器 <1>.根据项目编号，获得当日还款列表清单 <2>.校验还款账号余额 <3>.直接拼凑还款直接接口
	 * <4>.设置当期还款列表清单为冻结
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> repay(String projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date today = new Date();
		String theDayStr = DateUtils.formatDate(today, "yyyy-MM-dd");
		ProjectBaseInfo pbi = projectBaseInfoDao.get(projectId);
		logger.debug("正常还款 获取 项目编号" + projectId + "结束");
		String customerId = getCustomerId(pbi);
		String platformUserNo = customerAccountDao.get(customerId)
				.getPlatformUserNo();
		logger.debug("正常还款 获取 借款人编号" + platformUserNo + "结束");
		String status = pbi.getProjectStatus();
		// 检验项目状态是否为还款中
		if (status.equals(ProjectConstant.PROJECT_STATUS_REPAYMENTING)) {
			logger.debug("正常还款 校验项目状态通过");
			// 获取当期将要还款的信息,总金额，总本金，总利息
			ProjectRepaymentSplitRecord sumInfo = projectRepaymentSplitRecordDao
					.getWillReapyInfoOfTheTerm(projectId, theDayStr);
			if (sumInfo == null) {
				map.put("code", "err001");
				map.put("desc", "自动还款失败,查询账号余额异常");
				logger.error("正常还款当期将要还款的信息,总金额，总本金，总利息为空，异常");
				return map;
			}
			Double sumMoney = sumInfo.getSumMoneyShow();
			Double sumPrincipal = sumInfo.getSumPrincipalShow();
			Double sumInsterest = sumInfo.getSumInterestShow();
			logger.debug("正常还款当期将要还款的信息,总金额，总本金，总利息通过");
			// 查询余额状态
			AccountInfoResp accountInfoRespObj = queryAccountInfo(platformUserNo);
			if (!accountInfoRespObj.getCode().equals("1")) {
				map.put("code", "err001");
				map.put("desc", "自动还款失败,查询账号余额异常");
				logger.debug("正常还款 自动还款失败,查询账号余额异常");
				throw new ServiceException("自动还款失败");
			}
			// 可用余额
			double availableAmount = new Double(
					accountInfoRespObj.getAvailableAmount()).doubleValue();
			// 判断可用余额是否足够支付费用
			if (availableAmount >= sumMoney) {
				logger.debug("正常还款 余额足够支付费用");
				// 冻结资金
				String freeRequestNo = YeepayUtils.getSequenceNumber(
						YeepayConstant.PROJECT_INTERFACE_DIRECT_FREEZE_REQ,
						platformUserNo);
				FreezeResp freezeRespObj = freeze(platformUserNo, sumMoney,
						freeRequestNo);
				if (!freezeRespObj.getCode().equals("1")) {
					map.put("code", "err002");
					map.put("desc", "自动还款失败,冻结资金" + sumMoney + "元异常");
					logger.debug("正常还款 冻结资金异常");
					throw new ServiceException("自动还款失败");
				}
				logger.debug("正常还款 冻结资金通过 请求号：" + freeRequestNo);
				// 创建汇总的还款记录
				String sumRepayRecordId = createSumRepayRecord(projectId,
						sumMoney, sumPrincipal, sumInsterest, 0, 0,
						(double) sumMoney, freeRequestNo, "2", "");
				logger.debug("正常还款 创建汇总的还款记录通过 id=" + sumRepayRecordId);
				// 获取付款列表
				List<ProjectRepaymentSplitRecord> splitList = projectRepaymentSplitRecordDao
						.getRepaymentListByProjectAndDate(projectId, theDayStr);
				// 每笔记录对应的还款情况
				List<MoneyDetail> moneyList = new ArrayList<MoneyDetail>();
				logger.debug("正常还款  moneyList=" + moneyList.size());
				// 查询每个收款人的未还款记录状态修改为已还款

				for (ProjectRepaymentSplitRecord record : splitList) {
					record.setRecordId(new Long(sumRepayRecordId));
					record.setRepayType(ProjectConstant.PROJECT_REPAYMENT_TYPE_NORMAL);
					logger.info("正常还款 更新 split record="
							+ record.getSplitRecordId() + " sum record is ="
							+ sumRepayRecordId);
					projectRepaymentSplitRecordDao.update(record);
					String investRecordId = record.getInvestmentRecordId()
							.longValue() + "";
					String willPrincipal = record.getPrincipal().doubleValue()
							+ "";
					String willInterest = record.getInterest().doubleValue()
							+ "";
					projectInvestmentRecordDao.updateWillReceiveInfo(
							investRecordId, willPrincipal, willInterest);
					logger.info("正常还款 更新投资记录的待还本金利息  investRecordId="
							+ investRecordId + " 本金 =" + willPrincipal + "利息="
							+ willInterest);

					// 构造还款请求明细
					if (record.getMoney().doubleValue() > 0) {
						MoneyDetail mDetail = new MoneyDetail();
						mDetail.setAmount(record.getMoney().doubleValue() + "");
						mDetail.setBizType("REPAYMENT");
						mDetail.setTargetPlatformUserNo(record
								.getPayeeAccount());
						mDetail.setTargetUserType("MEMBER");
						moneyList.add(mDetail);
					}
					logger.info("正常还款  构造还款请求明细清单 size is " + moneyList.size());
					
					//调用还款动作消息处理
					invokeRepayMessageHandler(record.getPayeeUserId(), record.getPrincipal(), record.getInterest());
				}

				// 发起解冻
				UnfreezeResp unfreezeRespObj = unfreeze(freeRequestNo);
				if (!unfreezeRespObj.getCode().equals("1")) {
					map.put("code", "err004");
					map.put("desc", "自动还款失败,解冻资金" + sumMoney + "元异常");
					logger.debug("正常还款 解冻资金异常 id=" + freeRequestNo);
					throw new ServiceException("自动还款失败");
				}
				logger.debug("正常还款 解冻资金通过");
				// 直接发起批量还款接口，将所有收款记录的状态修改为冻结，等待notify来处理
				String repayRequestNo = YeepayUtils
						.getSequenceNumber(
								YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_NORMAL_REQ,
								platformUserNo);
				AutoTranscationResp autoRespObj = autoTranscation(
						projectId,
						platformUserNo,
						moneyList,
						repayRequestNo,
						YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_NORMAL_REQ);
				ProjectRepaymentRecord sumRecord = projectRepaymentRecordDao
						.get(sumRepayRecordId);
				sumRecord.setThirdPartyOrder(repayRequestNo);
				projectRepaymentRecordDao.update(sumRecord);
				if (!autoRespObj.getCode().equals("1")) {
					map.put("code", "err005");
					map.put("desc", "自动还款失败,发起批量还款异常");
					logger.debug("正常还款 发起批量还款异常 id=" + repayRequestNo);
					throw new ServiceException("自动还款失败");

				} else {
					logger.debug("正常还款 发起批量还款正常 id=" + repayRequestNo);
					// 付款确认
					CompleteTranscationResp completeResp = completeTransaction(repayRequestNo);
					if (completeResp.getCode().equals("1")) {
						map.put("code", "OK");
						map.put("desc", "冻结资金" + sumMoney + "元");
						logger.debug("正常还款 付款确认正常，冻结资金" + sumMoney + "元");
					} else {
						map.put("code", "err");
						map.put("desc", "冻结资金" + sumMoney + "元,确认失败");
						logger.debug("正常还款 付款确认正常，冻结资金" + sumMoney + "元,确认失败");
					}
					// 设置还款拆分记录为冻结状态
					for (ProjectRepaymentSplitRecord record : splitList) {
						logger.info("正常还款  freeze split record="
								+ record.getSplitRecordId() + " record is ="
								+ sumRepayRecordId + " to 冻结");
						projectRepaymentSplitRecordDao.updateToFreeze(record
								.getSplitRecordId().longValue() + "");
					}
					logger.debug("正常还款 发起批量还款通过 id=" + repayRequestNo);
				}
			} else {

				map.put("code", "err006");
				map.put("desc", "用户可用余额为：" + availableAmount + "元，不足以支付金额："
						+ sumMoney + "元");
				logger.debug("正常还款 用户可用余额为：" + availableAmount + "元，不足以支付金额："
						+ sumMoney + "元");
			}
		} else {
			map.put("code", "err007");
			map.put("desc", "项目状态不是还款中，不能正常还款");
			logger.debug("正常还款 项目状态不是还款中，不能正常还款id=" + projectId);
		}
		return map;
	}

	private void invokeRepayMessageHandler(Long accountId, double principal, double interest) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put(MessageConstant.PARA_ACCOUNT_ID, accountId);
		para.put(MessageConstant.PARA_AMOUNT, NumberUtils.add(principal, interest));
		para.put(MessageConstant.PARA_BEHAVIOR_CODE, MessageConstant.BEHAVIOR_TENDER_REPAY);
		para.put(MessageConstant.PARA_OP_TERM, ProjectConstant.OP_TERM_DICT_PC);
		para.put(MessageConstant.PARA_DATE, new Date());
		messageFacade.repay(para);
	}

	/**
	 * 解冻资金处理 接口3.3
	 * 
	 * @param freeRequestNo
	 * @return
	 */
	private UnfreezeResp unfreeze(String freeRequestNo) {
		Date startDate;
		Date endDate;
		UnfreezeReq uReq = new UnfreezeReq();
		uReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		uReq.setFreezeRequestNo(freeRequestNo);
		startDate = new Date();
		String unfreezeResp = directReqUtils.dirReq(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_UNFREEZE_REQ,
				uReq.toReq(),
				YeepayConstant.PROJECT_INTERFACE_DIRECT_UNFREEZE_SERVICE);
		endDate = new Date();
		UnfreezeResp unfreezeRespObj = JaxbMapper.fromXml(unfreezeResp,
				UnfreezeResp.class);
		saveThirdPartLog(freeRequestNo,
				YeepayConstant.PROJECT_INTERFACE_DIRECT_UNFREEZE_REQ,
				uReq.toReq(), startDate, unfreezeResp,
				unfreezeRespObj.getCode(), endDate);
		return unfreezeRespObj;
	}

	/**
	 * 收到正常还款notify
	 * 
	 * @param thirdPartCode
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> repayNotify(String notifyStr) {
		logger.info("正常还款 notify  收到 str=" + notifyStr);
		AutoTransactionNotify notify = JaxbMapper.fromXml(notifyStr,
				AutoTransactionNotify.class);
		String requestNo = notify.getRequestNo();
		logger.info("正常还款 notify requestNo=" + requestNo);
		// 根据第三方编号获取交互日志
		LogThirdParty log = logThirdPartyDao.getByRequestNo(requestNo);
		if (log == null) {
			logger.info("notify " + notifyStr + " 没有找到对应的日志记录");
			return null;
		} else if ((log.getNotifyCode() != null)
				&& (!log.getNotifyCode().equals(""))) {
			logger.info("notify " + notifyStr + " 重复返回，不用处理");
			return null;
		} else {
			String notifyCode = notify.getCode();
			logThirdPartyDao.updateWithNotify(requestNo, notifyStr, notifyCode,
					new Date());
			// 如果交易成功
			if (notifyCode.equals("1")) {
				logger.info("正常还款 notify requestNo=" + requestNo + "code is 1");
				normalRepaySuccess(requestNo);
			} else {
				logger.info("正常还款 notify requestNo=" + requestNo + "code is "
						+ notifyCode + " 需人工干预");
				// 失败暂不考虑

			}
		}
		logger.info("正常还款 notify  收到 str=" + notifyStr + " 处理结束");
		return null;
	}

	/**
	 * 正常还款成功
	 * 
	 * @param requestNo
	 */
	private void normalRepaySuccess(String requestNo) {
		logger.info("正常还款 " + requestNo + " 成功开始处理");
		// 根据第三方编号获得还款记录
		List<ProjectRepaymentRecord> sumRecoreList = projectRepaymentRecordDao
				.getProjectRepaymentRecordByThirdPartyCode(requestNo);

		if ((sumRecoreList == null) || (sumRecoreList.size() == 0)) {
			logger.info("正常还款 " + requestNo + " notify " + requestNo
					+ " 没有找到对应的还款记录");
		} else {
			// 更新总记录状态
			ProjectRepaymentRecord projectRepaymentRecord = sumRecoreList
					.get(0);
			projectRepaymentRecord.setStatus(new Long(
					ProjectConstant.PROJECT_REPAY_STATUS_ALREADY));
			projectRepaymentRecordDao.update(projectRepaymentRecord);
			logger.info("正常还款" + requestNo + " 更新总记录"
					+ projectRepaymentRecord.getRecordId() + " 状态 to "
					+ ProjectConstant.PROJECT_REPAY_STATUS_ALREADY);
			// 获取项目借款人
			ProjectBaseInfo pbi = projectBaseInfoDao.get(projectRepaymentRecord
					.getProjectId().longValue() + "");
			String customerId = getCustomerId(pbi);
			// 根据还款记录获得拆分明细记录列表
			List<ProjectRepaymentSplitRecord> splitList = projectRepaymentSplitRecordDao
					.getRepaymentListBySumRecordId(projectRepaymentRecord
							.getRecordId().longValue() + "",
							ProjectConstant.PROJECT_REPAY_STATUS_FREEZE);
			logger.info("正常还款  " + requestNo + " 根据还款记录获得查分明细记录列表 size is "
					+ splitList.size());
			for (ProjectRepaymentSplitRecord item : splitList) {
				logger.info("正常还款  " + requestNo + " item id is "
						+ item.getSplitRecordId());
				item.setStatus(ProjectConstant.PROJECT_REPAY_STATUS_ALREADY);
				item.setModifyDt(new Date());
				item.setActualRepaymentDt(new Date());
				item.setModifyRemark("正常还款");
				projectRepaymentSplitRecordDao.update(item);
				// 更新对应的投资记录信息
				ProjectInvestmentRecord projectInvestmentRecord = projectInvestmentRecordDao
						.get(item.getInvestmentRecordId().longValue() + "");
				projectInvestmentRecord
						.setWillReceiveInterest(projectInvestmentRecord
								.getWillReceiveInterest() - item.getInterest());
				projectInvestmentRecord
						.setWillReceivePrincipal(projectInvestmentRecord
								.getWillReceivePrincipal()
								- item.getPrincipal());
				int count = projectRepaymentSplitRecordDao
						.getNotRapayCountByInvestmentRecordId(item
								.getInvestmentRecordId() + "");
				logger.info("正常还款  " + requestNo + " investment_record_id=="
						+ item.getRecordId().longValue() + "  count = " + count);
				if (count == 0) {
					projectInvestmentRecord
							.setStatus(ProjectConstant.PROJECT_INVESTMENT_STATUS_END);
				}
				projectInvestmentRecordDao.update(projectInvestmentRecord);

				// 更新相关人员的投资、收益等信息
				// 收款人的信息资料
				logger.info("正常还款  " + item.getSplitRecordId()
						+ " reciveMoney start");
				reciveMoney(item);
				logger.info("正常还款  " + item.getSplitRecordId()
						+ " reciveMoney end");
			}
			// customerId作为借款人的还款信息资料
			logger.info("正常还款  " + customerId + " repayMoney start");
			repayMoney(customerId, projectRepaymentRecord);
			logger.info("正常还款  " + customerId + " repayMoney end");
			// 检查是否还有未还款的记录
			Date tomorrow = DateUtils.dateFormate(DateUtils.addDays(new Date(),
					1));
			// 更新项目快照
			ProjectExecuteSnapshot pes = projectExecuteSnapshotDao
					.getByProjectId(NumberUtil.toLong(pbi.getProjectId(), 0L),
							0L);

			logger.info("正常还款  更新项目快照结束");
			pes.setEndRepayMoney(pes.getEndRepayMoney()
					+ new Double(projectRepaymentRecord.getSumMoney()));
			pes.setStatus(ProjectConstant.PROJECT_REPAYMENT_TYPE_NORMAL);
			projectExecuteSnapshotDao.update(pes);
			logger.info("正常还款  更新项目快照结束");
			// 将指定计划的还款计划更新为正常还款
			projectRepaymentPlanDao.updateStatus(projectRepaymentRecord
					.getPlanId().longValue() + "",
					ProjectConstant.PROJECT_REPAY_PLAN_NORMAL_REPAY);
			int count = projectRepaymentSplitRecordDao
					.getAfterTheDayRecordCount(projectRepaymentRecord
							.getProjectId().longValue() + "", tomorrow);
			logger.info("正常还款  " + requestNo + " 剩余未还款项目拆分明细条目数： " + count);
			if (count == 0) {
				// 如果没有，则项目正常结束
				pbi.setLastRepaymentDate(new Date());
				pbi.setProjectStatus(ProjectConstant.PROJECT_STATUS_REPAYMENT_END);
				projectBaseInfoDao.update(pbi);
				logger.info("正常还款  " + requestNo + " 项目还款完成 ");
				// 生成还款结束流水节点
				projectDateNodeDao.updateRepayEndTime(projectRepaymentRecord
						.getProjectId().longValue() + "", new Date());
				logger.info("正常还款  " + requestNo + " 项目还款更新流水节点结束 ");

			} else {
				logger.info("正常还款  " + requestNo + " 还有未还款项目条目明细，不作处理 ");
				// 还有还款计划，则不做任何处理
			}

		}
		logger.info("正常还款 " + requestNo + " 成功处理结束");
	}

	/**
	 * 逾期还款处理器 <1>.计算逾期还款的分期额度 <2>.查询用户资金状态 <3>.将额度满足的记录分期还掉
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> overdueRepay(String projectId) {
		// TODO Auto-generated method stub
		Date today = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		// 计算逾期还款记录的分期额度
		List<ProjectRepaymentSplitRecord> splitTermList = projectRepaymentSplitRecordDao
				.getTremMoneyListOfOverdueProject(projectId, today);
		// 获取项目借款人
		ProjectBaseInfo pbi = projectBaseInfoDao.get(projectId);
		String customerId = getCustomerId(pbi);
		String platformUserNo = customerAccountDao.get(customerId)
				.getPlatformUserNo();
		// 查询用户资金状态
		// 查询余额状态
		AccountInfoResp accountInfoRespObj = queryAccountInfo(platformUserNo);
		if (!accountInfoRespObj.getCode().equals("1")) {
			map.put("code", "err");
			map.put("desc", "逾期项目自动还款失败,查询账号余额异常");
			throw new ServiceException("逾期项目自动还款失败");
		}
		// 可用余额
		double availableAmount = new Double(
				accountInfoRespObj.getAvailableAmount()).doubleValue();
		// 获得额度满足的分期记录
		List<ProjectRepaymentSplitRecord> canRepayTermList = getCanRepaySplitTermList(
				splitTermList, availableAmount);
		if (canRepayTermList.size() > 0) {
			for (ProjectRepaymentSplitRecord item : canRepayTermList) {
				double sumMoney = item.getSumMoneyShow().doubleValue();
				// 冻结资金
				String freeRequestNo = YeepayUtils.getSequenceNumber(
						YeepayConstant.PROJECT_INTERFACE_DIRECT_FREEZE_REQ,
						platformUserNo);
				FreezeResp freezeRespObj = freeze(platformUserNo, sumMoney,
						freeRequestNo);
				if (!freezeRespObj.getCode().equals("1")) {
					map.put("code", "err");
					map.put("desc", "自动还款失败,冻结资金" + sumMoney + "元异常");
					throw new ServiceException("自动还款失败");
				}
				// 创建汇总的还款记录
				String sumRepayRecordId = createSumRepayRecord(projectId,
						sumMoney, item.getSumPrincipalShow(),
						item.getSumInterestShow(), 0,
						item.getSumPenaltyMoneyShow(),
						item.getSumPrincipalShow() + item.getSumInterestShow(),
						freeRequestNo, "3", DateUtils.formatDate(
								item.getRepaymentDt(), "yyy-MM-dd"));
				List<ProjectRepaymentSplitRecord> splitList = projectRepaymentSplitRecordDao
						.getRepaymentListByProjectAndDate(projectId,
								DateUtils.formatDate(item.getRepaymentDt(),
										"yyyy-MM-dd"));
				// 每笔记录对应的还款情况
				List<MoneyDetail> moneyList = new ArrayList<MoneyDetail>();
				// 查询每个收款人的未还款记录状态修改为已还款
				for (ProjectRepaymentSplitRecord record : splitList) {
					record.setRecordId(new Long(sumRepayRecordId));
					record.setRepayType(ProjectConstant.PROJECT_REPAYMENT_TYPE_LATE);
					projectRepaymentSplitRecordDao.update(record);
					String investRecordId = record.getInvestmentRecordId()
							.longValue() + "";
					String willPrincipal = record.getPrincipal().doubleValue()
							+ "";
					String willInterest = record.getInterest().doubleValue()
							+ "";
					projectInvestmentRecordDao.updateWillReceiveInfo(
							investRecordId, willPrincipal, willInterest);
					// 构造还款请求明细
					if (record.getMoney().doubleValue() > 0) {
						MoneyDetail mDetail = new MoneyDetail();
						mDetail.setAmount(record.getMoney().doubleValue() + "");
						mDetail.setBizType("REPAYMENT");
						mDetail.setTargetPlatformUserNo(record
								.getPayeeAccount());
						mDetail.setTargetUserType("MEMBER");
						moneyList.add(mDetail);
					}
				}

				UnfreezeResp unfreezeRespObj = unfreeze(freeRequestNo);
				if (!unfreezeRespObj.getCode().equals("1")) {
					map.put("code", "err004");
					map.put("desc", "逾期项目自动还款失败,解冻资金" + sumMoney + "元异常");
					throw new ServiceException("逾期项目自动还款失败");
				}
				// 直接发起批量还款接口，将所有新的收款记录的状态修改为冻结，等待notify来处理

				String repayRequestNo = YeepayUtils
						.getSequenceNumber(
								YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_OVERDUE_REQ,
								platformUserNo);
				AutoTranscationResp autoRespObj = autoTranscation(
						projectId,
						platformUserNo,
						moneyList,
						repayRequestNo,
						YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_OVERDUE_REQ);
				ProjectRepaymentRecord sumRecord = projectRepaymentRecordDao
						.get(sumRepayRecordId);
				sumRecord.setThirdPartyOrder(repayRequestNo);
				projectRepaymentRecordDao.update(sumRecord);
				if (!autoRespObj.getCode().equals("1")) {
					map.put("code", "err005");
					map.put("desc", "逾期自动还款失败,发起批量还款异常");
					throw new ServiceException("逾期自动还款失败,发起批量还款异常");

				} else {
					// 进行转账确认
					CompleteTranscationResp completeResp = completeTransaction(repayRequestNo);
					if (completeResp.getCode().equals("1")) {
						map.put("code", "OK");
						map.put("desc", "冻结资金" + sumMoney + "元");
					} else {
						map.put("code", "err");
						map.put("desc", "冻结资金" + sumMoney + "元,确认失败");
					}
					// 设置还款拆分记录为冻结状态
					for (ProjectRepaymentSplitRecord record : splitList) {
						logger.info("freeze split record="
								+ record.getSplitRecordId() + " record is ="
								+ sumRepayRecordId);
						projectRepaymentSplitRecordDao.updateToFreeze(record
								.getSplitRecordId().longValue() + "");
					}
				}
			}
		}

		return map;
	}

	/**
	 * 自动还款操作接口 3.5
	 * 
	 * @param projectId
	 * @param platformUserNo
	 * @param freezeResp
	 * @param moneyList
	 * @param repayRequestNo
	 * @return
	 */
	private AutoTranscationResp autoTranscation(String projectId,
			String platformUserNo, List<MoneyDetail> moneyList,
			String repayRequestNo, String busiType) {
		Date startDate;
		Date endDate;
		AutoTranscationReq autoReq = new AutoTranscationReq();
		autoReq.setRequestNo(repayRequestNo);
		autoReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		autoReq.setPlatformUserNo(platformUserNo);
		autoReq.setBizType("REPAYMENT");
		autoReq.setUserType("MEMBER");
		
		List<Property> extend = new ArrayList<Property>();
		Property property = new Property();
		property.setName("tenderOrderNo");
		property.setValue(YeepayConstant.YEEPAY_TENDERORDERNO_PREFIX + projectId);
		extend.add(property);
		autoReq.setExtend(extend);
		
		autoReq.setDetail(moneyList);
		autoReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX
				+ "autoTranscation");
		startDate = new Date();
		String autoResp = directReqUtils
				.dirReq(busiType,
						autoReq.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_SERVICE);
		AutoTranscationResp autoRespObj = JaxbMapper.fromXml(autoResp,
				AutoTranscationResp.class);
		endDate = new Date();
		saveThirdPartLog(repayRequestNo, busiType, autoReq.toReq(), startDate,
				autoResp, autoRespObj.getCode(), endDate);
		return autoRespObj;
	}

	/**
	 * 自动还款确认 3.7
	 * 
	 * @param repayRequestNo
	 * @return
	 */
	private CompleteTranscationResp completeTransaction(String repayRequestNo) {
		Date startDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(startDateTime)
				+ ":CompleteTranscationResp start repayRequestNo : + "
				+ repayRequestNo + ".");
		CompleteTranscationReq completeReq = new CompleteTranscationReq();
		completeReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		completeReq.setRequestNo(repayRequestNo);
		completeReq
				.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX
						+ "completeTranscation");
		completeReq.setMode("CONFIRM");
		String reqStr = completeReq.toReq();
		String completeRespStr = directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_REQ,
						reqStr,
						YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_SERVICE);
		CompleteTranscationResp completeResp = JaxbMapper.fromXml(
				completeRespStr, CompleteTranscationResp.class);
		updateWithConfirm(repayRequestNo, reqStr, completeRespStr,
				completeResp.getCode());
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":CompleteTranscationResp start end. Total cost time : "
				+ (new Date().getTime() - startDateTime.getTime()) / 1000
				+ "s.");
		return completeResp;
	}

	/**
	 * 根据用户余额值过滤可以进行偿还的期数列表
	 * 
	 * @param canRepayTermList
	 * @param money
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getCanRepaySplitTermList(
			List<ProjectRepaymentSplitRecord> splitTermList, double money) {
		List<ProjectRepaymentSplitRecord> ret = new ArrayList<ProjectRepaymentSplitRecord>();
		for (ProjectRepaymentSplitRecord item : splitTermList) {
			if (item.getSumMoneyShow().doubleValue() <= money) {
				ret.add(item);
				money = money - item.getSumMoneyShow().doubleValue();
			}
		}
		return ret;
	}

	/**
	 * 逾期还款处理器通知
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> overdueRepayNotify(String notifyStr) {
		AutoTransactionNotify notify = JaxbMapper.fromXml(notifyStr,
				AutoTransactionNotify.class);
		String requestNo = notify.getRequestNo();
		// 根据第三方编号获取交互日志
		LogThirdParty log = logThirdPartyDao.getByRequestNo(requestNo);
		if (log == null) {
			logger.info("notify " + notifyStr + " 没有找到对应的日志记录");
			return null;
		} else if ((log.getNotifyCode() != null)
				&& (!log.getNotifyCode().equals(""))) {
			logger.info("notify " + notifyStr + " 重复返回，不用处理");
			return null;
		} else {
			String notifyCode = notify.getCode();
			logThirdPartyDao.updateWithNotify(requestNo, notifyStr, notifyCode,
					new Date());
			// 如果交易成功
			if (notifyCode.equals("1")) {
				overdueRepaySuccess(requestNo);
			} else {
				// 失败暂不考虑
			}
		}
		return null;
	}

	/**
	 * 逾期还款处理成功
	 * 
	 * @param requestNo
	 */
	private void overdueRepaySuccess(String requestNo) {
		// 根据第三方编号获得还款记录
		List<ProjectRepaymentRecord> sumRecoreList = projectRepaymentRecordDao
				.getProjectRepaymentRecordByThirdPartyCode(requestNo);

		if ((sumRecoreList == null) || (sumRecoreList.size() == 0)) {
			logger.info("notify " + requestNo + " 没有找到对应的还款记录");
		} else {
			// 更新总记录状态
			ProjectRepaymentRecord projectRepaymentRecord = sumRecoreList
					.get(0);
			projectRepaymentRecord.setStatus(new Long(
					ProjectConstant.PROJECT_REPAY_STATUS_ALREADY));

			projectRepaymentRecordDao.update(projectRepaymentRecord);
			// 获取项目借款人
			ProjectBaseInfo pbi = projectBaseInfoDao.get(projectRepaymentRecord
					.getProjectId().longValue() + "");
			String customerId = getCustomerId(pbi);
			// 根据还款记录获得拆分明细记录列表
			List<ProjectRepaymentSplitRecord> splitList = projectRepaymentSplitRecordDao
					.getRepaymentListBySumRecordId(projectRepaymentRecord
							.getRecordId().longValue() + "",
							ProjectConstant.PROJECT_REPAY_STATUS_FREEZE);
			for (ProjectRepaymentSplitRecord item : splitList) {
				item.setStatus(ProjectConstant.PROJECT_REPAY_STATUS_ALREADY);
				item.setModifyDt(new Date());
				item.setModifyRemark("逾期还款");
				projectRepaymentSplitRecordDao.update(item);
				// 更新对应的投资记录信息
				ProjectInvestmentRecord projectInvestmentRecord = projectInvestmentRecordDao
						.get(item.getInvestmentRecordId().longValue() + "");
				projectInvestmentRecord
						.setWillReceiveInterest(projectInvestmentRecord
								.getWillReceiveInterest() - item.getInterest());
				projectInvestmentRecord
						.setWillReceivePrincipal(projectInvestmentRecord
								.getWillReceivePrincipal()
								- item.getPrincipal());
				int count = projectRepaymentSplitRecordDao
						.getNotRapayCountByInvestmentRecordId(item
								.getInvestmentRecordId() + "");
				if (count == 0) {
					projectInvestmentRecord
							.setStatus(ProjectConstant.PROJECT_INVESTMENT_STATUS_END);
				}
				projectInvestmentRecordDao.update(projectInvestmentRecord);
				// 更新相关人员的投资、收益等信息
				// 收款人的信息资料
				reciveMoney(item);
			}
			// customerId作为借款人的还款信息资料
			repayMoney(customerId, projectRepaymentRecord);
			// 检查是否还有未还款的记录
			Date firstDay = DateUtils.dateFormate(pbi.getCreateDt());
			// 更新项目快照为提现还款结束
			ProjectExecuteSnapshot pes = projectExecuteSnapshotDao
					.getByProjectId(NumberUtil.toLong(pbi.getProjectId(), 0L),
							0L);
			pes.setEndRepayMoney(pes.getEndRepayMoney()
					+ new Double(projectRepaymentRecord.getSumMoney()));
			pes.setStatus(ProjectConstant.PROJECT_REPAYMENT_TYPE_NORMAL);
			projectExecuteSnapshotDao.update(pes);
			// 将指定计划编号的还款计划更新为逾期还款
			projectRepaymentPlanDao.updateStatus(projectRepaymentRecord
					.getPlanId().longValue() + "",
					ProjectConstant.PROJECT_REPAY_PLAN_OVERDUE_REPAY);
			int count = projectRepaymentSplitRecordDao
					.getAfterTheDayRecordCount(projectRepaymentRecord
							.getProjectId().longValue() + "", firstDay);

			if (count == 0) {
				// 如果没有，则项目正常结束
				pbi.setLastRepaymentDate(new Date());
				pbi.setProjectStatus(ProjectConstant.PROJECT_STATUS_REPAYMENT_END);
				projectBaseInfoDao.update(pbi);
				// 生成还款结束流水节点
				projectDateNodeDao.updateRepayEndTime(projectRepaymentRecord
						.getProjectId().longValue() + "", new Date());

			} else {
				// 还有还款计划，则不做任何处理
			}
		}
	}

	/**
	 * 补充执行流水
	 * 
	 * @param requestNo
	 */
	@Transactional
	public void fixProcess(ProjectRepaymentRecord prr) {
		String requestNo = prr.getThirdPartyOrder();
		// 根据流水号获取对应记录
		LogThirdParty log = logThirdPartyDao.getByRequestNo(requestNo);
		boolean canFix = false;
		// 如果调用ok
		if (log.getRespCode().equals("1")) {
			// 但是confirm不ok
			if ((log.getConfirmCode() == null) || (log.getConfirmCode().equals(""))
					|| (!log.getConfirmCode().equals("1"))||(log.getNotifyCode()==null)||(log.getNotifyCode().equals(""))) {
				// 则发起单笔查询
				QueryReq req = new QueryReq();
				req.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
				req.setRequestNo(requestNo);
				req.setMode("CP_TRANSACTION");
				String reqStr = req.toReq();
				String respStr = directReqUtils.dirReq(
						YeepayConstant.PROJECT_INTERFACE_DIRECT_QUERY_REQ,
						reqStr,
						YeepayConstant.PROJECT_INTERFACE_DIRECT_QUERY_SERVICE);
				QueryCpTranscationResp queryResp =JaxbMapper.fromXml(
						respStr, QueryCpTranscationResp.class);
				logger.info("单笔业务查询 转账记录，流水号为:" + requestNo + ",结果 :"+XStreamHandle.toXml(queryResp));
				if (queryResp.getCode().equals("1")) {
					if(queryResp.getRecords().size()>0){
						String status = queryResp.getRecords().get(0).getStatus();
						String subStatus = queryResp.getRecords().get(0).getSubStatus();
						logThirdPartyDao.updateWithQuery(requestNo, new Date(),
								reqStr, respStr, status + "||" + subStatus);
						if (status.equals("PREAUTH")) {// 已授权
							if (subStatus.equals("SUCCESS")) {
								// 补充confirm操作
								CompleteTranscationResp complete = completeTransaction(requestNo);
								if (complete.getCode().equals("1")) {
									canFix = true;
								}
							} else if (subStatus.equals("PROCESSING")) {
								// 处理中
								logger.info("还款记录执行缓慢，流水号为:" + requestNo + ",正在执行中");
							} else {
								logger.error("还款记录发现异常，流水号为:" + requestNo
										+ " 需要人工查证");
							}
						} else if (status.equals("CONFIRM")) { // 已确认
							if((log.getConfirmCode() == null) || (log.getConfirmCode().equals(""))
									|| (!log.getConfirmCode().equals("1"))){
							logThirdPartyDao.updateWithConfirm(requestNo,
									new Date(), "查询补全", "查询补全", "1");
							}else if((log.getNotifyCode() == null) || (log.getNotifyCode().equals(""))
							|| (!log.getNotifyCode().equals("1"))){
								logThirdPartyDao.updateWithNotify(requestNo,
									 "查询补全", "查询补全", new Date());
							}
							canFix = true;
						} else {
							logger.error("还款记录发现异常，流水号为:" + requestNo + " 需要人工查证");
						}
					}else{
						logger.error("还款记录发现异常，流水号为:" + requestNo + " 查询对应记录条数为0，不能自动补全");
						canFix=false;
					}
					
				}else{
					logger.error("还款记录发现异常，流水号为:" + requestNo + " 查询出错，不能自动补齐");
					canFix=false;
				}
				// 如果可以补全
				if (canFix) {
					// 获取对应的还款记录信息
					String repayType = prr.getRepayType().longValue() + "";
					if (repayType
							.equals(ProjectConstant.PROJECT_REPAYMENT_TYPE_NORMAL)) {
						// 正常还款补齐
						normalRepaySuccess(requestNo);
					} else if (repayType
							.equals(ProjectConstant.PROJECT_REPAYMENT_TYPE_EARLY)) {
						// 提前还款补全
						earlyRepaySuccess(requestNo);
					} else if (repayType
							.equals(ProjectConstant.PROJECT_REPAYMENT_TYPE_LATE)) {
						// 逾期还款补全
						overdueRepaySuccess(requestNo);
					} else {
						logger.error("还款记录还款类型异常，流水号为:" + requestNo + " 还款类型为："
								+ repayType + " 需要人工查证");
					}
				}

			}
		} else {
			// 执行还款时已经触发异常进行了回滚操作
			logger.error("还款记录发现异常，流水号为:" + requestNo + " 需要人工查证");
		}

		// 补充执行success信息
	}

}
