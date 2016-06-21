/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.BusinessConstant;
import com.thinkgem.jeesite.modules.api.po.PersonalReservate;
import com.thinkgem.jeesite.modules.api.service.IndexService;
import com.thinkgem.jeesite.modules.entity.CustomerLeaveMessage;
import com.thinkgem.jeesite.modules.entity.PersonalTailor;
import com.thinkgem.jeesite.modules.feedback.dao.CustomerLeaveMessageDao;
import com.thinkgem.jeesite.modules.personal.dao.PersonalTailorDao;
import com.hsbank.util.tool.MobileUtil;

/**
 * 客户留言Service
 * @author ydt
 * @version 2016-02-22
 */
@Service
@Transactional(readOnly = true)
public class CustomerLeaveMessageService extends CrudService<CustomerLeaveMessageDao, CustomerLeaveMessage> {

	@Autowired
	IndexService indexService;
	@Autowired
	PersonalTailorDao personalTailorDao;
	public CustomerLeaveMessage get(String id) {
		return super.get(id);
	}
	
	public List<CustomerLeaveMessage> findList(CustomerLeaveMessage customerLeaveMessage) {
		return super.findList(customerLeaveMessage);
	}
	
	public Page<CustomerLeaveMessage> findPage(Page<CustomerLeaveMessage> page, CustomerLeaveMessage customerLeaveMessage) {
		return super.findPage(page, customerLeaveMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerLeaveMessage customerLeaveMessage) {
		super.save(customerLeaveMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerLeaveMessage customerLeaveMessage) {
		super.delete(customerLeaveMessage);
	}

	/**
	 *
	 * <p>
	 * Description:预约项目功能<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年5月23日
	 * @param personalReservate
	 * @param request
	 * @return
	 * @throws Exception
	 * String
	 */
	@Transactional(readOnly = false)
	public String  saveReservationInfor(PersonalReservate personalReservate, HttpServletRequest request)throws  Exception{

		List<String> messages= ApiUtil.validateBean(personalReservate);
		if(messages!=null){
			return messages.get(0);
		}
		if(!MobileUtil.isMobile(personalReservate.getMobile())){
			return "请输入正确的手机号码";
		}
		if(StringUtils.isNotEmpty(personalReservate.getEmail())&&!isEmail(personalReservate.getEmail())){
			return "请输入正确的邮箱号码";
		}
		if(!indexService.validateCaptcha(personalReservate.getClient(), true, false, personalReservate.getVerifyCode())){
			return "请输入正确的图片验证码";
		}
		PersonalTailor personalTailor=personalTailorDao.queryByState(personalReservate.getProjectId(), BusinessConstant.PERSONAL_PASS);
		if(personalTailor==null){
			return "此项目已结束";
		}
		CustomerLeaveMessage customerLeaveMessage=new CustomerLeaveMessage();
		customerLeaveMessage.setIp(getRealIpAddr(request));
		customerLeaveMessage.setMobile(personalReservate.getMobile());
		customerLeaveMessage.setEmail(personalReservate.getEmail());
		customerLeaveMessage.setName(personalReservate.getName());
		customerLeaveMessage.setContent(personalReservate.getContent());
		customerLeaveMessage.setPersonalId(personalReservate.getProjectId().longValue());
		customerLeaveMessage.setOpDt(new Date());
		customerLeaveMessage.setType("2");
		this.save(customerLeaveMessage);
		return BusinessConstant.SUCCESS;
	}

	/**
	 * 校验邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return Pattern.matches(REGEX_EMAIL, email);
	}
	/**
	 * <p>
	 * Description:获得ip地址<br />
	 * </p>
	 *
	 * @author justin.xu
	 * @version 0.1 2014年4月16日
	 * @param request
	 * @return String
	 */
	public static String getRealIpAddr(HttpServletRequest request) {
		String ip = null;
		if (StringUtils.isNotEmpty((ip = request.getParameter("ip")))) {
			return ip;
		}

		ip = request.getHeader("X-Real-IP");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}