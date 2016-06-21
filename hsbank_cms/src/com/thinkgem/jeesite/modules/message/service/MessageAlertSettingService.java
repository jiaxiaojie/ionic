/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.project.service.util.handler.TicketHandler;
import com.thinkgem.jeesite.modules.sms.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MessageAlertSetting;
import com.thinkgem.jeesite.modules.message.dao.MessageAlertSettingDao;

/**
 * 消息提醒设置Service
 * @author hyc
 * @version 2016-05-09
 */
@Service
@Transactional(readOnly = true)
public class MessageAlertSettingService extends CrudService<MessageAlertSettingDao, MessageAlertSetting> {

	@Autowired
	private MessageAlertSettingDao messageAlertSettingDao;

	public MessageAlertSetting get(String id) {
		return super.get(id);
	}
	
	public List<MessageAlertSetting> findList(MessageAlertSetting messageAlertSetting) {
		return super.findList(messageAlertSetting);
	}

	/**
	 * 项目满标或项目结束短息提醒风控
	 * @param projectName
     */
	public void sendSmsFromFengControl(String projectName ) {
		try{
			MessageAlertSetting ms = messageAlertSettingDao.findAlertSmsFromMessageAlertSetting();
		if(ms!=null){
			String[] mobiles = ms.getMobile().split(",");
			String content = ms.getContent();
			if (content.contains("#projectName#")) {
				content = content.replaceAll("#projectName#", projectName);
				String contents = content;
				List<String> mobileList = new ArrayList<String>();
				for (String mobile : mobiles) {
					mobileList.add(mobile);
				}
				if (mobiles != null) {
					SmsUtils.sendSms(mobileList, contents);
				}
			}
		}
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	
	public Page<MessageAlertSetting> findPage(Page<MessageAlertSetting> page, MessageAlertSetting messageAlertSetting) {
		return super.findPage(page, messageAlertSetting);
	}
	
	@Transactional(readOnly = false)
	public void save(MessageAlertSetting messageAlertSetting) {
		if(messageAlertSetting!=null){
			messageAlertSetting.setCreateDt(new Date());
		}
		super.save(messageAlertSetting);
	}
	
	@Transactional(readOnly = false)
	public void delete(MessageAlertSetting messageAlertSetting) {
		super.delete(messageAlertSetting);
	}
	
}