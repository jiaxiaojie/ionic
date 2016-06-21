package com.thinkgem.jeesite.modules.api.web;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.to.*;
import com.thinkgem.jeesite.modules.cms.service.ActivityService;
import com.thinkgem.jeesite.modules.entity.Activity;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.HttpTookit;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.ZxingHandler;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.base.BusinessConstant;
import com.thinkgem.jeesite.modules.api.po.LoginReq;
import com.thinkgem.jeesite.modules.api.po.RegisterReq;
import com.thinkgem.jeesite.modules.api.po.ResetPwdReq;
import com.thinkgem.jeesite.modules.api.service.IndexService;
import com.thinkgem.jeesite.modules.api.service.InvestmentAmountService;
import com.thinkgem.jeesite.modules.carousel.service.AdPositionInfoService;
import com.thinkgem.jeesite.modules.cms.CmsConstant;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.entity.AdPositionInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordTokenFront;
import com.thinkgem.jeesite.modules.sys.utils.InvestRankCacheUtils;
import com.hsbank.util.tool.LogUtil;
import com.hsbank.util.tool.MobileUtil;
import com.hsbank.util.type.StringUtil;

/**
 * 首页Controller
 * <p/>
 *
 * @createDate 2016-5-13
 */
@Controller("apiIndexController")
@RequestMapping(value = "${frontPath}/api/index", method = RequestMethod.POST)
public class IndexController extends APIBaseController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ProjectInvestmentRecordService projectInvestmentRecordService;
    @Autowired
    IndexService indexService;
    @Autowired
    private AdPositionInfoService adPositionInfoService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private InvestmentAmountService investmentAmountService;

    /**
     * 最新投资
     *
     * @param client client
     * @param pageNumber 页码
     * @param pageSize  页容量
     * @return  json
     */
    @ResponseBody
    @RequestMapping(value = "getLatestInvestPageList")
    public Map<String, Object> getLatestInvestPageList(String client, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api getLatestInvestPageList start...");
        HashMap<String, Object> map = new HashMap<String, Object>();

        Page<ProjectInvestmentRecord> page = projectInvestmentRecordService.findLatestInvestPageList(new Page<ProjectInvestmentRecord>(pageNumber, pageSize, true));
        List<Object> pRespList = new ArrayList<Object>();
        for (ProjectInvestmentRecord record : page.getList()) {
            InvestmentRecordsResp resp = new InvestmentRecordsResp();
            resp.setAvatar(ApiUtil.imageUrlConver(record.getCa().getAvatarImage()));
            resp.setAmount(record.getAmount());
            resp.setMobile(StringUtils.vagueMobile(record.getCb().getMobile()));
            resp.setIntervalTime(DateUtils.pastTime(record.getOpDt()));
            pRespList.add(resp);
        }
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("count", page.getCount());
        dataMap.put("resultList", pRespList);
        ApiUtil.mapRespData(map, dataMap, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api getLatestInvestPageList end...");
        logger.info("api getLatestInvestPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return map;
    }

    /**
     * 今日排行
     *
     * @param client clent
     * @return json
     */
    @ResponseBody
    @RequestMapping(value = "todayRanking")
    public Map<String, Object> todayRanking(String client) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api todayRanking start...");
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<InvestmentRecordsResp> cacheList = InvestRankCacheUtils.getInvestmentRankList();
        ApiUtil.mapRespData(map, cacheList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api getLatestInvestPageList end...");
        logger.info("api todayRanking total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return map;
    }

    /**
     * 获取验证码
     *
     * @param client    client
     * @param response response
     */
    @RequestMapping(value = "getVerifyCode", method = RequestMethod.GET)
    public void getVerifyCode(String client, HttpServletResponse response) {

        try {
            indexService.getVerifyCode(client, response);
        } catch (IOException e) {
            logger.error("获取验证码异常:", e);
        }
    }

    /**
     * pc端登录
     *
     * @param loginReq
     * @param response
     */
    @RequestMapping(value = "login")
    public void login(LoginReq loginReq, HttpServletResponse response) {

        Date startTime = new Date();
        HashMap<String, Object> map = new HashMap<String, Object>();
        // 校验登录验证码
        LogUtil.start("登录2");  
        try {
            Subject subject = SecurityUtils.getSubject();
            String host = HttpTookit.getRealIpAddr(currentRequest());
            Date a=new Date();
            logger.info("登录1 subject花费时间：【" + (a.getTime() - startTime.getTime()) + "ms】");
            //用户密码令牌
            UsernamePasswordTokenFront token = new UsernamePasswordTokenFront(loginReq.getMobile(), loginReq.getPassword().toCharArray(),
                    false, host, loginReq.getVerifyCode(), false, loginReq.getClient(), true);
            token.setRememberMe(false);
            subject.login(token);
            Date b=new Date();
            //给某用户记录token令牌
            LoginResp data = new LoginResp(token.getToken());
            ApiUtil.mapRespData(map,data, "登录成功", true);
            logger.info("登录1 usernamePasswordTokenFront 花费时间：【" + (b.getTime() - a.getTime()) + "ms】");
            //清空验证码
            indexService.validateCaptcha(loginReq.getClient(), false, true, loginReq.getVerifyCode());
            // 登录成功，验证码计算器清零
            indexService.imageAuth(loginReq.getMobile(), false, true);
            Date c=new Date();
            logger.info("登录1 清空验证码花费时间：【" + (c.getTime() - b.getTime()) + "ms】");
        } catch (Exception e) {
            //非授权异常，登录失败，验证码加1。
            if (!AuthenticationException.class.getName().equals(e.getClass().getName())) {
                ApiUtil.mapRespData(map, null, "账户名与密码不匹配，请重新输入", false);
                map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
                if (indexService.imageAuth(loginReq.getMobile(), true, false)) {
                    map.put(ApiConstant.API_STATUS_CODE, "2");
                }
            } else {
                ApiUtil.mapRespData(map, null, "验证码不正确", false);
                map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
            }
            SecurityUtils.getSubject().logout();
            logger.error("登录异常", e);
        }
        LogUtil.end("登录2");  
        printLog("登录1", loginReq, startTime, response, map);
    }

    /**
     * pc端注册
     *
     * @param response
     * @param client
     * @param registerReq
     */
    @RequestMapping(value = "register")
    public void register(HttpServletResponse response, String client, RegisterReq registerReq) {
        Date startTime = new Date();
        HashMap<String, Object> map = new HashMap<String, Object>();
        String result = indexService.register(currentRequest(), client, registerReq, map,HttpTookit.getRealIpAddr(currentRequest()));
        if (!BusinessConstant.SUCCESS.equals(result)) {
            ApiUtil.mapRespData(map, null, result, false);
        }
        printLog("注册", registerReq, startTime, response, map);
    }


    /**
     * 登陆注册广告位
     *
     * @param response
     * @param client
     * @param adCode
     * @return
     */
    @RequestMapping(value = "getAdPositionInfo", method = RequestMethod.POST)
    public String getAdPositionInfo(HttpServletResponse response, HttpServletRequest request, String client, String adCode) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api getAdPositionInfo  start...");
        HashMap<String, Object> map = new HashMap<String, Object>();
        String opTerm = ApiUtil.getOperTerm(client);
        //根据状态、终端和广告位类型查询出对应的广告位信息
        AdPositionInfo adPositionInfo = adPositionInfoService.findAdPositionInfoByAdCodeAndTerminalType(adCode, opTerm);
        if (adPositionInfo != null) {
            this.SetAdPositionInfo(request, adPositionInfo, map);
        } else {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "数据不存在", false);
        }
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api getAdPositionInfo  end...");
        logger.info("api getAdPositionInfo  total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }

    /**
     * 广告信息
     *
     * @param adPositionInfo
     * @param map
     * @return
     */
    public AdPositionResp SetAdPositionInfo(HttpServletRequest request, AdPositionInfo adPositionInfo, HashMap<String, Object> map) {
        AdPositionResp adPositionResp = new AdPositionResp();
        adPositionResp.setAdCode(StringUtil.dealString(adPositionInfo.getCode()));
        adPositionResp.setImageUrl(ApiUtil.imageUrlConver(adPositionInfo.getImage()) + "?version=" + adPositionInfo.getVersion());
        adPositionResp.setIsClick(NumberUtils.toLong(adPositionInfo.getCanClick()));
        adPositionResp.setType(NumberUtils.toLong(adPositionInfo.getType()));
        adPositionResp.setTarget(adPositionInfo.getTarget());
        adPositionResp.setDescription(adPositionInfo.getDescription());
        ApiUtil.mapRespData(map, adPositionResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        return adPositionResp;
    }


    /**
     * 累计募集
     * @param client client
     * @author liuguoqing
     */
    @ResponseBody
    @RequestMapping(value = "collectTotal")
    public Map<String, Object> collectTotal(String client) {
        logger.debug("=== 累计募集接口,输入参数：client:{}", client);
        Map<String, Object> map = new HashMap<String, Object>();
        String result = investmentAmountService.getInvestmentAmountByCache();
        ApiUtil.mapRespData(map, new BigDecimal(result), ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        logger.debug("=== 累计募集接口,输出参数：{}", result);
        return map;
    }


    /**
     * 最新公告分页列表接口
     *
     * @param client client
     * @param flag 记录数量统计标记
     * @param pageNumber 页码
     * @param pageSize 页容量
     * @return json
     *
     * @author liuguoqing
     */
    @ResponseBody
    @RequestMapping(value = "getNoticePageList")
    public Map<String, Object> getNoticePageList(String client, @RequestParam(required = false, defaultValue = "0") Integer flag, Integer pageNumber, Integer pageSize) {
        logger.debug("=== 最新公告分页列表接口,输入参数：client={}, pageNumber={}, pageSize={}", client, pageNumber, pageSize);
        Map<String, Object> map = new HashMap<>();
        PageResponse<NoticeResp> pageResp = articleService.getNoticePageList(CmsConstant.CATEGORYID_ZXGG, flag, pageNumber, pageSize);
        ApiUtil.mapRespData(map, pageResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        logger.debug("=== 最新公告分页列表接口,输出参数：{}", pageResp);
        return map;
    }

    /**
     * 最新公告详情
     *
     * @param client client
     * @param noticeId 公告Id
     *
     * @author liuguoqing
     */
    @ResponseBody
    @RequestMapping(value = "getNoticeDetails")
    public Map<String, Object> getNoticeDetails(String client, String noticeId) {
        logger.debug("=== 最新公告详情接口,输入参数：client={}, noticeId={}", client, noticeId);
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(noticeId)) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "缺少公告ID！", false);
            return map;
        }
        NoticeResp notice = articleService.getNoticeDetails(noticeId);
        if(StringUtils.isNotBlank(notice.getContent())){
            //图片路径加前缀
            notice.setContent(ApiUtil.prefixSrc(notice.getContent()));
        }
        ApiUtil.mapRespData(map, notice, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        logger.debug("=== 最新公告详情接口,输出参数：{}", notice);
        return map;
    }

    /**
     *
     * 媒体报道分页接口
     *
     * @param client client
     * @param newsType 新闻类型
     * @param flag  记录数量统计标记
     * @param pageNumber 页码
     * @param pageSize 页容量
     * @return json
     *
     * @author liuguoqing
     */
    @ResponseBody
    @RequestMapping(value = "getNewsPageList")
    public Map<String, Object> getNewsPageList(String client, Integer newsType, @RequestParam(required = false, defaultValue = "0") Integer flag, Integer pageNumber, Integer pageSize) {
        logger.debug("=== 最新媒体报道分页接口,输入参数：client={}, pageNumber={}, pageSize={}", client, pageNumber, pageSize);
        Map<String, Object> map = new HashMap<>();

        String categoryId = CmsConstant.CATEGORYID_FDXW;
        if(newsType == null){
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "缺少新闻类型！", false);
            return map;
        }else if(newsType.intValue() == 2){
                categoryId = CmsConstant.CATEGORYID_GSDT;
        }

        PageResponse<NewsResp> pageResp = articleService.getNewsPageList(categoryId, flag, pageNumber, pageSize);
        for (NewsResp newResp :pageResp.getResultList()) {
            newResp.setLogoUrl(ApiUtil.imageUrlConver(newResp.getLogoUrl()));
            newResp.setThumbnail(ApiUtil.imageUrlConver(newResp.getThumbnail()));
        }
        ApiUtil.mapRespData(map, pageResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        logger.debug("=== 最新媒体报道分页接口,输出参数：{}", pageResp);
        return map;
    }

    /**
     *   * 媒体报道详情
     *
     * @param client client
     * @param newsId 媒体报道Id
     * @return json
     *
     * @author liuguoqing
     */
    @ResponseBody
    @RequestMapping(value = "getNewsDetails")
    public Map<String, Object> getNewsDetails(String client, String newsId) {
        logger.debug("=== 媒体报道详情接口,输入参数：client={}, newsId={}", client, newsId);
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(newsId)) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "缺少媒体报道ID！", false);
            return map;
        }
        NewsResp news = articleService.getNewsDetails(newsId);
        if(news != null){
            news.setLogoUrl(ApiUtil.imageUrlConver(news.getLogoUrl()));
            news.setThumbnail(ApiUtil.imageUrlConver(news.getThumbnail()));
            if(StringUtils.isNotBlank(news.getContent())){
                //图片路径加前缀
                news.setContent(ApiUtil.prefixSrc(news.getContent()));
            }
        }
        ApiUtil.mapRespData(map, news, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        logger.debug("=== 媒体报道详情接口,输出参数：{}", news);
        return map;
    }

    /**
     * 忘记密码时重置密码
     *
     * @param response
     * @param client
     * @param resetPwdReq
     * @return
     */
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public void resetPassword(HttpServletResponse response, HttpServletRequest request, String client, ResetPwdReq resetPwdReq) {
    	
    	Date startTime = new Date();
        HashMap<String, Object> map = new HashMap<String, Object>();
       
        String result=indexService.resetPasswrod(client, resetPwdReq);
        if(!BusinessConstant.SUCCESS.equals(result)){
        	ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, result, false);
        }else{
        	ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, result, true);
        }
        printLog("重置密码",resetPwdReq, startTime, response, map);
    }
    /**
     * 短信验证码-验证
     *
     * @param response
     * @param client
     * @param resetPwdReq
     * @return
     */
    @RequestMapping(value = "checkSmsCode", method = RequestMethod.POST)
    public void checkSmsCode(HttpServletResponse response, HttpServletRequest request, String client, ResetPwdReq resetPwdReq) {
    	
    	Date startTime = new Date();
        HashMap<String, Object> map = new HashMap<String, Object>();
       
        String result=indexService.checkSmsCode(client, resetPwdReq);
        if(!BusinessConstant.SUCCESS.equals(result)){
        	ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, result, false);
        }else{
        	ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, result, true);
        }
        printLog("短信验证码-验证",resetPwdReq, startTime, response, map);
    }

    /**
     * 重置
     *
     * @param mobile
     * @param newPassword
     */
    public void resetPasswordDo(String mobile, String newPassword) {
        //重置密码
        customerAccountService.resetPasswordByMobile(mobile, newPassword);
    }

    /**
     * 检查手机号码
     *
     * @param mobile
     * @param map
     * @return
     */
    public boolean checkIsMobilePhone(String mobile, HashMap<String, Object> map) {
        logger.info("mobile =#####" + mobile + "#####");
        if (MobileUtil.isMobile(mobile)) {
            return true;
        } else {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "请输入正确的手机号码", false);
        }
        return false;
    }

    /**
     *   * 生成二维码
     *
     * @param client client
     * @param width 二维码宽度,默认120
     * @param height 二维码高度,默认120
     * @param content 二维码内容
     * @param request request
     * @param response response
     *
     * @author liuguoqing
     */
    @RequestMapping(value = "generateQrCode", method = RequestMethod.GET)
    public void generateQrCode(String client, @RequestParam(required = false, defaultValue = "120") Integer width, @RequestParam(required = false, defaultValue = "120") Integer height, String content,
                               HttpServletRequest request, HttpServletResponse response) {
        logger.debug("=== 生成二维码接口,输入参数：client={}, width={}, height={}, content={}", client, width, height, content);
        try {
            if (StringUtils.isBlank(content)) {
                content = " ";
            }
            String tempPath = request.getSession().getServletContext().getRealPath("/");
            String fileType = "png";
            String imgPath = tempPath + "/temp" + File.separator + System.currentTimeMillis() + "." + fileType;
            File imgFile = new File(imgPath);
            //生成二维码图片
            ZxingHandler.createQRCode(content, width, height, fileType, imgPath);
            //二维码图片增加logo
            String logoPath = tempPath + File.separator + "static/images/hsbank_28x28.png";
            ZxingHandler.addLogo_QRCode( imgFile, new File(logoPath), fileType, response.getOutputStream());
            //删除临时文件
            imgFile.delete();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    /**
     * 
     * <p>
     * Description:短信发送验证码<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年6月13日
     * @param request
     * @param response
     * @param client
     * @param mobile
     * @return
     * String
     */
	@RequestMapping(value = "sendSmsCode", method = RequestMethod.POST)
	public void sendSmsCode(HttpServletRequest request, HttpServletResponse response, String client, String mobile,String verifyCode) {
		Date startTime = new Date();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result=indexService.sendSmsByRegister(request, client, mobile, verifyCode);
		if(!result.equals(BusinessConstant.SUCCESS)){
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT,result, false);
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_OPERA_SUCCESS, "发送成功", true);
		}
		printLog("sendSmsCode", client, startTime, response, map);
	}


    /**
     * 活动接口
     * @author 黄宇晨
     * @Date 2016/6/16 13:31
     */
    @RequestMapping(value = "activityInfo", method = RequestMethod.POST)
    public String activityInfo(String client, HttpServletResponse response) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api activityInfo start...");
        // 解析client
        ClientProperty cProperty = ApiUtil.getClient(client);
        //根据项目的排序及终端类型，活动的状态查询活动信息
        HashMap<String, Object> map = new HashMap<String, Object>();
        CarouselResp data = new CarouselResp();
        Activity activity = activityService.findActivityInfo(ApiUtil.getOperaChannel(cProperty.getType()));
        if(activity!=null){
            data =  this.setCarouselResp(map,activity);
            ApiUtil.mapRespData(map,data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        }else{
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        }
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime)
                + "】api activityInfo end...");
        logger.info("api activityInfo total time consuming：【"
                + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }

    /**
     * 活动数据
     * @param map
     * @param activity
     * @Date 2016/6/16 14:31
     * @author 黄宇晨
     * @return
     */
    public CarouselResp setCarouselResp(HashMap<String, Object> map, Activity activity) {
        CarouselResp data = new CarouselResp();
        data.setImageUrl(ApiUtil.imageUrlConver(activity.getActivityCover()));
        data.setDescription(activity.getActivityDescription());
        data.setTitle(activity.getTitle());
        data.setType(1);    //活动
        data.setTarget(activity.getActivityJoin());
        String activity_period = DateUtils.formatDate(activity.getStartDt(), "yyyy-MM-dd") + "至" + DateUtils.formatDate(activity.getEndDt(), "yyyy-MM-dd");
        String activityPeriod = DateUtils.formatDate(activity.getStartDt(), "yyyy-MM-dd") + "至" + DateUtils.formatDate(activity.getEndDt(), "yyyy-MM-dd");
        data.setActivity_period(activity_period);
        data.setActivityPeriod(activityPeriod);
        return data;
    }

}
