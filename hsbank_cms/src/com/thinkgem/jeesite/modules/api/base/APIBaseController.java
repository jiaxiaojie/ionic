package com.thinkgem.jeesite.modules.api.base;


import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TokenUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.Record;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.UnbindRecordResp;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.proxy.APIControllerProxy;
import com.thinkgem.jeesite.modules.api.to.BeforeWithdrawResp;
import com.thinkgem.jeesite.modules.api.to.ProjectBaseInfoResp;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.sys.service.BankInfoService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

public class APIBaseController extends BaseController implements FactoryBean {
	@Autowired
	private CustomerBankCardService customerBankCardService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;


	/**
	 * 后台异常
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler({Exception.class})
    public Map<String,Object> exception(Exception e) {
		Map<String, Object> map = new HashMap<String, Object>();

		if(e instanceof ServiceException){
			map = APIGenerator.createResultAPI(false,e.getMessage());
		}else{
			ApiUtil.sysExceptionRespMap(map);
		}
		e.printStackTrace();

        return map;
    }

	/**
	 * 银行卡信息
	 * @param path
	 * @param request
	 * @param customerBalance
	 * @param card
	 * @return
	 */
	public BeforeWithdrawResp bankCard(AccountInfoResp accountInfoResp, CustomerBalance customerBalance, CustomerBankCard card){
		BeforeWithdrawResp bankCard = new BeforeWithdrawResp();
		String cardNo ="";
		int i = customerBankCardService.hasAppointment(card.getUnbindRequestNo());
		// 绑定的卡号
		cardNo = accountInfoResp.getCardNo();
		//以下为易宝查询到的卡信息
		//卡状态
		String cardStatus = accountInfoResp.getCardStatus();
		//【见银行代码】
		String bank = accountInfoResp.getBank();
		bankCard.setCardNo(StringUtil.dealString(cardNo));
		//1是为绑卡返回，2是正预约绑卡，3是解绑成功
		if(i == 2){
			//解绑状态
			bankCard.setStatus(ApiConstant.UNBINDBANKCARD_STATUS);
			//状态名称
			bankCard.setStatusName(ApiConstant.UNBINDBANKCARD_STATUS_DESP);
			//查询出解绑信息数据（根据解绑请求流水号）
			UnbindRecordResp unbindRecordResp = yeepayCommonHandler.queryUnbindRecord(card.getUnbindRequestNo());
			Record record = (unbindRecordResp != null && unbindRecordResp.getRecords() != null && unbindRecordResp.getRecords().size()==1 ? unbindRecordResp.getRecords().get(0) : null);
			if(record != null){
				bankCard.setAppointmentDate(record.getAppointmentDate());
			}
		}else{
			bankCard.setStatus(StringUtil.dealString(cardStatus));
			bankCard.setStatusName(DictUtils.getDictLabel(cardStatus, "yeepay_bind_bank_card_status_dict", ""));
		}
		//银行卡所属银行
		bankCard.setBankCode(StringUtil.dealString(bank));
		//银行卡所属银行名称
		bankCard.setBankName(DictUtils.getDictLabel(bank, "yeepay_bank_code_dict", ""));
		BankInfo bankInfo = bankInfoService.getBankInfoByBankCode(bank);
		if(bankInfo == null){
			bankInfo = new BankInfo();
		}
		//银行卡Logo的ulr
		bankCard.setBankLogo(ApiUtil.imageUrlConver(bankInfo.getLogo()));
		Double quota = bankInfo!=null && bankInfo.getShortcutIndividual()!=null ? bankInfo.getShortcutIndividual().doubleValue() : 0.0;
		Double dayQuota = bankInfo!=null && bankInfo.getShortcutSingleDay()!=null ? bankInfo.getShortcutSingleDay().doubleValue() : 0.0;
		//每次限额
		bankCard.setQuota(quota);
		//日限额
		bankCard.setDayQuota(dayQuota);
		//可提现金额
		bankCard.setAmount(availableBalance(customerBalance));
		//提现券张数
		bankCard.setTicketCount(customerBalance.getFreeWithdrawCount());
		return bankCard;
	}

	/**
	 * 可用余额
	 * @param customerBalance
	 * @return
	 */
	public static Double availableBalance(CustomerBalance customerBalance){
		Double goldBalance = customerBalance.getGoldBalance() != null ?customerBalance.getGoldBalance() : 0;
		Double congealVal = customerBalance.getCongealVal() != null ? customerBalance.getCongealVal() : 0;
		Double availableBalance = goldBalance - congealVal < 0 ? 0 : goldBalance - congealVal;
		availableBalance = LoanUtil.formatAmount(availableBalance);
		return availableBalance;
	}
	/**
	 *
	 * <p>
	 * Description:通用打印方法<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年5月16日
	 * @param printName
	 * @param object
	 * @param start
	 * @param response
	 * @param map
	 * void
	 */
	public void printLog(String printName, Object object, Date start, HttpServletResponse response, HashMap<String, Object> map){
		logger.info("=== "+printName+",输入参数：{}",object!=null?object.toString():"");
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(start) + "】"+printName+"接口输出参数: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】"+printName+"接口结束...");
		logger.info(printName+"接口花费时间：【" + (endTime.getTime() - start.getTime())  + "ms】");
		String result = super.renderString(response, map);
		logger.info("=== "+printName+"接口列表接口，输出参数：{}", result);
	}

	protected HttpServletRequest currentRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	protected HttpSession currentSession() {
		return currentRequest().getSession();
	}

	protected CustomerClientToken getCurrentToken(){
		return TokenUtils.getCurrentCustomerClientToken();
	}


	/**
	 * 债权项目详情数据
	 * @param pInfo
	 * @param map
	 */
	public void detailCreate(HttpServletRequest request, CustomerBalance customerBalance,ProjectTransferInfo transferInfo, ProjectBaseInfo pInfo, ProjectExecuteSnapshot pSnapshot, HashMap<String, Object> map){
		ProjectBaseInfoResp pResp = new ProjectBaseInfoResp();
		//项目流水号
		pResp.setProjectId(NumberUtils.toLong(pInfo.getProjectId(), 0L));
		//转让编号
		pResp.setTransferProjectId(transferInfo.getTransferProjectId());
		//项目编号
		pResp.setProjectCode(pInfo.getProjectCode());
		//项目类型
		pResp.setProjectType(pInfo.getProjectTypeCode());
		//项目类型名称
		pResp.setProjectTypeName(DictUtils.getDictLabel(String.valueOf(pInfo.getProjectTypeCode()), "project_type_dict", ""));
		//项目名称
		pResp.setProjectName(pInfo.getProjectName());
		//项目提示
		String   projectTips=pInfo.getProjectTips();
		pResp.setProjectTips(projectTips);
		//还款方式
		pResp.setRepaymentMode(NumberUtil.toLong(pInfo.getRepaymentMode(), 0L));
		//还款方式名称
		pResp.setRepaymentModeName(DictUtils.getDictLabel(pInfo.getRepaymentMode(), "project_repayment_mode_dict", ""));
		//债权金额
		pResp.setAssignAmount(transferInfo.getTransferPrice());
		//
		Double financeMoney = transferInfo.getTransferPrice();
		Double endFinanceMoney = pSnapshot.getEndFinanceMoney();
		Double rate = endFinanceMoney / financeMoney * 100;
		//可投金额
		pResp.setAmount(this.voteAmount(financeMoney, endFinanceMoney));
		if(customerBalance !=null){
			Double availableBalances=availableBalance(customerBalance);
			pResp.setAvailableBalance(Double.toString(availableBalances));
		}
		//已投百分比(%)
		pResp.setRate(ApiUtil.formatRate(rate));
		//状态
		pResp.setStatus(NumberUtil.toLong(transferInfo.getStatus(), 0));
		//状态名称
		pResp.setStatusName(DictUtils.getDictLabel(transferInfo.getStatus(), "project_transfer_status_dict", ""));
		//年化利率
		pResp.setAnnualizedRate(pInfo.getAnnualizedRate());
		//借款人
		Long borrowersId = pInfo.getAgentUser();
		if(borrowersId == null || borrowersId == 0) {
			borrowersId = pInfo.getBorrowersUser();
		}
		pResp.setBorrowersUser(StringUtils.vagueName(customerBaseService.getCustomerNameByAccountId(borrowersId)));
		//剩余期限
		pResp.setRemainingTime(pSnapshot.getRemainingTime());
		//项目期限
		pResp.setProjectDuration(pInfo.getProjectDuration());
		//起投金额
		pResp.setStartingAmount(pInfo.getStartingAmount());
		//项目发布日期
		pResp.setPublishDt(DateUtils.FormatDate(pInfo.getPublishDt(),"yyyy-MM-dd HH:mm:ss"));
		//投资截止日期(yyyy-MM-dd HH:mi)
		pResp.setBiddingDeadline(DateUtils.FormatDate(transferInfo.getDiscountDate(),"yyyy-MM-dd HH:mm:ss"));
		//项目简介
		pResp.setProjectIntroduce(pInfo.getProjectIntroduce());
		//用途
		pResp.setUseMethod(pInfo.getUseMethod());
		//转让天数限制
		pResp.setTransferCode(String.valueOf(pInfo.getTransferCode()));
		//转让限制描述
		pResp.setTransferConstraint(DictUtils.getDictLabel(String.valueOf(pInfo.getTransferCode()), "project_transfer_code", ""));
		//
		pResp.setRiskInfo(pInfo.getRiskInfo());
		//
		pResp.setAboutFiles(aboutFiles(pInfo.getAboutFiles()));
		//
		pResp.setArea(pInfo.getArea().getName());
		//投资人数
		pResp.setInvestmentCount(projectInvestmentRecordService.getCountByProjectIdAndTransferProjectId(String.valueOf(transferInfo.getProjectId()), transferInfo.getTransferProjectId()));
		//是否重点推荐（0是，其它不是）
		pResp.setIsRecommend("1".equals(pInfo.getIsRecommend()) ? "0" : "1");
		//是否可用券（0是，其它不是）
		pResp.setIsUseTicket("1".equals(pInfo.getIsNewUser()) ? "0" : "1");
		//是否可转让（0是，其它不是）pInfo.getTransferCode() != -1 ? "0" : "-1"
		pResp.setIsCanAssign(pInfo.getTransferCode()!= -1 ? "0" : "-1");
		ApiUtil.mapRespData(map, pResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
	}

	/**
	 * 文件转化
	 * @param files
	 * @return
	 */
	public static String[] aboutFiles(String files){
		String[] aboutFiles = new String[]{};
		String[] fileStr = files.split("\\|");
		List<String> list = new ArrayList<String>();
		for(int i=1; i< fileStr.length; i++){
			if(StringUtils.isNotBlank(fileStr[i])){
				//String flieStr = fileStr[i].substring(fileStr[i].indexOf("userfiles"), fileStr[i].length());
				list.add(ApiUtil.imageUrlConver(fileStr[i]));
			}
		}
		aboutFiles = (String[]) list.toArray(new String[list.size()]);
		return aboutFiles;
	}


	/**
	 * 项目分页列表数据
	 * @param list
	 * @param
	 */
	public List<ProjectBaseInfoResp> detailCreatePageList(List<ProjectTransferInfo> list){
		List<ProjectBaseInfoResp> pRespList = new ArrayList<ProjectBaseInfoResp>();
		for(ProjectTransferInfo pInfo : list){
			ProjectBaseInfoResp pResp = new ProjectBaseInfoResp();
			pResp.setProjectId(pInfo.getProjectId());
			pResp.setTransferProjectId(pInfo.getTransferProjectId());
			pResp.setProjectCode(pInfo.getProjectBaseInfo().getProjectCode());
			pResp.setProjectType(pInfo.getProjectBaseInfo().getProjectTypeCode());
			pResp.setProjectTypeName(DictUtils.getDictLabel(String.valueOf(pInfo.getProjectBaseInfo().getProjectTypeCode()), "project_type_dict", ""));
			pResp.setProjectName(pInfo.getProjectBaseInfo().getProjectName());
			pResp.setRepaymentMode(NumberUtil.toLong(pInfo.getProjectBaseInfo().getRepaymentMode(), 0L));
			pResp.setRepaymentModeName(DictUtils.getDictLabel(pInfo.getProjectBaseInfo().getRepaymentMode(), "project_repayment_mode_dict", ""));
			pResp.setStartingAmount(pInfo.getProjectBaseInfo().getStartingAmount());
			Double financeMoney = pInfo.getTransferPrice();
			Double endFinanceMoney = pInfo.getProjectExecuteSnapshot()!=null ? pInfo.getProjectExecuteSnapshot().getEndFinanceMoney() : 0.0;
			Double rate = endFinanceMoney / financeMoney * 100;
			pResp.setAmount(this.voteAmount(financeMoney, endFinanceMoney));
			pResp.setRate(ApiUtil.formatRate(rate));
			pResp.setStatus(NumberUtil.toLong(pInfo.getStatus(), 0));
			pResp.setStatusName(DictUtils.getDictLabel(pInfo.getStatus(), "project_transfer_status_dict", ""));
			pResp.setAnnualizedRate(pInfo.getProjectBaseInfo().getAnnualizedRate());
			pResp.setRemainingTime(pInfo.getProjectExecuteSnapshot().getRemainingTime());
			pResp.setProjectDuration(pInfo.getProjectBaseInfo().getProjectDuration());
			pResp.setSafeguardMode(pInfo.getProjectBaseInfo().getSafeguardMode());
			pResp.setSafeguardModeName(DictUtils.getDictLabel(String.valueOf(pInfo.getProjectBaseInfo().getSafeguardMode()), "project_safeguard_mode_dict", ""));
			pResp.setIsRecommend("1".equals(pInfo.getIsRecommend()) ? "0" : "1");
			pResp.setIsUseTicket("1".equals(pInfo.getProjectBaseInfo().getIsNewUser()) ? "0" : "1");
			pRespList.add(pResp);
		}
		return pRespList;
	}


	/**
	 * 可投资金额
	 * @param financeMoney
	 * @param endFinanceMoney
	 * @return
	 */
	public Double voteAmount(Double financeMoney, Double endFinanceMoney){
		Double investmentFinanceMoney = financeMoney - endFinanceMoney < 0 ? 0 : financeMoney - endFinanceMoney;
		investmentFinanceMoney = LoanUtil.formatAmount(investmentFinanceMoney);
		return investmentFinanceMoney;
	}


	/**
	 * 格式化投资进度(保留一位小数)
	 * @param rate
	 * @return
	 */
	public static Double formatRate(Double rate) {
		return new BigDecimal(rate).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	@Override
	public Object getObject() throws Exception {
		return new APIControllerProxy().getProxy(this);
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
