package com.thinkgem.jeesite.modules.api.web;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.mail.handler.Mail;
import com.thinkgem.jeesite.common.utils.mail.handler.MailSendHandler;
import com.thinkgem.jeesite.common.utils.mail.handler.VelocityTemplate;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.Record;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.UnbindRecordResp;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.login.Token;
import com.thinkgem.jeesite.modules.api.to.BeforeWithdrawResp;
import com.thinkgem.jeesite.modules.api.to.MyResp;
import com.thinkgem.jeesite.modules.api.web.param.ActivateEmailParams;
import com.thinkgem.jeesite.modules.api.web.param.GetEmailCodeParams;
import com.thinkgem.jeesite.modules.api.web.param.UpdateEmailParams;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.log.service.LogSendValidateCodeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import com.hsbank.util.type.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 账户管理Controller
 *
 *<p/>
 * @createDate 2016-5-13
 */
@Controller("apiAccountManageController")
@RequestMapping(value="${frontPath}/api/accountManage")
public class AccountManageController extends APIBaseController {

    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerBaseService customerBaseService;
    @Autowired
    private CustomerBankCardService customerBankCardService;
    @Autowired
    private CustomerBalanceService customerBalanceService;
    @Autowired
    private YeepayCommonHandler yeepayCommonHandler;
    @Autowired
    private VelocityTemplate velocityTemplate;
    @Autowired
    private MailSendHandler mailSendHandler;
    @Autowired
    private LogSendValidateCodeService logSendValidateCodeService;

    /**
     * 账户设置-绑定邮箱(获取邮箱验证码)
     * @author 万端瑞
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getEmailCode")
    public Map<String,Object> getEmailCode(Token token, GetEmailCodeParams getEmailCodeParams){
        CustomerBase customerBase = customerBaseService.getByAccountId(token.getAccountId());
        String emailValidateCode =StringUtils.createValidateCode(6);
        Mail mail = new Mail();
        mail.setReceivers(getEmailCodeParams.getEmail());
        mail.setSubject("花生金服，邮箱验证码");
        mail.setTemplate(velocityTemplate.getMailTemplate("changeEmail",customerBase.getCustomerName(), emailValidateCode));
        mailSendHandler.sendEmail(mail);

        LogSendValidateCode logSendValidateCode = new LogSendValidateCode();
        logSendValidateCode.setEmail(getEmailCodeParams.getEmail());
        logSendValidateCode.setValidateCode(emailValidateCode);
        logSendValidateCode.setServiceTypeCode(ProjectConstant.SEND_VALIDATE_CODE_SERVICE_TYPE_CHANGE_EMAIL);
        logSendValidateCode.setOpDt(new Date());
        logSendValidateCode.setIp(currentRequest().getHeader("X-Real-IP"));
        logSendValidateCodeService.save(logSendValidateCode);
        return APIGenerator.createSuccessAPI();
    }


    /**
     * 账户设置-绑定邮箱(激活邮箱)
     * @param activateEmailParams
     * @author 万端瑞
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "activateEmail")
    public Map<String,Object> activateEmail(Token token, ActivateEmailParams activateEmailParams) {
        //执行激活操作
        HashMap<String,Object> map = customerAccountService.activateEmail(token.getAccountId(), activateEmailParams.getEmail(), activateEmailParams.getEmailCode());

        return APIGenerator.createResultAPI((boolean) map.get("success"),(String)map.get("message"));
    }

    /**
     * 账户设置-绑定邮箱(修改邮箱)
     * @param updateEmailParams
     * @author 万端瑞
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateEmail")
    public Map<String,Object> updateEmail(Token token, UpdateEmailParams updateEmailParams) {
        //执行修改邮箱操作
        HashMap<String,Object> map = customerAccountService.changeEmail(token.getAccountId(), updateEmailParams.getNewEmail(), updateEmailParams.getEmailCode(), updateEmailParams.getPassword());

        return APIGenerator.createResultAPI((boolean) map.get("success"),(String)map.get("message"));
    }

    /**
     * 账户设置——个人信息
     * @param response
     * @param client
     * @param token
     * @author huangyuchen
     * @return
     */
    @RequestMapping(value = "personalInfor", method = RequestMethod.POST)
    public String personalInfor(HttpServletRequest request, HttpServletResponse response, String client, String token) {
        Date startTime = new Date();
        long currentTime = System.currentTimeMillis();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api personalInfor start...");
        logger.info("【" + currentTime + "】my income parameter: token=" + token);
        HashMap<String, Object> map = new HashMap<String, Object>();
        //获取缓存数据
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
        //我的信息实体
        MyResp data = new MyResp();
        if(clientToken != null ){
            Long accountId = clientToken.getCustomerId();
            //根据会员id会员账户余额汇总数据
            CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
            //根据会员编号查询会员账号信息
            CustomerAccount customerAccount = customerAccountService.get(clientToken.getCustomerId());
            //根据会员编号查询会员基本信息
            CustomerBase customerBase = customerBaseService.get(Long.toString(clientToken.getCustomerId()));
            //若不为空则添加相关数据到我的信息中
            if(customerBalance != null ){
                //我的信息数据
                data = myAccount(request,customerBase,customerAccount, map);
            }else{
                ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
            }

        }else{
            ApiUtil.tokenInvalidRespMap(map);
        }
        Date endTime = new Date();
        logger.info("【" + currentTime + "】my out parameter: accountId=" + data.getAccountId() + ",mobile=" + data.getMobile() + ",customerName=" + data.getCustomerName() + ""
                + ",goldBalance=" + data.getGoldBalance() + ",congealVal=" + data.getCongealVal() + ",hasOpenThirdAccount=" + data.getHasOpenThirdAccount() + ",isNewUser=" + data.getIsNewUser() + "," + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT));
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api personalInfor end...");
        logger.info("api personalInfor total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }


    /**
     * 账户设置——个人信息数据
     * @param customerBase
     * @param customerAccount
     * @param map
     * @author huangyuchen
     * @return
     */
    public MyResp myAccount(HttpServletRequest request,CustomerBase customerBase, CustomerAccount  customerAccount,HashMap<String, Object> map){
        MyResp data = new MyResp();
        //会员账号Id
        data.setAccountId(String.valueOf(customerAccount.getAccountId()));
        //头像
        data.setAvatar(ApiUtil.imageUrlConver(customerAccount.getAvatarImage()));
        //账户名称（空表示昵称未设置）
        data.setAccountName(customerAccount.getAccountName());
        //昵称
        data.setNickname(StringUtil.dealString(customerAccount.getNickname()));
        AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
        String customerName ="";
        String certNum = "";
        if(ProjectConstant.HASOPENED.equals(accountInfoResp.getCode())){
            customerName = customerAccount.getCustomerBase().getCustomerName();
            certNum = customerAccount.getCustomerBase().getCertNum();
        }
        certNum = StringUtils.isNotBlank(certNum) ? StringUtils.vagueCertNum(certNum) : certNum;
        //真实姓名
        data.setCustomerName(StringUtil.dealString(customerName));
        data.setCertNum(StringUtil.dealString(certNum));
        //是否实名认证(0:未认证,1:已认证)
        data.setNameAuthCode(customerBase.getNameAuthCode());
        //手机号码
        data.setMobile(customerAccount.getCustomerBase().getMobile());
        //是否手机认证(0:未认证,1:已认证)
        data.setMobileAuthCode(customerBase.getMobileAuthCode());
        //将数据添加进map
        ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        return data;
    }



    /**
     * 账户设置——银行卡信息
     * @param response
     * @param request
     * @param client
     * @param token
     * @author huangyuchen
     * @return
     */
    @RequestMapping(value = "bankInfor", method = RequestMethod.POST)
    public String bankInfor(HttpServletResponse response, HttpServletRequest request, String client, String token) {
        Date startTime = new Date();
        long currentTime = System.currentTimeMillis();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api bankInfor start...");
        logger.info("【" + currentTime + "】bankInfor income parameter: token=" + token);
        HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
        BeforeWithdrawResp data = new BeforeWithdrawResp();
        if(clientToken != null ){
            //用户id
            Long accountId = clientToken.getCustomerId();
            //根据用户信息查询会员账户余额汇总
            CustomerBalance  customerBalance = customerBalanceService.get(accountId + "");
            //查询会员账户信息
            CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
            //查询会员银行卡信息
            CustomerBankCard card = customerBankCardService.getByAccountId(clientToken.getCustomerId());
            //查询用户易宝账户信息
            AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
            if(customerBalance != null){
                //我的信息数据
                data = bankCard(accountInfoResp,customerBalance, card);
                ApiUtil.mapRespData(map,data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
            }else{
                ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
            }
        }else{
            ApiUtil.tokenInvalidRespMap(map);
        }
        Date endTime = new Date();
        logger.info("【" + currentTime + "】my out parameter: bankCode=" + data.getBankCode() + ",bankName=" + data.getBankName() + ",bankLogo="+data.getBankLogo()+",cardNo=" + data.getCardNo()+ ""
                + ",status=" + data.getStatus() + ",statusName=" + data.getStatusName() + "," + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT));
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api bankInfor end...");
        logger.info("api bankInfor total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }




    /**
     * 账户设置-银行卡解绑结果
     * @param client
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "unbindBankCardResult")
    public Map<String,Object> unbindBankCardResult(String client, String token ) {
        long begin = System.currentTimeMillis();
        logger.debug("=== 账户设置-银行卡解绑结果接口,输入参数：client:{},token:{}",client, token);
        Map<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
        if (clientToken != null) {
            String status = "3";//操作失败！
            String statusName = "操作失败！";
            CustomerBankCard customerBankCard = customerBankCardService.getByAccountId(clientToken.getCustomerId());
            if(null != customerBankCard) {
                String unbindRequestNo = customerBankCard.getUnbindRequestNo();
                UnbindRecordResp unbindRecordResp = yeepayCommonHandler.queryUnbindRecord(unbindRequestNo);
                Record Record = (unbindRecordResp != null && unbindRecordResp.getRecords() != null && unbindRecordResp.getRecords().size() == 1 ? unbindRecordResp.getRecords().get(0) : null);
                if (unbindRecordResp != null) {
                    if (!"1".equals(unbindRecordResp.getCode())) {
                        status = "3";
                        statusName = "操作失败！";
                    } else {
                        if (Record != null && Record.getStatus() != null) {
                            if ("INIT".equals(Record.getStatus())) {
                                //预约解绑银行卡成功！
                                status = "2";
                                statusName = "预约解绑银行卡成功！";
                            } else if ("SUCCESS".equals(Record.getStatus())) {
                                //解绑银行卡成功！
                                status = "1";
                                statusName = "解绑银行卡成功！";
                            }
                        } else {
                            //解绑银行卡失败！
                            status = "0";
                            statusName = "解绑银行卡失败！";
                        }
                    }
                }
            }
            JSONObject json = new JSONObject();
            json.put("status",status);
            json.put("statusName",statusName);
            ApiUtil.mapRespData(map, json, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        } else {
            ApiUtil.tokenInvalidRespMap(map);
        }
        logger.debug("===账户设置-银行卡解绑结果接口,cost:{}, 输出参数：{}", (System.currentTimeMillis() - begin), map);
        return map;
    }
}
