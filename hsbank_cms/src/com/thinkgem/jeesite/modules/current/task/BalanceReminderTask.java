package com.thinkgem.jeesite.modules.current.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceAlertService;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceAlert;
import com.thinkgem.jeesite.modules.sms.utils.SmsUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * hyc
 * 
 * @author
 *
 */
@Component
public class BalanceReminderTask {

	Logger logger = Logger.getLogger(this.getClass());

	
	@Autowired
	private CustomerAccountDao customerAccountDao;

	@Autowired
	private CustomerBalanceAlertService customerBalanceAlertService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;

	/**
	 * 活期账户余额设置警戒提醒，每隔半小时进行账户余额检测， 余额低于设定值时（金额为正数），即时发送短信通知财务（可发送至多个手机号码
	 */
	public void job() {
		logger.info("=====BalanceReminderTask start=====");
		// 查询出账户余额提醒相关的数据
		CustomerBalanceAlert customerBalanceAlert = new CustomerBalanceAlert();
		List<CustomerBalanceAlert> lists = customerBalanceAlertService
				.findList(customerBalanceAlert);
		for (CustomerBalanceAlert cba : lists) {
			// 根据用户的平台编号查询用的登陆名
			String platformUserNo = cba.getPlatformUserNo();
			String[] mobiles = cba.getMobile().split(",");
			String content = cba.getContent();
			double amount = cba.getAmount();
			String customerName = customerAccountDao
					.selectCustomerNameByPlatformUserNo(platformUserNo);
			// 根据用户名查询融资人账户的可用余额
			AccountInfoResp accountInfoResp = yeepayCommonHandler
					.accountInfo(platformUserNo);
			double balance = Double.parseDouble(accountInfoResp.getBalance());
			if (content.contains("#customerName#")
					|| content.contains("#balance#")) {
				content = content.replaceAll("#customerName#", customerName);
				content = content.replaceAll("#balance#", balance + "");
				String contents = content;
				if (balance < amount) {
					List<String> mobileList = new ArrayList<String>();
					for(String mobile :mobiles){
						mobileList.add(mobile);
					}
					if (mobiles != null) {
							SmsUtils.sendSms(mobileList, contents);
					}
				}
			}
		}
		logger.info("=====BalanceReminderTask end=====");
	}

	/*
	 * public static void main(String[] args) throws Exception { double
	 * balance=100; double amount=500; String
	 * content=MobileConstant.smsContent.replace("#amount#", amount+"");
	 * if(balance<amount){ String[] mobiles = MobileConstant.mobile.split(",");
	 * if(mobiles!=null){ for(int i = 0; i < mobiles.length; i++){
	 * SmsUtils.sendSms(mobiles[i],content); } }
	 * 
	 * } }
	 */

}
