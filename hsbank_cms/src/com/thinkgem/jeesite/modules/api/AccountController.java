package com.thinkgem.jeesite.modules.api;

import com.hsbank.api.util.ApiParameterConstant;
import com.hsbank.api.util.ApiStatusConstant;
import com.hsbank.util.collection.MapUtil;
import com.hsbank.util.tool.FileUtil;
import com.hsbank.util.tool.MobileUtil;
import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.ticket.intelligent_ticket.CashTicketFacade;
import com.thinkgem.jeesite.common.ticket.util.bean.CashTicketBean;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.utils.sms_auth.handler.SmsAuthHandler;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.frame.util.APIUtils;
import com.thinkgem.jeesite.modules.api.frame.util.APIUtils.ConvertAction;
import com.thinkgem.jeesite.modules.api.handler.LoginHandler;
import com.thinkgem.jeesite.modules.api.po.ChangePwdReq;
import com.thinkgem.jeesite.modules.api.po.RegisterReq;
import com.thinkgem.jeesite.modules.api.po.ResetPwdReq;
import com.thinkgem.jeesite.modules.api.service.SmsCodeService;
import com.thinkgem.jeesite.modules.api.to.*;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountInterestChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountSummaryService;
import com.thinkgem.jeesite.modules.customer.service.*;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.integral.service.CustomerAddressService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductOrderService;
import com.thinkgem.jeesite.modules.log.service.LogSendValidateCodeService;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAwardRecordService;
import com.thinkgem.jeesite.modules.message.service.MessageInstanceService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentSplitRecordService;
import com.thinkgem.jeesite.modules.sys.service.BankInfoService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * 账户Controller
 *
 * @author lzb
 * @version 2015-10-16
 */
@Controller
@RequestMapping(value="${frontPath}/api/account", method = RequestMethod.POST)
public class AccountController extends APIBaseController {
	@Autowired
	private LoginHandler loginHandler;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerClientTokenService customerClientTokenService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private MarketFacadeService marketFacadeService;
	@Autowired
	private LogSendValidateCodeService logSendValidateCodeService;
	@Autowired
	private CustomerBankCardService customerBankCardService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private CustomerIntegralHisService customerIntegralHisService;
	@Autowired
	private CustomerInvestmentTicketService customerInvestmentTicketService;
	@Autowired
	private ProjectRepaymentSplitRecordService projectRepaymentSplitRecordService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private CustomerBalanceHisService customerBalanceHisService;
	@Autowired
	private ProjectExecuteSnapshotService projectExecuteSnapshotService;
	@Autowired
	private CustomerIntegralSnapshotService customerIntegralSnapshotService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private CustomerAddressService customerAddressService;
	@Autowired
	private IntegralMallProductOrderService integralMallProductOrderService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	@Autowired
	private LogThirdPartyService LogThirdPartyService;
	@Autowired
	private CurrentAccountSummaryService currentAccountSummaryService;
	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	@Autowired
	private CurrentAccountInterestChangeHisService currentAccountInterestChangeHisService;
	@Autowired
	private MessageInstanceService messageInstanceService;
	@Autowired
	private CustomerPushSwitchService customerPushSwitchService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
    @Autowired
    private SmsCodeService smsCodeService;
	/**
	 * 删除(我的)收件人地址
	 * @author wanduanrui
	 * @param response
	 * @param request
	 * @param client
	 * @param token
	 * @param addressId
	 * @return
	 */
	@RequestMapping(value = "customerAddressDelete", method = RequestMethod.POST)
	public String customerAddressDelete(HttpServletResponse response,HttpServletRequest request,
			String client, String token,
			String addressId){
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api customerAddressDelete start...");
		Map<String,Object> map = new HashMap<String,Object>();
		CustomerAccount customerAccount = getCustomerAccountByToken(token,ApiUtil.getOperTerm(client));
		CustomerAddress customerAddress = customerAddressService.get(addressId);
		if(customerAccount != null){
			if(customerAddress!=null){
				if(customerAddress.getAccountId() != null && customerAddress.getAccountId().equals(customerAccount.getAccountId())){
					//修改address状态为删除
					customerAddress.setStatus(ProjectConstant.CUSTOMER_ADDRESS_STATUS_DELETED);
					customerAddressService.update(customerAddress);
					logger.debug("remove addressId:" + addressId);
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				}else{
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "该地址不属于当前用户", false);
				}
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "地址不存在", false);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api customerAddressDelete end...");
		logger.info("api customerAddressDelete total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 编辑(我的)收件人地址
	 * @param response
	 * @param request
	 * @param client
	 * @param token
     * @return
     */
	@RequestMapping(value = "customerAddressEdit", method = RequestMethod.POST)
	public String customerAddressEdit(HttpServletResponse response,HttpServletRequest request, String client, String token){
		//将请求参数转换为实体
		CustomerAddress customerAddress = RequstUtils.toEntity(request, CustomerAddress.class);
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(customerAddress.getId())) {
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "收件人地址的id不能为空", false);
			return  renderString(response, map);
		}
		return customerAddressSave( response, request,client,token,customerAddress);
	}

	/**
	 * 新增(我的)收件人地址
	 * @author wanduanrui
	 * @param response
	 * @param request
	 * @param client
	 * @param token
	 * @param customerAddress
	 * @return
	 */
	@RequestMapping(value = "customerAddressAdd", method = RequestMethod.POST)
	public String customerAddressAdd(HttpServletResponse response,HttpServletRequest request,
			String client, String token,
			CustomerAddress customerAddress){
		customerAddress.setId(null);
		return customerAddressSave( response, request,client,token,customerAddress);
	}


	/**
	 * (我的)收件人地址分页列表
	 * @param response
	 * @param request
	 * @param client
	 * @param token
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @author wanduanrui
	 */
	@RequestMapping(value = "customerAddressPageList")
	public String customerAddressPageList(HttpServletResponse response,HttpServletRequest request,
			String client, String token,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required=false,defaultValue="1")Integer pageNumber) {
		Page<CustomerAddress> addresses = null;
		Map<String,Object> map = new HashMap<String,Object>();
		//查询分页地址数据
		CustomerAccount customerAccount = getCustomerAccountByToken(token,ApiUtil.getOperTerm(client));
		if(customerAccount != null ){
			new Page<CustomerAddress>(pageNumber, pageSize);
			addresses = customerAddressService.findPage(new Page<CustomerAddress>(pageNumber, pageSize,true), new CustomerAddress(customerAccount.getAccountId(),ProjectConstant.CUSTOMER_ADDRESS_STATUS_NORMAL));
			//构造返回的数据结构
			map = APIUtils.toAPIMap(addresses.getList(),new ConvertAction<CustomerAddress>(){

				@Override
				public Map<String, Object> convert(CustomerAddress customerAddress) {
					String statusNameValue = DictUtils.getDictLabel(customerAddress.getStatus(), "customer_address_status_dict", "");
					String isDefault = isDefaultConver(customerAddress.getIsDefault());

					Map<String,Object> dataMap = JsonUtils.beanToMap(customerAddress, "CustomerAddress");
					dataMap.put("status", customerAddress.getStatus());
					dataMap.put("statusName", statusNameValue);
					dataMap.put("isDefault", isDefault);
					dataMap.put("createDt", DateUtils.formatDateTime(customerAddress.getCreateDt()));
					return dataMap;
				}

			});

		}
		else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		return renderString(response, map);
	}

	/**
	 * 提交订单
	 * @param response
	 * @param client
	 * @param token
	 * @param productId
	 * @param productCount
	 * @param addressId
	 * @return
	 */
	@RequestMapping(value = "orderConfirm", method = RequestMethod.POST)
	public String orderConfirm(HttpServletResponse response, String client, String token, String productId,
			String productCount, String addressId) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api confirmOrder start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(productId)){
			String opTerm = ApiUtil.getOperTerm(client);
			CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
			if(clientToken != null ){
				try {
					Map<String, Object> reMap = integralMallProductOrderService.buy(clientToken.getCustomerId(), productId, addressId, NumberUtil.toInt(productCount, 0), opTerm);
					if((boolean) reMap.get("isSuccess")){
						Map<Object,String> dataMap = new HashMap<Object,String>();
						dataMap.put("orderCode", String.valueOf(reMap.get("orderNo")));
						dataMap.put("createDt", String.valueOf(reMap.get("createDt")));
						ApiUtil.mapRespData(map, dataMap, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
					}else{
						ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, String.valueOf(reMap.get("message")), false);
					}
				} catch (Exception e) {
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, e.getMessage(), false);
				}
			}else{
				ApiUtil.tokenInvalidRespMap(map);
			}
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "商品Id不能为空", false);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api confirmOrder end...");
		logger.info("api confirmOrder total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 兑换记录分页列表
	 * @param response
	 * @param client
	 * @param token
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "orderPageList", method = RequestMethod.POST)
	public String orderPageList(HttpServletResponse response, HttpServletRequest request, String client, String token, Integer pageNumber, Integer pageSize) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api orderPageList start...");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ){
			List<IntegralMallProductOrder> list = integralMallProductOrderService.findPageList(clientToken.getCustomerId(), pageNumber, pageSize);
			List<Object> dataList = new ArrayList<Object>();
			for(IntegralMallProductOrder order : list){
				MallOrderResp data = new MallOrderResp();
				data.setOrderCode(order.getOrderNo());
				data.setProductId(String.valueOf(order.getProductId()));
				data.setProductName(order.getProduct().getProductName());
				data.setProductCount(String.valueOf(order.getProductCount()));
				data.setLogoMin(ApiUtil.imageUrlConver(order.getProduct().getProductLogoMin()));
				data.setPrice(order.getPrice());
				data.setCreateDt(DateUtils.formatDateTime(order.getCreateDt()));
				data.setStatus(NumberUtil.toLong(order.getOrderStatus(), 0L));
				data.setStatusName(DictUtils.getDictLabel(order.getOrderStatus(), "integral_mall_order_status_dict", ""));
				dataList.add(data);
			}
			ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api orderPageList end...");
		logger.info("api orderPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 登录
	 * @param response
	 * @param client
	 * @param mobile
	 * @param password
	 * @param smsCode
     * @return
     */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletResponse response, String client, String mobile, String password, String smsCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> loginMap = null;
		password  = StringUtil.dealString(password);
		if ("".equals(password)) {
			//【手机号码 + 短信验证码】登录
			loginMap = loginHandler.loginBySmsCode(client, mobile, smsCode);
		} else {
			//【手机号码 + 密码】登录
			loginMap = loginHandler.loginByAccount(client, mobile, password);
		}
		String statusCode = MapUtil.getString(loginMap, ApiParameterConstant.STATUS_CODE);
		if (ApiStatusConstant.SUCCESS_CODE.equals(statusCode)) {
			//<1>.登录成功
			CustomerAccount customerAccount = (CustomerAccount)loginMap.get("customerAccount");
			String opTerm = MapUtil.getString(loginMap, "opTerm");
			//<2>.得到token
			String token = getToken(customerAccount, opTerm);
			LoginResp data = new LoginResp();
			data.setToken(token);
			resultMap.put(ApiConstant.API_RESP_DATA, data);
			resultMap = ApiUtil.success(resultMap);
			//<3>.执行登录相关活动
			doLoginActivity(customerAccount, opTerm);
		} else {
			//登录失败
			resultMap = ApiUtil.fail(resultMap, MapUtil.getString(loginMap, ApiParameterConstant.STATUS_TEXT));
		}
		String resultValue = renderString(response, resultMap);
		logger.info(resultValue);
		return resultValue;
	}

	/**
	 * 得到token
	 * @param customerAccount
	 * @param opTerm
	 */
	public String getToken(CustomerAccount customerAccount, String opTerm){
		CustomerClientToken clientToken = new CustomerClientToken();
		clientToken.setCustomerId(customerAccount.getAccountId());
		clientToken.setTermType(opTerm);
		clientToken.setToken(UUIDUtils.getToken());
		clientToken.setLastDt(new Date());
		return customerClientTokenService.operaCustomerClientToken(clientToken, opTerm);
	}

	/**
	 * 登录活动调用
	 * @param customerAccount
	 * @param opTerm
	 */
	public void doLoginActivity(CustomerAccount customerAccount, String opTerm){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, customerAccount.getAccountId());
		map.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LOGIN);
		map.put(MarketConstant.CHANNEL_PARA, opTerm);
		marketFacadeService.login(map);
	}

	/**
	 * 登出
	 * @param response
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public String logout(HttpServletResponse response, String client, String token) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api logout start...");
		logger.info("【" + currentTime + "】logout income parameter: token=" + token + "");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(CustomerClientUtils.checkToken(token)){
			CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CustomerClientUtils.CLIENT_CACHE, CustomerClientUtils.CLIENT_CACHE_TOKEN_ + token);
			CustomerClientUtils.clearCache(customerClientToken);
			Date theDate = DateUtils.addMinutes(new Date(), ApiConstant.API_MOBILE_CACHE_TOKEN_INVALID_TIME);
			if(ProjectConstant.OP_TERM_DICT_PC.equals(opTerm)){
				theDate = DateUtils.addMinutes(new Date(), ApiConstant.API_WEBSITE_CACHE_TOKEN_INVALID_TIME);
			}
			customerClientToken.setLastDt(theDate);
			int i = customerClientTokenService.update(customerClientToken);
			if(i > 0){
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "登出成功", true);
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "登出失败", false);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + currentTime + "】logout out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api logout end...");
		logger.info("api logout total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}


	/**
	 * 注册
	 * @param request
	 * @param response
	 * @param client
	 * @param registerReq
     * @return
     */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, HttpServletResponse response, String client,RegisterReq registerReq) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		String mobile = registerReq.getMobile();
		String smsCode =  registerReq.getSmsCode();
		String inviteCode = registerReq.getInviteCode();
		String channel = registerReq.getChannel();
		String lotteryToken = registerReq.getLotteryToken();
		String subid = registerReq.getSubid();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api register start...");
		logger.info("【" + currentTime + "】register income parameter: mobile=" + mobile + ",smsCode=" + smsCode + ",inviteCode="
		+ inviteCode + ",channel=" + channel + ",lotteryToken=" + lotteryToken + "subid=" + subid + "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String token = "";
		//入参数据校验
		List<String> messages  = ApiUtil.validateBean(registerReq);
		if(messages != null){
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
		}else{
			if(checkIsMobilePhone(mobile, map)){
				if(checkMobileCanUse(mobile)){
					if(smsCodeService.auth(mobile, smsCode)){
						//BASE64解码
						ClientProperty cProperty = ApiUtil.getClient(client);
						String registerChannel = ApiUtil.getOperaChannel(cProperty.getType());
						String registerSource = cProperty.getWechat()!=null && cProperty.getWechat().getUa()!=null ? cProperty.getWechat().getUa() : "";
						//邀请码校验
						if(StringUtils.isNotBlank(inviteCode)){
							if(!checkIsInviteCode(inviteCode, map)){
								return renderString(response, map);
							}else if(inviteCode.equals(mobile)){
								ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "推荐人手机号不能填自己", false);
								return renderString(response, map);
							}
						}
						//外部编号校验
						if(StringUtils.isNotBlank(subid)){
							if(!checkExtendNoCanUse(subid, map)){
								return renderString(response, map);
							}
						}
						String accountName = customerAccountService.registerWithMobileAndReturnAccountName(request, registerChannel, mobile, registerReq.getPassword(), inviteCode, channel, lotteryToken, subid, registerSource);
						if(StringUtils.isNotBlank(accountName)) {
							CustomerAccount customerAccount = customerAccountService.getByMobile(mobile);
							//调用注册活动
							doRegisterActivity(customerAccount, registerChannel,channel);
							//注册成功
							token = registerSuccess(customerAccount, registerChannel, map);
						}else{
							ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "注册失败", false);
						}
					}else{
						ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "请输入正确的验证码", false);
					}
				}else{
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "手机号码已注册，请直接登录", false);
				}
			}
		}
		Date endTime = new Date();
		logger.info("【" + currentTime + "】register out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + ",token=" + token + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api register end...");
		logger.info("api register total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 检查手机是否可用
	 * @param mobile
	 * @return
	 */
	public boolean checkMobileCanUse(String mobile) {
		CustomerBase customerBase = new CustomerBase();
		customerBase.setMobile(mobile);
		return customerBaseService.checkMobileCanUse(customerBase);
	}

	/**
	 * 检查扩展编号是否可用
	 * @param extendNo
	 * @param map
	 * @return
	 */
	public boolean checkExtendNoCanUse(String extendNo, HashMap<String, Object> map) {
		if(customerAccountService.checkOuterIdCanUse(extendNo)){
			return true;
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "外部编号已注册", false);
		}
		return false;
	}

	/**
	 * 调用注册活动
	 * @param customerAccount
	 * @param opTerm
	 */
	private void doRegisterActivity(CustomerAccount customerAccount, String opTerm, String channel) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, customerAccount.getAccountId());
		map.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_REGISTER);
		map.put(MarketConstant.CHANNEL_PARA, opTerm);
		map.put(MarketConstant.SOURCE_CHANNEL_PARA, channel);
		marketFacadeService.register(map);
	}

	/**
	 * 注册成功
	 * @param customerAccount
	 * @param map
	 */
	public String  registerSuccess(CustomerAccount customerAccount, String opTerm, HashMap<String, Object> map){
		CustomerClientToken clientToken = new CustomerClientToken();
		clientToken.setCustomerId(customerAccount
				.getAccountId());
		clientToken.setTermType(opTerm);
		clientToken.setToken(UUIDUtils
				.getToken());
		clientToken.setLastDt(new Date());
		String token = customerClientTokenService
				.operaCustomerClientToken(clientToken, opTerm);
		//调用登录活动
		doLoginActivity(customerAccount, opTerm);
		RegisterResp data = new RegisterResp();
		data.setToken(token);
		ApiUtil.mapRespData(map, data, "注册成功", true);
		return token;
	}

	/**
	 * 发送验证码
	 * @param response
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "sendSmsCode", method = RequestMethod.POST)
	public String sendSmsCode(HttpServletRequest request, HttpServletResponse response, String client, String mobile) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api sendSmsCode start...");
		logger.info("【" + currentTime + "】sendSmsCode income parameter: mobile=" + mobile + "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(mobile)){
			if(checkIsMobilePhone(mobile, map)){
				logger.debug("send the mobile：" + mobile);
				sendSmsCode(request, mobile, ProjectConstant.SEND_VALIDATE_CODE_SERVICE_TYPE_REGISTER);
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "发送成功", true);
			}
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "手机号码不能为空", false);
		}
		Date endTime = new Date();
		logger.info("【" + currentTime + "】sendSmsCode out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api sendSmsCode end...");
		logger.info("api sendSmsCode total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 发送短信验证码
	 * @param mobile
	 * @param serviceTypeCode
	 */
	private void sendSmsCode(HttpServletRequest request, String mobile, String serviceTypeCode) {
		SmsAuthHandler.getInstance().sendRequest(mobile);
		//记录日志
		LogSendValidateCode logSendValidateCode = new LogSendValidateCode();
		logSendValidateCode.setMobile(mobile);
		logSendValidateCode.setServiceTypeCode(serviceTypeCode);
		logSendValidateCode.setOpDt(new Date());
		logSendValidateCode.setIp(request.getHeader("X-Real-IP"));
		logSendValidateCodeService.save(logSendValidateCode);
	}

	/**
	 * 手机号码是否已注册
	 * @param response
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "hasRegistered", method = RequestMethod.POST)
	public String hasRegistered(HttpServletResponse response, String client, String mobile) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api hasRegistered start...");
		logger.info("【" + currentTime + "】hasRegistered income parameter: mobile=" + mobile + "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(mobile)){
			if(checkIsMobilePhone(mobile, map)){
				if(checkMobileCanUse(mobile)){
					logger.info("check the mobile：" + mobile);
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "未注册", false);
				}else{
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "已注册", true);
				}
			}
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "手机号码不能为空", false);
		}
		Date endTime = new Date();
		logger.info("【" + currentTime + "】hasRegistered out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api hasRegistered end...");
		logger.info("api hasRegistered total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 签到
	 * @param response
	 * @param client
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "sign", method = RequestMethod.POST)
	public String sign(HttpServletResponse response, String client, String token) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api sign start...");
		logger.info("【" + currentTime + "】sign income parameter: token=" + token + "");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ){
			CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
			if(customerIntegralSnapshotService.canSign(customerAccount.getAccountId())){
				Map signMap = customerIntegralSnapshotService.sign(customerAccount.getAccountId(), opTerm);
				int signIntegral = (int)signMap.get("signIntegral");
				ApiUtil.mapRespData(map, signIntegral, "签到成功", true);
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "今日已签到", false);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + currentTime + "】sign out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api sign end...");
		logger.info("api sign total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 重置密码
	 * @param response
	 * @param client
	 * @param resetPwdReq
     * @return
     */
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	public String resetPassword(HttpServletResponse response, String client, ResetPwdReq resetPwdReq) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		String mobile = resetPwdReq.getMobile();
		String smsCode = resetPwdReq.getSmsCode();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api resetPassword start...");
		logger.info("【" + currentTime + "】resetPassword income parameter: mobile=" + mobile + ",smsCode=" + smsCode + "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		//入参数据校验
		List<String> messages  = ApiUtil.validateBean(resetPwdReq);
		if(messages != null){
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
		}else{
			if(checkIsMobilePhone(mobile, map)){
				if(smsCodeService.auth(mobile, smsCode)){
					logger.info("reset password operation the mobile is：" + mobile);
					//重置密码动作
					this.resetPasswordDo(mobile, resetPwdReq.getNewPassword());
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "重置密码成功", true);
				}else{
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "请输入正确的验证码", false);
				}
			}
		}
		Date endTime = new Date();
		logger.info("【" + currentTime + "】resetPassword out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api resetPassword end...");
		logger.info("api resetPassword total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 修改密码
	 * @param response
	 * @param client
	 * @param changePwdReq
	 * @param token
     * @return
     */
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	public String changePassword(HttpServletResponse response, String client,ChangePwdReq changePwdReq, String token) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		String newPassword = changePwdReq.getNewPassword();
		String oldPassword = changePwdReq.getOldPassword();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api changePassword start...");
		logger.info("【" + currentTime + "】changePassword income parameter: token=" + token + ",oldPassword=" + oldPassword + ",newPassword=" + newPassword + "");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null){
			List<String> messages  = ApiUtil.validateBean(changePwdReq);
			if(messages != null){
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
			}else{
				CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
				if(SystemService.validatePassword(oldPassword, customerAccount.getAccountPwd())){
					//修改密码动作
					this.modifyPasswordDo(map, clientToken, customerAccount.getAccountId(), newPassword, oldPassword);
				}else{
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "旧密码不正确", false);
				}
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + currentTime + "】changePassword out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api changePassword end...");
		logger.info("api changePassword total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 重置
	 * @param mobile
	 * @param newPassword
	 */
	public void resetPasswordDo(String mobile, String newPassword){
		//重置密码
		customerAccountService.resetPasswordByMobile(mobile, newPassword);
	}

	/**
	 * 修改密码
	 * @param map
	 * @param accountId
	 * @param newPassword
	 * @param oldPassword
	 */
	public void modifyPasswordDo(HashMap<String, Object> map, CustomerClientToken cToken, Long accountId, String newPassword, String oldPassword){
		HashMap<String,Object> mapR = customerAccountService.changePassword(accountId, oldPassword, newPassword);
		String isSuccess = String.valueOf(mapR.get("success"));
		if("true".equals(isSuccess)){
			String newToken = UUIDUtils.getToken();
			CustomerClientToken clientToken = new CustomerClientToken();
			clientToken.setCustomerId(accountId);
			clientToken.setTermType(cToken.getTermType());
			clientToken.setToken(newToken);
			customerClientTokenService.update(clientToken);
			//清除缓存
			if(CustomerClientUtils.checkToken(cToken.getToken())){
				CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CustomerClientUtils.CLIENT_CACHE, CustomerClientUtils.CLIENT_CACHE_TOKEN_ + cToken.getToken());
				CustomerClientUtils.clearCache(customerClientToken);
			}
			Map<String,String> mapData = new HashMap<String,String>();
			mapData.put("token", newToken);
			ApiUtil.mapRespData(map, mapData, "修改密码成功", true);
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "修改密码失败", false);
		}

	}

	/**
	 * 检查手机号码
	 * @param mobile
	 * @param map
	 * @return
	 */
	public boolean checkIsMobilePhone(String mobile, HashMap<String, Object> map) {
		logger.info("mobile =#####" + mobile + "#####");
		if(MobileUtil.isMobile(mobile)){
			return true;
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "请输入正确的手机号码", false);
		}
		return false;
	}

	/**
	 * 密码校验
	 * @param password
	 * @param map
	 * @return
	 */
	public boolean checkPassword(String password, HashMap<String, Object> map) {
		if(StringUtils.validatePwd(password)){
			return true;
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "请输入6-16位数字或字母组合密码", false);
		}
		return false;
	}

	public static void main(String[] args) {
		//System.out.println(MobileUtil.isMobile("17078889999"));
	}

	/**
	 * 校验邀请码
	 * @param mobile
	 * @param map
	 * @return
	 */
	public boolean checkIsInviteCode(String mobile, HashMap<String, Object> map) {
		if(MobileUtil.isMobile(mobile)){
			return true;
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "推荐人手机号码不合法", false);
		}
		return false;
	}

	/**
	 * 检查密码长度
	 * @param newPassword
	 * @param map
	 * @return
	 */
	public boolean checkNewPassword(String newPassword, HashMap<String, Object> map) {
		if(newPassword.length() > 5){
			return true;
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "密码不能少于6位", false);
		}
		return false;
	}

	/**
	 * 保存昵称
	 * @param response
	 * @param token
	 * @param nickname
	 * @return
	 */
	@RequestMapping(value = "saveNickname", method = RequestMethod.POST)
	public String saveNickname(HttpServletResponse response, String client, String token, String nickname) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api saveNickname start...");
		logger.info("【" + currentTime + "】saveNickname income parameter: token=" + token + ",nickname=" + nickname + "");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(nickname)){
			if(StringUtils.validateNickName(nickname)){
				CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
				if(clientToken != null ){
					CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
					if(StringUtils.isBlank(customerAccount.getNickname())){
						if(customerAccountService.checkNicknameCanUse(nickname)){
							HashMap<String, Object> reMap = customerAccountService.setNickName(clientToken.getCustomerId(), nickname);
							if((boolean) reMap.get("success")){
								ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "保存昵称成功", true);
							}else{
								ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "保存昵称失败", false);
							}
						}else{
							ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "昵称已使用", false);
						}

					}else{
						ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "昵称只能修改一次", false);
					}

				}else{
					ApiUtil.tokenInvalidRespMap(map);
				}
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "昵称格式不合法", false);
			}
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "昵称不能为空", false);
		}

		Date endTime = new Date();
		logger.info("【" + currentTime + "】saveNickname out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api saveNickname end...");
		logger.info("api saveNickname total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 保存用户是否接收push消息的标识
	 * @param response
	 * @param client
	 * @param token
	 * @param pushSwitch
	 * @return
	 */
	@RequestMapping(value = "savePushSwitch", method = RequestMethod.POST)
	public String savePushSwitch(HttpServletResponse response, String client, String token, String pushSwitch) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api savePushSwitch start...");
		logger.info("【" + currentTime + "】savePushSwitch income parameter: token=" + token + ",pushSwitch=" + pushSwitch + "");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		pushSwitch = "true".equals(pushSwitch) ? ApiConstant.PUSH_SWITCH_YES : ApiConstant.PUSH_SWITCH_NO;
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ){
			customerPushSwitchService.updateIsReceive(clientToken.getCustomerId(), opTerm, pushSwitch);
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api savePushSwitch end...");
		logger.info("api savePushSwitch total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 保存Email
	 * @param response
	 * @param token
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "saveEmail", method = RequestMethod.POST)
	public String saveEmail(HttpServletResponse response, String client, String token, String email) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api saveEmail start...");
		logger.info("【" + currentTime + "】saveEmail income parameter: token=" + token + ",email=" + email + "");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(email)){
			CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
			if(clientToken != null ){
				int i = customerAccountService.updateEmail(clientToken.getCustomerId(), email);
				if(i > 0){
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "保存邮箱成功", true);
				}else{
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "保存邮箱失败", false);
				}
			}else{
				ApiUtil.tokenInvalidRespMap(map);
			}
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "邮箱不能为空", false);
		}

		Date endTime = new Date();
		logger.info("【" + currentTime + "】saveEmail out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api saveEmail end...");
		logger.info("api saveEmail total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 保存Mobile
	 * @param response
	 * @param token
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "saveMobile", method = RequestMethod.POST)
	public String saveMobile(HttpServletResponse response, String client, String token, String mobile) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api saveMobile start...");
		logger.info("【" + currentTime + "】saveMobile income parameter: token=" + token + ",mobile=" + mobile + "");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(mobile)){
			if(checkIsMobilePhone(mobile, map)){
				CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
				if(clientToken != null ){
					int i = customerAccountService.updateMobile(clientToken.getCustomerId(), mobile);
					if(i > 0){
						ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "保存手机成功", true);
					}else{
						ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "保存手机失败", false);
					}
				}else{
					ApiUtil.tokenInvalidRespMap(map);
				}
			}
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "手机号码不能为空", false);
		}

		Date endTime = new Date();
		logger.info("【" + currentTime + "】saveMobile out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api saveMobile end...");
		logger.info("api saveMobile total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 保存头像
	 * @param response
	 * @param token
	 * @param avatar
	 * @return
	 */
	@RequestMapping(value = "saveAvatar", method = RequestMethod.POST)
	public String saveAvatar(HttpServletResponse response,HttpServletRequest request, String client, String token,
			String avatar,String avatarUrl) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api saveAvatar start...");
		logger.info("【" + currentTime + "】saveAvatar income parameter: token=" + token + ",avatar=" + avatar + "");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		String imagePath = "";
		if(StringUtils.isNotBlank(avatar)){
			CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			if(clientToken != null ){
				String avatarPathName = new StringBuffer()
				.append(File.separator)
				.append("upload_files")
				.append(File.separator)
				.append("avatar")
				.append(File.separator)
				.append(FileUtil.getRandomFileNamePrefix())
				.append(".")
				.append("jpg")
				.toString();
				ImageUtils.GenerateImage(avatar, rootPath+avatarPathName);
				CustomerAccount customerAccount = new CustomerAccount();
				customerAccount.setAccountId(clientToken.getCustomerId());
				customerAccount.setAvatarImage(avatarPathName);
				customerAccountService.updateAvatar(customerAccount);
				CustomerAccount  cAccount = customerAccountService.get(clientToken.getCustomerId());
				imagePath =  ApiUtil.imageUrlConver(cAccount.getAvatarImage());
				ApiUtil.mapRespData(map, imagePath, "保存头像成功", true);
			}else{
				ApiUtil.tokenInvalidRespMap(map);
			}
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "头像不能为空", false);
		}
		Date endTime = new Date();
		logger.info("【" + currentTime + "】saveAvatar out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "avatarUrl=" + imagePath + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api saveAvatar end...");
		logger.info("api saveAvatar total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 提现前置接口
	 * @param response
	 * @param token
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "beforeWithdraw", method = RequestMethod.POST)
	public String beforeWithdraw(HttpServletResponse response, String client, String token) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api beforeWithdraw start...");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);
		if(clientToken != null ){
			Long accountId = clientToken.getCustomerId();
			CustomerBankCard card = customerBankCardService.getByAccountId(accountId);
			CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
			BeforeWithdrawResp data = new BeforeWithdrawResp();
			data.setCardNo(StringUtil.dealString(card.getCardNo()));
			data.setStatus(StringUtil.dealString(card.getCardStatusCode()));
			data.setBankCode(StringUtil.dealString(card.getBankCode()));
			data.setBankName(DictUtils.getDictLabel(card.getBankCode(), "yeepay_bank_code_dict", ""));
			data.setBankLogo("");
			data.setAmount(availableBalance(customerBalance));
			data.setTicketCount(customerBalance.getFreeWithdrawCount());
			ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api beforeWithdraw end...");
		logger.info("api beforeWithdraw total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}



	/**
	 * 我的信息
	 * @param response
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "my", method = RequestMethod.POST)
	public String my(HttpServletResponse response, HttpServletRequest request, String client, String token) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api my start...");
		logger.info("【" + currentTime + "】my income parameter: token=" + token);
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		MyResp data = new MyResp();
		if(clientToken != null ){
			Long accountId = clientToken.getCustomerId();
			CustomerBalance  customerBalance = customerBalanceService.get(accountId + "");
			CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
			CustomerBankCard card = customerBankCardService.getByAccountId(clientToken.getCustomerId());
			CustomerIntegralSnapshot cIntegralSnapshot = customerIntegralSnapshotService.getByAccountId(accountId);
			CurrentAccountSummary summary = currentAccountSummaryService.getByAccountId(accountId);
			if(customerBalance != null ){
				//我的信息数据
				data = mySuccess(opTerm, accountId, customerBalance, summary, customerAccount, card, cIntegralSnapshot, map);
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
			}

		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + currentTime + "】my out parameter: accountId=" + data.getAccountId() + ",mobile=" + data.getMobile() + ",customerName=" + data.getCustomerName() + ""
				+ ",goldBalance=" + data.getGoldBalance() + ",congealVal=" + data.getCongealVal() + ",availableBalance=" + data.getAvailableIntegral() + ""
				+ ",hasOpenThirdAccount=" + data.getHasOpenThirdAccount() + ",isNewUser=" + data.getIsNewUser() + "," + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT));
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api my end...");
		logger.info("api my total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 我的信息数据
	 * @param opTerm
	 * @param accountId
	 * @param customerBalance
	 * @param summary
	 * @param customerAccount
	 * @param card
	 * @param cIntegralSnapshot
     * @param map
     * @return
     */
	public MyResp mySuccess(String opTerm, Long accountId, CustomerBalance  customerBalance, CurrentAccountSummary summary, CustomerAccount  customerAccount, CustomerBankCard card, CustomerIntegralSnapshot cIntegralSnapshot, HashMap<String, Object> map){

		MyResp data = new MyResp();
		data.setAccountId(String.valueOf(customerAccount.getAccountId()));
		data.setAvatar(ApiUtil.imageUrlConver(customerAccount.getAvatarImage()));
		data.setCongealVal(customerBalance.getCongealVal());
		data.setGoldBalance(customerBalance.getGoldBalance());
		data.setNickname(StringUtil.dealString(customerAccount.getNickname()));
		data.setCustomerName(StringUtil.dealString(customerAccount.getCustomerBase().getCustomerName()));
		data.setCertNum(StringUtils.vagueCertNum(StringUtil.dealString(customerAccount.getCustomerBase().getCertNum())));
		data.setMobile(customerAccount.getCustomerBase().getMobile());
		data.setEmail(StringUtil.dealString(customerAccount.getCustomerBase().getEmail()));
		data.setBankCardNo(StringUtil.dealString(card.getCardNo()));
		Double willPrincipal = customerBalance.getWillPrincipal() !=null ? customerBalance.getWillPrincipal() : 0.0;
		Double currentPrincipal = summary.getCurrentPrincipal() !=null ? summary.getCurrentPrincipal() : 0.0;
		data.setCurrentInvestment(LoanUtil.formatAmount(currentPrincipal));
		data.setPeriodicInvestment(LoanUtil.formatAmount(willPrincipal));
		//待收本金 = 定期待收本金 + 活期当前持有本金
		willPrincipal = LoanUtil.formatAmount(willPrincipal + currentPrincipal);
		data.setWillPrincipal(willPrincipal);
		data.setNetAssets(LoanUtil.formatAmount(customerBalance.getGoldBalance() + willPrincipal));
		Double sumProfit = LoanUtil.formatAmount(customerBalance.getSumProfit());
		Double currentProfit = LoanUtil.formatAmount(summary.getTotalGetInterest());
		Double activityReward = LoanUtil.formatAmount(customerBalanceHisService.getActivityReward(accountId));
		Double willProfit = LoanUtil.formatAmount(customerBalance.getWillProfit());
		Double profitTotal = LoanUtil.formatAmount(sumProfit + currentProfit + activityReward + willProfit);
		data.setSumProfit(sumProfit);
		data.setObtainProfit(sumProfit);
		data.setCurrentProfit(currentProfit);
		data.setActivityReward(activityReward);
		data.setWillProfit(willProfit);
		data.setProfitTotal(profitTotal);
		data.setAvailableBalance(availableBalance(customerBalance));
		data.setAvailableIntegral(cIntegralSnapshot.getIntegralBalance().doubleValue());
		data.setHasSigned(customerIntegralSnapshotService.canSign(accountId) == false ? "true" : "false");
		data.setHasRemindOfMsg(messageInstanceService.hasRemindOfMsg(accountId, opTerm) == true ? "true" : "false");
		data.setHasRemindOfTicket(customerInvestmentTicketService.hasRemindOfTicket(accountId) == true ? "true" : "false");
		data.setHasRemindOfReward("false");
		AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
		data.setHasOpenThirdAccount(ProjectConstant.HASOPENED.equals(accountInfoResp.getCode()) ? ProjectConstant.HASOPENED : ProjectConstant.UNOPENED);
		data.setHasBindBankCard(customerBankCardService.hasBindBankCard(accountInfoResp) == true ? "1" : "0");
		data.setIsNewUser(projectInvestmentRecordService.isNewCustomer(accountId + "") == true ? "0" : "1");
		boolean hasRecharged = customerBalance.getRechargeCount() > 0 ? true : false;
		if(!hasRecharged){
			hasRecharged = LogThirdPartyService.isHasRecharged(customerAccount.getPlatformUserNo());
		}
		data.setHasRecharged(hasRecharged ? "0" : "1");
		//活期信息
		data.setCurrent(currentMapData(accountId, summary));
		//银行卡信息
		data.setBankCard(bankCard(accountInfoResp, customerBalance, card));
		ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		return data;
	}


	/**
	 * my的活期数据
	 * @param accountId
	 * @param summary
	 * @return
	 */
	public  Map<String, Object> currentMapData(Long accountId, CurrentAccountSummary summary){
		double maxPrincipal = CurrentProjectConstant.MAX_INVESTMENT_MONEY;
		//当前持有本金
		Double holdPrincipal = summary.getCurrentPrincipal() !=null ? summary.getCurrentPrincipal() :0.00;
		//投资冻结资金
		Double frozenAmount = currentAccountPrincipalChangeHisService.getFrozenAmount(accountId);
		//昨日收益
		Double yesterdayProfit = currentAccountInterestChangeHisService.getYesterdayProfitByAccountId(accountId, CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST);
		yesterdayProfit = yesterdayProfit !=null ? yesterdayProfit : 0.00;
		//剩余可购买活期额度 = 最大持有本金 - 已持有本金 - 投资冻结资金
		Double differPrincipal = LoanUtil.formatAmount(maxPrincipal - holdPrincipal - frozenAmount);
		Map<String ,Object> currentMap = new HashMap<String ,Object>();
		currentMap.put("amount", holdPrincipal);
		currentMap.put("availableAmount", differPrincipal);
		currentMap.put("interestOfYesterday", new BigDecimal(yesterdayProfit).setScale(4,   BigDecimal.ROUND_HALF_UP));
		return currentMap;
	}

	/**
	 * 我的现金券
	 * @param response
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "myTickets", method = RequestMethod.POST)
	public String myTickets(HttpServletResponse response, String client, String token, Integer status) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myTickets start...");
		logger.info("【" + currentTime + "】myTickets income parameter: token=" + token);
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		List<CustomerInvestmentTicket> cList = new ArrayList<CustomerInvestmentTicket>();
		if(clientToken != null ){
			Long accountId = clientToken.getCustomerId();
			CustomerInvestmentTicket cInvestmentTicket = new CustomerInvestmentTicket();
			cInvestmentTicket.setAccountId(accountId);
			if(status != null && (status>=0 && status<3)){
				cInvestmentTicket.setStatus(status+"");
			}
			cList = customerInvestmentTicketService.findList(cInvestmentTicket);
			cList = cList != null ? cList : new ArrayList<CustomerInvestmentTicket>();
			//我的优惠卷数据
			myTicketsSuccess(cList, map);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + currentTime + "】myTickets out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT));
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】myTickets my end...");
		logger.info("api myTickets total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 我的优惠卷数据
	 * @param cList
	 * @param map
	 */
	public void myTicketsSuccess(List<CustomerInvestmentTicket> cList, HashMap<String, Object> map){
		List<Object> dataList = new ArrayList<Object>();
		for(CustomerInvestmentTicket ticket : cList){
			MyTicketsResp mResp = new MyTicketsResp();
			mResp.setTicketId(NumberUtils.toLong(ticket.getId(), 0L));
			mResp.setType(ticket.getTicketTypeId());
			mResp.setTypeName(ticket.getInvestmentTicketType().getTicketTypeName());
			mResp.setUseInfo(ticket.getInvestmentTicketType().getUseInfo());
			mResp.setGetRemark(ticket.getGetRemark());
			mResp.setUseLimit(ticket.getInvestmentTicketType().getUseLimit());
			mResp.setAmount(ticket.getInvestmentTicketType().getDenomination());
			mResp.setStatus(NumberUtils.toLong(ticket.getStatus(), 0));
			mResp.setStatusName(DictUtils.getDictLabel(ticket.getStatus(), "customer_investment_ticket_dict", ""));
			mResp.setInvalidDt(DateUtils.formatDate(ticket.getInvalidDt()));
			dataList.add(mResp);
		}
		ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
	}

	/**
	 * 还款日历
	 * @param response
	 * @param token
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "repaymentCalendar", method = RequestMethod.POST)
	public String repaymentCalendar(HttpServletResponse response, String client, String token, String year, String month) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api repaymentCalenda start...");

		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null ){
			List<ProjectRepaymentSplitRecord> reList = projectRepaymentSplitRecordService.getRepaymentCalendarList(clientToken.getCustomerId(), year, month);
			Map<String, Object> recordMap = projectRepaymentSplitRecordService.getRepaymentCalendarDetailMap(clientToken.getCustomerId(), year, month);
			Map<String,Double> mapSum = (Map<String, Double>) recordMap.get("mapSum");
			Map<Date,List<Object>> mapList = (Map<Date, List<Object>>) recordMap.get("mapList");
			List<Object> dayList = new ArrayList<Object>();
			Map<String,Object> dataMap = new HashMap<String,Object>();
			for(ProjectRepaymentSplitRecord splitRecord : reList){
				RepaymentCalendarResp data = new RepaymentCalendarResp();
				data.setDay(splitRecord.getDay());
				data.setMoney(com.thinkgem.jeesite.common.utils.NumberUtils.formatWithScale(splitRecord.getMoney(),2));
				data.setStatus(Integer.parseInt(splitRecord.getStatus()));
				List<Object> dataDList = new ArrayList<Object>();
				dataDList = mapList.get(splitRecord.getRepaymentDt());
				data.setRecordList(dataDList);
				dayList.add(data);
			}
			dataMap.put("sumRepaymentAmout", mapSum.get("sumRepaymentAmout"));
			dataMap.put("repaymentAmout", mapSum.get("repaymentAmout"));
			dataMap.put("dayList", dayList);
			ApiUtil.mapRespData(map, dataMap, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api repaymentCalenda end...");
		logger.info("api repaymentCalenda total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 指定投资记录的还款计划
	 * @param response
	 * @param client
	 * @param recordId
	 * @return
	 */
	@RequestMapping(value = "repaymentPlan", method = RequestMethod.POST)
	public String repaymentPlan(HttpServletResponse response, String client, String recordId) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api repaymentPlan start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<RepaymentPlanResp> respList = new ArrayList<RepaymentPlanResp>();
		List<ProjectRepaymentSplitRecord> reList = projectRepaymentSplitRecordService.getRepaymentListByRecordIdAndStatus(recordId,StringUtils.surroundSymbol("0,1",",","'"));
		for(ProjectRepaymentSplitRecord splitRecord : reList){
			RepaymentPlanResp resp = new RepaymentPlanResp();
			resp.setPlanDate(DateUtils.formatDate(splitRecord.getRepaymentDt(), "yyyy-MM-dd"));
			resp.setPlanMoney(String.valueOf(splitRecord.getMoney()));
			resp.setPrincipal(String.valueOf(splitRecord.getPrincipal()));
			resp.setInterest(String.valueOf(splitRecord.getInterest()));
			resp.setRemainingPrincipal(String.valueOf(splitRecord.getRemainedPrincipal()));
			resp.setStatus(splitRecord.getStatus());
			resp.setStatusName(DictUtils.getDictLabel(splitRecord.getStatus(), "project_repay_status_dict", ""));
			respList.add(resp);
		}
		ApiUtil.mapRespData(map, respList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api repaymentPlan end...");
		logger.info("api repaymentPlan total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 我的投资
	 * @param response
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "myInvestment", method = RequestMethod.POST)
	public String myInvestment(HttpServletResponse response, String client, String token, String flag) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myInvestment start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null ){
			CustomerBalance customerBalance = customerBalanceService.get(clientToken.getCustomerId() + "");
			List<ProjectBaseInfo> projectList = projectBaseInfoService.findMyInvestmentList(clientToken.getCustomerId() + "", flag);
			//我的投资数据
			myInvestmentSuccess(customerBalance, projectList, map);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myInvestment end...");
		logger.info("api myInvestment total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 我的投资数据
	 * @param customerBalance
	 * @param projectList
	 * @param map
	 */
	public void myInvestmentSuccess(CustomerBalance customerBalance, List<ProjectBaseInfo> projectList, HashMap<String, Object> map){
		List<ProjectBaseInfoResp> projectBaseInfoRespList = new ArrayList<ProjectBaseInfoResp>();
		for(ProjectBaseInfo pInfo : projectList){
			ProjectBaseInfoResp pResp = new ProjectBaseInfoResp();
			pResp.setProjectId(NumberUtil.toLong(pInfo.getProjectId(),0L));
			pResp.setProjectCode(pInfo.getProjectCode());
			pResp.setRecordId(pInfo.getPir().getId());
			pResp.setProjectType(pInfo.getProjectTypeCode());
			pResp.setProjectTypeName(DictUtils.getDictLabel(String.valueOf(pInfo.getProjectTypeCode()), "project_type_dict", ""));
			pResp.setProjectDuration(pInfo.getProjectDuration());
			pResp.setProjectName(pInfo.getProjectName());
			pResp.setRepaymentMode(NumberUtil.toLong(pInfo.getRepaymentMode(), 0L));
			pResp.setRepaymentModeName(DictUtils.getDictLabel(pInfo.getRepaymentMode(), "project_repayment_mode_dict", ""));
			pResp.setAmount(pInfo.getPir().getAmount());
			ProjectRepaymentSplitRecord pSplitRecord = projectRepaymentSplitRecordService.getRepaymentInfoByProjectAndccountId(pInfo.getProjectId(), customerBalance.getAccountId(), NumberUtils.toLong(pInfo.getPir().getId(), 0L));
			pResp.setReceivedProfit(pSplitRecord.getSumReceivedProfit());
			pResp.setWillProfit(pSplitRecord.getSumWillProfit());
			pResp.setStatus(NumberUtil.toLong(pInfo.getProjectStatus(), 0));
			pResp.setStatusName(DictUtils.getDictLabel(pInfo.getProjectStatus(), "project_status_dict", ""));
			pResp.setAnnualizedRate(pInfo.getAnnualizedRate());
			pResp.setRemainingDays((long)DateUtils.getDistanceOfTwoDate(DateUtils.dateFormate(new Date()), pSplitRecord.getLastRepaymentDt()));
			pResp.setOpDt(DateUtils.formatDate(pInfo.getPir().getOpDt()));
			pResp.setLastRepaymentDate(DateUtils.formatDate(pSplitRecord.getLastRepaymentDt()));
			pResp.setIsNewUser(pInfo.getIsNewUser());
			pResp.setIsRecommend("1".equals(pInfo.getIsRecommend()) ? "0" : "1");
			projectBaseInfoRespList.add(pResp);
		}
		MyInvestmentResp data = new MyInvestmentResp();
		data.setTotalInvestment(customerBalance.getSumInvestment());
		data.setReceiveMoney(customerBalance.getReceiveMoney());
		data.setTipMsg("");
		data.setProjectList(projectBaseInfoRespList);
		ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
	}

	/**
	 * 我的投资--项目详情
	 * @param response
	 * @param client
	 * @param token
	 * @param recordId
     * @return
     */
	@SuppressWarnings("unused")
	@RequestMapping(value = "myInvestmentDetail", method = RequestMethod.POST)
	public String myInvestmentDetail(HttpServletResponse response, String client, String token, String recordId) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myInvestmentDetail start...");
		logger.info("【" + currentTime + "】myInvestmentDetail income parameter: token=" + token + ",recordId=" + recordId);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null ){
			ProjectBaseInfo pInfo = projectBaseInfoService.getProjectByRecordId(recordId);
			ProjectExecuteSnapshot pSnapshot = projectExecuteSnapshotService.getByProjectId(pInfo.getProjectId());
			if(pInfo != null){
				//我的投资-项目信息
				myInvestmentDetail(clientToken.getCustomerId(), pInfo, pSnapshot, map);
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
			}

		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myInvestmentDetail end...");
		logger.info("api myInvestmentDetail total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 我的投资-项目信息
	 * @param accountId
	 * @param pInfo
	 * @param map
	 */
	public void myInvestmentDetail(Long accountId, ProjectBaseInfo pInfo, ProjectExecuteSnapshot pSnapshot, HashMap<String, Object> map){
		ProjectBaseInfoResp pResp = new ProjectBaseInfoResp();
		pResp.setProjectId(NumberUtil.toLong(pInfo.getProjectId(),0L));
		pResp.setProjectCode(pInfo.getProjectCode());
		pResp.setRecordId(pInfo.getPir().getId());
		pResp.setProjectType(pInfo.getProjectTypeCode());
		pResp.setProjectTypeName(DictUtils.getDictLabel(String.valueOf(pInfo.getProjectTypeCode()), "project_type_dict", ""));
		pResp.setProjectName(pInfo.getProjectName());
		pResp.setRepaymentMode(NumberUtil.toLong(pInfo.getRepaymentMode(), 0L));
		pResp.setRepaymentModeName(DictUtils.getDictLabel(pInfo.getRepaymentMode(), "project_repayment_mode_dict", ""));
		pResp.setAmount(pInfo.getPir().getAmount());
		ProjectRepaymentSplitRecord pSplitRecord = projectRepaymentSplitRecordService.getRepaymentInfoByProjectAndccountId(pInfo.getProjectId(), accountId, NumberUtils.toLong(pInfo.getPir().getId(), 0L));
		pResp.setReceivedProfit(pSplitRecord.getSumReceivedProfit());
		pResp.setWillProfit(pSplitRecord.getSumWillProfit());
		pResp.setStatus(NumberUtil.toLong(pInfo.getProjectStatus(), 0));
		pResp.setStatusName(DictUtils.getDictLabel(pInfo.getProjectStatus(), "project_status_dict", ""));
		pResp.setAnnualizedRate(pInfo.getAnnualizedRate());
		int totalPeriods = pSplitRecord.getTotalPeriods() > 0 ? pSplitRecord.getTotalPeriods() : 1;
		//还款进度 = 已还款期数/总期数 * 100
		double rate = ((double)pSplitRecord.getReceivedPeriods() / (double)totalPeriods) * 100;
		pResp.setRate(ApiUtil.formatRate(rate));
		String investmentPeriod = pSplitRecord.getFirstRepaymentDt().compareTo(
				pSplitRecord.getLastRepaymentDt()) != 0 ? DateUtils.formatDate(pSplitRecord.getFirstRepaymentDt())
				+ "至"+ DateUtils.formatDate(pSplitRecord.getLastRepaymentDt())
				: DateUtils.formatDate(pSplitRecord.getFirstRepaymentDt());
		pResp.setInvestmentPeriod(investmentPeriod);
		String remaindAndTotalMonth = String.valueOf(pSplitRecord.getTotalPeriods() - pSplitRecord.getReceivedPeriods()) + "/" + String.valueOf(pSplitRecord.getTotalPeriods());
		pResp.setRemaindAndTotalMonth(remaindAndTotalMonth);
		pResp.setIsNewUser(pInfo.getIsNewUser());
		pResp.setIsRecommend("1".equals(pInfo.getIsRecommend()) ? "0" : "1");
		pResp.setIsCanAssign(pInfo.getTransferCode() != -1 ? "0" : "-1");
		ApiUtil.mapRespData(map, pResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
	}

	/**
	 * 交易记录分页列表
	 * @param response
	 * @param token
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "transactionRecord", method = RequestMethod.POST)
	public String transactionRecord(HttpServletResponse response, String client, String token, Integer pageNumber, Integer pageSize) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api transactionRecord start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token , ApiUtil.getOperTerm(client));
		if(clientToken != null ){
			List<CustomerBalanceHis> list = customerBalanceHisService.getTransactionRecordPage(clientToken.getCustomerId(), pageNumber, pageSize);
			List<Object> dataList = new ArrayList<Object>();
			for(CustomerBalanceHis cHis : list){
				TransactionRecordResp data = new TransactionRecordResp();
				data.setOpDt(DateUtils.formatDateTime(cHis.getOpDt()));
				data.setChangeType(cHis.getChangeType());
				data.setChangeTypeName(DictUtils.getDictLabel(String.valueOf(cHis.getChangeType()), "customer_balance_change_type_dict", ""));
				data.setChangeVal(LoanUtil.formatAmount(cHis.getChangeVal()));
				dataList.add(data);
			}
			ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api transactionRecord end...");
		logger.info("api transactionRecord total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 推荐有奖-好友分页列表
	 * @param response
	 * @param client
	 * @param token
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "myInvitationPageList", method = RequestMethod.POST)
	public String myInvitationPageList(HttpServletResponse response, String client, String token, String status, Integer pageNumber, Integer pageSize) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myInvitationPageList start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,  ApiUtil.getOperTerm(client));
		if(clientToken != null ){
			List<CustomerAccount> list = customerAccountService.getPageListByRecommendorAccountId(clientToken.getCustomerId(), pageNumber, pageSize);
			List<Object> dataList = new ArrayList<Object>();
			List<Object> registeredList = new ArrayList<Object>();//已注册list
			List<Object> tradedList = new ArrayList<Object>();//已成交list
			List<Object> investedList = new ArrayList<Object>();//已投资list
			List<Object> rechargeList = new ArrayList<Object>();//已充值list
			List<Object> hasopenthirdList = new ArrayList<Object>();//已开通第三方账号list
			for(CustomerAccount ca : list){
				CustomerBase customerBase = customerBaseService.getByAccountId(ca.getAccountId());
				ca.setCustomerBase(customerBase);
				String statusName = "已注册";
				String statusShow = ApiConstant.MY_INVITATION_STATUS_REGISTERED;
				int afterRepaymentCount = projectInvestmentRecordService.getRecordCountAfterRepaymentByAccountId(ca.getAccountId());
				int investCount = projectInvestmentRecordService.getInvestCountByAccountId(ca.getAccountId());
				CustomerBalance customerBalance = customerBalanceService.get(ca.getAccountId() + "");
				myInvitationList(registeredList, ca, customerBase, statusName, statusShow);
				if(investCount > 0){
					statusShow = ApiConstant.MY_INVITATION_STATUS_INVESTED;
					statusName = "已投资";
					myInvitationList(investedList, ca, customerBase, statusName, statusShow);
				}
				if(customerBalance.getRechargeCount() > 0){
					statusShow = ApiConstant.MY_INVITATION_STATUS_RECHARGE;
					statusName = "已充值";
					myInvitationList(rechargeList, ca, customerBase, statusName, statusShow);
				}
				if("1".equals(ca.getHasOpenThirdAccount())){
					statusShow = ApiConstant.MY_INVITATION_STATUS_HASOPENTHIRD;
					statusName = "已开通资金托管账户";
					myInvitationList(hasopenthirdList, ca, customerBase, statusName, statusShow);
				}

				myAllInvitationList(dataList, tradedList, ca, customerBase, customerBalance, afterRepaymentCount, investCount, statusName, statusShow);
			}
			if(ApiConstant.MY_INVITATION_STATUS_REGISTERED.equals(status)){
				dataList = registeredList;
			}else if(ApiConstant.MY_INVITATION_STATUS_INVESTED.equals(status)){
				dataList = investedList;
			}else if(ApiConstant.MY_INVITATION_STATUS_RECHARGE.equals(status)){
				dataList = rechargeList;
			}else if(ApiConstant.MY_INVITATION_STATUS_HASOPENTHIRD.equals(status)){
				dataList = hasopenthirdList;
			}else if(ApiConstant.MY_INVITATION_STATUS_TRADED.equals(status)){
				dataList = tradedList;
			}
			ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myInvitationPageList end...");
		logger.info("api myInvitationPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 组装list数据
	 * @param list
	 * @param customerBase
	 * @param statusName
	 * @param statusShow
	 * @return
	 */
	public List<Object> myInvitationList(List<Object> list, CustomerAccount ca, CustomerBase customerBase, String statusName, String statusShow){
		MyInvitationResp data = new MyInvitationResp();
		data.setName(StringUtils.vagueName(customerBase.getCustomerName()));
		data.setAccount(customerBase.getMobile());
		data.setStatus(NumberUtils.toLong(statusShow, 0L));
		data.setStatusName(statusName);
		data.setRegisterChannel(registerChannel(ca.getRegisterChannel()));
		Date registerDt = ca.getRegisterDt() != null ? ca.getRegisterDt() : new Date();
		data.setRegisterDt(DateUtils.formatDateTime(registerDt));
		list.add(data);
		return list;
	}

	/**
	 * 我的list数据
	 * @param dataList
	 * @param tradedList
	 * @param ca
	 * @param customerBase
	 * @param customerBalance
	 * @param afterRepaymentCount
	 * @param investCount
	 * @param statusName
	 * @param statusShow
	 */
	public void myAllInvitationList(List<Object> dataList, List<Object> tradedList, CustomerAccount ca, CustomerBase customerBase, CustomerBalance customerBalance, int afterRepaymentCount, int investCount, String statusName, String statusShow){
		if(afterRepaymentCount > 0) {
			statusShow = ApiConstant.MY_INVITATION_STATUS_TRADED;
			statusName = "已投资";
			this.myInvitationList(tradedList, ca, customerBase, statusName, statusShow);
		} else {
			if(investCount > 0) {
				statusShow = ApiConstant.MY_INVITATION_STATUS_INVESTED;
				statusName = "已投资";
			} else {
				if(customerBalance.getRechargeCount() > 0) {
					statusShow = ApiConstant.MY_INVITATION_STATUS_RECHARGE;
					statusName = "已充值";
				} else if("1".equals(ca.getHasOpenThirdAccount())) {
					statusShow = ApiConstant.MY_INVITATION_STATUS_HASOPENTHIRD;
					statusName = "已开通资金托管账户";
				}
			}
		}
		MyInvitationResp data = new MyInvitationResp();
		data.setName(StringUtils.vagueName(customerBase.getCustomerName()));
		data.setAccount(customerBase.getMobile());
		data.setStatus(NumberUtils.toLong(statusShow, 0L));
		data.setStatusName(statusName);
		data.setRegisterChannel(registerChannel(ca.getRegisterChannel()));
		Date registerDt = ca.getRegisterDt() != null ? ca.getRegisterDt() : new Date();
		data.setRegisterDt(DateUtils.formatDateTime(registerDt));
		dataList.add(data);
	}

	/**
	 * 注册渠道转化
	 * @param channel
	 * @return
	 */
	public static String registerChannel(String channel){
		String registerChannel = "";
		if(ProjectConstant.OP_TERM_DICT_PC.equals(channel)){
			registerChannel = "PC邀请注册";
		}else if(ProjectConstant.OP_TERM_DICT_WEIXIN.equals(channel)){
			registerChannel = "微信邀请注册";
		}else if(ProjectConstant.OP_TERM_DICT_ANDROID.equals(channel)){
			registerChannel = "安卓邀请注册";
		}else if(ProjectConstant.OP_TERM_DICT_IOS.equals(channel)){
			registerChannel = "iOS邀请注册";
		}
		return registerChannel;
	}

	/**
	 * 推荐有奖-统计
	 * @param response
	 * @param client
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "myInvitationStat", method = RequestMethod.POST)
	public String myInvitationStat(HttpServletResponse response, String client, String token) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myInvitationStat start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,  ApiUtil.getOperTerm(client));
		if(clientToken != null ){
			String causeType = StringUtils.surroundSymbol(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_FRIEND_INVESTMENT + "," + ProjectConstant.MARKET_AWARD_CAUSE_TYPE_FRIEND_OTHER_BEHAVIOR, ",","'");
			List<MarketingActivityAwardRecord> aRecordsList = marketingActivityAwardRecordService.findListByFriendSelected(clientToken.getCustomerId(), causeType);
			Double registerAmount = 0.0;
			Double nameAuthAmount = 0.0;
			Double investAmount = 0.0;
			Double earningAmount = 0.0;
			Double earningTicketAmount = 0.0;
			for(MarketingActivityAwardRecord aRecord: aRecordsList){
				String behaviorCode = aRecord.getBehaviorCode();
				String awardType =aRecord.getAwardType();
				Double awardValue = aRecord.getAwardValue() !=null ?  NumberUtil.toDouble(aRecord.getAwardValue(), 0.0) : 0.0;
				if(ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET.equals(awardType)){
					if(MarketConstant.CUSTOMER_BEHAVIOR_REGISTER.equals(behaviorCode)){
						registerAmount += awardValue;
					}else if(MarketConstant.CUSTOMER_BEHAVIOR_OPEN_THIRD_PARTY.equals(behaviorCode)){
						nameAuthAmount += awardValue;
					}else if(MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER_OVER.equals(behaviorCode)){
						investAmount += awardValue;
					}
					earningTicketAmount += awardValue;
				}else if(ProjectConstant.MARKETING_AWARD_TYPE_MONEY.equals(awardType)){
					earningAmount += awardValue;
				}
			}
			List<CustomerAccount> list = customerAccountService.getListByRecommendorAccountId(clientToken.getCustomerId());
			int registerCount = list != null ? list.size() : 0;	//注册人数
		    int nameAuthCount = 0;  //实名人数
		    int investAccount = 0;  //投资人数
			for(CustomerAccount ca : list){
				int investCount = projectInvestmentRecordService.getInvestCountByAccountId(ca.getAccountId());
				if("1".equals(ca.getHasOpenThirdAccount())){
					nameAuthCount += 1;
				}
				if(investCount > 0){
					investAccount += 1;
				}
			}
			MyInvitationStatResp data = new MyInvitationStatResp();
			data.setRegisterCount(registerCount);
			data.setNameAuthCount(nameAuthCount);
			data.setInvestCount(investAccount);
			data.setInvestAccount(investAccount);
			data.setRegisterAmount(registerAmount);
			data.setNameAuthAmount(nameAuthAmount);
			data.setEarningAmount(earningAmount);
			data.setEarningTicketAmount(earningTicketAmount);
			data.setInvestAmount(investAmount);
			ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);

		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myInvitationStat end...");
		logger.info("api myInvitationStat total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}


    /**
	 * 我的花生豆分页列表
	 * @param response
	 * @param client
	 * @param token
	 * @param pageNumber
	 * @param pageSize
     * @return
     */
	@RequestMapping(value = "myIntegralPageList", method = RequestMethod.POST)
	public String myIntegralPageList(HttpServletResponse response, String client, String token, Integer pageNumber, Integer pageSize) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myIntegralPageList start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,  ApiUtil.getOperTerm(client));
		String type="0";
		if(clientToken != null ){
			List<CustomerIntegralHis> list = customerIntegralHisService.findPageList(clientToken.getCustomerId(),type, pageNumber, pageSize);
			List<Object> dataList = new ArrayList<Object>();
			for(CustomerIntegralHis cIntegralHis : list){
				MyIntegralResp data = new MyIntegralResp();
				data.setOpDt(DateUtils.formatDateTime(cIntegralHis.getOpDt()));
				data.setChangeReason(cIntegralHis.getChangeReason());
				data.setChangeVal(String.valueOf(cIntegralHis.getChangeVal()));
				String types = cIntegralHis.getChangeVal().compareTo(0) < 0 ? "2" : "1";
				data.setType(types);
				data.setTypeName(DictUtils.getDictLabel(cIntegralHis.getChangeType(), "customer_integral_change_type_dict", ""));
				dataList.add(data);
			}
			ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myIntegralPageList end...");
		logger.info("api myIntegralPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 通过token返回CustomerAccount
	 * @param token
	 * @return
	 * @author wanduanrui
	 */
	private CustomerAccount getCustomerAccountByToken(String token, String termType) {
		CustomerAccount  customerAccount = null;

		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, termType);
		if(clientToken != null ){
			customerAccount = customerAccountService.get(clientToken.getCustomerId());
		}
		return customerAccount;
	}

	/**
	 * 新增/修改收件人地址
	 * @param response
	 * @param request
	 * @param client
	 * @param token
	 * @param customerAddress
	 * @return
	 */
	private String customerAddressSave(HttpServletResponse response,HttpServletRequest request,
			String client, String token,
			CustomerAddress customerAddress){
		Map<String,Object> map = new HashMap<String,Object>();
		//数据校验
		List<String> messages  = ApiUtil.validateBean(customerAddress);
		if(messages == null){//如果表单数据合法性校验成功
			CustomerAccount customerAccount = getCustomerAccountByToken(token, ApiUtil.getOperTerm(client));
			if(customerAccount != null){
				customerAddress.setAccountId(customerAccount.getAccountId());
				customerAddress.setIsDefault(isDefaultConver(customerAddress.getIsDefault()));
				if(StringUtils.isBlank(customerAddress.getId())) {
					customerAddressService.insert(customerAddress);
					logger.debug("add customerAddress success");
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "新增收件人地址成功", true);
				} else {
					customerAddressService.update(customerAddress);
					logger.debug("modify customerAddress success,the addressId:"+customerAddress.getId());
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "修改收件人地址成功", true);
				}
			}else{
				ApiUtil.tokenInvalidRespMap(map);
			}
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
		}
		return renderString(response, map);
	}

	/**
	 * 默认地址转换
	 * @param isDefault
	 * @return
	 */
	public static String isDefaultConver(String isDefault){
		if(StringUtils.isBlank(isDefault)){
			return "";
		}
		return ProjectConstant.CUSTOMER_ADDRESS_IS_DEFAULT_NOT.equals(isDefault) ? "1" : "0";
	}


	/**
	 * 是否领取新年红包
	 * @param response
	 * @param client
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "hasGetNewYearGiftMoney", method = RequestMethod.POST)
	public String hasGetNewYearGiftMoney(HttpServletResponse response, String client, String token) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api hasGetNewYearGiftMoney start...");
		logger.info("【" + currentTime + "】hasGetNewYearGiftMoney income parameter: token=" + token + "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null){
			Long accountId = clientToken.getCustomerId();
			boolean isHasGet = marketingActivityAwardRecordService.hasGetJanuaryAward(accountId);
			if(isHasGet){
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "领取成功", true);
			}else{
				map.put(ApiConstant.API_STATUS_CODE, "2");
				map.put(ApiConstant.API_STATUS_TEXT, "未领取");
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + currentTime + "】hasGetNewYearGiftMoney out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api hasGetNewYearGiftMoney end...");
		logger.info("api hasGetNewYearGiftMoney total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}


	/**
	 * 获取新年红包
	 * @param response
	 * @param client
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "getNewYearGiftMoney", method = RequestMethod.POST)
	public String getNewYearGiftMoney(HttpServletResponse response, String client, String token) {
		Date startTime = new Date();
		long currentTime = System.currentTimeMillis();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api getNewYearGiftMoney start...");
		logger.info("【" + currentTime + "】getNewYearGiftMoney income parameter: token=" + token + "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null){
			Long accountId = clientToken.getCustomerId();
			Map<String,Object> result = marketingActivityAwardRecordService.getJanuaryActivityAward(accountId);
			if((boolean)result.get("isSuccess")){
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "领取成功", true);
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, String.valueOf(result.get("message")), false);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + currentTime + "】getNewYearGiftMoney out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api getNewYearGiftMoney end...");
		logger.info("api getNewYearGiftMoney total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 我的投资券-智能选券
	 * @param response
	 * @param client
	 * @param token
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "intelligentChoose", method = RequestMethod.POST)
	public String intelligentChoose(HttpServletResponse response, String client, String token, Double amount) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api intelligentChoose start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null){
			Long accountId = clientToken.getCustomerId();
			List<CashTicketBean> list = customerInvestmentTicketService.findChooseListByAccountId(accountId, amount);
			List<Long> resultList = CashTicketFacade.getCashTicketService().getBestTicket(list, amount);
			ApiUtil.mapRespData(map, resultList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api intelligentChoose end...");
		logger.info("api intelligentChoose total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}




}
