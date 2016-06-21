/**
 * 
 */
package com.thinkgem.jeesite.modules.project.service.task;

import com.thinkgem.jeesite.modules.cms.CmsConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.config.MailConfig;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.modules.entity.SysBizPara;
import com.thinkgem.jeesite.modules.entity.ThirdPartyMailPara;
import com.thinkgem.jeesite.modules.entity.ThirdPartySmsPara;
import com.thinkgem.jeesite.modules.entity.ThirdPartyYeepayPara;
import com.thinkgem.jeesite.modules.front.CmsController;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.service.SysBizParaService;
import com.thinkgem.jeesite.modules.sys.service.ThirdPartyMailParaService;
import com.thinkgem.jeesite.modules.sys.service.ThirdPartySmsParaService;
import com.thinkgem.jeesite.modules.sys.service.ThirdPartyYeepayParaService;

/**
 * @author yangtao
 *
 */
@Component("initSysParaTask")
public class InitSysParaTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ThirdPartyYeepayParaService ThirdPartyYeepayParaService;
	@Autowired
	SysBizParaService sysBizParaService;
	@Autowired
	ThirdPartySmsParaService thirdPartySmsParaService;
	@Autowired
	ThirdPartyMailParaService thirdPartyMailParaService;
	/**
	 * 每5分钟执行一次
	 */
	public void job() {
		logger.info("initSysParaTask start...");
		// 处理易宝参数体系
		ThirdPartyYeepayPara para = ThirdPartyYeepayParaService.get("1");
		if (para != null) {
			YeepayConstant.YEEPAY_PLATFORM_NO = para.getYeepayPlatformNo();
			YeepayConstant.YEEPAY_GATE_URL_PREFIX = para
					.getYeepayGateUrlPrefix();
			YeepayConstant.YEEPAY_DIRECT_URL_PREFIX = para
					.getYeepayDirectUrlPrefix();
			YeepayConstant.YEEPAY_SIGN_URL_PREFIX = para
					.getYeepaySignRulPrefix();
			YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX = para
					.getYeepayCallbackUrlPrefixDemo();
			YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX = para
					.getYeepayNotifyUrlPrefixDemo();
			YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX = para
					.getYeepayCallbackUrlPrefix();
			YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX = para
					.getYeepayNotifyUrlPrefix();
			YeepayConstant.YEEPAY_TENDERORDERNO_PREFIX = para
					.getYeepayTenderordernoPrefix();
			YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX = para
					.getYeepayGateWayCallbackUrlPrefix();
			YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX = para
					.getYeepayGateWayNotifyPrefix();
			YeepayConstant.YEEPAY_DIRECT_NOTIFY_URL_PREFIX = para 
					.getYeepayDirectNotifyUrlPrefix();
			YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX = para 
					.getYeepayGateWayWirelessCallbackUrlPrefix();
		}
		// 处理项目参数设置
		SysBizPara bizPara = sysBizParaService.get("1");
		if (bizPara != null) {
			ProjectConstant.DEFAULT_RISK_INFO_ID = bizPara
					.getProjectDefaultRiskArticleId();
			ProjectConstant.PROJECT_EARLY_REPAY_DEFAULT_PAYMENT_RATIO = bizPara
					.getProjectEarlyRepayRatio();
			ProjectConstant.PROJECT_OVERDUE_REPAY_DEFAULT_PAYMENT_RATIO = bizPara.getProjectOverdueRepayRatio();
			ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE = bizPara.getProjectPersonCreditLoanYearRate();
			ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_SERVICE_RATE =bizPara.getProjectPersonalCreditLoanServiceRate();
			ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_MAX_VALUE = bizPara.getProjectPersonalCreditLoanMaxValue();
			ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_MAX_TERMS = bizPara.getProjectPersonalCreditLoanMaxTerms();
			ProjectConstant.PROJECT_TRANSFER_SERVICE_RATE = bizPara.getProjectTransferServiceRate();
			//前台栏目
			CmsConstant.CATEGORYID_FDXW=bizPara.getCmsZxxwCategoryId(); //富定新闻
			CmsConstant.CATEGORYID_ZXGG=bizPara.getCmsZxggCategoryId(); //最新公告
			CmsConstant.CATEGORYID_ZXHD=bizPara.getCmsZxhdCategoryId(); //最新活动
			ProjectConfig.getInstance().setMaxOverdueDayCount(bizPara.getProjectMaxOverdueDayCount());
			ProjectConfig.getInstance().setDayCountToNextRepay(bizPara.getProjectTransferDayCountToNextRepay());
			ProjectConfig.getInstance().setAssignmentFeeRateUp(bizPara.getProjectTransferServiceUpRate());
			ProjectConfig.getInstance().setAssignmentFeeRateDown(bizPara.getProjectTrnasferServiceDownRate());
			ProjectConfig.getInstance().setPlatformAmountRate(bizPara.getPlatformAmountRate());
			ProjectConfig.getInstance().setServiceChargeRate(bizPara.getProjectServiceChargeRate());
			ProjectConfig.getInstance().setTicketAmountRate(bizPara.getProjectTicketAmountRate());
			ProjectConfig.getInstance().setMaxAssignmentHours(bizPara.getProjectTransferMaxAssignmentHours());
			ProjectConfig.getInstance().setMaxAmountDefault(bizPara.getProjectMaxAmountDefault());
			ProjectConfig.getInstance().setMaxWithdrawCount(bizPara.getOnedayMaxWithdrawCount());
		}
		
		//处理短信通道参数
		ThirdPartySmsPara smsPara=thirdPartySmsParaService.get("1");
		if(smsPara!=null){
			ProjectConfig.getInstance().setSmsApiUrl(smsPara.getSmsApiUrl());
			ProjectConfig.getInstance().setSmsApiKey(smsPara.getSmsApiKey());
			ProjectConfig.getInstance().setSmsSecretKey(smsPara.getSmsSecretKey());
		}
		//处理邮箱通道参数
		ThirdPartyMailPara mailPara=thirdPartyMailParaService.get("1");
		if(mailPara!=null){
			MailConfig.MAIL_HOST=mailPara.getMailServerHost();
			MailConfig.MAIL_USER_NAME=mailPara.getMailUserName();
			MailConfig.MAIL_PASSWORD=mailPara.getMailPassword();
			MailConfig.MAIL_SMTP_TIMEOUT=mailPara.getMailSmtpTimeout();
			MailConfig.MAIL_SMTP_AUTH=mailPara.getMailSmtpAuth();
		}
		logger.info("initSysParaTask end...");
	}
}
