package com.thinkgem.jeesite.modules.front;

import com.hsbank.util.CommonUtil;
import com.hsbank.util.http.HttpRequestUtil;
import com.hsbank.util.tool.FileUtil;
import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.toBindBankCard.ToBindBankCardReq;
import com.thinkgem.jeesite.common.yeepay.toResetMobile.ToResetMobileReq;
import com.thinkgem.jeesite.common.yeepay.toResetPassword.ToResetPasswordReq;
import com.thinkgem.jeesite.common.yeepay.toUnbindBankCard.ToUnbindBankCardReq;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.Record;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.UnbindRecordResp;
import com.thinkgem.jeesite.modules.customer.service.*;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.front.CustomerChangeInfo;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.CutAvatarUtil;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectWillLoanService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * 账号管理
 * @author ydt
 *
 */
@Controller("frontCustomerAccountController")
@RequestMapping(value = "${frontPath}/customer/account")
public class CustomerAccountController extends BaseController {
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private CustomerWorkService customerWorkService;
	@Autowired
	private CustomerCreditAuthService customerCreditAuthService;
	@Autowired
	private CustomerBankCardService customerBankCardService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerHousingService customerHousingService;
	@Autowired
	private CustomerCarService customerCarService;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectWillLoanService projectWillLoanService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	/**
	 * 账号管理-个人基础信息
	 * @param model
	 * @return
	 */
	@RequestMapping("baseInfo")
	public String baseInfo(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		model.addAttribute("customerAccount", customerAccount);
		//基础信息
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		model.addAttribute("customerBase", customerBase);
		//工作信息
		CustomerWork customerWork = customerWorkService.getByCustomerId(customerBase.getCustomerId());
		model.addAttribute("customerWork", customerWork);
		//认证信息
		CustomerCreditAuth customerCreditAuth = customerCreditAuthService.getByAccountId(accountId);
		CustomerChangeInfo customerChangeInfo = new CustomerChangeInfo(customerBase, customerWork, customerCreditAuth);
		model.addAttribute("customerChangeInfo", customerChangeInfo);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "grjcxx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;账号管理&nbsp;&gt;&nbsp;<a href='#'>个人基础信息</a>");
		model.addAttribute("avatar_image", customerAccount.getAvatarImage());
		return "modules/front/wdzh/zhgl_grjcxx";
	}

	/**
	 * 保存修改信息
	 * @param customerChangeInfo
	 * @return
     */
	@RequestMapping("save")
	public String save(CustomerChangeInfo customerChangeInfo) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		customerBaseService.customerChangeHisInfo(customerBase.getCustomerId(), customerChangeInfo);
		return "redirect:"+Global.getFrontPath()+"/customer/account/baseInfo";
	}
	
	/**
	 * 账号管理-更换头像
	 * @param model
	 * @return
	 */
	@RequestMapping("change_avatar")
	public String changeAvatar(HttpServletRequest request, Model model) {
		model.addAttribute("customerAccount", CustomerUtils.get());
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "ghtx");
		CustomerAccount customerAccount = customerAccountService.get(CustomerUtils.getPrincipal().getAccountId());
		model.addAttribute("avatar_image", customerAccount.getAvatarImage());
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;账号管理&nbsp;&gt;&nbsp;<a href='#'>更换头像</a>");
		return "modules/front/wdzh/zhgl_ghtx";
	}
	
	/**
	 * 账号管理-更换头像：保存头像
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("save_avatar")
	@ResponseBody
	public boolean saveAvatar(HttpServletRequest request, Model model) {
		int x = NumberUtil.toInt(HttpRequestUtil.getInstance(request).getParameter("x"), 0);
		int y = NumberUtil.toInt(HttpRequestUtil.getInstance(request).getParameter("y"), 0);
		int w = NumberUtil.toInt(HttpRequestUtil.getInstance(request).getParameter("w"), 0);
		int h = NumberUtil.toInt(HttpRequestUtil.getInstance(request).getParameter("h"), 0);
		String sourcePathName = HttpRequestUtil.getInstance(request).getParameter("source_path_name");
		int sourceWidth = NumberUtil.toInt(HttpRequestUtil.getInstance(request).getParameter("source_width"), 0);
		int sourceHeight = NumberUtil.toInt(HttpRequestUtil.getInstance(request).getParameter("source_height"), 0);
		CustomerAccount customerAccount = CustomerUtils.getCustomerAccount();
		String rootPath = HttpRequestUtil.getInstance(request).getParameter("root_path");
		String avatarPathName = new StringBuffer()
			.append(File.separator)
			.append("upload_files")
			.append(File.separator)
			.append("avatar")
			.append(File.separator)
			.append(FileUtil.getRandomFileNamePrefix())
			.append(".")
			.append(FileUtil.getFileExt(sourcePathName))
			.toString();
		try {
			CutAvatarUtil.cutForAvatar(sourcePathName, sourceWidth, sourceHeight, x, y, w, h, rootPath + avatarPathName);
			customerAccount.setAvatarImage(avatarPathName);
			customerAccountService.updateAvatar(customerAccount);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 安全中心
	 * @param request
	 * @param model
     * @return
     */
	@RequestMapping(value = "safeCenter", method = RequestMethod.GET)
	public String changePassword(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		model.addAttribute("customerBase", customerBase);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "xgmm");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;账号管理&nbsp;&gt;&nbsp;<a href='#'>安全中心</a>");
		return "modules/front/wdzh/account/safeCenter";
	}
	
	/**
	 * 账号管理-认证信息
	 * @param model
	 * @return
	 */
	@RequestMapping("authInfo")
	public String authInfo(HttpServletRequest request, Model model) {
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "rzxx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;账号管理&nbsp;&gt;&nbsp;<a href='#'>认证信息</a>");
		
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		CustomerCreditAuth customerCreditAuth = customerCreditAuthService.getByAccountId(accountId);
		customerCreditAuth.setDefaultValue();
		
		// 获得还可以借贷的额度
		Double canUseLimit = customerCreditAuth.getCreditLimit() - projectWillLoanService.findNotEndApplyMoneyOfCustomer(accountId + "") - projectBaseInfoService.getNotEndProjectMyCreditProject(accountId + "");
		model.addAttribute("customerBase", customerBase);
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		model.addAttribute("canUseLimit", canUseLimit);
		return "modules/front/wdzh/zhgl_rzxx";
	}
	
	/**
	 * 账号管理-银行卡管理
	 * 		1.查询易宝用户银行卡信息
	 * 		2.更新数据库用户银行卡信息
	 * 		3.
	 * 			如果未绑定银行卡，则对数据进行签名，准备跳转到易宝进行绑卡操作
		 * 			1.对数据进行签名
		 * 			2.更新customerBankCard表requestNo
		 * 			3.插入logThirdParty绑卡记录
	 * 			如果已经绑定银行卡，则对数据进行签名，准备跳转到易宝进行取消绑卡操作
		 * 			1.对数据进行签名
		 * 			2.更新customerBank表requestNo
		 * 			3.插入logThirdParty取消绑卡记录
	 * @param model
	 * @return
	 */
	@RequestMapping("bankCard")
	public String bankCard(HttpServletRequest request, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		customerAccount = customerAccountService.get(customerAccount.getAccountId());
		if("1".equals(customerAccount.getHasOpenThirdAccount())) {
			model.addAttribute("hasOpenThirdAccount", true);
		}
		CustomerBankCard customerBankCard = customerBankCardService.getByAccountId(customerAccount.getAccountId());
		AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
		String cardNo = accountInfoResp.getCardNo();
		String cardStatus = accountInfoResp.getCardStatus();
		String bank = accountInfoResp.getBank();
		
		//查看本地绑定信息是否与易宝一致
		boolean needUpdate = needUpdate(customerBankCard, cardNo, cardStatus);
		
		//设置从易宝查询的数据到entity，以便带到前台以及数据同步
		customerBankCard.setCardNo(cardNo);
		customerBankCard.setCardStatusCode(cardStatus);
		customerBankCard.setBankCode(bank);
		customerBankCard.setOpDt(new Date());
		customerBankCard.setLastModifyDt(new Date());
		
		
		//易宝远程验证当前有没有正预约解绑的卡,三种情况，1没查到hasAppointmentYeePayVerify=1, 2有预约解绑hasAppointmentYeePayVerify=2, 3没有预约解绑hasAppointmentYeePayVerify=3
		UnbindRecordResp unbindRecordResp = yeepayCommonHandler.queryUnbindRecord(customerBankCard.getUnbindRequestNo());
		Record record = (unbindRecordResp != null && unbindRecordResp.getRecords() != null && unbindRecordResp.getRecords().size()==1 ? unbindRecordResp.getRecords().get(0) : null);
		int hasAppointmentYeePayVerify = 1;
		if(record != null){
			hasAppointmentYeePayVerify = "INIT".equals(record.getStatus())?2:3;
			model.addAttribute("appointmentDate", DateUtils.parseDate(record.getAppointmentDate())); 
		}
		
		//int i = customerBankCardService.hasAppointmentYeePayVerify(customerBankCard.getUnbindRequestNo());
		
		//是否是快捷支付
		boolean isUpgrade = YeepayConstant.YEEPAY_PAY_SWIFT_UPGRADE.equals(accountInfoResp.getPaySwift()) ;
		
		//如果当前没有正预约解绑的卡
		if(hasAppointmentYeePayVerify != 2 || !isUpgrade){
			
			//若在易宝查询到的银行卡信息与数据库中不一致
			if(needUpdate) {
				//则更新数据库中的信息
				customerBankCardService.update(customerBankCard);
			}
		}
		else if(hasAppointmentYeePayVerify == 2 ){
			model.addAttribute("unbindStatus", "INIT"); 
		}
		


		
		
		
		//银行卡绑定状态："UNBIND"未绑定、"VERIFYING"认证中、"VERIFIED"已认证
		if(StringUtils.isBlank(cardNo)) {
			model.addAttribute("cardStatus", "UNBIND");
			String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_REQ, customerAccount.getPlatformUserNo());
			ToBindBankCardReq toBindBankCardReq = new ToBindBankCardReq();
			toBindBankCardReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
			toBindBankCardReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
			toBindBankCardReq.setRequestNo(requestNo);
			toBindBankCardReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toBindBankCard?requestNo=" + requestNo);
			toBindBankCardReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toBindBankCard");
			
			String req = toBindBankCardReq.toReq();
			String sign = SignUtil.sign(req);
			model.addAttribute("requestNo", requestNo);
			model.addAttribute("req", req);
			model.addAttribute("sign", sign);
			model.addAttribute("bindBankCardUrl", YeepayConstant.YEEPAY_GATE_URL_PREFIX + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_ACTION);
			
			
			//customerBankCard.setRequestNo(requestNo);
			customerBankCardService.updateBankCardRequestNo(customerBankCard.getAccountId(),requestNo);
			
			logThirdPartyService.insertToBindBankCardReq(requestNo, req);
		} else {
			model.addAttribute("cardStatus", cardStatus);
			if("VERIFIED".equals(cardStatus)) {
				String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_REQ, customerAccount.getPlatformUserNo());
				ToUnbindBankCardReq toUnbindBankCardReq = new ToUnbindBankCardReq();
				toUnbindBankCardReq.setRequestNo(requestNo);
				toUnbindBankCardReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
				toUnbindBankCardReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
				toUnbindBankCardReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toUnbindBankCard?requestNo=" + requestNo);
				
				String req = toUnbindBankCardReq.toReq();
				String sign = SignUtil.sign(req);
				model.addAttribute("req", req);
				model.addAttribute("sign", sign);
				model.addAttribute("unbindBankCardUrl", YeepayConstant.YEEPAY_GATE_URL_PREFIX + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOUNBINDBANKCARD_ACTION);
				
				//customerBankCard.setRequestNo(requestNo);
				//customerBankCardService.updateUnbindBankCardRequestNo(customerBankCard);
				customerBankCardService.updateBankCardRequestNo(customerBankCard.getAccountId(),requestNo);
				
				logThirdPartyService.insertToUnbindBankCardReq(requestNo, req);
				
				
				
			}
		}
		
		model.addAttribute("customerBankCard", customerBankCard);
		model.addAttribute("customerAccount", customerAccount);

		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "yhkgl");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;账号管理&nbsp;&gt;&nbsp;<a href='#'>银行卡管理</a>");
		//是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		return "modules/front/wdzh/zhgl_yhkgl";
	}

	private boolean needUpdate(CustomerBankCard customerBankCard, String cardNo, String cardStatus) {
		if((cardNo != null && !cardNo.equals(customerBankCard.getCardNo())) || (cardStatus != null && !cardStatus.equals(customerBankCard.getCardStatusCode()))
				|| (customerBankCard.getCardNo() != null && !customerBankCard.getCardNo().equals(cardNo)) || (customerBankCard.getCardStatusCode() != null && !customerBankCard.getCardStatusCode().equals(cardStatus))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 取消绑卡操作
	 * 
	 * @return
	 */
	@RequestMapping("unbindBankCard")
	public String unbindBankCard(Model model, RedirectAttributes redirectAttributes) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】 unbindBankCard start...");
		CustomerAccount customerAccount = CustomerUtils.get();
		
		AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
		redirectAttributes.addAttribute("paySwift", accountInfoResp.getPaySwift());
		
		
		customerBankCardService.toUnbindBankCard(customerAccount.getPlatformUserNo(),customerAccount.getAccountId());
		
		
		CustomerBankCard customerBankCard = customerBankCardService.getByAccountId(customerAccount.getAccountId());
		redirectAttributes.addAttribute("unbindRequestNo", customerBankCard.getUnbindRequestNo());
		
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】accountId:"+customerAccount.getAccountId()+";unbindBankCard end...");
		logger.info("api toCurrentInvest total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		
		return "redirect:" + frontPath + "/customer/account/unbindBankCardResult";
	}

	/**
	 * 绑卡操作结果页面
	 * @param unbindRequestNo
	 * @param paySwift
	 * @param model
     * @return
     */
	@RequestMapping(value = "unbindBankCardResult")
	public String unbindBankCardResult(String unbindRequestNo,String paySwift,Model model) {
		
		UnbindRecordResp unbindRecordResp = yeepayCommonHandler.queryUnbindRecord(unbindRequestNo);
		Record Record = (unbindRecordResp != null && unbindRecordResp.getRecords() != null && unbindRecordResp.getRecords().size()==1 ? unbindRecordResp.getRecords().get(0) : null);
		
		
		String code = "3";
		if(unbindRecordResp != null){
			if(!"1".equals(unbindRecordResp.getCode())){
				code = "3";
			}
			else{
				if(Record != null && Record.getStatus() != null){
					if("INIT".equals(Record.getStatus())){
						//解绑中
						code = "2";
					}
					else if("SUCCESS".equals(Record.getStatus())){
						//已经解绑
						code = "1";
					}
				}
				else{
					//未查到绑卡信息
					code = "0";
				}
			}
		}
			
			
		
			
		model.addAttribute("code", code);
//		model.addAttribute("status", unbindRecordResp.getStatus());
		model.addAttribute("paySwift", paySwift);
		//model.addAttribute("description", description);
		return "modules/front/wdzh/unbindBankCardResult";
	}
	
	
	
	@RequestMapping(value = "authInfo/baseInfo")
	public String authBaseInfo(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerBase", customerBaseService.getByAccountId(accountId));
		return "modules/front/customer/authInfo/form/baseInfo";
	}
	
	@RequestMapping(value = "authInfo/baseInfoPost", method = RequestMethod.POST)
	public String authBaseInfoPost(CustomerBase customerBase, HttpServletRequest request, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		customerBase.setId(customerAccount.getCustomerBase().getCustomerId() + "");
		customerBase.setAccountId(customerAccount.getAccountId());
		customerBaseService.authBaseInfo(customerBase);
		return "redirect:" + frontPath + "/operateTip/success";
	}
	
	@RequestMapping(value = "authInfo/identityInfo")
	public String authIdentityInfo(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerBase", customerBaseService.getByAccountId(accountId));
		return "modules/front/customer/authInfo/form/identityInfo";
	}
	
	@RequestMapping(value = "authInfo/identityInfoPost", method = RequestMethod.POST)
	public String authIdentityInfoPost(CustomerBase customerBase, HttpServletRequest request, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		customerBase.setId(customerAccount.getCustomerBase().getCustomerId() + "");
		customerBase.setAccountId(customerAccount.getAccountId());
		customerBaseService.authIdentityInfo(customerBase);
		return "redirect:" + frontPath + "/operateTip/success";
	}
	
	@RequestMapping(value = "authInfo/incomeInfo")
	public String authIncomeInfo(HttpServletRequest request, Model model) {
		model.addAttribute("customerWork", customerWorkService.getByCustomerId(CustomerUtils.get().getCustomerBase().getCustomerId()));
		return "modules/front/customer/authInfo/form/incomeInfo";
	}
	
	@RequestMapping(value = "authInfo/incomeInfoPost", method = RequestMethod.POST)
	public String authIncomeInfoPost(CustomerWork customerWork, HttpServletRequest request, Model model) {
		if(customerWork.getCustomerId().longValue() != CustomerUtils.get().getCustomerBase().getCustomerId().longValue()) {
			throw new ServiceException("try change other's data!");
		}
		customerWorkService.authIncomeInfo(customerWork);
		return "redirect:" + frontPath + "/operateTip/success";
	}
	
	@RequestMapping(value = "authInfo/creditCardInfo")
	public String authcreditCardInfo(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerBase", customerBaseService.getByAccountId(accountId));
		return "modules/front/customer/authInfo/form/creditCardInfo";
	}
	
	@RequestMapping(value = "authInfo/creditCardInfoPost", method = RequestMethod.POST)
	public String authcreditCardInfo(CustomerBase customerBase, HttpServletRequest request, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		customerBase.setId(customerAccount.getCustomerBase().getCustomerId() + "");
		customerBase.setAccountId(customerAccount.getAccountId());
		customerBaseService.authcreditCardInfo(customerBase);
		return "redirect:" + frontPath + "/operateTip/success";
	}
	
	@RequestMapping(value = "authInfo/housingInfo")
	public String authHousingInfo(HttpServletRequest request, Model model) {
		model.addAttribute("customerHousing", customerHousingService.getByCustomerId(CustomerUtils.get().getCustomerBase().getCustomerId()));
		return "modules/front/customer/authInfo/form/housingInfo";
	}
	
	@RequestMapping(value = "authInfo/housingInfoPost", method = RequestMethod.POST)
	public String authHousingInfoPost(CustomerHousing customerHousing, HttpServletRequest request, Model model) {
		if(customerHousing.getCustomerId().longValue() != CustomerUtils.get().getCustomerBase().getCustomerId().longValue()) {
			throw new ServiceException("try change other's data!");
		}
		customerHousingService.authHousingInfo(customerHousing);
		return "redirect:" + frontPath + "/operateTip/success";
	}
	
	@RequestMapping(value = "authInfo/carInfo")
	public String authCarInfo(HttpServletRequest request, Model model) {
		model.addAttribute("customerCar", customerCarService.getByCustomerId(CustomerUtils.get().getCustomerBase().getCustomerId()));
		return "modules/front/customer/authInfo/form/carInfo";
	}
	
	@RequestMapping(value = "authInfo/carInfoPost", method = RequestMethod.POST)
	public String authCarInfoPost(CustomerCar customerCar, HttpServletRequest request, Model model) {
		if(customerCar.getCustomerId().longValue() != CustomerUtils.get().getCustomerBase().getCustomerId().longValue()) {
			throw new ServiceException("try change other's data!");
		}
		customerCarService.authCarInfo(customerCar);
		return "redirect:" + frontPath + "/operateTip/success";
	}

	@RequestMapping(value = "authInfo/addressInfo")
	public String authAddressInfo(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerBase", customerBaseService.getByAccountId(accountId));
		return "modules/front/customer/authInfo/form/addressInfo";
	}
	
	@RequestMapping(value = "authInfo/addressInfoPost", method = RequestMethod.POST)
	public String authAddressInfoPost(CustomerBase customerBase, HttpServletRequest request, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		customerBase.setId(customerAccount.getCustomerBase().getCustomerId() + "");
		customerBase.setAccountId(customerAccount.getAccountId());
		customerBaseService.authAddressInfo(customerBase);
		return "redirect:" + frontPath + "/operateTip/success";
	}

	@RequestMapping(value = "authInfo/educationInfo")
	public String authEducationInfo(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerBase", customerBaseService.getByAccountId(accountId));
		return "modules/front/customer/authInfo/form/educationInfo";
	}
	
	@RequestMapping(value = "authInfo/educationInfoPost", method = RequestMethod.POST)
	public String authEducationInfoPost(CustomerBase customerBase, HttpServletRequest request, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		customerBase.setId(customerAccount.getCustomerBase().getCustomerId() + "");
		customerBase.setAccountId(customerAccount.getAccountId());
		customerBaseService.authEducationInfo(customerBase);
		return "redirect:" + frontPath + "/operateTip/success";
	}

	@RequestMapping(value = "authInfo/creditReportInfo")
	public String creditReportInfo(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerBankCard", customerBankCardService.getByAccountId(accountId));
		return "modules/front/customer/authInfo/form/creditReportInfo";
	}
	
	@RequestMapping(value = "authInfo/creditReportInfoPost", method = RequestMethod.POST)
	public String creditReportInfoPost(CustomerBankCard customerBankCard, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerBankCard cbc = customerBankCardService.get(accountId + "");
		cbc.setCreditReportFile(customerBankCard.getCreditReportFile());
		cbc.setLastModifyDt(new Date());
		customerBankCardService.authCreditReportInfo(cbc);
		return "redirect:" + frontPath + "/operateTip/success";
	}
	
	/**
	 * 根据账户id获取账户汇总信息
	 * @param accountId
	 * @return
	 */
	/*@RequestMapping(value = "checkCustomerBalance")
	@ResponseBody
	public CustomerBalance checkCustomerBalance(String accountId){
		CustomerBalance customerBalance=customerBalanceService.get(accountId);
		return customerBalance;
	}*/
	
	/**
	 * 检查昵称是否可用
	 * @param nickname
	 * @return
	 */
	@RequestMapping(value = "checkNicknameCanUse")
	@ResponseBody
	public boolean checkNicknameCanUse(String nickname) {
		return customerAccountService.checkNicknameCanUse(nickname);
	}
	
	/**
	 * 检查邮箱是否可用
	 * @param nickname
	 * @return
	 */
	@RequestMapping(value = "checkEmailCanUse")
	@ResponseBody
	public boolean checkEmailCanUse(long accountId, String email) {
		CustomerBase customerBase = new CustomerBase();
		customerBase.setAccountId(accountId);
		customerBase.setEmail(email);
		return customerBaseService.checkEmailCanUse(customerBase);
	}
	
	/**
	 * 设置昵称
	 * @param accountId
	 * @param nickname
	 * @return
	 */
	@RequestMapping(value = "setNickname")
	@ResponseBody
	public String setNickName(String nickname) {
		CustomerAccount customerAccount = CustomerUtils.get();
		HashMap<String,Object> map = customerAccountService.setNickName(customerAccount.getAccountId(), nickname);
		if((boolean) map.get("success")){
			CustomerUtils.clearCache(customerAccount);
			CustomerUtils.get(customerAccount.getAccountId());
		}
		String json = JsonMapper.toJsonString(map);
		return json;
	}
	
	/**
	 * 修改密码
	 * @param customerAccount
	 * @param accountId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "changePassword")
	@ResponseBody
	public String changePassword(String oldPassword, String newPassword) {
		CustomerAccount customerAccount = CustomerUtils.get();
		HashMap<String,Object> map = customerAccountService.changePassword(customerAccount.getAccountId(), oldPassword, newPassword);
		if((boolean) map.get("success")){
			CustomerUtils.clearCache(customerAccount);
			CustomerUtils.get(customerAccount.getAccountId());
		}
		String json = JsonMapper.toJsonString(map);
		return json;
	}
	
	/**
	 * 修改邮箱
	 * @param customerAccount
	 * @param accountId
	 * @param newEmail
	 * @param emailCode
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "changeEmail")
	@ResponseBody
	public String changeEmail(String newEmail, String emailCode, String password) {
		CustomerAccount customerAccount = CustomerUtils.get();
		Long accountId = customerAccount.getAccountId();
		HashMap<String,Object> map = customerAccountService.changeEmail(accountId, newEmail, emailCode, password);
		if((boolean) map.get("success")){
			CustomerUtils.clearCache(customerAccount);
			CustomerUtils.get(customerAccount.getAccountId());
		}
		String json = JsonMapper.toJsonString(map);
		return json;
	}
	
	/**
	 * 绑定邮箱
	 * @param customerAccount
	 * @param accountId
	 * @param email
	 * @param emailCode
	 * @return
	 */
	@RequestMapping(value = "activateEmail")
	@ResponseBody
	public String activateEmail(String email, String emailCode) {
		CustomerAccount customerAccount = CustomerUtils.get();
		Long accountId = customerAccount.getAccountId();
		HashMap<String,Object> map = customerAccountService.activateEmail(accountId, email, emailCode);
		if((boolean) map.get("success")){
			CustomerUtils.clearCache(customerAccount);
			CustomerUtils.get(customerAccount.getAccountId());
		}
		String json = JsonMapper.toJsonString(map);
		return json;
	}
	
	/**
	 * 对数据进行签名，并返回到【即将跳转到易宝修改手机号提示】页面
	 * 		插入logThirdParty表修改手机号记录
	 * @param customerAccount
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("changeMobile/sign")
	public String changeMobileSign(HttpServletRequest request, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_REQ, customerAccount.getPlatformUserNo());
		ToResetMobileReq toResetMobileReq = new ToResetMobileReq();
		toResetMobileReq.setRequestNo(requestNo);
		toResetMobileReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		toResetMobileReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
		toResetMobileReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toResetMobile?requestNo=" + requestNo);
		toResetMobileReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toResetMobile?requestNo=" + requestNo);
		
		CustomerBase customerBase = customerBaseService.getByAccountId(customerAccount.getAccountId());
		customerBase.setCustomerName(StringUtils.vagueName(customerBase.getCustomerName()));
		model.addAttribute("customerBase", customerBase);
		
		String req = toResetMobileReq.toReq();
		String sign = SignUtil.sign(req);
		model.addAttribute("req", req);
		model.addAttribute("sign", sign);
		model.addAttribute("requestNo", requestNo);
		model.addAttribute("resetMobileUrl", YeepayConstant.YEEPAY_GATE_URL_PREFIX + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_ACTION);
		
		logThirdPartyService.insertToResetMobileReq(requestNo, req);

		model.addAttribute("menu", "wdzh");
		return "modules/front/wdzh/account/changeMobileConfirm";
	}
	
	/**
	 * 对数据进行签名，并返回到【即将跳转到易宝重置交易密码提示】页面
	 * 		插入logThirdParty表重置交易密码记录
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("resetTransPwd/sign")
	public String resetTransPwd(HttpServletRequest request, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_REQ, customerAccount.getPlatformUserNo());
		ToResetPasswordReq toResetPasswordReq = new ToResetPasswordReq();
		toResetPasswordReq.setRequestNo(requestNo);
		toResetPasswordReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		toResetPasswordReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
		toResetPasswordReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toResetTransPwd?requestNo=" + requestNo);
		
		CustomerBase customerBase = customerBaseService.getByAccountId(customerAccount.getAccountId());
		customerBase.setCustomerName(StringUtils.vagueName(customerBase.getCustomerName()));
		model.addAttribute("customerBase", customerBase);
		
		String req = toResetPasswordReq.toReq();
		String sign = SignUtil.sign(req);
		model.addAttribute("req", req);
		model.addAttribute("sign", sign);
		model.addAttribute("requestNo", requestNo);
		model.addAttribute("resetTransPwdUrl", YeepayConstant.YEEPAY_GATE_URL_PREFIX + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_ACTION);
		
		logThirdPartyService.insertToResetTransPwdReq(requestNo, req);

		model.addAttribute("menu", "wdzh");
		return "modules/front/wdzh/account/resetTransPwdConfirm";
	}
}
