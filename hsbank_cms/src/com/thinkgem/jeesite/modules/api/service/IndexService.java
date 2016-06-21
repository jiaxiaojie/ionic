package com.thinkgem.jeesite.modules.api.service;

import com.google.common.collect.Maps;
import com.hsbank.util.tool.MobileUtil;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CaptchaUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.UUIDUtils;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.BusinessConstant;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.po.LoginReq;
import com.thinkgem.jeesite.modules.api.po.RegisterReq;
import com.thinkgem.jeesite.modules.api.po.ResetPwdReq;
import com.thinkgem.jeesite.modules.api.to.LoginResp;
import com.thinkgem.jeesite.modules.api.to.RegisterResp;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.customer.service.CustomerClientTokenService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author pc
 * @version 2016-05-16
 */
@Service
public class IndexService {

    @Autowired
    private CustomerClientTokenService customerClientTokenService;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerBaseService customerBaseService;
    @Autowired
    private MarketFacadeService marketFacadeService;
    @Autowired
    private SmsCodeService smsCodeService;
    @Autowired
    private CustomerAccountDao customerAccountDao;
    @Autowired
    private SystemService systemService;
    /**
     *  获取验证码字节数组
     * @return
     */
    public void getVerifyCode(String clint, HttpServletResponse response)throws IOException {
        
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = CaptchaUtils.generateVerifyCode(4);  
        //存入会话session
        //存入验证码
        validateCaptcha(clint,false,false,verifyCode);
        //生成图片  
        int w = 88, h = 44;  
        CaptchaUtils.outputImage(w, h, response.getOutputStream(), verifyCode);  
    }

    /**
     * 校验图片验证码
     * @param request
     * @param validateCode
     * @return
     */
    public boolean validateCaptcha(HttpServletRequest request, String validateCode){
    	System.out.println("sessionId:"+request.getSession().getId());
        String code = (String)request.getSession().getAttribute(BusinessConstant.VALIDATE_CODE);
        System.out.println("缓存的验证码："+code);
        return validateCode.toUpperCase().equals(code);
    }

    /**
     * 登录成功，并向数据库中插入缓存信息。
     * @param mobile
     * @param opTerm
     * @param map
     * @return
     */
    public void loginSuccess(LoginReq loginReq, HashMap<String, Object> map,String ip)throws Exception{
        
    	CustomerAccount customerAccount = systemService.getCustomerAccountByAccountNameNew(loginReq.getMobile());
        
        ClientProperty cProperty = ApiUtil.getClient(loginReq.getClient());
        String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
        
        CustomerClientToken clientToken = new CustomerClientToken();
        clientToken.setCustomerId(customerAccount.getAccountId());
        clientToken.setTermType(opTerm);
        clientToken.setToken(UUIDUtils.getToken());
        clientToken.setLastDt(new Date());
        String token = customerClientTokenService.operaCustomerClientTokenByPc(clientToken, opTerm);
        
        //调用登录活动
        doLoginActivity(customerAccount, opTerm);
        //更新登陆时间和IP
        customerAccount.setLastLoginDt(new Date());
        customerAccount.setLastLoginIp(ip);
        customerAccount.setLastLoginTermCode(opTerm);
        customerAccountDao.update(customerAccount);
        LoginResp data = new LoginResp();
        data.setToken(token);
        ApiUtil.mapRespData(map, data, "登录成功", true);
    }

    /**
     * 注册校验参数
     * @param request
     * @param client
     * @param registerReq
     * @param map
     * @return
     */
    public String register(HttpServletRequest request,String client,RegisterReq registerReq,HashMap<String, Object> map,String ip){
        String result;
        List<String> messages  = ApiUtil.validateBean(registerReq);
        if(messages!=null){
            return messages.get(0);
        }
        result=checkMobileCanUse(registerReq.getMobile());
        if(!BusinessConstant.SUCCESS.equals(result)){
            return result;
        }
        String opTerm = ApiUtil.getOperTerm(client);
        if(ProjectConstant.OP_TERM_DICT_PC.equals(opTerm)&&!validateCaptcha(client, true, false, registerReq.getVerifyCode())){
        	return "请输入正确的图片验证码";
        }
        if(!smsCodeService.auth(registerReq.getMobile(), registerReq.getSmsCode())){
            return "请输入正确的短信验证码";
        }
        //邀请码校验
        if(StringUtils.isNotBlank(registerReq.getInviteCode())){
             result=this.checkInviteCode(registerReq);
            if(!BusinessConstant.SUCCESS.equals(result)){
               return result;
            }
        }
        //外部编号校验
        if(!StringUtils.isNotBlank(registerReq.getSubid())){
            if(!checkExtendNoCanUse(registerReq.getSubid())){
                return "外部编号已注册";
            }
        }
        //BASE64解码
        ClientProperty cProperty = ApiUtil.getClient(client);
        String registerChannel = ApiUtil.getOperaChannel(cProperty.getType());
        String registerSource = cProperty.getWechat()!=null && cProperty.getWechat().getUa()!=null ? cProperty.getWechat().getUa() : "";
        //生成账户名
        String accountName = customerAccountService.registerWithMobileAndReturnAccountName
                (request,registerChannel, registerReq.getMobile(),
                         registerReq.getPassword(), registerReq.getInviteCode(), registerReq.getChannel(),
                         registerReq.getLotteryToken(),registerReq.getSubid(), registerSource);
        //校验账户名是否生成
        if(!StringUtils.isNotBlank(accountName)) {
            return "发生了点意外，注册失败，请重新注册。";
        }
        CustomerAccount customerAccount = customerAccountService.getByMobile(registerReq.getMobile());
        //调用注册活动
        doRegisterActivity(customerAccount, registerChannel,registerReq.getChannel());
        registerSuccess(customerAccount, registerChannel, map,ip);
        return BusinessConstant.SUCCESS;
    }
    /**
     * 检查注册手机是否可用
     * @param mobile
     * @return
     */
    public String checkMobileCanUse(String mobile) {
        if(!MobileUtil.isMobile(mobile)){
            return "请输入正确的手机号码";
        }
        CustomerBase customerBase = new CustomerBase();
        customerBase.setMobile(mobile);
        if(!customerBaseService.checkMobileCanUse(customerBase)){
            return "手机号码已注册，请直接登录";
        }
        return BusinessConstant.SUCCESS;
    }
    public String checkInviteCode(RegisterReq registerReq) {
        if(!MobileUtil.isMobile(registerReq.getInviteCode())){
            return "请输入正确的手机号码!";
        }
        if(registerReq.getMobile().equals(registerReq.getInviteCode())){
            return "邀请人手机号码不能填自己";
        }
        CustomerBase customerBase = new CustomerBase();
        customerBase.setMobile(registerReq.getInviteCode());
        if(customerBaseService.checkMobileCanUse(customerBase)){
            return "邀请人手机号码不存在!";
        }
        return BusinessConstant.SUCCESS;
    }
    /**
     * 检查扩展编号是否可用
     * @param extendNo
     * @return
     */
    public boolean checkExtendNoCanUse(String extendNo) {
        return customerAccountService.checkOuterIdCanUse(extendNo);
    }
    /**
     * 调用注册活动
     * @param customerAccount
     * @param opTerm
     */
    public void doRegisterActivity(CustomerAccount customerAccount, String opTerm, String channel) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, customerAccount.getAccountId());
        map.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_REGISTER);
        map.put(MarketConstant.CHANNEL_PARA, opTerm);
        map.put(MarketConstant.SOURCE_CHANNEL_PARA, channel);
        marketFacadeService.register(map);
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
     * 注册成功
     * @param customerAccount
     * @param opTerm
     * @param map
     */
    public void  registerSuccess(CustomerAccount customerAccount, String opTerm, HashMap<String, Object> map,String ip){
        CustomerClientToken clientToken = new CustomerClientToken();
        clientToken.setCustomerId(customerAccount.getAccountId());
        clientToken.setTermType(opTerm);
        clientToken.setToken(UUIDUtils.getToken());
        clientToken.setLastDt(new Date());
        String token = customerClientTokenService.operaCustomerClientToken(clientToken, opTerm);
        //调用登录活动
        doLoginActivity(customerAccount, opTerm);
        //更新登陆时间和IP
        customerAccount.setLastLoginDt(new Date());
        customerAccount.setLastLoginIp(ip);
        customerAccount.setLastLoginTermCode(opTerm);
        customerAccountDao.update(customerAccount);
        RegisterResp data = new RegisterResp();
        data.setToken(token);
        ApiUtil.mapRespData(map, data, "注册成功", true);
    }
    /**
     * 校验图形验证码
     * @param mobile 用户名
     * @param isFail 计数加1
     * @param clean 计数清零
     * @return
     */
    public boolean imageAuth(String mobile, boolean isFail, boolean clean){
        Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
        if (loginFailMap==null || loginFailMap.size()==0){
            loginFailMap = Maps.newHashMap();
        }
        Integer loginFailNum = loginFailMap.get(mobile);
        if (loginFailNum==null){
            loginFailNum = 0;
        }
        if (isFail){
            loginFailNum++;
            loginFailMap.put(mobile, loginFailNum);
        }
        if (clean){
            loginFailMap.remove(mobile);
        }
        CacheUtils.put("loginFailMap", loginFailMap);
        return loginFailNum >= 3;
    }
    /**
     * 
     * <p>
     * Description:生成验证码<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年5月25日
     * @param clint
     * @param isvalidate
     * @param clean
     * @return
     * boolean
     */
    @SuppressWarnings("unchecked")
    public boolean validateCaptcha(String clint,boolean isvalidate,boolean clean,String code){
    	ClientProperty clientProperty=ApiUtil.getClient(clint);
    	boolean result=false;
    	if(StringUtils.isEmpty(clientProperty.getLocalId())){
    		return false;
    	} 
    	Map<String, String> ImageMap = (Map<String, String>) CacheUtils.get("ImageMap");
        if (ImageMap==null || ImageMap.size()==0){
        	ImageMap = Maps.newHashMap();
        }
        //若是校验验证码的话，在缓存取出验证码。
        if(isvalidate){
          
          String redisCode=ImageMap.get(clientProperty.getLocalId());
           
          if(StringUtils.isNotBlank(redisCode)&&redisCode.equalsIgnoreCase(code)){
        	 result=true;
          } 
        }else{
          //生成图片 ，将图片的文字存入缓存
          ImageMap.put(clientProperty.getLocalId(), code);	
        }
        if(clean){
        	ImageMap.remove(clientProperty.getLocalId());
        }
        CacheUtils.put("ImageMap", ImageMap);
        return result;
    }
    /**
     * 
     * <p>
     * Description:缓存短信验证码<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年6月14日
     * @param mobile
     * @param clean
     * @param isPut
     * @param smsCode
     * @return
     * String
     */
    @SuppressWarnings("unchecked")
    public String cacheSmsCode(String mobile, boolean clean,boolean isPut,String smsCode){
    	String result="";
        Map<String, String> phoneMap = (Map<String, String>) CacheUtils.get("phoneMap");

        if (phoneMap==null || phoneMap.size()==0){
        	phoneMap = Maps.newHashMap();
        }
        if(isPut){
        	phoneMap.put(mobile, smsCode);
        }else{
        	result=phoneMap.get(mobile);
        }
        if (clean){
        	phoneMap.remove(mobile);
        }
        CacheUtils.put("phoneMap", phoneMap);
        return result;
    }
    /**
     * 
     * <p>
     * Description:发送短信验证码，需要图片验证<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年6月13日
     * @param request
     * @param client
     * @param mobile
     * @param verifyCode
     * @return
     * String
     */
    public String sendSmsByRegister(HttpServletRequest request,String client, String mobile,String verifyCode){
    	
    	if(StringUtils.isBlank(mobile)&&!MobileUtil.isMobile(mobile)){
    		return "请输入正确的手机号码";
		}
		if(!validateCaptcha(client, true, false, verifyCode)){
			return "请输入正确的图片验证码";
        }
		smsCodeService.sendSmsCode(request, mobile, ProjectConstant.SEND_VALIDATE_CODE_SERVICE_TYPE_REGISTER);
		
    	return BusinessConstant.SUCCESS;
    }
    /**
     * 
     * <p>
     * Description:重置密码<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年6月14日
     * @param client
     * @param resetPwdReq
     * @return
     * String
     */
    public String resetPasswrod(String client,ResetPwdReq resetPwdReq){
    	
        List<String> messages = ApiUtil.validateBean(resetPwdReq);
        //入参数据校验
        if(messages!=null){
        	return messages.get(0);
        }
        //检查手机号码
        if(!MobileUtil.isMobile(resetPwdReq.getMobile())){
        	return "请输入正确的手机号码";
        }
        String opTerm = ApiUtil.getOperTerm(client); 
        //图片验证码校验
        if (ProjectConstant.OP_TERM_DICT_PC.equals(opTerm)&&!validateCaptcha(client, true, false, resetPwdReq.getVerifyCode())) {
        	return "请输入正确的图片验证码";
        }
        String cacheSmscode=this.cacheSmsCode(resetPwdReq.getMobile(), false, false, resetPwdReq.getSmsCode());
        //短信验证码校验
        if (!cacheSmscode.equals(resetPwdReq.getSmsCode())) {
        	return "请输入正确的短信验证码";
        }
        //清除手机验证码缓存问题
        this.cacheSmsCode(resetPwdReq.getMobile(), true, false, resetPwdReq.getSmsCode());
        this.validateCaptcha(client, false, true, resetPwdReq.getVerifyCode());
        //重置密码动作
        customerAccountService.resetPasswordByMobile(resetPwdReq.getMobile(), resetPwdReq.getNewPassword());
    	return BusinessConstant.SUCCESS;
    }
    /**
     * 短信验证码-验证
     * <p>
     * Description:描述<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年6月14日
     * @param client
     * @param resetPwdReq
     * @return
     * String
     */
    public String checkSmsCode(String client,ResetPwdReq resetPwdReq){
    	String opTerm = ApiUtil.getOperTerm(client); 
    	//检查手机号码
    	if(!MobileUtil.isMobile(resetPwdReq.getMobile())){
    		return "请输入正确的手机号码";
    	}
    	if(ProjectConstant.OP_TERM_DICT_PC.equals(opTerm)&&StringUtils.isEmpty(resetPwdReq.getVerifyCode())){
        	return "请输入图片验证码";
        }
        if(StringUtils.isEmpty(resetPwdReq.getSmsCode())){
        	return "请输入短信验证码";
        }
        //图片验证码校验
        if (!validateCaptcha(client, true, false, resetPwdReq.getVerifyCode())) {
        	return "请输入正确的图片验证码";
        }
        //短信验证码校验
        if (!smsCodeService.auth(resetPwdReq.getMobile(), resetPwdReq.getSmsCode())) {
        	return "请输入正确的短信验证码";
        }
        this.cacheSmsCode(resetPwdReq.getMobile(), false, true, resetPwdReq.getSmsCode());
    	return BusinessConstant.SUCCESS;
    }
}
